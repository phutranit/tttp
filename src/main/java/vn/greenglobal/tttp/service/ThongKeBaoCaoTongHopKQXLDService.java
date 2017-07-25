package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiNguoiDungDonEnum;
import vn.greenglobal.tttp.enums.PhanLoaiDonCongDanEnum;
import vn.greenglobal.tttp.enums.ThongKeBaoCaoLoaiKyEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class ThongKeBaoCaoTongHopKQXLDService {
	
	@Autowired
	private DonRepository donRepo;

	@Autowired
	private SoTiepCongDanRepository soTiepCongDanRepository;
	
	@Autowired
	private DonCongDanRepository donCongDanRepo;

	@Autowired
	private XuLyDonRepository xuLyDonRepository;
	
	BooleanExpression baseXLD = QXuLyDon.xuLyDon.daXoa.eq(false);
	BooleanExpression baseTCD = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false);
	BooleanExpression baseDonCongDan = QDon_CongDan.don_CongDan.daXoa.eq(false);
	BooleanExpression baseDon = QDon.don.daXoa.eq(false);
	
	public Predicate predicateFindAllXLD(String loaiKy, Integer quy, Integer year, Integer month, String tuNgay, String denNgay) {
		BooleanExpression predAllXLD = baseXLD;
		if (year != null && year > 0) { 
			predAllXLD = predAllXLD.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.year().eq(year));
		}
		
		if(loaiKy != null && StringUtils.isNotBlank(loaiKy)){
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			if (loaiKyEnum != null) { 
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
					if (quy != null && quy > 0) { 
						if (quy == 1) { 
							predAllXLD = predAllXLD.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.month().between(1, 3));
						}
						if (quy == 2) { 
							predAllXLD = predAllXLD.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.month().between(4, 6));
						}
						if (quy == 3) { 
							predAllXLD = predAllXLD.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.month().between(7, 9));
						}
						if (quy == 4) { 
							predAllXLD = predAllXLD.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.month().between(10, 12));
						}
					}
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_DAU_NAM)) {
					predAllXLD = predAllXLD.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.month().between(1, 6));
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_CUOI_NAM)) {
					predAllXLD = predAllXLD.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.month().between(6, 12));
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_THANG)) {
					if (month != null && month > 0) {
						predAllXLD = predAllXLD.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.month().eq(month));
					}
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_NGAY)) {
					if (StringUtils.isNotBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
						LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
						LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
						predAllXLD = predAllXLD.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.between(dtTuNgay, dtDenNgay));
					} else {
						if (StringUtils.isNotBlank(tuNgay)) {
							LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
							predAllXLD = predAllXLD.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.after(dtTuNgay));
						}
						if (StringUtils.isNotBlank(denNgay)) {
							LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
							predAllXLD = predAllXLD.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.before(dtDenNgay));
						}
					}
				}
			}
		}
		return predAllXLD;
	}
	
	public Predicate predicateFindAllTCD(String loaiKy, Integer quy, Integer year, Integer month, String tuNgay, String denNgay) {
		BooleanExpression predAllTCD = baseTCD.and(QSoTiepCongDan.soTiepCongDan.don.thanhLapDon.isTrue());
		if (year != null && year > 0) { 
			predAllTCD = predAllTCD.and(QSoTiepCongDan.soTiepCongDan.don.ngayTiepNhan.year().eq(year));
		}
		
		if(loaiKy != null && StringUtils.isNotBlank(loaiKy)){
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			if (loaiKyEnum != null) { 
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
					if (quy != null && quy > 0) { 
						if (quy == 1) { 
							predAllTCD = predAllTCD.and(QSoTiepCongDan.soTiepCongDan.don.ngayTiepNhan.month().between(1, 3));
						}
						if (quy == 2) { 
							predAllTCD = predAllTCD.and(QSoTiepCongDan.soTiepCongDan.don.ngayTiepNhan.month().between(4, 6));
						}
						if (quy == 3) { 
							predAllTCD = predAllTCD.and(QSoTiepCongDan.soTiepCongDan.don.ngayTiepNhan.month().between(7, 9));
						}
						if (quy == 4) { 
							predAllTCD = predAllTCD.and(QSoTiepCongDan.soTiepCongDan.don.ngayTiepNhan.month().between(10, 12));
						}
					}
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_DAU_NAM)) {
					predAllTCD = predAllTCD.and(QSoTiepCongDan.soTiepCongDan.don.ngayTiepNhan.month().between(1, 6));
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_CUOI_NAM)) {
					predAllTCD = predAllTCD.and(QSoTiepCongDan.soTiepCongDan.don.ngayTiepNhan.month().between(6, 12));
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_THANG)) {
					if (month != null && month > 0) {
						predAllTCD = predAllTCD.and(QSoTiepCongDan.soTiepCongDan.don.ngayTiepNhan.month().eq(month));
					}
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_NGAY)) {
					if (StringUtils.isNotBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
						LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
						LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
						predAllTCD = predAllTCD.and(QSoTiepCongDan.soTiepCongDan.don.ngayTiepNhan.between(dtTuNgay, dtDenNgay));
					} else {
						if (StringUtils.isNotBlank(tuNgay)) {
							LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
							predAllTCD = predAllTCD.and(QSoTiepCongDan.soTiepCongDan.don.ngayTiepNhan.after(dtTuNgay));
						}
						if (StringUtils.isNotBlank(denNgay)) {
							LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
							predAllTCD = predAllTCD.and(QSoTiepCongDan.soTiepCongDan.don.ngayTiepNhan.before(dtDenNgay));
						}
					}
				}
			}
		}
		return predAllTCD;
	}
	
	public Long getTongSoDonTiepNhanXLDTCD(BooleanExpression predAllXLD, BooleanExpression predAllTCD) {
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<XuLyDon> xuLyDons = new ArrayList<XuLyDon>();
		Set<Don> dons = new HashSet<Don>();
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAllTCD));
		xuLyDons.addAll((List<XuLyDon>) xuLyDonRepository.findAll(predAllXLD));
		dons.addAll(soTiepCongDans.stream().map(tcd -> tcd.getDon()).distinct().collect(Collectors.toSet()));
		dons.addAll(xuLyDons.stream().map(tcd -> tcd.getDon()).distinct().collect(Collectors.toSet()));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoDonDuDieuKienThuLyLuuDonVaTheoDoi(BooleanExpression predAllXLD) {
		System.out.println("getTongSoDonDuDieuKienThuLyLuuDonVaTheoDoi");
		Long tongSo = 0L;
		List<XuLyDon> xuLyDons = new ArrayList<XuLyDon>();
		Set<Don> dons = new HashSet<Don>();
		predAllXLD = predAllXLD.and(QXuLyDon.xuLyDon.don.huongXuLyXLD.isNotNull())
				.and(QXuLyDon.xuLyDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI))
				.and(QXuLyDon.xuLyDon.don.hoanThanhDon.isTrue());
		xuLyDons.addAll((List<XuLyDon>) xuLyDonRepository.findAll(predAllXLD));
		dons.addAll(xuLyDons.stream().map(tcd -> tcd.getDon()).distinct().collect(Collectors.toSet()));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhLienQuanDenDatDai(BooleanExpression predAllTCD,
			LinhVucDonThu linhVucCha, LinhVucDonThu linhVucHanhChinhKhieuNaiDatDaiNhaCua, 
			List<LinhVucDonThu> linhVucLienQuanDenDatDais) {
		System.out.println("getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhLienQuanDenDatDai");
		Long tongSo = 0L;
		List<XuLyDon> xuLyDons = new ArrayList<XuLyDon>();
		Set<Don> dons = new HashSet<Don>();
		if (linhVucCha == null && linhVucHanhChinhKhieuNaiDatDaiNhaCua == null && linhVucLienQuanDenDatDais == null) { 
			return tongSo;
		}
		predAllTCD = predAllTCD
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThu.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
				.and(QSoTiepCongDan.soTiepCongDan.don.thanhLapDon.isTrue())
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThu.eq(linhVucCha))
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThuChiTiet.eq(linhVucHanhChinhKhieuNaiDatDaiNhaCua))
				.and(QSoTiepCongDan.soTiepCongDan.don.chiTietLinhVucDonThuChiTiet.in(linhVucLienQuanDenDatDais));
		xuLyDons.addAll((List<XuLyDon>) xuLyDonRepository.findAll(predAllTCD));
		dons.addAll(xuLyDons.stream().map(tcd -> tcd.getDon()).distinct().collect(Collectors.toSet()));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTietCon(BooleanExpression predAllTCD,
			LinhVucDonThu linhVuc) {
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		Set<Don> dons = new HashSet<Don>();
		if (linhVuc == null) { 
			return tongSo;
		}
		predAllTCD = predAllTCD
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThu.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
				.and(QSoTiepCongDan.soTiepCongDan.don.chiTietLinhVucDonThuChiTiet.eq(linhVuc));
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAllTCD));
		dons.addAll(soTiepCongDans.stream().map(tcd -> tcd.getDon()).distinct().collect(Collectors.toSet()));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(BooleanExpression predAllTCD,
			LinhVucDonThu linhVuc) {
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		Set<Don> dons = new HashSet<Don>();
		if (linhVuc == null) { 
			return tongSo;
		}
		predAllTCD = predAllTCD
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThu.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThu.eq(linhVuc));
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAllTCD));
		dons.addAll(soTiepCongDans.stream().map(tcd -> tcd.getDon()).distinct().collect(Collectors.toSet()));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(BooleanExpression predAllTCD,
			LinhVucDonThu linhVuc) {
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		Set<Don> dons = new HashSet<Don>();
		if (linhVuc == null) { 
			return tongSo;
		}
		predAllTCD = predAllTCD
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThu.loaiDon.eq(LoaiDonEnum.DON_TO_CAO))
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThu.eq(linhVuc));
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAllTCD));
		dons.addAll(soTiepCongDans.stream().map(tcd -> tcd.getDon()).distinct().collect(Collectors.toSet()));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
	
	public Long getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucChiTietCha(BooleanExpression predAllTCD,
			LinhVucDonThu linhVuc, LinhVucDonThu linhVucChiTiet) {
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		Set<Don> dons = new HashSet<Don>();
		if (linhVuc == null && linhVucChiTiet == null) { 
			return tongSo;
		}
		predAllTCD = predAllTCD
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThu.loaiDon.eq(LoaiDonEnum.DON_TO_CAO))
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThu.eq(linhVuc))
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThuChiTiet.eq(linhVucChiTiet));
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAllTCD));
		dons.addAll(soTiepCongDans.stream().map(tcd -> tcd.getDon()).distinct().collect(Collectors.toSet()));
		tongSo = Long.valueOf(dons.size());
		return tongSo;
	}
}
