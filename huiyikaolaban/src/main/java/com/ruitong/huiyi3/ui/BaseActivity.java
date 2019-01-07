package com.ruitong.huiyi3.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.ruitong.huiyi3.MyApplication;
import com.ruitong.huiyi3.R;
import com.ruitong.huiyi3.beans.BaoCunBean;
import com.ruitong.huiyi3.beans.BaoCunBeanDao;


public class BaseActivity extends Activity {
    private BaoCunBean baoCunBean=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        BaoCunBeanDao baoCunBeanDao= MyApplication.myApplication.getDaoSession().getBaoCunBeanDao();
         baoCunBean=baoCunBeanDao.load(123456L);
        if (baoCunBean==null ){
            BaoCunBean baoCunBean2=new BaoCunBean();
            baoCunBean2.setId(123456L);
            baoCunBean2.setMoban(1);
            baoCunBean2.setYudiao(5);
            baoCunBean2.setYusu(5);
            baoCunBean2.setBoyingren(4);
            baoCunBeanDao.insert(baoCunBean2);
            baoCunBean=baoCunBeanDao.load(123456L);
        }
        switch (baoCunBean.getMoban()){

            case 1:

                startActivity(new Intent(BaseActivity.this,GongDiYanShiActivity.class));
                finish();
                break;
            case 2:
//                startActivity(new Intent(BaseActivity.this,XinChunActivity.class));
//                finish();

                break;
            case 3:

                break;
            case 4:


                break;

        }



    }



    public static class MyReceiver extends BroadcastReceiver {
        public MyReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                Intent i = new Intent(context, BaseActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }


}
