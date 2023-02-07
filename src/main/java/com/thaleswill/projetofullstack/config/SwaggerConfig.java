package com.thaleswill.projetofullstack.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.thaleswill.projetofullstack.resources"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("API de Sistemas de Vendas Simples",
				"Esta API é utilizada para instrução de alunos do Minicurso de Java e Springboot",
				"Versão 1.0",
				"https://github.com/thaleswillreis/projeto-backend-sistema-vendas",
				new Contact("Thales Will", "https://github.com/thaleswillreis", "thaleswillreis@gmail.com"),
				"Permitido uso para estudantes", "https://github.com/thaleswillreis/projeto-backend-sistema-vendas", Collections.emptyList());
	}

}