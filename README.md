## 介绍

一个 AVOS Cloud 推送消息的简单 Demo，直接在客户端推送消息，并自己接收。

**实际生产环境中，建议从应用设置里关闭从客户端推送，并且使用我们提供的推送 web 平台或者 REST API 发起服务端推送更为合适。否则推送可能被滥用。**

演示功能：

* installation保存
* 从客户端发起推送
* 订阅频道

## 如何运行

* 导入本工程到 Eclipse
* 右键点击项目，运行 `Run As -> Android Application`即可看到。

## 替换 App 信息

Demo 使用的是公共的 app id 和 app key，您可以在`com.avos.avoscloud.PushDemo.PushDemoApp`修改成您自己的应用 id 和 key。
