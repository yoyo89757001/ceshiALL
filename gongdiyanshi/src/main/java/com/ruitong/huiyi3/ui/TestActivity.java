package com.ruitong.huiyi3.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.ruitong.huiyi3.R;
import com.ruitong.huiyi3.service.AlarmReceiver;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;



public class TestActivity extends Activity {
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer=null;
    private IVLCVout vlcVout=null;
    private IVLCVout.Callback callback=null;
    private LibVLC libvlc;
    private Media media;
    private RelativeLayout relativeLayout;
    private int dw, dh;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        surfaceView=findViewById(R.id.surff);
        relativeLayout=findViewById(R.id.tanchuangrl);
        libvlc=new LibVLC(this);

        linearLayout=findViewById(R.id.ll1);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        dw = dm.widthPixels;
        dh = dm.heightPixels;

        mediaPlayer = new MediaPlayer(libvlc);
        vlcVout = mediaPlayer.getVLCVout();

        callback = new IVLCVout.Callback() {
            @Override
            public void onSurfacesCreated(IVLCVout vlcVout) {

                RelativeLayout.LayoutParams ppp = (RelativeLayout.LayoutParams) surfaceView.getLayoutParams();
                ppp.width = (int) ((float) dw * 1.0f);
                ppp.height = (int) ((float) dw * 0.6f);
                surfaceView.setLayoutParams(ppp);
                surfaceView.invalidate();

                changeSurfaceSize();

            }

            @Override
            public void onSurfacesDestroyed(IVLCVout vlcVout) {

                changeSurfaceSize();
            }
        };
        vlcVout.setVideoView(surfaceView);
        vlcVout.addCallback(callback);
        vlcVout.attachViews();


        Button button=findViewById(R.id.b1);
        Button button2=findViewById(R.id.b2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.play();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

                SystemClock.sleep(10000);
                sendBroadcast(new Intent(TestActivity.this, AlarmReceiver.class));
                //	synthesizer.speak("吃饭了吗");

            }
        }).start();



    }

    private void changeSurfaceSize() {

        if (mediaPlayer != null) {
            media = new Media(libvlc, Uri.parse("rtsp://admin:admin888@192.168.2.41:554/h264/ch0/main/av_stream"));
            mediaPlayer.setMedia(media);
            mediaPlayer.play();
        }

    }

}
