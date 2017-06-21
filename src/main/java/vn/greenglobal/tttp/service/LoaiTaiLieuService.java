package vn.greenglobal.tttp.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.LoaiTaiLieu;
import vn.greenglobal.tttp.model.QLoaiTaiLieu;
import vn.greenglobal.tttp.model.QTaiLieuBangChung;
import vn.greenglobal.tttp.model.TaiLieuBangChung;
import vn.greenglobal.tttp.repository.LoaiTaiLieuRepository;
import vn.greenglobal.tttp.repository.TaiLieuBangChungRepository;
import vn.greenglobal.tttp.repository.TaiLieuVanThuRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class LoaiTaiLieuService {
	
	@Autowired
	private LoaiTaiLieuRepository loaiTaiLieuRepository;

	BooleanExpression base = QLoaiTaiLieu.loaiTaiLieu.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QLoaiTaiLieu.loaiTaiLieu.ten.containsIgnoreCase(tuKhoa.trim())
					.or(QLoaiTaiLieu.loaiTaiLieu.moTa.containsIgnoreCase(tuKhoa.trim())));
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
	
	public LoaiTaiLieu save(LoaiTaiLieu obj, Long congChucId) {
		return Utils.save(loaiTaiLieuRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(LoaiTaiLieu obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(loaiTaiLieuRepository, obj, congChucId, eass, status);		
	}

}
