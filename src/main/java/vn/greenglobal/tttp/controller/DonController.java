package vn.greenglobal.tttp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.service.DonService;

@RestController
@RepositoryRestController
@Api(value = "dons", description = "Danh Sách Đơn")
public class DonController extends BaseController<Don> {

	private static Log log = LogFactory.getLog(DonController.class);
	private static DonService donService = new DonService();
	
	@Autowired
	private DonRepository repo;
	
	public DonController(BaseRepository<Don, Long> repo) {
		super(repo);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dons")
	@ApiOperation(value = "Lấy danh sách Đơn", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody PagedResources<Don> getList(Pageable pageable,
			PersistentEntityResourceAssembler eass){
		
		log.info("Get list 'Don'");
		Page<Don> pageData =  repo.findAll(donService.predicateFindAll(),pageable);
		return assembler.toResource(pageData, (ResourceAssembler) eass);
	}
}
