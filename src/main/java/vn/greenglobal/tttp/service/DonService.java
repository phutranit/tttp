package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.FlowStateEnum;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.KetQuaTrangThaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.NguonTiepNhanDonEnum;
import vn.greenglobal.tttp.enums.PhanLoaiDonCongDanEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.QuyTrinhXuLyDonEnum;
import vn.greenglobal.tttp.enums.TinhTrangGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.TrangThaiXuLyDonEnum;
import vn.greenglobal.tttp.enums.TrangThaiYeuCauGapLanhDaoEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.PropertyChangeObject;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.model.QGiaiQuyetDon;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.ThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.model.VaiTro;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.LinhVucDonThuRepository;
import vn.greenglobal.tttp.repository.ThamQuyenGiaiQuyetRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class DonService {

	@Autowired
	private LinhVucDonThuRepository lichVuDonThuRepo;

	@Autowired
	private ThamQuyenGiaiQuyetRepository thamQuyenGiaiQuyetRepo;

	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepository;
	
	@Autowired
	private DonRepository donRepo;

	@Autowired
	private DonCongDanRepository donCongDanRepo;
	
	@Autowired
	private CongChucRepository congChucRepo;
	
	BooleanExpression base = QDon.don.daXoa.eq(false);
	BooleanExpression baseDonCongDan = QDon_CongDan.don_CongDan.daXoa.eq(false);

	public Predicate predicateFindByCongDan(Long id) {
		BooleanExpression predAll = base;
		BooleanExpression preAllDCD = baseDonCongDan;
		List<Don> dons = new ArrayList<Don>();
		if (id > 0) {
			List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();
			preAllDCD = preAllDCD.and(QDon_CongDan.don_CongDan.congDan.id.eq(id));
			donCongDans.addAll((List<Don_CongDan>) donCongDanRepo.findAll(preAllDCD));
			dons.addAll(donCongDans.stream().map(dcd -> dcd.getDon()).distinct().collect(Collectors.toList()));
		}
		predAll = predAll.and(QDon.don.in(dons));
		return predAll;
	}
	
	public Predicate predFindOld(Long id, Long donViId) {
		BooleanExpression predAll = base;
		if (id > 0) {
			predAll = predAll.and(QDon.don.donGocId.eq(id));
			predAll = predAll.and(QDon.don.donChuyen.eq(true));
			predAll = predAll.and(QDon.don.donViXuLyDonChuyen.id.eq(donViId));
		}
		return predAll;
	}
	
	public Predicate predicateFindAll(String maDon, String tuKhoa, String nguonDon, String phanLoaiDon,
			String tiepNhanTuNgay, String tiepNhanDenNgay, String hanGiaiQuyetTuNgay, String hanGiaiQuyetDenNgay,
			String tinhTrangXuLy, boolean thanhLapDon, String trangThaiDon, Long phongBanGiaiQuyetXLD,
			Long canBoXuLyXLD, Long phongBanXuLyXLD, Long coQuanTiepNhanXLD, Long donViXuLyXLD, String chucVu,
			Set<VaiTro> vaitros, String hoTen, String trangThaiDonToanHT, String ketQuaToanHT, boolean isChuyenVienNhapLieu, XuLyDonRepository xuLyRepo, DonRepository donRepo,
			GiaiQuyetDonRepository giaiQuyetDonRepo, boolean coQuyTrinh) {		
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(thanhLapDon));
		predAll = predAll.and(QDon.don.old.eq(false))
				.and(QDon.don.xuLyDons.isNotEmpty()
						.or(QDon.don.processType.eq(ProcessTypeEnum.KIEM_TRA_DE_XUAT)
								.or(QDon.don.processType.isNull().and(QDon.don.thanhLapDon.isTrue()))
								.and(QDon.don.xuLyDons.isEmpty())));
		
		// Query don
		if (maDon != null && StringUtils.isNotBlank(maDon.trim())) {
			predAll = predAll.and(QDon.don.ma.eq(maDon.trim()));
		}

		if (hoTen != null && StringUtils.isNotBlank(hoTen.trim())) {
			List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();
			List<Don> dons = new ArrayList<Don>();
			BooleanExpression donCongDanQuery = baseDonCongDan;
			
			donCongDanQuery = donCongDanQuery
					.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON))
					.and(QDon_CongDan.don_CongDan.congDan.hoVaTenSearch.containsIgnoreCase(Utils.unAccent(tuKhoa.trim()))
							.or(QDon_CongDan.don_CongDan.tenCoQuan.containsIgnoreCase(hoTen.trim())));
			donCongDans = (List<Don_CongDan>) donCongDanRepo.findAll(donCongDanQuery);
			dons = donCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
			predAll = predAll.and(QDon.don.in(dons));
		}

		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();
			List<Don> dons = new ArrayList<Don>();
			List<Long> donIds = new ArrayList<Long>();
			BooleanExpression donCongDanQuery = baseDonCongDan;
			
			donCongDanQuery = donCongDanQuery
					.and(QDon_CongDan.don_CongDan.hoVaTenSearch.containsIgnoreCase(Utils.unAccent(tuKhoa.trim()))
							.or(QDon_CongDan.don_CongDan.tenCoQuan.containsIgnoreCase(tuKhoa.trim()))
							.or(QDon_CongDan.don_CongDan.soCMNDHoChieu.eq(tuKhoa.trim())))
					.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON));
			
			donCongDans = (List<Don_CongDan>) donCongDanRepo.findAll(donCongDanQuery);
			dons = donCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
			donIds.addAll(dons.stream().map(d -> {
				return d.getId();
			}).distinct().collect(Collectors.toList()));
			
			predAll = predAll.and(QDon.don.in(dons).or(QDon.don.donGocId.in(donIds)));
		}

		if (nguonDon != null && StringUtils.isNotBlank(nguonDon.trim())) {
			NguonTiepNhanDonEnum nguonDonEnum = NguonTiepNhanDonEnum.valueOf(nguonDon);
			if (nguonDonEnum != null) { 
				if (!nguonDonEnum.equals(NguonTiepNhanDonEnum.CHUYEN_DON) &&
						!nguonDonEnum.equals(NguonTiepNhanDonEnum.GIAO_TTXM) &&
						!nguonDonEnum.equals(NguonTiepNhanDonEnum.GIAO_KTDX)) {
					predAll = predAll.and(
							QDon.don.nguonTiepNhanDon.eq(NguonTiepNhanDonEnum.valueOf(StringUtils.upperCase(nguonDon)))
									.and(QDon.don.processType.ne(ProcessTypeEnum.KIEM_TRA_DE_XUAT))
									.and(QDon.don.processType.ne(ProcessTypeEnum.THAM_TRA_XAC_MINH))
									.and(QDon.don.xuLyDons.any().donChuyen.isFalse()));
				}
			}
		}

		if (phanLoaiDon != null && StringUtils.isNotBlank(phanLoaiDon.trim())) {
			predAll = predAll.and(QDon.don.loaiDon.eq(LoaiDonEnum.valueOf(StringUtils.upperCase(phanLoaiDon))));
		}
		
		if (trangThaiDonToanHT != null && StringUtils.isNotBlank(trangThaiDonToanHT.trim())) {
			TrangThaiDonEnum trangThaiDonValue = TrangThaiDonEnum.valueOf(StringUtils.upperCase(trangThaiDonToanHT));
			predAll = predAll.and((QDon.don.donViXuLyGiaiQuyet.id.eq(donViXuLyXLD)
						.and(QDon.don.trangThaiXLDGiaiQuyet.eq(trangThaiDonValue)))
					.or(QDon.don.donViThamTraXacMinh.id.eq(donViXuLyXLD)
							.and(QDon.don.trangThaiTTXM.eq(trangThaiDonValue))));
		}
		
		if (ketQuaToanHT != null && StringUtils.isNotBlank(ketQuaToanHT.trim())) {
			KetQuaTrangThaiDonEnum ketQuaValue = KetQuaTrangThaiDonEnum.valueOf(StringUtils.upperCase(ketQuaToanHT));
			predAll = predAll.and((QDon.don.donViXuLyGiaiQuyet.id.eq(donViXuLyXLD)
						.and(QDon.don.ketQuaXLDGiaiQuyet.eq(ketQuaValue))));
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

		List<Don> donCollections = new ArrayList<Don>();
		// Query xu ly don
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false).and(QXuLyDon.xuLyDon.old.eq(false));

		if (phongBanGiaiQuyetXLD != null) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanGiaiQuyet.id.eq(phongBanGiaiQuyetXLD));
		}

		/*
		 * if (canBoXuLyXLD != null && StringUtils.isNotBlank(chucVu) &&
		 * chucVu.equals(VaiTroEnum.CHUYEN_VIEN.name())) { xuLyDonQuery =
		 * xuLyDonQuery.and(QXuLyDon.xuLyDon.congChuc.id.eq(canBoXuLyXLD)); }
		 */
		CongChuc congChuc = congChucRepo.findOne(canBoXuLyXLD);
		VaiTroEnum vaiTro = VaiTroEnum.valueOf(StringUtils.upperCase(chucVu));
		if (coQuyTrinh) { 
			if (phongBanXuLyXLD != null && phongBanXuLyXLD > 0) {
				xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanXuLy.id.eq(phongBanXuLyXLD)
						.or(QXuLyDon.xuLyDon.phongBanXuLy.eq(congChuc.getCoQuanQuanLy().getDonVi())));
			}
			
			if (vaitros.size() > 0) {
				List<VaiTroEnum> listVaiTro = vaitros.stream().map(d -> d.getLoaiVaiTro()).distinct()
						.collect(Collectors.toList());
				xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.chucVu.in(listVaiTro)
						.or(QXuLyDon.xuLyDon.chucVu2.in(listVaiTro))
						.or(QXuLyDon.xuLyDon.chucVu.isNull()));
			} else {
				if (StringUtils.isNotBlank(chucVu)) {
					xuLyDonQuery = xuLyDonQuery
							.and(QXuLyDon.xuLyDon.chucVu.eq(vaiTro)
									.or(QXuLyDon.xuLyDon.chucVu2.eq(vaiTro))
											.or(QXuLyDon.xuLyDon.chucVu.isNull()));
				}
			}
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

		if (StringUtils.isNotBlank(chucVu) && ("CHUYEN_VIEN".equals(chucVu))) {			
			BooleanExpression qChucVu = QXuLyDon.xuLyDon.chucVu.eq(vaiTro).or(QXuLyDon.xuLyDon.chucVu2.eq(vaiTro));
			BooleanExpression qLuuTamCoLanhDao = QXuLyDon.xuLyDon.canBoXuLyChiDinh.id.ne(canBoXuLyXLD).and(qChucVu)
					.and(QXuLyDon.xuLyDon.don.currentState.type.eq(FlowStateEnum.BAT_DAU).or(QXuLyDon.xuLyDon.congChuc.id.eq(canBoXuLyXLD)));
			BooleanExpression qGiaoViec = QXuLyDon.xuLyDon.canBoXuLyChiDinh.id.eq(canBoXuLyXLD);
			BooleanExpression qLuuTamKhongLanhDao = QXuLyDon.xuLyDon.canBoXuLyChiDinh.isNull().and(qChucVu);
			if (isChuyenVienNhapLieu) {
				xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.chucVu.isNull()
						.or(qLuuTamCoLanhDao)
						.or(qGiaoViec)
						.or(qLuuTamKhongLanhDao));
			} else {
				xuLyDonQuery = xuLyDonQuery.and(qGiaoViec);
			}			
		}
		
		if (StringUtils.isNotBlank(trangThaiDon)) {
			TrangThaiDonEnum trangThai = TrangThaiDonEnum.valueOf(trangThaiDon);
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.trangThaiDon.eq(trangThai));
		}
		
		String searchXLD = "";
		if (nguonDon != null && StringUtils.isNotBlank(nguonDon.trim())) {
			NguonTiepNhanDonEnum nguonDonEnum = NguonTiepNhanDonEnum.valueOf(nguonDon);
			if (nguonDonEnum != null) { 
				if (nguonDonEnum.equals(NguonTiepNhanDonEnum.CHUYEN_DON)) {
					searchXLD = NguonTiepNhanDonEnum.CHUYEN_DON.name();
					xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donChuyen.isTrue());
				}
			}
		}
		
		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();
		Collection<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
		Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery, sortOrder);
		CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
		donCollections = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
				
		//Neu search chuyen don xua xu ly don
		if (StringUtils.isNotBlank(searchXLD)) { 
			predAll = predAll.and(QDon.don.in(donCollections));
			return predAll;
		}
		
		if (StringUtils.isNotBlank(chucVu) && ("VAN_THU".equals(chucVu) || "LANH_DAO".equals(chucVu) || "CHUYEN_VIEN".equals(chucVu))) {
			// Query don TTXM
			BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
					.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false));
			
			if (StringUtils.isNotBlank(trangThaiDon)) {
				TrangThaiXuLyDonEnum trangThaiXuLyDon = TrangThaiXuLyDonEnum
						.valueOf(StringUtils.upperCase(trangThaiDon));
				TinhTrangGiaiQuyetEnum tinhTrangGiaiQuyet = TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET;
				if (TrangThaiXuLyDonEnum.DANG_XU_LY.equals(trangThaiXuLyDon)) {
					tinhTrangGiaiQuyet = TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET;
				}
				giaiQuyetDonQuery = giaiQuyetDonQuery
						.and(QGiaiQuyetDon.giaiQuyetDon.tinhTrangGiaiQuyet.eq(tinhTrangGiaiQuyet));
				if (TrangThaiXuLyDonEnum.DA_XU_LY.equals(trangThaiXuLyDon)) {
					if (!"LANH_DAO".equals(chucVu)) { 
						giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.congChuc.isNull()
								.or(QGiaiQuyetDon.giaiQuyetDon.congChuc.id.eq(canBoXuLyXLD)));
					}					
					if ("CHUYEN_VIEN".equals(chucVu) && isChuyenVienNhapLieu) {
						giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.canBoXuLyChiDinh.isNull()
								.or(QGiaiQuyetDon.giaiQuyetDon.congChuc.id.eq(canBoXuLyXLD).and(QGiaiQuyetDon.giaiQuyetDon.canBoXuLyChiDinh.id.ne(canBoXuLyXLD)))
								);
						
					}
				} else {
					if ("CHUYEN_VIEN".equals(chucVu) && isChuyenVienNhapLieu) {
						giaiQuyetDonQuery = giaiQuyetDonQuery.and(QGiaiQuyetDon.giaiQuyetDon.canBoXuLyChiDinh.isNull());
					}
				}
			}
			
			if (StringUtils.isNotBlank(chucVu)) {
				giaiQuyetDonQuery = giaiQuyetDonQuery
						.and(QGiaiQuyetDon.giaiQuyetDon.chucVu.eq(vaiTro)
								.or(QGiaiQuyetDon.giaiQuyetDon.chucVu.isNull())
								.or(QGiaiQuyetDon.giaiQuyetDon.chucVu2.eq(vaiTro))
								);
			}

			if (donViXuLyXLD != null && donViXuLyXLD > 0) {
				giaiQuyetDonQuery = giaiQuyetDonQuery
						.and(QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.id.eq(donViXuLyXLD));
			}
			
			
			List<Don> donCollections2 = new ArrayList<Don>();
			OrderSpecifier<Integer> sortOrder2 = QGiaiQuyetDon.giaiQuyetDon.thuTuThucHien.desc();
			// query don chuyen cua giai quyet don
			String searchGQD = "";
			if (nguonDon != null && StringUtils.isNotBlank(nguonDon.trim())) {
				NguonTiepNhanDonEnum nguonDonEnum = NguonTiepNhanDonEnum.valueOf(nguonDon);
				if (nguonDonEnum != null) {
					if (nguonDonEnum.equals(NguonTiepNhanDonEnum.GIAO_KTDX)) {
						searchGQD = NguonTiepNhanDonEnum.GIAO_KTDX.name();
						giaiQuyetDonQuery = giaiQuyetDonQuery
								.and(QGiaiQuyetDon.giaiQuyetDon.donChuyen.isTrue());
					}
					if (nguonDonEnum.equals(NguonTiepNhanDonEnum.GIAO_TTXM)) {
						searchGQD = NguonTiepNhanDonEnum.GIAO_TTXM.name();
						giaiQuyetDonQuery = giaiQuyetDonQuery
								.and(QGiaiQuyetDon.giaiQuyetDon.laTTXM.isTrue());
					}
				}
			}
			
			Collection<GiaiQuyetDon> giaiQuyetDons = (Collection<GiaiQuyetDon>) giaiQuyetDonRepo
					.findAll(giaiQuyetDonQuery, sortOrder2);
			donCollections2 = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct()
					.collect(Collectors.toList());
			//Neu search ttxt hoac kkdx cua giai quyet don
			if (StringUtils.isNotBlank(searchGQD)) { 
				predAll = predAll.and(QDon.don.in(donCollections2));
				return predAll;
			}
			
			// for (GiaiQuyetDon gqd : giaiQuyetDons) {
			// System.out.println(gqd.getId());
			// }
			// if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			// BooleanExpression donViKiemTraDeXuat =
			// QDon.don.processType.eq(ProcessTypeEnum.KIEM_TRA_DE_XUAT)
			// .and(QDon.don.donViThamTraXacMinh.id.eq(donViXuLyXLD));
			// BooleanExpression donViThamTraXacMinh =
			// QDon.don.processType.eq(ProcessTypeEnum.THAM_TRA_XAC_MINH)
			// .and(QDon.don.donViThamTraXacMinh.id.eq(donViXuLyXLD));
			// BooleanExpression processXuLyDon =
			// QDon.don.processType.eq(ProcessTypeEnum.XU_LY_DON);
			// BooleanExpression processNull = QDon.don.processType.isNull();
			//
			// BooleanExpression predOr =
			// processNull.or(processXuLyDon).or(donViKiemTraDeXuat).or(donViThamTraXacMinh);
			// predAll = predAll.and(predOr);
			// }
			predAll = predAll.and(QDon.don.in(donCollections).or(QDon.don.in(donCollections2)));
		} else {
			if (nguonDon != null && StringUtils.isNotBlank(nguonDon.trim())) {
				NguonTiepNhanDonEnum nguonDonEnum = NguonTiepNhanDonEnum.valueOf(nguonDon);
				if (nguonDonEnum != null) {
					if (nguonDonEnum.equals(NguonTiepNhanDonEnum.GIAO_KTDX) || nguonDonEnum.equals(NguonTiepNhanDonEnum.GIAO_TTXM)) {
						donCollections.clear();
					}
				}
			}
			predAll = predAll.and(QDon.don.in(donCollections));
		}
		
