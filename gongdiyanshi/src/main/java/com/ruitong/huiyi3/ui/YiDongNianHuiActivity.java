package com.ruitong.huiyi3.ui;


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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.bumptech.glide.Glide;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import com.ruitong.huiyi3.MyApplication;
import com.ruitong.huiyi3.R;
import com.ruitong.huiyi3.beans.BaoCunBean;
import com.ruitong.huiyi3.beans.BaoCunBeanDao;
import com.ruitong.huiyi3.beans.BenDiMBbean;
import com.ruitong.huiyi3.beans.BenDiMBbeanDao;
import com.ruitong.huiyi3.beans.BenDiQianDaoDao;
import com.ruitong.huiyi3.beans.BenDiRenShuBean;
import com.ruitong.huiyi3.beans.BenDiRenShuBeanDao;
import com.ruitong.huiyi3.beans.HuiYiInFoBean;

import com.ruitong.huiyi3.beans.MoShengRenBean;
import com.ruitong.huiyi3.beans.MoShengRenBeanDao;
import com.ruitong.huiyi3.beans.QianDaoId;
import com.ruitong.huiyi3.beans.QianDaoIdDao;
import com.ruitong.huiyi3.beans.RenShu;
import com.ruitong.huiyi3.beans.ShiBieBean;


import com.ruitong.huiyi3.beans.TanChuangBean;
import com.ruitong.huiyi3.beans.WBBean;
import com.ruitong.huiyi3.beans.WeiShiBieBean;
import com.ruitong.huiyi3.beans.ZhuJiBeanH;
import com.ruitong.huiyi3.beans.ZhuJiBeanHDao;
import com.ruitong.huiyi3.huiyixinxi.HuiYiID;
import com.ruitong.huiyi3.huiyixinxi.HuiYiIDDao;
import com.ruitong.huiyi3.interfaces.RecytviewCash;
import com.ruitong.huiyi3.service.AlarmReceiver;
import com.ruitong.huiyi3.tts.control.InitConfig;
import com.ruitong.huiyi3.tts.control.MySyntherizer;
import com.ruitong.huiyi3.tts.control.NonBlockSyntherizer;
import com.ruitong.huiyi3.tts.listener.UiMessageListener;
import com.ruitong.huiyi3.tts.util.OfflineResource;
import com.ruitong.huiyi3.utils.DateUtils;
import com.ruitong.huiyi3.utils.GsonUtil;
import com.ruitong.huiyi3.utils.Utils;
import com.ruitong.huiyi3.view.GlideCircleTransform;
import com.ruitong.huiyi3.view.GlideRoundTransform;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;



import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sun.misc.BASE64Decoder;


public class YiDongNianHuiActivity extends Activity implements RecytviewCash {
	private final static String TAG = "WebsocketPushMsg";
//	private IjkVideoView ijkVideoView;
	private MyReceiver myReceiver=null;
	//private SurfaceView surfaceview;
	private ScrollView recyclerView;
	//private MyAdapter adapter=null;
	private HorizontalScrollView recyclerView2;
	//private MyAdapter2 adapter2=null;
	private MoShengRenBeanDao daoSession=null;
	//private SpeechSynthesizer mSpeechSynthesizer;
	//private WrapContentLinearLayoutManager manager;
	//private WrapContentLinearLayoutManager manager2;
	private static  WebSocketClient webSocketClient=null;
//	private MediaPlayer mediaPlayer=null;
//	private IVLCVout vlcVout=null;
//	private IVLCVout.Callback callback;
//	private LibVLC libvlc;
//	private Media media;
//	private SurfaceHolder mSurfaceHolder;
//	private String zhuji=null;
//	private static final String zhuji2="http://121.46.3.20";
	private static Vector<TanChuangBean> lingdaoList=null;
	private static Vector<TanChuangBean> yuangongList=null;
	private static Vector<View> viewList=new Vector<>();
	private int dw,dh;
	private ImageView dabg;
	private BaoCunBeanDao baoCunBeanDao=null;
	private BaoCunBean baoCunBean=null;
	private NetWorkStateReceiver netWorkStateReceiver=null;
	private LottieAnimationView wangluo;
	private boolean isLianJie=false;
	//private List<AllUserBean.DataBean> dataBeanList=new ArrayList<>();
	//private RelativeLayout top_rl;
//	private TanChuangBeanDao tanChuangBeanDao=null;
	private Typeface typeFace1;
	private TickerView y1;
	private String zhanghuID=null,huiyiID=null;
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
	private BenDiRenShuBean benDiRenShuBean=null;
	private BenDiRenShuBeanDao benDiRenShuBeanDao=null;
	private QianDaoIdDao qianDaoIdDao=null;
	private String huiYiName="", jubanGongSi="",huiyiZhuTi="";
	//private String tiHuan=null;
	//private HuanYinYuBeanDao huanYinYuBeanDao=MyApplication.getAppContext().getDaoSession().getHuanYinYuBeanDao();
	private BenDiQianDaoDao benDiQianDaoDao=null;
	private List<BenDiMBbean> mbLeiXingBeanList=null;
	private BenDiMBbeanDao benDiMBbeanDao=null;
	private LinearLayout rootLayout;
	private LinearLayout rootLayout2;
	private String touxiangPath=null;
	private ZhuJiBeanHDao zhuJiBeanHDao=null;
	private HuiYiIDDao huiYiIDDao=null;


	public  Handler handler=new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(final Message msg) {
			switch (msg.what) {
//				case 111:
//					//更新地址
//
//					break;
//				case 110:
//					if (lingdaoList.size() > 1) {
//
////						AnimatorSet animatorSet = new AnimatorSet();
////						animatorSet.playTogether(
////								ObjectAnimator.ofFloat(adapter2.getViewByPosition(recyclerView2, 1, R.id.ffflll), "scaleY", 1f, 0f),
////								ObjectAnimator.ofFloat(adapter2.getViewByPosition(recyclerView2, 1, R.id.ffflll), "scaleX", 1f, 0f)
////								//	ObjectAnimator.ofFloat(helper.itemView,"alpha",0f,1f)
////						);
////						animatorSet.setDuration(200);
////						animatorSet.setInterpolator(new AccelerateInterpolator());
////						animatorSet.addListener(new AnimatorListenerAdapter() {
////							@Override
////							public void onAnimationEnd(Animator animation) {
////								adapter2.notifyItemRemoved(1);
////								lingdaoList.remove(1);
////
////							}
////						});
////						animatorSet.start();
//
//					}
//
//
//					break;
				case 999:

					if (yuangongList.size()>0){
						//adapter.notifyItemRemoved(0);
						rootLayout.removeViewAt(1);
						//Log.d(TAG, "dddddd21212121d");
						yuangongList.remove(0);
					}

					if (lingdaoList.size()>6){
						rootLayout2.removeViewAt(0);
						lingdaoList.remove(0);
					}

					break;

			}

			if (msg.arg1==1){

				//view1.setVisibility(View.GONE);

				ShiBieBean.PersonBeanSB dataBean= (ShiBieBean.PersonBeanSB) msg.obj;
				try {

					final TanChuangBean bean=new TanChuangBean();
					bean.setBytes(null);
					bean.setBumen(dataBean.getDepartment()==null ? "0":dataBean.getDepartment());
					bean.setId(dataBean.getId());
					bean.setType(dataBean.getSubject_type());
					bean.setName(dataBean.getName()==null ? "":dataBean.getName());
					bean.setRemark(dataBean.getRemark());
					bean.setZhiwei(dataBean.getTitle()==null ? "":dataBean.getTitle());
					bean.setGonghao(dataBean.getJob_number()==null ? "":dataBean.getJob_number());
					bean.setTouxiang(dataBean.getAvatar());

					switch (dataBean.getSubject_type()) {
						case 0: //员工
							//Log.d(TAG, "员工k");

								int a = 0;
								for (int i2 = 0; i2 < yuangongList.size(); i2++) {
									if (Objects.equals(yuangongList.get(i2).getId(), bean.getId())) {
										a = 1;
									}
								}

							int b = 0;
							for (int i2 = 0; i2 < lingdaoList.size(); i2++) {
								if (Objects.equals(lingdaoList.get(i2).getId(), bean.getId())) {
									b = 1;
								}
							}

								if (a==0){
								int mbtype=1;
								String hyy="";
									yuangongList.add(bean);
									int i1 = yuangongList.size();
									if (bean.getBumen()!=null){
										for (BenDiMBbean mm:mbLeiXingBeanList){
												if (bean.getBumen().equals(mm.getSubType())){
													Log.d(TAG, "mm.getPhoto_index():" + mm.getPhoto_index());
													hyy=mm.getWelcomeSpeak();
													mbtype=mm.getPhoto_index();
												}
										}
									}

									switch (mbtype){
										case 1: {

											final View view1 = View.inflate(YiDongNianHuiActivity.this, R.layout.item1, null);
											ScreenAdapterTools.getInstance().loadView(view1);
											TextView name1 = (TextView) view1.findViewById(R.id.name);
											ImageView touxiang1 = (ImageView) view1.findViewById(R.id.touxiang);
											RelativeLayout root_rl1 = (RelativeLayout) view1.findViewById(R.id.root_rl);
											name1.setText(bean.getName());
											TextView zhiwei = (TextView) view1.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											TextView huanyinyu = (TextView) view1.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											synthesizer.speak(hyy==null?"":hyy);

											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													.error(R.drawable.thetwo)
													//.apply(myOptions)
													.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang1);

											rootLayout.addView(view1);

//											ViewGroup.LayoutParams params1 = root_rl1.getLayoutParams();
//											params1.height = dh / 5;
//											root_rl1.setLayoutParams(params1);
//											root_rl1.invalidate();

											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem1 = SpringSystem.create();
											final Spring spring1 = springSystem1.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring1.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring1.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view1.setScaleX(value);
													view1.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring1.setEndValue(1f);
										}
											break;
										case 2: {

											final View view2 = View.inflate(YiDongNianHuiActivity.this, R.layout.item2, null);
											ScreenAdapterTools.getInstance().loadView(view2);
											TextView name2 = (TextView) view2.findViewById(R.id.name);
											ImageView touxiang2 = (ImageView) view2.findViewById(R.id.touxiang);
											RelativeLayout root_rl2 = (RelativeLayout) view2.findViewById(R.id.root_rl);
											name2.setText(bean.getName());
											TextView zhiwei = (TextView) view2.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											TextView huanyinyu = (TextView) view2.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											synthesizer.speak(hyy==null?"":hyy);

											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													 .error(R.drawable.erroy_bg)
													//.apply(myOptions)
													.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang2);

											rootLayout.addView(view2);



											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem2 = SpringSystem.create();
											final Spring spring2 = springSystem2.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring2.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring2.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view2.setScaleX(value);
													view2.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring2.setEndValue(1f);
										}
											break;
										case 3: {
											final View view3 = View.inflate(YiDongNianHuiActivity.this, R.layout.item3, null);
											ScreenAdapterTools.getInstance().loadView(view3);
											TextView name3 = (TextView) view3.findViewById(R.id.name);
											ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
											RelativeLayout root_rl3 = (RelativeLayout) view3.findViewById(R.id.root_rl);
											name3.setText(bean.getName());
											TextView zhiwei = (TextView) view3.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											TextView huanyinyu = (TextView) view3.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											synthesizer.speak(hyy==null?"":hyy);

											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													.error(R.drawable.erroy_bg)
													//.apply(myOptions)
													.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang);

											rootLayout.addView(view3);


											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem3 = SpringSystem.create();
											final Spring spring3 = springSystem3.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring3.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view3.setScaleX(value);
													view3.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring3.setEndValue(1f);
										}
											break;
										case 4: {
											final View view3 = View.inflate(YiDongNianHuiActivity.this, R.layout.item4, null);
											ScreenAdapterTools.getInstance().loadView(view3);
											TextView name3 = (TextView) view3.findViewById(R.id.name);
											ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
											RelativeLayout root_rl3 = (RelativeLayout) view3.findViewById(R.id.root_rl);
											name3.setText(bean.getName());
											TextView zhiwei = (TextView) view3.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											TextView huanyinyu = (TextView) view3.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											synthesizer.speak(hyy==null?"":hyy);

											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													.error(R.drawable.erroy_bg)
													//.apply(myOptions)
													.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang);

											rootLayout.addView(view3);



											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem3 = SpringSystem.create();
											final Spring spring3 = springSystem3.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring3.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view3.setScaleX(value);
													view3.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring3.setEndValue(1f);
										}
											break;
										case 5: {
											final View view3 = View.inflate(YiDongNianHuiActivity.this, R.layout.item5, null);
											ScreenAdapterTools.getInstance().loadView(view3);
											TextView name3 = (TextView) view3.findViewById(R.id.name);
											ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
											RelativeLayout root_rl3 = (RelativeLayout) view3.findViewById(R.id.root_rl);
											name3.setText(bean.getName());
											TextView zhiwei = (TextView) view3.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											TextView huanyinyu = (TextView) view3.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											synthesizer.speak(hyy==null?"":hyy);

											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													.error(R.drawable.erroy_bg)
													//.apply(myOptions)
													.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang);

											rootLayout.addView(view3);

											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem3 = SpringSystem.create();
											final Spring spring3 = springSystem3.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring3.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view3.setScaleX(value);
													view3.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring3.setEndValue(1f);
										}
											break;
										case 6: {
											final View view3 = View.inflate(YiDongNianHuiActivity.this, R.layout.item6, null);
											ScreenAdapterTools.getInstance().loadView(view3);
											TextView name3 = (TextView) view3.findViewById(R.id.name);
											ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
											RelativeLayout root_rl3 = (RelativeLayout) view3.findViewById(R.id.root_rl);
											name3.setText(bean.getName());
											TextView zhiwei = (TextView) view3.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											TextView huanyinyu = (TextView) view3.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											synthesizer.speak(hyy==null?"":hyy);

											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													.error(R.drawable.erroy_bg)
													//.apply(myOptions)
													//.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang);

											rootLayout.addView(view3);

											ViewGroup.LayoutParams params3 = root_rl3.getLayoutParams();
											params3.width = dw / 3;
											root_rl3.setLayoutParams(params3);
											root_rl3.invalidate();

											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem3 = SpringSystem.create();
											final Spring spring3 = springSystem3.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring3.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view3.setScaleX(value);
													view3.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring3.setEndValue(1f);
										}
											break;
										case 7: {
											final View view3 = View.inflate(YiDongNianHuiActivity.this, R.layout.item7, null);
											ScreenAdapterTools.getInstance().loadView(view3);
											TextView name3 = (TextView) view3.findViewById(R.id.name);
											TextView huanyinyu = (TextView) view3.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
											LinearLayout root_rl3 = (LinearLayout) view3.findViewById(R.id.root_rl);
											name3.setText(bean.getName());
											TextView zhiwei = (TextView) view3.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											synthesizer.speak(hyy==null?"":hyy);
//
											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													.error(R.drawable.erroy_bg)
													//.apply(myOptions)
													//.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang);

											rootLayout.addView(view3);


											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem3 = SpringSystem.create();
											final Spring spring3 = springSystem3.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring3.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view3.setScaleX(value);
													view3.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring3.setEndValue(1f);
										}
											break;
										case 8: {
											final View view3 = View.inflate(YiDongNianHuiActivity.this, R.layout.item8, null);
											ScreenAdapterTools.getInstance().loadView(view3);
											TextView name3 = (TextView) view3.findViewById(R.id.name);
											ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
											RelativeLayout root_rl3 = (RelativeLayout) view3.findViewById(R.id.root_rl);
											name3.setText(bean.getName());
											TextView zhiwei = (TextView) view3.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											TextView huanyinyu = (TextView) view3.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											synthesizer.speak(hyy==null?"":hyy);

											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													.error(R.drawable.erroy_bg)
													//.apply(myOptions)
													//.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang);

											rootLayout.addView(view3);



											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem3 = SpringSystem.create();
											final Spring spring3 = springSystem3.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring3.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view3.setScaleX(value);
													view3.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring3.setEndValue(1f);
										}
											break;
										case 9: {
											final View view3 = View.inflate(YiDongNianHuiActivity.this, R.layout.item9, null);
											ScreenAdapterTools.getInstance().loadView(view3);
											TextView name3 = (TextView) view3.findViewById(R.id.name);
											ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
											RelativeLayout root_rl3 = (RelativeLayout) view3.findViewById(R.id.root_rl);
											name3.setText(bean.getName());
											TextView zhiwei = (TextView) view3.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											TextView huanyinyu = (TextView) view3.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											synthesizer.speak(hyy==null?"":hyy);

											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													.error(R.drawable.erroy_bg)
													//.apply(myOptions)
													//.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang);

											rootLayout.addView(view3);


											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem3 = SpringSystem.create();
											final Spring spring3 = springSystem3.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring3.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view3.setScaleX(value);
													view3.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring3.setEndValue(1f);
										}
										break;
										case 10: {
											final View view3 = View.inflate(YiDongNianHuiActivity.this, R.layout.item10, null);
											ScreenAdapterTools.getInstance().loadView(view3);
											TextView name3 = (TextView) view3.findViewById(R.id.name);
											ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
											RelativeLayout root_rl3 = (RelativeLayout) view3.findViewById(R.id.root_rl);
											name3.setText(bean.getName());
											TextView zhiwei = (TextView) view3.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											TextView huanyinyu = (TextView) view3.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											synthesizer.speak(hyy==null?"":hyy);

											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													.error(R.drawable.erroy_bg)
													//.apply(myOptions)
													.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang);

											rootLayout.addView(view3);


											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem3 = SpringSystem.create();
											final Spring spring3 = springSystem3.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring3.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view3.setScaleX(value);
													view3.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring3.setEndValue(1f);
										}
										break;
										case 11: {
											final View view3 = View.inflate(YiDongNianHuiActivity.this, R.layout.item11, null);
											ScreenAdapterTools.getInstance().loadView(view3);
											TextView name3 = (TextView) view3.findViewById(R.id.name);
											ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
											RelativeLayout root_rl3 = (RelativeLayout) view3.findViewById(R.id.root_rl);
											name3.setText(bean.getName());
											TextView zhiwei = (TextView) view3.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											TextView huanyinyu = (TextView) view3.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											synthesizer.speak(hyy==null?"":hyy);

											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													.error(R.drawable.erroy_bg)
													//.apply(myOptions)
													.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang);

