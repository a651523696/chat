package cn.edu.hdu.chat.util;

import java.util.HashMap;
import java.util.Map;

import cn.edu.hdu.chat.model.User;

/**
 * 内部维护了一个map，用来保存当前登录的用户
 * @author hasee
 *
 */
public class UserUtil {
			private static Map<String,User> userMap = new HashMap<String,User>();
			public  static synchronized void save(User u){
				String username = u.getUsername();
				userMap.put(username, u);
			}
			public static synchronized void delete(User u){
				String username = u.getUsername();
				if(username != null){
					userMap.remove(username);
				}
			}
			public static Map<String,User> getUserMap(){
				return userMap;
			}
}
