package com.gdsp.platform.common.helper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.ResourcePropertySource;

public class CustomerApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static Logger logger = LoggerFactory.getLogger(CustomerApplicationContextInitializer.class);
	
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		ResourcePropertySource propertySource = null;
		try {
			propertySource = new ResourcePropertySource("classpath:application.properties");
		} catch (IOException e) {
			logger.error("properties is not exists");
		}
		applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);
		
	}

}
