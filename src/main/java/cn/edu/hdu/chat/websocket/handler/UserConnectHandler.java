package cn.edu.hdu.chat.websocket.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
/**
 * 原本打算自定义该类来实现afterConnectionEstablished之后保存登录用户，目前用ApplicationListener来实现
 * @author hasee
 *
 */
public class UserConnectHandler  implements WebSocketHandler{

	
	 
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.print("my handler");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
