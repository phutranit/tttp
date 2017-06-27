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
	
	public Long getTongSoDonTheoPhanLoai(BooleanExpression predAll, LoaiDonEnum loaiDon, DonRepository donRepo) { 
		Long tongPhanLoaiDon = 0L;
		predAll = predAll.and(QDon.don.loaiDon.eq(loaiDon));
		tongPhanLoaiDon = Long.valueOf(((List<Don>)donRepo.findAll(predAll)).size());		
		return tongPhanLoaiDon;
	}
	
	public int getPhanTramTongSoDonTheoLinhVuc(BooleanExpression predAll, Long linhVucId, Long tongSoDon, DonRepository donRepo) { 
		Float tongSoDonFloat = tongSoDon.floatValue();
		Long tongPhanLoaiDon = 0L;
		predAll = predAll.and(QDon.don.linhVucDonThu.id.eq(linhVucId));
		tongPhanLoaiDon = Long.valueOf(((List<Don>)donRepo.findAll(predAll)).size());
		
		Float tongPhanLoaiDonFloat = tongPhanLoaiDon.floatValue();
		int tongPhanTramLinhVucDon = Math.round(((Float) (tongPhanLoaiDonFloat / tongSoDonFloat) * 100));
		return tongPhanTramLinhVucDon;
	}
	
	public int getPhanTramTongSoDonTheoPhanLoai(BooleanExpression predAll, LoaiDonEnum loaiDon, Long tongSoDon, DonRepository donRepo) { 
		Float tongSoDonFloat = tongSoDon.floatValue();
		Long tongPhanLoaiDon = 0L;
		predAll = predAll.and(QDon.don.loaiDon.eq(loaiDon));
		tongPhanLoaiDon = Long.valueOf(((List<Don>)donRepo.findAll(predAll)).size());
		
		Float tongPhanLoaiDonFloat = tongPhanLoaiDon.floatValue();
		int tongPhanTramPhanLoaiDon = Math.round(((Float) (tongPhanLoaiDonFloat / tongSoDonFloat) * 100));
		return tongPhanTramPhanLoaiDon;
	}
	
	public Predicate predicateFindDanhSachDonsTheoDonVi(Long donViXuLyXLD, int year, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true)).and(QDon.don.ngayTao.year().eq(year));
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false).and(QXuLyDon.xuLyDon.old.eq(false));
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
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true)).and(QDon.don.ngayTao.year().eq(year));
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false).and(QXuLyDon.xuLyDon.old.eq(false));
		
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
				.and(QDon.don.ngayTao.year().eq(year));
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
				.and(QXuLyDon.xuLyDon.don.ngayTao.year().eq(year))
				.and(QXuLyDon.xuLyDon.don.thanhLapDon.eq(true))
				.and(QXuLyDon.xuLyDon.don.daXoa.eq(false));
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.ngayTao.year().eq(year))
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
					return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(d.getId(), d.getNgayBatDauXLD(), d.getThoiHanXuLyXLD(),
							d.getNgayKetThucXLD());
				}
			}
			return Utils.isValidNgayTreHan(d.getNgayBatDauXLD(), d.getThoiHanXuLyXLD());
		}).collect(Collectors.toList());
		
		List<Don> donCollections2 = new ArrayList<Don>();
		List<GiaiQuyetDon> giaiQuyetDons = (List<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		donCollections2 = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
		donCollections2 = donCollections2.stream().filter(d -> {
			if (d.getThongTinGiaiQuyetDon() == null) {
				return false;
			}
			ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
			if (ttgqd.getNgayBatDauGiaiQuyet() == null || ttgqd.getNgayHetHanGiaiQuyet() == null) {
				return false;
			} else {
				if (ttgqd.getNgayKetThucGiaiQuyet() != null && ttgqd.getNgayBatDauGiaiQuyet() != null
						&& ttgqd.getNgayHetHanGiaiQuyet() != null) {
					return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(d.getId(), ttgqd.getNgayBatDauGiaiQuyet(),
							ttgqd.getNgayHetHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
				}
			}
			return Utils.isValidNgayTreHan(ttgqd.getNgayBatDauGiaiQuyet(), ttgqd.getNgayHetHanGiaiQuyet());
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
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.ngayTao.year().eq(year))
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
			ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
			if (ttgqd.getNgayBatDauGiaiQuyet() == null || ttgqd.getNgayHetHanGiaiQuyet() == null) {
				return false;
			}
			return Utils.isValidNgayTreHan(ttgqd.getNgayBatDauGiaiQuyet(), ttgqd.getNgayHetHanGiaiQuyet());
		}).collect(Collectors.toList());
		
		List<Don> listtDons = new ArrayList<Don>();
		listtDons.addAll(donCollections2);
		
		tongSo = Long.valueOf(listtDons.size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDonTreHanCuaNhieuCoQuanXLD(List<CoQuanQuanLy> coQuans, int year, XuLyDonRepository xuLyRepo, DonRepository donRepo) {
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false))
				.and(QXuLyDon.xuLyDon.don.ngayTao.year().eq(year))
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
			return Utils.isValidNgayTreHan(d.getNgayBatDauXLD(), d.getThoiHanXuLyXLD());
		}).collect(Collectors.toList());
		
		List<Don> listtDons = new ArrayList<Don>();
		listtDons.addAll(donCollections);
		
		tongSo = Long.valueOf(listtDons.size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDon(Long donViXuLyXLD, int year, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.ngayTao.year().eq(year));
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
				.and(QDon.don.ngayTao.year().eq(year));
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
				.and(QXuLyDon.xuLyDon.don.ngayTao.year().eq(year))
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
			return Utils.isValidNgayTreHan(d.getNgayBatDauXLD(), d.getThoiHanXuLyXLD());
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
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.ngayTao.year().eq(year))
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
			ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
			if (ttgqd.getNgayBatDauGiaiQuyet() == null || ttgqd.getNgayHetHanGiaiQuyet() == null) {
				return false;
			}
			return Utils.isValidNgayTreHan(ttgqd.getNgayBatDauGiaiQuyet(), ttgqd.getNgayHetHanGiaiQuyet());
		}).collect(Collectors.toList());
		
		List<Don> listtDons = new ArrayList<Don>();
		listtDons.addAll(donCollections2);
		
		Long tongSo = 0L;
		tongSo = Long.valueOf(listtDons.size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDonTreHan(Long donViXuLyXLD, int year, XuLyDonRepository xuLyRepo, DonRepository donRepo, 
			GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false))
				.and(QXuLyDon.xuLyDon.don.ngayTao.year().eq(year))
				.and(QXuLyDon.xuLyDon.don.thanhLapDon.eq(true))
				.and(QXuLyDon.xuLyDon.don.daXoa.eq(false));
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false))
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.ngayTao.year().eq(year))
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
					return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(d.getId(), d.getNgayBatDauXLD(), d.getThoiHanXuLyXLD(),
							d.getNgayKetThucXLD());
				}
			}
			return Utils.isValidNgayTreHan(d.getNgayBatDauXLD(), d.getThoiHanXuLyXLD());
		}).collect(Collectors.toList());
		
		List<Don> donCollections2 = new ArrayList<Don>();
		List<GiaiQuyetDon> giaiQuyetDons = (List<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		donCollections2 = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
		donCollections2 = donCollections2.stream().filter(d -> {
			if (d.getThongTinGiaiQuyetDon() == null) {
				return false;
			}
			ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
			if (ttgqd.getNgayBatDauGiaiQuyet() == null || ttgqd.getNgayHetHanGiaiQuyet() == null) {
				return false;
			} else {
				if (ttgqd.getNgayKetThucGiaiQuyet() != null && ttgqd.getNgayBatDauGiaiQuyet() != null
						&& ttgqd.getNgayHetHanGiaiQuyet() != null) {
					return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(d.getId(), ttgqd.getNgayBatDauGiaiQuyet(),
							ttgqd.getNgayHetHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
				}
			}
			return Utils.isValidNgayTreHan(ttgqd.getNgayBatDauGiaiQuyet(), ttgqd.getNgayHetHanGiaiQuyet());
		}).collect(Collectors.toList());
		
		List<Don> listtDons = new ArrayList<Don>();
		listtDons.addAll(donCollections);
		listtDons.addAll(donCollections2);
		
		Long tongSo = 0L;
		tongSo = Long.valueOf(listtDons.size());
		return tongSo;
	}
	
	public Predicate predicateFindDSDonMoiNhat(Long donViXuLyXLD, String chucVu, int year, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.ngayTao.year().eq(year));
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false).and(QXuLyDon.xuLyDon.old.eq(false));
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
	
	public Predicate predicateFindDSDonTreHan(Long donViXuLyXLD, String chucVu, int month,
			XuLyDonRepository xuLyRepo, DonRepository donRepo, 
			GiaiQuyetDonRepository giaiQuyetDonRepo) { 
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.trangThaiDon.eq(TrangThaiDonEnum.DANG_XU_LY)
						.or(QDon.don.trangThaiDon.eq(TrangThaiDonEnum.DANG_GIAI_QUYET)))
				.and(QDon.don.ngayTao.month().eq(month));
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false))
				.or(QXuLyDon.xuLyDon.donChuyen.eq(true));
				
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
		donCollections = donCollections.stream().filter(d -> {
			if (d.getNgayBatDauXLD() == null || d.getThoiHanXuLyXLD() == null) {
				return false;
			}
			return Utils.isValidNgayTreHan(d.getNgayBatDauXLD(), d.getThoiHanXuLyXLD());
		}).collect(Collectors.toList());
		
		List<Don> donCollections2 = new ArrayList<Don>();
		Collection<GiaiQuyetDon> giaiQuyetDons = (Collection<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		donCollections2 = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
		donCollections2 = donCollections2.stream().filter(d -> {
			if (d.getThongTinGiaiQuyetDon() == null) {
				return false;
			}
			ThongTinGiaiQuyetDon ttgqd = d.getThongTinGiaiQuyetDon();
			if (ttgqd.getNgayBatDauGiaiQuyet() == null || ttgqd.getNgayHetHanGiaiQuyet() == null) {
				return false;
			}
			return Utils.isValidNgayTreHan(ttgqd.getNgayBatDauGiaiQuyet(), ttgqd.getNgayHetHanGiaiQuyet());
		}).collect(Collectors.toList());
		
		Set<Don> setDons = new HashSet<Don>();
		setDons.addAll(donCollections2);
		setDons.addAll(donCollections);
		predAll = predAll.and(QDon.don.in(setDons));
		return predAll;
	}
}
