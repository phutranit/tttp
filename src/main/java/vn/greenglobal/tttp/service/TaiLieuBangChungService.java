package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QTaiLieuBangChung;
import vn.greenglobal.tttp.model.TaiLieuBangChung;
import vn.greenglobal.tttp.repository.TaiLieuBangChungRepository;

public class TaiLieuBangChungService {

	public Predicate predicateFindAll() {
		BooleanExpression predAll = QTaiLieuBangChung.taiLieuBangChung.daXoa.eq(false);		

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return QTaiLieuBangChung.taiLieuBangChung.daXoa.eq(false).and(QTaiLieuBangChung.taiLieuBangChung.id.eq(id));
	}

	public boolean isExists(TaiLieuBangChungRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QTaiLieuBangChung.taiLieuBangChung.daXoa.eq(false).and(QTaiLieuBangChung.taiLieuBangChung.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public TaiLieuBangChung deleteTaiLieuBangChung(TaiLieuBangChungRepository repo, Long id) {
		TaiLieuBangChung taiLieuBangChung = repo.findOne(predicateFindOne(id));

		if (taiLieuBangChung != null) {
			taiLieuBangChung.setDaXoa(true);
		}

		return taiLieuBangChung;
	}

	public boolean checkExistsData(TaiLieuBangChungRepository repo, TaiLieuBangChung body) {
		BooleanExpression predAll = QTaiLieuBangChung.taiLieuBangChung.daXoa.eq(false);

		if (!body.isNew()) {
			predAll = predAll.and(QTaiLieuBangChung.taiLieuBangChung.id.ne(body.getId()));
		}

		predAll = predAll.and(QTaiLieuBangChung.taiLieuBangChung.ten.eq(body.getTen()));
		TaiLieuBangChung taiLieuBangChung = repo.findOne(predAll);

		return taiLieuBangChung != null ? true : false;
	}

}
