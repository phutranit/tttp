package vn.greenglobal.tttp.controller;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.ThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.repository.ThamQuyenGiaiQuyetRepository;
import vn.greenglobal.tttp.service.ThamQuyenGiaiQuyetService;
import vn.greenglobal.tttp.util.ApiError;

@RepositoryRestController
public class ThamQuyenGiaiQuyetController extends BaseController<ThamQuyenGiaiQuyet> {

	private static Log log = LogFactory.getLog(ThamQuyenGiaiQuyetController.class);
	private static ThamQuyenGiaiQuyetService thamQuyenGiaiQuyetService = new ThamQuyenGiaiQuyetService();

	@Autowired
	private ThamQuyenGiaiQuyetRepository repo;

	public ThamQuyenGiaiQuyetController(BaseRepository<ThamQuyenGiaiQuyet, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/thamquyengiaiquyets")
	public ResponseEntity<PersistentEntityResource> create(@Valid @RequestBody ThamQuyenGiaiQuyet thamQuyenGiaiQuyet,
			PersistentEntityResourceAssembler eass) {
		log.info("Tao moi ThamQuyenGiaiQuyet");
		repo.save(thamQuyenGiaiQuyet);
		return new ResponseEntity<>(eass.toFullResource(thamQuyenGiaiQuyet), HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/thamquyengiaiquyets")
	public @ResponseBody PagedResources<ThamQuyenGiaiQuyet> getList(Pageable pageable,
			@RequestParam(value = "ten", required = false) String ten,
			@RequestParam(value = "moTa", required = false) String moTa,
			@RequestParam(value = "cha", required = false) Long cha,
			PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach ThamQuyenGiaiQuyet");

		Page<ThamQuyenGiaiQuyet> page = repo.findAll(thamQuyenGiaiQuyetService.predicateFindAll(ten, moTa, cha), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thamquyengiaiquyets/{id}")
	public ResponseEntity<PersistentEntityResource> getThamQuyenGiaiQuyet(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get ThamQuyenGiaiQuyet theo id: " + id);

		ThamQuyenGiaiQuyet thamQuyenGiaiQuyet = repo.findOne(thamQuyenGiaiQuyetService.predicateFindOne(id));
		if (thamQuyenGiaiQuyet == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(thamQuyenGiaiQuyet), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/thamquyengiaiquyets/{id}")
	public @ResponseBody ResponseEntity<PersistentEntityResource> update(@PathVariable("id") long id,
			@RequestBody ThamQuyenGiaiQuyet thamQuyenGiaiQuyet,
			PersistentEntityResourceAssembler eass) {
		log.info("Update ThamQuyenGiaiQuyet theo id: " + id);

		if (!thamQuyenGiaiQuyetService.isExists(repo, id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		thamQuyenGiaiQuyet.setId(id);
		repo.save(thamQuyenGiaiQuyet);
		return new ResponseEntity<>(eass.toFullResource(thamQuyenGiaiQuyet), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/thamquyengiaiquyets/{id}")
	public ResponseEntity<ThamQuyenGiaiQuyet> delete(@PathVariable("id") Long id) {
		log.info("Delete ThamQuyenGiaiQuyet theo id: " + id);

		ThamQuyenGiaiQuyet thamQuyenGiaiQuyet = thamQuyenGiaiQuyetService.deleteThamQuyenGiaiQuyet(repo, id);
		if (thamQuyenGiaiQuyet == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repo.save(thamQuyenGiaiQuyet);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
