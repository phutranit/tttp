package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QQuocTich;
import vn.greenglobal.tttp.model.QuocTich;
import vn.greenglobal.tttp.repository.QuocTichRepository;

@Component
public class QuocTichService {
	
	BooleanExpression base = QQuocTich.quocTich.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QQuocTich.quocTich.ma.containsIgnoreCase(tuKhoa)
					.or(QQuocTich.quocTich.ten.containsIgnoreCase(tuKhoa))
					.or(QQuocTich.quocTich.moTa.containsIgnoreCase(tuKhoa)));
		}
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QQuocTich.quocTich.id.eq(id));
	}

	public boolean isExists(QuocTichRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QQuocTich.quocTich.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public QuocTich delete(QuocTichRepository repo, Long id) {
		QuocTich quocTich = repo.findOne(predicateFindOne(id));

		if (quocTich != null) {
			quocTich.setDaXoa(true);
		}

		return quocTich;
	}

	public boolean checkExistsData(QuocTichRepository repo, QuocTich body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QQuocTich.quocTich.id.ne(body.getId()));
		}

		predAll = predAll.and(QQuocTich.quocTich.ten.eq(body.getTen()));
		QuocTich quocTich = repo.findOne(predAll);

		return quocTich != null ? true : false;
	}

}
