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
		
		quyenObj.put("tenChucNang","Thẩm quyền giải quyết");
		quyenObj.put("tenQuyen", QuyenEnum.THAMQUYENGIAIQUYET_LIETKE.getText() + "," + QuyenEnum.THAMQUYENGIAIQUYET_XEM.getText() + "," + QuyenEnum.THAMQUYENGIAIQUYET_THEM.getText() + "," + QuyenEnum.THAMQUYENGIAIQUYET_SUA.getText() + "," + QuyenEnum.THAMQUYENGIAIQUYET_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.THAMQUYENGIAIQUYET_LIETKE.name() + "," + QuyenEnum.THAMQUYENGIAIQUYET_XEM.name() + "," + QuyenEnum.THAMQUYENGIAIQUYET_THEM.name() + "," + QuyenEnum.THAMQUYENGIAIQUYET_SUA.name() + "," + QuyenEnum.THAMQUYENGIAIQUYET_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Vụ việc");
		quyenObj.put("tenQuyen", QuyenEnum.VUVIEC_LIETKE.getText() + "," + QuyenEnum.VUVIEC_XEM.getText() + "," + QuyenEnum.VUVIEC_THEM.getText() + "," + QuyenEnum.VUVIEC_SUA.getText() + "," + QuyenEnum.VUVIEC_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.VUVIEC_LIETKE.name() + "," + QuyenEnum.VUVIEC_XEM.name() + "," + QuyenEnum.VUVIEC_THEM.name() + "," + QuyenEnum.VUVIEC_SUA.name() + "," + QuyenEnum.VUVIEC_XOA.name());
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
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("quyenList", quyenList);

		return new ResponseEntity<>(quyenList, HttpStatus.OK);
	}
	
}
