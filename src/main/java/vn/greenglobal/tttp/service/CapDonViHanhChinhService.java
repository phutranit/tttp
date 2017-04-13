package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CapDonViHanhChinh;
import vn.greenglobal.tttp.model.QCapDonViHanhChinh;
import vn.greenglobal.tttp.repository.CapDonViHanhChinhRepository;

public class CapDonViHanhChinhService {

	public Predicate predicateFindAll(String ten) {
		BooleanExpression predAll = QCapDonViHanhChinh.capDonViHanhChinh.daXoa.eq(false);
		if (ten != null && !"".equals(ten)) {
			predAll = predAll.and(QCapDonViHanhChinh.capDonViHanhChinh.ten.containsIgnoreCase(ten)
					.or(QCapDonViHanhChinh.capDonViHanhChinh.ma.containsIgnoreCase(ten)));
		}
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return QCapDonViHanhChinh.capDonViHanhChinh.daXoa.eq(false).and(QCapDonViHanhChinh.capDonViHanhChinh.id.eq(id));
	}

	public boolean isExists(CapDonViHanhChinhRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QCapDonViHanhChinh.capDonViHanhChinh.daXoa.eq(false)
					.and(QCapDonViHanhChinh.capDonViHanhChinh.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public CapDonViHanhChinh deleteCapDonViHanhChinh(CapDonViHanhChinhRepository repo, Long id) {
		CapDonViHanhChinh capDonViHanhChinh = null;
		if (isExists(repo, id)) {
			capDonViHanhChinh = new CapDonViHanhChinh();
			capDonViHanhChinh.setId(id);
			capDonViHanhChinh.setDaXoa(true);
		}
		return capDonViHanhChinh;
	}

	public boolean checkExistsData(CapDonViHanhChinhRepository repo, String ten) {
		CapDonViHanhChinh capDonViHanhChinh = repo.findOne(QCapDonViHanhChinh.capDonViHanhChinh.daXoa.eq(false)
				.and(QCapDonViHanhChinh.capDonViHanhChinh.ten.eq(ten)));
		return capDonViHanhChinh != null ? true : false;
	}

}
