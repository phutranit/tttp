package vn.greenglobal.tttp.service;

import org.apache.commons.lang3.StringUtils;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.DonViHanhChinh;
import vn.greenglobal.tttp.model.QDonViHanhChinh;
import vn.greenglobal.tttp.repository.DonViHanhChinhRepository;

public class DonViHanhChinhService {
	
	public Predicate predicateFindAll(String ten, Long cha, Long capDonViHanhChinh) {
		BooleanExpression predAll = QDonViHanhChinh.donViHanhChinh.daXoa.eq(false);
		if (ten != null && !"".equals(ten)) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.ten.containsIgnoreCase(ten).or(QDonViHanhChinh.donViHanhChinh.ma.containsIgnoreCase(ten)));
		}
		
		if (cha != null && cha > 0) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.cha.id.eq(cha));
		}
		
		if (capDonViHanhChinh != null && capDonViHanhChinh > 0) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.id.eq(capDonViHanhChinh));
		}
		
		return predAll;
	}
	
	public Predicate predicateFindTinhThanhPho(String ten, Long capDonViHanhChinh) {
		BooleanExpression predAll = QDonViHanhChinh.donViHanhChinh.daXoa.eq(false);
		if (StringUtils.isNotBlank(ten)) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.ten.containsIgnoreCase(ten).or(QDonViHanhChinh.donViHanhChinh.ma.containsIgnoreCase(ten)));
		}
		
		predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.cha.isNull());
		
		if (capDonViHanhChinh != null && capDonViHanhChinh > 0) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.id.eq(capDonViHanhChinh));
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
	
	public DonViHanhChinh deleteDonViHanhChinh(DonViHanhChinhRepository repo, Long id) {
		DonViHanhChinh donViHanhChinh = null;
		if (isExists(repo, id)) {
			donViHanhChinh = new DonViHanhChinh();
			donViHanhChinh.setId(id);
			donViHanhChinh.setDaXoa(true);
		}
		return donViHanhChinh;
	}
		
	public boolean checkExistsData(DonViHanhChinhRepository repo, DonViHanhChinh body) {
		BooleanExpression predAll = QDonViHanhChinh.donViHanhChinh.daXoa.eq(false);

		if (!body.isNew()) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.id.ne(body.getId()));
		}

		predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.ten.eq(body.getTen()));
		DonViHanhChinh donViHanhChinh = repo.findOne(predAll);

		return donViHanhChinh != null ? true : false;
	}
	
}
