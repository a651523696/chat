package cn.edu.hdu.chat.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;

import cn.edu.hdu.chat.properties.CredentialsMatcherProperties;
import cn.edu.hdu.chat.shiro.realm.UserRealm;

@Configuration
@Component
@EnableConfigurationProperties(CredentialsMatcherProperties.class)
public class ShiroFilterConfig {
	@Autowired
	private CredentialsMatcherProperties matcherProperties;
	@Bean(name="lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifeCycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
		
	}
	//spring-boot中注册DelegatingFilterProxy
	@Bean
	public FilterRegistrationBean registFilter(){
		 FilterRegistrationBean registration = new FilterRegistrationBean();
		    registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
		    registration.addUrlPatterns("/*");
		    registration.addInitParameter("targetFilterLifecycle", "true");
		    registration.setEnabled(true);
		    return registration;
	}
	

	//安全管理器
	@Bean(name="securityManager")
	public SecurityManager getSecurityManager(EhCacheManager cacheManager,UserRealm realm){
			DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
			securityManager.setCacheManager(cacheManager);
			securityManager.setRealm(realm);
			return securityManager;
	}
	//缓存管理器
	@Bean
	public EhCacheManager getEhcacheManager(){
		EhCacheManager cacheManager = new EhCacheManager();
		//设置ehcache缓存的配置文件的位置
		cacheManager.setCacheManagerConfigFile("classpath:shiro-ehcache.xml");
		return  new EhCacheManager();
	}
	//开启spring aop
	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator d = new DefaultAdvisorAutoProxyCreator();
		d.setProxyTargetClass(true);
		return d;
	}
	//开启shiro注解的使用
	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthoirzationAdvisor(SecurityManager manager){
		AuthorizationAttributeSourceAdvisor aa = new AuthorizationAttributeSourceAdvisor();
		aa.setSecurityManager(manager);
		return aa;
	}
	 @Bean(name = "shiroFilter")
	    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
	        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
	        // 必须设置 SecurityManager  
	        shiroFilterFactoryBean.setSecurityManager(securityManager);
	        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
	        shiroFilterFactoryBean.setLoginUrl("/loginForward");
//	        shiroFilterFactoryBean.setUnauthorizedUrl("/login");
	        // 登录成功后要跳转的连接
//	        shiroFilterFactoryBean.setSuccessUrl("/user");
//	        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
	        
	        Map<String,String> definition = new HashMap<String,String>();
	        definition.put("/chatForward", "authc");
	        definition.put("/login", "anon");
	        shiroFilterFactoryBean.setFilterChainDefinitionMap(definition);
	        return shiroFilterFactoryBean;
	    }
	/* @Bean
	 public SimpleMappingExceptionResolver getExceptionResolver(){
		SimpleMappingExceptionResolver resolver =  new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.put("org.apache.shiro.authz.AuthorizException", "login");
		resolver.setExceptionMappings(mappings);
		return resolver;
	 }*/
	 @Bean
	 public FormAuthenticationFilter getFormFilter(){
		 FormAuthenticationFilter filter = new FormAuthenticationFilter();
		return filter;
	 }
	 @Bean
	 public UserRealm getUserRealm(){
		 UserRealm realm = new UserRealm();
		 HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		 System.out.println(this.matcherProperties);
//		 System.out.println("--------------------------"+this.matcherProperties.getHashIterations());
//		 System.out.println(this.matcherProperties.getHashAlgorithmName());
////		 matcher.setHashAlgorithmName("md5");
////		 matcher.setHashIterations(5);
		 realm.setCredentialsMatcher(matcher);
		 return realm;
	 }
}
