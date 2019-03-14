package com.ruitong.huiyi3;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.ruitong.huiyi3.beans.BaoCunBean;
import com.ruitong.huiyi3.beans.BenDiJiLuBean;
import com.ruitong.huiyi3.beans.ChengShiIDBean;


import com.ruitong.huiyi3.beans.GuanHuai;
import com.ruitong.huiyi3.beans.LunBoBean;
import com.ruitong.huiyi3.beans.MyObjectBox;
import com.ruitong.huiyi3.beans.Subject;
import com.ruitong.huiyi3.beans.TodayBean;
import com.ruitong.huiyi3.beans.XinXiAll;
import com.ruitong.huiyi3.beans.XinXiIdBean;
import com.ruitong.huiyi3.beans.ZhiChiChengShi;
import com.ruitong.huiyi3.beans.ZhuJiBean;
import com.ruitong.huiyi3.beans.ZhuJiBeanH;
import com.ruitong.huiyi3.cookies.CookiesManager;
import com.ruitong.huiyi3.dialogall.CommonData;
import com.ruitong.huiyi3.dialogall.CommonDialogService;
import com.ruitong.huiyi3.dialogall.ToastUtils;
import com.ruitong.huiyi3.utils.GsonUtil;
import com.tencent.bugly.Bugly;
import com.ruitong.huiyi3.utils.Utils;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;



import io.objectbox.Box;
import io.objectbox.BoxStore;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;


/**
 * Created by tangjun on 14-8-24.
 */
public class MyApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks  {
	public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
	private final static String TAG = "CookiesManager";
	public static MyApplication myApplication;
	public static OkHttpClient okHttpClient=null;

	// 超时时间
	public static final int TIMEOUT = 1000 * 60;

	private Box<ChengShiIDBean> chengShiIDBeanBox=null;
	private Box<BaoCunBean> baoCunBeanBox=null;
	private Box<Subject> subjectBox=null;
	private Box<LunBoBean> lunBoBeanBox=null;
	private Box<XinXiAll> xinXiAllBox=null;
	private Box<XinXiIdBean> xinXiIdBeanBox= null;
	private Box<GuanHuai> guanHuaiBox=null;
	private Box<TodayBean> todayBeanBox = null;
	private Box<BenDiJiLuBean> benDiJiLuBeanBox = null;
	private Box<ZhuJiBeanH> zhuJiBeanBox = null;
	private BaoCunBean baoCunBean=null;

	public static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath()+
			File.separator+"ruitongzipyqt";
	public static final String SDPATH2 = Environment.getExternalStorageDirectory().getAbsolutePath()+
			File.separator+"ruitongyqt";

