package cn.edu.hdu.chat.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.hdu.chat.model.ChatMessage;
import cn.edu.hdu.chat.model.User;
import cn.edu.hdu.chat.properties.UrlProperties;
import cn.edu.hdu.chat.repository.UserRepository;
import cn.edu.hdu.chat.util.ChatUtils;

@Controller	
@EnableConfigurationProperties(UrlProperties.class)
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UrlProperties urlProperties;

	@RequestMapping("hello")
	public @ResponseBody String index(){
		
		return "hello";
	}
	
	
	@RequestMapping(value="user",produces="application/json;charset=utf-8")
	public @ResponseBody User getUser(){
		return userRepository.findOne(1l);
	}
	
	@RequestMapping(value="login")
	public void login(HttpServletRequest request,HttpServletResponse response ,User user) throws IOException{
		Subject subject = SecurityUtils.getSubject();
		if(request.getMethod().equals("GET")){
			WebUtils.issueRedirect(request,response,urlProperties.getLoginForward());
			return;
		}
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
		try{
			
			subject.login(token);
		}catch(AuthenticationException e){
			WebUtils.issueRedirect(request,response,urlProperties.getLoginForward());
			return;
		}
		
		
		WebUtils.redirectToSavedRequest(request, response,urlProperties.getChatForward());
	}
	
	@MessageMapping("/chat")
	public ChatMessage receiveMessage(ChatMessage message){
		System.out.println("from User:"+message.getFrom());
		System.out.println("sendTime:"+message.getSendTime());
		ChatUtils.storeMessage(message);
		return message;
	}
}
