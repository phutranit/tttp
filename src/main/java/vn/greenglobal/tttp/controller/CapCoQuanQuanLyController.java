package vn.greenglobal.tttp.controller;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RequestBody;
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
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.model.CapCoQuanQuanLy;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.repository.CapCoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.service.CapCoQuanQuanLyService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "capCoQuanQuanLys", description = "Cấp Cơ Quan Quản Lý")
public class CapCoQuanQuanLyController extends TttpController<CapCoQuanQuanLy> {

	@Autowired
	private CapCoQuanQuanLyRepository repo;

	@Autowired
	private CapCoQuanQuanLyService capCoQuanQuanLyService;

	@Autowired
	private CoQuanQuanLyRepository repoCoQuanQuanLy;
	
	@Autowired
	private ThamSoRepository thamSoRepository;
	
	@Autowired
	private ThamSoService thamSoService;

	public CapCoQuanQuanLyController(BaseRepository<CapCoQuanQuanLy, ?> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/capCoQuanQuanLys")
	@ApiOperation(value = "Lấy danh sách Cấp Cơ Quan Quản Lý", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "cha", required = false) Long cha, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CAPCOQUANQUANLY_LIETKE) == null
				&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CAPCOQUANQUANLY_XEM) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		Page<CapCoQuanQuanLy> page = repo.findAll(capCoQuanQuanLyService.predicateFindAll(tuKhoa, cha), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/capCoQuanQuanLys/capCoQuanDaGiaiQuyets")
	@ApiOperation(value = "Lấy danh sách Cấp Cơ Quan Quản Lý", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getCapCoQuanDaGiaiQuyets(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, PersistentEntityResourceAssembler eass) {
		
		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CAPCOQUANQUANLY_XEM) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		Page<CapCoQuanQuanLy> page = null;
		ThamSo thamSoOne = thamSoRepository.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
		ThamSo thamSoTwo = thamSoRepository.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
		ThamSo thamSoThree = thamSoRepository.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
		ThamSo thamSoFour = thamSoRepository.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
		if (thamSoOne != null && thamSoTwo != null && thamSoThree != null && thamSoFour != null) {
			page = repo.findAll(capCoQuanQuanLyService.predicateFindCapCoQuanDaGiaiQuyet(Long.valueOf(thamSoOne.getGiaTri().toString()),
							Long.valueOf(thamSoTwo.getGiaTri().toString()), Long.valueOf(thamSoThree.getGiaTri().toString()),
							Long.valueOf(thamSoFour.getGiaTri().toString())),pageable);
		}

		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/capCoQuanQuanLys")
	@ApiOperation(value = "Thêm mới Cấp Cơ Quan Quản Lý", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Cấp Cơ Quan Quản Lý thành công", response = CapCoQuanQuanLy.class),
			@ApiResponse(code = 201, message = "Thêm mới Cấp Cơ Quan Quản Lý thành công", response = CapCoQuanQuanLy.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody CapCoQuanQuanLy capCoQuanQuanLy, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CAPCOQUANQUANLY_THEM) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		if (StringUtils.isNotBlank(capCoQuanQuanLy.getTen())
				&& capCoQuanQuanLyService.checkExistsData(repo, capCoQuanQuanLy)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
					ApiErrorEnum.TEN_EXISTS.getText());
		}

		return Utils.doSave(repo, capCoQuanQuanLy,
				Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
				HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/capCoQuanQuanLys/{id}")
	@ApiOperation(value = "Lấy Cấp Cơ Quan Quản Lý theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Cấp Cơ Quan Quản Lý thành công", response = CapCoQuanQuanLy.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CAPCOQUANQUANLY_XEM) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		CapCoQuanQuanLy capCoQuanQuanLy = repo.findOne(capCoQuanQuanLyService.predicateFindOne(id));
		if (capCoQuanQuanLy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(eass.toFullResource(capCoQuanQuanLy), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/capCoQuanQuanLys/{id}")
	@ApiOperation(value = "Cập nhật Cấp Cơ Quan Quản Lý", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Cấp Cơ Quan Quản Lý thành công", response = CapCoQuanQuanLy.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody CapCoQuanQuanLy capCoQuanQuanLy, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CAPCOQUANQUANLY_SUA) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		capCoQuanQuanLy.setId(id);
		if (StringUtils.isNotBlank(capCoQuanQuanLy.getTen())
				&& capCoQuanQuanLyService.checkExistsData(repo, capCoQuanQuanLy)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
					ApiErrorEnum.TEN_EXISTS.getText());
		}

		if (!capCoQuanQuanLyService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		return Utils.doSave(repo, capCoQuanQuanLy,
				Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/capCoQuanQuanLys/{id}")
	@ApiOperation(value = "Xoá Cấp Cơ Quan Quản Lý", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Cấp Cơ Quan Quản Lý thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CAPCOQUANQUANLY_XOA) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		if (capCoQuanQuanLyService.checkUsedData(repo, repoCoQuanQuanLy, id)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_USED.name(),
					ApiErrorEnum.DATA_USED.getText());
		}

		CapCoQuanQuanLy capCoQuanQuanLy = capCoQuanQuanLyService.delete(repo, id);
		if (capCoQuanQuanLy == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		Utils.save(repo, capCoQuanQuanLy,
				Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
