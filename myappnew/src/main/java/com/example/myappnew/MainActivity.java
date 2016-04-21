package com.example.myappnew;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collect.CollectActivity;
import com.example.pic.PicActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String name = null;
    private List<Fragment> fragments;
    private Handler handler;
    private List<ClassifyEntity> classifies;//分类
    private TextView userLogin;//登录





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//接收从登录界面返回的数据
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                String username = getSharedPreferences("userinfo", MODE_PRIVATE).getString("name", "");
                if (username != null && !"".equals(username)) {
                    userLogin.setText(username);
                }
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this, "ae97ce0f17de0cba9c15db0de3902d13");//初始化bmod



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //邮件悬浮按钮FloatingActionButton的点击事件设置，已删除，，


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);//左边弹出列表
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        userLogin = (TextView) navigationView.findViewById(R.id.login);


        BmobUser bmobUser = BmobUser.getCurrentUser(this);//拿用户名的缓存要在findViewById之后拿，才能设置给textview，但是还有一个bug，就是在登录状态注册后回到主界面时，不会更新用户名，所以，要把注册按钮放在登录页里，要想注册，就得退出登录，来到登录页才给注册，还有，登录按钮那里也要限制在按钮的text为“请登录”时才允许点击。不然已经登录了还能再次进去登录，那不逗我？
        if(bmobUser != null){                            //把注册放在登录页之后，能及时更新用户名了。
            // 允许用户使用应用
            String s = bmobUser.getUsername().toString();
            userLogin.setText(s);
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
        }


        userLogin.setOnClickListener(new View.OnClickListener() {//登录
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                if (((TextView) v).getText().equals("登录")) {//只有在登录按钮的文本为“请登录”时，才给点击
                    startActivityForResult(new Intent(MainActivity.this,LoginActivity.class),1);//用 startActivityForResult启动登录界面
                }
            }
        });



        navigationView.setNavigationItemSelectedListener(this);


//        if (!"".equals(name)) {
//            Intent intent = getIntent();
//            name = intent.getStringExtra("name");
//            userRegister.setText(name);
//        }


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        String str = ((String) msg.obj);
                        if (str == null || "".equals(str)) {
                            Toast.makeText(MainActivity.this, "无法连接网路", Toast.LENGTH_SHORT).show();
                        } else {
                            parseJson(str);
                            initView();
                        }
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = HttpUtils.Data("http://www.tngou.net/api/food/classify");
                Message msg = handler.obtainMessage();
                msg.what = 0;
                msg.obj = s;
                handler.sendMessage(msg);
            }
        }).start();




    }

    private void initView() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.new_navigation_bar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.new_vp);
        fragments = new ArrayList<>();
        for (ClassifyEntity classify : classifies) {
            Fragment fragment = BaseFragment.getInstence(classify.getId());
            fragments.add(fragment);
        }
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), fragments, classifies);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void parseJson(String str) {
        classifies = new ArrayList<ClassifyEntity>();
        try {
            JSONObject jo = new JSONObject(str);
            JSONArray tngou = jo.getJSONArray("tngou");
            for (int i = 0; i < tngou.length(); i++) {
                JSONObject data = tngou.getJSONObject(i);
                classifies.add(new ClassifyEntity(data.getInt("id"), data.getString("name")));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
                              //收藏放在详情页里了，那三个点
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_collect) {          //收藏的点击事件
            Toast.makeText(MainActivity.this, "谢谢收藏", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.处理导航视图条目点击这里
        int id = item.getItemId();

        if (id == R.id.MyHealth) {    //健康助手
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {//地道
            Intent intent = new Intent(this, NewActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {//幻灯片
            Intent intent = new Intent(this, PicActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {//管理
            Intent intent = new Intent(this, ExplainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_collect) {//收藏
            Intent intent = new Intent(this, CollectActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {// 退出
            BmobUser.logOut(this);   //清除缓存用户对象
            BmobUser currentUser = BmobUser.getCurrentUser(this); // 现在的currentUser是null了
            userLogin.setText("登录");
            Toast.makeText(MainActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
