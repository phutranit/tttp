package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.DanToc;
import vn.greenglobal.tttp.model.QDanToc;
import vn.greenglobal.tttp.repository.DanTocRepository;

public class DanTocService {

	public Predicate predicateFindAll(String tuKhoa, Long cha) {
		BooleanExpression predAll = QDanToc.danToc.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(
					QDanToc.danToc.ten.containsIgnoreCase(tuKhoa).or(QDanToc.danToc.tenKhac.containsIgnoreCase(tuKhoa))
							.or(QDanToc.danToc.moTa.containsIgnoreCase(tuKhoa)));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return QDanToc.danToc.daXoa.eq(false).and(QDanToc.danToc.id.eq(id));
	}

	public boolean isExists(DanTocRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QDanToc.danToc.daXoa.eq(false).and(QDanToc.danToc.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public DanToc deleteDanToc(DanTocRepository repo, Long id) {
		DanToc danToc = repo.findOne(predicateFindOne(id));

		if (danToc != null) {
			danToc.setDaXoa(true);
		}

		return danToc;
	}

	public boolean checkExistsData(DanTocRepository repo, DanToc body) {
		BooleanExpression predAll = QDanToc.danToc.daXoa.eq(false);

		if (!body.isNew()) {
			predAll = predAll.and(QDanToc.danToc.id.ne(body.getId()));
		}

		predAll = predAll.and(QDanToc.danToc.ten.eq(body.getTen()));
		DanToc danToc = repo.findOne(predAll);

		return danToc != null ? true : false;
	}

}