											rootLayout.addView(view3);

											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem3 = SpringSystem.create();
											final Spring spring3 = springSystem3.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring3.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view3.setScaleX(value);
													view3.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring3.setEndValue(1f);
										}
										break;
										case 12: {
											final View view3 = View.inflate(YiDongNianHuiActivity.this, R.layout.item12, null);
											ScreenAdapterTools.getInstance().loadView(view3);
											TextView name3 = (TextView) view3.findViewById(R.id.name);
											ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
											RelativeLayout root_rl3 = (RelativeLayout) view3.findViewById(R.id.root_rl);
											name3.setText(bean.getName());
											TextView zhiwei = (TextView) view3.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											TextView huanyinyu = (TextView) view3.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											synthesizer.speak(hyy==null?"":hyy);

											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													.error(R.drawable.erroy_bg)
													//.apply(myOptions)
													.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang);

											rootLayout.addView(view3);



											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem3 = SpringSystem.create();
											final Spring spring3 = springSystem3.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring3.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view3.setScaleX(value);
													view3.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring3.setEndValue(1f);
										}
										break;
										case 13: {
											final View view3 = View.inflate(YiDongNianHuiActivity.this, R.layout.item13, null);
											ScreenAdapterTools.getInstance().loadView(view3);
											TextView name3 = (TextView) view3.findViewById(R.id.name);
											ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
											RelativeLayout root_rl3 = (RelativeLayout) view3.findViewById(R.id.root_rl);
											name3.setText(bean.getName());
											TextView zhiwei = (TextView) view3.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											TextView huanyinyu = (TextView) view3.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											synthesizer.speak(hyy==null?"":hyy);

											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													.error(R.drawable.erroy_bg)
													//.apply(myOptions)
													.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang);

											rootLayout.addView(view3);

											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem3 = SpringSystem.create();
											final Spring spring3 = springSystem3.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring3.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view3.setScaleX(value);
													view3.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring3.setEndValue(1f);
										}
										break;
										case 14: {
											final View view3 = View.inflate(YiDongNianHuiActivity.this, R.layout.item14, null);
											ScreenAdapterTools.getInstance().loadView(view3);
											TextView name3 = (TextView) view3.findViewById(R.id.name);
											ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
											RelativeLayout root_rl3 = (RelativeLayout) view3.findViewById(R.id.root_rl);
											name3.setText(bean.getName());
											TextView zhiwei = (TextView) view3.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											TextView huanyinyu = (TextView) view3.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											synthesizer.speak(hyy==null?"":hyy);

											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													.error(R.drawable.erroy_bg)
													//.apply(myOptions)
													.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang);

											rootLayout.addView(view3);

											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem3 = SpringSystem.create();
											final Spring spring3 = springSystem3.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring3.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view3.setScaleX(value);
													view3.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring3.setEndValue(1f);
										}
										break;
										case 15: {
											final View view3 = View.inflate(YiDongNianHuiActivity.this, R.layout.item15, null);
											ScreenAdapterTools.getInstance().loadView(view3);
											TextView name3 = (TextView) view3.findViewById(R.id.name);
											ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
											RelativeLayout root_rl3 = (RelativeLayout) view3.findViewById(R.id.root_rl);
											name3.setText(bean.getName());
											TextView zhiwei = (TextView) view3.findViewById(R.id.zhiwei);
											zhiwei.setText(bean.getBumen());
											TextView huanyinyu = (TextView) view3.findViewById(R.id.huanyinyu);
											huanyinyu.setText(hyy);
											synthesizer.speak(hyy==null?"":hyy);

											Glide.with(YiDongNianHuiActivity.this)
													//	.load(R.drawable.vvv)
													.load(touxiangPath+bean.getTouxiang())
													.error(R.drawable.erroy_bg)
													//.apply(myOptions)
													.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
													//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
													.into(touxiang);

											rootLayout.addView(view3);

											new Handler().post(new Runnable() {
												@Override
												public void run() {
													recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
												}
											});

											//动画
											SpringSystem springSystem3 = SpringSystem.create();
											final Spring spring3 = springSystem3.createSpring();
											//两个参数分别是弹力系数和阻力系数
											spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
											// 添加弹簧监听器
											spring3.addListener(new SimpleSpringListener() {
												@Override
												public void onSpringUpdate(Spring spring) {
													// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
													float value = (float) spring.getCurrentValue();
													//Log.d(TAG, "value:" + value);
													//基于Y轴的弹簧阻尼动画
													//	helper.itemView.setTranslationY(value);
													// 对图片的伸缩动画
													//float scale = 1f - (value * 0.5f);
													view3.setScaleX(value);
													view3.setScaleY(value);
												}
											});
											// 设置动画结束值
											spring3.setEndValue(1f);
										}
										break;
									}

//**************************************************************************************************************
									//底部列表的
									if (b==0){
										lingdaoList.add(bean);
										//int i2 = lingdaoList.size();
										final View view3= View.inflate(YiDongNianHuiActivity.this, R.layout.tanchuang_item7, null);
										ScreenAdapterTools.getInstance().loadView(view3);
										ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
										TextView name3 = (TextView) view3.findViewById(R.id.test2);
										TextView time = (TextView) view3.findViewById(R.id.test3);
										name3.setTypeface(typeFace1);
										time.setTypeface(typeFace1);
										name3.setText(bean.getName());
										time.setText(DateUtils.time(System.currentTimeMillis()+""));


										Glide.with(YiDongNianHuiActivity.this)
												//	.load(R.drawable.vvv)
												.load(touxiangPath+bean.getTouxiang())
												.error(R.drawable.erroy_bg)
												//.apply(myOptions)
												//.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
												.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
												.into(touxiang);

										rootLayout2.addView(view3);


										new Handler().post(new Runnable() {
											@Override
											public void run() {
												recyclerView2.fullScroll(ScrollView.FOCUS_RIGHT);
											}
										});

										//动画
										SpringSystem springSystem3 = SpringSystem.create();
										final Spring spring3 = springSystem3.createSpring();
										//两个参数分别是弹力系数和阻力系数
										spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
										// 添加弹簧监听器
										spring3.addListener(new SimpleSpringListener() {
											@Override
											public void onSpringUpdate(Spring spring) {
												// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
												float value = (float) spring.getCurrentValue();
												//Log.d(TAG, "value:" + value);
												//基于Y轴的弹簧阻尼动画
												//	helper.itemView.setTranslationY(value);
												// 对图片的伸缩动画
												//float scale = 1f - (value * 0.5f);
												view3.setScaleX(value);
												view3.setScaleY(value);
											}
										});
										// 设置动画结束值
										spring3.setEndValue(1f);
									}

									new Thread(new Runnable() {
										@Override
										public void run() {

											try {

												SystemClock.sleep(10000);
												Message message = Message.obtain();
												message.what = 999;
												handler.sendMessage(message);

											} catch (Exception e) {
												e.printStackTrace();
											}


										}
									}).start();

					}
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

			}

			else if (msg.arg1==2) {

			final WeiShiBieBean dataBean = (WeiShiBieBean) msg.obj;

						try {

//							BASE64Decoder decoder = new BASE64Decoder();
//							// Base64解码
//							final byte[][] b;
//
//							b = new byte[][]{decoder.decodeBuffer(dataBean.getFace().getImage())};
//							for (int i = 0; i < b[0].length; ++i) {
//								if (b[0][i] < 0) {// 调整异常数据
//									b[0][i] += 256;
//								}
//							}

							final TanChuangBean bean=new TanChuangBean();
							bean.setBytes(null);
							bean.setBumen("");
							bean.setId(System.currentTimeMillis());
							bean.setType(0);
							bean.setName("嘉宾");
							bean.setRemark("");
							bean.setZhiwei("");
							bean.setGonghao("");
							bean.setTouxiang(null);

							yuangongList.add(bean);

							final View view1 = View.inflate(YiDongNianHuiActivity.this, R.layout.item2, null);
							ScreenAdapterTools.getInstance().loadView(view1);
							TextView name1 = (TextView) view1.findViewById(R.id.name);
							ImageView touxiang1 = (ImageView) view1.findViewById(R.id.touxiang);
							RelativeLayout root_rl1 = (RelativeLayout) view1.findViewById(R.id.root_rl);
							name1.setText(bean.getName());
							TextView zhiwei = (TextView) view1.findViewById(R.id.zhiwei);
							zhiwei.setText(bean.getBumen());
							TextView huanyinyu = (TextView) view1.findViewById(R.id.huanyinyu);
							huanyinyu.setText("欢迎参加成立大会");
							synthesizer.speak("欢迎参加成立大会");
							rootLayout.addView(view1);

							Glide.with(YiDongNianHuiActivity.this)
									//	.load(R.drawable.vvv)
									.load(dataBean.getTrack2())
									.error(R.drawable.thetwo)
									//.apply(myOptions)
									.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
									//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
									.into(touxiang1);


//											ViewGroup.LayoutParams params1 = root_rl1.getLayoutParams();
//											params1.height = dh / 5;
//											root_rl1.setLayoutParams(params1);
//											root_rl1.invalidate();

							new Handler().post(new Runnable() {
								@Override
								public void run() {
									recyclerView.fullScroll(ScrollView.FOCUS_DOWN);
								}
							});

							//动画
							SpringSystem springSystem1 = SpringSystem.create();
							final Spring spring1 = springSystem1.createSpring();
							//两个参数分别是弹力系数和阻力系数
							spring1.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
							// 添加弹簧监听器
							spring1.addListener(new SimpleSpringListener() {
								@Override
								public void onSpringUpdate(Spring spring) {
									// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
									float value = (float) spring.getCurrentValue();
									//Log.d(TAG, "value:" + value);
									//基于Y轴的弹簧阻尼动画
									//	helper.itemView.setTranslationY(value);
									// 对图片的伸缩动画
									//float scale = 1f - (value * 0.5f);
									view1.setScaleX(value);
									view1.setScaleY(value);
								}
							});
							// 设置动画结束值
							spring1.setEndValue(1f);

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
							new Thread(new Runnable() {
								@Override
								public void run() {

									try {

										SystemClock.sleep(10000);
										Message message = Message.obtain();
										message.what = 999;
										handler.sendMessage(message);

									} catch (Exception e) {
										e.printStackTrace();
									}


								}
							}).start();

						} catch (Exception e) {

							Log.d(TAG, e.getMessage() + "陌生人解码");
						}


			}

			return false;
		}
	});



	@Override
	public void reset() {

		//数据重置
		chongzhi();
	}



	private void chongzhi(){
		//yuangongList.clear();
		//tanchuangList.clear();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						yuangongList.clear();
						lingdaoList.clear();

//						TanChuangBean bean=new TanChuangBean();
//						bean.setName("");
//						bean.setIsLight(false);
//						bean.setBytes(null);
//						bean.setTouxiang(null);
//						bean.setType(-33);
//						yuangongList.add(bean);

//						if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE || (!recyclerView.isComputingLayout())) {
//							adapter.notifyDataSetChanged();
//						}
					}
				});

			}
		}).start();

	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//	Log.d(TAG, "创建111");

		benDiRenShuBeanDao=MyApplication.myApplication.getDaoSession().getBenDiRenShuBeanDao();
		benDiRenShuBean=benDiRenShuBeanDao.load(123456L);
		benDiMBbeanDao=MyApplication.myApplication.getDaoSession().getBenDiMBbeanDao();
		 mbLeiXingBeanList= benDiMBbeanDao.loadAll();

		if (benDiRenShuBean==null){
			BenDiRenShuBean ddd=new BenDiRenShuBean();
			ddd.setId(123456L);
			ddd.setN1(0);
			ddd.setNShen(0);
			ddd.setNShi(0);
			ddd.setNTeyao(0);
			ddd.setY1(0);
			ddd.setYShen(0);
			ddd.setYShi(0);
			ddd.setYTeyao(0);
			benDiRenShuBeanDao.insert(ddd);
			benDiRenShuBean=benDiRenShuBeanDao.load(123456L);
		}

		benDiQianDaoDao=MyApplication.myApplication.getDaoSession().getBenDiQianDaoDao();
		qianDaoIdDao=MyApplication.myApplication.getDaoSession().getQianDaoIdDao();
		//tanChuangBeanDao=MyApplication.myApplication.getDaoSession().getTanChuangBeanDao();
		baoCunBeanDao = MyApplication.myApplication.getDaoSession().getBaoCunBeanDao();
		zhuJiBeanHDao = MyApplication.myApplication.getDaoSession().getZhuJiBeanHDao();
		huiYiIDDao=MyApplication.myApplication.getDaoSession().getHuiYiIDDao();
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
		dw = Utils.getDisplaySize(YiDongNianHuiActivity.this).x;
		dh = Utils.getDisplaySize(YiDongNianHuiActivity.this).y;

		setContentView(R.layout.yidongnianhuiactivity);
		//ScreenAdapterTools.getInstance().reset(this);//如果希望android7.0分屏也适配的话,加上这句
		//在setContentView();后面加上适配语句
		ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

		rootLayout= (LinearLayout) findViewById(R.id.root_layout);
		rootLayout2= (LinearLayout) findViewById(R.id.root_layout2);



	//	LottieAnimationView vv= (LottieAnimationView) findViewById(R.id.animation_view);
		// 任何符合颜色过滤界面的类
	//	final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN);
		// 在整个视图中添加一个颜色过滤器
		//vv.addColorFilter(colorFilter);
