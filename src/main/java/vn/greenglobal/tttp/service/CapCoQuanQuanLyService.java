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

import vn.greenglobal.tttp.model.CapCoQuanQuanLy;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.QCapCoQuanQuanLy;
import vn.greenglobal.tttp.model.QCoQuanQuanLy;
import vn.greenglobal.tttp.repository.CapCoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class CapCoQuanQuanLyService {
	
	@Autowired
	private CapCoQuanQuanLyRepository capCoQuanQuanLyRepository;

	BooleanExpression base = QCapCoQuanQuanLy.capCoQuanQuanLy.daXoa.eq(false);
	
	public Predicate predicateFindAll() {
		BooleanExpression predAll = base;
		return predAll;
	}
	
	public Predicate predicateFindAll(String tuKhoa, Long cha) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QCapCoQuanQuanLy.capCoQuanQuanLy.ma.containsIgnoreCase(tuKhoa.trim())
					.or(QCapCoQuanQuanLy.capCoQuanQuanLy.tenSearch.containsIgnoreCase(Utils.unAccent(tuKhoa.trim())))
					.or(QCapCoQuanQuanLy.capCoQuanQuanLy.moTaSearch.containsIgnoreCase(Utils.unAccent(tuKhoa.trim()))));
		}

		if (cha != null && cha > 0) {
			predAll = predAll.and(QCapCoQuanQuanLy.capCoQuanQuanLy.cha.id.eq(cha));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QCapCoQuanQuanLy.capCoQuanQuanLy.id.eq(id));
	}

	public boolean isExists(CapCoQuanQuanLyRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QCapCoQuanQuanLy.capCoQuanQuanLy.id.eq(id));
			return repo.exists(predicate);
		}

		return false;
	}

	public CapCoQuanQuanLy delete(CapCoQuanQuanLyRepository repo, Long id) {
		CapCoQuanQuanLy capCoQuanQuanLy = repo.findOne(predicateFindOne(id));

		if (capCoQuanQuanLy != null) {
			capCoQuanQuanLy.setDaXoa(true);
		}

		return capCoQuanQuanLy;
	}

	public boolean checkExistsData(CapCoQuanQuanLyRepository repo, CapCoQuanQuanLy body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QCapCoQuanQuanLy.capCoQuanQuanLy.id.ne(body.getId()));
		}

		predAll = predAll.and(QCapCoQuanQuanLy.capCoQuanQuanLy.ten.eq(body.getTen()));
		if (body.getCha() != null) {
			predAll = predAll.and(QCapCoQuanQuanLy.capCoQuanQuanLy.cha.id.eq(body.getCha().getId()));
		} else {
			predAll = predAll.and(QCapCoQuanQuanLy.capCoQuanQuanLy.cha.isNull());
		}
		List<CapCoQuanQuanLy> capCoQuanQuanLys = (List<CapCoQuanQuanLy>) repo.findAll(predAll);

		return capCoQuanQuanLys != null && capCoQuanQuanLys.size() > 0 ? true : false;
	}

	public boolean checkUsedData(CapCoQuanQuanLyRepository repo, CoQuanQuanLyRepository repoCoQuanQuanLy, Long id) {
		List<CapCoQuanQuanLy> capCoQuanQuanLyList = (List<CapCoQuanQuanLy>) repo
				.findAll(base.and(QCapCoQuanQuanLy.capCoQuanQuanLy.cha.id.eq(id)));
		List<CoQuanQuanLy> coQuanQuanLyList = (List<CoQuanQuanLy>) repoCoQuanQuanLy.findAll(
				QCoQuanQuanLy.coQuanQuanLy.daXoa.eq(false).and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(id)));

		if ((capCoQuanQuanLyList != null && capCoQuanQuanLyList.size() > 0)
				|| (coQuanQuanLyList != null && coQuanQuanLyList.size() > 0)) {
			return true;
		}

		return false;
	}
	
	public Predicate predicateFindCapCoQuanDaGiaiQuyet(Long capCoQuanQuanLyTinhTP, Long capCoQuanQuanLySoBanNganh, Long capCoQuanQuanLyQuanHuyen, Long capCoQuanQuanLyPhuongXa) {
		BooleanExpression predAll = base;

		if (capCoQuanQuanLyTinhTP != null && capCoQuanQuanLyTinhTP > 0 && capCoQuanQuanLySoBanNganh != null
				&& capCoQuanQuanLySoBanNganh > 0 && capCoQuanQuanLyQuanHuyen != null && capCoQuanQuanLyQuanHuyen > 0
				&& capCoQuanQuanLyPhuongXa != null && capCoQuanQuanLyPhuongXa > 0) {
			predAll = predAll.and(QCapCoQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyTinhTP)
					.or(QCapCoQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLySoBanNganh))
					.or(QCapCoQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyQuanHuyen))
					.or(QCapCoQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyPhuongXa)));
		}

		return predAll;
	}
	
	public CapCoQuanQuanLy save(CapCoQuanQuanLy obj, Long congChucId) {
		return Utils.save(capCoQuanQuanLyRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(CapCoQuanQuanLy obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(capCoQuanQuanLyRepository, obj, congChucId, eass, status);		
	}

}
