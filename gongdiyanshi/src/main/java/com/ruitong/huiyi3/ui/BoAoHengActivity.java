package com.ruitong.huiyi3.ui;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruitong.huiyi3.MyApplication;
import com.ruitong.huiyi3.R;
import com.ruitong.huiyi3.adapter.MyAdapter;
import com.ruitong.huiyi3.beans.BaoCunBean;
import com.ruitong.huiyi3.beans.BaoCunBeanDao;
import com.ruitong.huiyi3.beans.BenDiMBbean;
import com.ruitong.huiyi3.beans.BenDiMBbeanDao;
import com.ruitong.huiyi3.beans.HuiYiInFoBean;
import com.ruitong.huiyi3.beans.MoShengRenBeanDao;
import com.ruitong.huiyi3.beans.ShiBieBean;
import com.ruitong.huiyi3.beans.TanChuangBean;
import com.ruitong.huiyi3.beans.WBBean;
import com.ruitong.huiyi3.beans.WeiShiBieBean;

import com.ruitong.huiyi3.beans.ZhuJiBeanHDao;
import com.ruitong.huiyi3.huiyixinxi.HuiYiID;
import com.ruitong.huiyi3.huiyixinxi.HuiYiIDDao;
import com.ruitong.huiyi3.service.AlarmReceiver;
import com.ruitong.huiyi3.tts.control.InitConfig;
import com.ruitong.huiyi3.tts.control.MySyntherizer;
import com.ruitong.huiyi3.tts.control.NonBlockSyntherizer;
import com.ruitong.huiyi3.tts.listener.UiMessageListener;
import com.ruitong.huiyi3.tts.util.OfflineResource;
import com.ruitong.huiyi3.utils.DateUtils;
import com.ruitong.huiyi3.utils.GsonUtil;
import com.ruitong.huiyi3.utils.Utils;

import com.ruitong.huiyi3.view.GlideRoundTransform;
import com.sdsmdg.tastytoast.TastyToast;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sun.misc.BASE64Decoder;


public class BoAoHengActivity extends Activity {
    private final static String TAG = "WebsocketPushMsg";
    public static String touxiangPath;
    @BindView(R.id.y37) TextView y37;@BindView(R.id.y36) TextView y36;@BindView(R.id.y38) TextView y38;@BindView(R.id.y34) TextView y34;
    @BindView(R.id.y32) TextView y32;@BindView(R.id.y31) TextView y31;@BindView(R.id.y33) TextView y33;@BindView(R.id.y35) TextView y35;
    @BindView(R.id.y29) TextView y29;@BindView(R.id.y27) TextView y27;@BindView(R.id.y26) TextView y26;@BindView(R.id.y28) TextView y28;
    @BindView(R.id.y30) TextView y30;@BindView(R.id.y24) TextView y24;@BindView(R.id.y22) TextView y22;@BindView(R.id.y21) TextView y21;
    @BindView(R.id.y23) TextView y23;@BindView(R.id.y25) TextView y25;@BindView(R.id.y19) TextView y19;@BindView(R.id.y17) TextView y17;
    @BindView(R.id.y16) TextView y16;@BindView(R.id.y18) TextView y18;@BindView(R.id.y20) TextView y20;@BindView(R.id.y14) TextView y14;
    @BindView(R.id.y12) TextView y12;@BindView(R.id.y11) TextView y11;@BindView(R.id.y13) TextView y13;@BindView(R.id.y15) TextView y15;
    @BindView(R.id.y9) TextView y9;@BindView(R.id.y7) TextView y7;@BindView(R.id.y6) TextView y6;@BindView(R.id.y8) TextView y8;@BindView(R.id.y10) TextView y10;
    @BindView(R.id.y4) TextView y4;@BindView(R.id.y2) TextView y2;@BindView(R.id.y1) TextView y1;@BindView(R.id.y3) TextView y3;@BindView(R.id.y5) TextView y5;
    private MyReceiver myReceiver = null;
    private RecyclerView recyclerView;
    private MyAdapter adapter = null;
    private ImageView hongsek, lvsek;
    private MoShengRenBeanDao daoSession = null;
    private static boolean isOne = true;
    //private SpeechSynthesizer mSpeechSynthesizer;
    //private WrapContentLinearLayoutManager manager;
    //private WrapContentLinearLayoutManager manager2;
    private static WebSocketClient webSocketClient = null;
    private static Vector<TanChuangBean> yuangongList = null;//上面的弹窗
    private static Vector<TanChuangBean> lingdaoList = null;//下面的弹窗
    private static Vector<TanChuangBean> lingshiList = null;//下面的弹窗
    //private static Vector<View> viewList=new Vector<>();
    private int dw, dh;
    private ImageView dabg;
    private BaoCunBeanDao baoCunBeanDao = null;
    private BaoCunBean baoCunBean = null;
    private NetWorkStateReceiver netWorkStateReceiver = null;
    private LottieAnimationView wangluo;
    private boolean isLianJie = false;
    private Typeface typeFace1;
    //private TickerView y1;
    private LinkedBlockingQueue<ShiBieBean.PersonBeanSB> linkedBlockingQueue;

    private String zhanghuID = null, huiyiID = null;
    protected Handler mainHandler;
    private String appId = "10588094";
    private String appKey = "dfudSSFfNNhDCDoK7UG9G5jn";
    private String secretKey = "9BaCHNSTw3TGRgTKht4ZZvPEb2fjKEC8";
    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    private TtsMode ttsMode = TtsMode.MIX;
    // 离线发音选择，VOICE_FEMALE即为离线女声发音。
    // assets目录下bd_etts_speech_female.data为离线男声模型；bd_etts_speech_female.data为离线女声模型
    private String offlineVoice = OfflineResource.VOICE_FEMALE;
    // 主控制类，所有合成控制方法从这个类开始
    private MySyntherizer synthesizer;
    //private View dongHauView;
    private GridLayoutManager gridLayoutManager;
    private static boolean isWC = true;
    private List<BenDiMBbean> mbLeiXingBeanList = null;
    private BenDiMBbeanDao benDiMBbeanDao = null;
    private RelativeLayout rootLayout;
    private ZhuJiBeanHDao zhuJiBeanHDao = null;
    private HuiYiIDDao huiYiIDDao = null;
    //private static int isDS=0;
    private final Timer timer = new Timer();
    private TimerTask task;
    private final Timer timer2 = new Timer();
    private TimerTask task2;
    private Button vipBT, jiabingBT, meitiBT;
    private int k1_x, k1_y;
    private static boolean isYY=true;
    private LinearLayout d_k1;
    private RelativeLayout diBuView, yanhuitu;
    private ImageView k1, k2, k3, k4, k5, k6, k7, k8, k9, k10, k11, k12, k13, k14, k15, k16, k17, k18,
            k19, k20, k21, k22, k23, k24, k25, k26, k27, k28, k29, k30, k31, k32, k33, k34, k35, k36,
            k37, k38, k39, k40, k41, k42, k43, k44, k45, dian, dian2, vipDian, jiabingdian, meitidian, jbv1, jbv2;
    private List<TextView> textViewList=new ArrayList<>();


