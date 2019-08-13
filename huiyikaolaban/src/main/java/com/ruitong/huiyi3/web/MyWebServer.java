package com.ruitong.huiyi3.web;


import android.util.Log;

import com.google.gson.Gson;
import com.ruitong.huiyi3.beans.KaoLaBeans;
import com.ruitong.huiyi3.beans.ResBean;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;


public class MyWebServer extends NanoHTTPD  {

    private final static int PORT = 9020;


    /*
    主构造函数，也用来启动http服务
    */
    public MyWebServer() throws IOException {
        super(PORT);

        start();
    }


    /*
    解析的主入口函数，所有请求从这里进，也从这里出
    */
    @Override
    public Response serve(final IHTTPSession session) {

        Map<String, List<String>> parms = session.getParameters();
        Method method = session.getMethod();
        String uri = session.getUri();

        Map<String, String> files = new HashMap<>();

        if (Method.POST.equals(method) || Method.PUT.equals(method)) {
            try {
                session.parseBody(files);
            } catch (IOException ioe) {
                return newFixedLengthResponse(Response.Status.OK,"application/json;charset=UTF-8",getResponse(-1,ioe.getMessage()+""));
            } catch (ResponseException re) {
                return newFixedLengthResponse(Response.Status.OK,"application/json;charset=UTF-8",getResponse(-1,re.getMessage()+""));
            }
        }


        //新增接口
        if ("/app/addFace".equalsIgnoreCase(uri)) {
           // Log.d("MyWebServer", parms.get("name").get(0));
            try {

//                if (parms.get("name")!=null){
//
//                    for (Object o : parms.get("name")){
//                        Log.d("MyWebServer", o.toString());
//                    }
//                }



                String name = parms.get("name")==null? "" : parms.get("name").get(0);
                String headImage = parms.get("headImage")==null? "" : parms.get("headImage").get(0);
                String interviewees = parms.get("interviewees")==null? "" : parms.get("interviewees").get(0);
               // String headImage = parms.get("headImage")==null? "" : parms.get("headImage").get(0);
                Log.d("MyWebServer", name);
                Log.d("MyWebServer", headImage+" null");
                Log.d("MyWebServer", interviewees+" null");

                KaoLaBeans beans =new KaoLaBeans();
                beans.setName(name);
                beans.setHeadImage(headImage);
                beans.setInterviewees(interviewees);

                EventBus.getDefault().post(beans);

                return  newFixedLengthResponse(Response.Status.OK,"application/json;charset=UTF-8",getResponse(0,"0")) ; // =========》  返回给客户端

            }catch (Exception e){
               Log.d("MyWebServer", e.getMessage());
            }

        }


        //最后的
        return  newFixedLengthResponse(Response.Status.OK,"application/json;charset=UTF-8",getResponse(-1,"未找到对应方法")) ; // =========》  返回给客户端
    }



    private String getResponse(int code,String message){
        Gson gson =new Gson();
        ResBean resBean=new ResBean();
        resBean.setCode(code);
        resBean.setMessage(message);
        return gson.toJson(resBean);
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
}