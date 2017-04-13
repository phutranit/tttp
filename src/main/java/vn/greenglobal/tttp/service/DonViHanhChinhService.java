package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.DonViHanhChinh;
import vn.greenglobal.tttp.model.QDonViHanhChinh;
import vn.greenglobal.tttp.repository.DonViHanhChinhRepository;

public class DonViHanhChinhService {
	
	public Predicate predicateFindAll(String ten) {
		BooleanExpression predAll = QDonViHanhChinh.donViHanhChinh.daXoa.eq(false);
		if (ten != null && !"".equals(ten)) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.ten.containsIgnoreCase(ten).or(QDonViHanhChinh.donViHanhChinh.ma.containsIgnoreCase(ten)));
		}		
		return predAll;
	}
	
	public Predicate predicateFindOne(Long id) {
		return QDonViHanhChinh.donViHanhChinh.daXoa.eq(false)
				.and(QDonViHanhChinh.donViHanhChinh.id.eq(id));
	}
	
	public boolean isExists(DonViHanhChinhRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QDonViHanhChinh.donViHanhChinh.daXoa.eq(false)
					.and(QDonViHanhChinh.donViHanhChinh.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}
	
	public DonViHanhChinh deleteCapDonViHanhChinh(DonViHanhChinhRepository repo, Long id) {
		DonViHanhChinh donViHanhChinh = null;
		if (isExists(repo, id)) {
			donViHanhChinh = new DonViHanhChinh();
			donViHanhChinh.setId(id);
			donViHanhChinh.setDaXoa(true);
		}
		return donViHanhChinh;
	}
	
	public boolean checkExistsData(DonViHanhChinhRepository repo, String ten) {
		DonViHanhChinh donViHanhChinh = repo.findOne(QDonViHanhChinh.donViHanhChinh.daXoa.eq(false)
				.and(QDonViHanhChinh.donViHanhChinh.ten.eq(ten)));
		return donViHanhChinh != null ? true : false;
	}
	
}
