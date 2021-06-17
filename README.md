## 介绍

一个 LeanCloud 推送消息的简单 Demo，直接在客户端推送消息，并自己接收。

![img](https://raw.githubusercontent.com/lzwjava/plan/master/push.png)


**实际生产环境中，建议从应用设置里关闭从客户端推送，并且使用我们提供的推送 web 平台或者 REST API 发起服务端推送更为合适。否则推送可能被滥用。**

你可以从推送 Demo 中学到：

* 如何自定义接受推送信息的 `Receiver`
* 如何订阅频道
* 如何保存 installation
* 如何根据 `installationId` 或 `channel` 推送，随意定义你的推送对象
* 如何推送 `json` 数据以及获取数据
* 如何从客户端发起推送

## 替换 App 信息

Demo 使用的是公共的 AppId、AppKey 和 ServerUrl，您可以在`com.avos.avoscloud.PushDemo.PushDemoApp`修改成您自己的应用 AppId、AppKey 和 ServerUrl。
