package vn.greenglobal.tttp.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CongDan;
import vn.greenglobal.tttp.model.DanToc;
import vn.greenglobal.tttp.model.QCongDan;
import vn.greenglobal.tttp.model.QDanToc;
import vn.greenglobal.tttp.repository.CongDanRepository;
import vn.greenglobal.tttp.repository.DanTocRepository;

@Component
public class DanTocService {

	BooleanExpression base = QDanToc.danToc.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa, Long cha) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(
					QDanToc.danToc.ten.containsIgnoreCase(tuKhoa).or(QDanToc.danToc.tenKhac.containsIgnoreCase(tuKhoa))
							.or(QDanToc.danToc.moTa.containsIgnoreCase(tuKhoa)));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QDanToc.danToc.id.eq(id));
	}

	public boolean isExists(DanTocRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QDanToc.danToc.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public DanToc delete(DanTocRepository repo, Long id) {
		DanToc danToc = repo.findOne(predicateFindOne(id));

		if (danToc != null) {
			danToc.setDaXoa(true);
		}

		return danToc;
	}

	public boolean checkExistsData(DanTocRepository repo, DanToc body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QDanToc.danToc.id.ne(body.getId()));
		}

		predAll = predAll.and(QDanToc.danToc.ten.eq(body.getTen()));
		DanToc danToc = repo.findOne(predAll);

		return danToc != null ? true : false;
	}
	
	public boolean checkUsedData(CongDanRepository congDanRepository, Long id) {
		List<CongDan> danTocList = (List<CongDan>) congDanRepository
				.findAll(QCongDan.congDan.daXoa.eq(false).and(QCongDan.congDan.danToc.id.eq(id)));

		if (danTocList != null && danTocList.size() > 0) {
			return true;
		}

		return false;
	}

}
