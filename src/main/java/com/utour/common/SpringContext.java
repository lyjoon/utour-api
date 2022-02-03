package com.utour.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * spring-context (applicationContext) 리소스 관련 공통
 */
@Component
@RequiredArgsConstructor
public class SpringContext {

	private final ApplicationContext applicationContext;

	protected Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}

	protected <T> T getBean(String beanName, Class<T> clz) {
		return applicationContext.getBean(beanName, clz);
	}

	protected <T> T getBean(Class<T> clz) {
		return applicationContext.getBean(clz);
	}
}
