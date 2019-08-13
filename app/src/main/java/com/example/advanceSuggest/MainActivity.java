package com.example.advanceSuggest;


import android.Manifest;
import android.app.Activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lansoeditor.demo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class MainActivity extends Activity implements EasyPermissions.PermissionCallbacks{
    private TextView t1;

    public int REQUEST_CODE_TAKE_PICTURE = 2221;
    private String f;
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .readTimeout(30000, TimeUnit.MILLISECONDS)
//				    .cookieJar(new CookiesManager())
            //     .retryOnConnectionFailure(true)
            .build();
    private TextView textView;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.llll);
        textView=findViewById(R.id.text);
        imageView =findViewById(R.id.image);
        EventBus.getDefault().register(this);//订阅
        methodRequiresTwoPermission();
    }



    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(Events event) {
        if (event.getType()==1) {
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
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(final File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            Log.d("MainActivity", "file.length():" + file.length());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                }
                            });

                            link_xiangsidu(file);

                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                        }
                    }).launch();
        }
    }

    public void kkkkk1(View view)
    {
        imageView.setImageBitmap(null);
        textView.setText("");
        startActivity(new Intent(MainActivity.this,Main2Activity.class));

    }

    public void kkkkk2(View view)
    {
        startActivity(new Intent(MainActivity.this,CameraActivity.class).putExtra("type",1));
     }




    private static final MediaType FROM_DATA = MediaType.parse("multipart/form-data");
    //上传识别记录2
    private void link_xiangsidu(File file) {
        //  final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
        MultipartBody body = new MultipartBody.Builder()
                .setType(FROM_DATA)
                .addFormDataPart("image","112.png",fileBody)
                .addFormDataPart("group","ruitongtest")
                .addFormDataPart("limit","2")
                .addFormDataPart("threshold","78")
                .build();


        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .post(body)
                .url("http://116.31.102.74:8061"+ "/search");

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
                    Gson gson = new Gson();
                    final XiangSiDu renShu = gson.fromJson(jsonObject, XiangSiDu.class);
                    Log.d("MainActivity", renShu.toString());
                    if (renShu.getFace()==null || renShu.getGroups()==null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("比对失败!");
                            }
                        });
                    }else {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (null==renShu.getGroups() || null==renShu.getGroups().get(0).getPhotos()){
                                    textView.setText("比对失败,未入库人员");
                                }else
                                textView.setText(renShu.getGroups().get(0).getPhotos().get(0).getTag()+"\n相似度："+renShu.getGroups().get(0).getPhotos().get(0).getScore());
                            }
                        });

                    }


                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(e.getMessage()+"");
                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage() + "gggg");
                }
            }
        });
    }


    private final int RC_CAMERA_AND_LOCATION=10000;

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.EXPAND_STATUS_BAR,
                Manifest.permission.RECEIVE_BOOT_COMPLETED, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.INTERNET};

        if (EasyPermissions.hasPermissions(this, perms)) {
            // 已经得到许可，就去做吧 //第一次授权成功也会走这个方法
            Log.d("BaseActivity", "成功获得权限");

           // start();

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "需要授予app权限,请点击确定",
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        Log.d("BaseActivity", "list.size():" + list.size());

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
        for (String s:list){
            Log.d("BaseActivity", s);
        }
        Log.d("BaseActivity", "list.size():" + list.size());
       // Toast.makeText(MainActivity.this,"权限被拒绝无法正常使用app",Toast.LENGTH_LONG).show();
       // finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
      //  glSurfaceView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
