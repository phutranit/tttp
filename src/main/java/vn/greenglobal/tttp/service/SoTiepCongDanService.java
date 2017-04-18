package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;

import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;

public class SoTiepCongDanService {
	
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
