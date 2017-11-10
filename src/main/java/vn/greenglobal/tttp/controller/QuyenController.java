package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vn.greenglobal.tttp.enums.QuyenEnum;

@RestController
@Api(value = "quyens", description = "Quyền")
public class QuyenController {

	@RequestMapping(method = RequestMethod.GET, value = "/quyens")
	@ApiOperation(value = "Lấy danh sách Quyền", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getList(@RequestHeader(value = "Authorization", required = true) String authorization) {
		
		List<Map<String, Object>> quyenList = new ArrayList<>();
		Map<String, Object> quyenObj = new HashMap<>();
		
		quyenObj.put("tenChucNang","Lĩnh vực đối tượng thanh tra");
		quyenObj.put("tenQuyen", QuyenEnum.LINHVUCDOITUONGTHANHTRA_LIETKE.getText() + "," + QuyenEnum.LINHVUCDOITUONGTHANHTRA_XEM.getText() + "," + QuyenEnum.LINHVUCDOITUONGTHANHTRA_THEM.getText() + "," + QuyenEnum.LINHVUCDOITUONGTHANHTRA_SUA.getText() + "," + QuyenEnum.LINHVUCDOITUONGTHANHTRA_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.LINHVUCDOITUONGTHANHTRA_LIETKE.name() + "," + QuyenEnum.LINHVUCDOITUONGTHANHTRA_XEM.name() + "," + QuyenEnum.LINHVUCDOITUONGTHANHTRA_THEM.name() + "," + QuyenEnum.LINHVUCDOITUONGTHANHTRA_SUA.name() + "," + QuyenEnum.LINHVUCDOITUONGTHANHTRA_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Đối tượng thanh tra");
		quyenObj.put("tenQuyen", QuyenEnum.DOITUONGTHANHTRA_LIETKE.getText() + "," + QuyenEnum.DOITUONGTHANHTRA_XEM.getText() + "," + QuyenEnum.DOITUONGTHANHTRA_THEM.getText() + "," + QuyenEnum.DOITUONGTHANHTRA_SUA.getText() + "," + QuyenEnum.DOITUONGTHANHTRA_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.DOITUONGTHANHTRA_LIETKE.name() + "," + QuyenEnum.DOITUONGTHANHTRA_XEM.name() + "," + QuyenEnum.DOITUONGTHANHTRA_THEM.name() + "," + QuyenEnum.DOITUONGTHANHTRA_SUA.name() + "," + QuyenEnum.DOITUONGTHANHTRA_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Thẩm quyền giải quyết");
		quyenObj.put("tenQuyen", QuyenEnum.THAMQUYENGIAIQUYET_LIETKE.getText() + "," + QuyenEnum.THAMQUYENGIAIQUYET_XEM.getText() + "," + QuyenEnum.THAMQUYENGIAIQUYET_THEM.getText() + "," + QuyenEnum.THAMQUYENGIAIQUYET_SUA.getText() + "," + QuyenEnum.THAMQUYENGIAIQUYET_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.THAMQUYENGIAIQUYET_LIETKE.name() + "," + QuyenEnum.THAMQUYENGIAIQUYET_XEM.name() + "," + QuyenEnum.THAMQUYENGIAIQUYET_THEM.name() + "," + QuyenEnum.THAMQUYENGIAIQUYET_SUA.name() + "," + QuyenEnum.THAMQUYENGIAIQUYET_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Loại tài liệu");
		quyenObj.put("tenQuyen", QuyenEnum.LOAITAILIEU_LIETKE.getText() + "," + QuyenEnum.LOAITAILIEU_XEM.getText() + "," + QuyenEnum.LOAITAILIEU_THEM.getText() + "," + QuyenEnum.LOAITAILIEU_SUA.getText() + "," + QuyenEnum.LOAITAILIEU_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.LOAITAILIEU_LIETKE.name() + "," + QuyenEnum.LOAITAILIEU_XEM.name() + "," + QuyenEnum.LOAITAILIEU_THEM.name() + "," + QuyenEnum.LOAITAILIEU_SUA.name() + "," + QuyenEnum.LOAITAILIEU_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Chức vụ");
		quyenObj.put("tenQuyen", QuyenEnum.CHUCVU_LIETKE.getText() + "," + QuyenEnum.CHUCVU_XEM.getText() + "," + QuyenEnum.CHUCVU_THEM.getText() + "," + QuyenEnum.CHUCVU_SUA.getText() + "," + QuyenEnum.CHUCVU_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.CHUCVU_LIETKE.name() + "," + QuyenEnum.CHUCVU_XEM.name() + "," + QuyenEnum.CHUCVU_THEM.name() + "," + QuyenEnum.CHUCVU_SUA.name() + "," + QuyenEnum.CHUCVU_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Quốc tịch");
		quyenObj.put("tenQuyen", QuyenEnum.QUOCTICH_LIETKE.getText() + "," + QuyenEnum.QUOCTICH_XEM.getText() + "," + QuyenEnum.QUOCTICH_THEM.getText() + "," + QuyenEnum.QUOCTICH_SUA.getText() + "," + QuyenEnum.QUOCTICH_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.QUOCTICH_LIETKE.name() + "," + QuyenEnum.QUOCTICH_XEM.name() + "," + QuyenEnum.QUOCTICH_THEM.name() + "," + QuyenEnum.QUOCTICH_SUA.name() + "," + QuyenEnum.QUOCTICH_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Cấp đơn vị hành chính");
		quyenObj.put("tenQuyen", QuyenEnum.CAPDONVIHANHCHINH_LIETKE.getText() + "," + QuyenEnum.CAPDONVIHANHCHINH_XEM.getText() + "," + QuyenEnum.CAPDONVIHANHCHINH_THEM.getText() + "," + QuyenEnum.CAPDONVIHANHCHINH_SUA.getText() + "," + QuyenEnum.CAPDONVIHANHCHINH_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.CAPDONVIHANHCHINH_LIETKE.name() + "," + QuyenEnum.CAPDONVIHANHCHINH_XEM.name() + "," + QuyenEnum.CAPDONVIHANHCHINH_THEM.name() + "," + QuyenEnum.CAPDONVIHANHCHINH_SUA.name() + "," + QuyenEnum.CAPDONVIHANHCHINH_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Đơn vị hành chính");
		quyenObj.put("tenQuyen", QuyenEnum.DONVIHANHCHINH_LIETKE.getText() + "," + QuyenEnum.DONVIHANHCHINH_XEM.getText() + "," + QuyenEnum.DONVIHANHCHINH_THEM.getText() + "," + QuyenEnum.DONVIHANHCHINH_SUA.getText() + "," + QuyenEnum.DONVIHANHCHINH_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.DONVIHANHCHINH_LIETKE.name() + "," + QuyenEnum.DONVIHANHCHINH_XEM.name() + "," + QuyenEnum.DONVIHANHCHINH_THEM.name() + "," + QuyenEnum.DONVIHANHCHINH_SUA.name() + "," + QuyenEnum.DONVIHANHCHINH_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Đơn vị ngoài hệ thống");
		quyenObj.put("tenQuyen", QuyenEnum.DONVINGOAIHETHONG_LIETKE.getText() + "," + QuyenEnum.DONVINGOAIHETHONG_XEM.getText() + "," + QuyenEnum.DONVINGOAIHETHONG_THEM.getText() + "," + QuyenEnum.DONVINGOAIHETHONG_SUA.getText() + "," + QuyenEnum.DONVINGOAIHETHONG_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.DONVINGOAIHETHONG_LIETKE.name() + "," + QuyenEnum.DONVINGOAIHETHONG_XEM.name() + "," + QuyenEnum.DONVINGOAIHETHONG_THEM.name() + "," + QuyenEnum.DONVINGOAIHETHONG_SUA.name() + "," + QuyenEnum.DONVINGOAIHETHONG_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Thông tin email");
		quyenObj.put("tenQuyen", QuyenEnum.THONGTINEMAIL_LIETKE.getText() + "," + QuyenEnum.THONGTINEMAIL_XEM.getText() + "," + QuyenEnum.THONGTINEMAIL_SUA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.THONGTINEMAIL_LIETKE.name() + "," + QuyenEnum.THONGTINEMAIL_XEM.name() + "," + QuyenEnum.THONGTINEMAIL_SUA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Loại cơ quan quản lý");
		quyenObj.put("tenQuyen", QuyenEnum.LOAICOQUANQUANLY_LIETKE.getText() + "," + QuyenEnum.LOAICOQUANQUANLY_XEM.getText() + "," + QuyenEnum.LOAICOQUANQUANLY_THEM.getText() + "," + QuyenEnum.LOAICOQUANQUANLY_SUA.getText() + "," + QuyenEnum.LOAICOQUANQUANLY_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.LOAICOQUANQUANLY_LIETKE.name() + "," + QuyenEnum.LOAICOQUANQUANLY_XEM.name() + "," + QuyenEnum.LOAICOQUANQUANLY_THEM.name() + "," + QuyenEnum.LOAICOQUANQUANLY_SUA.name() + "," + QuyenEnum.LOAICOQUANQUANLY_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Cấp cơ quan quản lý");
		quyenObj.put("tenQuyen", QuyenEnum.CAPCOQUANQUANLY_LIETKE.getText() + "," + QuyenEnum.CAPCOQUANQUANLY_XEM.getText() + "," + QuyenEnum.CAPCOQUANQUANLY_THEM.getText() + "," + QuyenEnum.CAPCOQUANQUANLY_SUA.getText() + "," + QuyenEnum.CAPCOQUANQUANLY_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.CAPCOQUANQUANLY_LIETKE.name() + "," + QuyenEnum.CAPCOQUANQUANLY_XEM.name() + "," + QuyenEnum.CAPCOQUANQUANLY_THEM.name() + "," + QuyenEnum.CAPCOQUANQUANLY_SUA.name() + "," + QuyenEnum.CAPCOQUANQUANLY_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Cơ quan quản lý");
		quyenObj.put("tenQuyen", QuyenEnum.COQUANQUANLY_LIETKE.getText() + "," + QuyenEnum.COQUANQUANLY_XEM.getText() + "," + QuyenEnum.COQUANQUANLY_THEM.getText() + "," + QuyenEnum.COQUANQUANLY_SUA.getText() + "," + QuyenEnum.COQUANQUANLY_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.COQUANQUANLY_LIETKE.name() + "," + QuyenEnum.COQUANQUANLY_XEM.name() + "," + QuyenEnum.COQUANQUANLY_THEM.name() + "," + QuyenEnum.COQUANQUANLY_SUA.name() + "," + QuyenEnum.COQUANQUANLY_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Tổ dân phố");
		quyenObj.put("tenQuyen", QuyenEnum.TODANPHO_LIETKE.getText() + "," + QuyenEnum.TODANPHO_XEM.getText() + "," + QuyenEnum.TODANPHO_THEM.getText() + "," + QuyenEnum.TODANPHO_SUA.getText() + "," + QuyenEnum.TODANPHO_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.TODANPHO_LIETKE.name() + "," + QuyenEnum.TODANPHO_XEM.name() + "," + QuyenEnum.TODANPHO_THEM.name() + "," + QuyenEnum.TODANPHO_SUA.name() + "," + QuyenEnum.TODANPHO_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Dân tộc");
		quyenObj.put("tenQuyen", QuyenEnum.DANTOC_LIETKE.getText() + "," + QuyenEnum.DANTOC_XEM.getText() + "," + QuyenEnum.DANTOC_THEM.getText() + "," + QuyenEnum.DANTOC_SUA.getText() + "," + QuyenEnum.DANTOC_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.DANTOC_LIETKE.name() + "," + QuyenEnum.DANTOC_XEM.name() + "," + QuyenEnum.DANTOC_THEM.name() + "," + QuyenEnum.DANTOC_SUA.name() + "," + QuyenEnum.DANTOC_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Lĩnh vực đơn thư");
		quyenObj.put("tenQuyen", QuyenEnum.LINHVUCDONTHU_LIETKE.getText() + "," + QuyenEnum.LINHVUCDONTHU_XEM.getText() + "," + QuyenEnum.LINHVUCDONTHU_THEM.getText() + "," + QuyenEnum.LINHVUCDONTHU_SUA.getText() + "," + QuyenEnum.LINHVUCDONTHU_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.LINHVUCDONTHU_LIETKE.name() + "," + QuyenEnum.LINHVUCDONTHU_XEM.name() + "," + QuyenEnum.LINHVUCDONTHU_THEM.name() + "," + QuyenEnum.LINHVUCDONTHU_SUA.name() + "," + QuyenEnum.LINHVUCDONTHU_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Công chức");
		quyenObj.put("tenQuyen", QuyenEnum.CONGCHUC_LIETKE.getText() + "," + QuyenEnum.CONGCHUC_XEM.getText() + "," + QuyenEnum.CONGCHUC_THEM.getText() + "," + QuyenEnum.CONGCHUC_SUA.getText() + "," + QuyenEnum.CONGCHUC_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.CONGCHUC_LIETKE.name() + "," + QuyenEnum.CONGCHUC_XEM.name() + "," + QuyenEnum.CONGCHUC_THEM.name() + "," + QuyenEnum.CONGCHUC_SUA.name() + "," + QuyenEnum.CONGCHUC_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Công dân");
		quyenObj.put("tenQuyen", QuyenEnum.CONGDAN_LIETKE.getText() + "," + QuyenEnum.CONGDAN_XEM.getText() + "," + QuyenEnum.CONGDAN_THEM.getText() + "," + QuyenEnum.CONGDAN_SUA.getText() + "," + QuyenEnum.CONGDAN_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.CONGDAN_LIETKE.name() + "," + QuyenEnum.CONGDAN_XEM.name() + "," + QuyenEnum.CONGDAN_THEM.name() + "," + QuyenEnum.CONGDAN_SUA.name() + "," + QuyenEnum.CONGDAN_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Vai trò");
		quyenObj.put("tenQuyen", QuyenEnum.VAITRO_LIETKE.getText() + "," + QuyenEnum.VAITRO_XEM.getText() + "," + QuyenEnum.VAITRO_THEM.getText() + "," + QuyenEnum.VAITRO_SUA.getText() + "," + QuyenEnum.VAITRO_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.VAITRO_LIETKE.name() + "," + QuyenEnum.VAITRO_XEM.name() + "," + QuyenEnum.VAITRO_THEM.name() + "," + QuyenEnum.VAITRO_SUA.name() + "," + QuyenEnum.VAITRO_XOA.name());
		quyenList.add(quyenObj);
		
//		quyenObj = new HashMap<>();
//		quyenObj.put("tenChucNang","Thời hạn");
//		quyenObj.put("tenQuyen", QuyenEnum.THOIHAN_LIETKE.getText() + "," + QuyenEnum.THOIHAN_XEM.getText() + "," + QuyenEnum.THOIHAN_THEM.getText() + "," + QuyenEnum.THOIHAN_SUA.getText() + "," + QuyenEnum.THOIHAN_XOA.getText());
//		quyenObj.put("giaTriQuyen", QuyenEnum.THOIHAN_LIETKE.name() + "," + QuyenEnum.THOIHAN_XEM.name() + "," + QuyenEnum.THOIHAN_THEM.name() + "," + QuyenEnum.THOIHAN_SUA.name() + "," + QuyenEnum.THOIHAN_XOA.name());
//		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Tham số");
		quyenObj.put("tenQuyen", QuyenEnum.THAMSO_LIETKE.getText() + "," + QuyenEnum.THAMSO_XEM.getText() + "," + QuyenEnum.THAMSO_THEM.getText() + "," + QuyenEnum.THAMSO_SUA.getText() + "," + QuyenEnum.THAMSO_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.THAMSO_LIETKE.name() + "," + QuyenEnum.THAMSO_XEM.name() + "," + QuyenEnum.THAMSO_THEM.name() + "," + QuyenEnum.THAMSO_SUA.name() + "," + QuyenEnum.THAMSO_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Sổ tiếp công dân");
		quyenObj.put("tenQuyen", QuyenEnum.SOTIEPCONGDAN_LIETKE.getText() + "," + QuyenEnum.SOTIEPCONGDAN_XEM.getText() + "," + QuyenEnum.SOTIEPCONGDAN_THEM.getText() + "," + QuyenEnum.SOTIEPCONGDAN_SUA.getText() + "," + QuyenEnum.SOTIEPCONGDAN_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.SOTIEPCONGDAN_LIETKE.name() + "," + QuyenEnum.SOTIEPCONGDAN_XEM.name() + "," + QuyenEnum.SOTIEPCONGDAN_THEM.name() + "," + QuyenEnum.SOTIEPCONGDAN_SUA.name() + "," + QuyenEnum.SOTIEPCONGDAN_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Xử lý đơn");
		quyenObj.put("tenQuyen", QuyenEnum.XULYDON_LIETKE.getText() + "," + QuyenEnum.XULYDON_XEM.getText() + "," + QuyenEnum.XULYDON_THEM.getText() + "," + QuyenEnum.XULYDON_SUA.getText() + "," + QuyenEnum.XULYDON_DINHCHI_RUTDON.getText() + "," + QuyenEnum.XULYDON_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.XULYDON_LIETKE.name() + "," + QuyenEnum.XULYDON_XEM.name() + "," + QuyenEnum.XULYDON_THEM.name() + "," + QuyenEnum.XULYDON_SUA.name() + "," + QuyenEnum.XULYDON_DINHCHI_RUTDON.name() + "," + QuyenEnum.XULYDON_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Giải quyết đơn");
		quyenObj.put("tenQuyen", QuyenEnum.GIAIQUYETDON_LIETKE.getText() + "," + QuyenEnum.GIAIQUYETDON_XEM.getText() + "," + QuyenEnum.GIAIQUYETDON_SUA.getText() + "," + QuyenEnum.GIAIQUYETDON_DINHCHI.getText() + "," + QuyenEnum.GIAIQUYETDON_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.GIAIQUYETDON_LIETKE.name() + "," + QuyenEnum.GIAIQUYETDON_XEM.name() + "," + QuyenEnum.GIAIQUYETDON_SUA.name() + "," + QuyenEnum.GIAIQUYETDON_DINHCHI.name() + "," + QuyenEnum.GIAIQUYETDON_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Thanh tra");
		quyenObj.put("tenQuyen", QuyenEnum.THANHTRA_LIETKE.getText() + "," + QuyenEnum.THANHTRA_XEM.getText() + "," + QuyenEnum.THANHTRA_THEM.getText() + "," + QuyenEnum.THANHTRA_SUA.getText() + "," + QuyenEnum.THANHTRA_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.THANHTRA_LIETKE.name() + "," + QuyenEnum.THANHTRA_XEM.name() + "," + QuyenEnum.THANHTRA_THEM.name() + "," + QuyenEnum.THANHTRA_SUA.name() + "," + QuyenEnum.THANHTRA_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Tổng hợp báo cáo");
		quyenObj.put("tenQuyen", QuyenEnum.TONGHOPBAOCAO_LIETKE.getText() + "," + QuyenEnum.TONGHOPBAOCAO_THONGKE.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.TONGHOPBAOCAO_LIETKE.name() + "," + QuyenEnum.TONGHOPBAOCAO_THONGKE.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Theo dõi giám sát");
		quyenObj.put("tenQuyen", QuyenEnum.THEODOIGIAMSAT_LIETKE.getText() + "," + QuyenEnum.THEODOIGIAMSAT_THONGKE.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.THEODOIGIAMSAT_LIETKE.name() + "," + QuyenEnum.THEODOIGIAMSAT_THONGKE.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Trạng thái ma trận");
		quyenObj.put("tenQuyen", QuyenEnum.STATE_LIETKE.getText() + "," + QuyenEnum.STATE_XEM.getText() + "," + QuyenEnum.STATE_THEM.getText() + "," + QuyenEnum.STATE_SUA.getText() + "," + QuyenEnum.STATE_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.STATE_LIETKE.name() + "," + QuyenEnum.STATE_XEM.name() + "," + QuyenEnum.STATE_THEM.name() + "," + QuyenEnum.STATE_SUA.name() + "," + QuyenEnum.STATE_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Trạng thái ma trận của đơn vị");
		quyenObj.put("tenQuyen", QuyenEnum.DONVIHASSTATE_LIETKE.getText() + "," + QuyenEnum.DONVIHASSTATE_XEM.getText() + "," + QuyenEnum.DONVIHASSTATE_THEM.getText() + "," + QuyenEnum.DONVIHASSTATE_SUA.getText() + "," + QuyenEnum.DONVIHASSTATE_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.DONVIHASSTATE_LIETKE.name() + "," + QuyenEnum.DONVIHASSTATE_XEM.name() + "," + QuyenEnum.DONVIHASSTATE_THEM.name() + "," + QuyenEnum.DONVIHASSTATE_SUA.name() + "," + QuyenEnum.DONVIHASSTATE_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Component xử lý quy trình");
		quyenObj.put("tenQuyen", QuyenEnum.FORM_LIETKE.getText() + "," + QuyenEnum.FORM_XEM.getText() + "," + QuyenEnum.FORM_THEM.getText() + "," + QuyenEnum.FORM_SUA.getText() + "," + QuyenEnum.FORM_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.FORM_LIETKE.name() + "," + QuyenEnum.FORM_XEM.name() + "," + QuyenEnum.FORM_THEM.name() + "," + QuyenEnum.FORM_SUA.name() + "," + QuyenEnum.FORM_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Quy trình ma trận");
		quyenObj.put("tenQuyen", QuyenEnum.PROCESS_LIETKE.getText() + "," + QuyenEnum.PROCESS_XEM.getText() + "," + QuyenEnum.PROCESS_THEM.getText() + "," + QuyenEnum.PROCESS_SUA.getText() + "," + QuyenEnum.PROCESS_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.PROCESS_LIETKE.name() + "," + QuyenEnum.PROCESS_XEM.name() + "," + QuyenEnum.PROCESS_THEM.name() + "," + QuyenEnum.PROCESS_SUA.name() + "," + QuyenEnum.PROCESS_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Transition ma trận");
		quyenObj.put("tenQuyen", QuyenEnum.TRANSITION_LIETKE.getText() + "," + QuyenEnum.TRANSITION_XEM.getText() + "," + QuyenEnum.TRANSITION_THEM.getText() + "," + QuyenEnum.TRANSITION_SUA.getText() + "," + QuyenEnum.TRANSITION_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.TRANSITION_LIETKE.name() + "," + QuyenEnum.TRANSITION_XEM.name() + "," + QuyenEnum.TRANSITION_THEM.name() + "," + QuyenEnum.TRANSITION_SUA.name() + "," + QuyenEnum.TRANSITION_XOA.name());
		quyenList.add(quyenObj);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("quyenList", quyenList);

		return new ResponseEntity<>(quyenList, HttpStatus.OK);
	}
	
}