//		NumberExpression<Integer> canBoXuLyChiDinh = QDon.don.canBoXuLyChiDinh.id.when(canBoXuLyXLD)					
//				.then(Expressions.numberTemplate(Integer.class, "0"))					
//				.otherwise(Expressions.numberTemplate(Integer.class, "1"));
//		OrderSpecifier<Long> sortOrderDon = QDon.don.id.desc();
//		
//		List<Don> listXuLyDon = (List<Don>) donRepo.findAll(predAll, canBoXuLyChiDinh.asc(), sortOrderDon);
//		for (Don d : listXuLyDon) {
//			System.out.println("listXuLyDon: " + d.getNoiDung());
//		}
//		
//		BooleanExpression predAllX = base.and(QDon.don.in(listXuLyDon));
//		
//		List<Don> listXuLyDon2 = (List<Don>) donRepo.findAll(predAllX);
//		for (Don d : listXuLyDon2) {
//			System.out.println("listXuLyDon: " + d.getNoiDung());
//		}
		return predAll;
	}
	
	public Predicate predicateFindAllDonTraCuu(String maDon, String diaChi, String nguonDon, String phanLoaiDon, Long linhVucId, Long linhVucChiTietId,
			String tuNgay, String denNgay,
			String tinhTrangXuLy, Long donViXuLyXLD, String hoTen, String ketQuaToanHT, 
			boolean taiDonVi, List<CoQuanQuanLy> listDonViTiepNhan,
			XuLyDonRepository xuLyRepo, DonRepository donRepo, 
			GiaiQuyetDonRepository giaiQuyetDonRepo) {	
		BooleanExpression predAll = base.and(QDon.don.thanhLapDon.eq(true));
		predAll = predAll.and(QDon.don.old.eq(false))
				.and(QDon.don.xuLyDons.isNotEmpty()
						.or(QDon.don.processType.eq(ProcessTypeEnum.KIEM_TRA_DE_XUAT)
								.or(QDon.don.processType.isNull().and(QDon.don.thanhLapDon.isTrue()))
								.and(QDon.don.xuLyDons.isEmpty())));
		
		// Query don
		if (maDon != null && StringUtils.isNotBlank(maDon.trim())) {
			predAll = predAll.and(QDon.don.maHoSo.eq(maDon.trim()));
		}

		if (hoTen != null && StringUtils.isNotBlank(hoTen.trim())) {
			List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();
			List<Don> dons = new ArrayList<Don>();
			BooleanExpression donCongDanQuery = baseDonCongDan;
			
			donCongDanQuery = donCongDanQuery
					.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON))
					.and(QDon_CongDan.don_CongDan.congDan.hoVaTenSearch.containsIgnoreCase(Utils.unAccent(hoTen.trim()))
							.or(QDon_CongDan.don_CongDan.tenCoQuan.containsIgnoreCase(hoTen.trim())));
			donCongDans = (List<Don_CongDan>) donCongDanRepo.findAll(donCongDanQuery);
			dons = donCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
			predAll = predAll.and(QDon.don.in(dons));
		}
		
		if (diaChi != null && StringUtils.isNotBlank(diaChi.trim())) {
			List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();
			List<Don> dons = new ArrayList<Don>();
			BooleanExpression donCongDanQuery = baseDonCongDan;
			
			donCongDanQuery = donCongDanQuery
					.and(QDon_CongDan.don_CongDan.diaChi.containsIgnoreCase(Utils.unAccent(diaChi.trim())));
			donCongDans = (List<Don_CongDan>) donCongDanRepo.findAll(donCongDanQuery);
			dons = donCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
			predAll = predAll.and(QDon.don.in(dons));
		}

		if (nguonDon != null && StringUtils.isNotBlank(nguonDon.trim())) {
			NguonTiepNhanDonEnum nguonDonEnum = NguonTiepNhanDonEnum.valueOf(nguonDon);
			if (nguonDonEnum != null) { 
				if (!nguonDonEnum.equals(NguonTiepNhanDonEnum.CHUYEN_DON) &&
						!nguonDonEnum.equals(NguonTiepNhanDonEnum.GIAO_TTXM) &&
						!nguonDonEnum.equals(NguonTiepNhanDonEnum.GIAO_KTDX)) {
					predAll = predAll.and(
							QDon.don.nguonTiepNhanDon.eq(NguonTiepNhanDonEnum.valueOf(StringUtils.upperCase(nguonDon)))
									.and(QDon.don.processType.ne(ProcessTypeEnum.KIEM_TRA_DE_XUAT))
									.and(QDon.don.processType.ne(ProcessTypeEnum.THAM_TRA_XAC_MINH))
									.and(QDon.don.xuLyDons.any().donChuyen.isFalse()));
				}
			}
		}
		
		if (ketQuaToanHT != null && StringUtils.isNotBlank(ketQuaToanHT.trim())) {
			KetQuaTrangThaiDonEnum ketQuaValue = KetQuaTrangThaiDonEnum.valueOf(StringUtils.upperCase(ketQuaToanHT));
			predAll = predAll.and((QDon.don.donViXuLyGiaiQuyet.id.eq(donViXuLyXLD)
						.and(QDon.don.ketQuaXLDGiaiQuyet.eq(ketQuaValue))));
			if (ketQuaValue.equals(KetQuaTrangThaiDonEnum.DINH_CHI) || ketQuaValue.equals(KetQuaTrangThaiDonEnum.DE_XUAT_THU_LY) 
					|| ketQuaValue.equals(KetQuaTrangThaiDonEnum.KHONG_DU_DIEU_KIEN_THU_LY) || ketQuaValue.equals(KetQuaTrangThaiDonEnum.KHONG_XU_LY_NEU_LY_DO)
					|| ketQuaValue.equals(KetQuaTrangThaiDonEnum.CHUYEN_DON) || ketQuaValue.equals(KetQuaTrangThaiDonEnum.TRA_DON_VA_HUONG_DAN)
					|| ketQuaValue.equals(KetQuaTrangThaiDonEnum.HUONG_DAN_VIET_LAI_DON) || ketQuaValue.equals(KetQuaTrangThaiDonEnum.LUU_DON_VA_THEO_DOI)
					|| ketQuaValue.equals(KetQuaTrangThaiDonEnum.YEU_CAU_GAP_LANH_DAO) || ketQuaValue.equals(KetQuaTrangThaiDonEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN)
					|| ketQuaValue.equals(KetQuaTrangThaiDonEnum.DA_CO_KET_QUA_TTXM) || ketQuaValue.equals(KetQuaTrangThaiDonEnum.DOI_THOAI)
					|| ketQuaValue.equals(KetQuaTrangThaiDonEnum.DA_LAP_DU_THAO) || ketQuaValue.equals(KetQuaTrangThaiDonEnum.CHO_RA_QUYET_DINH_GIAI_QUYET)
					|| ketQuaValue.equals(KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET) || ketQuaValue.equals(KetQuaTrangThaiDonEnum.LUU_HO_SO)) {
				if (tuNgay != null && denNgay != null && StringUtils.isNotBlank(tuNgay.trim())
						&& StringUtils.isNotBlank(denNgay.trim())) {
					LocalDateTime tuNgayTK = Utils.fixTuNgay(tuNgay);
					LocalDateTime denNgayTK = Utils.fixDenNgay(denNgay);
					predAll = predAll.and(QDon.don.ngayThucHienKetQuaXuLy.between(tuNgayTK, denNgayTK));
				} else if (StringUtils.isBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
					LocalDateTime denNgayTK = Utils.fixDenNgay(denNgay);
					predAll = predAll.and(QDon.don.ngayThucHienKetQuaXuLy.before(denNgayTK));
				} else if (StringUtils.isNotBlank(tuNgay) && StringUtils.isBlank(denNgay)) {
					LocalDateTime tuNgayTK = Utils.fixTuNgay(tuNgay);
					predAll = predAll.and(QDon.don.ngayThucHienKetQuaXuLy.after(tuNgayTK));
				}
			}
		}

		if (phanLoaiDon != null && StringUtils.isNotBlank(phanLoaiDon.trim())) {
			predAll = predAll.and(QDon.don.loaiDon.eq(LoaiDonEnum.valueOf(StringUtils.upperCase(phanLoaiDon))));
		}
		
		if (linhVucId != null && linhVucId > 0) {
			predAll = predAll.and(QDon.don.linhVucDonThu.id.eq(linhVucId));
		}
		
		if (linhVucChiTietId != null && linhVucChiTietId > 0) {
			predAll = predAll.and(QDon.don.linhVucDonThuChiTiet.id.eq(linhVucId));
		}
		
		if (taiDonVi) {
			predAll = predAll.and(QDon.don.donViXuLyGiaiQuyet.id.eq(donViXuLyXLD)
					.or(QDon.don.donViThamTraXacMinh.id.eq(donViXuLyXLD))
					.or(QDon.don.donViKiemTraDeXuat.id.eq(donViXuLyXLD)))
					;
		}
		
		if (listDonViTiepNhan != null && listDonViTiepNhan.size() > 0) {
			predAll = predAll.and(QDon.don.donViXuLyGiaiQuyet.in(listDonViTiepNhan));
		}
		
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false));
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false));
		
		if (tinhTrangXuLy != null && !tinhTrangXuLy.isEmpty()) {
			List<Don> donCollectionsGQD = new ArrayList<Don>();
			Collection<GiaiQuyetDon> giaiQuyetDons = (Collection<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery);
			donCollectionsGQD = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct().collect(Collectors.toList());
			donCollectionsGQD = donCollectionsGQD.stream().filter(d -> {
				if (d.getThongTinGiaiQuyetDon() == null) {
					return false;
				}
				return invalidNgayTreHanVaTinhTheoNgayKetThuc(d);
			}).collect(Collectors.toList());
			
			List<Don> donCollectionsXLD = new ArrayList<Don>();
			List<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
			Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery);
			CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
			donCollectionsXLD = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
			donCollectionsXLD = donCollectionsXLD.stream().filter(d -> {
				if (d.getNgayBatDauXLD() == null || d.getThoiHanXuLyXLD() == null) {
					return false;
				}
				return invalidNgayTreHanXLD(d);
			}).collect(Collectors.toList());
			
			if (tinhTrangXuLy.equals("TRE_HAN")) {
				predAll = predAll.and((QDon.don.processType.ne(ProcessTypeEnum.XU_LY_DON).and(QDon.don.in(donCollectionsGQD)))
						.or(QDon.don.processType.eq(ProcessTypeEnum.XU_LY_DON).and(QDon.don.in(donCollectionsXLD))));
			} else {
				predAll = predAll.and(QDon.don.notIn(donCollectionsGQD).and(QDon.don.notIn(donCollectionsXLD)));
			}
		}
		return predAll;
	}
	
	private boolean invalidNgayTreHanVaTinhTheoNgayKetThuc(Don don) {
		ThongTinGiaiQuyetDon ttgqd = don.getThongTinGiaiQuyetDon();
		
		if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() != null) {
			if (ttgqd.getNgayKetThucGiaiQuyet() != null) {
				return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
			} else {
				return Utils.isValidNgayTreHan(ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet());
			}
		} else if (ttgqd.getNgayBatDauGiaiQuyet() != null && ttgqd.getNgayHetHanGiaiQuyet() != null && ttgqd.getNgayHetHanSauKhiGiaHanGiaiQuyet() == null) {
			if (ttgqd.getNgayKetThucGiaiQuyet() != null) {
				return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(ttgqd.getNgayHetHanGiaiQuyet(), ttgqd.getNgayKetThucGiaiQuyet());
			} else {
				return Utils.isValidNgayTreHan(ttgqd.getNgayHetHanGiaiQuyet());
			}
		}
		return false;
	}
	
	private boolean invalidNgayTreHanXLD(Don don) {
		if (don.getNgayBatDauXLD() != null && don.getThoiHanXuLyXLD() != null && don.getNgayKetThucXLD() != null) {
			return Utils.isValidNgayTreHanTinhTheoNgayKetThuc(don.getThoiHanXuLyXLD(), don.getNgayKetThucXLD());
		} else if (don.getNgayBatDauXLD() != null && don.getThoiHanXuLyXLD() != null && don.getNgayKetThucXLD() == null) {
			return Utils.isValidNgayTreHan(don.getThoiHanXuLyXLD());
		}
		return false;
	}

	public Predicate predicateFindAllGQD(String maDon, String tuKhoa, String nguonDon, String phanLoaiDon, String tiepNhanTuNgay,
			String tiepNhanDenNgay, boolean thanhLapDon, String tinhTrangGiaiQuyet, String trangThaiDon,
			Long phongBanGiaiQuyetId, Long donViGiaiQuyetId, Long canBoGiaiQuyetId, String chucVu, String hoTen, String trangThaiDonToanHT, String ketQuaToanHT,
			GiaiQuyetDonRepository giaiQuyetDonRepo, XuLyDonRepository xuLyRepo) {
		BooleanExpression predAll = base
				.and(QDon.don.old.eq(false))
				.and(QDon.don.thanhLapDon.eq(thanhLapDon));
		
		// Query don
		if (StringUtils.isNotBlank(maDon)) {
			predAll = predAll.and(QDon.don.ma.eq(StringUtils.trimToEmpty(maDon)));
		}

		if (StringUtils.isNotBlank(hoTen)) {
			List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();
			List<Don> dons = new ArrayList<Don>();
			List<Long> donIds = new ArrayList<Long>();
			BooleanExpression donCongDanQuery = baseDonCongDan;
			
//			predAll = predAll
//					.and(QDon.don.donCongDans.any().congDan.hoVaTenSearch.containsIgnoreCase(Utils.unAccent(hoTen.trim()))
//							.or(QDon.don.donCongDans.any().tenCoQuan.containsIgnoreCase(hoTen)))
//					.and(QDon.don.donCongDans.any().phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON));
			
			donCongDanQuery = donCongDanQuery
					.and(QDon_CongDan.don_CongDan.hoVaTenSearch.containsIgnoreCase(Utils.unAccent(hoTen.trim()))
							.or(QDon_CongDan.don_CongDan.tenCoQuan.containsIgnoreCase(hoTen.trim())))
					.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON));
					
			donCongDans = (List<Don_CongDan>) donCongDanRepo.findAll(donCongDanQuery);
			dons = donCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
			donIds.addAll(dons.stream().map(d -> {
				return d.getId();
			}).distinct().collect(Collectors.toList()));
			
			predAll = predAll.and(QDon.don.in(dons).or(QDon.don.donGocId.in(donIds)));
		}
		
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();
			List<Don> dons = new ArrayList<Don>();
			List<Long> donIds = new ArrayList<Long>();
			BooleanExpression donCongDanQuery = baseDonCongDan;
			
