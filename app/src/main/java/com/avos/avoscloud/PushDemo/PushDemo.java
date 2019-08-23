package com.avos.avoscloud.PushDemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;

import cn.leancloud.AVInstallation;
import cn.leancloud.AVObject;
import cn.leancloud.AVPush;
import cn.leancloud.AVQuery;
import cn.leancloud.push.PushService;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class PushDemo extends Activity {
  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    // 设置默认打开的 Activity
    PushService.setDefaultPushCallback(this, PushDemo.class);
    // 订阅频道，当该频道消息到来的时候，打开对应的 Activity
    PushService.subscribe(this, "public", PushDemo.class);
    PushService.subscribe(this, "private", Callback1.class);
    PushService.subscribe(this, "protected", Callback2.class);

    final Context context = this;

    final TextView t = (TextView) this.findViewById(R.id.mylabel);
    // 显示的设备的 installationId，用于推送的设备标示
    t.setText("这个设备的 id: " + AVInstallation.getCurrentInstallation().getInstallationId());
    // 保存 installation 到服务器
    AVInstallation.getCurrentInstallation().saveInBackground().subscribe(new Observer<AVObject>() {
      @Override
      public void onSubscribe(Disposable d) {
      }
      @Override
      public void onNext(AVObject avObject) {
        // 关联 installationId 到用户表等操作。
        String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
        System.out.println("保存成功：" + installationId );
      }
      @Override
      public void onError(Throwable e) {
        System.out.println("保存失败，错误信息：" + e.getMessage());
      }
      @Override
      public void onComplete() {
      }
    });

    final EditText channelEdit = (EditText) this.findViewById(R.id.channel);
    final EditText msgEdit = (EditText) this.findViewById(R.id.message);
    final Button btn = (Button) this.findViewById(R.id.pushBtn);
    btn.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        AVPush push = new AVPush();
        // 设置频道
        push.setChannel(channelEdit.getText().toString().trim());
        // 设置消息
        push.setMessage(msgEdit.getText().toString().trim());
        // 设置查询条件，只推送给自己，不要打扰别人啦，这是 demo
        push.setQuery(AVInstallation.getQuery().whereEqualTo("installationId",
            AVInstallation.getCurrentInstallation().getInstallationId()));
        // 推送
        push.sendInBackground().subscribe(new Observer<JSONObject>() {
          @Override
          public void onSubscribe(Disposable d) {
          }
          @Override
          public void onNext(JSONObject jsonObject) {
            Toast.makeText(context, "Send successfully.", Toast.LENGTH_SHORT).show();
          }
          @Override
          public void onError(Throwable e) {
            Toast.makeText(context, "Send fails with :" + e.getMessage(), Toast.LENGTH_LONG).show();
          }
          @Override
          public void onComplete() {
          }
        });
      }
    });

    View customPushButton = this.findViewById(R.id.customPush);
    customPushButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        AVPush push = new AVPush();

        AVQuery<AVInstallation> query = AVInstallation.getQuery();
        query.whereEqualTo("installationId", AVInstallation.getCurrentInstallation()
            .getInstallationId());
        push.setQuery(query);
        push.setChannel(channelEdit.getText().toString().trim());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "com.pushdemo.action");
        jsonObject.put("alert", msgEdit.getText().toString().trim());

        push.setData(jsonObject);
        push.setPushToAndroid(true);

        push.sendInBackground().subscribe(new Observer<JSONObject>() {
          @Override
          public void onSubscribe(Disposable d) {
          }
          @Override
          public void onNext(JSONObject jsonObject) {
            Toast.makeText(context, "Send successfully.", Toast.LENGTH_SHORT).show();
          }
          @Override
          public void onError(Throwable e) {
            Toast.makeText(context, "Send fails with :" + e.getMessage(), Toast.LENGTH_LONG).show();
          }
          @Override
          public void onComplete() {
          }
        });
      }
    });
  }
}
