package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.repository.ThongTinGiaiQuyetDonRepository;

@Component
public class ThongTinGiaiQuyetDonService {

	BooleanExpression base = QThongTinGiaiQuyetDon.thongTinGiaiQuyetDon.daXoa.eq(false);
	
	public boolean isExists(ThongTinGiaiQuyetDonRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QThongTinGiaiQuyetDon.thongTinGiaiQuyetDon.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}
	
	public Predicate predicateFindOne(Long id) {
		return base.and(QThongTinGiaiQuyetDon.thongTinGiaiQuyetDon.id.eq(id));
	}

	public Predicate predicateFindByDon(Long donId) {
		return base.and(QThongTinGiaiQuyetDon.thongTinGiaiQuyetDon.don.id.eq(donId));
	}
}
