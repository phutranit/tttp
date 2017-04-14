package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.DanToc;
import vn.greenglobal.tttp.model.QDanToc;
import vn.greenglobal.tttp.repository.DanTocRepository;

public class DanTocService {

	public Predicate predicateFindAll(String tuKhoa, Long cha) {
		BooleanExpression predAll = QDanToc.danToc.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QDanToc.danToc.ten.containsIgnoreCase(tuKhoa)
					.or(QDanToc.danToc.tenKhac.containsIgnoreCase(tuKhoa))
					.or(QDanToc.danToc.moTa.containsIgnoreCase(tuKhoa)));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return QDanToc.danToc.daXoa.eq(false)
				.and(QDanToc.danToc.id.eq(id));
	}

	public boolean isExists(DanTocRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QDanToc.danToc.daXoa.eq(false)
					.and(QDanToc.danToc.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public DanToc deleteThamQuyenGiaiQuyet(DanTocRepository repo, Long id) {
		DanToc danToc = null;
		if (isExists(repo, id)) {
			danToc = new DanToc();
			danToc.setId(id);
			danToc.setDaXoa(true);
		}
		return danToc;
	}

	public boolean checkExistsData(DanTocRepository repo, String ten) {
		DanToc thamQuyenGiaiQuyet = repo.findOne(QDanToc.danToc.daXoa.eq(false)
				.and(QDanToc.danToc.ten.eq(ten)));
		return thamQuyenGiaiQuyet != null ? true : false;
	}

}
