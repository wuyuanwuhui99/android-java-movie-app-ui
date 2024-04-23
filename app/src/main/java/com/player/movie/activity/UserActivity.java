package com.player.movie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.player.common.Constants;
import com.player.movie.BaseApplication;
import com.player.R;
import com.player.movie.api.Api;
import com.player.movie.dialog.BottomMenu;
import com.player.movie.dialog.DatePickerFragment;
import com.player.movie.entity.UserEntity;
import com.player.movie.http.RequestUtils;
import com.player.movie.http.ResultEntity;
import com.player.movie.receiver.UpdateUserReciver;
import com.player.movie.utils.ActivityCollectorUtil;
import com.player.movie.dialog.PlugCamera;
import com.player.movie.utils.SharedPreferencesUtils;
import com.player.movie.dialog.CustomDialogFragment;
import com.player.movie.view.ReflectHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{
    private RoundedImageView roundedImageView;
    private PlugCamera plugCamera;
    private String field;
    private RelativeLayout loadingLayout;

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
        UserEntity userEntity = BaseApplication.getInstance().getUserEntity();
        RoundedImageView avater = findViewById(R.id.user_m_avater);
        if(userEntity.getAvater() != null){
            Glide.with(this).load(Api.HOST + userEntity.getAvater()).into(avater);
        }else{
            avater.setImageResource(R.mipmap.default_avater);
        }

        TextView userName = findViewById(R.id.user_m_name);
        userName.setText(userEntity.getUsername() == null ? "" : userEntity.getUsername());
        userName.setTag(true);// 必填


        TextView tel = findViewById(R.id.user_tel);
        tel.setText(userEntity.getTelephone() == null ? "" : userEntity.getTelephone());
        tel.setTag(false);// 非必填


        TextView email = findViewById(R.id.user_email);
        email.setText(userEntity.getEmail() == null ? "" : userEntity.getEmail());
        email.setTag(true);// 必填


        TextView birthday = findViewById(R.id.user_birthday);
        birthday.setText(userEntity.getBirthday() == null ? "" : userEntity.getBirthday());
        birthday.setTag(false);// 非必填


        TextView sex = findViewById(R.id.user_sex);
        sex.setText(userEntity.getSex() == null ? "" : userEntity.getSex());
        sex.setTag(true);// 必填


        TextView sign = findViewById(R.id.user_sign);
        sign.setText(userEntity.getSign() == null ? "" : userEntity.getSign());
        sign.setTag(false);// 必填

        TextView region = findViewById(R.id.user_region);
        region.setText(userEntity.getRegion() == null ? "" : userEntity.getRegion());
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

    @SuppressLint("NonConstantResourceId")
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
                useDatePicker(v);
                break;
            case R.id.user_sex_layout:
                useBottomSexMenu(v);
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
                plugCamera = new PlugCamera(this,UserActivity.this);
                plugCamera.showCamera();
                break;
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 获取弹窗里面的布局
     * @date: 2024-04-22 22:18
     */
    private View getEditUserLayout(String title){
        View toInsertLayout = getLayoutInflater().inflate(R.layout.edit_user_input, null);
        TextView editTitle = toInsertLayout.findViewById(R.id.edit_user_title);
        editTitle.setText(title);
        return toInsertLayout;
    }

    /**
     * @author: wuwenqiang
     * @description: 编辑出生年月
     * @date: 2024-04-22 22:18
     */
    private void useDatePicker(View v){
        LinearLayout ly = (LinearLayout)v;
        TextView valueTextView = (TextView) ly.getChildAt(1);
        String value = valueTextView.getText().toString();
        int year = 1990,month = 1,day = 1;
        if(!"".equals(value)){
            String[] split = value.split("-");
            year = Integer.parseInt(split[0]);
            month = Integer.parseInt(split[1]);
            day = Integer.parseInt(split[2]);
        }
        DatePickerFragment datePickerFragment = new DatePickerFragment(year,month,day, (selectedDate)->{
            useUpdateUser(valueTextView,"birthday",selectedDate);
        });
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * @author: wuwenqiang
     * @description: 更新用户信息
     * @date: 2024-4-22 22:39
     */
    private void useUpdateUser(TextView valueTextView,String field,String value){
        if(loadingLayout == null){
            // 获取LayoutInflater服务
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            loadingLayout = inflater.inflate(R.layout.layout_loading, (ViewGroup)getWindow().getDecorView().getRootView()).findViewById(R.id.layout_loading);
        }
        loadingLayout.setVisibility(View.VISIBLE);
        UserEntity userEntity = new UserEntity();
        ReflectHelper reflectHelper = new ReflectHelper(userEntity);
        reflectHelper.setMethodValue(field,value);
        Call<ResultEntity> updateUserCall = RequestUtils.getInstance().updateUser(userEntity);
        updateUserCall.enqueue(new Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                if(Constants.SUCCESS.equals(response.body().getStatus())){
                    UserEntity mUserEntity = BaseApplication.getInstance().getUserEntity();
                    ReflectHelper reflectHelper = new ReflectHelper(mUserEntity);
                    reflectHelper.setMethodValue(field,value);
                    valueTextView.setText(value);
                    Toast.makeText(UserActivity.this,getResources().getString(R.string.update_success_tip), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UserActivity.this,getResources().getString(R.string.update_fail_tip), Toast.LENGTH_SHORT).show();
                }
                loadingLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResultEntity> call, Throwable t) {
                loadingLayout.setVisibility(View.GONE);
            }
        });
    }

    /**
     * @author: wuwenqiang
     * @description: 底部弹出男女选择菜单
     * @date: 2024-4-22 22:20
     */
    private void useBottomSexMenu(View v){
        LinearLayout ly = (LinearLayout)v;
        TextView valueTextView = (TextView) ly.getChildAt(1);
        String[] menu = {"男","女"};
        new BottomMenu(menu, item -> {
            if(!item.equals(valueTextView.getText().toString())){
                useUpdateUser(valueTextView,"sex",item);
            }
        }).showBottomMenu(this);
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
        Boolean isRequire = (Boolean) valueTextView.getTag();

        View toInsertLayout = getLayoutInflater().inflate(R.layout.edit_user_input, null);
        TextView editTitle = toInsertLayout.findViewById(R.id.edit_user_title);
        editTitle.setText(title);
        this.field = field;// 记录现在修改是哪个字段
        View editUserLayout = getEditUserLayout(title);
        EditText input = editUserLayout.findViewById(R.id.edit_user_input);
        input.setText(value);

        CustomDialogFragment customDialogFragment = new CustomDialogFragment(editUserLayout,"修改"+title,()->{
            useUpdateUser(valueTextView,field,input.getText().toString());
        });

        customDialogFragment.show(getSupportFragmentManager(), "custom_dialog");
        if(isRequire){
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // 文本改变之前的处理
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // 文本正在改变的处理
                    String value = s.toString();
                    customDialogFragment.getSureBtn().setClickable(!"".equals(value));
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // 文本改变之后的处理
                }
            });
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 跳转到登录页，前面所有页面置空
     * @date: 2022-08-31 22:06
     */
    private void logout(){
        CustomDialogFragment  customDialogFragment = new CustomDialogFragment(R.layout.logout_text, getResources().getString(R.string.user_login), () -> {
            SharedPreferencesUtils.setParam(UserActivity.this,"token","");
            Intent intent = new Intent(UserActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 前面所有页面置空
            startActivity(intent);
            ActivityCollectorUtil.finishAllActivity();
        });
        customDialogFragment.show(getSupportFragmentManager(), "custom_dialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == PlugCamera.REQUEST_CODE_CAMERA) {
            // 从相册返回的数据
            Log.e(this.getClass().getName(), "Result:" + intent.toString());
            if (intent != null) {
                // 得到图片的全路径
                if("相机".equals(plugCamera.getCheck())){
                    Bundle bundle = intent.getExtras(); // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
                    Bitmap bitmap = (Bitmap) bundle.get("data"); // 将data中的信息流解析为Bitmap类型
                    roundedImageView.setImageBitmap(bitmap);
                }else {
                    Uri uri = intent.getData();
                    roundedImageView.setImageURI(uri);
                }
            }
        }else if (resultCode == RESULT_OK){
            initUI();
            if("username".equals(field) || "sign".equals(field)){
                sendBroadcast(new Intent(UpdateUserReciver.TAG));
            }
        }
    }
}
