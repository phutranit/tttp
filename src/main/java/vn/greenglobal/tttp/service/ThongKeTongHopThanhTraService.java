package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.HinhThucThanhTraEnum;
import vn.greenglobal.tttp.enums.LinhVucThanhTraEnum;
import vn.greenglobal.tttp.enums.ThongKeBaoCaoLoaiKyEnum;
import vn.greenglobal.tttp.enums.TienDoThanhTraEnum;
import vn.greenglobal.tttp.model.CuocThanhTra;
import vn.greenglobal.tttp.model.QCuocThanhTra;
import vn.greenglobal.tttp.repository.CuocThanhTraRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class ThongKeTongHopThanhTraService {

	@Autowired
	private CuocThanhTraRepository cuocThanhTraRepo;
	
	BooleanExpression base = QCuocThanhTra.cuocThanhTra.daXoa.eq(false);
	
	public Predicate predicateFindAllCuocThanhTra(String loaiKy, Integer quy, Integer year, Integer month, String tuNgay, String denNgay) {
		
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
	
	// 8
	public Long getSoDonViDuocThanhTra(BooleanExpression predAll, CuocThanhTraRepository cuocThanhTraRepo) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongGiaTri = 0L;
		
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
			
		tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> {

			Long tongDonVi = 1L;

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

			Long tongDonVi = 1L;
			
			if (StringUtils.equals(type, "TONG_VI_PHAM")) {

				if (StringUtils.equals(typeValue, "DAT")) {
					tongDonVi += elem.getDatThuViPham();
				} else if (StringUtils.equals(typeValue, "TIEN")) {
					tongDonVi += elem.getTienThuViPham();
				}
				
			} else if (StringUtils.equals(type, "KIEN_NGHI_THU_HOI")) {

				if (StringUtils.equals(typeValue, "DAT")) {
					tongDonVi += elem.getDatKienNghiThuHoi();
				} else if (StringUtils.equals(typeValue, "TIEN")) {
					tongDonVi += elem.getTienKienNghiThuHoi();
				}
				
			} else if (StringUtils.equals(type, "KIEN_NGHI_KHAC")) {

				if (StringUtils.equals(typeValue, "DAT")) {
					tongDonVi += elem.getDatTraKienNghiKhac();
				} else if (StringUtils.equals(typeValue, "TIEN")) {
					tongDonVi += elem.getTienTraKienNghiKhac();
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

			Long tongDonVi = 1L;
			
			if (StringUtils.equals(type, "TO_CHUC")) {

				tongDonVi += elem.getToChucXuLyHanhChinhViPham();	
			} else if (StringUtils.equals(type, "CA_NHAN")) {
				
				tongDonVi += elem.getCaNhanXuLyHanhChinhViPham();
			}
			return tongDonVi;

		}).mapToLong(Long::longValue).sum());
		
		return tongSoDon;
	}
	
	public Predicate predicateFindCuocThanhTraChuyenCoQuanDieuTra(BooleanExpression predAll, CuocThanhTraRepository cuocThanhTraRepo) {
		
		predAll = predAll.and(QCuocThanhTra.cuocThanhTra.chuyenCoQuanDieuTra.isTrue());
		
		return predAll;
	}
	
	public Long getKienNghiXuLyCCQDT(BooleanExpression predAll, String type,CuocThanhTraRepository cuocThanhTraRepo ) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		Long tongGiaTri = 0L;
		
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		tongGiaTri = Long.valueOf(cuocThanhTras.stream().map(elem -> {

			Long tongDonVi = 1L;
			
			if (StringUtils.equals(type, "VU")) {

				tongDonVi += elem.getSoVuDieuTra();	
			} else if (StringUtils.equals(type, "DOI_TUONG")) {
				
				tongDonVi += elem.getSoDoiTuongDieuTra();
			}
			return tongDonVi;

		}).mapToLong(Long::longValue).sum());
		
		return tongGiaTri;
	}
	
}
    