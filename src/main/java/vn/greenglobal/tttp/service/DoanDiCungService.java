package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.DoanDiCung;
import vn.greenglobal.tttp.model.QDoanDiCung;
import vn.greenglobal.tttp.repository.DoanDiCungRepository;

@Component
public class DoanDiCungService {

	public Predicate predicateFindOne(Long id) {
		return QDoanDiCung.doanDiCung.daXoa.eq(false).and(QDoanDiCung.doanDiCung.id.eq(id));
	}

	public boolean isExists(DoanDiCungRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QDoanDiCung.doanDiCung.daXoa.eq(false).and(QDoanDiCung.doanDiCung.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public boolean checkExistsData(DoanDiCungRepository repo, DoanDiCung body) {
		BooleanExpression predAll = QDoanDiCung.doanDiCung.daXoa.eq(false);

		if (!body.isNew()) {
			predAll = predAll.and(QDoanDiCung.doanDiCung.id.ne(body.getId()));
		}
		DoanDiCung doanDiCung = repo.findOne(predAll);

		return doanDiCung != null ? true : false;
	}

	public DoanDiCung delete(DoanDiCungRepository repo, Long id) {
		DoanDiCung dcd = repo.findOne(predicateFindOne(id));

		if (dcd != null) {
			dcd.setDaXoa(true);
		}

		return dcd;
	}

}
