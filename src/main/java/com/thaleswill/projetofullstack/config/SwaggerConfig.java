package com.thaleswill.projetofullstack.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private final ResponseMessage msg201ok = customMessage();
	private final ResponseMessage msg204put = simpleMessage(204, "Atualização ok");
	private final ResponseMessage msg204del = simpleMessage(204, "Deleção ok");
	private final ResponseMessage erro403 = simpleMessage(403, "Não autorizado");
	private final ResponseMessage erro404 = simpleMessage(404, "Não encontrado");
	private final ResponseMessage erro422 = simpleMessage(422, "Erro de validação");
	private final ResponseMessage erro500 = simpleMessage(500, "Erro inesperado");

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(erro403, erro404, erro500))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(msg201ok, erro403, erro422, erro500))
				.globalResponseMessage(RequestMethod.PUT, Arrays.asList(msg204put, erro403, erro404, erro422, erro500))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(msg204del, erro403, erro404, erro500))
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
				new Contact(
						"Thales Will", 
						"https://github.com/thaleswillreis", 
						"thaleswillreis@gmail.com"),
				"Permitido uso para estudantes", 
				"https://github.com/thaleswillreis/projeto-backend-sistema-vendas",
				Collections.emptyList());
	}

	private ResponseMessage simpleMessage(int code, String msg) {
		return new ResponseMessageBuilder().code(code).message(msg).build();
	}

	private ResponseMessage customMessage() {
		
		Map<String, Header> map = new HashMap<>();
		map.put("location", new Header("location", "URI do novo recurso", new ModelRef("string")));
		
		return new ResponseMessageBuilder()
				.code(201)
				.message("Recurso criado")
				.headersWithDescription(map)
				.build();
	}

	
}