//		//在特定的图层中添加一个颜色滤镜
//		vv.addColorFilterToLayer("hello_layer", colorFilter);
//		// 添加一个彩色过滤器特效“hello_layer”上的内容
//		vv.addColorFilterToContent("hello_layer", "hello", colorFilter);

		dabg= (ImageView) findViewById(R.id.dabg);
		wangluo = (LottieAnimationView) findViewById(R.id.wangluo);
		wangluo.setSpeed(1.8f);
		typeFace1 = Typeface.createFromAsset(getAssets(), "fonts/xk.TTF");


		y1= findViewById(R.id.y1);
		y1.setCharacterLists(TickerUtils.provideNumberList());
		y1.setAnimationDuration(1500);
		y1.setAnimationInterpolator(new OvershootInterpolator());

		String str = String.format("%04d", 0);
		char s1[]=str.toCharArray();
		StringBuilder cc=new StringBuilder();
		cc.append(" ");
		for (char c:s1){
			cc.append(String.valueOf(c)).append(" ");
		}
		y1.setText(cc.toString());


		lingdaoList=new Vector<>();
		yuangongList = new Vector<>();

		Button button = (Button) findViewById(R.id.dddk);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chongzhi();

				startActivity(new Intent(YiDongNianHuiActivity.this, SheZhiActivity.class));
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
		recyclerView = (ScrollView) findViewById(R.id.scrollView);
		recyclerView2 = (HorizontalScrollView) findViewById(R.id.recyclerView2);

//		manager = new WrapContentLinearLayoutManager(YiDongNianHuiActivity.this,LinearLayoutManager.VERTICAL,false,this);
//		recyclerView.setLayoutManager(manager);

//		manager2 = new WrapContentLinearLayoutManager(YiDongNianHuiActivity.this,LinearLayoutManager.HORIZONTAL,false,this);
//		//recyclerView2.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false));
//		recyclerView2.setLayoutManager(manager2);
		//recyclerView.addItemDecoration(new MyDecoration(VlcVideoActivity.this, LinearLayoutManager.VERTICAL,20,R.color.transparent));

//		adapter = new MyAdapter(YiDongNianHuiActivity.this, yuangongList);
//		recyclerView.setAdapter(adapter);

//		adapter2 = new MyAdapter2(R.layout.tanchuang_item7, lingdaoList);
//		recyclerView2.setAdapter(adapter2);


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

//		RelativeLayout.LayoutParams  params= (RelativeLayout.LayoutParams) recyclerView2.getLayoutParams();
//		params.height=dh/10;
//		recyclerView2.setLayoutParams(params);
//		recyclerView2.invalidate();

