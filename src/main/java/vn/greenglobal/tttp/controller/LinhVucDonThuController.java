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
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.repository.LinhVucDonThuRepository;
import vn.greenglobal.tttp.service.LinhVucDonThuService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "linhVucDonThus", description = "Lĩnh Vực Đơn Thư")
public class LinhVucDonThuController extends BaseController<LinhVucDonThu> {

	private static Log log = LogFactory.getLog(LinhVucDonThuController.class);
	private static LinhVucDonThuService linhVucDonThuService = new LinhVucDonThuService();

	@Autowired
	private LinhVucDonThuRepository repo;

	public LinhVucDonThuController(BaseRepository<LinhVucDonThu, Long> repo) {
		superC(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/linhVucDonThus")
	@ApiOperation(value = "Lấy danh sách Lĩnh Vực Đơn Thư", position=1, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<LinhVucDonThu> getList(Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "cha", required = false) Long cha, PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach LinhVucDonThu");

		Page<LinhVucDonThu> page = repo.findAll(linhVucDonThuService.predicateFindAll(tuKhoa, cha), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/linhVucDonThus")
	@ApiOperation(value = "Thêm mới Lĩnh Vực Đơn Thư", position=2, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Thêm mới Lĩnh Vực Đơn Thư thành công", response = LinhVucDonThu.class),
			@ApiResponse(code = 201, message = "Thêm mới Lĩnh Vực Đơn Thư thành công", response = LinhVucDonThu.class)})
	public ResponseEntity<Object> create(@RequestBody LinhVucDonThu LinhVucDonThu,
			PersistentEntityResourceAssembler eass) {
		log.info("Tao moi LinhVucDonThu");

		if (StringUtils.isNotBlank(LinhVucDonThu.getTen()) && linhVucDonThuService.checkExistsData(repo, LinhVucDonThu)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TEN_EXISTS", "Tên đã tồn tại trong hệ thống!");
		}
		return Utils.doSave(repo, LinhVucDonThu, eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/linhVucDonThus/{id}")
	@ApiOperation(value = "Lấy Lĩnh Vực Đơn Thư theo Id", position=3, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy Lĩnh Vực Đơn Thư thành công", response = LinhVucDonThu.class) })
	public ResponseEntity<PersistentEntityResource> getLinhVucDonThu(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get LinhVucDonThu theo id: " + id);

		LinhVucDonThu LinhVucDonThu = repo.findOne(linhVucDonThuService.predicateFindOne(id));
		if (LinhVucDonThu == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(LinhVucDonThu), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/linhVucDonThus/{id}")
	@ApiOperation(value = "Cập nhật Lĩnh Vực Đơn Thư", position=4, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Cập nhật Lĩnh Vực Đơn Thư thành công", response = LinhVucDonThu.class) })
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody LinhVucDonThu LinhVucDonThu, PersistentEntityResourceAssembler eass) {
		log.info("Update LinhVucDonThu theo id: " + id);
		LinhVucDonThu.setId(id);
		if (StringUtils.isNotBlank(LinhVucDonThu.getTen()) && linhVucDonThuService.checkExistsData(repo, LinhVucDonThu)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(), ApiErrorEnum.TEN_EXISTS.getText());
		}

		if (!linhVucDonThuService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		return Utils.doSave(repo, LinhVucDonThu, eass, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/linhVucDonThus/{id}")
	@ApiOperation(value = "Xoá Lĩnh Vực Đơn Thư", position=5, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 204, message = "Xoá Lĩnh Vực Đơn Thư thành công") })
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		log.info("Delete LinhVucDonThu theo id: " + id);

		LinhVucDonThu LinhVucDonThu = linhVucDonThuService.deleteLinhVucDonThu(repo, id);
		if (LinhVucDonThu == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(LinhVucDonThu);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
