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

import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiNguoiDungDonEnum;
import vn.greenglobal.tttp.enums.LoaiTiepDanEnum;
import vn.greenglobal.tttp.enums.PhanLoaiDonCongDanEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
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
	
	public Long getTongSoLuocTiepCongDanThuongXuyen(BooleanExpression predAll, Long donViId) { 
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		
		predAll = predAll
				.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.THUONG_XUYEN));
		
		if (donViId != null && donViId > 0) { 
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(donViId)
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(donViId))
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(donViId)));
		}
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		tongSo = Long.valueOf(soTiepCongDans.size());
		return tongSo;
	}
	
	public Long getTongSoNguoiDungTenTiepCongDanThuongXuyen(BooleanExpression predAll, Long donViId, boolean isCaNhanVaToChuc, 
			boolean isDoanDongNguoi) { 
		Long tongSo = 0L;
		BooleanExpression donCongDanQuery = baseDonCongDan;
		
		predAll = predAll
				.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.THUONG_XUYEN));
		if (donViId != null && donViId > 0) { 
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(donViId)
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(donViId))
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(donViId)));
		}
		if (isCaNhanVaToChuc) {
			List<Don> dons = new ArrayList<Don>();
			LoaiNguoiDungDonEnum caNhan = LoaiNguoiDungDonEnum.CA_NHAN;
			LoaiNguoiDungDonEnum toChuc = LoaiNguoiDungDonEnum.CO_QUAN_TO_CHUC;
			BooleanExpression donQuery = baseDon;
			
			donQuery = donQuery.and(QDon.don.loaiNguoiDungDon.eq(caNhan)
					.or(QDon.don.loaiNguoiDungDon.eq(toChuc)));
			dons.addAll((List<Don>) donRepo.findAll(donQuery));
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		if (isDoanDongNguoi) {
			List<Don> dons = new ArrayList<Don>();
			LoaiNguoiDungDonEnum doanDongNguoi = LoaiNguoiDungDonEnum.DOAN_DONG_NGUOI;
			BooleanExpression donQuery = baseDon;
			
			donQuery = donQuery.and(QDon.don.loaiNguoiDungDon.eq(doanDongNguoi));
			dons.addAll((List<Don>) donRepo.findAll(donQuery));
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		
		List<Don> dons = new ArrayList<Don>();
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		dons.addAll(soTiepCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));
		PhanLoaiDonCongDanEnum nguoiDungDon = PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON;
		donCongDanQuery = donCongDanQuery.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.eq(nguoiDungDon));
		donCongDanQuery = donCongDanQuery.and(QDon_CongDan.don_CongDan.don.in(dons));
		
		donCongDans.addAll((List<Don_CongDan>) donCongDanRepo.findAll(donCongDanQuery));
		tongSo = Long.valueOf(donCongDans.size());
		return tongSo;
	}
	
	public Long getTongSoDoanDongNguoiTiepCongDanThuongXuyen(BooleanExpression predAll, Long donViId, boolean isDoanDongNguoi) { 
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		
		predAll = predAll
				.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.THUONG_XUYEN));
		
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
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		tongSo = Long.valueOf(soTiepCongDans.size());
		return tongSo;
	}
	
	public Long getTongSoLuocTiepCongDanDinhKyDotXuat(BooleanExpression predAll, Long donViId) { 
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		
		predAll = predAll
				.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DINH_KY)
						.or(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DOT_XUAT)));
		
		if (donViId != null && donViId > 0) { 
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(donViId)
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(donViId))
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(donViId)));
		}
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		tongSo = Long.valueOf(soTiepCongDans.size());
		return tongSo;
	}
	
	public Long getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(BooleanExpression predAll, Long donViId, boolean isCaNhanVaToChuc, 
			boolean isDoanDongNguoi) { 
		Long tongSo = 0L;
		BooleanExpression donCongDanQuery = baseDonCongDan;
		
		predAll = predAll
				.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DINH_KY)
						.or(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DOT_XUAT)));
		
		if (donViId != null && donViId > 0) { 
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(donViId)
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(donViId))
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(donViId)));
		}
		if (isCaNhanVaToChuc) {
			List<Don> dons = new ArrayList<Don>();
			LoaiNguoiDungDonEnum caNhan = LoaiNguoiDungDonEnum.CA_NHAN;
			LoaiNguoiDungDonEnum toChuc = LoaiNguoiDungDonEnum.CO_QUAN_TO_CHUC;
			BooleanExpression donQuery = baseDon;
			
			donQuery = donQuery.and(QDon.don.loaiNguoiDungDon.eq(caNhan)
					.or(QDon.don.loaiNguoiDungDon.eq(toChuc)));
			dons.addAll((List<Don>) donRepo.findAll(donQuery));
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		if (isDoanDongNguoi) {
			List<Don> dons = new ArrayList<Don>();
			LoaiNguoiDungDonEnum doanDongNguoi = LoaiNguoiDungDonEnum.DOAN_DONG_NGUOI;
			BooleanExpression donQuery = baseDon;
			
			donQuery = donQuery.and(QDon.don.loaiNguoiDungDon.eq(doanDongNguoi));
			dons.addAll((List<Don>) donRepo.findAll(donQuery));
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		
		List<Don> dons = new ArrayList<Don>();
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		dons.addAll(soTiepCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList()));

		PhanLoaiDonCongDanEnum nguoiDungDon = PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON;
		donCongDanQuery = donCongDanQuery.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.eq(nguoiDungDon));
		donCongDanQuery = donCongDanQuery.and(QDon_CongDan.don_CongDan.don.in(dons));
		
		donCongDans.addAll((List<Don_CongDan>) donCongDanRepo.findAll(donCongDanQuery));
		tongSo = Long.valueOf(donCongDans.size());
		return tongSo;
	}
	
	public Long getTongSoDoanDongNguoiTiepCongDanDinhKyDotXuat(BooleanExpression predAll, Long donViId, boolean isDoanDongNguoi) { 
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		
		predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DINH_KY)
						.or(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DOT_XUAT)));
		
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
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));
		tongSo = Long.valueOf(soTiepCongDans.size());
		return tongSo;
	}
	
	public Long getTongSoVuViecTiepCongDanDonKienNghiPhanAnh(BooleanExpression predAll, Long donViId) { 
		Long tongSo = 0L;
		List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
		List<Long> soVuViecs = new ArrayList<Long>();
		
		predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.loaiDon.eq(LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH));
		
		if (donViId != null && donViId > 0) { 
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(donViId)
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(donViId))
					.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(donViId)));
		}
		
		soTiepCongDans.addAll((List<SoTiepCongDan>) soTiepCongDanRepository.findAll(predAll));		
		soTiepCongDans.forEach(st -> { 
			System.out.println("st " +st.getId() + " don " +st.getDon().getId());
		});
		
		tongSo = Long.valueOf(soTiepCongDans.stream().map(tcd -> {
			Long tongSoVuViec = 0L;
			Don don = tcd.getDon();
			if (don.getThongTinGiaiQuyetDon() != null) {
				System.out.println("ttgq " + donViId);
				ThongTinGiaiQuyetDon ttgqd = don.getThongTinGiaiQuyetDon();
				tongSoVuViec += ttgqd.getSoVuGiaiQuyetKhieuNai();
			} else {
				tongSoVuViec += 1;
			}
			return tongSoVuViec;
		}).distinct().mapToLong(Long::longValue).sum());
		
		System.out.println("tongSo " +tongSo);
		System.out.println("soVuViecs " +soVuViecs.size());
		return tongSo;
	}
}
