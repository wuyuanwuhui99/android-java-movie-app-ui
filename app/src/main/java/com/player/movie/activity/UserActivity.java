package com.player.movie.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.movie.BaseApplication;
import com.player.movie.R;
import com.player.movie.api.Api;
import com.player.movie.entity.EditEntity;
import com.player.movie.entity.UserEntity;
import com.player.movie.view.ReflectHelper;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    private UserEntity userEntity;
    private TextView editText;

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
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 跳转到编辑页面
     * @date: 2022-08-31 22:06
     */
    private void useEdit(View v,String field){
        LinearLayout ly = (LinearLayout)v;
        editText = (TextView)ly.getChildAt(1);
        TextView nameTextView = (TextView) ly.getChildAt(0);
        String title = nameTextView.getText().toString();
        TextView valueTextView = (TextView) ly.getChildAt(1);
        String value = valueTextView.getText().toString();
        Boolean require = (Boolean) valueTextView.getTag();
        Intent intent = new Intent(this,EditActivity.class);
        intent.putExtra("editEntity",JSON.toJSONString(new EditEntity(title,field,value,require)));
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            EditEntity editEntity = JSON.parseObject(data.getStringExtra("editEntity"),EditEntity.class);
            ReflectHelper reflectHelper = new ReflectHelper(userEntity);//创建工具类对象
            reflectHelper.setMethodValue(editEntity.getField(),editEntity.getValue());// 动态调用 set方法给文件对象内容赋值
            editText.setText(editEntity.getValue());
        }
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
}
