package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class SoTiepCongDanService {

	BooleanExpression base = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false);

	public Predicate predicateFindOne(Long id) {
		return base.and(QSoTiepCongDan.soTiepCongDan.id.eq(id));
	}

	public Predicate predicateFindAllTCD(String tuKhoa, String phanLoaiDon, String huongXuLy, String tuNgay,
			String denNgay, String loaiTiepCongDan, Long coQuanQuanLyId) {
		BooleanExpression predAll = base;

		if (StringUtils.isNotBlank(tuKhoa)) {
			predAll = predAll
					.and(QSoTiepCongDan.soTiepCongDan.don.donCongDans.any().congDan.hoVaTen.containsIgnoreCase(tuKhoa)
							.or(QSoTiepCongDan.soTiepCongDan.don.donCongDans.any().congDan.soCMNDHoChieu
									.containsIgnoreCase(tuKhoa)));
		}
		if (StringUtils.isNotBlank(phanLoaiDon)) {
			predAll = predAll
					.and(QSoTiepCongDan.soTiepCongDan.don.loaiDon.stringValue().containsIgnoreCase(phanLoaiDon));
		}
		predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(coQuanQuanLyId)
				.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(coQuanQuanLyId))
				.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(coQuanQuanLyId)));
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

			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepDan.between(dtTuNgay, dtDenNgay));
		} else {
			if (StringUtils.isNotBlank(tuNgay)) {
				LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
				predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepDan.after(dtTuNgay));
			}
			if (StringUtils.isNotBlank(denNgay)) {
				LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
				predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepDan.before(dtDenNgay));
			}
		}

		return predAll;
	}

	public boolean isExists(SoTiepCongDanRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QSoTiepCongDan.soTiepCongDan.id.eq(id));
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

	public SoTiepCongDan cancelCuocTiepDanDinhKyCuaLanhDao(SoTiepCongDanRepository repo, Long id) {
		SoTiepCongDan soTiepCongDan = repo.findOne(predicateFindOne(id));

		if (soTiepCongDan != null) {
			soTiepCongDan.getDon().setThanhLapTiepDanGapLanhDao(false);
			soTiepCongDan.setDaXoa(true);
		}

		return soTiepCongDan;
	}
}
