package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.KyBaoCaoTongHopEnum;
import vn.greenglobal.tttp.model.QBaoCaoTongHop;
import vn.greenglobal.tttp.repository.BaoCaoTongHopRepository;

public class BaoCaoTongHopService {
	
	@Autowired
	private BaoCaoTongHopRepository baoCaoTongHopRepository;
	
	BooleanExpression base = QBaoCaoTongHop.baoCaoTongHop.daXoa.eq(false);
	
	public Predicate predicateFindAll(Long donViId, Integer namBaoCao, KyBaoCaoTongHopEnum kyBaoCao) {
		BooleanExpression predAll = base;
		
		if(donViId != null && donViId > 0) {
			predAll = predAll.and(QBaoCaoTongHop.baoCaoTongHop.donVi.id.eq(donViId));
		}
		
		if(namBaoCao != null && namBaoCao > 0) {
			predAll = predAll.and(QBaoCaoTongHop.baoCaoTongHop.namBaoCao.eq(namBaoCao));
		}
		
		if(kyBaoCao != null) {
			predAll = predAll.and(QBaoCaoTongHop.baoCaoTongHop.kyBaoCao.eq(kyBaoCao));
		}
		
		return predAll;
	}

}
