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