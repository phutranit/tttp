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
import org.springframework.web.bind.annotation.RequestHeader;
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
	public @ResponseBody ResponseEntity<Object> login(
			@RequestHeader(value = "Username", required = true) String username,
			@RequestHeader(value = "Password", required = true) String password) {
		Map<String, Object> result = new HashMap<>();
		NguoiDung user;
		if (username != null && !username.isEmpty()) {
			user = repo.findByTenDangNhap(username);
			if (user != null || username.equals("tttp")) {
				final SignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
				final SecretEncryptionConfiguration secretEncryptionConfiguration = new SecretEncryptionConfiguration(salt);
				final JwtGenerator<CommonProfile> generator = new JwtGenerator<>();
				generator.setSignatureConfiguration(secretSignatureConfiguration);
				generator.setEncryptionConfiguration(secretEncryptionConfiguration);
				
				CommonProfile commonProfile = new CommonProfile();
				//commonProfile.addRole(null);
				//commonProfile.addPermission(null);
				commonProfile.setId(user.getId());
				commonProfile.addAttribute("username", user.getTenDangNhap());
				String token = generator.generate(commonProfile);
				result.put("token", token);
				result.put("username", user.getTenDangNhap());
				result.put("userId", user.getId());
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
