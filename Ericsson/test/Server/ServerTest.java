package Server;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import javax.jms.JMSException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ServerTest {
	
	Server server;
	@Before
	public void setUp() throws Exception {
		server = new Server();
	}

	@Test
	public void testFindUser() throws Exception{
		Method testFindUser = server.getClass().getDeclaredMethod("findUser",String.class);
		testFindUser.setAccessible(true);
		//用户名存在的情况下
		Object user1 = testFindUser.invoke(server, "liu");
		Assert.assertNotNull(user1);
		//用户名不存在的情况下
		Object user2 = testFindUser.invoke(server, "l");
		Assert.assertNull(user2);
	}
	
	@Test
	public void testCheckConnection() throws Exception{
		Method getFindUser = server.getClass().getDeclaredMethod("findUser",String.class);
		getFindUser.setAccessible(true);
		User user = (User)getFindUser.invoke(server, "liu");
		//在登陆状态下测试
		user.isLogin = true;
		Boolean result1 = server.checkConnection("liu");
		Assert.assertTrue(result1);
		//在未登录状态下测试
		user.isLogin = false;
		Boolean result2 = server.checkConnection("liu");		
		Assert.assertFalse(result2);
		//用不对的用户名测试
		Boolean result3 = server.checkConnection("llll");
		Assert.assertFalse(result3);
	}

	@Test
	public void testSendMessages() throws Exception{
		Method getFindUser = server.getClass().getDeclaredMethod("findUser",String.class);
		getFindUser.setAccessible(true);
		User user = (User)getFindUser.invoke(server, "liu");
		//可以发送消息状态
		user.sendMessagesNum = 80;
		Boolean result1 = server.sendMessages("liu");
		Assert.assertTrue(result1);
		//不可以发送消息状态
		user.sendMessagesNum = 180;
		Boolean result2 = server.sendMessages("liu");
		Assert.assertFalse(result2);
		//用户名不存在的情况下
		Boolean result3 = server.sendMessages("l");
		Assert.assertFalse(result3);
		
	}

	@Test
	public void testSendMsg() {
		ExpectedException thrown= ExpectedException.none();
		thrown.expect(JMSException.class);
	    server.sendMsg("hahaha", "aa");
	}
	
	@Test
	public void testCheckTime() throws Exception{
		Method getFindUser = server.getClass().getDeclaredMethod("findUser",String.class);
		getFindUser.setAccessible(true);
		User user = (User)getFindUser.invoke(server, "liu");
		
		Method testCheckTime = server.getClass().getDeclaredMethod("checkTime",User.class);
		testCheckTime.setAccessible(true);
		//根据调用checkTime（）函数的情况，可以判断此时user对象是一定存在的，所以测试时不用测试user对象不存在的情况
		//请求次数为0的情况下
		user.loginRequsetTime = 0;
		Boolean result1 = (Boolean) testCheckTime.invoke(server, user);
		Assert.assertTrue(result1);
		//请求次数大于0并且距离上次请求时间小于1s的情况下的两种情况
		//<1>请求次数小于5次
		user.loginDate = System.currentTimeMillis();
		user.loginRequsetTime = 3;
		Boolean result2 = (Boolean) testCheckTime.invoke(server, user);
		Assert.assertTrue(result2);
		//<2>请求次数大于5次
		user.loginDate = System.currentTimeMillis();
		user.loginRequsetTime = 6;
		Boolean result3 = (Boolean) testCheckTime.invoke(server, user);
		Assert.assertFalse(result3);
		//请求次数不为0并且请求时间间隔大于1s
		user.loginDate = System.currentTimeMillis()-2000;
		user.loginRequsetTime = 6;
		Boolean result4 = (Boolean) testCheckTime.invoke(server, user);
		Assert.assertTrue(result4);
	}
	
	@Test
	public void testLogin() throws Exception{
		//能正常登陆的情况
		int result1 = server.login("liu", "123");
		Assert.assertEquals(result1, 200);
		//密码不正确的情况
		int result2 = server.login("liu", "0");
		Assert.assertEquals(result2, 201);
		//每秒请求次数大于5次
		Method getFindUser = server.getClass().getDeclaredMethod("findUser",String.class);
		getFindUser.setAccessible(true);
		User user = (User)getFindUser.invoke(server, "liu");
		user.loginDate = System.currentTimeMillis();
		user.loginRequsetTime = 6;
		int result3 = server.login("liu", "123");
		Assert.assertEquals(result3, 203);
		//用户名不存在
		int result4 = server.login("l", "123");
		Assert.assertEquals(result4, 202);
		
	}

}
