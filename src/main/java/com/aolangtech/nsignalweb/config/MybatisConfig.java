/**
 * 
 */
/**
 * @author Maple.S
 *
 */
package com.aolangtech.nsignalweb.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.aolangtech.nsignalweb.mappers")
public class MybatisConfig {
	
}