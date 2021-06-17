package com.avos.avoscloud.PushDemo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CustomReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    // 获取推送消息数据
    String message = intent.getStringExtra("com.avoscloud.Data");
    String channel = intent.getStringExtra("com.avoscloud.Channel");
    System.out.println("message=" + message + ", channel=" + channel);
  }
}
