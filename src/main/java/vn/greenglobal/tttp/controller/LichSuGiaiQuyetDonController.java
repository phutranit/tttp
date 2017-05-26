package vn.greenglobal.tttp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.LichSuGiaiQuyetDon;
import vn.greenglobal.tttp.repository.LichSuGiaiQuyetDonRepository;
import vn.greenglobal.tttp.service.LichSuGiaiQuyetDonService;

@RestController
@RepositoryRestController
@Api(value = "lichSuGiaiQuyetDons", description = "Lịch sử giải quyết đơn")
public class LichSuGiaiQuyetDonController extends TttpController<LichSuGiaiQuyetDon> {

	@Autowired
	private LichSuGiaiQuyetDonRepository repo;

	@Autowired
	private LichSuGiaiQuyetDonService lichSuGiaiQuyetDonService;

	public LichSuGiaiQuyetDonController(BaseRepository<LichSuGiaiQuyetDon, Long> repo) {
		super(repo);
	}
	
}
