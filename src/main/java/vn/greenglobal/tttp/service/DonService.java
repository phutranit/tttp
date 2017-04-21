package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.*;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class DonService {

	BooleanExpression base = QDon.don.daXoa.eq(false);

	public Predicate predicateFindAll(String maDon, String tenNguoiDungDon, String cmndHoChieu, String phanLoaiDon,
			String tiepNhanTuNgay, String tiepNhanDenNgay, String hanGiaiQuyetTuNgay, String hanGiaiQuyetDenNgay,
			String tinhTrangXuLy, boolean thanhLapDon, String trangThaiDon, Long phongBanGiaiQuyetXLD,
			Long canBoXuLyXLD, Long phongBanXuLyXLD, Long coQuanTiepNhanXLD, String vaiTro) {

		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(thanhLapDon));

		if (StringUtils.isNotBlank(maDon)) {
			predAll = predAll.and(QDon.don.ma.eq(StringUtils.trimToEmpty(maDon)));
		}

		if (StringUtils.isNotBlank(tenNguoiDungDon)) {
			predAll = predAll.and(QDon.don.donCongDans.any().congDan.hoVaTen.containsIgnoreCase(tenNguoiDungDon));
		}

		if (StringUtils.isNotBlank(cmndHoChieu)) {
			predAll = predAll.and(QDon.don.donCongDans.any().congDan.soCMNDHoChieu.containsIgnoreCase(cmndHoChieu));
		}

		if (StringUtils.isNotBlank(phanLoaiDon)) {
			predAll = predAll.and(QDon.don.loaiDon.eq(LoaiDonEnum.valueOf(StringUtils.upperCase(phanLoaiDon))));
		}

		if (StringUtils.isNotBlank(trangThaiDon)) {
			predAll = predAll
					.and(QDon.don.trangThaiDon.eq(TrangThaiDonEnum.valueOf(StringUtils.upperCase(trangThaiDon))));
		}

		if (StringUtils.isNotBlank(vaiTro)) {
			predAll = predAll
					.and(QDon.don.xuLyDons.any().phongBanXuLy.congChucs.any().nguoiDung.vaiTros.any().quyen.eq(vaiTro));
		}

		if (phongBanGiaiQuyetXLD != null) {
			predAll = predAll.and(QDon.don.xuLyDons.any().phongBanGiaiQuyet.id.eq(phongBanGiaiQuyetXLD));
		}

		if (canBoXuLyXLD != null) {
			predAll = predAll.and(QDon.don.xuLyDons.any().congChuc.id.eq(canBoXuLyXLD));
		}

		if (phongBanXuLyXLD != null) {
			predAll = predAll.and(QDon.don.xuLyDons.any().phongBanXuLy.id.eq(phongBanXuLyXLD));
		}

		if (coQuanTiepNhanXLD != null) {
			predAll = predAll.and(QDon.don.xuLyDons.any().coQuanTiepNhan.id.eq(coQuanTiepNhanXLD));
		}

		if (StringUtils.isNotBlank(tiepNhanTuNgay)) {
			if (StringUtils.isNotBlank(tiepNhanDenNgay)) {
				predAll = predAll
						.and(QDon.don.ngayTiepNhan.between(fixTuNgay(tiepNhanTuNgay), fixDenNgay(tiepNhanDenNgay)));
			} else {
				predAll = predAll.and(QDon.don.ngayTiepNhan.year().eq(LocalDateTime.parse(tiepNhanDenNgay).getYear()))
						.and(QDon.don.ngayTiepNhan.month().eq(LocalDateTime.parse(tiepNhanDenNgay).getMonthValue()))
						.and(QDon.don.ngayTiepNhan.dayOfMonth()
								.eq(LocalDateTime.parse(tiepNhanDenNgay).getDayOfMonth()));
			}
		}

		// thoiHanGiaiQuyet ????
		/*
		 * if (StringUtils.isNotBlank(hanGiaiQuyetTuNgay)) {
		 * 
		 * if (StringUtils.isNotBlank(hanGiaiQuyetDenNgay)) {
		 * 
		 * predAll = predAll.and(QDon.don.ngayTiepNhan.between(
		 * fixTuNgay(hanGiaiQuyetTuNgay),fixDenNgay(hanGiaiQuyetDenNgay))); }
		 * predAll = predAll.and(QDon.don.ngayTiepNhan.year().eq(
		 * LocalDateTime.parse(hanGiaiQuyetTuNgay).getYear()))
		 * .and(QDon.don.ngayTiepNhan.month().eq(
		 * LocalDateTime.parse(hanGiaiQuyetTuNgay).getMonthValue()))
		 * .and(QDon.don.ngayTiepNhan.dayOfMonth().eq(
		 * LocalDateTime.parse(hanGiaiQuyetTuNgay).getDayOfMonth())); }
		 */

		if (StringUtils.isNotBlank(tinhTrangXuLy)) {

			predAll = predAll
					.and(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.valueOf(StringUtils.upperCase(tinhTrangXuLy))));
		}

		return predAll;
	}

	public LocalDateTime fixTuNgay(String tuNgayCurrent) {

		// Fix tuNgay
		LocalDateTime tuNgay = LocalDateTime.parse(tuNgayCurrent);
		tuNgay = LocalDateTime.of(tuNgay.getYear(), tuNgay.getMonth(), tuNgay.getDayOfMonth(), 0, 0, 0);
		return tuNgay;
	}

	public LocalDateTime fixDenNgay(String denNgayCurrent) {

		// Fix denNgay
		LocalDateTime denNgay = LocalDateTime.parse(denNgayCurrent);
		denNgay = LocalDateTime.of(denNgay.getYear(), denNgay.getMonth(), denNgay.getDayOfMonth(), 23, 59, 59);
		return denNgay;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QDon.don.id.eq(id));
	}

	public boolean isExists(DonRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QDon.don.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public Don updateQuyTrinhXuLyDon(DonRepository repo, Long id, QuyTrinhXuLyDonEnum quyTrinhXuLyDonEnum) {
		Don don = null;
		if (isExists(repo, id)) {
			don = new Don();
			don.setId(id);
			don.setQuyTrinhXuLy(quyTrinhXuLyDonEnum);
		}
		return don;
	}

	public Don updateHuongXuLy(DonRepository repo, Long id, HuongXuLyXLDEnum huongXuLyXLDEnum) {
		Don don = null;
		if (isExists(repo, id)) {
			don = new Don();
			don.setId(id);
			don.setHuongXuLyXLD(huongXuLyXLDEnum);
		}
		return don;
	}

	public Don updateTrangThaiDon(DonRepository repo, Long id, TrangThaiDonEnum trangThaiDonEnum) {
		Don don = null;
		if (isExists(repo, id)) {
			don = new Don();
			don.setId(id);
			don.setTrangThaiDon(trangThaiDonEnum);
		}
		return don;
	}

	public Don updateNgayLapDonGapLanhDao(DonRepository repo, Long id) {

		Don don = null;
		if (isExists(repo, id)) {
			don = new Don();
			don.setId(id);
			don.setNgayLapDonGapLanhDaoTmp(LocalDateTime.now());
		}
		return don;
	}

	public Don deleteDon(DonRepository repo, Long id) {
		Don don = repo.findOne(predicateFindOne(id));

		if (don != null) {
			don.setDaXoa(true);
		}

		return don;
	}

	public Predicate predicateFindDonYeuCauGapLanhDao(String tuNgay, String denNgay) {
		BooleanExpression predAll = base
				.and(QDon.don.yeuCauGapTrucTiepLanhDao.eq(true)
						.or(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO)))
				.and(QDon.don.thanhLapDon.eq(false));
		if (StringUtils.isNotBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
			LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
			LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
			predAll = predAll.and(QDon.don.ngayLapDonGapLanhDaoTmp.between(dtTuNgay, dtDenNgay));
		} else {
			if (StringUtils.isNotBlank(tuNgay)) {
				LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
				predAll = predAll.and(QDon.don.ngayLapDonGapLanhDaoTmp.after(dtTuNgay));
			}
			if (StringUtils.isNotBlank(denNgay)) {
				LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
				predAll = predAll.and(QDon.don.ngayLapDonGapLanhDaoTmp.before(dtDenNgay));
			}
		}

		predAll = predAll.and((Predicate) QDon.don.ngayLapDonGapLanhDaoTmp.desc());
		return predAll;
	}
}
