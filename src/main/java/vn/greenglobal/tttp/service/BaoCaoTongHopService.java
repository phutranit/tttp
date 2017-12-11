package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.KyBaoCaoTongHopEnum;
import vn.greenglobal.tttp.model.BaoCaoDonVi;
import vn.greenglobal.tttp.model.BaoCaoTongHop;
import vn.greenglobal.tttp.model.QBaoCaoDonVi;
import vn.greenglobal.tttp.model.QBaoCaoTongHop;
import vn.greenglobal.tttp.repository.BaoCaoTongHopRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class BaoCaoTongHopService {
	
	@Autowired
	private BaoCaoTongHopRepository baoCaoTongHopRepository;
	
	BooleanExpression base = QBaoCaoTongHop.baoCaoTongHop.daXoa.eq(false);
	
	public Predicate predicateFindAll(Long donViId, Integer namBaoCao, KyBaoCaoTongHopEnum ky, Integer quy, Integer thang) {
		BooleanExpression predAll = base;
		
		if(donViId != null && donViId > 0) {
			predAll = predAll.and(QBaoCaoTongHop.baoCaoTongHop.donVi.id.eq(donViId));
		}
		
		if(namBaoCao != null && namBaoCao > 0) {
			predAll = predAll.and(QBaoCaoTongHop.baoCaoTongHop.namBaoCao.eq(namBaoCao));
		}
		
		if(ky != null) {
			predAll = predAll.and(QBaoCaoTongHop.baoCaoTongHop.kyBaoCao.eq(ky));
			
			if(ky == KyBaoCaoTongHopEnum.THEO_QUY) {
				predAll = predAll.and(QBaoCaoTongHop.baoCaoTongHop.quyBaoCao.eq(quy));
			}
			
			if(ky == KyBaoCaoTongHopEnum.THEO_THANG) {
				predAll = predAll.and(QBaoCaoTongHop.baoCaoTongHop.thangBaoCao.eq(thang));
			}
		}
		
		return predAll;
	}
	
	public Predicate predicateFindOne(Long id) {
		return base.and(QBaoCaoTongHop.baoCaoTongHop.id.eq(id));
	}
	
	public BaoCaoTongHop save(BaoCaoTongHop obj, Long congChucId) {
		return Utils.save(baoCaoTongHopRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(BaoCaoTongHop obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(baoCaoTongHopRepository, obj, congChucId, eass, status);
	}
}
