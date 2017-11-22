package vn.greenglobal.tttp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.enums.VaiTroThanhVienDoanEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.CoQuanQuanLyService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.util.ProfileUtils;
import vn.greenglobal.tttp.enums.CanCuThanhTraLaiEnum;
import vn.greenglobal.tttp.enums.FlowStateEnum;
import vn.greenglobal.tttp.enums.HinhThucGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.ChucNangKeHoachThanhTraEnum;
import vn.greenglobal.tttp.enums.CoQuanDieuTraThanhTraEnum;
import vn.greenglobal.tttp.enums.DonViTheoDoiGQDEnum;
import vn.greenglobal.tttp.enums.HinhThucThanhTraEnum;
import vn.greenglobal.tttp.enums.HinhThucTheoDoiEnum;
import vn.greenglobal.tttp.enums.HinhThucThongKeEnum;
import vn.greenglobal.tttp.enums.HuongGiaiQuyetTCDEnum;
import vn.greenglobal.tttp.enums.HuongXuLyTCDEnum;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.KetQuaGiaiQuyetLan2Enum;
import vn.greenglobal.tttp.enums.KetLuanNoiDungKhieuNaiEnum;
import vn.greenglobal.tttp.enums.KetQuaThucHienTheoDoiEnum;
import vn.greenglobal.tttp.enums.KetQuaTrangThaiDonEnum;
import vn.greenglobal.tttp.enums.LinhVucThanhTraEnum;
import vn.greenglobal.tttp.enums.LoaiDoiTuongEnum;
import vn.greenglobal.tttp.enums.LoaiDoiTuongThanhTraEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiHinhThanhTraEnum;
import vn.greenglobal.tttp.enums.LoaiNguoiDungDonEnum;
import vn.greenglobal.tttp.enums.LoaiTepDinhKemEnum;
import vn.greenglobal.tttp.enums.LoaiThoiHanEnum;
import vn.greenglobal.tttp.enums.LoaiVuViecEnum;
import vn.greenglobal.tttp.enums.LyDoKhongDuDieuKienXuLyEnum;
import vn.greenglobal.tttp.enums.NguonTiepNhanDonEnum;
import vn.greenglobal.tttp.enums.PhanLoaiDonEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.QuyTrinhXuLyDonEnum;
import vn.greenglobal.tttp.enums.ThongKeBaoCaoLoaiKyEnum;
import vn.greenglobal.tttp.enums.TienDoThanhTraEnum;
import vn.greenglobal.tttp.enums.TinhTrangTaiLieuEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.TrangThaiTTXMEnum;
import vn.greenglobal.tttp.enums.TrangThaiYeuCauGapLanhDaoEnum;
import vn.greenglobal.tttp.util.Utils;

@RestController
@Api(value = "phanLoaiDanhMucs", description = "Danh Sách Các Combobox Enum")
public class EnumController {
	
	@Autowired
	private ProfileUtils profileUtil;
	
	@Autowired
	private DonRepository donRepo;
	
	@Autowired
	private XuLyDonRepository xuLyDonRepo;
	
	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepository;
	
	@Autowired
	private CoQuanQuanLyService coQuanQuanLyService;
	
	@Autowired
	private ThamSoRepository thamSoRepository;
	
