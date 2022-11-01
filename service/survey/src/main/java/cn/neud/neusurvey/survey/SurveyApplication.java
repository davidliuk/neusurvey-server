/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://survey.neud.cn
 *
 * 版权所有，侵权必究！
 */

package cn.neud.neusurvey.survey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


/**
 * neusurvey
 *
 * @author David l729641074@163.com
 */
@SpringBootApplication
@ComponentScan({"springfox.documentation.schema"})
public class SurveyApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SurveyApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SurveyApplication.class);
	}
}