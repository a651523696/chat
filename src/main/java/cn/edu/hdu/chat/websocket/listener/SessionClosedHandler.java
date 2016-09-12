package cn.edu.hdu.chat.websocket.listener;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.alibaba.fastjson.JSON;

import cn.edu.hdu.chat.model.User;
import cn.edu.hdu.chat.util.ChatUtils;

public class SessionClosedHandler implements  ApplicationListener<SessionDisconnectEvent> {
	@Autowired
	private SimpMessagingTemplate template;
	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		//获取当前用户
		Principal principal = event.getUser();
		User user = JSON.parseObject(principal.toString(),User.class);
		
		//从用户在线列表中删除
		ChatUtils.delete(user);
		
		
		//并通知客户端
		template.convertAndSend("/topic/useroffline", user.getId());
	}

}
