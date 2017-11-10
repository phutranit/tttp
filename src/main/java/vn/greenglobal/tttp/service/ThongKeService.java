package vn.greenglobal.tttp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.LinhVucDonThu;
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
public class ThongKeService {
	
	BooleanExpression base = QDon.don.daXoa.eq(false);
	BooleanExpression baseXLD = QXuLyDon.xuLyDon.daXoa.eq(false);
	BooleanExpression baseGQD = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false);
	
	public Long getTongSoDonTheoPhanLoai(BooleanExpression predAll, LoaiDonEnum loaiDon, DonRepository donRepo) { 
		Long tongPhanLoaiDon = 0L;
		predAll = predAll.and(QDon.don.loaiDon.eq(loaiDon));
		tongPhanLoaiDon = Long.valueOf(((List<Don>)donRepo.findAll(predAll)).size());		
		return tongPhanLoaiDon;
	}
	
	public Double getPhanTramTongSoDonTheoLinhVuc(BooleanExpression predAll, Long linhVucId, Long chiTietLinhVucChaId, Long chiTietLinhVucConId, Long tongSoDon, DonRepository donRepo) { 
		Double phanTramDouble = 0d;
		Long tongPhanLoaiDon = 0L;
		
		if ((linhVucId != null && linhVucId > 0) && (chiTietLinhVucChaId != null && chiTietLinhVucChaId > 0) &&
				(chiTietLinhVucConId != null && chiTietLinhVucConId > 0)) {
			return phanTramDouble;
		}
		
		if (linhVucId != null && linhVucId > 0) { 
			predAll = predAll.and(QDon.don.linhVucDonThu.id.eq(linhVucId))
					.and(QDon.don.linhVucDonThuChiTiet.isNull());
//					.and(QDon.don.chiTietLinhVucDonThuChiTiet.isNull());
		}
		
		if (chiTietLinhVucChaId != null && chiTietLinhVucChaId > 0) { 
			predAll = predAll.and(QDon.don.linhVucDonThuChiTiet.id.eq(chiTietLinhVucChaId))
					.and(QDon.don.linhVucDonThu.isNotNull());
//					.and(QDon.don.chiTietLinhVucDonThuChiTiet.isNull());
		}
		
		if (chiTietLinhVucConId != null && chiTietLinhVucConId > 0) { 
			predAll = predAll
//					.and(QDon.don.chiTietLinhVucDonThuChiTiet.id.eq(chiTietLinhVucConId))
					.and(QDon.don.linhVucDonThu.isNotNull());
//					.and(QDon.don.linhVucDonThuChiTiet.isNotNull());
		}
		
		tongPhanLoaiDon = Long.valueOf(((List<Don>)donRepo.findAll(predAll)).size());
		Double tongPhanTramLinhVucDon = (tongPhanLoaiDon.doubleValue() / tongSoDon.doubleValue()) * 100;
		if (tongPhanTramLinhVucDon > 0) { 
			phanTramDouble = Utils.round(tongPhanTramLinhVucDon, 2);
		}
		return phanTramDouble;
	}
	
	public int getTongSoDonTheoLinhVuc(BooleanExpression predAll, Long linhVucId, Long chiTietLinhVucChaId, Long chiTietLinhVucConId, DonRepository donRepo) {
		int tongSoDon = 0;
		
		if ((linhVucId != null && linhVucId > 0) && (chiTietLinhVucChaId != null && chiTietLinhVucChaId > 0) &&
				(chiTietLinhVucConId != null && chiTietLinhVucConId > 0)) {
			return tongSoDon;
		}
		
		if (linhVucId != null && linhVucId > 0) { 
			predAll = predAll.and(QDon.don.linhVucDonThu.id.eq(linhVucId))
					.and(QDon.don.linhVucDonThuChiTiet.isNull());
//					.and(QDon.don.chiTietLinhVucDonThuChiTiet.isNull());
		}
		
		if (chiTietLinhVucChaId != null && chiTietLinhVucChaId > 0) { 
			predAll = predAll.and(QDon.don.linhVucDonThuChiTiet.id.eq(chiTietLinhVucChaId))
					.and(QDon.don.linhVucDonThu.isNotNull());
//					.and(QDon.don.chiTietLinhVucDonThuChiTiet.isNull());
		}
		
		if (chiTietLinhVucConId != null && chiTietLinhVucConId > 0) { 
			predAll = predAll
//					.and(QDon.don.chiTietLinhVucDonThuChiTiet.id.eq(chiTietLinhVucConId));
					.and(QDon.don.linhVucDonThu.isNotNull());
//					.and(QDon.don.linhVucDonThuChiTiet.isNotNull());
		}
		
		tongSoDon = ((List<Don>) donRepo.findAll(predAll)).size();
		return tongSoDon;
	}
	
	public Double getPhanTramTongSoDonTheoPhanLoai(BooleanExpression predAll, LoaiDonEnum loaiDon, Long tongSoDon, DonRepository donRepo) { 
		Double phanTramDouble = 0d;
		Long tongPhanLoaiDon = 0L;
		predAll = predAll.and(QDon.don.loaiDon.eq(loaiDon));
		tongPhanLoaiDon = Long.valueOf(((List<Don>)donRepo.findAll(predAll)).size());
		Double tongPhanTramPhanLoaiDon = (tongPhanLoaiDon.doubleValue() / tongSoDon.doubleValue()) * 100;
		if (tongPhanTramPhanLoaiDon > 0) { 
			phanTramDouble = Utils.round(tongPhanTramPhanLoaiDon, 2);
		}
		return phanTramDouble;
	}
	
	public int getTongSoDonTheoLoaiDon(BooleanExpression predAll, LoaiDonEnum loaiDon, DonRepository donRepo) { 
		int tongPhanLoaiDon = 0;
		predAll = predAll.and(QDon.don.loaiDon.eq(loaiDon));
		tongPhanLoaiDon = ((List<Don>) donRepo.findAll(predAll)).size();
		return tongPhanLoaiDon;
	}
	
	public Predicate getPredDonTheoThang(BooleanExpression predAll, int month, DonRepository donRepo) { 
		BooleanExpression predThang = predAll;
		predThang = predThang.and(QDon.don.ngayTiepNhan.month().eq(month));
		return predThang;
	}
	
	public Predicate predicateFindDanhSachDonsTheoDonViTheoLinhVuc(Long donViXuLyXLD, int year, List<LinhVucDonThu> linhVucs, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.ngayTiepNhan.year().eq(year));
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false));
		List<Don> donCollections = new ArrayList<Don>();
		List<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		
		if (linhVucs != null && linhVucs.size() > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.don.linhVucDonThu.in(linhVucs)
					.or(QXuLyDon.xuLyDon.don.linhVucDonThuChiTiet.in(linhVucs)));
		}
		
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}
		
		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
		predAll = predAll.and(QDon.don.in(donCollections));
		
		return predAll;
	}
	
	public Predicate predicateFindDanhSachDonsTheoDonVi(Long donViXuLyXLD, int year, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.ngayTiepNhan.year().eq(year));
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
	
	public Long getThongKeTongSoDonThuocNhieuCoQuan(List<CoQuanQuanLy> coQuans, int year, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.ngayTiepNhan.year().eq(year));
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false));
		
		Long tongSo = 0L;
		List<Don> donCollections = new ArrayList<Don>();
		List<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		
		if (coQuans == null || coQuans.size() == 0) {
			return tongSo;
		}
		
		xuLyDonQuery = xuLyDonQuery
				.and(QXuLyDon.xuLyDon.donViXuLy.in(coQuans))
				.or(QXuLyDon.xuLyDon.phongBanXuLy.in(coQuans));
		
		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
		
		predAll = predAll.and(QDon.don.in(donCollections));
		
		tongSo = Long.valueOf(((List<Don>) donRepo.findAll(predAll)).size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDonChuaGiaiQuyetCuaNhieuCoQuan(List<CoQuanQuanLy> coQuans, int year, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.trangThaiDon.eq(TrangThaiDonEnum.DANG_GIAI_QUYET))
				.and(QDon.don.ngayTiepNhan.year().eq(year));
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.phongBanGiaiQuyet.in(coQuans)
						.or(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.in(coQuans)))
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.thanhLapDon.eq(true))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.daXoa.eq(false))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.trangThaiDon.eq(TrangThaiDonEnum.DANG_GIAI_QUYET));
		
		List<Don> donCollections = new ArrayList<Don>();
		List<GiaiQuyetDon> giaiQuyetDons = (List<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		donCollections = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
		predAll = predAll.and(QDon.don.in(donCollections));
		Long tongSo = 0L;
		tongSo = Long.valueOf(((List<Don>) donRepo.findAll(predAll)).size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDonTreHanCuaNhieuCoQuan(List<CoQuanQuanLy> coQuans, int year, XuLyDonRepository xuLyRepo, DonRepository donRepo, 
			GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false))
				.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.year().eq(year))
				.and(QXuLyDon.xuLyDon.don.thanhLapDon.eq(true))
				.and(QXuLyDon.xuLyDon.don.daXoa.eq(false));
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.ngayTiepNhan.year().eq(year))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.thanhLapDon.eq(true))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.daXoa.eq(false));
		Long tongSo = 0L;
		List<Don> donCollections = new ArrayList<Don>();
		List<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		
		if (coQuans == null || coQuans.size() == 0) {
			return tongSo;
		}
		
		xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.in(coQuans))
				.or(QXuLyDon.xuLyDon.phongBanXuLy.in(coQuans));
		giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.in(coQuans))
				.or(QGiaiQuyetDon.giaiQuyetDon.phongBanGiaiQuyet.in(coQuans));
	
		
		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
		donCollections = donCollections.stream().filter(d -> {
			if (d.getNgayBatDauXLD() == null || d.getThoiHanXuLyXLD() == null) {
				return false;
			} else {
				if (d.getNgayKetThucXLD() != null && 
						d.getNgayBatDauXLD() != null && d.getThoiHanXuLyXLD() != null) {
					return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(d.getThoiHanXuLyXLD(),
							d.getNgayKetThucXLD());
				}
			}
			return Utils.isValidNgayTreHan(d.getThoiHanXuLyXLD());
		}).collect(Collectors.toList());
		
		List<Don> donCollections2 = new ArrayList<Don>();
		List<GiaiQuyetDon> giaiQuyetDons = (List<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		donCollections2 = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
		donCollections2 = donCollections2.stream().filter(d -> {
			if (d.getThongTinGiaiQuyetDon() == null) {
				return false;
			}
			return invalidNgayTreHanVaTinhTheoNgayKetThuc(d);
		}).collect(Collectors.toList());
		
		List<Don> listtDons = new ArrayList<Don>();
		listtDons.addAll(donCollections);
		listtDons.addAll(donCollections2);
		
		tongSo = Long.valueOf(listtDons.size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDonTreHanCuaNhieuCoQuanGQD(List<CoQuanQuanLy> coQuans, int year, DonRepository donRepo, 
			GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.ngayTiepNhan.year().eq(year))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.thanhLapDon.eq(true))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.trangThaiDon.eq(TrangThaiDonEnum.DANG_GIAI_QUYET))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.daXoa.eq(false));
		Long tongSo = 0L;
		if (coQuans == null || coQuans.size() == 0) {
			return tongSo;
		}
		giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.in(coQuans))
				.or(QGiaiQuyetDon.giaiQuyetDon.phongBanGiaiQuyet.in(coQuans));
		
		List<Don> donCollections2 = new ArrayList<Don>();
		List<GiaiQuyetDon> giaiQuyetDons = (List<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		donCollections2 = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
		donCollections2 = donCollections2.stream().filter(d -> {
			if (d.getThongTinGiaiQuyetDon() == null) {
				return false;
			}
			return invalidNgayTreHan(d);
		}).collect(Collectors.toList());
		
		List<Don> listtDons = new ArrayList<Don>();
		listtDons.addAll(donCollections2);
		
		tongSo = Long.valueOf(listtDons.size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDonTreHanCuaNhieuCoQuanXLD(List<CoQuanQuanLy> coQuans, int year, XuLyDonRepository xuLyRepo, DonRepository donRepo) {
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false))
				.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.year().eq(year))
				.and(QXuLyDon.xuLyDon.don.thanhLapDon.eq(true))
				.and(QXuLyDon.xuLyDon.don.trangThaiDon.eq(TrangThaiDonEnum.DANG_XU_LY))
				.and(QXuLyDon.xuLyDon.don.daXoa.eq(false));
		Long tongSo = 0L;
		List<Don> donCollections = new ArrayList<Don>();
		List<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		
		if (coQuans == null || coQuans.size() == 0) {
			return tongSo;
		}
		
		xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.in(coQuans))
				.or(QXuLyDon.xuLyDon.phongBanXuLy.in(coQuans));
		
		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
		donCollections = donCollections.stream().filter(d -> {
			if (d.getNgayBatDauXLD() == null || d.getThoiHanXuLyXLD() == null) {
				return false;
			}
			return Utils.isValidNgayTreHan(d.getThoiHanXuLyXLD());
		}).collect(Collectors.toList());
		
		List<Don> listtDons = new ArrayList<Don>();
		listtDons.addAll(donCollections);
		
		tongSo = Long.valueOf(listtDons.size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDonTheoNam(Long donViXuLyXLD, int year, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.ngayTiepNhan.year().eq(year));
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false));
		
		Long tongSo = 0L;
		List<Don> donCollections = new ArrayList<Don>();
		List<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}

		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
		predAll = predAll.and(QDon.don.in(donCollections));
		
		tongSo = Long.valueOf(((List<Don>) donRepo.findAll(predAll)).size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDonTheoThang(Long donViXuLyXLD, int month, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.ngayTiepNhan.month().eq(month));
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false));
		
		Long tongSo = 0L;
		List<Don> donCollections = new ArrayList<Don>();
		List<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}

		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
		predAll = predAll.and(QDon.don.in(donCollections));
		
		tongSo = Long.valueOf(((List<Don>) donRepo.findAll(predAll)).size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDonChuaGiaiQuyet(Long donViXuLyXLD, int year, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.trangThaiDon.eq(TrangThaiDonEnum.DANG_GIAI_QUYET))
				.and(QDon.don.ngayTiepNhan.year().eq(year));
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.id.eq(donViXuLyXLD))
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.thanhLapDon.eq(true))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.daXoa.eq(false))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.trangThaiDon.eq(TrangThaiDonEnum.DANG_GIAI_QUYET));
		
		List<Don> donCollections = new ArrayList<Don>();
		List<GiaiQuyetDon> giaiQuyetDons = (List<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		donCollections = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
		predAll = predAll.and(QDon.don.in(donCollections));
		
		Long tongSo = 0L;
		tongSo = Long.valueOf(((List<Don>) donRepo.findAll(predAll)).size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDonTreHanXLD(Long donViXuLyXLD, int year, XuLyDonRepository xuLyRepo, DonRepository donRepo) {
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false))
				.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.year().eq(year))
				.and(QXuLyDon.xuLyDon.don.thanhLapDon.eq(true))
				.and(QXuLyDon.xuLyDon.don.trangThaiDon.eq(TrangThaiDonEnum.DANG_XU_LY))
				.and(QXuLyDon.xuLyDon.don.daXoa.eq(false));
		
		List<Don> donCollections = new ArrayList<Don>();
		List<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}
		
		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
		donCollections = donCollections.stream().filter(d -> {
			if (d.getNgayBatDauXLD() == null || d.getThoiHanXuLyXLD() == null) {
				return false;
			}
			return invalidNgayTreHanXLD(d);
		}).collect(Collectors.toList());
		
		
		List<Don> listtDons = new ArrayList<Don>();
		listtDons.addAll(donCollections);
		
		Long tongSo = 0L;
		tongSo = Long.valueOf(listtDons.size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDonTreHanGQD(Long donViXuLyXLD, int year, DonRepository donRepo, 
			GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.ngayTiepNhan.year().eq(year))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.thanhLapDon.eq(true))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.trangThaiDon.eq(TrangThaiDonEnum.DANG_GIAI_QUYET))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.daXoa.eq(false));
		
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.id.eq(donViXuLyXLD));
		}
	
		List<Don> donCollections2 = new ArrayList<Don>();
		List<GiaiQuyetDon> giaiQuyetDons = (List<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		donCollections2 = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
		donCollections2 = donCollections2.stream().filter(d -> {
			if (d.getThongTinGiaiQuyetDon() == null) {
				return false;
			}
			return invalidNgayTreHanVaTinhTheoNgayKetThuc(d);
		}).collect(Collectors.toList());
		
		List<Don> listtDons = new ArrayList<Don>();
		listtDons.addAll(donCollections2);
		
		Long tongSo = 0L;
		tongSo = Long.valueOf(listtDons.size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDonTreHanTheoThang(Long donViXuLyXLD, int month, XuLyDonRepository xuLyRepo, DonRepository donRepo, 
			GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false))
				.and(QXuLyDon.xuLyDon.don.ngayTiepNhan.month().eq(month))
				.and(QXuLyDon.xuLyDon.don.thanhLapDon.eq(true))
				.and(QXuLyDon.xuLyDon.don.daXoa.eq(false));
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.ngayTiepNhan.month().eq(month))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.thanhLapDon.eq(true))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.daXoa.eq(false));
		
		List<Don> donCollections = new ArrayList<Don>();
		List<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
			giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.id.eq(donViXuLyXLD));
		}
		
		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
		donCollections = donCollections.stream().filter(d -> {
			if (d.getNgayBatDauXLD() == null || d.getThoiHanXuLyXLD() == null) {
				return false;
			} else {
				if (d.getNgayKetThucXLD() != null && 
						d.getNgayBatDauXLD() != null && d.getThoiHanXuLyXLD() != null) {
					return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(d.getThoiHanXuLyXLD(),
							d.getNgayKetThucXLD());
				}
			}
			return Utils.isValidNgayTreHan(d.getThoiHanXuLyXLD());
		}).collect(Collectors.toList());
		
		List<Don> donCollections2 = new ArrayList<Don>();
		List<GiaiQuyetDon> giaiQuyetDons = (List<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		donCollections2 = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
		donCollections2 = donCollections2.stream().filter(d -> {
			if (d.getThongTinGiaiQuyetDon() == null) {
				return false;
			}
			return invalidNgayTreHanVaTinhTheoNgayKetThuc(d);
		}).collect(Collectors.toList());
		
		List<Don> listtDons = new ArrayList<Don>();
		listtDons.addAll(donCollections);
		listtDons.addAll(donCollections2);
		
		Long tongSo = 0L;
		tongSo = Long.valueOf(listtDons.size());
		return tongSo;
	}

	public Predicate predicateFindDSDonMoiNhat(Long donViXuLyXLD, int month, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.ngayTiepNhan.month().eq(month));
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false));
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false));
		
		List<Don> donCollections = new ArrayList<Don>();
		List<XuLyDon> xldCollections = new ArrayList<XuLyDon>();

		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
			giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.id.eq(donViXuLyXLD));
		}

		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
		
		List<Don> donCollections2 = new ArrayList<Don>();
		Collection<GiaiQuyetDon> giaiQuyetDons = (Collection<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		donCollections2 = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
		
		predAll = predAll.and(QDon.don.in(donCollections).or(QDon.don.in(donCollections2)));
		return predAll;
	}
	
	public Predicate predicateFindDSDonTreHanGQ(Long donViXuLyXLD, XuLyDonRepository xuLyRepo, DonRepository donRepo, 
			GiaiQuyetDonRepository giaiQuyetDonRepo) { 
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.trangThaiDon.eq(TrangThaiDonEnum.DANG_GIAI_QUYET));
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false));
		
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.id.eq(donViXuLyXLD));
		}
		
		List<Don> donCollections2 = new ArrayList<Don>();
		Collection<GiaiQuyetDon> giaiQuyetDons = (Collection<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		donCollections2 = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
		donCollections2 = donCollections2.stream().filter(d -> {
			if (d.getThongTinGiaiQuyetDon() == null) {
				return false;
			}
			return invalidNgayTreHan(d);
		}).collect(Collectors.toList());
		
		predAll = predAll.and(QDon.don.in(donCollections2));
		return predAll;
	}
	
	public Predicate predicateFindTongSoLuongDonCuaNamTheoDonVi(Long donViXuLyXLGQ, int year, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base;
		BooleanExpression xuLyDonQuery = baseXLD.and(QXuLyDon.xuLyDon.old.eq(false));
		BooleanExpression giaiQuyetDonQuery = baseGQD.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false));

		Set<Don> dons = new HashSet<Don>();
		List<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		List<GiaiQuyetDon> gqdCollections = new ArrayList<GiaiQuyetDon>();

		if (donViXuLyXLGQ != null && donViXuLyXLGQ > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLGQ));
			giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.id.eq(donViXuLyXLGQ));
		}

		xldCollections.addAll((List<XuLyDon>) xuLyRepo.findAll(xuLyDonQuery));
		gqdCollections.addAll((List<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery));

		dons.addAll(xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));
		dons.addAll(gqdCollections.stream().map(d -> {
			ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
			if (ttgqd != null) {
				return ttgqd.getDon();
			}
			return null;
		}).distinct().collect(Collectors.toList()));

		predAll = predAll.and(QDon.don.in(dons));
		predAll = predAll.and(QDon.don.ngayTiepNhan.year().eq(year));

		return predAll;
	}
	
	public Long getThongKeTongSoDonTreHanXLDTheoThang(BooleanExpression predAll, DonRepository donRepo) {
		predAll = predAll.and(QDon.don.thanhLapDon.isTrue()
				.or(QDon.don.ngayBatDauXLD.isNotNull()));
		List<Don> donCollections = new ArrayList<Don>();
		
		donCollections.addAll((List<Don>) donRepo.findAll(predAll));
		donCollections = donCollections.stream().filter(d -> {
			if (d.getNgayBatDauXLD() == null || d.getThoiHanXuLyXLD() == null) {
				return false;
			} else {
				if (d.getNgayKetThucXLD() != null && 
						d.getNgayBatDauXLD() != null && d.getThoiHanXuLyXLD() != null) {
					return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(d.getThoiHanXuLyXLD(),
							d.getNgayKetThucXLD());
				}
			}
			return Utils.isValidNgayTreHan(d.getThoiHanXuLyXLD());
		}).collect(Collectors.toList());

		Long tongSo = 0L;
		tongSo = Long.valueOf(donCollections.size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDonTreHanGQDTheoThang(BooleanExpression predAll, DonRepository donRepo) {
		predAll = predAll.and(QDon.don.thongTinGiaiQuyetDon.giaiQuyetDons.isNotEmpty());
		List<Don> donCollections = new ArrayList<Don>();
		
		donCollections.addAll((List<Don>) donRepo.findAll(predAll));
		donCollections = donCollections.stream().filter(d -> {
			return invalidNgayTreHanVaTinhTheoNgayKetThuc(d);
		}).collect(Collectors.toList());
		
		Long tongSo = 0L;
		tongSo = Long.valueOf(donCollections.size());
		return tongSo;
	}
	
	private boolean invalidNgayTreHan(Don don) {
		ThongTinGiaiQuyetDon ttgqd = don.getThongTinGiaiQuyetDon();
		if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() != null) {
			return Utils.isValidNgayTreHan(ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet());
		} else if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() == null) {
			return Utils.isValidNgayTreHan(ttgqd.getNgayHetHanGiaiQuyet());
		} else {
			return false;
		}
	}
	
	private boolean invalidNgayTreHanVaTinhTheoNgayKetThuc(Don don) {
		ThongTinGiaiQuyetDon ttgqd = don.getThongTinGiaiQuyetDon();
		
		if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() != null) {
			if (ttgqd.getNgayKetThucGiaiQuyet() != null) {
				return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
			} else {
				return Utils.isValidNgayTreHan(ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet());
			}
		} else if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() == null) {
			if (ttgqd.getNgayKetThucGiaiQuyet() != null) {
				return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(ttgqd.getNgayHetHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
			} else {
				return Utils.isValidNgayTreHan(ttgqd.getNgayHetHanGiaiQuyet());
			}
		}
		return false;
	}
	
	private boolean invalidNgayTreHanXLD(Don don) {
		if (don.getNgayBatDauXLD() != null && don.getThoiHanXuLyXLD() != null && don.getNgayKetThucXLD() != null) {
			return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(don.getThoiHanXuLyXLD(), don.getNgayKetThucXLD());
		} else if (don.getNgayBatDauXLD() != null && don.getThoiHanXuLyXLD() != null && don.getNgayKetThucXLD() == null) {
			return Utils.isValidNgayTreHan(don.getThoiHanXuLyXLD());
		}
		return false;
	}
}
