package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QTaiLieuVanThu;
import vn.greenglobal.tttp.model.QTepDinhKem;
import vn.greenglobal.tttp.model.TaiLieuVanThu;
import vn.greenglobal.tttp.model.TepDinhKem;
import vn.greenglobal.tttp.repository.TaiLieuVanThuRepository;
import vn.greenglobal.tttp.repository.TepDinhKemRepository;

@Component
public class TepDinhKemService {

	BooleanExpression base = QTepDinhKem.tepDinhKem.daXoa.eq(false);

	public Predicate predicateFindAll() {
		BooleanExpression predAll = base;
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QTepDinhKem.tepDinhKem.id.eq(id));
	}

	public boolean isExists(TepDinhKemRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QTepDinhKem.tepDinhKem.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public TepDinhKem delete(TepDinhKemRepository repo, Long id) {
		TepDinhKem tepDinhKem = repo.findOne(predicateFindOne(id));

		if (tepDinhKem != null) {
			tepDinhKem.setDaXoa(true);
		}

		return tepDinhKem;
	}

}
