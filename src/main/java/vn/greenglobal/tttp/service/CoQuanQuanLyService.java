package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.QCoQuanQuanLy;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;

@Component
public class CoQuanQuanLyService {
	
	BooleanExpression base = QCoQuanQuanLy.coQuanQuanLy.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa, Long cha, Long capCoQuanQuanLy, Long donViHanhChinh,Boolean notCha) {

		BooleanExpression predAll = base;
		if (tuKhoa != null && !"".equals(tuKhoa) && !notCha) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.ma.containsIgnoreCase(tuKhoa)
					.or(QCoQuanQuanLy.coQuanQuanLy.ten.containsIgnoreCase(tuKhoa))
					.or(QCoQuanQuanLy.coQuanQuanLy.moTa.containsIgnoreCase(tuKhoa)));
		}

		if (cha != null && cha > 0 && !notCha) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.cha.id.eq(cha));
		}

		if (capCoQuanQuanLy != null && capCoQuanQuanLy > 0 && !notCha) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(cha));
		}
		
		if (donViHanhChinh != null && donViHanhChinh > 0 && !notCha) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.donViHanhChinh.id.eq(donViHanhChinh));
		}
		
		if (donViHanhChinh == null && capCoQuanQuanLy == null && cha == null && notCha) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.cha.id.isNull());
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QCoQuanQuanLy.coQuanQuanLy.id.eq(id));
	}

	public boolean isExists(CoQuanQuanLyRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QCoQuanQuanLy.coQuanQuanLy.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public CoQuanQuanLy delete(CoQuanQuanLyRepository repo, Long id) {
		CoQuanQuanLy coQuanQuanLy = repo.findOne(predicateFindOne(id));

		if (coQuanQuanLy != null) {
			coQuanQuanLy.setDaXoa(true);
		}

		return coQuanQuanLy;
	}

	public boolean checkExistsData(CoQuanQuanLyRepository repo, CoQuanQuanLy body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.id.ne(body.getId()));
		}

		predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.ten.eq(body.getTen()));
		CoQuanQuanLy coQuanQuanLy = repo.findOne(predAll);

		return coQuanQuanLy != null ? true : false;
	}

}