    public Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(final Message msg) {
            switch (msg.what) {

                case 999:

                    //定义一个零时的数组，在最后一个view执行消失动画之前清掉数据，然后用这个零时的view来判断 是不是被覆盖。(在执行动画的时候 会有数据加进来，所以要用一个零时数组)

                    //判断下面数组有没有这个人
                    if (yuangongList.size() == 0) {

                        break;
                    }

                    int b = 0;
                    for (int i2 = 0; i2 < lingdaoList.size(); i2++) {
                        if (Objects.equals(lingdaoList.get(i2).getId(), yuangongList.get(0).getId())) {
                            b = 1;
                        }
                    }
                    lingshiList.remove(0);
                    final View view = yuangongList.get(0).getView();

                    if (b == 0) {
                        //不存在
                        final boolean[] kk = {true};
                        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
                        ObjectAnimator alphaAnim0 = ObjectAnimator.ofFloat(view, "translationY", 0, 480f);//设置透明度改变
                        alphaAnim0.setDuration(1000);//设置持续时间
                        ObjectAnimator alphaAnim1 = ObjectAnimator.ofFloat(view, "translationX", 0, -250f);//设置透明度改变
                        alphaAnim1.setDuration(1000);//设置持续时间
                        final ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.4f);//设置透明度改变
                        alphaAnim.setDuration(1000);//设置持续时间
                        //alphaAnim.start();
                        ObjectAnimator alphaAnim2 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.3f);//设置透明度改变
                        alphaAnim2.setDuration(1000);//设置持续时间
                        alphaAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                //Log.d(TAG, "animation.getCurrentPlayTime():" + animation.getCurrentPlayTime());

                                if (animation.getCurrentPlayTime() >= 600 && kk[0]) {
                                    kk[0] = false;

                                    //底部列表的
                                    lingdaoList.add(0, yuangongList.get(0));
                                    adapter.notifyItemInserted(0);
                                    gridLayoutManager.scrollToPosition(0);
                                    if (lingdaoList.size() > 6) {
                                        int si = lingdaoList.size() - 1;
                                        lingdaoList.remove(si);
                                        adapter.notifyItemRemoved(si);
                                        //adapter.notifyItemChanged(1);
                                        //adapter.notifyItemRangeChanged(1,tanchuangList.size());
                                        //adapter.notifyDataSetChanged();
                                        gridLayoutManager.scrollToPosition(0);
                                    }
                                }
                            }
                        });
                        alphaAnim2.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                try {
                                    view.setVisibility(View.GONE);
                                    rootLayout.removeViewAt(0);
                                    yuangongList.remove(0);
                                } catch (Exception e) {
                                    Log.d(TAG, e.getMessage() + "");
                                }
                                if (linkedBlockingQueue.size()==0)
                                isOne = true;


                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }

                        });
                        //alphaAnim.start();\
                        animators.add(alphaAnim0);
                        animators.add(alphaAnim1);
                        animators.add(alphaAnim);
                        animators.add(alphaAnim2);
                        AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
                        btnSexAnimatorSet.playTogether(animators);//设置一起播放
                        btnSexAnimatorSet.start();//开始播放



                    } else {
                        //存在

                        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
//							ObjectAnimator alphaAnim0 = ObjectAnimator.ofFloat(view,"translationY",0,560f);//设置透明度改变
//							alphaAnim0.setDuration(1000);//设置持续时间
//							ObjectAnimator alphaAnim1 = ObjectAnimator.ofFloat(view,"translationX",0,-250f);//设置透明度改变
//							alphaAnim1.setDuration(1000);//设置持续时间
                        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.05f);//设置透明度改变
                        alphaAnim.setDuration(1000);//设置持续时间
                        //alphaAnim.start();
                        ObjectAnimator alphaAnim2 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.05f);//设置透明度改变
                        alphaAnim2.setDuration(1000);//设置持续时间
                        alphaAnim2.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                view.setVisibility(View.GONE);

                                try {
                                    rootLayout.removeViewAt(0);
                                    yuangongList.remove(0);
                                } catch (Exception e) {
                                    Log.d(TAG, e.getMessage() + "");
                                }
                                if (linkedBlockingQueue.size()==0)
                                isOne = true;

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        //alphaAnim.start();
                        animators.add(alphaAnim);
                        animators.add(alphaAnim2);
                        AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
                        btnSexAnimatorSet.playTogether(animators);//设置一起播放
                        btnSexAnimatorSet.start();//开始播放
                    }


                    break;

            }


            if (msg.arg1 == 1) {

                ShiBieBean.PersonBeanSB dataBean = (ShiBieBean.PersonBeanSB) msg.obj;

                try {

                    final TanChuangBean bean = new TanChuangBean();
                    bean.setBytes(dataBean.getImage());
                    bean.setBumen(dataBean.getDepartment() == null ? "0" : dataBean.getDepartment());
                    bean.setId(dataBean.getId());
                    bean.setType(dataBean.getSubject_type());
                    bean.setName(dataBean.getName() == null ? "" : dataBean.getName());
                    bean.setRemark(dataBean.getRemark());
                    bean.setZhiwei(dataBean.getTitle() == null ? "" : dataBean.getTitle());
                    bean.setGonghao(dataBean.getJob_number() == null ? "" : dataBean.getJob_number());
                    bean.setTouxiang(dataBean.getAvatar());

                    Random rand = new Random();
                   // int randNumber =rand.nextInt(MAX - MIN + 1) + MIN; // randNumber 将被赋值为一个 MIN 和 MAX 范围内的随机数

                    donghua(textViewList.get(rand.nextInt(38)));

                    switch (dataBean.getSubject_type()) {
                        case 0: //员工

                            int mbtype = 1;
                            String hyy = "";
                            if (bean.getBumen() != null) {
                                for (BenDiMBbean mm : mbLeiXingBeanList) {
                                    if (bean.getBumen().equals(mm.getSubType())) {
                                        //	Log.d(TAG, "mm.getPhoto_index():" + mm.getPhoto_index());
                                        hyy = mm.getWelcomeSpeak();
                                        //	mbtype=mm.getPhoto_index();
                                    }
                                }
                            }

                            switch (mbtype) {
                                case 1: {

                                    final View view1 = View.inflate(BoAoHengActivity.this, R.layout.baoaoshang_item, null);
                                    ScreenAdapterTools.getInstance().loadView(view1);
                                    TextView name1 = (TextView) view1.findViewById(R.id.name);
                                    ImageView touxiang1 = (ImageView) view1.findViewById(R.id.touxiang);
                                    final RelativeLayout root_rl1 = (RelativeLayout) view1.findViewById(R.id.root_rl);
                                    if (bean.getName().equals("")){
                                        name1.setText(Html.fromHtml("<font color='#0d2cf9'><big>"+"嘉宾您好"+"</big></font>"));

                                    }else {
                                        name1.setText(Html.fromHtml("<font color='#0d2cf9'><big>"+bean.getName()+"</big></font>"+" <font color='#111111'>嘉宾</font>"));

                                    }
                                    TextView zhiwei = (TextView) view1.findViewById(R.id.zhiwei);
                                 //   zhiwei.setText(Html.fromHtml("<font color='#111111'>欢迎您光临博鳌会议</font>"));
                                    TextView huanyinyu = (TextView) view1.findViewById(R.id.huanyinyu);
                                    if (hyy.equals("")){
                                        huanyinyu.setText(Html.fromHtml("<font color='#111111'>欢迎莅临博鳌论坛</font>"));
                                        if (isYY){
                                            isYY=false;
                                            synthesizer.speak("欢迎莅临博鳌论坛");
                                        }

                                    }else {
                                        huanyinyu.setText(hyy);
                                        if (isYY){
                                            isYY=false;
                                            synthesizer.speak(hyy);
                                        }

                                    }

                                    if (bean.getTouxiang()!=null) {
                                        Glide.with(BoAoHengActivity.this)
                                                //	.load(R.drawable.vvv)
                                                .load(touxiangPath + bean.getTouxiang())
                                                .error(R.drawable.erroy_bg)
                                                //.apply(myOptions)
                                                .transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
                                               // .transform(new GlideCircleTransform(MyApplication.getAppContext(), 2, Color.parseColor("#ffffffff")))
                                                .into(touxiang1);
                                    }else {
                                       // Log.d(TAG, "SSSSSSSSSSSSS");
                                        Glide.with(BoAoHengActivity.this)
                                                //	.load(R.drawable.vvv)
                                                .load(bean.getBytes())
                                                .error(R.drawable.erroy_bg)
                                                //.apply(myOptions)
                                                .transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
                                              //  .transform(new GlideCircleTransform(MyApplication.getAppContext(), 2, Color.parseColor("#ffffffff")))
                                                .into(touxiang1);
                                    }

                                    view1.setX(dw);
                                    rootLayout.addView(view1);
                                    bean.setView(view1);


                                    RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) root_rl1.getLayoutParams();
                                    params1.height = dh / 4 + 30;
                                    params1.leftMargin = 40;
                                    params1.rightMargin = 40;
                                    root_rl1.setLayoutParams(params1);
                                    root_rl1.invalidate();

                                    //启动定时器或重置定时器
                                    if (task != null) {
                                        task.cancel();
                                        //timer.cancel();
                                        task = new TimerTask() {
                                            @Override
                                            public void run() {

                                                Message message = new Message();
                                                message.what = 999;
                                                handler.sendMessage(message);
                                                //	Log.d(TAG, "gggggggggggg");

                                            }
                                        };
                                        timer.schedule(task, 9000);
                                    } else {
                                        task = new TimerTask() {
                                            @Override
                                            public void run() {

                                                Message message = new Message();
                                                message.what = 999;
                                                handler.sendMessage(message);
                                                //	Log.d(TAG, "gggggggggggg2222");

                                            }
                                        };
                                        timer.schedule(task, 9000);
                                    }


                                    yuangongList.add(bean);
                                    lingshiList.add(bean);

                                    //入场动画(从右往左)
                                    ValueAnimator anim = ValueAnimator.ofInt(dw, 40);
                                    anim.setDuration(1100);
                                    anim.setRepeatMode(ValueAnimator.RESTART);
                                    Interpolator interpolator = new DecelerateInterpolator(2f);
                                    anim.setInterpolator(interpolator);
                                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {

                                            int currentValue = (Integer) animation.getAnimatedValue();
                                            // 获得改变后的值
//												System.out.println(currentValue);
                                            // 输出改变后的值

                                            // 步骤4：将改变后的值赋给对象的属性值，下面会详细说明
                                            view1.setX(currentValue);
                                            // 步骤5：刷新视图，即重新绘制，从而实现动画效果
                                            view1.requestLayout();

                                        }
                                    });
                                    anim.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            try {
                                                ShiBieBean.PersonBeanSB beanSB = linkedBlockingQueue.poll(10, TimeUnit.MILLISECONDS);
                                                if (beanSB == null) {
                                                    isOne = true;
                                                } else {
                                                    //	Log.d(TAG, "拿到的id"+beanSB.getId());
                                                    Message message2 = Message.obtain();
                                                    message2.arg1 = 1;
                                                    message2.obj = beanSB;
                                                    handler.sendMessage(message2);

                                                }

                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {

                                        }
                                    });
                                    anim.start();

                                    //	Log.d(TAG, "ttttttyuangongList:" + yuangongList.size());

                                    if (lingshiList.size() > 1) {

                                        int b = 0;
                                        for (int i2 = 0; i2 < lingdaoList.size(); i2++) {
                                            if (Objects.equals(lingdaoList.get(i2).getId(), yuangongList.get(0).getId())) {
                                                b = 1;
                                            }
                                        }
                                        lingshiList.remove(0);
                                        final View view = yuangongList.get(0).getView();
                                        if (b == 0) {
                                            //不存在
                                            final boolean[] kk = {true};
                                            List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
                                            ObjectAnimator alphaAnim0 = ObjectAnimator.ofFloat(view, "translationY", 0, 480f);//设置透明度改变
                                            alphaAnim0.setDuration(1000);//设置持续时间
                                            ObjectAnimator alphaAnim1 = ObjectAnimator.ofFloat(view, "translationX", 0, -250f);//设置透明度改变
                                            alphaAnim1.setDuration(1000);//设置持续时间
                                            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.4f);//设置透明度改变
                                            alphaAnim.setDuration(1000);//设置持续时间
                                            //alphaAnim.start();
                                            ObjectAnimator alphaAnim2 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.3f);//设置透明度改变
                                            alphaAnim2.setDuration(1000);//设置持续时间
                                            alphaAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                @Override
                                                public void onAnimationUpdate(ValueAnimator animation) {

                                                    if (animation.getCurrentPlayTime() >= 600 && kk[0]) {
                                                        kk[0] = false;
                                                        //底部列表的
                                                        lingdaoList.add(0, yuangongList.get(0));
                                                        adapter.notifyItemInserted(0);
                                                        gridLayoutManager.scrollToPosition(0);
                                                        if (lingdaoList.size() > 6) {
                                                            int si = lingdaoList.size() - 1;
                                                            lingdaoList.remove(si);
                                                            adapter.notifyItemRemoved(si);
                                                            //adapter.notifyItemChanged(1);
                                                            //adapter.notifyItemRangeChanged(1,tanchuangList.size());
                                                            //adapter.notifyDataSetChanged();
                                                            gridLayoutManager.scrollToPosition(0);
                                                        }
                                                    }

                                                }
                                            });
                                            alphaAnim2.addListener(new Animator.AnimatorListener() {
                                                @Override
                                                public void onAnimationStart(Animator animation) {

                                                }

                                                @Override
                                                public void onAnimationEnd(Animator animation) {

                                                    try {
                                                        view.setVisibility(View.GONE);
                                                        rootLayout.removeViewAt(0);
                                                        yuangongList.remove(0);
                                                    } catch (Exception e) {
                                                        Log.d(TAG, e.getMessage() + "");
                                                    }
                                                    if (linkedBlockingQueue.size()==0)
                                                    isOne = true;


                                                }

                                                @Override
                                                public void onAnimationCancel(Animator animation) {

                                                }

                                                @Override
                                                public void onAnimationRepeat(Animator animation) {

                                                }
                                            });
                                            //alphaAnim.start();\
                                            animators.add(alphaAnim0);
                                            animators.add(alphaAnim1);
                                            animators.add(alphaAnim);
                                            animators.add(alphaAnim2);
                                            AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
                                            btnSexAnimatorSet.playTogether(animators);//设置一起播放
                                            btnSexAnimatorSet.start();//开始播放
                                        } else {
                                            //存在
                                            List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
//							ObjectAnimator alphaAnim0 = ObjectAnimator.ofFloat(view,"translationY",0,560f);//设置透明度改变
//							alphaAnim0.setDuration(1000);//设置持续时间
//							ObjectAnimator alphaAnim1 = ObjectAnimator.ofFloat(view,"translationX",0,-250f);//设置透明度改变
//							alphaAnim1.setDuration(1000);//设置持续时间
                                            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.05f);//设置透明度改变
                                            alphaAnim.setDuration(1000);//设置持续时间
                                            //alphaAnim.start();
                                            ObjectAnimator alphaAnim2 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.05f);//设置透明度改变
                                            alphaAnim2.setDuration(1000);//设置持续时间
                                            alphaAnim2.addListener(new Animator.AnimatorListener() {
                                                @Override
                                                public void onAnimationStart(Animator animation) {

                                                }

                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    view.setVisibility(View.GONE);
//									viewList.remove(0);
//									if (viewList.size()>10){
//										viewList.clear();
//									}
                                                    try {
                                                        rootLayout.removeViewAt(0);
                                                        yuangongList.remove(0);
                                                    } catch (Exception e) {
                                                        Log.d(TAG, e.getMessage() + "");
                                                    }
                                                    if (linkedBlockingQueue.size()==0)
                                                    isOne = true;


                                                }

                                                @Override
                                                public void onAnimationCancel(Animator animation) {

                                                }

                                                @Override
                                                public void onAnimationRepeat(Animator animation) {

                                                }
                                            });
                                            //alphaAnim.start();
                                            animators.add(alphaAnim);
                                            animators.add(alphaAnim2);
                                            AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
                                            btnSexAnimatorSet.playTogether(animators);//设置一起播放
                                            btnSexAnimatorSet.start();//开始播放
                                        }

                                    }

                                    // 启动动画


