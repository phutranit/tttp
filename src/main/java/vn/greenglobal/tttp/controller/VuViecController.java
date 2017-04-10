package vn.greenglobal.tttp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
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

import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.VuViec;
import vn.greenglobal.tttp.repository.VuViecRepository;

@RepositoryRestController
public class VuViecController extends BaseController<VuViec> {

	private static Log log = LogFactory.getLog(VuViecController.class);
	
	@Autowired
	private VuViecRepository repo;
	
	public VuViecController(BaseRepository<VuViec, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/vuviecs")
	public ResponseEntity<VuViec> create(@RequestBody VuViec vuViec) {
		repo.save(vuViec);
		return new ResponseEntity<>(vuViec, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/vuviecs/{id}")
	public ResponseEntity<VuViec> getVuViec(@PathVariable("id") long id) {
		log.info("Unable to fetch VuViec with id="+id+", not found");
		VuViec cv = repo.getOne(id);
		if(cv==null){
			log.info("Unable to fetch VuViec with id="+id+", not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cv, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PATCH, value = "/vuviecs/{id}")
	public ResponseEntity<VuViec> updatePost(@PathVariable("id") long id, @RequestBody VuViec VuViec) {
		if(!repo.exists(id)){
			log.info("Unable to fetch VuViec with id="+id+" for update, not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repo.save(VuViec);
		return new ResponseEntity<>(VuViec, HttpStatus.OK);
	}
	
	/*@RequestMapping(method = RequestMethod.PATCH, value = "/VuViecs/{id}")
	public ResponseEntity<VuViec> updatePatch(@PathVariable("id") long id, @RequestBody VuViec VuViec) {
		VuViec old = repo.getOne(id);
		if(old==null){
			log.info("Unable to fetch VuViec with id="+id+" for update, not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repo.save(VuViec);
		return new ResponseEntity<>(VuViec, HttpStatus.OK);
	}*/
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/vuviecs/{id}")
	public ResponseEntity<VuViec> delete(@PathVariable("id") long id) {
		VuViec cv = repo.findOne(id);
		if(cv==null){
			log.info("Unable to delete. VuViec with id="+id+", not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repo.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/vuviecs/search/findByRsql")
	public @ResponseBody PagedResources<?> findByRsql(@RequestParam(value = "rsql") String rsql,
			PersistentEntityResourceAssembler eass) {
		System.out.println(123 + "sdfsdgfdsg");
		return super.findByRsql(rsql, eass);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/vuviecs/search/countRsql")
	public @ResponseBody ResponseEntity<Resource<Long>> countRsql(@RequestParam(value = "rsql") String rsql) {
		return super.countRsql(rsql);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/vuviecs/search/existRsql")
	public @ResponseBody ResponseEntity<Resource<Boolean>> existRsql(@RequestParam(value = "rsql") String rsql) {
		return super.existRsql(rsql);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/vuviecs/search/pageRsql")
	public @ResponseBody PagedResources<?> pageRsql(@RequestParam(value = "rsql") String rsql, Pageable pageable,
			PersistentEntityResourceAssembler eass) {
		return super.pageRsql(rsql, pageable, eass);
	}
}
