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
import vn.greenglobal.tttp.model.VuViec;
import vn.greenglobal.tttp.repository.VuViecRepository;
import vn.greenglobal.tttp.service.VuViecService;
import vn.greenglobal.tttp.util.Utils;

@RepositoryRestController
@Api(value = "vuViecs", description = "Vụ Việc")
@RestController
public class VuViecController extends BaseController<VuViec> {

	private static Log log = LogFactory.getLog(VuViecController.class);
	private static VuViecService vuViecService = new VuViecService();

	@Autowired
	private VuViecRepository repo;

	public VuViecController(BaseRepository<VuViec, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/vuViecs")
	@ApiOperation(value = "Thêm mới Vụ Việc", position=2, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Thêm mới Vụ Việc thành công", response = VuViec.class),
			@ApiResponse(code = 201, message = "Thêm mới Vụ Việc thành công", response = VuViec.class)})
	public ResponseEntity<Object> create(@RequestBody VuViec VuViec,
			PersistentEntityResourceAssembler eass) {
		log.info("Tao moi VuViec");
		
		if (VuViec.getTen() == null || "".equals(VuViec.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_REQUIRED.name(), ApiErrorEnum.TEN_REQUIRED.getText());
		}
		
		if (vuViecService.checkExistsData(repo, VuViec)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(), ApiErrorEnum.TEN_EXISTS.getText());
		}
		
		repo.save(VuViec);
		return new ResponseEntity<>(eass.toFullResource(VuViec), HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/vuViecs")
	@ApiOperation(value = "Lấy danh sách Vụ Việc", position=1, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<VuViec> getList(Pageable pageable,
			@RequestParam(value = "ten", required = false) String ten,
			PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach VuViec");

		Page<VuViec> page = repo.findAll(vuViecService.predicateFindAll(ten), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/vuViecs/{id}")
	@ApiOperation(value = "Lấy Vụ Việc theo Id", position=3, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy Vụ Việc thành công", response = VuViec.class) })
	public ResponseEntity<PersistentEntityResource> getVuViec(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get VuViec theo id: " + id);

		VuViec VuViec = repo.findOne(vuViecService.predicateFindOne(id));
		if (VuViec == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(VuViec), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/vuViecs/{id}")
	@ApiOperation(value = "Cập nhật Vụ Việc", position=4, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Cập nhật Vụ Việc thành công", response = VuViec.class) })
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody VuViec vuViec,
			PersistentEntityResourceAssembler eass) {
		log.info("Update VuViec theo id: " + id);
		vuViec.setId(id);
		if (vuViec.getTen() == null || "".equals(vuViec.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_REQUIRED.name(), ApiErrorEnum.TEN_REQUIRED.getText());
		}
		
		if (vuViecService.checkExistsData(repo, vuViec)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(), ApiErrorEnum.TEN_EXISTS.getText());
		}
		
		if (!vuViecService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(vuViec);
		return new ResponseEntity<>(eass.toFullResource(vuViec), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/vuViecs/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		log.info("Delete VuViec theo id: " + id);

		VuViec vuViec = vuViecService.deleteVuViec(repo, id);
		if (vuViec == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(vuViec);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
