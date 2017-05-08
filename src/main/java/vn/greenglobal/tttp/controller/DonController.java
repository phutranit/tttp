package vn.greenglobal.tttp.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.enums.LoaiNguoiDungDonEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.QCoQuanQuanLy;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "dons", description = "Danh Sách Đơn")
public class DonController extends TttpController<Don> {

	@Autowired
	private DonRepository repo;

	@Autowired
	private XuLyDonRepository xuLyRepo;
	
	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepo;

	@Autowired
	private DonService donService;

	public DonController(BaseRepository<Don, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/dons")
	@ApiOperation(value = "Lấy danh sách Đơn", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy dữ liệu đơn thành công", response = Don.class),
			@ApiResponse(code = 203, message = "Không có quyền lấy dữ liệu"),
			@ApiResponse(code = 204, message = "Không có dữ liệu"),
			@ApiResponse(code = 400, message = "Param không đúng kiểu"), })
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "maDon", required = false) String maDon,
			@RequestParam(value = "tenNguoiDungDon", required = false) String tenNguoiDungDon,
			@RequestParam(value = "cmndHoChieu", required = false) String cmndHoChieu,
			@RequestParam(value = "nguonDon", required = false) String nguonDon,
			@RequestParam(value = "phanLoaiDon", required = false) String phanLoaiDon,
			@RequestParam(value = "tiepNhanTuNgay", required = false) String tiepNhanTuNgay,
			@RequestParam(value = "tiepNhanDenNgay", required = false) String tiepNhanDenNgay,
			@RequestParam(value = "hanGiaiQuyetTuNgay", required = false) String hanGiaiQuyetTuNgay,
			@RequestParam(value = "hanGiaiQuyetDenNgay", required = false) String hanGiaiQuyetDenNgay,
			@RequestParam(value = "trinhTrangXuLy", required = false) String trinhTrangXuLy,
			@RequestParam(value = "thanhLapDon", required = true) boolean thanhLapDon,
			@RequestParam(value = "trangThaiDon", required = false) String trangThaiDon,
			@RequestParam(value = "phongBanGiaiQuyetXLD", required = false) Long phongBanGiaiQuyet,
			@RequestParam(value = "canBoXuLyXLD", required = false) Long canBoXuLyXLD,
			@RequestParam(value = "phongBanXuLyXLD", required = false) Long phongBanXuLyXLD,
			@RequestParam(value = "coQuanTiepNhanXLD", required = false) Long coQuanTiepNhanXLD,
			@RequestParam(value = "chucVu", required = false) String chucVu, PersistentEntityResourceAssembler eass) {

		NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DON_LIETKE);
		if (nguoiDung != null) {
			Page<Don> pageData = repo
					.findAll(donService.predicateFindAll(maDon, tenNguoiDungDon, nguonDon, phanLoaiDon, tiepNhanTuNgay,
							tiepNhanDenNgay, hanGiaiQuyetTuNgay, hanGiaiQuyetDenNgay, trinhTrangXuLy, thanhLapDon,
							trangThaiDon, phongBanGiaiQuyet, canBoXuLyXLD, phongBanXuLyXLD, coQuanTiepNhanXLD, chucVu, xuLyRepo),
							pageable);
			return assembler.toResource(pageData, (ResourceAssembler) eass);
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}

	public boolean checkInputDateTime(String tuNgay, String denNgay) {

		if (StringUtils.isNotBlank(tuNgay)) {
			try {
				LocalDateTime.parse(denNgay);
			} catch (DateTimeParseException ex) {
				return false;
			}
			if (StringUtils.isNotBlank(tuNgay)) {
				try {
					LocalDateTime.parse(denNgay);
				} catch (DateTimeParseException ex) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/dons")
	@ApiOperation(value = "Thêm mới Đơn", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Thêm mới Đơn thành công", response = Don.class),
			@ApiResponse(code = 201, message = "Thêm mới Đơn thành công", response = Don.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Don don, PersistentEntityResourceAssembler eass) {

		NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DON_THEM);
		  CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
		  
		  if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId") && 
		    commonProfile.containsAttribute("coQuanQuanLyId")) {
		   
		   Long congChucId = new Long(commonProfile.getAttribute("congChucId").toString());
		   Long coQuanQuanLyId = new Long(commonProfile.getAttribute("coQuanQuanLyId").toString());
		   
		   if (don.isBoSungThongTinBiKhieuTo()) {
			   if (don.getLoaiNguoiBiKhieuTo() == null) {
					return Utils.responseErrors(HttpStatus.BAD_REQUEST, "LOAINGUOIBIKHIEUTO_REQUIRED",
							"Trường loaiNguoiBiKhieuTo không được để trống!");
			   }
			   if (LoaiNguoiDungDonEnum.CA_NHAN.equals(don.getLoaiNguoiBiKhieuTo())) {
				   don.setDiaChiCoQuanBKT("");
				   don.setSoDienThoaiCoQuanBKT("");
				   don.setTenCoQuanBKT("");
				   don.setTinhThanhCoQuanBKT(null);
				   don.setQuanHuyenCoQuanBKT(null);
				   don.setPhuongXaCoQuanBKT(null);
				   don.setToDanPhoCoQuanBKT(null);
			   }
		   } else {
			   don.setDiaChiCoQuanBKT("");
			   don.setSoDienThoaiCoQuanBKT("");
			   don.setTenCoQuanBKT("");
			   don.setTinhThanhCoQuanBKT(null);
			   don.setQuanHuyenCoQuanBKT(null);
			   don.setPhuongXaCoQuanBKT(null);
			   don.setToDanPhoCoQuanBKT(null);
		   }
		   
		   
		//   if (don.isThanhLapDon()) {
//		    don.setMa(donService.getMaDonMoi(repo));
		//   }
		   don.setNgayLapDonGapLanhDaoTmp(LocalDateTime.now());
		   Don donMoi = Utils.save(repo, don, congChucId);
		   XuLyDon xuLyDon = new XuLyDon();
		   xuLyDon.setDon(donMoi);
		   xuLyDon.setChucVu(VaiTroEnum.VAN_THU);
		   xuLyDon.setPhongBanXuLy(coQuanQuanLyRepo.findOne(QCoQuanQuanLy.coQuanQuanLy.id.eq(coQuanQuanLyId)));
		   xuLyDon.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		   xuLyDon.setThuTuThucHien(0);
		   Utils.save(xuLyRepo,xuLyDon, congChucId);
		   donMoi.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		   return Utils.doSave(repo, donMoi, congChucId, eass, HttpStatus.CREATED);
		  }
		  return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
		    ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/dons/{id}")
	@ApiOperation(value = "Lấy Đơn theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Đơn thành công", response = Don.class) })
	public ResponseEntity<Object> getDon(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DON_XEM);
		if (nguoiDung != null) {
			Don don = repo.findOne(donService.predicateFindOne(id));
			if (don == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			return new ResponseEntity<>(eass.toFullResource(don), HttpStatus.OK);
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/dons/{id}")
	@ApiOperation(value = "Cập nhật Đơn", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cập nhật Đơn thành công", response = Don.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody Don don, PersistentEntityResourceAssembler eass) {
		NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DON_SUA);
		if (nguoiDung != null) {
			don.setId(id);
			
			if (don.isBoSungThongTinBiKhieuTo()) {
				   if (don.getLoaiNguoiBiKhieuTo() == null) {
						return Utils.responseErrors(HttpStatus.BAD_REQUEST, "LOAINGUOIBIKHIEUTO_REQUIRED",
								"Trường loaiNguoiBiKhieuTo không được để trống!");
				   }
				   if (LoaiNguoiDungDonEnum.CA_NHAN.equals(don.getLoaiNguoiBiKhieuTo())) {
					   don.setDiaChiCoQuanBKT("");
					   don.setSoDienThoaiCoQuanBKT("");
					   don.setTenCoQuanBKT("");
					   don.setTinhThanhCoQuanBKT(null);
					   don.setQuanHuyenCoQuanBKT(null);
					   don.setPhuongXaCoQuanBKT(null);
					   don.setToDanPhoCoQuanBKT(null);
				   }
			   } else {
				   don.setDiaChiCoQuanBKT("");
				   don.setSoDienThoaiCoQuanBKT("");
				   don.setTenCoQuanBKT("");
				   don.setTinhThanhCoQuanBKT(null);
				   don.setQuanHuyenCoQuanBKT(null);
				   don.setPhuongXaCoQuanBKT(null);
				   don.setToDanPhoCoQuanBKT(null);
			   }
			
			if (!donService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			Don donOld = repo.findOne(id);
			if (don.isYeuCauGapTrucTiepLanhDao() && !donOld.isYeuCauGapTrucTiepLanhDao()) {
				don.setNgayLapDonGapLanhDaoTmp(LocalDateTime.now());
			}
			return Utils.doSave(repo, don,
					new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/dons/{id}")
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DON_XOA);
		if (nguoiDung != null) {

			Don don = donService.deleteDon(repo, id);
			if (don == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			Utils.save(repo, don,
					new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}
}
