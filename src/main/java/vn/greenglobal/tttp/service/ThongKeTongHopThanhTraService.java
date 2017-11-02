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
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(getDateFromCalendar(year, 4, 1))
									.and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull()));
						}
						if (quy == 3) { 
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(getDateFromCalendar(year, 7, 1))
									.and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull()));
						}
						if (quy == 4) { 
							System.out.println("year: " + year);
							System.out.println("getDateFromCalendar(year, 10, 1): " + getDateFromCalendar(year, 10, 1));
							predAllCuocThanhTra = predAllCuocThanhTra.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(getDateFromCalendar(year, 10, 1))
									.and(QCuocThanhTra.cuocThanhTra.ngayBanHanhKetLuanThanhTra.isNull()));
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
					//and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.isNotNull()).
					and(QCuocThanhTra.cuocThanhTra.hinhThucThanhTra.eq(HinhThucThanhTraEnum.THEO_KE_HOACH));
		} else if (StringUtils.equals(hinhThucThanhTraEnum.name(), HinhThucThanhTraEnum.DOT_XUAT.name())) {
			
			predAll = predAll.
					//and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.isNull()).
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

			return tongDonVi + 1;

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
	
	public Long getSoVuNguoiThamNhung(BooleanExpression predAll, CuocThanhTraRepository cuocThanhTraRepo, String type) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongGiaTri = 0L;
		
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		if (type.equals("VU")) {
			tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getSoVuThamNhung()).mapToInt(Integer::intValue).sum());
		} else if (type.equals("NGUOI")) {
			tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getSoDoiTuongThamNhung()).mapToInt(Integer::intValue).sum());
		}
		
		return tongGiaTri;
	}
	
	public Long getSoKienNghiXuLyHanhChinhThamNhung(BooleanExpression predAll, CuocThanhTraRepository cuocThanhTraRepo, String type) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongGiaTri = 0L;
		
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		if (type.equals("TO_CHUC")) {
			tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getToChucXuLyHanhChinhThamNhung()).mapToInt(Integer::intValue).sum());
		} else if (type.equals("CA_NHAN")) {
			tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getCaNhanXuLyHanhChinhThamNhung()).mapToInt(Integer::intValue).sum());
		}
		
		return tongGiaTri;
	}
	
	public Long getSoDaThuTrongQuaTrinhThanhTra(BooleanExpression predAll, CuocThanhTraRepository cuocThanhTraRepo, String type) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongGiaTri = 0L;
		
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> {

			Long tongDonVi = 0L;
			
			if (StringUtils.equals(type, "DAT")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getDatDaThuTrongQuaTrinhThanhTra();				
				}
			} else if (StringUtils.equals(type, "TIEN")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getTienDaThuTrongQuaTrinhThanhTra();				
				}
			} else if (StringUtils.equals(type, "QD_GIAO_DAT")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					if (doiTuong.getQuyetDinhGiaoDatThuHoiTrongQuaTrinhThanhTra() != null && !doiTuong.getQuyetDinhGiaoDatThuHoiTrongQuaTrinhThanhTra().isEmpty()) {
						tongDonVi++;
					}		
				}
			}

			return tongDonVi;

		}).mapToLong(Long::longValue).sum());
		
		return tongGiaTri;
	}
	
	public Long getSoChuyenCoQuanDieuTraThamNhung(BooleanExpression predAll, CuocThanhTraRepository cuocThanhTraRepo, String type) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongGiaTri = 0L;
		
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		if (type.equals("VU")) {
			tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getSoVuThamNhung()).mapToInt(Integer::intValue).sum());
		} else if (type.equals("DOI_TUONG")) {
			tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getSoDoiTuongThamNhung()).mapToInt(Integer::intValue).sum());
		}
		
		return tongGiaTri;
	}
	
	public Long getGiaTriThamNhung(BooleanExpression predAll, CuocThanhTraRepository cuocThanhTraRepo, String type, String loaiGiaTri) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongGiaTri = 0L;
		
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		if (type.equals("TAI_SAN_THAM_NHUNG")) {
			if (loaiGiaTri.equals("TONG_TIEN")) {
				tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getTienThamNhung() + elem.getTaiSanKhacThamNhung()).mapToLong(Long::longValue).sum());
			} else if (loaiGiaTri.equals("TIEN")) {
				tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getTienThamNhung()).mapToLong(Long::longValue).sum());
			} else if (loaiGiaTri.equals("TAI_SAN_KHAC")) {
				tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getTaiSanKhacThamNhung()).mapToLong(Long::longValue).sum());
			} else if (loaiGiaTri.equals("DAT")) {
				tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getDatThamNhung()).mapToLong(Long::longValue).sum());
			}			
		} else if (type.equals("KIEN_NGHI_THU_HOI")) {
			if (loaiGiaTri.equals("TONG_TIEN")) {
				tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getTienKienNghiThuHoi() + elem.getTaiSanKhacKienNghiThuHoi()).mapToLong(Long::longValue).sum());
			} else if (loaiGiaTri.equals("TIEN")) {
				tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getTienKienNghiThuHoi()).mapToLong(Long::longValue).sum());
			} else if (loaiGiaTri.equals("TAI_SAN_KHAC")) {
				tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getTaiSanKhacKienNghiThuHoi()).mapToLong(Long::longValue).sum());
			} else if (loaiGiaTri.equals("DAT")) {
				tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getDatKienNghiThuHoi()).mapToLong(Long::longValue).sum());
			}
		} else if (type.equals("DA_THU")) {
			if (loaiGiaTri.equals("TONG_TIEN")) {
				tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getTienDaThu() + elem.getTaiSanKhacDaThu()).mapToLong(Long::longValue).sum());
			} else if (loaiGiaTri.equals("TIEN")) {
				tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getTienDaThu()).mapToLong(Long::longValue).sum());
			} else if (loaiGiaTri.equals("TAI_SAN_KHAC")) {
				tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getTaiSanKhacDaThu()).mapToLong(Long::longValue).sum());
			} else if (loaiGiaTri.equals("DAT")) {
				tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> elem.getDatDaThu()).mapToLong(Long::longValue).sum());
			}
		}
		
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
						tongDonVi += doiTuong.getSaiPhamVeDatKienNghiThuHoiKienNghiXuLyVeKinhTe() + doiTuong.getSaiPhamVeDatKienNghiKhacXuLyVeKinhTe();				
					}
				} else if (StringUtils.equals(typeValue, "TIEN")) {
					for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
						tongDonVi += doiTuong.getSaiPhamVeTienKienNghiThuHoiKienNghiXuLyVeKinhTe() + doiTuong.getSaiPhamVeTienKienNghiKhacXuLyVeKinhTe();				
					}
				}
				
			} else if (StringUtils.equals(type, "KIEN_NGHI_THU_HOI")) {

				if (StringUtils.equals(typeValue, "DAT")) {
					for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
						tongDonVi += doiTuong.getSaiPhamVeDatKienNghiThuHoiKienNghiXuLyVeKinhTe();			
					}
				} else if (StringUtils.equals(typeValue, "TIEN")) {
					for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
						tongDonVi += doiTuong.getSaiPhamVeTienKienNghiThuHoiKienNghiXuLyVeKinhTe();		
					}
				} else if (StringUtils.equals(typeValue, "QD_GIAO_DAT")) {
					for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
						if (doiTuong.getQuyetDinhGiaoDat() != null && !doiTuong.getQuyetDinhGiaoDat().isEmpty()) {
							tongDonVi++;
						}
					}
				}
				
			} else if (StringUtils.equals(type, "KIEN_NGHI_KHAC")) {

				if (StringUtils.equals(typeValue, "DAT")) {
					for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
						tongDonVi += doiTuong.getSaiPhamVeDatKienNghiKhacXuLyVeKinhTe();				
					}
				} else if (StringUtils.equals(typeValue, "TIEN")) {
					for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
						tongDonVi += doiTuong.getSaiPhamVeTienKienNghiKhacXuLyVeKinhTe();			
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
					if (doiTuong.getToChucCoSaiPham() != null && !doiTuong.getToChucCoSaiPham().isEmpty()) {
						String[] array = doiTuong.getToChucCoSaiPham().split(";");
						tongDonVi += array.length;
					}
				}
			} else if (StringUtils.equals(type, "CA_NHAN")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					if (doiTuong.getCaNhanCoSaiPham() != null && !doiTuong.getCaNhanCoSaiPham().isEmpty()) {
						String[] array = doiTuong.getCaNhanCoSaiPham().split(";");
						tongDonVi += array.length;
					}
				}
			} else if (StringUtils.equals(type, "TONG_SO")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					if (doiTuong.getCaNhanCoSaiPham() != null && !doiTuong.getCaNhanCoSaiPham().isEmpty()) {
						String[] array = doiTuong.getCaNhanCoSaiPham().split(";");
						tongDonVi += array.length;
					}
					if (doiTuong.getToChucCoSaiPham() != null && !doiTuong.getToChucCoSaiPham().isEmpty()) {
						String[] array = doiTuong.getToChucCoSaiPham().split(";");
						tongDonVi += array.length;
					}
				}
			}
			return tongDonVi;

		}).mapToLong(Long::longValue).sum());
		
		return tongSoDon;
	}
	
	
	public Long getTienViPhamKienNghiXuLyHanhChinh(BooleanExpression predAll, String type, CuocThanhTraRepository cuocThanhTraRepo) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongSoDon = 0L;
	
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		tongSoDon = Long.valueOf(cuocThanhTras.stream().map(elem -> {			
			Long tongDonVi = 0L;
			
			if (StringUtils.equals(type, "TO_CHUC")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getTienToChucViPhamKienNghiXuLyVeKinhTe();
				}
			} else if (StringUtils.equals(type, "CA_NHAN")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getTienCaNhanViPhamKienNghiXuLyVeKinhTe();
				}
			} else if (StringUtils.equals(type, "TONG_SO")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getTienToChucViPhamKienNghiXuLyVeKinhTe() + doiTuong.getTienCaNhanViPhamKienNghiXuLyVeKinhTe();
				}
			} if (StringUtils.equals(type, "KIEN_NGHI_THU_HOI")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getSoTienKienNghiThuHoiKienNghiXuLyVeKinhTe();
				}
			}
			return tongDonVi;

		}).mapToLong(Long::longValue).sum());
		
		return tongSoDon;
	}
	
	public Long getSoQuyetDinhXuPhatKienNghiXuLyHanhChinh(BooleanExpression predAll, String type, CuocThanhTraRepository cuocThanhTraRepo) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongSoDon = 0L;
	
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		tongSoDon = Long.valueOf(cuocThanhTras.stream().map(elem -> {			
			Long tongDonVi = 0L;
			
			if (StringUtils.equals(type, "TO_CHUC")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					if (doiTuong.getSoQuyetDinhKienNghiXuLyHanhChinhToChuc() != null && !doiTuong.getSoQuyetDinhKienNghiXuLyHanhChinhToChuc().isEmpty()) {
						tongDonVi++;
					}
				}
			} else if (StringUtils.equals(type, "CA_NHAN")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					if (doiTuong.getSoQuyetDinhKienNghiXuLyHanhChinhCaNhan() != null && !doiTuong.getSoQuyetDinhKienNghiXuLyHanhChinhCaNhan().isEmpty()) {
						tongDonVi++;
					}
				}
			} else if (StringUtils.equals(type, "TONG_SO")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					if (doiTuong.getSoQuyetDinhKienNghiXuLyHanhChinhToChuc() != null && !doiTuong.getSoQuyetDinhKienNghiXuLyHanhChinhToChuc().isEmpty()) {
						tongDonVi++;
					}
					if (doiTuong.getSoQuyetDinhKienNghiXuLyHanhChinhCaNhan() != null && !doiTuong.getSoQuyetDinhKienNghiXuLyHanhChinhCaNhan().isEmpty()) {
						tongDonVi++;
					}
				}
			} 
			return tongDonVi;

		}).mapToLong(Long::longValue).sum());
		
		return tongSoDon;
	}
	
	public Long getTienXuLyTaiSanKienNghiXuLyHanhChinh(BooleanExpression predAll, String type, CuocThanhTraRepository cuocThanhTraRepo) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongSoDon = 0L;
	
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		tongSoDon = Long.valueOf(cuocThanhTras.stream().map(elem -> {			
			Long tongDonVi = 0L;
			
			if (StringUtils.equals(type, "TICH_THU")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getTienTichThuXuLyTaiSanViPhamKienNghiXuLyVeKinhTe();
				}
			} else if (StringUtils.equals(type, "TIEU_HUY")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getTienTieuHuyXuLyTaiSanViPhamKienNghiXuLyVeKinhTe();
				}
			} else if (StringUtils.equals(type, "TONG_SO")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getTienTichThuXuLyTaiSanViPhamKienNghiXuLyVeKinhTe() + doiTuong.getTienTieuHuyXuLyTaiSanViPhamKienNghiXuLyVeKinhTe();
				}
			} 
			return tongDonVi;

		}).mapToLong(Long::longValue).sum());
		
		return tongSoDon;
	}
	
	public Long getTienXuPhatViPhamKienNghiXuLyHanhChinh(BooleanExpression predAll, String type, CuocThanhTraRepository cuocThanhTraRepo) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongSoDon = 0L;
	
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		tongSoDon = Long.valueOf(cuocThanhTras.stream().map(elem -> {			
			Long tongDonVi = 0L;
			
			if (StringUtils.equals(type, "CA_NHAN")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getTienCaNhanXuPhatViPhamKienNghiXuLyVeKinhTe();
				}
			} else if (StringUtils.equals(type, "TO_CHUC")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getTienToChucXuPhatViPhamKienNghiXuLyVeKinhTe();
				}
			} else if (StringUtils.equals(type, "TONG_SO")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getTienCaNhanXuPhatViPhamKienNghiXuLyVeKinhTe() + doiTuong.getTienToChucXuPhatViPhamKienNghiXuLyVeKinhTe();
				}
			} 
			return tongDonVi;

		}).mapToLong(Long::longValue).sum());
		
		return tongSoDon;
	}
	
	public Long getTienDaThuThanhTraChuyenNganh(BooleanExpression predAll, String type, CuocThanhTraRepository cuocThanhTraRepo) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongSoDon = 0L;
	
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		tongSoDon = Long.valueOf(cuocThanhTras.stream().map(elem -> {			
			Long tongDonVi = 0L;
			
			if (StringUtils.equals(type, "CA_NHAN")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getCaNhanTienThuHoiTrongQuaTrinhThanhTra();
				}
			} else if (StringUtils.equals(type, "TO_CHUC")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getToChucTienThuHoiTrongQuaTrinhThanhTra();
				}
			} else if (StringUtils.equals(type, "TONG_SO")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getCaNhanTienThuHoiTrongQuaTrinhThanhTra() + doiTuong.getToChucTienThuHoiTrongQuaTrinhThanhTra();
				}
			} 
			return tongDonVi;

		}).mapToLong(Long::longValue).sum());
		
		return tongSoDon;
	}
	
	public Long getCacDangViPhamVeDat(BooleanExpression predAll, String type, CuocThanhTraRepository cuocThanhTraRepo) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongSoDon = 0L;
	
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		tongSoDon = Long.valueOf(cuocThanhTras.stream().map(elem -> {			
			Long tongDonVi = 0L;
			
			if (StringUtils.equals(type, "DAT_LAN_CHIEM")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getDatLanChiemKienNghiXuLyVeDat();
				}
			} else if (StringUtils.equals(type, "GIAO_DAT_CAP_DAT_SAI_DOI_TUONG")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getGiaoCapDatSaiDoiTuongQDKienNghiXuLyVeDat();
				}
			} else if (StringUtils.equals(type, "CAP_BAN_GIAO_DAT_TRAI_THAM_QUYEN")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getCapBanDatTraiThamQuyenKienNghiXuLyVeDat();
				}
			} else if (StringUtils.equals(type, "CAP_GCN_QSDD_SAI")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getCapGCNQSDDatSaiKienNghiXuLyVeDat();
				} 
			} else if (StringUtils.equals(type, "CHUYEN_NHUONG_CHO_THUE_KHONG_DUNG_QUY_DINH")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getcNChoThueKhongDungQDKienNghiXuLyVeDat();
				}
			} else if (StringUtils.equals(type, "SU_DUNG_DAT_KHONG_DUNG_MUC_DICH")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getsDDatKhongDungMDKienNghiXuLyVeDat();
				}
			} else if (StringUtils.equals(type, "BO_HOANG_HOA")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getBoHoanHoaKienNghiXuLyVeDat();
				}
			} else if (StringUtils.equals(type, "VI_PHAM_KHAC")) {
				for (DoiTuongViPham doiTuong : elem.getListDoiTuongViPham()) {
					tongDonVi += doiTuong.getSaiPhamKhacKienNghiXuLyVeDat();
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
    