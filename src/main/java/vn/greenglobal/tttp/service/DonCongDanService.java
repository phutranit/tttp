package vn.greenglobal.tttp.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.repository.DonCongDanRepository;

@Component
public class DonCongDanService {

	public Predicate predicateFindAll(Long don, Long congDan, String phanLoai) {
		Predicate predAll = QDon_CongDan.don_CongDan.daXoa.eq(false);
		if (don != null && don > 0) {
			predAll = QDon_CongDan.don_CongDan.don.id.eq(don);
		}
		if (congDan != null && congDan > 0) {
			predAll = QDon_CongDan.don_CongDan.congDan.id.eq(congDan);
		}
		if (StringUtils.isNotBlank(phanLoai)) {
			predAll = QDon_CongDan.don_CongDan.phanLoaiCongDan.stringValue().containsIgnoreCase(phanLoai);
		}
		return predAll;
	}

	public Predicate predicateFindAll(String tuKhoa) {
		BooleanExpression predAll = QDon_CongDan.don_CongDan.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QDon_CongDan.don_CongDan.congDan.hoVaTen.containsIgnoreCase(tuKhoa))
					.or(QDon_CongDan.don_CongDan.congDan.soCMNDHoChieu.eq(tuKhoa));
		}
		return predAll;
	}

	public Predicate predicateFindByTCD(String tuKhoa) {
		BooleanExpression predAll = QDon_CongDan.don_CongDan.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QDon_CongDan.don_CongDan.congDan.hoVaTen.containsIgnoreCase(tuKhoa))
					.or(QDon_CongDan.don_CongDan.congDan.soCMNDHoChieu.eq(tuKhoa));
		}
		return predAll;
	}

	public Predicate predicateFindAllTCD(String tuKhoa, boolean thanhLapDon) {
		BooleanExpression predAll = QDon_CongDan.don_CongDan.daXoa.eq(false)
				.and(QDon_CongDan.don_CongDan.don.thanhLapDon.eq(thanhLapDon));
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return QDon_CongDan.don_CongDan.daXoa.eq(false).and(QDon_CongDan.don_CongDan.id.eq(id));
	}

	public boolean isExists(DonCongDanRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QDon_CongDan.don_CongDan.daXoa.eq(false).and(QDon_CongDan.don_CongDan.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public boolean checkExistsData(DonCongDanRepository repo, Don_CongDan body) {
		BooleanExpression predAll = QDon_CongDan.don_CongDan.daXoa.eq(false);

		if (!body.isNew()) {
			predAll = predAll.and(QDon_CongDan.don_CongDan.id.ne(body.getId()));
		}
		Don_CongDan donCongDan = repo.findOne(predAll);

		return donCongDan != null ? true : false;
	}

	public Don_CongDan delete(DonCongDanRepository repo, Long id) {
		Don_CongDan dcd = repo.findOne(predicateFindOne(id));

		if (dcd != null) {
			dcd.setDaXoa(true);
		}

		return dcd;
	}

}
