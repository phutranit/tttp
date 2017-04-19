package vn.greenglobal.tttp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.XuLyDonService;

@RestController
@RepositoryRestController
@Api(value = "xuLyDons", description = "Xử lý đơn")
public class XuLyDonController extends BaseController<XuLyDon> {

	private static Log log = LogFactory.getLog(XuLyDonController.class);
	private static XuLyDonService xuLyDonService = new XuLyDonService();
	
	@Autowired
	private XuLyDonRepository repo;
	
	public XuLyDonController(BaseRepository<XuLyDon, Long> repo) {
		super(repo);
	}
}
