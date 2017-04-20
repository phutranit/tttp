package vn.greenglobal.tttp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.repository.NguoiDungRepository;

@RestController
@RepositoryRestController
@Api(value = "nguoiDungs", description = "Người dùng")
public class NguoiDungController extends TttpController<NguoiDung>{

	private static Log log = LogFactory.getLog(NguoiDungController.class);
	
	@Autowired
	NguoiDungRepository repo;
	
	public NguoiDungController(BaseRepository<NguoiDung, ?> repo) {
		super(repo);
	}

	/*@RequestMapping(method = RequestMethod.POST, value = "/login")
	public void login(@RequestBody NguoiDung nguoiDung, Errors errors){
		System.out.println("login");
		if(nguoiDung!=null){
			System.out.println(nguoiDung.getTenDangNhap());
		}
	}*/
}
