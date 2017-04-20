package vn.greenglobal.tttp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.repository.NguoiDungRepository;

@RestController
@RepositoryRestController
@Api(value = "nguoiDungs", description = "Người dùng")
public class NguoiDungController extends TttpController<NguoiDung> {

	@Autowired
	NguoiDungRepository repo;

	public NguoiDungController(BaseRepository<NguoiDung, ?> repo) {
		super(repo);
	}
}
