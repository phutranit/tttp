package vn.greenglobal.tttp.controller;

import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;
import org.pac4j.springframework.security.authentication.Pac4jAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import vn.greenglobal.tttp.enums.TrangThaiInvalidTokenEnum;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.InvalidToken;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.QNguoiDung;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.NguoiDungRepository;
import vn.greenglobal.tttp.service.CongChucService;
import vn.greenglobal.tttp.service.InvalidTokenService;

@Controller
public class CasController {
	
	@Autowired
	NguoiDungRepository nguoiDungRepository;

	@Autowired
	CongChucRepository congChucRepository;
	
	@Autowired
	CongChucService congChucService;
	
	@Autowired
	InvalidTokenService invalidTokenService;
	
	@Value("${salt}")
	private String salt;
	
	@GetMapping("/cas/login")
	public String login(ModelMap modelMap) {
		System.out.println("secured::");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth::" + auth);
		if (auth != null) {
			Pac4jAuthenticationToken pac4j = (Pac4jAuthenticationToken) auth;
			
			String username = pac4j.getName();
			String redirectUrl = "http://localhost:3000/redirect?token=";
			
			NguoiDung nguoiDung = nguoiDungRepository.findOne(QNguoiDung.nguoiDung.daXoa.eq(false).and(QNguoiDung.nguoiDung.active.isTrue()).and(QNguoiDung.nguoiDung.email.eq(username)));
			if (nguoiDung != null && !nguoiDung.isDaXoa() && nguoiDung.isActive()) {
				CongChuc congChuc = congChucRepository.findOne(congChucService.predicateFindByNguoiDungId(nguoiDung.getId()));
				if(congChuc!=null) {
					final SignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
					final SecretEncryptionConfiguration secretEncryptionConfiguration = new SecretEncryptionConfiguration(salt);
					final JwtGenerator<CommonProfile> generator = new JwtGenerator<>();

					generator.setSignatureConfiguration(secretSignatureConfiguration);
					generator.setEncryptionConfiguration(secretEncryptionConfiguration);

					CommonProfile commonProfile = new CommonProfile();
					commonProfile.addAttribute("email", username);
					commonProfile.setId(nguoiDung.getId());
					String token = generator.generate(commonProfile);
					InvalidToken invalidToken = invalidTokenService.predFindToKenCurrentByUser(nguoiDung.getId());
					if (invalidToken == null) {
						invalidToken = new InvalidToken();
					}
					invalidToken.setToken(token);
					invalidToken.setActive(true);
					invalidToken.setNguoiDung(nguoiDung);
					invalidToken.setTrangThaiToken(TrangThaiInvalidTokenEnum.DANG_NHAP);
					invalidTokenService.save(invalidToken, congChuc.getId());
					
					return "redirect:"+redirectUrl + token;
				}
			}
		}
		return "redirect:http://localhost:3000";
	}

	@GetMapping("/cas/callback")
	public String callback(ModelMap modelMap) {
		System.out.println("callback::");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth::" + auth);
		if (auth != null && auth.getPrincipal() != null && auth.getPrincipal() instanceof UserDetails) {
			modelMap.put("username", ((UserDetails) auth.getPrincipal()).getUsername());

		}
		return "redirect:http://localhost:3000";
	}

}
