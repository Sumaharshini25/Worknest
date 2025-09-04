package com.worknest.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;



/**
 * HibernateConfig - Java based configuration for Hibernate SessionFactory and Transaction Manager.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.worknest")
public class HibernateConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/wiproproject?useSSL=false&serverTimezone=UTC");
        ds.setUsername("root");
        ds.setPassword("root");
        return ds;
    }

    private Properties hibernateProperties() {
        Properties p = new Properties();
        p.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        p.put("hibernate.show_sql", "true");
        p.put("hibernate.format_sql", "true");
        p.put("hibernate.hbm2ddl.auto", "update");
        return p;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
        lsfb.setDataSource(dataSource());
        lsfb.setPackagesToScan("com.worknest.entity"); // scan entities
        lsfb.setHibernateProperties(hibernateProperties());
        return lsfb;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager tm = new HibernateTransactionManager();
        tm.setSessionFactory(sessionFactory);
        return tm;
    }
}
