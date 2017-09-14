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

	BooleanExpression base = QDoiTuongViPham.doiTuongViPham.daXoa.eq(false);

	public Predicate predicateFindOne(Long id) {
		return base.and(QDoiTuongViPham.doiTuongViPham.id.eq(id));
	}

	public boolean isExists(DoiTuongViPhamRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QDoiTuongViPham.doiTuongViPham.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public DoiTuongViPham delete(DoiTuongViPhamRepository repo, Long id) {
		DoiTuongViPham doiTuongViPham = repo.findOne(predicateFindOne(id));

		if (doiTuongViPham != null) {
			doiTuongViPham.setDaXoa(true);
		}

		return doiTuongViPham;
	}

	public boolean checkExistsData(DoiTuongViPhamRepository repo, DoiTuongViPham body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QDoiTuongViPham.doiTuongViPham.id.ne(body.getId()));
		}

		predAll = predAll.and(QDoiTuongViPham.doiTuongViPham.ten.eq(body.getTen()));
		DoiTuongViPham doiTuongViPham = repo.findOne(predAll);

		return doiTuongViPham != null ? true : false;
	}
	
	public DoiTuongViPham save(DoiTuongViPham obj, Long congChucId) {
		return Utils.save(doiTuongViPhamRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(DoiTuongViPham obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(doiTuongViPhamRepository, obj, congChucId, eass, status);		
	}

}
