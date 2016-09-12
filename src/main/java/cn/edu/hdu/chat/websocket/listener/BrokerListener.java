package cn.edu.hdu.chat.websocket.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;

public class BrokerListener implements ApplicationListener<BrokerAvailabilityEvent>{

	@Override
	public void onApplicationEvent(BrokerAvailabilityEvent event) {
		System.out.println("chat**broker status:"+event.isBrokerAvailable());
	}
	public void init(){
		System.out.println("broker listener init");
	}
}