//			predAll = predAll
//					.and(QDon.don.donCongDans.any().congDan.hoVaTenSearch.containsIgnoreCase(Utils.unAccent(hoTen.trim()))
//							.or(QDon.don.donCongDans.any().tenCoQuan.containsIgnoreCase(tuKhoa.trim()))
//							.or(QDon.don.donCongDans.any().soCMNDHoChieu.containsIgnoreCase(tuKhoa.trim())))
//					.and(QDon.don.donCongDans.any().phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON));
			
			donCongDanQuery = donCongDanQuery
					.and(QDon_CongDan.don_CongDan.hoVaTenSearch.containsIgnoreCase(Utils.unAccent(tuKhoa.trim()))
							.or(QDon_CongDan.don_CongDan.tenCoQuan.containsIgnoreCase(tuKhoa.trim()))
							.or(QDon_CongDan.don_CongDan.soCMNDHoChieu.eq(tuKhoa.trim())))
					.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON));
			
			
			donCongDans = (List<Don_CongDan>) donCongDanRepo.findAll(donCongDanQuery);
			dons = donCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
			donIds.addAll(dons.stream().map(d -> {
				return d.getId();
			}).distinct().collect(Collectors.toList()));
			
			predAll = predAll.and(QDon.don.in(dons).or(QDon.don.donGocId.in(donIds)));
		}

		if (StringUtils.isNotBlank(nguonDon)) {
			NguonTiepNhanDonEnum nguonDonEnum = NguonTiepNhanDonEnum.valueOf(nguonDon);
			if (nguonDonEnum != null) {
				if (!nguonDonEnum.equals(NguonTiepNhanDonEnum.CHUYEN_DON) &&
						!nguonDonEnum.equals(NguonTiepNhanDonEnum.GIAO_TTXM) &&
						!nguonDonEnum.equals(NguonTiepNhanDonEnum.GIAO_KTDX)) {
					predAll = predAll.and(
							QDon.don.nguonTiepNhanDon.eq(NguonTiepNhanDonEnum.valueOf(StringUtils.upperCase(nguonDon)))
									.and(QDon.don.processType.ne(ProcessTypeEnum.KIEM_TRA_DE_XUAT))
									.and(QDon.don.processType.ne(ProcessTypeEnum.THAM_TRA_XAC_MINH))
									.and(QDon.don.thongTinGiaiQuyetDon.giaiQuyetDons.any().donChuyen.isFalse()));
				}
			}
		}

		if (StringUtils.isNotBlank(phanLoaiDon)) {
			predAll = predAll.and(QDon.don.loaiDon.eq(LoaiDonEnum.valueOf(StringUtils.upperCase(phanLoaiDon))));
		}
		
		if (trangThaiDonToanHT != null && StringUtils.isNotBlank(trangThaiDonToanHT.trim())) {
			TrangThaiDonEnum trangThaiDonValue = TrangThaiDonEnum.valueOf(StringUtils.upperCase(trangThaiDonToanHT));
			predAll = predAll.and((QDon.don.donViXuLyGiaiQuyet.id.eq(donViGiaiQuyetId)
						.and(QDon.don.trangThaiXLDGiaiQuyet.eq(trangThaiDonValue)))
					.or(QDon.don.donViThamTraXacMinh.id.eq(donViGiaiQuyetId)
							.and(QDon.don.trangThaiTTXM.eq(trangThaiDonValue))));
		}
		
		if (ketQuaToanHT != null && StringUtils.isNotBlank(ketQuaToanHT.trim())) {
			KetQuaTrangThaiDonEnum ketQuaValue = KetQuaTrangThaiDonEnum.valueOf(StringUtils.upperCase(ketQuaToanHT));
			predAll = predAll.and((QDon.don.donViXuLyGiaiQuyet.id.eq(donViGiaiQuyetId)
						.and(QDon.don.ketQuaXLDGiaiQuyet.eq(ketQuaValue))));
		}

		if (StringUtils.isNotBlank(tinhTrangGiaiQuyet)) {
			predAll = predAll
					.and(QDon.don.trangThaiDon.eq(TrangThaiDonEnum.valueOf(StringUtils.upperCase(tinhTrangGiaiQuyet))));
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

		// if (phongBanGiaiQuyetId != null && phongBanGiaiQuyetId.longValue() >
		// 0) {
		// predAll =
		// predAll.and(QDon.don.phongBanGiaiQuyet.id.eq(phongBanGiaiQuyetId));
		// }
		
		// Query Giai quyet don
		BooleanExpression giaiQuyetDonQuery = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false)
				.and(QGiaiQuyetDon.giaiQuyetDon.old.eq(false));

		if (StringUtils.isNotBlank(trangThaiDon)) {
			giaiQuyetDonQuery = giaiQuyetDonQuery
					.and(QGiaiQuyetDon.giaiQuyetDon.tinhTrangGiaiQuyet.stringValue().eq(trangThaiDon));
		}

		if (phongBanGiaiQuyetId != null && phongBanGiaiQuyetId.longValue() > 0) {
			BooleanExpression pbgq = QGiaiQuyetDon.giaiQuyetDon.phongBanGiaiQuyet.id.eq(phongBanGiaiQuyetId);
			BooleanExpression chucVuNotNull = QGiaiQuyetDon.giaiQuyetDon.chucVu.isNotNull();
			BooleanExpression chucVuNull = QGiaiQuyetDon.giaiQuyetDon.chucVu.isNull();
			BooleanExpression dvgq = QGiaiQuyetDon.giaiQuyetDon.donViGiaiQuyet.id.eq(donViGiaiQuyetId);
			giaiQuyetDonQuery = giaiQuyetDonQuery.and((dvgq.and(chucVuNull)).or(pbgq.and(chucVuNotNull)));
		}

		if (StringUtils.isNotBlank(chucVu)) {
			giaiQuyetDonQuery = giaiQuyetDonQuery
					.and(QGiaiQuyetDon.giaiQuyetDon.chucVu.eq(VaiTroEnum.valueOf(StringUtils.upperCase(chucVu))).or(QGiaiQuyetDon.giaiQuyetDon.chucVu.isNull()));

			if (VaiTroEnum.CHUYEN_VIEN.equals(VaiTroEnum.valueOf(StringUtils.upperCase(chucVu)))) {
				giaiQuyetDonQuery = giaiQuyetDonQuery
						.and(QGiaiQuyetDon.giaiQuyetDon.canBoXuLyChiDinh.id.eq(canBoGiaiQuyetId).or(QGiaiQuyetDon.giaiQuyetDon.chucVu.isNull()));
			}
		}
		
		if (nguonDon != null && StringUtils.isNotBlank(nguonDon.trim())) {
			NguonTiepNhanDonEnum nguonDonEnum = NguonTiepNhanDonEnum.valueOf(nguonDon);
			if (nguonDonEnum != null) { 
				if (nguonDonEnum.equals(NguonTiepNhanDonEnum.GIAO_KTDX)) {
					giaiQuyetDonQuery = giaiQuyetDonQuery
							.and(QGiaiQuyetDon.giaiQuyetDon.donChuyen.isTrue());
				}
				if (nguonDonEnum.equals(NguonTiepNhanDonEnum.GIAO_TTXM)) {
					giaiQuyetDonQuery = giaiQuyetDonQuery
							.and(QGiaiQuyetDon.giaiQuyetDon.laTTXM.isTrue());
				}
			}
		}
		
		List<Don> donCollections = new ArrayList<Don>();
		OrderSpecifier<Integer> sortOrder = QGiaiQuyetDon.giaiQuyetDon.thuTuThucHien.desc();
		Collection<GiaiQuyetDon> giaiQuyetDons = (Collection<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery,
				sortOrder);
		donCollections = giaiQuyetDons.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct()
				.collect(Collectors.toList());
		predAll = predAll.and(QDon.don.in(donCollections));
		
		
		// Query don chuyen xu ly don
		List<Don> donCollectionXLDs = new ArrayList<Don>();
		List<Don> donCollectionGQDs = new ArrayList<Don>();
		BooleanExpression xuLyDonQuery = QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.old.eq(false));
		if (nguonDon != null && StringUtils.isNotBlank(nguonDon.trim())) {
			NguonTiepNhanDonEnum nguonDonEnum = NguonTiepNhanDonEnum.valueOf(nguonDon);
			if (nguonDonEnum != null) { 
				if (nguonDonEnum.equals(NguonTiepNhanDonEnum.CHUYEN_DON)) { 
					Long donViXuLyXLD = 0L;
					Long phongBanGiaiQuyetXLD = phongBanGiaiQuyetId.longValue();
					HuongXuLyXLDEnum huongXuLyEnum = HuongXuLyXLDEnum.DE_XUAT_THU_LY;
					xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.huongXuLy.eq(huongXuLyEnum));
					
					if (StringUtils.isNotBlank(chucVu) && ("VAN_THU".equals(chucVu) || "LANH_DAO".equals(chucVu))) {
						donViXuLyXLD = phongBanGiaiQuyetXLD.longValue();
						phongBanGiaiQuyetXLD = 0L;
					}
					
					if (donViXuLyXLD != null && donViXuLyXLD > 0) {
						xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
					}
					
					if (phongBanGiaiQuyetXLD != null && phongBanGiaiQuyetXLD > 0) {
						xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanGiaiQuyet.id.eq(phongBanGiaiQuyetXLD));
					}

					if (StringUtils.isNotBlank(tinhTrangGiaiQuyet)) {
						TrangThaiDonEnum trangThaiXuLyDon = TrangThaiDonEnum.DA_XU_LY;
						xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.trangThaiDon.eq(trangThaiXuLyDon));
					}
					
					xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donChuyen.isTrue());
					
					Collection<XuLyDon> xldCollections = new ArrayList<XuLyDon>();
					Iterable<XuLyDon> xuLyDons = xuLyRepo.findAll(xuLyDonQuery);
					CollectionUtils.addAll(xldCollections, xuLyDons.iterator());
					donCollectionXLDs = xldCollections.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
					
					giaiQuyetDonQuery = giaiQuyetDonQuery
							.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.in(donCollectionXLDs));
					Collection<GiaiQuyetDon> GQDs = (Collection<GiaiQuyetDon>) giaiQuyetDonRepo.findAll(giaiQuyetDonQuery,
							sortOrder);
					
					donCollectionGQDs = GQDs.stream().map(d -> d.getThongTinGiaiQuyetDon().getDon()).distinct()
							.collect(Collectors.toList());
					predAll = predAll.and(QDon.don.in(donCollectionGQDs));
				}
			}
		}
		
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
	
	public String getMaHoSo(DonRepository repo, Long donId) {
		String maHoSo = "MHS" + Utils.getMaDon();
		boolean flagTonTai = true;
		while (flagTonTai) {
			flagTonTai = isMaDonExists(repo, donId, null, maHoSo);
			if (flagTonTai) {
				maHoSo = "MHS" + Utils.getMaDon();
			}
		}
		return maHoSo;
	}

	public String getMaDon(DonRepository repo, Long donId) {
		String maDon = Utils.getMaDon();
		boolean flagTonTai = true;
		while (flagTonTai) {
			flagTonTai = isMaDonExists(repo, donId, maDon, null);
			if (flagTonTai) {
				maDon = Utils.getMaDon();
			}
		}
		return maDon;
	}

	public boolean isMaDonExists(DonRepository repo, Long donId, String maDon, String maHS) {
		if (donId != null && donId > 0) {
			if (maHS != null) {
				Predicate predicate = base.and(QDon.don.id.ne(donId)).and(QDon.don.maHoSo.eq(maHS));
				return repo.exists(predicate);
			} else {
				Predicate predicate = base.and(QDon.don.id.ne(donId)).and(QDon.don.ma.eq(maDon));
				return repo.exists(predicate);
			}
		} else {
			if (maHS != null) {
				Predicate predicate = base.and(QDon.don.maHoSo.eq(maHS));
				return repo.exists(predicate);
			} else {
				Predicate predicate = base.and(QDon.don.ma.eq(maDon));
				return repo.exists(predicate);
			}
		}
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
			don.setNgayLapDonGapLanhDaoTmp(Utils.localDateTimeNow());
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
	
	public Predicate predicateFindDonYeuCauGapLanhDaoDinhKy(String tuKhoa, String tuNgay, String denNgay, String phanLoai, String nguonDon, String trangThai,  
			Long donViId) {
		BooleanExpression predAll = base
				.and(QDon.don.yeuCauGapTrucTiepLanhDao.eq(true).and(QDon.don.thanhLapDon.eq(false)))
				.or(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO)
						.and(QDon.don.thanhLapDon.eq(true)))
				//.and(QDon.don.thanhLapTiepDanGapLanhDao.eq(false))
				.and(QDon.don.old.eq(false));
		
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll
					.and(QDon.don.donCongDans.any().congDan.hoVaTenSearch.containsIgnoreCase(Utils.unAccent(tuKhoa.trim()))
							.or(QDon.don.donCongDans.any().tenCoQuan.containsIgnoreCase(tuKhoa.trim()))
							.or(QDon.don.donCongDans.any().soCMNDHoChieu.eq(tuKhoa.trim())))
					.and(QDon.don.donCongDans.any().phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON));
		}
		
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
		
		if (donViId != null && donViId > 0) {
			predAll = predAll.and(QDon.don.donViTiepDan.id.eq(donViId));
		}
		
		if (phanLoai != null && !"".equals(phanLoai)) {
			predAll = predAll.and(QDon.don.loaiDon.eq(LoaiDonEnum.valueOf(phanLoai)));
		}

		if (nguonDon != null && !"".equals(nguonDon)) {
			predAll = predAll.and(QDon.don.nguonTiepNhanDon.eq(NguonTiepNhanDonEnum.valueOf(nguonDon)));
		}
		
		if (trangThai != null && !"".equals(trangThai)) {
			predAll = predAll.and(QDon.don.trangThaiYeuCauGapLanhDao.eq(TrangThaiYeuCauGapLanhDaoEnum.valueOf(trangThai)));
		}
		
		return predAll;
	}

	public Predicate predicateFindDonYeuCauGapLanhDao(String tuNgay, String denNgay, String loaiDon, Long linhVucId, Long linhVucChiTietChaId, Long linhVucChiTietConId, 
			Long donViId) {
		BooleanExpression predAll = base
				.and(QDon.don.yeuCauGapTrucTiepLanhDao.eq(true).and(QDon.don.thanhLapDon.eq(false)))
				.or(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO)
						.and(QDon.don.thanhLapDon.eq(true)))
				.and(QDon.don.thanhLapTiepDanGapLanhDao.eq(false))
				.and(QDon.don.old.eq(false))
				.and(QDon.don.trangThaiYeuCauGapLanhDao.eq(TrangThaiYeuCauGapLanhDaoEnum.CHO_XIN_Y_KIEN));
		
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
		
		if (donViId != null && donViId > 0) {
			predAll = predAll.and(QDon.don.donViTiepDan.id.eq(donViId));
		}
		
		if (loaiDon != null && !"".equals(loaiDon)) {
			predAll = predAll.and(QDon.don.loaiDon.eq(LoaiDonEnum.valueOf(loaiDon)));
		}

		if ((linhVucId != null && linhVucId > 0) && ((linhVucChiTietChaId == null && linhVucChiTietChaId == null))) {
			predAll = predAll.and(QDon.don.linhVucDonThu.id.eq(linhVucId));
		}
		
		if ((linhVucChiTietChaId != null && linhVucChiTietChaId > 0) && (linhVucId != null && linhVucId > 0) && 
				(linhVucChiTietConId == null)) { 
			predAll = predAll
					.and(QDon.don.linhVucDonThu.id.eq(linhVucId))
					.and(QDon.don.linhVucDonThuChiTiet.id.eq(linhVucChiTietChaId));
		}
		
		if ((linhVucChiTietConId != null && linhVucChiTietConId > 0) && (linhVucId != null && linhVucId > 0) && 
				(linhVucChiTietChaId != null && linhVucChiTietChaId > 0)) { 
			predAll = predAll
					.and(QDon.don.linhVucDonThu.id.eq(linhVucId))
					.and(QDon.don.linhVucDonThuChiTiet.id.eq(linhVucChiTietChaId));
//					.and(QDon.don.chiTietLinhVucDonThuChiTiet.id.eq(linhVucChiTietConId));
		}
		return predAll;
	}
	
	public List<PropertyChangeObject> getListThayDoi(Don donNew, Don donOld) {
		List<PropertyChangeObject> list = new ArrayList<PropertyChangeObject>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		if (donNew.getNoiDung() != null && !donNew.getNoiDung().isEmpty() && donOld.getNoiDung() != null
				&& !donNew.getNoiDung().equals(donOld.getNoiDung())) {
			list.add(new PropertyChangeObject("Nội dung", donOld.getNoiDung(), donNew.getNoiDung()));
		}
		if (donNew.getYeuCauCuaCongDan() != null
				&& !donNew.getYeuCauCuaCongDan().equals(donOld.getYeuCauCuaCongDan())) {
			list.add(new PropertyChangeObject("Yêu cầu của công dân", donOld.getYeuCauCuaCongDan(),
					donNew.getYeuCauCuaCongDan()));
		}
		if (donNew.getHuongGiaiQuyetDaThucHien() != null
				&& !donNew.getHuongGiaiQuyetDaThucHien().equals(donOld.getHuongGiaiQuyetDaThucHien())) {
			list.add(new PropertyChangeObject("Hướng giải quyết đã thực hiện", donOld.getHuongGiaiQuyetDaThucHien(),
					donNew.getHuongGiaiQuyetDaThucHien()));
		}
		if (donNew.getyKienXuLyDon() != null && !donNew.getyKienXuLyDon().equals(donOld.getyKienXuLyDon())) {
			list.add(new PropertyChangeObject("Ý kiến xử lý đơn", donOld.getyKienXuLyDon(), donNew.getyKienXuLyDon()));
		}
		if (donNew.getGhiChuXuLyDon() != null && !donNew.getGhiChuXuLyDon().equals(donOld.getGhiChuXuLyDon())) {
			list.add(new PropertyChangeObject("Ghi chú xử lý đơn", donOld.getGhiChuXuLyDon(),
					donNew.getGhiChuXuLyDon()));
		}
		if (donNew.getLyDoDinhChi() != null && !donNew.getLyDoDinhChi().equals(donOld.getLyDoDinhChi())) {
			list.add(new PropertyChangeObject("Lý do đình chỉ", donOld.getLyDoDinhChi(), donNew.getLyDoDinhChi()));
		}
		if (donNew.getSoQuyetDinhDinhChi() != null
				&& !donNew.getSoQuyetDinhDinhChi().equals(donOld.getSoQuyetDinhDinhChi())) {
			list.add(new PropertyChangeObject("Số quyết định đình chỉ", donOld.getSoQuyetDinhDinhChi(),
					donNew.getSoQuyetDinhDinhChi()));
		}
		if (donNew.getSoVanBanDaGiaiQuyet() != null
				&& !donNew.getSoVanBanDaGiaiQuyet().equals(donOld.getSoVanBanDaGiaiQuyet())) {
			list.add(new PropertyChangeObject("Số văn bản đã giải quyết", donOld.getSoVanBanDaGiaiQuyet(),
					donNew.getSoVanBanDaGiaiQuyet()));
		}
		if (donNew.getSoLanKhieuNaiToCao() != donOld.getSoLanKhieuNaiToCao()) {
			list.add(new PropertyChangeObject("Số lần khiếu nại tố cáo", donOld.getSoLanKhieuNaiToCao() + "",
					donNew.getSoLanKhieuNaiToCao() + ""));
		}
		if (donNew.getSoNguoi() != donOld.getSoNguoi()) {
			list.add(new PropertyChangeObject("Số người tham gia", donOld.getSoNguoi() + "", donNew.getSoNguoi() + ""));
		}
		if (donNew.isCoUyQuyen() != donOld.isCoUyQuyen()) {
			list.add(new PropertyChangeObject("Có ủy quyền", donOld.isCoUyQuyen() ? "Có" : "Không",
					donNew.isCoUyQuyen() ? "Có" : "Không"));
		}
		if (donNew.isThanhLapDon() != donOld.isThanhLapDon()) {
			list.add(new PropertyChangeObject("Thành lập đơn", donOld.isThanhLapDon() ? "Có" : "Không",
					donNew.isThanhLapDon() ? "Có" : "Không"));
		}
		if (donNew.getNgayTiepNhan() != null && !donNew.getNgayTiepNhan().format(formatter)
				.equals(donOld.getNgayTiepNhan() != null ? donOld.getNgayTiepNhan().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày tiếp nhận đơn",
					donOld.getNgayTiepNhan() != null ? donOld.getNgayTiepNhan().format(formatter) : "",
					donNew.getNgayTiepNhan().format(formatter)));
		}
		if (donNew.getNgayQuyetDinhDinhChi() != null && !donNew.getNgayQuyetDinhDinhChi().format(formatter).equals(
				donOld.getNgayQuyetDinhDinhChi() != null ? donOld.getNgayQuyetDinhDinhChi().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày quyết định đình chỉ",
					donOld.getNgayQuyetDinhDinhChi() != null ? donOld.getNgayQuyetDinhDinhChi().format(formatter) : "",
					donNew.getNgayQuyetDinhDinhChi().format(formatter)));
		}
		if (donNew.getThoiHanXuLyXLD() != null && !donNew.getThoiHanXuLyXLD().format(formatter)
				.equals(donOld.getThoiHanXuLyXLD() != null ? donOld.getThoiHanXuLyXLD().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Thời hạn xử lý XLĐ",
					donOld.getThoiHanXuLyXLD() != null ? donOld.getThoiHanXuLyXLD().format(formatter) : "",
					donNew.getThoiHanXuLyXLD().format(formatter)));
		}
		if (donNew.getNgayBatDauXLD() != null && !donNew.getNgayBatDauXLD().format(formatter)
				.equals(donOld.getNgayBatDauXLD() != null ? donOld.getNgayBatDauXLD().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày bắt đầu XLĐ",
					donOld.getNgayBatDauXLD() != null ? donOld.getNgayBatDauXLD().format(formatter) : "",
					donNew.getNgayBatDauXLD().format(formatter)));
		}
		if (donNew.getNgayKetThucXLD() != null && !donNew.getNgayKetThucXLD().format(formatter)
				.equals(donOld.getNgayKetThucXLD() != null ? donOld.getNgayKetThucXLD().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày kết thúc xử lý đơn",
					donOld.getNgayKetThucXLD() != null ? donOld.getNgayKetThucXLD().format(formatter) : "",
					donNew.getNgayKetThucXLD().format(formatter)));
		}
		if (donNew.getNgayBanHanhVanBanDaGiaiQuyet() != null && !donNew.getNgayBanHanhVanBanDaGiaiQuyet()
				.format(formatter).equals(donOld.getNgayBanHanhVanBanDaGiaiQuyet() != null
						? donOld.getNgayBanHanhVanBanDaGiaiQuyet().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày ban hành văn bản đã giải quyết",
					donOld.getNgayBanHanhVanBanDaGiaiQuyet() != null
							? donOld.getNgayBanHanhVanBanDaGiaiQuyet().format(formatter) : "",
					donNew.getNgayBanHanhVanBanDaGiaiQuyet().format(formatter)));
		}

		if (donNew.getLinhVucDonThu() != null && donNew.getLinhVucDonThu() != donOld.getLinhVucDonThu()) {
			LinhVucDonThu linhVucDonThuNew = lichVuDonThuRepo.findOne(donNew.getLinhVucDonThu().getId());
			list.add(new PropertyChangeObject("Lĩnh vực đơn thư",
					donOld.getLinhVucDonThu() != null ? donOld.getLinhVucDonThu().getTen() : "",
					linhVucDonThuNew.getTen()));
		}
		if ((donNew.getLinhVucDonThuChiTiet() == null && donOld.getLinhVucDonThuChiTiet() != null)
				|| (donNew.getLinhVucDonThuChiTiet() != null && donOld.getLinhVucDonThuChiTiet() == null)
				|| (donNew.getLinhVucDonThuChiTiet() != donOld.getLinhVucDonThuChiTiet())) {
			LinhVucDonThu linhVucDonThuNew = lichVuDonThuRepo
					.findOne(donNew.getLinhVucDonThuChiTiet() != null ? donNew.getLinhVucDonThuChiTiet().getId() : 0L);
			list.add(new PropertyChangeObject("Lĩnh vực đơn thư chi tiết",
					donOld.getLinhVucDonThuChiTiet() != null ? donOld.getLinhVucDonThuChiTiet().getTen() : "",
					linhVucDonThuNew != null ? linhVucDonThuNew.getTen() : ""));
		}
		if (donNew.getLinhVucChiTietKhac() != null && !donNew.getLinhVucChiTietKhac().isEmpty() && donOld.getLinhVucChiTietKhac() != null
				&& !donNew.getLinhVucChiTietKhac().equals(donOld.getLinhVucChiTietKhac())) {
			list.add(new PropertyChangeObject("Lĩnh vực chi tiết khác", donOld.getLinhVucChiTietKhac(), donNew.getLinhVucChiTietKhac()));
		}
