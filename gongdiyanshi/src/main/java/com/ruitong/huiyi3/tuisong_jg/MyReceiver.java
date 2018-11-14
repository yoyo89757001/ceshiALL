package com.ruitong.huiyi3.tuisong_jg;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;

import android.util.Log;
import android.util.Xml;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.ruitong.huiyi3.MyApplication;

import com.ruitong.huiyi3.beans.BaoCunBean;
import com.ruitong.huiyi3.beans.BaoCunBeanDao;
import com.ruitong.huiyi3.beans.BenDiMBbean;
import com.ruitong.huiyi3.beans.BenDiMBbeanDao;
import com.ruitong.huiyi3.beans.BenDiQianDaoDao;
import com.ruitong.huiyi3.beans.MOBan;
import com.ruitong.huiyi3.beans.PiLiangBean;
import com.ruitong.huiyi3.beans.RenYuanInFo;
import com.ruitong.huiyi3.beans.RenYuanInFoDao;
import com.ruitong.huiyi3.beans.SheBeiInFoBean;
import com.ruitong.huiyi3.beans.SheBeiInFoBeanDao;
import com.ruitong.huiyi3.beans.Subject;
import com.ruitong.huiyi3.beans.TuiSongBean;
import com.ruitong.huiyi3.beans.ZhuJiBeanH;
import com.ruitong.huiyi3.beans.ZhuJiBeanHDao;
import com.ruitong.huiyi3.cookies.CookiesManager;
import com.ruitong.huiyi3.dialog.GaiNiMaBi;
import com.ruitong.huiyi3.dialogall.ToastUtils;
import com.ruitong.huiyi3.utils.DateUtils;
import com.ruitong.huiyi3.utils.FileUtil;
import com.ruitong.huiyi3.utils.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
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

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JIGUANG-Example";
	public  OkHttpClient okHttpClient=null;
	private BaoCunBeanDao baoCunBeanDao=null;
	private BaoCunBean baoCunBean=null;
	private ZhuJiBeanH zhuJiBeanH=null;
	private ZhuJiBeanHDao zhuJiBeanHDao=null;
	private SheBeiInFoBeanDao sheBeiInFoBeanDao=null;
	//private SheBeiInFoBean sheBeiInFoBean=null;
	private RenYuanInFoDao renYuanInFoDao=null;
	private boolean isA=true;
	private BenDiMBbeanDao benDiMBbeanDao=null;
	private StringBuilder stringBuilder=null;
	private StringBuilder stringBuilder2=null;
	String path2=null;
	private Context context;
	private final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"ruitongzip";
	public static boolean isDW=true;


	@Override
	public void onReceive(final Context context, Intent intent) {
		this.context=context;

		try {
			stringBuilder=new StringBuilder();
			stringBuilder2=new StringBuilder();
			baoCunBeanDao = MyApplication.myApplication.getDaoSession().getBaoCunBeanDao();
			benDiMBbeanDao = MyApplication.myApplication.getDaoSession().getBenDiMBbeanDao();
			baoCunBean = baoCunBeanDao.load(123456L);
			zhuJiBeanHDao = MyApplication.myApplication.getDaoSession().getZhuJiBeanHDao();
			sheBeiInFoBeanDao = MyApplication.myApplication.getDaoSession().getSheBeiInFoBeanDao();
			renYuanInFoDao = MyApplication.myApplication.getDaoSession().getRenYuanInFoDao();

			Bundle bundle = intent.getExtras();
		//	Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

				Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
				JsonObject jsonObject= GsonUtil.parse(bundle.getString(JPushInterface.EXTRA_MESSAGE)).getAsJsonObject();
				if (jsonObject.get("title").getAsString().equals("迎宾设置")){
					String p1=null;
					Gson gson=new Gson();
					MOBan renShu=gson.fromJson(jsonObject,MOBan.class);
					String tp[]= renShu.getContent().getSubType().split("-");
					for (String typee : tp) {

						List<BenDiMBbean> bb = benDiMBbeanDao.queryBuilder().where(BenDiMBbeanDao.Properties.SubType.eq(typee)).list();
						if (bb != null) {
							int size = bb.size();
							for (int i = 0; i < size; i++) {
								//删掉相同身份的，保证只有一种最新的身份
								benDiMBbeanDao.delete(bb.get(i));
							}
						}

						p1=renShu.getContent().getBottemImageUrl().substring(1,renShu.getContent().getBottemImageUrl().length()-1);
						String p2=renShu.getContent().getPopupImageUrl().substring(1,renShu.getContent().getPopupImageUrl().length()-1);

						BenDiMBbean benDiMBbean=new BenDiMBbean();
						benDiMBbean.setId(System.currentTimeMillis());
						benDiMBbean.setBottemImageUrl(p1);
						benDiMBbean.setPopupImageUrl(p2);
						benDiMBbean.setWelcomeSpeak(renShu.getContent().getWelcomeSpeak());
						benDiMBbean.setSubType(typee);
						benDiMBbean.setPhoto_index(renShu.getContent().getPhoto_index());
						benDiMBbeanDao.insert(benDiMBbean);
						baoCunBean.setWenzi(p1);
					}

					List<BenDiMBbean> f=  benDiMBbeanDao.loadAll();
					for (BenDiMBbean ll:f){
						Log.d(TAG, "ll.getPhoto_index():" + ll.getPhoto_index()+ll.getSubType());
					}

					baoCunBeanDao.update(baoCunBean);
					Intent intent2=new Intent("gxshipingdizhi");
					intent2.putExtra("bgPath",p1);
					context.sendBroadcast(intent2);
					Log.d(TAG, "推送过去");
				}
				if (jsonObject.get("title").getAsString().equals("zip包下载地址")){
					FileDownloader.setup(context);
					isDW=true;
					Thread.sleep(1200);
					baoCunBean.setZhanhuiId(jsonObject.get("content").getAsJsonObject().get("id").getAsInt()+"");
					baoCunBean.setGonggao(jsonObject.get("content").getAsJsonObject().get("screenId").getAsInt()+"");
					baoCunBeanDao.update(baoCunBean);

					Intent intent2=new Intent("gxshipingdizhi");
					context.sendBroadcast(intent2);

					path2 =baoCunBean.getHoutaiDiZhi()+jsonObject.get("content").getAsJsonObject().get("zip_url").getAsString();
					Log.d(TAG, path2);
					File file = new File(SDPATH);
					if (!file.exists()) {
						Log.d(TAG, "file.mkdirs():" + file.mkdirs());
					}
					if (isDW) {
						isDW=false;
						Log.d(TAG, "进入下载");
						FileDownloader.getImpl().create(path2)
								.setPath(SDPATH + File.separator + System.currentTimeMillis() + ".zip")
								.setListener(new FileDownloadListener() {
									@Override
									protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
										Log.d(TAG, "pending"+soFarBytes);

									}

									@Override
									protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
										//已经连接上
										Log.d(TAG, "isContinue:" + isContinue);

										}

									@Override
									protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
										Log.d(TAG, "soFarBytes:" + soFarBytes+task.getUrl());
										//进度
										isDW=false;
										if (task.getUrl().equals(path2)){
										//	Log.d(TAG, totalBytes+"KB");
											showNotifictionIcon(context,((float)soFarBytes/(float) totalBytes)*100,"下载中","下载人脸库中"+((float)soFarBytes/(float) totalBytes)*100+"%");
										}
									}

									@Override
									protected void blockComplete(BaseDownloadTask task) {
										//完成

									}

									@Override
									protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
									//重试
										Log.d(TAG, ex.getMessage()+"重试 "+retryingTimes);


									}

									@Override
									protected void completed(BaseDownloadTask task) {
										//完成整个下载过程
										if (task.getUrl().equals(path2)){
											isDW=true;
											String ss=SDPATH+File.separator+(task.getFilename().substring(0,task.getFilename().length()-4));
											File file = new File(ss);
											if (!file.exists()) {
												Log.d(TAG, "创建文件状态:" + file.mkdir());
											}
											showNotifictionIcon(context,0,"解压中","解压人脸库中");
											jieya(SDPATH+File.separator+task.getFilename(),ss);

											Log.d(TAG, "task.isRunning():" + task.isRunning()+ task.getFilename());
											if (baoCunBean!=null && baoCunBean.getZhanghuId()!=null)
												link_uplodexiazai();
										}
									}

									@Override
									protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

									}

									@Override
									protected void error(BaseDownloadTask task, Throwable e) {
										//出错
										if (task.getUrl().equals(path2)){
											isDW=true;
											showNotifictionIcon(context,0,"下载失败",""+e);
											Log.d(TAG, "task.isRunning():" + task.getFilename()+"失败"+e);
										}
									}

									@Override
									protected void warn(BaseDownloadTask task) {
										//在下载队列中(正在等待/正在下载)已经存在相同下载连接与相同存储路径的任务

									}
								}).start();

					}

				}
				Gson gson=new Gson();
				TuiSongBean renShu=gson.fromJson(jsonObject,TuiSongBean.class);
				//1 新增 2修改//3是删除
				switch (renShu.getTitle()){
					case "主机管理":
						//先从老黄哪里拿主机数据。
						link_getHouTaiZhuJi(renShu.getContent().getId(),context,renShu.getContent().getStatus());
						break;
					case "设备管理":
						//先从老黄哪里拿门禁数据。
						link_getHouTaiMenJin(renShu.getContent().getId(),context,renShu.getContent().getStatus());
						break;
					case "人员管理":  //单个人员
						//先从老黄哪里拿人员数据。
						link_getHouTaiDanRen(renShu.getContent().getId(),context,renShu.getContent().getStatus());
						break;
					case "人员列表管理":
						//先从老黄哪里拿批量人员数据。
						baoCunBean.setHuiyiId(renShu.getContent().getId()+"");
						baoCunBeanDao.update(baoCunBean);
						link_getHouTaiPiLiang(renShu.getContent().getId(),context,renShu.getContent().getStatus());

						Intent intent2=new Intent("gxshipingdizhi");
						context.sendBroadcast(intent2);
						break;

				}
			//	processCustomMessage(context, bundle);

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);


			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");

				//打开自定义的Activity
