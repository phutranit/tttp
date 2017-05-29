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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.model.LichSuGiaiQuyetDon;
import vn.greenglobal.tttp.repository.LichSuGiaiQuyetDonRepository;
import vn.greenglobal.tttp.service.LichSuGiaiQuyetDonService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "lichSuGiaiQuyetDons", description = "Lịch sử giải quyết đơn")
public class LichSuGiaiQuyetDonController extends TttpController<LichSuGiaiQuyetDon> {

	@Autowired
	private LichSuGiaiQuyetDonRepository repo;

	@Autowired
	private LichSuGiaiQuyetDonService lichSuGiaiQuyetDonService;

	public LichSuGiaiQuyetDonController(BaseRepository<LichSuGiaiQuyetDon, Long> repo) {
		super(repo);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/lichSuGiaiQuyetDons")
	@ApiOperation(value = "Thêm mới Lịch sử giải quyết đơn", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Thêm mới Lịch sử giải quyết đơn thành công", response = LichSuGiaiQuyetDon.class),
			@ApiResponse(code = 201, message = "Thêm mới Lịch sử giải quyết đơn thành công", response = LichSuGiaiQuyetDon.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody LichSuGiaiQuyetDon lichSuGiaiQuyetDon, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		return Utils.doSave(repo, lichSuGiaiQuyetDon,
				new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
				HttpStatus.CREATED);
	}
}
