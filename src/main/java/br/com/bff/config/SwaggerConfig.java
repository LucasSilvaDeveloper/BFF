package br.com.bff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
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
				.apis(RequestHandlerSelectors.basePackage("br.com.bff.controller"))
				.paths(PathSelectors.any())
				.build()
//				.tags(new Tag(Constantes.CANDIDATO, Constantes.CANDIDATO_DESCRICAO))
		        .apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("WebService de Usuarios")
				.description("Crud e relatorio de usuario baseado em filtros")
				.version("1.0.0")
				.license("Todos os direitos Reservados!")
				.contact(new Contact("Lucas de Oliveira da Silva", "https://www.linkedin.com/in/lucas-oliveira-da-silva-b97782125", "lucassilva_developer@hotmail.com"))
				.build();
	}
	
}