package Server;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.HaroldLIU.PerformanceManager;

import Server.ServerRec.Listen;
import extent.SaveMsgtoFile;
import reuse.cm.ReadJson;

public class ServerSave {

	static public String port;
	static public Server serverLogin;
	public static String msgPath = ReadJson.GetConfig("ServerMsgPath", "sets.txt");
	public String fileName = new SimpleDateFormat("yyyy_MM_dd").format(Calendar.getInstance().getTime());

	PerformanceManager performanceManager = new PerformanceManager(ReadJson.GetConfig("path", "sets.txt"),
			ReadJson.GetConfig("zipPath", "sets.txt"), 60 * 1000);

	public ServerSave(){
		long date = Calendar.getInstance().getTimeInMillis();
		performanceManager.setBegin(date);
		performanceManager.setZipSpaceTime(Long.parseLong(ReadJson.GetConfig("zipSpace", "sets.txt")));
		performanceManager.setZipFiledTime(Long.parseLong(ReadJson.GetConfig("zipFiled", "sets.txt")));
		performanceManager.start();

	}
	class Listen extends Thread {

		String topicName;
		ActiveMQConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Destination topic = null;
		MessageConsumer consumer = null;
		public Listen(String _topicName) {
			topicName = _topicName;
		}

		public void init() throws JMSException{
			factory = new ActiveMQConnectionFactory(port);
			
			connection = factory.createConnection();
			connection.setClientID("save");                
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Topic topic = session.createTopic(topicName);
			consumer = session.createDurableSubscriber(topic, "save"); // ³Ö¾Ã¶©ÔÄ


		}
		public void ListenMsg() throws JMSException {

			if(factory==null){
				init();
			}
			
			try {
				
				consumer.setMessageListener(new MessageListener() {
					public void onMessage(Message msg) {

						TextMessage txtMsg = (TextMessage) msg;

						try {
							String currentTime =  new SimpleDateFormat("yyyy_MM_dd").format(Calendar.getInstance().getTime());
							
							File f=new File(msgPath+"\\data");
							if(!f.exists()){
								f.mkdirs();
							}
								if(Server.sharedServer().level == 1)
									SaveMsgtoFile.SavetoFile(msgPath+"\\data\\", "ServerNormalLog" + fileName + ".txt",
											"ValidTime:\t"+performanceManager.successTime+"\tInvalidTime:\t"+performanceManager.failTime+"\t"+currentTime);
								else if (Server.sharedServer().level == 2)
                                    SaveMsgtoFile.SavetoFile(msgPath+"\\data\\", "ServerDebugLog" + fileName + ".txt",
										"ValidTime:\t"+performanceManager.successTime+"\tInvalidTime:\t"+performanceManager.failTime+"\t"+txtMsg.getText()+"\t"+currentTime);
								else
									SaveMsgtoFile.SavetoFile(msgPath+"\\data\\", "ServerLightLog" + fileName + ".txt",
											currentTime);
							
								
					
							
						} catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
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
			try {
				ListenMsg();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     performanceManager.zip(msgPath+"\\data\\", "ServerLog" + fileName + ".zip", msgPath);
		       
         }

	public void sendMsg(String msgText, String toipcName) {
		try {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(port);

			Connection connection = factory.createConnection();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination dest = session.createTopic(toipcName);
			MessageProducer producer = session.createProducer(dest);

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
		ServerSave serverSave=new ServerSave();
		
		
		Listen receivedMsg = serverSave.new Listen("ServerMsg");
		receivedMsg.start();
		System.out.println("--------Server Recevie Start------");
	}
}
