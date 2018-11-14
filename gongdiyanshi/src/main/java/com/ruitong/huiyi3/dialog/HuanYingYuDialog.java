package com.ruitong.huiyi3.dialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruitong.huiyi3.MyApplication;
import com.ruitong.huiyi3.R;
import com.ruitong.huiyi3.adapter.PopupWindowAdapter;
import com.ruitong.huiyi3.beans.HuanYinYuBean;
import com.ruitong.huiyi3.beans.HuanYinYuBeanDao;

import java.util.ArrayList;
import java.util.List;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class HuanYingYuDialog extends Dialog {
    private TextView a1,juese;
    private Button queren,quxiao;
    private EditText huanyinci;
    private List<String> stringList2=new ArrayList<>();
    private PopupWindow popupWindow=null;
    private HuanYinYuBeanDao huanYinYuBeanDao= MyApplication.getAppContext().getDaoSession().getHuanYinYuBeanDao();
    private HuanYinYuBean huanYinYuBean=null;
    private Context context;
    private PopupWindowAdapter adapterss;
    private String name="内部员工";

    public HuanYingYuDialog(Context context) {
        super(context, R.style.dialog_style2);
        this.context=context;
        stringList2.add("内部员工");
        stringList2.add("普通嘉宾");
        stringList2.add("内部领导");
        stringList2.add("外部领导");
        //stringList2.add("合作伙伴");
        stringList2.add("陌生人");

        Window window =  this.getWindow();
        if ( window != null) {
            WindowManager.LayoutParams attr = window.getAttributes();
            if (attr != null) {
                attr.height = LayoutParams.WRAP_CONTENT;
                attr.width = LayoutParams.WRAP_CONTENT;
                attr.gravity = Gravity.CENTER;//设置dialog 在布局中的位置
            }
        }

        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.queren_huanyingyu, null);
        a1= (TextView) mView.findViewById(R.id.a1);
        juese= (TextView) mView.findViewById(R.id.juese);
        juese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View contentView2 = LayoutInflater.from(context).inflate(R.layout.xiangmu_po_item, null);
                ListView listView2= (ListView) contentView2.findViewById(R.id.dddddd);
                adapterss=new PopupWindowAdapter(context,stringList2);
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        juese.setText(stringList2.get(position));
                        popupWindow.dismiss();
                        huanyinci.clearFocus();
                    }
                });
                listView2.setAdapter(adapterss);
                popupWindow=new PopupWindow(contentView2,200, 180);
                popupWindow.setFocusable(true);//获取焦点
                popupWindow.setOutsideTouchable(true);//获取外部触摸事件
                popupWindow.setTouchable(true);//能够响应触摸事件
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景
                popupWindow.showAsDropDown(juese, 0,4);
            }
        });
        huanyinci= (EditText) mView.findViewById(R.id.huanyin_et);
        huanyinci.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //失去焦点
                    Log.d("HuanYingYuDialog", "失去");
                    //先用保存的name
                    switch (name){

                        case "内部员工":
                         HuanYinYuBean huanYinYuBean=huanYinYuBeanDao.load(1L);
                         if (huanYinYuBean==null){
                             //插入
                             HuanYinYuBean h=new HuanYinYuBean();
                             h.setId(1L);
                             h.setName(name);
                             h.setHuanyinci(huanyinci.getText().toString().trim());
                             huanYinYuBeanDao.insert(h);

                         }else {
                             //更新
                             huanYinYuBean.setHuanyinci(huanyinci.getText().toString().trim());
                             huanYinYuBeanDao.update(huanYinYuBean);
                         }


                            break;
                        case "普通嘉宾":
                            HuanYinYuBean huanYinYuBean2=huanYinYuBeanDao.load(2L);
                            if (huanYinYuBean2==null){
                                //插入
                                HuanYinYuBean h=new HuanYinYuBean();
                                h.setId(2L);
                                h.setName(name);
                                h.setHuanyinci(huanyinci.getText().toString().trim());
                                huanYinYuBeanDao.insert(h);

                            }else {
                                //更新
                                huanYinYuBean2.setHuanyinci(huanyinci.getText().toString().trim());
                                huanYinYuBeanDao.update(huanYinYuBean2);
                            }
                            break;
                        case "内部领导":

                            HuanYinYuBean huanYinYuBean3=huanYinYuBeanDao.load(3L);
                            if (huanYinYuBean3==null){
                                //插入
                                HuanYinYuBean h=new HuanYinYuBean();
                                h.setId(3L);
                                h.setName(name);
                                h.setHuanyinci(huanyinci.getText().toString().trim());
                                huanYinYuBeanDao.insert(h);

                            }else {
                                //更新
                                huanYinYuBean3.setHuanyinci(huanyinci.getText().toString().trim());
                                huanYinYuBeanDao.update(huanYinYuBean3);
                            }

                            break;
                        case "外部领导":
                            HuanYinYuBean huanYinYuBean4=huanYinYuBeanDao.load(4L);
                            if (huanYinYuBean4==null){
                                //插入
                                HuanYinYuBean h=new HuanYinYuBean();
                                h.setId(4L);
                                h.setName(name);
                                h.setHuanyinci(huanyinci.getText().toString().trim());
                                huanYinYuBeanDao.insert(h);

                            }else {
                                //更新
                                huanYinYuBean4.setHuanyinci(huanyinci.getText().toString().trim());
                                huanYinYuBeanDao.update(huanYinYuBean4);
                            }
                            break;
//                        case "合作伙伴":
//                            HuanYinYuBean huanYinYuBean5=huanYinYuBeanDao.load(5L);
//                            if (huanYinYuBean5==null){
//                                //插入
//                                HuanYinYuBean h=new HuanYinYuBean();
//                                h.setId(5L);
//                                h.setName(name);
//                                h.setHuanyinci(huanyinci.getText().toString().trim());
//                                huanYinYuBeanDao.insert(h);
//
//                            }else {
//                                //更新
//                                huanYinYuBean5.setHuanyinci(huanyinci.getText().toString().trim());
//                                huanYinYuBeanDao.update(huanYinYuBean5);
//                            }
//                            break;
                        case "陌生人":
                            HuanYinYuBean huanYinYuBean6=huanYinYuBeanDao.load(6L);
                            if (huanYinYuBean6==null){
                                //插入
                                HuanYinYuBean h=new HuanYinYuBean();
                                h.setId(6L);
                                h.setName(name);
                                h.setHuanyinci(huanyinci.getText().toString().trim());
                                huanYinYuBeanDao.insert(h);

                            }else {
                                //更新
                                huanYinYuBean6.setHuanyinci(huanyinci.getText().toString().trim());
                                huanYinYuBeanDao.update(huanYinYuBean6);
                            }
                            break;
                    }

                    huanyinci.setText("");
                    //显示保存起来的欢迎语
                    switch (juese.getText().toString()){
                        case "内部员工":
                            if (huanYinYuBeanDao.load(1L)==null || huanYinYuBeanDao.load(1L).getHuanyinci().equals("")){
                                huanyinci.setHint("请设置内部员工欢迎语");
                            }else {
                                huanyinci.setText(huanYinYuBeanDao.load(1L).getHuanyinci());
                            }
                            break;
                        case "普通嘉宾":
                            if (huanYinYuBeanDao.load(2L)==null ||huanYinYuBeanDao.load(2L).getHuanyinci().equals("")){
                                huanyinci.setHint("请设置普通嘉宾欢迎语");
                            }else {
                                huanyinci.setText(huanYinYuBeanDao.load(2L).getHuanyinci());
                            }
                            break;
                        case "内部领导":
                            if (huanYinYuBeanDao.load(3L)==null || huanYinYuBeanDao.load(3L).getHuanyinci().equals("")){
                                huanyinci.setHint("请设置内部领导欢迎语");
                            }else {
                                huanyinci.setText(huanYinYuBeanDao.load(3L).getHuanyinci());
                            }
                            break;
                        case "外部领导":
                            if (huanYinYuBeanDao.load(4L)==null || huanYinYuBeanDao.load(4L).getHuanyinci().equals("")){
                                huanyinci.setHint("请设置外部领导欢迎语");
                            }else {
                                huanyinci.setText(huanYinYuBeanDao.load(4L).getHuanyinci());
                            }
                            break;
//                        case "合作伙伴":
//                            if (huanYinYuBeanDao.load(5L)==null || huanYinYuBeanDao.load(5L).getHuanyinci().equals("")){
//                                huanyinci.setHint("请设置合作伙伴欢迎语");
//                            }else {
//                                huanyinci.setText(huanYinYuBeanDao.load(5L).getHuanyinci());
//                            }
//                            break;
                        case "陌生人":
                            if (huanYinYuBeanDao.load(6L)==null || huanYinYuBeanDao.load(6L).getHuanyinci().equals("")){
                                huanyinci.setHint("请设置陌生人欢迎语");
                            }else {
                                huanyinci.setText(huanYinYuBeanDao.load(6L).getHuanyinci());
                            }
                            break;

                    }
                    //最后更新 零时的name
                name=juese.getText().toString();

                }else {
                    Log.d("HuanYingYuDialog", "失去2");
                }

            }
        });
        queren= (Button) mView.findViewById(R.id.queren);
        quxiao= (Button) mView.findViewById(R.id.quxiao);
        if (huanYinYuBeanDao.load(1L)!=null && !huanYinYuBeanDao.load(1L).getHuanyinci().equals(""))
        huanyinci.setText(huanYinYuBeanDao.load(1L).getHuanyinci());
        else
            huanyinci.setHint("请设置内部员工欢迎语");

        super.setContentView(mView);


    }

    public void saveText(){
        name=juese.getText().toString();
        switch (name){

            case "内部员工":
                HuanYinYuBean huanYinYuBean=huanYinYuBeanDao.load(1L);
                if (huanYinYuBean==null){
                    //插入
                    HuanYinYuBean h=new HuanYinYuBean();
                    h.setId(1L);
                    h.setName(name);
                    h.setHuanyinci(huanyinci.getText().toString().trim());
                    huanYinYuBeanDao.insert(h);

                }else {
                    //更新
                    huanYinYuBean.setHuanyinci(huanyinci.getText().toString().trim());
                    huanYinYuBeanDao.update(huanYinYuBean);
                }


                break;
            case "普通嘉宾":
                HuanYinYuBean huanYinYuBean2=huanYinYuBeanDao.load(2L);
                if (huanYinYuBean2==null){
                    //插入
                    HuanYinYuBean h=new HuanYinYuBean();
                    h.setId(2L);
                    h.setName(name);
                    h.setHuanyinci(huanyinci.getText().toString().trim());
                    huanYinYuBeanDao.insert(h);

                }else {
                    //更新
                    huanYinYuBean2.setHuanyinci(huanyinci.getText().toString().trim());
                    huanYinYuBeanDao.update(huanYinYuBean2);
                }
                break;
            case "内部领导":

                HuanYinYuBean huanYinYuBean3=huanYinYuBeanDao.load(3L);
                if (huanYinYuBean3==null){
                    //插入
                    HuanYinYuBean h=new HuanYinYuBean();
                    h.setId(3L);
                    h.setName(name);
                    h.setHuanyinci(huanyinci.getText().toString().trim());
                    huanYinYuBeanDao.insert(h);

                }else {
                    //更新
                    huanYinYuBean3.setHuanyinci(huanyinci.getText().toString().trim());
                    huanYinYuBeanDao.update(huanYinYuBean3);
                }

                break;
            case "外部领导":
                HuanYinYuBean huanYinYuBean4=huanYinYuBeanDao.load(4L);
                if (huanYinYuBean4==null){
                    //插入
                    HuanYinYuBean h=new HuanYinYuBean();
                    h.setId(4L);
                    h.setName(name);
                    h.setHuanyinci(huanyinci.getText().toString().trim());
                    huanYinYuBeanDao.insert(h);

                }else {
                    //更新
                    huanYinYuBean4.setHuanyinci(huanyinci.getText().toString().trim());
                    huanYinYuBeanDao.update(huanYinYuBean4);
                }
                break;
//            case "合作伙伴":
//                HuanYinYuBean huanYinYuBean5=huanYinYuBeanDao.load(5L);
//                if (huanYinYuBean5==null){
//                    //插入
//                    HuanYinYuBean h=new HuanYinYuBean();
//                    h.setId(5L);
//                    h.setName(name);
//                    h.setHuanyinci(huanyinci.getText().toString().trim());
//                    huanYinYuBeanDao.insert(h);
//
//                }else {
//                    //更新
//                    huanYinYuBean5.setHuanyinci(huanyinci.getText().toString().trim());
//                    huanYinYuBeanDao.update(huanYinYuBean5);
//                }
//                break;
            case "陌生人":
                HuanYinYuBean huanYinYuBean6=huanYinYuBeanDao.load(6L);
                if (huanYinYuBean6==null){
                    //插入
                    HuanYinYuBean h=new HuanYinYuBean();
                    h.setId(6L);
                    h.setName(name);
                    h.setHuanyinci(huanyinci.getText().toString().trim());
                    huanYinYuBeanDao.insert(h);

                }else {
                    //更新
                    huanYinYuBean6.setHuanyinci(huanyinci.getText().toString().trim());
                    huanYinYuBeanDao.update(huanYinYuBean6);
                }
                break;
        }

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

    /**
     * 确定键监听器
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener){
        queren.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setOnQuXiaoListener(View.OnClickListener listener){
        quxiao.setOnClickListener(listener);
    }
}
