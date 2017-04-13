package vn.greenglobal.tttp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
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
import vn.greenglobal.tttp.repository.ChucVuRepository;

@RestController
public class ChucVuController extends BaseController<ChucVu> {

	private static Log log = LogFactory.getLog(ChucVuController.class);
	
	@Autowired
	private ChucVuRepository repo;
	
	public ChucVuController(BaseRepository<ChucVu, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/chucvus")
	public ResponseEntity<ChucVu> create(@RequestBody ChucVu chucVu) {
		repo.save(chucVu);
		return new ResponseEntity<>(chucVu, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/chucvus/{id}")
	public ResponseEntity<ChucVu> getChucVu(@PathVariable("id") long id) {
		log.info("Unable to fetch ChucVu with id="+id+", not found");
		ChucVu cv = repo.getOne(id);
		if(cv==null){
			log.info("Unable to fetch ChucVu with id="+id+", not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cv, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/chucvus/{id}")
	public ResponseEntity<ChucVu> updatePost(@PathVariable("id") long id, @RequestBody ChucVu chucVu) {
		if(!repo.exists(id)){
			log.info("Unable to fetch ChucVu with id="+id+" for update, not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repo.save(chucVu);
		return new ResponseEntity<>(chucVu, HttpStatus.OK);
	}
	
	/*@RequestMapping(method = RequestMethod.PATCH, value = "/chucvus/{id}")
	public ResponseEntity<ChucVu> updatePatch(@PathVariable("id") long id, @RequestBody ChucVu chucVu) {
		ChucVu old = repo.getOne(id);
		if(old==null){
			log.info("Unable to fetch ChucVu with id="+id+" for update, not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repo.save(chucVu);
		return new ResponseEntity<>(chucVu, HttpStatus.OK);
	}*/
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/chucvus/{id}")
	public ResponseEntity<ChucVu> delete(@PathVariable("id") long id) {
		ChucVu cv = repo.findOne(id);
		if(cv==null){
			log.info("Unable to delete. ChucVu with id="+id+", not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repo.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/chucvus/search/findByRsql")
	public @ResponseBody PagedResources<?> findByRsql(@RequestParam(value = "rsql") String rsql,
			PersistentEntityResourceAssembler eass) {
		System.out.println(123 + "sdfsdgfdsg");
		return super.findByRsql(rsql, eass);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/chucvus/search/countRsql")
	public @ResponseBody ResponseEntity<Resource<Long>> countRsql(@RequestParam(value = "rsql") String rsql) {
		return super.countRsql(rsql);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/chucvus/search/existRsql")
	public @ResponseBody ResponseEntity<Resource<Boolean>> existRsql(@RequestParam(value = "rsql") String rsql) {
		return super.existRsql(rsql);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/chucvus/search/pageRsql")
	public @ResponseBody PagedResources<?> pageRsql(@RequestParam(value = "rsql") String rsql, Pageable pageable,
			PersistentEntityResourceAssembler eass) {
		return super.pageRsql(rsql, pageable, eass);
	}
}
