package com.example.sanjiaoji.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.sanjiaoji.MyApplication;
import com.example.sanjiaoji.R;
import com.example.sanjiaoji.dialog.BangDingDialog;
import com.example.sanjiaoji.dialog.XiuGaiDiZhiDialog;
import com.example.sanjiaoji.model.BaoCunBean;
import com.example.sanjiaoji.utils.FileUtil;
import com.example.sanjiaoji.utils.GsonUtil;
import com.example.sanjiaoji.utils.SharedPreferenceHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class SheZhiActivity extends Activity {
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;


    private BangDingDialog bangDingDialog = null;
    private int cameraRotation;
    private static final String group_name = "face-pass-test-x";
   // private Box<BaoCunBean> baoCunBeanDao = null;
   // private BaoCunBean baoCunBean = null;
    // public OkHttpClient okHttpClient = null;
    private SharedPreferenceHelper sharedPreferencesHelper = null;
    private String url=null,userName=null,pwd=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_zhi2);
        ButterKnife.bind(this);
        //ScreenAdapterTools.getInstance().reset(this);//如果希望android7.0分屏也适配的话,加上这句
        //在setContentView();后面加上适配语句
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
       // baoCunBeanDao = MyApplication.myApplication.getBoxStore().boxFor(BaoCunBean.class);
       // baoCunBean = baoCunBeanDao.get(123456L);
        EventBus.getDefault().register(this);//订阅

        sharedPreferencesHelper = new SharedPreferenceHelper(
                SheZhiActivity.this, "xiaojun");

        url = sharedPreferencesHelper.getSharedPreference("url", "http://192.168.2.2").toString().trim();
        userName = sharedPreferencesHelper.getSharedPreference("username", "").toString().trim();
        pwd = sharedPreferencesHelper.getSharedPreference("passewod", "").toString().trim();

    }


    @OnClick({R.id.rl1, R.id.rl2, R.id.rl3,  })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl1:
                final XiuGaiDiZhiDialog diZhiDialog = new XiuGaiDiZhiDialog(SheZhiActivity.this);
                diZhiDialog.setCanceledOnTouchOutside(false);
                diZhiDialog.setContents(url, userName,pwd);
                diZhiDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        link_login(diZhiDialog.getUrl(),diZhiDialog.getZhangHao(),diZhiDialog.getMiMa());
                        diZhiDialog.dismiss();

                    }
                });
                diZhiDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        diZhiDialog.dismiss();
                    }
                });
                diZhiDialog.show();

                break;
            case R.id.rl2:
                bangDingDialog = new BangDingDialog(SheZhiActivity.this);
                bangDingDialog.setCanceledOnTouchOutside(false);
                bangDingDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        bangDingDialog.jiazai();
                    }
                });
                bangDingDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bangDingDialog.dismiss();
                    }
                });
                bangDingDialog.show();

                break;
            case R.id.rl3:

                break;

        }
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅

    }

    //登陆旷世
    private void link_login(final String url, final String zhangHao, final String miMa){
        //	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient= new OkHttpClient();
        //RequestBody requestBody = RequestBody.create(JSON, json);
        RequestBody body = new FormBody.Builder()
                .add("username",zhangHao)
                .add("password",miMa)
                .add("pad_id",FileUtil.getSerialNumber(this) == null ? FileUtil.getIMSI() : FileUtil.getSerialNumber(this))
                .add("device_type", "2")
                .build();
        Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.header("user-agent","Koala Admin")
                //.post(requestBody)
                //.get()
                .post(body)
                .url(url+"/pad/login");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败"+e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast tastyToast= TastyToast.makeText(SheZhiActivity.this,"登陆后台返回数据异常，请检查地址和网络",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                Log.d("AllConnects", "请求成功"+call.request().toString());
                //获得返回体
                try{

                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                    Log.d("AllConnects", "注册码"+ss);
					final JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					Gson gson=new Gson();
					if (jsonObject.get("code").getAsInt()==0){

                        sharedPreferencesHelper.put("screen_token", jsonObject.get("data").getAsJsonObject().get("screen_token").getAsString());
                        sharedPreferencesHelper.put("url", url);
                        sharedPreferencesHelper.put("username", zhangHao);
                        sharedPreferencesHelper.put("passewod", miMa);

                        Log.d("SheZhiActivity",
                                sharedPreferencesHelper.getSharedPreference("screen_token", "").toString());
                       // baoCunBeanDao.put(baoCunBean);
                      //  Log.d("SheZhiActivity", baoCunBeanDao.get(123456L).toString());

                        EventBus.getDefault().post("dizhidizhi");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast tastyToast= TastyToast.makeText(SheZhiActivity.this,"登陆成功",TastyToast.LENGTH_LONG,TastyToast.INFO);
                                tastyToast.setGravity(Gravity.CENTER,0,0);
                                tastyToast.show();

                            }
                        });
                    }

					//final HuiYiInFoBean renShu=gson.fromJson(jsonObject,HuiYiInFoBean.class);

                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast tastyToast= TastyToast.makeText(SheZhiActivity.this,"登陆后台返回数据异常",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();

                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage()+"ttttt");
                }

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(String event) {


    }








}
