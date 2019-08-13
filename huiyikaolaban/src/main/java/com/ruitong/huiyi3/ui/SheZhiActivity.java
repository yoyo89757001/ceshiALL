package com.ruitong.huiyi3.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ruitong.huiyi3.MyApplication;
import com.ruitong.huiyi3.R;
import com.ruitong.huiyi3.beans.BaoCunBean;

import com.ruitong.huiyi3.beans.Subject;
import com.ruitong.huiyi3.beans.ZhuJiBean;
import com.ruitong.huiyi3.beans.ZhuJiBeanH;

import com.ruitong.huiyi3.cookies.CookiesManager;
import com.ruitong.huiyi3.dialog.BangDingDialog;
import com.ruitong.huiyi3.dialog.DuQuDialog;
import com.ruitong.huiyi3.dialog.GaiNiMaBi;
import com.ruitong.huiyi3.dialog.MoBanDialog;
import com.ruitong.huiyi3.dialog.XiuGaiHouTaiDialog;

import com.ruitong.huiyi3.dialog.XiuGaiXinXiDialog;

import com.ruitong.huiyi3.dialog.XuanIPDialog;
import com.ruitong.huiyi3.dialog.YuYingDialog;
import com.ruitong.huiyi3.utils.DateUtils;
import com.ruitong.huiyi3.utils.FaceInit;
import com.ruitong.huiyi3.utils.FileUtil;
import com.ruitong.huiyi3.utils.GsonUtil;

import com.ruitong.huiyi3.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sdsmdg.tastytoast.TastyToast;


import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


import io.objectbox.Box;
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

import static com.ruitong.huiyi3.view.AutoScrollTextView.TAG;


public class SheZhiActivity extends Activity implements View.OnClickListener, View.OnFocusChangeListener, FileUtil.ZipListener {
    private Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt10,bt11,bt12;
    private List<Button> sheZhiBeanList;
    private Box<BaoCunBean> baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
    private int dw,dh;
    private Box<ZhuJiBeanH> zhuJiBeanHDao=null;
    public  OkHttpClient okHttpClient=null;
    private ZhuJiBeanH zhuJiBeanH=null;
    private DuQuDialog duQuDialog=null;
    private static String usbPath=null;
    private StringBuilder stringBuilder=new StringBuilder();
    //private UnzipFileListener mUnzipFileListener;
    private int curpercent = 0;
    private BangDingDialog bangDingDialog;
    private boolean isT=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dw = Utils.getDisplaySize(SheZhiActivity.this).x;
        dh = Utils.getDisplaySize(SheZhiActivity.this).y;
        zhuJiBeanHDao = MyApplication.myApplication.getZhuJiBeanBox();
        baoCunBeanDao= MyApplication.myApplication.getBaoCunBeanBox();
        baoCunBean=baoCunBeanDao.get(123456L);
        if (baoCunBean.getWenzi()==null){
            baoCunBean.setWenzi("");
        }

        EventBus.getDefault().register(this);//订阅

        baoCunBeanDao.put(baoCunBean);
        baoCunBean=baoCunBeanDao.get(123456L);

        setContentView(R.layout.activity_she_zhi);

        if (dw>dh){
            /**
             * 设置为横屏
             */
            if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

        }else {

            /**
             * 设置为竖屏
             */
            if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }

        }


        bt1= (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(this);
        bt1.setOnFocusChangeListener(this);
        bt2= (Button) findViewById(R.id.bt2);
        bt2.setOnClickListener(this);
        bt2.setOnFocusChangeListener(this);
        bt3= (Button) findViewById(R.id.bt3);
        bt3.setOnClickListener(this);
        bt3.setOnFocusChangeListener(this);
        bt4= (Button) findViewById(R.id.bt4);
        bt4.setOnClickListener(this);
        bt4.setOnFocusChangeListener(this);
        bt5= (Button) findViewById(R.id.bt5);
        bt5.setOnClickListener(this);
        bt5.setOnFocusChangeListener(this);
        bt6= (Button) findViewById(R.id.bt6);
        bt6.setOnClickListener(this);
        bt6.setOnFocusChangeListener(this);
        bt7= (Button) findViewById(R.id.bt7);
        bt7.setOnClickListener(this);
        bt7.setOnFocusChangeListener(this);
        bt8= (Button) findViewById(R.id.bt8);
        bt8.setOnClickListener(this);
        bt8.setOnFocusChangeListener(this);
        bt9= (Button) findViewById(R.id.bt9);
        bt9.setOnClickListener(this);
        bt9.setOnFocusChangeListener(this);
        bt10= (Button) findViewById(R.id.bt10);
        bt10.setOnClickListener(this);
        bt10.setOnFocusChangeListener(this);
        bt11= (Button) findViewById(R.id.bt11);
        bt11.setOnClickListener(this);
        bt11.setOnFocusChangeListener(this);
        bt12= (Button) findViewById(R.id.bt12);
        bt12.setOnClickListener(this);
        bt12.setOnFocusChangeListener(this);
        bt1.requestFocus();

        sheZhiBeanList = new ArrayList<>();
        sheZhiBeanList.add(bt1);
        sheZhiBeanList.add(bt2);
        sheZhiBeanList.add(bt3);
        sheZhiBeanList.add(bt4);
        sheZhiBeanList.add(bt5);
        sheZhiBeanList.add(bt6);
        sheZhiBeanList.add(bt7);
        sheZhiBeanList.add(bt8);
        sheZhiBeanList.add(bt9);
        sheZhiBeanList.add(bt10);
        sheZhiBeanList.add(bt11);
        sheZhiBeanList.add(bt12);


}


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(String event) {
        if (bangDingDialog != null && bangDingDialog.isShowing()) {
            bangDingDialog.setContents(event);
        }

        Toast tastyToast = TastyToast.makeText(SheZhiActivity.this, event, TastyToast.LENGTH_LONG, TastyToast.INFO);
        tastyToast.setGravity(Gravity.CENTER, 0, 0);
        tastyToast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
    }

    @Override
    protected void onPause() {
        //开启Activity
        //  Log.d("SheZhiActivity", "baoCunBean.getMoban():" + baoCunBean.getMoban());
        switch (baoCunBean.getMoban()){
            case 201:
                if (isT){
                    startActivity(new Intent(SheZhiActivity.this,BaseActivity.class));
                    SystemClock.sleep(1600);
                }
                break;

            case 2:
//                startActivity(new Intent(SheZhiActivity.this,XinChunActivity.class));
//                SystemClock.sleep(1600);
                break;
            case 3:

                break;
            case 4:


                break;

        }

        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("SheZhiActivity", "停止");

        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt1:
                ChongsZHI();
                bt1.requestFocus();
                bt1.setTextColor(Color.WHITE);
                bt1.setBackgroundResource(R.drawable.zidonghuoqu1);
                  AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt1,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt1,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet.setDuration(300);
                    animatorSet.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {
                            final XuanIPDialog dialog=new XuanIPDialog(SheZhiActivity.this);

                            dialog.setOnQueRenListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d("SheZhiActivity", "WebsocketPushMsg"+dialog.xuanze());
                                    baoCunBean.setShipingIP(dialog.xuanze());
                                    baoCunBeanDao.put(baoCunBean);
                                    baoCunBean=baoCunBeanDao.get(123456L);
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                        }
                    });
                    animatorSet.start();
                break;
            case R.id.bt2:
                ChongsZHI();
                bt2.requestFocus();
                bt2.setTextColor(Color.WHITE);
                bt2.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt2,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt2,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());


                        isT=false;
                        kaiPing();
                        finish();

