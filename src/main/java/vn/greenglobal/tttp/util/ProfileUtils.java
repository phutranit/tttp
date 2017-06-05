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

import vn.greenglobal.tttp.model.InvalidToken;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.QInvalidToken;
import vn.greenglobal.tttp.repository.InvalidTokenRepository;
import vn.greenglobal.tttp.repository.NguoiDungRepository;

@Component
public class ProfileUtils {

	@Value("${salt}")
	private String salt;

	@Autowired
	NguoiDungRepository nguoiDungRepository;

	@Autowired
	InvalidTokenRepository invalidTokenRep;
	
	private CommonProfile profile;
	private SignatureConfiguration secretSignatureConfiguration;
	private SecretEncryptionConfiguration secretEncryptionConfiguration;
	private JwtAuthenticator authenticator;

	public NguoiDung getUserInfo(String authHeader) {
		CommonProfile profile = getCommonProfile(authHeader);
		if (profile != null) {
			NguoiDung user = nguoiDungRepository.findByEmail(String.valueOf(profile.getAttribute("username")));
			return user;
		}
		return null;
	}

	public CommonProfile getCommonProfile(String authHeader) {
		if (authHeader != null && authHeader.startsWith("Bearer")) {
			String token = StringUtils.substringAfter(authHeader, " ");
			InvalidToken invalid = invalidTokenRep.findOne(QInvalidToken.invalidToken.token.eq(token));
			if(invalid!=null){
				return null;
			}
			secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
			secretEncryptionConfiguration = new SecretEncryptionConfiguration(salt);
			authenticator = new JwtAuthenticator(secretSignatureConfiguration, secretEncryptionConfiguration);
			profile = authenticator.validateToken(token);
			if (profile != null) {
				return profile;
			}
		}
		return null;
	}

}
