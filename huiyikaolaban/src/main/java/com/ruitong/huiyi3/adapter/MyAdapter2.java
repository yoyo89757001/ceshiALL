//package com.ruitong.huiyi3.adapter;
//
///**
// * Created by Administrator on 2018/7/3.
// */
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.text.Html;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.ruitong.huiyi3.MyApplication;
//import com.ruitong.huiyi3.R;
//import com.ruitong.huiyi3.beans.TanChuangBean;
//import com.ruitong.huiyi3.beans.ZhuJiBeanHDao;
//import com.ruitong.huiyi3.view.GlideRoundTransform;
//import com.yatoooon.screenadaptation.ScreenAdapterTools;
//
//import java.util.List;
//
///**
// * Created  2018/1/15.
// */
//
//
//public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder>{
//
//    private List<TanChuangBean> list;
//    private Context context;
//    private int dw,dh;
//   // private String touxiangPath;
//    private ZhuJiBeanHDao zhuJiBeanHDao=null;
//
//    public MyAdapter2(List<TanChuangBean> list, Context context, int dw, int dh)
//            {
//                zhuJiBeanHDao = MyApplication.myApplication.getDaoSession().getZhuJiBeanHDao();
//                this.list = list;
//                this.context=context;
//                this.dw=dw;
//                this.dh=dh;
//
//
//            }
//
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dibuitem2, parent, false);
//             ScreenAdapterTools.getInstance().loadView(view);
//
//              return new ViewHolder(view);
//
//            }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//                if (!list.get(position).getName().equals("")){
//                    holder.name.setText( Html.fromHtml("<font color='#ffffff'><big>"+list.get(position).getName()+"</big></font>"+" <font color='#ffffff'>嘉宾</font>"));
//                }else {
//                    holder.name.setText(  Html.fromHtml("<font color='#ffffff'><big>嘉宾您好</big></font>"));
//                }
//
//            holder.gongsi.setText(Html.fromHtml("<font color='#ffffff'>欢迎莅临博鳌论坛</font>"));
//          //  holder.zuoweihao.setText(list.get(position).getRemark());
//        Log.d("MyAdapter2", BoAoHengActivity2.touxiangPath + list.get(position).getTouxiang());
//            if (list.get(position).getTouxiang()!=null){
//                Glide.with(context)
//                        //	.load(R.drawable.vvv)
//                        .load(BoAoHengActivity2.touxiangPath+list.get(position).getTouxiang())
//                        .error(R.drawable.erroy_bg)
//                        //.apply(myOptions)
//                        .transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
//                        // .transform(new GlideCircleTransform(MyApplication.getAppContext(),2, Color.parseColor("#ffffffff")))
//                        .into(holder.touxiang);
//            }else {
//                Glide.with(context)
//                        //	.load(R.drawable.vvv)
//                        .load(list.get(position).getBytes())
//                        .error(R.drawable.erroy_bg)
//                        //.apply(myOptions)
//                        .transform(new GlideRoundTransform(MyApplication.getAppContext(), 20))
//                        // .transform(new GlideCircleTransform(MyApplication.getAppContext(),2, Color.parseColor("#ffffffff")))
//                        .into(holder.touxiang);
//            }
//
//
//
//        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams)holder.touxiang.getLayoutParams();
//        params1.height=dh/13;
//        params1.width=dh/16;
//        params1.leftMargin=40;
//        holder.touxiang.setLayoutParams(params1);
//        holder.touxiang.invalidate();
//
//        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) holder.root_rl.getLayoutParams();
//        params2.topMargin=60;
//        params2.width=dw*2/5;
//        params2.height=dh/8;
//        holder. root_rl.setLayoutParams(params2);
//        holder. root_rl.invalidate();
//
//
//            }
//
//    @Override
//    public int getItemCount() {
//
//             return list.size();
//
//            }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        TextView gongsi,name,zuoweihao;
//        ImageView touxiang;
//        RelativeLayout root_rl;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//             touxiang =itemView.findViewById(R.id.touxiang);
//             root_rl = itemView .findViewById(R.id.root_rl2);
//             name = itemView.findViewById(R.id.test2);
//             zuoweihao = itemView .findViewById(R.id.test4);
//             gongsi = itemView.findViewById(R.id.test3);
//        }
//    }
//
//}