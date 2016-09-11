package cn.edu.hdu.chat.config;

import org.jboss.logging.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.session.ExpiringSession;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
@Configuration
@EnableWebSocketMessageBroker

/** 
 *  extend AbstractSessionWebSocketMessageBrokerConfigurer
 *  instead  AbstractWebSocketMessageBrokerConfigurer to config
 *  spring session  
 * @author hasee
 *
 */
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<ExpiringSession>  {
	public static Logger logger = Logger.getLogger(WebSocketConfig.class);
	
	@Override
	public void configureStompEndpoints(StompEndpointRegistry registry) {
		// TODO Auto-generated method stub
		registry.addEndpoint("/chatServer");
		registry.addEndpoint("/chatServer").withSockJS();
	}


	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
	
		//使用第三方的消息代理，前提是第三方消息代理服务启动,例子中使用rabbitMq,不用做其他配置的原因是spring的
		//的	StompBrokerRelayMessageHandler默认配置了其连接属性
		registry.enableStompBrokerRelay("/topic","/queue");
		registry.setApplicationDestinationPrefixes("/app");
		//使用spring built-in simple broker    spring自带的消息代理
//		registry.enableSimpleBroker("/topic","/queue");
	}


	/*@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
		registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
			
			@Override
			public WebSocketHandler decorate(WebSocketHandler handler) {
				// TODO Auto-generated method stub
				return new WebSocketHandlerDecorator(new UserConnectHandler());
			}
		});
		super.configureWebSocketTransport(registration);
	}*/


	



	
	
}
