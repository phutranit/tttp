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
import vn.greenglobal.tttp.model.DonViHanhChinh;
import vn.greenglobal.tttp.repository.DonViHanhChinhRepository;
import vn.greenglobal.tttp.service.DonViHanhChinhService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "donViHanhChinhs", description = "Đơn Vị Hành Chính")
public class DonViHanhChinhController extends BaseController<DonViHanhChinh> {

	@Autowired
	private DonViHanhChinhRepository repo;

	@Autowired
	DonViHanhChinhService donViHanhChinhService;

	public DonViHanhChinhController(BaseRepository<DonViHanhChinh, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/donViHanhChinhs")
	@ApiOperation(value = "Lấy danh sách Đơn Vị Hành Chính", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<DonViHanhChinh> getList(Pageable pageable,
			@RequestParam(value = "ten", required = false) String ten,
			@RequestParam(value = "capDonViHanhChinh", required = false) Long capDonViHanhChinh,
			@RequestParam(value = "cha", required = false) Long cha, PersistentEntityResourceAssembler eass) {

		Page<DonViHanhChinh> page = repo.findAll(donViHanhChinhService.predicateFindAll(ten, cha, capDonViHanhChinh),
				pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/donViHanhChinhs")
	@ApiOperation(value = "Thêm mới Đơn Vị Hành Chính", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Đơn Vị Hành Chính thành công", response = DonViHanhChinh.class),
			@ApiResponse(code = 201, message = "Thêm mới Đơn Vị Hành Chính thành công", response = DonViHanhChinh.class) })
	public ResponseEntity<Object> create(@RequestBody DonViHanhChinh donViHanhChinh,
			PersistentEntityResourceAssembler eass) {
		return Utils.doSave(repo, donViHanhChinh, eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/donViHanhChinhs/{id}")
	@ApiOperation(value = "Lấy Đơn Vị Hành Chính theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Đơn Vị Hành Chính thành công", response = DonViHanhChinh.class) })
	public ResponseEntity<PersistentEntityResource> getById(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		
		DonViHanhChinh donViHanhChinh = repo.findOne(donViHanhChinhService.predicateFindOne(id));
		if (donViHanhChinh == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(eass.toFullResource(donViHanhChinh), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/donViHanhChinhs/{id}")
	@ApiOperation(value = "Cập nhật Đơn Vị Hành Chính", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Đơn Vị Hành Chính thành công", response = DonViHanhChinh.class) })
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody DonViHanhChinh donViHanhChinh, PersistentEntityResourceAssembler eass) {
		
		donViHanhChinh.setId(id);
		if (!donViHanhChinhService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		
		return Utils.doSave(repo, donViHanhChinh, eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/donViHanhChinhs/{id}")
	@ApiOperation(value = "Xoá Đơn Vị Hành Chính", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Đơn Vị Hành Chính thành công") })
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		
		DonViHanhChinh donViHanhChinh = donViHanhChinhService.delete(repo, id);
		if (donViHanhChinh == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		
		repo.save(donViHanhChinh);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