//		int si=dw/6;
		//Log.d(TAG, "si:" + si);
		RelativeLayout.LayoutParams  params2= (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
		params2.topMargin=dh/13;
		//params2.height=(dh*7)/10-60;
		recyclerView.setLayoutParams(params2);
		recyclerView.invalidate();

	//	link_login();

		new Thread(new Runnable() {
			@Override
			public void run() {

				SystemClock.sleep(10000);
				sendBroadcast(new Intent(YiDongNianHuiActivity.this,AlarmReceiver.class));
			//	synthesizer.speak("吃饭了吗");

			}
		}).start();

//		if (baoCunBean.getHoutaiDiZhi()!=null && !baoCunBean.getHoutaiDiZhi().equals("") && baoCunBean.getZhanghuId()!=null && !baoCunBean.getZhanghuId().equals("") && baoCunBean.getHuiyiId()!=null && !baoCunBean.getHuiyiId().equals("")){
//			//link_login();
//		}




//		File logFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +"qiandao.txt");
//		// Make sure log file is exists
//		if (!logFile.exists()) {
//
//			try {
//				logFile.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//
//			}
//
//		}
//
//		FileOutputStream outputStream;
//
//		try {
//			outputStream = openFileOutput(logFile.getName(), Context.MODE_APPEND);
//			outputStream.write(builder.toString().getBytes());
//			outputStream.flush();
//			outputStream.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		View view = View.inflate(YiDongNianHuiActivity.this,R.layout.item0,null);
		ScreenAdapterTools.getInstance().loadView(view);
		view.setTag("123");
		rootLayout.addView(view);
		View view1 =view.findViewWithTag("123");
		view1.setVisibility(View.INVISIBLE);


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

	/**
	 * 合成的参数，可以初始化时填写，也可以在合成前设置。
	 *
	 * @return
	 */
	protected Map<String, String> getParams() {
		Map<String, String> params = new HashMap<String, String>();
		// 以下参数均为选填
		params.put(SpeechSynthesizer.PARAM_SPEAKER, baoCunBean.getBoyingren()+""); // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
		params.put(SpeechSynthesizer.PARAM_VOLUME, "8"); // 设置合成的音量，0-9 ，默认 5
		params.put(SpeechSynthesizer.PARAM_SPEED, baoCunBean.getYusu()+"");// 设置合成的语速，0-9 ，默认 5
		params.put(SpeechSynthesizer.PARAM_PITCH, baoCunBean.getYudiao()+"");// 设置合成的语调，0-9 ，默认 5
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




//	private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
//
//		private LayoutInflater layoutInflater;
//		private Context context;
//		private List<TanChuangBean> contents;
//
//		private MyAdapter(Context c, List<TanChuangBean> strs){
//			layoutInflater = LayoutInflater.from(c);
//			context = c;
//			contents = strs;
//		}
//
//		@Override
//		public int getItemCount() {
//
//			return contents != null ? contents.size() : 0;
//		}
//
//		@Override
//		public int getItemViewType(int position) {
//			//Log.d(TAG, contents.get(position).getBumen()+"gggggggggggggggg");
//			if (mbLeiXingBeanList!=null){
//				int size=mbLeiXingBeanList.size();
//				for (int i=0;i<size;i++){
//					if (contents.get(position).getBumen()!=null && contents.get(position).getBumen().equals(mbLeiXingBeanList.get(i).getSubType())){
//						//什么身份返回什么类型
//						Log.d(TAG, "getNum(7):" + getNum(7));
//						return getNum(7);
//
//					}
//
//				}
//			}
//			return getNum(7);
//		//	return contents.get(position).getType() == 0 ? ITEM_TYPE.ITEM_TYPE_LEFT.ordinal() : ITEM_TYPE.ITEM_TYPE_RIGHT.ordinal() ;
//		}
//
//		@Override
//		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//			Log.d(TAG, "viewType:" + viewType);
//			RecyclerView.ViewHolder holder=null;
//			//固定的几种模版
//			switch (viewType){
//
//				case 0:
//					holder= new ViewHolder0(layoutInflater.inflate(R.layout.item0,parent,false));
//					break;
//				case 1:
//					holder= new ViewHolder1(layoutInflater.inflate(R.layout.item1,parent,false));
//					break;
//				case 2:
//					holder= new ViewHolder2(layoutInflater.inflate(R.layout.item2,parent,false));
//					break;
//				case 3:
//					holder= new ViewHolder3(layoutInflater.inflate(R.layout.item3,parent,false));
//					break;
//				case 4:
//					holder= new ViewHolder4(layoutInflater.inflate(R.layout.item4,parent,false));
//					break;
//				case 5:
//					holder= new ViewHolder5(layoutInflater.inflate(R.layout.item5,parent,false));
//					break;
//				case 6:
//					holder= new ViewHolder6(layoutInflater.inflate(R.layout.item6,parent,false));
//					break;
//				case 7:
//					holder= new ViewHolder7(layoutInflater.inflate(R.layout.item7,parent,false));
//					break;
//
//					default:
//						holder= new ViewHolder0(layoutInflater.inflate(R.layout.item0,parent,false));
//
//			}
//			return holder;
//
//		}
//
//		@Override
//		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//			if(holder instanceof ViewHolder0){
//
//
//				((ViewHolder0) holder).name0.setText(contents.get(position).getName());
//				((ViewHolder0) holder).zhiwei.setText(contents.get(position).getBumen());
//				int s=mbLeiXingBeanList.size();
//				for (int i=0;i<s;i++){
//					if (contents.get(position).getBumen()!=null && mbLeiXingBeanList.get(i).getSubType()!=null && contents.get(position).getBumen().equals(mbLeiXingBeanList.get(i).getSubType())){
//						//获取本地保存的欢迎语
//						//设置语音
//						((ViewHolder0) holder).huanyinyu.setText(contents.get(position).getName());
//					}
//				}
//
//
//				Log.d(TAG, "ViewHolder0");
//
//			}else if(holder instanceof  ViewHolder1){
//				Log.d(TAG, "ViewHolder1");
//
//				((ViewHolder1) holder).name1.setText(contents.get(position).getName());
//
//			}else if (holder instanceof  ViewHolder2){
//				Log.d(TAG, "ViewHolder2");
//
//				((ViewHolder2) holder).name2.setText(contents.get(position).getName());
//
//			}else if (holder instanceof  ViewHolder3){
//				Log.d(TAG, "ViewHolder3");
//				((ViewHolder3) holder).name3.setText(contents.get(position).getName());
//
//			}else if (holder instanceof  ViewHolder4){
//				Log.d(TAG, "ViewHolder4");
//				((ViewHolder4) holder).name4.setText(contents.get(position).getName());
//
//			}else if (holder instanceof  ViewHolder5){
//				Log.d(TAG, "ViewHolder5");
//				((ViewHolder5) holder).name5.setText(contents.get(position).getName());
//
//			}else if (holder instanceof  ViewHolder6){
//				Log.d(TAG, "ViewHolder6");
//				((ViewHolder6) holder).name6.setText(contents.get(position).getName());
//
//			}else if (holder instanceof  ViewHolder7){
//				Log.d(TAG, "ViewHolder7");
//				((ViewHolder7) holder).name7.setText(contents.get(position).getName());
//
//			}
//
//		}
//
//		private   class ViewHolder0 extends RecyclerView.ViewHolder{
//
//			private TextView name0,huanyinyu,zhiwei;
//			private ViewHolder0(View v){
//				super(v);
//				name0 = (TextView)v.findViewById(R.id.name);
//				huanyinyu = (TextView)v.findViewById(R.id.huanyinyu);
//				zhiwei = (TextView)v.findViewById(R.id.zhiwei);
//			}
//		}
//
//		private  class ViewHolder1 extends RecyclerView.ViewHolder{
//
//			private TextView name1;
//			private ViewHolder1(View v){
//				super(v);
//				name1 = (TextView)v.findViewById(R.id.name);
//			}
//		}
//
//		private  class ViewHolder2 extends RecyclerView.ViewHolder{
//
//			private TextView name2;
//			private ViewHolder2(View v){
//				super(v);
//				name2 = (TextView)v.findViewById(R.id.name);
//			}
//		}
//
//		private  class ViewHolder3 extends RecyclerView.ViewHolder{
//
//			private TextView name3;
//			private ViewHolder3(View v){
//				super(v);
//				name3 = (TextView)v.findViewById(R.id.name);
//			}
//		}
//
//		private  class ViewHolder4 extends RecyclerView.ViewHolder{
//
//			private TextView name4;
//			private ViewHolder4(View v){
//				super(v);
//				name4 = (TextView)v.findViewById(R.id.name);
//			}
//		}
//
//		private  class ViewHolder5 extends RecyclerView.ViewHolder{
//
//			private TextView name5;
//			private ViewHolder5(View v){
//				super(v);
//				name5 = (TextView)v.findViewById(R.id.name);
//			}
//		}
//
//		private  class ViewHolder6 extends RecyclerView.ViewHolder{
//
//			private TextView name6;
//			private ViewHolder6(View v){
//				super(v);
//				name6 = (TextView)v.findViewById(R.id.name);
//			}
//		}
//
//		private  class ViewHolder7 extends RecyclerView.ViewHolder{
//
//			private TextView name7;
//			private ViewHolder7(View v){
//				super(v);
//				name7 = (TextView)v.findViewById(R.id.name);
//			}
//		}
//
//
//	}

//	//学生跟陌生人
//	public  class MyAdapter extends BaseQuickAdapter<TanChuangBean,BaseViewHolder> {
//		//private RequestOptions myOptions = null;
//		//private RequestOptions myOptions2 = null;
//
//		private MyAdapter(int layoutResId, List<TanChuangBean> data) {
//			super(layoutResId, data);
////			 myOptions = new RequestOptions()
////					.circleCrop()
////					.diskCacheStrategy(DiskCacheStrategy.NONE);
////			myOptions2 = new RequestOptions()
////					.circleCrop();
//		}
//
//
//		@Override
//		protected void convert(final BaseViewHolder helper, TanChuangBean item) {
//
//			try {
//			ImageView imageView= helper.getView(R.id.touxiang);
//			TextView name=helper.getView(R.id.name33);
//			LottieAnimationView lottieAnimationView=helper.getView(R.id.dagou);
//			TextView zhuangtai=helper.getView(R.id.zhuangtai33);
//			RelativeLayout toprl=helper.getView(R.id.ffflll);
////			LinearLayout rl=helper.getView(R.id.zi_ll);
//			String bumen=item.getBumen();
//			name.setTypeface(typeFace1);
//			zhuangtai.setTypeface(typeFace1);
//			zhuangtai.setTextColor(Color.WHITE);
//			name.setTextColor(Color.WHITE);
//
//
//			if (helper.getAdapterPosition()==0 ){
//			// 	rl.setBackgroundColor(Color.parseColor("#00000000"));
//				toprl.setBackgroundColor(Color.parseColor("#00000000"));
//				imageView.setBackgroundColor(Color.parseColor("#00000000"));
//				imageView.setImageBitmap(null);
//				name.setText("");
//				zhuangtai.setText("");
//				lottieAnimationView.setVisibility(View.GONE);
//				//弹窗的高宽
//				RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) toprl.getLayoutParams();
//				lp.width = dw - 20;
//				lp.leftMargin = 10;
//				lp.height = ((dh * 7) / 10) / 3;
//				toprl.setLayoutParams(lp);
//				toprl.invalidate();
//			}else {
//				//Log.d(TAG, "jinlai");
//				switch (item.getType()) {
//					case -1:
//						//陌生人
//						synthesizer.speak("来宾未登记");
//						imageView.setBackgroundColor(Color.parseColor("#00000000"));
//						toprl.setBackgroundResource(R.drawable.msr_bg2);
//						zhuangtai.setTextColor(Color.RED);
//						name.setTextColor(Color.RED);
//
//						if (huanYinYuBeanDao.load(6L)!=null){
//							String s=huanYinYuBeanDao.load(6L).getHuanyinci();
//							if (!s.equals("")){
//								//String  s1=s.replace("#@",item.getName());
////								synthesizer.speak(s1);
//								name.setText(s);
//								zhuangtai.setText("");
//
//							}else{
////								synthesizer.speak("你身份未登记,请到会议签到处签到");
//								name.setText("来宾未登记");
//								zhuangtai.setText("");
//							}
//						}else {
////							synthesizer.speak("你身份未登记,请到会议签到处签到");
//							name.setText("来宾未登记");
//							zhuangtai.setText("");
//						}
//
//
//					//	mSpeechSynthesizer.speak("陌生人进入,请保安尽快到现场或短信保安预警");
//
//						break;
//					case 0:
//						//员工
//						//name.setText(item.getName());
//						switch (bumen){
//							case "0":
//								//synthesizer.speak("欢迎光临");
//								//后台0是代表 当做普通内部员工
//								//我保存的欢迎语 3 是代表
//								imageView.setBackgroundColor(Color.parseColor("#00000000"));
//								toprl.setBackgroundResource(R.drawable.yuangong2);
//								if (huanYinYuBeanDao.load(1L)!=null){
//									String s=huanYinYuBeanDao.load(1L).getHuanyinci();
//									if (!s.equals("")){
//										String  s1=s.replace("#@",item.getName()).replace("%*",item.getZhiwei());
//									//	synthesizer.speak(s1);
//										name.setText(s1);
//									}else{
//										name.setText("未设置内部员工欢迎语");
//									//	synthesizer.speak("欢迎代表莅临指导");
//									}
//								}else {
//									name.setText("未设置内部员工欢迎语");
//									//synthesizer.speak("欢迎代表莅临指导");
//								}
//
//								break;
//							case "1":
//								//嘉宾
//							//	synthesizer.speak("欢迎光临");
//								// 2 嘉宾 普通嘉宾
//								imageView.setBackgroundColor(Color.parseColor("#00000000"));
//								toprl.setBackgroundResource(R.drawable.jiabing_bg);
//								if (huanYinYuBeanDao.load(2L)!=null){
//									String s=huanYinYuBeanDao.load(2L).getHuanyinci();
//									if (!s.equals("")){
//										String  s1=s.replace("#@",item.getName()).replace("%*",item.getZhiwei());
//										//synthesizer.speak(s1);
//										name.setText(s1);
//									}else{
//										name.setText("未设置普通嘉宾欢迎语");
//										//synthesizer.speak("欢迎嘉宾莅临指导");
//									}
//								}else {
//									name.setText("未设置普通嘉宾欢迎语");
//									//synthesizer.speak("欢迎嘉宾莅临指导");
//								}
//
//								break;
//							case "2":
//								//1内部领导
//								//我保存的欢迎语1是领导
//								toprl.setBackgroundResource(R.drawable.liaoning1_03);
//								imageView.setBackgroundResource(R.drawable.yuanquan);
//								if (huanYinYuBeanDao.load(3L)!=null){
//									String s=huanYinYuBeanDao.load(3L).getHuanyinci();
//									if (!s.equals("")){
//										String  s1=s.replace("#@",item.getName()).replace("%*",item.getZhiwei());
//										synthesizer.speak(s1);
//										name.setText(s1);
//									}else{
//										name.setText("未设置内部领导欢迎语");
//									//	synthesizer.speak("欢迎领导莅临指导");
//									}
//								}else {
//									name.setText("未设置内部领导欢迎语");
//								//	synthesizer.speak("欢迎领导莅临指导");
//								}
//								break;
//							case "3":
//								//工作人员
//								//4工作人员
////								toprl.setBackgroundResource(R.drawable.lingdao_bg);
////								imageView.setBackgroundResource(R.drawable.yuanquan);
////								if (huanYinYuBeanDao.load(4L)!=null){
////									String s=huanYinYuBeanDao.load(4L).getHuanyinci();
////									if (!s.equals("")){
////										String  s1=s.replace("#@",item.getName());
////										//synthesizer.speak(s1);
////										zhuangtai.setText(s1);
////									}else{
////										zhuangtai.setText("签到成功");
////										//synthesizer.speak("签到成功");
////									}
////								}else {
////									zhuangtai.setText("签到成功");
////									//synthesizer.speak("签到成功");
////								}
//								break;
//							case "4":
//								//合作伙伴
//								//5外部领导
//								toprl.setBackgroundResource(R.drawable.liaoning1_03);
//								imageView.setBackgroundResource(R.drawable.yuanquan);
//								if (huanYinYuBeanDao.load(4L)!=null){
//									String s=huanYinYuBeanDao.load(4L).getHuanyinci();
//									if (!s.equals("")){
//										String  s1=s.replace("#@",item.getName()).replace("%*",item.getZhiwei());
//										synthesizer.speak(s1);
//										name.setText(s1);
//									}else{
//										name.setText("未设置外部领导欢迎语");
//										//synthesizer.speak("签到成功");
//									}
//								}else {
//									name.setText("未设置外部领导欢迎语");
//									//synthesizer.speak("签到成功");
//								}
//								break;
//							case "5":
//								//陌生人
//
//
//								break;
//
//						}
//
//						lottieAnimationView.setSpeed(0.5f);
//						lottieAnimationView.playAnimation();
//						lottieAnimationView.setVisibility(View.VISIBLE);
//
//						break;
//					case 1:
//						//访客
//						//toprl.setBackgroundResource(R.drawable.zidonghuoqu15);
////						name.setTypeface(typeFace1);
////						zhuangtai.setTypeface(typeFace1);
////						name.setText(item.getName());
////						zhuangtai.setText("返校报道成功");
//
//						//richeng.setText("");
//						//name.setText(item.getName());
//						//autoScrollTextView.setText("欢迎你来本公司参观指导。");
//						break;
//					case 2:
////						name.setTypeface(typeFace1);
////						zhuangtai.setTypeface(typeFace1);
////						name.setText(item.getName());
//					//	zhuangtai.setText("返校报道成功");
//
//						//VIP访客
//						//	toprl.setBackgroundResource(R.drawable.ms_bg);
//						//	richeng.setText("");
//						//	name.setText(item.getName());
//						//autoScrollTextView.setText("欢迎VIP访客 "+item.getName()+" 来本公司指导工作。");
//						break;
//				}
//				if (item.getTouxiang()!=null){
//
//					Glide.with(MyApplication.getAppContext())
//							//.load(zhuji+item.getTouxiang())
//								.load(item.getTouxiang())
//							//.apply(myOptions2)
//							.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
//						//	.transform(new GlideRoundTransform(MyApplication.getAppContext(), 6))
//							.into((ImageView) helper.getView(R.id.touxiang));
//				}else {
//					Glide.with(MyApplication.getAppContext())
//							.load(item.getBytes())
//							//.apply(myOptions)
//							.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
//							//	.transform(new GlideRoundTransform(MyApplication.getAppContext(), 6))
//							.into((ImageView) helper.getView(R.id.touxiang));
//				}
//
//			}
//
//			RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
//			if (item.getType()==-1){
//				zhuangtai.setVisibility(View.GONE);
//				name.setTextSize(54);
//				//头像的高宽
//				lp2.topMargin=-10;
//				lp2.width=dw/3-130;
//				lp2.height=dw/3-130;
//				imageView.setLayoutParams(lp2);
//				imageView.invalidate();
//				//弹窗的高宽
//				RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) toprl.getLayoutParams();
//				lp.width=dw-20;
//				lp.leftMargin=10;
//				lp.height=((dh*7)/10)/3-40;
//				toprl.setLayoutParams(lp);
//				toprl.invalidate();
//			}else {
//				zhuangtai.setVisibility(View.VISIBLE);
//				name.setTextSize(80);
//
//				if (bumen.equals("0") || bumen.equals("1")){
//					//工作人员，合作伙伴
//					// 头像的高宽
//					imageView.setPadding(60,60,60,60);
//					lp2.topMargin=0;
//					lp2.leftMargin=-2;
//					lp2.width = ((dh*7)/10)/3-60;
//					lp2.height = ((dh*7)/10)/3-60;
//					imageView.setLayoutParams(lp2);
//					imageView.invalidate();
//					//弹窗的高宽
//					RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) toprl.getLayoutParams();
//					lp.width=dw-20;
//					lp.leftMargin=10;
//					lp.height=((dh*7)/10)/3-60;
//					toprl.setLayoutParams(lp);
//					toprl.invalidate();
//
//				}else {
//					// 头像的高宽
//					imageView.setPadding(30,30,30,30);
//					lp2.topMargin = ((((dh * 7) / 10) / 3 ) - (dw / 3 + 20)) / 2;
//					lp2.width = dw / 3 + 20;
//					lp2.leftMargin=2;
//					lp2.height = dw / 3 + 20;
//					imageView.setLayoutParams(lp2);
//					imageView.invalidate();
//					//弹窗的高宽
//					RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) toprl.getLayoutParams();
//					lp.width = dw - 20;
//					lp.leftMargin = 10;
//					lp.height = ((dh * 7) / 10) / 3;
//					toprl.setLayoutParams(lp);
//					toprl.invalidate();
//				}
//			}
////				//字
////			RelativeLayout.LayoutParams zi = (RelativeLayout.LayoutParams) rl.getLayoutParams();
////				if (item.getType()==-1){
////					zi.leftMargin=dw/3-160;
////
////				}else {
////					zi.leftMargin=dw/3-80;
////				}
////				rl.setLayoutParams(zi);
////				rl.invalidate();
//
//			//弹窗的高宽
//
//
//			SpringSystem springSystem = SpringSystem.create();
//			final Spring spring = springSystem.createSpring();
//			//两个参数分别是弹力系数和阻力系数
//			spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
//			// 添加弹簧监听器
//			spring.addListener(new SimpleSpringListener() {
//
//				@Override
//				public void onSpringUpdate(Spring spring) {
//					// value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
//					float value = (float) spring.getCurrentValue();
//					//Log.d(TAG, "value:" + value);
//					//基于Y轴的弹簧阻尼动画
//					//	helper.itemView.setTranslationY(value);
//
//					// 对图片的伸缩动画
//					//float scale = 1f - (value * 0.5f);
//					helper.itemView.setScaleX(value);
//					helper.itemView.setScaleY(value);
//				}
//			});
//
//				// 设置动画结束值
//				spring.setEndValue(1f);
//			}catch (Exception e){
//				Log.d(TAG, e.getMessage()+"");
//			}
//		}
//
//	}


//	//领导
//	private  class MyAdapter2 extends BaseQuickAdapter<TanChuangBean,BaseViewHolder> {
//		//private RequestOptions myOptions = null;
//
//		private MyAdapter2(int layoutResId, List<TanChuangBean> data) {
//			super(layoutResId, data);
//			//myOptions = new RequestOptions();
//			//myOptions.transform(new GrayscaleTransformation(this));
//		}
//
//
//		@Override
//		protected void convert(final BaseViewHolder helper, TanChuangBean item) {
//
//
//			RelativeLayout toprl= helper.getView(R.id.ffflll);
//			TextView t1=helper.getView(R.id.test);
//			TextView t2=helper.getView(R.id.test2);
//			TextView t3=helper.getView(R.id.test3);
//			t1.setTypeface(typeFace1);
//			t1.setText("学号");
//			t2.setTypeface(typeFace1);
//			t2.setText(item.getName());
//			t3.setTypeface(typeFace1);
//			t3.setText(DateUtils.time(System.currentTimeMillis()+""));
//
//			ImageView imageView=helper.getView(R.id.touxiang);
//
//			//tt.setText(item.getName());
////
////				switch (item.getType()){
////					case -1:
////						//陌生人
////						//	toprl.setBackgroundResource(R.drawable.tanchuang);
////
////
////						break;
////					case 0:
////						//员工
////						toprl.setBackgroundResource(R.color.tabtextcolo);
////						String sa1="热烈欢迎"+item.getName()+"莅临参观指导";
////						StringBuilder sb1=new StringBuilder();
////						for(int i=0;i<sa1.length();i++){
////							sb1.append((sa1.charAt(i)));//依次加入sb中
////							if((i+1)%(8)==0 &&((i+1)!=sa1.length())){
////								sb1.append("\n");
////							}
////						}
////
////						break;
////
////					case 1:
////						//访客
////
////						toprl.setBackgroundResource(R.color.tabtextcolo);
////						String sa="热烈欢迎"+item.getName()+"莅临参观指导";
////						StringBuilder sb=new StringBuilder();
////						for(int i=0;i<sa.length();i++){
////							sb.append((sa.charAt(i)));//依次加入sb中
////							if((i+1)%(8)==0 &&((i+1)!=sa.length())){
////								sb.append("\n");
////							}
////						}
////
////
////						break;
////					case 2:
////						//VIP访客
////						toprl.setBackgroundResource(R.color.tabtextcolo);
////						String sa2="热烈欢迎"+item.getName()+"莅临参观指导";
////						StringBuilder sb2=new StringBuilder();
////						for(int i=0;i<sa2.length();i++){
////							sb2.append((sa2.charAt(i)));//依次加入sb中
////							if((i+1)%(8)==0 &&((i+1)!=sa2.length())){
////								sb2.append("\n");
////							}
////						}
////
////
////						break;
////
////				}
////					case 18:
////						GPUImage	gpuImage18 = new GPUImage(VlcVideoActivity.this);
////						gpuImage18.setImage(BitmapFactory.decodeResource(getResources(),R.drawable.a18));
////						gpuImage18.setFilter(new GPUImageBrightnessFilter(-0.6f));
////						Bitmap bitmap18 = gpuImage18.getBitmapWithFilterApplied();
////						imageView.setImageBitmap(bitmap18);
////					//	imageView.setImageResource(R.drawable.a18);
//////						Glide.with(MyApplication.getAppContext())
//////								.load(R.drawable.a18)
//////								//.load("http://121.46.3.20"+item.getTouxiang())
//////								//.apply(myOptions)
//////								//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
//////								//	.bitmapTransform(new GrayscaleTransformation(VlcVideoActivity.this))
//////								.into(imageView);
////						break;
////					case 19:
////						imageView.setImageResource(R.drawable.a19);
//////						Glide.with(MyApplication.getAppContext())
//////								.load(R.drawable.a19)
//////								//.load("http://121.46.3.20"+item.getTouxiang())
//////								//.apply(myOptions)
//////								//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
//////								//	.bitmapTransform(new GrayscaleTransformation(VlcVideoActivity.this))
//////								.into(imageView);
////						break;
////
////
////				}
////
//				if (item.getTouxiang()!=null ){
//					if (item.getTouxiang()!=null){
//					//	Log.d(TAG, baoCunBean.getTouxiangzhuji() + item.getTouxiang());
//						Glide.with(MyApplication.getAppContext())
//								//	.load(R.drawable.vvv)
//								.load(item.getTouxiang())
//							//	.load(zhuji+item.getTouxiang())
//								//.apply(myOptions)
//								.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
//							//	.bitmapTransform(new BrightnessFilterTransformation(YiZhongYanShiActivity.this,-0.7f))
//								//.bitmapTransform(new GrayscaleTransformation(VlcVideoActivity.this))
//								.into(imageView);
//					}else {
//						Glide.with(MyApplication.getAppContext())
//								.load(R.drawable.zidonghuoqu1)
//								//.load("http://121.46.3.20"+item.getTouxiang())
//								//.apply(myOptions)
//								.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
//								//	.bitmapTransform(new GrayscaleTransformation(VlcVideoActivity.this))
//								.into(imageView);
//					}
//				}
//
//			RelativeLayout.LayoutParams  ll= (RelativeLayout.LayoutParams) imageView.getLayoutParams();
//			ll.width=(dw/12);
//			ll.height=(dw/12);
//			imageView.setLayoutParams(ll);
//			imageView.invalidate();
//
////			RecyclerView.LayoutParams  ll2= (RecyclerView.LayoutParams) toprl.getLayoutParams();
////			ll2.height=(dw/13)+10;
////			toprl.setLayoutParams(ll2);
////			toprl.invalidate();
//
//			}
//	}

//	/**
//	 * 生成二维码
//	 * @param string 二维码中包含的文本信息
//	 * @param mBitmap logo图片
//	 * @param format  编码格式
//	 * [url=home.php?mod=space&uid=309376]@return[/url] Bitmap 位图
//	 * @throws WriterException
//	 */
//	private static final int IMAGE_HALFWIDTH = 1;//宽度值，影响中间图片大小
//	public Bitmap createCode(String string,Bitmap mBitmap, BarcodeFormat format)
//			throws WriterException {
//		Matrix m = new Matrix();
//		float sx = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getWidth();
//		float sy = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getHeight();
//		m.setScale(sx, sy);//设置缩放信息
//		//将logo图片按martix设置的信息缩放
//		mBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
//				mBitmap.getWidth(), mBitmap.getHeight(), m, false);
//		MultiFormatWriter writer = new MultiFormatWriter();
//		Hashtable<EncodeHintType, String> hst = new Hashtable<EncodeHintType, String>();
//		hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");//设置字符编码
//		BitMatrix matrix = writer.encode(string, format, 600, 600, hst);//生成二维码矩阵信息
//		int width = matrix.getWidth();//矩阵高度
//		int height = matrix.getHeight();//矩阵宽度
//		int halfW = width/2;
//		int halfH = height/2;
//		int[] pixels = new int[width * height];//定义数组长度为矩阵高度*矩阵宽度，用于记录矩阵中像素信息
//		for (int y = 0; y < height; y++) {//从行开始迭代矩阵
//			for (int x = 0; x < width; x++) {//迭代列
//				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
//						&& y > halfH - IMAGE_HALFWIDTH
//						&& y < halfH + IMAGE_HALFWIDTH) {//该位置用于存放图片信息
//			//记录图片每个像素信息
//					pixels[y * width + x] = mBitmap.getPixel(x - halfW
//							+ IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);              } else {
//					if (matrix.get(x, y)) {//如果有黑块点，记录信息
//						pixels[y * width + x] = 0xff000000;//记录黑块信息
//					}
//				}
//			}
//		}
//		Bitmap bitmap = Bitmap.createBitmap(width, height,
//				Bitmap.Config.ARGB_8888);
//		// 通过像素数组生成bitmap
//		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//		return bitmap;
//	}
//	private class MyReceiverFile  extends BroadcastReceiver {
//
//		@Override
//		public void onReceive(Context context, final Intent intent) {
//			String action = intent.getAction();
//			if (action.equals(Intent.ACTION_MEDIA_EJECT)) {
//				//USB设备移除，更新UI
//				Log.d(TAG, "设备被移出");
////				if (rollPagerView!=null){
////					if (rollPagerView.isPlaying()){
////						rollPagerView.pause();
////					}
////
////
////				}
//				if (ijkMediaPlayer!=null){
//					Log.d(TAG, "播放暂停");
//					ijkVideoView.pause();
//					ijkVideoView.canPause();
//
//					//ijkVideoView.stopPlayback();
//					//ijkMediaPlayer.stop();
//				}
//			} else if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
//				//USB设备挂载，更新UI
//				Log.d(TAG, "设备插入");
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						String usbPath = intent.getDataString();//（usb在手机上的路径）
//
//						getAllFiles(new File(usbPath.split("file:///")[1]+File.separator+"file"));
//						Log.d(TAG, usbPath);
//					}
//				}).start();
//			}
//
//		}
//	}

	/**
	 * 生成一个0 到 count 之间的随机数
	 * @param endNum
	 * @return
	 */
	public  int getNum(int endNum){
		if(endNum > 0){
			Random random = new Random();
			return random.nextInt(endNum);
		}
		return 0;
	}

	int c=0;
	boolean pp=true;



	private class MyReceiver  extends BroadcastReceiver {

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
					if (webSocketClient!=null){
					//	Log.d(TAG, "刷脸监听");
						if (!isLianJie){
						//	Log.d(TAG, "进来2");
					try {
						baoCunBean=baoCunBeanDao.load(123456L);
						WebsocketPushMsg websocketPushMsg = new WebsocketPushMsg();
						websocketPushMsg.close();
						if (baoCunBean.getZhujiDiZhi() != null && baoCunBean.getShipingIP() != null) {
							websocketPushMsg.startConnection(baoCunBean.getHoutaiDiZhi(), baoCunBean.getShipingIP());
						}


					} catch (Exception e) {
						Log.d(TAG, e.getMessage()+"aaa");

					}
						}
				}
			}
				if (intent.getAction().equals("gxshipingdizhi")) {
					//重新开启刷脸监听
					baoCunBean=baoCunBeanDao.load(123456L);
					if (baoCunBean.getShipingIP() != null && baoCunBean.getZhujiDiZhi() != null) {
						try {
							WebsocketPushMsg websocketPushMsg = new WebsocketPushMsg();
							websocketPushMsg.close();
							if (baoCunBean.getShipingIP() != null && baoCunBean.getZhujiDiZhi() != null) {
								websocketPushMsg.startConnection(baoCunBean.getHoutaiDiZhi(), baoCunBean.getShipingIP());
							}
						} catch (Exception e) {
							Log.d(TAG, e.getMessage()+"fghj");
						}

					}
					if (mbLeiXingBeanList!=null && mbLeiXingBeanList.size()>0)
						mbLeiXingBeanList.clear();
					mbLeiXingBeanList= benDiMBbeanDao.loadAll();
					new Thread(new Runnable() {
						@Override
						public void run() {

							Glide.get(YiDongNianHuiActivity.this).clearDiskCache();

						}
					}).start();

					final String ss=intent.getStringExtra("bgPath");
					if (ss!=null){
						Log.d(TAG, "换底图图片"+ss);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Glide.with(YiDongNianHuiActivity.this)
								//.load(R.drawable.vvv)
								.load(ss)
								//	.load(zhuji+item.getTouxiang())
								//.apply(myOptions)
								//.transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
								//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
								.into(dabg);

					}

					if (baoCunBean.getHoutaiDiZhi()!=null && !baoCunBean.getHoutaiDiZhi().equals("")
							&& baoCunBean.getZhanghuId()!=null && !baoCunBean.getZhanghuId().equals("")
							&& baoCunBean.getZhanhuiId()!=null && !baoCunBean.getZhanhuiId().equals("") ){
						//link_login();

						link_bg();

					}

				}
				if (intent.getAction().equals("shoudongshuaxin")) {

					Toast.makeText(YiDongNianHuiActivity.this,"下载后台图片失败",Toast.LENGTH_SHORT).show();
				}

				if (intent.getAction().equals("guanbi")){
					Log.d(TAG, "关闭");
					finish();
				}
			}
	//	}
	}

	// 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
	public static void getAllFiles(File root,String nameaaa){
		File files[] = root.listFiles();

		if(files != null){
			for (File f : files){
				if(f.isDirectory()){
					getAllFiles(f,nameaaa);
				}else{
					String name=f.getName();
					if (name.equals(nameaaa)){
						Log.d(TAG, "视频文件删除:" + f.delete());
					}
				}
 			}
		}
	}

	private void link_fasong(int timestamp, String id, final long huiyi) {
		//Log.d(TAG, DateUtils.time(timestamp + "000"));

		OkHttpClient okHttpClient= MyApplication.getOkHttpClient();
		RequestBody body = new FormBody.Builder()
				.add("machineName",baoCunBean.getGuanggaojiMing())
               	 .add("machineCode",Utils.getSerialNumber(this)==null?Utils.getIMSI():Utils.getSerialNumber(this))
                 .add("exhibition_id",baoCunBean.getZhanhuiId())
				.add("timestamp",DateUtils.time(timestamp+"000"))
				.add("subjectId",id+"")
				.add("screenId",baoCunBean.getGonggao())
				//.add("conferenceName",huiYiName)
			//	.add("screenPosition",weizhi)
				.add("conference_id",huiyi+"")
				.build();

		//Log.d(TAG, baoCunBean.getZhanghuId()+"hhhhhhhhhhhhhhhh");
		Request.Builder requestBuilder = new Request.Builder()
				//.header("Content-Type", "application/json")
				.post(body)
				.url(baoCunBean.getHoutaiDiZhi()+"/appSave.do");
		//Log.d(TAG, baoCunBean.getHoutaiDiZhi() + "/appSave.do");
		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

				Log.d("AllConnects", "请求获取二维码失败"+e.getMessage());

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
				String ss=body.string();
				  Log.d("AllConnects", "签到返回"+ss);
				  link_shishi_renshu(huiyi);

//					JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
//					Gson gson=new Gson();
//						int code=jsonObject.get("resultCode").getAsInt();
//						if (code==0){
//					JsonArray array=jsonObject.get("data").getAsJsonArray();
//					int a=array.size();
//					for (int i=0;i<a;i++){
//						YongHuBean zhaoPianBean=gson.fromJson(array.get(i),YongHuBean.class);
//						moShengRenBean2List.add(zhaoPianBean);
//						//Log.d("VlcVideoActivity", zhaoPianBean.getSubjectId());
//					}

				//	}

				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"签到返回异常");
				}

			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if( KeyEvent.KEYCODE_MENU == keyCode ){  //如果按下的是菜单
			Log.d(TAG, "按下菜单键 ");
			chongzhi();
			//isTiaoZhuang=false;
			startActivity(new Intent(YiDongNianHuiActivity.this, SheZhiActivity.class));
			finish();
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {

//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//
//				for ( int i=0;i<6;i++){
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					ShiBieBean.PersonBeanSB sb=new ShiBieBean.PersonBeanSB();
//					sb.setId(1234567L);
//					sb.setDepartment("观众");
//					sb.setName("测试");
//
//					Message message3 = Message.obtain();
//					message3.arg1 = 1;
//					message3.obj = sb;
//					handler.sendMessage(message3);
//
//				}
//
//
//			}
//		}).start();

		if (netWorkStateReceiver == null) {
			netWorkStateReceiver = new NetWorkStateReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			registerReceiver(netWorkStateReceiver, filter);
		}

		baoCunBean=baoCunBeanDao.load(123456L);
		if (baoCunBean.getHoutaiDiZhi()!=null && !baoCunBean.getHoutaiDiZhi().equals("")
				&& baoCunBean.getZhanghuId()!=null && !baoCunBean.getZhanghuId().equals("")
				&& baoCunBean.getZhanhuiId()!=null && !baoCunBean.getZhanhuiId().equals("")){
			link_bg();
			//link_shishi_renshu();
		}

		List<ZhuJiBeanH> hh=zhuJiBeanHDao.loadAll();
		if (hh!=null && hh.size()>0){
			touxiangPath=hh.get(0).getHostUrl();
			if (touxiangPath==null || touxiangPath.equals("")){
				touxiangPath="http://www.192.168.1.1";
			}
		}else {
			touxiangPath="http://www.192.168.1.1";
		}

		zhanghuID=baoCunBean.getZhanghuId();
		huiyiID=baoCunBean.getZhanhuiId();


		if (baoCunBean!=null && baoCunBean.getZhujiDiZhi()!=null){
			try {
				//String[] a1=baoCunBean.getZhujiDiZhi().split("//");
				//String[] b1=a1[1].split(":");
				//zhuji="http://"+b1[0];
				WebsocketPushMsg websocketPushMsg = new WebsocketPushMsg();
				websocketPushMsg.close();
				if (baoCunBean.getZhujiDiZhi()!=null && baoCunBean.getShipingIP() != null ) {
					websocketPushMsg.startConnection(baoCunBean.getHoutaiDiZhi(), baoCunBean.getShipingIP());
				}
			} catch (URISyntaxException e) {
				Log.d(TAG, e.getMessage()+"ddd");

			}
		}else {
			TastyToast.makeText(YiDongNianHuiActivity.this,"没有设置后台地址",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
		}

		if (benDiMBbeanDao!=null && benDiMBbeanDao.loadAll().size()>0){

			String ppp =baoCunBean.getWenzi();
			if (ppp!=null && !ppp.equals(""))
			Glide.with(YiDongNianHuiActivity.this)
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
		if (webSocketClient!=null){
			webSocketClient.close();
			webSocketClient=null;
		}

		Intent intent1=new Intent("guanbi333"); //关闭监听服务
		sendBroadcast(intent1);
		synthesizer.release();
		handler.removeCallbacksAndMessages(null);
		if (myReceiver != null)
			unregisterReceiver(myReceiver);
		unregisterReceiver(netWorkStateReceiver);
		super.onDestroy();

	}


//	private void changeSurfaceSize() {
//		// get screen size
//		int dw = Utils.getDisplaySize(getApplicationContext()).x;
//		int dh = Utils.getDisplaySize(getApplicationContext()).y;
//
////		RelativeLayout.LayoutParams re1 = (RelativeLayout.LayoutParams)surfaceview.getLayoutParams();
////
////		  re1.width=dw/3;
////		  re1.height = dh/5;
////
////		surfaceview.setLayoutParams(re1);
////		surfaceview.invalidate();
//		Log.d(TAG, baoCunBean.getShipingIP()+"hhhhh");
//		if (mediaPlayer != null) {
//			Log.d(TAG, baoCunBean.getShipingIP()+"gggg");
//
//			media = new Media(libvlc, Uri.parse("rtsp://"+baoCunBean.getShipingIP()+"/user=admin&password=&channel=1&stream=0.sdp"));
//			mediaPlayer.setMedia(media);
//			mediaPlayer.play();
//
//		}
//
//	}
//	/**
//	 * websocket接口返回face.image
//	 * image为base64编码的字符串
//	 * 将字符串转为可以识别的图片
//	 * @param imgStr
//	 * @return
//	 */
//	public Bitmap generateImage(String imgStr, int cont, WBWeiShiBieDATABean dataBean, Context context) throws Exception {
//		// 对字节数组字符串进行Base64解码并生成图片
//		if (imgStr == null) // 图像数据为空
//			return null;
//		BASE64Decoder decoder = new BASE64Decoder();
//		try {
//			// Base64解码
//			final byte[][] b = {decoder.decodeBuffer(imgStr)};
//			for (int i = 0; i < b[0].length; ++i) {
//				if (b[0][i] < 0) {// 调整异常数据
//					b[0][i] += 256;
//				}
//			}
//			MoShengRenBean2 moShengRenBean2=new MoShengRenBean2();
//			moShengRenBean2.setId(dataBean.getTrack());
//			moShengRenBean2.setBytes(b[0]);
//			moShengRenBean2.setUrl("dd");
//
//			moShengRenBean2List.add(moShengRenBean2);
//
//				adapter.notifyDataSetChanged();
//
//
//
//
//
//			//   Bitmap bitmap= BitmapFactory.decodeByteArray(b[0],0, b[0].length);
//
//			//  Log.d("WebsocketPushMsg", "bitmap.getHeight():" + bitmap.getHeight());
//
//			// 生成jpeg图片
//			//  OutputStream out = new FileOutputStream(imgFilePath);
//			//   out.write(b);
//			//  out.flush();
//			//  out.close();
//
//
////			dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
////				@Override
////				public void onDismiss(DialogInterface dialog) {
////					Log.d("VlcVideoActivity", "Dialog销毁2");
////					b[0] =null;
////				}
////			});
//			//dialog.show();
//
//
//			return null;
//		} catch (Exception e) {
//			throw e;
//
//		}
//	}

	public  int dip2px(Context context, float dipValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dipValue * scale + 0.5f);
	}
	public  int px2dip(Context context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(pxValue / scale + 0.5f);
	}
	/**
	 * 识别消息推送
	 * 主机盒子端API ws://[主机ip]:9000/video
	 * 通过 websocket 获取 识别结果
	 * @author Wangshutao
	 */
	private class WebsocketPushMsg {
		/** * 识别消息推送
		 * @param wsUrl websocket接口 例如 ws://192.168.1.50:9000/video
		 * @param rtspUrl 视频流地址 门禁管理-门禁设备-视频流地址
		 *                例如 rtsp://192.168.0.100/live1.sdp
		 *                或者 rtsp://admin:admin12345@192.168.1.64/live1.sdp
		 *                或者 rtsp://192.168.1.103/user=admin&password=&channel=1&stream=0.sdp
		 *                或者 rtsp://192.168.1.100/live1.sdp
		 *                       ?__exper_tuner=lingyun&__exper_tuner_username=admin
		 *                       &__exper_tuner_password=admin&__exper_mentor=motion
		 *                       &__exper_levels=312,1,625,1,1250,1,2500,1,5000,1,5000,2,10000,2,10000,4,10000,8,10000,10
		 *                       &__exper_initlevel=6
		 * @throws URISyntaxException
		 * @throws
		 * @throws
		 *
		 *  ://192.168.2.52/user=admin&password=&channel=1&stream=0.sdp
		 *
		 *   rtsp://192.166.2.55:554/user=admin_password=tljwpbo6_channel=1_stream=0.sdp?real_stream
		 */
		private void startConnection(String wsUrl, String rtspUrl) throws URISyntaxException {
			 URI uri=null;
			//当视频流地址中出现&符号时，需要进行进行url编码
			if (rtspUrl.contains("&")){
				try {
					//Log.d("WebsocketPushMsg", "dddddttttttttttttttt"+rtspUrl);
					rtspUrl = URLEncoder.encode(rtspUrl,"UTF-8");

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					//Log.d("WebsocketPushMsg", e.getMessage());
				}
			}

			try {

				uri = URI.create("ws://"+wsUrl.split("//")[1]+":9000/video" + "?url=" + rtspUrl);

			Log.d("WebsocketPushMsg", "url="+uri);
			  webSocketClient = new WebSocketClient(uri) {
			//	private Vector vector=new Vector();

				@Override
				public void onOpen(ServerHandshake serverHandshake) {
					isLianJie=true;
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (!YiDongNianHuiActivity.this.isFinishing())
								wangluo.setVisibility(View.GONE);
						}
					});
				}

				@Override
				public void onMessage(String ss) {

					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					Gson gson=new Gson();
					WBBean wbBean= gson.fromJson(jsonObject, WBBean.class);

					if (wbBean.getType().equals("recognized")) { //识别
						Log.d("WebsocketPushMsg", "识别出了");

						final ShiBieBean dataBean = gson.fromJson(jsonObject, ShiBieBean.class);

							try {

										if (baoCunBean.getHoutaiDiZhi()!=null
												&& !baoCunBean.getHoutaiDiZhi().equals("")
												&& zhanghuID!=null && !zhanghuID.equals("")
												&& huiyiID!=null && !huiyiID.equals("")){

											int po=1;
											String bm[]= dataBean.getPerson().getDescription().split(",");
											Log.d("WebsocketPushMsg", "bm.length:" + bm.length);

											for (String s:bm){
												try {
													HuiYiID  huiyi=huiYiIDDao.queryBuilder().where(HuiYiIDDao.Properties.SubConferenceCode.eq(s)).unique();
													if (huiyi==null){
														runOnUiThread(new Runnable() {
															@Override
															public void run() {
																TastyToast.makeText(YiDongNianHuiActivity.this,"查询不到展会编码",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
															}
														});
													}
													Log.d("WebsocketPushMsg", baoCunBean.getZhanhuiBianMa()+"编码");
													Log.d("WebsocketPushMsg", s+"编码2");
													Log.d("WebsocketPushMsg", "huiyi.getStartTime():" + DateUtils.time(huiyi.getStartTime()+""));
													Log.d("WebsocketPushMsg", "huiyi.getEndTime():" + DateUtils.time(huiyi.getEndTime()+""));
													if (baoCunBean.getZhanhuiBianMa().contains(s)
															&& (huiyi.getStartTime()<System.currentTimeMillis())
															&& (huiyi.getEndTime()>System.currentTimeMillis())){
														Message message2 = Message.obtain();
														message2.arg1 = 1;
														message2.obj = dataBean.getPerson();
														handler.sendMessage(message2);
														link_fasong(dataBean.getData().getTimestamp(),dataBean.getPerson().getJob_number(),huiyi.getId());
														po=1;
														break;

													}else {
														po=2;
														Log.d("WebsocketPushMsg", "33333333333333");
													}
												}catch (Exception e){
													Log.d("WebsocketPushMsg", e.getMessage()+"666666666666666");
												}

											}
											if (po==2){
												runOnUiThread(new Runnable() {
													@Override
													public void run() {
														TastyToast.makeText(YiDongNianHuiActivity.this,"非当前时段会议人员",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
													}
												});
											}

											//link_getAll_User(dataBean.getPerson().getJob_number());

//										switch (dataBean.getPerson().getDepartment()) {
//											case "省公司领导":
//												benDiRenShuBean.setNShen((benDiRenShuBean.getNShen() - 1) < 0 ? 0 : (benDiRenShuBean.getNShen() - 1));
//												benDiRenShuBean.setN1((benDiRenShuBean.getN1() - 1) < 0 ? 0 : (benDiRenShuBean.getN1() - 1));
//												benDiRenShuBean.setYShen(benDiRenShuBean.getYShen() + 1);
//												benDiRenShuBean.setY1(benDiRenShuBean.getY1() + 1);
//												benDiRenShuBeanDao.update(benDiRenShuBean);
//
//												break;
//											case "市公司领导":
//												benDiRenShuBean.setNShi((benDiRenShuBean.getNShi() - 1) < 0 ? 0 : (benDiRenShuBean.getNShi() - 1));
//												benDiRenShuBean.setN1((benDiRenShuBean.getN1() - 1) < 0 ? 0 : (benDiRenShuBean.getN1() - 1));
//												benDiRenShuBean.setYShi(benDiRenShuBean.getYShi() + 1);
//												benDiRenShuBean.setY1(benDiRenShuBean.getY1() + 1);
//												benDiRenShuBeanDao.update(benDiRenShuBean);
//
//												break;
//											case "特邀嘉宾":
//												benDiRenShuBean.setNTeyao((benDiRenShuBean.getNTeyao() - 1) < 0 ? 0 : (benDiRenShuBean.getNTeyao() - 1));
//												benDiRenShuBean.setN1((benDiRenShuBean.getN1() - 1) < 0 ? 0 : (benDiRenShuBean.getN1() - 1));
//												benDiRenShuBean.setYTeyao(benDiRenShuBean.getYTeyao() + 1);
//												benDiRenShuBean.setY1(benDiRenShuBean.getY1() + 1);
//												benDiRenShuBeanDao.update(benDiRenShuBean);
//
//												break;
//											default:
//												benDiRenShuBean.setN1((benDiRenShuBean.getN1() - 1) < 0 ? 0 : (benDiRenShuBean.getN1() - 1));
//												benDiRenShuBean.setY1(benDiRenShuBean.getY1() + 1);
//												benDiRenShuBeanDao.update(benDiRenShuBean);
//
//												break;
//										}
//										qianDaoId.setIsQd(true);
//										qianDaoIdDao.update(qianDaoId)
									}else {
											runOnUiThread(new Runnable() {
												@Override
												public void run() {
													TastyToast.makeText(YiDongNianHuiActivity.this,"参数不全",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
												}
											});

										}
								//daoSession.insert(bean);
								//Log.d(TAG, "111");

							}catch (Exception e){
								Log.d("WebsocketPushMsg", e.getMessage()+"aaajjjj");
							}

//						BenDiQianDao qianDao=new BenDiQianDao();
//						qianDao.setId(Long.parseLong(dataBean.getPerson().getJob_number()));
//						qianDao.setName(dataBean.getPerson().getName());
//						qianDao.setPhone(DateUtils.time(System.currentTimeMillis()+""));
//						try {
//							benDiQianDaoDao.insert(qianDao);
//						}catch (Exception e){
//							e.printStackTrace();
//						}

					}
             else if (wbBean.getType().equals("unrecognized")) {
					//	Log.d("WebsocketPushMsg", "识别出了陌生人");
						if (!baoCunBean.getIsShowMoshengren()){

						JsonObject jsonObject1 = jsonObject.get("data").getAsJsonObject();

						final WeiShiBieBean dataBean = gson.fromJson(jsonObject1, WeiShiBieBean.class);

						try {
							MoShengRenBean bean = new MoShengRenBean(dataBean.getTrack(), "sss");

							daoSession.insert(bean);

							BASE64Decoder decoder = new BASE64Decoder();
							// Base64解码
							final byte[][] b;

							b = new byte[][]{decoder.decodeBuffer(dataBean.getFace().getImage())};
							for (int i = 0; i < b[0].length; ++i) {
								if (b[0][i] < 0) {// 调整异常数据
									b[0][i] += 256;
								}
							}
							dataBean.setTrack2(b[0]);
							Message message = new Message();
							message.arg1 = 2;
							message.obj = dataBean;
							handler.sendMessage(message);


						} catch (Exception e) {
							Log.d("WebsocketPushMsg", e.getMessage());
							//e.printStackTrace();
						}finally {
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							try {

								daoSession.deleteByKey(dataBean.getTrack());
								//Log.d("WebsocketPushMsg", "删除");
							}catch (Exception e){
								Log.d("WebsocketPushMsg", e.getMessage());
								}
							}
						}
					}
				}

				@Override
				public void onClose(int i, String s, boolean b) {
					isLianJie=false;
					Log.d("WebsocketPushMsg", "onClose"+i);
					runOnUiThread( new Runnable() {
						@Override
						public void run() {
							if (!YiDongNianHuiActivity.this.isFinishing()){
								wangluo.setVisibility(View.VISIBLE);
								//wangluo.setText("连接识别主机失败,重连中...");
							}

						}
					});
//
//					if (conntionHandler==null && runnable==null){
//						Looper.prepare();
//						conntionHandler=new Handler();
//						runnable=new Runnable() {
//							@Override
//							public void run() {
//
//								Intent intent=new Intent("duanxianchonglian");
//								sendBroadcast(intent);
//							}
//						};
//						conntionHandler.postDelayed(runnable,13000);
//						Looper.loop();
//					}

				}

				@Override
				public void onError(Exception e) {
					isLianJie=false;
					//Log.d("WebsocketPushMsg", "onClose"+i);
					runOnUiThread( new Runnable() {
						@Override
						public void run() {
							if (!YiDongNianHuiActivity.this.isFinishing()){
								wangluo.setVisibility(View.VISIBLE);
								//wangluo.setText("连接识别主机失败,重连中...");
							}

						}
					});
					Log.d("WebsocketPushMsg9", "onError"+e.getMessage());

				}
			};

			webSocketClient.connect();

			}catch (Exception e){
				Log.d(TAG, e.getMessage()+"");
			}

		}
		private void close(){
//
//			if (conntionHandler!=null && runnable!=null){
//				conntionHandler.removeCallbacks(runnable);
//				conntionHandler=null;
//				runnable=null;
//
//			}
			if (webSocketClient!=null){
				webSocketClient.close();
				webSocketClient=null;
				System.gc();

			}

		}

	}



	private void creatUser(byte[] bytes, Long tt, String age) {
		//Log.d("WebsocketPushMsg", "创建用户");
		String fileName="tong"+System.currentTimeMillis()+".jpg";
		//通过bytes数组创建图片文件
		createFileWithByte(bytes,fileName,tt,age);
		//上传
	//	addPhoto(fileName);
	}

	/**
	 * 根据byte数组生成文件
	 *
	 * @param bytes
	 *            生成文件用到的byte数组
	 * @param age
	 */
	private void createFileWithByte(byte[] bytes, String filename, Long tt, String age) {
		/**
		 * 创建File对象，其中包含文件所在的目录以及文件的命名
		 */
		File file=null;
		String	sdDir = this.getFilesDir().getAbsolutePath();//获取跟目录
		makeRootDirectory(sdDir);

		// 创建FileOutputStream对象
		FileOutputStream outputStream = null;
		// 创建BufferedOutputStream对象
		BufferedOutputStream bufferedOutputStream = null;

		try {
			file = new File(sdDir +File.separator+ filename);
			// 在文件系统中根据路径创建一个新的空文件
		//	file2.createNewFile();
		//	Log.d(TAG, file.createNewFile()+"");

			// 获取FileOutputStream对象
			outputStream = new FileOutputStream(file);
			// 获取BufferedOutputStream对象
			bufferedOutputStream = new BufferedOutputStream(outputStream);
			// 往文件所在的缓冲输出流中写byte数据
			bufferedOutputStream.write(bytes);
			// 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
			bufferedOutputStream.flush();
			//上传文件
			//addPhoto(sdDir,filename,bytes,tt,age);

		} catch (Exception e) {
			// 打印异常信息
			//Log.d(TAG, "ssssssssssssssssss"+e.getMessage());
		} finally {
			// 关闭创建的流对象
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	public static void makeRootDirectory(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {

		}
	}



//
//	private void link_houtai(User zhaoPianBean) {
//		//final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//		//http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
//		OkHttpClient okHttpClient= new OkHttpClient();
//
//		RequestBody body = new FormBody.Builder()
//				.add("cmd","addSign")
//				.add("subjectId",zhaoPianBean.getId()+"")
//				.add("subjectPhoto",zhaoPianBean.getPhotos().get(0).getUrl())
//				.build();
//		Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.post(body)
//				.url("http://192.168.2.17:8080/sign");
//
//		// step 3：创建 Call 对象
//		Call call = okHttpClient.newCall(requestBuilder.build());
//
//		//step 4: 开始异步请求
//		call.enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				Log.d("AllConnects", "请求添加陌生人失败"+e.getMessage());
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				Log.d("AllConnects", "请求添加陌生人成功"+call.request().toString());
//				//获得返回体
//				try {
//
//				ResponseBody body = response.body();
//			//	Log.d("AllConnects", "aa   "+response.body().string());
//
//				JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
//				Gson gson=new Gson();
//				int code=jsonObject.get("resultCode").getAsInt();
//				if (code==0){
//
////					JsonObject array=jsonObject.get("data").getAsJsonObject();
////					User zhaoPianBean=gson.fromJson(array,User.class);
////					link_houtai(zhaoPianBean);
//					//link_gengxing_erweima();
//				}
//
//				}catch (Exception e){
//					Log.d("WebsocketPushMsg", e.getMessage());
//				}
//			}
//		});
//
//
//		}

	public class NetWorkStateReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			//检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

				//获得ConnectivityManager对象
				ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

				//获取ConnectivityManager对象对应的NetworkInfo对象
				//以太网
				NetworkInfo wifiNetworkInfo1 = connMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
				//获取WIFI连接的信息
				NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				//获取移动数据连接的信息
				NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				if (wifiNetworkInfo1.isConnected() || wifiNetworkInfo.isConnected() || dataNetworkInfo.isConnected()){
					wangluo.setVisibility(View.GONE);

				}else {
					isLianJie=false;

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
			}else {

				Log.d(TAG, "API23");
				//获得ConnectivityManager对象
				ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

				//获取所有网络连接的信息
				Network[] networks = connMgr.getAllNetworks();
				//用于存放网络连接信息
				StringBuilder sb = new StringBuilder();
				//通过循环将网络信息逐个取出来
				Log.d(TAG, "networks.length:" + networks.length);
				if (networks.length==0){
					isLianJie=false;
					wangluo.setVisibility(View.VISIBLE);
				}
				for (int i=0; i < networks.length; i++){
					//获取ConnectivityManager对象对应的NetworkInfo对象
					NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);

					if (networkInfo.isConnected()){
						wangluo.setVisibility(View.GONE);

					}
				}

			}
		}
	}


	private void link_getAll_User(String id){

		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

		OkHttpClient okHttpClient=MyApplication.getOkHttpClient();

	//	RequestBody requestBody = RequestBody.create(JSON, json);

//		Log.d("AllConnects", zhuji);
//		RequestBody body = new FormBody.Builder()
//				.add("name",name)
//				.add("subject_type",0+"")
//				.add("photo_ids","["+id+"]")
//				.build();
		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				//.post(requestBody)
				.get()
				.url(baoCunBean.getHoutaiDiZhi()+"/subjectSign.do?conferenceId="+baoCunBean.getHuiyiId()+"&subjectId="+id+"&screenId="+baoCunBean.getShiPingWeiZhi());

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
					Log.d("AllConnects", "签到后请求"+ss);
//
//					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//					Gson gson=new Gson();
//					AllUserBean zhaoPianBean=gson.fromJson(jsonObject,AllUserBean.class);
//					if (lingdaoList.size()>0){
//						lingdaoList.clear();
//					}
//					int size=zhaoPianBean.getData().size();
//					for (int i=0;i<size;i++){
//						if (tanChuangBeanDao.load((long) zhaoPianBean.getData().get(i).getId())==null){
//							TanChuangBean bean=new TanChuangBean();
//							bean.setId((long) zhaoPianBean.getData().get(i).getId());
//							bean.setName(zhaoPianBean.getData().get(i).getName());
//							bean.setIsLight(false);
//							if (zhaoPianBean.getData().get(i).getAvatar()!=null && !zhaoPianBean.getData().get(i).getAvatar().equals("")){
//								bean.setTouxiang(zhaoPianBean.getData().get(i).getAvatar());
//							}else {
//								bean.setTouxiang(zhaoPianBean.getData().get(i).getPhotos().get(0).getUrl());
//							}
//							tanChuangBeanDao.insert(bean);
//						}
//					}
//
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run() {
//							if (lingdaoList.size()>0){
//								lingdaoList.clear();
//							}
//							lingdaoList.addAll(tanChuangBeanDao.loadAll());
//							//adapter2.notifyDataSetChanged();
//						}
//					});


				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}

			}
		});


	}

	private void link_login(){

		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

		OkHttpClient okHttpClient= MyApplication.getOkHttpClient();


	//	RequestBody requestBody = RequestBody.create(JSON, json);

//		RequestBody body = new FormBody.Builder()
//				.add("username","rtceshi@163.com")
//				.add("password","123456")
//				.build();

		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.header("user-agent","Koala Admin")
				//.post(requestBody)
				.get()
				//.post(body)
				.url(baoCunBean.getHoutaiDiZhi()+"/subjectDeptCount.do?accountId="+baoCunBean.getZhanghuId()+"&id="+baoCunBean.getHuiyiId());

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
			//	try{

					ResponseBody body = response.body();
					String ss=body.string().trim();
					Log.d("AllConnects", "获取人数"+ss);

					JsonArray jsonObject= GsonUtil.parse(ss).getAsJsonArray();
					Gson gson=new Gson();
					int N=0;
					RenShu renShu=gson.fromJson(jsonObject.get(0),RenShu.class);

//						 if (renShu.getDept().equals("省公司领导")){
//							benDiRenShuBean.setNShen((renShu.getCount()-benDiRenShuBean.getYShen())<0 ? 0 : (renShu.getCount()-benDiRenShuBean.getYShen()));
//							benDiRenShuBeanDao.update(benDiRenShuBean);
//						}
//						if (renShu.getDept().equals("市公司领导")){
//							benDiRenShuBean.setNShi((renShu.getCount()-benDiRenShuBean.getYShi())<0?0:(renShu.getCount()-benDiRenShuBean.getYShi()));
//							benDiRenShuBeanDao.update(benDiRenShuBean);
//						}
//						if (renShu.getDept().equals("特邀嘉宾")){
//							benDiRenShuBean.setNTeyao((renShu.getCount()-benDiRenShuBean.getYTeyao())<0?0:(renShu.getCount()-benDiRenShuBean.getYTeyao()));
//							benDiRenShuBeanDao.update(benDiRenShuBean);
//						}
					N=renShu.getCount();
					//Log.d("YiDongNianHuiActivity", "N:" + N);
					benDiRenShuBean.setN1((N-benDiRenShuBean.getY1())<0?0:(N-benDiRenShuBean.getY1()));
					benDiRenShuBeanDao.update(benDiRenShuBean);
					if (renShu.getSubjectIds()!=null){

						String ids[]=renShu.getSubjectIds().split(",");
						//int size=ids.length;
						//Log.d("YiDongNianHuiActivity", "size:" + size);
						for (String id : ids) {

							QianDaoId qianDaoId = new QianDaoId(Long.valueOf(id), "dd", false);
							if (qianDaoIdDao.load(qianDaoId.getId()) == null) {
								Log.d("WebsocketPushMsg", "qianDaoId:" + qianDaoId);
								qianDaoIdDao.insert(qianDaoId);
							}

						}

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								benDiRenShuBean=benDiRenShuBeanDao.load(123456L);

								String str = String.format("%04d", benDiRenShuBean.getY1());
								char s1[]=str.toCharArray();
								StringBuilder cc=new StringBuilder();
								cc.append(" ");
								for (char c:s1){
									cc.append(String.valueOf(c)).append(" ");
								}
								y1.setText(cc.toString());

//								y2.setText(benDiRenShuBean.getYShen()+"");
//								y3.setText(benDiRenShuBean.getYShi()+"");
//								y4.setText(benDiRenShuBean.getYTeyao()+"");

//								String str2 = String.format("%04d", benDiRenShuBean.getN1());
//								char s2[]=str2.toCharArray();
//								StringBuilder cc2=new StringBuilder();
//								for (char c:s2){
//									cc2.append(String.valueOf(c)).append(" ");
//								}
								TastyToast.makeText(YiDongNianHuiActivity.this,"更新总人数成功",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();

							}
						});
					}


				//	Gson gson=new Gson();
					//int code=jsonObject.get("code").getAsInt();

				//	if (code==0){

				//	link_getAll_User();

				//	}

			//	}catch (Exception e){
				//	Log.d("WebsocketPushMsg", e.getMessage()+"ttttt");
			//	}

			}
		});
	}

	private void link_bg(){
		if (huiYiIDDao!=null)
			huiYiIDDao.deleteAll();
	//	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		OkHttpClient okHttpClient= MyApplication.getOkHttpClient();
			//RequestBody requestBody = RequestBody.create(JSON, json);
		RequestBody body = new FormBody.Builder()
				.add("id",baoCunBean.getZhanhuiId())
				.build();
		Log.d(TAG, baoCunBean.getZhanhuiId()+"获取后台会议信息");
		Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.header("user-agent","Koala Admin")
				//.post(requestBody)
				//.get()
				.post(body)
					.url(baoCunBean.getHoutaiDiZhi()+"/findSubConference.do");

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
					Log.d("AllConnects", "获取后台会议信息"+ss);

					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					Gson gson=new Gson();
					final HuiYiInFoBean renShu=gson.fromJson(jsonObject,HuiYiInFoBean.class);
					List<HuiYiInFoBean.ObjectsBean> gg= renShu.getObjects();
					int si=gg.size();
					StringBuilder stringBuilder=new StringBuilder();
					for (int i=0;i<si;i++){

						if (gg.get(i).getMachineCode().contains(Utils.getSerialNumber(YiDongNianHuiActivity.this)==null?Utils.getIMSI():Utils.getSerialNumber(YiDongNianHuiActivity.this))){
							stringBuilder.append(gg.get(i).getSubConferenceCode());
							stringBuilder.append(",");
							//所有展会编码已逗号分开保存
							try {
								HuiYiID huiYiID=new HuiYiID();
								huiYiID.setId((long) gg.get(i).getId());
								huiYiID.setSubConferenceCode(gg.get(i).getSubConferenceCode());
								huiYiID.setStartTime(gg.get(i).getStartTime());
								huiYiID.setEndTime(gg.get(i).getEndTime());
								huiYiIDDao.insert(huiYiID);
							}catch (Exception e){

								Log.d("YiDongNianHuiActivity", e.getMessage()+"");
							}

						}
					}

					baoCunBean.setZhanhuiBianMa(stringBuilder.toString());
					baoCunBeanDao.update(baoCunBean);
					Log.d("YiDongNianHuiActivity", baoCunBean.getZhanhuiBianMa()+"d");

				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"ttttt");
				}

			}
		});
	}

	private void link_shishi_renshu(long huiyi){

		//	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		OkHttpClient okHttpClient= MyApplication.getOkHttpClient();
		//RequestBody requestBody = RequestBody.create(JSON, json);
		RequestBody body = new FormBody.Builder()
				.add("meetingId",huiyi+"")
				.add("signInChannel","1")
				.add("machineCode", Utils.getSerialNumber(this)==null?Utils.getIMSI():Utils.getSerialNumber(this))
				.build();

		Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.header("user-agent","Koala Admin")
				//.post(requestBody)
				//.get()
				.post(body)
				.url(baoCunBean.getHoutaiDiZhi()+"/querySignSubjectMeetingPeoples.do");

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
					Log.d("AllConnects", "获取实时人数"+ss);

					final JsonObject jsonArray= GsonUtil.parse(ss).getAsJsonObject();

				//	Gson gson=new Gson();
					//final ShiShiRenShuBean renShu=gson.fromJson(jsonArray.get(""),ShiShiRenShuBean.class);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								String str = String.format("%04d",jsonArray.get("dtoDesc").getAsInt());
								char s1[]=str.toCharArray();
								StringBuilder cc=new StringBuilder();
								cc.append(" ");
								for (char c:s1){
									cc.append(String.valueOf(c)).append(" ");
								}
								y1.setText(cc.toString());

							}catch (Exception e){
								Log.d("YiDongNianHuiActivity", e.getMessage()+"获取实时人数异常");
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


				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"tttttiiii");
				}

			}
		});
	}

