package com.nick.hw.kafkaflink.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Add one more multiple DB instance for TiDB
 */
@Configuration
@EnableJpaRepositories(value = "com.nick.hw.kafkaflink.tidb_repository",
        entityManagerFactoryRef = "secondaryEntityManager",
        transactionManagerRef = "secondaryTransactionManager")
public class TiDBConfiguraiton {

    @Bean
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource initSecondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("secondaryEntityManager")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManager() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(initSecondaryDataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan(new String[] {"com.nick.hw.kafkaflink.tidb_domain"});
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager secondaryTransactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                secondaryEntityManager().getObject());
        return transactionManager;
    }
}
