package com.player.movie.dialog;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;

public class PlugCamera {
    String [] menu = {"相机","相册"};
    Activity activity;
    Context context;
    private String checkItem;
    public static final int REQUEST_CODE_CAMERA = 2;


    public void showCamera() {
        new BottomMenu(menu,item->{
            checkItem = item;
            if("相机".equals(item)){
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}
                        , REQUEST_CODE_CAMERA);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }else{
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        }).showBottomMenu(context);
    }

    public PlugCamera(Context context,Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public String getCheck(){
        return checkItem;
    }
}
