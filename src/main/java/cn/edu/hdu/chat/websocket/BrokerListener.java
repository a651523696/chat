package cn.edu.hdu.chat.websocket;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;

public class BrokerListener implements ApplicationListener<BrokerAvailabilityEvent>{

	@Override
	public void onApplicationEvent(BrokerAvailabilityEvent event) {
		System.out.println("*****broker status:"+event.isBrokerAvailable());
	}

}