//											SpringSystem springSystem1 = SpringSystem.create();
//											final Spring spring1 = springSystem1.createSpring();
//											//两个参数分别是弹力系数和阻力系数
//											spring1.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(100, 4));
//											// 添加弹簧监听器
//											spring1.addListener(new SimpleSpringListener() {
//												@Override
//												public void onSpringUpdate(Spring spring) {
//													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
//													float value = (float) spring.getCurrentValue();
//													float value2 = (float) spring.getCurrentDisplacementDistance();
//
//													Log.d(TAG, "value:" + value2);
//													//基于Y轴的弹簧阻尼动画
//													view1.setTranslationX(10f*value2);
//
//													// 对图片的伸缩动画
//													//	float scale = 2f - (value );
//													//	view1.setScaleX(value);
//													//view1.setScaleY(value);
//													//	Log.d(TAG, "spring1.getStartValue():" + spring1.getStartValue());
//
//												}
//											});
//											// 设置动画结束值
//
//											spring1.setEndValue(2f);


                                }
                                break;

                            }

//**************************************************************************************************************

                            break;

//						case 1: //普通访客
//							yuangongList.add(bean);
//							int i2=yuangongList.size();
//							adapter.notifyItemInserted(i2);
//							manager.scrollToPosition(i2-1);
//							new Thread(new Runnable() {
//								@Override
//								public void run() {
//
//									try {
//										Thread.sleep(10000);
//
//										Message message=Message.obtain();
//										message.what=999;
//										handler.sendMessage(message);
//
//									} catch (InterruptedException e) {
//										e.printStackTrace();
//									}
//
//
//								}
//							}).start();
//
//							break;
//						case 2:  //VIP访客
//							yuangongList.add(bean);
//							int i3=yuangongList.size();
//							adapter.notifyItemInserted(i3);
//							manager.scrollToPosition(i3-1);
//
//							new Thread(new Runnable() {
//								@Override
//								public void run() {
//
//									try {
//										Thread.sleep(10000);
//										Message message=Message.obtain();
//										message.what=999;
//										handler.sendMessage(message);
//
//									} catch (InterruptedException e) {
//										e.printStackTrace();
//									}
//
//
//								}
//							}).start();
//
//
//							break;

                    }
                } catch (Exception e) {
                    //Log.d("WebsocketPushMsg", e.getMessage());
                    e.printStackTrace();
                }

            } else if (msg.arg1 == 2) {

                final WeiShiBieBean dataBean = (WeiShiBieBean) msg.obj;

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            BASE64Decoder decoder = new BASE64Decoder();
                            // Base64解码
                            final byte[][] b;

                            b = new byte[][]{decoder.decodeBuffer(dataBean.getFace().getImage())};
                            for (int i = 0; i < b[0].length; ++i) {
                                if (b[0][i] < 0) {// 调整异常数据
                                    b[0][i] += 256;
                                }
                            }

//							TanChuangBean bean = new TanChuangBean();
//							bean.setBytes(b[0]);
//							bean.setName("陌生人");
//							bean.setType(-1);
//							bean.setTouxiang(null);
//							yuangongList.add(bean);
//							final int i3=yuangongList.size();
//							runOnUiThread(new Runnable() {
//								@Override
//								public void run() {
//
//									adapter.notifyItemInserted(i3);
//									manager.scrollToPosition(i3 - 1);
//
//								}
//							});
//
//							Thread.sleep(8000);
//
//							Message message = Message.obtain();
//							message.what = 999;
//							handler.sendMessage(message);
                        } catch (Exception e) {

                            Log.d(TAG, e.getMessage() + "陌生人解码");
                        }

                    }
                }).start();
            }

            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linkedBlockingQueue = new LinkedBlockingQueue<>();
        benDiMBbeanDao = MyApplication.myApplication.getDaoSession().getBenDiMBbeanDao();
        mbLeiXingBeanList = benDiMBbeanDao.loadAll();

        //tanChuangBeanDao=MyApplication.myApplication.getDaoSession().getTanChuangBeanDao();
        baoCunBeanDao = MyApplication.myApplication.getDaoSession().getBaoCunBeanDao();
        zhuJiBeanHDao = MyApplication.myApplication.getDaoSession().getZhuJiBeanHDao();
        huiYiIDDao = MyApplication.myApplication.getDaoSession().getHuiYiIDDao();
        baoCunBean = baoCunBeanDao.load(123456L);
        if (baoCunBean == null) {
            BaoCunBean baoCunBea = new BaoCunBean();
            baoCunBea.setId(123456L);
            baoCunBeanDao.insert(baoCunBea);
            baoCunBean = baoCunBeanDao.load(123456L);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        //DisplayMetrics dm = getResources().getDisplayMetrics();
        dw = Utils.getDisplaySize(BoAoHengActivity.this).x;
        dh = Utils.getDisplaySize(BoAoHengActivity.this).y;

        yuangongList = new Vector<>();
        lingdaoList = new Vector<>();
        lingshiList = new Vector<>();

        setContentView(R.layout.boaoheng);
        ButterKnife.bind(this);
        //ScreenAdapterTools.getInstance().reset(this);//如果希望android7.0分屏也适配的话,加上这句
        //在setContentView();后面加上适配语句
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        diBuView = (RelativeLayout) findViewById(R.id.dibuview);
        yanhuitu = (RelativeLayout) findViewById(R.id.yanhuitu);
        initview();
        rootLayout = (RelativeLayout) findViewById(R.id.root_layout);
        //rootLayout2= (LinearLayout) findViewById(R.id.root_layout2);
        //rootLayout3= (LinearLayout) findViewById(R.id.root_layout3);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MyAdapter(lingdaoList, BoAoHengActivity.this, dw, dh);
        recyclerView.setAdapter(adapter);

        dabg = (ImageView) findViewById(R.id.dabg);
        wangluo = (LottieAnimationView) findViewById(R.id.wangluo);
        wangluo.setSpeed(1.8f);
        typeFace1 = Typeface.createFromAsset(getAssets(), "fonts/xk.TTF");

//
//		y1= findViewById(R.id.y1);
//		y1.setCharacterLists(TickerUtils.provideNumberList());
//		y1.setAnimationDuration(1500);
//		y1.setAnimationInterpolator(new OvershootInterpolator());
//		String str = String.format("%04d", 0);
//		char s1[]=str.toCharArray();
//		StringBuilder cc=new StringBuilder();
//		cc.append(" ");
//		for (char c:s1){
//			cc.append(String.valueOf(c)).append(" ");
//		}
//		y1.setText(cc.toString());


        Button button = (Button) findViewById(R.id.dddk);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BoAoHengActivity.this, SheZhiActivity.class));
                finish();
            }
        });

        //实例化过滤器并设置要过滤的广播
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("duanxianchonglian");
        intentFilter.addAction("gxshipingdizhi");
        intentFilter.addAction("shoudongshuaxin");
        intentFilter.addAction("updateGonggao");
        intentFilter.addAction("updateTuPian");
        intentFilter.addAction("updateShiPing");
        intentFilter.addAction("delectShiPing");
        intentFilter.addAction("guanbi");
        intentFilter.addAction(Intent.ACTION_TIME_TICK);

        // 注册广播
        registerReceiver(myReceiver, intentFilter);

        daoSession = MyApplication.getAppContext().getDaoSession().getMoShengRenBeanDao();
        daoSession.deleteAll();
        //recyclerView = (HorizontalScrollView) findViewById(R.id.scrollView);
        //recyclerView2 = (ScrollView) findViewById(R.id.recyclerView2);
        //recyclerView3 = (ScrollView) findViewById(R.id.recyclerView3);


        mainHandler = new Handler() {
            /*
             * @param msg
             */

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //Log.d(TAG, "msg:" + msg);
            }

        };


        //Utils.initPermission(YiDongNianHuiActivity.this);
        initialTts();


        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) rootLayout.getLayoutParams();
        params2.topMargin = dh / 6 + 40;
        params2.height = dh / 4 + 30;
        rootLayout.setLayoutParams(params2);
        rootLayout.invalidate();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
        params.topMargin = 40;
        params.height = dh / 2;
        recyclerView.setLayoutParams(params);
        recyclerView.invalidate();

        //view重绘时回调
        diBuView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (isWC) {
                    isWC = false;

                    int[] location = new int[2];
                    int[] locationDian = new int[2];
                    int[] locationDian2 = new int[2];
                    int[] locationVip = new int[2];
                    int[] locationjiabing = new int[2];
                    int[] locationmeiti = new int[2];
                    d_k1.getLocationInWindow(location);
                    dian.getLocationInWindow(locationDian);
                    dian2.getLocationInWindow(locationDian2);
                    vipDian.getLocationInWindow(locationVip);
                    jiabingdian.getLocationInWindow(locationjiabing);
                    meitidian.getLocationInWindow(locationmeiti);

                    k1_x = location[0];
                    k1_y = location[1];


                    RelativeLayout.LayoutParams params4 = (RelativeLayout.LayoutParams) hongsek.getLayoutParams();
                    params4.topMargin = 10;
                    params4.leftMargin = locationDian[0] - 50;
                    params4.width = ((k1_x + d_k1.getWidth()) - locationDian[0]) + 10;
                    params4.height = d_k1.getHeight();
                    hongsek.setLayoutParams(params4);
                    hongsek.invalidate();

                    RelativeLayout.LayoutParams params5 = (RelativeLayout.LayoutParams) lvsek.getLayoutParams();
                    params5.topMargin = (d_k1.getHeight() / 2) + 10;
                    params5.leftMargin = locationDian2[0] - 46;
                    params5.width = locationDian[0] - locationDian2[0];
                    params5.height = d_k1.getHeight() / 2;
                    lvsek.setLayoutParams(params5);
                    lvsek.invalidate();

                    //vipbuttpn
                    RelativeLayout.LayoutParams params6 = (RelativeLayout.LayoutParams) vipBT.getLayoutParams();
                    params6.topMargin = locationVip[1] - location[1] + 40;
                    params6.leftMargin = locationVip[0] - 30;
                    params6.width = 100;
                    params6.height = 60;
                    vipBT.setLayoutParams(params6);
                    vipBT.invalidate();

                    RelativeLayout.LayoutParams params7 = (RelativeLayout.LayoutParams) jiabingBT.getLayoutParams();
                    params7.topMargin = locationjiabing[1] - location[1] + 40;
                    params7.leftMargin = locationjiabing[0] - 30;
                    params7.width = 100;
                    params7.height = 60;
                    jiabingBT.setLayoutParams(params7);
                    jiabingBT.invalidate();

                    RelativeLayout.LayoutParams params8 = (RelativeLayout.LayoutParams) meitiBT.getLayoutParams();
                    params8.topMargin = locationmeiti[1] - location[1] + 40;
                    params8.leftMargin = locationmeiti[0] - 10;
                    params8.width = 100;
                    params8.height = 60;
                    meitiBT.setLayoutParams(params8);
                    meitiBT.invalidate();
                    //嘉宾闪光区域
                    RelativeLayout.LayoutParams params9 = (RelativeLayout.LayoutParams) jbv1.getLayoutParams();
                    params9.topMargin = 10;
                    params9.leftMargin = 30;
                    params9.width = locationDian[0] - location[0] - 10;
                    params9.height = d_k1.getHeight() / 2;
                    jbv1.setLayoutParams(params9);
                    jbv1.invalidate();

                    RelativeLayout.LayoutParams params10 = (RelativeLayout.LayoutParams) jbv2.getLayoutParams();
                    params10.topMargin = (d_k1.getHeight() / 2) + 10;
                    params10.leftMargin = 30;
                    params10.width = locationDian2[0] - location[0] - 4;
                    params10.height = d_k1.getHeight() / 2;
                    jbv2.setLayoutParams(params10);
                    jbv2.invalidate();

                    final View vv = diBuView.findViewWithTag("1l1");
                    ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(vv, "alpha", 0.2f, 0.8f, 0.2f, 0.8f, 0.2f, 0.8f);//设置透明度改变
                    alphaAnim.setDuration(1020);//设置持续时间
                    alphaAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            vv.setBackgroundResource(R.color.huangse);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            vv.setBackgroundResource(R.color.img_full_opaque);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    alphaAnim.setRepeatCount(100000);
                    alphaAnim.start();


                }

            }

        });

        RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) diBuView.getLayoutParams();
        params3.topMargin = 40;
        params3.leftMargin = 40;
        params3.rightMargin = 40;
        params3.bottomMargin = 40;
        diBuView.setLayoutParams(params3);
        diBuView.invalidate();

        RelativeLayout.LayoutParams params13 = (RelativeLayout.LayoutParams) yanhuitu.getLayoutParams();
        params13.topMargin = 40;
        params13.leftMargin = 40;
        params13.rightMargin = 40;
        params13.bottomMargin = 40;
        yanhuitu.setLayoutParams(params13);
        yanhuitu.invalidate();


        //	link_login();

        new Thread(new Runnable() {
            @Override
            public void run() {

                SystemClock.sleep(10000);
                sendBroadcast(new Intent(BoAoHengActivity.this, AlarmReceiver.class));
                //	synthesizer.speak("吃饭了吗");

            }
        }).start();


        dingshi();
    }


    private void dingshi(){
        task2 = new TimerTask() {
            @Override
            public void run() {
                isYY=true;
             //   Log.d(TAG, "isYY2:" + isYY);
                if (task2!=null)
                    task2.cancel();
                dingshi();

            }
        };
        timer2.schedule(task2, 5000);

    }

    /**
     * 初始化引擎，需要的参数均在InitConfig类里
     * <p>
     * DEMO中提供了3个SpeechSynthesizerListener的实现
     * MessageListener 仅仅用log.i记录日志，在logcat中可以看见
     * UiMessageListener 在MessageListener的基础上，对handler发送消息，实现UI的文字更新
     * FileSaveListener 在UiMessageListener的基础上，使用 onSynthesizeDataArrived回调，获取音频流
     */
    protected void initialTts() {
        // 设置初始化参数
        SpeechSynthesizerListener listener = new UiMessageListener(mainHandler); // 此处可以改为 含有您业务逻辑的SpeechSynthesizerListener的实现类
        Map<String, String> params = getParams();
        // appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
        InitConfig initConfig = new InitConfig(appId, appKey, secretKey, ttsMode, params, listener);
        synthesizer = new NonBlockSyntherizer(this, initConfig, mainHandler); // 此处可以改为MySyntherizer 了解调用过程

    }

    private void donghua(final TextView view){
        if (view!=null){
        ObjectAnimator alphaAnim2 = ObjectAnimator.ofFloat(view, "scaleX", 0.6f,0.2f, 1.0f, 0.2f, 1.0f, 0.2f, 0.8f);//设置透明度改变
        alphaAnim2.setDuration(1020);//设置持续时间
        alphaAnim2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setBackgroundResource(R.drawable.zhuozi2);
                view.setTextColor(Color.WHITE);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setBackgroundResource(R.drawable.zhuozi);
                view.setTextColor(Color.parseColor("#0d2cf9"));

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        alphaAnim2.start();
        ObjectAnimator alphaAnim3 = ObjectAnimator.ofFloat(view, "scaleY", 0.6f,0.2f, 1.0f, 0.2f, 1.0f, 0.2f, 0.8f);//设置透明度改变
        alphaAnim3.setDuration(1020);//设置持续时间
        alphaAnim3.start();

        }

    }

    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        // 以下参数均为选填
        params.put(SpeechSynthesizer.PARAM_SPEAKER, baoCunBean.getBoyingren() + ""); // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_VOLUME, "8"); // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, baoCunBean.getYusu() + "");// 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, baoCunBean.getYudiao() + "");// 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);         // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

        // 离线资源文件
        OfflineResource offlineResource = createOfflineResource(offlineVoice);
        // 声学模型文件路径 (离线引擎使用), 请确认下面两个文件存在
        params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename());
        params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
                offlineResource.getModelFilename());

        return params;
    }

    protected OfflineResource createOfflineResource(String voiceType) {
        OfflineResource offlineResource = null;
        try {
            offlineResource = new OfflineResource(this, voiceType);
        } catch (IOException e) {
            // IO 错误自行处理
            e.printStackTrace();
            // toPrint("【error】:copy files from assets failed." + e.getMessage());
        }
        return offlineResource;
    }


    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            //Log.d(TAG, "intent:" + intent.getAction());

            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {

                //String time=(System.currentTimeMillis())+"";
                //	xiaoshi.setText(DateUtils.timeMinute(time));
                //	riqi.setText(DateUtils.timesTwo(time));
                //xingqi.setText(DateUtils.getWeek(System.currentTimeMillis()));
//				if (baoCunBean.getZhanghuId()!=null && baoCunBean.getHuiyiId()!=null)
//				link_shishi_renshu();

            }
            if (intent.getAction().equals("duanxianchonglian")) {

                //断线重连
                if (webSocketClient != null) {
                    //	Log.d(TAG, "刷脸监听");
                    if (!isLianJie) {
                        //	Log.d(TAG, "进来2");
                        try {
                            baoCunBean = baoCunBeanDao.load(123456L);
                            WebsocketPushMsg websocketPushMsg = new WebsocketPushMsg();
                            websocketPushMsg.close();
                            if (baoCunBean.getZhujiDiZhi() != null && baoCunBean.getShipingIP() != null) {
                                websocketPushMsg.startConnection(baoCunBean.getZhujiDiZhi(), baoCunBean.getShipingIP());
                            }


                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage() + "aaa");

                        }
                    }
                }
            }
            if (intent.getAction().equals("gxshipingdizhi")) {
                //重新开启刷脸监听
                baoCunBean = baoCunBeanDao.load(123456L);
                if (baoCunBean.getShipingIP() != null && baoCunBean.getZhujiDiZhi() != null) {
                    try {
                        WebsocketPushMsg websocketPushMsg = new WebsocketPushMsg();
                        websocketPushMsg.close();
                        if (baoCunBean.getShipingIP() != null && baoCunBean.getZhujiDiZhi() != null) {
                            websocketPushMsg.startConnection(baoCunBean.getZhujiDiZhi(), baoCunBean.getShipingIP());
                        }
                    } catch (Exception e) {
                        Log.d(TAG, e.getMessage() + "fghj");
                    }

                }
                touxiangPath=baoCunBean.getTouxiangzhuji();
                if (mbLeiXingBeanList != null && mbLeiXingBeanList.size() > 0)
                    mbLeiXingBeanList.clear();
                mbLeiXingBeanList = benDiMBbeanDao.loadAll();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Glide.get(BoAoHengActivity.this).clearDiskCache();

                    }
                }).start();

                final String ss = intent.getStringExtra("bgPath");
                if (ss != null) {
                    Log.d(TAG, "换底图图片" + ss);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Glide.with(BoAoHengActivity.this)
                            //.load(R.drawable.vvv)
                            .load(ss)
                            //	.load(zhuji+item.getTouxiang())
                            //.apply(myOptions)
                            //.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
                            //.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
                            .into(dabg);

                }

                if (baoCunBean.getHoutaiDiZhi() != null && !baoCunBean.getHoutaiDiZhi().equals("")
                        && baoCunBean.getZhanghuId() != null && !baoCunBean.getZhanghuId().equals("")
                        && baoCunBean.getZhanhuiId() != null && !baoCunBean.getZhanhuiId().equals("")) {
                    //link_login();

                    link_bg();

                }

            }
            if (intent.getAction().equals("shoudongshuaxin")) {

                Toast.makeText(BoAoHengActivity.this, "下载后台图片失败", Toast.LENGTH_SHORT).show();
            }

            if (intent.getAction().equals("guanbi")) {
                Log.d(TAG, "关闭");
                finish();
            }
        }
        //	}
    }

    // 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
    public static void getAllFiles(File root, String nameaaa) {
        File files[] = root.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    getAllFiles(f, nameaaa);
                } else {
                    String name = f.getName();
                    if (name.equals(nameaaa)) {
                        Log.d(TAG, "视频文件删除:" + f.delete());
                    }
                }
            }
        }
    }

    private void link_fasong(int timestamp, String id, final long huiyi) {
        //Log.d(TAG, DateUtils.time(timestamp + "000"));

        OkHttpClient okHttpClient = MyApplication.getOkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("machineName", baoCunBean.getGuanggaojiMing())
                .add("machineCode", Utils.getSerialNumber(this) == null ? Utils.getIMSI() : Utils.getSerialNumber(this))
                .add("exhibition_id", baoCunBean.getZhanhuiId())
                .add("timestamp", DateUtils.time(timestamp + "000"))
                .add("subjectId", id + "")
                .add("screenId", baoCunBean.getGonggao())
                //.add("conferenceName",huiYiName)
                //	.add("screenPosition",weizhi)
                .add("conference_id", huiyi + "")
                .build();

        //Log.d(TAG, baoCunBean.getZhanghuId()+"hhhhhhhhhhhhhhhh");
        Request.Builder requestBuilder = new Request.Builder()
                //.header("Content-Type", "application/json")
                .post(body)
                .url(baoCunBean.getHoutaiDiZhi() + "/appSave.do");
        //Log.d(TAG, baoCunBean.getHoutaiDiZhi() + "/appSave.do");
        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d("AllConnects", "请求获取二维码失败" + e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.d("AllConnects", "请求获取二维码成功"+call.request().toString());
                //获得返回体
                //List<YongHuBean> yongHuBeanList=new ArrayList<>();
                //List<MoShengRenBean2> intlist=new ArrayList<>();
                //	intlist.addAll(moShengRenBean2List);
                try {

                    ResponseBody body = response.body();
                    String ss = body.string();
                    Log.d("AllConnects", "签到返回" + ss);
                    link_shishi_renshu(huiyi);


                } catch (Exception e) {
                    Log.d("WebsocketPushMsg", e.getMessage() + "签到返回异常");
                }

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (KeyEvent.KEYCODE_MENU == keyCode) {  //如果按下的是菜单
            Log.d(TAG, "按下菜单键 ");

            startActivity(new Intent(BoAoHengActivity.this, SheZhiActivity.class));
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(800);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                for (int i = 0; i < 55555; i++) {
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    ShiBieBean.PersonBeanSB sb = new ShiBieBean.PersonBeanSB();
//                    sb.setId(1234567L + i);
//                    sb.setDepartment("观众");
//                    sb.setName("测试");
//                    linkedBlockingQueue.offer(sb);
//                    if (isOne) {
//                        isOne = false;
//                        Log.d(TAG, "进来了");
//                        Message message2 = Message.obtain();
//                        message2.arg1 = 1;
//                        message2.obj = sb;
//                        handler.sendMessage(message2);
//                        ShiBieBean.PersonBeanSB beanSB = null;
//
//                        try {
//                            beanSB = linkedBlockingQueue.poll(10, TimeUnit.MILLISECONDS);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        Log.d(TAG, "移出的id:" + beanSB.getId());
//                    }
//
////					Message message3 = Message.obtain();
////					message3.arg1 = 1;
////					message3.obj = sb;
////					handler.sendMessage(message3);
//
//                }
//
//
//            }
//        }).start();

        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new NetWorkStateReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netWorkStateReceiver, filter);
        }

        baoCunBean = baoCunBeanDao.load(123456L);
        if (baoCunBean.getHoutaiDiZhi() != null && !baoCunBean.getHoutaiDiZhi().equals("")
                && baoCunBean.getZhanghuId() != null && !baoCunBean.getZhanghuId().equals("")
                && baoCunBean.getZhanhuiId() != null && !baoCunBean.getZhanhuiId().equals("")) {
            link_bg();
            //link_shishi_renshu();
        }

//        List<ZhuJiBeanH> hh = zhuJiBeanHDao.loadAll();
//        if (hh != null && hh.size() > 0) {
//            touxiangPath = hh.get(0).getHostUrl();
//            Log.d("jjjjj", touxiangPath);
//            if (touxiangPath == null || touxiangPath.equals("")) {
//                touxiangPath = "http://www.192.168.1.1";
//            }
//        } else {
//            touxiangPath = "http://www.192.168.1.1";
//        }
        touxiangPath=baoCunBean.getTouxiangzhuji();

        zhanghuID = baoCunBean.getZhanghuId();
        huiyiID = baoCunBean.getZhanhuiId();


        if (baoCunBean != null && baoCunBean.getZhujiDiZhi() != null) {
            try {
                //String[] a1=baoCunBean.getZhujiDiZhi().split("//");
                //String[] b1=a1[1].split(":");
                //zhuji="http://"+b1[0];
                WebsocketPushMsg websocketPushMsg = new WebsocketPushMsg();
                websocketPushMsg.close();
                if (baoCunBean.getZhujiDiZhi() != null && baoCunBean.getShipingIP() != null) {
                    websocketPushMsg.startConnection(baoCunBean.getZhujiDiZhi(), baoCunBean.getShipingIP());
                }
            } catch (URISyntaxException e) {
                Log.d(TAG, e.getMessage() + "ddd");

            }
        } else {
            TastyToast.makeText(BoAoHengActivity.this, "没有设置后台地址", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
        }

        if (benDiMBbeanDao != null && benDiMBbeanDao.loadAll().size() > 0) {

            String ppp = baoCunBean.getWenzi();
            if (ppp != null && !ppp.equals(""))
                Glide.with(BoAoHengActivity.this)
                        //	.load(R.drawable.vvv)
                        .load(ppp)
                        //	.load(zhuji+item.getTouxiang())
                        //.apply(myOptions)
                        //.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
                        //.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
                        .into(dabg);
        }

        super.onResume();

    }


    @Override
    public void onPause() {

        Log.d(TAG, "暂停");

        super.onPause();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        isWC = true;
        if (webSocketClient != null) {
            webSocketClient.close();
            webSocketClient = null;
        }

        Intent intent1 = new Intent("guanbi333"); //关闭监听服务
        sendBroadcast(intent1);
        synthesizer.release();
        handler.removeCallbacksAndMessages(null);
        if (myReceiver != null)
            unregisterReceiver(myReceiver);
        unregisterReceiver(netWorkStateReceiver);
        super.onDestroy();

    }


    /**
     * 识别消息推送
     * 主机盒子端API ws://[主机ip]:9000/video
     * 通过 websocket 获取 识别结果
     *
     * @author Wangshutao
     */
    private class WebsocketPushMsg {
        /**
         * 识别消息推送
         *
         * @param wsUrl   websocket接口 例如 ws://192.168.1.50:9000/video
         * @param rtspUrl 视频流地址 门禁管理-门禁设备-视频流地址
         *                例如 rtsp://192.168.0.100/live1.sdp
         *                或者 rtsp://admin:admin12345@192.168.1.64/live1.sdp
         *                或者 rtsp://192.168.1.103/user=admin&password=&channel=1&stream=0.sdp
         *                或者 rtsp://192.168.1.100/live1.sdp
         *                ?__exper_tuner=lingyun&__exper_tuner_username=admin
         *                &__exper_tuner_password=admin&__exper_mentor=motion
         *                &__exper_levels=312,1,625,1,1250,1,2500,1,5000,1,5000,2,10000,2,10000,4,10000,8,10000,10
         *                &__exper_initlevel=6
         * @throws URISyntaxException
         * @throws
         * @throws ://192.168.2.52/user=admin&password=&channel=1&stream=0.sdp rtsp://192.166.2.55:554/user=admin_password=tljwpbo6_channel=1_stream=0.sdp?real_stream
         */
        private void startConnection(String wsUrl, String rtspUrl) throws URISyntaxException {
            URI uri = null;
            //当视频流地址中出现&符号时，需要进行进行url编码
            if (rtspUrl.contains("&")) {
                try {
                    //Log.d("WebsocketPushMsg", "dddddttttttttttttttt"+rtspUrl);
                    rtspUrl = URLEncoder.encode(rtspUrl, "UTF-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    //Log.d("WebsocketPushMsg", e.getMessage());
                }
            }

            try {

                uri = URI.create(wsUrl + "?url=" + rtspUrl);

                Log.d("WebsocketPushMsg", "url=" + uri);
                webSocketClient = new WebSocketClient(uri) {
                    //	private Vector vector=new Vector();

                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        isLianJie = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!BoAoHengActivity.this.isFinishing())
                                    wangluo.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onMessage(String ss) {

                        JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                        Gson gson = new Gson();
                        WBBean wbBean = gson.fromJson(jsonObject, WBBean.class);

                        if (wbBean.getType().equals("recognized")) { //识别
                            Log.d("WebsocketPushMsg", "识别出了");

                            final ShiBieBean dataBean = gson.fromJson(jsonObject, ShiBieBean.class);

                            try {

//                                if (baoCunBean.getHoutaiDiZhi() != null
//                                        && !baoCunBean.getHoutaiDiZhi().equals("")
//                                        && zhanghuID != null && !zhanghuID.equals("")
//                                        && huiyiID != null && !huiyiID.equals("")) {
//
//                                    int po = 1;
 //                                   String bm[] = dataBean.getPerson().getDescription().split(",");
                                    //	Log.d("WebsocketPushMsg", "bm.length:" + bm.length);

//                                    for (String s : bm) {
//                                        try {
//                                            HuiYiID huiyi = huiYiIDDao.queryBuilder().where(HuiYiIDDao.Properties.SubConferenceCode.eq(s)).unique();
//                                            if (huiyi == null) {
//                                                runOnUiThread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        TastyToast.makeText(BoAoHengActivity.this, "查询不到展会编码", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
//                                                    }
//                                                });
//                                            }

//                                            if (baoCunBean.getZhanhuiBianMa().contains(s)
//                                                    && (huiyi.getStartTime() < System.currentTimeMillis())
//                                                    && (huiyi.getEndTime() > System.currentTimeMillis())) {


                                                linkedBlockingQueue.offer(dataBean.getPerson());
                                                if (isOne) {
                                                    isOne = false;
                                                    Message message2 = Message.obtain();
                                                    message2.arg1 = 1;
                                                    message2.obj = dataBean.getPerson();
                                                    handler.sendMessage(message2);

                                                    ShiBieBean.PersonBeanSB beanSB = null;
                                                    try {
                                                        beanSB = linkedBlockingQueue.poll(10, TimeUnit.MILLISECONDS);
                                                      //  Log.d("WebsocketPushMsg", "111111消费掉的:" + beanSB.getId());
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                    //Log.d(TAG, "移出的id:" + beanSB.getId());
                                                }

//                                                link_fasong(dataBean.getData().getTimestamp(), dataBean.getPerson().getJob_number(), huiyi.getId());
//                                                po = 1;
//
//                                                break;
//
//                                            } else {
//                                                po = 2;
//                                                //Log.d("WebsocketPushMsg", "33333333333333");
//                                            }
//                                        } catch (Exception e) {
//                                            Log.d("WebsocketPushMsg", e.getMessage() + "666666666666666");
//                                        }

                                 //   }
//                                    if (po == 2) {
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                TastyToast.makeText(BoAoHengActivity.this, "非当前时段会议人员", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
//                                            }
//                                        });
//                                    }


//                                } else {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            TastyToast.makeText(BoAoHengActivity.this, "参数不全", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
//                                        }
//                                    });
//
 //                               }


                            } catch (Exception e) {
                                Log.d("WebsocketPushMsg", e.getMessage() + "aaajjjj");
                            }


                        } else if (wbBean.getType().equals("unrecognized")) {
                            //	Log.d("WebsocketPushMsg", "识别出了陌生人");
                         //   if (baoCunBean.getIsShowMoshengren()) {

                                JsonObject jsonObject1 = jsonObject.get("data").getAsJsonObject();

                                final WeiShiBieBean dataBean = gson.fromJson(jsonObject1, WeiShiBieBean.class);

                                try {
                                 //   MoShengRenBean bean = new MoShengRenBean(dataBean.getTrack(), "sss");

                                 //   daoSession.insert(bean);
                                    try {

                                        BASE64Decoder decoder = new BASE64Decoder();
                                        // Base64解码
                                        final byte[][] b;

                                        b = new byte[][]{decoder.decodeBuffer(dataBean.getFace().getImage())};
                                        for (int i = 0; i < b[0].length; ++i) {
                                            if (b[0][i] < 0) {// 调整异常数据
                                                b[0][i] += 256;
                                            }
                                        }

                                        ShiBieBean.PersonBeanSB sb=new ShiBieBean.PersonBeanSB();
                                        sb.setId(System.currentTimeMillis());
                                        sb.setName("");
                                        sb.setImage(b[0]);
                                        linkedBlockingQueue.offer(sb);
                                        if (isOne) {
                                            isOne = false;
                                            Message message2 = Message.obtain();
                                            message2.arg1 = 1;
                                            message2.obj = sb;
                                            handler.sendMessage(message2);

                                            ShiBieBean.PersonBeanSB beanSB = null;
                                            try {
                                                beanSB = linkedBlockingQueue.poll(10, TimeUnit.MILLISECONDS);
                                                Log.d("WebsocketPushMsg", "111111消费掉的:" + beanSB.getId());
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            //Log.d(TAG, "移出的id:" + beanSB.getId());
                                        }

                                    } catch (Exception e) {

                                        Log.d(TAG, e.getMessage() + "陌生人解码");
                                    }





                                } catch (Exception e) {
                                    Log.d("WebsocketPushMsg", e.getMessage()+"陌生人异常");
                                    //e.printStackTrace();
                                }
                                //finally {
//                                    try {
//                                        Thread.sleep(300);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                    try {
//                                        daoSession.deleteByKey(dataBean.getTrack());
//                                        //Log.d("WebsocketPushMsg", "删除");
//                                    } catch (Exception e) {
//                                        Log.d("WebsocketPushMsg", e.getMessage());
//                                    }
                              //  }
                            }
                       // }
                    }

                    @Override
                    public void onClose(int i, String s, boolean b) {
                        isLianJie = false;
                        Log.d("WebsocketPushMsg", "onClose" + i);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!BoAoHengActivity.this.isFinishing()) {
                                    wangluo.setVisibility(View.VISIBLE);
                                    //wangluo.setText("连接识别主机失败,重连中...");
                                }

                            }
                        });

                    }

                    @Override
                    public void onError(Exception e) {
                        isLianJie = false;
                        //Log.d("WebsocketPushMsg", "onClose"+i);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!BoAoHengActivity.this.isFinishing()) {
                                    wangluo.setVisibility(View.VISIBLE);
                                    //wangluo.setText("连接识别主机失败,重连中...");
                                }

                            }
                        });
                        Log.d("WebsocketPushMsg9", "onError" + e.getMessage());

                    }
                };

                webSocketClient.connect();

            } catch (Exception e) {
                Log.d(TAG, e.getMessage() + "");
            }

        }

        private void close() {
//
//			if (conntionHandler!=null && runnable!=null){
//				conntionHandler.removeCallbacks(runnable);
//				conntionHandler=null;
//				runnable=null;
//
//			}
            if (webSocketClient != null) {
                webSocketClient.close();
                webSocketClient = null;
                System.gc();

            }

        }

    }


    public class NetWorkStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                //获取ConnectivityManager对象对应的NetworkInfo对象
                //以太网
                NetworkInfo wifiNetworkInfo1 = connMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
                //获取WIFI连接的信息
                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //获取移动数据连接的信息
                NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (wifiNetworkInfo1.isConnected() || wifiNetworkInfo.isConnected() || dataNetworkInfo.isConnected()) {
                    wangluo.setVisibility(View.GONE);

                } else {
                    isLianJie = false;

                    wangluo.setVisibility(View.VISIBLE);
                }