//				Intent i = new Intent(context, TestActivity.class);
//				i.putExtras(bundle);
//				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//				context.startActivity(i);

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Logger.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){
			Logger.d(TAG, "[MyReceiver] 抛出了异常"+e.getMessage());
		}
	}


//	// 打印所有的 intent extra 数据
//	private static String printBundle(Bundle bundle) {
//		StringBuilder sb = new StringBuilder();
//		for (String key : bundle.keySet()) {
//			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
//				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
//			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
//				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
//			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
//				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
//					Logger.i(TAG, "This message has no Extra data");
//					continue;
//				}
//
//				try {
//					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
//					Iterator<String> it =  json.keys();
//
//					while (it.hasNext()) {
//						String myKey = it.next();
//						sb.append("\nkey:" + key + ", value: [" +
//								myKey + " - " +json.optString(myKey) + "]");
//					}
//				} catch (JSONException e) {
//					Logger.e(TAG, "Get message extra JSON error!");
//				}
//
//			} else {
//				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
//			}
//		}
//		return sb.toString();
//	}

	//从老黄后台拿主机信息
	private void link_getHouTaiZhuJi(int id, final Context context, final int status){
		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		OkHttpClient okHttpClient=  new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();

		RequestBody body = new FormBody.Builder()
				.add("id",id+"")
				.build();
		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(body)
				.url(baoCunBean.getHoutaiDiZhi()+"/getEntity.do");

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
					Gson gson=new Gson();
					ZhuJiBeanH zhaoPianBean=gson.fromJson(jsonObject,ZhuJiBeanH.class);
					//ws://192.168.2.58:9000/video
					String s1=zhaoPianBean.getHostUrl();
					if (s1.contains("//")){
						String s=s1.split("//")[1];
						baoCunBean.setTouxiangzhuji(zhaoPianBean.getHostUrl());
						baoCunBean.setZhujiDiZhi("ws://"+s+":9000/video");
						baoCunBeanDao.update(baoCunBean);
					}
					zhuJiBeanHDao.deleteAll();
					zhuJiBeanHDao.insert(zhaoPianBean);
					Log.d("hhhhh", zhaoPianBean.getHostUrl());
				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}
			}
		});
	}



	public static void showNotifictionIcon(Context context,float p,String title,String contextss) {
		//Log.d(TAG, "尽量");
		ToastUtils.getInstances().showDialog(title,contextss, (int) p);

//		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//		//Intent intent = new Intent(context, XXXActivity.class);//将要跳转的界面
//		Intent intent = new Intent();//只显示通知，无页面跳转
//		builder.setAutoCancel(true);//点击后消失
//		builder.setSmallIcon(R.drawable.huiyi_logo);//设置通知栏消息标题的头像
//		//builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
//		builder.setContentTitle(title);
//		builder.setContentText(contextss);
//		builder.setProgress(100, (int) p,false);
//		builder.setDefaults(Notification.DEFAULT_LIGHTS); //设置通知的提醒方式： 呼吸灯
//		builder.setPriority(NotificationCompat.PRIORITY_MAX); //设置通知的优先级：最大
//		//利用PendingIntent来包装我们的intent对象,使其延迟跳转
//		PendingIntent intentPend = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//		builder.setContentIntent(intentPend);
//		NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//		manager.notify(0, builder.build());
	}

	private void jieya(String pathZip,String path222){

		ZipFile zipFile=null;
		List fileHeaderList=null;
		try {
			// Initiate ZipFile object with the path/name of the zip file.
			zipFile = new ZipFile(pathZip);
			zipFile.setFileNameCharset("GBK");
			fileHeaderList = zipFile.getFileHeaders();
			// Loop through the file headers
			Log.d(TAG, "fileHeaderList.size():" + fileHeaderList.size());

			for (int i = 0; i < fileHeaderList.size(); i++) {
				FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
				FileHeader fileHeader2 = (FileHeader) fileHeaderList.get(0);

				Log.d(TAG, fileHeader2.getFileName());

				if (fileHeader.getFileName().contains(".xml")){
					zipFile.extractFile( fileHeader.getFileName(), path222);
					Log.d(TAG, "找到了"+i+"张照片");
				}
				// Various other properties are available in FileHeader. Please have a look at FileHeader
				// class to see all the properties
			}
		} catch (final ZipException e) {

			showNotifictionIcon(context,0,"解压失败",e.getMessage()+"");
		}
		//   UnZipfile.getInstance(SheZhiActivity.this).unZip(zz,trg,zipHandler);

		//拿到XML
		showNotifictionIcon(context,0,"解析XML中","解析XML中。。。。");
		List<String> xmls=new ArrayList<>();
		final List<String> xmlList= FileUtil.getAllFileXml(path222,xmls);
		if (xmlList==null || xmlList.size()==0){
			return;
		}
		//解析XML
		try {
			FileInputStream fin=new FileInputStream(xmlList.get(0));
			//Log.d("SheZhiActivity", "fin:" + fin);
			List<Subject> subjectList=  pull2xml(fin);
			Log.d(TAG, "XMLList值:" + subjectList);
			if (subjectList!=null && subjectList.size()>0){
				//排序
				Collections.sort(subjectList, new Subject());
				Log.d("SheZhiActivity", "解析成功,文件个数:"+subjectList.size());
				if (zipFile!=null){
					zipFile.setRunInThread(true); // true 在子线程中进行解压 ,
					// false主线程中解压
					zipFile.extractAll(path222); // 将压缩文件解压到filePath中..
				}
				zhuJiBeanH=zhuJiBeanHDao.loadAll().get(0);
				//先登录旷视
				if (zhuJiBeanH.getUsername()!=null && zhuJiBeanH.getPwd()!=null){
					getOkHttpClient3(subjectList,path222);

				}else {
					showNotifictionIcon(context,0,"旷视账号为空","密码为空");
				}

				final int size= subjectList.size();
				Log.d("ffffff", "size:" + size);

			}else {
				showNotifictionIcon(context,0,"解析失败","人脸库XML解析失败");

			}

		} catch (Exception e) {
			showNotifictionIcon(context,0,"解析失败","人脸库XML解析异常");
			Log.d("SheZhiActivity", e.getMessage()+"解析XML异常");
		}

}

