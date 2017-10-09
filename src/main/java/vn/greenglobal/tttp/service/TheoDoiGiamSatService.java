package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QGiaiQuyetDon;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class TheoDoiGiamSatService {
BooleanExpression base = QDon.don.daXoa.eq(false);
	
	public Predicate predicateFindDanhSachDons(String tiepNhanTuNgay, String tiepNhanDenNgay, Long month, Long year, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.old.eq(false));
		
		if (month != null && month > 0) { 
			predAll = predAll.and(QDon.don.ngayTiepNhan.month().eq(month.intValue()));
		}
		
		if (year != null && year > 0) { 
			predAll = predAll.and(QDon.don.ngayTiepNhan.year().eq(year.intValue()));
		}
		
		if (tiepNhanTuNgay != null && tiepNhanDenNgay != null && StringUtils.isNotBlank(tiepNhanTuNgay.trim())
				&& StringUtils.isNotBlank(tiepNhanDenNgay.trim())) {
			LocalDateTime tuNgay = Utils.fixTuNgay(tiepNhanTuNgay);
			LocalDateTime denNgay = Utils.fixDenNgay(tiepNhanDenNgay);
			predAll = predAll.and(QDon.don.ngayTiepNhan.between(tuNgay, denNgay));
		} else if (StringUtils.isBlank(tiepNhanTuNgay) && StringUtils.isNotBlank(tiepNhanDenNgay)) {
			LocalDateTime denNgay = Utils.fixDenNgay(tiepNhanDenNgay);
			predAll = predAll.and(QDon.don.ngayTiepNhan.before(denNgay));
		} else if (StringUtils.isNotBlank(tiepNhanTuNgay) && StringUtils.isBlank(tiepNhanDenNgay)) {
			LocalDateTime tuNgay = Utils.fixTuNgay(tiepNhanTuNgay);
			predAll = predAll.and(QDon.don.ngayTiepNhan.after(tuNgay));
		}
		return predAll;
	}
	
	
	
	public Predicate predicateFindDanhSachDonsTheoDonViXLD(BooleanExpression predDS, Long donViXuLyXLD, XuLyDonRepository xuLyRepo,
			DonRepository donRepo) {
		BooleanExpression predAll = predDS;
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false));
		List<Don> donCollections = new ArrayList<Don>();
		List<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}
		
		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
		
		predAll = predAll.and(QDon.don.in(donCollections));
		
		return predAll;
	}
	
	public Long getTongSoDonDungHanTreHanByTrangThaiXLD(BooleanExpression predAll, DonRepository donRepo, boolean isDungHan, TrangThaiDonEnum trangThaiEnum) {
		Long tongSo = 0L;
		if (predAll != null) {
			List<Don> dons = new ArrayList<Don>();
			if (trangThaiEnum.equals(TrangThaiDonEnum.DANG_XU_LY)) {
				predAll = predAll.and(QDon.don.trangThaiDon.eq(trangThaiEnum));
				predAll = predAll.and(QDon.don.ngayKetThucXLD.isNull());
				dons.addAll((List<Don>) donRepo.findAll(predAll));
				if (isDungHan) {
					dons = dons.stream().filter(d -> {
						if (d.getNgayBatDauXLD() != null && d.getThoiHanXuLyXLD() != null) {
							return Utils.isValidNgayDungHanDangXuLy(d.getThoiHanXuLyXLD());
						}
						return false;
					}).collect(Collectors.toList());
				} else {		
					dons = dons.stream().filter(d -> {
						if (d.getNgayBatDauXLD() != null && d.getThoiHanXuLyXLD() != null) {
							return Utils.isValidNgayTreHanDangXuLy(d.getThoiHanXuLyXLD());
						}
						return false;
					}).collect(Collectors.toList());
				}
			} else {
				predAll = predAll.and(QDon.don.ngayKetThucXLD.isNotNull());
				dons.addAll((List<Don>) donRepo.findAll(predAll));
				if (isDungHan) {
					dons = dons.stream().filter(d -> {
						if (d.getNgayBatDauXLD() != null && d.getThoiHanXuLyXLD() != null && d.getNgayKetThucXLD() != null) { 
							return Utils.isValidNgayDungHanDaXuLy(d.getThoiHanXuLyXLD(), d.getNgayKetThucXLD());
						}
						return false;
					}).collect(Collectors.toList());
				} else {
					dons = dons.stream().filter(d -> {
						if (d.getNgayBatDauXLD() != null && d.getThoiHanXuLyXLD() != null && d.getNgayKetThucXLD() != null) { 
							return Utils.isValidNgayTreHanDaXuLy(d.getThoiHanXuLyXLD(), d.getNgayKetThucXLD());
						}
						return false;
					}).collect(Collectors.toList());
				}
			}
			
			tongSo = Long.valueOf(dons.size());
		}
		return tongSo;
	}
	
	public Predicate predicateFindDanhSachDonsTheoDonViKTDX(BooleanExpression predDS, Long donViKTDX, GiaiQuyetDonRepository giaiQuyetDonRepo,
			DonRepository donRepo) {
		BooleanExpression predAll = predDS;
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false));
		List<Don> donCollections = new ArrayList<Don>();
		List<GiaiQuyetDon> gqdCollections = new ArrayList<GiaiQuyetDon>();
		
		if (donViKTDX != null && donViKTDX > 0) {
			giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.id.eq(donViKTDX));
		}
		Iterable<GiaiQuyetDon> giaiQuyetDons = giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		CollectionUtils.addAll(gqdCollections, giaiQuyetDons.iterator());
		donCollections = gqdCollections.stream().map(d -> {
			return d.getThongTinGiaiQuyetDon().getDon();
		}).distinct().collect(Collectors.toList());
		
		predAll = predAll.and(QDon.don.in(donCollections));
		
		return predAll;
	}
	
	public Long getTongSoDonDungHanTreHanByTrangThaiKTDX(BooleanExpression predAll, DonRepository donRepo, boolean isDungHan, TrangThaiDonEnum trangThaiEnum) {
		Long tongSo = 0L;
		if (predAll != null) {
			List<Don> dons = new ArrayList<Don>();
			predAll = predAll.and(QDon.don.trangThaiKTDX.eq(trangThaiEnum));
			if (trangThaiEnum.equals(TrangThaiDonEnum.DANG_GIAI_QUYET)) { 
				predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucKTDX.isNull());
				dons.addAll((List<Don>) donRepo.findAll(predAll));
				if (isDungHan) {
					dons = dons.stream().filter(d -> {
						ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
						if (ttgqd.getNgayBatDauKTDX() != null && ttgqd.getNgayHetHanKTDX() != null) {
							return Utils.isValidNgayDungHanDangXuLy(ttgqd.getNgayHetHanKTDX());
						}
						return false;
					}).collect(Collectors.toList());
				} else {		
					dons = dons.stream().filter(d -> {
						ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
						if (ttgqd.getNgayBatDauKTDX() != null && ttgqd.getNgayHetHanKTDX() != null) {
							return Utils.isValidNgayTreHanDangXuLy(ttgqd.getNgayHetHanKTDX());
						}
						return false;
					}).collect(Collectors.toList());
				}
			} else {
				predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucKTDX.isNotNull());
				dons.addAll((List<Don>) donRepo.findAll(predAll));
				if (isDungHan) {
					dons = dons.stream().filter(d -> {
						ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
						if (ttgqd.getNgayBatDauKTDX() != null && ttgqd.getNgayHetHanKTDX() != null && ttgqd.getNgayKetThucKTDX() != null) { 
							return Utils.isValidNgayDungHanDaXuLy(ttgqd.getNgayHetHanKTDX(), ttgqd.getNgayKetThucKTDX());
						}
						return false;
					}).collect(Collectors.toList());
				} else {
					dons = dons.stream().filter(d -> {
						ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
						if (ttgqd.getNgayBatDauKTDX() != null && ttgqd.getNgayHetHanKTDX() != null && ttgqd.getNgayKetThucKTDX() != null) { 
							return Utils.isValidNgayTreHanDaXuLy(ttgqd.getNgayHetHanKTDX(), ttgqd.getNgayKetThucKTDX());
						}
						return false;
					}).collect(Collectors.toList());
				}
			}
			
			tongSo = Long.valueOf(dons.size());
		}
		return tongSo;
	}
	
	public Predicate predicateFindDanhSachDonsTheoDonViTTXM(BooleanExpression predDS, Long donViXuLyTTXM, GiaiQuyetDonRepository giaiQuyetDonRepo,
			DonRepository donRepo) {
		BooleanExpression predAll = predDS;
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false))
				.and(QGiaiQuyetDon.giaiQuyetDon.donViChuyenDon.isNotNull());
		List<Don> donCollections = new ArrayList<Don>();
		List<GiaiQuyetDon> gqdCollections = new ArrayList<GiaiQuyetDon>();
		
		if (donViXuLyTTXM != null && donViXuLyTTXM > 0) {
			giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.id.eq(donViXuLyTTXM));
		}
		Iterable<GiaiQuyetDon> giaiQuyetDons = giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		CollectionUtils.addAll(gqdCollections, giaiQuyetDons.iterator());
		donCollections = gqdCollections.stream().map(d -> {
			return d.getThongTinGiaiQuyetDon().getDon();
		}).distinct().collect(Collectors.toList());
		
		predAll = predAll.and(QDon.don.in(donCollections));
		
		return predAll;
	}
	
	public Long getTongSoDonDungHanTreHanByTrangThaiTTXM(BooleanExpression predAll, DonRepository donRepo, boolean isDungHan, TrangThaiDonEnum trangThaiEnum) {
		Long tongSo = 0L;
		if (predAll != null) {
			List<Don> dons = new ArrayList<Don>();
			if (trangThaiEnum.equals(TrangThaiDonEnum.DANG_GIAI_QUYET)) {
				predAll = predAll.and(QDon.don.trangThaiDon.eq(trangThaiEnum));
				predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucTTXM.isNull());
				dons.addAll((List<Don>) donRepo.findAll(predAll));
				if (isDungHan) {
					dons = dons.stream().filter(d -> {
//						ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//						if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null) {
//							return Utils.isValidNgayDungHanDangXuLy(ttgqd.getNgayHetHanTTXM());
//						}
//						return false;
						return invalidNgayDungHanDangGQTTXM(d);
					}).collect(Collectors.toList());
				} else {		
					dons = dons.stream().filter(d -> {
//						ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//						if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null) {
//							return Utils.isValidNgayTreHanDangXuLy(ttgqd.getNgayHetHanTTXM());
//						}
//						return false;
						return invalidNgayTreHanDangGQTTXM(d);
					}).collect(Collectors.toList());
				}
			} else {
				predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucTTXM.isNotNull());
				dons.addAll((List<Don>) donRepo.findAll(predAll));
				if (isDungHan) {
					dons = dons.stream().filter(d -> {
//						ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//						if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null && ttgqd.getNgayKetThucTTXM() != null) { 
//							return Utils.isValidNgayDungHanDaXuLy(ttgqd.getNgayHetHanTTXM(), ttgqd.getNgayKetThucTTXM());
//						}
//						return false;
						return invalidNgayDungHanDaGQTTXM(d);
					}).collect(Collectors.toList());
				} else {
					dons = dons.stream().filter(d -> {
//						ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//						if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null && ttgqd.getNgayKetThucTTXM() != null) { 
//							return Utils.isValidNgayTreHanDaXuLy(ttgqd.getNgayHetHanTTXM(), ttgqd.getNgayKetThucTTXM());
//						}
//						return false;
						return invalidNgayTreHanDaGQTTXM(d);
					}).collect(Collectors.toList());
				}
			}
			
			tongSo = Long.valueOf(dons.size());
		}
		return tongSo;
	}
	
	public Predicate predicateFindDanhSachDonsTheoDonViGQD(BooleanExpression predDS, Long donViXuLyXLD, GiaiQuyetDonRepository giaiQuyetDonRepo,
			DonRepository donRepo) {
		BooleanExpression predAll = predDS;
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false))
				.and(QGiaiQuyetDon.giaiQuyetDon.donViChuyenDon.isNull());
		List<Don> donCollections = new ArrayList<Don>();
		List<GiaiQuyetDon> gqdCollections = new ArrayList<GiaiQuyetDon>();
		
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.id.eq(donViXuLyXLD));
		}
		Iterable<GiaiQuyetDon> giaiQuyetDons = giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		CollectionUtils.addAll(gqdCollections, giaiQuyetDons.iterator());
		donCollections = gqdCollections.stream().map(d -> {
			return d.getThongTinGiaiQuyetDon().getDon();
		}).distinct().collect(Collectors.toList());
		
		predAll = predAll.and(QDon.don.in(donCollections));
		
		return predAll;
	}
	
	public Long getTongSoDonDungHanTreHanByTrangThaiGQD(BooleanExpression predAll, DonRepository donRepo, boolean isDungHan, TrangThaiDonEnum trangThaiEnum) {
		Long tongSo = 0L;
		if (predAll != null) {
			List<Don> dons = new ArrayList<Don>();
			if (trangThaiEnum.equals(TrangThaiDonEnum.DANG_GIAI_QUYET)) {
				predAll = predAll.and(QDon.don.trangThaiDon.eq(trangThaiEnum));
				predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.isNull());
				dons.addAll((List<Don>) donRepo.findAll(predAll));
				if (isDungHan) {
					dons = dons.stream().filter(d -> {
//						ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//						if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null) {
//							return Utils.isValidNgayDungHanDangXuLy(ttgqd.getNgayHetHanGiaiQuyet());
//						}
//						return false;
						return invalidNgayDungHanDangGQGQD(d);
					}).collect(Collectors.toList());
				} else {		
					dons = dons.stream().filter(d -> {
//						ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//						if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null) {
//							return Utils.isValidNgayTreHanDangXuLy(ttgqd.getNgayHetHanGiaiQuyet());
//						}
//						return false;
						return invalidNgayTreHanDangGQGQD(d);
					}).collect(Collectors.toList());
				}
			} else {
				predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.isNotNull());
				dons.addAll((List<Don>) donRepo.findAll(predAll));
				if (isDungHan) {
					dons = dons.stream().filter(d -> {
//						ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//						if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayKetThucGiaiQuyet() != null) { 
//							return Utils.isValidNgayDungHanDaXuLy(ttgqd.getNgayHetHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
//						}
//						return false;
						return invalidNgayDungHanDaGQGQD(d);
					}).collect(Collectors.toList());
				} else {
					dons = dons.stream().filter(d -> {
//						ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//						if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayKetThucGiaiQuyet() != null) { 
//							return Utils.isValidNgayTreHanDaXuLy(ttgqd.getNgayHetHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
//						}
//						return false;
						return invalidNgayTreHanDaGQGQD(d);
					}).collect(Collectors.toList());
				}
			}
			
			tongSo = Long.valueOf(dons.size());
		}
		return tongSo;
	}
	
	public Long getTongSoDon(BooleanExpression predAll, DonRepository donRepo) {
		Long tongSo = 0L;
		if (predAll != null) {
			List<Don> dons = new ArrayList<Don>();
			dons.addAll((List<Don>) donRepo.findAll(predAll));
			tongSo = Long.valueOf(dons.size());
		}
		return tongSo;
	}
	
	public Predicate predicateFindTongSoDonDungHanTreHanByTrangThaiGQD(BooleanExpression predAll, DonRepository donRepo, boolean isDungHan, TrangThaiDonEnum trangThaiEnum) {
		List<Don> dons = new ArrayList<Don>();
		if (trangThaiEnum.equals(TrangThaiDonEnum.DANG_GIAI_QUYET)) {
			predAll = predAll.and(QDon.don.trangThaiDon.eq(trangThaiEnum));
			predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.isNull());
			dons.addAll((List<Don>) donRepo.findAll(predAll));
			if (isDungHan) {
				dons = dons.stream().filter(d -> {
//					ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//					if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null) {
//						return Utils.isValidNgayDungHanDangXuLy(ttgqd.getNgayHetHanGiaiQuyet());
//					}
//					return false;
					return invalidNgayDungHanDangGQGQD(d);
				}).collect(Collectors.toList());
			} else {		
				dons = dons.stream().filter(d -> {
//					ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//					if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null) {
//						return Utils.isValidNgayTreHanDangXuLy(ttgqd.getNgayHetHanGiaiQuyet());
//					}
//					return false;
					return invalidNgayTreHanDangGQGQD(d);
				}).collect(Collectors.toList());
			}
		} else {
			predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucGiaiQuyet.isNotNull());
			dons.addAll((List<Don>) donRepo.findAll(predAll));
			if (isDungHan) {
				dons = dons.stream().filter(d -> {
//					ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//					if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayKetThucGiaiQuyet() != null) { 
//						return Utils.isValidNgayDungHanDaXuLy(ttgqd.getNgayHetHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
//					}
//					return false;
					return invalidNgayDungHanDaGQGQD(d);
				}).collect(Collectors.toList());
			} else {
				dons = dons.stream().filter(d -> {
//					ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//					if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayKetThucGiaiQuyet() != null) { 
//						return Utils.isValidNgayTreHanDaXuLy(ttgqd.getNgayHetHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
//					}
//					return false;
					return invalidNgayTreHanDaGQGQD(d);
				}).collect(Collectors.toList());
			}
		}
		predAll = predAll.and(QDon.don.in(dons));
		return predAll;
	}
	
	public Predicate predicateFindTongSoDonDungHanTreHanByTrangThaiXLD(BooleanExpression predAll, DonRepository donRepo, boolean isDungHan, TrangThaiDonEnum trangThaiEnum) {
		List<Don> dons = new ArrayList<Don>();
		if (trangThaiEnum.equals(TrangThaiDonEnum.DANG_XU_LY)) {
			predAll = predAll.and(QDon.don.trangThaiDon.eq(trangThaiEnum));
			predAll = predAll.and(QDon.don.ngayKetThucXLD.isNull());
			dons.addAll((List<Don>) donRepo.findAll(predAll));
			if (isDungHan) {
				dons = dons.stream().filter(d -> {
					if (d.getNgayBatDauXLD() != null && d.getThoiHanXuLyXLD() != null) {
						return Utils.isValidNgayDungHanDangXuLy(d.getThoiHanXuLyXLD());
					}
					return false;
				}).collect(Collectors.toList());
			} else {		
				dons = dons.stream().filter(d -> {
					if (d.getNgayBatDauXLD() != null && d.getThoiHanXuLyXLD() != null) {
						return Utils.isValidNgayTreHanDangXuLy(d.getThoiHanXuLyXLD());
					}
					return false;
				}).collect(Collectors.toList());
			}
		} else {
			predAll = predAll.and(QDon.don.ngayKetThucXLD.isNotNull());
			dons.addAll((List<Don>) donRepo.findAll(predAll));
			if (isDungHan) {
				dons = dons.stream().filter(d -> {
					if (d.getNgayBatDauXLD() != null && d.getThoiHanXuLyXLD() != null && d.getNgayKetThucXLD() != null) { 
						return Utils.isValidNgayDungHanDaXuLy(d.getThoiHanXuLyXLD(), d.getNgayKetThucXLD());
					}
					return false;
				}).collect(Collectors.toList());
			} else {
				dons = dons.stream().filter(d -> {
					if (d.getNgayBatDauXLD() != null && d.getThoiHanXuLyXLD() != null && d.getNgayKetThucXLD() != null) { 
						return Utils.isValidNgayTreHanDaXuLy(d.getThoiHanXuLyXLD(), d.getNgayKetThucXLD());
					}
					return false;
				}).collect(Collectors.toList());
			}
		}
		predAll = predAll.and(QDon.don.in(dons));
		return predAll;
	}
	
	public Predicate predicateFindTongSoDonDungHanTreHanByTrangThaiTTXM(BooleanExpression predAll, DonRepository donRepo, boolean isDungHan, TrangThaiDonEnum trangThaiEnum) {
		List<Don> dons = new ArrayList<Don>();
		if (trangThaiEnum.equals(TrangThaiDonEnum.DANG_GIAI_QUYET)) {
			predAll = predAll.and(QDon.don.trangThaiDon.eq(trangThaiEnum));
			predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucTTXM.isNull());
			dons.addAll((List<Don>) donRepo.findAll(predAll));
			if (isDungHan) {
				dons = dons.stream().filter(d -> {
//					ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//					if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null) {
//						return Utils.isValidNgayDungHanDangXuLy(ttgqd.getNgayHetHanTTXM());
//					}
//					return false;
					return invalidNgayDungHanDangGQTTXM(d);
				}).collect(Collectors.toList());
			} else {		
				dons = dons.stream().filter(d -> {
//					ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//					if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null) {
//						return Utils.isValidNgayTreHanDangXuLy(ttgqd.getNgayHetHanTTXM());
//					}
//					return false;
					return invalidNgayTreHanDangGQTTXM(d);
				}).collect(Collectors.toList());
			}
		} else {
			predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucTTXM.isNotNull());
			dons.addAll((List<Don>) donRepo.findAll(predAll));
			if (isDungHan) {
				dons = dons.stream().filter(d -> {
//					ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//					if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null && ttgqd.getNgayKetThucTTXM() != null) { 
//						return Utils.isValidNgayDungHanDaXuLy(ttgqd.getNgayHetHanTTXM(), ttgqd.getNgayKetThucTTXM());
//					}
//					return false;
					return invalidNgayDungHanDaGQTTXM(d);
				}).collect(Collectors.toList());
			} else {
				dons = dons.stream().filter(d -> {
//					ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
//					if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null && ttgqd.getNgayKetThucTTXM() != null) { 
//						return Utils.isValidNgayTreHanDaXuLy(ttgqd.getNgayHetHanTTXM(), ttgqd.getNgayKetThucTTXM());
//					}
//					return false;
					return invalidNgayTreHanDaGQTTXM(d);
				}).collect(Collectors.toList());
			}
		}
		predAll = predAll.and(QDon.don.in(dons));
		return predAll;
	}
	
	public Predicate predicateFindTongSoDonDungHanTreHanByTrangThaiKTDX(BooleanExpression predAll, DonRepository donRepo, boolean isDungHan, TrangThaiDonEnum trangThaiEnum) {
		List<Don> dons = new ArrayList<Don>();
		predAll = predAll.and(QDon.don.trangThaiKTDX.eq(trangThaiEnum));
		if (trangThaiEnum.equals(TrangThaiDonEnum.DANG_GIAI_QUYET)) { 
			predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucKTDX.isNull());
			dons.addAll((List<Don>) donRepo.findAll(predAll));
			if (isDungHan) {
				dons = dons.stream().filter(d -> {
					ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
					if (ttgqd.getNgayBatDauKTDX() != null && ttgqd.getNgayHetHanKTDX() != null) {
						return Utils.isValidNgayDungHanDangXuLy(ttgqd.getNgayHetHanKTDX());
					}
					return false;
				}).collect(Collectors.toList());
			} else {		
				dons = dons.stream().filter(d -> {
					ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
					if (ttgqd.getNgayBatDauKTDX() != null && ttgqd.getNgayHetHanKTDX() != null) {
						return Utils.isValidNgayTreHanDangXuLy(ttgqd.getNgayHetHanKTDX());
					}
					return false;
				}).collect(Collectors.toList());
			}
		} else {
			predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.ngayKetThucKTDX.isNotNull());
			dons.addAll((List<Don>) donRepo.findAll(predAll));
			if (isDungHan) {
				dons = dons.stream().filter(d -> {
					ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
					if (ttgqd.getNgayBatDauKTDX() != null && ttgqd.getNgayHetHanKTDX() != null && ttgqd.getNgayKetThucKTDX() != null) { 
						return Utils.isValidNgayDungHanDaXuLy(ttgqd.getNgayHetHanKTDX(), ttgqd.getNgayKetThucKTDX());
					}
					return false;
				}).collect(Collectors.toList());
			} else {
				dons = dons.stream().filter(d -> {
					ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
					if (ttgqd.getNgayBatDauKTDX() != null && ttgqd.getNgayHetHanKTDX() != null && ttgqd.getNgayKetThucKTDX() != null) { 
						return Utils.isValidNgayTreHanDaXuLy(ttgqd.getNgayHetHanKTDX(), ttgqd.getNgayKetThucKTDX());
					}
					return false;
				}).collect(Collectors.toList());
			}
		}
		predAll = predAll.and(QDon.don.in(dons));
		return predAll;
	}
	
	private boolean invalidNgayDungHanDangGQGQD(Don don) {
		ThongTinGiaiQuyetDon ttgqd = don.getThongTinGiaiQuyetDon();
		if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() != null) {
			return Utils.isValidNgayDungHanDangXuLy(ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet());
		} else if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() == null) {
			return Utils.isValidNgayDungHanDangXuLy(ttgqd.getNgayHetHanGiaiQuyet());
		} else {
			return false;
		}
	}
	
	private boolean invalidNgayTreHanDangGQGQD(Don don) {
		ThongTinGiaiQuyetDon ttgqd = don.getThongTinGiaiQuyetDon();
		if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() != null) {
			return Utils.isValidNgayTreHan(ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet());
		} else if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() == null) {
			return Utils.isValidNgayTreHan(ttgqd.getNgayHetHanGiaiQuyet());
		} else {
			return false;
		}
	}
	
	private boolean invalidNgayDungHanDaGQGQD(Don don) {
		ThongTinGiaiQuyetDon ttgqd = don.getThongTinGiaiQuyetDon();
		if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayKetThucGiaiQuyet() != null
				&& ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() != null) {
			return Utils.isValidNgayDungHanDaXuLy(ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
		} else if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayKetThucGiaiQuyet() != null 
				&& ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() == null) {
			return Utils.isValidNgayDungHanDaXuLy(ttgqd.getNgayHetHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
		} else {
			return false;
		}
	}
	
	private boolean invalidNgayTreHanDaGQGQD(Don don) {
		ThongTinGiaiQuyetDon ttgqd = don.getThongTinGiaiQuyetDon();
		if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayKetThucGiaiQuyet() != null
				&& ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() != null) {
			return Utils.isValidNgayTreHanDaXuLy(ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
		} else if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayKetThucGiaiQuyet() != null 
				&& ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() == null) {
			return Utils.isValidNgayTreHanDaXuLy(ttgqd.getNgayHetHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
		} else {
			return false;
		}
	}
	
	private boolean invalidNgayDungHanDaGQTTXM(Don don) {
		ThongTinGiaiQuyetDon ttgqd = don.getThongTinGiaiQuyetDon();
		if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null && ttgqd.getNgayKetThucTTXM() != null
				&& ttgqd.getNgayHetHanSauKhiGiaHanTTXM() != null) {
			return Utils.isValidNgayDungHanDaXuLy(ttgqd.getNgayHetHanSauKhiGiaHanTTXM(), ttgqd.getNgayKetThucTTXM());
		} else if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null && ttgqd.getNgayKetThucTTXM() != null 
				&& ttgqd.getNgayHetHanSauKhiGiaHanTTXM() == null) {
			return Utils.isValidNgayDungHanDaXuLy(ttgqd.getNgayHetHanTTXM(), ttgqd.getNgayKetThucTTXM());
		} else {
			return false;
		}
	}
	
	private boolean invalidNgayTreHanDaGQTTXM(Don don) {
		ThongTinGiaiQuyetDon ttgqd = don.getThongTinGiaiQuyetDon();
		if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null && ttgqd.getNgayKetThucTTXM() != null
				&& ttgqd.getNgayHetHanSauKhiGiaHanTTXM() != null) {
			return Utils.isValidNgayTreHanDaXuLy(ttgqd.getNgayHetHanSauKhiGiaHanTTXM(), ttgqd.getNgayKetThucTTXM());
		} else if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null && ttgqd.getNgayKetThucTTXM() != null 
				&& ttgqd.getNgayHetHanSauKhiGiaHanTTXM() == null) {
			return Utils.isValidNgayTreHanDaXuLy(ttgqd.getNgayHetHanTTXM(), ttgqd.getNgayKetThucTTXM());
		} else {
			return false;
		}
	}
	
	private boolean invalidNgayDungHanDangGQTTXM(Don don) {
		ThongTinGiaiQuyetDon ttgqd = don.getThongTinGiaiQuyetDon();
		if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null && ttgqd.getNgayHetHanSauKhiGiaHanTTXM() != null) {
			return Utils.isValidNgayDungHanDangXuLy(ttgqd.getNgayHetHanSauKhiGiaHanTTXM());
		} else if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null && ttgqd.getNgayHetHanSauKhiGiaHanTTXM() == null) {
			return Utils.isValidNgayDungHanDangXuLy(ttgqd.getNgayHetHanTTXM());
		} else {
			return false;
		}
	}
	
	private boolean invalidNgayTreHanDangGQTTXM(Don don) {
		ThongTinGiaiQuyetDon ttgqd = don.getThongTinGiaiQuyetDon();
		if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null && ttgqd.getNgayHetHanSauKhiGiaHanTTXM() != null) {
			return Utils.isValidNgayTreHanDangXuLy(ttgqd.getNgayHetHanSauKhiGiaHanTTXM());
		} else if (ttgqd.getNgayBatDauTTXM() != null && ttgqd.getNgayHetHanTTXM() != null && ttgqd.getNgayHetHanSauKhiGiaHanTTXM() == null) {
			return Utils.isValidNgayTreHanDangXuLy(ttgqd.getNgayHetHanTTXM());
		} else {
			return false;
		}
	}
}
