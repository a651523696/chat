package cn.edu.hdu.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.hdu.chat.model.User;
import cn.edu.hdu.chat.properties.CredentialsMatcherProperties;
import cn.edu.hdu.chat.repository.UserRepository;

@Controller	
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CredentialsMatcherProperties properties;
	@RequestMapping("hello")
	
	public String index(){
		System.out.println(properties.getHashAlgorithmName());
		return "hello";
	}
	
	
	@RequestMapping(value="user",produces="application/json;charset=utf-8")
	public @ResponseBody User getUser(){
		return userRepository.findOne(1l);
	}
	
	@RequestMapping(value="login")
	public String login(){
		System.out.println("login");
		return "login";
	}
	@SubscribeMapping("/connect")
	public String connect(){
		return "ss";
	}
}
