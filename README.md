# myzone —— a simple email demo

设计实现：

1、使用轻量级框架jfinal作为持久层框架；

2、使用mysql作为数据库；

3、使用freeMark作为模板引擎；

4、使用bootstrap作为前端界面ui。



使用声明

1、本系统由于时间原因，属于残次品，仅为个人练习或毕业设计使用；

2、因时间原因，页面处理部分地方不尽合理，表单少有信息校验(建议演示时尽量填写完整)，待优化；

3、业务逻辑走主要流程尚可，部分地方不尽合理，待优化；

4、数据库设计不尽合理，待优化；

5、系统开发未完待续。


项目启动步骤

1： 使用 myzone.sql 中的 sql 语句创建数据库与数据库表

2: 修改 src/doc/dataSource.txt 文件，填入正确的数据库连接用户名、密码

3: 将项目导入 eclipse。推荐使用 Eclipse IDE for Java EE Developers， 版本：Eclipse Kepler (4.3.2) SR2，此版本天然支持 git
        下载地址:
        32位： https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/kepler/SR2/eclipse-jee-kepler-SR2-win32.zip&mirror_id=547
        64位： https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/kepler/SR2/eclipse-jee-kepler-SR2-win32-x86_64.zip&mirror_id=547
        如果觉得皮肤不好看，可以通过设置：Windows ---> Preferences ---> General ---> Appearance ---> Theme 选择 classic

4: 打开 com.myzone.config包下的 StartServer 文件，右键单击该文件并选择 Debug As ---> Java Application。
        其它启动项目的方式见 《JFinal手册》。除此之外，项目还可以与其它普通java web 项目一样使用 tomcat
   jetty 等 web server 来启动，启动方式与非jfinal项目完全一样。

5: 打开浏览器输入  localhost 即可查看运行效果

注意： 请确保您安装了 JavaSE 1.6 或更高版本，tomcat下运行项目需要先删除 jetty-server-xxx.jar，否则会有冲突
