package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.LoaiTaiLieu;
import vn.greenglobal.tttp.model.QLoaiTaiLieu;
import vn.greenglobal.tttp.repository.LoaiTaiLieuRepository;

public class LoaiTaiLieuService {

	public Predicate predicateFindAll(String tuKhoa) {
		BooleanExpression predAll = QLoaiTaiLieu.loaiTaiLieu.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QLoaiTaiLieu.loaiTaiLieu.ten.containsIgnoreCase(tuKhoa)
					.or(QLoaiTaiLieu.loaiTaiLieu.moTa.containsIgnoreCase(tuKhoa)));
		}
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return QLoaiTaiLieu.loaiTaiLieu.daXoa.eq(false).and(QLoaiTaiLieu.loaiTaiLieu.id.eq(id));
	}

	public boolean isExists(LoaiTaiLieuRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QLoaiTaiLieu.loaiTaiLieu.daXoa.eq(false).and(QLoaiTaiLieu.loaiTaiLieu.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public LoaiTaiLieu deleteLoaiTaiLieu(LoaiTaiLieuRepository repo, Long id) {
		LoaiTaiLieu loaiTaiLieu = null;
		if (isExists(repo, id)) {
			loaiTaiLieu = new LoaiTaiLieu();
			loaiTaiLieu.setId(id);
			loaiTaiLieu.setDaXoa(true);
		}
		return loaiTaiLieu;
	}

	public boolean checkExistsData(LoaiTaiLieuRepository repo, String ten) {
		LoaiTaiLieu loaiTaiLieu = repo
				.findOne(QLoaiTaiLieu.loaiTaiLieu.daXoa.eq(false).and(QLoaiTaiLieu.loaiTaiLieu.ten.eq(ten)));
		return loaiTaiLieu != null ? true : false;
	}

}
