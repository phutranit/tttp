package vn.greenglobal.tttp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.repository.NguoiDungRepository;

@RestController
@Api(value = "login", description = "")
public class LoginController {
	
	private static Log log = LogFactory.getLog(LoginController.class);
	
	@Value("${salt}")
    private String salt;
	
	@Autowired
	NguoiDungRepository repo;

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public String login(@RequestBody NguoiDung nguoiDung, Errors errors){
		System.out.println("login:"+errors);
		if(nguoiDung!=null){
			System.out.println(nguoiDung.getTenDangNhap());
		}
		final SecretSignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
		final SecretEncryptionConfiguration secretEncryptionConfiguration = new SecretEncryptionConfiguration(salt);
		final JwtGenerator<CommonProfile> generator = new JwtGenerator<>();
		generator.setSignatureConfiguration(secretSignatureConfiguration);
		generator.setEncryptionConfiguration(secretEncryptionConfiguration);
		System.out.println(1);
		CommonProfile commonProfile = new CommonProfile();
		commonProfile.addRole("admin");
		commonProfile.addPermission("admin");
		String token = generator.generate(commonProfile);
		System.out.println(token);
		return token;
	}
}
