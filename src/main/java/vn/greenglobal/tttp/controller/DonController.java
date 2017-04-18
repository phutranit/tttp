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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.ThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.util.Utils;

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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/dons")
	@ApiOperation(value = "Lấy danh sách Đơn", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy dữ liệu đơn thành công", response = Don.class)})
	public @ResponseBody PagedResources<Don> getList(Pageable pageable,
			@RequestParam(value = "maDon", required = false) String maDon,
			@RequestParam(value = "tenNguoiDungDon", required = false) String tenNguoiDungDon,
			@RequestParam(value = "cmndHoChieu", required = false) String cmndHoChieu,
			@RequestParam(value = "nguonDon", required = false) String nguonDon,
			@RequestParam(value = "phanLoaiDon", required = false) String phanLoaiDon,
			@RequestParam(value = "tiepNhanTuNgay", required = false) String tiepNhanTuNgay,
			@RequestParam(value = "tiepNhanDenNgay", required = false) String tiepNhanDenNgay,
			@RequestParam(value = "hanGiaiQuyetTuNgay", required = false) String hanGiaiQuyetTuNgay,
			@RequestParam(value = "hanGiaiQuyetDenNgay", required = false) String hanGiaiQuyetDenNgay,
			@RequestParam(value = "trinhTrangXuLy", required = false) String trinhTrangXuLy,
			PersistentEntityResourceAssembler eass){
		
		log.info("Get list 'Don'");
		
		Page<Don> pageData =  repo.findAll(donService.predicateFindAll(maDon, tenNguoiDungDon, 
				nguonDon, phanLoaiDon, tiepNhanTuNgay, tiepNhanDenNgay, hanGiaiQuyetTuNgay, 
				hanGiaiQuyetDenNgay, trinhTrangXuLy),pageable);
		
		return assembler.toResource(pageData, (ResourceAssembler) eass);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/dons")
	@ApiOperation(value = "Thêm mới đơn", position = 2 , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Thêm mới đơn thành công", response = Don.class),
			@ApiResponse(code = 203, message = "Không có quyền thực hiện thêm mới đơn"),
			
	})
	public ResponseEntity<Object> create(@RequestBody Don don,
			PersistentEntityResourceAssembler eass) {
		
		log.info("Create 'Don'");
		
		
	}
}
