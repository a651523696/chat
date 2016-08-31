package cn.edu.hdu.chat.config;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;

import cn.edu.hdu.chat.websocket.BrokerListener;

@Configuration
public class BeanConfig {
	@Bean
	public ApplicationListener<BrokerAvailabilityEvent> getBrokerListener(){
		return new BrokerListener();
	}

}