//		if ((donNew.getChiTietLinhVucDonThuChiTiet() == null && donOld.getChiTietLinhVucDonThuChiTiet() != null)
//				|| (donNew.getChiTietLinhVucDonThuChiTiet() != null && donOld.getChiTietLinhVucDonThuChiTiet() == null)
//				|| donNew.getChiTietLinhVucDonThuChiTiet() != donOld.getChiTietLinhVucDonThuChiTiet()) {
//			LinhVucDonThu linhVucDonThuNew = lichVuDonThuRepo.findOne(donNew.getChiTietLinhVucDonThuChiTiet().getId());
//			list.add(new PropertyChangeObject(
//					"Chi tiết lĩnh vực đơn thư chi tiết", donOld.getChiTietLinhVucDonThuChiTiet() != null
//							? donOld.getChiTietLinhVucDonThuChiTiet().getTen() : "",
//					linhVucDonThuNew != null ? linhVucDonThuNew.getTen() : ""));
//		}
		if ((donNew.getThamQuyenGiaiQuyet() == null && donOld.getThamQuyenGiaiQuyet() != null)
				|| (donNew.getThamQuyenGiaiQuyet() != null && donOld.getThamQuyenGiaiQuyet() == null)
				|| (donNew.getThamQuyenGiaiQuyet() != donOld.getThamQuyenGiaiQuyet())) {
			ThamQuyenGiaiQuyet thamQuyenGiaiQuyet = thamQuyenGiaiQuyetRepo
					.findOne(donNew.getThamQuyenGiaiQuyet() != null ? donNew.getThamQuyenGiaiQuyet().getId() : 0L);
			list.add(new PropertyChangeObject("Thẩm quyền giải quyết",
					donOld.getThamQuyenGiaiQuyet() != null ? donOld.getThamQuyenGiaiQuyet().getTen() : "",
					thamQuyenGiaiQuyet != null ? thamQuyenGiaiQuyet.getTen() : ""));
		}
		if ((donNew.getCoQuanDaGiaiQuyet() == null && donOld.getCoQuanDaGiaiQuyet() != null)
				|| (donNew.getCoQuanDaGiaiQuyet() != null && donOld.getCoQuanDaGiaiQuyet() == null)
				|| (donNew.getCoQuanDaGiaiQuyet() != donOld.getCoQuanDaGiaiQuyet())) {
			CoQuanQuanLy coQuanQuanLy = coQuanQuanLyRepository
					.findOne(donNew.getCoQuanDaGiaiQuyet() != null ? donNew.getCoQuanDaGiaiQuyet().getId() : 0L);
			list.add(new PropertyChangeObject("Cơ quan đã giải quyết",
					donOld.getCoQuanDaGiaiQuyet() != null ? donOld.getCoQuanDaGiaiQuyet().getTen() : "",
					coQuanQuanLy != null ? coQuanQuanLy.getTen() : ""));
		}
		if (donNew.getLoaiDon() != null && !donNew.getLoaiDon().equals(donOld.getLoaiDon())) {
			list.add(new PropertyChangeObject("Loại đơn",
					donOld.getLoaiDon() != null ? donOld.getLoaiDon().getText() : "",
					donNew.getLoaiDon() != null ? donNew.getLoaiDon().getText() : ""));
		}
		if (donNew.getLoaiDoiTuong() != null && !donNew.getLoaiDoiTuong().equals(donOld.getLoaiDoiTuong())) {
			list.add(new PropertyChangeObject("Loại đối tượng",
					donOld.getLoaiDoiTuong() != null ? donOld.getLoaiDoiTuong().getText() : "",
					donNew.getLoaiDoiTuong() != null ? donNew.getLoaiDoiTuong().getText() : ""));
		}

		if (donNew.getNguonTiepNhanDon() != null
				&& !donNew.getNguonTiepNhanDon().equals(donOld.getNguonTiepNhanDon())) {
			list.add(new PropertyChangeObject("Nguồn tiếp nhận đơn",
					donOld.getNguonTiepNhanDon() != null ? donOld.getNguonTiepNhanDon().getText() : "",
					donNew.getNguonTiepNhanDon() != null ? donNew.getNguonTiepNhanDon().getText() : ""));
		}

		if (donNew.getLoaiNguoiDungDon() != null
				&& !donNew.getLoaiNguoiDungDon().equals(donOld.getLoaiNguoiDungDon())) {
			list.add(new PropertyChangeObject("Loại người đứng đơn",
					donOld.getLoaiNguoiDungDon() != null ? donOld.getLoaiNguoiDungDon().getText() : "",
					donNew.getLoaiNguoiDungDon() != null ? donNew.getLoaiNguoiDungDon().getText() : ""));
		}
		
		if (donNew.getTrangThaiYeuCauGapLanhDao() != null && !donNew.getTrangThaiYeuCauGapLanhDao().equals(donOld.getTrangThaiYeuCauGapLanhDao())) {
			list.add(new PropertyChangeObject("Trạng thái yêu cầu gặp lãnh đạo",
					donOld.getTrangThaiYeuCauGapLanhDao() != null ? donOld.getTrangThaiYeuCauGapLanhDao().getText() : "",
					donNew.getTrangThaiYeuCauGapLanhDao() != null ? donNew.getTrangThaiYeuCauGapLanhDao().getText() : ""));
		}

		if (donNew.getLyDoThayDoiTTYeuCauGapLanhDao() != null && !donNew.getLyDoThayDoiTTYeuCauGapLanhDao().equals(donOld.getLyDoThayDoiTTYeuCauGapLanhDao())) {
			list.add(new PropertyChangeObject("Lý do thay đổi trạng thái", donOld.getLyDoThayDoiTTYeuCauGapLanhDao(), donNew.getLyDoThayDoiTTYeuCauGapLanhDao()));
		}
		
		return list;
	}

	public Don save(Don obj, Long congChucId) {
		return Utils.save(donRepo, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(Don obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(donRepo, obj, congChucId, eass, status);		
	}
}
