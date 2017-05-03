package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QTaiLieuVanThu;
import vn.greenglobal.tttp.model.TaiLieuVanThu;
import vn.greenglobal.tttp.repository.TaiLieuVanThuRepository;

@Component
public class TaiLieuVanThuService {

	BooleanExpression base = QTaiLieuVanThu.taiLieuVanThu.daXoa.eq(false);

	public Predicate predicateFindAll() {
		BooleanExpression predAll = base;
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QTaiLieuVanThu.taiLieuVanThu.id.eq(id));
	}

	public boolean isExists(TaiLieuVanThuRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QTaiLieuVanThu.taiLieuVanThu.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public TaiLieuVanThu delete(TaiLieuVanThuRepository repo, Long id) {
		TaiLieuVanThu taiLieuVanThu = repo.findOne(predicateFindOne(id));

		if (taiLieuVanThu != null) {
			taiLieuVanThu.setDaXoa(true);
		}

		return taiLieuVanThu;
	}

	public boolean checkExistsData(TaiLieuVanThuRepository repo, TaiLieuVanThu body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QTaiLieuVanThu.taiLieuVanThu.id.ne(body.getId()));
		}

		predAll = predAll.and(QTaiLieuVanThu.taiLieuVanThu.ten.eq(body.getTen()));
		TaiLieuVanThu taiLieuVanThu = repo.findOne(predAll);

		return taiLieuVanThu != null ? true : false;
	}

}
