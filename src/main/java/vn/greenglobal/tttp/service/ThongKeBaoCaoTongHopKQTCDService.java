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

import vn.greenglobal.tttp.enums.LoaiNguoiDungDonEnum;
import vn.greenglobal.tttp.enums.LoaiTiepDanEnum;
import vn.greenglobal.tttp.enums.PhanLoaiDonCongDanEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.LinhVucDonThuRepository;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class ThongKeBaoCaoTongHopKQTCDService {
	@Autowired
	private LinhVucDonThuRepository lichVuDonThuRepo;

	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepository;

	@Autowired
	private DonRepository donRepo;

	@Autowired
	private SoTiepCongDanRepository soTiepCongDanRepository;
	
	@Autowired
	private DonCongDanRepository donCongDanRepo;
	
	BooleanExpression baseTCD = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false);
	BooleanExpression baseDonCongDan = QDon_CongDan.don_CongDan.daXoa.eq(false);
	BooleanExpression baseDon = QDon.don.daXoa.eq(false);
	
	public Predicate predicateFindAllTCD(String tuNgay, String denNgay, Long donViId) { 
		BooleanExpression predAll = baseTCD;
		
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
		
		if (donViId != null && donViId > 0) { 
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(donViId)
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(donViId))
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(donViId)));
		}
		
		return predAll;
	}
	
	public Long getTongSoLuocTiepCongDan(BooleanExpression predAll, LoaiTiepDanEnum loaiTiepCongDan, Long donViId) { 
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		
		if (loaiTiepCongDan != null) {
			predAll = predAll
					.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(loaiTiepCongDan));
		}
		if (donViId != null && donViId > 0) { 
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(donViId)
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(donViId))
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(donViId)));
		}
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		tongSo = Long.valueOf(soTiepCongDans.size());

		soTiepCongDans.forEach(st -> {
			System.out.println("st " +st.getId() + " donViId " +donViId);
			System.out.println("don vi tiep " +st.getDonViTiepDan().getId());
		});
		
		return tongSo;
	}
	
	public Long getTongSoNguoiDungTenTiepCongDan(BooleanExpression predAll, LoaiTiepDanEnum loaiTiepCongDan, Long donViId, boolean isCaNhanVaToChuc, 
			boolean isDoanDongNguoi) { 
		Long tongSo = 0L;
		BooleanExpression donCongDanQuery = baseDonCongDan;
		
		System.out.println("--------------------------------------------------------" +donViId);
		if (loaiTiepCongDan != null) {
			predAll = predAll
					.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(loaiTiepCongDan));
		}
		if (donViId != null && donViId > 0) { 
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(donViId)
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(donViId))
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(donViId)));
		}
		if (isCaNhanVaToChuc) {
			System.out.println("ca nhan to chuc");
			List<Don> dons = new ArrayList<Don>();
			LoaiNguoiDungDonEnum caNhan = LoaiNguoiDungDonEnum.CA_NHAN;
			LoaiNguoiDungDonEnum toChuc = LoaiNguoiDungDonEnum.CO_QUAN_TO_CHUC;
			BooleanExpression donQuery = baseDon;
			
			donQuery = donQuery.and(QDon.don.loaiNguoiDungDon.eq(caNhan)
					.or(QDon.don.loaiNguoiDungDon.eq(toChuc)));
			dons.addAll((List<Don>) donRepo.findAll(donQuery));
			
			dons.forEach(d -> {
				System.out.println("don " +d.getId());
				System.out.println("loai don " +d.getLoaiDon());
			});
			
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		if (isDoanDongNguoi) {
			List<Don> dons = new ArrayList<Don>();
			LoaiNguoiDungDonEnum doanDongNguoi = LoaiNguoiDungDonEnum.DOAN_DONG_NGUOI;
			BooleanExpression donQuery = baseDon;
			
			donQuery = donQuery.and(QDon.don.loaiNguoiDungDon.eq(doanDongNguoi));
			dons.addAll((List<Don>) donRepo.findAll(donQuery));
			
			dons.forEach(d -> {
				System.out.println("don 1 " +d.getId());
				System.out.println("loai don 2 " +d.getLoaiDon());
			});
			
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		
		List<Don> dons = new ArrayList<Don>();
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		dons.addAll(soTiepCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));
		dons.forEach(d -> {
			System.out.println("don 2 " +d.getId());
			System.out.println("loai don 2 " +d.getLoaiDon());
		});
		
		PhanLoaiDonCongDanEnum nguoiDungDon = PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON;
		donCongDanQuery = donCongDanQuery.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.eq(nguoiDungDon));
		donCongDanQuery = donCongDanQuery.and(QDon_CongDan.don_CongDan.don.in(dons));
		
		donCongDans.addAll((List<Don_CongDan>) donCongDanRepo.findAll(donCongDanQuery));
		donCongDans.forEach(d -> {
			System.out.println("donCongDan " +d.getId());
			System.out.println("donCongDan ten " +d.getHoVaTen());
			System.out.println("donCongDan phan loai " +d.getPhanLoaiCongDan());
		});
		System.out.println("donCongDans " +donCongDans.size());
		tongSo = Long.valueOf(donCongDans.size());
		return tongSo;
	}
	
	public Long getTongSoDoanDongNguoiTiepCongDan(BooleanExpression predAll, LoaiTiepDanEnum loaiTiepCongDan, Long donViId, boolean isDoanDongNguoi) { 
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		
		if (loaiTiepCongDan != null) {
			predAll = predAll
					.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(loaiTiepCongDan));
		}
		if (donViId != null && donViId > 0) { 
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(donViId)
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(donViId))
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(donViId)));
		}
		if (isDoanDongNguoi) {
			List<Don> dons = new ArrayList<Don>();
			LoaiNguoiDungDonEnum doanDongNguoi = LoaiNguoiDungDonEnum.DOAN_DONG_NGUOI;
			BooleanExpression donQuery = baseDon;
			
			donQuery = donQuery.and(QDon.don.loaiNguoiDungDon.eq(doanDongNguoi));
			dons.addAll((List<Don>) donRepo.findAll(donQuery));
			
			dons.forEach(d -> {
				System.out.println("DDN don 1 " +d.getId());
				System.out.println("DDN loai don 2 " +d.getLoaiDon());
			});
			
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		tongSo = Long.valueOf(soTiepCongDans.size());

		soTiepCongDans.forEach(st -> {
			System.out.println("DDN " +st.getId() + " donViId " +donViId);
			System.out.println("DDN don vi tiep " +st.getDonViTiepDan().getId());
		});
		
		return tongSo;
	}
}
