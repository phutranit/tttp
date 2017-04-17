package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.QCoQuanQuanLy;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;

public class CoQuanQuanLyService {

	public Predicate predicateFindAll(String tuKhoa, Long cha, Long capCoQuanQuanLy) {
		BooleanExpression predAll = QCoQuanQuanLy.coQuanQuanLy.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.ma.containsIgnoreCase(tuKhoa)
					.or(QCoQuanQuanLy.coQuanQuanLy.ten.containsIgnoreCase(tuKhoa))
					.or(QCoQuanQuanLy.coQuanQuanLy.moTa.containsIgnoreCase(tuKhoa)));
		}

		if (cha != null && cha > 0) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.cha.id.eq(cha));
		}

		if (capCoQuanQuanLy != null && capCoQuanQuanLy > 0) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(cha));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return QCoQuanQuanLy.coQuanQuanLy.daXoa.eq(false).and(QCoQuanQuanLy.coQuanQuanLy.id.eq(id));
	}

	public boolean isExists(CoQuanQuanLyRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QCoQuanQuanLy.coQuanQuanLy.daXoa.eq(false).and(QCoQuanQuanLy.coQuanQuanLy.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public CoQuanQuanLy deleteCoQuanQuanLy(CoQuanQuanLyRepository repo, Long id) {
		CoQuanQuanLy coQuanQuanLy = null;
		if (isExists(repo, id)) {
			coQuanQuanLy = new CoQuanQuanLy();
			coQuanQuanLy.setId(id);
			coQuanQuanLy.setDaXoa(true);
		}
		return coQuanQuanLy;
	}

	public boolean checkExistsData(CoQuanQuanLyRepository repo, CoQuanQuanLy body) {
		BooleanExpression predAll = QCoQuanQuanLy.coQuanQuanLy.daXoa.eq(false);

		if (!body.isNew()) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.id.ne(body.getId()));
		}

		predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.ten.eq(body.getTen()));
		CoQuanQuanLy coQuanQuanLy = repo.findOne(predAll);

		return coQuanQuanLy != null ? true : false;
	}

}
