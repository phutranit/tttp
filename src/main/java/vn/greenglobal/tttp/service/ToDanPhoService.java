package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QToDanPho;
import vn.greenglobal.tttp.model.ToDanPho;
import vn.greenglobal.tttp.repository.ToDanPhoRepository;

public class ToDanPhoService {

	public Predicate predicateFindAll(String tuKhoa, Long donViHanhChinh) {
		BooleanExpression predAll = QToDanPho.toDanPho.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QToDanPho.toDanPho.ten.containsIgnoreCase(tuKhoa)
					.or(QToDanPho.toDanPho.moTa.containsIgnoreCase(tuKhoa)));
		}

		if (donViHanhChinh != null && donViHanhChinh > 0) {
			predAll = predAll.and(QToDanPho.toDanPho.donViHanhChinh.id.eq(donViHanhChinh));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return QToDanPho.toDanPho.daXoa.eq(false).and(QToDanPho.toDanPho.id.eq(id));
	}

	public boolean isExists(ToDanPhoRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QToDanPho.toDanPho.daXoa.eq(false).and(QToDanPho.toDanPho.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public ToDanPho deleteToDanPho(ToDanPhoRepository repo, Long id) {
		ToDanPho toDanPho = repo.findOne(predicateFindOne(id));

		if (toDanPho != null) {
			toDanPho.setDaXoa(true);
		}

		return toDanPho;
	}

	public boolean checkExistsData(ToDanPhoRepository repo, ToDanPho body) {
		BooleanExpression predAll = QToDanPho.toDanPho.daXoa.eq(false);

		if (!body.isNew()) {
			predAll = predAll.and(QToDanPho.toDanPho.id.ne(body.getId()));
		}

		predAll = predAll.and(QToDanPho.toDanPho.ten.eq(body.getTen()));
		ToDanPho toDanPho = repo.findOne(predAll);

		return toDanPho != null ? true : false;
	}

}
