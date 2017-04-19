package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.repository.DonCongDanRepository;

public class DonCongDanService {

	public  Predicate predicateFindAll(Long don) {
		BooleanExpression predAll = QDon_CongDan.don_CongDan.daXoa.eq(false);
		if (don != null && don > 0) {
			QDon_CongDan.don_CongDan.don.id.eq(don);
		}
		return predAll;
	}
	
	
	public Predicate predicateFindOne(Long id) {
		return QDon_CongDan.don_CongDan.daXoa.eq(false).and(QDon_CongDan.don_CongDan.id.eq(id));
	}

	public boolean isExists(DonCongDanRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QDon_CongDan.don_CongDan.daXoa.eq(false)
					.and(QDon_CongDan.don_CongDan.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public Don_CongDan deleteDonCongDan(DonCongDanRepository repo, Long id) {
		Don_CongDan donCongDan = null;
		if (isExists(repo, id)) {
			donCongDan = new Don_CongDan();
			donCongDan.setId(id);
			donCongDan.setDaXoa(true);
		}
		return donCongDan;
	}
	
	public boolean checkExistsData(DonCongDanRepository repo, Don_CongDan body) {
		BooleanExpression predAll = QDon_CongDan.don_CongDan.daXoa.eq(false);

		if (!body.isNew()) {
			predAll = predAll.and(QDon_CongDan.don_CongDan.id.ne(body.getId()));
		}
		Don_CongDan donCongDan = repo.findOne(predAll);
		
		return donCongDan != null ? true : false;
	}
}
