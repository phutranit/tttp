package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.DoanDiCung;
import vn.greenglobal.tttp.model.QDoanDiCung;
import vn.greenglobal.tttp.repository.DoanDiCungRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class DoanDiCungService {
	
	@Autowired
	private DoanDiCungRepository doanDiCungRepository;

	public Predicate predicateFindOne(Long id) {
		return QDoanDiCung.doanDiCung.daXoa.eq(false).and(QDoanDiCung.doanDiCung.id.eq(id));
	}

	public boolean isExists(DoanDiCungRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QDoanDiCung.doanDiCung.daXoa.eq(false).and(QDoanDiCung.doanDiCung.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public boolean checkExistsData(DoanDiCungRepository repo, DoanDiCung body) {
		BooleanExpression predAll = QDoanDiCung.doanDiCung.daXoa.eq(false);

		if (!body.isNew()) {
			predAll = predAll.and(QDoanDiCung.doanDiCung.id.ne(body.getId()));
		}
		DoanDiCung doanDiCung = repo.findOne(predAll);

		return doanDiCung != null ? true : false;
	}

	public DoanDiCung delete(DoanDiCungRepository repo, Long id) {
		DoanDiCung dcd = repo.findOne(predicateFindOne(id));

		if (dcd != null) {
			dcd.setDaXoa(true);
		}

		return dcd;
	}
	
	public DoanDiCung save(DoanDiCung obj, Long congChucId) {
		return Utils.save(doanDiCungRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(DoanDiCung obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(doanDiCungRepository, obj, congChucId, eass, status);		
	}

}
