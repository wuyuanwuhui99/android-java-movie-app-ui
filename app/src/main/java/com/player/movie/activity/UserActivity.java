package com.player.movie.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.movie.BaseApplication;
import com.player.movie.R;
import com.player.movie.api.Api;
import com.player.movie.entity.EditEntity;
import com.player.movie.entity.UserEntity;
import com.player.movie.view.ButtomMenuDialog;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int REQUEST_CODE_CAMERA = 2;
    private UserEntity userEntity;
    private static int type = 1;// 1表示相机，2表示相册
    private ButtomMenuDialog dialog;
    private RoundedImageView roundedImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initUI();
        setOnClickListener();
    }

    /**
     * @author: wuwenqiang
     * @description: 设置头像
     * @date: 2022-08-30 22:39
     */
    private void initUI(){
        userEntity = BaseApplication.getInstance().getUserEntity();
        RoundedImageView avater = findViewById(R.id.user_m_avater);
        Glide.with(this).load(Api.HOST + userEntity.getAvater()).into(avater);

        TextView userName = findViewById(R.id.user_m_name);
        userName.setText(userEntity.getUsername());
        userName.setTag(true);// 必填

        TextView tel = findViewById(R.id.user_tel);
        tel.setText(userEntity.getTelephone());
        tel.setTag(false);// 非必填

        TextView email = findViewById(R.id.user_email);
        email.setText(userEntity.getEmail());
        email.setTag(true);// 必填

        TextView birthday = findViewById(R.id.user_birthday);
        birthday.setText(userEntity.getBirthday());
        birthday.setTag(false);// 非必填

        TextView sex = findViewById(R.id.user_sex);
        sex.setText(userEntity.getSex());
        sex.setTag(true);// 必填

        TextView sign = findViewById(R.id.user_sign);
        sign.setText(userEntity.getSign());
        sign.setTag(false);// 必填

        TextView region = findViewById(R.id.user_region);
        region.setText(userEntity.getRegion());
        region.setTag(false);// 必填
    }

    /**
     * @author: wuwenqiang
     * @description: 绑定点击事件
     * @date: 2022-08-30 22:39
     */
    private void setOnClickListener(){
        roundedImageView = findViewById(R.id.user_m_avater);
        roundedImageView.setOnClickListener(this);// 头像
        findViewById(R.id.user_name_layout).setOnClickListener(this);// 昵称
        findViewById(R.id.user_tel_layout).setOnClickListener(this);// 电话
        findViewById(R.id.user_email_layout).setOnClickListener(this);// 邮箱
        findViewById(R.id.user_birthday_layout).setOnClickListener(this);// 出生年月日
        findViewById(R.id.user_sex_layout).setOnClickListener(this);// 性别
        findViewById(R.id.user_sign_layout).setOnClickListener(this);// 签名
        findViewById(R.id.user_region_layout).setOnClickListener(this);// 地区
        findViewById(R.id.user_logout).setOnClickListener(this);// 退出登录
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_name_layout:
                useEdit(v,"username");
                break;
            case R.id.user_tel_layout:
                useEdit(v,"telephone");
                break;
            case R.id.user_email_layout:
                useEdit(v,"email");
                break;
            case R.id.user_birthday_layout:
                useEdit(v,"birthday");
                break;
            case R.id.user_sex_layout:
                useEdit(v,"sex");
                break;
            case R.id.user_sign_layout:
                useEdit(v,"sign");
                break;
            case R.id.user_region_layout:
                useEdit(v,"region");
                break;
            case R.id.user_logout:
                logout();
                break;
            case R.id.user_m_avater:
                showCamera();
                break;
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 跳转到编辑页面
     * @date: 2022-08-31 22:06
     */
    private void useEdit(View v,String field){
        LinearLayout ly = (LinearLayout)v;
        TextView nameTextView = (TextView) ly.getChildAt(0);
        String title = nameTextView.getText().toString();
        TextView valueTextView = (TextView) ly.getChildAt(1);
        String value = valueTextView.getText().toString();
        Boolean require = (Boolean) valueTextView.getTag();
        Intent intent = new Intent(this,EditActivity.class);
        intent.putExtra("editEntity",JSON.toJSONString(new EditEntity(title,field,value,require)));
        startActivityForResult(intent,1);
    }


    /**
     * @author: wuwenqiang
     * @description: 跳转到登录页，前面所有页面置空
     * @date: 2022-08-31 22:06
     */
    private void logout(){
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);// 前面所有页面置空
        startActivity(intent);
    }

    /**
     * 选择相机
     */

    private void showCamera() {
        ButtomMenuDialog.Builder builder = new ButtomMenuDialog.Builder(this);
        //添加条目，可多个
        builder.addMenu("相机", view -> {
            dialog.cancel();
            type = 1;
            ActivityCompat.requestPermissions(UserActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}
                    , REQUEST_CODE_CAMERA);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        }).addMenu("相册", view -> {
            dialog.cancel();
            type = 2;
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        });
        //下面这些设置都可不写
//        builder.setTitle("这是标题");//添加标题
        builder.setCanCancel(true);//点击阴影时是否取消dialog，true为取消
        builder.setShadow(true);//是否设置阴影背景，true为有阴影
        builder.setCancelText("取消");//设置最下面取消的文本内容
        //设置点击取消时的事件
        builder.setCancelListener(view -> {
            dialog.cancel();
            Toast.makeText(UserActivity.this, "取消", Toast.LENGTH_SHORT).show();
        });
        dialog = builder.create();
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA) {
            // 从相册返回的数据
            Log.e(this.getClass().getName(), "Result:" + data.toString());
            if (data != null) {
                // 得到图片的全路径
                if(type == 1){
                    Bundle bundle = data.getExtras(); // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
                    Bitmap bitmap = (Bitmap) bundle.get("data"); // 将data中的信息流解析为Bitmap类型
                    roundedImageView.setImageBitmap(bitmap);
                }else {
                    Uri uri = data.getData();
                    roundedImageView.setImageURI(uri);
                }
            }
        }
    }
}
