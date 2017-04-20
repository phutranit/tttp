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
import vn.greenglobal.tttp.model.TaiLieuBangChung;
import vn.greenglobal.tttp.repository.TaiLieuBangChungRepository;
import vn.greenglobal.tttp.service.TaiLieuBangChungService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "TaiLieuBangChungs", description = "Tài liệu bằng chứng")
public class TaiLieuBangChungController extends BaseController<TaiLieuBangChung> {

	private static Log log = LogFactory.getLog(TaiLieuBangChungController.class);
	private static TaiLieuBangChungService taiLieuBangChungService = new TaiLieuBangChungService();

	@Autowired
	private TaiLieuBangChungRepository repo;

	public TaiLieuBangChungController(BaseRepository<TaiLieuBangChung, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/taiLieuBangChungs")
	@ApiOperation(value = "Lấy danh sách Tài liệu bằng chứng", position=1, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<TaiLieuBangChung> getList(Pageable pageable, PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach ThamQuyenGiaiQuyet");

		Page<TaiLieuBangChung> page = repo.findAll(taiLieuBangChungService.predicateFindAll(), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/taiLieuBangChungs")
	@ApiOperation(value = "Thêm mới Tài liệu bằng chứng", position=2, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Thêm mới Tài liệu bằng chứng thành công", response = TaiLieuBangChung.class),
			@ApiResponse(code = 201, message = "Thêm mới Tài liệu bằng chứng thành công", response = TaiLieuBangChung.class)})
	public ResponseEntity<Object> create(@RequestBody TaiLieuBangChung taiLieuBangChung,
			PersistentEntityResourceAssembler eass) {
		log.info("Tao moi ThamQuyenGiaiQuyet");
		
		if (StringUtils.isNotBlank(taiLieuBangChung.getTen()) && taiLieuBangChungService.checkExistsData(repo, taiLieuBangChung)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(), ApiErrorEnum.TEN_EXISTS.getText());
		}
		return Utils.doSave(repo, taiLieuBangChung, eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/taiLieuBangChungs/{id}")
	@ApiOperation(value = "Lấy Tài liệu bằng chứng theo Id", position=3, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy Tài liệu bằng chứng thành công", response = TaiLieuBangChung.class) })
	public ResponseEntity<PersistentEntityResource> getTaiLieuBangChung(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get ThamQuyenGiaiQuyet theo id: " + id);

		TaiLieuBangChung taiLieuBangChung = repo.findOne(taiLieuBangChungService.predicateFindOne(id));
		if (taiLieuBangChung == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(taiLieuBangChung), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/taiLieuBangChungs/{id}")
	@ApiOperation(value = "Cập nhật Tài liệu bằng chứng", position=4, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Cập nhật Tài liệu bằng chứng thành công", response = TaiLieuBangChung.class) })
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody TaiLieuBangChung taiLieuBangChung, PersistentEntityResourceAssembler eass) {
		log.info("Update ThamQuyenGiaiQuyet theo id: " + id);

		if (!taiLieuBangChungService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		taiLieuBangChung.setId(id);
		return Utils.doSave(repo, taiLieuBangChung, eass, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/taiLieuBangChungs/{id}")
	@ApiOperation(value = "Xoá Tài liệu bằng chứng", position=5, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 204, message = "Xoá Tài liệu bằng chứng thành công") })
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		log.info("Delete ThamQuyenGiaiQuyet theo id: " + id);

		TaiLieuBangChung taiLieuBangChung = taiLieuBangChungService.deleteTaiLieuBangChung(repo, id);
		if (taiLieuBangChung == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(taiLieuBangChung);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
