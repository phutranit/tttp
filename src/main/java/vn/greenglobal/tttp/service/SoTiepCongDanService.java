package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.HuongGiaiQuyetTCDEnum;
import vn.greenglobal.tttp.enums.PhanLoaiDonCongDanEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class SoTiepCongDanService {

	BooleanExpression base = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false);
	BooleanExpression baseDonCongDan = QDon_CongDan.don_CongDan.daXoa.eq(false);
	
	public Predicate predicateFindOne(Long id) {
		return base.and(QSoTiepCongDan.soTiepCongDan.id.eq(id));
	}

	public Predicate predicateFindAllTCD(String tuKhoa, String phanLoaiDon, String huongXuLy, String tuNgay,
			String denNgay, String loaiTiepCongDan, Long donViId, Long lanhDaoId, String tenNguoiDungDon, String tinhTrangXuLy,
			String ketQuaTiepDan, CongChucRepository congChucRepo, DonCongDanRepository donCongDanRepo) {
		BooleanExpression predAll = base;
		BooleanExpression donCongDanQuery = baseDonCongDan;
		
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

		predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(donViId)
				.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(donViId))
				.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(donViId)));

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
		
		if (lanhDaoId != null && lanhDaoId > 0) {
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.canBoTiepDan.isNotNull()
					.and(QSoTiepCongDan.soTiepCongDan.canBoTiepDan.id.eq(lanhDaoId)));
		}
		
		/*if (StringUtils.isNotBlank(tinhTrangXuLy)) {
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.huongGiaiQuyetTCDLanhDao.isNotNull()
					.and(QSoTiepCongDan.soTiepCongDan.huongGiaiQuyetTCDLanhDao.eq(HuongGiaiQuyetTCDEnum.valueOf(tinhTrangXuLy))));
		}*/
		
		if (StringUtils.isNotBlank(ketQuaTiepDan)) {
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.huongGiaiQuyetTCDLanhDao.isNotNull()
					.and(QSoTiepCongDan.soTiepCongDan.huongGiaiQuyetTCDLanhDao.eq(HuongGiaiQuyetTCDEnum.valueOf(ketQuaTiepDan))));
		}

		List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();
		List<Don> dons = new ArrayList<Don>();
		
		if (StringUtils.isNotBlank(tenNguoiDungDon)) {
			donCongDanQuery = donCongDanQuery
					.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.stringValue()
							.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON.name()))
					.and(QDon_CongDan.don_CongDan.congDan.hoVaTen.containsIgnoreCase(tenNguoiDungDon)
							.or(QDon_CongDan.don_CongDan.tenCoQuan.containsIgnoreCase(tenNguoiDungDon)));
			donCongDans = (List<Don_CongDan>) donCongDanRepo.findAll(donCongDanQuery);
			dons = donCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
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