//                        final XiuGaiXinXiDialog dialog=new XiuGaiXinXiDialog(SheZhiActivity.this);
//                        if (baoCunBean.getZhujiDiZhi()==null){
//                            dialog.setContents("设置主机地址","ws://192.168.2.78:9000/video");
//                        }else {
//                            dialog.setContents("设置主机地址",baoCunBean.getZhujiDiZhi());
//                        }
//                        dialog.setOnQueRenListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                baoCunBean.setZhujiDiZhi(dialog.getContents());
//                                baoCunBeanDao.put(baoCunBean);
//                                baoCunBean=baoCunBeanDao.get(123456L);
//                                dialog.dismiss();
//                            }
//                        });
//                        dialog.setQuXiaoListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//                        dialog.show();


                break;
            case R.id.bt3:
                ChongsZHI();
                bt3.requestFocus();
                bt3.setTextColor(Color.WHITE);
                bt3.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet3 = new AnimatorSet();
                animatorSet3.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt3,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt3,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet3.setDuration(300);
                animatorSet3.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
//                        final XiuGaiXinXiDialog dialog=new XiuGaiXinXiDialog(SheZhiActivity.this);
//                        if (baoCunBean.getTuisongDiZhi()==null){
//                            dialog.setContents("设置推送地址","http://192.168.2.50");
//                        }else {
//                            dialog.setContents("设置推送地址",baoCunBean.getTuisongDiZhi());
//                        }
//                        dialog.setOnQueRenListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                baoCunBean.setTuisongDiZhi(dialog.getContents());
//                                baoCunBeanDao.update(baoCunBean);
//                                baoCunBean=baoCunBeanDao.load(123456L);
//                                dialog.dismiss();
//                            }
//                        });
//                        dialog.setQuXiaoListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//                        dialog.show();
//                        XuanIPDialog yuLanDialog=new XuanIPDialog(SheZhiActivity.this);
//                            yuLanDialog.show();
                    }
                });
                animatorSet3.start();

                break;
            case R.id.bt4:
                ChongsZHI();
                bt4.requestFocus();
                bt4.setTextColor(Color.WHITE);
                bt4.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet4 = new AnimatorSet();
                animatorSet4.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt4,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt4,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet4.setDuration(300);
                animatorSet4.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {

                        final XiuGaiXinXiDialog dialog=new XiuGaiXinXiDialog(SheZhiActivity.this);
                        if (baoCunBean.getTouxiangzhuji()==null){
                            dialog.setContents("设置头像主机地址","http://192.168.2.58");
                        }else {
                            dialog.setContents("设置头像主机地址",baoCunBean.getTouxiangzhuji());
                        }
                        dialog.setOnQueRenListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baoCunBean.setTouxiangzhuji(dialog.getContents());
                                baoCunBeanDao.put(baoCunBean);
                                baoCunBean=baoCunBeanDao.get(123456L);
                                dialog.dismiss();
                            }
                        });
                        dialog.setQuXiaoListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    }
                });
                animatorSet4.start();
                break;
            case R.id.bt5:
                ChongsZHI();
                bt5.requestFocus();
                bt5.setTextColor(Color.WHITE);
                bt5.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet5 = new AnimatorSet();
                animatorSet5.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt5,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt5,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet5.setDuration(300);
                animatorSet5.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
//                        if (baoCunBean.getIsShowMoshengren()){ //false为 竖屏
//                            baoCunBean.setIsShowMoshengren(false);
//                            baoCunBeanDao.update(baoCunBean);
//                            baoCunBean=baoCunBeanDao.load(123456L);
//                            bt5.setText("已设置为不弹");
//                            TastyToast.makeText(SheZhiActivity.this,"已设置为不弹",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
//
//                        }else {
//                            baoCunBean.setIsShowMoshengren(true);
//                            baoCunBeanDao.update(baoCunBean);
//                            baoCunBean=baoCunBeanDao.load(123456L);
//                            bt5.setText("已设置为弹出");
//                            TastyToast.makeText(SheZhiActivity.this,"已设置为弹出",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
//                        }


                    }
                });
                animatorSet5.start();
                break;
            case R.id.bt6:
                ChongsZHI();
                bt6.requestFocus();
                bt6.setTextColor(Color.WHITE);
                bt6.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet6 = new AnimatorSet();
                animatorSet6.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt6,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt6,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet6.setDuration(300);
                animatorSet6.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
                       //语音设置
                        YuYingDialog yuYingDialog=new YuYingDialog(SheZhiActivity.this);
                        yuYingDialog.show();

                    }
                });
                animatorSet6.start();

                break;

            case R.id.bt7:
                ChongsZHI();
                bt7.requestFocus();
                bt7.setTextColor(Color.WHITE);
                bt7.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet7 = new AnimatorSet();
                animatorSet7.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt7,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt7,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet7.setDuration(300);
                animatorSet7.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
                        //弹窗
                        MoBanDialog dialog=new MoBanDialog(SheZhiActivity.this,baoCunBeanDao);
                        dialog.show();
                        bt7.setEnabled(true);
                    }
                });
                animatorSet7.start();
                bt7.setEnabled(false);

                break;

            case R.id.bt8:
                ChongsZHI();
                bt8.requestFocus();
                bt8.setTextColor(Color.WHITE);
                bt8.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet8 = new AnimatorSet();
                animatorSet8.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt8,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt8,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet8.setDuration(300);
                animatorSet8.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {

                        bangDingDialog = new BangDingDialog(SheZhiActivity.this);
                        bangDingDialog.setCanceledOnTouchOutside(false);
                        bangDingDialog.setContents(baoCunBean.getJihuoma()+"",null);
                        bangDingDialog.setOnQueRenListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String jihuoma=bangDingDialog.getZhuCeMa();
                                String jhm[] =jihuoma.split("-");
                                if (jhm.length==5){
                                    baoCunBean.setJihuoma(jihuoma);
                                    baoCunBeanDao.put(baoCunBean);
                                    Log.d("SheZhiActivity2", "保存激活码成功");
                                }

                                FaceInit init = new FaceInit(SheZhiActivity.this);
                                init.init(bangDingDialog.getZhuCeMa(), baoCunBean);
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

//                        final ShanChuKuDialog shanChuKuDialog=new ShanChuKuDialog(SheZhiActivity.this);
//                        shanChuKuDialog.setOnQueRenListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if (shanChuKuDialog.getMiMa().equals("123456789")){
//                                    //执行删除
//                                    if (zhuJiBeanH!=null && zhuJiBeanH.getHostUrl()!=null &&
//                                            !zhuJiBeanH.getHostUrl().equals("") && zhuJiBeanH.getUsername()!=null &&
//                                            !zhuJiBeanH.getUsername().equals("")){
//                                        getOkHttpClient(2);
//                                        shanChuKuDialog.dismiss();
//                                    }else {
//                                        shanChuKuDialog.setMiMa("没有主机地址");
//                                    }
//
//                                }else {
//
//                                    shanChuKuDialog.setMiMa("密码不正确");
//                                }
//
//
//                            }
//                        });
//                        shanChuKuDialog.setQuXiaoListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                shanChuKuDialog.dismiss();
//                            }
//                        });
//                        shanChuKuDialog.show();


                        //弹窗
//                        final XiuGaiWenZiDialog dialog=new XiuGaiWenZiDialog(SheZhiActivity.this);
//                        dialog.setContents(baoCunBean.getWenzi()+"",baoCunBean.getSize()==0? "30":String.valueOf(baoCunBean.getSize()),
//                                baoCunBean.getWenzi1()+"",baoCunBean.getSize1()==0? "60":String.valueOf(baoCunBean.getSize1()));
//                        dialog.setOnQueRenListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                baoCunBean.setWenzi(dialog.getContents());
//                                baoCunBean.setSize(Integer.valueOf(dialog.getSize()));
//                                baoCunBean.setWenzi1(dialog.getContents2());
//                                baoCunBean.setSize1(Integer.valueOf(dialog.getSize2()));
//                                baoCunBeanDao.update(baoCunBean);
//                                baoCunBean=baoCunBeanDao.load(123456L);
//                                dialog.dismiss();
//
//                            }
//                        });
//                        dialog.setQuXiaoListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                        dialog.show();


                        baoCunBean.setSize(0);
                        baoCunBeanDao.put(baoCunBean);

                        bt8.setEnabled(true);
                    }
                });
                animatorSet8.start();
                bt8.setEnabled(false);

                break;
            case R.id.bt9:

                ChongsZHI();
                bt9.requestFocus();
                bt9.setTextColor(Color.WHITE);
                bt9.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet9 = new AnimatorSet();
                animatorSet9.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt9,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt9,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet9.setDuration(300);
                animatorSet9.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
                        //弹窗
                        final XiuGaiHouTaiDialog dialog=new XiuGaiHouTaiDialog(SheZhiActivity.this);
                        if (baoCunBean.getTouxiangzhuji()==null){
                            dialog.setContents("http://192.168.2.78","账号","密码");
                        }else {
                            dialog.setContents(baoCunBean.getTouxiangzhuji(),baoCunBean.getWenzi(),baoCunBean.getGonggao());
                        }
                        dialog.setOnQueRenListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baoCunBean.setTouxiangzhuji(dialog.getIp());
                                baoCunBean.setWenzi(dialog.getZhanghao());
                                baoCunBean.setGonggao(dialog.getMima());
                                baoCunBeanDao.put(baoCunBean);
                                baoCunBean=baoCunBeanDao.get(123456L);
                              //  try {
                                  //  link_login();

                              //  }catch (Exception e){
                                 //   Log.d("SheZhiActivity", e.getMessage()+"ffffff");
                               // }

                                dialog.dismiss();

                            }
                        });
                        dialog.setQuXiaoListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                        bt9.setEnabled(true);
                    }
                });
                animatorSet9.start();
                bt9.setEnabled(false);

                break;
            case R.id.bt10:

                ChongsZHI();
                bt10.requestFocus();
                bt10.setTextColor(Color.WHITE);
                bt10.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet10 = new AnimatorSet();
                animatorSet10.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt10,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt10,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet10.setDuration(300);
                animatorSet10.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
                        //弹窗
                        final XiuGaiXinXiDialog dialog=new XiuGaiXinXiDialog(SheZhiActivity.this);
                        if (baoCunBean.getZhanghuId()==null){
                            dialog.setContents("设置账户id","10000046");
                        }else {
                            dialog.setContents("设置账户id",baoCunBean.getZhanghuId());
                        }
                        dialog.setOnQueRenListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baoCunBean.setZhanghuId(dialog.getContents());
                                baoCunBeanDao.put(baoCunBean);
                                baoCunBean=baoCunBeanDao.get(123456L);
                                dialog.dismiss();
                            }
                        });
                        dialog.setQuXiaoListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                        bt10.setEnabled(true);
                    }
                });
                animatorSet10.start();
                bt10.setEnabled(false);
                break;
            case R.id.bt11:
                ChongsZHI();
                bt11.requestFocus();
                bt11.setTextColor(Color.WHITE);
                bt11.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet11 = new AnimatorSet();
                animatorSet11.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt11,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt11,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet11.setDuration(300);
                animatorSet11.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
                        //弹窗
