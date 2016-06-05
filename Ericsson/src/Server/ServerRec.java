package Server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.HaroldLIU.PerformanceManager;

import Server.Server.Listen;
import extent.SaveMsgtoFile;
import reuse.cm.ReadJson;

public class ServerRec {
	static public String port;
	static public Server serverLogin;
	public static String msgPath = ReadJson.GetConfig("ServerMsgPath", "sets.txt");
	public String fileName = new SimpleDateFormat("yyyy_MM_dd").format(Calendar.getInstance().getTime());

	PerformanceManager performanceManager = new PerformanceManager(ReadJson.GetConfig("path", "sets.txt"),
			ReadJson.GetConfig("zipPath", "sets.txt"), 60 * 1000);

	public ServerRec(){
		long date = Calendar.getInstance().getTimeInMillis();
		performanceManager.setBegin(date);
		performanceManager.setZipSpaceTime(Long.parseLong(ReadJson.GetConfig("zipSpace", "sets.txt")));
		performanceManager.setZipFiledTime(Long.parseLong(ReadJson.GetConfig("zipFiled", "sets.txt")));
		performanceManager.start();

	}
	class Listen extends Thread {

		String topicName;

		public Listen(String _topicName) {
			topicName = _topicName;
		}

		public void ListenMsg() {

			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(port);
			Connection connection;
			try {
				connection = factory.createConnection();

				connection.start();

				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

				Destination topic = session.createTopic(topicName);
				MessageConsumer consumer = session.createConsumer(topic);

				consumer.setMessageListener(new MessageListener() {
					public void onMessage(Message msg) {

						TextMessage txtMsg = (TextMessage) msg;

						try {
							
						  sendMsg(txtMsg.getText(),"ServerMsg");
					      System.out.println(txtMsg.getText());
						} catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 

					}
				});
			} catch (JMSException e1) {
				e1.printStackTrace();
			}

		}

		public void run() {
			ListenMsg();
		     performanceManager.zip(msgPath+"\\data\\", "ServerLog" + fileName + ".zip", msgPath);
		       
         }

	public void sendMsg(String msgText, String toipcName) {
		try {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(port);

			Connection connection = factory.createConnection();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination dest = session.createTopic(toipcName);
			MessageProducer producer = session.createProducer(dest);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			TextMessage msg = session.createTextMessage();
			msg.setText(msgText);
			producer.send(msg);
			System.out.println(msg.getText());

		} catch (JMSException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	}
	
	public static void main(String[] args) throws Exception {

		port = "tcp://localhost:" + ReadJson.GetConfig("port", "sets.txt");
		ServerRec serverRec=new ServerRec();
		
		
		Listen receivedMsg = serverRec.new Listen("Ericsson");
		receivedMsg.start();
		System.out.println("--------Server Recevie Start------");
	}

}