//				if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//					Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
//				} else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
//					Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
//				} else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//					Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
//				} else {
//					Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
//				}
//API大于23时使用下面的方式进行网络监听
            } else {

                Log.d(TAG, "API23");
                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                //获取所有网络连接的信息
                Network[] networks = connMgr.getAllNetworks();
                //用于存放网络连接信息
                StringBuilder sb = new StringBuilder();
                //通过循环将网络信息逐个取出来
                Log.d(TAG, "networks.length:" + networks.length);
                if (networks.length == 0) {
                    isLianJie = false;
                    wangluo.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < networks.length; i++) {
                    //获取ConnectivityManager对象对应的NetworkInfo对象
                    NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);

                    if (networkInfo.isConnected()) {
                        wangluo.setVisibility(View.GONE);

                    }
                }

            }
        }
    }


    private void link_bg() {
        if (huiYiIDDao != null)
            huiYiIDDao.deleteAll();
        //	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = MyApplication.getOkHttpClient();
        //RequestBody requestBody = RequestBody.create(JSON, json);
        RequestBody body = new FormBody.Builder()
                .add("id", baoCunBean.getZhanhuiId())
                .build();
        Log.d(TAG, baoCunBean.getZhanhuiId() + "获取后台会议信息");
        Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.header("user-agent","Koala Admin")
                //.post(requestBody)
                //.get()
                .post(body)
                .url(baoCunBean.getHoutaiDiZhi() + "/findSubConference.do");

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
                    Log.d("AllConnects", "获取后台会议信息" + ss);

                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    final HuiYiInFoBean renShu = gson.fromJson(jsonObject, HuiYiInFoBean.class);
                    List<HuiYiInFoBean.ObjectsBean> gg = renShu.getObjects();
                    int si = gg.size();
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < si; i++) {

                        if (gg.get(i).getMachineCode().contains(Utils.getSerialNumber(BoAoHengActivity.this) == null ? Utils.getIMSI() : Utils.getSerialNumber(BoAoHengActivity.this))) {
                            stringBuilder.append(gg.get(i).getSubConferenceCode());
                            stringBuilder.append(",");
                            //所有展会编码已逗号分开保存
                            try {
                                HuiYiID huiYiID = new HuiYiID();
                                huiYiID.setId((long) gg.get(i).getId());
                                huiYiID.setSubConferenceCode(gg.get(i).getSubConferenceCode());
                                huiYiID.setStartTime(gg.get(i).getStartTime());
                                huiYiID.setEndTime(gg.get(i).getEndTime());
                                huiYiIDDao.insert(huiYiID);
                            } catch (Exception e) {

                                Log.d("YiDongNianHuiActivity", e.getMessage() + "");
                            }

                        }
                    }

                    baoCunBean.setZhanhuiBianMa(stringBuilder.toString());
                    baoCunBeanDao.update(baoCunBean);
                    Log.d("YiDongNianHuiActivity", baoCunBean.getZhanhuiBianMa() + "d");

                } catch (Exception e) {
                    Log.d("WebsocketPushMsg", e.getMessage() + "ttttt");
                }

            }
        });
    }

    private void link_shishi_renshu(long huiyi) {

        //	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = MyApplication.getOkHttpClient();
        //RequestBody requestBody = RequestBody.create(JSON, json);
        RequestBody body = new FormBody.Builder()
                .add("meetingId", huiyi + "")
                .add("signInChannel", "1")
                .add("machineCode", Utils.getSerialNumber(this) == null ? Utils.getIMSI() : Utils.getSerialNumber(this))
                .build();

        Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.header("user-agent","Koala Admin")
                //.post(requestBody)
                //.get()
                .post(body)
                .url(baoCunBean.getHoutaiDiZhi() + "/querySignSubjectMeetingPeoples.do");

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
                    Log.d("AllConnects", "获取实时人数" + ss);

                    final JsonObject jsonArray = GsonUtil.parse(ss).getAsJsonObject();

                    //	Gson gson=new Gson();
                    //final ShiShiRenShuBean renShu=gson.fromJson(jsonArray.get(""),ShiShiRenShuBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
//								String str = String.format("%04d",jsonArray.get("dtoDesc").getAsInt());
//								char s1[]=str.toCharArray();
//								StringBuilder cc=new StringBuilder();
//								cc.append(" ");
//								for (char c:s1){
//									cc.append(String.valueOf(c)).append(" ");
//								}
                                //y1.setText(cc.toString());

                            } catch (Exception e) {
                                Log.d("YiDongNianHuiActivity", e.getMessage() + "获取实时人数异常");
                            }