//                        final XiuGaiXinXiDialog dialog=new XiuGaiXinXiDialog(SheZhiActivity.this);
//                        if (baoCunBean.getHuiyiId()==null){
//                            dialog.setContents("设置会议id","10010037");
//                        }else {
//                            dialog.setContents("设置会议id",baoCunBean.getHuiyiId());
//                        }
//                        dialog.setOnQueRenListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                baoCunBean.setHuiyiId(dialog.getContents());
//                                baoCunBeanDao.update(baoCunBean);
//                                baoCunBean=baoCunBeanDao.load(123456L);
//                                dialog.dismiss();
//                            }
//                        });
//                        dialog.setQuXiaoListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//                        dialog.show();

//                        final HuanYingYuDialog dialog=new HuanYingYuDialog(SheZhiActivity.this);
//                        dialog.setOnPositiveListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.saveText();
//                                dialog.dismiss();
//                            }
//                        });
//                        dialog.setOnQuXiaoListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                        dialog.show();


                        bt11.setEnabled(true);
                    }
                });
                animatorSet11.start();
                bt11.setEnabled(false);

                break;
            case R.id.bt12:
                bt12.setEnabled(false);
                ChongsZHI();
                bt12.requestFocus();
                bt12.setTextColor(Color.WHITE);
                bt12.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet12 = new AnimatorSet();
                animatorSet12.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt12,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt12,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet12.setDuration(300);
                animatorSet12.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
                        //弹窗

                            duQuDialog=new DuQuDialog(SheZhiActivity.this);
                            duQuDialog.setCanceledOnTouchOutside(false);
                            duQuDialog.setOnPositiveListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    duQuDialog.dismiss();
                                }
                            });
                            duQuDialog.show();
                            bt12.setEnabled(true);
                            if (usbPath==null){
                                duQuDialog.setTiShi("     读取U盘数据失败");
                                TastyToast.makeText(SheZhiActivity.this,"请插拔一下USB",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
                                duQuDialog.setClose();
                                return;
                            }


                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (duQuDialog!=null){

                                                    duQuDialog.setTiShi("     寻找压缩文件中...(注意:只会解压找到的第一个压缩文件)");
                                                }

                                            }
                                        });

                                        List<String> zips=new ArrayList<>();
                                        List<String> zipList= FileUtil.getAllFiles(usbPath,zips);
                                        if (zipList==null || zipList.size()==0){
                                            return;
                                        }
                                        //获取文件名
                                        String zipName=null;
                                        String zz=zipList.get(0);
                                        zipName=zz.substring(zz.lastIndexOf("/")+1,zz.length());
                                        String trg=zz.substring(0, zz.length() - 4);
                                        Log.d("SheZhiActivity", trg);
                                        File file = new File(trg);
                                        if (!file.exists()) {
                                            Log.d(TAG, "创建文件状态:" + file.mkdir());
                                        }
                                        final String finalZipName = zipName;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (duQuDialog!=null){

                                                    duQuDialog.setTiShi("        解压文件 "+ finalZipName +" 中...");
                                                }

                                            }
                                        });
                                        //解压zip
                                      //  FileUtil.Unzip(zz,trg);
                                    //    FileUtil.readByApacheZipFile(zz,trg,SheZhiActivity.this);
                                        ZipFile zipFile=null;
                                        List fileHeaderList=null;
                                        try {
                                            // Initiate ZipFile object with the path/name of the zip file.
                                            zipFile = new ZipFile(zz);
                                            zipFile.setFileNameCharset("GBK");
                                             fileHeaderList = zipFile.getFileHeaders();
                                            // Loop through the file headers
                                            Log.d(TAG, "fileHeaderList.size():" + fileHeaderList.size());

                                            for (int i = 0; i < fileHeaderList.size(); i++) {
                                                FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
                                                FileHeader fileHeader2 = (FileHeader) fileHeaderList.get(0);

                                                Log.d(TAG, fileHeader2.getFileName());

                                                if (fileHeader.getFileName().contains(".xml")){
                                                    zipFile.extractFile( fileHeader.getFileName(), trg);
                                                    Log.d(TAG, "找到了"+i+"张照片");
                                                }
                                                // Various other properties are available in FileHeader. Please have a look at FileHeader
                                                // class to see all the properties
                                            }


                                        } catch (final ZipException e) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (duQuDialog!=null)
                                                        duQuDialog.setTiShi("        "+e.getMessage());
                                                }
                                            });
                                            e.printStackTrace();
                                        }
                                     //   UnZipfile.getInstance(SheZhiActivity.this).unZip(zz,trg,zipHandler);

                                        //拿到XML
                                        List<String> xmls=new ArrayList<>();
                                        final List<String> xmlList= FileUtil.getAllFileXml(trg,xmls);
                                        if (xmlList==null || xmlList.size()==0){
                                            return;
                                        }

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (duQuDialog!=null)
                                                duQuDialog.setTiShi("        解析Xml "+ xmlList.get(0) +" 中...");
                                            }
                                        });
                                        //解析XML
                                        try {

                                            FileInputStream fin=new FileInputStream(xmlList.get(0));
                                            Log.d("SheZhiActivity", "fin:" + fin);
                                            List<Subject> subjectList=  pull2xml(fin);
                                            if (subjectList!=null && subjectList.size()>0){
                                                //排序
                                                Collections.sort(subjectList, new Subject());
                                                Log.d("SheZhiActivity", "解析成功,文件个数:"+subjectList.size());
                                                if (zipFile!=null){
                                                    zipFile.setRunInThread(true); // true 在子线程中进行解压 ,
                                                    // false主线程中解压
                                                    zipFile.extractAll(trg); // 将压缩文件解压到filePath中..
                                                }
                                                //先登录旷视
                                                if (zhuJiBeanH.getUsername()!=null && zhuJiBeanH.getPwd()!=null){
                                                    getOkHttpClient2(subjectList,trg);

                                                }else {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (duQuDialog!=null){
                                                                duQuDialog.setClose();
                                                                duQuDialog.setTiShi("        登录后台失败,没有账户，密码");
                                                            }

                                                        }
                                                    });
                                                }

                                                final int size= subjectList.size();
                                                Log.d("ffffff", "size:" + size);

                                            }else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (duQuDialog!=null){
                                                            duQuDialog.setClose();
                                                            duQuDialog.setTiShi("        解析Xml失败...");
                                                        }

                                                    }
                                                });
                                            }
