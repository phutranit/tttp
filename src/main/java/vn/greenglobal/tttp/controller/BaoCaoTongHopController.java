package vn.greenglobal.tttp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.BaoCaoTongHop;
import vn.greenglobal.tttp.repository.BaoCaoTongHopRepository;

public class BaoCaoTongHopController extends TttpController<BaoCaoTongHop> {

	@Autowired
	private BaoCaoTongHopRepository baoCaoTongHopRepository;
	
	public BaoCaoTongHopController(BaseRepository<BaoCaoTongHop, ?> repo) {
		super(repo);
	}
	
	public Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "kyBaoCao", required = false) String kyBaoCao,
			@RequestParam(value = "namBaoCao", required = false) String namBaoCao) {
		
		
		
	}
	

}
