package com.example.pic;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.myappnew.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PicActivity extends Activity {
    private GridView gv;
    private List<PisEntity> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        gv = (GridView) findViewById(R.id.gv);
        DownloadText dlt = new DownloadText();
        dlt.execute("http://www.tngou.net/tnfs/api/list?page=1&rows=10");
    }

    class DownloadText extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            list = new ArrayList<>();
            try {
                JSONObject jo = new JSONObject(result);
                JSONArray tngou = jo.getJSONArray("tngou");
                for (int i = 0; i < tngou.length(); i++) {
                    JSONObject data = tngou.getJSONObject(i);
                    int id = data.getInt("id");
                    String img = data.getString("img");
                    String title = data.getString("title");
                    PisEntity pe = new PisEntity("http://tnfs.tngou.net/image" + img, title, id);
                    list.add(pe);
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }

            MyAdapter adapter = new MyAdapter(list,PicActivity.this);
            gv.setAdapter(adapter);
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(PicActivity.this, SecondActivity.class);
                    intent.putExtra("id", list.get(position).getId());

                    startActivity(intent);
                }
            });
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection con = null;
            BufferedReader br = null;
            StringBuffer result = new StringBuffer();
            try {
                URL url = new URL(params[0]);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.connect();
                if (con.getResponseCode() == 200) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String str;
                    while ((str = br.readLine()) != null) {
                        result.append(str);
                    }
                    return result.toString();
                }
            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

    }

}
