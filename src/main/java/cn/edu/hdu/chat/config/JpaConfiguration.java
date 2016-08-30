package cn.edu.hdu.chat.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.alibaba.druid.pool.DruidDataSource;
@Configuration
@EnableJpaRepositories(basePackages={"cn.edu.hdu.chat.repository"})
public class JpaConfiguration {
	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean(DataSource dataSource){
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		//设置数据源
		factory.setDataSource(dataSource);
		// 指定Jpa持久化实现厂商类,这里以Hibernate为例 
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		//实体类扫描
		factory.setPackagesToScan(new String[]{"cn.edu.hdu.chat.model"});
		Properties p = new Properties();
		p.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		p.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
		p.put("hibernate.show_sql", true);
		p.put("hibernate.format_sql", true);
		p.put("hibernate.hbm2ddl.auto", "update");
		factory.setJpaProperties(p);
		return factory;
	}
	@Bean(name="transactionManager")
	public JpaTransactionManager getTransactionManager(LocalContainerEntityManagerFactoryBean factory){
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory((EntityManagerFactory)factory);
		return manager;
	}
	@Bean
	public DataSource getDataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/chat?useUnicode=true&characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}
}
