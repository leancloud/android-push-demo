package com.avos.avoscloud.PushDemo;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

import cn.leancloud.LCException;
import cn.leancloud.LCInstallation;
import cn.leancloud.LCPush;
import cn.leancloud.LCQuery;
import cn.leancloud.json.JSONObject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class PushDemo extends AppCompatActivity {
  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    final String installationId = LCInstallation.getCurrentInstallation().getInstallationId();

    final TextView t = (TextView) this.findViewById(R.id.mylabel);
    // 显示的设备的 installationId，用于推送的设备标示
    t.setText("这个设备的 id: " + installationId);

    final EditText channelEdit = (EditText) this.findViewById(R.id.channel);
    final EditText msgEdit = (EditText) this.findViewById(R.id.message);
    final Button customPushtn = (Button) this.findViewById(R.id.customPush);
    customPushtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LCQuery pushQuery = LCInstallation.getQuery();
        pushQuery.whereEqualTo("channels", channelEdit.getText().toString());
        LCPush push = new LCPush();
        push.setQuery(pushQuery);
        push.setMessage(msgEdit.getText().toString());
        push.setPushToAndroid(true);
        push.sendInBackground().subscribe(new Observer<JSONObject>() {
          @Override
          public void onSubscribe(Disposable d) {
          }
          @Override
          public void onNext(JSONObject jsonObject) {
            System.out.println("推送成功" + jsonObject);
          }
          @Override
          public void onError(Throwable throwable) {
            System.out.println("推送失败，错误信息：" + throwable.getMessage());
          }
          @Override
          public void onComplete() {
          }
        });

      }
    });
    final Button pushButton = (Button) this.findViewById(R.id.pushBtn);
    pushButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        System.out.println("installationId：" + installationId);

        LCQuery pushQuery = LCInstallation.getQuery();
        pushQuery.whereEqualTo("installationId", installationId);
        LCPush push = new LCPush();
        push.setPushToAndroid(true);
        push.sendMessageInBackground(msgEdit.getText().toString(),pushQuery).subscribe(new Observer() {
          @Override
          public void onSubscribe(Disposable d) {
          }
          @Override
          public void onNext(Object object) {
            System.out.println("推送成功" + object);
          }
          @Override
          public void onError(Throwable throwable) {
            LCException exception = new LCException(throwable);
            System.out.println("推送失败，错误信息：" + exception.getCode() +exception.getMessage());
          }
          @Override
          public void onComplete() {
          }
        });
      }
    });
  }
}
