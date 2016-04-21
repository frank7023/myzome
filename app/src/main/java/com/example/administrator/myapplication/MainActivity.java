package com.example.administrator.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ClassifyEntity> classifies;//下载实体集
    private List<Fragment> fragments;//fragment集
//    private GoogleApiClient client;
    private Handler handle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        String str = ((String) msg.obj);
                        parseJson(str);
                        initView();
                        break;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                String string = HttpUtils.getData("http://www.tngou.net/api/food/classify");
                Message msg = handle.obtainMessage();
                msg.what = 0;
                msg.obj = string;
                handle.sendMessage(msg);
            }
        }).start();
    }

//    public void downlook() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpURLConnection con = null;
//                BufferedInputStream bis = null;
//                StringBuffer result = new StringBuffer();
//                try {
//                    URL url = new URL("http://www.tngou.net/api/food/classify");
//                    con = ((HttpURLConnection) url.openConnection());
//                    con.setConnectTimeout(5000);
//                    con.connect();
//                    if(con.getResponseCode() == 200) {
//                        bis = new BufferedInputStream(con.getInputStream());//没有这句，报空指针异常
//                        int len;
//                        byte[] buf = new byte[1024];
//                        while ((len = bis.read(buf)) != -1) {
//                            str = result.append(new String(buf, 0, len)).toString();
//                        }
//                    }
//                    handle.sendEmptyMessage(1);/////////////////////////////子线程发数据给子线程处理
//
//                } catch (java.io.IOException e) {
//                    e.printStackTrace();
//                }finally {
//                    if (con != null){
//                        con.disconnect();
//                    }
//                    if(bis != null){
//                        try {
//                            bis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }).start();
//    }

    private void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp);
        TabLayout tabLayout = ((TabLayout) findViewById(R.id.tab_layout));
        fragments = new ArrayList<>();//没有new这个fragments，所以报错了
        for(ClassifyEntity classify : classifies){
            Fragment fragment = BaseFragment.getInstance(classify.getId());
            fragments.add(fragment);
        }
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), fragments, classifies);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);//传进去一个高效tab布局库里
    }

    private void parseJson(String str) {
        classifies = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(str);
            JSONArray tngou = json.getJSONArray("tngou");
            for (int i = 0; i < tngou.length(); i++) {
                JSONObject data = tngou.getJSONObject(i);
                classifies.add(new ClassifyEntity(data.getInt("id"), data.getString("name")));
            }
//            handle.sendEmptyMessage(0);/////////////////////主线程将消息发送给子线程
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
