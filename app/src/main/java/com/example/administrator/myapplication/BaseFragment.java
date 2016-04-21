package com.example.administrator.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/17 0017.
 */
public class BaseFragment extends Fragment {

    private List<FoodEntity> list;
    private ListView listView;
    private Handler handle;
    private String str;

    public static Fragment getInstance(int id){ //工厂模式
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        args.putInt("ia",id);
        fragment.setArguments(args);//设置
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_layout, null);
        listView = (ListView) view.findViewById(R.id.lv);
        handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        String str = ((String) msg.obj);
                        parseJson(str);
                        ListViewAdapter adapter = new ListViewAdapter(list, getActivity());
                        listView.setAdapter(adapter);
                        break;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                String string = HttpUtils.getData(String.format("http://www.tngou.net/api/food/list?id=%d", getArguments().getInt("id")));
                Message msg = handle.obtainMessage();
                msg.what = 0;
                msg.obj = string;
                handle.sendMessage(msg);
            }
        }).start();
        return view;
    }

//    public void downlook2() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpURLConnection con = null;
//                BufferedInputStream bis = null;
//                StringBuffer result = new StringBuffer();
//                try {
//                    URL url = new URL(String.format("http://www.tngou.net/api/food/list?id=%d", getArguments().getInt("id")));
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


    private void parseJson(String s) {
        list = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(s);
            JSONArray tngou = json.getJSONArray("tngou");
            for (int i = 0; i < tngou.length(); i++) {
                JSONObject data = tngou.getJSONObject(i);
                String description = data.getString("description");
                String keywords = data.getString("keywords");
                String img ="http://tnfs.tngou.net/image" +  data.getString("img");
                list.add(new FoodEntity(description,keywords,img));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