//							String str2 = String.format("%04d", renShu.getTjOutPeople());
//							char s2[]=str2.toCharArray();
//							StringBuilder cc2=new StringBuilder();
//							for (char c:s2){
//								cc2.append(String.valueOf(c)).append(" ");
//							}
//							n1.setText(cc2.toString());

                        }
                    });


                } catch (Exception e) {
                    Log.d("WebsocketPushMsg", e.getMessage() + "tttttiiii");
                }

            }
        });
    }


    private void initview() {
        d_k1 = findViewById(R.id.d_k1);
        k2 = findViewById(R.id.k2);
        k3 = findViewById(R.id.k3);
        k16 = findViewById(R.id.k16);
        k31 = findViewById(R.id.k31);
        k1 = findViewById(R.id.k1);
        k17 = findViewById(R.id.k17);
        k32 = findViewById(R.id.k32);
        k18 = findViewById(R.id.k18);
        k33 = findViewById(R.id.k33);
        k4 = findViewById(R.id.k4);k19 = findViewById(R.id.k19);k34 = findViewById(R.id.k34);
        k5 = findViewById(R.id.k5);k20 = findViewById(R.id.k20);
        k35 = findViewById(R.id.k35);k6 = findViewById(R.id.k6);k21 = findViewById(R.id.k21);
        k36 = findViewById(R.id.k36);k7 = findViewById(R.id.k7);k22 = findViewById(R.id.k22);
        k37 = findViewById(R.id.k37);k8 = findViewById(R.id.k8);k23 = findViewById(R.id.k23);
        k38 = findViewById(R.id.k38);k9 = findViewById(R.id.k9);k24 = findViewById(R.id.k24);
        k39 = findViewById(R.id.k39);
        k10 = findViewById(R.id.k10);k25 = findViewById(R.id.k25);k40 = findViewById(R.id.k40);
        k11 = findViewById(R.id.k11);
        k26 = findViewById(R.id.k26);k41 = findViewById(R.id.k41);
        k12 = findViewById(R.id.k12);k27 = findViewById(R.id.k27);k42 = findViewById(R.id.k42);
        k13 = findViewById(R.id.k13);k28 = findViewById(R.id.k28);k43 = findViewById(R.id.k43);k14 = findViewById(R.id.k14);
        k29 = findViewById(R.id.k29);k44 = findViewById(R.id.k44);
        k15 = findViewById(R.id.k15);k30 = findViewById(R.id.k30);
        k45 = findViewById(R.id.k45);
        dian = findViewById(R.id.dian);
        dian2 = findViewById(R.id.dian2);
        vipBT = findViewById(R.id.vipbutton);
        meitiBT = findViewById(R.id.meitibutton);
        jiabingBT = findViewById(R.id.jiabingbutton);
        vipDian = findViewById(R.id.vipdian);
        jiabingdian = findViewById(R.id.jiabingdian);
        meitidian = findViewById(R.id.meitidian);
        jbv1 = findViewById(R.id.jbv1);
        jbv2 = findViewById(R.id.jbv2);
        hongsek = findViewById(R.id.hongsek);
        lvsek = findViewById(R.id.lvsek);
        k1.setTag("1l1");
        k2.setTag("1l2");
        k3.setTag("1l3");
        k4.setTag("1c1");
        k5.setTag("1c2");
        k6.setTag("1c3");
        k7.setTag("1r1");
        k8.setTag("1r2");
        k9.setTag("1r3");

        y1.setTag(1); y9.setTag(9); y17.setTag(17); y25.setTag(25); y33.setTag(33);
        y2.setTag(2); y10.setTag(10); y18.setTag(18); y26.setTag(26); y34.setTag(34);
        y3.setTag(3); y11.setTag(11); y19.setTag(19); y27.setTag(27); y35.setTag(35);
        y4.setTag(4); y12.setTag(12); y20.setTag(20); y28.setTag(28); y36.setTag(36);
        y5.setTag(5); y13.setTag(13); y21.setTag(21); y29.setTag(29); y37.setTag(37);
        y6.setTag(6); y14.setTag(14); y22.setTag(22); y30.setTag(30); y38.setTag(38);
        y7.setTag(7); y15.setTag(15); y23.setTag(23); y31.setTag(31);
        y8.setTag(8); y16.setTag(16); y24.setTag(24); y32.setTag(32);


        for (int i=1;i<=38;i++){

            textViewList.add((TextView) yanhuitu.findViewWithTag(i));

        }

    }


}
