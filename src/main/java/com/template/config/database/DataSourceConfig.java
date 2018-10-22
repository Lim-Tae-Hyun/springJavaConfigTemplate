package com.template.config.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * @discription : datasource configuration class for connectiong database
 * @author : limth
 */
/* @Configuration : used on class which defines bean */
@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment datasourceProp;

    /* connecting database */
    @Bean(name = "datasourcePostgreSQL")
    public DataSource datasource() {
        org.apache.commons.dbcp2.BasicDataSource dataSource = new org.apache.commons.dbcp2.BasicDataSource();

        dataSource.setDriverClassName(datasourceProp.getProperty("template.driverClassName"));
        dataSource.setUrl(datasourceProp.getProperty("template.url"));
        dataSource.setUsername(datasourceProp.getProperty("template.username"));
        dataSource.setPassword(datasourceProp.getProperty("template.password"));
        dataSource.setMaxWaitMillis(Long.valueOf(datasourceProp.getProperty("template.maxwait")));
        dataSource.setMaxTotal(Integer.valueOf(datasourceProp.getProperty("template.maxactive")));

        return dataSource;
    }
}
