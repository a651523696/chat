package cn.edu.hdu.chat.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="shiro.credentialsMatcher")
public class CredentialsMatcherProperties {
	
	private String hashAlgorithmName;
	
	private Integer hashIterations;

	public String getHashAlgorithmName() {
		return hashAlgorithmName;
	}

	public void setHashAlgorithmName(String hashAlgorithmName) {
		this.hashAlgorithmName = hashAlgorithmName;
	}

	public Integer getHashIterations() {
		return hashIterations;
	}

	public void setHashIterations(Integer hashIterations) {
		this.hashIterations = hashIterations;
	}
	
	
}
