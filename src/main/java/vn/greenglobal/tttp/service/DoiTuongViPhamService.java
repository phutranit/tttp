package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.DoiTuongViPham;
import vn.greenglobal.tttp.model.QDoiTuongViPham;
import vn.greenglobal.tttp.repository.DoiTuongViPhamRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class DoiTuongViPhamService {
	
	@Autowired
	private DoiTuongViPhamRepository doiTuongViPhamRepository;

	public Predicate predicateFindOne(Long id) {
		return QDoiTuongViPham.doiTuongViPham.daXoa.eq(false).and(QDoiTuongViPham.doiTuongViPham.id.eq(id));
	}

	public boolean isExists(DoiTuongViPhamRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QDoiTuongViPham.doiTuongViPham.daXoa.eq(false).and(QDoiTuongViPham.doiTuongViPham.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public boolean checkExistsData(DoiTuongViPhamRepository repo, DoiTuongViPham body) {
		BooleanExpression predAll = QDoiTuongViPham.doiTuongViPham.daXoa.eq(false);

		if (!body.isNew()) {
			predAll = predAll.and(QDoiTuongViPham.doiTuongViPham.id.ne(body.getId()));
		}
		DoiTuongViPham doiTuongViPham = repo.findOne(predAll);

		return doiTuongViPham != null ? true : false;
	}

	public DoiTuongViPham delete(DoiTuongViPhamRepository repo, Long id) {
		DoiTuongViPham dtvp = repo.findOne(predicateFindOne(id));

		if (dtvp != null) {
			dtvp.setDaXoa(true);
		}

		return dtvp;
	}
	
	public DoiTuongViPham save(DoiTuongViPham obj, Long congChucId) {
		return Utils.save(doiTuongViPhamRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(DoiTuongViPham obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(doiTuongViPhamRepository, obj, congChucId, eass, status);		
	}

}
