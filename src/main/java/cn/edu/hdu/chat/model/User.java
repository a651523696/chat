package cn.edu.hdu.chat.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
		@Id
		@GeneratedValue
		private Long id;
		
		private String username;
		public User(){}
		public User(Long id, String username, String password) {
			super();
			this.id = id;
			this.username = username;
			this.password = password;
		}

		private String password;
		private Date registTime;
		public Date getRegistTime() {
			return registTime;
		}
		public void setRegistTime(Date registTime) {
			this.registTime = registTime;
		}
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
}
