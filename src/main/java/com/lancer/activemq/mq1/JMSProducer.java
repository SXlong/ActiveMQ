package com.lancer.activemq.mq1;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息生产者(队列)
 * 
 * @author Lancer
 *
 */
public class JMSProducer {

	private static final String USERNAME=ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
	private static final String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
	private static final String BROKEURL=ActiveMQConnection.DEFAULT_BROKER_URL; // 默认的连接地址
	private static final int SENDNUM=10; // 发送的消息数量
	
	public static void main(String[] args) {
		
		ConnectionFactory connectionFactory; // 连接工厂
		Connection connection = null; // 连接
		Session session; // 会话 接受或者发送消息的线程
		Destination destination; // 消息的目的地
		MessageProducer messageProducer; // 消息生产者
		
		// 实例化连接工厂
		connectionFactory=new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKEURL);
		
		try {
			connection=connectionFactory.createConnection(); // 通过连接工厂获取连接
			connection.start(); // 启动连接
			session=connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE); // 创建Session
			destination=session.createQueue("FirstQueue1"); // 创建消息队列
			messageProducer=session.createProducer(destination); // 创建消息生产者
			sendMessage(session, messageProducer); // 发送消息
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//关闭连接
			if(connection!=null){
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 发送消息
	 * @param session
	 * @param messageProducer
	 * @throws Exception
	 */
	public static void sendMessage(Session session,MessageProducer messageProducer)throws Exception{
		for(int i=0;i<JMSProducer.SENDNUM;i++){
			/**
			 * ActiveMQ支持多种消息类型，如：
			 * 1、文本消息
			 * TextMessage textMessage = session.createTextMessage("文本消息");
			 * 2、键值对消息
			 * MapMessage mapMessage = session.createMapMessage();  
			 * 3、流消息
			 * StreamMessage streamMessage = session.createStreamMessage(); 
			 * 4、字节消息
			 * String s = "BytesMessage字节消息"; 
			 * BytesMessage bytesMessage = session.createBytesMessage();
			 * bytesMessage.writeBytes(s.getBytes());
			 * 5、对象消息
			 * User user = new User(); User对象必须实现Serializable接口   
			 * ObjectMessage objectMessage = session.createObjectMessage();
			 * objectMessage.setObject(user);
			 */
			TextMessage message=session.createTextMessage("ActiveMQ 发送的消息"+i);
			System.out.println("发送消息："+"ActiveMQ 发送的消息"+i);
			messageProducer.send(message);
		}
	}
}
