package com.lancer.activemq.mqtt2;



/**
 *	消费者(对应配置文件mqttConsumer1.xml)
 *
 *	@author Lancer
 */
public class ClientService{

	public void startCase(String message){  
        System.out.println("客户端收到了:"+message);  
    }  

}
