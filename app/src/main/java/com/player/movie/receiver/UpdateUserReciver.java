package com.player.movie.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.player.movie.entity.UserEntity;

public class UpdateUserReciver extends BroadcastReceiver {
    public static final String TAG = "UpdateUserBroadcastReceiver";

    private static MessageListener mMessageListener;

    public UpdateUserReciver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        UserEntity userEntity = JSON.parseObject(intent.getStringExtra("userData"), UserEntity.class);
        mMessageListener.OnReceived(userEntity);
    }

    // 回调接口
    public interface MessageListener {

        /**
         * @author: wuwenqiang
         * @description: 接收到自己的验证码时回调
         * @date: 2023-04-20 23:07
         */
        void OnReceived(UserEntity userEntity);
    }

    /**
     * 设置验证码接收监听
     *
     * @param messageListener 自己验证码的接受监听，接收到自己验证码时回调
     */
    public void setOnReceivedMessageListener(MessageListener messageListener) {
        mMessageListener = messageListener;
    }
}
