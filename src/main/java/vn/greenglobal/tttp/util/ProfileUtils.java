package vn.greenglobal.tttp.util;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.repository.NguoiDungRepository;

@Component
public class ProfileUtils {

	@Value("${salt}")
	private String salt;

	@Autowired
	NguoiDungRepository nguoiDungRepository;
	
	final SignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
	final SecretEncryptionConfiguration secretEncryptionConfiguration = new SecretEncryptionConfiguration(salt);
	final JwtAuthenticator authenticator = new JwtAuthenticator(secretSignatureConfiguration, secretEncryptionConfiguration);
	
	private CommonProfile profile;
	
	public NguoiDung getUserInfo(String authHeader) {
		if (authHeader != null && authHeader.startsWith("Bearer")) {
			String token = StringUtils.substringAfter(authHeader, " ");
			profile = authenticator.validateToken(token);
			NguoiDung user = nguoiDungRepository.findByTenDangNhap(String.valueOf(profile.getAttribute("username")));
			return user;
		}
		return null;
	}
}
