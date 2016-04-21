package com.example.collect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myappnew.FoodName;
import com.example.myappnew.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class CollectActivity extends AppCompatActivity {
    private List<String> list2 = new ArrayList<>();//用来存放食物名，将食物名收藏起来，再通过listView的点击事件去访问食物详情
    private ListView listView;
    private String foodName1;//食物名
//    private List<FoodEntity> list = new ArrayList<>();
//    private Handler handler;
//    private String foodUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        listView = (ListView)findViewById(R.id.collect_lv);

        //只返回FoodName表的foodName这列的值
        BmobQuery<FoodName> bmobQuery = new BmobQuery<>();//通过BmobQuery去bmob上查找数据
        bmobQuery.addQueryKeys("foodName");//查询一列
//        bmobQuery.addQueryKeys("foodUri");
        bmobQuery.findObjects(this, new FindListener<FoodName>() {
            @Override
            public void onSuccess(List<FoodName> object) {//查询成功的操作
                // TODO Auto-generated method stub
                Log.d("lenve", "onSuccess() returned:  +  " +object.size());
                for (FoodName name : object) {//遍历这列数据
                   foodName1 = name.getFoodName();
//                    foodUri = name.getFoodUri();
//                    Log.d("lenve", "onSuccess() returned:  +  " + foodName1);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String s = HttpUtils.Data(foodUri);
//                            Message msg = handler.obtainMessage();
//                            msg.what = 0;
//                            msg.obj = s;
//                            handler.sendMessage(msg);
//                        }
//                    }).start();
//                    handler = new Handler(){
//                        @Override
//                        public void handleMessage(Message msg) {
//                            switch (msg.what) {
//                                case 0:
//                                    String s = (String) msg.obj;
//                                    Log.d("lenve", "onSuccess() returned:  +  "+ s);
//                                    jsonStr(s);
//                                    ListViewAdapter adapter = new ListViewAdapter(list,CollectActivity.this);
//                                    listView.setAdapter(adapter);
//
//                                    break;
//                            }
//                        }
//                    };
                    list2.add(foodName1);//用个list存起来
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CollectActivity.this, android.R.layout.simple_list_item_1, list2);
                    listView.setAdapter(adapter);//将收藏了得食物名放到listView里
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//监听listView的点击事件
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(CollectActivity.this, DetailsActivity.class);//这里是跳转到collect（收藏）这个包的详情页里
                            intent.putExtra("name", list2.get(position).toString());
                            startActivity(intent);
                        }
                    });
                }
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub

            }
        });


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String s = HttpUtils.Data(String.format(foodName1));
//                Message msg = handler.obtainMessage();
//                msg.what = 0;
//                msg.obj = s;
//                handler.sendMessage(msg);
//            }
//        }).start();
//
//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 0:
//                        String str = ((String) msg.obj);
//                            parseJson(str);
//                            ListViewAdapter adapter = new ListViewAdapter(list,CollectActivity.this);
//                            listView.setAdapter(adapter);
//                        break;
//                    case 1:
//
//                        break;
//                }
//            }
//        };

    }



//    private void parseJson(String str) {
//        list =  new ArrayList<>();
//        try {
//            JSONObject jo = new JSONObject(str);
//                int id = jo.getInt("id");
//                String name = jo.getString("name");
//                String description = jo.getString("description");
//                String keywords = jo.getString("keywords");
//                String img = "http://tnfs.tngou.net/image" + jo.getString("img");
//                list.add(new FoodEntity(name,id,description,keywords,img));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

//    private void jsonStr(String s) {
//        list =  new ArrayList<>();
//        try {
//            JSONObject jo = new JSONObject(s);
//            JSONArray tngou = jo.getJSONArray("tngou");
//            for (int i = 0; i < tngou.length(); i++) {
//                JSONObject data = tngou.getJSONObject(i);
//                int id = data.getInt("id");
//                String name = data.getString("name");
//                String description = data.getString("description");
//                String keywords = data.getString("keywords");
//                String img = "http://tnfs.tngou.net/image" + data.getString("img");
//                list.add(new FoodEntity(name,id,description,keywords,img));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
