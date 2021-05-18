package com.nick.hw.kafkaflink.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * The primary DB for persistence is postgreSQL
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.nick.hw.kafkaflink.repository",
        entityManagerFactoryRef = "primaryEntityManager",
        transactionManagerRef = "primaryTransactionManager")
public class PostgreDBConfiguraiton {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource initPrimaryDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }

    @Primary
    @Bean("primaryEntityManager")
    public LocalContainerEntityManagerFactoryBean primaryEntityManager() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(initPrimaryDataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan(new String[] {"com.nick.hw.kafkaflink.domain"});
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        return localContainerEntityManagerFactoryBean;
    }


    @Primary
    @Bean
    public PlatformTransactionManager primaryTransactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                primaryEntityManager().getObject());
        return transactionManager;
    }
}
