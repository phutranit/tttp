package vn.greenglobal.tttp.controller;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.DanToc;
import vn.greenglobal.tttp.repository.DanTocRepository;

@RepositoryRestController
public class DanTocController extends BaseController<DanToc> {
	
	private static Log log = LogFactory.getLog(DanTocController.class);
	
	@Autowired
	private DanTocRepository repo;
	
	public DanTocController(BaseRepository<DanToc, Long> repo) {
		super(repo);
		// TODO Auto-generated constructor stub
	}

	
	/*@RequestMapping(method = RequestMethod.GET, value = "/dantocs/{id}")
	public ResponseEntity<DanToc> getDanTocById(@PathVariable("id") Long id) {
		DanToc danToc = this.repo.findOne(id);
		//final DanToc author = entityOrNotFoundException(this.repo.findOne(id));
		//final AuthorResource resource = authorResourceAssembler.toResource(author);
		return new ResponseEntity<>(danToc, HttpStatus.OK);
	}*/
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/dantocs/{id}")
	public ResponseEntity<DanToc> getDanTocById(@PathVariable("id") long id) {
		log.info("Unable to fetch DanToc with id="+id+", not found");
		DanToc cv = repo.getOne(id);
		if(cv==null){
			log.info("Unable to fetch DanToc with id="+id+", not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cv, HttpStatus.OK);
	}
	
	
	/*@RequestMapping(method = RequestMethod.GET, value = "/dantocs")
	public ResponseEntity<DanToc> getAllDanTocs() {
		System.out.println("@@@ " + this.repo.findAll());
		//return ResponseEntity.ok(this.repo.findAll());
		
		return new ResponseEntity<>(this.repo.findAll(), HttpStatus.OK);
	}*/
	
	@RequestMapping(method = RequestMethod.POST, value = "/createDanToc")
	public ResponseEntity<DanToc> getCreateDanToc(@RequestBody DanToc danToc) {
		this.repo.save(danToc);
		return new ResponseEntity<DanToc>(danToc, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PATCH, value = "/updateDantoc/{id}")
	public ResponseEntity<DanToc> getSaveDanToc(@PathVariable("id") Long id, @Valid @RequestBody DanToc danTocRequest) {
		DanToc danToc = this.repo.findOne(id);
		BeanUtils.copyProperties(danTocRequest, danToc);
		danToc.setId(id);
		return ResponseEntity.ok(this.repo.save(danToc));
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteDantoc/{id}")
	public ResponseEntity<DanToc> getDeleteDanToc(@PathVariable("id") Long id) {
		this.repo.delete(id);		
		return ResponseEntity.ok(null);
	}
	
	/*@RequestMapping(method = RequestMethod.GET, value = "/loaivanbans/search/findByRsql")
	public @ResponseBody PagedResources<?> findByRsql(@RequestParam(value = "rsql") String rsql,
			PersistentEntityResourceAssembler eass) {
		return super.findByRsql(rsql, eass);
	}*/
	
	/*@RequestMapping(method = RequestMethod.GET, value = "/loaivanbans/search/findByRsql")
	public @ResponseBody PagedResources<?> findByRsql(@RequestParam(value = "rsql") String rsql,
			PersistentEntityResourceAssembler eass) {
		return super.findByRsql(rsql, eass);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loaivanbans/search/countRsql")
	public @ResponseBody ResponseEntity<Resource<Long>> countRsql(@RequestParam(value = "rsql") String rsql) {
		return super.countRsql(rsql);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loaivanbans/search/existRsql")
	public @ResponseBody ResponseEntity<Resource<Boolean>> existRsql(@RequestParam(value = "rsql") String rsql) {
		return super.existRsql(rsql);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loaivanbans/search/pageRsql")
	public @ResponseBody PagedResources<?> pageRsql(@RequestParam(value = "rsql") String rsql, Pageable pageable,
			PersistentEntityResourceAssembler eass) {
		return super.pageRsql(rsql, pageable, eass);
	}*/
}
