package vn.greenglobal.tttp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.repository.NguoiDungRepository;

@RestController
@Api(value = "login", description = "")
public class LoginController {
	
	private static Log log = LogFactory.getLog(LoginController.class);
	
	@Autowired
	NguoiDungRepository repo;

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public void login(@RequestBody NguoiDung nguoiDung, Errors errors){
		System.out.println("login");
		if(nguoiDung!=null){
			System.out.println(nguoiDung.getTenDangNhap());
		}
	}
}
