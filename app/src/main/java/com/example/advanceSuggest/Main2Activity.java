package com.example.advanceSuggest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.lansoeditor.demo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class Main2Activity extends Activity {

    private TextView textView;
    private ImageView imageView;
    public int REQUEST_CODE_TAKE_PICTURE = 33333;
    private String f;
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .readTimeout(30000, TimeUnit.MILLISECONDS)
//				    .cookieJar(new CookiesManager())
            //     .retryOnConnectionFailure(true)
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView=findViewById(R.id.text);
        imageView =findViewById(R.id.image);
        EventBus.getDefault().register(this);//订阅

    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(Events event) {
        if (event.getType()==2) {
            Luban.with(this)
                    .load(event.getPath())
                    .ignoreBy(100)
                    // .setTargetDir(getPath())
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(final File file) {

                            Log.d("MainActivity", "file.length():" + file.length());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                }
                            });

                            if (!textView.getText().toString().trim().equals("")) {
                                link_xiangsidu(file, textView.getText().toString().trim());

                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Toast.makeText(Main2Activity.this, "请先输入姓名,再拍照", Toast.LENGTH_LONG).show();

                                    }
                                });

                            }

                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    }).launch();
        }

    }


    public void kkkkk1(View view)
    {
        startActivity(new Intent(Main2Activity.this,CameraActivity.class).putExtra("type",2));

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//订阅
    }

    private static final MediaType FROM_DATA = MediaType.parse("multipart/form-data");
    //上传识别记录2
    private void link_xiangsidu(File file,String name) {
        //  final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
        MultipartBody body = new MultipartBody.Builder()
                .setType(FROM_DATA)
                .addFormDataPart("group","ruitongtest")
                .addFormDataPart("tag",name)
                .addFormDataPart("image","112.png",fileBody)
                .build();

        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .post(body)
                .url("http://116.31.102.74:8061"+ "/g/ruitongtest/");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "上传识别记录" + ss);
                    //成功的话 删掉本地保存的记录
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                        if (jsonObject.get("id").getAsInt()>=0){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Main2Activity.this,"入库成功",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });

                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Main2Activity.this,"入库失败，未找到人脸",Toast.LENGTH_LONG).show();
                                }
                            });
                        }




                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Main2Activity.this,"入库失败，未找到人脸",Toast.LENGTH_LONG).show();

                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage() + "gggg");
                }
            }
        });
    }

    public void saveBitmap2File2(Bitmap bm, final String path, int quality) {
        if (null == bm || bm.isRecycled()) {
            Log.d("InFoActivity", "回收|空");
            return;
        }
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.PNG, quality, bos);
            bos.flush();
            bos.close();

        } catch (Exception e) {
            e.printStackTrace();

        }finally {
            bm.recycle();
            bm=null;
        }
    }

}
