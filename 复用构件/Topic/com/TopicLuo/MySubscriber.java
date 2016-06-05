package Topic;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.TopicViewMBean;

import javax.jms.*;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

 


public class MySubscriber extends JFrame{

	
	
	
	    public static long getConsumerCount() throws Exception{
	    	
            JMXServiceURL url;
			url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
			JMXConnector jmxc = JMXConnectorFactory.connect(url);  
	    	MBeanServerConnection conn = jmxc.getMBeanServerConnection();  
	    	ObjectName activeMQ = new ObjectName("org.apache.activemq:BrokerName=localhost,Type=Broker");  
	    	ObjectName mbeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName=localhost");
	    	BrokerViewMBean mbean = MBeanServerInvocationHandler.newProxyInstance(conn, mbeanName, BrokerViewMBean.class, true);
            for (ObjectName topicName : mbean.getTopics()) {
            	
            TopicViewMBean topicMbean = (TopicViewMBean) MBeanServerInvocationHandler.newProxyInstance(conn, topicName, TopicViewMBean.class,true);
            	
	            if(topicMbean.getName().equals("Ericsson")){
	            	
	            	return topicMbean.getConsumerCount();
	            }
	       
          }
	    	return 0;
	    }
	   
	
	 
}

