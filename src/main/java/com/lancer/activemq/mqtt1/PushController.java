package com.lancer.activemq.mqtt1;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 广播消息测试接口类
 * 
 * @author Lancer
 */
@Controller
public class PushController{
	 
	private String host = "tcp://127.0.0.1:1883"; 
	private String clientid = "mqtt-server-1";  
	private String userName = "admin";  
	private String passWord = "password";  
	  
	/**
	 * 返回发送信息页面
	 */
	@RequestMapping(value="/publish",method = RequestMethod.GET)
	public String publish() {
	   return "publish";
	}
   
	/**
	 * 建立连接发送信息 发完关闭
	 */
	@RequestMapping(value="/redirect")
	public String redirect(HttpServletRequest request) throws MqttException {
		//获取前台传过来的两个参数
		String topic = request.getParameter("topic");
		String message = request.getParameter("message");
		//new mqttClient
		//MemoryPersistence设置clientid的保存形式，默认为以内存保存
		MqttClient mqttClient = new MqttClient(host, clientid, new MemoryPersistence());  
		//与activeMQ连接
		//new mqttConnection 用来设置一些连接的属性
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
	  	try {  
	  		// 设置回调类
	  		mqttClient.setCallback(new PushCallback());  
	  		// 连接
	  		mqttClient.connect(options);
	  		// 获取activeMQ上的topic
	  		MqttTopic mqttTopic = mqttClient.getTopic(topic);  
			//new mqttMessage
			MqttMessage mqttMessage = new MqttMessage();  
			//设置服务质量默认为1
			mqttMessage.setQos(2);  
			//设置是否在服务器中保存消息体 
			mqttMessage.setRetained(true);
			//设置消息的内容
			mqttMessage.setPayload(message.getBytes());  
			//发布
			MqttDeliveryToken token = mqttTopic.publish(mqttMessage);
			token.waitForCompletion();
			System.out.println("message is published completely! "  + token.isComplete());
			return "result";
	  	} catch (Exception e) {  
	  		e.printStackTrace();  
	  	}finally{
	  		//关闭资源
	  		mqttClient.disconnect();
	  		mqttClient.close();
	  	}
	  	return null;
	}

}
