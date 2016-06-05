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
		server = Server.sharedServer();
	}

	@Test
	public void testFindUser() throws Exception{
		Method testFindUser = server.getClass().getDeclaredMethod("findUser",String.class);
		testFindUser.setAccessible(true);
		//�û�����ڵ������
		Object user1 = testFindUser.invoke(server, "liu");
		Assert.assertNotNull(user1);
		//�û�����ڵ������
		Object user2 = testFindUser.invoke(server, "l");
		Assert.assertNull(user2);
	}

	@Test
	public void testCheckConnection() throws Exception{
		Method getFindUser = server.getClass().getDeclaredMethod("findUser",String.class);
		getFindUser.setAccessible(true);
		User user = (User)getFindUser.invoke(server, "liu");
		//�ڵ�½״̬�²���
		user.setIsLogin(true);
		Boolean result1 = server.checkConnection("liu");
		Assert.assertTrue(result1);
		//��δ��¼״̬�²���
		user.setIsLogin(false);
		Boolean result2 = server.checkConnection("liu");
		Assert.assertFalse(result2);
		//�ò��Ե��û������
		Boolean result3 = server.checkConnection("llll");
		Assert.assertFalse(result3);
	}

//	@Test
//	public void testSendMessages() throws Exception{
//		Method getFindUser = server.getClass().getDeclaredMethod("findUser",String.class);
//		getFindUser.setAccessible(true);
//		User user = (User)getFindUser.invoke(server, "liu");
//		//���Է�����Ϣ״̬
//		user.sendMessagesNum = 80;
//		Boolean result1 = server.sendMessages("liu");
//		Assert.assertTrue(result1);
//		//�����Է�����Ϣ״̬
//		user.sendMessagesNum = 180;
//		Boolean result2 = server.sendMessages("liu");
//		Assert.assertFalse(result2);
//		//�û�����ڵ������
//		Boolean result3 = server.sendMessages("l");
//		Assert.assertFalse(result3);
//
//	}

//	@Test
//	public void testSendMsg() {
//		ExpectedException thrown= ExpectedException.none();
//		thrown.expect(JMSException.class);
//	    server.sendMsg("hahaha", "aa");
//	}

	@Test
	public void testCheckTime() throws Exception{
		Method getFindUser = server.getClass().getDeclaredMethod("findUser",String.class);
		getFindUser.setAccessible(true);
		User user = (User)getFindUser.invoke(server, "liu");

		Method testCheckTime = server.getClass().getDeclaredMethod("checkTime",User.class);
		testCheckTime.setAccessible(true);
		//��ݵ���checkTime�������������������жϴ�ʱuser������һ�����ڵģ����Բ���ʱ���ò���user���󲻴��ڵ����
		//�������Ϊ0�������
		user.setLoginRequsetTime(0);
		Boolean result1 = (Boolean) testCheckTime.invoke(server, user);
		Assert.assertTrue(result1);
		//����������0���Ҿ����ϴ�����ʱ��С��1s������µ��������
		//<1>�������С��5��
		user.setLoginDate(System.currentTimeMillis());
		user.setLoginRequsetTime(3);
		Boolean result2 = (Boolean) testCheckTime.invoke(server, user);
		Assert.assertTrue(result2);
		//<2>����������5��
		user.setLoginDate(System.currentTimeMillis());
		user.setLoginRequsetTime(6);
		Boolean result3 = (Boolean) testCheckTime.invoke(server, user);
		Assert.assertFalse(result3);
		//�������Ϊ0��������ʱ��������1s
		user.setLoginDate(System.currentTimeMillis()-2000);
		user.setLoginRequsetTime(6);
		Boolean result4 = (Boolean) testCheckTime.invoke(server, user);
		Assert.assertTrue(result4);
	}

	@Test
	public void testLogin() throws Exception{
		//�����½�����
		int result1 = server.login("liu", "123");
		Assert.assertEquals(result1, 200);
		//���벻��ȷ�����
		int result2 = server.login("liu", "0");
		Assert.assertEquals(result2, 201);
		//ÿ������������5��
		Method getFindUser = server.getClass().getDeclaredMethod("findUser",String.class);
		getFindUser.setAccessible(true);
		User user = (User)getFindUser.invoke(server, "liu");
		user.setLoginDate( System.currentTimeMillis());
		user.setLoginRequsetTime(6);
		int result3 = server.login("liu", "123");
		Assert.assertEquals(result3, 203);
		//�û������
		int result4 = server.login("l", "123");
		Assert.assertEquals(result4, 202);

	}

}
