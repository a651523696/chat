package cn.edu.hdu.chat.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import cn.edu.hdu.chat.properties.CredentialsMatcherProperties;
import cn.edu.hdu.chat.shiro.realm.UserRealm;

@Configuration

public class ShiroFilterConfig {
	
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
	        shiroFilterFactoryBean.setLoginUrl("/login");
	        
//	        shiroFilterFactoryBean.setUnauthorizedUrl("/login");
	        // 登录成功后要跳转的连接
//	        shiroFilterFactoryBean.setSuccessUrl("/user");
//	        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
	        Map<String,Filter> filters = new HashMap<String,Filter>();
	        filters.put("authc",getFormFilter());
	        Map<String,String> definition = new HashMap<String,String>();
	        definition.put("/chatForward", "authc");
	        definition.put("/login", "anon");
	        shiroFilterFactoryBean.setFilterChainDefinitionMap(definition);
	        shiroFilterFactoryBean.setFilters(filters);
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
	 public PassThruAuthenticationFilter getFormFilter(){
		 PassThruAuthenticationFilter filter = new PassThruAuthenticationFilter();
		 return filter;
	 }
//	@Bean
//	public FormAuthenticationFilter getFormFilter(){
//		return new FormAuthenticationFilter();
//	}
	 @Bean
	 public UserRealm getUserRealm(CredentialsMatcherProperties matcherProperties){
		 UserRealm realm = new UserRealm();
		 HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		 matcher.setHashAlgorithmName(matcherProperties.getHashAlgorithmName());
		 matcher.setHashIterations(matcherProperties.getHashIterations());
		 realm.setCredentialsMatcher(matcher);
		 return realm;
	 }
}
