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

import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class TheoDoiGiamSatService {
BooleanExpression base = QDon.don.daXoa.eq(false);
	
	public Predicate predicateFindDanhSachDons(String quyTrinh, String tiepNhanTuNgay, String tiepNhanDenNgay, Long month, Long year, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true));
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false));
		List<Don> donCollections = new ArrayList<Don>();
		List<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		
		if (StringUtils.isNotBlank(quyTrinh)) {
			ProcessTypeEnum processType = ProcessTypeEnum.valueOf(quyTrinh);
			if (processType.equals(ProcessTypeEnum.XU_LY_DON)) {
				predAll = predAll.and(QDon.don.processType.eq(processType).or(QDon.don.ngayKetThucXLD.isNotNull()));
			} else {
				predAll = predAll.and(QDon.don.processType.eq(processType));
			}
		}
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
		
		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
		
		predAll = predAll.and(QDon.don.in(donCollections));
		
		return predAll;
	}
	
	public Predicate predicateFindDanhSachDonsTheoDonVi(BooleanExpression predDS, Long donViXuLyXLD, XuLyDonRepository xuLyRepo,
			DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {
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
	
	public Long getTongSoDonDungHanTreHanByTrangThai(BooleanExpression predAll, DonRepository donRepo, boolean isDungHan, TrangThaiDonEnum trangThaiEnum) {
		Long tongSo = 0L;
		if (predAll != null) {
			LocalDateTime hienTai = LocalDateTime.now();
			List<Don> dons = new ArrayList<Don>();
			if (trangThaiEnum.equals(TrangThaiDonEnum.DANG_XU_LY)) { 
				predAll = predAll.and(QDon.don.trangThaiDon.eq(trangThaiEnum));
				predAll = predAll.and(QDon.don.processType.eq(ProcessTypeEnum.XU_LY_DON))
						.and(QDon.don.ngayKetThucXLD.isNull());
				
				if (isDungHan) {
					predAll = predAll.and(QDon.don.thoiHanXuLyXLD.after(hienTai)
							.or(QDon.don.thoiHanXuLyXLD.year().eq(hienTai.getYear())
									.and(QDon.don.thoiHanXuLyXLD.month().eq(hienTai.getMonthValue())
											.and(QDon.don.thoiHanXuLyXLD.dayOfMonth().eq(hienTai.getDayOfMonth())))));					
					dons.addAll((List<Don>) donRepo.findAll(predAll));
				} else {		
//					predAll = predAll.and(QDon.don.thoiHanXuLyXLD.before(hienTai));
					dons.addAll((List<Don>) donRepo.findAll(predAll));
					dons = dons.stream().filter(d -> {
						if (d.getNgayBatDauXLD() != null && d.getThoiHanXuLyXLD() != null) {
							return Utils.isValidNgayTreHan(d.getNgayBatDauXLD(), d.getThoiHanXuLyXLD());
						}
						return false;
					}).collect(Collectors.toList());
				}
			} else {
				predAll = predAll.and(QDon.don.ngayKetThucXLD.isNotNull()
						)//.or(QDon.don.trangThaiDon.eq(TrangThaiDonEnum.DA_XU_LY)))
						.and(QDon.don.trangThaiDon.ne(TrangThaiDonEnum.DANG_XU_LY));
				predAll = predAll.and(QDon.don.processType.eq(ProcessTypeEnum.XU_LY_DON));
				if (isDungHan) {
					dons.addAll((List<Don>) donRepo.findAll(predAll));
					dons = dons.stream().filter(d -> {
						if (d.getThoiHanXuLyXLD() != null && d.getThoiHanXuLyXLD() != null && d.getNgayKetThucXLD() != null) { 
							return Utils.isValidNgayDungHan(d.getThoiHanXuLyXLD(), d.getNgayKetThucXLD());
						}
						return false;
					}).collect(Collectors.toList());
				} else {
					dons.addAll((List<Don>) donRepo.findAll(predAll));
					dons = dons.stream().filter(d -> {
						if (d.getThoiHanXuLyXLD() != null && d.getNgayKetThucXLD() != null && d.getNgayBatDauXLD() != null) { 
							return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(d.getId(), d.getNgayBatDauXLD(), d.getThoiHanXuLyXLD(),
									d.getNgayKetThucXLD());
						}
						return false;
					}).collect(Collectors.toList());
				}
			}
			//dons.addAll((List<Don>) donRepo.findAll(predAll));
			dons.forEach(d -> {
				System.out.println("don " +d.getId());
			});
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
}
