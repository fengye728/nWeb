package com.aolangtech.nsignalweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.aolangtech.nsignalweb.config.ConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class NSignalWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(NSignalWebApplication.class, args);
	}
}
