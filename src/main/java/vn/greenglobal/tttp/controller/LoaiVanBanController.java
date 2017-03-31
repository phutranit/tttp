package vn.greenglobal.tttp.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.LoaiVanBan;

@RepositoryRestController
public class LoaiVanBanController extends BaseController<LoaiVanBan> {

	public LoaiVanBanController(BaseRepository<LoaiVanBan, Long> repo) {
		super(repo, LoaiVanBan.class);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loaivanbans/search/findByRsql")
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
	}
}
