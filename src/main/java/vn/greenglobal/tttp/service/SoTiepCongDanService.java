package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;

public class SoTiepCongDanService {
	/*public Predicate predicateFindAll(String ten) {
		
		BooleanExpression predAll = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false);
		if (ten != null && !"".equals(ten)) {
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ten.eq(ten));
		}
		return predAll;
	}*/

	public Predicate predicateFindOne(Long id) {
		return QSoTiepCongDan.soTiepCongDan.daXoa.eq(false).and(QSoTiepCongDan.soTiepCongDan.id.eq(id));
	}

	public boolean isExists(SoTiepCongDanRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false).and(QSoTiepCongDan.soTiepCongDan.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public SoTiepCongDan deleteSoTiepCongDan(SoTiepCongDanRepository repo, Long id) {
		SoTiepCongDan stCongDan = null;
		if (isExists(repo, id)) {
			stCongDan = new SoTiepCongDan();
			stCongDan.setId(id);
			stCongDan.setDaXoa(true);
		}
		return stCongDan;
	}
}
