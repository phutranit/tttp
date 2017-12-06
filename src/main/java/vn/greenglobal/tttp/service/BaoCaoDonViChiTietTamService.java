package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import vn.greenglobal.tttp.model.BaoCaoDonViChiTietTam;
import vn.greenglobal.tttp.model.QBaoCaoDonViChiTiet;
import vn.greenglobal.tttp.model.QBaoCaoDonViChiTietTam;
import vn.greenglobal.tttp.repository.BaoCaoDonViChiTietTamRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class BaoCaoDonViChiTietTamService {
	
	@Autowired
	private BaoCaoDonViChiTietTamRepository baoCaoDonViChiTietTamRepository;

	BooleanExpression base = QBaoCaoDonViChiTietTam.baoCaoDonViChiTietTam.daXoa.eq(false);

	
	public Predicate predicateFindAll(Long baoCaoDonViChiTietChaId) {
		BooleanExpression predAll = base;		
		predAll = predAll.and(QBaoCaoDonViChiTietTam.baoCaoDonViChiTietTam.baoCaoDonViChiTiet.cha.id.eq(baoCaoDonViChiTietChaId));
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.id.eq(id));
	}
	
	public BaoCaoDonViChiTietTam save(BaoCaoDonViChiTietTam obj, Long congChucId) {
		return Utils.save(baoCaoDonViChiTietTamRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(BaoCaoDonViChiTietTam obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(baoCaoDonViChiTietTamRepository, obj, congChucId, eass, status);		
	}

}
