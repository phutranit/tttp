package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QVuViec;
import vn.greenglobal.tttp.model.VuViec;
import vn.greenglobal.tttp.repository.VuViecRepository;

public class VuViecService {

	public Predicate predicateFindAll(String ten) {
		BooleanExpression predAll = QVuViec.vuViec.daXoa.eq(false);
		if (ten != null && !"".equals(ten)) {
			predAll = predAll.and(QVuViec.vuViec.ten.eq(ten));
		}
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return QVuViec.vuViec.daXoa.eq(false).and(QVuViec.vuViec.id.eq(id));
	}

	public boolean isExists(VuViecRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QVuViec.vuViec.daXoa.eq(false).and(QVuViec.vuViec.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public VuViec deleteVuViec(VuViecRepository repo, Long id) {
		VuViec vuViec = null;
		if (isExists(repo, id)) {
			vuViec = new VuViec();
			vuViec.setId(id);
			vuViec.setDaXoa(true);
		}
		return vuViec;
	}

	public boolean checkExistsData(VuViecRepository repo, String ten) {
		VuViec vuViec = repo.findOne(QVuViec.vuViec.daXoa.eq(false).and(QVuViec.vuViec.ten.eq(ten)));
		return vuViec != null ? true : false;
	}

}
