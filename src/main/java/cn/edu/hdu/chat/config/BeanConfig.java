package cn.edu.hdu.chat.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.web.context.request.RequestAttributes;

import cn.edu.hdu.chat.controller.ErrorPageController;
import cn.edu.hdu.chat.websocket.listener.BrokerListener;
import cn.edu.hdu.chat.websocket.listener.SessionClosedHandler;
import cn.edu.hdu.chat.websocket.listener.SessionConnectHandler;

@Configuration
public  class BeanConfig {
	//消息代理的状态监听器
	@Bean
	public ApplicationListener<BrokerAvailabilityEvent> getBrokerListener(){
		return new BrokerListener();
	}
	//客户端连接监听器
	@Bean
	public SessionConnectHandler getSessionConnectHandler(){
		return new SessionConnectHandler();
	}
	//客户端断开连接监听器
	@Bean
	public SessionClosedHandler getSessionClosedHandler(){
		return new SessionClosedHandler();
	}
	@Bean
	public ErrorPageController getErrorPageController(){
		return new ErrorPageController(new ErrorAttributes() {
			
			@Override
			public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Throwable getError(RequestAttributes requestAttributes) {
				// TODO Auto-generated method stub
				return null;
			}
		},new ErrorProperties());
	}
	
}
