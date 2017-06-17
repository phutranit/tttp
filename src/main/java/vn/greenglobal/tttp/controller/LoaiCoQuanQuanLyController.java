package vn.greenglobal.tttp.controller;

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
import vn.greenglobal.tttp.model.LoaiCoQuanQuanLy;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.LoaiCoQuanQuanLyRepository;
import vn.greenglobal.tttp.service.LoaiCoQuanQuanLyService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "loaiCoQuanQuanLys", description = "Loại Cơ Quan Quản Lý")
public class LoaiCoQuanQuanLyController extends TttpController<LoaiCoQuanQuanLy> {

	@Autowired
	private LoaiCoQuanQuanLyRepository repo;

	@Autowired
	private LoaiCoQuanQuanLyService loaiCoQuanQuanLyService;

	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepository;

	public LoaiCoQuanQuanLyController(BaseRepository<LoaiCoQuanQuanLy, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/loaiCoQuanQuanLys")
	@ApiOperation(value = "Lấy danh sách Loại Cơ Quan Quản Lý", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "ten", required = false) String ten,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.LOAICOQUANQUANLY_LIETKE) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.LOAICOQUANQUANLY_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Page<LoaiCoQuanQuanLy> page = repo.findAll(loaiCoQuanQuanLyService.predicateFindAll(ten), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/loaiCoQuanQuanLys")
	@ApiOperation(value = "Thêm mới Loại Cơ Quan Quản Lý", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Loại Cơ Quan Quản Lý thành công", response = LoaiCoQuanQuanLy.class),
			@ApiResponse(code = 201, message = "Thêm mới Loại Cơ Quan Quản Lý thành công", response = LoaiCoQuanQuanLy.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody LoaiCoQuanQuanLy loaiCoQuanQuanLy, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.LOAICOQUANQUANLY_THEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (loaiCoQuanQuanLyService.checkExistsData(repo, loaiCoQuanQuanLy)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
						ApiErrorEnum.TEN_EXISTS.getText(), ApiErrorEnum.TEN_EXISTS.getText());
			}

			return Utils.doSave(repo, loaiCoQuanQuanLy,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loaiCoQuanQuanLys/{id}")
	@ApiOperation(value = "Lấy Loại Cơ Quan Quản Lý theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Loại Cơ Quan Quản Lý thành công", response = LoaiCoQuanQuanLy.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.LOAICOQUANQUANLY_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			LoaiCoQuanQuanLy loaiCoQuanQuanLy = repo.findOne(loaiCoQuanQuanLyService.predicateFindOne(id));
			if (loaiCoQuanQuanLy == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(eass.toFullResource(loaiCoQuanQuanLy), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/loaiCoQuanQuanLys/{id}")
	@ApiOperation(value = "Cập nhật Loại Cơ Quan Quản Lý", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Loại Cơ Quan Quản Lý thành công", response = LoaiCoQuanQuanLy.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody LoaiCoQuanQuanLy loaiCoQuanQuanLy, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.LOAICOQUANQUANLY_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			loaiCoQuanQuanLy.setId(id);
			if (loaiCoQuanQuanLyService.checkExistsData(repo, loaiCoQuanQuanLy)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
						ApiErrorEnum.TEN_EXISTS.getText(), ApiErrorEnum.TEN_EXISTS.getText());
			}

			if (!loaiCoQuanQuanLyService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			return Utils.doSave(repo, loaiCoQuanQuanLy,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/loaiCoQuanQuanLys/{id}")
	@ApiOperation(value = "Xoá Loại Cơ Quan Quản Lý", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Loại Cơ Quan Quản Lý thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.LOAICOQUANQUANLY_XOA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (loaiCoQuanQuanLyService.checkUsedData(coQuanQuanLyRepository, id)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_USED.name(),
						ApiErrorEnum.DATA_USED.getText(), ApiErrorEnum.DATA_USED.getText());
			}

			LoaiCoQuanQuanLy loaiCoQuanQuanLy = loaiCoQuanQuanLyService.delete(repo, id);
			if (loaiCoQuanQuanLy == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			Utils.save(repo, loaiCoQuanQuanLy,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
}
