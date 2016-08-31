package cn.edu.hdu.chat.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

import cn.edu.hdu.chat.util.PropertiesUtil;

//@Configuration
//以下两个注解表示两种配置jpa的方式,一种是从外部导入xml，一种是java config的形式，当然在spring-boot中jpa都是自动配置的，如果需要自定义一些dataSource等，需要单独配置
// @ImportResource("classpath:spring-jpa.xml")
//@EnableJpaRepositories(basePackages = { "cn.edu.hdu.chat.repository" })
public class JpaConfig {
	@Bean(name = "entityManagerFactory")
	public EntityManagerFactory getEntityManagerFactoryBean(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		// 设置数据源
		factory.setDataSource(dataSource);
		// 指定Jpa持久化实现厂商类,这里以Hibernate为例
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		// 实体类扫描
		factory.setPackagesToScan(new String[] { "cn.edu.hdu.chat.model" });
		Properties p = new Properties();
		p.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		p.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
		p.put("hibernate.show_sql", true);
		p.put("hibernate.format_sql", true);
		p.put("hibernate.hbm2ddl.auto", "update");
		factory.setJpaProperties(p);
		// 设置了Properties之后，需要调用该方法才能完成属性配置
		factory.afterPropertiesSet();
		// 利用factory来创建bean对象
		return factory.getObject();
	}

	@Bean(name = "transactionManager")
	public JpaTransactionManager getTransactionManager(EntityManagerFactory emf) {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(emf);
		return manager;
	}

	@Bean
	public DataSource getDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		PropertiesUtil.loadProperties("db.properties");
		dataSource.setDriverClassName(PropertiesUtil.getValue("driverClass"));
		dataSource.setUrl(PropertiesUtil.getValue("url"));
		dataSource.setUsername(PropertiesUtil.getValue("username"));
		dataSource.setPassword(PropertiesUtil.getValue("password"));
		return dataSource;
	}
}
