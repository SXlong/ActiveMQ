package com.lancer.activemq.mqtt2;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 发送消息(对应配置文件mqttProducer1.xml)
 * 
 * @author Lancer
 *
 */
@Controller  
@RequestMapping("/mqtt")  
public class PushControllerTwo {

	@Resource  
    private MqttPahoMessageHandler mqtt;  
	
    @RequestMapping(value="/send")  
    public void sendMessage(){  
        Message<String> message = 
        		MessageBuilder.withPayload("=="+new Date().getTime()+"==").setHeader(MqttHeaders.TOPIC, "robot_server").build();  
        mqtt.handleMessage(message);  
        System.out.println(message.toString());
        System.out.println("发送成功");  
    } 
    

}
