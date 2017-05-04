package vn.greenglobal.tttp.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.LoaiTaiLieu;
import vn.greenglobal.tttp.model.QLoaiTaiLieu;
import vn.greenglobal.tttp.model.QTaiLieuBangChung;
import vn.greenglobal.tttp.model.QTaiLieuVanThu;
import vn.greenglobal.tttp.model.TaiLieuBangChung;
import vn.greenglobal.tttp.model.TaiLieuVanThu;
import vn.greenglobal.tttp.repository.LoaiTaiLieuRepository;
import vn.greenglobal.tttp.repository.TaiLieuBangChungRepository;
import vn.greenglobal.tttp.repository.TaiLieuVanThuRepository;

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

	public boolean checkUsedData(TaiLieuBangChungRepository taiLieuBangChungRepository,
			TaiLieuVanThuRepository taiLieuVanThuRepository, Long id) {
		List<TaiLieuBangChung> taiLieuBangChungList = (List<TaiLieuBangChung>) taiLieuBangChungRepository
				.findAll(QTaiLieuBangChung.taiLieuBangChung.daXoa.eq(false)
						.and(QTaiLieuBangChung.taiLieuBangChung.loaiTaiLieu.id.eq(id)));

		if (taiLieuBangChungList != null && taiLieuBangChungList.size() > 0) {
			return true;
		}

		return false;
	}

}
