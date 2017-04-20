package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.LoaiTaiLieu;
import vn.greenglobal.tttp.model.QLoaiTaiLieu;
import vn.greenglobal.tttp.repository.LoaiTaiLieuRepository;

@Component
public class LoaiTaiLieuService {
	
	BooleanExpression base = QLoaiTaiLieu.loaiTaiLieu.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QLoaiTaiLieu.loaiTaiLieu.ten.containsIgnoreCase(tuKhoa)
					.or(QLoaiTaiLieu.loaiTaiLieu.moTa.containsIgnoreCase(tuKhoa)));
		}
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QLoaiTaiLieu.loaiTaiLieu.id.eq(id));
	}

	public boolean isExists(LoaiTaiLieuRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QLoaiTaiLieu.loaiTaiLieu.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public LoaiTaiLieu delete(LoaiTaiLieuRepository repo, Long id) {
		LoaiTaiLieu loaiTaiLieu = repo.findOne(predicateFindOne(id));

		if (loaiTaiLieu != null) {
			loaiTaiLieu.setDaXoa(true);
		}

		return loaiTaiLieu;
	}

	public boolean checkExistsData(LoaiTaiLieuRepository repo, LoaiTaiLieu body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QLoaiTaiLieu.loaiTaiLieu.id.ne(body.getId()));
		}

		predAll = predAll.and(QLoaiTaiLieu.loaiTaiLieu.ten.eq(body.getTen()));
		LoaiTaiLieu loaiTaiLieu = repo.findOne(predAll);

		return loaiTaiLieu != null ? true : false;
	}

}
