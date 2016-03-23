package Client;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.*;

import org.apache.activemq.ActiveMQConnectionFactory;

import Server.Server;
import Topic.MySubscriber;

public class Client extends JFrame{
	public String staticUsername;
	
	//登陆JFrame
	public JFrame loginFrame;
	JLabel username;
	JLabel password;
	JTextField usernameInput;
	JPasswordField passwordInput;
	JButton login;
	JButton register;
	
	
	//注册时用到的组件
	JLabel regUsername;
	JLabel regPassword;
	JTextField regUsernameInput;
	JPasswordField regPasswordInput;
	JButton regBtn;
	
	//显示状态和结果的组件
	JLabel currentState;
	JLabel currentStateDisplay;
	JLabel feedback;
	JLabel feedbackDisplay;
	JLabel msgNumber;
	JLabel msgNumberDisplay;
	int msgNumberCount;
	int unmsgNumberCount;
	boolean status;
	JLabel loginSuccessful;
	JLabel loginSuccessfulDisplay;
	int loginSuccessfulCount;
	JLabel loginFail;
	JLabel loginFailDisplay;
	int loginFailCount;
//	public int meassage_num=0,unmsg_num=0;
	
	//显示消息和发送消息组件
	JLabel msgDisplayLabel;
	JTextArea msgDisplay;
	//JScrollPane msgDisplayScroll;
	JLabel msgSentLabel;
	JTextArea msgSent;
	JButton sentButton;
	//JScrollPane msgSentScroll;
	
	String name;
	Server server;
	
	public Client(){
		super();
		
		JFrame loginFrame = new JFrame();
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setTitle("登陆");
		loginFrame.setBounds(50, 50, 300, 200);
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new GridLayout(3, 1));
		
		username = new JLabel("用户名 :");
		password = new JLabel("密   码 :");
		usernameInput = new JTextField(10);
		passwordInput = new JPasswordField(10);
		login = new JButton("登陆");
		register = new JButton("注册");
		
