package vn.greenglobal.tttp.service;

import javax.persistence.EntityManager;
import javax.validation.constraints.Max;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.common.collect.Iterables;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.EclipseLinkTemplates;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.impl.JPAQuery;

import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.XuLyDonRepository;

public class XuLyDonService {
	public static transient final Logger LOG = LogManager.getLogger(XuLyDonService.class.getName());
	
	BooleanExpression base = QXuLyDon.xuLyDon.daXoa.eq(false);
	
	public XuLyDon predicateFindMax(XuLyDonRepository repo, Long id) {
		QXuLyDon qXuLyDon = QXuLyDon.xuLyDon;
//		JPQLQuery query = new JPAQuery(EntityManager.class.cast(xuLyDon),EclipseLinkTemplates.DEFAULT);
//		
//		XuLyDon dataXuLyDon = query.from(xuLyDon).where(xuLyDon.don.id.eq(id).and())
		
		BooleanExpression where = base.and(qXuLyDon.don.id.eq(id));
		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();
		Iterable<XuLyDon> results = repo.findAll(where, sortOrder);
		return Iterables.get(results, 0);
	}
	
	public Predicate predicateFindOne(Long id) {
		return base.and(QXuLyDon.xuLyDon.id.eq(id));
	}
	
	public boolean isExists(XuLyDonRepository repo, Long id) {
		Predicate predicate = base.and(QXuLyDon.xuLyDon.don .id.eq(id));
		return repo.exists(predicate);
	}
}