	@Override
	public void onCreate() {
		super.onCreate();


				try {
					ScreenAdapterTools.init(this);

					FileDownloader.setupOnApplicationOnCreate(this)
							.connectionCreator(new FileDownloadUrlConnection
									.Creator(new FileDownloadUrlConnection.Configuration()
									.connectTimeout(25_000) // set connection timeout.
									.readTimeout(25_000) // set read timeout.
							))
							.commit();

					BoxStore mBoxStore = MyObjectBox.builder().androidContext(this).build();

//				// /搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
//				QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//
//					@Override
//					public void onViewInitFinished(boolean arg0) {
//						//x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//						//	Log.d("app", " onViewInitFinished is " + arg0);
//					}
//
//					@Override
//					public void onCoreInitFinished() {
//
//					}
//				};
//				//x5内核初始化接口
//				QbSdk.initX5Environment(getApplicationContext(),  cb);


				Bugly.init(getApplicationContext(), "1f18c0b4d8", false);

					this.registerActivityLifecycleCallbacks(this);//注册

					CommonData.applicationContext = this;
					DisplayMetrics metric = new DisplayMetrics();
					WindowManager mWindowManager  = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
					mWindowManager.getDefaultDisplay().getMetrics(metric);
					CommonData.ScreenWidth = metric.widthPixels; // 屏幕宽度（像素）

					Intent dialogservice = new Intent(this, CommonDialogService.class);
					startService(dialogservice);


					baoCunBeanBox= mBoxStore.boxFor(BaoCunBean.class);
					subjectBox= mBoxStore.boxFor(Subject.class);
					lunBoBeanBox= mBoxStore.boxFor(LunBoBean.class);
					xinXiAllBox= mBoxStore.boxFor(XinXiAll.class);
					xinXiIdBeanBox= mBoxStore.boxFor(XinXiIdBean.class);
					guanHuaiBox= mBoxStore.boxFor(GuanHuai.class);
					chengShiIDBeanBox= mBoxStore.boxFor(ChengShiIDBean.class);
					todayBeanBox= mBoxStore.boxFor(TodayBean.class);
					benDiJiLuBeanBox= mBoxStore.boxFor(BenDiJiLuBean.class);
					zhuJiBeanBox= mBoxStore.boxFor(ZhuJiBeanH.class);
				} catch (Exception e) {
					Log.d(TAG, e.getMessage()+"主程序");
				}
					myApplication = this;

		String path= Environment.getExternalStorageDirectory()+File.separator+"linhefile";
		File destDir = new File(path);

		if (!destDir.exists()) {
			destDir.mkdirs();
		}


		if(chengShiIDBeanBox.getAll().size()==0){
			OkHttpClient okHttpClient= new OkHttpClient();
			okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder()
					.get()
					.url("http://v.juhe.cn/weather/citys?key=356bf690a50036a5cfc37d54dc6e8319");
			// .url("http://v.juhe.cn/weather/index?format=2&cityname="+text1+"&key=356bf690a50036a5cfc37d54dc6e8319");
			// step 3：创建 Call 对象
			Call call = okHttpClient.newCall(requestBuilder.build());
			//step 4: 开始异步请求
			call.enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					Log.d("AllConnects", "请求失败"+e.getMessage());
				}

				@Override
				public void onResponse(Call call, okhttp3.Response response) throws IOException {
					Log.d("AllConnects", "请求成功"+call.request().toString());
					//获得返回体
					try{
						ResponseBody body = response.body();
						String ss=body.string().trim();
						Log.d("AllConnects", "天气"+ss);

						JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
						Gson gson=new Gson();
						final ZhiChiChengShi renShu=gson.fromJson(jsonObject,ZhiChiChengShi.class);
						int size=renShu.getResult().size();
						//  chengShiIDBeanBox.removeAll();

						for (int i=0;i<size;i++){
							ChengShiIDBean bean=new ChengShiIDBean();
							bean.setId(renShu.getResult().get(i).getId());
							bean.setCity(renShu.getResult().get(i).getCity());
							bean.setDistrict(renShu.getResult().get(i).getDistrict());
							bean.setProvince(renShu.getResult().get(i).getProvince());
							chengShiIDBeanBox.put(bean);
						}

					}catch (Exception e){
						Log.d("WebsocketPushMsg", e.getMessage()+"ttttt");
					}

				}
			});
		}




		baoCunBean = baoCunBeanBox.get(123456L);
		if (baoCunBean == null) {
			baoCunBean = new BaoCunBean();
			baoCunBean.setId(123456L);
			baoCunBean.setHoutaiDiZhi("http://hy.inteyeligence.com/front");
			baoCunBean.setTouxiangzhuji("http://192.168.2.78");
			baoCunBean.setShibieFaceSize(20);
			baoCunBean.setShibieFaZhi(70);
			baoCunBean.setYudiao(5);
			baoCunBean.setYusu(5);
			baoCunBean.setBoyingren(4);
			baoCunBean.setRuKuFaceSize(60);
			baoCunBean.setRuKuMoHuDu(0.3f);
			baoCunBean.setHuoTiFZ(70);
			baoCunBean.setHuoTi(false);
			baoCunBean.setJihuoma("0000-0000-0000-0000-0000");
			baoCunBean.setDangqianShiJian("d");
			baoCunBean.setTianQi(false);
			baoCunBean.setMoban(201);
			baoCunBeanBox.put(baoCunBean);
		}



	}



