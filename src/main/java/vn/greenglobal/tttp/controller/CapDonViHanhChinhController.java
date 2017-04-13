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
import vn.greenglobal.tttp.model.CapDonViHanhChinh;
import vn.greenglobal.tttp.model.VuViec;
import vn.greenglobal.tttp.repository.CapDonViHanhChinhRepository;
import vn.greenglobal.tttp.service.CapDonViHanhChinhService;
import vn.greenglobal.tttp.util.ApiErrorEnum;
import vn.greenglobal.tttp.util.Utils;

@RepositoryRestController
public class CapDonViHanhChinhController extends BaseController<CapDonViHanhChinh> {

	private static Log log = LogFactory.getLog(CapDonViHanhChinhController.class);
	private static CapDonViHanhChinhService capDonViHanhChinhService = new CapDonViHanhChinhService();

	@Autowired
	private CapDonViHanhChinhRepository repo;

	public CapDonViHanhChinhController(BaseRepository<CapDonViHanhChinh, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/capdonvihanhchinhs")
	public ResponseEntity<Object> create(@RequestBody CapDonViHanhChinh capDonViHanhChinh,
			PersistentEntityResourceAssembler eass) {
		log.info("Tao moi VuViec");
		
		if (capDonViHanhChinh.getTen() == null || "".equals(capDonViHanhChinh.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_REQUIRED.name(), ApiErrorEnum.TEN_REQUIRED.getText());
		}
		
		if (capDonViHanhChinhService.checkExistsData(repo, capDonViHanhChinh.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(), ApiErrorEnum.TEN_EXISTS.getText());
		}
		
		repo.save(capDonViHanhChinh);
		return new ResponseEntity<>(eass.toFullResource(capDonViHanhChinh), HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/capdonvihanhchinhs")
	public @ResponseBody PagedResources<VuViec> getList(Pageable pageable,
			@RequestParam(value = "ten", required = false) String ten,
			PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach VuViec");

		Page<CapDonViHanhChinh> page = repo.findAll(capDonViHanhChinhService.predicateFindAll(ten), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/capdonvihanhchinhs/{id}")
	public ResponseEntity<PersistentEntityResource> getVuViec(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get VuViec theo id: " + id);

		CapDonViHanhChinh capDonViHanhChinh = repo.findOne(capDonViHanhChinhService.predicateFindOne(id));
		if (capDonViHanhChinh == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(capDonViHanhChinh), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/capdonvihanhchinhs/{id}")
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody CapDonViHanhChinh capDonViHanhChinh,
			PersistentEntityResourceAssembler eass) {
		log.info("Update VuViec theo id: " + id);
		
		if (capDonViHanhChinh.getTen() == null || "".equals(capDonViHanhChinh.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_REQUIRED.name(), ApiErrorEnum.TEN_REQUIRED.getText());
		}
		
		if (capDonViHanhChinhService.checkExistsData(repo, capDonViHanhChinh.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(), ApiErrorEnum.TEN_EXISTS.getText());
		}
		
		if (!capDonViHanhChinhService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		
		capDonViHanhChinh.setId(id);
		repo.save(capDonViHanhChinh);
		return new ResponseEntity<>(eass.toFullResource(capDonViHanhChinh), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/capdonvihanhchinhs/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		log.info("Delete VuViec theo id: " + id);

		CapDonViHanhChinh capDonViHanhChinh = capDonViHanhChinhService.deleteCapDonViHanhChinh(repo, id);
		if (capDonViHanhChinh == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(capDonViHanhChinh);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
