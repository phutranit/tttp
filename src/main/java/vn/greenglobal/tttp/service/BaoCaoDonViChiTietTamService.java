package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.CanCuThanhTraLaiEnum;
import vn.greenglobal.tttp.enums.HinhThucThanhTraEnum;
import vn.greenglobal.tttp.enums.KyBaoCaoTongHopEnum;
import vn.greenglobal.tttp.enums.LinhVucThanhTraEnum;
import vn.greenglobal.tttp.enums.LoaiBaoCaoTongHopEnum;
import vn.greenglobal.tttp.enums.LoaiTiepDanEnum;
import vn.greenglobal.tttp.enums.LoaiVuViecEnum;
import vn.greenglobal.tttp.enums.TienDoThanhTraEnum;
import vn.greenglobal.tttp.enums.TrangThaiBaoCaoDonViEnum;
import vn.greenglobal.tttp.model.BaoCaoDonViChiTiet;
import vn.greenglobal.tttp.model.BaoCaoDonViChiTietTam;
import vn.greenglobal.tttp.model.BaoCaoTongHop;
import vn.greenglobal.tttp.model.CuocThanhTra;
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.QBaoCaoDonViChiTiet;
import vn.greenglobal.tttp.model.QBaoCaoDonViChiTietTam;
import vn.greenglobal.tttp.model.QCuocThanhTra;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.medial.Medial_ThanhTraHanhChinh;
import vn.greenglobal.tttp.model.medial.Medial_ThanhTraKTChuyenNganh;
import vn.greenglobal.tttp.model.medial.Medial_ThanhTraLai;
import vn.greenglobal.tttp.model.medial.Medial_ThanhTraLinhVucDTXDCB;
import vn.greenglobal.tttp.model.medial.Medial_ThanhTraLinhVucDatDai;
import vn.greenglobal.tttp.model.medial.Medial_ThanhTraLinhVucTaiChinh;
import vn.greenglobal.tttp.model.medial.Medial_ThanhTraThamNhungPHQThanhTra;
import vn.greenglobal.tttp.model.medial.Medial_TongHopKetQuaTiepCongDan;
import vn.greenglobal.tttp.repository.BaoCaoDonViChiTietRepository;
import vn.greenglobal.tttp.repository.BaoCaoDonViChiTietTamRepository;
import vn.greenglobal.tttp.repository.CuocThanhTraRepository;
import vn.greenglobal.tttp.repository.LinhVucDonThuRepository;
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
	
	@Autowired
	private LinhVucDonThuRepository linhVucDonThuRepo;
	
	@Autowired
	private LinhVucDonThuService linhVucDonThuService;
	
	@Autowired
	private ThongKeBaoCaoTongHopKQTCDService thongKeBaoCaoTongHopKQTCDService;

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
					return getDataKetQuaThanhTraLinhVucDauTuXDCB(baoCao);
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_THANH_TRA_TRONG_LINH_VUC_TAI_CHINH_NGAN_SACH.equals(loaiBaoCao)) {
					return getDataKetQuaThanhTraLinhVucTaiChinhNganSach(baoCao);
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_THANH_TRA_TRONG_LINH_VUC_DAT_DAI.equals(loaiBaoCao)) {
					return getDataKetQuaThanhTraLinhVucDatDai(baoCao);
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_THANH_TRA_LAI.equals(loaiBaoCao)) {
					return getDataKetQuaThanhTraLai(baoCao);
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_THANH_TRA_KIEM_TRA_CHUYEN_NGANH.equals(loaiBaoCao)) {
					return getDataKetQuaThanhTraKiemTraChuyenNganh(baoCao);
				} else if (LoaiBaoCaoTongHopEnum.KET_QUA_PHAT_HIEN_XU_LY_THAM_NHUNG_QUA_THANH_TRA.equals(loaiBaoCao)) {
					return getDataKetQuaPhatHienThamNhungQuaThanhTra(baoCao);
				} else if (LoaiBaoCaoTongHopEnum.CONG_TAC_QUAN_LY_NHA_NUOC_VE_THANH_TRA.equals(loaiBaoCao)) {
					// tu dong cot 7 > 10
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
					// tu dong cot 5,6
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
	
	public String getDataKetQuaTiepCongDan(BaoCaoDonViChiTietTam baoCao) {
		Medial_TongHopKetQuaTiepCongDan medial = Utils.json2Object(Medial_TongHopKetQuaTiepCongDan.class, baoCao.getSoLieuBaoCao());		
		BaoCaoTongHop baoCaoTongHop = baoCao.getBaoCaoDonViChiTiet().getBaoCaoDonVi().getBaoCaoTongHop();
		LocalDateTime ngayBatDau = getNgayBatDauBaoCao(baoCao.getBaoCaoDonViChiTiet().getCha());
		LocalDateTime ngayKetThuc = getNgayKetThucBaoCao(baoCao.getBaoCaoDonViChiTiet().getCha());
		String ngayBatDauStr = ngayBatDau.toString().concat("Z");
		String ngayKetThucStr = ngayKetThuc.toString().concat("Z");
		
		// khieu nai
		LinhVucDonThu linhVucHanhChinhDonKhieuNai = linhVucDonThuRepo.findOne(1L);
		LinhVucDonThu linhVucHanhChinhKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
		LinhVucDonThu linhVucHanhChinhKhieuNaiVeDang = linhVucDonThuRepo.findOne(56L);

		// to cao
		LinhVucDonThu linhVucHanhChinhDonToCao = linhVucDonThuRepo.findOne(15L);
		LinhVucDonThu linhVucTuPhapDonToCao = linhVucDonThuRepo.findOne(16L);
		LinhVucDonThu linhVucThamNhungDonToCao = linhVucDonThuRepo.findOne(39L);
		LinhVucDonThu linhVucVeDangDonToCao = linhVucDonThuRepo.findOne(62L);
		LinhVucDonThu linhVucKhacDonToCao = linhVucDonThuRepo.findOne(63L);
		
		List<Long> idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais = new ArrayList<Long>();
		idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais.add(55L);
		
		List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachs = new ArrayList<Long>();
		idLinhVucHanhChinhDonKhieuNaiVeChinhSachs.add(5L);

		List<Long> idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs = new ArrayList<Long>();
		idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs.add(53L);
		
		List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
		idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(57L);
		
		List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs = new ArrayList<Long>();
		idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(58L);

		List<Long> idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs = new ArrayList<Long>();
		idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(59L);
		
		List<LinhVucDonThu> linhVucTranhChapVeDatDais = new ArrayList<LinhVucDonThu>();
		linhVucTranhChapVeDatDais.addAll(linhVucDonThuService
				.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais));

		List<LinhVucDonThu> linhVucVeChinhSachs = new ArrayList<LinhVucDonThu>();
		linhVucVeChinhSachs.addAll(
				linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeChinhSachs));

		List<LinhVucDonThu> linhVucVeCheDoCCVCs = new ArrayList<LinhVucDonThu>();
		linhVucVeCheDoCCVCs.addAll(
				linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs));
		
		List<LinhVucDonThu> linhVucVeNhaCuaTaiSans = new ArrayList<LinhVucDonThu>();
		linhVucVeNhaCuaTaiSans.addAll(
				linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));
		
		List<LinhVucDonThu> linhVucVeChinhSachCCVCs = new ArrayList<LinhVucDonThu>();
		linhVucVeChinhSachCCVCs.addAll(
				linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs));
		
		List<LinhVucDonThu> linhVucCTVHXHKKhacs = new ArrayList<LinhVucDonThu>();
		linhVucCTVHXHKKhacs.addAll(linhVucDonThuService
				.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs));
		
		BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQTCDService
				.predicateFindAllTCD("TUY_CHON", 0, baoCaoTongHop.getNamBaoCao(), 0, ngayBatDauStr, ngayKetThucStr);
		
		BooleanExpression predAllDSTCDDonVi = predAllDSTCD;
		predAllDSTCDDonVi = predAllDSTCDDonVi
				.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.eq(baoCao.getBaoCaoDonViChiTiet().getDonVi()));

		BooleanExpression predAllDSTCDThuongXuyen = predAllDSTCDDonVi;
		predAllDSTCDThuongXuyen = predAllDSTCDThuongXuyen
				.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.THUONG_XUYEN));

		predAllDSTCDThuongXuyen = predAllDSTCDThuongXuyen
				.and(QSoTiepCongDan.soTiepCongDan.don.yeuCauGapTrucTiepLanhDao.isFalse());

		BooleanExpression predAllDSTCDLanhDao = predAllDSTCDDonVi;
		predAllDSTCDLanhDao = predAllDSTCDLanhDao
				.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DINH_KY)
						.or(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DOT_XUAT)));


		// tiep cong dan thuong xuyen
		// luoc - 1
		Long tiepCongDanThuongXuyenLuot = thongKeBaoCaoTongHopKQTCDService
				.getTongSoLuocTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen);

		// nguoi - 2
		Long tiepCongDanThuongXuyenNguoi = thongKeBaoCaoTongHopKQTCDService
				.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, false, false);

		// vu viec cu - 3
		Long tiepCongDanThuongXuyenVuViecCu = thongKeBaoCaoTongHopKQTCDService
				.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, false, LoaiVuViecEnum.CU);

		// vu viec moi phat sinh - 4
		Long tiepCongDanThuongXuyenVuViecMoiPhatSinh = thongKeBaoCaoTongHopKQTCDService
				.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, false,
						LoaiVuViecEnum.MOI_PHAT_SINH);

		// doan dong nguoi
		// so doan - 5
		Long tiepCongDanThuongXuyenDoanDongNguoiSoDoan = thongKeBaoCaoTongHopKQTCDService
				.getTongSoDoanDongNguoiTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true);

		// nguoi - 6
		Long tiepCongDanThuongXuyenDoanDongNguoiSoNguoi = thongKeBaoCaoTongHopKQTCDService
				.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, false, true);

		// doan dong nguoi - vu viec
		// cu - 7
		Long tiepCongDanThuongXuyenDoanDongNguoiVuViecCu = thongKeBaoCaoTongHopKQTCDService
				.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true, LoaiVuViecEnum.CU);

		// moi phat sinh - 8
		Long tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh = thongKeBaoCaoTongHopKQTCDService
				.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true,
						LoaiVuViecEnum.MOI_PHAT_SINH);

		// tiep cong dan dinh ky dot xuat cua lanh dao
		// luoc - 9
		Long tiepCongDanDinhKyDotXuatCuaLanhDaoLuot = thongKeBaoCaoTongHopKQTCDService
				.getTongSoLuocTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao);

		// nguoi - 10
		Long tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi = thongKeBaoCaoTongHopKQTCDService
				.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao);

		// tiep cong dan cua lanh dao - vu viec
		// cu - 11
		Long tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu = thongKeBaoCaoTongHopKQTCDService
				.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, false, LoaiVuViecEnum.CU);

		// moi phat sinh - 12
		Long tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh = thongKeBaoCaoTongHopKQTCDService
				.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, false,
						LoaiVuViecEnum.MOI_PHAT_SINH);

		// tiep cong dan cua lanh dao - doan dong nguoi
		// so doan - 13
		Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan = thongKeBaoCaoTongHopKQTCDService
				.getTongSoDoanDongNguoiTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true);

		// so nguoi - 14
		Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi = thongKeBaoCaoTongHopKQTCDService
				.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, false, true);

		// tiep cong dan cua lanh dao - doan dong nguoi - vu viec
		// cu - 15
		Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu = thongKeBaoCaoTongHopKQTCDService
				.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true, LoaiVuViecEnum.CU);

		// moi phat sinh - 16
		Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh = thongKeBaoCaoTongHopKQTCDService
				.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true,
						LoaiVuViecEnum.MOI_PHAT_SINH);

		Long tongSoVuViecKhieuNaiLinhVucHanhChinhLienQuanDenDatDai18 = thongKeBaoCaoTongHopKQTCDService
				.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi,
						linhVucHanhChinhDonKhieuNai, linhVucTranhChapVeDatDais);
		Long tongSoVuViecKhieuNaiLinhVucHanhChinhVeNhaTaiSan19 = thongKeBaoCaoTongHopKQTCDService
				.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi,
						linhVucHanhChinhDonKhieuNai, linhVucVeNhaCuaTaiSans);
		Long tongSoVuViecKhieuNaiLinhVucHanhChinhVeChinhSachCCVC20 = thongKeBaoCaoTongHopKQTCDService
				.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi,
						linhVucHanhChinhDonKhieuNai, linhVucVeChinhSachCCVCs);
		Long tongSoVuViecKhieuNaiLinhVucHanhChinhCTVHXHKKhac21 = thongKeBaoCaoTongHopKQTCDService
				.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi,
						linhVucHanhChinhDonKhieuNai, linhVucCTVHXHKKhacs);

		Long tongVuViecDonKhieuNaiLinhVucHanhChinh17 = tongSoVuViecKhieuNaiLinhVucHanhChinhLienQuanDenDatDai18
				+ tongSoVuViecKhieuNaiLinhVucHanhChinhVeNhaTaiSan19
				+ tongSoVuViecKhieuNaiLinhVucHanhChinhVeChinhSachCCVC20
				+ tongSoVuViecKhieuNaiLinhVucHanhChinhCTVHXHKKhac21;
		
		Long donToCaoLinhVucHanhChinh = thongKeBaoCaoTongHopKQTCDService
				.getTongSoVuViecTiepCongDanDonToCaoLinhVucDonThu(predAllDSTCDDonVi, linhVucHanhChinhDonToCao);
		Long donToCaoLinhVucTuPhap = thongKeBaoCaoTongHopKQTCDService
				.getTongSoVuViecTiepCongDanDonToCaoLinhVucDonThu(predAllDSTCDDonVi, linhVucTuPhapDonToCao);
		Long donToCaoLinhVucThamNhung = thongKeBaoCaoTongHopKQTCDService
				.getTongSoVuViecTiepCongDanDonToCaoLinhVucDonThu(predAllDSTCDDonVi, linhVucThamNhungDonToCao);
		Long donToCaoLinhVucVeDang = thongKeBaoCaoTongHopKQTCDService
				.getTongSoVuViecTiepCongDanDonToCaoLinhVucDonThu(predAllDSTCDDonVi, linhVucVeDangDonToCao);
		Long donToCaoLinhVucKhac = thongKeBaoCaoTongHopKQTCDService
				.getTongSoVuViecTiepCongDanDonToCaoLinhVucDonThu(predAllDSTCDDonVi, linhVucKhacDonToCao);

		Long tongVuViecDonToCaoLinhVucHanhChinh18 = donToCaoLinhVucHanhChinh + donToCaoLinhVucTuPhap
				+ donToCaoLinhVucThamNhung + donToCaoLinhVucVeDang + donToCaoLinhVucKhac;

		
		medial.setTiepCongDanThuongXuyenLuot(tiepCongDanThuongXuyenLuot);
		medial.setTiepCongDanThuongXuyenNguoi(tiepCongDanThuongXuyenNguoi);
		medial.setTiepCongDanThuongXuyenVuViecCu(tiepCongDanThuongXuyenVuViecCu);
		medial.setTiepCongDanThuongXuyenVuViecMoiPhatSinh(tiepCongDanThuongXuyenVuViecMoiPhatSinh);
		medial.setTiepCongDanThuongXuyenDoanDongNguoiSoDoan(tiepCongDanThuongXuyenDoanDongNguoiSoDoan);
		medial.setTiepCongDanThuongXuyenDoanDongNguoiSoNguoi(tiepCongDanThuongXuyenDoanDongNguoiSoNguoi);
		medial.setTiepCongDanThuongXuyenDoanDongNguoiVuViecCu(tiepCongDanThuongXuyenDoanDongNguoiVuViecCu);
		medial.setTiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh(tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh);
		medial.setTiepCongDanDinhKyDotXuatCuaLanhDaoLuot(tiepCongDanDinhKyDotXuatCuaLanhDaoLuot);
		medial.setTiepCongDanDinhKyDotXuatCuaLanhDaoNguoi(tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi);
		medial.setTiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu(tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu);
		medial.setTiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh(tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh);
		medial.setTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan(tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan);
		medial.setTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi(tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi);
		medial.setTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu(tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu);
		medial.setTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh(tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh);
		medial.setTongVuViecDonKhieuNaiLinhVucHanhChinh(tongVuViecDonKhieuNaiLinhVucHanhChinh17);
		medial.setDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai(tongSoVuViecKhieuNaiLinhVucHanhChinhLienQuanDenDatDai18);
		medial.setDonKhieuNaiLinhVucHanhChinhVeNhaTaiSan(tongSoVuViecKhieuNaiLinhVucHanhChinhVeNhaTaiSan19);
		medial.setDonKhieuNaiLinhVucHanhChinhVeChinhSachCCVC(tongSoVuViecKhieuNaiLinhVucHanhChinhVeChinhSachCCVC20);
		medial.setDonKhieuNaiLinhVucHanhChinhVeChinhCTVHXHKKhac(tongSoVuViecKhieuNaiLinhVucHanhChinhCTVHXHKKhac21);
		medial.setDonKhieuNaiLinhVucTuPhap(0L);
		medial.setDonKhieuNaiLinhVucVeDang(0L);
		medial.setTongVuViecDonToCao(0L);
		medial.setDonToCaoLinhVucHanhChinh(0L);
		medial.setDonToCaoLinhVucTuPhap(0L);
		medial.setDonToCaoLinhVucThamNhung(0L);
		medial.setDonToCaoLinhVucVeDang(0L);
		medial.setDonToCaoLinhVucKhac(0L);
		medial.setPhanAnhKienNghiKhac(0L);
		medial.setChuaDuocGiaiQuyet(0L);
		medial.setChuaCoQuyetDinhGiaiQuyet(0L);
		medial.setDaCoQuyetDinhGiaiQuyet(0L);
		medial.setDaCoBanAnCuaToa(0L);
		
		return Utils.object2Json(medial);
	}
	
	public String getDataKetQuaPhatHienThamNhungQuaThanhTra(BaoCaoDonViChiTietTam baoCao) {
		Medial_ThanhTraThamNhungPHQThanhTra medial = Utils.json2Object(Medial_ThanhTraThamNhungPHQThanhTra.class, baoCao.getSoLieuBaoCao());		
		BaoCaoTongHop baoCaoTongHop = baoCao.getBaoCaoDonViChiTiet().getBaoCaoDonVi().getBaoCaoTongHop();
		LocalDateTime ngayBatDau = getNgayBatDauBaoCao(baoCao.getBaoCaoDonViChiTiet().getCha());
		LocalDateTime ngayKetThuc = getNgayKetThucBaoCao(baoCao.getBaoCaoDonViChiTiet().getCha());
		String ngayBatDauStr = ngayBatDau.toString().concat("Z");
		String ngayKetThucStr = ngayKetThuc.toString().concat("Z");
				
		BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindAllCuocThanhTraTrongKy("TUY_CHON", 0, baoCaoTongHop.getNamBaoCao(), 0, ngayBatDauStr, ngayKetThucStr);
		
		BooleanExpression predAllThamNhungQuaThanhTra = predAllCuocThanhTra
				.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(baoCao.getBaoCaoDonViChiTiet().getDonVi()));
		
		// So vu tham nhung
		Long soVuThamNhung = thongKeTongHopThanhTraService
				.getSoVuNguoiThamNhung(predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "VU");

		Long soNguoiThamNhung = thongKeTongHopThanhTraService
				.getSoVuNguoiThamNhung(predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "NGUOI");
		
		// Tai san tham nhung
		Long taiSanThamNhungTongTien = thongKeTongHopThanhTraService.getGiaTriThamNhung(
				predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "TAI_SAN_THAM_NHUNG", "TONG_TIEN");
		Long taiSanThamNhungTien = thongKeTongHopThanhTraService.getGiaTriThamNhung(
				predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "TAI_SAN_THAM_NHUNG", "TIEN");
		Long taiSanThamNhungTaiSanKhac = thongKeTongHopThanhTraService.getGiaTriThamNhung(
				predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "TAI_SAN_THAM_NHUNG", "TAI_SAN_KHAC");
		Long taiSanThamNhungDat = thongKeTongHopThanhTraService.getGiaTriThamNhung(
				predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "TAI_SAN_THAM_NHUNG", "DAT");

		// Kien nghi thu hoi
		Long kienNghiThuHoiTongTien = thongKeTongHopThanhTraService.getGiaTriThamNhung(
				predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "KIEN_NGHI_THU_HOI", "TONG_TIEN");
		Long kienNghiThuHoiTien = thongKeTongHopThanhTraService.getGiaTriThamNhung(
				predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "KIEN_NGHI_THU_HOI", "TIEN");
		Long kienNghiThuHoiTaiSanKhac = thongKeTongHopThanhTraService.getGiaTriThamNhung(
				predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "KIEN_NGHI_THU_HOI", "TAI_SAN_KHAC");
		Long kienNghiThuHoiDat = thongKeTongHopThanhTraService.getGiaTriThamNhung(
				predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "KIEN_NGHI_THU_HOI", "DAT");

		// Da thu
		Long daThuTongTien = thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
				cuocThanhTraRepo, "DA_THU", "TONG_TIEN");
		Long daThuTien = thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
				cuocThanhTraRepo, "DA_THU", "TIEN");
		Long daThuTaiSanKhac = thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
				cuocThanhTraRepo, "DA_THU", "TAI_SAN_KHAC");
		Long daThuDat = thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
				cuocThanhTraRepo, "DA_THU", "DAT");

		// Kien nghi xu ly
		Long hanhChinhToChuc = thongKeTongHopThanhTraService.getSoKienNghiXuLyHanhChinhThamNhung(
				predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "TO_CHUC");
		Long hanhChinhCaNhan = thongKeTongHopThanhTraService.getSoKienNghiXuLyHanhChinhThamNhung(
				predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "CA_NHAN");

		Long chuyenCoQuanDieuTraVu = thongKeTongHopThanhTraService
				.getSoChuyenCoQuanDieuTraThamNhung(predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "VU");
		Long chuyenCoQuanDieuTraDoiTuong = thongKeTongHopThanhTraService.getSoChuyenCoQuanDieuTraThamNhung(
				predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "DOI_TUONG");
		
		medial.setSoNguoi(soNguoiThamNhung);
		medial.setSoVu(soVuThamNhung);
		medial.setTaiSanThamNhungTongTien(taiSanThamNhungTongTien);
		medial.setTaiSanThamNhungTien(taiSanThamNhungTien);
		medial.setTaiSanThamNhungTaiSanKhac(taiSanThamNhungTaiSanKhac);
		medial.setTaiSanThamNhungDat(taiSanThamNhungDat);
		medial.setKienNghiThuHoiTongTien(kienNghiThuHoiTongTien);
		medial.setKienNghiThuHoiTien(kienNghiThuHoiTien);
		medial.setKienNghiThuHoiTaiSanKhac(kienNghiThuHoiTaiSanKhac);
		medial.setKienNghiThuHoiDat(kienNghiThuHoiDat);
		medial.setDaThuTongTien(daThuTongTien);
		medial.setDaThuTien(daThuTien);
		medial.setDaThuTaiSanKhac(daThuTaiSanKhac);
		medial.setDaThuDat(daThuDat);
		medial.setHanhChinhToChuc(hanhChinhToChuc);
		medial.setHanhChinhCaNhan(hanhChinhCaNhan);
		medial.setChuyenCoQuanDieuTraVu(chuyenCoQuanDieuTraVu);
		medial.setChuyenCoQuanDieuTraDoiTuong(chuyenCoQuanDieuTraDoiTuong);
		medial.setDaXuLyHanhChinhToChuc(0L);
		medial.setDaXuLyHanhChinhCaNhan(0L);
		medial.setDaKhoiToVu(0L);
		medial.setDaKhoiToDoiTuong(0L);
		medial.setXuLyTrachNhiemNguoiDungDauKienNghi(0L);
		medial.setXuLyTrachNhiemNguoiDungDauDaXuLy(0L);
		
		return Utils.object2Json(medial);
	}
	
	public String getDataKetQuaThanhTraKiemTraChuyenNganh(BaoCaoDonViChiTietTam baoCao) {
		Medial_ThanhTraKTChuyenNganh medial = Utils.json2Object(Medial_ThanhTraKTChuyenNganh.class, baoCao.getSoLieuBaoCao());		
		BaoCaoTongHop baoCaoTongHop = baoCao.getBaoCaoDonViChiTiet().getBaoCaoDonVi().getBaoCaoTongHop();
		LocalDateTime ngayBatDau = getNgayBatDauBaoCao(baoCao.getBaoCaoDonViChiTiet().getCha());
		LocalDateTime ngayKetThuc = getNgayKetThucBaoCao(baoCao.getBaoCaoDonViChiTiet().getCha());
		String ngayBatDauStr = ngayBatDau.toString().concat("Z");
		String ngayKetThucStr = ngayKetThuc.toString().concat("Z");
		
		BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindAllCuocThanhTraTrongKy("TUY_CHON", 0, baoCaoTongHop.getNamBaoCao(), 0, ngayBatDauStr, ngayKetThucStr);
		
		BooleanExpression predAllCuocThanhTraCoQuan = predAllCuocThanhTra
				.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(baoCao.getBaoCaoDonViChiTiet().getDonVi()));

		// Get cuoc thanh tra theo linh vuc hanh chinh
		predAllCuocThanhTraCoQuan = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindCuocThanhTraTheoLinhVuc(predAllCuocThanhTraCoQuan,
						LinhVucThanhTraEnum.TAI_CHINH);
		// So Cuoc Thanh Tra
		Long soCuocTTKTTongSo = Long
				.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size());
		
		Long soCuocTTKTThanhLapDoan = Long.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo
				.findAll(predAllCuocThanhTraCoQuan.and(QCuocThanhTra.cuocThanhTra.thanhVienDoan.isTrue())))
						.size());
		
		Long soCuocTTKTThanhTraDocLap = Long.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo
				.findAll(predAllCuocThanhTraCoQuan.and(QCuocThanhTra.cuocThanhTra.thanhVienDoan.isFalse())))
						.size());

		// So Ca nhan duoc thanh tra kiem tra
		Long caNhanDuocTTKTThanhTra = 0L;
		Long caNhanDuocTTKTKiemTra = 0L;

		// So To chuc duoc thanh tra, kiem tra
		Long toChucDuocTTKTThanhTra = 0L;
		Long toChucDuocTTKTKiemTra = 0L;

		// Ket qua
		// So co vi pham
		Long soCoViPhamTongSo = thongKeTongHopThanhTraService
				.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TONG_SO", cuocThanhTraRepo);
		Long soCoViPhamCaNhan = thongKeTongHopThanhTraService
				.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "CA_NHAN", cuocThanhTraRepo);
		Long soCoViPhanToChuc = thongKeTongHopThanhTraService
				.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TO_CHUC", cuocThanhTraRepo);

		// So quyet dinh xu phat hanh chinh duoc ban hanh
		Long soQDXuPhatHanhChinhTongSo = thongKeTongHopThanhTraService
				.getSoQuyetDinhXuPhatKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TONG_SO",
						cuocThanhTraRepo);
		Long soQDXuPhatHanhChinhCaNhan = thongKeTongHopThanhTraService
				.getSoQuyetDinhXuPhatKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "CA_NHAN",
						cuocThanhTraRepo);
		Long soQDXuPhatHanhChinhToChuc = thongKeTongHopThanhTraService
				.getSoQuyetDinhXuPhatKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TO_CHUC",
						cuocThanhTraRepo);

		// So tien vi pham
		Long soTienViPhamTongSo = thongKeTongHopThanhTraService
				.getTienViPhamKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TONG_SO", cuocThanhTraRepo);
		Long soTienViPhamCaNhan = thongKeTongHopThanhTraService
				.getTienViPhamKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "CA_NHAN", cuocThanhTraRepo);
		Long soTienViPhamToChuc = thongKeTongHopThanhTraService
				.getTienViPhamKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TO_CHUC", cuocThanhTraRepo);

		// So tien kien nghi thu hoi
		Long soTienKienNghiThuHoi = thongKeTongHopThanhTraService.getTienViPhamKienNghiXuLyHanhChinh(
				predAllCuocThanhTraCoQuan, "KIEN_NGHI_THU_HOI", cuocThanhTraRepo);
		
		// So tien xu ly tai san vi pham
		Long soTienXuLyTaiSanTongSo = thongKeTongHopThanhTraService.getTienXuLyTaiSanKienNghiXuLyHanhChinh(
				predAllCuocThanhTraCoQuan, "TONG_SO", cuocThanhTraRepo);
		Long soTienXuLyTaiSanTichThu = thongKeTongHopThanhTraService.getTienXuLyTaiSanKienNghiXuLyHanhChinh(
				predAllCuocThanhTraCoQuan, "TICH_THU", cuocThanhTraRepo);
		Long soTienXuLyTaiSanTieuHuy = thongKeTongHopThanhTraService.getTienXuLyTaiSanKienNghiXuLyHanhChinh(
				predAllCuocThanhTraCoQuan, "TIEU_HUY", cuocThanhTraRepo);

		// So tien xu ly vi pham
		Long soTienXuLyViPhamTongSo = thongKeTongHopThanhTraService
				.getTienXuPhatViPhamKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TONG_SO",
						cuocThanhTraRepo);
		Long soTienXuLyViPhamCaNhan = thongKeTongHopThanhTraService
				.getTienXuPhatViPhamKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "CA_NHAN",
						cuocThanhTraRepo);
		Long soTienXuLyViPhamToChuc = thongKeTongHopThanhTraService
				.getTienXuPhatViPhamKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TO_CHUC",
						cuocThanhTraRepo);

		// So tien da thu
		Long soTienDaThuTongSo = thongKeTongHopThanhTraService
				.getTienDaThuThanhTraChuyenNganh(predAllCuocThanhTraCoQuan, "TONG_SO", cuocThanhTraRepo);
		Long soTienDaThuCaNhan = thongKeTongHopThanhTraService
				.getTienDaThuThanhTraChuyenNganh(predAllCuocThanhTraCoQuan, "CA_NHAN", cuocThanhTraRepo);
		Long soTienDaThuToChuc = thongKeTongHopThanhTraService
				.getTienDaThuThanhTraChuyenNganh(predAllCuocThanhTraCoQuan, "TO_CHUC", cuocThanhTraRepo);
		
		
		medial.setSoCuocTTKTTongSo(soCuocTTKTTongSo);
		medial.setThanhLapDoan(soCuocTTKTThanhLapDoan);
		medial.setThanhTraDocLap(soCuocTTKTThanhTraDocLap);
		medial.setCaNhanDuocThanhTra(caNhanDuocTTKTThanhTra);
		medial.setCaNhanDuocKiemTra(caNhanDuocTTKTKiemTra);
		medial.setToChucDuocThanhTra(toChucDuocTTKTThanhTra);
		medial.setToChucDuocKiemTra(toChucDuocTTKTKiemTra);
		medial.setSoCoViPhamTongSo(soCoViPhamTongSo);
		medial.setSoCoViPhamCaNhan(soCoViPhamCaNhan);
		medial.setSoCoViPhanToChuc(soCoViPhanToChuc);
		medial.setSoQDXuPhatHanhChinhTongSo(soQDXuPhatHanhChinhTongSo);
		medial.setSoQDXuPhatHanhChinhCaNhan(soQDXuPhatHanhChinhCaNhan);
		medial.setSoQDXuPhatHanhChinhToChuc(soQDXuPhatHanhChinhToChuc);
		medial.setSoTienViPhamTongSo(soTienViPhamTongSo);
		medial.setSoTienViPhamCaNhan(soTienViPhamCaNhan);
		medial.setSoTienViPhamToChuc(soTienViPhamToChuc);
		medial.setSoTienKienNghiThuHoi(soTienKienNghiThuHoi);
		medial.setSoTienXuLyTaiSanTongSo(soTienXuLyTaiSanTongSo);
		medial.setSoTienXuLyTaiSanTichThu(soTienXuLyTaiSanTichThu);
		medial.setSoTienXuLyTaiSanTieuHuy(soTienXuLyTaiSanTieuHuy);
		medial.setSoTienXuLyViPhamTongSo(soTienXuLyViPhamTongSo);
		medial.setSoTienXuLyViPhamCaNhan(soTienXuLyViPhamCaNhan);
		medial.setSoTienXuLyViPhamToChuc(soTienXuLyViPhamToChuc);
		medial.setSoTienDaThuTongSo(soTienDaThuTongSo);
		medial.setSoTienDaThuCaNhan(soTienDaThuCaNhan);
		medial.setSoTienDaThuToChuc(soTienDaThuToChuc);
		
		return Utils.object2Json(medial);
	}
	
	public String getDataKetQuaThanhTraLai(BaoCaoDonViChiTietTam baoCao) {
		Medial_ThanhTraLai medial = Utils.json2Object(Medial_ThanhTraLai.class, baoCao.getSoLieuBaoCao());		
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
		
		BooleanExpression predAllCuocThanhTraLai = predAllCuocThanhTra;

		// Get cuoc thanh tra lai
		predAllCuocThanhTraLai = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindCuocThanhTraLai(predAllCuocThanhTraLai
						.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(baoCao.getBaoCaoDonViChiTiet().getDonVi())));

		// Dem so cuoc thanh tra lai
		Long tongSoCuocThanhTraLai = Long
				.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraLai)).size());

		// Dem so cuoc theo thanh lai
		// Trong ky
		BooleanExpression predAllCuocThanhTraLaiTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindCuocThanhTraLai(predAllCuocThanhTraTrongKy
						.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(baoCao.getBaoCaoDonViChiTiet().getDonVi())));
		BooleanExpression predAllCuocThanhTraLaiKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindCuocThanhTraLai(predAllCuocThanhTraKyTruoc
						.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(baoCao.getBaoCaoDonViChiTiet().getDonVi())));

		Long tongSoCuocThanhTrongKy = Long.valueOf(
				((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraLaiTrongKy)).size());
		Long tongSoCuocThanhKyTruoc = Long.valueOf(
				((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraLaiKyTruoc)).size());

		// Dem so cuoc thanh tra theo tien do
		// Ket thuc thanh tra truc tiep
		Long tongSoCuocThanhTraKetThucTrucTiep = thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(
				predAllCuocThanhTraLai, TienDoThanhTraEnum.KET_THUC_THANH_TRA_TRUC_TIEP, cuocThanhTraRepo);
		// Da ban hanh ket luan
		Long tongSoCuocThanhTraDaBanHanhKetLuan = thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(
				predAllCuocThanhTraLai, TienDoThanhTraEnum.DA_BAN_HANH_KET_LUAN, cuocThanhTraRepo);

		// Dem so don vi duoc thanh tra
		Long tongSoDonViDuocThanhTra = thongKeTongHopThanhTraService
				.getSoDonViDuocThanhTra(predAllCuocThanhTraLai, cuocThanhTraRepo);

		// Lay danh sach cuoc thanh tra co vi pham
		BooleanExpression predAllCuocThanhTraCoQuanViPham = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindCuocThanhTraCoViPham(predAllCuocThanhTraLai, cuocThanhTraRepo);

		// Dem so don vi co vi pham
		Long tongSoDonViCoViPham = thongKeTongHopThanhTraService
				.getSoDonViDuocThanhTraCoViPham(predAllCuocThanhTraLai, cuocThanhTraRepo);

		// Block noi dung vi pham
		Long tongViPhamTien = thongKeTongHopThanhTraService.getTienDatTheoViPham(
				predAllCuocThanhTraCoQuanViPham, "TONG_VI_PHAM", "TIEN", cuocThanhTraRepo);
		Long tongViPhamDat = thongKeTongHopThanhTraService.getTienDatTheoViPham(
				predAllCuocThanhTraCoQuanViPham, "TONG_VI_PHAM", "DAT", cuocThanhTraRepo);

		Long tongKNTHTien = thongKeTongHopThanhTraService.getTienDatTheoViPham(
				predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_THU_HOI", "TIEN", cuocThanhTraRepo);
		Long tongKNTHDat = thongKeTongHopThanhTraService.getTienDatTheoViPham(
				predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_THU_HOI", "DAT", cuocThanhTraRepo);

		Long tongKNKTien = thongKeTongHopThanhTraService.getTienDatTheoViPham(
				predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_KHAC", "TIEN", cuocThanhTraRepo);
		Long tongKNKDat = thongKeTongHopThanhTraService.getTienDatTheoViPham(
				predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_KHAC", "DAT", cuocThanhTraRepo);

		// Kien nghi xu ly
		Long tongKNXLHanhChinhToChuc = thongKeTongHopThanhTraService
				.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuanViPham, "TO_CHUC", cuocThanhTraRepo);
		Long tongKNXLHanhChinhCaNhan = thongKeTongHopThanhTraService
				.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuanViPham, "CA_NHAN", cuocThanhTraRepo);

		// Chuyen co quan dieu tra
		Long tongKNXLVu = thongKeTongHopThanhTraService.getKienNghiXuLyCCQDT(predAllCuocThanhTraLai, "VU",
				cuocThanhTraRepo);
		Long tongKNXLDoiTuong = thongKeTongHopThanhTraService.getKienNghiXuLyCCQDT(predAllCuocThanhTraLai,
				"DOI_TUONG", cuocThanhTraRepo);
		
		Long canCuThanhTraLaiViPhamTrinhTu = thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(
				predAllCuocThanhTraLai, CanCuThanhTraLaiEnum.VI_PHAM_TRINH_TU, cuocThanhTraRepo);
		Long canCuThanhTraLaiKetLuanKhongPhuHop = thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(
				predAllCuocThanhTraLai, CanCuThanhTraLaiEnum.KET_LUAN_KHONG_PHU_HOP, cuocThanhTraRepo);
		Long canCuThanhTraLaiSaiLamApDungPhapLuat = thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(
				predAllCuocThanhTraLai, CanCuThanhTraLaiEnum.SAI_LAM_AP_DUNG_PHAP_LUAT, cuocThanhTraRepo);
		Long canCuThanhTraLaiCoYLamSaiLechHoSo = thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(
				predAllCuocThanhTraLai, CanCuThanhTraLaiEnum.CO_Y_LAM_SAI_LECH_HO_SO, cuocThanhTraRepo);
		Long canCuThanhTraLaiViPhamNghiemTrong = thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(
				predAllCuocThanhTraLai, CanCuThanhTraLaiEnum.VI_PHAM_NGHIEM_TRONG, cuocThanhTraRepo);
		
		medial.setTongSoCuocThanhTraLai(tongSoCuocThanhTraLai);
		medial.setKyTruocChuyenSang(tongSoCuocThanhKyTruoc);
		medial.setTrienKhaiTrongKyBaoCao(tongSoCuocThanhTrongKy);
		medial.setKetThucThanhTraTrucTiep(tongSoCuocThanhTraKetThucTrucTiep);
		medial.setDaBanHanhKetLuan(tongSoCuocThanhTraDaBanHanhKetLuan);
		medial.setViPhamTrinhTu(canCuThanhTraLaiViPhamTrinhTu);
		medial.setKetLuanKhongPhuHop(canCuThanhTraLaiKetLuanKhongPhuHop);
		medial.setSaiLamApDungPhapLuat(canCuThanhTraLaiSaiLamApDungPhapLuat);
		medial.setCoYLamSaiLechHoSo(canCuThanhTraLaiCoYLamSaiLechHoSo);
		medial.setViPhamNghiemTrong(canCuThanhTraLaiViPhamNghiemTrong);
		medial.setSoDonViDaThanhTraLai(tongSoDonViDuocThanhTra);
		medial.setSoDonViCoViPham(tongSoDonViCoViPham);
		medial.setViPhamVeKinhTeTien(tongViPhamTien);
		medial.setViPhamVeKinhTeDat(tongViPhamDat);
		medial.setKienNghiThuHoiTien(tongKNTHTien);
		medial.setKienNghiThuHoiDat(tongKNTHDat);
		medial.setKienNghiKhacTien(tongKNKTien);
		medial.setKienNghiKhacDat(tongKNKDat);
		medial.setHanhChinhToChuc(tongKNXLHanhChinhToChuc);
		medial.setHanhChinhCaNhan(tongKNXLHanhChinhCaNhan);
		medial.setChuyenCoQuanDieuTraVu(tongKNXLVu);
		medial.setChuyenCoQuanDieuTraDoiTuong(tongKNXLDoiTuong);
		medial.setKetQuaThucHienTienDaThu(0L);
		medial.setKetQuaThucHienDatDaThu(0L);
		medial.setKetQuaThucHienToChuc(0L);
		medial.setKetQuaThucHienCaNhan(0L);
		medial.setKetQuaThucHienVu(0L);
		medial.setKetQuaThucHienDoiTuong(0L);
		
		return Utils.object2Json(medial);
	}
	
	public String getDataKetQuaThanhTraLinhVucDatDai(BaoCaoDonViChiTietTam baoCao) {
		Medial_ThanhTraLinhVucDatDai medial = Utils.json2Object(Medial_ThanhTraLinhVucDatDai.class, baoCao.getSoLieuBaoCao());		
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
				.predicateFindCuocThanhTraTheoLinhVuc(predAllCuocThanhTraCoQuan,
						LinhVucThanhTraEnum.DAT_DAI);
		
		// So cuoc thanh tra
		BooleanExpression predAllCuocThanhTraCoQuanTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindCuocThanhTraTheoLinhVuc(
						predAllCuocThanhTraTrongKy
								.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(baoCao.getBaoCaoDonViChiTiet().getDonVi())),
						LinhVucThanhTraEnum.DAT_DAI);
		BooleanExpression predAllCuocThanhTraCoQuanKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindCuocThanhTraTheoLinhVuc(
						predAllCuocThanhTraKyTruoc
								.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(baoCao.getBaoCaoDonViChiTiet().getDonVi())),
						LinhVucThanhTraEnum.DAT_DAI);
		
		// Dem so cuoc thanh tra
		Long tongSoCuocThanhTra = Long
				.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size());
		
		Long tongSoCuocThanhTraTrongKy = Long.valueOf(
				((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanTrongKy)).size());
		Long tongSoCuocThanhTraKyTruoc = Long.valueOf(
				((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanKyTruoc)).size());

		// Dem so cuoc theo thanh tra hinh thuc
		// Theo ke hoach
		Long tongSoCuocThanhTraTheoKeHoach = thongKeTongHopThanhTraService.getCuocThanhTraTheoHinhThuc(
				predAllCuocThanhTraCoQuan, HinhThucThanhTraEnum.THEO_KE_HOACH, cuocThanhTraRepo);
		// Dot xuat
		Long tongSoCuocThanhTraDotXuat = thongKeTongHopThanhTraService.getCuocThanhTraTheoHinhThuc(
				predAllCuocThanhTraCoQuan, HinhThucThanhTraEnum.DOT_XUAT, cuocThanhTraRepo);

		// Dem so cuoc thanh tra theo tien do
		// Ket thuc thanh tra truc tiep
		Long tongSoCuocThanhTraKetThucTrucTiep = thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(
				predAllCuocThanhTraCoQuan, TienDoThanhTraEnum.KET_THUC_THANH_TRA_TRUC_TIEP,
				cuocThanhTraRepo);
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
		Long tongViPhamDat = thongKeTongHopThanhTraService.getTienDatTheoViPham(
				predAllCuocThanhTraCoQuanViPham, "TONG_VI_PHAM", "DAT", cuocThanhTraRepo);
		
		// Cac dang vi pham ve dat
		Long cacDangViPhamVeDatDatLanChiem = thongKeTongHopThanhTraService
				.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham, "DAT_LAN_CHIEM", cuocThanhTraRepo);
		
		Long cacDangViPhamVeDatGiaoDatCapDatSaiDoiTuong = thongKeTongHopThanhTraService
				.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham, "GIAO_DAT_CAP_DAT_SAI_DOI_TUONG",
						cuocThanhTraRepo);
		
		Long cacDangViPhamVeDatCapBanDatTraiThamQuyen = thongKeTongHopThanhTraService.getCacDangViPhamVeDat(
				predAllCuocThanhTraCoQuanViPham, "CAP_BAN_GIAO_DAT_TRAI_THAM_QUYEN", cuocThanhTraRepo);
		
		Long cacDangViPhamVeDatCapGCNQSDDSai = thongKeTongHopThanhTraService.getCacDangViPhamVeDat(
				predAllCuocThanhTraCoQuanViPham, "CAP_GCN_QSDD_SAI", cuocThanhTraRepo);
		
		Long cacDangViPhamVeDatChuyenNhuongChoThueKhongDungQuyDinh = thongKeTongHopThanhTraService
				.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham,
						"CHUYEN_NHUONG_CHO_THUE_KHONG_DUNG_QUY_DINH", cuocThanhTraRepo);
		
		Long cacDangViPhamVeDatSuDungDatKhongDungMucDich = thongKeTongHopThanhTraService
				.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham, "SU_DUNG_DAT_KHONG_DUNG_MUC_DICH",
						cuocThanhTraRepo);
		
		Long cacDangViPhamVeDatBoHoangHoa = thongKeTongHopThanhTraService
				.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham, "BO_HOANG_HOA", cuocThanhTraRepo);
		
		Long cacDangViPhamVeDatViPhamKhac = thongKeTongHopThanhTraService
				.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham, "VI_PHAM_KHAC", cuocThanhTraRepo);

		Long tongKNTHTien = thongKeTongHopThanhTraService.getTienDatTheoViPham(
				predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_THU_HOI", "TIEN", cuocThanhTraRepo);
		Long tongKNTHDat = thongKeTongHopThanhTraService.getTienDatTheoViPham(
				predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_THU_HOI", "DAT", cuocThanhTraRepo);
		Long tongKNTHQDGiaoDat = thongKeTongHopThanhTraService.getTienDatTheoViPham(
				predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_THU_HOI", "QD_GIAO_DAT", cuocThanhTraRepo);

		Long tongKNKTien = thongKeTongHopThanhTraService.getTienDatTheoViPham(
				predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_KHAC", "TIEN", cuocThanhTraRepo);
		Long tongKNKDat = thongKeTongHopThanhTraService.getTienDatTheoViPham(
				predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_KHAC", "DAT", cuocThanhTraRepo);

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
		Long tongKNXLDoiTuong = thongKeTongHopThanhTraService.getKienNghiXuLyCCQDT(
				predAllCuocThanhTraChuyenCoQuanDieuTra, "DOI_TUONG", cuocThanhTraRepo);
		
		// Da thu
		Long daThuTien = thongKeTongHopThanhTraService
				.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "TIEN");
		
		Long daThuDat = thongKeTongHopThanhTraService
				.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "DAT");
		
		Long daThuQdGiaoDat = thongKeTongHopThanhTraService.getSoDaThuTrongQuaTrinhThanhTra(
				predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "QD_GIAO_DAT");

		
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
		medial.setDatLanChiem(cacDangViPhamVeDatDatLanChiem);
		medial.setGiaoDatCapDatSaiDoiTuong(cacDangViPhamVeDatGiaoDatCapDatSaiDoiTuong);
		medial.setCapBanDatTraiThamQuyen(cacDangViPhamVeDatCapBanDatTraiThamQuyen);
		medial.setCapGCNQSDDSai(cacDangViPhamVeDatCapGCNQSDDSai);
		medial.setChuyenNhuongChoThueKhongDungQuyDinh(cacDangViPhamVeDatChuyenNhuongChoThueKhongDungQuyDinh);
		medial.setSuDungDatKhongDungMucDich(cacDangViPhamVeDatSuDungDatKhongDungMucDich);
		medial.setBoHoangHoa(cacDangViPhamVeDatBoHoangHoa);
		medial.setViPhamKhac(cacDangViPhamVeDatViPhamKhac);
		medial.setKienNghiThuHoiTien(tongKNTHTien);
		medial.setKienNghiThuHoiDat(tongKNTHDat);
		medial.setKienNghiThuHoiQDGiaoDat(tongKNTHQDGiaoDat);
		medial.setKienNghiKhacTien(tongKNKTien);
		medial.setKienNghiKhacDat(tongKNKDat);
		medial.setHanhChinhToChuc(tongKNXLHanhChinhToChuc);
		medial.setHanhChinhCaNhan(tongKNXLHanhChinhCaNhan);
		medial.setChuyenCoQuanDieuTraVu(tongKNXLVu);
		medial.setChuyenCoQuanDieuTraDoiTuong(tongKNXLDoiTuong);
		medial.setDaThuTien(daThuTien);
		medial.setDaThuDat(daThuDat);
		medial.setDaThuQdGiaoDat(daThuQdGiaoDat);
		medial.setTongSoKLTTVaQDXLDaKTDD(0L);
		medial.setKetQuaKiemTraDonDocTienPhaiThu(0L);
		medial.setKetQuaKiemTraDonDocTienDaThu(0L);
		medial.setKetQuaKiemTraDonDocDatPhaiThu(0L);
		medial.setKetQuaKiemTraDonDocDatDaThu(0L);
		medial.setDaXuLyHanhChinhToChuc(0L);
		medial.setDaXuLyHanhChinhCaNhan(0L);
		medial.setDaKhoiToVu(0L);
		medial.setDaKhoiToDoiTuong(0L);
		
		return Utils.object2Json(medial);
	}
	
	public String getDataKetQuaThanhTraLinhVucTaiChinhNganSach(BaoCaoDonViChiTietTam baoCao) {
		Medial_ThanhTraLinhVucTaiChinh medial = Utils.json2Object(Medial_ThanhTraLinhVucTaiChinh.class, baoCao.getSoLieuBaoCao());		
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
		Long tongSoCuocThanhTra = Long
				.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size());
		
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

		// Dem so cuoc theo thanh tra hinh thuc
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

		Long tongKNTHTien = thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
				"KIEN_NGHI_THU_HOI", "TIEN", cuocThanhTraRepo);

		Long tongKNKTien = thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
				"KIEN_NGHI_KHAC", "TIEN", cuocThanhTraRepo);
		
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

		// Tien da thu trong qua trinh thanh tra
		Long daThuTien = thongKeTongHopThanhTraService
				.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "TIEN");
		
		medial.setTongSoCuocThanhTra(tongSoCuocThanhTra);
		medial.setKyTruocChuyenSang(tongSoCuocThanhTraKyTruoc);
		medial.setTrienKhaiTrongKyBaoCao(tongSoCuocThanhTraTrongKy);
		medial.setTheoKeHoach(tongSoCuocThanhTraTheoKeHoach);
		medial.setDotXuat(tongSoCuocThanhTraDotXuat);
		medial.setKetThucThanhTraTrucTiep(tongSoCuocThanhTraKetThucTrucTiep);
		medial.setDaBanHanhKetLuan(tongSoCuocThanhTraDaBanHanhKetLuan);
		medial.setSoDonViDuocThanhTra(tongSoDonViDuocThanhTra);
		medial.setSoDonViCoViPham(tongSoDonViCoViPham);
		medial.setSoTienViPham(tongViPhamTien);
		medial.setSoTienKienNghiThuHoi(tongKNTHTien);
		medial.setKienNghiKhac(tongKNKTien);
		medial.setKienNghiXLHanhChinhToChuc(tongKNXLHanhChinhToChuc);
		medial.setKienNghiXLHanhChinhCaNhan(tongKNXLHanhChinhCaNhan);
		medial.setKienNghiXuLyVu(tongKNXLVu);
		medial.setKienNghiXuLyDoiTuong(tongKNXLDoiTuong);
		medial.setSoTienDaThu(daThuTien);
		medial.setTongSoKLTTVaQDXLDaKTDD(0L);
		medial.setKiemTraDonDocTienPhaiThu(0L);
		medial.setKiemTraDonDocTienDaThu(0L);
		medial.setKetQuaKiemTraDonDocDaXuLyHCToChuc(0L);
		medial.setKetQuaKiemTraDonDocDaXuLyHCCaNhan(0L);
		medial.setKetQuaKiemTraDonDocDaKhoiToVu(0L);
		medial.setKetQuaKiemTraDonDocDaKhoiToDoiTuong(0L);
		
		return Utils.object2Json(medial);
	}
	
	public String getDataKetQuaThanhTraLinhVucDauTuXDCB(BaoCaoDonViChiTietTam baoCao) {
		Medial_ThanhTraLinhVucDTXDCB medial = Utils.json2Object(Medial_ThanhTraLinhVucDTXDCB.class, baoCao.getSoLieuBaoCao());		
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
				.predicateFindCuocThanhTraTheoLinhVuc(predAllCuocThanhTraCoQuan,
						LinhVucThanhTraEnum.XAY_DUNG_CO_BAN);
		
		// Dem so cuoc thanh tra
		Long tongSoCuocThanhTra = Long
				.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size());
		
		// So cuoc thanh tra
		BooleanExpression predAllCuocThanhTraCoQuanTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindCuocThanhTraTheoLinhVuc(
						predAllCuocThanhTraTrongKy
								.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(baoCao.getBaoCaoDonViChiTiet().getDonVi())),
						LinhVucThanhTraEnum.XAY_DUNG_CO_BAN);
		BooleanExpression predAllCuocThanhTraCoQuanKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
				.predicateFindCuocThanhTraTheoLinhVuc(
						predAllCuocThanhTraKyTruoc
								.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(baoCao.getBaoCaoDonViChiTiet().getDonVi())),
						LinhVucThanhTraEnum.XAY_DUNG_CO_BAN);

		Long tongSoCuocThanhTraTrongKy = Long.valueOf(
				((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanTrongKy)).size());
		Long tongSoCuocThanhTraKyTruoc = Long.valueOf(
				((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanKyTruoc)).size());
		
		// Dem so cuoc theo thanh tra hinh thuc
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
		
		// Da thu
		Long daThuTien = thongKeTongHopThanhTraService
				.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "TIEN");

		Long daThuDat = thongKeTongHopThanhTraService
				.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "DAT");
		
		medial.setTongSoCuocThanhTra(tongSoCuocThanhTra);
		medial.setTrienKhaiTrongKyBaoCao(tongSoCuocThanhTraTrongKy);
		medial.setKyTruocChuyenSang(tongSoCuocThanhTraKyTruoc);
		medial.setDaBanHanhKetLuan(tongSoCuocThanhTraDaBanHanhKetLuan);
		medial.setTheoKeHoach(tongSoCuocThanhTraTheoKeHoach);
		medial.setDotXuat(tongSoCuocThanhTraDotXuat);
		medial.setKetThucThanhTraTrucTiep(tongSoCuocThanhTraKetThucTrucTiep);
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
		medial.setKetQuaKiemTraDonDocTienDaThu(0L);
		medial.setKetQuaKiemTraDonDocDatPhaiThu(0L);
		medial.setKetQuaKiemTraDonDocDatDaThu(0L);
		medial.setKetQuaKiemTraDonDocDaXuLyHCToChuc(0L);
		medial.setKetQuaKiemTraDonDocDaXuLyHCCaNhan(0L);
		medial.setKetQuaKiemTraDonDocDaKhoiToVu(0L);
		medial.setKetQuaKiemTraDonDocDaKhoiToDoiTuong(0L);
		return Utils.object2Json(medial);
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
