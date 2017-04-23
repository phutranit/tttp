package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.DonViHanhChinh;
import vn.greenglobal.tttp.model.QDonViHanhChinh;
import vn.greenglobal.tttp.repository.DonViHanhChinhRepository;

@Component
public class DonViHanhChinhService {

	BooleanExpression base = QDonViHanhChinh.donViHanhChinh.daXoa.eq(false);

	public Predicate predicateFindAll(String ten, Long cha, Long capDonViHanhChinh) {
		BooleanExpression predAll = base;
		if (ten != null && !"".equals(ten)) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.ten.containsIgnoreCase(ten)
					.or(QDonViHanhChinh.donViHanhChinh.ma.containsIgnoreCase(ten)));
		}

		if (cha != null && cha > 0) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.cha.id.eq(cha));
		}

		if (capDonViHanhChinh != null && capDonViHanhChinh > 0) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.id.eq(capDonViHanhChinh));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QDonViHanhChinh.donViHanhChinh.id.eq(id));
	}

	public boolean isExists(DonViHanhChinhRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QDonViHanhChinh.donViHanhChinh.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public DonViHanhChinh delete(DonViHanhChinhRepository repo, Long id) {
		DonViHanhChinh donViHanhChinh = repo.findOne(predicateFindOne(id));

		if (donViHanhChinh != null) {
			donViHanhChinh.setDaXoa(true);
		}

		return donViHanhChinh;
	}

	public boolean checkExistsData(DonViHanhChinhRepository repo, DonViHanhChinh body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.id.ne(body.getId()));
		}

		predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.ten.eq(body.getTen()));
		DonViHanhChinh donViHanhChinh = repo.findOne(predAll);

		return donViHanhChinh != null ? true : false;
	}

}
