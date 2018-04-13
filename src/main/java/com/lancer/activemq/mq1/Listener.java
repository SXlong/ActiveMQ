package com.lancer.activemq.mq1;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息监听
 * 
 * @author Lancer
 *
 */
public class Listener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		/**
		 * 接收消息可使用 instanceof 判断类型进行处理 此处省略
		 */
		try {
			System.out.println("收到的消息："+((TextMessage)message).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
