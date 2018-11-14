package com.ruitong.huiyi3.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruitong.huiyi3.MyApplication;
import com.ruitong.huiyi3.R;
import com.ruitong.huiyi3.adapter.SheBeiAdapter;
import com.ruitong.huiyi3.beans.BaoCunBean;
import com.ruitong.huiyi3.beans.BaoCunBeanDao;
import com.ruitong.huiyi3.beans.IPbean;
import com.ruitong.huiyi3.beans.IPfanhuibean;
import com.ruitong.huiyi3.cookies.CookiesManager;
import com.ruitong.huiyi3.utils.GsonUtil;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class XuanIPDialog extends Dialog  {

    private OkHttpClient okHttpClient=null;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
    private Button button;
    private ListView listView;
    public static final int TIMEOUT = 1000 * 30;
    private Activity context;
    private List<IPbean> iPbeanList=new ArrayList<>();
    private SheBeiAdapter adapter=null;



    public XuanIPDialog(Activity context) {
        super(context, R.style.dialog_style2);
        this.context=context;
        baoCunBeanDao = MyApplication.myApplication.getDaoSession().getBaoCunBeanDao();
        baoCunBean = baoCunBeanDao.load(123456L);
        if (baoCunBean.getWenzi()==null || baoCunBean.getWenzi1()==null){
            Toast tastyToast= TastyToast.makeText(context,"请先设置账号密码!",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER,0,0);
            tastyToast.show();
        }else {
            denglu();
        }

        Window window =  this.getWindow();
        if ( window != null) {
            WindowManager.LayoutParams attr = window.getAttributes();
            if (attr != null) {
                attr.height = 400;
                attr.width = 400;
                attr.gravity = Gravity.CENTER;//设置dialog 在布局中的位置
            }
        }

        View mView = LayoutInflater.from(getContext()).inflate(R.layout.xuanip_item, null);
        listView=mView.findViewById(R.id.listview);
        adapter=new SheBeiAdapter(context,iPbeanList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("XuanIPDialog", "position:" + position);
                for (int i=0 ;i<iPbeanList.size();i++){
                    if (i==position){
                        iPbeanList.get(i).setTrue(true);
                    }else {
                        iPbeanList.get(i).setTrue(false);
                    }
                }
                adapter.notifyDataSetChanged();

            }
        });

        button=mView.findViewById(R.id.queren);

        super.setContentView(mView);
    }



    public  String xuanze(){
        String ss="";

        for (int i=0;i<iPbeanList.size();i++){
            if (iPbeanList.get(i).isTrue()){
                ss=iPbeanList.get(i).getText();
                break;
            }
        }
        return ss;

    }


    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("XuanIPDialog", "dialog分离");

    }

    /**
     * 确定键监听器
     * @param listener
     */
    public void setOnQueRenListener(View.OnClickListener listener){
            button.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setQuXiaoListener(View.OnClickListener listener){
       // l2.setOnClickListener(listener);
    }



    private void denglu(){
        okHttpClient= new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .cookieJar(new CookiesManager())
                .build();

        RequestBody body = new FormBody.Builder()
                .add("username",baoCunBean.getWenzi())
                .add("password",baoCunBean.getWenzi1())
                .build();

        Request.Builder requestBuilder = new Request.Builder()
                .header("user-agent", "Koala Admin")
                .post(body)
                .url(baoCunBean.getHoutaiDiZhi() + "/auth/login");
        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast tastyToast= TastyToast.makeText(context,"登陆后台出错!",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }
                });
                Log.d("AllConnects", "请求识别失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("AllConnects", "请求识别成功"+call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss=body.string();
                    Log.d("XuanIPDialog", ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    int code= jsonObject.get("code").getAsInt();
                    if (code==0){
                        getip();
                    }else {
                       context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast tastyToast= TastyToast.makeText(context,"登陆后台出错,无法进行后续任务",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER,0,0);
                                tastyToast.show();
                            }
                        });
                    }

                }catch (Exception e){

                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

    }

    private void getip(){

        Request.Builder requestBuilder = new Request.Builder()
                .header("user-agent", "Koala Admin")
                .get()
                .url(baoCunBean.getHoutaiDiZhi() + "/system/screen");
        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast tastyToast= TastyToast.makeText(context,"网络出错!",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }
                });
                Log.d("AllConnects", "请求识别失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("AllConnects", "请求识别成功"+call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss=body.string();
                    Log.d("XuanIPDialog", ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    int code= jsonObject.get("code").getAsInt();
                    if (code==0){
                        Gson gson = new Gson();
                        final IPfanhuibean menBean = gson.fromJson(jsonObject, IPfanhuibean.class);


                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                List<IPfanhuibean.DataBean> bean=menBean.getData();
                                if (bean!=null && bean.size()>0){
                                    for (IPfanhuibean.DataBean bb : bean){
                                        if (bb.getCamera_address()!=null){
                                            IPbean iPbean=new IPbean();
                                            iPbean.setText(bb.getCamera_address());
                                            iPbean.setTrue(false);
                                            iPbeanList.add(iPbean);
                                        }
                                    }

                                    if (iPbeanList.size()<=0){

                                            Toast tastyToast= TastyToast.makeText(context,"获取不到视频流IP",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                            tastyToast.setGravity(Gravity.CENTER,0,0);
                                            tastyToast.show();

                                    }else {

                                        adapter.notifyDataSetChanged();

                                    }


                                }else {

                                    Toast tastyToast= TastyToast.makeText(context,"获取不到视频流IP",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                    tastyToast.setGravity(Gravity.CENTER,0,0);
                                    tastyToast.show();

                                }

                            }
                        });




                    }else {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast tastyToast= TastyToast.makeText(context,"获取视频地址出错",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER,0,0);
                                tastyToast.show();
                            }
                        });
                    }

                }catch (Exception e){
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast tastyToast= TastyToast.makeText(context,"获取视频地址出错",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

    }

}
