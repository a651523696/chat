package cn.edu.hdu.chat.websocket.listener;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.session.web.socket.events.SessionConnectEvent;

import com.alibaba.fastjson.JSON;

import cn.edu.hdu.chat.model.User;
import cn.edu.hdu.chat.util.ChatUtils;

public class SessionConnectHandler implements ApplicationListener<SessionConnectEvent>{
	@Autowired
	private SimpMessagingTemplate template;
	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
			Principal principal = event.getWebSocketSession().getPrincipal();
			User user = JSON.parseObject(principal.toString(),User.class);
			
			ChatUtils.save(user);
			template.convertAndSend("/topic/useronline", user);
		
	}

	

}
