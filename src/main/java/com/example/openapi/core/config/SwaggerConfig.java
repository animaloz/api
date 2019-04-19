package com.example.openapi.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Value("${t3.swagger.title:未设置配置项t3.swagger.title}")
	private String title;

	@Value("${t3.swagger.desc:未设置配置项t3.swagger.desc}")
	private String desc;

	@Value("${t3.swagger.version:未设置配置项t3.swagger.version}")
	private String version;

	@Value("${t3.swagger.company:未设置配置项t3.swagger.company}")
	private String company;

	@Value("${t3.swagger.lisense:未设置配置项t3.swagger.lisense}")
	private String lisense;

	@Value("${t3.swagger.contact:未设置配置项t3.swagger.contact}")
	private String contact;

	@Value("${t3.swagger.website:未设置配置项t3.swagger.website}")
	private String website;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo(title, desc, version, lisense, contact, company, website);
		return apiInfo;
	}
}
