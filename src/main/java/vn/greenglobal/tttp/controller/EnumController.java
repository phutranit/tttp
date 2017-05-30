package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.enums.HinhThucGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.HuongGiaiQuyetTCDEnum;
import vn.greenglobal.tttp.enums.HuongXuLyTCDEnum;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.LoaiDoiTuongEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiNguoiDungDonEnum;
import vn.greenglobal.tttp.enums.LoaiTepDinhKemEnum;
import vn.greenglobal.tttp.enums.LoaiThoiHanEnum;
import vn.greenglobal.tttp.enums.NguonTiepNhanDonEnum;
import vn.greenglobal.tttp.enums.QuyTrinhXuLyDonEnum;

@RestController
@Api(value = "phanLoaiDanhMucs", description = "Danh Sách Các Combobox Enum")
public class EnumController {

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
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

		object.put("ten", LoaiDoiTuongEnum.HANH_VI_HANH_CHINH.getText());
		object.put("giaTri", LoaiDoiTuongEnum.HANH_VI_HANH_CHINH.name());
		list.add(object);

		object = new HashMap<>();
		object.put("ten", LoaiDoiTuongEnum.QUYET_DINH_HANH_CHINH.getText());
		object.put("giaTri", LoaiDoiTuongEnum.QUYET_DINH_HANH_CHINH.name());
		list.add(object);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("list", list);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/huongXuLyTCDs")
	@ApiOperation(value = "Lấy danh sách Hướng Xử Lý TCD", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachHuongXuLyTCDs(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> object = new HashMap<>();

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
			object.put("ten", hxl.getText());
			object.put("giaTri", hxl.name());
			list.add(object);
			object = new HashMap<>();
		}

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

//		object = new HashMap<>();
//		object.put("ten", HuongGiaiQuyetTCDEnum.GIAO_DON_VI_KIEM_TRA_VA_DE_XUAT.getText());
//		object.put("giaTri", HuongGiaiQuyetTCDEnum.GIAO_DON_VI_KIEM_TRA_VA_DE_XUAT.name());
//		list.add(object);

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
			object.put("ten", "Đang giải quyết");
			object.put("giaTri", HuongGiaiQuyetTCDEnum.GIAI_QUYET_NGAY.name());
			list.add(object);
			
			object = new HashMap<>();
			object.put("ten", "Hoàn thành");
			object.put("giaTri", HuongGiaiQuyetTCDEnum.GIAI_QUYET_NGAY.name());
			list.add(object);
		}
		
		if (StringUtils.equals(ketQuaTiepDan, HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET.name())) {
			object.put("ten", "Giao kiểm tra đề xuất");
			object.put("giaTri", HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET.name());
			list.add(object);
			
			object = new HashMap<>();
			object.put("ten", "Đã có báo cáo kiểm tra đề xuất");
			object.put("giaTri", HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET.name());
			list.add(object);
			
			object = new HashMap<>();
			object.put("ten", "Hoàn thành");
			object.put("giaTri", HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET.name());
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
		object.put("giaTri", HinhThucGiaiQuyetEnum.TRA_LOI_BANG_VAN_BAN.name());
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

}