//	private class DownloadReceiver extends ResultReceiver {
//		public DownloadReceiver(Handler handler) {
//			super(handler);
//		}
//		@Override
//		protected void onReceiveResult(int resultCode, Bundle resultData) {
//			super.onReceiveResult(resultCode, resultData);
//			if (resultCode == DownloadService.UPDATE_PROGRESS) {
//				String ididid=resultData.getString("ididid2");
//				int progress = resultData.getInt("progress");
//
//				if (progress == 100) {
//					try {
//
//						//下载完成
//						//更新状态
//						Log.d(TAG, "ididididididd值："+ididid);
//						if (ididid!=null) {
//							ShiPingBean b = shiPingBeanDao.load(ididid);
//							b.setIsDownload(true);
//							shiPingBeanDao.update(b);
//
//							if (shiPingBeanList.size() > 0) {
//								shiPingBeanList.clear();
//							}
//							shiPingBeanList = shiPingBeanDao.loadAll();
//
//							ijkVideoView.setVideoPath(Environment.getExternalStorageDirectory() + File.separator + "linhefile" + File.separator + b.getId() + "." + b.getVideoType().split("\\/")[1]);
//							ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//								@Override
//								public void onPrepared(IMediaPlayer iMediaPlayer) {
//									ijkVideoView.start();
//								}
//							});
//						}else {
//							Log.d(TAG, "id的值是空");
//						}
//
//					}catch (Exception e){
//						Log.d(TAG, "捕捉到异常onReceiveResult"+e.getMessage());
//					}
//
//					//ijkVideoView.setVideoPath(mFile.getPath());
//					//ijkVideoView.start();
////					Intent install = new Intent();
////					install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////					install.setAction(android.content.Intent.ACTION_VIEW);
////					install.setDataAndType(Uri.fromFile(mFile),"application/vnd.android.package-archive");
////					startActivity(install);  //下载完成打开APK
//				}
//			}
//		}
//	}

