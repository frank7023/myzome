package com.example.myappnew;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/19 0019.
 */
public class BaseFragment extends Fragment {
    private List<FoodEntity> list;
    private static BaseFragment fragment;
    private static Bundle args;
    private ListView listView;
    private Handler handler;


    public static Fragment getInstence(int id) {//工厂模式
        fragment = new BaseFragment();
        args = new Bundle();
        args.putInt("id",id);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, null);
        listView = (ListView) view.findViewById(R.id.food_lv);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        String str = ((String) msg.obj);
                        if (str == null || "".equals(str)) {
                            Toast.makeText(getActivity(), "网络无法连接", Toast.LENGTH_SHORT).show();
                        } else {
                            parseJson(str);
                            ListViewAdapter adapter = new ListViewAdapter(list,getActivity());
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                                    intent.putExtra("id", list.get(position).getId());
                                    intent.putExtra("name", list.get(position).getName());
                                    intent.putExtra("img",list.get(position).getImg());
                                    intent.putExtra("description", list.get(position).getDescription());
                                    startActivity(intent);
                                }
                            });
                        }
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = HttpUtils.Data(String.format("http://www.tngou.net/api/food/list?id=%d", getArguments().getInt("id")));
                Message msg = handler.obtainMessage();
                msg.what = 0;
                msg.obj = s;
                handler.sendMessage(msg);
            }
        }).start();
        return view;
    }

    private void parseJson(String str) {
        list =  new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(str);
            JSONArray tngou = jo.getJSONArray("tngou");
            for (int i = 0; i < tngou.length(); i++) {
                JSONObject data = tngou.getJSONObject(i);
                int id = data.getInt("id");
                String name = data.getString("name");
                String description = data.getString("description");
                String keywords = data.getString("keywords");
                String img = "http://tnfs.tngou.net/image" + data.getString("img");
                list.add(new FoodEntity(name,id,description,keywords,img));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
