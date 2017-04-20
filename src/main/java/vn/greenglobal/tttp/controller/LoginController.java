package vn.greenglobal.tttp.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.repository.NguoiDungRepository;
import vn.greenglobal.tttp.util.Utils;

@RestController
@Api(value = "login", description = "")
public class LoginController {
	
	private static Log log = LogFactory.getLog(LoginController.class);
	
	@Value("${salt}")
	private String salt;
	
	@Autowired
	NguoiDungRepository repo;

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public @ResponseBody ResponseEntity<Object> login(@RequestBody NguoiDung nguoiDung, Errors errors){
		Map<String, Object> result = new HashMap<>();
		
		NguoiDung user = null;
		if(nguoiDung!=null){
			System.out.println(nguoiDung.getTenDangNhap());
			user = repo.findByTenDangNhap(nguoiDung.getTenDangNhap());
			if(user!=null){
				final SignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
				final SecretEncryptionConfiguration secretEncryptionConfiguration = new SecretEncryptionConfiguration(salt);
				final JwtGenerator<CommonProfile> generator = new JwtGenerator<>();
				generator.setSignatureConfiguration(secretSignatureConfiguration);
				generator.setEncryptionConfiguration(secretEncryptionConfiguration);
				CommonProfile commonProfile = new CommonProfile();
				commonProfile.addAttribute("user", user);
				String token = generator.generate(commonProfile);
				result.put("token", token);
				result.put("user", user);
				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
		}
		return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
				ApiErrorEnum.DATA_NOT_FOUND.getText());
	}
}
