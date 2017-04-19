package vn.greenglobal;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.matching.ExcludedPathMatcher;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.pac4j.springframework.security.web.SecurityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.thetransactioncompany.cors.CORSFilter;

import vn.greenglobal.core.model.common.BaseRepositoryImpl;
import vn.greenglobal.tttp.CustomAuthorizer;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
@EnableAutoConfiguration(exclude = { ElasticsearchAutoConfiguration.class })
@EnableWebSecurity
@Controller
@ComponentScan(basePackages = { "vn.greenglobal.core.model.common", "vn.greenglobal.tttp.controller",
		"vn.greenglobal.tttp.service", "vn.greenglobal.tttp" })
public class Application extends SpringBootServletInitializer {
	private final Logger log = LoggerFactory.getLogger(Application.class);
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/upload", produces = "application/json")
	@ResponseBody
	public Object upload(HttpServletRequest req) {
		System.out.println("//file");
		System.out.println(req);
		Enumeration<String> hd = req.getHeaderNames();
		String result = "";
		for (; hd.hasMoreElements();) {
			String s = hd.nextElement();
			System.out.println(s + " = " + req.getHeader(s));
			result += s + " = " + req.getHeader(s) + "; ";
		}
		return Collections.singletonMap("response", result);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/authenticate", produces = "application/json")
	@ResponseBody
	public Object authenticate(HttpServletRequest req) {
		System.out.println("//authenticate");
		System.out.println(req);
		Enumeration<String> hd = req.getHeaderNames();
		String result = "";
		for (; hd.hasMoreElements();) {
			String s = hd.nextElement();
			System.out.println(s + " = " + req.getHeader(s));
			result += s + " = " + req.getHeader(s) + "; ";
		}
		return Collections.singletonMap("response", result);
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

				registry.addMapping("/**").allowedOrigins("*").allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE").maxAge(3600).allowedHeaders("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
			}
		};
	}

	@Bean
	public WebSecurityConfigurerAdapter securityConfiguration() {
		return new WebSecurityConfigurerAdapter() {
			/*@Override
			public void configure(AuthenticationManagerBuilder auth) throws Exception {
				auth.inMemoryAuthentication().withUser("tttp123").password("tttp@123").roles("USER", "ADMIN",
						"ACTUATOR");

			}*/

			@Override
			public void configure(WebSecurity sec) throws Exception {
				sec.ignoring().antMatchers("/login");
			}
			
			@Override
			protected void configure(HttpSecurity http) throws Exception {
				
				final SecurityFilter filter = new SecurityFilter(configPac4j(), "ParameterClient,HeaderClient", "custom");
				http.addFilterBefore(filter, BasicAuthenticationFilter.class).sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.NEVER);
				
				http.authorizeRequests()
					.anyRequest().authenticated()
					.and().csrf().disable();

			}
		};
	}

	@Value("${salt}")
	private String salt;

	@Bean
	public Config configPac4j() throws ParseException {
		final SignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
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
		return config;
	}
}
