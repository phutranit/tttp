package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QGiaiQuyetDon;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;

@Component
public class GiaiQuyetDonService {

	BooleanExpression base = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false);
	
	public boolean isExists(GiaiQuyetDonRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QGiaiQuyetDon.giaiQuyetDon.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}
	
	public Predicate predicateFindOne(Long id) {
		return base.and(QGiaiQuyetDon.giaiQuyetDon.id.eq(id));
	}

}
