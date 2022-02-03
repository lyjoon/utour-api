package com.utour;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 */
@EnableWebMvc
@EnableConfigurationProperties
@SpringBootApplication
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
