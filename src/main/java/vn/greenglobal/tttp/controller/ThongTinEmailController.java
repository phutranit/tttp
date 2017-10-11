package vn.greenglobal.tttp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.model.ThongTinEmail;
import vn.greenglobal.tttp.repository.ThongTinEmailRepository;
import vn.greenglobal.tttp.service.ThongTinEmailService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "thongTinEmails", description = "Thông tin email")
public class ThongTinEmailController extends TttpController<ThongTinEmail> {

	@Autowired
	private ThongTinEmailRepository repo;
	
	@Autowired
	private ThongTinEmailService thongTinEmailService;

	public ThongTinEmailController(BaseRepository<ThongTinEmail, Long> repo) {
		super(repo);
	}

	
	@RequestMapping(method = RequestMethod.GET, value = "/thongTinEmails")
	@ApiOperation(value = "Lấy thông tin email", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy thông tin email thành công", response = ThongTinEmail.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THONGTINEMAIL_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			ThongTinEmail thongTinEmail = repo.findOne(1L);
			if (thongTinEmail == null) {
				thongTinEmail = new ThongTinEmail();
				thongTinEmail.setUsername("javagreenglobal@gmail.com");
				thongTinEmail.setPassword("javagreenglobal123");
				thongTinEmail.setEnableAuth(true);
				thongTinEmail.setEnableStarttls(true);
				thongTinEmail.setPort(587);
				thongTinEmail.setHost("smtp.gmail.com");
				thongTinEmail = thongTinEmailService.save(thongTinEmail, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			}

			return new ResponseEntity<>(eass.toFullResource(thongTinEmail), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/thongTinEmails")
	@ApiOperation(value = "Cập nhật thông tin email", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật thông tin email thành công", response = ThongTinEmail.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			@RequestBody ThongTinEmail thongTinEmail, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THONGTINEMAIL_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			ThongTinEmail thongTinEmailSave = repo.findOne(1L);
			if (thongTinEmailSave == null) {
				thongTinEmailSave = new ThongTinEmail();
			}
			thongTinEmailSave.setPassword(thongTinEmail.getPassword());
			thongTinEmailSave.setUsername(thongTinEmail.getUsername());
			thongTinEmailSave.setEnableAuth(thongTinEmail.isEnableAuth());
			thongTinEmailSave.setEnableStarttls(thongTinEmail.isEnableStarttls());
			thongTinEmailSave.setPort(thongTinEmail.getPort());
			thongTinEmailSave.setHost(thongTinEmail.getHost());
			return thongTinEmailService.doSave(thongTinEmailSave,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
}
