package cn.edu.hdu.chat.model;

import java.util.Date;

public class ChatMessage {
	private String to;
	private String content;
	private User from;
	
	private Date sendTime;
	public ChatMessage() {
		this.sendTime = new Date();
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
