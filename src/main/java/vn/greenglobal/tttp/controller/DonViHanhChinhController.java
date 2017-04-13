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

import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.DonViHanhChinh;
import vn.greenglobal.tttp.repository.DonViHanhChinhRepository;
import vn.greenglobal.tttp.service.DonViHanhChinhService;
import vn.greenglobal.tttp.util.Utils;

@RepositoryRestController
public class DonViHanhChinhController extends BaseController<DonViHanhChinh> {

	private static Log log = LogFactory.getLog(DonViHanhChinhController.class);
	private static DonViHanhChinhService donViHanhChinhService = new DonViHanhChinhService();

	@Autowired
	private DonViHanhChinhRepository repo;

	public DonViHanhChinhController(BaseRepository<DonViHanhChinh, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/donvihanhchinhs")
	public ResponseEntity<Object> create(@RequestBody DonViHanhChinh donViHanhChinh,
			PersistentEntityResourceAssembler eass) {
		log.info("Tao moi DonViHanhChinh");
		
		if (donViHanhChinh.getTen() == null || "".equals(donViHanhChinh.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TEN_REQUIRED", "Trường tên không được để trống!");
		}
		
		if (donViHanhChinh.getCapDonViHanhChinh() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "CAPDONVIHANHCHINH_REQUIRED", "Trường cấp đơn vị hành chính không được để trống!");
		}
		
		if (donViHanhChinhService.checkExistsData(repo, donViHanhChinh.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TEN_EXISTS", "Tên đã tồn tại trong hệ thống!");
		}
		repo.save(donViHanhChinh);
		return new ResponseEntity<>(eass.toFullResource(donViHanhChinh), HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/donvihanhchinhs")
	public @ResponseBody PagedResources<DonViHanhChinh> getList(Pageable pageable,
			@RequestParam(value = "ten", required = false) String ten,
			@RequestParam(value = "capDonViHanhChinh", required = false) Long capDonViHanhChinh,
			@RequestParam(value = "cha", required = false) Long cha,
			PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach DonViHanhChinh");

		Page<DonViHanhChinh> page = repo.findAll(donViHanhChinhService.predicateFindAll(ten, cha, capDonViHanhChinh), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/donvihanhchinhs/{id}")
	public ResponseEntity<PersistentEntityResource> getDonViHanhChinh(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get DonViHanhChinh theo id: " + id);

		DonViHanhChinh donViHanhChinh = repo.findOne(donViHanhChinhService.predicateFindOne(id));
		if (donViHanhChinh == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(donViHanhChinh), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/donvihanhchinhs/{id}")
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody DonViHanhChinh donViHanhChinh,
			PersistentEntityResourceAssembler eass) {
		log.info("Update DonViHanhChinh theo id: " + id);
		
		if (donViHanhChinh.getTen() == null || "".equals(donViHanhChinh.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_REQUIRED.name(), ApiErrorEnum.TEN_REQUIRED.getText());
		}
		
		if (donViHanhChinh.getCapDonViHanhChinh() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "CAPDONVIHANHCHINH_REQUIRED", "Trường cấp đơn vị hành chính không được để trống!");
		}
		
		if (donViHanhChinhService.checkExistsData(repo, donViHanhChinh.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(), ApiErrorEnum.TEN_EXISTS.getText());
		}
		
		if (!donViHanhChinhService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		
		donViHanhChinh.setId(id);
		repo.save(donViHanhChinh);
		return new ResponseEntity<>(eass.toFullResource(donViHanhChinh), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/donvihanhchinhs/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		log.info("Delete DonViHanhChinh theo id: " + id);

		DonViHanhChinh donViHanhChinh = donViHanhChinhService.deleteDonViHanhChinh(repo, id);
		if (donViHanhChinh == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(donViHanhChinh);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