//	private class DownloadReceiverTuPian extends ResultReceiver {
//		public DownloadReceiverTuPian(Handler handler) {
//			super(handler);
//		}
//		@Override
//		protected void onReceiveResult(int resultCode, Bundle resultData) {
//			super.onReceiveResult(resultCode, resultData);
//			if (resultCode == DownloadTuPianService.UPDATE_PROGRESS2) {
//				int progress = resultData.getInt("progress");
//
//				if (progress == 100) {
//					try {
//						//下载完成
//						// Environment.getExternalStorageDirectory()+ File.separator+"linhefile"+File.separator+"tupian111.jpg"
//						Log.d(TAG, "图片下载完成");
//
//					}catch (Exception e){
//						Log.d(TAG, "捕捉到异常onReceiveResult"+e.getMessage());
//					}
//
//				}
//			}
//		}
//	}

//	public static final int TIMEOUT = 1000 * 60;
//	private void link_chengshi() {
//		//final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//		//http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
//		OkHttpClient okHttpClient= new OkHttpClient.Builder()
//				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.retryOnConnectionFailure(true)
//				.build();
//
////		RequestBody body = new FormBody.Builder()
////				.add("cityCode","101040100")
////				.add("weatherType","1")
////				.build();
//
//		Request.Builder requestBuilder = new Request.Builder()
//				//.header("Content-Type", "application/json")
//				.get()
//				.url("http://api.map.baidu.com/location/ip?ak=uTTmEt0NeHSsgAKsXGLAMC8mvg6zPNLm" +
//						"&mcode=21:21:DA:F2:00:51:3B:AB:C4:E6:19:18:31:C6:98:CA:D6:7B:44:AE;com.ruitong.huiyi3");
//		//.url("http://wthrcdn.etouch.cn/weather_mini?city=广州市");
//
//		// step 3：创建 Call 对象
//		Call call = okHttpClient.newCall(requestBuilder.build());
//
//		//step 4: 开始异步请求
//		call.enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				Log.d("AllConnects", "请求添加陌生人失败"+e.getMessage());
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				Log.d("AllConnects", "请求添加陌生人成功"+call.request().toString());
//				//获得返回体
//				try {
//
//					ResponseBody body = response.body();
//				//	Log.d("AllConnects", "aa   "+response.body().string());
//
//					JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
//					Gson gson=new Gson();
//				//	JsonObject object=jsonObject.get("ContentBean").getAsJsonObject();
//
//					IpAddress zhaoPianBean=gson.fromJson(jsonObject,IpAddress.class);
//
//
//					/**从assets中读取txt
//					 * 按行读取txt
//					 * @param
//					 * @return
//					 * @throws Exception
//					 */
//
//						InputStream is = getAssets().open("tianqi.txt");
//						InputStreamReader reader = new InputStreamReader(is);
//						BufferedReader bufferedReader = new BufferedReader(reader);
//						//StringBuffer buffer = new StringBuffer("");
//						String str;
//						String aa=zhaoPianBean.getContent().getAddress_detail().getCity();
//						if (aa.length()>2){
//							aa=aa.substring(0,2);
//						//	Log.d("VlcVideoActivity", "fffff9"+aa);
//						}
//						while ((str = bufferedReader.readLine()) != null) {
//
//
//							if (str.contains(aa)){
//								//Log.d("VlcVideoActivity", "fffff3"+str);
//								link_tianqi(str);
//								break;
//							}
//						}
//
//				}catch (Exception e){
//					Log.d("WebsocketPushMsg", e.getMessage());
//				}
//			}
//		});
//
//
//	}

