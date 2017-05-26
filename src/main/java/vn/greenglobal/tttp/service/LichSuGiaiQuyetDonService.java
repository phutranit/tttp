package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QLichSuGiaiQuyetDon;

@Component
public class LichSuGiaiQuyetDonService {

	BooleanExpression base = QLichSuGiaiQuyetDon.lichSuGiaiQuyetDon.daXoa.eq(false);

}
