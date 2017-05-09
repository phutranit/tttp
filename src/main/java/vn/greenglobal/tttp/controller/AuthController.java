package vn.greenglobal.tttp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pac4j.core.config.Config;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.engine.LogoutLogic;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.VaiTro;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.NguoiDungRepository;
import vn.greenglobal.tttp.repository.VaiTroRepository;
import vn.greenglobal.tttp.service.CongChucService;
import vn.greenglobal.tttp.service.VaiTroService;
import vn.greenglobal.tttp.util.ProfileUtils;
import vn.greenglobal.tttp.util.Utils;

@RestController
@Api(value = "auth", description = "")
public class AuthController {

	@Value("${salt}")
	private String salt;

	@Autowired
	Config config;

	@Autowired
	ProfileUtils profileUtil;

	@Autowired
	NguoiDungRepository nguoiDungRepository;

	@Autowired
	CongChucRepository congChucRepository;

	@Autowired
	CongChucService congChucService;

	@Autowired
	VaiTroRepository vaiTroRepository;

	@Autowired
	VaiTroService vaiTroService;

	@RequestMapping(method = RequestMethod.POST, value = "/auth/login")
	public @ResponseBody ResponseEntity<Object> login(
			@RequestHeader(value = "Username", required = true) String username,
			@RequestHeader(value = "Password", required = true) String password) {
		Map<String, Object> result = new HashMap<>();
		NguoiDung user;

		if (username != null && !username.isEmpty()) {
			user = nguoiDungRepository.findByTenDangNhap(username);
			if (user != null) {
				if (user.checkPassword(password)) {
					return returnUser(result, user);
				} else {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.USER_PASSWORD_INCORRECT.name(),
							ApiErrorEnum.USER_PASSWORD_INCORRECT.getText());
				}
			} else {
				congChucService.bootstrapCongChuc(congChucRepository, nguoiDungRepository);
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
		}

		return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
				ApiErrorEnum.DATA_NOT_FOUND.getText());
	}

	/*
	 * @RequestMapping(method = RequestMethod.POST, value = "/logout")
	 * public @ResponseBody ResponseEntity<Object> logout(
	 * 
	 * @RequestHeader(value = "Authorization", required = true) String
	 * authorization, HttpServletRequest request, HttpServletResponse response,
	 * Authentication auth) { Map<String, Object> result = new HashMap<>();
	 * NguoiDung user; System.out.println("auth:"+auth);
	 * 
	 * return Utils.responseErrors(HttpStatus.NOT_FOUND,
	 * ApiErrorEnum.DATA_NOT_FOUND.name(),
	 * ApiErrorEnum.DATA_NOT_FOUND.getText()); }
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, value = "/auth/logout")
	public void logout(@RequestHeader(value = "Authorization", required = true) final String authorization,
			final HttpServletRequest request, final HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth:" + auth);
		final J2EContext context = new J2EContext(request, response);

		LogoutLogic<Object, J2EContext> logoutLogic = config.getLogoutLogic();
		@SuppressWarnings("rawtypes")
		final Function<WebContext, ProfileManager> profileManagerFactory = (Function<WebContext, ProfileManager>) config
				.getProfileManagerFactory();
		System.out.println(logoutLogic);
		System.out.println("profileManagerFactory:" + profileManagerFactory);
		ProfileManager<CommonProfile> profileManager = null;
		final List<CommonProfile> profiles;
		if (profileManagerFactory != null) {
			profileManager = profileManagerFactory.apply(context);
			profiles = profileManager.getAll(true);
			System.out.println("profiles:" + profiles);
		}

		// logoutLogic.perform(context, config,
		// J2ENopHttpActionAdapter.INSTANCE, null, null, true, true, true);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/auth/switchRole")
	public @ResponseBody ResponseEntity<Object> switchRole(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "vaiTroMacDinhId", required = false) Long vaiTroMacDinhId) {

		Map<String, Object> result = new HashMap<>();

		NguoiDung user = profileUtil.getUserInfo(authorization);

		if (user != null) {
			VaiTro vaiTroMacDinh = vaiTroRepository.findOne(vaiTroService.predicateFindOne(vaiTroMacDinhId));
			user.setVaiTroMacDinh(vaiTroMacDinh);
			return returnUser(result, user);
		}

		return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
				ApiErrorEnum.DATA_NOT_FOUND.getText());
	}

	private ResponseEntity<Object> returnUser(Map<String, Object> result, NguoiDung user) {
		CongChuc congChuc = null;
		final SignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
		final SecretEncryptionConfiguration secretEncryptionConfiguration = new SecretEncryptionConfiguration(salt);
		final JwtGenerator<CommonProfile> generator = new JwtGenerator<>();

		generator.setSignatureConfiguration(secretSignatureConfiguration);
		generator.setEncryptionConfiguration(secretEncryptionConfiguration);

		CommonProfile commonProfile = new CommonProfile();
		commonProfile.addAttribute("username", user.getTenDangNhap());

		if (user != null) {
			commonProfile.setId(user.getId());
			congChuc = congChucRepository.findOne(congChucService.predicateFindByNguoiDungId(user.getId()));

			if (congChuc != null) {
				commonProfile.addAttribute("congChucId", congChuc.getId());
				commonProfile.addAttribute("coQuanQuanLyId", congChuc.getCoQuanQuanLy().getId());
				commonProfile.addAttribute("loaiVaiTro",
						user.getVaiTroMacDinh() != null ? user.getVaiTroMacDinh().getLoaiVaiTro() : "");

				result.put("congChucId", congChuc.getId());
				result.put("coQuanQuanLyId", congChuc.getId());
				result.put("tenCoQuanQuanLy", congChuc.getCoQuanQuanLy().getTen());
			}

			String token = generator.generate(commonProfile);
			result.put("token", token);
			result.put("username", user.getTenDangNhap());
			result.put("userId", user.getId());
			result.put("roles", user.getVaiTros());
			result.put("vaiTroMacDinhId", user.getVaiTroMacDinh().getId());
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
