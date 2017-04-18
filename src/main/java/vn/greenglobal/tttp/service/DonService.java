package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QDon;

public class DonService {

	
//	public Predicate predicateFindAll(String tuKhoa, Long cha) {
//		BooleanExpression predAll = QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.daXoa.eq(false);
//		if (tuKhoa != null && !"".equals(tuKhoa)) {
//			predAll = predAll.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.ten.containsIgnoreCase(tuKhoa)
//					.or(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.moTa.containsIgnoreCase(tuKhoa)));
//		}
//
//		if (cha != null && cha > 0) {
//			predAll = predAll.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.cha.id.eq(cha));
//		}
//
//		return predAll;
//	}
	
	public Predicate predicateFindAll() {
		
		BooleanExpression predAll = QDon.don.daXoa.eq(false);
		return predAll;
		
	}
}