		JPanel loginPanel1 = new JPanel();
		loginPanel1.add(username);
		loginPanel1.add(usernameInput);
		JPanel loginPanel2 = new JPanel();
		loginPanel2.add(password);
		loginPanel2.add(passwordInput);
		JPanel loginPanel3 = new JPanel();
		loginPanel3.add(login);
		loginPanel3.add(register);
		
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("before"+usernameInput.getText());
				Login(usernameInput.getText(), passwordInput.getText());
				staticUsername = usernameInput.getText();
		
			}
		});
		register.addActionListener(new ActionListener() {
			
			@Override
			/*JLabel regUsername;
			JLabel regPassword;
			JTextField regUsernameInput;
			JPasswordField regPasswordInput;
			JButton regBtn;*/
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				final JFrame tempFrame = new JFrame();
				tempFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				tempFrame.setTitle("注册");
				tempFrame.setBounds(50, 50, 300, 200);
				JPanel tempPanel = new JPanel();
				tempPanel.setLayout(new GridLayout(3, 1));
				
				regUsername = new JLabel("用户名 :");
				regPassword = new JLabel("密   码 :");
				regUsernameInput = new JTextField(10);
				regPasswordInput = new JPasswordField(10);
				regBtn = new JButton("注册");
				
				JPanel regPanel1 = new JPanel();
				regPanel1.add(regUsername);
				regPanel1.add(regUsernameInput);
				JPanel regPanel2 = new JPanel();
				regPanel2.add(regPassword);
				regPanel2.add(regPasswordInput);
				JPanel regPanel3 = new JPanel();
				regPanel3.add(regBtn);
				
				regBtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String strUsername = regUsernameInput.getText();//注册时获取的用户名
						String strPassword = regPasswordInput.getText();//注册时获取的密码
						tempFrame.setVisible(false);
					}
				});
				
				tempPanel.add(regPanel1);
				tempPanel.add(regPanel2);
				tempPanel.add(regPanel3);
				
				tempFrame.add(tempPanel);
				tempFrame.setVisible(true);
				
			}
		});
		
		loginPanel.add(loginPanel1);
		loginPanel.add(loginPanel2);
		loginPanel.add(loginPanel3);
		
		loginFrame.add(loginPanel);
		loginFrame.setVisible(true);
		
		
		
		
		
		
		
		
		
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("客户端");
		this.setBounds(10, 10, 600, 700);
		name="";
		status=true;
		server=Server.sharedServer();
		msgNumberCount = 0;
		unmsgNumberCount = 0;
		loginSuccessfulCount = 0;
		loginFailCount = 0;
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0,0,600,700);
		
		/*username = new JLabel();
		username.setText("用户名 :");
		password = new JLabel();
		password.setText("密   码 :");
		usernameInput = new JTextField(10);
		//usernameInput.setBounds(0, 0, 100, 50);
		passwordInput = new JPasswordField(10);
		//passwordInput.setBounds(0, 0, 100, 50);
		login = new JButton("登陆");
		register = new JButton("注册");
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(3, 1));
		
		JPanel subPanel1 = new JPanel();
		subPanel1.add(username);
		subPanel1.add(usernameInput);
		JPanel subPanel2 = new JPanel();
		subPanel2.add(password);
		subPanel2.add(passwordInput);
		JPanel subPanel3 = new JPanel();
		subPanel3.add(login);
		subPanel3.add(register);
		
		subPanel.add(subPanel1);
		subPanel.add(subPanel2);
		subPanel.add(subPanel3);
		
		subPanel.setBounds(0, 20, 300, 180);
		panel.add(subPanel);*/
		
		
		
		
		
		
		
		JPanel subRightPanel = new JPanel();
		subRightPanel.setLayout(new GridLayout(5,1));
		
		currentState = new JLabel("当前状态:");
		currentStateDisplay = new JLabel("未登录");
		
		msgNumber = new JLabel("已发送消息数目:");
		msgNumberDisplay = new JLabel("0");
		
		loginSuccessful = new JLabel("登陆成功次数:");
		loginSuccessfulDisplay = new JLabel("0");
		
		loginFail = new JLabel("登陆失败次数:");
		loginFailDisplay = new JLabel("0");
		
		feedback = new JLabel("反馈结果:");
		feedbackDisplay = new JLabel("空");
		
		JPanel subPanel4 = new JPanel();
		subPanel4.setLayout(new GridLayout(1,2));
		subPanel4.add(currentState);
		subPanel4.add(currentStateDisplay);
		JPanel subPanel6 = new JPanel();
		subPanel6.setLayout(new GridLayout(1,2));
		subPanel6.add(msgNumber);
		subPanel6.add(msgNumberDisplay);
		JPanel subPanel7 = new JPanel();
		subPanel7.setLayout(new GridLayout(1,2));
		subPanel7.add(loginSuccessful);
		subPanel7.add(loginSuccessfulDisplay);
		JPanel subPanel8 = new JPanel();
		subPanel8.setLayout(new GridLayout(1,2));
		subPanel8.add(loginFail);
		subPanel8.add(loginFailDisplay);
		JPanel subPanel5 = new JPanel();
		subPanel5.setLayout(new GridLayout(1,2));
		subPanel5.add(feedback);
		subPanel5.add(feedbackDisplay);
		
		subRightPanel.add(subPanel4);
		subRightPanel.add(subPanel6);
		subRightPanel.add(subPanel7);
		subRightPanel.add(subPanel8);
		subRightPanel.add(subPanel5);
		subRightPanel.setBounds(350, 10, 200, 200);
		panel.add(subRightPanel);
		
		msgDisplayLabel = new JLabel("消息显示框");
		msgDisplay = new JTextArea();
		//msgDisplayScroll = new JScrollPane();
		msgDisplay.setLineWrap(true);
		msgDisplayLabel.setBounds(20,230,100,20);
		msgDisplay.setBounds(20,250,500,100);
		msgDisplay.setBorder(BorderFactory.createLineBorder(Color.gray,2));
		//msgDisplayScroll.add(msgDisplay);
		//msgDisplayScroll.setBounds(20,250,500,100);
		//msgDisplayScroll.setEnabled(true);
		panel.add(msgDisplayLabel);
		panel.add(msgDisplay);
		//panel.add(msgDisplayScroll);
		
		msgSentLabel = new JLabel("消息发送框");
		msgSent = new JTextArea();
		sentButton = new JButton("发送");
		
		msgSent.setLineWrap(true);
		msgSentLabel.setBounds(20, 380, 100, 20);
		msgSent.setBounds(20,400,500,100);
		sentButton.setBounds(450,510,70,30);
		msgSent.setBorder(BorderFactory.createLineBorder(Color.gray,2));
		panel.add(msgSentLabel);
		panel.add(msgSent);
		panel.add(sentButton);
		/*login.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {			
				Login(usernameInput.getText(), passwordInput.getText());
		
			}
		});*/
		sentButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			    
				String str = msgSent.getText();
				msgNumberCount++;
				msgNumberDisplay.setText(String.valueOf(msgNumberCount));
				//msgDisplay.setText(str);   //显示到消息显示框
				status=false;
				sendMsg(str,"Ericsson",false);
			}
		});
	
		this.add(panel);
		this.setVisible(false);
	}

	public  void Login(String userName, String password){
    	
    	int state=server.login(userName, password);
		sendMsg(String.valueOf(state),name,true);
    }
    public  void sendMsg(String msgText,String toipcName,boolean isLogin){
    	
    	
        try {
    	ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

		Connection connection = factory.createConnection();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination dest = session.createTopic(toipcName);
		MessageProducer producer = session.createProducer(dest);

		
		TextMessage msg = session.createTextMessage();
		msg.setText(msgText);
		if(isLogin){
		producer.send(msg);}
		else{
			if(server.sendMessages(staticUsername)){
				producer.send(msg);
           	}else{
           		//////////relogin
           		this.setVisible(false);
           		loginFrame.setVisible(true);
			}
		}
        System.out.println(msg.getText());

		
		//producer.close();
		//session.close();
		//connection.close();

			} catch (JMSException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

    
    }
   
    class Listen extends Thread{
    	 String topicName;
    	 boolean isLogin;
    	 
    	 public Listen(String _topicName,boolean _isLogin){
    		 topicName=_topicName;
    		 isLogin=_isLogin;
    	 }
    	public  void ListenMsg()throws JMSException {
    		
  		  ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
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
  					System.out.println(isLogin+"  "+topicName+ " "+ status+txtMsg.getText());
  					if(!isLogin){
  						if(status){ 
  							if(feedbackDisplay.getText().equals("200")){
  							msgDisplay.setText(txtMsg.getText());
  							
  							}
  						}
  						
  					}else{
  						
  						feedbackDisplay.setText(txtMsg.getText());
  						if(txtMsg.getText().equals("200")){
  							
  							////////alredy login
  							Client.this.setVisible(true);
  							loginFrame.setVisible(false);
  							
  							currentStateDisplay.setText("已登录");
  							loginSuccessfulCount++;
  							loginSuccessfulDisplay.setText(String.valueOf(loginSuccessfulCount));
  						}else{
  							loginFailCount++;
  							loginFailDisplay.setText(String.valueOf(loginFailCount));
  						}
  					}
  						
  					} catch (JMSException e) {
  						// TODO Auto-generated catch block
  						e.printStackTrace();
  					}
  				status=true;
  			}
  		    });
  		    
          // if(close){
  		//	consumer.setMessageListener(null);
  		//	consumer.close();
  		//	session.close();
  		//	connection.close();		
          // }
  			} catch (JMSException e1) {
  				e1.printStackTrace();
  			}
   }

    	public void run(){

    		try {
				ListenMsg();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }
	
	public static void main(String[] args) throws Exception {
		
		long ClientCount=MySubscriber.getConsumerCount();
		Client client = new Client();
		Client.Listen mainListen=client.new Listen("Ericsson",false);
		mainListen.start();
		client.name=String.valueOf(ClientCount);
		Listen loginListen = client.new Listen(client.name,true);
		//Listen loginListen = client.new Listen("sb",true);
		
	    loginListen.start();
		
	}

}
