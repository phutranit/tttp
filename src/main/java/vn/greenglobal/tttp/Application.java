package vn.greenglobal.tttp;

import java.text.ParseException;
import java.util.Arrays;

import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.matching.Matcher;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.pac4j.springframework.security.web.SecurityFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import vn.greenglobal.core.model.common.BaseRepositoryImpl;
import vn.greenglobal.tttp.util.CustomAuthorizer;

@SpringBootApplication
@EnableWebSecurity
@EnableWebMvc
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
@EnableAutoConfiguration(exclude = { ElasticsearchAutoConfiguration.class })
public class Application extends SpringBootServletInitializer {

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
	/*
	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://domain1.com");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}
	*/
	
	@Bean
	public WebSecurityConfigurerAdapter securityConfiguration() {
		return new WebSecurityConfigurerAdapter() {
			@Override
			public void configure(AuthenticationManagerBuilder auth) throws Exception {
				auth.inMemoryAuthentication()
					.withUser("tttp123").password("tttp@123").roles("USER", "ADMIN", "ACTUATOR");
			}

			@Override
			protected void configure(HttpSecurity http) throws Exception {
				
				http
				.authorizeRequests()
						.antMatchers("/resources/**", "/about").permitAll() 
						.antMatchers("/browser/**").hasRole("ADMIN")
						.antMatchers("/**").authenticated()
					.and()
				.formLogin()
					.and()
				.logout()
						.invalidateHttpSession(true)
					.and()
				.httpBasic();
				
				/*final SecurityFilter filter = new SecurityFilter(configPac4j(), "ParameterClient,HeaderClient", "custom");
				http.addFilterBefore(filter, BasicAuthenticationFilter.class)
						.sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.NEVER);*/
	
				
			}
		};
	}

	
	
	@Value("${salt}")
	private String salt;// = "12345678901234567890123456789012"; // 32 chars

	@Bean
	public Config configPac4j() throws ParseException {
		final SecretSignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
		final SecretEncryptionConfiguration secretEncryptionConfiguration = new SecretEncryptionConfiguration(salt);
		final JwtAuthenticator authenticator = new JwtAuthenticator();
		
		authenticator.setSignatureConfiguration(secretSignatureConfiguration);
		authenticator.setEncryptionConfiguration(secretEncryptionConfiguration);
		
		HeaderClient headerClient = new HeaderClient("Authorization", "Bearer ", authenticator);
		
		ParameterClient parameterClient = new ParameterClient("token", authenticator);
		parameterClient.setSupportGetRequest(true);
		
		final Clients clients = new Clients("http://localhost", parameterClient, headerClient);
		
		final Config config = new Config(clients);
		config.addAuthorizer("admin", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
		config.addAuthorizer("custom", new CustomAuthorizer());
		
		final JwtGenerator<CommonProfile> generator = new JwtGenerator<>();
		generator.setSignatureConfiguration(secretSignatureConfiguration);
		generator.setEncryptionConfiguration(secretEncryptionConfiguration);
		System.out.println(1);
		CommonProfile commonProfile = new CommonProfile();
		commonProfile.addRole("admin");
		commonProfile.addPermission("admin");
		String token = generator.generate(commonProfile);
		System.out.println(token);
		System.out.println(authenticator.validateTokenAndGetClaims(token));
		System.out.println(authenticator.validateToken(token));
		System.out.println(2);
		return config;
	}
}
