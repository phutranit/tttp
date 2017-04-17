package vn.greenglobal.tttp.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.dsl.BooleanExpression;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.CapCoQuanQuanLy;
import vn.greenglobal.tttp.model.LoaiTaiLieu;
import vn.greenglobal.tttp.model.QVaiTro;
import vn.greenglobal.tttp.model.VaiTro;
import vn.greenglobal.tttp.repository.VaiTroRepository;
import vn.greenglobal.tttp.service.VaiTroService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "vaiTros", description = "Vai trò")
public class VaiTroController extends BaseController<VaiTro>{

	private static Log log = LogFactory.getLog(VaiTroController.class);
			
	@Autowired
	VaiTroRepository repo;
	
	@Autowired
	VaiTroService vaiTroService;
	
	public VaiTroController(BaseRepository<VaiTro, ?> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/vaiTros")
	@ApiOperation(value = "Lấy danh sách vai trò", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedResources<CapCoQuanQuanLy> getList(Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach VaiTro");
		BooleanExpression predAll = QVaiTro.vaiTro.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QVaiTro.vaiTro.ten.containsIgnoreCase(tuKhoa));
		}
		Page<VaiTro> page = repo.findAll(predAll, pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/v1/vaiTros")
	@ApiOperation(value = "Thêm mới vai trò", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Vai Trò thành công", response = LoaiTaiLieu.class),
			@ApiResponse(code = 201, message = "Thêm mới Vai Trò thành công", response = LoaiTaiLieu.class) })
	public ResponseEntity<Object> create(@RequestBody VaiTro vaiTro, PersistentEntityResourceAssembler eass) {
		log.info("Tao moi VaiTro");

		if (StringUtils.isNotBlank(vaiTro.getTen())
				&& vaiTroService.isExists(repo, vaiTro)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
					ApiErrorEnum.TEN_EXISTS.getText());
		}
		return Utils.doSave(repo, vaiTro, eass, HttpStatus.CREATED);
	}
}
