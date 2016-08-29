package cn.edu.hdu.chat.config;


import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter	{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
	}
		@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("chatForward").setViewName("index");
	}
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
	}
	
	
}