//    public BoxStore getBoxStore(){
//        return mBoxStore;
//    }

	public Box<TodayBean> getTodayBeanBox(){
		return todayBeanBox;
	}

	public Box<BenDiJiLuBean> getBenDiJiLuBeanBox(){
		return benDiJiLuBeanBox;
	}
	public Box<ChengShiIDBean> getChengShiIDBeanBox(){
		return chengShiIDBeanBox;
	}

	public Box<Subject> getSubjectBox(){
		return subjectBox;
	}

	public Box<LunBoBean> getLunBoBeanBox(){
		return lunBoBeanBox;
	}

	public Box<XinXiAll> getXinXiAllBox(){
		return xinXiAllBox;
	}
	public Box<XinXiIdBean> getXinXiIdBeanBox(){
		return xinXiIdBeanBox;
	}
	public Box<GuanHuai> getGuanHuaiBox(){
		return guanHuaiBox;
	}
	public Box<BaoCunBean> getBaoCunBeanBox(){
		return baoCunBeanBox;
	}

	public Box<ZhuJiBeanH> getZhuJiBeanBox(){
		return zhuJiBeanBox;
	}


	public static MyApplication getAppContext() {
		return myApplication;
	}







	public File getDiskCacheDir(String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = MyApplication.getAppContext().getExternalCacheDir().getPath();
		} else {
			cachePath = MyApplication.getAppContext().getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}

	public int getAppVersion() {
		try {
			PackageInfo info = MyApplication.getAppContext().getPackageManager().getPackageInfo(MyApplication.getAppContext().getPackageName(), 0);
			return info.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}


	public static OkHttpClient getOkHttpClient(){

		return new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();
		//okhttpclient.dispatcher().cancelAll();取消所有的请求
	}


	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		if(activity.getParent()!=null){
			CommonData.mNowContext = activity.getParent();
		}else
			CommonData.mNowContext = activity;
	}

	@Override
	public void onActivityStarted(Activity activity) {
		if(activity.getParent()!=null){
			CommonData.mNowContext = activity.getParent();
		}else
			CommonData.mNowContext = activity;
	}

	@Override
	public void onActivityResumed(Activity activity) {
		if(activity.getParent()!=null){
			CommonData.mNowContext = activity.getParent();
		}else
			CommonData.mNowContext = activity;
	}

	@Override
	public void onActivityPaused(Activity activity) {
		ToastUtils.getInstances().cancel();
	}

	@Override
	public void onActivityStopped(Activity activity) {

	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

	}

	@Override
	public void onActivityDestroyed(Activity activity) {

	}


//	public static OkHttpClient getOkHttpClient(){
//		if (okHttpClient==null){
//			okHttpClient = new OkHttpClient.Builder()
//					.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//					.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//					.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//					.cookieJar(new CookiesManager())
//					.retryOnConnectionFailure(true)
//					.build();
//
////			JSONObject json = new JSONObject();
////			try {
////				json.put("username", "test@megvii.com");
////				json.put("password", "123456");
////			} catch (JSONException e) {
////				e.printStackTrace();
////			}
//
//
//			//创建一个RequestBody(参数1：数据类型 参数2传递的json串)
//		//	RequestBody requestBody = RequestBody.create(JSON, json.toString());
//
//			RequestBody body = new FormBody.Builder()
//					.add("username", "test@megvii.com")
//					.add("password", "123456")
//					.build();
//
//			Request.Builder requestBuilder = new Request.Builder();
//			requestBuilder.header("User-Agent", "Koala Admin");
//			requestBuilder.header("Content-Type","application/json");
//			requestBuilder.post(body);
//			requestBuilder.url("http://192.166.2.65/auth/login");
//			Request request = requestBuilder.build();
//
//			Call mcall= okHttpClient.newCall(request);
//			mcall.enqueue(new Callback() {
//				@Override
//				public void onFailure(Call call, IOException e) {
//
//				}
//
//				@Override
//				public void onResponse(Call call, Response response) throws IOException {
//
//					Log.d(TAG, "123   "+response.body().string());
////					if (response.isSuccessful()){
////						Headers headers = response.headers();
////						List<String> cookies = headers.values("Set-Cookie");
////						//Log.d(TAG, "456   "+cookies.get(0).toString());
////						for (String str:cookies) {
////							if (str.startsWith("Koala Admin")) {
////								//将sessionId保存到本地
////
////							}
////						}
////					}
//
//				}
//			});
//		}
//		return okHttpClient;
//	}
}
