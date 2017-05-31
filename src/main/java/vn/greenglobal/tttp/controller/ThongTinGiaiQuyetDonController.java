package vn.greenglobal.tttp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.repository.ThongTinGiaiQuyetDonRepository;
import vn.greenglobal.tttp.service.ThongTinGiaiQuyetDonService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "thongTinGiaiQuyetDons", description = "Thông tin giải quyết đơn")
public class ThongTinGiaiQuyetDonController extends TttpController<ThongTinGiaiQuyetDon> {

	@Autowired
	private ThongTinGiaiQuyetDonRepository repo;

	@Autowired
	private ThongTinGiaiQuyetDonService thongTinGiaiQuyetDonService;

	public ThongTinGiaiQuyetDonController(BaseRepository<ThongTinGiaiQuyetDon, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/thongTinGiaiQuyetDons/{id}")
	@ApiOperation(value = "Cập nhật thông tin Giải quyết đơn", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật thông tin Giải quyết đơn thành công", response = ThongTinGiaiQuyetDon.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody ThongTinGiaiQuyetDon giaiQuyetDon, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_SUA) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		giaiQuyetDon.setId(id);
		if (!thongTinGiaiQuyetDonService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		return Utils.doSave(repo, giaiQuyetDon,
				new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
				HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongTinGiaiQuyetDons/{id}")
	@ApiOperation(value = "Lấy Thông tin giải quyết đơn theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Thông tin giải quyết đơn thành công", response = ThongTinGiaiQuyetDon.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_XEM) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		ThongTinGiaiQuyetDon thongTinGiaiQuyetDon = repo.findOne(thongTinGiaiQuyetDonService.predicateFindOne(id));
		if (thongTinGiaiQuyetDon == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(thongTinGiaiQuyetDon), HttpStatus.OK);
	}
}
