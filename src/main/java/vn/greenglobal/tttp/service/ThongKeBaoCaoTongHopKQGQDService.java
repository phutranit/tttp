package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.KetLuanNoiDungKhieuNaiEnum;
import vn.greenglobal.tttp.enums.KetQuaGiaiQuyetLan2Enum;
import vn.greenglobal.tttp.enums.KetQuaTrangThaiDonEnum;
import vn.greenglobal.tttp.enums.ThongKeBaoCaoLoaiKyEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
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
			predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.year().eq(year));
		}
		
		if(loaiKy != null && StringUtils.isNotBlank(loaiKy)){
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			if (loaiKyEnum != null) { 
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
					if (quy != null && quy > 0) { 
						if (quy == 1) { 
							predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.month().between(1, 3));
						}
						if (quy == 2) { 
							predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.month().between(4, 6));
						}
						if (quy == 3) { 
							predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.month().between(7, 9));
						}
						if (quy == 4) { 
							predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.month().between(10, 12));
						}
					}
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_DAU_NAM)) {
					predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.month().between(1, 6));
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_CUOI_NAM)) {
					predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.month().between(6, 12));
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_THANG)) {
					if (month != null && month > 0) {
						predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.month().eq(month));
					}
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_NGAY)) {
					if (StringUtils.isNotBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
						LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
						LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);

						predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.between(dtTuNgay, dtDenNgay));
					} else {
						if (StringUtils.isNotBlank(tuNgay)) {
							LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
							predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.after(dtTuNgay));
						}
						if (StringUtils.isNotBlank(denNgay)) {
							LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
							predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.before(dtDenNgay));
						}
					}
				}
			}
		}
		
		return predAll;
	}
	
	public Long getTongSoDon(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoDonTrongKyBaoCao(BooleanExpression predAll, String loaiKy, Integer quy, Integer year, Integer month, String tuNgay, String denNgay) { 
		Long tongSo = 0L;
		
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
		
		List<Don> dons = new ArrayList<Don>();
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoDonTonKyTruoc(BooleanExpression predAll, String loaiKy, Integer quy, Integer year, Integer month, String tuNgay, String denNgay) { 
		Long tongSo = 0L;
		
		if(loaiKy != null && StringUtils.isNotBlank(loaiKy)){
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			if (loaiKyEnum != null) { 
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
					
					if (quy != null && quy > 0) { 						
											
						if (quy == 1) { 
							LocalDateTime ngayTiepNhan = LocalDateTime.of(year, 1, 1, 0, 0);
							predAll = predAll.and(QDon.don.ngayTiepNhan.before(ngayTiepNhan));
						}
						if (quy == 2) { 
							LocalDateTime ngayTiepNhan = LocalDateTime.of(year, 4, 1, 0, 0);
							predAll = predAll.and(QDon.don.ngayTiepNhan.before(ngayTiepNhan));
						}
						if (quy == 3) { 
							LocalDateTime ngayTiepNhan = LocalDateTime.of(year, 7, 1, 0, 0);
							predAll = predAll.and(QDon.don.ngayTiepNhan.before(ngayTiepNhan));
						}
						if (quy == 4) { 
							LocalDateTime ngayTiepNhan = LocalDateTime.of(year, 10, 1, 0, 0);
							predAll = predAll.and(QDon.don.ngayTiepNhan.before(ngayTiepNhan));
						}
					}
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_DAU_NAM)) {
					LocalDateTime ngayTiepNhan = LocalDateTime.of(year, 1, 1, 0, 0);
					predAll = predAll.and(QDon.don.ngayTiepNhan.before(ngayTiepNhan));
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_CUOI_NAM)) {
					LocalDateTime ngayTiepNhan = LocalDateTime.of(year, 7, 1, 0, 0);
					predAll = predAll.and(QDon.don.ngayTiepNhan.before(ngayTiepNhan));
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_THANG)) {
					if (month != null && month > 0) {
						LocalDateTime ngayTiepNhan = LocalDateTime.of(year, month, 1, 0, 0);		
						predAll = predAll.and(QDon.don.ngayTiepNhan.before(ngayTiepNhan));
					}
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_NGAY)) {
					if (StringUtils.isNotBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
						LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);

						predAll = predAll.and(QDon.don.ngayTiepNhan.before(dtTuNgay));
					} else {
						if (StringUtils.isNotBlank(tuNgay)) {
							LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
							predAll = predAll.and(QDon.don.ngayTiepNhan.before(dtTuNgay));
						}
					}
				}
			}
		}		
		
		List<Don> dons = new ArrayList<Don>();
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoDonToCaoThuocThamQuyen(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.ketQuaXLDGiaiQuyet.eq(KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET));
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoDonKhieuNaiThuocThamQuyen(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.ketQuaXLDGiaiQuyet.eq(KetQuaTrangThaiDonEnum.DINH_CHI)
				.or(QDon.don.ketQuaXLDGiaiQuyet.eq(KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET)));
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoDonDinhChi(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.ketQuaXLDGiaiQuyet.eq(KetQuaTrangThaiDonEnum.DINH_CHI));
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoDonCoQuyetDinh(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.ketQuaXLDGiaiQuyet.eq(KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET));
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoVuViecGiaiQuyetKhieuNaiDungThoiHan(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.ketQuaXLDGiaiQuyet.eq(KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET));
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.stream().map(d -> {
			Long tongSoVuViec = 0L;
			ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
			if (ttgqd != null && ttgqd.getNgayKetThucGiaiQuyet().isBefore(ttgqd.getNgayHetHanGiaiQuyet())) {				
				tongSoVuViec += ttgqd.getSoVuGiaiQuyetKhieuNai();
			} 
			return tongSoVuViec;
		}).mapToLong(Long::longValue).sum());
		return tongSo;
	}
	
	public Long getTongSoVuViecGiaiQuyetToCaoDungThoiHan(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.ketQuaXLDGiaiQuyet.eq(KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET));
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.stream().map(d -> {
			Long tongSoVuViec = 0L;
			ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
			if (ttgqd != null) {				
				if (ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() != null) {
					if (ttgqd.getNgayKetThucGiaiQuyet().isBefore(ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet())) {
						tongSoVuViec += ttgqd.getSoVuGiaiQuyetKhieuNai();
					}
				} else {
					if (ttgqd.getNgayKetThucGiaiQuyet().isBefore(ttgqd.getNgayHetHanGiaiQuyet())) {
						tongSoVuViec += ttgqd.getSoVuGiaiQuyetKhieuNai();
					}
				}				
			} 
			return tongSoVuViec;
		}).mapToLong(Long::longValue).sum());
		return tongSo;
	}
	
	public Long getTongSoVuViecGiaiQuyetKhieuNaiQuaThoiHan(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.ketQuaXLDGiaiQuyet.eq(KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET));
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.stream().map(d -> {
			Long tongSoVuViec = 0L;
			ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
			if (ttgqd != null && ttgqd.getNgayKetThucGiaiQuyet().isAfter(ttgqd.getNgayHetHanGiaiQuyet())) {				
				tongSoVuViec += ttgqd.getSoVuGiaiQuyetKhieuNai();
			} 
			return tongSoVuViec;
		}).mapToLong(Long::longValue).sum());
		return tongSo;
	}
	
	public Long getTongSoVuViecGiaiQuyetToCaoQuaThoiHan(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.ketQuaXLDGiaiQuyet.eq(KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET));
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.stream().map(d -> {
			Long tongSoVuViec = 0L;
			ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
			if (ttgqd != null) {				
				if (ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() != null) {
					if (ttgqd.getNgayKetThucGiaiQuyet().isAfter(ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet())) {
						tongSoVuViec += ttgqd.getSoVuGiaiQuyetKhieuNai();
					}
				} else {
					if (ttgqd.getNgayKetThucGiaiQuyet().isAfter(ttgqd.getNgayHetHanGiaiQuyet())) {
						tongSoVuViec += ttgqd.getSoVuGiaiQuyetKhieuNai();
					}
				}				
			}  
			return tongSoVuViec;
		}).mapToLong(Long::longValue).sum());
		return tongSo;
	}
	
	public Long getTongSoVuViecKhieuNaiThuocThamQuyen(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.ketQuaXLDGiaiQuyet.eq(KetQuaTrangThaiDonEnum.DINH_CHI)
				.or(QDon.don.ketQuaXLDGiaiQuyet.eq(KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET)));
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
	
	public Long getTongSoVuViecToCaoThuocThamQuyen(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.ketQuaXLDGiaiQuyet.eq(KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET));
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
	
	public Long getTongSoVuViecKhieuNaiDung(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ketLuanNoiDungKhieuNaiGiaoTTXM.eq(KetLuanNoiDungKhieuNaiEnum.DUNG_TOAN_BO));
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
	
	public Long getTongSoNguoiDuocTraLaiQuyenLoi(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.stream().map(d -> {
			Long tongSoVuViec = 0L;
			if (d.getThongTinGiaiQuyetDon() != null) {
				tongSoVuViec += d.getThongTinGiaiQuyetDon().getSoNguoiDuocTraLaiQuyenLoi();
			} 
			return tongSoVuViec;
		}).mapToLong(Long::longValue).sum());
		return tongSo;
	}
	
	public Long getTongSoNguoiXuLyHanhChinh(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.stream().map(d -> {
			Long tongSoVuViec = 0L;
			if (d.getThongTinGiaiQuyetDon() != null) {
				tongSoVuViec += d.getThongTinGiaiQuyetDon().getTongSoNguoiXuLyHanhChinh();
			} 
			return tongSoVuViec;
		}).mapToLong(Long::longValue).sum());
		return tongSo;
	}
	
	public Long getTongSoVuChuyenCoQuanDieuTra(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.stream().map(d -> {
			Long tongSoVuViec = 0L;
			if (d.getThongTinGiaiQuyetDon() != null) {
				tongSoVuViec += d.getThongTinGiaiQuyetDon().getSoVuGiaoCoQuanDieuTra();
			} 
			return tongSoVuViec;
		}).mapToLong(Long::longValue).sum());
		return tongSo;
	}
	
	public Long getTongSoVuDaKhoiTo(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.stream().map(d -> {
			Long tongSoVuViec = 0L;
			if (d.getThongTinGiaiQuyetDon() != null) {
				tongSoVuViec += d.getThongTinGiaiQuyetDon().getSoVuBiKhoiTo();
			} 
			return tongSoVuViec;
		}).mapToLong(Long::longValue).sum());
		return tongSo;
	}
	
	public Long getTongSoDoiTuongDaKhoiTo(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.stream().map(d -> {
			Long tongSoVuViec = 0L;
			if (d.getThongTinGiaiQuyetDon() != null) {
				tongSoVuViec += d.getThongTinGiaiQuyetDon().getSoDoiTuongBiKhoiTo();
			} 
			return tongSoVuViec;
		}).mapToLong(Long::longValue).sum());
		return tongSo;
	}
	
	public Long getTongSoDoiTuongChuyenCoQuanDieuTra(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.stream().map(d -> {
			Long tongSoVuViec = 0L;
			if (d.getThongTinGiaiQuyetDon() != null) {
				tongSoVuViec += d.getThongTinGiaiQuyetDon().getSoDoiTuongGiaoCoQuanDieuTra();
			} 
			return tongSoVuViec;
		}).mapToLong(Long::longValue).sum());
		return tongSo;
	}
	
	public Long getTongSoNguoiDaBiXuLyHanhChinh(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		tongSo = Long.valueOf(dons.stream().map(d -> {
			Long tongSoVuViec = 0L;
			if (d.getThongTinGiaiQuyetDon() != null) {
				tongSoVuViec += d.getThongTinGiaiQuyetDon().getSoNguoiDaBiXuLyHanhChinh();
			} 
			return tongSoVuViec;
		}).mapToLong(Long::longValue).sum());
		return tongSo;
	}
	
	public Long getTongSoTienDatPhaiThuVeQDGQ(BooleanExpression predAll, String tienDat) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		if ("tienNhaNuoc".equals(tienDat)) { 
			tongSo = Long.valueOf(dons.stream().map(d -> {
				Long tongSoTien = 0L;
				if (d.getThongTinGiaiQuyetDon() != null) {
					tongSoTien += d.getThongTinGiaiQuyetDon().getTienPhaiThuNhaNuocQDGQ();					
				} 
				return tongSoTien;
			}).mapToLong(Long::longValue).sum());
		} else if ("datNhaNuoc".equals(tienDat)) {
			tongSo = Long.valueOf(dons.stream().map(d -> {
				Long tongSoTien = 0L;
				if (d.getThongTinGiaiQuyetDon() != null) {
					tongSoTien += d.getThongTinGiaiQuyetDon().getDatPhaiThuNhaNuocQDGQ();					
				} 
				return tongSoTien;
			}).mapToLong(Long::longValue).sum());
		} else if ("tienCongDan".equals(tienDat)) {
			tongSo = Long.valueOf(dons.stream().map(d -> {
				Long tongSoTien = 0L;
				if (d.getThongTinGiaiQuyetDon() != null) {
					tongSoTien += d.getThongTinGiaiQuyetDon().getTienPhaiTraCongDanQDGQ();					
				} 
				return tongSoTien;
			}).mapToLong(Long::longValue).sum());
		} else if ("datCongDan".equals(tienDat)) {
			tongSo = Long.valueOf(dons.stream().map(d -> {
				Long tongSoTien = 0L;
				if (d.getThongTinGiaiQuyetDon() != null) {
					tongSoTien += d.getThongTinGiaiQuyetDon().getDatPhaiTraCongDanQDGQ();					
				} 
				return tongSoTien;
			}).mapToLong(Long::longValue).sum());
		}		
		return tongSo;
	}
	
	public Long getTongSoTienDatPhaiThuTra(BooleanExpression predAll, String tienDat) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		dons.addAll((List<Don>) donRepo.findAll(predAll));
		if ("tienNhaNuocPhaiThu".equals(tienDat)) { 
			tongSo = Long.valueOf(dons.stream().map(d -> {
				Long tongSoTien = 0L;
				if (d.getThongTinGiaiQuyetDon() != null) {
					tongSoTien += d.getThongTinGiaiQuyetDon().getTienPhaiThuNhaNuoc();					
				} 
				return tongSoTien;
			}).mapToLong(Long::longValue).sum());
		} else if ("tienNhaNuocDaThu".equals(tienDat)) { 
			tongSo = Long.valueOf(dons.stream().map(d -> {
				Long tongSoTien = 0L;
				if (d.getThongTinGiaiQuyetDon() != null) {
					tongSoTien += d.getThongTinGiaiQuyetDon().getTienDaThuNhaNuoc();					
				} 
				return tongSoTien;
			}).mapToLong(Long::longValue).sum());
		} else if ("datNhaNuocPhaiThu".equals(tienDat)) { 
			tongSo = Long.valueOf(dons.stream().map(d -> {
				Long tongSoTien = 0L;
				if (d.getThongTinGiaiQuyetDon() != null) {
					tongSoTien += d.getThongTinGiaiQuyetDon().getDatPhaiThuNhaNuoc();					
				} 
				return tongSoTien;
			}).mapToLong(Long::longValue).sum());
		} else if ("datNhaNuocDaThu".equals(tienDat)) { 
			tongSo = Long.valueOf(dons.stream().map(d -> {
				Long tongSoTien = 0L;
				if (d.getThongTinGiaiQuyetDon() != null) {
					tongSoTien += d.getThongTinGiaiQuyetDon().getDatDaThuNhaNuoc();					
				} 
				return tongSoTien;
			}).mapToLong(Long::longValue).sum());
		} else if ("tienCongDanPhaiTra".equals(tienDat)) { 
			tongSo = Long.valueOf(dons.stream().map(d -> {
				Long tongSoTien = 0L;
				if (d.getThongTinGiaiQuyetDon() != null) {
					tongSoTien += d.getThongTinGiaiQuyetDon().getTienPhaiTraCongDan();					
				} 
				return tongSoTien;
			}).mapToLong(Long::longValue).sum());
		} else if ("tienCongDanDaTra".equals(tienDat)) { 
			tongSo = Long.valueOf(dons.stream().map(d -> {
				Long tongSoTien = 0L;
				if (d.getThongTinGiaiQuyetDon() != null) {
					tongSoTien += d.getThongTinGiaiQuyetDon().getTienDaTraCongDan();					
				} 
				return tongSoTien;
			}).mapToLong(Long::longValue).sum());
		} else if ("datCongDanPhaiTra".equals(tienDat)) { 
			tongSo = Long.valueOf(dons.stream().map(d -> {
				Long tongSoTien = 0L;
				if (d.getThongTinGiaiQuyetDon() != null) {
					tongSoTien += d.getThongTinGiaiQuyetDon().getDatPhaiTraCongDan();				
				} 
				return tongSoTien;
			}).mapToLong(Long::longValue).sum());
		} else if ("datCongDanDaTra".equals(tienDat)) { 
			tongSo = Long.valueOf(dons.stream().map(d -> {
				Long tongSoTien = 0L;
				if (d.getThongTinGiaiQuyetDon() != null) {
					tongSoTien += d.getThongTinGiaiQuyetDon().getDatDaTraCongDan();				
				} 
				return tongSoTien;
			}).mapToLong(Long::longValue).sum());
		} 
		return tongSo;
	}
	
	public Long getTongSoVuViecGiaiQuyetLan1(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.soLanGiaiQuyetLai.eq(1));
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
	
	public Long getTongSoVuViecGiaiQuyetLan2CongNhan(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.soLanGiaiQuyetLai.eq(2))
				.and(QDon.don.thongTinGiaiQuyetDon.ketQuaGiaiQuyetLan2.eq(KetQuaGiaiQuyetLan2Enum.CONG_NHAN_QDGQ_LAN_I));
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
	
	public Long getTongSoVuViecGiaiQuyetLan2HuySua(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.soLanGiaiQuyetLai.eq(2))
				.and(QDon.don.thongTinGiaiQuyetDon.ketQuaGiaiQuyetLan2.eq(KetQuaGiaiQuyetLan2Enum.HUY_SUA_QDGQ_LAN_I));
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
	
	public Long getTongSoVuViecDungMotPhan(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ketLuanNoiDungKhieuNaiGiaoTTXM.eq(KetLuanNoiDungKhieuNaiEnum.DUNG_MOT_PHAN));
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
	
	public Long getTongSoVuViecKhieuNaiSai(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ketLuanNoiDungKhieuNaiGiaoTTXM.eq(KetLuanNoiDungKhieuNaiEnum.SAI_TOAN_BO));
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
	
	public Long getTongSoVuViec(BooleanExpression predAll) { 
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
