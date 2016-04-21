package com.example.myappnew;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static com.example.myappnew.R.menu.main;

public class DetailsActivity extends AppCompatActivity {

    private String name;
    private String img;
    private String description;
    private FoodName foodName;
    private Handler handler;
    private Integer count = 0;//判断是否收藏0和1
    private String collect;
    private List<String> list = new ArrayList<>();
    //    private DBHelper dbHelper;
//    private static SQLiteDatabase db;
//    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        description = intent.getStringExtra("description");
        img = intent.getStringExtra("img");

//        MenuView.ItemView item =  (MenuView.ItemView) findViewById(R.id.action_collect);


        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = HttpUtils.Data("http://www.tngou.net/api/food/name?name=" + name);
                Message message1 = handler.obtainMessage();
                message1.what = 0;
                message1.obj = s;
                handler.sendMessage(message1);
            }
        }).start();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        String s = (String) msg.obj;
                        jsonStr(s);

                        break;
                }
            }
        };
//        id = intent.getIntExtra("id",1);
//        format = String.format("http://www.tngou.net/api/food/show?id=%d", id);
//        dbHelper = new DBHelper(this);
//        db = dbHelper.getReadableDatabase();


        TextView textView = (TextView) findViewById(R.id.name_tv);
        TextView descriptionTV = (TextView) findViewById(R.id.description);
        ImageView imageView = (ImageView) findViewById(R.id.Detial_iv);

        textView.setText(name);
        descriptionTV.setText(description);
        Picasso.with(this).load(img).into(imageView);


    
//                  查询已收藏的name
        BmobQuery<FoodName> bmobQuery = new BmobQuery<>();//通过BmobQuery去bmob上查找数据
        bmobQuery.addQueryKeys("foodName");//查询一列
        bmobQuery.findObjects(this, new FindListener<FoodName>() {
            @Override
            public void onSuccess(List<FoodName> object) {//查询成功的操作
                // TODO Auto-generated method stub
                Log.d("lenve", "onSuccess() returned:  +  " +object.size());
                for (FoodName name : object) {//遍历这列数据
                    collect = name.getFoodName();
                    list.add(collect);//用个list存起来
                }
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub

            }
        });




    }

    private void jsonStr(String s) {
        try {
            JSONObject jo = new JSONObject(s);
            String message = jo.getString("message");
            TextView messageTV = (TextView) findViewById(R.id.message);
            messageTV.setText(Html.fromHtml(message));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //加载收藏菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//显示右上角的菜单栏
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(main, menu);


        return true;//返回一个true表示显示出来。false表示隐藏
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_collect) {          //分享的点击事件\
            switch (count) {
                case 0:
                    foodName = new FoodName();
                    //注意：不能调用gameScore.setObjectId("")方法
                    foodName.setFoodName(name);
                    foodName.setCount(1);
                    foodName.save(this, new SaveListener() {
                        @Override
                        public void onSuccess() {

                            item.setTitle("已收藏");
                            //                    Log.d("lenve", "onSuccess() returned:  +  " + format);
                            Toast.makeText(DetailsActivity.this, "添加数据成功，返回objectId为："+ foodName.getFoodName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int code, String arg0) {
                            // 添加失败
                        }
                    });
                    return true;

                case 1:
                    break;

            }

        }

        return super.onOptionsItemSelected(item);
    }


//    public void btn(View view) {
//
////            // 使用rawQuery语句查询数据库
////            Cursor cursor = db.rawQuery("select * from " + DBHelper.FOODNAME + " where name=?",
////                    new String[] { "name" });
////            while (cursor.moveToNext()) {
////                String id = cursor.getString(0);
////                String name = cursor.getString(cursor.getColumnIndex("name"));
////                System.out.print("----------------------------------------------------");
////                Log.d("qianfeng", "id:" + id + ";username:" + name);
//            }
//
//    }
}