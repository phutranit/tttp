package vn.greenglobal.tttp.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.common.collect.Iterables;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.XuLyDonRepository;

public class XuLyDonService {
	public static transient final Logger LOG = LogManager.getLogger(XuLyDonService.class.getName());
	
	public XuLyDon predicateFindMax(XuLyDonRepository repo, Long id) {
		QXuLyDon qXuLyDon = QXuLyDon.xuLyDon;
		BooleanExpression where = qXuLyDon.daXoa.eq(false).and(qXuLyDon.don.id.eq(id));
		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();
		Iterable<XuLyDon> results = repo.findAll(where, sortOrder);
		return Iterables.get(results, 0);
	}
	
	public boolean isExists(XuLyDonRepository repo, Long id) {
		Predicate predicate = QXuLyDon.xuLyDon.daXoa.eq(false)
					.and(QXuLyDon.xuLyDon.don .id.eq(id));
		return repo.exists(predicate);
	}
}