//	private void link_tianqi(String bean) {
//		//final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//		//http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
//		OkHttpClient okHttpClient= new OkHttpClient.Builder()
//				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.retryOnConnectionFailure(true)
//				.build();
////		RequestBody body = new FormBody.Builder()
////				.add("cityCode","101040100")
////				.add("weatherType","1")
////				.build();
//
//		Request.Builder requestBuilder = new Request.Builder()
//				//.header("Content-Type", "application/json")
//				.get()
//				//.url("https://api.map.baidu.com/location/ip?ak=uTTmEt0NeHSsgAKsXGLAMC8mvg6zPNLm" +
//					//	"&mcode=21:21:DA:F2:00:51:3B:AB:C4:E6:19:18:31:C6:98:CA:D6:7B:44:AE;com.ruitong.huiyi3");
//
//				.url("http://wthrcdn.etouch.cn/weather_mini?citykey=" + bean.substring(bean.length() - 9, bean.length()));
//
//		// step 3：创建 Call 对象
//		Call call = okHttpClient.newCall(requestBuilder.build());
//
//		//step 4: 开始异步请求
//		call.enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				Log.d("AllConnects", "请求添加陌生人失败"+e.getMessage());
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				Log.d("AllConnects", "请求天气成功"+call.request().toString());
//				//获得返回体
//				try {
//
//					ResponseBody body = response.body();
//					//Log.d("AllConnects", "aa   "+response.body().string());
//
//					JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
//					Gson gson=new Gson();
//					//JsonObject object=jsonObject.get("ContentBean").getAsJsonObject();
//
//					final TianQiBean zhaoPianBean=gson.fromJson(jsonObject,TianQiBean.class);
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run() {
//							tianqi0.setText(zhaoPianBean.getData().getCity());
//							tianqi1.setText(zhaoPianBean.getData().getWendu()+" 度");
//						//	tianqi2.setText(zhaoPianBean.getData().getGanmao());
//						}
//					});
//
//
//				}catch (Exception e){
//					Log.d("WebsocketPushMsg", e.getMessage());
//				}
//			}
//		});
//
//
//	}



}
