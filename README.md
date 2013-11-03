homework-05
===========

GpointGame

客户端包括ClientDemo.java 和 ClientThread.java两个源码
ClientDemo.java 是客户端入口

服务器端的源码是
Excel.java        用于存取玩家用户名和密码
ServerDemo.java   服务端入口即主程序
ServerThread.java 服务端为每个用户服务的线程
UserMessage.java  用户类，运行时数据\n
ViewFrame.java    服务器界面

其中服务器端调用了一个外部包jxl.jar 用于将用户数据保存在excel文件中
