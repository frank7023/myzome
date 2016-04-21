package com.example.pic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myappnew.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        int id=getIntent().getIntExtra("id", 0);
        DownLoadText dlt = new DownLoadText();
        dlt.execute(String.format("http://www.tngou.net/tnfs/api/list?page=1&rows=50", id));
    }

    class DownLoadText extends AsyncTask<String, Integer, byte[]> {

        private TextView tv;
        private ProgressBar pb;

        @Override
        protected void onProgressUpdate(Integer... values) {

            if (values.length > 0) {
                tv.setText(values[0] + "%");
                pb.setProgress(values[0]);
            }
        }

        @Override
        protected void onPreExecute() {

            tv = (TextView) findViewById(R.id.tv);
            pb = (ProgressBar) findViewById(R.id.pb);

        }

        @Override
        protected void onPostExecute(byte[] result) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
            ImageView iv = (ImageView) findViewById(R.id.iv);
            iv.setImageBitmap(bitmap);
        }

        @Override
        protected byte[] doInBackground(String... params) {
            HttpURLConnection con = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream is = null;
            try {
                URL url = new URL(params[0]);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.connect();
                double totalCount = con.getContentLength();
                int count = 0;
                if (con.getResponseCode() == 200) {
                    is = con.getInputStream();
                    int len = 0;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        baos.write(buf, 0, len);
                        baos.flush();
                        count += len;
                        publishProgress((int) ((count / totalCount) * 100));
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }

            return baos.toByteArray();
        }

    }

}
