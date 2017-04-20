package vn.greenglobal.tttp.controller;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.DanToc;
import vn.greenglobal.tttp.repository.DanTocRepository;
import vn.greenglobal.tttp.service.DanTocService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "danTocs", description = "Dân tộc")
public class DanTocController extends BaseController<DanToc> {

	@Autowired
	private DanTocRepository repo;

	@Autowired
	private DanTocService danTocService;

	public DanTocController(BaseRepository<DanToc, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/danTocs")
	@ApiOperation(value = "Lấy danh sách Dân tộc", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<DanToc> getList(Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "cha", required = false) Long cha, PersistentEntityResourceAssembler eass) {

		Page<DanToc> page = repo.findAll(danTocService.predicateFindAll(tuKhoa, cha), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/danTocs")
	@ApiOperation(value = "Thêm mới Dân tộc", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Thêm mới Dân tộc thành công", response = DanToc.class),
			@ApiResponse(code = 201, message = "Thêm mới Dân tộc thành công", response = DanToc.class) })
	public ResponseEntity<Object> create(@RequestBody DanToc danToc, PersistentEntityResourceAssembler eass) {

		if (StringUtils.isNotBlank(danToc.getTen()) && danTocService.checkExistsData(repo, danToc)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TEN_EXISTS", "Tên đã tồn tại trong hệ thống!");
		}
		return Utils.doSave(repo, danToc, eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/danTocs/{id}")
	@ApiOperation(value = "Lấy Dân tộc theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Dân tộc thành công", response = DanToc.class) })
	public ResponseEntity<PersistentEntityResource> getById(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {

		DanToc danToc = repo.findOne(danTocService.predicateFindOne(id));
		if (danToc == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(danToc), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/danTocs/{id}")
	@ApiOperation(value = "Cập nhật Dân tộc", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Dân tộc thành công", response = DanToc.class) })
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id, @RequestBody DanToc danToc,
			PersistentEntityResourceAssembler eass) {
		
		danToc.setId(id);
		if (StringUtils.isNotBlank(danToc.getTen()) && danTocService.checkExistsData(repo, danToc)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
					ApiErrorEnum.TEN_EXISTS.getText());
		}

		if (!danTocService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		return Utils.doSave(repo, danToc, eass, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/danTocs/{id}")
	@ApiOperation(value = "Xoá Dân tộc", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Dân tộc thành công") })
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {

		DanToc danToc = danTocService.delete(repo, id);
		if (danToc == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(danToc);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
