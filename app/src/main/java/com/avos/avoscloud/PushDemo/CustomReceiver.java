package com.avos.avoscloud.PushDemo;

import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import cn.leancloud.AVOSCloud;

public class CustomReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    try {
      if (intent.getAction().equals("com.pushdemo.action")) {
        JSONObject json = new JSONObject(intent.getExtras().getString("com.avos.avoscloud.Data"));
        final String message = json.getString("alert");
        Intent resultIntent = new Intent(AVOSCloud.getContext(), PushDemo.class);
        PendingIntent pendingIntent =
            PendingIntent.getActivity(AVOSCloud.getContext(), 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(AVOSCloud.getContext())
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(
                    AVOSCloud.getContext().getResources().getString(R.string.app_name))
                .setContentText(message)
                .setTicker(message);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);

        int mNotificationId = 10086;
        NotificationManager mNotifyMgr =
            (NotificationManager) AVOSCloud.getContext()
                .getSystemService(
                    Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
      }
    } catch (Exception e) {

    }
  }
}
