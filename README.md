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

===

### 可复用构件(Reuse Component):

- [Performance Management Model](https://github.com/Gavin96/SoftwareReuse/blob/master/PerformanceManager/src/com/HaroldLIU/PerformanceManager.java):
接收系统的性能指标，每分钟自动生成报告并且输出到单独的性能文件(包括报告时间)
使用方法:

首先需要引入[jar包](https://github.com/Gavin96/SoftwareReuse/tree/master/Jar)
```java

/**
 * path: 输出文件的路径
 * delay: 多长时间输出一次
 */
PerformanceManager performanceManager = new PerformanceManager(String path,long delay);
performanceManager.start();
//现在支持两种性能属性，成功登录次数和失败登录次数
performanceManager.successTime;
performanceManager.failTime;
```
TODO: 自定义性能属性

- [License Model](https://github.com/Gavin96/SoftwareReuse/blob/master/LicenseManager/src/com/HaroldLIU/LicenseManager.java):
提供Throughput和Capacity两种检测方式，同样需要引入[jar包](https://github.com/Gavin96/SoftwareReuse/tree/master/Jar)

```java
//初始化
LicenseManager licenseManager = new LicenseManager();
/**
 * max: 预设的上限
 * init: 计数初始值
 */
licenseManager.CapacityInit(int max, int init);
/**
 * max: 预设的上限
 * time: 时间间隔
 * init: 计数初始值
 */
licenseManager.ThroughputInit(int max,long time, int init);
//使用:
// 返回值为bool，如果是true表示未超过上限，如果是false表示已超过上限
licenseManager.CapacityCheck();
licenseManager.ThroughputCheck();
```

- [通讯组件](https://github.com/Gavin96/SoftwareReuse/blob/master/Ericsson/src/Topic/MySubscriber.java)
在activemq的基础上，对activemq提供的topic模式进行了一定的封装，为实现收发消息的系统提供了更简易的Topic收发订阅组件
使用方法:
```java
//需要jar包，同上
long ClientCount=MySubscriber.getConsumerCount();
```
===


### 使用方法(Install)
- 本项目依赖Activemq框架，因此需要引入activemq的jar包，[下载jar](http://www.apache.org/dyn/closer.cgi?path=/activemq/5.13.2/apache-activemq-5.13.2-bin.zip).
- **并需要开启JMX监听，具体开启方法如下:**
- For Windows User:
  1. 解压apache-activemq-5.12.1-bin.Zip
  2. 进入conf/activemq.xml进行修改,
      - 首先要添加上 **useJmx="true"**
      - 其次是要将**managementContext createConnector="false"的false置为true**
  3. 启动activemq,进入bin文件夹，进入win32/win64，双击activemq.bat，若出现: access to all MBeans is allowed表明开启成功，注意这个控制台程序不能在运行时关闭.
  4. 此时进入http://localhost:8161/admin(用户名admin，密码admin）
```xml
<broker xmlns="http://activemq.apache.org/schema/core" brokerName="localhost" useJmx="true" dataDirectory="${activemq.data}">

<managementContext>
     <managementContext createConnector="true"/>
</managementContext>

```
- For Mac OS X User:
 
1.使用homebrew安装Activemq
  
```bash
$ /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)" 
//如果你没有安装homebrew，执行上一条，否则跳过
$ brew install activemq
```
  2.配置JMX监制
```bash
$ cd usr/local/Cellar/activemq/5.11.2/libexec/conf
$ vi activemq.xml
//修改的地方同windows
```
  3.开启activemq
```bash
$ cd 
$ activemq start 
```

- 将项目clone到本地:
```bash 
$ git clone https://github.com/Gavin96/SoftwareReuse.git
//🍺=>然后你就可以运行了
```

===

### Server 接口和返回值说明

- public boolean signUp(String userName,String password)

返回值 | 代表结果 | 
--- | --- | 
true  | 注册成功  | 
false | 注册失败,用户名已存在 |


- int login(String username,String password)

返回值 | 代表结果 | 
--- | --- | 
200 | 登录成功 | 
201 | 密码错误 |
202 | 用户名不存在 |
203 | 每秒请求超过5次 |

- boolean checkConnection(String userName)

返回值 | 代表结果 | 
--- | --- | 
true  | 在登录  | 
false | 未登录 |

- void sendMessages(String msg,String senderName )

====

Copyright 2016 &copy;  Group 3
