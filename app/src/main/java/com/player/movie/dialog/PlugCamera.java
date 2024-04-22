package com.player.movie.dialog;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;

import com.player.movie.view.ButtomMenuDialog;

public class PlugCamera {
    private ButtomMenuDialog dialog;
    private int type;
    public static final int REQUEST_CODE_CAMERA = 2;
    public void showCamera(Context context, Activity activity) {
        ButtomMenuDialog.Builder builder = new ButtomMenuDialog.Builder(context);
        //添加条目，可多个
        builder.addMenu("相机", view -> {
            dialog.cancel();
            type = 1;
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}
                    , REQUEST_CODE_CAMERA);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
        }).addMenu("相册", view -> {
            dialog.cancel();
            type = 2;
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
        });
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

    public int getType(){
        return type;
    }
}
