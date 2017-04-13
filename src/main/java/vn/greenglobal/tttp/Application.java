package vn.greenglobal.tttp;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import vn.greenglobal.core.model.common.BaseRepositoryImpl;

@SpringBootApplication
// @Import(KatharsisConfigV2.class)
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
@EnableAutoConfiguration(exclude = { ElasticsearchAutoConfiguration.class })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/tttp/api/**");
            }
        };
    }
	
	
	/*@Bean
	public ServiceUrlProvider getServiceUrlProvider() {
		return new ServiceUrlProvider() {
			@Value("${katharsis.pathPrefix}")
			private String pathPrefix;

			@Resource
			private HttpServletRequest request;

			@Override
			public String getUrl() {
				return request.getScheme() + "://" + request.getHeader("host") + request.getContextPath() + pathPrefix;
			}
		};
	}*/

	/*
	 * @Bean public ServiceUrlProvider getServiceUrlProvider() { return new
	 * ServiceUrlProvider() {
	 * 
	 * @Value("${katharsis.pathPrefix}") private String pathPrefix;
	 * 
	 * @Resource private HttpServletRequest request;
	 * 
	 * @Override public String getUrl() { return request.getScheme() + "://" +
	 * request.getHeader("host") + request.getContextPath() + pathPrefix; } }; }
	 */
}
