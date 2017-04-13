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
import vn.greenglobal.tttp.model.ThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.repository.ThamQuyenGiaiQuyetRepository;
import vn.greenglobal.tttp.service.ThamQuyenGiaiQuyetService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "thamQuyenGiaiQuyets", description = "Thẩm Quyền Giải Quyết")
public class ThamQuyenGiaiQuyetController extends BaseController<ThamQuyenGiaiQuyet> {

	private static Log log = LogFactory.getLog(ThamQuyenGiaiQuyetController.class);
	private static ThamQuyenGiaiQuyetService thamQuyenGiaiQuyetService = new ThamQuyenGiaiQuyetService();

	@Autowired
	private ThamQuyenGiaiQuyetRepository repo;

	public ThamQuyenGiaiQuyetController(BaseRepository<ThamQuyenGiaiQuyet, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/thamQuyenGiaiQuyets")
	@ApiOperation(value = "Lấy danh sách ThamQuyenGiaiQuyet", position=1, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<ThamQuyenGiaiQuyet> getList(Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "cha", required = false) Long cha, PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach ThamQuyenGiaiQuyet");

		Page<ThamQuyenGiaiQuyet> page = repo.findAll(thamQuyenGiaiQuyetService.predicateFindAll(tuKhoa, cha), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/thamQuyenGiaiQuyets")
	@ApiOperation(value = "Thêm mới ThamQuyenGiaiQuyet", position=2, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Thêm mới ThamQuyenGiaiQuyet thành công", response = ThamQuyenGiaiQuyet.class),
			@ApiResponse(code = 201, message = "Thêm mới ThamQuyenGiaiQuyet thành công", response = ThamQuyenGiaiQuyet.class)})
	public ResponseEntity<Object> create(@RequestBody ThamQuyenGiaiQuyet thamQuyenGiaiQuyet,
			PersistentEntityResourceAssembler eass) {
		log.info("Tao moi ThamQuyenGiaiQuyet");

		if (StringUtils.isNotBlank(thamQuyenGiaiQuyet.getTen()) && thamQuyenGiaiQuyetService.checkExistsData(repo, thamQuyenGiaiQuyet.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TEN_EXISTS", "Tên đã tồn tại trong hệ thống!");
		}
		return Utils.doSave(repo, thamQuyenGiaiQuyet, eass, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Lấy ThamQuyenGiaiQuyet theo Id", position=3, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy ThamQuyenGiaiQuyet thành công", response = ThamQuyenGiaiQuyet.class) })
	@RequestMapping(method = RequestMethod.GET, value = "/thamQuyenGiaiQuyets/{id}")
	public ResponseEntity<PersistentEntityResource> getThamQuyenGiaiQuyet(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get ThamQuyenGiaiQuyet theo id: " + id);

		ThamQuyenGiaiQuyet thamQuyenGiaiQuyet = repo.findOne(thamQuyenGiaiQuyetService.predicateFindOne(id));
		if (thamQuyenGiaiQuyet == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(thamQuyenGiaiQuyet), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/thamQuyenGiaiQuyets/{id}")
	@ApiOperation(value = "Cập nhật ThamQuyenGiaiQuyet", position=4, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Cập nhật ThamQuyenGiaiQuyet thành công", response = ThamQuyenGiaiQuyet.class) })
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody ThamQuyenGiaiQuyet thamQuyenGiaiQuyet, PersistentEntityResourceAssembler eass) {
		log.info("Update ThamQuyenGiaiQuyet theo id: " + id);

		if (StringUtils.isNotBlank(thamQuyenGiaiQuyet.getTen()) && thamQuyenGiaiQuyetService.checkExistsData(repo, thamQuyenGiaiQuyet.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(), ApiErrorEnum.TEN_EXISTS.getText());
		}

		if (!thamQuyenGiaiQuyetService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		thamQuyenGiaiQuyet.setId(id);
		return Utils.doSave(repo, thamQuyenGiaiQuyet, eass, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/thamQuyenGiaiQuyets/{id}")
	@ApiOperation(value = "Xoá ThamQuyenGiaiQuyet", position=5, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 204, message = "Xoá ThamQuyenGiaiQuyet thành công") })
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		log.info("Delete ThamQuyenGiaiQuyet theo id: " + id);

		ThamQuyenGiaiQuyet thamQuyenGiaiQuyet = thamQuyenGiaiQuyetService.deleteThamQuyenGiaiQuyet(repo, id);
		if (thamQuyenGiaiQuyet == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(thamQuyenGiaiQuyet);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
