package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.movie.R;
import com.player.movie.api.Api;
import com.player.movie.state.State;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initUI();
    }

    /**
     * @author: wuwenqiang
     * @description: 设置头像
     * @date: 2022-08-30 22:39
     */
    private void initUI(){
        RoundedImageView avater = findViewById(R.id.user_m_avater);
        Glide.with(this).load(Api.HOST + State.userEntity.getAvater()).into(avater);

        TextView userName = findViewById(R.id.user_m_name);
        userName.setText(State.userEntity.getUsername());

        TextView tel = findViewById(R.id.user_tel);
        tel.setText(State.userEntity.getTelephone());

        TextView email = findViewById(R.id.user_email);
        email.setText(State.userEntity.getEmail());

        TextView birthday = findViewById(R.id.user_birthday);
        birthday.setText(State.userEntity.getBirthday());

        TextView sex = findViewById(R.id.user_sex);
        sex.setText(State.userEntity.getSex());

        TextView sign = findViewById(R.id.user_sign);
        sign.setText(State.userEntity.getSign());

        TextView region = findViewById(R.id.user_region);
        region.setText(State.userEntity.getRegion());
    }

    @Override
    public void onClick(View v) {

    }
}
