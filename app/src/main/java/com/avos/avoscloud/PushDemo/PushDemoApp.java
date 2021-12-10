package com.avos.avoscloud.PushDemo;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import cn.leancloud.LCInstallation;
import cn.leancloud.LCLogger;
import cn.leancloud.LCObject;
import cn.leancloud.LeanCloud;
import cn.leancloud.push.PushService;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PushDemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //开启调试日志
        LeanCloud.setLogLevel(LCLogger.Level.DEBUG);
        // 初始化应用信息
        LeanCloud.initialize("Gvv2k8PugDTmYOCfuK8tiWd8-gzGzoHsz", "dpwAo94n81jPsHVxaWwdxJVu", "https://gvv2k8pu.lc-cn-n1-shared.com");
        // 设置默认打开的 Activity
        PushService.setDefaultPushCallback(this, PushDemo.class);
//     订阅频道，当该频道消息到来的时候，打开对应的 Activity
//     参数依次为：当前的 context、频道名称、回调对象的类
        PushService.subscribe(this, "public", PushDemo.class);
        PushService.subscribe(this, "private", PushDemo.class);
        PushService.subscribe(this, "protected", PushDemo.class);

//     设置通知展示的默认 channel
        NotificationChannel channel = new NotificationChannel("channelid01", "channelid01", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("用于 Demo 测试的 channel");
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        PushService.setDefaultChannelId(this, channel.getId());


//    保存 Installation
        LCInstallation.getCurrentInstallation().saveInBackground().subscribe(new Observer<LCObject>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(LCObject avObject) {
                // 关联 installationId 到用户表等操作。
                String installationId = LCInstallation.getCurrentInstallation().getInstallationId();
                System.out.println("保存成功：" + installationId);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("保存失败，错误信息：" + e.getMessage());
            }

            @Override
            public void onComplete() {
            }
        });


    }
}
