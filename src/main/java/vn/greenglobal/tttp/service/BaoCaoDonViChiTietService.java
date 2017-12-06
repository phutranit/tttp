package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import vn.greenglobal.tttp.model.BaoCaoDonViChiTiet;
import vn.greenglobal.tttp.model.QBaoCaoDonViChiTiet;
import vn.greenglobal.tttp.repository.BaoCaoDonViChiTietRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class BaoCaoDonViChiTietService {
	
	@Autowired
	private BaoCaoDonViChiTietRepository baoCaoDonViChiTietRepository;

	BooleanExpression base = QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.daXoa.eq(false);

	public Predicate predicateFindAll(Long baoCaoDonViId, Long donViId) {
		BooleanExpression predAll = base;
		
		predAll = predAll.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.id.eq(baoCaoDonViId))
				.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.cha.isNull())
				.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.donVi.id.eq(donViId));

		return predAll;
	}
	
	public Predicate predicateFindAllBaoCaoDaChot(Long baoCaoDonViChiTietChaId) {
		BooleanExpression predAll = base;
		
		predAll = predAll.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.cha.id.eq(baoCaoDonViChiTietChaId));

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.id.eq(id));
	}
	
	public BaoCaoDonViChiTiet save(BaoCaoDonViChiTiet obj, Long congChucId) {
		return Utils.save(baoCaoDonViChiTietRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(BaoCaoDonViChiTiet obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(baoCaoDonViChiTietRepository, obj, congChucId, eass, status);		
	}

}
