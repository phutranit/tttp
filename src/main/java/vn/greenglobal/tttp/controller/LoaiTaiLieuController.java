package vn.greenglobal.tttp.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.LoaiTaiLieu;
import vn.greenglobal.tttp.repository.LoaiTaiLieuRepository;
import vn.greenglobal.tttp.service.LoaiTaiLieuService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "loaiTaiLieus", description = "Loại Tài Liệu")
public class LoaiTaiLieuController extends BaseController<LoaiTaiLieu> {

	private static Log log = LogFactory.getLog(LoaiTaiLieuController.class);
	private static LoaiTaiLieuService loaiTaiLieuQuyetService = new LoaiTaiLieuService();

	@Autowired
	private LoaiTaiLieuRepository repo;

	public LoaiTaiLieuController(BaseRepository<LoaiTaiLieu, Long> repo) {
		superC(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/loaiTaiLieus")
	@ApiOperation(value = "Lấy danh sách Loại Tài Liệu", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<LoaiTaiLieu> getList(Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa, PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach LoaiTaiLieu");

		Page<LoaiTaiLieu> page = repo.findAll(loaiTaiLieuQuyetService.predicateFindAll(tuKhoa), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/loaiTaiLieus")
	@ApiOperation(value = "Thêm mới Loại Tài Liệu", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Loại Tài Liệu thành công", response = LoaiTaiLieu.class),
			@ApiResponse(code = 201, message = "Thêm mới Loại Tài Liệu thành công", response = LoaiTaiLieu.class) })
	public ResponseEntity<Object> create(@RequestBody LoaiTaiLieu loaiTaiLieu, PersistentEntityResourceAssembler eass) {
		log.info("Tao moi LoaiTaiLieu");

		if (StringUtils.isNotBlank(loaiTaiLieu.getTen())
				&& loaiTaiLieuQuyetService.checkExistsData(repo, loaiTaiLieu)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
					ApiErrorEnum.TEN_EXISTS.getText());
		}
		return Utils.doSave(repo, loaiTaiLieu, eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loaiTaiLieus/{id}")
	@ApiOperation(value = "Lấy Loại Tài Liệu theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Loại Tài Liệu thành công", response = LoaiTaiLieu.class) })
	public ResponseEntity<PersistentEntityResource> getLoaiTaiLieu(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get LoaiTaiLieu theo id: " + id);

		LoaiTaiLieu loaiTaiLieu = repo.findOne(loaiTaiLieuQuyetService.predicateFindOne(id));
		if (loaiTaiLieu == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(loaiTaiLieu), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/loaiTaiLieus/{id}")
	@ApiOperation(value = "Cập nhật Loại Tài Liệu", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Loại Tài Liệu thành công", response = LoaiTaiLieu.class) })
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody LoaiTaiLieu loaiTaiLieu, PersistentEntityResourceAssembler eass) {
		log.info("Update LoaiTaiLieu theo id: " + id);

		loaiTaiLieu.setId(id);

		if (StringUtils.isNotBlank(loaiTaiLieu.getTen())
				&& loaiTaiLieuQuyetService.checkExistsData(repo, loaiTaiLieu)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
					ApiErrorEnum.TEN_EXISTS.getText());
		}

		if (!loaiTaiLieuQuyetService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		return Utils.doSave(repo, loaiTaiLieu, eass, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/loaiTaiLieus/{id}")
	@ApiOperation(value = "Xoá Loại Tài Liệu", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Loại Tài Liệu thành công") })
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		log.info("Delete LoaiTaiLieu theo id: " + id);

		LoaiTaiLieu loaiTaiLieu = loaiTaiLieuQuyetService.deleteLoaiTaiLieu(repo, id);
		if (loaiTaiLieu == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(loaiTaiLieu);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
