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
import vn.greenglobal.tttp.model.CoQuanToChucTiepDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.repository.CoQuanToChucTiepDanRepository;
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
	
	@Autowired
	private CoQuanToChucTiepDanRepository repoCoQuanToChucTiepDan;

	public SoTiepCongDanController(BaseRepository<SoTiepCongDan, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans")
	@ApiOperation(value = "Lấy danh sách Tiếp Công Dân", position = 1, produces = MediaType.APPLICATION_JSON_VALUE, response = SoTiepCongDan.class)
	public @ResponseBody PagedResources<SoTiepCongDan> getDanhSachTiepCongDanThuongXuyens(Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa, PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach Tiep Cong Dan");
		boolean thanhLapDon = false;
		
		Page<SoTiepCongDan> page = repo.findAll(soTiepCongDanService.predicateFindAllTCD(tuKhoa, thanhLapDon), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans/{id}")
	@ApiOperation(value = "Lấy Tiếp Công Dân theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy lượt Tiếp Công Dân thành công", response = SoTiepCongDan.class) })
	public ResponseEntity<PersistentEntityResource> getsoTiepCongDans(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get SoTiepCongDan theo id: " + id);
		SoTiepCongDan soTiepCongDan = repo.findOne(soTiepCongDanService.predicateFindOne(id));
		if (soTiepCongDan == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(soTiepCongDan), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/soTiepCongDanDinhKys")
	@ApiOperation(value = "Thêm mới Sổ Tiếp Công Dân Đình Kỳ", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Sổ Tiếp Công Dân Đình Kỳ thành công", response = SoTiepCongDan.class),
			@ApiResponse(code = 201, message = "Thêm mới Sổ Tiếp Công Dân Đình Kỳ thành công", response = SoTiepCongDan.class) })
	public ResponseEntity<Object> createSoTiepCongDanDinhKy(@RequestBody SoTiepCongDan soTiepCongDan,
			PersistentEntityResourceAssembler eass) {
		log.info("Tao moi SoTiepCongDan");

		for (CoQuanToChucTiepDan coQuanToChucTiepDan : soTiepCongDan.getCoQuanToChucTiepDans()) {
			repoCoQuanToChucTiepDan.save(coQuanToChucTiepDan);
		}
		
		return Utils.doSave(repo, soTiepCongDan, eass, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PATCH, value = "/soTiepCongDanDinhKys/{id}")
	@ApiOperation(value = "Cập nhật Sổ Tiếp Công Dân Đình Kỳ", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Sổ Tiếp Công Dân Đình Kỳ thành công", response = SoTiepCongDan.class) })
	public @ResponseBody ResponseEntity<Object> updateSoTiepCongDanDinhKy(@PathVariable("id") long id,
			@RequestBody SoTiepCongDan soTiepCongDan, PersistentEntityResourceAssembler eass) {
		log.info("Update SoTiepCongDan theo id: " + id);

		soTiepCongDan.setId(id);
		
		for (CoQuanToChucTiepDan coQuanToChucTiepDan : soTiepCongDan.getCoQuanToChucTiepDans()) {
			repoCoQuanToChucTiepDan.save(coQuanToChucTiepDan);
		}

		return Utils.doSave(repo, soTiepCongDan, eass, HttpStatus.OK);
	}

}
