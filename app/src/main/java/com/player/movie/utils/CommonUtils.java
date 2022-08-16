package com.player.movie.utils;

import android.content.Context;

import java.util.regex.Pattern;

public class CommonUtils {
    public static final int dip2px(Context context,int resId){
        float scale = context.getResources().getDisplayMetrics().density;
        float dpValue = Float.parseFloat(Pattern.compile("[^0-9.]").matcher(context.getResources().getString(resId)).replaceAll("").trim());
        return (int)(dpValue * scale + 0.5f);
    }

    public static final int dip2px(Context context,float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
}
