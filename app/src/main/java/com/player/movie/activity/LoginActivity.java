package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.player.R;
import com.player.common.Constants;
import com.player.movie.BaseApplication;
import com.player.movie.entity.UserEntity;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;
import com.player.movie.utils.MD5;
import com.player.movie.utils.SharedPreferencesUtils;

import org.apache.commons.codec.digest.DigestUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText userEditText;
    EditText passwordEditText;
    boolean isLoading = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    /**
     * @author: wuwenqiang
     * @description: 登录和注册按钮点击事件
     * @date: 2024-04-20 18:42
     */
    void initView(){
        String userId = BaseApplication.getInstance().getUserEntity().getUserId();
        userEditText = findViewById(R.id.user_input);
        userEditText.setText(userId);
        passwordEditText = findViewById(R.id.password_input);
        findViewById(R.id.user_login).setOnClickListener(this);
        findViewById(R.id.user_register).setOnClickListener(this);
    }

    /**
     * @author: wuwenqiang
     * @description: 登录
     * @date: 2024-04-20 18:42
     */
    void login(String userId,String password){
        if(isLoading)return;
        isLoading = true;
        String encryptedPsw = MD5.getStrMD5(password);
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setPassword(encryptedPsw);
        Call<ResultEntity> loginCall = RequestUtils.getInstance().login(userEntity);
        loginCall.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                isLoading = false;
                if(response.body().getStatus().equals(Constants.SUCCESS)){
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_success_tip), Toast.LENGTH_SHORT).show();
                    // 延迟2000毫秒执行跳转首页
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferencesUtils.setParam(LoginActivity.this,"token",response.body().getToken());
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 前面所有页面置空
                            startActivity(intent);
                            finish();
                        }
                    }, 2000);
                }else{
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_err_tip), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_err_tip), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_login:
                String user = userEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if("".equals(user)){
                    Toast.makeText(this, getResources().getString(R.string.user_empty_tip), Toast.LENGTH_SHORT).show();
                }else if("".equals(password)){
                    Toast.makeText(this, getResources().getString(R.string.password_empty_tip), Toast.LENGTH_SHORT).show();
                }else if(password.length() < 6){
                    Toast.makeText(this, getResources().getString(R.string.password_length_tip), Toast.LENGTH_SHORT).show();
                }else{
                    login(user,password);
                }
                break;
            case R.id.user_register:
                break;
        }
    }
}
