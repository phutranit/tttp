package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.QNguoiDung;
import vn.greenglobal.tttp.repository.NguoiDungRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class NguoiDungService {
	
	@Autowired
	private NguoiDungRepository nguoiDungRepository;

	BooleanExpression base = QNguoiDung.nguoiDung.daXoa.eq(false);

	public Predicate predicateFindOne(Long id) {
		return base.and(QNguoiDung.nguoiDung.id.eq(id));
	}

	public boolean isExists(NguoiDungRepository repo, Long id) {
		if (id != null && id > 0) {
			return repo.exists(base.and(QNguoiDung.nguoiDung.id.eq(id)));
		}
		return false;
	}

	public boolean checkExistsData(NguoiDungRepository repo, NguoiDung body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QNguoiDung.nguoiDung.id.ne(body.getId()));
		}

		predAll = predAll.and(QNguoiDung.nguoiDung.email.eq(body.getEmail()));
		NguoiDung nguoiDung = repo.findOne(predAll);

		return nguoiDung != null ? true : false;
	}

	public NguoiDung deleteNguoiDung(NguoiDungRepository repo, Long id) {
		NguoiDung nguoiDung = repo.findOne(predicateFindOne(id));

		if (nguoiDung != null) {
			nguoiDung.setDaXoa(true);
		}

		return nguoiDung;
	}
	
	public NguoiDung save(NguoiDung obj, Long congChucId) {
		return Utils.save(nguoiDungRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(NguoiDung obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(nguoiDungRepository, obj, congChucId, eass, status);		
	}

}
