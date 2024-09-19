package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.player.R;
import com.player.common.Constants;
import com.player.movie.BaseApplication;
import com.player.movie.dialog.BottomMenu;
import com.player.movie.dialog.DatePickerFragment;
import com.player.movie.entity.UserEntity;
import com.player.http.RequestUtils;
import com.player.http.ResultEntity;
import com.player.movie.utils.ActivityCollectorUtil;
import com.player.movie.utils.MD5;
import com.player.movie.utils.SharedPreferencesUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,View.OnFocusChangeListener{
    EditText userIdInput;
    EditText passwordInput;
    EditText confirmPasswordInput;
    EditText usernameInput;
    EditText sexInput;
    EditText birthdayInput;
    EditText telInput;
    EditText emailInput;
    EditText regionInput;
    EditText signInput;
    private RelativeLayout loadingLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    /**
     * @author: wuwenqiang
     * @description: 初始化表单
     * @date: 2024-04-23 23:49
     */
    private void initView(){
        userIdInput = findViewById(R.id.user_id_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
        usernameInput = findViewById(R.id.username_input);
        sexInput = findViewById(R.id.sex_input);
        birthdayInput = findViewById(R.id.birthday_input);
        telInput = findViewById(R.id.tel_input);
        emailInput = findViewById(R.id.email_input);
        regionInput = findViewById(R.id.region_input);
        signInput = findViewById(R.id.sign_input);

        sexInput.setOnClickListener(this);
        birthdayInput.setOnClickListener(this);
        findViewById(R.id.user_register_submit).setOnClickListener(this);

        userIdInput.setOnFocusChangeListener(this);
        passwordInput.setOnFocusChangeListener(this);
        confirmPasswordInput.setOnFocusChangeListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sex_input:
                useBottomSexMenu();
                break;
            case R.id.birthday_input:
                useDatePicker();
                break;
            case R.id.user_register_submit:
                useRegister();
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 选择出生年月日
     * @date: 2024-04-23 23:49
     */
    private void useDatePicker(){
        new DatePickerFragment(1990,1,1, (selectedDate)->{
            birthdayInput.setText(selectedDate);
        }).show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * @author: wuwenqiang
     * @description: 底部弹出男女选择菜单
     * @date: 2024-4-23 23:50
     */
    private void useBottomSexMenu(){
        String[] menu = {"男","女"};
        new BottomMenu(menu, item -> {
            sexInput.setText(item);
        }).showBottomMenu(this);
    }

    /**
     * @author: wuwenqiang
     * @description: 注册
     * @date: 2024-4-23 23:50
     */
    private void useRegister(){
        if(userIdInput.getText().toString().length() < 6){
            Toast.makeText(RegisterActivity.this,getResources().getString(R.string.user_verified_tip), Toast.LENGTH_SHORT).show();
        }else if(userIdInput.getText().toString().length() >= 6){
            Call<ResultEntity> getUserByIdCall = RequestUtils.getMovieInstance().getUserById(userIdInput.getText().toString());
            getUserByIdCall.enqueue(new Callback<ResultEntity>() {
                @Override
                public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                    Double num = (Double) response.body().getData();
                    if(num > 0){// 账号已注册，校验不通过
                        Toast.makeText(RegisterActivity.this,getResources().getString(R.string.user_exist_tip), Toast.LENGTH_SHORT).show();
                    }else{
                        submit();
                    }
                }

                @Override
                public void onFailure(Call<ResultEntity> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this,getResources().getString(R.string.error_tip), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            submit();
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 提交
     * @date: 2024-4-24 00:43
     */
    private void submit(){
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();
        String username = usernameInput.getText().toString();
        if(password.length() < 6){
            Toast.makeText(RegisterActivity.this,getResources().getString(R.string.password_length_tip), Toast.LENGTH_SHORT).show();
        }else if(confirmPassword.length() < 6){
            Toast.makeText(RegisterActivity.this,getResources().getString(R.string.confirm_password_length_tip), Toast.LENGTH_SHORT).show();
        }else if(!confirmPassword.equals(password)){
            Toast.makeText(RegisterActivity.this,getResources().getString(R.string.confirm_password_verified_tip), Toast.LENGTH_SHORT).show();
        }else if(username.length() < 1){
            Toast.makeText(RegisterActivity.this,getResources().getString(R.string.username_empty_tip), Toast.LENGTH_SHORT).show();
        }else{
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(userIdInput.getText().toString());
            userEntity.setPassword(MD5.getStrMD5(password));
            userEntity.setUsername(username);
            userEntity.setBirthday(birthdayInput.getText().toString());
            userEntity.setEmail(emailInput.getText().toString());
            userEntity.setSex(sexInput.getText().toString());
            userEntity.setTelephone(telInput.getText().toString());
            userEntity.setRegion(regionInput.getText().toString());

            if(loadingLayout == null){
                // 获取LayoutInflater服务
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                loadingLayout = inflater.inflate(R.layout.layout_loading, (ViewGroup)getWindow().getDecorView().getRootView()).findViewById(R.id.layout_loading);
            }
            loadingLayout.setVisibility(View.VISIBLE);
            Call<ResultEntity> registerCall = RequestUtils.getMovieInstance().register(userEntity);
            registerCall.enqueue(new Callback<ResultEntity>() {
                @Override
                public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                    if(Constants.SUCCESS.equals(response.body().getStatus())){
                        BaseApplication.getInstance().setToken(response.body().getToken());
                        SharedPreferencesUtils.setParam(RegisterActivity.this,Constants.TOKEN,response.body().getToken());
                        SharedPreferencesUtils.setParam(RegisterActivity.this,userIdInput.getText().toString(),password);
                        Toast.makeText(RegisterActivity.this,getResources().getString(R.string.register_success_tip), Toast.LENGTH_SHORT).show();
                        // 延迟2000毫秒执行跳转首页
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 前面所有页面置空
                                startActivity(intent);
                                ActivityCollectorUtil.finishAllActivity();
                            }
                        }, 2000);
                    }else{
                        Toast.makeText(RegisterActivity.this,getResources().getString(R.string.register_fail_tip), Toast.LENGTH_SHORT).show();
                    }
                    loadingLayout.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ResultEntity> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this,getResources().getString(R.string.error_tip), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            // 这里处理EditText失去焦点的事件
            // 例如，隐藏软键盘
            switch (v.getId()){
                case R.id.user_id_input:
                    if(userIdInput.getText().toString().length() < 6){
                        Toast.makeText(RegisterActivity.this,getResources().getString(R.string.user_verified_tip), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Call<ResultEntity> getUserByIdCall = RequestUtils.getMovieInstance().getUserById(userIdInput.getText().toString());
                    getUserByIdCall.enqueue(new Callback<ResultEntity>() {
                        @Override
                        public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                            Double num = (Double) response.body().getData();
                            if(num > 0){// 账号已注册，校验不通过
                                Toast.makeText(RegisterActivity.this,getResources().getString(R.string.user_exist_tip), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultEntity> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this,getResources().getString(R.string.error_tip), Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                case R.id.password_input:
                    if(passwordInput.getText().toString().length() < 6){
                        Toast.makeText(RegisterActivity.this,getResources().getString(R.string.password_length_tip), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.confirm_password_input:
                    String confirmPassword = confirmPasswordInput.getText().toString();
                    if(confirmPassword.length() < 6){
                        Toast.makeText(RegisterActivity.this,getResources().getString(R.string.confirm_password_length_tip), Toast.LENGTH_SHORT).show();
                    }else if(!confirmPassword.equals(passwordInput.getText().toString())){
                        Toast.makeText(RegisterActivity.this,getResources().getString(R.string.confirm_password_verified_tip), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.username_input:
                    if(usernameInput.getText().length() < 0){
                        Toast.makeText(RegisterActivity.this,getResources().getString(R.string.username_empty_tip), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
}