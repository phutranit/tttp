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
		quyenObj.put("tenQuyen", QuyenEnum.THAM_QUYEN_GIAI_QUYET_LIET_KE.getText() + "," + QuyenEnum.THAM_QUYEN_GIAI_QUYET_XEM.getText() + "," + QuyenEnum.THAM_QUYEN_GIAI_QUYET_THEM.getText() + "," + QuyenEnum.THAM_QUYEN_GIAI_QUYET_CHINH_SUA.getText() + "," + QuyenEnum.THAM_QUYEN_GIAI_QUYET_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.THAM_QUYEN_GIAI_QUYET_LIET_KE.name() + "," + QuyenEnum.THAM_QUYEN_GIAI_QUYET_XEM.name() + "," + QuyenEnum.THAM_QUYEN_GIAI_QUYET_THEM.name() + "," + QuyenEnum.THAM_QUYEN_GIAI_QUYET_CHINH_SUA.name() + "," + QuyenEnum.THAM_QUYEN_GIAI_QUYET_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Vụ việc");
		quyenObj.put("tenQuyen", QuyenEnum.VU_VIEC_LIET_KE.getText() + "," + QuyenEnum.VU_VIEC_XEM.getText() + "," + QuyenEnum.VU_VIEC_THEM.getText() + "," + QuyenEnum.VU_VIEC_CHINH_SUA.getText() + "," + QuyenEnum.VU_VIEC_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.VU_VIEC_LIET_KE.name() + "," + QuyenEnum.VU_VIEC_XEM.name() + "," + QuyenEnum.VU_VIEC_THEM.name() + "," + QuyenEnum.VU_VIEC_CHINH_SUA.name() + "," + QuyenEnum.VU_VIEC_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Loại tài liệu");
		quyenObj.put("tenQuyen", QuyenEnum.LOAI_TAI_LIEU_LIET_KE.getText() + "," + QuyenEnum.LOAI_TAI_LIEU_XEM.getText() + "," + QuyenEnum.LOAI_TAI_LIEU_THEM.getText() + "," + QuyenEnum.LOAI_TAI_LIEU_CHINH_SUA.getText() + "," + QuyenEnum.LOAI_TAI_LIEU_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.LOAI_TAI_LIEU_LIET_KE.name() + "," + QuyenEnum.LOAI_TAI_LIEU_XEM.name() + "," + QuyenEnum.LOAI_TAI_LIEU_THEM.name() + "," + QuyenEnum.LOAI_TAI_LIEU_CHINH_SUA.name() + "," + QuyenEnum.LOAI_TAI_LIEU_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Chức vụ");
		quyenObj.put("tenQuyen", QuyenEnum.CHUC_VU_LIET_KE.getText() + "," + QuyenEnum.CHUC_VU_XEM.getText() + "," + QuyenEnum.CHUC_VU_THEM.getText() + "," + QuyenEnum.CHUC_VU_CHINH_SUA.getText() + "," + QuyenEnum.CHUC_VU_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.CHUC_VU_LIET_KE.name() + "," + QuyenEnum.CHUC_VU_XEM.name() + "," + QuyenEnum.CHUC_VU_THEM.name() + "," + QuyenEnum.CHUC_VU_CHINH_SUA.name() + "," + QuyenEnum.CHUC_VU_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Quốc tịch");
		quyenObj.put("tenQuyen", QuyenEnum.QUOC_TICH_LIET_KE.getText() + "," + QuyenEnum.QUOC_TICH_XEM.getText() + "," + QuyenEnum.QUOC_TICH_THEM.getText() + "," + QuyenEnum.QUOC_TICH_CHINH_SUA.getText() + "," + QuyenEnum.QUOC_TICH_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.QUOC_TICH_LIET_KE.name() + "," + QuyenEnum.QUOC_TICH_XEM.name() + "," + QuyenEnum.QUOC_TICH_THEM.name() + "," + QuyenEnum.QUOC_TICH_CHINH_SUA.name() + "," + QuyenEnum.QUOC_TICH_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Cấp đơn vị hành chính");
		quyenObj.put("tenQuyen", QuyenEnum.CAP_DON_VI_HANH_CHINH_LIET_KE.getText() + "," + QuyenEnum.CAP_DON_VI_HANH_CHINH_XEM.getText() + "," + QuyenEnum.CAP_DON_VI_HANH_CHINH_THEM.getText() + "," + QuyenEnum.CAP_DON_VI_HANH_CHINH_CHINH_SUA.getText() + "," + QuyenEnum.CAP_DON_VI_HANH_CHINH_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.CAP_DON_VI_HANH_CHINH_LIET_KE.name() + "," + QuyenEnum.CAP_DON_VI_HANH_CHINH_XEM.name() + "," + QuyenEnum.CAP_DON_VI_HANH_CHINH_THEM.name() + "," + QuyenEnum.CAP_DON_VI_HANH_CHINH_CHINH_SUA.name() + "," + QuyenEnum.CAP_DON_VI_HANH_CHINH_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Đơn vị hành chính");
		quyenObj.put("tenQuyen", QuyenEnum.DON_VI_HANH_CHINH_LIET_KE.getText() + "," + QuyenEnum.DON_VI_HANH_CHINH_XEM.getText() + "," + QuyenEnum.DON_VI_HANH_CHINH_THEM.getText() + "," + QuyenEnum.DON_VI_HANH_CHINH_CHINH_SUA.getText() + "," + QuyenEnum.DON_VI_HANH_CHINH_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.DON_VI_HANH_CHINH_LIET_KE.name() + "," + QuyenEnum.DON_VI_HANH_CHINH_XEM.name() + "," + QuyenEnum.DON_VI_HANH_CHINH_THEM.name() + "," + QuyenEnum.DON_VI_HANH_CHINH_CHINH_SUA.name() + "," + QuyenEnum.DON_VI_HANH_CHINH_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Cấp cơ quan quản lý");
		quyenObj.put("tenQuyen", QuyenEnum.CAP_CO_QUAN_QUAN_LY_LIET_KE.getText() + "," + QuyenEnum.CAP_CO_QUAN_QUAN_LY_XEM.getText() + "," + QuyenEnum.CAP_CO_QUAN_QUAN_LY_THEM.getText() + "," + QuyenEnum.CAP_CO_QUAN_QUAN_LY_CHINH_SUA.getText() + "," + QuyenEnum.CAP_CO_QUAN_QUAN_LY_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.CAP_CO_QUAN_QUAN_LY_LIET_KE.name() + "," + QuyenEnum.CAP_CO_QUAN_QUAN_LY_XEM.name() + "," + QuyenEnum.CAP_CO_QUAN_QUAN_LY_THEM.name() + "," + QuyenEnum.CAP_CO_QUAN_QUAN_LY_CHINH_SUA.name() + "," + QuyenEnum.CAP_CO_QUAN_QUAN_LY_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Cơ quan quản lý");
		quyenObj.put("tenQuyen", QuyenEnum.CO_QUAN_QUAN_LY_LIET_KE.getText() + "," + QuyenEnum.CO_QUAN_QUAN_LY_XEM.getText() + "," + QuyenEnum.CO_QUAN_QUAN_LY_THEM.getText() + "," + QuyenEnum.CO_QUAN_QUAN_LY_CHINH_SUA.getText() + "," + QuyenEnum.CO_QUAN_QUAN_LY_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.CO_QUAN_QUAN_LY_LIET_KE.name() + "," + QuyenEnum.CO_QUAN_QUAN_LY_XEM.name() + "," + QuyenEnum.CO_QUAN_QUAN_LY_THEM.name() + "," + QuyenEnum.CO_QUAN_QUAN_LY_CHINH_SUA.name() + "," + QuyenEnum.CO_QUAN_QUAN_LY_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Tổ dân phố");
		quyenObj.put("tenQuyen", QuyenEnum.TO_DAN_PHO_LIET_KE.getText() + "," + QuyenEnum.TO_DAN_PHO_XEM.getText() + "," + QuyenEnum.TO_DAN_PHO_THEM.getText() + "," + QuyenEnum.TO_DAN_PHO_CHINH_SUA.getText() + "," + QuyenEnum.TO_DAN_PHO_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.TO_DAN_PHO_LIET_KE.name() + "," + QuyenEnum.TO_DAN_PHO_XEM.name() + "," + QuyenEnum.TO_DAN_PHO_THEM.name() + "," + QuyenEnum.TO_DAN_PHO_CHINH_SUA.name() + "," + QuyenEnum.TO_DAN_PHO_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Dân tộc");
		quyenObj.put("tenQuyen", QuyenEnum.DAN_TOC_LIET_KE.getText() + "," + QuyenEnum.DAN_TOC_XEM.getText() + "," + QuyenEnum.DAN_TOC_THEM.getText() + "," + QuyenEnum.DAN_TOC_CHINH_SUA.getText() + "," + QuyenEnum.DAN_TOC_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.DAN_TOC_LIET_KE.name() + "," + QuyenEnum.DAN_TOC_XEM.name() + "," + QuyenEnum.DAN_TOC_THEM.name() + "," + QuyenEnum.DAN_TOC_CHINH_SUA.name() + "," + QuyenEnum.DAN_TOC_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Lĩnh vực đơn thư");
		quyenObj.put("tenQuyen", QuyenEnum.LINH_VUC_DON_THU_LIET_KE.getText() + "," + QuyenEnum.LINH_VUC_DON_THU_XEM.getText() + "," + QuyenEnum.LINH_VUC_DON_THU_THEM.getText() + "," + QuyenEnum.LINH_VUC_DON_THU_CHINH_SUA.getText() + "," + QuyenEnum.LINH_VUC_DON_THU_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.LINH_VUC_DON_THU_LIET_KE.name() + "," + QuyenEnum.LINH_VUC_DON_THU_XEM.name() + "," + QuyenEnum.LINH_VUC_DON_THU_THEM.name() + "," + QuyenEnum.LINH_VUC_DON_THU_CHINH_SUA.name() + "," + QuyenEnum.LINH_VUC_DON_THU_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Công chức");
		quyenObj.put("tenQuyen", QuyenEnum.CONG_CHUC_LIET_KE.getText() + "," + QuyenEnum.CONG_CHUC_XEM.getText() + "," + QuyenEnum.CONG_CHUC_THEM.getText() + "," + QuyenEnum.CONG_CHUC_CHINH_SUA.getText() + "," + QuyenEnum.CONG_CHUC_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.CONG_CHUC_LIET_KE.name() + "," + QuyenEnum.CONG_CHUC_XEM.name() + "," + QuyenEnum.CONG_CHUC_THEM.name() + "," + QuyenEnum.CONG_CHUC_CHINH_SUA.name() + "," + QuyenEnum.CONG_CHUC_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Công dân");
		quyenObj.put("tenQuyen", QuyenEnum.CONG_DAN_LIET_KE.getText() + "," + QuyenEnum.CONG_DAN_XEM.getText() + "," + QuyenEnum.CONG_DAN_THEM.getText() + "," + QuyenEnum.CONG_DAN_CHINH_SUA.getText() + "," + QuyenEnum.CONG_DAN_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.CONG_DAN_LIET_KE.name() + "," + QuyenEnum.CONG_DAN_XEM.name() + "," + QuyenEnum.CONG_DAN_THEM.name() + "," + QuyenEnum.CONG_DAN_CHINH_SUA.name() + "," + QuyenEnum.CONG_DAN_XOA.name());
		quyenList.add(quyenObj);
		
		quyenObj = new HashMap<>();
		quyenObj.put("tenChucNang","Vai trò");
		quyenObj.put("tenQuyen", QuyenEnum.VAI_TRO_LIET_KE.getText() + "," + QuyenEnum.VAI_TRO_XEM.getText() + "," + QuyenEnum.VAI_TRO_THEM.getText() + "," + QuyenEnum.VAI_TRO_CHINH_SUA.getText() + "," + QuyenEnum.VAI_TRO_XOA.getText());
		quyenObj.put("giaTriQuyen", QuyenEnum.VAI_TRO_LIET_KE.name() + "," + QuyenEnum.VAI_TRO_XEM.name() + "," + QuyenEnum.VAI_TRO_THEM.name() + "," + QuyenEnum.VAI_TRO_CHINH_SUA.name() + "," + QuyenEnum.VAI_TRO_XOA.name());
		quyenList.add(quyenObj);
		
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("quyenList", quyenList);

		return new ResponseEntity<>(quyenList, HttpStatus.OK);
	}
	
}
