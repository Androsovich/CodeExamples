package com.epam.summer.lab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "com.epam.summer.lab.repositories")
@EnableTransactionManagement
public class OrmConfig {

    @Autowired
    private final Environment env;

    public OrmConfig(Environment env) {
        this.env = env;
    }

    @Bean
    DataSource dataSource() {
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        databaseBuilder.setType(EmbeddedDatabaseType.H2);
        databaseBuilder.addScript("static/scripts_sql/schema.sql");

        return databaseBuilder.build();
    }

    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.dialect", env.getProperty("db.hibernate.dialect"));
        hibernateProp.put("hibernate.format sql", env.getProperty("db.hibernate.format_sql"));
        hibernateProp.put("hibernate.use sql comments", env.getProperty("db.hibernate.use.sql.comments"));
        hibernateProp.put("hibernate.show_sql", env.getProperty("db.hibernate.show_sql"));
        hibernateProp.put("hibernate.max fetch_depth", env.getProperty("db.hibernate.max.fetch_depth"));
        hibernateProp.put("hibernate.jdbc.batch_size", env.getProperty("db.hibernate.jdbc.batch_size"));
        hibernateProp.put("hibernate.jdbc.fetch_size", env.getProperty("db.hibernate.jdbc.fetch_size"));
        return hibernateProp;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.H2);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.epam.summer.lab.entities");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}