//首先登录
	public void getOkHttpClient3(final List<Subject> subjectList, final String trg){
		zhuJiBeanH=zhuJiBeanHDao.loadAll().get(0);
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
							Log.d(TAG, "i:" + j);
							while (true){
								try {
									Thread.sleep(100);
									t++;
									// 获取后缀名
									//String sname = name.substring(name.lastIindexOf("."));
									filePath=trg+File.separator+subjectList.get(j).getId()+(subjectList.get(j).getPhoto().
											substring(subjectList.get(j).getPhoto().lastIndexOf(".")));
									File file=new File(filePath);
									if ((file.isFile() && file.length()>0)|| t==4000){
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
							Log.d(TAG, "文件存在");

							//  Log.d("SheZhiActivity", "循环到"+j);

							showNotifictionIcon(context, (int) ((j / (float) size) * 100),"入库中","入库中"+(int) ((j / (float) size) * 100)+"%");

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

						String ss=stringBuilder2.toString();

						if (ss.length()>0){

							try {
								FileUtil.savaFileToSD("失败记录"+DateUtils.timesOne(System.currentTimeMillis()+"")+".txt",ss);
								showNotifictionIcon(context, 0,"入库完成","有失败的记录,已保存到本地根目录");
								stringBuilder2.delete(0, stringBuilder2.length());
							} catch (Exception e) {
								e.printStackTrace();
							}

						}else {
							showNotifictionIcon(context, 0,"入库完成","全部入库成功，没有失败记录");
						}

					} else {
						showNotifictionIcon(context, 0,"登陆失败","登陆失败");
					}


				}catch(Exception e) {
					showNotifictionIcon(context, 0,"登陆异常",""+e.getMessage());
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

				stringBuilder2.append("上传图片失败记录:")
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
						stringBuilder2.append("上传图片失败记录:")
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
					stringBuilder2.append("上传图片失败记录:").append("ID").
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

	//查询人员//批量的
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
				stringBuilder2.append("查询旷视失败记录:").append("ID:")
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
								//Log.d("MyReceiver", "222");
							}
							else {
								//相同就不需要再往下比对了，跳出当前循环
								pp=1;
								//更新旷视人员信息//先传图片
							//	Log.d("MyReceiver", "jjjj:" + zhaoPianBean.getData().get(i).getPhotos().get(0).getIdX());
								if (zhaoPianBean.getData().get(i).getPhotos().size()>0)
								subject.setLingshiZPID(zhaoPianBean.getData().get(i).getPhotos().get(0).getIdX());
								link_P1(zhuJiBeanH,filePath,subject,zhaoPianBean.getData().get(i).getId());
								//Log.d("MyReceiver", "333");
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
					stringBuilder2.append("查询旷视失败记录:").append("ID:")
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


	//查询单个人员
	private void link_chaXunRenYuan2(final OkHttpClient okHttpClient, final RenYuanInFo renYuanInFo, final int ii){
		Log.d(TAG, "单个人员招聘id:" + ii);
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
				.url(zhuJiBeanH.getHostUrl()+"/mobile-admin/subjects/list?category=employee&name="+renYuanInFo.getName());

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				link_XiuGaiRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,ii);
				Log.d("AllConnects查询旷视", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				//  Log.d("AllConnects查询旷视", "请求成功"+call.request().toString());
				//获得返回体
				try{

					ResponseBody body = response.body();
					String ss=body.string().trim();
					Log.d("MyReceivereee", "查询旷视单个人员"+ss);
					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					Gson gson = new Gson();
					GaiNiMaBi zhaoPianBean = gson.fromJson(jsonObject, GaiNiMaBi.class);
					if (zhaoPianBean.getData()!=null){
						int size=zhaoPianBean.getData().size();
						if (size==0){

							link_XiuGaiRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,ii);
						}
						int pp=-1;
						for (int i=0;i<size;i++){
							//相同就更新
							if (!zhaoPianBean.getData().get(i).getJob_number().equals(renYuanInFo.getId()+"")){
								//跟所有人都不同， 再新增
								pp=0;
								Log.d("MyReceiver", "222");
							}
							else {
								//相同就不需要再往下比对了，跳出当前循环
								pp=1;
								//更新旷视人员信息//先传图片
								renYuanInFo.setSid(zhaoPianBean.getData().get(i).getId());
								link_XiuGaiRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,ii);
								Log.d("MyReceiver", "333");
								break;
							}
						}
						if (pp==0){
							//跟所有人都不同， 再新增
							link_XiuGaiRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,ii);
						}

					}else {
						//	Log.d("MyReceiver", "444");
						//先传图片
						link_XiuGaiRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,ii);
					}


				}catch (Exception e){

					Log.d("AllConnects查询旷视异常", e.getMessage()+"gggg");

				}


			}
		});
	}

	//修改人员
	private void link_XiuGaiRenYuan(final OkHttpClient okHttpClient, final Subject renYuanInFo, int i, int id){
		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		//Log.d("hhhhhhhhhhh", renYuanInFo.toString());
		JSONObject json = new JSONObject();
		try {
			JSONArray jsonArray= new JSONArray();
			jsonArray.put(i);
			json.put("subject_type","0");
			json.put("name",renYuanInFo.getName());
			json.put("remark",renYuanInFo.getRemark());
			if (i!=0){
				Log.d(TAG, "i:" + i);
				json.put("photo_ids",jsonArray);
			}else {
				JSONArray jsonArray2= new JSONArray();
				jsonArray2.put(renYuanInFo.getLingshiZPID());
				json.put("photo_ids",jsonArray2);
				//Log.d(TAG, "renYuanInFo.getLingshiZPID():" + renYuanInFo.getLingshiZPID());
			}
			json.put("phone",renYuanInFo.getPhone());
			json.put("department",renYuanInFo.getDepartment());
			json.put("title",renYuanInFo.getTitle());
			json.put("job_number", renYuanInFo.getId());
			json.put("description", renYuanInFo.getSourceMeeting());

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
				stringBuilder2.append("修改人员失败记录:").append("ID:")
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
					stringBuilder2.append("修改人员失败记录:").append("ID:")
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
		//Log.d("hhhhhhhhhh", subject.toString());
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
			json.put("department",subject.getDepartment());
			json.put("title",subject.getTitle());
			json.put("job_number", subject.getId());
			json.put("description", subject.getSourceMeeting());
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
				stringBuilder2.append("创建人员失败记录:").append("ID").append(subject.getId()).append("姓名:")
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
						stringBuilder2.append("创建人员失败记录:").append("ID").append(subject.getId()).append("姓名:")
								.append(subject.getName()).append("时间:").append(DateUtils.time(System.currentTimeMillis()+"")).append("\n");
					}
				}catch (Exception e){
					stringBuilder2.append("创建人员失败记录:").append("ID").append(subject.getId()).append("姓名:")
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


	public List<Subject> pull2xml(InputStream is) throws Exception {
		Log.d(TAG, "jiexi 111");
		List<Subject> list  = new ArrayList<>();;
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
							Log.d(TAG, "jiexi222 ");
							showNotifictionIcon(context,0,"解析XML失败","xml账户ID不匹配");
							Log.d(TAG, "333jiexi ");
							return null;
						}

					} else if ("Subject".equals(parser.getName())) {

						student=new Subject();
						student.setId(parser.getAttributeValue(0));

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
							student.setComeFrom(URLDecoder.decode(nickName, "UTF-8"));
						}
					}
					else if ("interviewee".equals(parser.getName())) {
						//获取nickName值
						String nickName = parser.nextText();
						if (nickName!=null){
							student.setInterviewee(URLDecoder.decode(nickName, "UTF-8"));
						}
					}
					else if ("city".equals(parser.getName())) {
						//获取nickName值
						String nickName = parser.nextText();
						if (nickName!=null){
							student.setCity(URLDecoder.decode(nickName, "UTF-8"));
						}
					}
					else if ("department".equals(parser.getName())) {
						//获取nickName值
						String nickName = parser.nextText();
						if (nickName!=null){
							student.setDepartment(URLDecoder.decode(nickName, "UTF-8"));
						}
					}
					else if ("email".equals(parser.getName())) {
						//获取nickName值
						String nickName = parser.nextText();
						if (nickName!=null){
							student.setEmail(URLDecoder.decode(nickName, "UTF-8"));
						}
					}
					else if ("title".equals(parser.getName())) {
						//获取nickName值
						String nickName = parser.nextText();
						if (nickName!=null){
							student.setTitle(URLDecoder.decode(nickName, "UTF-8"));
						}
					}
					else if ("location".equals(parser.getName())) {
						//获取nickName值
						String nickName = parser.nextText();
						if (nickName!=null){
							student.setLocation(URLDecoder.decode(nickName, "UTF-8"));
						}
					}
					else if ("assemblyId".equals(parser.getName())) {
						//获取nickName值
						String nickName = parser.nextText();
						if (nickName!=null){
							student.setAssemblyId(URLDecoder.decode(nickName, "UTF-8"));
						}
					}
					else if ("sourceMeeting".equals(parser.getName())) {
						//获取nickName值
						String nickName = parser.nextText();
						if (nickName!=null){
							student.setSourceMeeting(URLDecoder.decode(nickName, "UTF-8"));
						}
					}
					else if ("remark".equals(parser.getName())) {
						//获取nickName值
						String nickName = parser.nextText();
						if (nickName!=null){
							student.setRemark(URLDecoder.decode(nickName, "UTF-8"));
						}
					}
					else if ("photo".equals(parser.getName())) {
						//获取nickName值
						String nickName = parser.nextText();
						if (nickName!=null){
							student.setPhoto(URLDecoder.decode(nickName, "UTF-8"));
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


	//从老黄后台拿门禁信息
	private void link_getHouTaiMenJin(int id, final Context context, final int status){
		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		OkHttpClient okHttpClient=  new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();

		RequestBody body = new FormBody.Builder()
				.add("id",id+"")
				.build();
		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(body)
				.url(baoCunBean.getHoutaiDiZhi()+"/getEquipment.do");

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
					Log.d("AllConnects", "获取设备信息"+ss);

					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					Gson gson=new Gson();
					SheBeiInFoBean zhaoPianBean=gson.fromJson(jsonObject,SheBeiInFoBean.class);
					baoCunBean.setShipingIP(zhaoPianBean.getCamera_address());
					baoCunBean.setShiPingWeiZhi(zhaoPianBean.getCamera_position());
					baoCunBeanDao.update(baoCunBean);

					//保存到本地
					if (sheBeiInFoBeanDao.load(zhaoPianBean.getId())==null){
						//新增
						sheBeiInFoBeanDao.insert(zhaoPianBean);
					}
					//先登陆
					getOkHttpClient(context,status,zhaoPianBean, null);


				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}
			}
		});
	}

	//从老黄后台拿单人信息
	private void link_getHouTaiDanRen(int id, final Context context, final int status){
		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		OkHttpClient okHttpClient=  new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();

		RequestBody body = new FormBody.Builder()
				.add("id",id+"")
				.build();
		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(body)
				.url(baoCunBean.getHoutaiDiZhi()+"/getAppSubject.do");

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
					Log.d("AllConnects", "获取单人信息"+ss);

					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					Gson gson=new Gson();
					RenYuanInFo renYuanInFo=gson.fromJson(jsonObject,RenYuanInFo.class);

					//保存到本地
//					if (sheBeiInFoBeanDao.load(zhaoPianBean.getId())==null){
//						//新增
//						sheBeiInFoBeanDao.insert(zhaoPianBean);
//					}
					//先登陆
					getOkHttpClient(context,status,null,renYuanInFo);
					Log.d("MyReceiver", "登陆");

				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}
			}
		});
	}

		//首先登录-->获取所有主机-->创建或者删除或者更新门禁
		private void getOkHttpClient(final Context context, final int status, final SheBeiInFoBean zhaoPianBean, final RenYuanInFo renYuanInFo){
		 zhuJiBeanH=zhuJiBeanHDao.loadAll().get(0);
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

			Call mcall= okHttpClient.newCall(request);
			mcall.enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {

					Log.d(TAG, "登陆失败"+e.getMessage());
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					JsonObject jsonObject=null;
					try {
						String s=response.body().string();
						Log.d(TAG, "123   "+s);
						 jsonObject= GsonUtil.parse(s).getAsJsonObject();
					}catch (Exception e){

						Log.d(TAG, e.getMessage()+"");
						return;
					}
					int i=1;
					i=jsonObject.get("code").getAsInt();
					if (i==0){
						//登录成功,后续的连接操作因为cookies 原因,要用 MyApplication.okHttpClient
						MyApplication.okHttpClient=okHttpClient;
						if (zhaoPianBean!=null){
							//对设备的操作 增删该等等
							link_getALLzhuji(MyApplication.okHttpClient,context,status,zhuJiBeanH,zhaoPianBean);
						}
						if (renYuanInFo!=null){
							//对人员的操作 增删该等等
							switch (status){
								case 1:
									//先传图片，再新增单个人员
									Bitmap bitmap1=null;
									try {
										  bitmap1 = Glide.with(context)
                                                .load(baoCunBean.getHoutaiDiZhi()+"/upload/compare/"+renYuanInFo.getPhoto_ids())
                                                .asBitmap()
                                                // .sizeMultiplier(0.5f)
                                                .fitCenter()
                                                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                                .get();
									} catch (InterruptedException | ExecutionException e) {
										e.printStackTrace();
									}
									if (bitmap1!=null){
										link_P1(zhuJiBeanH,compressImage(bitmap1),renYuanInFo,context);
									}else {
										link_addRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
									}

									break;
								case 2:
							//修改人员
									Bitmap bitmap=null;
									try {
										  bitmap = Glide.with(context)
												.load(baoCunBean.getHoutaiDiZhi()+"/upload/compare/"+renYuanInFo.getPhoto_ids())
												.asBitmap()
												// .sizeMultiplier(0.5f)
												.fitCenter()
												.into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
												.get();
									} catch (InterruptedException | ExecutionException e) {
										e.printStackTrace();
									}
									if (bitmap!=null){

										link_P2(zhuJiBeanH,compressImage(bitmap),renYuanInFo,context);

									}else {
										link_chaXunRenYuan2(okHttpClient,renYuanInFo,0);
										//link_XiuGaiRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
									}
									break;
								case 3:
									//删除人员
									link_ShanChuRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo);
									break;
							}

						}

					}else {
						Intent intent=new Intent("gxshipingdizhi");
						intent.putExtra("date","登录失败");
						context.sendBroadcast(intent);
					}

				}
			});


	}

	/**
	 * 压缩图片（质量压缩）
	 * @param bitmap
	 */
	public static File compressImage(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			options -= 10;//每次都减少10
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			//long length = baos.toByteArray().length;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date(System.currentTimeMillis());
		String filename = format.format(date);
		File file = new File(Environment.getExternalStorageDirectory(),filename+".png");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			try {
				fos.write(baos.toByteArray());
				fos.flush();
				fos.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	//	recycleBitmap(bitmap);
		return file;
	}
//	public static void recycleBitmap(Bitmap... bitmaps) {
//		if (bitmaps==null) {
//			return;
//		}
//		for (Bitmap bm : bitmaps) {
//			if (null != bm && !bm.isRecycled()) {
//				bm.recycle();
//			}
//		}
//	}

	private void link_XinZengMenJing(OkHttpClient okHttpClient, ZhuJiBeanH zhuji, final Context context, String box_token, final SheBeiInFoBean zhaoPianBean){

		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");


		RequestBody body = new FormBody.Builder()
				.add("box_id",box_token)
				.add("network_switcher","")
				.add("description","")
				.add("camera_address",zhaoPianBean.getCamera_address())
				.add("camera_position",zhaoPianBean.getCamera_position())
				.build();

		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(body)
				.url(zhuji.getHostUrl()+"/system/screen");

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
					Log.d("AllConnects", "新增门禁"+ss);

					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					JsonObject jo=jsonObject.get("data").getAsJsonObject();
					zhaoPianBean.setBid(jo.get("id").getAsInt());
					 sheBeiInFoBeanDao.update(zhaoPianBean);



				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}

			}
		});
	}


	private void link_ShanChuMenJing(final OkHttpClient okHttpClient, ZhuJiBeanH zhuji, final Context context, final String box_token, final SheBeiInFoBean zhaoPianBean, final int i){

		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		SheBeiInFoBean sheBeiInFoBean=sheBeiInFoBeanDao.load(zhaoPianBean.getId());

		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.delete()
				.url(zhuji.getHostUrl()+"/system/screen/"+sheBeiInFoBean.getBid());

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
					Log.d("AllConnects", "删除门禁"+ss);
					if (i==1){
						link_XinZengMenJing(okHttpClient,zhuJiBeanH,context,box_token,zhaoPianBean);
					}

				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}

			}
		});
	}

	//先要获取局域网内所有可用主机
	private void link_getALLzhuji(final OkHttpClient okHttpClient, final Context context, final int status, final ZhuJiBeanH zhuJiBeanH, final SheBeiInFoBean zhaoPianBean){

		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.get()
				.url(zhuJiBeanH.getHostUrl()+"/system/boxes");

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
					JsonArray jsonArray=jsonObject.get("data").getAsJsonArray();
				    JsonObject  joo=jsonArray.get(0).getAsJsonObject();
				    String box_token=joo.get("id").getAsString();
					if (box_token!=null){
						switch (status){
							case 1:
								//新增
								link_XinZengMenJing(okHttpClient,zhuJiBeanH,context,box_token,zhaoPianBean);
								break;
							case 2:
								//修改
								link_ShanChuMenJing(okHttpClient,zhuJiBeanH,context,box_token,zhaoPianBean,1);
								break;
							case 3:
								//删除
								link_ShanChuMenJing(okHttpClient,zhuJiBeanH,context,box_token,zhaoPianBean,2);
								break;

						}
					}


				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}

			}
		});
	}


	//创建人员
	private void link_addRenYuan(final OkHttpClient okHttpClient, final Context contex, final ZhuJiBeanH zhuJiBeanH, final RenYuanInFo renYuanInFo, int i){

		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

		JSONObject json = new JSONObject();
			try {
				JSONArray jsonArray= new JSONArray();
				jsonArray.put(i);
				json.put("subject_type","0");
				json.put("name",renYuanInFo.getName());
				json.put("remark",renYuanInFo.getRemark());
				json.put("photo_ids",jsonArray);
				json.put("phone",renYuanInFo.getPhone());
				json.put("department",renYuanInFo.getDepartment());
				json.put("title",renYuanInFo.getTitle());
				json.put("job_number", renYuanInFo.getId());
				json.put("description", renYuanInFo.getSourceMeeting());

			} catch (JSONException e) {
				e.printStackTrace();
			}
		Log.d(TAG, json.toString());
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

				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{
					ResponseBody body = response.body();
					String ss=body.string().trim();
					Log.d("AllConnects", "创建人员"+ss);
					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					if (jsonObject.get("code").getAsInt()==0){
						JsonObject jo=jsonObject.get("data").getAsJsonObject();
						renYuanInFo.setSid(jo.get("id").getAsInt());
						renYuanInFoDao.insert(renYuanInFo);
					}
				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}

			}
		});
	}

	//提交下载状态
	private void link_uplodexiazai(){

		//	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		OkHttpClient okHttpClient= MyApplication.getOkHttpClient();
		//RequestBody requestBody = RequestBody.create(JSON, json);
		RequestBody body = new FormBody.Builder()
				.add("id",baoCunBean.getZhanhuiId())
				.add("downloads","1")
				.build();
		Log.d(TAG, baoCunBean.getZhanhuiId()+"展会id");
		Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.header("user-agent","Koala Admin")
				//.post(requestBody)
				//.get()
				.post(body)
				.url(baoCunBean.getHoutaiDiZhi()+"/appSaveExDownloads.do");

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
					Log.d("AllConnects", "上传下载次数状态"+ss);

//					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//					Gson gson=new Gson();
//					final HuiYiInFoBean renShu=gson.fromJson(jsonObject,HuiYiInFoBean.class);


				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"ttttt");
				}

			}
		});
	}


	//修改人员
	private void link_XiuGaiRenYuan(final OkHttpClient okHttpClient, final Context contex, final ZhuJiBeanH zhuJiBeanH, final RenYuanInFo renYuanInFo, int i){
		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		Log.d("ggggggggggg", renYuanInFo.toString());
		JSONObject json = new JSONObject();
		try {
			JSONArray jsonArray= new JSONArray();
			jsonArray.put(i);
			json.put("subject_type","0");
			json.put("name",renYuanInFo.getName());
			json.put("remark",renYuanInFo.getRemark());
			json.put("photo_ids",jsonArray);
			json.put("phone",renYuanInFo.getPhone());
			json.put("department",renYuanInFo.getDepartment());
			json.put("title",renYuanInFo.getTitle());
			json.put("job_number", renYuanInFo.getId());
			json.put("gender", renYuanInFo.getGender());
			json.put("description", renYuanInFo.getSourceMeeting());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.d(TAG, json.toString());
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
		//RenYuanInFo renYuanInFo1= renYuanInFoDao.load(renYuanInFo.getId());

		Request.Builder requestBuilder = new Request.Builder()
				//.header("Content-Type", "application/json")
				.put(requestBody)
				.url(zhuJiBeanH.getHostUrl()+"/subject/"+renYuanInFo.getSid());

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				stringBuilder.append("更新人员失败记录:")
						.append("ID").append(renYuanInFo.getId())
						.append("姓名:").append(renYuanInFo.getName())
						.append("时间:")
						.append(DateUtils.time(System.currentTimeMillis()+""))
						.append("\n");
				isA=false;
				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{
					ResponseBody body = response.body();
					String ss=body.string().trim();
					Log.d("AllConnects", "修该人员"+ss);
					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					if (jsonObject.get("code").getAsInt()==0){
						stringBuilder.append("更新人员失败记录:")
								.append("ID").append(renYuanInFo.getId())
								.append("姓名:").append(renYuanInFo.getName())
								.append("时间:")
								.append(DateUtils.time(System.currentTimeMillis()+""))
								.append("\n");
					}
				}catch (Exception e){
					stringBuilder.append("更新人员失败记录:")
							.append("ID").append(renYuanInFo.getId())
							.append("姓名:").append(renYuanInFo.getName())
							.append("时间:")
							.append(DateUtils.time(System.currentTimeMillis()+""))
							.append("\n");
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}finally {
					isA=false;
				}

			}
		});
	}

	//删除人员
	private void link_ShanChuRenYuan(final OkHttpClient okHttpClient, final Context contex, final ZhuJiBeanH zhuJiBeanH, final RenYuanInFo renYuanInFo){

		RenYuanInFo renYuanInFo1= renYuanInFoDao.load(renYuanInFo.getId());

		Request.Builder requestBuilder = new Request.Builder()
				//.header("Content-Type", "application/json")
				.delete()
				.url(zhuJiBeanH.getHostUrl()+"/subject/"+(renYuanInFo1==null?"":renYuanInFo1.getSid()));

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
					Log.d("AllConnects", "删除人员"+ss);
//					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//					if (jsonObject.get("code").getAsInt()==0){
//						JsonObject jo=jsonObject.get("data").getAsJsonObject();
//						renYuanInFo.setSid(jo.get("id").getAsInt());
//						renYuanInFoDao.insert(renYuanInFo);
//					}
				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}

			}
		});
	}

	private void link_P1(final ZhuJiBeanH zhuJiBeanH, final File file, final RenYuanInFo renYuanInFo, final Context context) {

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();
		;
		MultipartBody mBody;
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

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
				link_addRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
				Log.d("AllConnects", "请求识别失败" + e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				file.delete();
				Log.d("AllConnects", "请求识别成功" + call.request().toString());
				//获得返回体
				try {
					ResponseBody body = response.body();
					String ss = body.string();

					Log.d("AllConnects", "传照片" + ss);
					int ii=0;
					JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
					JsonObject jo=jsonObject.get("data").getAsJsonObject();
					ii=jo.get("id").getAsInt();
					if (ii!=0){
						link_addRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,ii);
					}else {
						link_addRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
					}
				} catch (Exception e) {
					link_addRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
					Log.d("WebsocketPushMsg2", e.getMessage());
				}
			}
		});

	}
	public static final int TIMEOUT = 1000 * 150;
	private void link_P2(final ZhuJiBeanH zhuJiBeanH, final File file, final RenYuanInFo renYuanInFo, final Context context) {

		final OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();
		;
		MultipartBody mBody;
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

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
				link_chaXunRenYuan2(okHttpClient,renYuanInFo,0);

			//
				Log.d("AllConnects", "请求识别失败" + e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				file.delete();
				Log.d("AllConnects", "请求识别成功" + call.request().toString());
				//获得返回体
				try {
					ResponseBody body = response.body();
					String ss = body.string();

					Log.d("AllConnects", "传照片单个人员" + ss);
					int ii=0;
					JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
					JsonObject jo=jsonObject.get("data").getAsJsonObject();
					ii=jo.get("id").getAsInt();
					if (ii!=0){
					//	link_XiuGaiRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,ii);
						link_chaXunRenYuan2(okHttpClient,renYuanInFo,ii);

					}else {
						//link_XiuGaiRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
						link_chaXunRenYuan2(okHttpClient,renYuanInFo,0);
					}
				} catch (Exception e) {
					//link_XiuGaiRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
					link_chaXunRenYuan2(okHttpClient,renYuanInFo,0);
					Log.d("WebsocketPushMsg1", e.getMessage());
				}
			}
		});
	}


	//	<----------------------------------------------------------------------------------------------------------------------->
	//批量人员操作

	//从老黄后台拿批量信息
	private void link_getHouTaiPiLiang(final int id, final Context context, final int status){
		if (status==3){
			//删除
			BenDiQianDaoDao dao= MyApplication.myApplication.getDaoSession().getBenDiQianDaoDao();
			dao.deleteAll();
			getOkHttpClient2(context,3);

		}else {
			//新增
			final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
			OkHttpClient okHttpClient = new OkHttpClient.Builder()
					.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
					.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
					.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.cookieJar(new CookiesManager())
					.retryOnConnectionFailure(true)
					.build();

			RequestBody body = new FormBody.Builder()
					.add("companyName", baoCunBean.getZhanghuId())
					.add("cardNumber", id + "")
					.build();
			Request.Builder requestBuilder = new Request.Builder()
					.header("Content-Type", "application/json")
					.post(body)
					.url(baoCunBean.getHoutaiDiZhi() + "/queryMeetingSubject.do");

			// step 3：创建 Call 对象
			Call call = okHttpClient.newCall(requestBuilder.build());

			//step 4: 开始异步请求
			call.enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					Log.d("AllConnects", "请求失败" + e.getMessage());
					stringBuilder.append("从后台获取人员信息失败记录:")
							.append("ID").append(id)
							.append("时间:")
							.append(DateUtils.time(System.currentTimeMillis()+""))
							.append("\n");
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					Log.d("AllConnects", "请求成功" + call.request().toString());
					//获得返回体
					try {
						//没了删除，所有在添加前要删掉所有
						renYuanInFoDao.deleteAll();
						ResponseBody body = response.body();
						String ss = body.string().trim();
						Log.d("AllConnects", "获取批量人员信息" + ss);

						JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
						Gson gson = new Gson();
						PiLiangBean zhaoPianBean = gson.fromJson(jsonObject, PiLiangBean.class);
						int size = zhaoPianBean.getObjects().size();

						for (int i = 0; i < size; i++) {
							PiLiangBean.ObjectsBean bbb = zhaoPianBean.getObjects().get(i);
							RenYuanInFo inFo = new RenYuanInFo();
							inFo.setAccountId(bbb.getAccountId());
							//inFo.setCardNumber(bbb.getCardNumber());
							inFo.setDepartment(bbb.getDepartment());
							inFo.setGender(bbb.getGender());
							inFo.setId((long) bbb.getId());
							inFo.setName(bbb.getName());
							inFo.setPhone(bbb.getPhone());
							inFo.setRemark(bbb.getRemark());
							inFo.setPhoto_ids(bbb.getPhoto_ids());
							inFo.setTitle(bbb.getTitle());
							inFo.setJobStatus(bbb.getJobStatus());
							inFo.setSourceMeeting(bbb.getSourceMeeting());
							try {//保存到本地
								renYuanInFoDao.insert(inFo);
							} catch (Exception e) {
								Log.d("MyReceiver", "插入批量到本地异常" + e.getMessage());
							}
						}

						//Log.d("MyReceivereee", "DDDDD");
						//先登陆
						getOkHttpClient2(context, 1);


					} catch (Exception e) {
						stringBuilder.append("获取后台数据异常记录:")
								.append("ID").append(id)
								.append("时间:")
								.append(DateUtils.time(System.currentTimeMillis()+""))
								.append("\n");
						Log.d("WebsocketPushMsg", e.getMessage() + "gggg");
					}
				}
			});
		}
	}

	//首先登录-->获取所有主机-->创建或者删除或者更新门禁
	private void getOkHttpClient2(final Context context, final int status){
		zhuJiBeanH=zhuJiBeanHDao.loadAll().get(0);
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

		Call mcall= okHttpClient.newCall(request);
		mcall.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Intent intent=new Intent("gxshipingdizhi");
				intent.putExtra("date","登录失败");
				context.sendBroadcast(intent);
				Log.d(TAG, "登陆失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {

				String s=response.body().string();
				//Log.d(TAG, "123   "+s);
				JsonObject jsonObject= GsonUtil.parse(s).getAsJsonObject();
				int n=1;
				n=jsonObject.get("code").getAsInt();
				if (n==0){
					//登录成功,后续的连接操作因为cookies 原因,要用 MyApplication.okHttpClient
					MyApplication.okHttpClient=okHttpClient;
					List<RenYuanInFo> renYuanInFoList=renYuanInFoDao.loadAll();
					int size=renYuanInFoList.size();
				//	Log.d("MyReceiver", "size:" + size);
					if (status==3){
						//删除
						for (int i=0;i<size;i++){
							SystemClock.sleep(500);
							link_ShanChuRenYuan2(okHttpClient,context,renYuanInFoList.get(i));
						}

					}else {
					//	Log.d("MyReceiver", "图片-1");
						for (int i=0;i<size;i++){
							isA=true;
							XiaZaiTuPian(context,renYuanInFoList.get(i));
							while (isA){
//								Log.d(TAG, "d");
								int iu=0;
							}
						}
						try {
							String ss=stringBuilder.toString();
							FileUtil.savaFileToSD("失败记录"+DateUtils.timesOne(System.currentTimeMillis()+"")+".txt",ss);
							stringBuilder.delete(0, stringBuilder.length());
						} catch (Exception e) {
							e.printStackTrace();
						}

					}


				}else {
//					Intent intent=new Intent("gxshipingdizhi");
//					intent.putExtra("date","登录失败");
//					context.sendBroadcast(intent);
				}

			}
		});
	}


	//删除批量人员
	private void link_ShanChuRenYuan2(final OkHttpClient okHttpClient, final Context contex, final RenYuanInFo renYuanInFo){

		Request.Builder requestBuilder = new Request.Builder()
				//.header("Content-Type", "application/json")
				.delete()
				.url(zhuJiBeanH.getHostUrl()+"/subject/"+renYuanInFo.getSid());

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
				//Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{
					ResponseBody body = response.body();
					String ss=body.string().trim();
					renYuanInFoDao.delete(renYuanInFo);
					//Log.d("AllConnects", "批量删除人员"+ss);

//					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//					if (jsonObject.get("code").getAsInt()==0){
//						JsonObject jo=jsonObject.get("data").getAsJsonObject();
//						renYuanInFo.setSid(jo.get("id").getAsInt());
//						renYuanInFoDao.insert(renYuanInFo);
//					}
				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}

			}
		});
	}


	private void link_piliang_P2(final ZhuJiBeanH zhuJiBeanH, final File file, final RenYuanInFo renYuanInFo, final Context context) {

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();
		;
		MultipartBody mBody;
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

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

				stringBuilder.append("上传到旷视图片失败记录:")
						.append("ID").append(renYuanInFo.getId())
						.append("姓名:").append(renYuanInFo.getName())
						.append("时间:")
						.append(DateUtils.time(System.currentTimeMillis()+""))
						.append("\n");
				link_chaXunRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
				//link_addPiLiangRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
				//Log.d("AllConnects", "请求识别失败" + e.getMessage());
				//Log.d("MyReceiver", "图片3");
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				file.delete();
				//Log.d("AllConnects", "请求识别成功" + call.request().toString());
				//获得返回体
				try {
					ResponseBody body = response.body();
					String ss = body.string();

					//Log.d("AllConnects", "传照片" + ss);
					int ii=0;
					JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
					JsonObject jo=jsonObject.get("data").getAsJsonObject();
					ii=jo.get("id").getAsInt();
					if (ii!=0){
						link_chaXunRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,ii);
						//Log.d("MyReceiver", "图片4");
					}else {
						stringBuilder.append("上传到旷视图片失败记录:")
								.append("ID").append(renYuanInFo.getId())
								.append("姓名:").append(renYuanInFo.getName())
								.append("时间:")
								.append(DateUtils.time(System.currentTimeMillis()+""))
								.append("\n");
						link_chaXunRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
						//link_addPiLiangRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
						//Log.d("MyReceiver", "图片5");
					}
				} catch (Exception e) {
					//Log.d("MyReceiver", "图片6");
					stringBuilder.append("上传到旷视图片失败记录:")
							.append("ID").append(renYuanInFo.getId())
							.append("姓名:").append(renYuanInFo.getName())
							.append("时间:")
							.append(DateUtils.time(System.currentTimeMillis()+""))
							.append("\n");
					link_chaXunRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
				//	link_addPiLiangRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
					//Log.d("WebsocketPushMsg", e.getMessage());
				}
			}
		});
	}

	private void XiaZaiTuPian(Context context,RenYuanInFo renYuanInFo){
		//Log.d("MyReceiver", "图片0");
		Bitmap bitmap=null;
		try {
			  bitmap = Glide.with(context)
					.load(baoCunBean.getHoutaiDiZhi()+"/upload/compare/"+renYuanInFo.getPhoto_ids())
					.asBitmap()
					// .sizeMultiplier(0.5f)
					.fitCenter()
					.into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
					.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			stringBuilder.append("从瑞瞳后台下载图片失败记录:")
					.append("图片地址").append(baoCunBean.getHoutaiDiZhi()).append("/upload/compare/").append(renYuanInFo.getPhoto_ids())
					.append("时间:")
					.append(DateUtils.time(System.currentTimeMillis()+""))
					.append("\n");
		}

		if (bitmap!=null){

			link_piliang_P2(zhuJiBeanH,compressImage(bitmap),renYuanInFo,context);

		}else {

			Intent intent=new Intent("shoudongshuaxin");
			intent.putExtra("date","登录失败");
			context.sendBroadcast(intent);
			link_chaXunRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
			//link_addPiLiangRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
		}
	}


	//创建批量人员
	private void link_addPiLiangRenYuan(final OkHttpClient okHttpClient, final Context contex, final ZhuJiBeanH zhuJiBeanH, final RenYuanInFo renYuanInFo, int i){

		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

		JSONObject json = new JSONObject();
		try {
			JSONArray jsonArray= new JSONArray();
			jsonArray.put(i);
			json.put("subject_type","0");
			json.put("name",renYuanInFo.getName());
			json.put("remark",renYuanInFo.getRemark());
			json.put("photo_ids",jsonArray);
			json.put("phone",renYuanInFo.getPhone());
			json.put("department",renYuanInFo.getDepartment());
			json.put("title",renYuanInFo.getTitle());
			json.put("job_number", renYuanInFo.getId());
			json.put("gender", renYuanInFo.getGender());
			json.put("description", renYuanInFo.getSourceMeeting());
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
					isA=false;
				stringBuilder.append("创建人员失败记录:")
						.append("ID").append(renYuanInFo.getId())
						.append("姓名:").append(renYuanInFo.getName())
						.append("时间:")
						.append(DateUtils.time(System.currentTimeMillis()+""))
						.append("\n");

				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				//Log.d("MyReceiver", "请求成功"+call.request().toString());
				//获得返回体
				try{

					ResponseBody body = response.body();
					String ss=body.string().trim();
				//	Log.d("MyReceiver", "批量创建人员"+ss);
					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					if (jsonObject.get("code").getAsInt()==0){
						stringBuilder.append("创建人员失败记录:")
								.append("ID").append(renYuanInFo.getId())
								.append("姓名:").append(renYuanInFo.getName())
								.append("时间:")
								.append(DateUtils.time(System.currentTimeMillis()+""))
								.append("\n");
//						JsonObject jo=jsonObject.get("data").getAsJsonObject();
//						renYuanInFo.setSid(jo.get("id").getAsInt());
//						renYuanInFoDao.update(renYuanInFo);
					}
				}catch (Exception e){
					Log.d("MyReceiver", e.getMessage()+"gggg");
					stringBuilder.append("创建人员失败记录:")
							.append("ID").append(renYuanInFo.getId())
							.append("姓名:").append(renYuanInFo.getName())
							.append("时间:")
							.append(DateUtils.time(System.currentTimeMillis()+""))
							.append("\n");

				}finally {
					isA=false;
				}


			}
		});
	}

	//查询人员
	private void link_chaXunRenYuan(final OkHttpClient okHttpClient, final Context contex, final ZhuJiBeanH zhuJiBeanH, final RenYuanInFo renYuanInFo, final int i){
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
				.url(zhuJiBeanH.getHostUrl()+"/mobile-admin/subjects/list?category=employee&name="+renYuanInFo.getName());
		Log.d("MyReceivereee", renYuanInFo.getName());
		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

				stringBuilder.append("查询旷视人员失败记录:")
						.append("ID").append(renYuanInFo.getId())
						.append("姓名:").append(renYuanInFo.getName())
						.append("时间:")
						.append(DateUtils.time(System.currentTimeMillis()+""))
						.append("\n");

				isA=false;
				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("MyReceivereee", "请求成功"+call.request().toString());
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

							link_addPiLiangRenYuan(MyApplication.okHttpClient,contex,zhuJiBeanH,renYuanInFo,i);
							//Log.d("MyReceiver", "111");
						}
						int pp=-1;

						for (int i=0;i<size;i++){
							//相同不做操作
							if (!zhaoPianBean.getData().get(i).getJob_number().equals(renYuanInFo.getId()+"")){
								pp=0;
								Log.d("MyReceiver", "222");
							}else {
								pp=1;
								link_XiuGaiRenYuan(MyApplication.okHttpClient,contex,zhuJiBeanH,renYuanInFo,i);
								Log.d("MyReceiver", "333");
								break;

							}
						}

						if (pp==0){
							link_addPiLiangRenYuan(MyApplication.okHttpClient,contex,zhuJiBeanH,renYuanInFo,i);
						}


					}else {
					//	Log.d("MyReceiver", "444");
						link_addPiLiangRenYuan(MyApplication.okHttpClient,contex,zhuJiBeanH,renYuanInFo,i);
					}


				}catch (Exception e){
					Log.d("MyReceivereee", e.getMessage()+"gggg");
					stringBuilder.append("查询旷视人员失败记录:")
							.append("ID").append(renYuanInFo.getId())
							.append("姓名:").append(renYuanInFo.getName())
							.append("时间:")
							.append(DateUtils.time(System.currentTimeMillis()+""))
							.append("\n");

					isA=false;
				}


			}
		});
	}

}
