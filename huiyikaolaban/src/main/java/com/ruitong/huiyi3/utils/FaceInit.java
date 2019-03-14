package com.ruitong.huiyi3.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ruitong.huiyi3.beans.BangDingBean;
import com.ruitong.huiyi3.beans.BaoCunBean;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sun.misc.BASE64Decoder;


public class FaceInit {
    private Context context;
    private BaoCunBean baoCunBean;
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .readTimeout(30000, TimeUnit.MILLISECONDS)
//				    .cookieJar(new CookiesManager())
            //         .retryOnConnectionFailure(true)
            .build();



    public FaceInit(Context context) {
        this.context =context;

    }


    public void initFacePass(){


            initFacePassSDK("","","","",1,null);


    }


    private  void initFacePassSDK(String s1, String s2, String s3, String id, int type, String url) {

        link_uplodeBD(id,url);

       // EventBus.getDefault().post("激活设备失败");


    }

    public void init(String registration, BaoCunBean baoCunBean){
        this.baoCunBean=baoCunBean;
        if (baoCunBean.getXgToken()!=null && !baoCunBean.getXgToken().equals("")){
            link_uplod(registration,baoCunBean.getHoutaiDiZhi());
        }else {
            EventBus.getDefault().post("注册推送失败,请重启设备后再试");
        }

    }

    //绑定
    private void link_uplod(String zhucema, final String url){
        //	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        Log.d("FaceInit", "dsadsadasf");
        //RequestBody requestBody = RequestBody.create(JSON, json);
        RequestBody body = new FormBody.Builder()
                .add("registerCode",zhucema)
                .add("machineCode", baoCunBean.getTuisongDiZhi())
                .add("packagesName", AppUtils.getPackageName(context)+"")
                .add("machineToken",baoCunBean.getXgToken()+"")
                .build();
        Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.header("user-agent","Koala Admin")
                //.post(requestBody)
                //.get()
                .post(body)
                .url(url+"/app/machineSave");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败"+e.getMessage());
                EventBus.getDefault().post("网络请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) {
              //  Log.d("AllConnects", "请求成功"+call.request().toString());
                //获得返回体
                String ss=null;
                try{
                    ResponseBody body = response.body();
                     ss=body.string().trim();
                    Log.d("AllConnects", "注册码"+ss);
                    final JsonObject jsonObject= parse(ss).getAsJsonObject();
                    String idid=jsonObject.get("id").getAsString();
                    String urlurl=jsonObject.get("url").getAsString();
                    link_ferrce(idid,url+urlurl,url);

                }catch (Exception e){
                    try {
                        JsonObject jsonObject= parse(ss).getAsJsonObject();
                        String idid=jsonObject.get("message").getAsString();
                        EventBus.getDefault().post(idid);
                    }catch (Exception eee){
                        EventBus.getDefault().post(e.getMessage()+"");
                    }
                  //  Log.d("WebsocketPushMsg", e.getMessage()+"ttttt");
                }

            }
        });
    }

    //绑定设备
    private void link_ferrce(String id, final String url, final String url2){
        //	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

        //RequestBody requestBody = RequestBody.create(JSON, json);
        RequestBody body = new FormBody.Builder()
                .add("id",id)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.header("user-agent","Koala Admin")
                //.post(requestBody)
                //.get()
                .post(body)
                .url(url);

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
              //  Log.d("AllConnects", "请求失败"+e.getMessage());
                EventBus.getDefault().post("网络请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              //  Log.d("AllConnects", "请求成功"+call.request().toString());
                //获得返回体
                try{
                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                   // Log.d("AllConnects", "绑定："+ss);
                    JsonObject jsonObject= parse(ss).getAsJsonObject();

                    Gson gson=new Gson();
                    final BangDingBean dingBean=gson.fromJson(jsonObject,BangDingBean.class);
//                    String passd=recess(dingBean.getAccredit()+dingBean.getSdkPwd());
//
//                    byte[] b1=new BASE64Decoder().decodeBuffer(passd);
//                    byte[] b2=dingBean.getKeyiv().getBytes();
//                    byte[] b3=new BASE64Decoder().decodeBuffer(dingBean.getVerifyIp());
//                    byte[] b4=new BASE64Decoder().decodeBuffer(dingBean.getApiKey());
//                    byte[] b5=new BASE64Decoder().decodeBuffer(dingBean.getApiSecret());
//
//                    String s1s= null;
//                    String s2s=null;
//                    String s3s=null;
                    try {
//                        s1s = new String(des3DecodeCBC(b1,b2,b3));
//                        s2s = new String(des3DecodeCBC(b1,b2,b4));
//                        s3s = new String(des3DecodeCBC(b1,b2,b5));
                        initFacePassSDK("","","",dingBean.getId(),2,url2);
                    } catch (Exception e) {
//                        e.printStackTrace();
//                        EventBus.getDefault().post("激活设备失败"+e.getMessage());
////                        Log.d("MyReceiver", e.getMessage()+"gggg");
                    }



                }catch (Exception e){
                  //  Log.d("WebsocketPushMsg", e.getMessage()+"ttttt");
                    EventBus.getDefault().post("激活设备失败"+e.getMessage());
                }

            }
        });
    }



