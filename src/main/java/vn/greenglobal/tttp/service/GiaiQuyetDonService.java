package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QGiaiQuyetDon;

@Component
public class GiaiQuyetDonService {

	BooleanExpression base = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false);

}
