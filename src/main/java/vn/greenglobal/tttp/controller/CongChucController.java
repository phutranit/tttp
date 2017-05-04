package vn.greenglobal.tttp.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.NguoiDungRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.service.CongChucService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "congChucs", description = "Công chức")
public class CongChucController extends TttpController<CongChuc> {

	@Autowired
	private CongChucRepository repo;

	@Autowired
	private NguoiDungRepository repoNguoiDung;

	@Autowired
	private CongChucService congChucService;

	@Autowired
	private ThamSoRepository repoThamSo;

	@Autowired
	private ThamSoService thamSoService;

	public CongChucController(BaseRepository<CongChuc, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/congChucs")
	@ApiOperation(value = "Lấy danh sách Công Chức", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "cha", required = false) Long cha, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_LIETKE) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		Page<CongChuc> page = repo.findAll(congChucService.predicateFindAll(tuKhoa), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/lanhDaoTiepCongDans")
	@ApiOperation(value = "Lấy danh sách Lãnh đạo tiếp công dân", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListDanhSachLanhDaoTCD(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {
		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_LIETKE) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		CongChuc congChuc = repo.findOne(new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
		ThamSo thamSo = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
		CoQuanQuanLy donVi = null;
		if (congChuc != null && congChuc.getCoQuanQuanLy() != null) {
			if (thamSo != null && thamSo.getGiaTri().toString().equals(congChuc.getCoQuanQuanLy().getCapCoQuanQuanLy().getId())) {
				donVi = congChuc.getCoQuanQuanLy().getCha();
			} else {
				donVi = congChuc.getCoQuanQuanLy();
			}
		}
		Page<CongChuc> page = repo.findAll(congChucService.predicateFindLanhDaoTiepCongDan(donVi), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST, value = "/congChucs")
	@ApiOperation(value = "Thêm mới Công Chức", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Công Chức thành công", response = CongChuc.class),
			@ApiResponse(code = 201, message = "Thêm mới Công Chức thành công", response = CongChuc.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody CongChuc congChuc, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_THEM) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		if (congChuc.getNguoiDung() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "THONGTINDANGNHAP_REQUIRED",
					"Thông tin đăng nhập không được để trống!");
		} else {
			if (congChuc.getNguoiDung().getMatKhau() == null || congChuc.getNguoiDung().getMatKhau().isEmpty()) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, "MATKHAU_REQUIRED",
						"Trường mật khẩu không được để trống!");
			}
			if (congChuc.getNguoiDung().getTenDangNhap() == null
					|| congChuc.getNguoiDung().getTenDangNhap().isEmpty()) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TENDANGNHAP_REQUIRED",
						"Trường tên đăng nhập không được để trống!");
			}
		}

		if (congChuc.getHoVaTen() == null && congChuc.getHoVaTen().isEmpty()) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "HOVATEN_REQUIRED",
					"Trường Họ và tên không được để trống!");
		}

		if (congChuc.getNgaySinh() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "NGAYSINH_REQUIRED",
					"Trường ngày sinh không được để trống!");
		}

		if (congChuc.getCoQuanQuanLy() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "DONVI_REQUIRED",
					"Trường cơ quan quản lý không được để trống!");
		}

		if (congChuc.getChucVu() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "CHUCVU_REQUIRED",
					"Trường chức vụ không được để trống!");
		}

		if (congChuc.getEmail() != null && !Utils.isValidEmailAddress(congChuc.getEmail())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "EMAIL_INVALID", "Trường email không đúng định dạng!");
		}

		if (StringUtils.isNotBlank(congChuc.getNguoiDung().getTenDangNhap())
				&& congChucService.checkExistsData(repo, congChuc)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TENDANGNHAP_EXISTS",
					"Tên đăng nhập đã tồn tại trong hệ thống!");
		}

		return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus arg0) {
				congChuc.getNguoiDung().updatePassword(congChuc.getNguoiDung().getMatKhau());
				Utils.save(repoNguoiDung, congChuc.getNguoiDung(),
						new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
				return Utils.doSave(repo, congChuc,
						new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
						eass, HttpStatus.CREATED);
			}
		});
	}

	@RequestMapping(method = RequestMethod.GET, value = "/congChucs/{id}")
	@ApiOperation(value = "Lấy Công Chức theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Công Chức thành công", response = CongChuc.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_XEM) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		CongChuc congChuc = repo.findOne(congChucService.predicateFindOne(id));
		if (congChuc == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(eass.toFullResource(congChuc), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PATCH, value = "/congChucs/{id}")
	@ApiOperation(value = "Cập nhật Công Chức", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Công Chức thành công", response = CongChuc.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody CongChuc congChuc, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_SUA) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		congChuc.setId(id);
		if (congChuc.getNguoiDung() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "THONGTINDANGNHAP_REQUIRED",
					"Thông tin đăng nhập không được để trống!");
		}
		/*
		 * else { if (congChuc.getNguoiDung().getMatKhau() == null ||
		 * congChuc.getNguoiDung().getMatKhau().isEmpty()) { return
		 * Utils.responseErrors(HttpStatus.BAD_REQUEST, "MATKHAU_REQUIRED",
		 * "Trường mật khẩu không được để trống!"); } if
		 * (congChuc.getNguoiDung().getTenDangNhap() == null ||
		 * congChuc.getNguoiDung().getTenDangNhap().isEmpty()) { return
		 * Utils.responseErrors(HttpStatus.BAD_REQUEST, "TENDANGNHAP_REQUIRED",
		 * "Trường tên đăng nhập không được để trống!"); } }
		 */

		if (congChuc.getEmail() != null && !Utils.isValidEmailAddress(congChuc.getEmail())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "EMAIL_INVALID", "Trường email không đúng định dạng!");
		}

		if (StringUtils.isNotBlank(congChuc.getNguoiDung().getTenDangNhap())
				&& congChucService.checkExistsData(repo, congChuc)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "TENDANGNHAP_EXISTS",
					"Tên đăng nhập đã tồn tại trong hệ thống!");
		}

		if (congChuc.getHoVaTen() == null && congChuc.getHoVaTen().isEmpty()) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "HOVATEN_REQUIRED",
					"Trường Họ và tên không được để trống!");
		}

		if (congChuc.getNgaySinh() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "NGAYSINH_REQUIRED",
					"Trường ngày sinh không được để trống!");
		}

		if (congChuc.getCoQuanQuanLy() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "DONVI_REQUIRED",
					"Trường cơ quan quản lý không được để trống!");
		}

		if (congChuc.getChucVu() == null) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, "CHUCVU_REQUIRED",
					"Trường chức vụ không được để trống!");
		}

		if (!congChucService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus arg0) {
				congChuc.getNguoiDung().updatePassword(congChuc.getNguoiDung().getMatKhau());
				Utils.save(repoNguoiDung, congChuc.getNguoiDung(),
						new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
				return Utils.doSave(repo, congChuc,
						new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
						eass, HttpStatus.CREATED);
			}

		});
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/congChucs/{id}")
	@ApiOperation(value = "Xoá Công Chức", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Công Chức thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_XOA) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		CongChuc congChuc = congChucService.delete(repo, id);
		if (congChuc == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		Utils.save(repo, congChuc,
				new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
