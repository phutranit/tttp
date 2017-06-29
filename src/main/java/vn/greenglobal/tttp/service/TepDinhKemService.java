package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QTepDinhKem;
import vn.greenglobal.tttp.model.TepDinhKem;
import vn.greenglobal.tttp.repository.TepDinhKemRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class TepDinhKemService {
	
	@Autowired
	private TepDinhKemRepository tepDinhKemRepository;

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
	
	public TepDinhKem save(TepDinhKem obj, Long congChucId) {
		return Utils.save(tepDinhKemRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(TepDinhKem obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(tepDinhKemRepository, obj, congChucId, eass, status);		
	}

}
