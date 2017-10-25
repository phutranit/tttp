package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.CanCuThanhTraLaiEnum;
import vn.greenglobal.tttp.enums.HinhThucThanhTraEnum;
import vn.greenglobal.tttp.enums.LinhVucThanhTraEnum;
import vn.greenglobal.tttp.enums.LoaiHinhThanhTraEnum;
import vn.greenglobal.tttp.enums.ThongKeBaoCaoLoaiKyEnum;
import vn.greenglobal.tttp.enums.TienDoThanhTraEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CuocThanhTra;
import vn.greenglobal.tttp.model.DoiTuongViPham;
import vn.greenglobal.tttp.model.QCuocThanhTra;
import vn.greenglobal.tttp.repository.CuocThanhTraRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class ThongKeTongHopThanhTraService {
	
	BooleanExpression base = QCuocThanhTra.cuocThanhTra.daXoa.eq(false);
	
	public Predicate predicateFindAllCuocThanhTraTrongKy(String loaiKy, Integer quy, Integer year, Integer month, String tuNgay, String denNgay) {
		
		BooleanExpression predAllCuocThanhTra = base;
		
		if (year != null && year > 0) { 
			predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.year().eq(year));
		}
		
		if (loaiKy != null && StringUtils.isNotBlank(loaiKy)) {
			
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			if (loaiKyEnum  != null) {
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
					if (quy != null && quy > 0) { 
						if (quy == 1) { 
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().between(1, 3));
						}
						if (quy == 2) { 
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().between(4, 6));
						}
						if (quy == 3) { 
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().between(7, 9));
						}
						if (quy == 4) { 
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().between(10, 12));
						}
					}
				}
				
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_DAU_NAM)) {
					predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().between(1, 6));
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_CUOI_NAM)) {
					predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().between(7, 12));
				}
				
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_THANG)) {
					if (month != null && month > 0) {
						predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().eq(month));
					}
				}
				
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON)) {
					if (StringUtils.isNotBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
						LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
						LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
						predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.between(dtTuNgay, dtDenNgay));
					} else {
						if (StringUtils.isNotBlank(tuNgay)) {
							LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.after(dtTuNgay));
						}
						if (StringUtils.isNotBlank(denNgay)) {
							LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(dtDenNgay));
						}
					}
				}
			}
		}
		
		return predAllCuocThanhTra;
	}
	
	public Predicate predicateFindAllCuocThanhTra(String loaiKy, Integer quy, Integer year, Integer month, String tuNgay, String denNgay) {
		
		BooleanExpression predAllCuocThanhTra = base;
		
		if (year != null && year > 0) { 
			predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.year().eq(year)
					.or(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.year().lt(year).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull()))
					);
		}
		
		if (loaiKy != null && StringUtils.isNotBlank(loaiKy)) {
			
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			if (loaiKyEnum  != null) {
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
					if (quy != null && quy > 0) { 
						if (quy == 1) { 
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().between(1, 3));
						}
						if (quy == 2) { 
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().between(4, 6)
									.or(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().lt(4).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull())));
						}
						if (quy == 3) { 
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().between(7, 9)
									.or(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().lt(7).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull())));
						}
						if (quy == 4) { 
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().between(10, 12)
									.or(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().lt(10).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull())));
						}
					}
				}
				
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_DAU_NAM)) {
					predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().between(1, 6));
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_CUOI_NAM)) {
					predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().between(7, 12)
							.or(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().lt(7).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull())));
				}
				
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_THANG)) {
					if (month != null && month > 0) {
						predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().eq(month)
								.or(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().lt(month).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull())));
					}
				}
				
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON)) {
					if (StringUtils.isNotBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
						LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
						LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
						predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.between(dtTuNgay, dtDenNgay)
								.or(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(dtTuNgay).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull())));
					} else {
						if (StringUtils.isNotBlank(tuNgay)) {
							LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.after(dtTuNgay)
									.or(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(dtTuNgay).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull())));
						}
						if (StringUtils.isNotBlank(denNgay)) {
							LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(dtDenNgay));
						}
					}
				}
			}
		}
		
		return predAllCuocThanhTra;
	}
	
	public LocalDateTime getDateFromCalendar(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONDAY, month-1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE,0);
		return LocalDateTime.ofInstant(cal.getTime().toInstant(), ZoneId.systemDefault());
	}
	
	public Predicate predicateFindAllCuocThanhTraKyTruoc(String loaiKy, Integer quy, Integer year, Integer month, String tuNgay, String denNgay) {
		BooleanExpression predAllCuocThanhTra = base;
		
		if (year != null && year > 0) { 
			predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.year().eq(year)
					.or(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.year().lt(year).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull()))
					);
		}
		
		if (loaiKy != null && StringUtils.isNotBlank(loaiKy)) {
			
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			if (loaiKyEnum  != null) {
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
					if (quy != null && quy > 0) { 
						if (quy == 2) { 
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(getDateFromCalendar(year, 4, 1)).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull()));
						}
						if (quy == 3) { 
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(getDateFromCalendar(year, 7, 1)).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull()));
						}
						if (quy == 4) { 
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(getDateFromCalendar(year, 10, 1)).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull()));
						}
					}
				}
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.SAU_THANG_CUOI_NAM)) {
					predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(getDateFromCalendar(year, 7, 1)).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull()));
				}
				
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_THANG)) {
					if (month != null && month > 0) {
						predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.month().eq(month)
								.or(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(getDateFromCalendar(year, month, 1)).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull())));
					}
				}
				
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON)) {
					if (StringUtils.isNotBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
						LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
						predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(dtTuNgay).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull()));
					} else {
						if (StringUtils.isNotBlank(tuNgay)) {
							LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(dtTuNgay).and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull()));
						}
					}
				}
			}
		}
		
		return predAllCuocThanhTra;
	}
	
	/*
	 * Tim cuoc thanh tra lai
	 * 
	 */
	public Predicate predicateFindCuocThanhTraLai(BooleanExpression predAll, CuocThanhTraRepository cuocThanhTraRepo, CoQuanQuanLy coQuanQuanLy) {
		
		predAll = predAll.and(QCuocThanhTra.cuocThanhTra.loaiHinhThanhTra.eq(LoaiHinhThanhTraEnum.THANH_TRA_LAI))
				.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(coQuanQuanLy))
				.and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.isNull());
		
		return predAll;
	}
	
	// 1
	public Predicate predicateFindCuocThanhTraTheoLinhVuc(BooleanExpression predAll,Long donViXuLyXLD ,LinhVucThanhTraEnum linhVucThanhTraEnum,CuocThanhTraRepository cuocThanhTraRepo) {
		
		predAll = predAll.
				and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(donViXuLyXLD)).
				and(QCuocThanhTra.cuocThanhTra.linhVucThanhTra.eq(linhVucThanhTraEnum));
		
		return predAll; 
	}
	
	// 4,5
	public Long getCuocThanhTraTheoHinhThuc(BooleanExpression predAll, HinhThucThanhTraEnum hinhThucThanhTraEnum, CuocThanhTraRepository cuocThanhTraRepo) {
		
		Long tongSoDon = 0L;
		
		if (StringUtils.equals(hinhThucThanhTraEnum.name(), HinhThucThanhTraEnum.THEO_KE_HOACH.name())) {
			
			predAll = predAll.
					and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.isNotNull()).
					and(QCuocThanhTra.cuocThanhTra.hinhThucThanhTra.eq(HinhThucThanhTraEnum.THEO_KE_HOACH));
		} else if (StringUtils.equals(hinhThucThanhTraEnum.name(), HinhThucThanhTraEnum.DOT_XUAT.name())) {
			
			predAll = predAll.
					and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.isNull()).
					and(QCuocThanhTra.cuocThanhTra.hinhThucThanhTra.eq(HinhThucThanhTraEnum.DOT_XUAT));
		}
		
		tongSoDon = Long.valueOf(((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll)).size());
		
		return tongSoDon;
	}
	 
	// 6,7
	public Long getCuocThanhTraTheoTienDo(BooleanExpression predAll, TienDoThanhTraEnum tienDoThanhTraEnum, CuocThanhTraRepository cuocThanhTraRepo) {
		
		Long tongSoDon = 0L;

		predAll = predAll.and(QCuocThanhTra.cuocThanhTra.tienDoThanhTra.eq(tienDoThanhTraEnum));
		tongSoDon = Long.valueOf(((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll)).size());
		
		return tongSoDon;
	}
	
	public Long getCuocThanhTraTheoCanCu(BooleanExpression predAll, CanCuThanhTraLaiEnum canCuThanhTraLai, CuocThanhTraRepository cuocThanhTraRepo) {
		
		Long tongSoDon = 0L;

		predAll = predAll.and(QCuocThanhTra.cuocThanhTra.canCuThanhTraLai.contains(canCuThanhTraLai.getText()));
		tongSoDon = Long.valueOf(((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll)).size());
		
		return tongSoDon;
	}
	
	
	
	// 8
	public Long getSoDonViDuocThanhTra(BooleanExpression predAll, CuocThanhTraRepository cuocThanhTraRepo) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongGiaTri = 0L;
		
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
			
		tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> {

			Long tongDonVi = (long) elem.getDoiTuongThanhTras().size();

			if (!elem.getDoiTuongThanhTraLienQuan().isEmpty()) {

				String[] array = elem.getDoiTuongThanhTraLienQuan().split(";");
				tongDonVi += array.length;
			}

			return tongDonVi;

		}).mapToLong(Long::longValue).sum());
		
		return tongGiaTri;
	}
	
	public Long getSoDonViDuocThanhTraCoViPham(BooleanExpression predAll, CuocThanhTraRepository cuocThanhTraRepo) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongGiaTri = 0L;
		
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll.and(QCuocThanhTra.cuocThanhTra.viPham.eq(true))));
			
		tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> {

			Long tongDonVi = (long) elem.getDoiTuongThanhTras().size();

			if (!elem.getDoiTuongThanhTraLienQuan().isEmpty()) {

				String[] array = elem.getDoiTuongThanhTraLienQuan().split(";");
				tongDonVi += array.length;
			}

			return tongDonVi;

		}).mapToLong(Long::longValue).sum());
		
		return tongGiaTri;
	}
	
	
	// 9
	public Predicate predicateFindCuocThanhTraCoViPham(BooleanExpression predAll, CuocThanhTraRepository cuocThanhTraRepo) {
		
		predAll = predAll.and(QCuocThanhTra.cuocThanhTra.viPham.isTrue());
		
		return predAll;
	}
	
	// 11 - 15
	public Long getTienDatTheoViPham(BooleanExpression predAll, String type, String typeValue, CuocThanhTraRepository cuocThanhTraRepo) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongGiaTri = 0L;
		
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> {

			Long tongDonVi = 0L;
			
			if (StringUtils.equals(type, "TONG_VI_PHAM")) {				
				if (StringUtils.equals(typeValue, "DAT")) {
					for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
						if (doiTuong.isChuyenCoQuanDieuTra()) {
							tongDonVi += doiTuong.getSaiPhamVeDatKienNghiThuHoiKienNghiXuLyVeKinhTe() + doiTuong.getSaiPhamVeDatKienNghiKhacXuLyVeKinhTe();
						}					
					}
				} else if (StringUtils.equals(typeValue, "TIEN")) {
					for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
						if (doiTuong.isChuyenCoQuanDieuTra()) {
							tongDonVi += doiTuong.getSaiPhamVeTienKienNghiThuHoiKienNghiXuLyVeKinhTe() + doiTuong.getSaiPhamVeTienKienNghiKhacXuLyVeKinhTe();
						}					
					}
				}
				
			} else if (StringUtils.equals(type, "KIEN_NGHI_THU_HOI")) {

				if (StringUtils.equals(typeValue, "DAT")) {
					for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
						if (doiTuong.isChuyenCoQuanDieuTra()) {
							tongDonVi += doiTuong.getSaiPhamVeDatKienNghiThuHoiKienNghiXuLyVeKinhTe();
						}					
					}
				} else if (StringUtils.equals(typeValue, "TIEN")) {
					for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
						if (doiTuong.isChuyenCoQuanDieuTra()) {
							tongDonVi += doiTuong.getSaiPhamVeTienKienNghiThuHoiKienNghiXuLyVeKinhTe();
						}					
					}
				}
				
			} else if (StringUtils.equals(type, "KIEN_NGHI_KHAC")) {

				if (StringUtils.equals(typeValue, "DAT")) {
					for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
						if (doiTuong.isChuyenCoQuanDieuTra()) {
							tongDonVi += doiTuong.getSaiPhamVeDatKienNghiKhacXuLyVeKinhTe();
						}					
					}
				} else if (StringUtils.equals(typeValue, "TIEN")) {
					for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
						if (doiTuong.isChuyenCoQuanDieuTra()) {
							tongDonVi += doiTuong.getSaiPhamVeTienKienNghiKhacXuLyVeKinhTe();
						}					
					}
				}
			}

			return tongDonVi;

		}).mapToLong(Long::longValue).sum());
	
		return tongGiaTri;
	}
	
	public Long getKienNghiXuLyHanhChinh(BooleanExpression predAll, String type, CuocThanhTraRepository cuocThanhTraRepo) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongSoDon = 0L;
	
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		tongSoDon = Long.valueOf(cuocThanhTras.stream().map(elem -> {			
			Long tongDonVi = 0L;
			
			if (StringUtils.equals(type, "TO_CHUC")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					if (!doiTuong.getToChucCoSaiPham().isEmpty()) {
						String[] array = doiTuong.getToChucCoSaiPham().split(";");
						tongDonVi += array.length;
					}
				}
			} else if (StringUtils.equals(type, "CA_NHAN")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					if (!doiTuong.getCaNhanCoSaiPham().isEmpty()) {
						String[] array = doiTuong.getToChucCoSaiPham().split(";");
						tongDonVi += array.length;
					}
				}
			}
			return tongDonVi;

		}).mapToLong(Long::longValue).sum());
		
		return tongSoDon;
	}
	
	public Predicate predicateFindCuocThanhTraChuyenCoQuanDieuTra(BooleanExpression predAll, CuocThanhTraRepository cuocThanhTraRepo) {
		
		predAll = predAll.and(QCuocThanhTra.cuocThanhTra.chuyenCoQuanDieuTra.eq(true));
		
		return predAll;
	}
	
	public Long getKienNghiXuLyCCQDT(BooleanExpression predAll, String type,CuocThanhTraRepository cuocThanhTraRepo ) {
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongGiaTri = 0L;
		
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> {

			Long tongDonVi = 0L;
			
			if ("VU".equals(type)) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					if (doiTuong.isChuyenCoQuanDieuTra()) {
						tongDonVi += doiTuong.getSoVuChuyenCoQuanDieuTra();
					}					
				}
			} else if ("DOI_TUONG".equals(type)) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					if (doiTuong.isChuyenCoQuanDieuTra()) {
						tongDonVi += doiTuong.getSoVuViecKienNghiChuyenCoQuanDieuTra();
					}					
				}
			}
			return tongDonVi;

		}).mapToLong(Long::longValue).sum());
		
		return tongGiaTri;
	}
	
}
    