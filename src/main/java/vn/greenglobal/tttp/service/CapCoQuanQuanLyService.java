package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CapCoQuanQuanLy;
import vn.greenglobal.tttp.model.QCapCoQuanQuanLy;
import vn.greenglobal.tttp.repository.CapCoQuanQuanLyRepository;

public class CapCoQuanQuanLyService {

	BooleanExpression base = QCapCoQuanQuanLy.capCoQuanQuanLy.daXoa.eq(false);
	
	public Predicate predicateFindAll(String tuKhoa, Long cha) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QCapCoQuanQuanLy.capCoQuanQuanLy.ma.containsIgnoreCase(tuKhoa)
					.or(QCapCoQuanQuanLy.capCoQuanQuanLy.ten.containsIgnoreCase(tuKhoa))
					.or(QCapCoQuanQuanLy.capCoQuanQuanLy.moTa.containsIgnoreCase(tuKhoa)));
		}

		if (cha != null && cha > 0) {
			predAll = predAll.and(QCapCoQuanQuanLy.capCoQuanQuanLy.cha.id.eq(cha));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QCapCoQuanQuanLy.capCoQuanQuanLy.id.eq(id));
	}

	public boolean isExists(CapCoQuanQuanLyRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QCapCoQuanQuanLy.capCoQuanQuanLy.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public CapCoQuanQuanLy deleteCapCoQuanQuanLy(CapCoQuanQuanLyRepository repo, Long id) {
		CapCoQuanQuanLy capCoQuanQuanLy = null;
		if (isExists(repo, id)) {
			capCoQuanQuanLy = new CapCoQuanQuanLy();
			capCoQuanQuanLy.setId(id);
			capCoQuanQuanLy.setDaXoa(true);
		}
		return capCoQuanQuanLy;
	}

	public boolean checkExistsData(CapCoQuanQuanLyRepository repo, CapCoQuanQuanLy body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QCapCoQuanQuanLy.capCoQuanQuanLy.id.ne(body.getId()));
		}

		predAll = predAll.and(QCapCoQuanQuanLy.capCoQuanQuanLy.ten.eq(body.getTen()));
		CapCoQuanQuanLy capCoQuanQuanLy = repo.findOne(predAll);

		return capCoQuanQuanLy != null ? true : false;
	}

}
