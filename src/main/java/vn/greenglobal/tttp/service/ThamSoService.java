package vn.greenglobal.tttp.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QThamSo;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class ThamSoService {

	@Autowired
	private ThamSoRepository thamSoRepository;
	
	BooleanExpression base = QThamSo.thamSo.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QThamSo.thamSo.ten.containsIgnoreCase(tuKhoa.trim())
					.or(QThamSo.thamSo.giaTri.containsIgnoreCase(tuKhoa.trim())));
		}

		return predAll;
	}

	public Predicate predicateFindTen(String ten) {
		return base.and(QThamSo.thamSo.ten.eq(ten));
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QThamSo.thamSo.id.eq(id));
	}

	public boolean isExists(ThamSoRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QThamSo.thamSo.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public ThamSo delete(ThamSoRepository repo, Long id) {
		ThamSo thamSo = repo.findOne(predicateFindOne(id));

		if (thamSo != null) {
			thamSo.setDaXoa(true);
		}

		return thamSo;
	}

	public boolean checkExistsData(ThamSoRepository repo, ThamSo body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QThamSo.thamSo.id.ne(body.getId()));
		}

		predAll = predAll.and(QThamSo.thamSo.ten.eq(body.getTen()));
		ThamSo thamSo = repo.findOne(predAll);

		return thamSo != null ? true : false;
	}
	
	public ThamSo save(ThamSo obj, Long congChucId) {
		return Utils.save(thamSoRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(ThamSo obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(thamSoRepository, obj, congChucId, eass, status);		
	}

}
