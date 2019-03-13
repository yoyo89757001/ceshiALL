package com.ruitong.huiyi3.utils;


import android.graphics.Color;

import com.bumptech.glide.request.RequestOptions;
import com.ruitong.huiyi3.R;
import com.ruitong.huiyi3.view.GlideCircleTransform;


public class GlideUtils {

    public static RequestOptions getRequestOptions(){
        return  new RequestOptions()
                .centerCrop()

                .error(R.drawable.erroy_bg)
                .transform(new GlideCircleTransform(2, Color.WHITE))
                ;
    }


}
