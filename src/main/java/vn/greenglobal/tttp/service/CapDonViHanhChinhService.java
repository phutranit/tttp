package vn.greenglobal.tttp.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CapDonViHanhChinh;
import vn.greenglobal.tttp.model.DonViHanhChinh;
import vn.greenglobal.tttp.model.QCapCoQuanQuanLy;
import vn.greenglobal.tttp.model.QCapDonViHanhChinh;
import vn.greenglobal.tttp.model.QDonViHanhChinh;
import vn.greenglobal.tttp.repository.CapDonViHanhChinhRepository;
import vn.greenglobal.tttp.repository.DonViHanhChinhRepository;

@Component
public class CapDonViHanhChinhService {

	BooleanExpression base = QCapDonViHanhChinh.capDonViHanhChinh.daXoa.eq(false);

	public Predicate predicateFindAll(String ten) {
		BooleanExpression predAll = base;
		if (ten != null && !"".equals(ten)) {
			predAll = predAll.and(QCapDonViHanhChinh.capDonViHanhChinh.ten.containsIgnoreCase(ten)
					.or(QCapDonViHanhChinh.capDonViHanhChinh.ma.containsIgnoreCase(ten)));
		}
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QCapDonViHanhChinh.capDonViHanhChinh.id.eq(id));
	}

	public boolean isExists(CapDonViHanhChinhRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QCapDonViHanhChinh.capDonViHanhChinh.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public CapDonViHanhChinh delete(CapDonViHanhChinhRepository repo, Long id) {
		CapDonViHanhChinh capDonViHanhChinh = repo.findOne(predicateFindOne(id));

		if (capDonViHanhChinh != null) {
			capDonViHanhChinh.setDaXoa(true);
		}

		return capDonViHanhChinh;
	}

	public boolean checkExistsData(CapDonViHanhChinhRepository repo, CapDonViHanhChinh body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QCapDonViHanhChinh.capDonViHanhChinh.id.ne(body.getId()));
		}

		predAll = predAll.and(QCapDonViHanhChinh.capDonViHanhChinh.ten.eq(body.getTen()));
		CapDonViHanhChinh capDonViHanhChinh = repo.findOne(predAll);

		return capDonViHanhChinh != null ? true : false;
	}

	public boolean checkUsedData(CapDonViHanhChinhRepository repo, DonViHanhChinhRepository repoDonViHanhChinh,
			Long id) {
		List<CapDonViHanhChinh> capCoQuanQuanLyList = (List<CapDonViHanhChinh>) repo
				.findAll(base.and(QCapCoQuanQuanLy.capCoQuanQuanLy.cha.id.eq(id)));
		List<DonViHanhChinh> coQuanQuanLyList = (List<DonViHanhChinh>) repoDonViHanhChinh
				.findAll(QDonViHanhChinh.donViHanhChinh.daXoa.eq(false)
						.and(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.id.eq(id)));

		if ((capCoQuanQuanLyList != null && capCoQuanQuanLyList.size() > 0)
				|| (coQuanQuanLyList != null && coQuanQuanLyList.size() > 0)) {
			return true;
		}

		return false;
	}

}
