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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.ChucVu;
import vn.greenglobal.tttp.model.VuViec;
import vn.greenglobal.tttp.repository.ChucVuRepository;
import vn.greenglobal.tttp.service.ChucVuService;
import vn.greenglobal.tttp.util.Utils;

@RepositoryRestController
public class ChucVuController extends BaseController<ChucVu> {

	private static Log log = LogFactory.getLog(ChucVuController.class);
	private static ChucVuService chucVuService = new ChucVuService();

	@Autowired
	private ChucVuRepository repo;

	public ChucVuController(BaseRepository<ChucVu, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/chucvus")
	public ResponseEntity<Object> create(@RequestBody ChucVu chucVu,
			PersistentEntityResourceAssembler eass) {
		log.info("Tao moi VuViec");
		
		if (chucVu.getTen() == null || "".equals(chucVu.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TEN_REQUIRED", "Trường tên không được để trống!");
		}
		
		if (chucVuService.checkExistsData(repo, chucVu.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TEN_EXISTS", "Tên đã tồn tại trong hệ thống!");
		}
		
		repo.save(chucVu);
		return new ResponseEntity<>(eass.toFullResource(chucVu), HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/chucvus")
	public @ResponseBody PagedResources<ChucVu> getList(Pageable pageable,
			@RequestParam(value = "ten", required = false) String ten,
			PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach VuViec");

		Page<ChucVu> page = repo.findAll(chucVuService.predicateFindAll(ten), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/chucvus/{id}")
	public ResponseEntity<PersistentEntityResource> getChucVu(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get VuViec theo id: " + id);

		ChucVu chucVu = repo.findOne(chucVuService.predicateFindOne(id));
		if (chucVu == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(chucVu), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/chucvus/{id}")
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody ChucVu chucVu,
			PersistentEntityResourceAssembler eass) {
		log.info("Update VuViec theo id: " + id);
		
		if (chucVu.getTen() == null || "".equals(chucVu.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TEN_REQUIRED", "Trường tên không được để trống!");
		}
		
		if (chucVuService.checkExistsData(repo, chucVu.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TEN_EXISTS", "Tên đã tồn tại trong hệ thống!");
		}
		
		if (!chucVuService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, "DATA_NOT_FOUND", "Dữ liệu này không tồn tại trong hệ thống!");
		}
		
		chucVu.setId(id);
		repo.save(chucVu);
		return new ResponseEntity<>(eass.toFullResource(chucVu), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/chucvus/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		log.info("Delete VuViec theo id: " + id);

		ChucVu chucVu = chucVuService.deleteChucVu(repo, id);
		if (chucVu == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, "DATA_NOT_FOUND", "Dữ liệu này không tồn tại trong hệ thống!");
		}
		repo.save(chucVu);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
