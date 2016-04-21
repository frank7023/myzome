package com.example.collect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappnew.HttpUtils;
import com.example.myappnew.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {

    private String name;
    private String description;
    private String img;
    private String keywords;
    private String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");



        final Handler handler = new Handler(){
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

    }

    private void jsonStr(String s) {
        try {
            JSONObject jo = new JSONObject(s);
            description = jo.getString("description");
            img ="http://tnfs.tngou.net/image" +  jo.getString("img");
            keywords = jo.getString("keywords");
            message = jo.getString("message");
            TextView textView = (TextView) findViewById(R.id.name_tv);
            TextView description = (TextView) findViewById(R.id.description);
            TextView messageTV = (TextView) findViewById(R.id.message);
            ImageView imageView = (ImageView) findViewById(R.id.Detial_iv);
            textView.setText(name);
            description.setText(this.description);
            messageTV.setText(Html.fromHtml(message));//messageTV和message这两个位置的名字不能一模一样，不然就要你在message的前面加上String.valueOf，显示就出问题了
            Picasso.with(this).load(img).into(imageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
