package vn.greenglobal.tttp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.service.SoTiepCongDanService;
import vn.greenglobal.tttp.util.Utils;

@RepositoryRestController
@Api(value = "soTiepCongDans", description = "Sổ tiếp công dân")
@RestController
public class SoTiepCongDanController extends BaseController<SoTiepCongDan> {

	private static Log log = LogFactory.getLog(SoTiepCongDanController.class);
	private static SoTiepCongDanService soTiepCongDanService = new SoTiepCongDanService();

	@Autowired
	private SoTiepCongDanRepository repo;

	public SoTiepCongDanController(BaseRepository<SoTiepCongDan, Long> repo) {
		superC(repo);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/soTiepCongDans")
	@ApiOperation(value = "Thêm mới Tiếp Dân Thường Xuyên", position=2, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Thêm mới Tiếp Dân thành công", response = SoTiepCongDan.class),
			@ApiResponse(code = 201, message = "Thêm mới Tiếp Dân thành công", response = SoTiepCongDan.class)})
	public ResponseEntity<Object> create(@RequestBody SoTiepCongDan soTiepCongDan,
			PersistentEntityResourceAssembler eass) {
		log.info("Tao moi SoTiepCongDan TX");
		
		if (soTiepCongDan.getHuongXuLy() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_REQUIRED.name(), ApiErrorEnum.TEN_REQUIRED.getText());
		}
		
		repo.save(soTiepCongDan);
		return new ResponseEntity<>(eass.toFullResource(soTiepCongDan), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans/{id}")
	@ApiOperation(value = "Lấy Tiếp Dân theo Id", position=3, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy Tiếp Dân thành công", response = SoTiepCongDan.class) })
	public ResponseEntity<PersistentEntityResource> getsoTiepCongDans(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get SoTiepCongDan theo id: " + id);

		SoTiepCongDan soTiepCongDan = repo.findOne(soTiepCongDanService.predicateFindOne(id));
		if (soTiepCongDan == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(soTiepCongDan), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/soTiepCongDans/{id}")
	@ApiOperation(value = "Cập nhật Tiếp Dân", position=4, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Cập nhật Tiếp Dân thành công", response = SoTiepCongDan.class) })
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody SoTiepCongDan soTiepCongDan,
			PersistentEntityResourceAssembler eass) {
		log.info("Update SoTiepCongDan theo id: " + id);
		soTiepCongDan.setId(id);
		
		if (soTiepCongDan.getHuongXuLy() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_REQUIRED.name(), ApiErrorEnum.TEN_REQUIRED.getText());
		}
		
		if (!soTiepCongDanService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(soTiepCongDan);
		return new ResponseEntity<>(eass.toFullResource(soTiepCongDan), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/soTiepCongDans/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		log.info("Delete SoTiepCongDan theo id: " + id);

		SoTiepCongDan soTiepCongDan = soTiepCongDanService.deleteSoTiepCongDan(repo, id);
		if (soTiepCongDan == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(soTiepCongDan);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
