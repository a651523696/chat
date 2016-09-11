package cn.edu.hdu.chat.websocket.listener;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.alibaba.fastjson.JSON;

import cn.edu.hdu.chat.model.User;
import cn.edu.hdu.chat.util.UserUtil;

public class SessionClosedHandler implements  ApplicationListener<SessionDisconnectEvent> {
	@Autowired
	private SimpMessagingTemplate template;
	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		Principal principal = event.getUser();
		User user = JSON.parseObject(principal.toString(),User.class);
		
		UserUtil.delete(user);
		
		template.convertAndSend("/topic/useroffline", user.getId());
	}

}
