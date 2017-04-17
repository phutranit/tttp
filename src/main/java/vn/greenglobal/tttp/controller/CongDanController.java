package vn.greenglobal.tttp.controller;

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
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.CongDan;
import vn.greenglobal.tttp.repository.CongDanRepository;
import vn.greenglobal.tttp.service.CongDanService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "congDans", description = "Công Dân")
public class CongDanController extends BaseController<CongDan> {

	private static Log log = LogFactory.getLog(CongDanController.class);
	private static CongDanService congDanService = new CongDanService();

	@Autowired
	private CongDanRepository repo;

	public CongDanController(BaseRepository<CongDan, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/congDans")
	@ApiOperation(value = "Lấy danh sách Công Dân", position=1, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<CongDan> getList(Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "tinhThanh", required = false) Long tinhThanh,
			@RequestParam(value = "quanHuyen", required = false) Long quanHuyen,
			@RequestParam(value = "phuongXa", required = false) Long phuongXa,
			@RequestParam(value = "toDanPho", required = false) Long toDanPho,
			PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach CongDan");

		Page<CongDan> page = repo.findAll(congDanService.predicateFindAll(tuKhoa, tinhThanh, quanHuyen, phuongXa, toDanPho), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/congDans")
	@ApiOperation(value = "Thêm mới Công Dân", position=2, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Thêm mới Công Dân thành công", response = CongDan.class),
			@ApiResponse(code = 201, message = "Thêm mới Công Dân thành công", response = CongDan.class)})
	public ResponseEntity<Object> create(@RequestBody CongDan congDan,
			PersistentEntityResourceAssembler eass) {
		log.info("Tao moi CongDan");
		return Utils.doSave(repo, congDan, eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/congDans/{id}")
	@ApiOperation(value = "Lấy Công Dân theo Id", position=3, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy Công Dân thành công", response = CongDan.class) })
	public ResponseEntity<PersistentEntityResource> getCongDan(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get CongDan theo id: " + id);

		CongDan congDan = repo.findOne(congDanService.predicateFindOne(id));
		if (congDan == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(congDan), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/congDans/{id}")
	@ApiOperation(value = "Cập nhật Công Dân", position=4, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Cập nhật Công Dân thành công", response = CongDan.class) })
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody CongDan congDan, PersistentEntityResourceAssembler eass) {
		log.info("Update CongDan theo id: " + id);


		if (!congDanService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		congDan.setId(id);
		return Utils.doSave(repo, congDan, eass, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/congDans/{id}")
	@ApiOperation(value = "Xoá Công Dân", position=5, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 204, message = "Xoá Công Dân thành công") })
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		log.info("Delete CongDan theo id: " + id);

		CongDan CongDan = congDanService.deleteCongDan(repo, id);
		if (CongDan == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(CongDan);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
