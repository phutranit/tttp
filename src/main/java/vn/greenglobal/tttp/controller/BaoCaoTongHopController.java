package vn.greenglobal.tttp.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.KyBaoCaoTongHopEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.enums.ThongKeBaoCaoLoaiKyEnum;
import vn.greenglobal.tttp.model.BaoCaoTongHop;
import vn.greenglobal.tttp.repository.BaoCaoTongHopRepository;
import vn.greenglobal.tttp.service.BaoCaoTongHopService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "baoCaoTongHops", description = "Báo cáo tổng hợp")
public class BaoCaoTongHopController extends TttpController<BaoCaoTongHop> {

	@Autowired
	private BaoCaoTongHopRepository repo;
	
	@Autowired
	private BaoCaoTongHopService service;
	
	public BaoCaoTongHopController(BaseRepository<BaoCaoTongHop, ?> repo) {
		super(repo);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/baoCaoTongHops")
	@ApiOperation(value = "Lấy danh sách báo cáo tổng hợp", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "year", required = false) Integer year,
			Pageable pageable,PersistentEntityResourceAssembler eass) {
		
		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			KyBaoCaoTongHopEnum loaiKyEnum = null;
			if(loaiKy != null) {
				loaiKyEnum = KyBaoCaoTongHopEnum.valueOf(loaiKy);
			}
			if (year == null || year == 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum != null && loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			Page<BaoCaoTongHop> page = repo.findAll(service.predicateFindAll(donViId, year, loaiKyEnum, quy, month), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/baoCaoTongHops/{id}")
	@ApiOperation(value = "Lấy báo cáo tổng hợp theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy báo cáo tổng hợp thành công", response = BaoCaoTongHop.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.BAOCAOTONGHOP_LIETKE) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			BaoCaoTongHop baoCaoTongHop = repo.findOne(service.predicateFindOne(id));
			if (baoCaoTongHop == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(eass.toFullResource(baoCaoTongHop), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

}
