package vn.greenglobal.tttp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.TaiLieuVanThu;
import vn.greenglobal.tttp.repository.TaiLieuVanThuRepository;
import vn.greenglobal.tttp.service.TaiLieuVanThuService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "TaiLieuVanThus", description = "Tài liệu văn thư")
public class TaiLieuVanThuController extends TttpController<TaiLieuVanThu> {

	@Autowired
	private TaiLieuVanThuRepository repo;

	@Autowired
	private TaiLieuVanThuService taiLieuVanThuService;

	public TaiLieuVanThuController(BaseRepository<TaiLieuVanThu, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/taiLieuVanThus")
	@ApiOperation(value = "Lấy danh sách Tài liệu văn thư", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<TaiLieuVanThu> getList(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		Page<TaiLieuVanThu> page = repo.findAll(taiLieuVanThuService.predicateFindAll(), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/taiLieuVanThus")
	@ApiOperation(value = "Thêm mới Tài liệu văn thư", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Tài liệu văn thư thành công", response = TaiLieuVanThu.class),
			@ApiResponse(code = 201, message = "Thêm mới Tài liệu văn thư thành công", response = TaiLieuVanThu.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody TaiLieuVanThu taiLieuVanThu, PersistentEntityResourceAssembler eass) {

		return Utils.doSave(repo, taiLieuVanThu,
				new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
				HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/taiLieuVanThus/{id}")
	@ApiOperation(value = "Lấy Tài liệu văn thư theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Tài liệu văn thư thành công", response = TaiLieuVanThu.class) })
	public ResponseEntity<PersistentEntityResource> getById(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {

		TaiLieuVanThu taiLieuVanThu = repo.findOne(taiLieuVanThuService.predicateFindOne(id));
		if (taiLieuVanThu == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(eass.toFullResource(taiLieuVanThu), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/taiLieuVanThus/{id}")
	@ApiOperation(value = "Cập nhật Tài liệu văn thư", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Tài liệu văn thư thành công", response = TaiLieuVanThu.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody TaiLieuVanThu taiLieuVanThu, PersistentEntityResourceAssembler eass) {

		taiLieuVanThu.setId(id);
		if (!taiLieuVanThuService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		return Utils.doSave(repo, taiLieuVanThu,
				new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/taiLieuVanThus/{id}")
	@ApiOperation(value = "Xoá Tài liệu văn thư", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Tài liệu văn thư thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		TaiLieuVanThu taiLieuVanThu = taiLieuVanThuService.delete(repo, id);
		if (taiLieuVanThu == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		Utils.save(repo, taiLieuVanThu,
				new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
