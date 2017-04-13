package vn.greenglobal.tttp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
   /* @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
        		.
          .select()                                  
//          .apis(RequestHandlerSelectors.any()) 
          .apis(RequestHandlerSelectors.basePackage("vn.greenglobal.tttp.controller"))
          .paths(PathSelectors.any())                         
          .build()
          .apiInfo(apiInfo());                                           
    }*/
    
    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("apis")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build();
    }
    
    /*private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
    private ApiInfo apiInfo() {
        @SuppressWarnings("deprecation")
		ApiInfo apiInfo = new ApiInfo(
          "API Thanh Tra Thành Phố",
          "Danh sách danh mục API.",
          "API TOS",
          "Terms of service",
          "toantt@greenglobal.com",
          "License of API",
          "API license URL");
        return apiInfo;
    }*/
    
    
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Ecommerce Platform REST API Documents")
				.description("Documents with Swagger 2").termsOfServiceUrl("http://nit-software.com").contact("")
				.license("").licenseUrl("").version("2.0").build();
	}
}
