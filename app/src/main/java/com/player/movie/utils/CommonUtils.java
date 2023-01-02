package com.player.movie.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    /**
     * @author: wuwenqiang
     * @description: 跳转到编辑页面
     * @date: 2022-12-30 22:37
     */
    public static final String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
