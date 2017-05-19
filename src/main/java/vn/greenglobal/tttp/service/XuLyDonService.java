package vn.greenglobal.tttp.service;

import java.util.List;

import com.google.common.collect.Iterables;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.XuLyDonRepository;

public class XuLyDonService {

	QXuLyDon xuLyDon = QXuLyDon.xuLyDon;
	BooleanExpression base = xuLyDon.daXoa.eq(false);

	public XuLyDon predFindCurrent(XuLyDonRepository repo, Long id) {
		System.out.println("predFindCurrent: " + id);
		BooleanExpression where = base.and(xuLyDon.don.id.eq(id));
		System.out.println("where: "  +where);
		if (repo.exists(where)) {
			OrderSpecifier<Integer> sortOrder = xuLyDon.thuTuThucHien.desc();			
			List<XuLyDon> results = (List<XuLyDon>) repo.findAll(where, sortOrder);
			Long xldId = results.get(0).getId();
			return repo.findOne(xldId);
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
