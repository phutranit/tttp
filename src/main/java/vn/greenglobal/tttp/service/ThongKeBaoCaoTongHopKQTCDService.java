package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiNguoiDungDonEnum;
import vn.greenglobal.tttp.enums.PhanLoaiDonCongDanEnum;
import vn.greenglobal.tttp.enums.ThongKeBaoCaoLoaiKyEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class ThongKeBaoCaoTongHopKQTCDService {
	
	@Autowired
	private DonRepository donRepo;

	@Autowired
	private SoTiepCongDanRepository soTiepCongDanRepository;
	
	@Autowired
	private DonCongDanRepository donCongDanRepo;
	
	BooleanExpression baseTCD = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false);
	BooleanExpression baseDonCongDan = QDon_CongDan.don_CongDan.daXoa.eq(false);
	BooleanExpression baseDon = QDon.don.daXoa.eq(false);
	
	public Predicate predicateFindAllTCD(String loaiKy, int quy, int year, int month, String tuNgay, String denNgay, Long donViId) { 
		BooleanExpression predAll = baseTCD;
		ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

		if (year > 0) { 
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepDan.year().eq(year));
		}
		
		if (loaiKyEnum != null) { 
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) { 
				if (quy == 1) { 
					predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepDan.month().between(1, 3));
				}
				if (quy == 2) { 
					predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepDan.month().between(4, 6));
				}
				if (quy == 3) { 
					predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepDan.month().between(7, 9));
				}
				if (quy == 4) { 
					predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepDan.month().between(10, 12));
				}
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_DAU_NAM)) {
				predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepDan.month().between(1, 6));
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_CUOI_NAM)) {
				predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepDan.month().between(6, 12));
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_THANG)) {
				if (month > 0) {
					predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepDan.month().eq(month));
				}
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_NGAY)) {
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
			}
		}

		if (donViId != null && donViId > 0) { 
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(donViId)
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(donViId))
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(donViId)));
		}
		
		return predAll;
	}
	
	public Long getTongSoLuocTiepCongDanThuongXuyen(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		tongSo = Long.valueOf(soTiepCongDans.size());
		return tongSo;
	}
	
	public Long getTongSoNguoiDungTenTiepCongDanThuongXuyen(BooleanExpression predAll, boolean isCaNhanVaToChuc, 
			boolean isDoanDongNguoi) { 
		Long tongSo = 0L;
		BooleanExpression donCongDanQuery = baseDonCongDan;

		if (isCaNhanVaToChuc) {
			List<Don> dons = new ArrayList<Don>();
			LoaiNguoiDungDonEnum caNhan = LoaiNguoiDungDonEnum.CA_NHAN;
			LoaiNguoiDungDonEnum toChuc = LoaiNguoiDungDonEnum.CO_QUAN_TO_CHUC;
			BooleanExpression donQuery = baseDon;
			
			donQuery = donQuery.and(QDon.don.loaiNguoiDungDon.eq(caNhan)
					.or(QDon.don.loaiNguoiDungDon.eq(toChuc)));
			dons.addAll((List<Don>) donRepo.findAll(donQuery));
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		if (isDoanDongNguoi) {
			List<Don> dons = new ArrayList<Don>();
			LoaiNguoiDungDonEnum doanDongNguoi = LoaiNguoiDungDonEnum.DOAN_DONG_NGUOI;
			BooleanExpression donQuery = baseDon;
			
			donQuery = donQuery.and(QDon.don.loaiNguoiDungDon.eq(doanDongNguoi));
			dons.addAll((List<Don>) donRepo.findAll(donQuery));
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		
		List<Don> dons = new ArrayList<Don>();
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		dons.addAll(soTiepCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));
		PhanLoaiDonCongDanEnum nguoiDungDon = PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON;
		donCongDanQuery = donCongDanQuery.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.eq(nguoiDungDon));
		donCongDanQuery = donCongDanQuery.and(QDon_CongDan.don_CongDan.don.in(dons));
		
		donCongDans.addAll((List<Don_CongDan>) donCongDanRepo.findAll(donCongDanQuery));
		tongSo = Long.valueOf(donCongDans.size());
		return tongSo;
	}
	
	public Long getTongSoDoanDongNguoiTiepCongDanThuongXuyen(BooleanExpression predAll, boolean isDoanDongNguoi) { 
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		
		if (isDoanDongNguoi) {
			List<Don> dons = new ArrayList<Don>();
			LoaiNguoiDungDonEnum doanDongNguoi = LoaiNguoiDungDonEnum.DOAN_DONG_NGUOI;
			BooleanExpression donQuery = baseDon;
			
			donQuery = donQuery.and(QDon.don.loaiNguoiDungDon.eq(doanDongNguoi));
			dons.addAll((List<Don>) donRepo.findAll(donQuery));
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		tongSo = Long.valueOf(soTiepCongDans.size());
		return tongSo;
	}
	
	public Long getTongSoLuocTiepCongDanDinhKyDotXuat(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		tongSo = Long.valueOf(soTiepCongDans.size());
		return tongSo;
	}
	
	public Long getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(BooleanExpression predAll, boolean isCaNhanVaToChuc, 
			boolean isDoanDongNguoi) { 
		Long tongSo = 0L;
		BooleanExpression donCongDanQuery = baseDonCongDan;

		if (isCaNhanVaToChuc) {
			List<Don> dons = new ArrayList<Don>();
			LoaiNguoiDungDonEnum caNhan = LoaiNguoiDungDonEnum.CA_NHAN;
			LoaiNguoiDungDonEnum toChuc = LoaiNguoiDungDonEnum.CO_QUAN_TO_CHUC;
			BooleanExpression donQuery = baseDon;
			
			donQuery = donQuery.and(QDon.don.loaiNguoiDungDon.eq(caNhan)
					.or(QDon.don.loaiNguoiDungDon.eq(toChuc)));
			dons.addAll((List<Don>) donRepo.findAll(donQuery));
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		if (isDoanDongNguoi) {
			List<Don> dons = new ArrayList<Don>();
			LoaiNguoiDungDonEnum doanDongNguoi = LoaiNguoiDungDonEnum.DOAN_DONG_NGUOI;
			BooleanExpression donQuery = baseDon;
			
			donQuery = donQuery.and(QDon.don.loaiNguoiDungDon.eq(doanDongNguoi));
			dons.addAll((List<Don>) donRepo.findAll(donQuery));
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		
		List<Don> dons = new ArrayList<Don>();
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		dons.addAll(soTiepCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));

		PhanLoaiDonCongDanEnum nguoiDungDon = PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON;
		donCongDanQuery = donCongDanQuery.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.eq(nguoiDungDon));
		donCongDanQuery = donCongDanQuery.and(QDon_CongDan.don_CongDan.don.in(dons));
		
		donCongDans.addAll((List<Don_CongDan>) donCongDanRepo.findAll(donCongDanQuery));
		tongSo = Long.valueOf(donCongDans.size());
		return tongSo;
	}
	
	public Long getTongSoDoanDongNguoiTiepCongDanDinhKyDotXuat(BooleanExpression predAll, boolean isDoanDongNguoi) { 
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();

		if (isDoanDongNguoi) {
			List<Don> dons = new ArrayList<Don>();
			LoaiNguoiDungDonEnum doanDongNguoi = LoaiNguoiDungDonEnum.DOAN_DONG_NGUOI;
			BooleanExpression donQuery = baseDon;
			
			donQuery = donQuery.and(QDon.don.loaiNguoiDungDon.eq(doanDongNguoi));
			dons.addAll((List<Don>) donRepo.findAll(donQuery));
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		tongSo = Long.valueOf(soTiepCongDans.size());
		return tongSo;
	}
	
	public Long getTongSoVuViecTiepCongDanDonKienNghiPhanAnh(BooleanExpression predAll) { 
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.loaiDon.eq(LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH));
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		dons.addAll(soTiepCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));
		tongSo = Long.valueOf(dons.stream().map(d -> {
			Long tongSoVuViec = 0L;
			if (d.getThongTinGiaiQuyetDon() != null) {
				ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
				tongSoVuViec += ttgqd.getSoVuGiaiQuyetKhieuNai();
			} else {
				tongSoVuViec += 1;
			}
			return tongSoVuViec;
		}).distinct().mapToLong(Long::longValue).sum());
		return tongSo;
	}
	
	public Long getTongSoVuViecTiepCongDanDonToCaoLinhVucHanhChinhVaTuPhap(BooleanExpression predAll, LinhVucDonThu linhVuc) {
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Don> dons = new ArrayList<Don>();
		
		if(linhVuc == null) { 
			return tongSo;
		}
		
		predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThu.loaiDon.eq(LoaiDonEnum.DON_TO_CAO))
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThu.eq(linhVuc));
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		dons.addAll(soTiepCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));
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
	
	public Long getTongSoVuViecTiepCongDanDonToCaoLinhVucThamNhung(BooleanExpression predAll, LinhVucDonThu linhVuc) {
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Don> dons = new ArrayList<Don>();
		
		if(linhVuc == null) { 
			return tongSo;
		}
		
		predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThuChiTiet.loaiDon.eq(LoaiDonEnum.DON_TO_CAO))
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThuChiTiet.eq(linhVuc));
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		dons.addAll(soTiepCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));
		
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
	
	public Long getTongSoVuViecTiepCongDanDonKhieuNaiLinhVucChiTietCha(BooleanExpression predAll, LinhVucDonThu linhVuc) {
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Don> dons = new ArrayList<Don>();
		
		if(linhVuc == null) { 
			return tongSo;
		}
		
		predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThuChiTiet.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThuChiTiet.eq(linhVuc));
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		dons.addAll(soTiepCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));
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
	
	public Long getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(BooleanExpression predAll, LinhVucDonThu linhVuc) {
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Don> dons = new ArrayList<Don>();
		
		if(linhVuc == null) { 
			return tongSo;
		}
		
		predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThu.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
				.and(QSoTiepCongDan.soTiepCongDan.don.linhVucDonThu.eq(linhVuc));
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		dons.addAll(soTiepCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));
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
	
	public Long getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCon(BooleanExpression predAll, List<LinhVucDonThu> linhVucs) {
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Don> dons = new ArrayList<Don>();
		
		if(linhVucs == null) { 
			return tongSo;
		}
		
		predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.chiTietLinhVucDonThuChiTiet.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
				.and(QSoTiepCongDan.soTiepCongDan.don.chiTietLinhVucDonThuChiTiet.in(linhVucs));
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		dons.addAll(soTiepCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));
		
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
	
	public Long getTongSoVuViecTiepCongDanDonChuaChotHuongXuLyXLD(BooleanExpression predAll) {
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Don> dons = new ArrayList<Don>();
		
		predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.thanhLapDon.isTrue())
				.and(QSoTiepCongDan.soTiepCongDan.don.ngayKetThucXLD.isNull());
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		dons.addAll(soTiepCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));
		
		tongSo = Long.valueOf(dons.stream().map(d -> {
			Long tongSoVuViec = 1L;
			return tongSoVuViec;
		}).mapToLong(Long::longValue).sum());		
		return tongSo;
	}
	
	public Long getTongSoVuViecTiepCongDanDonChuaCoQuyetDinhGiaiQuyet(BooleanExpression predAll) {
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Don> dons = new ArrayList<Don>();
		
		predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.thanhLapDon.isTrue())
				.and(QSoTiepCongDan.soTiepCongDan.don.thongTinGiaiQuyetDon.isNotNull())
				.and(QSoTiepCongDan.soTiepCongDan.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.isNull());
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		dons.addAll(soTiepCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));
		
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
	
	public Long getTongSoVuViecTiepCongDanDonDaCoQuyetDinhGiaiQuyet(BooleanExpression predAll) {
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Don> dons = new ArrayList<Don>();
		
		predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.thanhLapDon.isTrue())
				.and(QSoTiepCongDan.soTiepCongDan.don.coThongTinCoQuanDaGiaiQuyet.isTrue())
				.and(QSoTiepCongDan.soTiepCongDan.don.coQuanDaGiaiQuyet.isNotNull())
				.and(QSoTiepCongDan.soTiepCongDan.don.thongTinGiaiQuyetDon.isNotNull())
				.and(QSoTiepCongDan.soTiepCongDan.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.isNotNull());
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		dons.addAll(soTiepCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));
		
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