//                                            else {
//                                                runOnUiThread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        if (duQuDialog!=null){
//                                                            duQuDialog.setClose();
//                                                            duQuDialog.setTiShi("        解析Xml "+ xmlList.get(0) +" 失败...");
//                                                        }
//
//                                                    }
//                                                });
//                                            }

                                        } catch (Exception e) {

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (duQuDialog!=null){
                                                        duQuDialog.setClose();
                                                        duQuDialog.setTiShi("        解析Xml "+ xmlList.get(0) +" 失败...,请确认账户id跟zip包id是否一致");
                                                    }

                                                }
                                            });
                                            Log.d("SheZhiActivity", e.getMessage()+"解析XML异常");
                                        }

                                    } catch (Exception e) {
                                        Log.d("ffffff", e.getMessage() + "");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (duQuDialog!=null){
                                                    duQuDialog.setClose();
                                                    duQuDialog.setTiShi("         出现异常");
                                                }

                                            }
                                        });
                                    }

                                }
                            }).start();


                    }
                });
                animatorSet12.start();


                break;

        }

    }


    private void  ChongsZHI(){
        if (sheZhiBeanList!=null){
        for (int i=0;i<sheZhiBeanList.size();i++){
            sheZhiBeanList.get(i).setBackgroundResource(R.drawable.zidonghuoqu2);
            sheZhiBeanList.get(i).setTextColor(Color.parseColor("#1b37d6"));
          }
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.bt1:
              //  Log.d("SheZhiActivity", "hasFocus:1" + hasFocus);
                if (hasFocus){
                    ChongsZHI();
                    bt1.setTextColor(Color.WHITE);
                    bt1.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt1,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt1,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
                break;
            case R.id.bt2:
             //   Log.d("SheZhiActivity", "hasFocus:2" + hasFocus);
                if (hasFocus){
                    ChongsZHI();
                    bt2.setTextColor(Color.WHITE);
                    bt2.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt2,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt2,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
                break;
            case R.id.bt3:
                if (hasFocus){
                    ChongsZHI();
                    bt3.setTextColor(Color.WHITE);
                    bt3.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt3,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt3,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
               // Log.d("SheZhiActivity", "hasFocus:3" + hasFocus);
                break;
            case R.id.bt4:
                if (hasFocus){
                    ChongsZHI();
                    bt4.setTextColor(Color.WHITE);
                    bt4.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt4,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt4,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
               // Log.d("SheZhiActivity", "hasFocus:4" + hasFocus);
                break;
            case R.id.bt5:
                if (hasFocus){
                    ChongsZHI();
                    bt5.setTextColor(Color.WHITE);
                    bt5.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt5,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt5,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
              //  Log.d("SheZhiActivity", "hasFocus:5" + hasFocus);
                break;
            case R.id.bt6:
                if (hasFocus){
                    ChongsZHI();
                    bt6.setTextColor(Color.WHITE);
                    bt6.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt6,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt6,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }

                break;
            case R.id.bt7:
                if (hasFocus){
                    ChongsZHI();
                    bt7.setTextColor(Color.WHITE);
                    bt7.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt7,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt7,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
              //  Log.d("SheZhiActivity", "hasFocus7:" + hasFocus);
                break;
            case R.id.bt8:
                if (hasFocus){
                    ChongsZHI();
                    bt8.setTextColor(Color.WHITE);
                    bt8.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt8,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt8,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
                //  Log.d("SheZhiActivity", "hasFocus7:" + hasFocus);
                break;
            case R.id.bt9:
                if (hasFocus){
                    ChongsZHI();
                    bt9.setTextColor(Color.WHITE);
                    bt9.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet9 = new AnimatorSet();
                    animatorSet9.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt9,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt9,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet9.setDuration(300);
                    animatorSet9.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet9.start();
                }
                //  Log.d("SheZhiActivity", "hasFocus7:" + hasFocus);
                break;
            case R.id.bt10:
                if (hasFocus){
                    ChongsZHI();
                    bt10.setTextColor(Color.WHITE);
                    bt10.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet10 = new AnimatorSet();
                    animatorSet10.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt10,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt10,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet10.setDuration(300);
                    animatorSet10.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet10.start();
                }
                //  Log.d("SheZhiActivity", "hasFocus7:" + hasFocus);
                break;
            case R.id.bt11:
                if (hasFocus){
                    ChongsZHI();
                    bt11.setTextColor(Color.WHITE);
                    bt11.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt11,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt11,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
                //  Log.d("SheZhiActivity", "hasFocus7:" + hasFocus);
                break;
            case R.id.bt12:
                if (hasFocus){
                    ChongsZHI();
                    bt12.setTextColor(Color.WHITE);
                    bt12.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt12,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt12,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
                //  Log.d("SheZhiActivity", "hasFocus7:" + hasFocus);
                break;
        }
    }

    private void link_login(){

        final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

        OkHttpClient okHttpClient= MyApplication.getOkHttpClient();


        //	RequestBody requestBody = RequestBody.create(JSON, json);

		RequestBody body = new FormBody.Builder()
				.add("possId", Utils.getSerialNumber(this)==null?Utils.getIMSI():Utils.getSerialNumber(this))
				.add("possName",baoCunBean.getGuanggaojiMing())
                .add("accountId",baoCunBean.getZhanghuId())
                .add("status","1")
				.build();

        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("user-agent","Koala Admin")
                //.post(requestBody)
                .get()
                .post(body)
                .url(baoCunBean.getHoutaiDiZhi()+"/addPossEntity.do");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功"+call.request().toString());
                //获得返回体
                	try {

                        ResponseBody body = response.body();
                        String ss = body.string().trim();
                        Log.d("AllConnects", "序列号" + ss);

                        JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                        int N= jsonObject.get("dtoResult").getAsInt();
                        if (N==0){
                            link_getHouTaiZhuJi(baoCunBean.getZhanghuId());
                            //Log.d("YiDongNianHuiActivity", "N:" + N);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TastyToast.makeText(SheZhiActivity.this, "设置成功", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
                                }
                            });
                        }

                    }catch (Exception e){
                        Log.d("SheZhiActivity", e.getMessage()+"hhhhhh");
                    }


            }
        });
    }

  //  往SD卡写入文件的方法
    public void savaFileToSD(String filename, String filecontent) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + filename;

            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename,true);
            output.write(filecontent.getBytes());
            //将String字符串以字节流的形式写入到输出流中
            output.close();
            //关闭输出流
        }
    }

    private int TIMEOUT=60000;
    //从老黄后台拿主机信息
    private void link_getHouTaiZhuJi(String id){
        final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient=  new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.cookieJar(new CookiesManager())
                .retryOnConnectionFailure(true)
                .build();

        RequestBody body = new FormBody.Builder()
                .add("accountId",id)
                .add("machineCode",Utils.getSerialNumber(this)==null?Utils.getIMSI():Utils.getSerialNumber(this))
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .post(body)
                .url(baoCunBean.getHoutaiDiZhi()+"/findHost.do");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功"+call.request().toString());
                //获得返回体
                try{

                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                    Log.d("AllConnects", "获取主机数据"+ss);
                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    JsonArray j=jsonObject.getAsJsonArray("objects");
                    JsonObject object=  j.get(0). getAsJsonObject();
                    Gson gson=new Gson();
                    ZhuJiBeanH zhaoPianBean=gson.fromJson(object,ZhuJiBeanH.class);
                    //ws://192.168.2.58:9000/video
                    String s1=zhaoPianBean.getHostUrl();
                    if (s1.contains("//")){
                        String s=s1.split("//")[1];
                        baoCunBean.setTouxiangzhuji(zhaoPianBean.getHostUrl()+"/");
                        baoCunBean.setZhujiDiZhi("ws://"+s+":9000/video");
                        baoCunBeanDao.put(baoCunBean);
                    }

                    zhuJiBeanHDao.removeAll();
                    zhuJiBeanHDao.put(zhaoPianBean);
                    getOkHttpClient(1);
                }catch (Exception e){

                    Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
                }
            }
        });
    }

    //首先登录-->获取所有主机-->创建或者删除或者更新门禁
    public void getOkHttpClient(final int type){
        zhuJiBeanH=zhuJiBeanHDao.getAll().get(0);
        okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .cookieJar(new CookiesManager())
                .retryOnConnectionFailure(true)
                .build();

//			JSONObject json = new JSONObject();
//			try {
//				json.put("username", "test@megvii.com");
//				json.put("password", "123456");
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}


        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        //	RequestBody requestBody = RequestBody.create(JSON, json.toString());

        RequestBody body = new FormBody.Builder()
                .add("username", zhuJiBeanH.getUsername())
                .add("password", zhuJiBeanH.getPwd())
                .build();

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.header("User-Agent", "Koala Admin");
        requestBuilder.header("Content-Type","application/json");
        requestBuilder.post(body);
        requestBuilder.url(zhuJiBeanH.getHostUrl()+"/auth/login");
        final Request request = requestBuilder.build();
        Log.d("SheZhiActivity", zhuJiBeanH.getUsername());
        Log.d("SheZhiActivity", zhuJiBeanH.getPwd());
        Call mcall= okHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d("ffffff", "登陆失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                Log.d("ffffff", "登陆"+s);
                JsonObject jsonObject= GsonUtil.parse(s).getAsJsonObject();
                int i=1;
                i=jsonObject.get("code").getAsInt();
                if (i==0){
                    //登录成功,后续的连接操作因为cookies 原因,要用 MyApplication.okHttpClient
                    MyApplication.okHttpClient=okHttpClient;
                    if (type==1){
                        link_huiqumenjin();
                    }else {

                        link_getKu();
                    }



                }

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (zhuJiBeanHDao.getAll().size()>0)
        zhuJiBeanH=zhuJiBeanHDao.getAll().get(0);
    }

    //首先登录
    public void getOkHttpClient2(final List<Subject> subjectList, final String trg){
        zhuJiBeanH=zhuJiBeanHDao.getAll().get(0);
        okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .cookieJar(new CookiesManager())
                .retryOnConnectionFailure(true)
                .build();

        RequestBody body = new FormBody.Builder()
                .add("username", zhuJiBeanH.getUsername())
                .add("password", zhuJiBeanH.getPwd())
                .build();

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.header("User-Agent", "Koala Admin");
        requestBuilder.header("Content-Type","application/json");
        requestBuilder.post(body);
        requestBuilder.url(zhuJiBeanH.getHostUrl()+"/auth/login");
        final Request request = requestBuilder.build();
        Log.d("SheZhiActivity", zhuJiBeanH.getUsername());
        Log.d("SheZhiActivity", zhuJiBeanH.getPwd());
        Call mcall= okHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (duQuDialog!=null){
                            duQuDialog.setClose();
                            duQuDialog.setTiShi("        登录后台失败");
                        }

                    }
                });
                Log.d("ffffff", "登陆失败" + e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    String s = response.body().string();
                    Log.d("ffffff", "登陆" + s);
                    JsonObject jsonObject = GsonUtil.parse(s).getAsJsonObject();
                    int i = 1;
                    i = jsonObject.get("code").getAsInt();
                    if (i == 0) {
                        //登录成功,后续的连接操作因为cookies 原因,要用 MyApplication.okHttpClient
                        MyApplication.okHttpClient = okHttpClient;
                        String filePath=null;
                        final int size=subjectList.size();
                        int t=0;
                        Log.d(TAG, "size:" + size);
                        //循环
                        for (int j=0;j<size;j++) {
                           // Log.d(TAG, "i:" + j);
                            while (true){
                                try {
                                    Thread.sleep(100);
                                    t++;
                                    filePath=trg+File.separator+subjectList.get(j).getId()+(subjectList.get(j).getPhoto().
                                            substring(subjectList.get(j).getPhoto().lastIndexOf(".")));
                                    File file=new File(filePath);
                                    if ((file.isFile()&& file.length()>0)|| t==4000){
                                        t=0;
                                        Log.d(TAG, "file.length():" + file.length()+"   t:"+t);
                                        break;
                                    }
                                }catch (Exception e){
                                    filePath=null;
                                    Log.d(TAG, e.getMessage()+"检测文件是否存在异常");
                                    break;
                                }

                            }
                          //  Log.d(TAG, "文件存在");

                          //  Log.d("SheZhiActivity", "循环到"+j);
                            final int finalJ = j;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (duQuDialog!=null){
                                        duQuDialog.setProgressBar(((finalJ / (float) size) * 100));
                                    }

                                }
                            });

                            //查询旷视
                            synchronized (subjectList.get(j)) {
                                link_chaXunRenYuan(okHttpClient, subjectList.get(j),trg,filePath);
                                try {
                                    subjectList.get(j).wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                     //   Log.d("SheZhiActivity", "循环完了");

                        try {
                            String ss=stringBuilder.toString();
                            FileUtil.savaFileToSD("失败记录"+DateUtils.timesOne(System.currentTimeMillis()+"")+".txt",ss);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (duQuDialog!=null){
                                    if (stringBuilder.length()>0){
                                        duQuDialog.setClose();
                                        duQuDialog.setTiShi("            有失败的记录，已经保存到根目录");
                                    }else {
                                        duQuDialog.setClose();
                                        duQuDialog.setTiShi("            全部导入成功");
                                    }
                                }
                                stringBuilder.delete(0, stringBuilder.length());
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (duQuDialog!=null){
                                    duQuDialog.setTiShi("        登录后台失败");
                                    duQuDialog.setClose();
                                }

                            }
                        });
                    }


            }catch(Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (duQuDialog!=null){
                                duQuDialog.setClose();
                                duQuDialog.setTiShi("        出现异常");
                            }

                        }
                    });
                e.printStackTrace();
            }
          }
        });


    }

    public static final int TIMEOUT2 = 1000 * 100;
    private void link_P1(final ZhuJiBeanH zhuJiBeanH, String filePath, final Subject subject, final int id) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT2, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT2, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT2, TimeUnit.MILLISECONDS)
                .cookieJar(new CookiesManager())
                .retryOnConnectionFailure(true)
                .build();

        MultipartBody mBody;
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Log.d("SheZhiActivity", filePath+"图片文件路径");

        final File file=new File(filePath==null?"/a":filePath);
        RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream"),file);

        builder.addFormDataPart("photo",file.getName(), fileBody1);
        //builder.addFormDataPart("subject_id","228");
        mBody = builder.build();

        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .post(mBody)
                .url(zhuJiBeanH.getHostUrl()+ "/subject/photo");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                stringBuilder.append("上传图片失败记录:")
                        .append("ID").append(subject.getId()).append("姓名:")
                        .append(subject.getName())
                        .append("原因:")
                        .append(e.getMessage())
                        .append("时间:")
                        .append(DateUtils.time(System.currentTimeMillis()+""))
                        .append("\n");

                if (id==-1){
                    //新增
                    link_addPiLiangRenYuan(MyApplication.okHttpClient,subject,0);
                } else {
                    //更新
                    link_XiuGaiRenYuan(MyApplication.okHttpClient,subject,0,id);
                }

                Log.d("AllConnects图片上传", "请求识别失败" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            //    Log.d("AllConnects", "请求识别成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string();
                    Log.d("AllConnects图片上传", "传照片" + ss);
                    int ii=0;
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    if (jsonObject.get("code").getAsInt()==0){


                    JsonObject jo=jsonObject.get("data").getAsJsonObject();
                    ii=jo.get("id").getAsInt();

                    if (ii!=0) {
                        // ii 照片id
                        if (id == -1) {
                            //新增
                            link_addPiLiangRenYuan(MyApplication.okHttpClient, subject, ii);
                        } else {
                            //更新
                            link_XiuGaiRenYuan(MyApplication.okHttpClient, subject, ii, id);
                        }

                    }

                    }else {
                       // Log.d("SheZhiActivity333333", jsonObject.get("desc").getAsString());
                        stringBuilder.append("上传图片失败记录:")
                                .append("ID").append(subject.getId())
                                .append("姓名:")
                                .append(subject.getName())
                                .append("原因:")
                                .append(jsonObject.get("desc").getAsString())
                                .append("时间:")
                                .append(DateUtils.time(System.currentTimeMillis()+"")).append("\n");

                        if (id==-1){
                            //新增
                            link_addPiLiangRenYuan(MyApplication.okHttpClient,subject,0);
                        } else {
                            //更新
                            link_XiuGaiRenYuan(MyApplication.okHttpClient,subject,0,id);
                        }
                    }
                } catch (Exception e) {
                    stringBuilder.append("上传图片失败记录:").append("ID").
                            append(subject.getId())
                            .append("姓名:")
                            .append(subject.getName())
                            .append("原因:")
                            .append(e.getMessage())
                            .append("时间:").
                            append(DateUtils.time(System.currentTimeMillis()+"")).append("\n");
                    if (id==-1){
                        //新增
                        link_addPiLiangRenYuan(MyApplication.okHttpClient,subject,0);
                    } else {
                        //更新
                        link_XiuGaiRenYuan(MyApplication.okHttpClient,subject,0,id);
                    }
                    Log.d("AllConnects图片上传异常", e.getMessage());
                }
            }
        });

    }


    //查询人员
    private void link_chaXunRenYuan(final OkHttpClient okHttpClient, final Subject subject, final String trg, final String filePath){

        //	Log.d("MyReceivereee", "进来");
        final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

        //Log.d(TAG, json.toString());
        //RequestBody requestBody = RequestBody.create(JSON, json.toString());

//		RequestBody body = new FormBody.Builder()
//				.add("subject_type","0")
//				.add("name",renYuanInFo.getName())
//				.add("remark",renYuanInFo.getRemark())
//				.add("photo_ids",list.toString())
//				.add("phone",renYuanInFo.getPhone())
//				.add("department",renYuanInFo.getDepartment())
//				.add("title",renYuanInFo.getTitle())
//				.build();

        Request.Builder requestBuilder = new Request.Builder()
                //.header("Content-Type", "application/json")
                .get()
                .url(zhuJiBeanH.getHostUrl()+"/mobile-admin/subjects/list?category=employee&name="+subject.getName());

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                stringBuilder.append("查询旷视失败记录:").append("ID:")
                        .append(subject.getId()).append("姓名:")
                        .append(subject.getName()).append("时间:")
                        .append(DateUtils.time(System.currentTimeMillis()+"")).append("\n");
                link_P1(zhuJiBeanH,filePath,subject, -1);
                synchronized (subject){
                    subject.notify();
                }
                Log.d("AllConnects查询旷视", "请求失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              //  Log.d("AllConnects查询旷视", "请求成功"+call.request().toString());
                //获得返回体
                try{

                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                    Log.d("MyReceivereee", "查询旷视"+ss);
                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    GaiNiMaBi zhaoPianBean = gson.fromJson(jsonObject, GaiNiMaBi.class);
                    if (zhaoPianBean.getData()!=null){
                        int size=zhaoPianBean.getData().size();
                        if (size==0){
                            //先传图片
                            link_P1(zhuJiBeanH,filePath,subject, -1);
                        }
                        int pp=-1;
                        for (int i=0;i<size;i++){
                            //相同就更新
                            if (!zhaoPianBean.getData().get(i).getJob_number().equals(subject.getId()+"")){
                                //跟所有人都不同， 再新增
                                pp=0;
                                Log.d("MyReceiver", "222");
                            }
                            else {
                                //相同就不需要再往下比对了，跳出当前循环
                                pp=1;
                                //更新旷视人员信息//先传图片
                                if (zhaoPianBean.getData().get(i).getPhotos().size()>0)
                             //   subject.setLingshiZPID(zhaoPianBean.getData().get(i).getPhotos().get(0).getIdX());
                                link_P1(zhuJiBeanH,filePath,subject,zhaoPianBean.getData().get(i).getId());
                               // Log.d("MyReceiver", "333");
                                break;
                            }
                        }
                        if (pp==0){
                            //跟所有人都不同， 再新增
                            //先传图片
                            link_P1(zhuJiBeanH,filePath,subject,-1);
                        }

                    }else {
                        //	Log.d("MyReceiver", "444");
                        //先传图片
                        link_P1(zhuJiBeanH,filePath,subject, -1);
                    }


                }catch (Exception e){

                    Log.d("AllConnects查询旷视异常", e.getMessage()+"gggg");
                    stringBuilder.append("查询旷视失败记录:").append("ID:")
                            .append(subject.getId()).append("姓名:")
                            .append(subject.getName()).append("时间:")
                            .append(DateUtils.time(System.currentTimeMillis()+"")).append("\n");
                    link_P1(zhuJiBeanH,filePath,subject, -1);

                    synchronized (subject){
                        subject.notify();
                    }
                }


            }
        });
    }

    //修改人员
    private void link_XiuGaiRenYuan(final OkHttpClient okHttpClient, final Subject renYuanInFo, int i, int id){
        final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

        JSONObject json = new JSONObject();
        try {
            JSONArray jsonArray= new JSONArray();
            jsonArray.put(i);
            json.put("subject_type","0");
            json.put("name",renYuanInFo.getName());
            json.put("remark",renYuanInFo.getRemark());
            if (i!=0){
                json.put("photo_ids",jsonArray);
            }else {
                JSONArray jsonArray2= new JSONArray();
             //   jsonArray2.put(renYuanInFo.getLingshiZPID());
                json.put("photo_ids",jsonArray2);
            }
            json.put("phone",renYuanInFo.getPhone());
          //  json.put("department",renYuanInFo.getDepartment());
           // json.put("title",renYuanInFo.getTitle());
            json.put("job_number", renYuanInFo.getId());
         //   json.put("description", renYuanInFo.getSourceMeeting());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(JSON, json.toString());

//		RequestBody body = new FormBody.Builder()
//				.add("subject_type","0")
//				.add("name",renYuanInFo.getName())
//				.add("remark",renYuanInFo.getRemark())
//				.add("photo_ids",list.toString())
//				.add("phone",renYuanInFo.getPhone())
//				.add("department",renYuanInFo.getDepartment())
//				.add("title",renYuanInFo.getTitle())
//				.build();

        Request.Builder requestBuilder = new Request.Builder()
                //.header("Content-Type", "application/json")
                .put(requestBody)
                .url(zhuJiBeanH.getHostUrl()+"/subject/"+id);

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                stringBuilder.append("修改人员失败记录:").append("ID:")
                        .append(renYuanInFo.getId()).append("姓名:")
                        .append(renYuanInFo.getName()).append("时间:")
                        .append(DateUtils.time(System.currentTimeMillis()+"")).append("\n");
                synchronized (renYuanInFo){
                    renYuanInFo.notify();
                }
                Log.d("AllConnects修改失败", "请求失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功"+call.request().toString());
                //获得返回体
                try{
                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                    Log.d("AllConnects修改", "修该人员"+ss);
//					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//					if (jsonObject.get("code").getAsInt()==0){
//						JsonObject jo=jsonObject.get("data").getAsJsonObject();
//						renYuanInFo.setSid(jo.get("id").getAsInt());
//						renYuanInFoDao.insert(renYuanInFo);
//					}
                }catch (Exception e){
                    stringBuilder.append("修改人员失败记录:").append("ID:")
                            .append(renYuanInFo.getId()).append("姓名:")
                            .append(renYuanInFo.getName()).append("时间:")
                            .append(DateUtils.time(System.currentTimeMillis()+"")).append("\n");
                    synchronized (renYuanInFo){
                        renYuanInFo.notify();
                    }
                    Log.d("AllConnects修改异常", e.getMessage()+"gggg");
                }finally {
                    synchronized (renYuanInFo){
                        renYuanInFo.notify();
                    }
                }

            }
        });

    }


    //创建批量人员
    private void link_addPiLiangRenYuan(final OkHttpClient okHttpClient, final Subject subject, int ii){

        final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

        JSONObject json = new JSONObject();
        try {
            JSONArray jsonArray= new JSONArray();
            jsonArray.put(ii);
            json.put("subject_type","0");
            json.put("name",subject.getName());
            json.put("remark",subject.getRemark());
            json.put("photo_ids",jsonArray);
            json.put("phone",subject.getPhone());
         //   json.put("department",subject.getDepartment());
          //  json.put("title",subject.getTitle());
            json.put("job_number", subject.getId());
          //  json.put("description", subject.getSourceMeeting());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.d(TAG, json.toString());
        RequestBody requestBody = RequestBody.create(JSON, json.toString());

//		RequestBody body = new FormBody.Builder()
//				.add("subject_type","0")
//				.add("name",renYuanInFo.getName())
//				.add("remark",renYuanInFo.getRemark())
//				.add("photo_ids",list.toString())
//				.add("phone",renYuanInFo.getPhone())
//				.add("department",renYuanInFo.getDepartment())
//				.add("title",renYuanInFo.getTitle())
//				.build();

        Request.Builder requestBuilder = new Request.Builder()
                //.header("Content-Type", "application/json")
                .post(requestBody)
                .url(zhuJiBeanH.getHostUrl()+"/subject");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                stringBuilder.append("创建人员失败记录:").append("ID").append(subject.getId()).append("姓名:")
                        .append(subject.getName()).append("时间:").append(DateUtils.time(System.currentTimeMillis()+"")).append("\n");
                synchronized (subject){
                    subject.notify();
                }
              //  Log.d("AllConnects批量新增", "请求失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.d("MyReceiver", "请求成功"+call.request().toString());
                //获得返回体
                try{

                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                    //	Log.d("AllConnects批量新增", "批量创建人员"+ss);
                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    if (jsonObject.get("code").getAsInt()!=0){
                        stringBuilder.append("创建人员失败记录:").append("ID").append(subject.getId()).append("姓名:")
                                .append(subject.getName()).append("时间:").append(DateUtils.time(System.currentTimeMillis()+"")).append("\n");
                    }
                }catch (Exception e){
                    stringBuilder.append("创建人员失败记录:").append("ID").append(subject.getId()).append("姓名:")
                            .append(subject.getName()).append("时间:").append(DateUtils.time(System.currentTimeMillis()+"")).append("\n");
                    Log.d("AllConnects批量新增异常", e.getMessage()+"gggg");
                }finally {
                    synchronized (subject){
                        subject.notify();
                    }
                }


            }
        });
    }

    //获取全部库
    private void link_getKu(){
        final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//        OkHttpClient okHttpClient=  new OkHttpClient.Builder()
//                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//			//	.cookieJar(new CookiesManager())
//                .retryOnConnectionFailure(true)
//                .build();

//        RequestBody body = new FormBody.Builder()
//                .add("accountId",id)
//                .add("machineCode",Utils.getSerialNumber(this)==null?Utils.getIMSI():Utils.getSerialNumber(this))
//                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .get()
                .url(zhuJiBeanH.getHostUrl()+"/mobile-admin/subjects");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              //  Log.d("AllConnects", "请求成功"+call.request().toString());
                //获得返回体
                try{
                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                    Log.d("AllConnects", "获取库"+ss);
                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                  //  BuMenBeans zhaoPianBean = gson.fromJson(jsonObject, BuMenBeans.class);
                    JsonArray jsonArray=jsonObject.get("data").getAsJsonArray();
                   // Log.d("SheZhiActivity5555", "jsonArray.size():" + jsonArray.size());
                    for (JsonElement j : jsonArray ){
                        Thread.sleep(20);
                        link_shanchuKu(j.getAsJsonObject().get("id").getAsString());
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TastyToast.makeText(SheZhiActivity.this, "删除人脸库成功", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
                        }
                    });

                }catch (Exception e){

                    Log.d("WebsocketPushMsg", e.getMessage()+"kkkkk");
                }
            }
        });
    }

    //删除全部库
    private void link_shanchuKu(String id){
      //  Log.d("SheZhiActivity5555", id);
        final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//        OkHttpClient okHttpClient=  new OkHttpClient.Builder()
//                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//			//	.cookieJar(new CookiesManager())
//                .retryOnConnectionFailure(true)
//                .build();

//        RequestBody body = new FormBody.Builder()
//                .add("accountId",id)
//                .add("machineCode",Utils.getSerialNumber(this)==null?Utils.getIMSI():Utils.getSerialNumber(this))
//                .build();
        Request.Builder requestBuilder = new Request.Builder()
               // .header("Content-Type", "application/json")
                .delete()
                .url(zhuJiBeanH.getHostUrl()+"/subject/"+id);

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功"+call.request().toString());
                //获得返回体
                try{

                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                    Log.d("AllConnects", "删除库"+ss);
                  //  JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();


                }catch (Exception e){

                    Log.d("WebsocketPushMsg", e.getMessage()+"kkkkk");
                }
            }
        });
    }

    private void link_huiqumenjin(){
        final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//        OkHttpClient okHttpClient=  new OkHttpClient.Builder()
//                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//			//	.cookieJar(new CookiesManager())
//                .retryOnConnectionFailure(true)
//                .build();

//        RequestBody body = new FormBody.Builder()
//                .add("accountId",id)
//                .add("machineCode",Utils.getSerialNumber(this)==null?Utils.getIMSI():Utils.getSerialNumber(this))
//                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .get()
                .url(zhuJiBeanH.getHostUrl()+"/system/screen");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功"+call.request().toString());
                //获得返回体
               try{
                    String camera_address=null;
                    String camera_position=null;

                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                    Log.d("AllConnects", "获取门禁"+ss);
                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    JsonArray jsonArray=jsonObject.get("data").getAsJsonArray();
                    JsonObject  joo=jsonArray.get(0).getAsJsonObject();
                     camera_address=joo.get("camera_address").getAsString()==null?"":joo.get("camera_address").getAsString();
                     camera_position=joo.get("camera_position").getAsString()==null?"":joo.get("camera_position").getAsString();
                    baoCunBean.setShipingIP(camera_address);
                    baoCunBean.setShiPingWeiZhi(camera_position);
                    baoCunBeanDao.put(baoCunBean);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TastyToast.makeText(SheZhiActivity.this, "更新数据成功", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
                    }
                });

                }catch (Exception e){

                    Log.d("WebsocketPushMsg", e.getMessage()+"kkkkk");
                }
            }
        });
    }

    public List<Subject> pull2xml(InputStream is) throws Exception {
        List<Subject> list  = new ArrayList<>();
        Subject student = null;
        //创建xmlPull解析器
        XmlPullParser parser = Xml.newPullParser();
        ///初始化xmlPull解析器
        parser.setInput(is, "utf-8");
        //读取文件的类型
        int type = parser.getEventType();
        //无限判断文件类型进行读取
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                //开始标签
                case XmlPullParser.START_TAG:
                    if ("Root".equals(parser.getName())) {
                        String	id=parser.getAttributeValue(0);
                       if (baoCunBean.getZhanghuId()==null || !baoCunBean.getZhanghuId().equals(id)){
                           if (duQuDialog!=null)
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   if (duQuDialog!=null){
                                       duQuDialog.setClose();
                                       duQuDialog.setTiShi("           还没设置账户ID 或者账户id不一致");
                                   }

                               }
                           });

                           Thread.sleep(1500);
                           return null;
                       }

                    } else if ("Subject".equals(parser.getName())) {

                        student=new Subject();
                       // student.setId(parser.getAttributeValue(0));

                    } else if ("name".equals(parser.getName())) {
                        //获取name值
                        String name = parser.nextText();
                        if (name!=null){
                            student.setName(URLDecoder.decode(name, "UTF-8"));
                        }

                    } else if ("phone".equals(parser.getName())) {
                        //获取nickName值
                        String nickName = parser.nextText();
                        student.setPhone(nickName);
                    }else if ("comeFrom".equals(parser.getName())) {
                        //获取nickName值
                        String nickName = parser.nextText();
                        if (nickName!=null){
                           // student.setComeFrom(URLDecoder.decode(nickName, "UTF-8"));
                        }
                    }
                    else if ("interviewee".equals(parser.getName())) {
                        //获取nickName值
                        String nickName = parser.nextText();
                        if (nickName!=null){
                         //   student.setInterviewee(URLDecoder.decode(nickName, "UTF-8"));
                        }
                    }
                    else if ("city".equals(parser.getName())) {
                        //获取nickName值
                        String nickName = parser.nextText();
                        if (nickName!=null){
                          //  student.setCity(URLDecoder.decode(nickName, "UTF-8"));
                        }
                    }
                    else if ("department".equals(parser.getName())) {
                        //获取nickName值
                        String nickName = parser.nextText();
                        if (nickName!=null){
                        //    student.setDepartment(URLDecoder.decode(nickName, "UTF-8"));
                        }
                    }
                    else if ("email".equals(parser.getName())) {
                        //获取nickName值
                        String nickName = parser.nextText();
                        if (nickName!=null){
                         //   student.setEmail(URLDecoder.decode(nickName, "UTF-8"));
                        }
                    }
                    else if ("title".equals(parser.getName())) {
                        //获取nickName值
                        String nickName = parser.nextText();
                        if (nickName!=null){
                         //   student.setTitle(URLDecoder.decode(nickName, "UTF-8"));
                        }
                    }
                    else if ("location".equals(parser.getName())) {
                        //获取nickName值
                        String nickName = parser.nextText();
                        if (nickName!=null){
                          //  student.setLocation(URLDecoder.decode(nickName, "UTF-8"));
                        }
                    }
                    else if ("assemblyId".equals(parser.getName())) {
                        //获取nickName值
                        String nickName = parser.nextText();
                        if (nickName!=null){
                          //  student.setAssemblyId(URLDecoder.decode(nickName, "UTF-8"));
                        }
                    }
                    else if ("sourceMeeting".equals(parser.getName())) {
                        //获取nickName值
                        String nickName = parser.nextText();
                        if (nickName!=null){
                          //  student.setSourceMeeting(URLDecoder.decode(nickName, "UTF-8"));
                        }
                    }
                    else if ("remark".equals(parser.getName())) {
                        //获取nickName值
                        String nickName = parser.nextText();
                        if (nickName!=null){
                          //  student.setRemark(URLDecoder.decode(nickName, "UTF-8"));
                        }
                    }
                    else if ("photo".equals(parser.getName())) {
                        //获取nickName值
                        String nickName = parser.nextText();
                        if (nickName!=null){
                           // student.setPhoto(URLDecoder.decode(nickName, "UTF-8"));
                        }
                    }
                    break;
                //结束标签
                case XmlPullParser.END_TAG:
                    if ("Subject".equals(parser.getName())) {
                        list.add(student);
                    }
                    break;
            }
            //继续往下读取标签类型
            type = parser.next();
        }
        return list;
    }

    @Override
    public void zipStart() {

    }

    @Override
    public void zipSuccess() {

    }

    @Override
    public void zipProgress(final int progress) {

        if (duQuDialog!=null){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (duQuDialog!=null){
                    duQuDialog.setProgressBar(progress);
                }
            }
        });

        }


    }

    @Override
    public void zipFail() {

    }


    private void kaiPing(){

        sendBroadcast(new Intent("com.android.internal.policy.impl.showNavigationBar"));
        sendBroadcast(new Intent("com.android.systemui.statusbar.phone.statusopen"));
    }

    private void guanPing(){

        sendBroadcast(new Intent("com.android.internal.policy.impl.hideNavigationBar"));
        sendBroadcast(new Intent("com.android.systemui.statusbar.phone.statusclose"));
    }

    public static class  UsbBroadCastReceiver extends BroadcastReceiver {

        public UsbBroadCastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction()!=null && intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")){
                usbPath = intent.getData().getPath();
                List<String> sss=  FileUtil.getMountPathList();
                int size=sss.size();
                for (int i=0;i<size;i++){

                    if (sss.get(i).contains(usbPath)){
                        usbPath=sss.get(i);
                    }

                }

                Log.d("UsbBroadCastReceiver", usbPath);
            }


        }
    }


}
