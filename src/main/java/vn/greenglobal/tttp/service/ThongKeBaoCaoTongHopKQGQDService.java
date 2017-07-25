package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.KetQuaTrangThaiDonEnum;
import vn.greenglobal.tttp.enums.ThongKeBaoCaoLoaiKyEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class ThongKeBaoCaoTongHopKQGQDService {	
	
	@Autowired
	private DonRepository donRepo;

	
	BooleanExpression baseTCD = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false);
	BooleanExpression baseDonCongDan = QDon_CongDan.don_CongDan.daXoa.eq(false);
	BooleanExpression baseDon = QDon.don.daXoa.eq(false);
	

	public Predicate predicateFindAllGQD(String loaiKy, Integer quy, Integer year, Integer month, String tuNgay, String denNgay) { 
		BooleanExpression predAll = baseDon;
		if (year != null && year > 0) { 
			predAll = predAll.and(QDon.don.ngayTiepNhan.year().eq(year));
		}
		
		if(loaiKy != null && StringUtils.isNotBlank(loaiKy)){
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			if (loaiKyEnum != null) { 
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
					if (quy != null && quy > 0) { 
						if (quy == 1) { 
							predAll = predAll.and(QDon.don.ngayTiepNhan.month().between(1, 3));
						}
						if (quy == 2) { 
							predAll = predAll.and(QDon.don.ngayTiepNhan.month().between(4, 6));
						}
						if (quy == 3) { 
							predAll = predAll.and(QDon.don.ngayTiepNhan.month().between(7, 9));
						}
						if (quy == 4) { 
							predAll = predAll.and(QDon.don.ngayTiepNhan.month().between(10, 12));
						}
					}
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_DAU_NAM)) {
					predAll = predAll.and(QDon.don.ngayTiepNhan.month().between(1, 6));
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_CUOI_NAM)) {
					predAll = predAll.and(QDon.don.ngayTiepNhan.month().between(6, 12));
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_THANG)) {
					if (month != null && month > 0) {
						predAll = predAll.and(QDon.don.ngayTiepNhan.month().eq(month));
					}
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_NGAY)) {
					if (StringUtils.isNotBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
						LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
						LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);

						predAll = predAll.and(QDon.don.ngayTiepNhan.between(dtTuNgay, dtDenNgay));
					} else {
						if (StringUtils.isNotBlank(tuNgay)) {
							LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
							predAll = predAll.and(QDon.don.ngayTiepNhan.after(dtTuNgay));
						}
						if (StringUtils.isNotBlank(denNgay)) {
							LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
							predAll = predAll.and(QDon.don.ngayTiepNhan.before(dtDenNgay));
						}
					}
				}
			}
		}
		
		return predAll;
	}
	
	public Long getTongSoDonKhieuNai(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoDonKhieuNaiDinhChi(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.ketQuaXLDGiaiQuyet.eq(KetQuaTrangThaiDonEnum.DINH_CHI));
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoVuViecKhieuNai(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.stream().map(d -> {
			Long tongSoVuViec = 0L;
			if (d.getThongTinGiaiQuyetDon() != null) {
				ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
				tongSoVuViec += ttgqd.getSoVuGiaiQuyetKhieuNai();
			} else {
				tongSoVuViec += 1;
			}
			return tongSoVuViec;
		}).mapToLong(Long::longValue).sum());
		return tongSo;
	}
}
