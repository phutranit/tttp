package vn.greenglobal.tttp.service;

import org.springframework.web.bind.annotation.RequestParam;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.repository.DonRepository;

public class DonService {
		
	public Predicate predicateFindAll(String maDon, 
			String tenNguoiDungDon, String cmndHoChieu, String phanLoaiDon, 
			String tiepNhanTuNgay,String tiepNhanDenNgay, String hanGiaiQuyetTuNgay,
			String hanGiaiQuyetDenNgay, String trinhTrangXuLy) {
		
		BooleanExpression predAll = QDon.don.daXoa.eq(false);
		return predAll;
		
	}
	
	public Predicate predicateFindOne(Long id) {
		return QDon.don.daXoa.eq(false).and(QDon.don.id.eq(id));
	}

	public boolean isExists(DonRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QDon.don.daXoa.eq(false).and(QDon.don.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public Don deleteDon(DonRepository repo, Long id) {
		Don don = null;
		if (isExists(repo, id)) {
			don = new Don();
			don.setId(id);
			don.setDaXoa(true);
		}
		return don;
	}
}
