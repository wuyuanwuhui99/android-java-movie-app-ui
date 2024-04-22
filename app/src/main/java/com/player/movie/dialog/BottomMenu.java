package com.player.movie.dialog;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;

import com.player.movie.view.ButtomMenuDialog;

public class BottomMenu {
    private ButtomMenuDialog dialog;
    private final String [] menu;
    private ClickLister clickLister;
    public void showBottomMenu(Context context) {
        ButtomMenuDialog.Builder builder = new ButtomMenuDialog.Builder(context);
        //添加条目，可多个
        for(String item:menu){
            builder.addMenu(item, view -> {
                clickLister.onClick(item);
                dialog.cancel();
            });
        }
        //下面这些设置都可不写
        builder.setCanCancel(true);//点击阴影时是否取消dialog，true为取消
        builder.setShadow(true);//是否设置阴影背景，true为有阴影
        builder.setCancelText("取消");//设置最下面取消的文本内容
        //设置点击取消时的事件
        builder.setCancelListener(view -> {
            dialog.cancel();
        });
        dialog = builder.create();
        dialog.show();
    }

    public BottomMenu(String [] menu,ClickLister clickLister){
        this.menu = menu;
        this.clickLister = clickLister;
    }

    public interface ClickLister{
        void onClick(String item);
    }
}
