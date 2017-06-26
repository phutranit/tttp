package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.NguonTiepNhanDonEnum;
import vn.greenglobal.tttp.enums.PhanLoaiDonCongDanEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.QuyTrinhXuLyDonEnum;
import vn.greenglobal.tttp.enums.TinhTrangGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.TrangThaiXuLyDonEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.PropertyChangeObject;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QGiaiQuyetDon;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.ThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.model.VaiTro;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.LinhVucDonThuRepository;
import vn.greenglobal.tttp.repository.ThamQuyenGiaiQuyetRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class DonService {

	@Autowired
	private LinhVucDonThuRepository lichVuDonThuRepo;

	@Autowired
	private ThamQuyenGiaiQuyetRepository thamQuyenGiaiQuyetRepo;

	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepository;

	@Autowired
	private DonRepository donRepo;

	BooleanExpression base = QDon.don.daXoa.eq(false);

	public Predicate predicateFindByCongDan(Long id) {
		BooleanExpression predAll = base.and(QDon.don.daXoa.eq(false));
		if (id > 0) {
			predAll = predAll.and(QDon.don.donCongDans.any().congDan.id.eq(id));
		}

		return predAll;
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
	
	public Long getThongKeTongSoDonThuocNhieuCoQuan(List<CoQuanQuanLy> coQuans, int year, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true)).and(QDon.don.ngayTao.year().eq(year));
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false).and(QXuLyDon.xuLyDon.old.eq(false));
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false));
		Long tongSo = 0L;
		List<Don> donCollections = new ArrayList<Don>();
		List<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		
		if (coQuans == null || coQuans.size() == 0) {
			return tongSo;
		}
		
		xuLyDonQuery = xuLyDonQuery
				.and(QXuLyDon.xuLyDon.donViXuLy.in(coQuans))
				.or(QXuLyDon.xuLyDon.phongBanXuLy.in(coQuans));
		giaiQuyetDonQuery = giaiQuyetDonQuery
				.and(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.in(coQuans))
				.or(QGiaiQuyetDon.giaiQuyetDon.phongBanGiaiQuyet.in(coQuans));
				
		
		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
		
		List<Don> donCollections2 = new ArrayList<Don>();
		Collection<GiaiQuyetDon> giaiQuyetDons = (Collection<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
		donCollections2 = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
		
		predAll = predAll.and(QDon.don.in(donCollections).or(QDon.don.in(donCollections2)));
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
			return Utils.isValidNgayTreHan(d.getId(), d.getNgayBatDauXLD(), d.getThoiHanXuLyXLD());
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
			return Utils.isValidNgayTreHan(d.getId(), ttgqd.getNgayBatDauGiaiQuyet(), ttgqd.getNgayHetHanGiaiQuyet());
		}).collect(Collectors.toList());
		
		List<Don> listtDons = new ArrayList<Don>();
		listtDons.addAll(donCollections);
		listtDons.addAll(donCollections2);
		
		tongSo = Long.valueOf(listtDons.size());
		return tongSo;
	}
	
	public Long getThongKeTongSoDon(Long donViXuLyXLD, int year, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.ngayTao.year().eq(year));
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false).and(QXuLyDon.xuLyDon.old.eq(false));
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false));
		Long tongSo = 0L;
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
			return Utils.isValidNgayTreHan(d.getId(), d.getNgayBatDauXLD(), d.getThoiHanXuLyXLD());
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
			return Utils.isValidNgayTreHan(d.getId(), ttgqd.getNgayBatDauGiaiQuyet(), ttgqd.getNgayHetHanGiaiQuyet());
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
	
	public Predicate predicateFindDSDonTreHan(Long donViXuLyXLD, String chucVu, int year,
			XuLyDonRepository xuLyRepo, DonRepository donRepo, 
			GiaiQuyetDonRepository giaiQuyetDonRepo) { 
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true))
				.and(QDon.don.ngayTao.year().eq(year));
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
			return Utils.isValidNgayTreHan(d.getId(), d.getNgayBatDauXLD(), d.getThoiHanXuLyXLD());
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
			} else {
				if (ttgqd.getNgayKetThucGiaiQuyet() != null && ttgqd.getNgayBatDauGiaiQuyet() != null
						&& ttgqd.getNgayHetHanGiaiQuyet() != null) {
					return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(d.getId(), ttgqd.getNgayBatDauGiaiQuyet(),
							ttgqd.getNgayHetHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
				}
			}
			return Utils.isValidNgayTreHan(d.getId(), ttgqd.getNgayBatDauGiaiQuyet(), ttgqd.getNgayHetHanGiaiQuyet());
		}).collect(Collectors.toList());
		
		Set<Don> setDons = new HashSet<Don>();
		setDons.addAll(donCollections2);
		setDons.addAll(donCollections);
		predAll = predAll.and(QDon.don.in(setDons));
		return predAll;
	}

	public Predicate predicateFindAll(String maDon, String tuKhoa, String nguonDon, String phanLoaiDon,
			String tiepNhanTuNgay, String tiepNhanDenNgay, String hanGiaiQuyetTuNgay, String hanGiaiQuyetDenNgay,
			String tinhTrangXuLy, boolean thanhLapDon, String trangThaiDon, Long phongBanGiaiQuyetXLD,
			Long canBoXuLyXLD, Long phongBanXuLyXLD, Long coQuanTiepNhanXLD, Long donViXuLyXLD, String chucVu,
			Set<VaiTro> vaitros, String hoTen, XuLyDonRepository xuLyRepo, DonRepository donRepo,
			GiaiQuyetDonRepository giaiQuyetDonRepo) {

		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(thanhLapDon));
		predAll = predAll.and(QDon.don.xuLyDons.isNotEmpty()
				.or(QDon.don.processType.eq(ProcessTypeEnum.KIEM_TRA_DE_XUAT).and(QDon.don.xuLyDons.isEmpty())));

		// Query don
		if (maDon != null && StringUtils.isNotBlank(maDon.trim())) {
			predAll = predAll.and(QDon.don.ma.eq(maDon.trim()));
		}

		if (hoTen != null && StringUtils.isNotBlank(hoTen.trim())) {
			predAll = predAll
					.and(QDon.don.donCongDans.any().congDan.hoVaTen.containsIgnoreCase(hoTen.trim())
							.or(QDon.don.donCongDans.any().tenCoQuan.containsIgnoreCase(hoTen.trim())))
					.and(QDon.don.donCongDans.any().phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON));
		}

		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll
					.and(QDon.don.donCongDans.any().congDan.hoVaTen.containsIgnoreCase(tuKhoa.trim())
							.or(QDon.don.donCongDans.any().tenCoQuan.containsIgnoreCase(tuKhoa.trim()))
							.or(QDon.don.donCongDans.any().soCMNDHoChieu.containsIgnoreCase(tuKhoa.trim())))
					.and(QDon.don.donCongDans.any().phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON));
		}

		if (nguonDon != null && StringUtils.isNotBlank(nguonDon.trim())) {
			predAll = predAll
					.and(QDon.don.nguonTiepNhanDon.eq(NguonTiepNhanDonEnum.valueOf(StringUtils.upperCase(nguonDon))));
		}

		if (phanLoaiDon != null && StringUtils.isNotBlank(phanLoaiDon.trim())) {
			predAll = predAll.and(QDon.don.loaiDon.eq(LoaiDonEnum.valueOf(StringUtils.upperCase(phanLoaiDon))));
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
		List<Don> donCollections = new ArrayList<Don>();
		// Query xu ly don
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false).and(QXuLyDon.xuLyDon.old.eq(false));

		if (phongBanGiaiQuyetXLD != null) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanGiaiQuyet.id.eq(phongBanGiaiQuyetXLD));
		}

		/*
		 * if (canBoXuLyXLD != null && StringUtils.isNotBlank(chucVu) &&
		 * chucVu.equals(VaiTroEnum.CHUYEN_VIEN.name())) { xuLyDonQuery =
		 * xuLyDonQuery.and(QXuLyDon.xuLyDon.congChuc.id.eq(canBoXuLyXLD)); }
		 */

		if (phongBanXuLyXLD != null && phongBanXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanXuLy.id.eq(phongBanXuLyXLD));
		}

		if (vaitros.size() > 0) {
			List<VaiTroEnum> listVaiTro = vaitros.stream().map(d -> d.getLoaiVaiTro()).distinct()
					.collect(Collectors.toList());
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.chucVu.in(listVaiTro));
		} else {
			if (StringUtils.isNotBlank(chucVu)) {
				xuLyDonQuery = xuLyDonQuery
						.and(QXuLyDon.xuLyDon.chucVu.eq(VaiTroEnum.valueOf(StringUtils.upperCase(chucVu))));
			}
		}

		if (coQuanTiepNhanXLD != null) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.coQuanTiepNhan.id.eq(coQuanTiepNhanXLD));
		}

		if (StringUtils.isNotBlank(tinhTrangXuLy)) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.huongXuLy.isNotNull())
					.and(QXuLyDon.xuLyDon.huongXuLy.eq(HuongXuLyXLDEnum.valueOf(StringUtils.upperCase(tinhTrangXuLy))));
		}

		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}

		if (StringUtils.isNotBlank(chucVu) && ("CHUYEN_VIEN".equals(chucVu))) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.canBoXuLyChiDinh.id.eq(canBoXuLyXLD));
		}

		if (StringUtils.isNotBlank(trangThaiDon)) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.trangThaiDon.stringValue().eq(trangThaiDon));
		}

		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();
		Collection<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery, sortOrder);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
		if (StringUtils.isNotBlank(chucVu) && ("VAN_THU".equals(chucVu) || "LANH_DAO".equals(chucVu))) {
			// Query don TTXM
			BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
					.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false));
			if (StringUtils.isNotBlank(trangThaiDon)) {
				TrangThaiXuLyDonEnum trangThaiXuLyDon = TrangThaiXuLyDonEnum
						.valueOf(StringUtils.upperCase(trangThaiDon));
				TinhTrangGiaiQuyetEnum tinhTrangGiaiQuyet = TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET;
				if (TrangThaiXuLyDonEnum.DANG_XU_LY.equals(trangThaiXuLyDon)) {
					tinhTrangGiaiQuyet = TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET;
				}
				giaiQuyetDonQuery = giaiQuyetDonQuery
						.and(QGiaiQuyetDon.giaiQuyetDon.tinhTrangGiaiQuyet.eq(tinhTrangGiaiQuyet));
			}

			if (StringUtils.isNotBlank(chucVu)) {
				giaiQuyetDonQuery = giaiQuyetDonQuery
						.and(QGiaiQuyetDon.giaiQuyetDon.chucVu.eq(VaiTroEnum.valueOf(StringUtils.upperCase(chucVu))));
			}

			if (donViXuLyXLD != null && donViXuLyXLD > 0) {
				giaiQuyetDonQuery = giaiQuyetDonQuery
						.and(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.id.eq(donViXuLyXLD));
			}

			List<Don> donCollections2 = new ArrayList<Don>();
			OrderSpecifier<Integer> sortOrder2 = QGiaiQuyetDon.giaiQuyetDon.thuTuThucHien.desc();
			Collection<GiaiQuyetDon> giaiQuyetDons = (Collection<GiaiQuyetDon>) giaiQuyetDonRepo
					.findAll(giaiQuyetDonQuery, sortOrder2);
			donCollections2 = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct()
					.collect(Collectors.toList());
			for (GiaiQuyetDon gqd : giaiQuyetDons) {
				System.out.println(gqd.getId());
			}
			// if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			// BooleanExpression donViKiemTraDeXuat =
			// QDon.don.processType.eq(ProcessTypeEnum.KIEM_TRA_DE_XUAT)
			// .and(QDon.don.donViThamTraXacMinh.id.eq(donViXuLyXLD));
			// BooleanExpression donViThamTraXacMinh =
			// QDon.don.processType.eq(ProcessTypeEnum.THAM_TRA_XAC_MINH)
			// .and(QDon.don.donViThamTraXacMinh.id.eq(donViXuLyXLD));
			// BooleanExpression processXuLyDon =
			// QDon.don.processType.eq(ProcessTypeEnum.XU_LY_DON);
			// BooleanExpression processNull = QDon.don.processType.isNull();
			//
			// BooleanExpression predOr =
			// processNull.or(processXuLyDon).or(donViKiemTraDeXuat).or(donViThamTraXacMinh);
			// predAll = predAll.and(predOr);
			// }
			predAll = predAll.and(QDon.don.in(donCollections).or(QDon.don.in(donCollections2)));
		} else {
			predAll = predAll.and(QDon.don.in(donCollections));
		}

		return predAll;
	}

	public Predicate predicateFindAllGQD(String maDon, String nguonDon, String phanLoaiDon, String tiepNhanTuNgay,
			String tiepNhanDenNgay, boolean thanhLapDon, String tinhTrangGiaiQuyet, String trangThaiDon,
			Long phongBanGiaiQuyetId, Long canBoGiaiQuyetId, String chucVu, String hoTen,
			GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(thanhLapDon));

		// Query don
		if (StringUtils.isNotBlank(maDon)) {
			predAll = predAll.and(QDon.don.ma.eq(StringUtils.trimToEmpty(maDon)));
		}

		if (StringUtils.isNotBlank(hoTen)) {
			predAll = predAll
					.and(QDon.don.donCongDans.any().congDan.hoVaTen.containsIgnoreCase(hoTen)
							.or(QDon.don.donCongDans.any().tenCoQuan.containsIgnoreCase(hoTen)))
					.and(QDon.don.donCongDans.any().phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON));
		}

		if (StringUtils.isNotBlank(nguonDon)) {
			predAll = predAll
					.and(QDon.don.nguonTiepNhanDon.eq(NguonTiepNhanDonEnum.valueOf(StringUtils.upperCase(nguonDon))));
		}

		if (StringUtils.isNotBlank(phanLoaiDon)) {
			predAll = predAll.and(QDon.don.loaiDon.eq(LoaiDonEnum.valueOf(StringUtils.upperCase(phanLoaiDon))));
		}

		if (StringUtils.isNotBlank(tinhTrangGiaiQuyet)) {
			predAll = predAll
					.and(QDon.don.trangThaiDon.eq(TrangThaiDonEnum.valueOf(StringUtils.upperCase(tinhTrangGiaiQuyet))));
		}

		if (StringUtils.isNotBlank(tiepNhanTuNgay) && StringUtils.isNotBlank(tiepNhanDenNgay)) {
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

		// if (phongBanGiaiQuyetId != null && phongBanGiaiQuyetId.longValue() >
		// 0) {
		// predAll =
		// predAll.and(QDon.don.phongBanGiaiQuyet.id.eq(phongBanGiaiQuyetId));
		// }

		// Query Giai quyet don
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false));

		if (StringUtils.isNotBlank(trangThaiDon)) {
			giaiQuyetDonQuery = giaiQuyetDonQuery
					.and(QGiaiQuyetDon.giaiQuyetDon.tinhTrangGiaiQuyet.stringValue().eq(trangThaiDon));
		}

		if (phongBanGiaiQuyetId != null && phongBanGiaiQuyetId.longValue() > 0) {
			giaiQuyetDonQuery = giaiQuyetDonQuery
					.and(QGiaiQuyetDon.giaiQuyetDon.phongBanGiaiQuyet.id.eq(phongBanGiaiQuyetId)
							.or(QGiaiQuyetDon.giaiQuyetDon.phongBanGiaiQuyet.cha.id.eq(phongBanGiaiQuyetId)));
		}

		if (StringUtils.isNotBlank(chucVu)) {
			giaiQuyetDonQuery = giaiQuyetDonQuery
					.and(QGiaiQuyetDon.giaiQuyetDon.chucVu.eq(VaiTroEnum.valueOf(StringUtils.upperCase(chucVu))));

			if (VaiTroEnum.CHUYEN_VIEN.equals(VaiTroEnum.valueOf(StringUtils.upperCase(chucVu)))) {
				giaiQuyetDonQuery = giaiQuyetDonQuery
						.and(QGiaiQuyetDon.giaiQuyetDon.canBoXuLyChiDinh.id.eq(canBoGiaiQuyetId));
			}
		}

		List<Don> donCollections = new ArrayList<Don>();
		OrderSpecifier<Integer> sortOrder = QGiaiQuyetDon.giaiQuyetDon.thuTuThucHien.desc();
		Collection<GiaiQuyetDon> giaiQuyetDons = (Collection<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery,
				sortOrder);
		donCollections = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct()
				.collect(Collectors.toList());
		predAll = predAll.and(QDon.don.in(donCollections));
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

	public String getMaDon(DonRepository repo, Long donId) {
		String maDon = Utils.getMaDon();
		boolean flagTonTai = true;
		while (flagTonTai) {
			flagTonTai = isMaDonExists(repo, donId, maDon);
			if (flagTonTai) {
				maDon = Utils.getMaDon();
			}
		}
		return maDon;
	}

	public boolean isMaDonExists(DonRepository repo, Long donId, String maDon) {
		if (donId != null && donId > 0) {
			Predicate predicate = base.and(QDon.don.id.ne(donId)).and(QDon.don.ma.eq(maDon));
			return repo.exists(predicate);
		}
		return false;
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
			don.setYeuCauGapTrucTiepLanhDao(true);
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
				.and(QDon.don.yeuCauGapTrucTiepLanhDao.eq(true).and(QDon.don.thanhLapDon.eq(false)))
				.or(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO).and(QDon.don.thanhLapDon.eq(true)))
				.and(QDon.don.thanhLapTiepDanGapLanhDao.eq(false));
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

		return predAll;
	}

	public List<PropertyChangeObject> getListThayDoi(Don donNew, Don donOld) {
		List<PropertyChangeObject> list = new ArrayList<PropertyChangeObject>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		if (donNew.getNoiDung() != null && !donNew.getNoiDung().isEmpty() && donOld.getNoiDung() != null
				&& !donNew.getNoiDung().equals(donOld.getNoiDung())) {
			list.add(new PropertyChangeObject("Nội dung", donOld.getNoiDung(), donNew.getNoiDung()));
		}
		if (donNew.getYeuCauCuaCongDan() != null
				&& !donNew.getYeuCauCuaCongDan().equals(donOld.getYeuCauCuaCongDan())) {
			list.add(new PropertyChangeObject("Yêu cầu của công dân", donOld.getYeuCauCuaCongDan(),
					donNew.getYeuCauCuaCongDan()));
		}
		if (donNew.getHuongGiaiQuyetDaThucHien() != null
				&& !donNew.getHuongGiaiQuyetDaThucHien().equals(donOld.getHuongGiaiQuyetDaThucHien())) {
			list.add(new PropertyChangeObject("Hướng giải quyết đã thực hiện", donOld.getHuongGiaiQuyetDaThucHien(),
					donNew.getHuongGiaiQuyetDaThucHien()));
		}
		if (donNew.getyKienXuLyDon() != null && !donNew.getyKienXuLyDon().equals(donOld.getyKienXuLyDon())) {
			list.add(new PropertyChangeObject("Ý kiến xử lý đơn", donOld.getyKienXuLyDon(), donNew.getyKienXuLyDon()));
		}
		if (donNew.getGhiChuXuLyDon() != null && !donNew.getGhiChuXuLyDon().equals(donOld.getGhiChuXuLyDon())) {
			list.add(new PropertyChangeObject("Ghi chú xử lý đơn", donOld.getGhiChuXuLyDon(),
					donNew.getGhiChuXuLyDon()));
		}
		if (donNew.getLyDoDinhChi() != null && !donNew.getLyDoDinhChi().equals(donOld.getLyDoDinhChi())) {
			list.add(new PropertyChangeObject("Lý do đình chỉ", donOld.getLyDoDinhChi(), donNew.getLyDoDinhChi()));
		}
		if (donNew.getSoQuyetDinhDinhChi() != null
				&& !donNew.getSoQuyetDinhDinhChi().equals(donOld.getSoQuyetDinhDinhChi())) {
			list.add(new PropertyChangeObject("Số quyết định đình chỉ", donOld.getSoQuyetDinhDinhChi(),
					donNew.getSoQuyetDinhDinhChi()));
		}
		if (donNew.getSoVanBanDaGiaiQuyet() != null
				&& !donNew.getSoVanBanDaGiaiQuyet().equals(donOld.getSoVanBanDaGiaiQuyet())) {
			list.add(new PropertyChangeObject("Số văn bản đã giải quyết", donOld.getSoVanBanDaGiaiQuyet(),
					donNew.getSoVanBanDaGiaiQuyet()));
		}
		if (donNew.getSoLanKhieuNaiToCao() != donOld.getSoLanKhieuNaiToCao()) {
			list.add(new PropertyChangeObject("Số lần khiếu nại tố cáo", donOld.getSoLanKhieuNaiToCao() + "",
					donNew.getSoLanKhieuNaiToCao() + ""));
		}
		if (donNew.getSoNguoi() != donOld.getSoNguoi()) {
			list.add(new PropertyChangeObject("Số người tham gia", donOld.getSoNguoi() + "", donNew.getSoNguoi() + ""));
		}
		if (donNew.isCoUyQuyen() != donOld.isCoUyQuyen()) {
			list.add(new PropertyChangeObject("Có ủy quyền", donOld.isCoUyQuyen() ? "Có" : "Không",
					donNew.isCoUyQuyen() ? "Có" : "Không"));
		}
		if (donNew.isThanhLapDon() != donOld.isThanhLapDon()) {
			list.add(new PropertyChangeObject("Thành lập đơn", donOld.isThanhLapDon() ? "Có" : "Không",
					donNew.isThanhLapDon() ? "Có" : "Không"));
		}
		if (donNew.getNgayTiepNhan() != null && !donNew.getNgayTiepNhan().format(formatter)
				.equals(donOld.getNgayTiepNhan() != null ? donOld.getNgayTiepNhan().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày tiếp nhận đơn",
					donOld.getNgayTiepNhan() != null ? donOld.getNgayTiepNhan().format(formatter) : "",
					donNew.getNgayTiepNhan().format(formatter)));
		}
		if (donNew.getNgayQuyetDinhDinhChi() != null && !donNew.getNgayQuyetDinhDinhChi().format(formatter).equals(
				donOld.getNgayQuyetDinhDinhChi() != null ? donOld.getNgayQuyetDinhDinhChi().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày quyết định đình chỉ",
					donOld.getNgayQuyetDinhDinhChi() != null ? donOld.getNgayQuyetDinhDinhChi().format(formatter) : "",
					donNew.getNgayQuyetDinhDinhChi().format(formatter)));
		}
		if (donNew.getThoiHanXuLyXLD() != null && !donNew.getThoiHanXuLyXLD().format(formatter)
				.equals(donOld.getThoiHanXuLyXLD() != null ? donOld.getThoiHanXuLyXLD().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Thời hạn xử lý XLĐ",
					donOld.getThoiHanXuLyXLD() != null ? donOld.getThoiHanXuLyXLD().format(formatter) : "",
					donNew.getThoiHanXuLyXLD().format(formatter)));
		}
		if (donNew.getNgayBatDauXLD() != null && !donNew.getNgayBatDauXLD().format(formatter)
				.equals(donOld.getNgayBatDauXLD() != null ? donOld.getNgayBatDauXLD().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày bắt đầu XLĐ",
					donOld.getNgayBatDauXLD() != null ? donOld.getNgayBatDauXLD().format(formatter) : "",
					donNew.getNgayBatDauXLD().format(formatter)));
		}
		if (donNew.getNgayKetThucXLD() != null && !donNew.getNgayKetThucXLD().format(formatter)
				.equals(donOld.getNgayKetThucXLD() != null ? donOld.getNgayKetThucXLD().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày kết thúc xử lý đơn",
					donOld.getNgayKetThucXLD() != null ? donOld.getNgayKetThucXLD().format(formatter) : "",
					donNew.getNgayKetThucXLD().format(formatter)));
		}
		if (donNew.getNgayBanHanhVanBanDaGiaiQuyet() != null && !donNew.getNgayBanHanhVanBanDaGiaiQuyet()
				.format(formatter).equals(donOld.getNgayBanHanhVanBanDaGiaiQuyet() != null
						? donOld.getNgayBanHanhVanBanDaGiaiQuyet().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày ban hành văn bản đã giải quyết",
					donOld.getNgayBanHanhVanBanDaGiaiQuyet() != null
							? donOld.getNgayBanHanhVanBanDaGiaiQuyet().format(formatter) : "",
					donNew.getNgayBanHanhVanBanDaGiaiQuyet().format(formatter)));
		}

		if (donNew.getLinhVucDonThu() != null && donNew.getLinhVucDonThu() != donOld.getLinhVucDonThu()) {
			LinhVucDonThu linhVucDonThuNew = lichVuDonThuRepo.findOne(donNew.getLinhVucDonThu().getId());
			list.add(new PropertyChangeObject("Lĩnh vực đơn thư",
					donOld.getLinhVucDonThu() != null ? donOld.getLinhVucDonThu().getTen() : "",
					linhVucDonThuNew.getTen()));
		}
		if ((donNew.getLinhVucDonThuChiTiet() == null && donOld.getLinhVucDonThuChiTiet() != null)
				|| (donNew.getLinhVucDonThuChiTiet() != null && donOld.getLinhVucDonThuChiTiet() == null)
				|| (donNew.getLinhVucDonThuChiTiet() != donOld.getLinhVucDonThuChiTiet())) {
			LinhVucDonThu linhVucDonThuNew = lichVuDonThuRepo
					.findOne(donNew.getLinhVucDonThuChiTiet() != null ? donNew.getLinhVucDonThuChiTiet().getId() : 0L);
			list.add(new PropertyChangeObject("Lĩnh vực đơn thư chi tiết",
					donOld.getLinhVucDonThuChiTiet() != null ? donOld.getLinhVucDonThuChiTiet().getTen() : "",
					linhVucDonThuNew != null ? linhVucDonThuNew.getTen() : ""));
		}
		if ((donNew.getChiTietLinhVucDonThuChiTiet() == null && donOld.getChiTietLinhVucDonThuChiTiet() != null)
				|| (donNew.getChiTietLinhVucDonThuChiTiet() != null && donOld.getChiTietLinhVucDonThuChiTiet() == null)
				|| donNew.getChiTietLinhVucDonThuChiTiet() != donOld.getChiTietLinhVucDonThuChiTiet()) {
			LinhVucDonThu linhVucDonThuNew = lichVuDonThuRepo.findOne(donNew.getChiTietLinhVucDonThuChiTiet().getId());
			list.add(new PropertyChangeObject(
					"Chi tiết lĩnh vực đơn thư chi tiết", donOld.getChiTietLinhVucDonThuChiTiet() != null
							? donOld.getChiTietLinhVucDonThuChiTiet().getTen() : "",
					linhVucDonThuNew != null ? linhVucDonThuNew.getTen() : ""));
		}
		if ((donNew.getThamQuyenGiaiQuyet() == null && donOld.getThamQuyenGiaiQuyet() != null)
				|| (donNew.getThamQuyenGiaiQuyet() != null && donOld.getThamQuyenGiaiQuyet() == null)
				|| (donNew.getThamQuyenGiaiQuyet() != donOld.getThamQuyenGiaiQuyet())) {
			ThamQuyenGiaiQuyet thamQuyenGiaiQuyet = thamQuyenGiaiQuyetRepo
					.findOne(donNew.getThamQuyenGiaiQuyet() != null ? donNew.getThamQuyenGiaiQuyet().getId() : 0L);
			list.add(new PropertyChangeObject("Thẩm quyền giải quyết",
					donOld.getThamQuyenGiaiQuyet() != null ? donOld.getThamQuyenGiaiQuyet().getTen() : "",
					thamQuyenGiaiQuyet != null ? thamQuyenGiaiQuyet.getTen() : ""));
		}
		if ((donNew.getCoQuanDaGiaiQuyet() == null && donOld.getCoQuanDaGiaiQuyet() != null)
				|| (donNew.getCoQuanDaGiaiQuyet() != null && donOld.getCoQuanDaGiaiQuyet() == null)
				|| (donNew.getCoQuanDaGiaiQuyet() != donOld.getCoQuanDaGiaiQuyet())) {
			CoQuanQuanLy coQuanQuanLy = coQuanQuanLyRepository
					.findOne(donNew.getCoQuanDaGiaiQuyet() != null ? donNew.getCoQuanDaGiaiQuyet().getId() : 0L);
			list.add(new PropertyChangeObject("Cơ quan đã giải quyết",
					donOld.getCoQuanDaGiaiQuyet() != null ? donOld.getCoQuanDaGiaiQuyet().getTen() : "",
					coQuanQuanLy != null ? coQuanQuanLy.getTen() : ""));
		}
		if (donNew.getLoaiDon() != null && !donNew.getLoaiDon().equals(donOld.getLoaiDon())) {
			list.add(new PropertyChangeObject("Loại đơn",
					donOld.getLoaiDon() != null ? donOld.getLoaiDon().getText() : "",
					donNew.getLoaiDon() != null ? donNew.getLoaiDon().getText() : ""));
		}
		if (donNew.getLoaiDoiTuong() != null && !donNew.getLoaiDoiTuong().equals(donOld.getLoaiDoiTuong())) {
			list.add(new PropertyChangeObject("Loại đối tượng",
					donOld.getLoaiDoiTuong() != null ? donOld.getLoaiDoiTuong().getText() : "",
					donNew.getLoaiDoiTuong() != null ? donNew.getLoaiDoiTuong().getText() : ""));
		}

		if (donNew.getNguonTiepNhanDon() != null
				&& !donNew.getNguonTiepNhanDon().equals(donOld.getNguonTiepNhanDon())) {
			list.add(new PropertyChangeObject("Nguồn tiếp nhận đơn",
					donOld.getNguonTiepNhanDon() != null ? donOld.getNguonTiepNhanDon().getText() : "",
					donNew.getNguonTiepNhanDon() != null ? donNew.getNguonTiepNhanDon().getText() : ""));
		}

		if (donNew.getLoaiNguoiDungDon() != null
				&& !donNew.getLoaiNguoiDungDon().equals(donOld.getLoaiNguoiDungDon())) {
			list.add(new PropertyChangeObject("Loại người đứng đơn",
					donOld.getLoaiNguoiDungDon() != null ? donOld.getLoaiNguoiDungDon().getText() : "",
					donNew.getLoaiNguoiDungDon() != null ? donNew.getLoaiNguoiDungDon().getText() : ""));
		}

		return list;
	}

	public Don save(Don obj, Long congChucId) {
		return Utils.save(donRepo, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(Don obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(donRepo, obj, congChucId, eass, status);		
	}
}
