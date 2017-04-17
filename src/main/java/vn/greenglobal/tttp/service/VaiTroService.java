package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QVaiTro;
import vn.greenglobal.tttp.model.VaiTro;
import vn.greenglobal.tttp.repository.VaiTroRepository;

@Component
public class VaiTroService {

	BooleanExpression basePrediCate = QVaiTro.vaiTro.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa) {
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			basePrediCate = basePrediCate.and(QVaiTro.vaiTro.ten.containsIgnoreCase(tuKhoa));
		}

		return basePrediCate;
	}

	public Predicate predicateFindOne(Long id) {
		return basePrediCate.and(QVaiTro.vaiTro.id.eq(id));
	}

	public boolean isExists(VaiTroRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = basePrediCate.and(QVaiTro.vaiTro.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public VaiTro delete(VaiTroRepository repo, Long id) {
		VaiTro vaiTro = repo.findOne(predicateFindOne(id));
		if (vaiTro != null) {
			vaiTro.setDaXoa(true);
		}
		return vaiTro;
	}

	public boolean checkExistsData(VaiTroRepository repo, VaiTro body) {
		if (!body.isNew()) {
			basePrediCate = basePrediCate.and(QVaiTro.vaiTro.id.ne(body.getId()));
		}

		basePrediCate = basePrediCate.and(QVaiTro.vaiTro.ten.eq(body.getTen()));
		VaiTro vaiTro = repo.findOne(basePrediCate);

		return vaiTro != null ? true : false;
	}

}
