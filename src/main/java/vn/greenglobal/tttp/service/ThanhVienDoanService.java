package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QThanhVienDoan;
import vn.greenglobal.tttp.model.ThanhVienDoan;
import vn.greenglobal.tttp.repository.ThanhVienDoanRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class ThanhVienDoanService {
	
	@Autowired
	private ThanhVienDoanRepository thanhVienDoanRepository;

	public Predicate predicateFindOne(Long id) {
		return QThanhVienDoan.thanhVienDoan.daXoa.eq(false).and(QThanhVienDoan.thanhVienDoan.id.eq(id));
	}

	public boolean isExists(ThanhVienDoanRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QThanhVienDoan.thanhVienDoan.daXoa.eq(false).and(QThanhVienDoan.thanhVienDoan.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public boolean checkExistsData(ThanhVienDoanRepository repo, ThanhVienDoan body) {
		BooleanExpression predAll = QThanhVienDoan.thanhVienDoan.daXoa.eq(false);

		if (!body.isNew()) {
			predAll = predAll.and(QThanhVienDoan.thanhVienDoan.id.ne(body.getId()));
		}
		ThanhVienDoan thanhVienDoan = repo.findOne(predAll);

		return thanhVienDoan != null ? true : false;
	}

	public ThanhVienDoan delete(ThanhVienDoanRepository repo, Long id) {
		ThanhVienDoan tdv = repo.findOne(predicateFindOne(id));

		if (tdv != null) {
			tdv.setDaXoa(true);
		}

		return tdv;
	}
	
	public ThanhVienDoan save(ThanhVienDoan obj, Long congChucId) {
		return Utils.save(thanhVienDoanRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(ThanhVienDoan obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(thanhVienDoanRepository, obj, congChucId, eass, status);		
	}

}
