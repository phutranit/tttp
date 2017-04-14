package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import vn.greenglobal.tttp.model.QToDanPho;
import vn.greenglobal.tttp.model.ToDanPho;
import vn.greenglobal.tttp.repository.ToDanPhoRepository;

public class ToDanPhoService {

	public Predicate predicateFindAll(String tuKhoa, Long donViHanhChinh) {
		BooleanExpression predAll = QToDanPho.toDanPho.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QToDanPho.toDanPho.ten.containsIgnoreCase(tuKhoa)
					.or(QToDanPho.toDanPho.moTa.containsIgnoreCase(tuKhoa)));
		}

		if (donViHanhChinh != null && donViHanhChinh > 0) {
			predAll = predAll.and(QToDanPho.toDanPho.donViHanhChinh.id.eq(donViHanhChinh));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return QToDanPho.toDanPho.daXoa.eq(false)
				.and(QToDanPho.toDanPho.id.eq(id));
	}

	public boolean isExists(ToDanPhoRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QToDanPho.toDanPho.daXoa.eq(false)
					.and(QToDanPho.toDanPho.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public ToDanPho deleteThamQuyenGiaiQuyet(ToDanPhoRepository repo, Long id) {
		ToDanPho toDanPho = null;
		if (isExists(repo, id)) {
			toDanPho = new ToDanPho();
			toDanPho.setId(id);
			toDanPho.setDaXoa(true);
		}
		return toDanPho;
	}

	public boolean checkExistsData(ToDanPhoRepository repo, String ten) {
		ToDanPho toDanPho = repo.findOne(QToDanPho.toDanPho.daXoa.eq(false)
				.and(QToDanPho.toDanPho.ten.eq(ten)));
		return toDanPho != null ? true : false;
	}

}
