package cn.edu.hdu.chat.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import cn.edu.hdu.chat.model.User;
import cn.edu.hdu.chat.util.UserUtil;
@Controller	
public class SubscribeController {
	@SubscribeMapping("/connect")
	public String connect(){
		return "ss";
	}
	@SubscribeMapping("/userlist")
	public List<User> getUserList(){
		Map<String,User> userMap = UserUtil.getUserMap(); 
		Set<Map.Entry< String, User>> entrySet = userMap.entrySet();
		List<User> userlist = entrySet.stream().collect(() -> new ArrayList<User>(),(list,item)-> list.add(item.getValue()),(list1,list2)->list1.addAll(list2));
		return userlist;
	}
}
