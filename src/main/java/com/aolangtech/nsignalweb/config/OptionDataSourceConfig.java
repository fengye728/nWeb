/**
 * 
 */
/**
 * @author Maple.S
 *
 */
package com.aolangtech.nsignalweb.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "com.aolangtech.nsignalweb.mappers.option", sqlSessionTemplateRef = "optionSqlSessionTemplate")
public class OptionDataSourceConfig {
	
    @Bean(name = "optionDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.option")
    @Primary
    public DataSource optionDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "optionSqlSessionFactory")
    @Primary
    public SqlSessionFactory optionSqlSessionFactory(@Qualifier("optionDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/option/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "optionTransactionManager")
    @Primary
    public DataSourceTransactionManager optionTransactionManager(@Qualifier("optionDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "optionSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate optionSqlSessionTemplate(@Qualifier("optionSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}