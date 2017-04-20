package vn.greenglobal.tttp.service;

import java.util.Comparator;

import javax.persistence.criteria.Root;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;

import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.repository.XuLyDonRepository;

public class XuLyDonService {
	
	public Predicate predicateFindMax(Long id) {
		return (Predicate) QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.id.eq(id)).desc();
	}
	
	public boolean isExists(XuLyDonRepository repo, Long id) {
		Predicate predicate = QXuLyDon.xuLyDon.daXoa.eq(false)
					.and(QXuLyDon.xuLyDon.id.eq(id));
		return repo.exists(predicate);
	}
}
