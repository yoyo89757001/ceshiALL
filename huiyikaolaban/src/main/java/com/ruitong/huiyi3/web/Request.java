package com.ruitong.huiyi3.web;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruitong.huiyi3.beans.TuiSongBena;
import com.ruitong.huiyi3.utils.GsonUtil;
import com.ruitong.huiyi3.utils.LogUtil;

import java.io.InputStream;
import java.net.URLDecoder;

public class Request {
    private final static int BUFFER_SIZE = 10 * 1024;
    private String uri;

    public synchronized void parse(InputStream input) {
        StringBuilder request = new StringBuilder();
        int readLength;
        byte[] buffer = new byte[BUFFER_SIZE];
        System.out.println("==============================");
        try {
            while ((readLength = input.read(buffer)) != -1) {
                request.append(new String(buffer, "UTF-8"));
            }
            System.out.println("length:" + readLength);


        } catch (Exception e) {
        }
        try {
          //  System.out.println(URLDecoder.decode(request.toString(), "UTF-8"));
            uri = parseUri(request.toString());
            String ss =  URLDecoder.decode(request.toString(), "UTF-8");
            LogUtil.d("dddd",ss);
            JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
            Gson gson = new Gson();
            TuiSongBena dataBean = gson.fromJson(jsonObject, TuiSongBena.class);

            Log.d("MyWebServer", dataBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1)
                return requestString.substring(index1 + 1, index2);
        }
        return null;
    }

    public String getUri() {
        return uri;
    }
}
