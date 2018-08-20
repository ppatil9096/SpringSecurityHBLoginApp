package com.pravin.spring.secuity.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan("com.pravin.spring.*")
@EnableTransactionManagement
public class LoginAppDataConfig {
    @Autowired
    private Environment env;

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
	BasicDataSource dataSource = new BasicDataSource();
	dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
	dataSource.setUrl(env.getProperty("ds.url"));
	dataSource.setUsername(env.getProperty("ds.username"));
	dataSource.setPassword(env.getProperty("ds.password"));
	return dataSource;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {

	try {
	    Properties properties = new Properties();
	    // See: ds-hibernate-cfg.properties
	    properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
	    properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
	    // properties.put("current_session_context_class",
	    // env.getProperty("current_session_context_class"));

	    LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
	    // Package contain entity classes
	    factoryBean.setPackagesToScan(new String[] { "com.pravin.spring.secuity.entity" });
	    factoryBean.setDataSource(dataSource);
	    factoryBean.setHibernateProperties(properties);
	    factoryBean.afterPropertiesSet();

	    SessionFactory sf = factoryBean.getObject();
	    System.out.println("Get SessionFactory :: " + sf);
	    return sf;
	} catch (Exception e) {
	    System.out.println("Error getSessionFactory :: " + e);
	    e.printStackTrace();
	    throw e;
	}
    }

    // Hibernate Transaction Manager
    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
	HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
	return transactionManager;
    }
}
