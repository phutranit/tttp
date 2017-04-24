package vn.greenglobal.tttp.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
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
import vn.greenglobal.tttp.model.CapCoQuanQuanLy;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.service.CoQuanQuanLyService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "coQuanQuanLys", description = "Cơ Quan Quản Lý")
public class CoQuanQuanLyController extends TttpController<CoQuanQuanLy> {

	@Autowired
	private CoQuanQuanLyRepository repo;

	@Autowired
	private CoQuanQuanLyService coQuanQuanLyService;

	public CoQuanQuanLyController(BaseRepository<CoQuanQuanLy, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys")
	@ApiOperation(value = "Lấy danh sách Cơ Quan Quản Lý", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<CapCoQuanQuanLy> getList(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "cha", required = false) Long cha,
			@RequestParam(value = "capCoQuanQuanLy", required = false) Long capCoQuanQuanLy,
			@RequestParam(value = "donViHanhChinh", required = false) Long donViHanhChinh,
			@RequestParam(value = "notCha", required = false, defaultValue = "false") Boolean notCha,
			PersistentEntityResourceAssembler eass) {

		Page<CoQuanQuanLy> page = repo.findAll(coQuanQuanLyService.predicateFindAll(tuKhoa, cha, capCoQuanQuanLy, donViHanhChinh, notCha),
				pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/coQuanQuanLys")
	@ApiOperation(value = "Thêm mới Cơ Quan Quản Lý", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Cơ Quan Quản Lý thành công", response = CoQuanQuanLy.class),
			@ApiResponse(code = 201, message = "Thêm mới Cơ Quan Quản Lý thành công", response = CoQuanQuanLy.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody CoQuanQuanLy coQuanQuanLy, PersistentEntityResourceAssembler eass) {

		if (StringUtils.isNotBlank(coQuanQuanLy.getTen()) && coQuanQuanLyService.checkExistsData(repo, coQuanQuanLy)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
					ApiErrorEnum.TEN_EXISTS.getText());
		}
		return Utils.doSave(repo, coQuanQuanLy, eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/{id}")
	@ApiOperation(value = "Lấy Cơ Quan Quản Lý theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Cơ Quan Quản Lý thành công", response = CoQuanQuanLy.class) })
	public ResponseEntity<PersistentEntityResource> getById(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {

		CoQuanQuanLy coQuanQuanLy = repo.findOne(coQuanQuanLyService.predicateFindOne(id));
		if (coQuanQuanLy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(coQuanQuanLy), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/coQuanQuanLys/{id}")
	@ApiOperation(value = "Cập nhật Cơ Quan Quản Lý", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Cơ Quan Quản Lý thành công", response = CoQuanQuanLy.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody CoQuanQuanLy coQuanQuanLy, PersistentEntityResourceAssembler eass) {

		coQuanQuanLy.setId(id);
		if (StringUtils.isNotBlank(coQuanQuanLy.getTen()) && coQuanQuanLyService.checkExistsData(repo, coQuanQuanLy)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
					ApiErrorEnum.TEN_EXISTS.getText());
		}

		if (!coQuanQuanLyService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		return Utils.doSave(repo, coQuanQuanLy, eass, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/coQuanQuanLys/{id}")
	@ApiOperation(value = "Xoá Cơ Quan Quản Lý", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Cơ Quan Quản Lý thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		CoQuanQuanLy coQuanQuanLy = coQuanQuanLyService.delete(repo, id);
		if (coQuanQuanLy == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(coQuanQuanLy);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
