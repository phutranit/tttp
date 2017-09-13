package vn.greenglobal;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;

import javax.servlet.MultipartConfigElement;

import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.springframework.security.web.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.LocaleResolver;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import vn.greenglobal.core.model.common.BaseRepositoryImpl;
import vn.greenglobal.tttp.CustomAuthorizer;
import vn.greenglobal.tttp.repository.ChucVuRepository;
import vn.greenglobal.tttp.repository.CuocThanhTraRepository;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.service.ChucVuService;
import vn.greenglobal.tttp.util.upload.StorageProperties;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
@EnableAutoConfiguration(exclude = { ElasticsearchAutoConfiguration.class })
@EnableConfigurationProperties(StorageProperties.class)
@Controller
@ComponentScan(basePackages = { "vn.greenglobal.core.model.common", "vn.greenglobal.tttp.controller",
		"vn.greenglobal.tttp.service", "vn.greenglobal.tttp" })
public class Application extends SpringBootServletInitializer {

	public static Application app;

	public Application() {
		app = this;
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
//		System.setProperty("user.timezone", "GMT+7");
		SpringApplication.run(Application.class, args);
	}

	// @Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
			System.out.println(":::::" + beanNames.length + " beans");
		};
	}
	
//	@Value("${cors.allowedOrigins}")
//	private String[] myAllowedOriginList;
	
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurerAdapter() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//						.allowedOrigins(myAllowedOriginList)
//						.allowCredentials(true)
//						.allowedMethods("POST", "PATCH", "GET", "PUT", "OPTIONS", "DELETE", "HEAD")
//						.allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Content-Length",
//								"email", "password", "authorization", "client-security-token",
//								"X-Application-Context", "Date", "Content-Disposition")
//						.maxAge(3600);
//			}
//		};
//	}

	@Bean
	public WebSecurityConfigurerAdapter securityConfiguration() {
		return new WebSecurityConfigurerAdapter() {

			@Override
			public void configure(AuthenticationManagerBuilder auth) throws Exception {
				auth.inMemoryAuthentication().withUser("tttp123").password("tttp@123").roles("USER", "ADMIN",
						"ACTUATOR");
			}

			@Override
			public void configure(WebSecurity sec) throws Exception {
				sec.ignoring().antMatchers("/auth/login", "/auth/logout", "/v2/api-docs",
						"/giaiQuyetDons/inPhieuGiaoNhiemVuXacMinhToCao", "/giaiQuyetDons/inPhieuGiaoNhiemVuXacMinhKhieuNai",
						"/soTiepCongDans/inPhieuHen", "/documents/uploadhandler", "/tttpdata/files/**",
						"/soTiepCongDans/excel", "/xuLyDons/inPhieuDeXuatThuLy", "/dons/xuatExcel",
						"/xuLyDons/inPhieuKhongDuDieuKienThuLyKhieuNai", "/xuLyDons/inPhieuDuThaoThongBaoThuLyGQTC",
						"/xuLyDons/inPhieuDuThaoThongBaoThuLyKhieuNai", "/xuLyDons/inPhieuDeXuatKienNghi",
						"/xuLyDons/inPhieuKhongDuDieuKienThuLy", "/soTiepCongDans/word", "/configuration/ui",
						"/configuration/security", "/xuLyDons/inPhieuTraDonVaHuongDanKhieuNai", "/swagger-resources",
						"/swagger-ui.html", "/swagger-resources/configuration/ui", "/xuLyDons/inPhieuChuyenDonToCao",
						"/xuLyDons/inPhieuTraDonChuyenKhongDungThamQuyen", "/xuLyDons/inPhieuDuThaoThongBaoThuLyKienNghi",
						"/xuLyDons/inPhieuChuyenDonKienNghiPhanAnh", "/swagger-resources/configuration/security",
						"/thongKeBaoCaos/tongHopKetQuaTiepCongDan/xuatExcel", "/thongKeBaoCaos/tongHopKetQuaXuLyDonThu/xuatExcel",
						"/thongKeBaoCaos/tongHopKetQuaGiaiQuyetDonKhieuNai/xuatExcel",
						"/thongKeBaoCaos/tongHopKetQuaGiaiQuyetDonToCao/xuatExcel",
						"/soTiepCongDans/inPhieuTuChoi", "/soTiepCongDans/inPhieuHuongDanKhieuNai", 
						"/soTiepCongDans/inPhieuHuongDanToCao",
						"/thongKeBaoCaos/tongHopKetQuaThanhTraTheoHanhChinh/xuatExcel",
						"/thongKeBaoCaos/tongHopKetQuaThanhTraTheoDauTuXayDungCoBan/xuatExcel",
						"/thongKeBaoCaos/tongHopKetQuaThanhTraTheoTaiChinhNganSach/xuatExcel",
						"/thongKeBaoCaos/tongHopKetQuaThanhTraTheoDatDai/xuatExcel",
						"/webjars/**").antMatchers(HttpMethod.OPTIONS, "/**");
			}

			@Override
			protected void configure(HttpSecurity http) throws Exception {

				final SecurityFilter filter = new SecurityFilter(configPac4j(), "ParameterClient,HeaderClient",
						"custom");
				http.addFilterBefore(filter, BasicAuthenticationFilter.class).sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.NEVER);

				http.authorizeRequests().anyRequest().authenticated()
						// .and().httpBasic()
						.and().logout().logoutSuccessUrl("/").invalidateHttpSession(true).clearAuthentication(true)
						.and().csrf().disable();
			}
		};
	}

	@Bean
	public Config configPac4j() throws ParseException {
		final SignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
		final SecretEncryptionConfiguration secretEncryptionConfiguration = new SecretEncryptionConfiguration(salt);
		final JwtAuthenticator authenticator = new JwtAuthenticator();
		authenticator.setSignatureConfiguration(secretSignatureConfiguration);
		authenticator.setEncryptionConfiguration(secretEncryptionConfiguration);
		HeaderClient headerClient = new HeaderClient(HEADER_STRING, TOKEN_PREFIX + " ", authenticator);
		ParameterClient parameterClient = new ParameterClient("token", authenticator);
		parameterClient.setSupportGetRequest(true);
		final Clients clients = new Clients("http://localhost", parameterClient, headerClient);
		final Config config = new Config(clients);
		config.addAuthorizer("admin", new RequireAnyRoleAuthorizer<>("ROLE_ADMIN"));
		config.addAuthorizer("custom", new CustomAuthorizer());
		return config;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.US);
		return slr;
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:locale/messages");
		messageSource.setCacheSeconds(3600); // refresh cache once per hour
		return messageSource;
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(maxFileSize);
		factory.setMaxRequestSize(maxRequestSize);
		return factory.createMultipartConfig();
	}

	static final long EXPIRATIONTIME = 864_000_000; // 10 days
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	
	@Value("${airbrake.active:false}")
	public boolean airBrakeActive;
	
	@Value("${salt}")
	private String salt;

	@Value("${spring.http.multipart.max-file-size:54000KB}")
	private String maxFileSize;

	@Value("${spring.http.multipart.max-request-size:54000KB}")
	private String maxRequestSize;

	@Value("${action.xem:xem}")
	public String XEM = "";
	@Value("${action.lietke:lietke}")
	public String LIETKE = "";
	@Value("${action.sua:sua}")
	public String SUA = "";
	@Value("${action.xoa:xoa}")
	public String XOA = "";
	@Value("${action.them:them}")
	public String THEM = "";
	@Value("${action.gui:gui}")
	public String GUI = "";
	@Value("${action.duyet:duyet}")
	public String DUYET = "";
	@Value("${action.export:export}")
	public String EXPORT = "";

	@Value("${resource.nguoidung:nguoidung}")
	public String NGUOIDUNG = "";
	@Value("${resource.vaitro:vaitro}")
	public String VAITRO = "";

	public char CHAR_CACH = ':';
	public String CACH = CHAR_CACH + "";

	@Value("${resource.vaitro}" + ":" + "${action.xem}")
	public String VAITRO_XEM;
	@Value("${resource.vaitro}" + ":" + "${action.them}")
	public String VAITRO_THEM = "";
	@Value("${resource.vaitro}" + ":" + "${action.lietke}")
	public String VAITRO_LIETKE = "";
	@Value("${resource.vaitro}" + ":" + "${action.xoa}")
	public String VAITRO_XOA = "";
	@Value("${resource.vaitro}" + ":" + "${action.sua}")
	public String VAITRO_SUA = "";

	@Value("${resource.nguoidung}" + ":" + "${action.xem}")
	public String NGUOIDUNG_XEM = "";
	@Value("${resource.nguoidung}" + ":" + "${action.them}")
	public String NGUOIDUNG_THEM = "";
	@Value("${resource.nguoidung}" + ":" + "${action.lietke}")
	public String NGUOIDUNG_LIETKE = "";
	@Value("${resource.nguoidung}" + ":" + "${action.xoa}")
	public String NGUOIDUNG_XOA = "";
	@Value("${resource.nguoidung}" + ":" + "${action.sua}")
	public String NGUOIDUNG_SUA = "";

	public String[] getRESOURCES() {
		return new String[] { NGUOIDUNG, VAITRO };
	}

	public String[] getACTIONS() {
		return new String[] { LIETKE, XEM, THEM, SUA, XOA, GUI, DUYET };
	}
	
	@Autowired
	private SoTiepCongDanRepository soTiepCongDanRepository;
	
	@Autowired
	private CuocThanhTraRepository cuocThanhTraRepository;
	
	@Autowired
	private DonCongDanRepository donCongDanRepository;
	
	@Autowired
	private ChucVuRepository chucVuRepository;

	@Autowired
	private DonRepository donRepository;
	
	@Autowired
	private ChucVuService chucVuService;
	
	
	public SoTiepCongDanRepository getSoTiepCongDanRepository() {
		return soTiepCongDanRepository;
	}

	public CuocThanhTraRepository getCuocThanhTraRepository() {
		return cuocThanhTraRepository;
	}
	
	public DonRepository getDonRepository() {
		return donRepository;
	}

	public DonCongDanRepository getDonCongDanRepository() {
		return donCongDanRepository;
	}
	
	public ChucVuRepository getChucVuRepository() {
		return chucVuRepository;
	}

	public ChucVuService getChucVuService() {
		return chucVuService;
	}

}
