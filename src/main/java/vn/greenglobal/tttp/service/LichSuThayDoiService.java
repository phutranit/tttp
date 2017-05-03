package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.DoiTuongThayDoiEnum;
import vn.greenglobal.tttp.model.QLichSuThayDoi;

@Component
public class LichSuThayDoiService {

	BooleanExpression base = QLichSuThayDoi.lichSuThayDoi.daXoa.eq(false);

	public Predicate predicateFindAll(DoiTuongThayDoiEnum doiTuongThayDoi, Long idDoiTuong) {
		BooleanExpression predAll = base;
		predAll = predAll.and(QLichSuThayDoi.lichSuThayDoi.doiTuongThayDoi.eq(doiTuongThayDoi))
				.and(QLichSuThayDoi.lichSuThayDoi.idDoiTuong.eq(idDoiTuong));
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QLichSuThayDoi.lichSuThayDoi.id.eq(id));
	}
}
