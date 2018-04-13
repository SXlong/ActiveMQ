package com.lancer.activemq.mqtt1;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * 模拟客户端1号
 * 
 * @author Lancer
 */
public class TestClient {

	 private static final String HOST = "tcp://127.0.0.1:1883"; 
	 private static final String TOPIC = "robot_server";	//从哪个topic接收信息
	 private static final String clientid = "android-client-1";  
	 private static final String userName = "admin";  
	 private static final String passWord = "password";  
	  

	
	public static void main(String[] args){
		try {
			MqttClient client = new MqttClient(HOST, clientid, new MemoryPersistence());
			
			// new mqttConnection 用来设置一些连接的属性
			MqttConnectOptions options = new MqttConnectOptions();   
			// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接 
			// 换而言之，设置为false时可以客户端可以接受离线消息
			options.setCleanSession(false);  
			// 设置连接的用户名和密码
			options.setUserName(userName);  
			options.setPassword(passWord.toCharArray());  
			// 设置超时时间  
			options.setConnectionTimeout(10);  
			// 设置会话心跳时间  
			options.setKeepAliveInterval(20);  
			// 设置回调类
			client.setCallback(new ClientPushCallback());  
			// 获取activeMQ上名为TOPIC的topic
			client.getTopic(TOPIC);  
			// 连接
			client.connect(options);
			//mqtt客户端订阅主题
            //在mqtt中用QoS来标识服务质量
            //QoS=0时，报文最多发送一次，有可能丢失
            //QoS=1时，报文至少发送一次，有可能重复
            //QoS=2时，报文只发送一次，并且确保消息只到达一次。
            int[] Qos  = {2};  
            String[] topic = {TOPIC};  
			client.subscribe(topic, Qos);	//可订阅多个主题
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
