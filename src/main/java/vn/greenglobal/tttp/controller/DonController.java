package vn.greenglobal.tttp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.service.DonService;

@RestController
@RepositoryRestController
@Api(value = "dons", description = "Danh Sách Đơn")
public class DonController extends BaseController<Don> {

	private static Log log = LogFactory.getLog(DonController.class);
	private static DonService DonService = new DonService();
	
	public DonController(BaseRepository<Don, Long> repo) {
		super(repo);
		// TODO Auto-generated constructor stub
	}
}
