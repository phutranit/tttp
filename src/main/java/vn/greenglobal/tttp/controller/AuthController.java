package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.config.Config;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import io.swagger.annotations.Api;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.FlowStateEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.InvalidToken;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.model.QNguoiDung;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.model.VaiTro;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.InvalidTokenRepository;
import vn.greenglobal.tttp.repository.NguoiDungRepository;
import vn.greenglobal.tttp.repository.ProcessRepository;
import vn.greenglobal.tttp.repository.StateRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.repository.VaiTroRepository;
import vn.greenglobal.tttp.service.CongChucService;
import vn.greenglobal.tttp.service.ProcessService;
import vn.greenglobal.tttp.service.StateService;
import vn.greenglobal.tttp.service.TransitionService;
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
	StateRepository stateRepository;
	
	@Autowired
	ProcessRepository processRepository;
	
	@Autowired
	CoQuanQuanLyRepository coQuanQuanLyRepository;

	@Autowired
	TransitionRepository transitionRepository;
	
	@Autowired
	TransitionService transitionService;
	
	@Autowired
	StateService stateService;
	
	@Autowired
	ProcessService processService;

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

	@Autowired
	InvalidTokenRepository invalidTokenRep;

	@RequestMapping(method = RequestMethod.POST, value = "/auth/login")
	public @ResponseBody ResponseEntity<Object> login(@RequestHeader(value = "Email", required = true) String username,
			@RequestHeader(value = "Password", required = true) String password) {

		try {
			Map<String, Object> result = new HashMap<>();
			NguoiDung user;

			if (username != null && !username.isEmpty()) {
				user = nguoiDungRepository.findOne(QNguoiDung.nguoiDung.daXoa.eq(false).and(QNguoiDung.nguoiDung.email.eq(username)));
				if (user != null && !user.isDaXoa() && user.isActive()) {
					if (user.checkPassword(password)) {
						return returnUser(result, user);
					} else {
						return Utils.responseErrors(HttpStatus.NOT_FOUND,
								ApiErrorEnum.LOGIN_USER_PASSWORD_INCORRECT.name(),
								ApiErrorEnum.LOGIN_USER_PASSWORD_INCORRECT.getText(),
								ApiErrorEnum.LOGIN_USER_PASSWORD_INCORRECT.getText());
					}
				} else {
					congChucService.bootstrapCongChuc(congChucRepository, nguoiDungRepository);
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.USER_NOT_EXISTS.name(),
							ApiErrorEnum.USER_NOT_EXISTS.getText(), ApiErrorEnum.USER_NOT_EXISTS.getText());
				}
			}

			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/auth/logout")
	public ResponseEntity<Object> logout(
			@RequestHeader(value = "Authorization", required = true) final String authorization,
			final HttpServletRequest request, final HttpServletResponse response) {
		/*
		 * Authentication auth =
		 * SecurityContextHolder.getContext().getAuthentication();
		 * System.out.println("auth:" + auth); final J2EContext context = new
		 * J2EContext(request, response);
		 * 
		 * LogoutLogic<Object, J2EContext> logoutLogic =
		 * config.getLogoutLogic();
		 * 
		 * @SuppressWarnings("rawtypes") final Function<WebContext,
		 * ProfileManager> profileManagerFactory = (Function<WebContext,
		 * ProfileManager>) config .getProfileManagerFactory();
		 * System.out.println(logoutLogic);
		 * System.out.println("profileManagerFactory:" + profileManagerFactory);
		 * ProfileManager<CommonProfile> profileManager = null; final
		 * List<CommonProfile> profiles; if (profileManagerFactory != null) {
		 * profileManager = profileManagerFactory.apply(context); profiles =
		 * profileManager.getAll(true); System.out.println("profiles:" +
		 * profiles); }
		 */

		try {
			if (authorization != null && authorization.startsWith("Bearer")) {
				String currentToken = StringUtils.substringAfter(authorization, " ");
				final SignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
				final SecretEncryptionConfiguration secretEncryptionConfiguration = new SecretEncryptionConfiguration(
						salt);
				final JwtAuthenticator authenticator = new JwtAuthenticator(secretSignatureConfiguration,
						secretEncryptionConfiguration);
				if (authenticator.validateToken(currentToken) != null) {
					InvalidToken token = new InvalidToken(currentToken);
					invalidTokenRep.save(token);
					return new ResponseEntity<>(HttpStatus.OK);
				}
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/auth/switchRole")
	public @ResponseBody ResponseEntity<Object> switchRole(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "vaiTroMacDinhId", required = false) Long vaiTroMacDinhId) {

		try {
			Map<String, Object> result = new HashMap<>();

			NguoiDung user = profileUtil.getUserInfo(authorization);

			if (user != null) {
				VaiTro vaiTroMacDinh = vaiTroRepository.findOne(vaiTroService.predicateFindOne(vaiTroMacDinhId));
				user.setVaiTroMacDinh(vaiTroMacDinh);
				return returnUser(result, user);
			}

			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	private ResponseEntity<Object> returnUser(Map<String, Object> result, NguoiDung user) {
		try {
			CongChuc congChuc = null;
			final SignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
			final SecretEncryptionConfiguration secretEncryptionConfiguration = new SecretEncryptionConfiguration(salt);
			final JwtGenerator<CommonProfile> generator = new JwtGenerator<>();

			generator.setSignatureConfiguration(secretSignatureConfiguration);
			generator.setEncryptionConfiguration(secretEncryptionConfiguration);

			CommonProfile commonProfile = new CommonProfile();
			commonProfile.addAttribute("email", user.getEmail());

			if (user != null) {
				commonProfile.setId(user.getId());
				congChuc = congChucRepository.findOne(congChucService.predicateFindByNguoiDungId(user.getId()));

				if (congChuc != null) {
					commonProfile.addAttribute("congChucId", congChuc.getId());
					commonProfile.addAttribute("coQuanQuanLyId", congChuc.getCoQuanQuanLy().getId());
					commonProfile.addAttribute("donViId", congChuc.getCoQuanQuanLy().getDonVi().getId());
					commonProfile.addAttribute("loaiVaiTro", user.getVaiTroMacDinh() != null ? user.getVaiTroMacDinh().getLoaiVaiTro() : "");
					commonProfile.addAttribute("capCoQuanQuanLyId", congChuc.getCoQuanQuanLy() != null ? congChuc.getCoQuanQuanLy().getCapCoQuanQuanLy().getId() : "");
					commonProfile.addAttribute("capCoQuanQuanLyCuaDonViId", congChuc.getCoQuanQuanLy() != null ? congChuc.getCoQuanQuanLy().getDonVi().getCapCoQuanQuanLy().getId() : "");
					commonProfile.addAttribute("quyenBatDauQuyTrinh", checkQuyenBatDauQuyTrinhXuLyDon(congChuc.getCoQuanQuanLy().getDonVi().getId(), user.getVaiTroMacDinh().getLoaiVaiTro()));

					result.put("congChucId", congChuc.getId());
					result.put("coQuanQuanLyId", congChuc.getCoQuanQuanLy().getId());
					result.put("capCoQuanQuanLyId", congChuc.getCoQuanQuanLy() != null ? congChuc.getCoQuanQuanLy().getCapCoQuanQuanLy().getId() : "");
					result.put("capCoQuanQuanLyCuaDonViId", congChuc.getCoQuanQuanLy() != null ? congChuc.getCoQuanQuanLy().getDonVi().getCapCoQuanQuanLy().getId() : "");
					result.put("tenCoQuanQuanLy", congChuc.getCoQuanQuanLy().getTen());
					result.put("tenCapCoQuanQuanLy", congChuc.getCoQuanQuanLy() != null ? congChuc.getCoQuanQuanLy().getCapCoQuanQuanLy().getTen() : "");
					result.put("tenCapCoQuanQuanLyCuaDonViId", congChuc.getCoQuanQuanLy() != null ? congChuc.getCoQuanQuanLy().getDonVi().getCapCoQuanQuanLy().getTen() : "");
					result.put("donViId", congChuc.getCoQuanQuanLy().getDonVi().getId());
					result.put("quyenBatDauQuyTrinh", checkQuyenBatDauQuyTrinhXuLyDon(congChuc.getCoQuanQuanLy().getDonVi().getId(), user.getVaiTroMacDinh().getLoaiVaiTro()));
				}

				String token = generator.generate(commonProfile);
				result.put("token", token);
				result.put("email", user.getEmail());
				result.put("userId", user.getId());
				result.put("roles", user.getVaiTros());
				result.put("vaiTroMacDinhId", user.getVaiTroMacDinh().getId());
				result.put("loaiVaiTroMacDinh", user.getVaiTroMacDinh().getLoaiVaiTro());
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	private boolean checkQuyenBatDauQuyTrinhXuLyDon(Long donViId, VaiTroEnum loaiVaiTro) {
		State beginState = stateRepository.findOne(stateService.predicateFindByType(FlowStateEnum.BAT_DAU));					
		Predicate predicateProcess = processService.predicateFindAllByDonVi(coQuanQuanLyRepository.findOne(donViId), ProcessTypeEnum.XU_LY_DON);
		List<Process> listProcess = (List<Process>) processRepository.findAll(predicateProcess);
		
		//Vai tro tiep theo
		List<State> listState = new ArrayList<State>();
		Process process = null;
		for (Process processFromList : listProcess) {
			Predicate predicate = stateService.predicateFindAll(beginState.getId(), processFromList, transitionRepository);
			listState = ((List<State>) stateRepository.findAll(predicate));
			if (listState.size() > 0) {
				process = processFromList;
				break;
			}
		}
		
		Transition transition = null;
		
		if (listState.size() < 1) {
			return false;
		} else {
			for (State stateFromList : listState) {
				transition = transitionRepository.findOne(transitionService.predicatePrivileged(beginState, stateFromList, process));
				if (transition != null) {
					break;
				}	
			}					
			if (transition != null) {
				return transition.getProcess().getVaiTro().getLoaiVaiTro().equals(loaiVaiTro);
			}
		}
		
		return false;
	}

}
