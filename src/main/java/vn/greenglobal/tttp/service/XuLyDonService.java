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

	BooleanExpression base = QXuLyDon.xuLyDon.daXoa.eq(false);
	
	public XuLyDon predFindCurrent(XuLyDonRepository repo, Long id) {
		
		QXuLyDon xuLyDon = QXuLyDon.xuLyDon;
		BooleanExpression where = base.and(xuLyDon.don.id.eq(id));
		if (repo.exists(where)) {
			
			OrderSpecifier<Integer> sortOrder = xuLyDon.thuTuThucHien.desc();
			Iterable<XuLyDon> results = repo.findAll(where, sortOrder);	
			return Iterables.get(results, 0);
		}
		return null;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QXuLyDon.xuLyDon.id.eq(id));
	}

	public boolean isExists(XuLyDonRepository repo, Long id) {
		Predicate predicate = base.and(QXuLyDon.xuLyDon.don.id.eq(id));
		return repo.exists(predicate);
	}
}
