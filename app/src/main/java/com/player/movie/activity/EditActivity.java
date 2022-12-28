package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.player.movie.R;
import com.player.movie.entity.EditEntity;

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
                Intent intent = getIntent();
                editEntity.setValue(editText.getText().toString());
                intent.putExtra("editEntity",JSON.toJSONString(editEntity));
                setResult(RESULT_OK,intent);
                finish();
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

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
