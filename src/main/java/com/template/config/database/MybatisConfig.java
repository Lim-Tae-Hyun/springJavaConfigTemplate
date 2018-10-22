package com.template.config.database;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.template.config.interceptor.MyBatisLogInterceptor;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @description : mybatis framework configuration class
 * @author : limth
 */
/* @Lazy : delay loading, loading when used in practice */
/* @Configuration : used on classes which define beans */
/* @EnableTransactionManagement : be used to transact database status, same as <tx:annotation-driven> in xml configuration */
/* @MapperScan : for scanning package location which defines sql sentences */
@Lazy
@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
@MapperScan(basePackages = {"com.template.api.mapper"}
            , sqlSessionFactoryRef = "sqlSession")
public class MybatisConfig {

    /* @Qualifier : when same type Bean is existed, specific Bean can be select */
    @Autowired
    @Qualifier("datasourcePostgreSQL")
    private DataSource datasourcePostgreSQL;

    @Autowired
    private Environment mybatisProp;

    /* sql session factory */
    @Bean(name = "sqlSession")
    public SqlSessionFactoryBean sqlSessionFactory(DataSource datasourcePostgreSQL, ApplicationContext applicationContext) throws Exception  {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(datasourcePostgreSQL);
        sqlSessionFactoryBean.setTypeAliasesPackage(mybatisProp.getProperty("template.typeAliasesPackage"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(mybatisProp.getProperty("template.mapperLocation")));
        sqlSessionFactoryBean.setPlugins(new Interceptor[] { new MyBatisLogInterceptor() });
        return sqlSessionFactoryBean;
    }

    /* transaction manager */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource datasourcePostgreSQL) throws SQLException {
        return new DataSourceTransactionManager(datasourcePostgreSQL);
    }
}
