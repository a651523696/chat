package cn.edu.hdu.chat;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import cn.edu.hdu.chat.properties.CredentialsMatcherProperties;
@EnableConfigurationProperties(CredentialsMatcherProperties.class)
@SpringBootApplication
public class Application {
	public static void main(String [] args){
		SpringApplication.run(new Object[]{Application.class}, args);
	}
}