//    public native static String init(long l1);
//    public native static String dinner(long l2);
//    public native static String recess(String info);
//    public native static String recess2(long info);

    public static JsonElement parse(String json) {

        JsonParser mJsonParser = new JsonParser();
        return mJsonParser.parse(json);
    }

    public static JsonObject optJsonObject(JsonElement e) {
        return optJsonObject(e, null);
    }

    public static JsonObject optJsonObject(JsonElement e, JsonObject defaultOne) {
        if (null == e || e.isJsonNull()) {
            return defaultOne;
        }

        return e.getAsJsonObject();
    }

    public static JsonArray optJsonArray(JsonElement e) {
        return optJsonArray(e, null);
    }

    public static JsonArray optJsonArray(JsonElement e, JsonArray defaultOne) {
        if (null == e || e.isJsonNull()) {
            return defaultOne;
        }

        return e.getAsJsonArray();
    }

    public static String optString(JsonElement e) {
        return optString(e, null);
    }

    public static String optString(JsonElement e, String defaultOne) {
        if (null == e || e.isJsonNull()) {
            return defaultOne;
        }

        return e.getAsString();
    }

    public static int optInt(JsonElement e) {
        return optInt(e, -1);
    }

    public static int optInt(JsonElement e, int defaultOne) {
        if (null == e || e.isJsonNull()) {
            return defaultOne;
        }

        return e.getAsInt();
    }




    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        return cipher.doFinal(data);
    }

    public static String getSerialNumber(Context context){

        String serial = null;

        try {

            serial = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return serial;

    }

    /**
     * 获取手机IMSI号
     */
    public static String getIMSI(){


        return "355" +
                Build.BOARD.length()%10 +
                Build.BRAND.length()%10 +
                Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 +
                Build.HOST.length()%10 +
                Build.ID.length()%10 +
                Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 +
                Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 +
                Build.TYPE.length()%10 +
                Build.USER.length()%10;
    }

    public static boolean isExists(String path) {
        if (null == path) {
            return false;
        }
        String name;

        name = SDPATH + File.separator + path;

        File file = new File(name);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.exists();
    }
    private static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath();


    //提交绑定状态
    private  void link_uplodeBD(String id, String url){

        //	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

        //RequestBody requestBody = RequestBody.create(JSON, json);
        RequestBody body = new FormBody.Builder()
                .add("id",id)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.header("user-agent","Koala Admin")
                //.post(requestBody)
                //.get()
                .post(body)
                .url(url+"/app/destroyMachine");
        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {
              //  Log.d("AllConnects", "请求失败"+e.getMessage());
                EventBus.getDefault().post("网络请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              //  Log.d("AllConnects", "请求成功"+call.request().toString());
                //获得返回体
                try{
                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                    Log.d("AllConnects", "上传绑定成功状态"+ss);
                   JsonObject jsonObject= parse(ss).getAsJsonObject();

                   EventBus.getDefault().post(jsonObject.get("message").getAsString());

//					Gson gson=new Gson();
//					final HuiYiInFoBean renShu=gson.fromJson(jsonObject,HuiYiInFoBean.class);

                }catch (Exception e){
                    EventBus.getDefault().post("绑定设备异常"+e.getMessage());
                //    Log.d("WebsocketPushMsg", e.getMessage()+"ttttt");
                }

            }
        });
    }

}
