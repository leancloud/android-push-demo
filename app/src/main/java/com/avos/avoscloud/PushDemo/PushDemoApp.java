package com.avos.avoscloud.PushDemo;

import android.app.Application;

import cn.leancloud.AVLogger;
import cn.leancloud.AVOSCloud;

/**
 * Created with IntelliJ IDEA. User: tangxiaomin Date: 4/19/13 Time: 12:57 PM
 */
public class PushDemoApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    //开启调试日志
    AVOSCloud.setLogLevel(AVLogger.Level.DEBUG);
    // 初始化应用信息
    AVOSCloud.initialize(this, "Gvv2k8PugDTmYOCfuK8tiWd8-gzGzoHsz",
        "dpwAo94n81jPsHVxaWwdxJVu");
  }
}
