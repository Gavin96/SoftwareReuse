# SoftwareReuse

### Activemq:

- Windows:
  1. 解压apache-activemq-5.12.1-bin.Zip
  2. 进入conf/activemq.xml进行修改，在红框内加上useJmx="true"，如下图:
  ![](https://s3.amazonaws.com/f.cl.ly/items/1B0k3G0z063l411U3C2H/Image%202016-03-20%20at%209.41.19%20PM.png)
  3. 将managementContext createConnector="false"改为managementContext createConnector="true",如下图：
  ![](http://f.cl.ly/items/0v183y322S143O052R08/Image%202016-03-20%20at%209.44.24%20PM.png)
  4. 启动activemq,进入bin文件夹，进入win32/win64，双击activemq.bat，若出现:
  ![](http://f.cl.ly/items/2X3Q071b1S0V0x1U2D1N/Image%202016-03-20%20at%209.47.20%20PM.png)
  则启动成功（这个黑框不能关闭）
  5. 此时进入http://localhost:8161/admin(用户名admin，密码admin）
  6. 导入项目后，需导入自己activemq的jar（activemq-all-5.12.1.jar）

- Mac:

  1.使用homebrew安装Activemq
  
  ```bash
  $ /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)" 
  //如果你没有安装homebrew，执行上一条，否则跳过
  $ brew install activemq
  ```
  
  2.配置JMX监制
  ...待补充

### Server 接口和返回值说明

- int login(String username,String password)
  返回值说明：

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
