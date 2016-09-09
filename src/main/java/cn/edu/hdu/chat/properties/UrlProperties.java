package cn.edu.hdu.chat.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="chat.url")
public class UrlProperties {
	private String chatForward;
	
	private String loginForward;

	public String getChatForward() {
		return chatForward;
	}

	public void setChatForward(String chatForward) {
		this.chatForward = chatForward;
	}

	public String getLoginForward() {
		return loginForward;
	}

	public void setLoginForward(String loginForward) {
		this.loginForward = loginForward;
	}
}
