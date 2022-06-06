package com.utour;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * boot-runner
 */
@EnableConfigurationProperties
@EnableAspectJAutoProxy
@EnableCaching
@SpringBootApplication
//@org.springframework.web.servlet.config.annotation.EnableWebMvc //(application.yml 에 설정된 jackson 관련 설정이 무시됨)
@EnableScheduling
@EnableBatchProcessing
public class Application extends SpringBootServletInitializer {

	/**
	 * spring-application run(type servlet)
	 * @param args
	 */
	public static void main(String args[]) {
		new SpringApplicationBuilder(Application.class)
				.web(WebApplicationType.SERVLET)
				.build()
				.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder
				.web(WebApplicationType.SERVLET)
				.sources(Application.class);
	}
}
