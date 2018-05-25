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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "com.aolangtech.nsignalweb.mappers.stock", sqlSessionTemplateRef = "stockSqlSessionTemplate")
public class StockDataSourceConfig {
	
    @Bean(name = "stockDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.stock")
    public DataSource stockDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "stockSqlSessionFactory")
    public SqlSessionFactory stockSqlSessionFactory(@Qualifier("stockDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/stock/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "stockTransactionManager")
    public DataSourceTransactionManager stockTransactionManager(@Qualifier("stockDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "stockSqlSessionTemplate")
    public SqlSessionTemplate stockSqlSessionTemplate(@Qualifier("stockSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}