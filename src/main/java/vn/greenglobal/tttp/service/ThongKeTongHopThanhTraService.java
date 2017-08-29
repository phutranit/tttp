package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import vn.greenglobal.tttp.model.QXuLyDon;
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
				
				if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_NGAY)) {
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
	public Long getCuocThanhTraTheoLinhVuc(BooleanExpression predAll,Long donViXuLyXLD ,LinhVucThanhTraEnum linhVucThanhTraEnum,CuocThanhTraRepository cuocThanhTraRepo) {
		
		Long tongSoDon = 0L;
		
		predAll = base.
				and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(donViXuLyXLD)).
				and(QCuocThanhTra.cuocThanhTra.linhVucThanhTra.eq(linhVucThanhTraEnum));
		
		tongSoDon = Long.valueOf(((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll)).size());
		
		return tongSoDon; 
	}
	
	// 4,5
	public Long getCuocThanhTraTheoHinhThuc(BooleanExpression predAll,Long donViXuLyXLD ,LinhVucThanhTraEnum linhVucThanhTraEnum, CuocThanhTraRepository cuocThanhTraRepo,HinhThucThanhTraEnum hinhThucThanhTraEnum) {

		Long tongSoDon = 0L;
		
		if (StringUtils.equals(hinhThucThanhTraEnum.name(), HinhThucThanhTraEnum.THEO_KE_HOACH.name())) {
			
			predAll = base.
					and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(donViXuLyXLD)).
					and(QCuocThanhTra.cuocThanhTra.linhVucThanhTra.eq(linhVucThanhTraEnum)).
					and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.isNotNull()).
					and(QCuocThanhTra.cuocThanhTra.hinhThucThanhTra.eq(HinhThucThanhTraEnum.THEO_KE_HOACH));
		} else if (StringUtils.equals(hinhThucThanhTraEnum.name(), HinhThucThanhTraEnum.DOT_XUAT.name())) {
			
			predAll = base.
					and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(donViXuLyXLD)).
					and(QCuocThanhTra.cuocThanhTra.linhVucThanhTra.eq(linhVucThanhTraEnum)).
					and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.isNull()).
					and(QCuocThanhTra.cuocThanhTra.hinhThucThanhTra.eq(HinhThucThanhTraEnum.DOT_XUAT));
		}
		
		tongSoDon = Long.valueOf(((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll)).size());
		
		return tongSoDon;
	}
	 
	// 6,7
	public Long getCuocThanhTraTheoTienDo(BooleanExpression predAll,Long donViXuLyXLD ,LinhVucThanhTraEnum linhVucThanhTraEnum ,CuocThanhTraRepository cuocThanhTraRepo,TienDoThanhTraEnum tienDoThanhTraEnum) {
		
		Long tongSoDon = 0L;
			
		predAll = base.
				and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(donViXuLyXLD)).
				and(QCuocThanhTra.cuocThanhTra.linhVucThanhTra.eq(linhVucThanhTraEnum)).
				and(QCuocThanhTra.cuocThanhTra.tienDoThanhTra.eq(tienDoThanhTraEnum));
		
		tongSoDon = Long.valueOf(((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll)).size());
		
		return tongSoDon;
	}
	
	// 8
	public Long getSoDonViDuocThanhTra(BooleanExpression predAll,Long donViXuLyXLD ,LinhVucThanhTraEnum linhVucThanhTraEnum ,CuocThanhTraRepository cuocThanhTraRepo,Long doiTuongId) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		final Long[] tongGiaTri = new Long[0];
		
		predAll = base.				
				and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(donViXuLyXLD)).
				and(QCuocThanhTra.cuocThanhTra.linhVucThanhTra.eq(linhVucThanhTraEnum));
	
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		if (cuocThanhTras.size() > 0) {
			
			cuocThanhTras.forEach(elem->{
				
				if (elem.getDoiTuongThanhTra() != null) {
					tongGiaTri[0] = tongGiaTri[0] + 1;
				}
				
				if (elem.getDoiTuongThanhTraLienQuan() != null) {
					
					String[] array = elem.getDoiTuongThanhTraLienQuan().split(";");
					tongGiaTri[0] = tongGiaTri[0] + array.length;
				}
			});
		}
		
		return tongGiaTri[0];
	}
	
	
	// 9
	public Long getSoDonViCoViPham(BooleanExpression predAll,Long donViXuLyXLD ,LinhVucThanhTraEnum linhVucThanhTraEnum ,CuocThanhTraRepository cuocThanhTraRepo) {
		
		Long tongSoDon = 0L;
		
		predAll = base.
				and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(donViXuLyXLD)).
				and(QCuocThanhTra.cuocThanhTra.linhVucThanhTra.eq(linhVucThanhTraEnum)).
				and(QCuocThanhTra.cuocThanhTra.viPham.eq(true));
		
		tongSoDon = Long.valueOf(((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll)).size());
		
		return tongSoDon;
	}
	
	public Long getTienDatTheoViPham(BooleanExpression predAll,Long donViXuLyXLD ,LinhVucThanhTraEnum linhVucThanhTraEnum ,CuocThanhTraRepository cuocThanhTraRepo ,String type,String typeValue) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		final Long[] tongGiaTri = new Long[0];
		
		predAll = base.
				and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(donViXuLyXLD)).
				and(QCuocThanhTra.cuocThanhTra.linhVucThanhTra.eq(linhVucThanhTraEnum));
		
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		if (cuocThanhTras.size() > 0) {

			cuocThanhTras.forEach(elem->{
				
				if (StringUtils.equals(type, "TONG_VI_PHAM")) {

					if (StringUtils.equals(typeValue, "DAT")) {
						tongGiaTri[0] = tongGiaTri[0] + elem.getDatThuViPham();
					} else if (StringUtils.equals(typeValue, "TIEN")) {
						tongGiaTri[0] = tongGiaTri[0] + elem.getTienThuViPham();
					}
					
				} else if (StringUtils.equals(type, "KIEN_NGHI_THU_HOI")) {

					if (StringUtils.equals(typeValue, "DAT")) {
						tongGiaTri[0] = tongGiaTri[0] + elem.getDatKienNghiThuHoi();
					} else if (StringUtils.equals(typeValue, "TIEN")) {
						tongGiaTri[0] = tongGiaTri[0] + elem.getTienKienNghiThuHoi();
					}
					
				} else if (StringUtils.equals(type, "KIEN_NGHI_KHAC")) {

					if (StringUtils.equals(typeValue, "DAT")) {
						tongGiaTri[0] = tongGiaTri[0] + elem.getDatTraKienNghiKhac();
					} else if (StringUtils.equals(typeValue, "TIEN")) {
						tongGiaTri[0] = tongGiaTri[0] + elem.getTienTraKienNghiKhac();
					}
				}
			});	
		}
	
		return tongGiaTri[0];
	}
	
	public Long getBlockNoiDungViPham(BooleanExpression predAll,Long donViXuLyXLD ,LinhVucThanhTraEnum linhVucThanhTraEnum ,CuocThanhTraRepository cuocThanhTraRepo ,String type) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		final Long[] tongSoDon = new Long[0];
		
		predAll = base.
				and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(donViXuLyXLD)).
				and(QCuocThanhTra.cuocThanhTra.linhVucThanhTra.eq(linhVucThanhTraEnum)).
				and(QCuocThanhTra.cuocThanhTra.viPham.isTrue());
		
		
	
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		if (cuocThanhTras.size() > 0) {

			cuocThanhTras.forEach(elem->{
				
				if (StringUtils.equals(type, "TO_CHUC")) {
					
					tongSoDon[0] = tongSoDon[0] + elem.getToChucXuLyHanhChinhViPham();
				} else if (StringUtils.equals(type, "CA_NHAN")) {
					
					tongSoDon[0] = tongSoDon[0] + elem.getCaNhanXuLyHanhChinhViPham();
				}
			});
		}
		
		return tongSoDon[0];
	}
	
	public Long getBlockGiaoCoQuanDieuTra(BooleanExpression predAll,Long donViXuLyXLD ,LinhVucThanhTraEnum linhVucThanhTraEnum ,CuocThanhTraRepository cuocThanhTraRepo ,String type) {
		
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		final Long[] tongGiaTri = new Long[0];
		
		predAll = base.
				and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(donViXuLyXLD)).
				and(QCuocThanhTra.cuocThanhTra.linhVucThanhTra.eq(linhVucThanhTraEnum)).
				and(QCuocThanhTra.cuocThanhTra.chuyenCoQuanDieuTra.isTrue());
		
		cuocThanhTras.addAll((List<CuocThanhTra>)cuocThanhTraRepo.findAll(predAll));
		
		if (cuocThanhTras.size() > 0) {

			cuocThanhTras.forEach(elem->{
				
				if (StringUtils.equals(type, "VU")) {
					
					tongGiaTri[0] = tongGiaTri[0] + elem.getSoVuDieuTra();
				} else if (StringUtils.equals(type, "DOI_TUONG")) {
					
					tongGiaTri[0] = tongGiaTri[0] + elem.getSoDoiTuongDieuTra();
				}
			});
		}
		
		return tongGiaTri[0];
	}
	
}
    