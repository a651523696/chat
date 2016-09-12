package cn.edu.hdu.chat.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import cn.edu.hdu.chat.model.ChatMessage;
import cn.edu.hdu.chat.model.User;
import cn.edu.hdu.chat.properties.ChatProperties;

/**
 * 内部维护了一个用户map，用来保存当前登录的用户 内部维护了一个历史聊天记录，默认保存20条历史记录
 * 
 * @author hasee
 *
 */
@EnableConfigurationProperties(ChatProperties.class)
@Component
public class ChatUtils {
	@Autowired
	private static ChatProperties chatProperties;
	private static Map<String, User> userMap = new HashMap<String, User>();
	private static List<ChatMessage> messageHistory = Collections.synchronizedList(new ArrayList<ChatMessage>());

	public static synchronized void save(User u) {
		String username = u.getUsername();
		userMap.put(username, u);
	}

	public static synchronized void delete(User u) {
		String username = u.getUsername();
		if (username != null) {
			userMap.remove(username);
		}
	}

	public static Map<String, User> getUserMap() {
		return userMap;
	}

	public static void storeMessage(ChatMessage message) {
//		System.out.println(chatProperties.getCacheSize());
		synchronized (messageHistory) {
			if (message != null) {
				if (messageHistory.size() > 20) {
					messageHistory.remove(0);
				}
				messageHistory.add((message));
			}
		}
	}

	public static List<ChatMessage> getMessageHistory() {
		return messageHistory;
	}
}