	@Autowired
	private ThamSoService thamSoService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/giaiQuyetDon/donViTheoDoi")
	@ApiOperation(value = "Lấy danh sách Đơn vị theo dõi", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getTrangThaiDonViTheoDois(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", DonViTheoDoiGQDEnum.THANH_TRA.getText());
		object.put("giaTri", DonViTheoDoiGQDEnum.THANH_TRA.name());
		list.add(object);

		object = new HashMap<String, Object>();
		object.put("ten", DonViTheoDoiGQDEnum.KHAC.getText());
		object.put("giaTri", DonViTheoDoiGQDEnum.KHAC.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCao/timKiemTheoLoaiKy")
	@ApiOperation(value = "Lấy tìm kiếm theo Loại kỳ thống kê báo cáo", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getTimKiemTheoLoaiKys(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "loaiKy", required = true) String loaiKy) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
		int year = Utils.localDateTimeNow().getYear();
		int month = Utils.localDateTimeNow().getMonthValue();
		
		if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
			object.put("year", year);
			object.put("quy", Utils.getQuyHienTai());
		} else if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_DAU_NAM)) {
			object.put("year", year);
		} else if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_CUOI_NAM)) {
			object.put("year", year);
		} else if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_THANG)) {
			object.put("year", year);
			object.put("month", month);
		} else if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON)) {
			LocalDateTime tuNgay = LocalDateTime.of(year, month, 1, 0, 0);
			Calendar c = Utils.getMocThoiGianLocalDateTime(tuNgay, 0, 0);
			LocalDateTime denNgay = LocalDateTime.of(year, month, c.getActualMaximum(Calendar.DAY_OF_MONTH), 0, 0);
			object.put("tuNgay", tuNgay);
			object.put("denNgay", denNgay);
		}
		list.add(object);
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/layNgayGioHienTai")
	@ApiOperation(value = "Lấy ngày giờ hiện tại", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getNgayGioHienTai(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		Map<String, Object> map = new HashMap<>();
		map.put("ngayGioHienTai", Utils.localDateTimeNow());

		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/tiepCongDan/trangThaiYeuCauGapLanhDao")
	@ApiOperation(value = "Lấy danh sách Trạng thái yêu cầu gặp lãnh đạo", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getTrangThaiYeuCauGapLanhDaos(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", TrangThaiYeuCauGapLanhDaoEnum.DONG_Y.getText());
		object.put("giaTri", TrangThaiYeuCauGapLanhDaoEnum.DONG_Y.name());
		list.add(object);

		object = new HashMap<String, Object>();
		object.put("ten", TrangThaiYeuCauGapLanhDaoEnum.CHO_XIN_Y_KIEN.getText());
		object.put("giaTri", TrangThaiYeuCauGapLanhDaoEnum.CHO_XIN_Y_KIEN.name());
		list.add(object);
		
		object = new HashMap<String, Object>();
		object.put("ten", TrangThaiYeuCauGapLanhDaoEnum.KHONG_DONG_Y.getText());
		object.put("giaTri", TrangThaiYeuCauGapLanhDaoEnum.KHONG_DONG_Y.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/tiepCongDan/phanLoaiDon")
	@ApiOperation(value = "Lấy danh sách Phân loại đơn", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getPhanLoais(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", PhanLoaiDonEnum.DU_DIEU_KIEN_XU_LY.getText());
		object.put("giaTri", PhanLoaiDonEnum.DU_DIEU_KIEN_XU_LY.name());
		list.add(object);

		object = new HashMap<String, Object>();
		object.put("ten", PhanLoaiDonEnum.KHONG_DU_DIEU_KIEN_XU_LY.getText());
		object.put("giaTri", PhanLoaiDonEnum.KHONG_DU_DIEU_KIEN_XU_LY.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCao/years")
	@ApiOperation(value = "Lấy danh sách Loại Quý thống kê báo cáo", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getYears(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<String, Object>();
		int current = Utils.localDateTimeNow().getYear();
		for (int i = current; i >= 2010; i--) {
			object = new HashMap<String, Object>();
			object.put("ten", i);
			object.put("giaTri", i);
			list.add(object);
		}
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCao/months")
	@ApiOperation(value = "Lấy danh sách các tháng trong năm", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getMonths(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<String, Object>();
		int now = Utils.localDateTimeNow().getMonthValue();
		for (int m = 1; m <= now; m ++) {
			object.put("ten", m);
			object.put("giaTri", m);
			list.add(object);
			object = new HashMap<String, Object>();
		}
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCao/hinhThucThongKe")
	@ApiOperation(value = "Lấy danh sách Hình thức thống kê", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getHinhThucThongKes(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", HinhThucThongKeEnum.DON_VI.getText());
		object.put("giaTri", HinhThucThongKeEnum.DON_VI.name());
		list.add(object);

		object = new HashMap<String, Object>();
		object.put("ten", HinhThucThongKeEnum.CAP_DON_VI.getText());
		object.put("giaTri", HinhThucThongKeEnum.CAP_DON_VI.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCao/loaiQuys")
	@ApiOperation(value = "Lấy danh sách Loại Quý thống kê báo cáo", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getLoaiQuys(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", "I");
		object.put("giaTri", 1);
		list.add(object);

		object = new HashMap<String, Object>();
		object.put("ten", "II");
		object.put("giaTri", 2);
		list.add(object);

		object = new HashMap<String, Object>();
		object.put("ten", "III");
		object.put("giaTri", 3);
		list.add(object);
		
		object = new HashMap<String, Object>();
		object.put("ten", "IV");
		object.put("giaTri", 4);
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCao/loaiKy")
	@ApiOperation(value = "Lấy danh sách Loại kỳ thống kê báo cáo", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getLoaiKys(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", ThongKeBaoCaoLoaiKyEnum.THEO_QUY.getText());
		object.put("giaTri", ThongKeBaoCaoLoaiKyEnum.THEO_QUY.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", ThongKeBaoCaoLoaiKyEnum.SAU_THANG_DAU_NAM.getText());
		object.put("giaTri", ThongKeBaoCaoLoaiKyEnum.SAU_THANG_DAU_NAM.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", ThongKeBaoCaoLoaiKyEnum.SAU_THANG_CUOI_NAM.getText());
		object.put("giaTri", ThongKeBaoCaoLoaiKyEnum.SAU_THANG_CUOI_NAM.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", ThongKeBaoCaoLoaiKyEnum.THEO_THANG.getText());
		object.put("giaTri", ThongKeBaoCaoLoaiKyEnum.THEO_THANG.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", ThongKeBaoCaoLoaiKyEnum.TUY_CHON.getText());
		object.put("giaTri", ThongKeBaoCaoLoaiKyEnum.TUY_CHON.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/theoDoiGiamSat/ngayThangNams")
	@ApiOperation(value = "Lấy danh sách Ngày tháng năm của theo dõi giám sát", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getNgayThangNams(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", "Ngày");
		object.put("giaTri", "day");
		list.add(object);

		object = new HashMap<>();
		object.put("ten", "Tháng");
		object.put("giaTri", "month");
		list.add(object);

		object = new HashMap<>();
		object.put("ten", "Năm");
		object.put("giaTri", "year");
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/theoDoiGiamSat/phanHes")
	@ApiOperation(value = "Lấy danh sách phân hệ của theo dõi giám sát", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getQuyTrinhs(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		
		object = new HashMap<>();
		object.put("ten", ProcessTypeEnum.XU_LY_DON.getText());
		object.put("giaTri", ProcessTypeEnum.XU_LY_DON.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", ProcessTypeEnum.KIEM_TRA_DE_XUAT.getText());
		object.put("giaTri", ProcessTypeEnum.KIEM_TRA_DE_XUAT.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", ProcessTypeEnum.GIAI_QUYET_DON.getText());
		object.put("giaTri", ProcessTypeEnum.GIAI_QUYET_DON.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", ProcessTypeEnum.THAM_TRA_XAC_MINH.getText());
		object.put("giaTri", ProcessTypeEnum.THAM_TRA_XAC_MINH.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/processTypes")
	@ApiOperation(value = "Lấy danh sách loại quy trình", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getProcessTypes(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", ProcessTypeEnum.TIEP_CONG_DAN.getText());
		object.put("giaTri", ProcessTypeEnum.TIEP_CONG_DAN.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", ProcessTypeEnum.XU_LY_DON.getText());
		object.put("giaTri", ProcessTypeEnum.XU_LY_DON.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", ProcessTypeEnum.KIEM_TRA_DE_XUAT.getText());
		object.put("giaTri", ProcessTypeEnum.KIEM_TRA_DE_XUAT.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", ProcessTypeEnum.GIAI_QUYET_DON.getText());
		object.put("giaTri", ProcessTypeEnum.GIAI_QUYET_DON.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", ProcessTypeEnum.THAM_TRA_XAC_MINH.getText());
		object.put("giaTri", ProcessTypeEnum.THAM_TRA_XAC_MINH.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/phanLoaiNguoiDungDons")
	@ApiOperation(value = "Lấy danh sách Phân Loại Người Đứng Đơn", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getPhanLoaiNguoiDungDons(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", LoaiNguoiDungDonEnum.CA_NHAN.getText());
		object.put("giaTri", LoaiNguoiDungDonEnum.CA_NHAN.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiNguoiDungDonEnum.CO_QUAN_TO_CHUC.getText());
		object.put("giaTri", LoaiNguoiDungDonEnum.CO_QUAN_TO_CHUC.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiNguoiDungDonEnum.DOAN_DONG_NGUOI.getText());
		object.put("giaTri", LoaiNguoiDungDonEnum.DOAN_DONG_NGUOI.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/trangChu/loaiDonThus")
	@ApiOperation(value = "Lấy danh sách Loại Đơn Thư", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getTrangChuLoaiDonThus(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", LoaiDonEnum.DON_KHIEU_NAI.getText());
		object.put("giaTri", LoaiDonEnum.DON_KHIEU_NAI.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiDonEnum.DON_TO_CAO.getText());
		object.put("giaTri", LoaiDonEnum.DON_TO_CAO.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loaiDonThus")
	@ApiOperation(value = "Lấy danh sách Loại Đơn Thư", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getLoaiDonThus(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", LoaiDonEnum.DON_KHIEU_NAI.getText());
		object.put("giaTri", LoaiDonEnum.DON_KHIEU_NAI.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiDonEnum.DON_TO_CAO.getText());
		object.put("giaTri", LoaiDonEnum.DON_TO_CAO.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH.getText());
		object.put("giaTri", LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiDonEnum.DON_TRANH_CHAP_DAT.getText());
		object.put("giaTri", LoaiDonEnum.DON_TRANH_CHAP_DAT.name());
		list.add(object);

//		object = new HashMap<>();
//		object.put("ten", LoaiDonEnum.DON_CO_NHIEU_NOI_DUNG_KHAC_NHAU.getText());
//		object.put("giaTri", LoaiDonEnum.DON_CO_NHIEU_NOI_DUNG_KHAC_NHAU.name());
//		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loaiDoiTuongs")
	@ApiOperation(value = "Lấy danh sách Loại Đối Tượng", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getLoaiDoiTuongs(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "loaiDonThu", required = true) String loaiDonThu) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		
		if (StringUtils.isNotBlank(loaiDonThu)) { 
			LoaiDonEnum loaiDon = LoaiDonEnum.valueOf(loaiDonThu);
			if (loaiDon.equals(LoaiDonEnum.DON_KHIEU_NAI)) { 
				object.put("ten", LoaiDoiTuongEnum.HANH_VI_HANH_CHINH.getText());
				object.put("giaTri", LoaiDoiTuongEnum.HANH_VI_HANH_CHINH.name());
				list.add(object);

				object = new HashMap<>();
				object.put("ten", LoaiDoiTuongEnum.QUYET_DINH_HANH_CHINH.getText());
				object.put("giaTri", LoaiDoiTuongEnum.QUYET_DINH_HANH_CHINH.name());
				list.add(object);
			}
			if (loaiDon.equals(LoaiDonEnum.DON_TO_CAO)) { 
				object.put("ten", LoaiDoiTuongEnum.CA_NHAN.getText());
				object.put("giaTri", LoaiDoiTuongEnum.CA_NHAN.name());
				list.add(object);

				object = new HashMap<>();
				object.put("ten", LoaiDoiTuongEnum.TO_CHUC.getText());
				object.put("giaTri", LoaiDoiTuongEnum.TO_CHUC.name());
				list.add(object);
			}
		}
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/huongXuLyTCDs")
	@ApiOperation(value = "Lấy danh sách Hướng Xử Lý TCD", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachHuongXuLyTCDs(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "phanLoaiDon", required = false) String phanLoaiDon) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		
		if (phanLoaiDon != null && !"".equals(phanLoaiDon) && PhanLoaiDonEnum.KHONG_DU_DIEU_KIEN_XU_LY.equals(PhanLoaiDonEnum.valueOf(phanLoaiDon))) {
			object.put("ten", HuongXuLyTCDEnum.TU_CHOI.getText());
			object.put("giaTri", HuongXuLyTCDEnum.TU_CHOI.name());
			list.add(object);
			
			object = new HashMap<>();
			object.put("ten", HuongXuLyTCDEnum.TRA_DON_VA_HUONG_DAN.getText());
			object.put("giaTri", HuongXuLyTCDEnum.TRA_DON_VA_HUONG_DAN.name());
			list.add(object);
		} else {
			object.put("ten", HuongXuLyTCDEnum.TU_CHOI.getText());
			object.put("giaTri", HuongXuLyTCDEnum.TU_CHOI.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyTCDEnum.BO_SUNG_THONG_TIN.getText());
			object.put("giaTri", HuongXuLyTCDEnum.BO_SUNG_THONG_TIN.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyTCDEnum.TIEP_NHAN_DON.getText());
			object.put("giaTri", HuongXuLyTCDEnum.TIEP_NHAN_DON.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyTCDEnum.TRA_DON_VA_HUONG_DAN.getText());
			object.put("giaTri", HuongXuLyTCDEnum.TRA_DON_VA_HUONG_DAN.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyTCDEnum.YEU_CAU_GAP_LANH_DAO.getText());
			object.put("giaTri", HuongXuLyTCDEnum.YEU_CAU_GAP_LANH_DAO.name());
			list.add(object);
			
			object = new HashMap<>();
			object.put("ten", HuongXuLyTCDEnum.KHAC.getText());
			object.put("giaTri", HuongXuLyTCDEnum.KHAC.name());
			list.add(object);
		}
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/quyTrinhXuLyXLDs/giaoViec")
	@ApiOperation(value = "Lấy danh sách Quy Trình Xử Lý Đơn XLD", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachQuyTrinhXuLyXLDs(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam("vaiTro") String vaiTro) {

		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		
		if (StringUtils.equals(vaiTro, VaiTroEnum.VAN_THU.name())) {
			object.put("ten", QuyTrinhXuLyDonEnum.TRINH_LANH_DAO.getText());
			object.put("giaTri", QuyTrinhXuLyDonEnum.TRINH_LANH_DAO.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", QuyTrinhXuLyDonEnum.DINH_CHI.getText());
			object.put("giaTri", QuyTrinhXuLyDonEnum.DINH_CHI.name());
			list.add(object);
		}
		
		if (StringUtils.equals(vaiTro, VaiTroEnum.LANH_DAO.name())) {
			object.put("ten", QuyTrinhXuLyDonEnum.GIAO_VIEC.getText());
			object.put("giaTri", QuyTrinhXuLyDonEnum.GIAO_VIEC.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", QuyTrinhXuLyDonEnum.DINH_CHI.getText());
			object.put("giaTri", QuyTrinhXuLyDonEnum.DINH_CHI.name());
			list.add(object);
		}

		if (StringUtils.equals(vaiTro, VaiTroEnum.TRUONG_PHONG.name())) {
			object.put("ten", QuyTrinhXuLyDonEnum.GIAO_VIEC.getText());
			object.put("giaTri", QuyTrinhXuLyDonEnum.GIAO_VIEC.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI.getText());
			object.put("giaTri", QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI.name());
			list.add(object);
		}

		if (StringUtils.equals(vaiTro, VaiTroEnum.CHUYEN_VIEN.name())) {
			object.put("ten", QuyTrinhXuLyDonEnum.CHUYEN_CHO_VAN_THU.getText());
			object.put("giaTri", QuyTrinhXuLyDonEnum.CHUYEN_CHO_VAN_THU.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI.getText());
			object.put("giaTri", QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI.name());
			list.add(object);
		}

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/quyTrinhXuLyXLDs/deXuat")
	@ApiOperation(value = "Lấy danh sách Quy Trình Xử Lý Đơn chuyển cho bộ phận giải quyết", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSach(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam("vaiTro") String vaiTro) {

		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		if (StringUtils.equals(vaiTro, VaiTroEnum.LANH_DAO.name())) {

			object.put("ten", QuyTrinhXuLyDonEnum.CHUYEN_CAN_BO_XU_LY.getText());
			object.put("giaTri", QuyTrinhXuLyDonEnum.CHUYEN_CAN_BO_XU_LY.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", QuyTrinhXuLyDonEnum.CHUYEN_BO_PHAN_GIAI_QUYET.getText());
			object.put("giaTri", QuyTrinhXuLyDonEnum.CHUYEN_BO_PHAN_GIAI_QUYET.name());
			list.add(object);
		}

		if (StringUtils.equals(vaiTro, VaiTroEnum.TRUONG_PHONG.name())) {

			object.put("ten", QuyTrinhXuLyDonEnum.YEU_CAU_KIEM_TRA_LAI.getText());
			object.put("giaTri", QuyTrinhXuLyDonEnum.YEU_CAU_KIEM_TRA_LAI.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", QuyTrinhXuLyDonEnum.TRINH_LANH_DAO.getText());
			object.put("giaTri", QuyTrinhXuLyDonEnum.TRINH_LANH_DAO.name());
			list.add(object);
		}

		if (StringUtils.equals(vaiTro, VaiTroEnum.CHUYEN_VIEN.name())) {

			object.put("ten", QuyTrinhXuLyDonEnum.CHUYEN_CHO_VAN_THU.getText());
			object.put("giaTri", QuyTrinhXuLyDonEnum.CHUYEN_CHO_VAN_THU.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI.getText());
			object.put("giaTri", QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI.name());
			list.add(object);
		}

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/nguonTiepNhanDons")
	@ApiOperation(value = "Lấy danh sách tất cả Nguồn Tiếp Nhận Đơn", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachNguonTiepNhanDons(
			@RequestHeader(value = "Authorization", required = true) String authorization) {

		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		for (NguonTiepNhanDonEnum nguonTiepNhanDon : NguonTiepNhanDonEnum.values()) {
			object.put("ten", nguonTiepNhanDon.getText());
			object.put("giaTri", nguonTiepNhanDon.name());
			list.add(object);
			object = new HashMap<>();
		}

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/huongXuLyXLDs/vaiTro")
	@ApiOperation(value = "Lấy danh sách Hướng Xử Lý Đơn XLD theo Vai Trò", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachHuongXuLyXLDs(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam("vaiTro") String vaiTro) {

		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		
		if (StringUtils.equals(vaiTro, VaiTroEnum.TRUONG_PHONG.name())) {

			object.put("ten", HuongXuLyXLDEnum.DE_XUAT_THU_LY.getText());
			object.put("giaTri", HuongXuLyXLDEnum.DE_XUAT_THU_LY.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.getText());
			object.put("giaTri", HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.CHUYEN_DON.getText());
			object.put("giaTri", HuongXuLyXLDEnum.CHUYEN_DON.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.getText());
			object.put("giaTri", HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.getText());
			object.put("giaTri", HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.name());
			list.add(object);
		}

		if (StringUtils.equals(vaiTro, VaiTroEnum.LANH_DAO.name())) {

			object.put("ten", HuongXuLyXLDEnum.DE_XUAT_THU_LY.getText());
			object.put("giaTri", HuongXuLyXLDEnum.DE_XUAT_THU_LY.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.getText());
			object.put("giaTri", HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.CHUYEN_DON.getText());
			object.put("giaTri", HuongXuLyXLDEnum.CHUYEN_DON.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.getText());
			object.put("giaTri", HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.getText());
			object.put("giaTri", HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.name());
			list.add(object);
		}
		
		if (StringUtils.equals(vaiTro, VaiTroEnum.CHUYEN_VIEN.name()) 
				|| StringUtils.equals(vaiTro, VaiTroEnum.VAN_THU.getText())) {

			object.put("ten", HuongXuLyXLDEnum.DE_XUAT_THU_LY.getText());
			object.put("giaTri", HuongXuLyXLDEnum.DE_XUAT_THU_LY.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.CHUYEN_DON.getText());
			object.put("giaTri", HuongXuLyXLDEnum.CHUYEN_DON.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.getText());
			object.put("giaTri", HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.name());
			list.add(object);
			
			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.getText());
			object.put("giaTri", HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.name());
			list.add(object);
		
			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.getText());
			object.put("giaTri", HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.name());
			list.add(object);
			
			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO.getText());
			object.put("giaTri", HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO.name());
			list.add(object);
		}
		
		if (StringUtils.equals(vaiTro, VaiTroEnum.VAN_THU.name())) {
			object.put("ten", HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.getText());
			object.put("giaTri", HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.name());
			list.add(object);
			
			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.DE_XUAT_THU_LY.getText());
			object.put("giaTri", HuongXuLyXLDEnum.DE_XUAT_THU_LY.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.CHUYEN_DON.getText());
			object.put("giaTri", HuongXuLyXLDEnum.CHUYEN_DON.name());
			list.add(object);

			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.getText());
			object.put("giaTri", HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.name());
			list.add(object);
			
			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.getText());
			object.put("giaTri", HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.name());
			list.add(object);
		
			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.getText());
			object.put("giaTri", HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.name());
			list.add(object);
			
			object = new HashMap<>();
			object.put("ten", HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO.getText());
			object.put("giaTri", HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO.name());
			list.add(object);
		}

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/huongXuLys")
	@ApiOperation(value = "Lấy danh sách tất cả Hướng Xử Lý Đơn XLD", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachHuongXuLyXLDs(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		for (HuongXuLyXLDEnum hxl : HuongXuLyXLDEnum.values()) {
			if (!hxl.equals(HuongXuLyXLDEnum.DINH_CHI)) { 
				object.put("ten", hxl.getText());
				object.put("giaTri", hxl.name());
				list.add(object);
				object = new HashMap<>();
			}
		}

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/huongXuLys/don")
	@ApiOperation(value = "Lấy danh sách tất cả Hướng Xử Lý Đơn XLD", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachHuongXuLyXLDsTheoDon(
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			@RequestParam(value = "donId", required = false) Long donId, 
			@RequestParam(value = "loaiDon", required = false) String loaiDon) {		
		Don don = null;
		if (donId != null && donId > 0) {
			don = donRepo.findOne(donId);
		}
		
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		
		if (don == null) {
			for (HuongXuLyXLDEnum hxl : HuongXuLyXLDEnum.values()) {
				if (!hxl.equals(HuongXuLyXLDEnum.DINH_CHI) && !hxl.equals(HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN) &&
						!hxl.equals(HuongXuLyXLDEnum.HUONG_DAN_VIET_LAI_DON) && 
						!hxl.equals(HuongXuLyXLDEnum.KHONG_XU_LY_NEU_LY_DO)) { 
					object.put("ten", hxl.getText());
					object.put("giaTri", hxl.name());
					list.add(object);
					object = new HashMap<>();
				}
				if (loaiDon != null && StringUtils.isNotBlank(loaiDon)
						&& loaiDon.equals(LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH.toString())) {
					if (hxl.equals(HuongXuLyXLDEnum.HUONG_DAN_VIET_LAI_DON) || 
							hxl.equals(HuongXuLyXLDEnum.KHONG_XU_LY_NEU_LY_DO)) {
						object.put("ten", hxl.getText());
						object.put("giaTri", hxl.name());
						list.add(object);
						object = new HashMap<>();
					}
					if (hxl.equals(HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY)) {
						object.put("ten", hxl.getText());
						object.put("giaTri", hxl.name());
						list.remove(object);
						object = new HashMap<>();
					}
				}
			}
			errorBody.put("list", list);
			
			if ((loaiDon != null && StringUtils.isNotBlank(loaiDon) && loaiDon.equals(LoaiDonEnum.DON_TO_CAO.toString())) ||
					(loaiDon != null && StringUtils.isNotBlank(loaiDon) && loaiDon.equals(LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH.toString()))) {
				for (Map<String, Object> obj : list) {
					if (obj.get("giaTri").equals(HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.name())) { 
						list.remove(obj);
						break;
					}
				}
			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		
		CommonProfile commonProfile =  profileUtil.getCommonProfile(authorization);
		Long congChucId = Long.valueOf(commonProfile.getAttribute("congChucId").toString());
		
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.don.eq(don));
		
		if (congChucId != null && congChucId > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.congChuc.id.eq(congChucId));
		}
		
		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();
		XuLyDon xld = new XuLyDon();
		if (xuLyDonRepo.exists(xuLyDonQuery)) {
			List<XuLyDon> results = (List<XuLyDon>) xuLyDonRepo.findAll(xuLyDonQuery, sortOrder);
			xld = results.get(0);
		}

		
		if (xld != null) {
			for (HuongXuLyXLDEnum hxl : HuongXuLyXLDEnum.values()) {
				if (!hxl.equals(HuongXuLyXLDEnum.DINH_CHI) && !hxl.equals(HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN) &&
						!hxl.equals(HuongXuLyXLDEnum.HUONG_DAN_VIET_LAI_DON) && 
						!hxl.equals(HuongXuLyXLDEnum.KHONG_XU_LY_NEU_LY_DO)) { 
					object.put("ten", hxl.getText());
					object.put("giaTri", hxl.name());
					list.add(object);
					object = new HashMap<>();
				}
				if (don.getLoaiDon().equals(LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH)
						|| (loaiDon != null && StringUtils.isNotBlank(loaiDon)
								&& loaiDon.equals(LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH.toString()))) {
					if (hxl.equals(HuongXuLyXLDEnum.HUONG_DAN_VIET_LAI_DON) || 
							hxl.equals(HuongXuLyXLDEnum.KHONG_XU_LY_NEU_LY_DO)) {
						object.put("ten", hxl.getText());
						object.put("giaTri", hxl.name());
						list.add(object);
						object = new HashMap<>();
					}
					if (hxl.equals(HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY)) {
						object.put("ten", hxl.getText());
						object.put("giaTri", hxl.name());
						list.remove(object);
						object = new HashMap<>();
					}
				}
			}
//			if (xld.isDonChuyen()) {
//				System.out.println("don chuyen");
//				object.put("ten", HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.getText());
//				object.put("giaTri", HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.name());
//				list.add(object);
//			}
		}
		
		Map<String, Object> mapRemove = new HashMap<>();
		if (don.isDonChuyen()) { 
			object.put("ten", HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.getText());
			object.put("giaTri", HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.name());
			list.add(object);
			for (Map<String, Object> obj : list) {
				if (obj.get("giaTri").equals(HuongXuLyXLDEnum.CHUYEN_DON.name())) { 
					mapRemove = obj;
					break;
				}
			}
			list.remove(mapRemove);
		}

		if ((don.getLoaiDon().equals(LoaiDonEnum.DON_TO_CAO)
				|| don.getLoaiDon().equals(LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH))
				|| (loaiDon != null && StringUtils.isNotBlank(loaiDon)
						&& loaiDon.equals(LoaiDonEnum.DON_TO_CAO.toString()))
				|| (loaiDon != null && StringUtils.isNotBlank(loaiDon)
						&& loaiDon.equals(LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH.toString()))) {
			for (Map<String, Object> obj : list) {
				if (obj.get("giaTri").equals(HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.name())) {
					list.remove(obj);
					break;
				}
			}
		}
		
		errorBody.put("list", list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/huongGiaiQuyetCuaLanhDaoTCDs/timKiem")
	@ApiOperation(value = "Lấy danh sách Hướng Giải Quyết của Lãnh đạo TCD", position = 8, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachGiaiQuyetTimKiemCuaLanhDaoTCDs(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", HuongGiaiQuyetTCDEnum.GIAI_QUYET_NGAY.getText());
		object.put("giaTri", HuongGiaiQuyetTCDEnum.GIAI_QUYET_NGAY.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET.getText());
		object.put("giaTri", HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", HuongGiaiQuyetTCDEnum.CHO_TIEP.getText());
		object.put("giaTri", HuongGiaiQuyetTCDEnum.CHO_TIEP.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/huongGiaiQuyetCuaLanhDaoTCDs")
	@ApiOperation(value = "Lấy danh sách Hướng Giải Quyết của Lãnh đạo TCD", position = 8, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachGiaiQuyetCuaLanhDaoTCDs(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", HuongGiaiQuyetTCDEnum.GIAI_QUYET_NGAY.getText());
		object.put("giaTri", HuongGiaiQuyetTCDEnum.GIAI_QUYET_NGAY.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET.getText());
		object.put("giaTri", HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/tinhTrangGiaiQuyets")
	@ApiOperation(value = "Lấy danh sách Tình trạng giải quyết", position = 8, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachTinhTrangGiaiQuyet(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		
		object.put("ten", TrangThaiDonEnum.DA_GIAI_QUYET.getText());
		object.put("giaTri", TrangThaiDonEnum.DA_GIAI_QUYET.name());
		list.add(object);
			
		object = new HashMap<>();
		object.put("ten", TrangThaiDonEnum.DANG_GIAI_QUYET.getText());
		object.put("giaTri", TrangThaiDonEnum.DANG_GIAI_QUYET.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", TrangThaiDonEnum.HOAN_THANH.getText());
		object.put("giaTri", TrangThaiDonEnum.HOAN_THANH.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/trangThaiTTXMs")
	@ApiOperation(value = "Lấy danh sách trạng thái TTXM", position = 8, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachTrangThaiTTXM(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		
		object.put("ten", TrangThaiTTXMEnum.TU_TTXM.getText());
		object.put("giaTri", TrangThaiTTXMEnum.TU_TTXM.name());
		list.add(object);
			
		object = new HashMap<>();
		object.put("ten", TrangThaiTTXMEnum.GIAO_TTXM.getText());
		object.put("giaTri", TrangThaiTTXMEnum.GIAO_TTXM.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/trangThaiDonToanHTs")
	@ApiOperation(value = "Lấy danh sách Trạng thái đơn toàn hệ thống", position = 8, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachTrangThaiDonToanHT(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		
		
		object.put("ten", TrangThaiDonEnum.DANG_XU_LY.getText());
		object.put("giaTri", TrangThaiDonEnum.DANG_XU_LY.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", TrangThaiDonEnum.DA_XU_LY.getText());
		object.put("giaTri", TrangThaiDonEnum.DA_XU_LY.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", TrangThaiDonEnum.DA_GIAI_QUYET.getText());
		object.put("giaTri", TrangThaiDonEnum.DA_GIAI_QUYET.name());
		list.add(object);
			
		object = new HashMap<>();
		object.put("ten", TrangThaiDonEnum.DANG_GIAI_QUYET.getText());
		object.put("giaTri", TrangThaiDonEnum.DANG_GIAI_QUYET.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", TrangThaiDonEnum.HOAN_THANH.getText());
		object.put("giaTri", TrangThaiDonEnum.HOAN_THANH.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/ketQuaGiaiQuyetTheoDons")
	@ApiOperation(value = "Lấy danh sách kết quả giải quyết theo theo đơn", position = 8, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachKetQuaGiaiQuyetTheoDon(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam("khongCoDoiThoai") boolean khongCoDoiThoai) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		
		if (!khongCoDoiThoai) {
			object.put("ten", KetQuaTrangThaiDonEnum.DOI_THOAI.getText());
			object.put("giaTri", KetQuaTrangThaiDonEnum.DOI_THOAI.name());
			list.add(object);
		}
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.CHO_RA_QUYET_DINH_GIAI_QUYET.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.CHO_RA_QUYET_DINH_GIAI_QUYET.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/ketQuaGiaiQuyetToanHTs")
	@ApiOperation(value = "Lấy danh sách kết quả giải quyết toàn hệ thống", position = 8, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachKetQuaGiaiQuyetToanHT(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		
		object.put("ten", KetQuaTrangThaiDonEnum.DINH_CHI.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.DINH_CHI.name());
		list.add(object);
			
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.DE_XUAT_THU_LY.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.DE_XUAT_THU_LY.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.KHONG_DU_DIEU_KIEN_THU_LY.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.KHONG_DU_DIEU_KIEN_THU_LY.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.CHUYEN_DON.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.CHUYEN_DON.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.TRA_DON_VA_HUONG_DAN.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.TRA_DON_VA_HUONG_DAN.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.LUU_DON_VA_THEO_DOI.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.LUU_DON_VA_THEO_DOI.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.YEU_CAU_GAP_LANH_DAO.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.YEU_CAU_GAP_LANH_DAO.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.DANG_TTXM.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.DANG_TTXM.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.DA_CO_KET_QUA_TTXM.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.DA_CO_KET_QUA_TTXM.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.DOI_THOAI.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.DOI_THOAI.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.DANG_LAP_DU_THAO.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.DANG_LAP_DU_THAO.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.DA_LAP_DU_THAO.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.DA_LAP_DU_THAO.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.CHO_RA_QUYET_DINH_GIAI_QUYET.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.CHO_RA_QUYET_DINH_GIAI_QUYET.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.LUU_HO_SO.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.LUU_HO_SO.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.KHONG_XU_LY_NEU_LY_DO.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.KHONG_XU_LY_NEU_LY_DO.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaTrangThaiDonEnum.HUONG_DAN_VIET_LAI_DON.getText());
		object.put("giaTri", KetQuaTrangThaiDonEnum.HUONG_DAN_VIET_LAI_DON.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/tinhTrangXuLyCuaLanhDaoTCDs")
	@ApiOperation(value = "Lấy danh sách Tình trạng xử lý của Lãnh đạo TCD", position = 8, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachTinhTrangXuLyCuaLanhDaoTCDs(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam("ketQuaTiepDan") String ketQuaTiepDan) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		
		if (StringUtils.equals(ketQuaTiepDan, HuongGiaiQuyetTCDEnum.GIAI_QUYET_NGAY.name())) {
			object.put("ten", HuongGiaiQuyetTCDEnum.DANG_XU_LY.getText());
			object.put("giaTri", HuongGiaiQuyetTCDEnum.DANG_XU_LY.name());
			list.add(object);
			
			object = new HashMap<>();
			object.put("ten", HuongGiaiQuyetTCDEnum.HOAN_THANH.getText());
			object.put("giaTri", HuongGiaiQuyetTCDEnum.HOAN_THANH.name());
			list.add(object);
		}
		
		if (StringUtils.equals(ketQuaTiepDan, HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET.name())) {
			object.put("ten", HuongGiaiQuyetTCDEnum.GIAO_DON_VI_KIEM_TRA_VA_DE_XUAT.getText());
			object.put("giaTri", HuongGiaiQuyetTCDEnum.GIAO_DON_VI_KIEM_TRA_VA_DE_XUAT.name());
			list.add(object);
			
			object = new HashMap<>();
			object.put("ten", HuongGiaiQuyetTCDEnum.DA_CO_BAO_CAO_KIEM_TRA_DE_XUAT.getText());
			object.put("giaTri", HuongGiaiQuyetTCDEnum.DA_CO_BAO_CAO_KIEM_TRA_DE_XUAT.name());
			list.add(object);
			
			object = new HashMap<>();
			object.put("ten", HuongGiaiQuyetTCDEnum.HOAN_THANH.getText());
			object.put("giaTri", HuongGiaiQuyetTCDEnum.HOAN_THANH.name());
			list.add(object);
		}

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/hinhThucDaGiaiQuyet")
	@ApiOperation(value = "Lấy danh sách Hình Thức Giải Quyết", position = 9, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachHinhThucGiaiQuyets(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", HinhThucGiaiQuyetEnum.CHUA_CO_QUYET_DINH_GIAI_QUYET.getText());
		object.put("giaTri", HinhThucGiaiQuyetEnum.CHUA_CO_QUYET_DINH_GIAI_QUYET.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", HinhThucGiaiQuyetEnum.QUYET_DINH_LAN_I.getText());
		object.put("giaTri", HinhThucGiaiQuyetEnum.QUYET_DINH_LAN_I.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", HinhThucGiaiQuyetEnum.QUYET_DINH_LAN_II.getText());
		object.put("giaTri", HinhThucGiaiQuyetEnum.QUYET_DINH_LAN_II.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", HinhThucGiaiQuyetEnum.QUYET_DINH_CUOI_CUNG.getText());
		object.put("giaTri", HinhThucGiaiQuyetEnum.QUYET_DINH_CUOI_CUNG.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", HinhThucGiaiQuyetEnum.BAN_AN_SO_THAM.getText());
		object.put("giaTri", HinhThucGiaiQuyetEnum.BAN_AN_SO_THAM.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", HinhThucGiaiQuyetEnum.BAN_AN_PHUC_THAM.getText());
		object.put("giaTri", HinhThucGiaiQuyetEnum.BAN_AN_PHUC_THAM.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", HinhThucGiaiQuyetEnum.BAN_AN_TAI_THAM.getText());
		object.put("giaTri", HinhThucGiaiQuyetEnum.BAN_AN_TAI_THAM.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", HinhThucGiaiQuyetEnum.QUYET_DINH_GIAM_DOC_THAM.getText());
		object.put("giaTri", HinhThucGiaiQuyetEnum.QUYET_DINH_GIAM_DOC_THAM.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", HinhThucGiaiQuyetEnum.TRA_LOI_BANG_VAN_BAN.getText());
		object.put("giaTri", HinhThucGiaiQuyetEnum.TRA_LOI_BANG_VAN_BAN.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", HinhThucGiaiQuyetEnum.KET_LUAN_THONG_BAO_BAO_CAO.getText());
		object.put("giaTri", HinhThucGiaiQuyetEnum.KET_LUAN_THONG_BAO_BAO_CAO.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loaiTepDinhKems")
	@ApiOperation(value = "Lấy danh sách Loại tệp đính kèm", position = 9, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachLoaiTepDinhKems(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", LoaiTepDinhKemEnum.QUYET_DINH.getText());
		object.put("giaTri", LoaiTepDinhKemEnum.QUYET_DINH.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiTepDinhKemEnum.KHAC.getText());
		object.put("giaTri", LoaiTepDinhKemEnum.KHAC.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loaiDoiTuongBiKhieuTos")
	@ApiOperation(value = "Lấy danh sách Loại Đối Tượng Bị Khiếu Tố", position = 10, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachLoaiDoiTuongKhieuTos(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", LoaiNguoiDungDonEnum.CA_NHAN.getText());
		object.put("giaTri", LoaiNguoiDungDonEnum.CA_NHAN.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiNguoiDungDonEnum.CO_QUAN_TO_CHUC.getText());
		object.put("giaTri", LoaiNguoiDungDonEnum.CO_QUAN_TO_CHUC.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loaiThoiHans")
	@ApiOperation(value = "Lấy danh sách Loại Thời Hạn", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachLoaiThoiHan(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", LoaiThoiHanEnum.THOIHAN_XULYDON.getText());
		object.put("giaTri", LoaiThoiHanEnum.THOIHAN_XULYDON.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiThoiHanEnum.THOIHAN_GIAIQUYETKHIEUNAI.getText());
		object.put("giaTri", LoaiThoiHanEnum.THOIHAN_GIAIQUYETKHIEUNAI.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiThoiHanEnum.THOIHAN_GIAIQUYETTOCAO.getText());
		object.put("giaTri", LoaiThoiHanEnum.THOIHAN_GIAIQUYETTOCAO.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiThoiHanEnum.THOIHAN_KIENNGHIPHANANH.getText());
		object.put("giaTri", LoaiThoiHanEnum.THOIHAN_KIENNGHIPHANANH.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loaiVaiTros")
	@ApiOperation(value = "Lấy danh sách Loại Vai Trò", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getLoaiVaiTro(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", VaiTroEnum.LANH_DAO.getText());
		object.put("giaTri", VaiTroEnum.LANH_DAO.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", VaiTroEnum.TRUONG_PHONG.getText());
		object.put("giaTri", VaiTroEnum.TRUONG_PHONG.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", VaiTroEnum.CHUYEN_VIEN.getText());
		object.put("giaTri", VaiTroEnum.CHUYEN_VIEN.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", VaiTroEnum.VAN_THU.getText());
		object.put("giaTri", VaiTroEnum.VAN_THU.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/ketLuanNoiDungKhieuNais")
	@ApiOperation(value = "Lấy danh sách kết luận nội dung khiếu nại", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getKetLuanNoiDungKhieuNais(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "loaiDon", required = false) String loaiDon) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", KetLuanNoiDungKhieuNaiEnum.DUNG_TOAN_BO.getText());
		object.put("giaTri", KetLuanNoiDungKhieuNaiEnum.DUNG_TOAN_BO.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", KetLuanNoiDungKhieuNaiEnum.SAI_TOAN_BO.getText());
		object.put("giaTri", KetLuanNoiDungKhieuNaiEnum.SAI_TOAN_BO.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", KetLuanNoiDungKhieuNaiEnum.DUNG_MOT_PHAN.getText());
		object.put("giaTri", KetLuanNoiDungKhieuNaiEnum.DUNG_MOT_PHAN.name());
		list.add(object);
		
		if (!"".equals(loaiDon) && loaiDon != null && LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH.equals(LoaiDonEnum.valueOf(loaiDon))) {
			object = new HashMap<>();
			object.put("ten", KetLuanNoiDungKhieuNaiEnum.KHAC.getText());
			object.put("giaTri", KetLuanNoiDungKhieuNaiEnum.KHAC.name());
			list.add(object);
		}

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/hinhThucTheoDois")
	@ApiOperation(value = "Lấy danh sách hình thức theo dõi", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getHinhThucTheoDois(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", HinhThucTheoDoiEnum.TU_THEO_DOI.getText());
		object.put("giaTri", HinhThucTheoDoiEnum.TU_THEO_DOI.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", HinhThucTheoDoiEnum.GIAO_CO_QUAN_KHAC_THEO_DOI.getText());
		object.put("giaTri", HinhThucTheoDoiEnum.GIAO_CO_QUAN_KHAC_THEO_DOI.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/ketQuaThucHiens")
	@ApiOperation(value = "Lấy danh sách kết quả thực hiện", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getKetQuaThucHiens(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", KetQuaThucHienTheoDoiEnum.CHUA_THUC_HIEN.getText());
		object.put("giaTri", KetQuaThucHienTheoDoiEnum.CHUA_THUC_HIEN.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaThucHienTheoDoiEnum.DANG_THUC_HIEN.getText());
		object.put("giaTri", KetQuaThucHienTheoDoiEnum.DANG_THUC_HIEN.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", KetQuaThucHienTheoDoiEnum.DA_THUC_HIEN.getText());
		object.put("giaTri", KetQuaThucHienTheoDoiEnum.DA_THUC_HIEN.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/tinhTrangTaiLieus")
	@ApiOperation(value = "Lấy danh sách tình trạng tài liệu", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getTinhTrangTaiLieus(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", TinhTrangTaiLieuEnum.BAN_CHINH.getText());
		object.put("giaTri", TinhTrangTaiLieuEnum.BAN_CHINH.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", TinhTrangTaiLieuEnum.BAN_SAO.getText());
		object.put("giaTri", TinhTrangTaiLieuEnum.BAN_SAO.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", TinhTrangTaiLieuEnum.BAN_PHOTO.getText());
		object.put("giaTri", TinhTrangTaiLieuEnum.BAN_PHOTO.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", TinhTrangTaiLieuEnum.VAM_BAN_KHAC.getText());
		object.put("giaTri", TinhTrangTaiLieuEnum.VAM_BAN_KHAC.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/flowStates")
	@ApiOperation(value = "Lấy danh sách kiểu trạng thái", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getFlowStates(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", FlowStateEnum.BAT_DAU.getText());
		object.put("giaTri", FlowStateEnum.BAT_DAU.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", FlowStateEnum.TRINH_LANH_DAO.getText());
		object.put("giaTri", FlowStateEnum.TRINH_LANH_DAO.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", FlowStateEnum.LANH_DAO_GIAO_VIEC_TRUONG_PHONG.getText());
		object.put("giaTri", FlowStateEnum.LANH_DAO_GIAO_VIEC_TRUONG_PHONG.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", FlowStateEnum.TRUONG_PHONG_DE_XUAT_GIAO_VIEC_LAI.getText());
		object.put("giaTri", FlowStateEnum.TRUONG_PHONG_DE_XUAT_GIAO_VIEC_LAI.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO.getText());
		object.put("giaTri", FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", FlowStateEnum.CAN_BO_DE_XUAT_GIAO_VIEC_LAI.getText());
		object.put("giaTri", FlowStateEnum.CAN_BO_DE_XUAT_GIAO_VIEC_LAI.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", FlowStateEnum.CAN_BO_DE_XUAT_HUONG_XU_LY.getText());
		object.put("giaTri", FlowStateEnum.CAN_BO_DE_XUAT_HUONG_XU_LY.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", FlowStateEnum.LANH_DAO_GIAO_VIEC_CAN_BO.getText());
		object.put("giaTri", FlowStateEnum.LANH_DAO_GIAO_VIEC_CAN_BO.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", FlowStateEnum.CAN_BO_CHUYEN_VAN_THU_GIAO_TTXM.getText());
		object.put("giaTri", FlowStateEnum.CAN_BO_CHUYEN_VAN_THU_GIAO_TTXM.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", FlowStateEnum.VAN_THU_CHUYEN_DON_VI_TTXM.getText());
		object.put("giaTri", FlowStateEnum.VAN_THU_CHUYEN_DON_VI_TTXM.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", FlowStateEnum.CAN_BO_NHAN_KET_QUA_TTXM.getText());
		object.put("giaTri", FlowStateEnum.CAN_BO_NHAN_KET_QUA_TTXM.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", FlowStateEnum.CAN_BO_CHUYEN_VAN_THU_KET_QUA_TTXM.getText());
		object.put("giaTri", FlowStateEnum.CAN_BO_CHUYEN_VAN_THU_KET_QUA_TTXM.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", FlowStateEnum.CAN_BO_GIAI_QUYET_DON.getText());
		object.put("giaTri", FlowStateEnum.CAN_BO_GIAI_QUYET_DON.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", FlowStateEnum.CAN_BO_CHUYEN_VE_DON_VI_GIAI_QUYET.getText());
		object.put("giaTri", FlowStateEnum.CAN_BO_CHUYEN_VE_DON_VI_GIAI_QUYET.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", FlowStateEnum.CAN_BO_CHUYEN_KET_QUA_DON_VI_GIAO.getText());
		object.put("giaTri", FlowStateEnum.CAN_BO_CHUYEN_KET_QUA_DON_VI_GIAO.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", FlowStateEnum.KET_THUC.getText());
		object.put("giaTri", FlowStateEnum.KET_THUC.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/vuViecs")
	@ApiOperation(value = "Lấy danh sách loại vụ việc", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getLoaiVuViecs(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", LoaiVuViecEnum.CU.getText());
		object.put("giaTri", LoaiVuViecEnum.CU.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiVuViecEnum.MOI_PHAT_SINH.getText());
		object.put("giaTri", LoaiVuViecEnum.MOI_PHAT_SINH.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/loaiDoiTuongThanhTras")
	@ApiOperation(value = "Lấy danh sách loại đối tượng thanh tra", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getLoaiDoiTuongThanhTras(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", LoaiDoiTuongThanhTraEnum.CA_NHAN.getText());
		object.put("giaTri", LoaiDoiTuongThanhTraEnum.CA_NHAN.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiDoiTuongThanhTraEnum.TO_CHUC.getText());
		object.put("giaTri", LoaiDoiTuongThanhTraEnum.TO_CHUC.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/linhVucThanhTras")
	@ApiOperation(value = "Lấy danh sách lĩnh vực thanh tra", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getLinhVucThanhTras(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object = new HashMap<>();
		object.put("ten", LinhVucThanhTraEnum.XAY_DUNG_CO_BAN.getText());
		object.put("giaTri", LinhVucThanhTraEnum.XAY_DUNG_CO_BAN.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", LinhVucThanhTraEnum.DAT_DAI.getText());
		object.put("giaTri", LinhVucThanhTraEnum.DAT_DAI.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", LinhVucThanhTraEnum.TAI_CHINH.getText());
		object.put("giaTri", LinhVucThanhTraEnum.TAI_CHINH.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", LinhVucThanhTraEnum.THANH_TRA_TRACH_NHIEM.getText());
		object.put("giaTri", LinhVucThanhTraEnum.THANH_TRA_TRACH_NHIEM.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/chucNangThanhTras")
	@ApiOperation(value = "Lấy danh sách hình thức thanh tra", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getHinhThucThanhTras(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

//		object.put("ten", HinhThucThanhTraEnum.THEO_KE_HOACH.getText());
//		object.put("giaTri", HinhThucThanhTraEnum.THEO_KE_HOACH.name());
//		list.add(object);

		object = new HashMap<>();
		object.put("ten", HinhThucThanhTraEnum.THUONG_XUYEN.getText());
		object.put("giaTri", HinhThucThanhTraEnum.THUONG_XUYEN.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", HinhThucThanhTraEnum.DOT_XUAT.getText());
		object.put("giaTri", HinhThucThanhTraEnum.DOT_XUAT.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/vaiTroThanhVienDoans")
	@ApiOperation(value = "Lấy danh sách vai trò thành viên đoàn", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getVaiTroThanhVienDoans(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", VaiTroThanhVienDoanEnum.TRUONG_DOAN.getText());
		object.put("giaTri", VaiTroThanhVienDoanEnum.TRUONG_DOAN.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", VaiTroThanhVienDoanEnum.PHO_DOAN.getText());
		object.put("giaTri", VaiTroThanhVienDoanEnum.PHO_DOAN.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", VaiTroThanhVienDoanEnum.THANH_VIEN.getText());
		object.put("giaTri", VaiTroThanhVienDoanEnum.THANH_VIEN.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/loaiHinhThanhTras")
	@ApiOperation(value = "Lấy danh sách loại hình thanh tra", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getLoaiHinhThanhTras(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", LoaiHinhThanhTraEnum.MOI.getText());
		object.put("giaTri", LoaiHinhThanhTraEnum.MOI.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiHinhThanhTraEnum.THANH_TRA_LAI.getText());
		object.put("giaTri", LoaiHinhThanhTraEnum.THANH_TRA_LAI.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/tienDoThanhTras")
	@ApiOperation(value = "Lấy danh sách tiến độ thanh tra", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getTienDoThanhTras(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", TienDoThanhTraEnum.DANG_TIEN_HANH.getText());
		object.put("giaTri", TienDoThanhTraEnum.DANG_TIEN_HANH.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", TienDoThanhTraEnum.KET_THUC_THANH_TRA_TRUC_TIEP.getText());
		object.put("giaTri", TienDoThanhTraEnum.KET_THUC_THANH_TRA_TRUC_TIEP.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", TienDoThanhTraEnum.DA_BAN_HANH_KET_LUAN.getText());
		object.put("giaTri", TienDoThanhTraEnum.DA_BAN_HANH_KET_LUAN.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", TienDoThanhTraEnum.THEO_DOI_THUC_THIEN_KET_LUAN_THANH_TRA.getText());
		object.put("giaTri", TienDoThanhTraEnum.THEO_DOI_THUC_THIEN_KET_LUAN_THANH_TRA.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/canCuThanhTraLais")
	@ApiOperation(value = "Lấy danh sách căn cứ thanh tra lại", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getCanCuThanhTraLais(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", CanCuThanhTraLaiEnum.VI_PHAM_TRINH_TU.getText());
		object.put("giaTri", CanCuThanhTraLaiEnum.VI_PHAM_TRINH_TU.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", CanCuThanhTraLaiEnum.KET_LUAN_KHONG_PHU_HOP.getText());
		object.put("giaTri", CanCuThanhTraLaiEnum.KET_LUAN_KHONG_PHU_HOP.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", CanCuThanhTraLaiEnum.SAI_LAM_AP_DUNG_PHAP_LUAT.getText());
		object.put("giaTri", CanCuThanhTraLaiEnum.SAI_LAM_AP_DUNG_PHAP_LUAT.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", CanCuThanhTraLaiEnum.CO_Y_LAM_SAI_LECH_HO_SO.getText());
		object.put("giaTri", CanCuThanhTraLaiEnum.CO_Y_LAM_SAI_LECH_HO_SO.name());
		list.add(object);
		
		object = new HashMap<>();
		object.put("ten", CanCuThanhTraLaiEnum.VI_PHAM_NGHIEM_TRONG.getText());
		object.put("giaTri", CanCuThanhTraLaiEnum.VI_PHAM_NGHIEM_TRONG.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/ketQuaGiaiQuyetLan2s")
	@ApiOperation(value = "Lấy danh sách kết quả giải quyết lại lần 2", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getKetLuanGiaiQuyetLais(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", KetQuaGiaiQuyetLan2Enum.CONG_NHAN_QDGQ_LAN_I.getText());
		object.put("giaTri", KetQuaGiaiQuyetLan2Enum.CONG_NHAN_QDGQ_LAN_I.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", KetQuaGiaiQuyetLan2Enum.HUY_SUA_QDGQ_LAN_I.getText());
		object.put("giaTri", KetQuaGiaiQuyetLan2Enum.HUY_SUA_QDGQ_LAN_I.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}	
	
	@RequestMapping(method = RequestMethod.GET, value = "/hinhThucKeHoachThanhTras")
	@ApiOperation(value = "Lấy danh sách .", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getHinhThucKeHoachThanhTras(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", ChucNangKeHoachThanhTraEnum.HANH_CHINH.getText());
		object.put("giaTri", ChucNangKeHoachThanhTraEnum.HANH_CHINH.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", ChucNangKeHoachThanhTraEnum.CHUYEN_NGANH.getText());
		object.put("giaTri", ChucNangKeHoachThanhTraEnum.CHUYEN_NGANH.name());
		list.add(object);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/namThanhTras")
	@ApiOperation(value = "Lấy danh sách các năm thanh tra", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getNamThanhTras(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "isTimKiem", required = false, defaultValue = "false") Boolean isTimKiem) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<String, Object>();
		int current = Utils.localDateTimeNow().getYear();
		int end = current - 7;
		if (isTimKiem) {
			end = 2010;
		}
		for (int i = current + 3; i >= end; i--) {
			object = new HashMap<String, Object>();
			object.put("ten", i);
			object.put("giaTri", i);
			list.add(object);
		}
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/donViBanHanhs")
	@ApiOperation(value = "Lấy danh sách đơn vị ban hành.", position = 11, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDonViBanHanhs(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();
		Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
		Long capCoQuanQuanLyCuaDonViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("capCoQuanQuanLyCuaDonViId").toString());
		
		if (donViId != null && donViId > 0) {
			ThamSo thamSoCQQLUBNDThanhPho = thamSoRepository.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCQQLThanhTraThanhPho = thamSoRepository.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLSoBanNganh = thamSoRepository.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			CoQuanQuanLy coQuanQuanLy = null;
			if (donViId.equals(Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri()))
					|| donViId.equals(Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri()))) {
				CoQuanQuanLy coQuanQuanLyThanhTraThanhPho = coQuanQuanLyRepository.findOne(coQuanQuanLyService.predicateFindOne(Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri())));
				if (coQuanQuanLyThanhTraThanhPho != null) {
					object.put("ten", "UBND Thành phố");
					object.put("giaTri", "UY_BAN_NHAN_DAN");
					list.add(object);

					object = new HashMap<>();
					object.put("ten", coQuanQuanLyThanhTraThanhPho.getTen());
					object.put("giaTri", "THANH_TRA");
					list.add(object);
				}
			} else if (capCoQuanQuanLyCuaDonViId.equals(Long.valueOf(thamSoCCQQLSoBanNganh.getGiaTri()))) { 
				coQuanQuanLy = coQuanQuanLyRepository.findOne(coQuanQuanLyService.predicateFindOne(donViId));
				if (coQuanQuanLy != null) {
					object.put("ten", "UBND Thành phố");
					object.put("giaTri", "UY_BAN_NHAN_DAN");
					list.add(object);
	
					object = new HashMap<>();
					object.put("ten", "Thanh tra " + coQuanQuanLy.getTen());
					object.put("giaTri", "THANH_TRA");
					list.add(object);
				}
			} else {
				coQuanQuanLy = coQuanQuanLyRepository.findOne(coQuanQuanLyService.predicateFindOne(donViId));
				if (coQuanQuanLy != null) {
					object.put("ten", "UBND " + coQuanQuanLy.getTen());
					object.put("giaTri", "UY_BAN_NHAN_DAN");
					list.add(object);
	
					object = new HashMap<>();
					object.put("ten", "Thanh tra " + coQuanQuanLy.getTen());
					object.put("giaTri", "THANH_TRA");
					list.add(object);
				}
			}
		}
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/phanLoaiDons")
	@ApiOperation(value = "Lấy danh sách tất cả Phần Loại Đơn", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachPhanLoaiDons(
			@RequestHeader(value = "Authorization", required = true) String authorization) {

		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		for (PhanLoaiDonEnum phanLoaiDon : PhanLoaiDonEnum.values()) {
			object.put("ten", phanLoaiDon.getText());
			object.put("giaTri", phanLoaiDon.name());
			list.add(object);
			object = new HashMap<>();
		}

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/lyDoKhongDuDieuKienXuLys")
	@ApiOperation(value = "Lấy danh sách tất cả Lý Do Không Đủ Điều Kiện Xử Lý", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachLyDoKhongDuDieuKienXuLys(
			@RequestHeader(value = "Authorization", required = true) String authorization) {

		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		for (LyDoKhongDuDieuKienXuLyEnum lyDoKhongDuDieuKienXuLy : LyDoKhongDuDieuKienXuLyEnum.values()) {
			object.put("ten", lyDoKhongDuDieuKienXuLy.getText());
			object.put("giaTri", lyDoKhongDuDieuKienXuLy.name());
			list.add(object);
			object = new HashMap<>();
		}

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanDieuTraThanhTra")
	@ApiOperation(value = "Lấy danh sách tất cả cơ quan điều tra thanh tra", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachCoQuanDieuTraThanhTras(
			@RequestHeader(value = "Authorization", required = true) String authorization) {

		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		for (CoQuanDieuTraThanhTraEnum coQuanDieuTraThanhTra : CoQuanDieuTraThanhTraEnum.values()) {
			object.put("ten", coQuanDieuTraThanhTra.getText());
			object.put("giaTri", coQuanDieuTraThanhTra.name());
			list.add(object);
			object = new HashMap<>();
		}

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
