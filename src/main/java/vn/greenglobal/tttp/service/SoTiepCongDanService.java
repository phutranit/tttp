package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.util.Utils;

public class SoTiepCongDanService {
	private static Log log = LogFactory.getLog(SoTiepCongDanService.class);

	public Predicate predicateFindOne(Long id) {
		return QSoTiepCongDan.soTiepCongDan.daXoa.eq(false).and(QSoTiepCongDan.soTiepCongDan.id.eq(id));
	}

	public Predicate predicateFindAllTCD(String tuKhoa, String phanLoaiDon, String huongXuLy, String tuNgay,
			String denNgay, String loaiTiepCongDan, boolean thanhLapDon) {
		BooleanExpression predAll = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false)
				.and(QSoTiepCongDan.soTiepCongDan.don.thanhLapDon.eq(thanhLapDon));

		if (StringUtils.isNotBlank(tuKhoa)) {
			log.info("-- tuKhoa : " + tuKhoa);
			predAll = predAll
					.and(QSoTiepCongDan.soTiepCongDan.don.donCongDans.any().congDan.hoVaTen.containsIgnoreCase(tuKhoa)
							.or(QSoTiepCongDan.soTiepCongDan.don.donCongDans.any().congDan.soCMNDHoChieu
									.containsIgnoreCase(tuKhoa)));
		}
		if (StringUtils.isNotBlank(phanLoaiDon)) {
			predAll = predAll
					.and(QSoTiepCongDan.soTiepCongDan.don.loaiDon.stringValue().containsIgnoreCase(phanLoaiDon));
		}
		if (StringUtils.isNotBlank(huongXuLy)) {
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.huongXuLy.stringValue().containsIgnoreCase(huongXuLy));
		}
		if (StringUtils.isNotBlank(loaiTiepCongDan)) {
			predAll = predAll
					.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.stringValue().containsIgnoreCase(loaiTiepCongDan));
		}
		if (StringUtils.isNotBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
			LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
			LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);

			log.info("-- dtTuNgay : " + dtTuNgay);
			log.info("-- dtDenNgay : " + dtDenNgay);
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepNhan.between(dtTuNgay, dtDenNgay));
		} else {
			if (StringUtils.isNotBlank(tuNgay)) {
				LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
				log.info("-- dtTuNgay : " + dtTuNgay);
				predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepNhan.after(dtTuNgay));
			}
			if (StringUtils.isNotBlank(denNgay)) {
				LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
				log.info("-- dtTuNgay : " + dtDenNgay);
				predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepNhan.before(dtDenNgay));
			}
		}

		return predAll;
	}

	public Predicate predicateFindTCDYeuCauGapLanhDao(String tuNgay, String denNgay) {
		BooleanExpression predAll = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false)
				.and(QSoTiepCongDan.soTiepCongDan.yeuCauGapTrucTiepLanhDao.eq(true))
				.and(QSoTiepCongDan.soTiepCongDan.don.thanhLapDon.eq(false));
		if (StringUtils.isNotBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
			LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
			LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepNhan.between(dtTuNgay, dtDenNgay));
		} else {
			if (StringUtils.isNotBlank(tuNgay)) {
				LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
				predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepNhan.after(dtTuNgay));
			}
			if (StringUtils.isNotBlank(denNgay)) {
				LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
				predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepNhan.before(dtDenNgay));
			}
		}

		return predAll;
	}

	public boolean isExists(SoTiepCongDanRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false)
					.and(QSoTiepCongDan.soTiepCongDan.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public SoTiepCongDan deleteSoTiepCongDan(SoTiepCongDanRepository repo, Long id) {
		SoTiepCongDan soTiepCongDan = repo.findOne(predicateFindOne(id));

		if (soTiepCongDan != null) {
			soTiepCongDan.setDaXoa(true);
		}

		return soTiepCongDan;
	}
}
