package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.player.R;
import com.player.movie.dialog.BottomMenu;
import com.player.movie.dialog.DatePickerFragment;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
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
}