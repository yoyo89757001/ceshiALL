package com.example.advanceSuggest;

import android.Manifest;
import android.app.Activity;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;


import android.util.Log;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;
import com.lansoeditor.demo.R;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.io.FileOutputStream;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {

    private static final int PERMISSIONS_REQUEST = 1;
    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private static final String PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String PERMISSION_READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String PERMISSION_INTERNET = Manifest.permission.INTERNET;
    private static final String PERMISSION_ACCESS_WIFI_STATE = Manifest.permission.ACCESS_WIFI_STATE;
    private static final String PERMISSION_ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE;
    private String[] Permission = new String[]{PERMISSION_CAMERA, PERMISSION_WRITE_STORAGE, PERMISSION_READ_STORAGE, PERMISSION_INTERNET, PERMISSION_ACCESS_NETWORK_STATE,PERMISSION_ACCESS_WIFI_STATE};
    private   StringBuilder builder;
    private TextView t1,t2;

    private MediaPlayer mediaPlayer = null;
    private IVLCVout vlcVout = null;
    private IVLCVout.Callback callback;
    private LibVLC libvlc;
    private Media media;
    SurfaceView surfaceview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.llll);
         t1=findViewById(R.id.t1);
         t2=findViewById(R.id.t2);
        surfaceview=findViewById(R.id.surfaceview);
         builder=new StringBuilder();




        /* 申请程序所需权限 */
        if (!hasPermission()) {
            requestPermission();
        } else {
            builder.append("以太网地址: ");
            builder.append(getMacAddreth0());
            builder.append("\n");
            builder.append("wifi地址: ");
            builder.append(getMacAddrwlan0());
            try {
                savaFileToSD("地址记录"+DateUtils.timesOne(System.currentTimeMillis()+"")+".txt",builder.toString());
                Toast.makeText(MainActivity.this,"已经保存地址到sd卡根目录",Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,e.getMessage()+"",Toast.LENGTH_LONG).show();
            }

            t1.setText("以太网地址: "+getMacAddreth0());
            t2.setText("Wi-Fi地址: "+getMacAddrwlan0());
        }

     //   FileUtil.savaFileToSD("失败记录"+DateUtils.timesOne(System.currentTimeMillis()+"")+".txt",ss);
    play();


    }


    private void play() {
        if (mediaPlayer!=null){
            mediaPlayer.stop();
        }
        if (vlcVout!=null){
            vlcVout.removeCallback(callback);
            vlcVout.detachViews();
        }

        libvlc = new LibVLC(MainActivity.this);
        mediaPlayer = new MediaPlayer(libvlc);
        vlcVout = mediaPlayer.getVLCVout();

        callback = new IVLCVout.Callback() {


            @Override
            public void onNewLayout(IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {

            }

            @Override
            public void onSurfacesCreated(IVLCVout ivlcVout) {
                try {

                    if (mediaPlayer != null ) {

                        media = new Media(libvlc, Uri.parse("rtsp://admin:admin888@192.168.2.41:554/h264/ch0/main/av_stream"));
                        mediaPlayer.setMedia(media);
                        mediaPlayer.play();

                    }

                } catch (Exception e) {
                    Log.d("vlc-newlayout", e.toString());
                }
            }

            @Override
            public void onSurfacesDestroyed(IVLCVout ivlcVout) {

            }

            @Override
            public void onHardwareAccelerationError(IVLCVout vlcVout) {

            }

        };

        vlcVout.addCallback(callback);
        vlcVout.setVideoView(surfaceview);
        vlcVout.attachViews();

    }





    /* 判断程序是否有所需权限 android22以上需要自申请权限 */
    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(PERMISSION_CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(PERMISSION_READ_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(PERMISSION_WRITE_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(PERMISSION_INTERNET) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(PERMISSION_ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED&&
                    checkSelfPermission(PERMISSION_ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    /* 请求程序所需权限 */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(Permission, PERMISSIONS_REQUEST);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST) {
            boolean granted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED)
                    granted = false;
            }
            if (!granted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    if (!shouldShowRequestPermissionRationale(PERMISSION_CAMERA)
                            || !shouldShowRequestPermissionRationale(PERMISSION_READ_STORAGE)
                            || !shouldShowRequestPermissionRationale(PERMISSION_WRITE_STORAGE)
                            || !shouldShowRequestPermissionRationale(PERMISSION_INTERNET)
                            || !shouldShowRequestPermissionRationale(PERMISSION_ACCESS_NETWORK_STATE)
                            || !shouldShowRequestPermissionRationale(PERMISSION_ACCESS_WIFI_STATE)) {
                        Toast.makeText(getApplicationContext(), "需要开启摄像头网络文件存储权限", Toast.LENGTH_SHORT).show();
                    }
            } else {

                builder.append("以太网地址: ");
                builder.append(getMacAddreth0());
                builder.append("\n");
                builder.append("wifi地址: ");
                builder.append(getMacAddrwlan0());
                try {
                    savaFileToSD("地址记录"+DateUtils.timesOne(System.currentTimeMillis()+"")+".txt",builder.toString());
                    Toast.makeText(MainActivity.this,"已经保存地址到sd卡根目录",Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,e.getMessage()+"",Toast.LENGTH_LONG).show();
                }
                t1.setText("以太网地址: "+getMacAddreth0());
                t2.setText("Wi-Fi地址: "+getMacAddrwlan0());
            }
        }
    }


    public  String getMacAddreth0() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                //3C:D1:6E:51:18:D5
                //0C:9A:42:C3:A8:CC
                // if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                if (!nif.getName().equalsIgnoreCase("eth0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            Log.d("MainActivity", ex.getMessage());
        }
        return "获取失败";
    }


    public  String getMacAddrwlan0() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                //3C:D1:6E:51:18:D5
                //0C:9A:42:C3:A8:CC
                 if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
              //  if (!nif.getName().equalsIgnoreCase("eth0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            Log.d("MainActivity", ex.getMessage());
        }
        return "获取失败";
    }

    //  往SD卡写入文件的方法
    public  void savaFileToSD(String filename, String filecontent) throws Exception {
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


}
