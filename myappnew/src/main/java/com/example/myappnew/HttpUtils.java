package com.example.myappnew;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/3/19 0019.
 */
public class HttpUtils {
    public static String Data(String string){
        HttpURLConnection con = null;
        InputStream is = null;
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(string);
            con = ((HttpURLConnection) url.openConnection());
            con.setConnectTimeout(5000);
            con.connect();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK){
                is = con.getInputStream();
                int len;
                byte [] buf = new byte[1024 * 8];
                while ((len = is.read(buf)) != -1){
                    result.append(new String (buf, 0, len));
                }
            }
            return result.toString();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }finally{
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                con.disconnect();
            }
        }
        return null;
    }
}
