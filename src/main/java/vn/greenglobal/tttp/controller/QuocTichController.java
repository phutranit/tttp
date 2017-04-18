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
import vn.greenglobal.tttp.model.QuocTich;
import vn.greenglobal.tttp.repository.QuocTichRepository;
import vn.greenglobal.tttp.service.QuocTichService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "quocTichs", description = "Quốc Tịch")
public class QuocTichController extends BaseController<QuocTich> {

	private static Log log = LogFactory.getLog(QuocTichController.class);
	private static QuocTichService quocTichService = new QuocTichService();

	@Autowired
	private QuocTichRepository repo;

	public QuocTichController(BaseRepository<QuocTich, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/quocTichs")
	@ApiOperation(value = "Lấy danh sách Quốc Tịch", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<QuocTich> getList(Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa, PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach QuocTich");

		Page<QuocTich> page = repo.findAll(quocTichService.predicateFindAll(tuKhoa), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/quocTichs")
	@ApiOperation(value = "Thêm mới Quốc Tịch", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Quốc Tịch thành công", response = QuocTich.class),
			@ApiResponse(code = 201, message = "Thêm mới Quốc Tịch thành công", response = QuocTich.class) })
	public ResponseEntity<Object> create(@RequestBody QuocTich quocTich, PersistentEntityResourceAssembler eass) {
		log.info("Tao moi QuocTich");

		if (StringUtils.isNotBlank(quocTich.getTen()) && quocTichService.checkExistsData(repo, quocTich)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
					ApiErrorEnum.TEN_EXISTS.getText());
		}
		return Utils.doSave(repo, quocTich, eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/quocTichs/{id}")
	@ApiOperation(value = "Lấy Quốc Tịch theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Quốc Tịch thành công", response = QuocTich.class) })
	public ResponseEntity<PersistentEntityResource> getQuocTich(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get QuocTich theo id: " + id);

		QuocTich quocTich = repo.findOne(quocTichService.predicateFindOne(id));
		if (quocTich == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(quocTich), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/quocTichs/{id}")
	@ApiOperation(value = "Cập nhật Quốc Tịch", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Quốc Tịch thành công", response = QuocTich.class) })
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id, @RequestBody QuocTich quocTich,
			PersistentEntityResourceAssembler eass) {
		log.info("Update QuocTich theo id: " + id);

		quocTich.setId(id);

		if (StringUtils.isNotBlank(quocTich.getTen()) && quocTichService.checkExistsData(repo, quocTich)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
					ApiErrorEnum.TEN_EXISTS.getText());
		}

		if (!quocTichService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		return Utils.doSave(repo, quocTich, eass, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/quocTichs/{id}")
	@ApiOperation(value = "Xoá Quốc Tịch", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Quốc Tịch thành công") })
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		log.info("Delete QuocTich theo id: " + id);

		QuocTich quocTich = quocTichService.deleteQuocTich(repo, id);
		if (quocTich == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(quocTich);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
