package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vn.greenglobal.tttp.enums.HuongGiaiQuyetTCDEnum;
import vn.greenglobal.tttp.enums.HuongXuLyTCDEnum;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.LoaiDoiTuongEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiNguoiDungDonEnum;

@RestController
@Api(value = "phanLoaiDanhMucs", description = "Danh Sách Các Combobox Enum")
public class EnumController {

	@RequestMapping(method = RequestMethod.GET, value = "/phanLoaiNguoiDungDons")
	@ApiOperation(value = "Lấy danh sách Phân Loại Người Đứng Đơn", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getPhanLoaiNguoiDungDons() {
		List<Map<String, Object>> phanLoaiList = new ArrayList<>();
		Map<String, Object> phanLoaiObj = new HashMap<>();

		phanLoaiObj.put("ten", LoaiNguoiDungDonEnum.CA_NHAN.getText());
		phanLoaiObj.put("giaTri", LoaiNguoiDungDonEnum.CA_NHAN.name());
		phanLoaiList.add(phanLoaiObj);

		phanLoaiObj = new HashMap<>();
		phanLoaiObj.put("ten", LoaiNguoiDungDonEnum.CO_QUAN_TO_CHUC.getText());
		phanLoaiObj.put("giaTri", LoaiNguoiDungDonEnum.CO_QUAN_TO_CHUC.name());
		phanLoaiList.add(phanLoaiObj);

		phanLoaiObj = new HashMap<>();
		phanLoaiObj.put("ten", LoaiNguoiDungDonEnum.DOAN_DONG_NGUOI.getText());
		phanLoaiObj.put("giaTri", LoaiNguoiDungDonEnum.DOAN_DONG_NGUOI.name());
		phanLoaiList.add(phanLoaiObj);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("phanLoaiList", phanLoaiList);

		return new ResponseEntity<>(phanLoaiList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loaiDonThus")
	@ApiOperation(value = "Lấy danh sách Loại Đơn Thư", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getLoaiDonThus() {
		List<Map<String, Object>> phanLoaiList = new ArrayList<>();
		Map<String, Object> phanLoaiObj = new HashMap<>();

		phanLoaiObj.put("ten", LoaiDonEnum.DON_KHIEU_NAI.getText());
		phanLoaiObj.put("giaTri", LoaiDonEnum.DON_KHIEU_NAI.name());
		phanLoaiList.add(phanLoaiObj);

		phanLoaiObj = new HashMap<>();
		phanLoaiObj.put("ten", LoaiDonEnum.DON_TO_CAO.getText());
		phanLoaiObj.put("giaTri", LoaiDonEnum.DON_TO_CAO.name());
		phanLoaiList.add(phanLoaiObj);

		phanLoaiObj = new HashMap<>();
		phanLoaiObj.put("ten", LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH.getText());
		phanLoaiObj.put("giaTri", LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH.name());
		phanLoaiList.add(phanLoaiObj);

		phanLoaiObj = new HashMap<>();
		phanLoaiObj.put("ten", LoaiDonEnum.DON_CO_NHIEU_NOI_DUNG_KHAC_NHAU.getText());
		phanLoaiObj.put("giaTri", LoaiDonEnum.DON_CO_NHIEU_NOI_DUNG_KHAC_NHAU.name());
		phanLoaiList.add(phanLoaiObj);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("phanLoaiList", phanLoaiList);

		return new ResponseEntity<>(phanLoaiList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loaiDoiTuongs")
	@ApiOperation(value = "Lấy danh sách Loại Đối Tượng", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getLoaiDoiTuongs() {
		List<Map<String, Object>> phanLoaiList = new ArrayList<>();
		Map<String, Object> phanLoaiObj = new HashMap<>();

		phanLoaiObj.put("ten", LoaiDoiTuongEnum.QUYET_DINH_HANH_CHINH.getText());
		phanLoaiObj.put("giaTri", LoaiDoiTuongEnum.QUYET_DINH_HANH_CHINH.name());
		phanLoaiList.add(phanLoaiObj);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("phanLoaiList", phanLoaiList);

		return new ResponseEntity<>(phanLoaiList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/huongXuLyTCDs")
	@ApiOperation(value = "Lấy danh sách Hướng Xử Lý TCD", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachHuongXuLyTCDs() {
		List<Map<String, Object>> phanLoaiList = new ArrayList<>();
		Map<String, Object> phanLoaiObj = new HashMap<>();

		phanLoaiObj.put("ten", HuongXuLyTCDEnum.TU_CHOI.getText());
		phanLoaiObj.put("giaTri", HuongXuLyTCDEnum.TU_CHOI.name());
		phanLoaiList.add(phanLoaiObj);

		phanLoaiObj = new HashMap<>();
		phanLoaiObj.put("ten", HuongXuLyTCDEnum.BO_SUNG_THONG_TIN.getText());
		phanLoaiObj.put("giaTri", HuongXuLyTCDEnum.BO_SUNG_THONG_TIN.name());
		phanLoaiList.add(phanLoaiObj);

		phanLoaiObj = new HashMap<>();
		phanLoaiObj.put("ten", HuongXuLyTCDEnum.TIEP_NHAN_DON.getText());
		phanLoaiObj.put("giaTri", HuongXuLyTCDEnum.TIEP_NHAN_DON.name());
		phanLoaiList.add(phanLoaiObj);

		phanLoaiObj = new HashMap<>();
		phanLoaiObj.put("ten", HuongXuLyTCDEnum.TRA_DON_VA_HUONG_DAN.getText());
		phanLoaiObj.put("giaTri", HuongXuLyTCDEnum.TRA_DON_VA_HUONG_DAN.name());
		phanLoaiList.add(phanLoaiObj);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("phanLoaiList", phanLoaiList);

		return new ResponseEntity<>(phanLoaiList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/huongXuLyXLDs")
	@ApiOperation(value = "Lấy danh sách Hướng Xử Lý XLD", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachHuongXuLyXLDs() {
		List<Map<String, Object>> phanLoaiList = new ArrayList<>();
		Map<String, Object> phanLoaiObj = new HashMap<>();

		phanLoaiObj.put("ten", HuongXuLyXLDEnum.CHUYEN_DON.getText());
		phanLoaiObj.put("giaTri", HuongXuLyXLDEnum.CHUYEN_DON.name());
		phanLoaiList.add(phanLoaiObj);

		phanLoaiObj = new HashMap<>();
		phanLoaiObj.put("ten", HuongXuLyXLDEnum.DA_DE_XUAT_GIAO_VIEC_LAI.getText());
		phanLoaiObj.put("giaTri", HuongXuLyXLDEnum.DA_DE_XUAT_GIAO_VIEC_LAI.name());
		phanLoaiList.add(phanLoaiObj);

		phanLoaiObj = new HashMap<>();
		phanLoaiObj.put("ten", HuongXuLyXLDEnum.DE_XUAT_THU_LY.getText());
		phanLoaiObj.put("giaTri", HuongXuLyXLDEnum.DE_XUAT_THU_LY.name());
		phanLoaiList.add(phanLoaiObj);

		phanLoaiObj = new HashMap<>();
		phanLoaiObj.put("ten", HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.getText());
		phanLoaiObj.put("giaTri", HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.name());
		phanLoaiList.add(phanLoaiObj);

		phanLoaiObj = new HashMap<>();
		phanLoaiObj.put("ten", HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.getText());
		phanLoaiObj.put("giaTri", HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.name());
		phanLoaiList.add(phanLoaiObj);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("phanLoaiList", phanLoaiList);

		return new ResponseEntity<>(phanLoaiList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/huongGiaiQuyetTCDs")
	@ApiOperation(value = "Lấy danh sách Hướng Giải Quyết TCD", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachGiaiQuyetTCDs() {
		List<Map<String, Object>> phanLoaiList = new ArrayList<>();
		Map<String, Object> phanLoaiObj = new HashMap<>();

		phanLoaiObj.put("ten", HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET.getText());
		phanLoaiObj.put("giaTri", HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET.name());
		phanLoaiList.add(phanLoaiObj);

		phanLoaiObj = new HashMap<>();
		phanLoaiObj.put("ten", HuongGiaiQuyetTCDEnum.GIAI_QUYET_NGAY.getText());
		phanLoaiObj.put("giaTri", HuongGiaiQuyetTCDEnum.GIAI_QUYET_NGAY.name());
		phanLoaiList.add(phanLoaiObj);

		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("phanLoaiList", phanLoaiList);

		return new ResponseEntity<>(phanLoaiList, HttpStatus.OK);
	}
}
