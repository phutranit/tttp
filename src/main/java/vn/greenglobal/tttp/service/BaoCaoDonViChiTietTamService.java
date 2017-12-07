package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.HinhThucThanhTraEnum;
import vn.greenglobal.tttp.enums.KyBaoCaoTongHopEnum;
import vn.greenglobal.tttp.enums.LinhVucThanhTraEnum;
import vn.greenglobal.tttp.enums.LoaiBaoCaoTongHopEnum;
import vn.greenglobal.tttp.enums.TienDoThanhTraEnum;
import vn.greenglobal.tttp.enums.TrangThaiBaoCaoDonViEnum;
import vn.greenglobal.tttp.model.BaoCaoDonViChiTiet;
import vn.greenglobal.tttp.model.BaoCaoDonViChiTietTam;
import vn.greenglobal.tttp.model.BaoCaoTongHop;
import vn.greenglobal.tttp.model.CuocThanhTra;
import vn.greenglobal.tttp.model.QBaoCaoDonViChiTiet;
import vn.greenglobal.tttp.model.QBaoCaoDonViChiTietTam;
import vn.greenglobal.tttp.model.QCuocThanhTra;
import vn.greenglobal.tttp.model.medial.Medial_ThanhTraHanhChinh;
import vn.greenglobal.tttp.repository.BaoCaoDonViChiTietRepository;
import vn.greenglobal.tttp.repository.BaoCaoDonViChiTietTamRepository;
import vn.greenglobal.tttp.repository.CuocThanhTraRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class BaoCaoDonViChiTietTamService {
	
	@Autowired
	private BaoCaoDonViChiTietTamRepository baoCaoDonViChiTietTamRepository;
	
	@Autowired
	private BaoCaoDonViChiTietRepository baoCaoDonViChiTietRepo;
	
	@Autowired
	private ThongKeTongHopThanhTraService thongKeTongHopThanhTraService;
	
	@Autowired
	private CuocThanhTraRepository cuocThanhTraRepo;

	BooleanExpression base = QBaoCaoDonViChiTietTam.baoCaoDonViChiTietTam.daXoa.eq(false);

	
	public Predicate predicateFindAll(Long baoCaoDonViChiTietChaId) {
		BooleanExpression predAll = base;		
		predAll = predAll.and(QBaoCaoDonViChiTietTam.baoCaoDonViChiTietTam.baoCaoDonViChiTiet.cha.id.eq(baoCaoDonViChiTietChaId));
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.id.eq(id));
	}
	
	public BaoCaoDonViChiTietTam save(BaoCaoDonViChiTietTam obj, Long congChucId) {
		return Utils.save(baoCaoDonViChiTietTamRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(BaoCaoDonViChiTietTam obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(baoCaoDonViChiTietTamRepository, obj, congChucId, eass, status);		
	}

	public String getDataFromDB(BaoCaoDonViChiTietTam baoCao) {
		LoaiBaoCaoTongHopEnum loaiBaoCao = baoCao.getBaoCaoDonViChiTiet().getLoaiBaoCao();
		if (LoaiBaoCaoTongHopEnum.CONG_TAC_XAY_DUNG_LUC_LUONG_THANH_TRA.equals(loaiBaoCao)
				|| LoaiBaoCaoTongHopEnum.KET_QUA_CHU_YEU_VE_CONG_TAC_PHONG_CHONG_THAM_NHUNG.equals(loaiBaoCao)
				|| LoaiBaoCaoTongHopEnum.SO_LIEU_KE_KHAI_XAC_MINH_TAI_SAN.equals(loaiBaoCao)) {
			return baoCao.getSoLieuBaoCao();
		} else {
			if (baoCao.getBaoCaoDonViChiTiet().getDonVi() == null) {
				return baoCao.getSoLieuBaoCao();
			} else {
				if (LoaiBaoCaoTongHopEnum.KET_QUA_THANH_TRA_HANH_CHINH.equals(loaiBaoCao)) {
					return getDataKetQuaThanhTraHanhChinh(baoCao);
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_THANH_TRA_TRONG_LINH_VUC_DAU_TU_XAY_DUNG_CO_BAN.equals(loaiBaoCao)) {
					return "";
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_THANH_TRA_TRONG_LINH_VUC_TAI_CHINH_NGAN_SACH.equals(loaiBaoCao)) {
					return "";
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_THANH_TRA_TRONG_LINH_VUC_TAI_CHINH_NGAN_SACH.equals(loaiBaoCao)) {
					return "";
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_THANH_TRA_TRONG_LINH_VUC_DAT_DAI.equals(loaiBaoCao)) {
					return "";
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_THANH_TRA_LAI.equals(loaiBaoCao)) {
					return "";
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_THANH_TRA_KIEM_TRA_CHUYEN_NGANH.equals(loaiBaoCao)) {
					return "";
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_PHAT_HIEN_XU_LY_THAM_NHUNG_QUA_THANH_TRA.equals(loaiBaoCao)) {
					return "";
				} else if (LoaiBaoCaoTongHopEnum.CONG_TAC_QUAN_LY_NHA_NUOC_VE_THANH_TRA.equals(loaiBaoCao)) {
					return "";
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_TIEP_CONG_DAN.equals(loaiBaoCao)) {
					return "";
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_XU_LY_DON_KHIEU_NAI_TO_CAO.equals(loaiBaoCao)) {
					return "";
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_GIAI_QUYET_DON_KHIEU_NAI.equals(loaiBaoCao)) {
					return "";
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_GIAI_QUYET_DON_TO_CAO.equals(loaiBaoCao)) {
					return "";
				} else if (LoaiBaoCaoTongHopEnum.CONG_TAC_QUAN_LY_NHA_NUOC_VE_KHIEU_NAI_TO_CAO.equals(loaiBaoCao)) {
					return "";
				} 
			}
		}		
		return "";
	}
	
	public LocalDateTime getNgayKetThucBaoCao(BaoCaoDonViChiTiet baoCaoDonViChiTiet) {
		BaoCaoTongHop baoCaoTongHop = baoCaoDonViChiTiet.getBaoCaoDonVi().getBaoCaoTongHop();
		LocalDateTime now = LocalDateTime.now();
		if (now.isAfter(baoCaoTongHop.getNgayKetThucBC())) {
			return baoCaoTongHop.getNgayKetThucBC();
		}
		LocalDateTime timeKetThuc =
			    LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 0, 0, 0, 0);
		return timeKetThuc;
	}
	
	public LocalDateTime getNgayBatDauBaoCao(BaoCaoDonViChiTiet baoCaoDonViChiTiet) {
		BaoCaoTongHop baoCaoTongHop = baoCaoDonViChiTiet.getBaoCaoDonVi().getBaoCaoTongHop();
		if (baoCaoTongHop.getKyBaoCao().equals(KyBaoCaoTongHopEnum.CHIN_THANG) 
				|| baoCaoTongHop.getKyBaoCao().equals(KyBaoCaoTongHopEnum.SAU_THANG)
				|| baoCaoTongHop.getKyBaoCao().equals(KyBaoCaoTongHopEnum.NAM)
				|| (baoCaoTongHop.getKyBaoCao().equals(KyBaoCaoTongHopEnum.THEO_QUY) && baoCaoTongHop.getQuyBaoCao() == 1)
				|| (baoCaoTongHop.getKyBaoCao().equals(KyBaoCaoTongHopEnum.THEO_THANG) && baoCaoTongHop.getThangBaoCao() == 1)) {
			BooleanExpression pred = QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.daXoa.eq(false)
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.cha.isNull())
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.trangThaiBaoCao.eq(TrangThaiBaoCaoDonViEnum.DA_GUI))
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.donVi.eq(baoCaoDonViChiTiet.getBaoCaoDonVi().getDonVi()))
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.baoCaoTongHop.kyBaoCao.eq(KyBaoCaoTongHopEnum.NAM))
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.loaiBaoCao.eq(baoCaoDonViChiTiet.getLoaiBaoCao()))
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.baoCaoTongHop.namBaoCao.eq(baoCaoTongHop.getNamBaoCao()-1));
			BaoCaoDonViChiTiet baoCaoKyTruoc = baoCaoDonViChiTietRepo.findOne(pred);
			if (baoCaoKyTruoc != null) {
				return baoCaoKyTruoc.getNgayNop();
			}
		} else if (baoCaoTongHop.getKyBaoCao().equals(KyBaoCaoTongHopEnum.THEO_QUY) && baoCaoTongHop.getQuyBaoCao() != 1) {
			BooleanExpression pred = QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.daXoa.eq(false)
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.cha.isNull())
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.trangThaiBaoCao.eq(TrangThaiBaoCaoDonViEnum.DA_GUI))
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.donVi.eq(baoCaoDonViChiTiet.getBaoCaoDonVi().getDonVi()))
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.loaiBaoCao.eq(baoCaoDonViChiTiet.getLoaiBaoCao()))
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.baoCaoTongHop.kyBaoCao.eq(KyBaoCaoTongHopEnum.THEO_QUY))
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.baoCaoTongHop.namBaoCao.eq(baoCaoTongHop.getNamBaoCao()))
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.baoCaoTongHop.quyBaoCao.eq(baoCaoTongHop.getQuyBaoCao()));
			BaoCaoDonViChiTiet baoCaoKyTruoc = baoCaoDonViChiTietRepo.findOne(pred);
			if (baoCaoKyTruoc != null) {
				return baoCaoKyTruoc.getNgayNop();
			}
		} else if (baoCaoTongHop.getKyBaoCao().equals(KyBaoCaoTongHopEnum.THEO_THANG) && baoCaoTongHop.getThangBaoCao() != 1) {
			BooleanExpression pred = QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.daXoa.eq(false)
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.cha.isNull())
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.trangThaiBaoCao.eq(TrangThaiBaoCaoDonViEnum.DA_GUI))
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.donVi.eq(baoCaoDonViChiTiet.getBaoCaoDonVi().getDonVi()))
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.loaiBaoCao.eq(baoCaoDonViChiTiet.getLoaiBaoCao()))
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.baoCaoTongHop.kyBaoCao.eq(KyBaoCaoTongHopEnum.THEO_THANG))
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.baoCaoTongHop.namBaoCao.eq(baoCaoTongHop.getNamBaoCao()))
					.and(QBaoCaoDonViChiTiet.baoCaoDonViChiTiet.baoCaoDonVi.baoCaoTongHop.quyBaoCao.eq(baoCaoTongHop.getThangBaoCao()));
			BaoCaoDonViChiTiet baoCaoKyTruoc = baoCaoDonViChiTietRepo.findOne(pred);
			if (baoCaoKyTruoc != null) {
				return baoCaoKyTruoc.getNgayNop();
			}
		}
		return baoCaoTongHop.getNgayBatDauBC();
	}
	
	public String getDataKetQuaThanhTraHanhChinh(BaoCaoDonViChiTietTam baoCao) {
		Medial_ThanhTraHanhChinh medial = Utils.json2Object(Medial_ThanhTraHanhChinh.class, baoCao.getSoLieuBaoCao());		
		BaoCaoTongHop baoCaoTongHop = baoCao.getBaoCaoDonViChiTiet().getBaoCaoDonVi().getBaoCaoTongHop();
		LocalDateTime ngayBatDau = getNgayBatDauBaoCao(baoCao.getBaoCaoDonViChiTiet().getCha());
		LocalDateTime ngayKetThuc = getNgayKetThucBaoCao(baoCao.getBaoCaoDonViChiTiet().getCha());
		String ngayBatDauStr = ngayBatDau.toString().concat("Z");
		String ngayKetThucStr = ngayKetThuc.toString().concat("Z");
		BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindAllCuocThanhTra("TUY_CHON", 0, baoCaoTongHop.getNamBaoCao(), 0, ngayBatDauStr, ngayKetThucStr);
		BooleanExpression predAllCuocThanhTraTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindAllCuocThanhTraTrongKy("TUY_CHON", 0, baoCaoTongHop.getNamBaoCao(), 0, ngayBatDauStr, ngayKetThucStr);
		BooleanExpression predAllCuocThanhTraKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindAllCuocThanhTraKyTruoc("TUY_CHON", 0, baoCaoTongHop.getNamBaoCao(), 0, ngayBatDauStr, ngayKetThucStr);
		
		BooleanExpression predAllCuocThanhTraCoQuan = predAllCuocThanhTra
				.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(baoCao.getBaoCaoDonViChiTiet().getDonVi()));

		// Get cuoc thanh tra theo linh vuc hanh chinh
		predAllCuocThanhTraCoQuan = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindCuocThanhTraTheoLinhVuc(predAllCuocThanhTraCoQuan, LinhVucThanhTraEnum.TAI_CHINH);

		// Dem so cuoc thanh tra
		Long tongSoCuocThanhTra = Long.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size());	
		
		// So cuoc thanh tra
		BooleanExpression predAllCuocThanhTraCoQuanTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindCuocThanhTraTheoLinhVuc(
						predAllCuocThanhTraTrongKy
								.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(baoCao.getBaoCaoDonViChiTiet().getDonVi())),
						LinhVucThanhTraEnum.TAI_CHINH);
		BooleanExpression predAllCuocThanhTraCoQuanKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindCuocThanhTraTheoLinhVuc(
						predAllCuocThanhTraKyTruoc
								.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(baoCao.getBaoCaoDonViChiTiet().getDonVi())),
						LinhVucThanhTraEnum.TAI_CHINH);

		Long tongSoCuocThanhTraTrongKy = Long.valueOf(
				((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanTrongKy)).size());
		Long tongSoCuocThanhTraKyTruoc = Long.valueOf(
				((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanKyTruoc)).size());
		
		// Theo ke hoach
		Long tongSoCuocThanhTraTheoKeHoach = thongKeTongHopThanhTraService.getCuocThanhTraTheoHinhThuc(
				predAllCuocThanhTraCoQuan, HinhThucThanhTraEnum.THEO_KE_HOACH, cuocThanhTraRepo);
		// Dot xuat
		Long tongSoCuocThanhTraDotXuat = thongKeTongHopThanhTraService.getCuocThanhTraTheoHinhThuc(
				predAllCuocThanhTraCoQuan, HinhThucThanhTraEnum.DOT_XUAT, cuocThanhTraRepo);
		
		// Dem so cuoc thanh tra theo tien do
		// Ket thuc thanh tra truc tiep
		Long tongSoCuocThanhTraKetThucTrucTiep = thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(
				predAllCuocThanhTraCoQuan, TienDoThanhTraEnum.KET_THUC_THANH_TRA_TRUC_TIEP, cuocThanhTraRepo);
		// Da ban hanh ket luan
		Long tongSoCuocThanhTraDaBanHanhKetLuan = thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(
				predAllCuocThanhTraCoQuan, TienDoThanhTraEnum.DA_BAN_HANH_KET_LUAN, cuocThanhTraRepo);
		
		// Dem so don vi duoc thanh tra
		Long tongSoDonViDuocThanhTra = thongKeTongHopThanhTraService
				.getSoDonViDuocThanhTra(predAllCuocThanhTraCoQuan, cuocThanhTraRepo);

		// Lay danh sach cuoc thanh tra co vi pham
		BooleanExpression predAllCuocThanhTraCoQuanViPham = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindCuocThanhTraCoViPham(predAllCuocThanhTraCoQuan, cuocThanhTraRepo);
		
		// Dem so don vi co vi pham
		Long tongSoDonViCoViPham = Long.valueOf(
				((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanViPham)).size());

		// Block noi dung vi pham
		Long tongViPhamTien = thongKeTongHopThanhTraService.getTienDatTheoViPham(
				predAllCuocThanhTraCoQuanViPham, "TONG_VI_PHAM", "TIEN", cuocThanhTraRepo);
		Long tongViPhamDat = thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
				"TONG_VI_PHAM", "DAT", cuocThanhTraRepo);

		Long tongKNTHTien = thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
				"KIEN_NGHI_THU_HOI", "TIEN", cuocThanhTraRepo);
		Long tongKNTHDat = thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
				"KIEN_NGHI_THU_HOI", "DAT", cuocThanhTraRepo);

		Long tongKNKTien = thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
				"KIEN_NGHI_KHAC", "TIEN", cuocThanhTraRepo);
		Long tongKNKDat = thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
				"KIEN_NGHI_KHAC", "DAT", cuocThanhTraRepo);
		
		// Kien nghi xu ly
		Long tongKNXLHanhChinhToChuc = thongKeTongHopThanhTraService
				.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuanViPham, "TO_CHUC", cuocThanhTraRepo);
		Long tongKNXLHanhChinhCaNhan = thongKeTongHopThanhTraService
				.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuanViPham, "CA_NHAN", cuocThanhTraRepo);

		// Lay danh sach cuoc thanh tra co vi pham
		BooleanExpression predAllCuocThanhTraChuyenCoQuanDieuTra = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindCuocThanhTraChuyenCoQuanDieuTra(predAllCuocThanhTraCoQuan, cuocThanhTraRepo);

		// Chuyen co quan dieu tra
		Long tongKNXLVu = thongKeTongHopThanhTraService
				.getKienNghiXuLyCCQDT(predAllCuocThanhTraChuyenCoQuanDieuTra, "VU", cuocThanhTraRepo);
		Long tongKNXLDoiTuong = thongKeTongHopThanhTraService
				.getKienNghiXuLyCCQDT(predAllCuocThanhTraChuyenCoQuanDieuTra, "DOI_TUONG", cuocThanhTraRepo);
		
		Long daThuTien = thongKeTongHopThanhTraService
				.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "TIEN");

		Long daThuDat = thongKeTongHopThanhTraService
				.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "DAT");
		
		medial.setTongSoCuocThanhTra(tongSoCuocThanhTra);
		medial.setKyTruocChuyenSang(tongSoCuocThanhTraKyTruoc);
		medial.setTrienKhaiTrongKyBaoCao(tongSoCuocThanhTraTrongKy);
		medial.setTheoKeHoach(tongSoCuocThanhTraTheoKeHoach);
		medial.setDotXuat(tongSoCuocThanhTraDotXuat);
		medial.setKetThucThanhTraTrucTiep(tongSoCuocThanhTraKetThucTrucTiep);
		medial.setDaBanHanhKetLuan(tongSoCuocThanhTraDaBanHanhKetLuan);
		medial.setSoDonViDuocThanhTra(tongSoDonViDuocThanhTra);
		medial.setSoDonViCoViPham(tongSoDonViCoViPham);
		medial.setTongViPhamTien(tongViPhamTien);
		medial.setTongViPhamDat(tongViPhamDat);
		medial.setKienNghiThuHoiTien(tongKNTHTien);
		medial.setKienNghiThuHoiDat(tongKNTHDat);
		medial.setKienNghiKhacTien(tongKNKTien);
		medial.setKienNghiKhacDat(tongKNKDat);
		medial.setKienNghiXLHanhChinhToChuc(tongKNXLHanhChinhToChuc);
		medial.setKienNghiXLHanhChinhCaNhan(tongKNXLHanhChinhCaNhan);
		medial.setKienNghiXLVu(tongKNXLVu);
		medial.setKienNghiXLDoiTuong(tongKNXLDoiTuong);
		medial.setDaThuTien(daThuTien);
		medial.setDaThuDat(daThuDat);
		medial.setTongSoKLTTVaQDXLDaKTDD(0L);
		medial.setKetQuaKiemTraDonDocTienPhaiThu(0L);
		medial.setKetQuaKiemTraDonDocTienDaThu(0l);
		medial.setKetQuaKiemTraDonDocDatPhaiThu(0L);
		medial.setKetQuaKiemTraDonDocDatDaThu(0L);
		medial.setKetQuaKiemTraDonDocDaXuLyHCToChuc(0L);
		medial.setKetQuaKiemTraDonDocDaXuLyHCCaNhan(0L);
		medial.setKetQuaKiemTraDonDocDaKhoiToVu(0L);
		medial.setKetQuaKiemTraDonDocDaKhoiToDoiTuong(0L);
		return Utils.object2Json(medial);
	}
	
}
