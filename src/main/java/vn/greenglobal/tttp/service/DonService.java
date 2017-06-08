package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QGiaiQuyetDon;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class DonService {

	BooleanExpression base = QDon.don.daXoa.eq(false);
	
	public Predicate predicateFindByCongDan(Long id) {
		BooleanExpression predAll = base.and(QDon.don.daXoa.eq(false));
		if(id > 0) {
			predAll = predAll.and(QDon.don.donCongDans.any().congDan.id.eq(id));
		}
		
		return predAll;
	}
	
	public Predicate predicateFindAll(String maDon, String tuKhoa, String nguonDon, String phanLoaiDon,
			String tiepNhanTuNgay, String tiepNhanDenNgay, String hanGiaiQuyetTuNgay, String hanGiaiQuyetDenNgay,
			String tinhTrangXuLy, boolean thanhLapDon, String trangThaiDon, Long phongBanGiaiQuyetXLD,
			Long canBoXuLyXLD, Long phongBanXuLyXLD, Long coQuanTiepNhanXLD, Long donViXuLyXLD, String chucVu, String hoTen, 
			XuLyDonRepository xuLyRepo, DonRepository donRepo, GiaiQuyetDonRepository giaiQuyetDonRepo) {

		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(thanhLapDon));
		predAll = predAll.and(QDon.don.xuLyDons.isNotEmpty().or(QDon.don.processType.eq(ProcessTypeEnum.KIEM_TRA_DE_XUAT).and(QDon.don.xuLyDons.isEmpty())));
		
		//Query don
		if (StringUtils.isNotBlank(maDon)) {
			predAll = predAll.and(QDon.don.ma.eq(StringUtils.trimToEmpty(maDon)));
		}

		if (StringUtils.isNotBlank(hoTen)) {
			predAll = predAll.and(QDon.don.donCongDans.any().congDan.hoVaTen.containsIgnoreCase(hoTen)
					.or(QDon.don.donCongDans.any().tenCoQuan.containsIgnoreCase(hoTen)))
					.and(QDon.don.donCongDans.any().phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON));
		}

		if (StringUtils.isNotBlank(nguonDon)) {
			predAll = predAll.and(QDon.don.nguonTiepNhanDon.eq(NguonTiepNhanDonEnum.valueOf(StringUtils.upperCase(nguonDon))));
		}
		
		if (StringUtils.isNotBlank(phanLoaiDon)) {
			predAll = predAll.and(QDon.don.loaiDon.eq(LoaiDonEnum.valueOf(StringUtils.upperCase(phanLoaiDon))));
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
		List<Don> donCollections = new ArrayList<Don>();
		//Query xu ly don
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false));		
		
		if (phongBanGiaiQuyetXLD != null) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanGiaiQuyet.id.eq(phongBanGiaiQuyetXLD));
		}
		
		if (canBoXuLyXLD != null && StringUtils.isNotBlank(chucVu) && chucVu.equals(VaiTroEnum.CHUYEN_VIEN.name())) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.congChuc.id.eq(canBoXuLyXLD));
		}
		
		if (phongBanXuLyXLD != null && phongBanXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanXuLy.id.eq(phongBanXuLyXLD));
		}
		
		if (StringUtils.isNotBlank(chucVu)) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.chucVu.eq(VaiTroEnum.valueOf(StringUtils.upperCase(chucVu))));
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
		
		if (StringUtils.isNotBlank(trangThaiDon)) {			
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.trangThaiDon.stringValue().eq(trangThaiDon));
		}
		
		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();
		Collection<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery, sortOrder);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
		System.out.println("donCollections " +donCollections.size());
		System.out.println("xldCollections " +xldCollections.size());
		System.out.println("phongBanXuLyXLD " +phongBanXuLyXLD);
		
		if (StringUtils.isNotBlank(chucVu) && ("VAN_THU".equals(chucVu) || "LANH_DAO".equals(chucVu))) {
			//Query don TTXM
			BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
					.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false));
			if (StringUtils.isNotBlank(trangThaiDon)) {			
				TrangThaiXuLyDonEnum trangThaiXuLyDon = TrangThaiXuLyDonEnum.valueOf(StringUtils.upperCase(trangThaiDon));
				TinhTrangGiaiQuyetEnum tinhTrangGiaiQuyet = TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET;
				if (TrangThaiXuLyDonEnum.DANG_XU_LY.equals(trangThaiXuLyDon)) {
					tinhTrangGiaiQuyet = TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET;
				} 				
				giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.tinhTrangGiaiQuyet.eq(tinhTrangGiaiQuyet));
			}
			
			if (StringUtils.isNotBlank(chucVu)) {
				giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.chucVu.eq(VaiTroEnum.valueOf(StringUtils.upperCase(chucVu))));
			}
			
			List<Don> donCollections2 = new ArrayList<Don>();
			OrderSpecifier<Integer> sortOrder2 = QGiaiQuyetDon.giaiQuyetDon.thuTuThucHien.desc();		
			Collection<GiaiQuyetDon> giaiQuyetDons = (Collection<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery, sortOrder2);
			donCollections2 = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
			if (donViXuLyXLD != null && donViXuLyXLD > 0) {
				BooleanExpression donViKiemTraDeXuat = QDon.don.processType.eq(ProcessTypeEnum.KIEM_TRA_DE_XUAT)
						.and(QDon.don.thongTinGiaiQuyetDon.donViThamTraXacMinh.id.eq(donViXuLyXLD));
				BooleanExpression donViThamTraXacMinh = QDon.don.processType.eq(ProcessTypeEnum.THAM_TRA_XAC_MINH)
						.and(QDon.don.thongTinGiaiQuyetDon.donViThamTraXacMinh.id.eq(donViXuLyXLD));
				/*predAll = predAll.and(QDon.don.processType.isNull()
						.or(QDon.don.processType.eq(ProcessTypeEnum.XU_LY_DON))
						.or(donViKiemTraDeXuat)
						.or(donViThamTraXacMinh));*/
				
				predAll = predAll.and(QDon.don.processType.isNull()
						.or(QDon.don.processType.eq(ProcessTypeEnum.XU_LY_DON)));
			} 
			predAll = predAll.and(QDon.don.in(donCollections).or(QDon.don.in(donCollections2)));
		} else {
			predAll = predAll.and(QDon.don.in(donCollections));
		}
		
		return predAll;
	}
	
	public Predicate predicateFindAllGQD(String maDon, String nguonDon, String phanLoaiDon,
			String tiepNhanTuNgay, String tiepNhanDenNgay, boolean thanhLapDon, 
			String trangThaiDon, Long phongBanGiaiQuyetId,
			Long canBoGiaiQuyetId, String chucVu, String hoTen, 
			GiaiQuyetDonRepository giaiQuyetDonRepo) {
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(thanhLapDon));
		
		//Query don
		if (StringUtils.isNotBlank(maDon)) {
			predAll = predAll.and(QDon.don.ma.eq(StringUtils.trimToEmpty(maDon)));
		}

		if (StringUtils.isNotBlank(hoTen)) {
			predAll = predAll.and(QDon.don.donCongDans.any().congDan.hoVaTen.containsIgnoreCase(hoTen)
					.or(QDon.don.donCongDans.any().tenCoQuan.containsIgnoreCase(hoTen)))
					.and(QDon.don.donCongDans.any().phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON));
		}

		if (StringUtils.isNotBlank(nguonDon)) {
			predAll = predAll.and(QDon.don.nguonTiepNhanDon.eq(NguonTiepNhanDonEnum.valueOf(StringUtils.upperCase(nguonDon))));
		}
		
		if (StringUtils.isNotBlank(phanLoaiDon)) {
			predAll = predAll.and(QDon.don.loaiDon.eq(LoaiDonEnum.valueOf(StringUtils.upperCase(phanLoaiDon))));
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
		
//		if (phongBanGiaiQuyetId != null && phongBanGiaiQuyetId.longValue() > 0) {
//			predAll = predAll.and(QDon.don.phongBanGiaiQuyet.id.eq(phongBanGiaiQuyetId));
//		}
		
		//Query Giai quyet don
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false));
			
		if (StringUtils.isNotBlank(trangThaiDon)) {
			giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.tinhTrangGiaiQuyet.stringValue().eq(trangThaiDon));
		}
		
		if (phongBanGiaiQuyetId != null && phongBanGiaiQuyetId.longValue() > 0) {
			giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.phongBanGiaiQuyet.id.eq(phongBanGiaiQuyetId)
					.or(QGiaiQuyetDon.giaiQuyetDon.phongBanGiaiQuyet.cha.id.eq(phongBanGiaiQuyetId)));
		}
		
		if (StringUtils.isNotBlank(chucVu)) {
			giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.chucVu.eq(VaiTroEnum.valueOf(StringUtils.upperCase(chucVu))));
			
			if (VaiTroEnum.CHUYEN_VIEN.equals(VaiTroEnum.valueOf(StringUtils.upperCase(chucVu)))) {
				giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.canBoXuLyChiDinh.id.eq(canBoGiaiQuyetId));
			}
		}

		List<Don> donCollections = new ArrayList<Don>();
		OrderSpecifier<Integer> sortOrder = QGiaiQuyetDon.giaiQuyetDon.thuTuThucHien.desc();		
		Collection<GiaiQuyetDon> giaiQuyetDons = (Collection<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery, sortOrder);
		donCollections = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
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

	public String getMaDonMoi(DonRepository repo) {
		Predicate pred = QDon.don.daXoa.eq(false).and(QDon.don.ma.isNotEmpty());
		OrderSpecifier<Long> sortOrder = QDon.don.ma.castToNum(Long.class).desc();
		List<Don> list = (List<Don>) repo.findAll(pred, sortOrder);
		if (list != null && list.size() > 0) {
			Long maDonMoi = Long.parseLong(list.get(0).getMa()) + 1;
			if (maDonMoi < 10) {
				return "00" + maDonMoi;
			} else if (maDonMoi < 100) {
				return "0" + maDonMoi;
			} else {
				return "" + maDonMoi;
			}
		}
		return "001";
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
			Predicate predicate = base
					.and(QDon.don.id.ne(donId))
					.and(QDon.don.ma.eq(maDon));
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
}
