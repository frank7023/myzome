package com.example.administrator.myapplication;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/3/17 0017.
 */
public class HttpUtils {
    public static String getData(String urlStr){
        HttpURLConnection con = null;
        StringBuffer result = new StringBuffer();
        BufferedInputStream bis = null;
        try {
            URL url = new URL(urlStr);
            con = ((HttpURLConnection) url.openConnection());
            con.setConnectTimeout(5 * 1000);
            con.connect();
            if(con.getResponseCode()==200){
                bis = new BufferedInputStream(con.getInputStream());
                int len;
                byte[] buf = new byte[1024];
                while((len = bis.read(buf)) != -1){
                    result.append(new String(buf,0,len));
                }
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(con != null){
                con.disconnect();
            }
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
