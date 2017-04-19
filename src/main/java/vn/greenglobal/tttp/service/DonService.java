package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.HuongXuLyTCDEnum;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.repository.DonRepository;

/**
 * 
 * @author thanghq
 *
 */
public class DonService {

	public Predicate predicateFindAll(String maDon, 
			String tenNguoiDungDon, String cmndHoChieu, String phanLoaiDon, 
			String tiepNhanTuNgay,String tiepNhanDenNgay, 
			String hanGiaiQuyetTuNgay, String hanGiaiQuyetDenNgay,
			String tinhTrangXuLy) {
		
		Log log = LogFactory.getLog(DonService.class);
		
		BooleanExpression predAll = QDon.don.daXoa.eq(false);
		
		if (StringUtils.isNotBlank(maDon)) {
			
			predAll = predAll.and(QDon.don.ma.eq(StringUtils.trimToEmpty(maDon)));
		}
		
		if (StringUtils.isNotBlank(tenNguoiDungDon)) {

			predAll = predAll.and(QDon.don.donCongDans.any().congDan.hoVaTen.
					containsIgnoreCase(tenNguoiDungDon));

		}
		
		if (StringUtils.isNotBlank(cmndHoChieu)) {
		
			predAll = predAll.and(QDon.don.donCongDans.any().congDan.soCMNDHoChieu.
					containsIgnoreCase(cmndHoChieu));
		}
	
		if (StringUtils.isNotBlank(phanLoaiDon)) {
			
			predAll = predAll.and(QDon.don.loaiDon.eq(
					LoaiDonEnum.valueOf(StringUtils.upperCase(phanLoaiDon))));
		}
		
		if (StringUtils.isNotBlank(tiepNhanTuNgay)) {

			if (StringUtils.isNotBlank(tiepNhanDenNgay)) {
				
				predAll = predAll.and(QDon.don.ngayTiepNhan.between(
						fixTuNgay(tiepNhanTuNgay),fixDenNgay(tiepNhanDenNgay)));
				log.info(fixTuNgay(tiepNhanTuNgay).toString() + fixDenNgay(tiepNhanDenNgay).toString());
			} else {

				predAll = predAll.and(QDon.don.ngayTiepNhan.year().eq(
						LocalDateTime.parse(tiepNhanDenNgay).getYear()))
						.and(QDon.don.ngayTiepNhan.month().eq(
						LocalDateTime.parse(tiepNhanDenNgay).getMonthValue()))
						.and(QDon.don.ngayTiepNhan.dayOfMonth().eq(
						LocalDateTime.parse(tiepNhanDenNgay).getDayOfMonth()));
			}
		}
		
		// thoiHanGiaiQuyet ????
		/*if (StringUtils.isNotBlank(hanGiaiQuyetTuNgay)) {
			
			if (StringUtils.isNotBlank(hanGiaiQuyetDenNgay)) {
				
				predAll = predAll.and(QDon.don.ngayTiepNhan.between(
						fixTuNgay(hanGiaiQuyetTuNgay),fixDenNgay(hanGiaiQuyetDenNgay)));
			}
			predAll = predAll.and(QDon.don.ngayTiepNhan.year().eq(
					LocalDateTime.parse(hanGiaiQuyetTuNgay).getYear()))
					.and(QDon.don.ngayTiepNhan.month().eq(
					LocalDateTime.parse(hanGiaiQuyetTuNgay).getMonthValue()))
					.and(QDon.don.ngayTiepNhan.dayOfMonth().eq(
					LocalDateTime.parse(hanGiaiQuyetTuNgay).getDayOfMonth()));
		}*/
		
		if (StringUtils.isNotBlank(tinhTrangXuLy)) {
			
			predAll = predAll.and(QDon.don.huongXuLyXLD.eq(
					HuongXuLyXLDEnum.valueOf(StringUtils.upperCase(tinhTrangXuLy))));
		}
		
		return predAll;
	}

	public LocalDateTime fixTuNgay(String tuNgayCurrent) {
		
		// Fix tuNgay
		LocalDateTime tuNgay = LocalDateTime.parse(tuNgayCurrent);
		tuNgay = LocalDateTime.of(tuNgay.getYear(),
				tuNgay.getMonth(),tuNgay.getDayOfMonth(),0,0,0);
		return tuNgay;
	}
	
	public LocalDateTime fixDenNgay(String denNgayCurrent) {
		
		// Fix denNgay
		LocalDateTime denNgay = LocalDateTime.parse(denNgayCurrent);
		denNgay = LocalDateTime.of(denNgay.getYear(),
				denNgay.getMonth(),denNgay.getDayOfMonth(),23,59,59);
		return denNgay;
	}
	
	public Predicate predicateFindOne(Long id) {
		return QDon.don.daXoa.eq(false).and(QDon.don.id.eq(id));
	}

	public boolean isExists(DonRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QDon.don.daXoa.eq(false).and(QDon.don.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public Don deleteDon(DonRepository repo, Long id) {
		Don don = null;
		if (isExists(repo, id)) {
			don = new Don();
			don.setId(id);
			don.setDaXoa(true);
		}
		return don;
	}
}
