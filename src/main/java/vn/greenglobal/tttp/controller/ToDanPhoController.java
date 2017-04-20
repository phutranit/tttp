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
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.ToDanPho;
import vn.greenglobal.tttp.repository.ToDanPhoRepository;
import vn.greenglobal.tttp.service.ToDanPhoService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "toDanPhos", description = "Tổ dân phố")
public class ToDanPhoController extends BaseController<ToDanPho> {

	private static Log log = LogFactory.getLog(ToDanPhoController.class);
	private static ToDanPhoService toDanPhoService = new ToDanPhoService();

	@Autowired
	private ToDanPhoRepository repo;

	public ToDanPhoController(BaseRepository<ToDanPho, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/toDanPhos")
	@ApiOperation(value = "Lấy danh sách Tổ Dân Phố", position=1, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<ToDanPho> getList(Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "donViHanhChinh", required = false) Long donViHanhChinh, PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach ThamQuyenGiaiQuyet");

		Page<ToDanPho> page = repo.findAll(toDanPhoService.predicateFindAll(tuKhoa, donViHanhChinh), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/toDanPhos")
	@ApiOperation(value = "Thêm mới Tổ Dân Phố", position=2, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Thêm mới Tổ dân phố thành công", response = ToDanPho.class),
			@ApiResponse(code = 201, message = "Thêm mới Tổ dân phố thành công", response = ToDanPho.class)})
	public ResponseEntity<Object> create(@RequestBody ToDanPho toDanPho,
			PersistentEntityResourceAssembler eass) {
		log.info("Tao moi ThamQuyenGiaiQuyet");
		
		if (StringUtils.isNotBlank(toDanPho.getTen()) && toDanPhoService.checkExistsData(repo, toDanPho)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(), ApiErrorEnum.TEN_EXISTS.getText());
		}
		return Utils.doSave(repo, toDanPho, eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/toDanPhos/{id}")
	@ApiOperation(value = "Lấy Tổ Dân Phố theo Id", position=3, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy Tổ Dân Phố thành công", response = ToDanPho.class) })
	public ResponseEntity<PersistentEntityResource> getToDanPho(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get ThamQuyenGiaiQuyet theo id: " + id);

		ToDanPho toDanPho = repo.findOne(toDanPhoService.predicateFindOne(id));
		if (toDanPho == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(toDanPho), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/toDanPhos/{id}")
	@ApiOperation(value = "Cập nhật Tổ Dân Phố", position=4, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Cập nhật Tổ Dân Phố thành công", response = ToDanPho.class) })
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody ToDanPho toDanPho, PersistentEntityResourceAssembler eass) {
		log.info("Update ThamQuyenGiaiQuyet theo id: " + id);

		if (!toDanPhoService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		toDanPho.setId(id);
		return Utils.doSave(repo, toDanPho, eass, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/toDanPhos/{id}")
	@ApiOperation(value = "Xoá Tổ Dân Phố", position=5, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 204, message = "Xoá Tổ Dân Phố thành công") })
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		log.info("Delete ThamQuyenGiaiQuyet theo id: " + id);

		ToDanPho toDanPho = toDanPhoService.deleteToDanPho(repo, id);
		if (toDanPho == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(toDanPho);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
