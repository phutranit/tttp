package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QLichSuQuaTrinhXuLy;

@Component
public class LichSuQuaTrinhXuLyService {

	BooleanExpression base = QLichSuQuaTrinhXuLy.lichSuQuaTrinhXuLy.daXoa.eq(false);

	public Predicate predicateFindAll(Long donId) {
		BooleanExpression predAll = base.and(QLichSuQuaTrinhXuLy.lichSuQuaTrinhXuLy.don.id.eq(donId));
		return predAll;
	}
}
