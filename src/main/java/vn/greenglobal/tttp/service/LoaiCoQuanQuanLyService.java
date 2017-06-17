package vn.greenglobal.tttp.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.LoaiCoQuanQuanLy;
import vn.greenglobal.tttp.model.QCoQuanQuanLy;
import vn.greenglobal.tttp.model.QLoaiCoQuanQuanLy;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.LoaiCoQuanQuanLyRepository;

@Component
public class LoaiCoQuanQuanLyService {

	BooleanExpression base = QLoaiCoQuanQuanLy.loaiCoQuanQuanLy.daXoa.eq(false);

	public Predicate predicateFindAll(String ten) {
		BooleanExpression predAll = base;
		if (ten != null && StringUtils.isNotBlank(ten.trim())) {
			predAll = predAll.and(QLoaiCoQuanQuanLy.loaiCoQuanQuanLy.ten.containsIgnoreCase(ten.trim())
					.or(QLoaiCoQuanQuanLy.loaiCoQuanQuanLy.moTa.containsIgnoreCase(ten.trim())));
		}
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QLoaiCoQuanQuanLy.loaiCoQuanQuanLy.id.eq(id));
	}

	public boolean isExists(LoaiCoQuanQuanLyRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QLoaiCoQuanQuanLy.loaiCoQuanQuanLy.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public LoaiCoQuanQuanLy delete(LoaiCoQuanQuanLyRepository repo, Long id) {
		LoaiCoQuanQuanLy loaiCoQuanQuanLy = repo.findOne(predicateFindOne(id));

		if (loaiCoQuanQuanLy != null) {
			loaiCoQuanQuanLy.setDaXoa(true);
		}

		return loaiCoQuanQuanLy;
	}

	public boolean checkExistsData(LoaiCoQuanQuanLyRepository repo, LoaiCoQuanQuanLy body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QLoaiCoQuanQuanLy.loaiCoQuanQuanLy.id.ne(body.getId()));
		}

		predAll = predAll.and(QLoaiCoQuanQuanLy.loaiCoQuanQuanLy.ten.eq(body.getTen()));
		List<LoaiCoQuanQuanLy> loaiCoQuanQuanLys = (List<LoaiCoQuanQuanLy>) repo.findAll(predAll);

		return loaiCoQuanQuanLys != null && loaiCoQuanQuanLys.size() > 0 ? true : false;
	}

	public boolean checkUsedData(CoQuanQuanLyRepository coQuanQuanLyRepository, Long id) {
		List<CoQuanQuanLy> coQuanQuanLyList = (List<CoQuanQuanLy>) coQuanQuanLyRepository
				.findAll(QCoQuanQuanLy.coQuanQuanLy.daXoa.eq(false).and(QCoQuanQuanLy.coQuanQuanLy.loaiCoQuanQuanLy.id.eq(id)));

		if (coQuanQuanLyList != null && coQuanQuanLyList.size() > 0) {
			return true;
		}

		return false;
	}

}
