package vn.greenglobal.tttp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		/*ParameterBuilder aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.name("Authorization")
				.modelRef(new ModelRef("string"))
				.parameterType("header").required(false).build();
		
		List<Parameter> aParameters = new ArrayList<Parameter>();
		aParameters.add(aParameterBuilder.build());*/
		return new Docket(DocumentationType.SWAGGER_2).select()
				// .apis(RequestHandlerSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("vn.greenglobal.tttp.controller")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo());
				//.globalOperationParameters(aParameters);
	}

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("API Thanh Tra Thành Phố", "Danh sách API Thanh Tra Thành Phố",
				"API VERSION: 1.0", "", "", "", "");
		return apiInfo;
	}
}
