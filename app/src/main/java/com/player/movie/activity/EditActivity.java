package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.player.movie.BaseApplication;
import com.player.movie.R;
import com.player.movie.entity.EditEntity;
import com.player.movie.entity.UserEntity;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;
import com.player.movie.view.ReflectHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher {

    private EditEntity editEntity;
    private Button sureButton;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initUI();
        initEventListener();
    }

    private void initUI(){
        Intent intent = getIntent();
        editEntity = JSON.parseObject(intent.getStringExtra("editEntity"),EditEntity.class);
        TextView title = findViewById(R.id.user_title);
        sureButton = findViewById(R.id.edit_sure);
        title.setText("修改"+editEntity.getTitle());
        sureButton.setEnabled(false);
        editText = findViewById(R.id.user_edit);
        editText.setText(editEntity.getValue());
    }

    private void initEventListener(){
        findViewById(R.id.edit_cancel).setOnClickListener(this);
        sureButton.setOnClickListener(this);
        editText.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_cancel:
                finish();
                break;
            case R.id.edit_sure:
                String field = editEntity.getField();
                String value = editEntity.getValue();
                UserEntity userEntity = new UserEntity();
                UserEntity mUserEntity = BaseApplication.getInstance().getUserEntity();
                userEntity.setUserId(mUserEntity.getUserId());
                ReflectHelper reflectHelper = new ReflectHelper(userEntity);
                reflectHelper.setMethodValue(field,value);
                Call<ResultEntity> userData = RequestUtils.getInstance().updateUser(userEntity);
                userData.enqueue(new Callback<ResultEntity>() {
                    @Override
                    public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                        if("SUCCESS".equals(response.body().getStatus())){
                            ReflectHelper reflectHelper = new ReflectHelper(mUserEntity);
                            reflectHelper.setMethodValue(field,value);
                            Intent intent = getIntent();
                            editEntity.setValue(editText.getText().toString());
                            setResult(RESULT_OK,intent);
                            finish();
                        }else{
                            Toast.makeText(EditActivity.this,"更改用户信息失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultEntity> call, Throwable t) {
                        System.out.println("错误");
                    }
                });

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Boolean require = editEntity.getRequire();
        if(require && "".equals(s)){
            sureButton.setEnabled(false);
        }else{
            sureButton.setEnabled(true);
        }
        editEntity.setValue(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
