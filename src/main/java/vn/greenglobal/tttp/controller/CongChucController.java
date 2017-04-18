package vn.greenglobal.tttp.controller;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PathVariable;
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
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.NguoiDungRepository;
import vn.greenglobal.tttp.util.Utils;
import vn.greenglobal.tttp.service.CongChucService;

@RestController
@RepositoryRestController
@Api(value = "congChucs", description = "Công chức")
public class CongChucController extends BaseController<CongChuc> {

	private static Log log = LogFactory.getLog(CongChucController.class);
	private static CongChucService congChucService = new CongChucService();

	@Autowired
	private CongChucRepository repo;
	
	@Autowired
	private NguoiDungRepository repoNguoiDung;

	@Autowired
	public EntityManager em;
	@Autowired
	public PlatformTransactionManager transactionManager;
	@Autowired
	public TransactionTemplate transactioner;
	
	public CongChucController(BaseRepository<CongChuc, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/congChucs")
	@ApiOperation(value = "Lấy danh sách Công Chức", position=1, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<CongChuc> getList(Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "cha", required = false) Long cha, PersistentEntityResourceAssembler eass) {
		log.info("Get danh sach CongChuc");
		System.out.println(em +":"+transactionManager+":"+transactioner);
		Page<CongChuc> page = repo.findAll(congChucService.predicateFindAll(tuKhoa), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST, value = "/congChucs")
	@ApiOperation(value = "Thêm mới Công Chức", position=2, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Thêm mới Công Chức thành công", response = CongChuc.class),
			@ApiResponse(code = 201, message = "Thêm mới Công Chức thành công", response = CongChuc.class)})
	public ResponseEntity<Object> create(@RequestBody CongChuc congChuc,
			PersistentEntityResourceAssembler eass) {
		log.info("Tao moi CongChuc");
		if (congChuc.getNguoiDung() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "THONGTINDANGNHAP_REQUIRED", "Thông tin đăng nhập không được để trống!");
		} else {
			if (congChuc.getNguoiDung().getMatKhau() == null || congChuc.getNguoiDung().getMatKhau().isEmpty()) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, "MATKHAU_REQUIRED", "Trường mật khẩu không được để trống!");
			}
			if (congChuc.getNguoiDung().getTenDangNhap() == null || congChuc.getNguoiDung().getTenDangNhap().isEmpty()) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TENDANGNHAP_REQUIRED", "Trường tên đăng nhập không được để trống!");
			}
		}
		
		if (congChuc.getHoVaTen() == null && congChuc.getHoVaTen().isEmpty()) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "HOVATEN_REQUIRED", "Trường mật khẩu không được để trống!");
		}
		
		if (congChuc.getNgaySinh() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "NGAYSINH_REQUIRED", "Trường ngày sinh không được để trống!");
		}
		
		if (congChuc.getDonVi() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "DONVI_REQUIRED", "Trường đơn vị không được để trống!");
		}
		
		if (congChuc.getChucVu() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "CHUCVU_REQUIRED", "Trường chức vụ không được để trống!");
		}
		
		if (congChuc.getEmail() != null && !Utils.isValidEmailAddress(congChuc.getEmail())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "EMAIL_INVALID", "Trường email không đúng định dạng!");
		}
		
		if (StringUtils.isNotBlank(congChuc.getNguoiDung().getTenDangNhap()) && congChucService.checkExistsData(repo, congChuc)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TENDANGNHAP_EXISTS", "Tên đăng nhập đã tồn tại trong hệ thống!");
		}
		
		return (ResponseEntity<Object>) transactioner.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus arg0) {
				congChuc.getNguoiDung().updatePassword(congChuc.getNguoiDung().getMatKhau());
				repoNguoiDung.save(congChuc.getNguoiDung());
				return Utils.doSave(repo, congChuc, eass, HttpStatus.CREATED);
			}
		});
	}

	@RequestMapping(method = RequestMethod.GET, value = "/congChucs/{id}")
	@ApiOperation(value = "Lấy Công Chức theo Id", position=3, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy Công Chức thành công", response = CongChuc.class) })
	public ResponseEntity<PersistentEntityResource> getCongChuc(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		log.info("Get CongChuc theo id: " + id);

		CongChuc congChuc = repo.findOne(congChucService.predicateFindOne(id));
		if (congChuc == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(congChuc), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PATCH, value = "/congChucs/{id}")
	@ApiOperation(value = "Cập nhật Công Chức", position=4, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Cập nhật Công Chức thành công", response = CongChuc.class) })
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody CongChuc congChuc, PersistentEntityResourceAssembler eass) {
		log.info("Update CongChuc theo id: " + id);
		congChuc.setId(id);
		if (congChuc.getNguoiDung() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "THONGTINDANGNHAP_REQUIRED", "Thông tin đăng nhập không được để trống!");
		} else {
			if (congChuc.getNguoiDung().getMatKhau() == null || congChuc.getNguoiDung().getMatKhau().isEmpty()) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, "MATKHAU_REQUIRED", "Trường mật khẩu không được để trống!");
			}
			if (congChuc.getNguoiDung().getTenDangNhap() == null || congChuc.getNguoiDung().getTenDangNhap().isEmpty()) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TENDANGNHAP_REQUIRED", "Trường tên đăng nhập không được để trống!");
			}
		}
		
		if (congChuc.getEmail() != null && !Utils.isValidEmailAddress(congChuc.getEmail())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "EMAIL_INVALID", "Trường email không đúng định dạng!");
		}
		
		if (StringUtils.isNotBlank(congChuc.getNguoiDung().getTenDangNhap()) && congChucService.checkExistsData(repo, congChuc)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TENDANGNHAP_EXISTS", "Tên đăng nhập đã tồn tại trong hệ thống!");
		}
		
		if (congChuc.getHoVaTen() == null && congChuc.getHoVaTen().isEmpty()) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "HOVATEN_REQUIRED", "Trường mật khẩu không được để trống!");
		}
		
		if (congChuc.getNgaySinh() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "NGAYSINH_REQUIRED", "Trường ngày sinh không được để trống!");
		}
		
		if (congChuc.getDonVi() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "DONVI_REQUIRED", "Trường đơn vị không được để trống!");
		}
		
		if (congChuc.getChucVu() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "CHUCVU_REQUIRED", "Trường chức vụ không được để trống!");
		}

		if (!congChucService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		return (ResponseEntity<Object>) transactioner.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus arg0) {
				congChuc.getNguoiDung().updatePassword(congChuc.getNguoiDung().getMatKhau());
				repoNguoiDung.save(congChuc.getNguoiDung());
				return Utils.doSave(repo, congChuc, eass, HttpStatus.CREATED);
			}
			
		});
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/congChucs/{id}")
	@ApiOperation(value = "Xoá Công Chức", position=5, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 204, message = "Xoá Công Chức thành công") })
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		log.info("Delete CongChuc theo id: " + id);

		CongChuc congChuc = congChucService.deleteCongChuc(repo, id);
		if (congChuc == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(congChuc);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
