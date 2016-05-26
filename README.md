# SoftwareReuse

### Team Members:

- 罗晓丹[luoxiaodan](https://github.com/luoxiaodan)
- 倪雨婷[nytfancy](https://github.com/nytfancy)
- 王刚 [Gavin96](https://github.com/Gavin96)
- 叶坤宇[KieranYe](https://github.com/KieranYe)
- 刘旭东[xdliu002](https://github.com/xdliu002)

===

### Documents:

- [管理文档](https://github.com/Gavin96/SoftwareReuse/blob/master/Document/%E7%AE%A1%E7%90%86%E6%96%87%E6%A1%A3.pdf)
- [测试文档](https://github.com/Gavin96/SoftwareReuse/blob/master/Document/%E6%B5%8B%E8%AF%95%E6%96%87%E6%A1%A3.pdf)
- [程序文档](https://github.com/Gavin96/SoftwareReuse/blob/master/Document/%E7%A8%8B%E5%BA%8F%E6%96%87%E6%A1%A3.pdf)
- [复用文档](https://github.com/Gavin96/SoftwareReuse/blob/master/Document%2F%E5%A4%8D%E7%94%A8%E6%96%87%E6%A1%A3.pdf)
- [构件测试文档](https://github.com/Gavin96/SoftwareReuse/blob/master/Document%2F%E6%9E%84%E4%BB%B6%E6%B5%8B%E8%AF%95%E6%96%87%E6%A1%A3.pdf)
- [重构复用文档](https://github.com/Gavin96/SoftwareReuse/tree/master/Document/%E9%87%8D%E6%9E%84%E7%A8%8B%E5%BA%8F%E6%96%87%E6%A1%A3)
- [实践6测试文档](https://github.com/Gavin96/SoftwareReuse/blob/master/Document%2F%E5%AE%9E%E8%B7%B56%E6%B5%8B%E8%AF%95%E6%96%87%E6%A1%A3.pdf)
- [实践7测试文档](https://github.com/Gavin96/SoftwareReuse/blob/master/Document%2F%E5%AE%9E%E8%B7%B57%E6%B5%8B%E8%AF%95%E6%96%87%E6%A1%A3.pdf)
- [实践8测试文档](https://github.com/Gavin96/SoftwareReuse/blob/master/Document%2F%E5%AE%9E%E8%B7%B58%E6%B5%8B%E8%AF%95%E6%96%87%E6%A1%A3.pdf)
- [实践9测试文档](https://github.com/Gavin96/SoftwareReuse/blob/master/Document%2F%E5%AE%9E%E8%B7%B59%E6%B5%8B%E8%AF%95%E6%96%87%E6%A1%A3.pdf)
- [ActiveMQ 消息持久化说明](https://github.com/Gavin96/SoftwareReuse/blob/master/%E6%8C%81%E4%B9%85%E5%8C%96%E8%AF%B4%E6%98%8E.md)

===

### 可复用构件(Reuse Component):

**具体使用方法参见[可复用构件使用说明](https://github.com/Gavin96/SoftwareReuse/blob/master/%E5%A4%8D%E7%94%A8%E6%9E%84%E4%BB%B6/%E5%8F%AF%E5%A4%8D%E7%94%A8%E6%9E%84%E4%BB%B6%E5%8F%8A%E5%85%B6%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)**

⚠️构件运行的java版本为:1.8.

- [Configuration Management Model](https://github.com/Gavin96/SoftwareReuse/blob/master/%E5%A4%8D%E7%94%A8%E6%9E%84%E4%BB%B6%2FConfigurationManager%2Fsrc%2FConfiguration%2FConfiguration.java)
读取文件中的系统配置指标，并且提供实时地查询和动态加载功能.**👉[jar包](https://github.com/Gavin96/SoftwareReuse/blob/master/Jar%2FCM%2FConfiguration.jar?raw=true)**,同时需要引入对于JSON数据进行处理的**[jar包](https://github.com/Gavin96/SoftwareReuse/tree/master/Jar/CM/JSON)**


- [Performance Management Model](https://github.com/Gavin96/SoftwareReuse/blob/master/%E5%A4%8D%E7%94%A8%E6%9E%84%E4%BB%B6%2FPerformanceManager%2Fsrc%2Fcom%2FHaroldLIU%2FPerformanceManager.java):
接收系统的性能指标，每分钟自动生成报告并且输出到单独的性能文件(包括报告时间)，**👉[jar包](https://github.com/Gavin96/SoftwareReuse/blob/master/Jar%2FPM%2FPerformanceManager.jar?raw=true)**

- [License Model](https://github.com/Gavin96/SoftwareReuse/blob/master/%E5%A4%8D%E7%94%A8%E6%9E%84%E4%BB%B6%2FLicenseManager%2Fsrc%2Fcom%2FHaroldLIU%2FLicenseManager.java):
提供Throughput和Capacity两种检测方式，**👉[jar包](https://github.com/Gavin96/SoftwareReuse/blob/master/Jar%2FLicense%2FLicenseManager.jar?raw=true)**

- [通讯组件](https://github.com/Gavin96/SoftwareReuse/blob/master/%E5%A4%8D%E7%94%A8%E6%9E%84%E4%BB%B6%2FTopic%2Fcom%2FTopicLuo%2FMySubscriber.java)
在activemq的基础上，对activemq提供的topic模式进行了一定的封装，为实现收发消息的系统提供了更简易的Topic收发订阅组件，**👉[jar包](https://github.com/Gavin96/SoftwareReuse/blob/master/Jar%2FTopic.jar?raw=true)**

===

### 使用方法(Install)
> 本项目依赖Activemq框架，因此需要引入activemq的jar包，[下载jar](http://www.apache.org/dyn/closer.cgi?path=/activemq/5.13.2/apache-activemq-5.13.2-bin.zip).
> 并需要开启JMX监听，具体开启方法如下:

#### Windows User:
- 解压apache-activemq-5.12.1-bin.Zip
- 进入conf/activemq.xml进行修改
   - 在``<broker >``里面添加上 ``useJmx="true"``
   - 将``managementContext createConnector="false"``的``false``置为``true``
- 启动activemq,进入bin文件夹，进入win32/win64，双击activemq.bat，若出现: ``access to all MBeans is allowed``表明开启成功，注意这个控制台程序不能在运行时关闭.
- 此时进入http://localhost:8161/admin(用户名admin，密码admin）

```xml
<broker xmlns="http://activemq.apache.org/schema/core" brokerName="localhost" useJmx="true" dataDirectory="${activemq.data}">

<managementContext>
     <managementContext createConnector="true"/>
</managementContext>

```
#### Mac OS X User:
 
- 使用homebrew安装ActiveMQ
  
```bash
$ /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)" 
//如果你没有安装homebrew，执行上一条，否则跳过
$ brew install activemq
```
   - 配置JMX监制
```bash
$ cd usr/local/Cellar/activemq/5.11.2/libexec/conf
$ vi activemq.xml
//修改的地方同windows
```
   - 开启ActiveMQ
```bash
$ cd 
$ activemq start 
```
   - 将项目clone到本地:
```bash 
$ git clone https://github.com/Gavin96/SoftwareReuse.git
//🍺=>然后你就可以运行了
```

====

Copyright 2016 &copy;  Group 3
