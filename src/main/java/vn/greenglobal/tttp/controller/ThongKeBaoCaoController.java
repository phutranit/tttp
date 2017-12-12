package vn.greenglobal.tttp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.dsl.BooleanExpression;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.CanCuThanhTraLaiEnum;
import vn.greenglobal.tttp.enums.HinhThucThanhTraEnum;
import vn.greenglobal.tttp.enums.HinhThucThongKeEnum;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.LinhVucThanhTraEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiTiepDanEnum;
import vn.greenglobal.tttp.enums.LoaiVuViecEnum;
import vn.greenglobal.tttp.enums.ThongKeBaoCaoLoaiKyEnum;
import vn.greenglobal.tttp.enums.TienDoThanhTraEnum;
import vn.greenglobal.tttp.model.CapCoQuanQuanLy;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CuocThanhTra;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.QCuocThanhTra;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.ThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.repository.CapCoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CuocThanhTraRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.repository.LinhVucDonThuRepository;
import vn.greenglobal.tttp.repository.ThamQuyenGiaiQuyetRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.service.CoQuanQuanLyService;
import vn.greenglobal.tttp.service.LinhVucDonThuService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.service.ThongKeBaoCaoTongHopKQGQDService;
import vn.greenglobal.tttp.service.ThongKeBaoCaoTongHopKQTCDService;
import vn.greenglobal.tttp.service.ThongKeBaoCaoTongHopKQXLDService;
import vn.greenglobal.tttp.service.ThongKeTongHopThanhTraService;
import vn.greenglobal.tttp.util.ExcelUtil;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "thongKeBaoCaos", description = "Thống kê báo cáo")
public class ThongKeBaoCaoController extends TttpController<Don> {
	
	@Autowired
	XuLyDonRepository xldrepo;

	@Autowired
	private ThamSoRepository repoThamSo;

	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepo;

	@Autowired
	private CapCoQuanQuanLyRepository capCoQuanQuanLyRepo;

	@Autowired
	private LinhVucDonThuRepository linhVucDonThuRepo;

	@Autowired
	private ThamQuyenGiaiQuyetRepository thamQuyenGiaiQuyetRepo;

	@Autowired
	private LinhVucDonThuService linhVucDonThuService;

	@Autowired
	private CuocThanhTraRepository cuocThanhTraRepo;

	@Autowired
	private ThamSoService thamSoService;

	@Autowired
	private ThongKeBaoCaoTongHopKQTCDService thongKeBaoCaoTongHopKQTCDService;

	@Autowired
	private ThongKeBaoCaoTongHopKQXLDService thongKeBaoCaoTongHopKQXLDService;

	@Autowired
	private ThongKeBaoCaoTongHopKQGQDService thongKeBaoCaoTongHopKQGQDService;

	@Autowired
	private ThongKeTongHopThanhTraService thongKeTongHopThanhTraService;

	@Autowired
	private CoQuanQuanLyService coQuanQuanLyService;

	public ThongKeBaoCaoController(BaseRepository<Don, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaTiepCongDan")
	@ApiOperation(value = "Tổng hợp kết quả tiếp công dân", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaTiepCongDan(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			PersistentEntityResourceAssembler eass) {
		try {

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(), 
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			Long donViXuLy = Long
					.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());

			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();

			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

			if (year == null || year == 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}

			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQTCDService
					.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais.add(55L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachs.add(5L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs.add(53L);

			List<LinhVucDonThu> linhVucTranhChapVeDatDais = new ArrayList<LinhVucDonThu>();
			linhVucTranhChapVeDatDais.addAll(linhVucDonThuService
					.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais));

			List<LinhVucDonThu> linhVucVeChinhSachs = new ArrayList<LinhVucDonThu>();
			linhVucVeChinhSachs.addAll(
					linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeChinhSachs));

			List<LinhVucDonThu> linhVucVeCheDoCCVCs = new ArrayList<LinhVucDonThu>();
			linhVucVeCheDoCCVCs.addAll(
					linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs));

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(54L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(57L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(58L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(59L);

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

			List<LinhVucDonThu> linhVucLienQuanDenDatDais = new ArrayList<LinhVucDonThu>();
			linhVucLienQuanDenDatDais.addAll(linhVucDonThuService
					.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais));

			List<LinhVucDonThu> linhVucCTVHXHKKhacs = new ArrayList<LinhVucDonThu>();
			linhVucCTVHXHKKhacs.addAll(linhVucDonThuService
					.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs));

			List<LinhVucDonThu> linhVucVeChinhSachCCVCs = new ArrayList<LinhVucDonThu>();
			linhVucVeChinhSachCCVCs.addAll(
					linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs));

			List<LinhVucDonThu> linhVucVeNhaCuaTaiSans = new ArrayList<LinhVucDonThu>();
			linhVucVeNhaCuaTaiSans.addAll(
					linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));

			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));

			Map<String, Object> mapTongCong = new HashMap<>();
			Long tongCongTiepCongDanThuongXuyenLuot1 = 0L;
			Long tongCongTiepCongDanThuongXuyenNguoi2 = 0L;
			Long tongCongTiepCongDanThuongXuyenVuViecCu3 = 0L;
			Long tongCongTiepCongDanThuongXuyenVuViecMoiPhatSinh4 = 0L;
			Long tongCongTiepCongDanThuongXuyenDoanDongNguoiSoDoan5 = 0L;
			Long tongCongTiepCongDanThuongXuyenDoanDongNguoiSoNguoi6 = 0L;
			Long tongCongTiepCongDanThuongXuyenDoanDongNguoiVuViecCu7 = 0L;
			Long tongCongTiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh8 = 0L;
			Long tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoLuot9 = 0L;
			Long tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoNguoi10 = 0L;
			Long tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu11 = 0L;
			Long tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh12 = 0L;
			Long tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan13 = 0L;
			Long tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi14 = 0L;
			Long tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu15 = 0L;
			Long tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh16 = 0L;
			Long tongCongTongVuViecDonKhieuNaiLinhVucHanhChinh17 = 0L;
			Long tongCongDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai18 = 0L;
			Long tongCongDonKhieuNaiLinhVucHanhChinhVeNhaTaiSan19 = 0L;
			Long tongCongDonKhieuNaiLinhVucHanhChinhVeChinhSachCCVC20 = 0L;
			Long tongCongDonKhieuNaiLinhVucHanhChinhVeChinhCTVHXHKKhac21 = 0L;
			Long tongCongDonKhieuNaiLinhVucTuPhap22 = 0L;
			Long tongCongDonKhieuNaiLinhVucVeDang23 = 0L;
			Long tongVuViecDonToCao24 = 0L;
			Long tongCongDonToCaoLinhVucHanhChinh25 = 0L;
			Long tongCongDonToCaoLinhVucTuPhap26 = 0L;
			Long tongCongDonToCaoLinhVucThamNhung27 = 0L;
			Long tongCongDonToCaoLinhVucVeDang28 = 0L;
			Long tongCongDonToCaoLinhVucKhac29 = 0L;
			Long tongCongPhanAnhKienNghiKhac30 = 0L;
			Long tongCongChuaDuocGiaiQuyet31 = 0L;
			Long tongCongChuaCoQuyetDinhGiaiQuyet32 = 0L;
			Long tongCongDaCoQuyetDinhGiaiQuyet33 = 0L;
			Long tongCongDaCoBanAnCuaToa34 = 0L;

			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null && listDonVis.size() > 0) {
					for (CoQuanQuanLy dv : listDonVis) {
						if (dv != null) {
							CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
							if (coQuan != null) {
								list.add(coQuan);
							}
						}
					}
					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null && listCapDonVis.size() > 0) {
					List<Long> thamSos = new ArrayList<Long>();
					thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

					for (CapCoQuanQuanLy cdv : listCapDonVis) {
						CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
						if (capDonVi != null) {
							listCapCQs.add(capDonVi.getId());
						}
					}
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));

					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			}

			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSTCDDonVi = predAllDSTCD;
				if (cq.getCapCoQuanQuanLy().getId().equals(Long.valueOf(thamSoUBNDTP.getGiaTri().toString()))) {
					predAllDSTCDDonVi = predAllDSTCDDonVi
							.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(cq.getId()));
				} else {
					predAllDSTCDDonVi = predAllDSTCDDonVi.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id
							.eq(cq.getId()).or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(cq.getId()))
							.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(cq.getId())));
				}

				BooleanExpression predAllDSTCDThuongXuyen = predAllDSTCDDonVi;
				predAllDSTCDThuongXuyen = predAllDSTCDThuongXuyen
						.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.THUONG_XUYEN));

				predAllDSTCDThuongXuyen = predAllDSTCDThuongXuyen
						.and(QSoTiepCongDan.soTiepCongDan.don.yeuCauGapTrucTiepLanhDao.isFalse());

				BooleanExpression predAllDSTCDLanhDao = predAllDSTCDDonVi;
				predAllDSTCDLanhDao = predAllDSTCDLanhDao
						.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DINH_KY)
								.or(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DOT_XUAT)));

				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());

				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("donVi", mapDonVi);

				// tiep cong dan thuong xuyen
				// luoc - 1
				Long tiepCongDanThuongXuyenLuot = thongKeBaoCaoTongHopKQTCDService
						.getTongSoLuocTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen);
				tongCongTiepCongDanThuongXuyenLuot1 += tiepCongDanThuongXuyenLuot;
				mapMaSo.put("tiepCongDanThuongXuyenLuot", tiepCongDanThuongXuyenLuot);

				// nguoi - 2
				Long tiepCongDanThuongXuyenNguoi = thongKeBaoCaoTongHopKQTCDService
						.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, false, false);
				tongCongTiepCongDanThuongXuyenNguoi2 += tiepCongDanThuongXuyenNguoi;
				mapMaSo.put("tiepCongDanThuongXuyenNguoi", tiepCongDanThuongXuyenNguoi);

				// vu viec cu - 3
				Long tiepCongDanThuongXuyenVuViecCu = thongKeBaoCaoTongHopKQTCDService
						.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, false, LoaiVuViecEnum.CU);
				tongCongTiepCongDanThuongXuyenVuViecCu3 += tiepCongDanThuongXuyenVuViecCu;
				mapMaSo.put("tiepCongDanThuongXuyenVuViecCu", tiepCongDanThuongXuyenVuViecCu);

				// vu viec moi phat sinh - 4
				Long tiepCongDanThuongXuyenVuViecMoiPhatSinh = thongKeBaoCaoTongHopKQTCDService
						.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, false,
								LoaiVuViecEnum.MOI_PHAT_SINH);
				tongCongTiepCongDanThuongXuyenVuViecMoiPhatSinh4 += tiepCongDanThuongXuyenVuViecMoiPhatSinh;
				mapMaSo.put("tiepCongDanThuongXuyenVuViecMoiPhatSinh", tiepCongDanThuongXuyenVuViecMoiPhatSinh);

				// doan dong nguoi
				// so doan - 5
				Long tiepCongDanThuongXuyenDoanDongNguoiSoDoan = thongKeBaoCaoTongHopKQTCDService
						.getTongSoDoanDongNguoiTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true);
				tongCongTiepCongDanThuongXuyenDoanDongNguoiSoDoan5 += tiepCongDanThuongXuyenDoanDongNguoiSoDoan;
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiSoDoan", tiepCongDanThuongXuyenDoanDongNguoiSoDoan);

				// nguoi - 6
				Long tiepCongDanThuongXuyenDoanDongNguoiSoNguoi = thongKeBaoCaoTongHopKQTCDService
						.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, false, true);
				tongCongTiepCongDanThuongXuyenDoanDongNguoiSoNguoi6 += tiepCongDanThuongXuyenDoanDongNguoiSoNguoi;
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiSoNguoi", tiepCongDanThuongXuyenDoanDongNguoiSoNguoi);

				// doan dong nguoi - vu viec
				// cu - 7
				Long tiepCongDanThuongXuyenDoanDongNguoiVuViecCu = thongKeBaoCaoTongHopKQTCDService
						.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true, LoaiVuViecEnum.CU);
				tongCongTiepCongDanThuongXuyenDoanDongNguoiVuViecCu7 += tiepCongDanThuongXuyenDoanDongNguoiVuViecCu;
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiVuViecCu", tiepCongDanThuongXuyenDoanDongNguoiVuViecCu);

				// moi phat sinh - 8
				Long tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh = thongKeBaoCaoTongHopKQTCDService
						.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true,
								LoaiVuViecEnum.MOI_PHAT_SINH);
				tongCongTiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh8 += tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh;
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh",
						tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh);

				// tiep cong dan dinh ky dot xuat cua lanh dao
				// luoc - 9
				Long tiepCongDanDinhKyDotXuatCuaLanhDaoLuot = thongKeBaoCaoTongHopKQTCDService
						.getTongSoLuocTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao);
				tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoLuot9 += tiepCongDanDinhKyDotXuatCuaLanhDaoLuot;
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoLuot", tiepCongDanDinhKyDotXuatCuaLanhDaoLuot);

				// nguoi - 10
				Long tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi = thongKeBaoCaoTongHopKQTCDService
						.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao);
				tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoNguoi10 += tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi;
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi", tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi);

				// tiep cong dan cua lanh dao - vu viec
				// cu - 11
				Long tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu = thongKeBaoCaoTongHopKQTCDService
						.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, false, LoaiVuViecEnum.CU);
				tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu11 += tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu;
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu", tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu);

				// moi phat sinh - 12
				Long tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh = thongKeBaoCaoTongHopKQTCDService
						.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, false,
								LoaiVuViecEnum.MOI_PHAT_SINH);
				tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh12 += tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh;
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh",
						tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh);

				// tiep cong dan cua lanh dao - doan dong nguoi
				// so doan - 13
				Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan = thongKeBaoCaoTongHopKQTCDService
						.getTongSoDoanDongNguoiTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true);
				tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan13 += tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan;
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan",
						tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan);

				// so nguoi - 14
				Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi = thongKeBaoCaoTongHopKQTCDService
						.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, false, true);
				tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi14 += tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi;
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi",
						tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi);

				// tiep cong dan cua lanh dao - doan dong nguoi - vu viec
				// cu - 15
				Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu = thongKeBaoCaoTongHopKQTCDService
						.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true, LoaiVuViecEnum.CU);
				tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu15 += tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu;
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu",
						tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu);

				// moi phat sinh - 16
				Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh = thongKeBaoCaoTongHopKQTCDService
						.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true,
								LoaiVuViecEnum.MOI_PHAT_SINH);
				tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh16 += tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh;
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh",
						tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh);

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

				// tong - 17
				tongCongTongVuViecDonKhieuNaiLinhVucHanhChinh17 += tongVuViecDonKhieuNaiLinhVucHanhChinh17;
				mapMaSo.put("tongVuViecDonKhieuNaiLinhVucHanhChinh", tongVuViecDonKhieuNaiLinhVucHanhChinh17);

				// lien quan dat dai - 18
				tongCongDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai18 += tongCongDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai18;
				mapMaSo.put("donKhieuNaiLinhVucHanhChinhLienQuanDenDatDai",
						tongSoVuViecKhieuNaiLinhVucHanhChinhLienQuanDenDatDai18);

				// ve nha tai san - 19
				tongCongDonKhieuNaiLinhVucHanhChinhVeNhaTaiSan19 += tongSoVuViecKhieuNaiLinhVucHanhChinhVeNhaTaiSan19;
				mapMaSo.put("donKhieuNaiLinhVucHanhChinhVeNhaTaiSan",
						tongSoVuViecKhieuNaiLinhVucHanhChinhVeNhaTaiSan19);

				// ve chinh sach che do cc, vc - 20
				tongCongDonKhieuNaiLinhVucHanhChinhVeChinhSachCCVC20 += tongSoVuViecKhieuNaiLinhVucHanhChinhVeChinhSachCCVC20;
				mapMaSo.put("donKhieuNaiLinhVucHanhChinhVeChinhSachCCVC",
						tongSoVuViecKhieuNaiLinhVucHanhChinhVeChinhSachCCVC20);

				// ve chinh sach che do cc, vc - 21
				tongCongDonKhieuNaiLinhVucHanhChinhVeChinhCTVHXHKKhac21 += tongSoVuViecKhieuNaiLinhVucHanhChinhCTVHXHKKhac21;
				mapMaSo.put("donKhieuNaiLinhVucHanhChinhVeChinhCTVHXHKKhac",
						tongSoVuViecKhieuNaiLinhVucHanhChinhCTVHXHKKhac21);

				// linh Vuc Tu Phap - 22
				Long donKhieuNaiLinhVucTuPhap = thongKeBaoCaoTongHopKQTCDService
						.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi,
								linhVucHanhChinhKhieuNaiTuPhap);
				tongCongDonKhieuNaiLinhVucTuPhap22 += donKhieuNaiLinhVucTuPhap;
				mapMaSo.put("donKhieuNaiLinhVucTuPhap", donKhieuNaiLinhVucTuPhap);

				// ve dang - 23
				Long donKhieuNaiLinhVucVeDang = thongKeBaoCaoTongHopKQTCDService
						.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi,
								linhVucHanhChinhKhieuNaiVeDang);
				tongCongDonKhieuNaiLinhVucVeDang23 += donKhieuNaiLinhVucVeDang;
				mapMaSo.put("donKhieuNaiLinhVucVeDang", donKhieuNaiLinhVucVeDang);

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

				// tong so vu viec don to cao - 24
				tongVuViecDonToCao24 += tongVuViecDonToCaoLinhVucHanhChinh18;
				mapMaSo.put("tongVuViecDonToCao", tongVuViecDonToCaoLinhVucHanhChinh18);

				// tong so vu viec don to cao linh vuc hanh chinh - 25
				tongCongDonToCaoLinhVucHanhChinh25 += donToCaoLinhVucHanhChinh;
				mapMaSo.put("donToCaoLinhVucHanhChinh", donToCaoLinhVucHanhChinh);

				// tong so vu viec don to cao linh vuc tu phap - 26
				tongCongDonToCaoLinhVucTuPhap26 += donToCaoLinhVucTuPhap;
				mapMaSo.put("donToCaoLinhVucTuPhap", donToCaoLinhVucTuPhap);

				// tong so vu viec don to cao linh vuc tham nhung - 27
				tongCongDonToCaoLinhVucThamNhung27 += donToCaoLinhVucThamNhung;
				mapMaSo.put("donToCaoLinhVucThamNhung", donToCaoLinhVucThamNhung);

				// tong so vu viec don to cao linh vuc ve dang - 28
				tongCongDonToCaoLinhVucVeDang28 += donToCaoLinhVucVeDang;
				mapMaSo.put("donToCaoLinhVucVeDang", donToCaoLinhVucVeDang);

				// tong so vu viec don to cao linh vuc khac - 29
				tongCongDonToCaoLinhVucKhac29 += donToCaoLinhVucKhac;
				mapMaSo.put("donToCaoLinhVucKhac", donToCaoLinhVucKhac);

				// //ve tranh chap - 17
				// mapMaSo.put("linhVucVeTranhChap",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi,
				// linhVucTranhChapVeDatDais));
				//
				// //veChinhSach - 18
				// mapMaSo.put("linhVucVeChinhSach",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi,
				// linhVucVeChinhSachs));
				//
				// //veNhaCuaVaTaiSan - 19
				// mapMaSo.put("linhVucVeNhaCuaVaTaiSan",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi,
				// linhVucVeNhaCuaTaiSans));
				//
				// //veCheDoCC,VC - 20
				// mapMaSo.put("linhVucVeCheDoCCVC",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi,
				// linhVucVeCheDoCCVCs));
				//
				// //linhVucTuPhap - 21
				// mapMaSo.put("linhVucTuPhapDonKhieuNai",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi,
				// linhVucKhieuNaiTuPhap));
				//
				// //linhVucChinhTriVanHoaXaHoi - 22
				// mapMaSo.put("linhVucChinhTriVanHoaXaHoiKhac",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi,
				// linhVucChinhTriVanHoaXaHoiKhac));
				//
				// //don to cao
				// //linhVucHanhChinh - 23
				// mapMaSo.put("linhVucHanhChinh",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucDonThu(predAllDSTCDDonVi,
				// linhVucHanhChinh));
				//
				// //linhVucHanhChinh - 24
				// mapMaSo.put("linhVucTuPhapDonToCao",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucDonThu(predAllDSTCDDonVi,
				// linhVucTuPhap));
				//
				// //linhVucThamNhung - 25
				// mapMaSo.put("linhVucThamNhung",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucDonThu(predAllDSTCDDonVi,
				// linhVucThamNhung));

				// don kien nghi phan anh
				// kienNghiPhanAnh - 30
				Long phanAnhKienNghiKhac = thongKeBaoCaoTongHopKQTCDService
						.getTongSoDonKienNghiPhanAnhHXLLuuDonVaTheoDoi(predAllDSTCDDonVi);
				tongCongPhanAnhKienNghiKhac30 += phanAnhKienNghiKhac;
				mapMaSo.put("phanAnhKienNghiKhac", phanAnhKienNghiKhac);

				// ket qua tiep dan
				// chua duoc giai quyet - 31
				Long chuaDuocGiaiQuyet = 0L;
				tongCongChuaDuocGiaiQuyet31 += chuaDuocGiaiQuyet;
				// mapMaSo.put("chuaDuocGiaiQuyet",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonChuaChotHuongXuLyXLD(predAllDSTCDDonVi));
				mapMaSo.put("chuaDuocGiaiQuyet", chuaDuocGiaiQuyet);

				// da duoc giai quyet
				// chua co quyet dinh giai quyet - 32
				Long chuaCoQuyetDinhGiaiQuyet = 0L;
				tongCongChuaCoQuyetDinhGiaiQuyet32 += chuaCoQuyetDinhGiaiQuyet;
				// mapMaSo.put("chuaCoQuyetDinhGiaiQuyet",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonChuaCoQuyetDinhGiaiQuyet(predAllDSTCDDonVi));
				mapMaSo.put("chuaCoQuyetDinhGiaiQuyet", chuaCoQuyetDinhGiaiQuyet);

				// da co quyet dinh giai quyet - 33
				Long daCoQuyetDinhGiaiQuyet = 0L;
				tongCongDaCoQuyetDinhGiaiQuyet33 += daCoQuyetDinhGiaiQuyet;
				// mapMaSo.put("daCoQuyetDinhGiaiQuyet",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonDaCoQuyetDinhGiaiQuyet(predAllDSTCDDonVi));
				mapMaSo.put("daCoQuyetDinhGiaiQuyet", daCoQuyetDinhGiaiQuyet);

				// da co ban an cua toa - 34
				Long daCoBanAnCuaToa = 0L;
				tongCongDaCoBanAnCuaToa34 += daCoBanAnCuaToa;
				mapMaSo.put("daCoBanAnCuaToa", daCoBanAnCuaToa);

				// da co ban an cua toa - 35
				mapMaSo.put("ghiChu", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
				mapDonVi = new HashMap<String, Object>();
			}

			mapTongCong.put("tiepCongDanThuongXuyenLuot", tongCongTiepCongDanThuongXuyenLuot1);
			mapTongCong.put("tiepCongDanThuongXuyenNguoi", tongCongTiepCongDanThuongXuyenNguoi2);
			mapTongCong.put("tiepCongDanThuongXuyenVuViecCu", tongCongTiepCongDanThuongXuyenVuViecCu3);
			mapTongCong.put("tiepCongDanThuongXuyenVuViecMoiPhatSinh",
					tongCongTiepCongDanThuongXuyenVuViecMoiPhatSinh4);
			mapTongCong.put("tiepCongDanThuongXuyenDoanDongNguoiSoDoan",
					tongCongTiepCongDanThuongXuyenDoanDongNguoiSoDoan5);
			mapTongCong.put("tiepCongDanThuongXuyenDoanDongNguoiSoNguoi",
					tongCongTiepCongDanThuongXuyenDoanDongNguoiSoNguoi6);
			mapTongCong.put("tiepCongDanThuongXuyenDoanDongNguoiVuViecCu",
					tongCongTiepCongDanThuongXuyenDoanDongNguoiVuViecCu7);
			mapTongCong.put("tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh",
					tongCongTiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh8);
			mapTongCong.put("tiepCongDanDinhKyDotXuatCuaLanhDaoLuot", tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoLuot9);
			mapTongCong.put("tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi",
					tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoNguoi10);
			mapTongCong.put("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu",
					tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu11);
			mapTongCong.put("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh",
					tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh12);
			mapTongCong.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan",
					tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan13);
			mapTongCong.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi",
					tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi14);
			mapTongCong.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu",
					tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu15);
			mapTongCong.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh",
					tongCongTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh16);
			mapTongCong.put("tongVuViecDonKhieuNaiLinhVucHanhChinh", tongCongTongVuViecDonKhieuNaiLinhVucHanhChinh17);
			mapTongCong.put("donKhieuNaiLinhVucHanhChinhLienQuanDenDatDai",
					tongCongDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai18);
			mapTongCong.put("donKhieuNaiLinhVucHanhChinhVeNhaTaiSan", tongCongDonKhieuNaiLinhVucHanhChinhVeNhaTaiSan19);
			mapTongCong.put("donKhieuNaiLinhVucHanhChinhVeChinhSachCCVC",
					tongCongDonKhieuNaiLinhVucHanhChinhVeChinhSachCCVC20);
			mapTongCong.put("donKhieuNaiLinhVucHanhChinhVeChinhCTVHXHKKhac",
					tongCongDonKhieuNaiLinhVucHanhChinhVeChinhCTVHXHKKhac21);
			mapTongCong.put("donKhieuNaiLinhVucTuPhap", tongCongDonKhieuNaiLinhVucTuPhap22);
			mapTongCong.put("donKhieuNaiLinhVucVeDang", tongCongDonKhieuNaiLinhVucVeDang23);
			mapTongCong.put("tongVuViecDonToCao", tongVuViecDonToCao24);
			mapTongCong.put("donToCaoLinhVucHanhChinh", tongCongDonToCaoLinhVucHanhChinh25);
			mapTongCong.put("donToCaoLinhVucTuPhap", tongCongDonToCaoLinhVucTuPhap26);
			mapTongCong.put("donToCaoLinhVucThamNhung", tongCongDonToCaoLinhVucThamNhung27);
			mapTongCong.put("donToCaoLinhVucVeDang", tongCongDonToCaoLinhVucVeDang28);
			mapTongCong.put("donToCaoLinhVucKhac", tongCongDonToCaoLinhVucKhac29);
			mapTongCong.put("phanAnhKienNghiKhac", tongCongPhanAnhKienNghiKhac30);
			mapTongCong.put("chuaDuocGiaiQuyet", tongCongChuaDuocGiaiQuyet31);
			mapTongCong.put("chuaCoQuyetDinhGiaiQuyet", tongCongChuaCoQuyetDinhGiaiQuyet32);
			mapTongCong.put("daCoQuyetDinhGiaiQuyet", tongCongDaCoQuyetDinhGiaiQuyet33);
			mapTongCong.put("daCoBanAnCuaToa", tongCongDaCoBanAnCuaToa34);

			map.put("tongCongs", mapTongCong);
			map.put("maSos", maSos);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaGiaiQuyetDonKhieuNai")
	@ApiOperation(value = "Tổng hợp kết quả kết quả giải quyết khiếu nại", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaGiaiQuyetDonKhieuNai(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			PersistentEntityResourceAssembler eass) {
		try {

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Long donViXuLy = Long
					.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());

			Map<String, Object> mapDonVi = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			ThamSo phongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo phuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			Long idCapCQQLPhongBan = Long.valueOf(phongBan.getGiaTri().toString());
			Long idCapCQQLPhuongXa = Long.valueOf(phuongXa.getGiaTri().toString());
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			Map<String, Object> mapTongCong = new HashMap<>();

			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));

			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

			if (year == null || year == 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}

			Long tongCongTongSoDonKhieuNai1 = 0L;
			Long tongCongDonNhanTrongKyBaoCao2 = 0L;
			Long tongCongDonTonKyTruocChuyenSang3 = 0L;
			Long tongCongTongSoVuViec4 = 0L;
			Long tongCongSoDonThuocThamQuyen5 = 0L;
			Long tongCongSoVuViecThuocThamQuyen6 = 0L;
			Long tongCongSoVuViecGiaiQuyetBangQDHanhChinh7 = 0L;
			Long tongCongSoVuViecRutDonThongQuaGiaiThichThuyetPhuc8 = 0L;
			Long tongCongKhieuNaiDung9 = 0L;
			Long tongCongKhieuNaiSai10 = 0L;
			Long tongCongKhieuNaiDungMotPhan11 = 0L;
			Long tongCongGiaiQuyetLan112 = 0L;
			Long tongCongCongNhanQuyetDinhGiaiQuyetLan113 = 0L;
			Long tongCongHuySuaQuyetDinhGiaiQuyetLan114 = 0L;
			Long tongCongKienNghiThuHoiChoNhaNuocTien15 = 0L;
			Long tongCongKienNghiThuHoiChoNhaNuocDat16 = 0L;
			Long tongCongTraLaiChoCongDanTien17 = 0L;
			Long tongCongTraLaiChoCongDanDat18 = 0L;
			Long tongCongSoNguoiDuocTraLaiQuyenLoi19 = 0L;
			Long tongCongKienNghiXuLyHanhChinhTongSoNguoi20 = 0L;
			Long tongCongKienNghiXuLyHanhChinhSoNguoiDaBiXuLy21 = 0L;
			Long tongTongSoVuChuyenCoQuanDieuTra22 = 0L;
			Long tongCongSoDoiTuongChuyenCoQuanDieuTra23 = 0L;
			Long tongCongSoVuDaKhoiTo24 = 0L;
			Long tongCongSoDoiTuongDaKhoiTo25 = 0L;
			Long tongCongSoVuViecGiaiQuyetDungThoiHan26 = 0L;
			Long tongCongSoVuViecGiaiQuyetQuaThoiHan27 = 0L;
			Long tongCongTongSoQuyetDinhPhaiToChucThucHien28 = 0L;
			Long tongCongTongSoQuyetDinhPhaiToChucThucHienDaThucHien29 = 0L;
			Long tongCongTienPhaiThuChoNhaNuoc30 = 0L;
			Long tongCongDatPhaiThuChoNhaNuoc31 = 0L;
			Long tongCongTienDaThuChoNhaNuoc32 = 0L;
			Long tongCongDatDaThuChoNhaNuoc33 = 0L;
			Long tongCongTienPhaiTraChoCongDan34 = 0L;
			Long tongCongDatPhaiTraChoCongDan35 = 0L;
			Long tongCongTienDaTraChoCongDan36 = 0L;
			Long tongCongDatDaTraChoCongDan37 = 0L;

			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null && listDonVis.size() > 0) {
					for (CoQuanQuanLy dv : listDonVis) {
						if (dv != null) {
							CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
							if (coQuan != null) {
								list.add(coQuan);
							}
						}
					}
					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null && listCapDonVis.size() > 0) {
					List<Long> thamSos = new ArrayList<Long>();
					thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

					for (CapCoQuanQuanLy cdv : listCapDonVis) {
						CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
						if (capDonVi != null) {
							listCapCQs.add(capDonVi.getId());
						}
					}
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));

					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			}

			BooleanExpression predAllDSDon = (BooleanExpression) thongKeBaoCaoTongHopKQGQDService
					.predicateFindAllGQD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSDonTrongKy = (BooleanExpression) thongKeBaoCaoTongHopKQGQDService
					.predicateFindAllGQDTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSDonTruocKy = (BooleanExpression) thongKeBaoCaoTongHopKQGQDService
					.predicateFindAllGQDTruocKy(loaiKy, quy, year, month, tuNgay, denNgay);
			predAllDSDon = predAllDSDon.and(QDon.don.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
					.and(QDon.don.thanhLapDon.eq(true)).and(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.DE_XUAT_THU_LY));
			predAllDSDonTrongKy = predAllDSDonTrongKy.and(QDon.don.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
					.and(QDon.don.thanhLapDon.eq(true)).and(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.DE_XUAT_THU_LY));
			predAllDSDonTruocKy = predAllDSDonTruocKy.and(QDon.don.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
					.and(QDon.don.thanhLapDon.eq(true)).and(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.DE_XUAT_THU_LY));

			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSGQDDonVi = predAllDSDon;
				BooleanExpression predAllDSGQDDonViTrongKy = predAllDSDonTrongKy;
				BooleanExpression predAllDSGQDDonViTruocKy = predAllDSDonTruocKy;
				predAllDSGQDDonVi = predAllDSGQDDonVi.and(QDon.don.donViXuLyGiaiQuyet.id.eq(cq.getId())
						.or(QDon.don.donViXuLyGiaiQuyet.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))
						.or(QDon.don.donViXuLyGiaiQuyet.cha.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa)))));
				
				predAllDSGQDDonViTrongKy = predAllDSGQDDonViTrongKy.and(QDon.don.donViXuLyGiaiQuyet.id.eq(cq.getId())
						.or(QDon.don.donViXuLyGiaiQuyet.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))
						.or(QDon.don.donViXuLyGiaiQuyet.cha.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa)))));
				
				predAllDSGQDDonViTruocKy = predAllDSGQDDonViTruocKy.and(QDon.don.donViXuLyGiaiQuyet.id.eq(cq.getId())
						.or(QDon.don.donViXuLyGiaiQuyet.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))
						.or(QDon.don.donViXuLyGiaiQuyet.cha.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa)))));

				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				mapMaSo.put("donVi", mapDonVi);

//				Long donNhanTrongKyBaoCao = thongKeBaoCaoTongHopKQGQDService
//						.getTongSoDonTrongKyBaoCao(predAllDSGQDDonVi, loaiKy, quy, year, month, tuNgay, denNgay);

				Long donNhanTrongKyBaoCao = thongKeBaoCaoTongHopKQGQDService
						.getTongSoDonTrongKyBaoCao(predAllDSGQDDonViTrongKy);
				
				// ThongKeBaoCaoLoaiKyEnum loaiKyEnum =
				// ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
				// Long donTonKyTruocChuyenSang =
				// loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON) &&
				// StringUtils.isBlank(tuNgay) ?
				// 0 :
				// thongKeBaoCaoTongHopKQGQDService.getTongSoDonTonKyTruoc(predAllDSGQDDonVi,
				// loaiKy, quy, year, month, tuNgay, denNgay);

//				Long donThuLyKyTruocChuyenSang1 = loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON)
//						&& StringUtils.isBlank(tuNgay) ? 0
//								: thongKeBaoCaoTongHopKQGQDService.getTongSoDonTonKyTruoc(predAllDSGQDDonViTruocKy,
//										loaiKy, quy, year, month, tuNgay, denNgay);
				
//				Long soDonThuocThamQuyen5 = loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON)
//						&& StringUtils.isBlank(tuNgay) ? 0
//								: thongKeBaoCaoTongHopKQGQDService.getTongSoDonKhieuNaiThuocThamQuyenCuaKyTruoc(
//										predAllDSGQDDonViTruocKy, loaiKy, quy, year, month, tuNgay, denNgay);
				
//				Long soDonThuocThamQuyen5 = loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON)
//						&& StringUtils.isBlank(tuNgay) ? 0
//								: thongKeBaoCaoTongHopKQGQDService.getTongSoDonKhieuNaiThuocThamQuyen(predAllDSGQDDonVi);
				
//				Long donTonKyTruocChuyenSang = donThuLyKyTruocChuyenSang1 - soDonThuocThamQuyen5;
//				Long tongSoDonKhieuNai = donNhanTrongKyBaoCao + donTonKyTruocChuyenSang;


				Long donTonKyTruocChuyenSang = thongKeBaoCaoTongHopKQGQDService.getTongSoDonTonKyTruoc(predAllDSGQDDonViTruocKy);
				Long tongSoDonKhieuNai = donNhanTrongKyBaoCao + donTonKyTruocChuyenSang;
				
				// 1 - Tong so don khieu nai
				// mapMaSo.put("tongSoDonKhieuNai",
				// thongKeBaoCaoTongHopKQGQDService.getTongSoDon(predAllDSGQDDonVi));
				tongCongTongSoDonKhieuNai1 += tongSoDonKhieuNai;
				mapMaSo.put("tongSoDonKhieuNai", tongSoDonKhieuNai);

				// 2 - Don nhan trong ky bao cao
				tongCongDonNhanTrongKyBaoCao2 += donNhanTrongKyBaoCao;
				mapMaSo.put("donNhanTrongKyBaoCao", donNhanTrongKyBaoCao);

				// 3 - Don ton ky truoc chuyen sang
				tongCongDonTonKyTruocChuyenSang3 += donTonKyTruocChuyenSang;
				mapMaSo.put("donTonKyTruocChuyenSang", donTonKyTruocChuyenSang);

				// 4 - Tong so vu viec
				Long tongSoVuViec = thongKeBaoCaoTongHopKQGQDService.getTongSoVuViec(predAllDSGQDDonVi);
				tongCongTongSoVuViec4 += tongSoVuViec;
				mapMaSo.put("tongSoVuViec", tongSoVuViec);

				// 5 - So don thuoc tham quyen
				Long soDonThuocThamQuyen = thongKeBaoCaoTongHopKQGQDService
						.getTongSoDonKhieuNaiThuocThamQuyen(predAllDSGQDDonVi);
				tongCongSoDonThuocThamQuyen5 += soDonThuocThamQuyen;
				mapMaSo.put("soDonThuocThamQuyen", soDonThuocThamQuyen);

				Long soVuViecRutDonThongQuaGiaiThichThuyetPhuc8 = thongKeBaoCaoTongHopKQGQDService
						.getTongSoDonDinhChi(predAllDSGQDDonVi);
				Long khieuNaiDung9 = thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiDung(predAllDSGQDDonVi);
				Long khieuNaiSai10 = thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiSai(predAllDSGQDDonVi);
				Long khieuNaiDungMotPhan11 = thongKeBaoCaoTongHopKQGQDService
						.getTongSoVuViecDungMotPhan(predAllDSGQDDonVi);
				Long soVuViecGiaiQuyetBangQDHanhChinh7 = khieuNaiDung9 + khieuNaiSai10 + khieuNaiDungMotPhan11;
				Long soVuViecThuocThamQuyen6 = soVuViecGiaiQuyetBangQDHanhChinh7
						+ soVuViecRutDonThongQuaGiaiThichThuyetPhuc8;

				// 6 - So vu viec thuoc tham quyen
				// mapMaSo.put("soVuViecThuocThamQuyen",
				// thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiThuocThamQuyen(predAllDSGQDDonVi));
				tongCongSoVuViecThuocThamQuyen6 += soVuViecThuocThamQuyen6;
				mapMaSo.put("soVuViecThuocThamQuyen", soVuViecThuocThamQuyen6);

				// 7 - So vu viec giai quyet bang QD hanh chinh
				// mapMaSo.put("soVuViecGiaiQuyetBangQDHanhChinh",
				// thongKeBaoCaoTongHopKQGQDService.getTongSoDonCoQuyetDinh(predAllDSGQDDonVi));
				tongCongSoVuViecGiaiQuyetBangQDHanhChinh7 += soVuViecGiaiQuyetBangQDHanhChinh7;
				mapMaSo.put("soVuViecGiaiQuyetBangQDHanhChinh", soVuViecGiaiQuyetBangQDHanhChinh7);

				// 8 - So vu viec rut don thong qua giai thich, thuyet phuc
				// mapMaSo.put("soVuViecRutDonThongQuaGiaiThichThuyetPhuc",
				// thongKeBaoCaoTongHopKQGQDService.getTongSoDonDinhChi(predAllDSGQDDonVi));
				tongCongSoVuViecRutDonThongQuaGiaiThichThuyetPhuc8 += soVuViecRutDonThongQuaGiaiThichThuyetPhuc8;
				mapMaSo.put("soVuViecRutDonThongQuaGiaiThichThuyetPhuc", soVuViecRutDonThongQuaGiaiThichThuyetPhuc8);

				// 9 - Khieu nai dung
				// mapMaSo.put("khieuNaiDung",
				// thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiDung(predAllDSGQDDonVi));
				tongCongKhieuNaiDung9 += khieuNaiDung9;
				mapMaSo.put("khieuNaiDung", khieuNaiDung9);

				// 10 - Khieu nai sai
				// mapMaSo.put("khieuNaiSai",
				// thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiSai(predAllDSGQDDonVi));
				tongCongKhieuNaiSai10 += khieuNaiSai10;
				mapMaSo.put("khieuNaiSai", khieuNaiSai10);

				// 11 - Khieu nai dung 1 phan
				// mapMaSo.put("khieuNaiDungMotPhan",thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecDungMotPhan(predAllDSGQDDonVi));
				tongCongKhieuNaiDungMotPhan11 += khieuNaiDungMotPhan11;
				mapMaSo.put("khieuNaiDungMotPhan", khieuNaiDungMotPhan11);

				// 12 - Giai quyet lan 1
				Long giaiQuyetLan1 = thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetLan1(predAllDSGQDDonVi);
				tongCongGiaiQuyetLan112 += giaiQuyetLan1;
				mapMaSo.put("giaiQuyetLan1", giaiQuyetLan1);

				// 13 - Cong nhan quyet dinh giai quyet lan 1
				Long congNhanQuyetDinhGiaiQuyetLan1 = thongKeBaoCaoTongHopKQGQDService
						.getTongSoVuViecGiaiQuyetLan2CongNhan(predAllDSGQDDonVi);
				tongCongCongNhanQuyetDinhGiaiQuyetLan113 += congNhanQuyetDinhGiaiQuyetLan1;
				mapMaSo.put("congNhanQuyetDinhGiaiQuyetLan1", congNhanQuyetDinhGiaiQuyetLan1);

				// 14 - Huy, sua quyet dinh giai quyet lan 1
				Long huySuaQuyetDinhGiaiQuyetLan1 = thongKeBaoCaoTongHopKQGQDService
						.getTongSoVuViecGiaiQuyetLan2HuySua(predAllDSGQDDonVi);
				tongCongHuySuaQuyetDinhGiaiQuyetLan114 += huySuaQuyetDinhGiaiQuyetLan1;
				mapMaSo.put("huySuaQuyetDinhGiaiQuyetLan1", huySuaQuyetDinhGiaiQuyetLan1);

				// 15 - Kien nghi thu hoi cho nha nuoc Tien
				Long kienNghiThuHoiChoNhaNuocTien = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "tienNhaNuoc");
				tongCongKienNghiThuHoiChoNhaNuocTien15 += kienNghiThuHoiChoNhaNuocTien;
				mapMaSo.put("kienNghiThuHoiChoNhaNuocTien", kienNghiThuHoiChoNhaNuocTien);

				// 16 - Kien nghi thu hoi cho nha nuoc Dat
				Long kienNghiThuHoiChoNhaNuocDat = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "datNhaNuoc");
				tongCongKienNghiThuHoiChoNhaNuocDat16 += kienNghiThuHoiChoNhaNuocDat;
				mapMaSo.put("kienNghiThuHoiChoNhaNuocDat", kienNghiThuHoiChoNhaNuocDat);

				// 17 - Tra lai cho cong dan Tien
				Long traLaiChoCongDanTien = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "tienCongDan");
				tongCongTraLaiChoCongDanTien17 += traLaiChoCongDanTien;
				mapMaSo.put("traLaiChoCongDanTien", traLaiChoCongDanTien);

				// 18 - Tra lai cho cong dan Dat
				Long traLaiChoCongDanDat = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "datCongDan");
				tongCongTraLaiChoCongDanDat18 += traLaiChoCongDanDat;
				mapMaSo.put("traLaiChoCongDanDat", traLaiChoCongDanDat);

				// 19 - So nguoi duoc tra lai quyen loi
				Long soNguoiDuocTraLaiQuyenLoi = thongKeBaoCaoTongHopKQGQDService
						.getTongSoNguoiDuocTraLaiQuyenLoi(predAllDSGQDDonVi);
				tongCongSoNguoiDuocTraLaiQuyenLoi19 += soNguoiDuocTraLaiQuyenLoi;
				mapMaSo.put("soNguoiDuocTraLaiQuyenLoi", soNguoiDuocTraLaiQuyenLoi);

				// 20 - Kien nghi xu ly hanh chinh Tong so nguoi
				Long kienNghiXuLyHanhChinhTongSoNguoi = thongKeBaoCaoTongHopKQGQDService
						.getTongSoNguoiXuLyHanhChinh(predAllDSGQDDonVi);
				tongCongKienNghiXuLyHanhChinhTongSoNguoi20 += kienNghiXuLyHanhChinhTongSoNguoi;
				mapMaSo.put("kienNghiXuLyHanhChinhTongSoNguoi", kienNghiXuLyHanhChinhTongSoNguoi);

				// 21 - Kien nghi xu ly hanh chinh So nguoi da bi xu ly
				Long kienNghiXuLyHanhChinhSoNguoiDaBiXuLy = thongKeBaoCaoTongHopKQGQDService
						.getTongSoNguoiDaBiXuLyHanhChinh(predAllDSGQDDonVi);
				tongCongKienNghiXuLyHanhChinhSoNguoiDaBiXuLy21 += kienNghiXuLyHanhChinhSoNguoiDaBiXuLy;
				mapMaSo.put("kienNghiXuLyHanhChinhSoNguoiDaBiXuLy", kienNghiXuLyHanhChinhSoNguoiDaBiXuLy);

				// 22 - Chuyen co quan dieu tra so vu
				Long soVuChuyenCoQuanDieuTra = thongKeBaoCaoTongHopKQGQDService
						.getTongSoVuChuyenCoQuanDieuTra(predAllDSGQDDonVi);
				tongTongSoVuChuyenCoQuanDieuTra22 += soVuChuyenCoQuanDieuTra;
				mapMaSo.put("soVuChuyenCoQuanDieuTra", soVuChuyenCoQuanDieuTra);

				// 23 - Chuyen co quan dieu tra so doi tuong
				Long soDoiTuongChuyenCoQuanDieuTra = thongKeBaoCaoTongHopKQGQDService
						.getTongSoDoiTuongChuyenCoQuanDieuTra(predAllDSGQDDonVi);
				tongCongSoDoiTuongChuyenCoQuanDieuTra23 += soDoiTuongChuyenCoQuanDieuTra;
				mapMaSo.put("soDoiTuongChuyenCoQuanDieuTra", soDoiTuongChuyenCoQuanDieuTra);

				// 24 - So vu da khoi to
				Long soVuDaKhoiTo = thongKeBaoCaoTongHopKQGQDService.getTongSoVuDaKhoiTo(predAllDSGQDDonVi);
				tongCongSoVuDaKhoiTo24 += soVuDaKhoiTo;
				mapMaSo.put("soVuDaKhoiTo", soVuDaKhoiTo);

				// 25 - So doi tuong da khoi to
				Long soDoiTuongDaKhoiTo = thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongDaKhoiTo(predAllDSGQDDonVi);
				tongCongSoDoiTuongDaKhoiTo25 += soDoiTuongDaKhoiTo;
				mapMaSo.put("soDoiTuongDaKhoiTo", soDoiTuongDaKhoiTo);

				// 26 - So vu viec giai quyet dung thoi han
				Long soVuViecGiaiQuyetDungThoiHan = thongKeBaoCaoTongHopKQGQDService
						.getTongSoVuViecGiaiQuyetKhieuNaiDungThoiHan(predAllDSGQDDonVi);
				tongCongSoVuViecGiaiQuyetDungThoiHan26 += soVuViecGiaiQuyetDungThoiHan;
				mapMaSo.put("soVuViecGiaiQuyetDungThoiHan", soVuViecGiaiQuyetDungThoiHan);

				// 27 - So vu viec giai quyet qua thoi han
				Long soVuViecGiaiQuyetQuaThoiHan = thongKeBaoCaoTongHopKQGQDService
						.getTongSoVuViecGiaiQuyetKhieuNaiQuaThoiHan(predAllDSGQDDonVi);
				tongCongSoVuViecGiaiQuyetQuaThoiHan27 += soVuViecGiaiQuyetQuaThoiHan;
				mapMaSo.put("soVuViecGiaiQuyetQuaThoiHan", soVuViecGiaiQuyetQuaThoiHan);

				// 28 - Tong so quyet dinh phai to chuc thuc hien trong ky bao
				// cao
				tongCongTongSoQuyetDinhPhaiToChucThucHien28 += soVuViecGiaiQuyetBangQDHanhChinh7;
				mapMaSo.put("tongSoQuyetDinhPhaiToChucThucHien", soVuViecGiaiQuyetBangQDHanhChinh7);

				Long tongSoQuyetDinhPhaiToChucThucHienDaThucHien = thongKeBaoCaoTongHopKQGQDService
						.getTongSoQuyetDinhPhaiToChucThucHienDaThucHien(predAllDSGQDDonVi);
				// 29 - Tong so quyet dinh phai to chuc thuc hien trong ky bao
				// cao da thuc hien
				tongCongTongSoQuyetDinhPhaiToChucThucHienDaThucHien29 += tongSoQuyetDinhPhaiToChucThucHienDaThucHien;
				mapMaSo.put("tongSoQuyetDinhPhaiToChucThucHienDaThucHien", tongSoQuyetDinhPhaiToChucThucHienDaThucHien);

				// 30 - Tien phai thu cho nha nuoc
				Long tienPhaiThuChoNhaNuoc = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienNhaNuocPhaiThu");
				tongCongTienPhaiThuChoNhaNuoc30 += tienPhaiThuChoNhaNuoc;
				mapMaSo.put("tienPhaiThuChoNhaNuoc", tienPhaiThuChoNhaNuoc);

				// 31 - Dat phai thu cho nha nuoc
				Long datPhaiThuChoNhaNuoc = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datNhaNuocPhaiThu");
				tongCongDatPhaiThuChoNhaNuoc31 += datPhaiThuChoNhaNuoc;
				mapMaSo.put("datPhaiThuChoNhaNuoc", datPhaiThuChoNhaNuoc);

				// 32 - Tien da thu cho nha nuoc
				Long tienDaThuChoNhaNuoc = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienNhaNuocDaThu");
				tongCongTienDaThuChoNhaNuoc32 += tienDaThuChoNhaNuoc;
				mapMaSo.put("tienDaThuChoNhaNuoc", tienDaThuChoNhaNuoc);

				// 33 - Dat da thu cho nha nuoc
				Long datDaThuChoNhaNuoc = thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"datNhaNuocDaThu");
				tongCongDatDaThuChoNhaNuoc33 += datDaThuChoNhaNuoc;
				mapMaSo.put("datDaThuChoNhaNuoc", datDaThuChoNhaNuoc);

				// 34 - Tien phai tra cho cong dan
				Long tienPhaiTraChoCongDan = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienCongDanPhaiTra");
				tongCongTienPhaiTraChoCongDan34 += tienPhaiTraChoCongDan;
				mapMaSo.put("tienPhaiTraChoCongDan", tienPhaiTraChoCongDan);

				// 35 - Dat phai tra cho cong dan
				Long datPhaiTraChoCongDan = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datCongDanPhaiTra");
				tongCongDatPhaiTraChoCongDan35 += datPhaiTraChoCongDan;
				mapMaSo.put("datPhaiTraChoCongDan", datPhaiTraChoCongDan);

				// 36 - Tien da tra cho cong dan
				Long tienDaTraChoCongDan = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienCongDanDaTra");
				tongCongTienDaTraChoCongDan36 += tienDaTraChoCongDan;
				mapMaSo.put("tienDaTraChoCongDan", tienDaTraChoCongDan);

				// 37 - Dat da tra cho cong dan
				Long datDaTraChoCongDan = thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"datCongDanDaTra");
				tongCongDatDaTraChoCongDan37 += datDaTraChoCongDan;
				mapMaSo.put("datDaTraChoCongDan", datDaTraChoCongDan);

				// 38 - Ghi chu
				mapMaSo.put("ghiChu", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
				mapDonVi = new HashMap<String, Object>();
			}

			mapTongCong.put("tongSoDonKhieuNai", tongCongTongSoDonKhieuNai1);
			mapTongCong.put("donNhanTrongKyBaoCao", tongCongDonNhanTrongKyBaoCao2);
			mapTongCong.put("donTonKyTruocChuyenSang", tongCongDonTonKyTruocChuyenSang3);
			mapTongCong.put("tongSoVuViec", tongCongTongSoVuViec4);
			mapTongCong.put("soDonThuocThamQuyen", tongCongSoDonThuocThamQuyen5);
			mapTongCong.put("soVuViecThuocThamQuyen", tongCongSoVuViecThuocThamQuyen6);
			mapTongCong.put("soVuViecGiaiQuyetBangQDHanhChinh", tongCongSoVuViecGiaiQuyetBangQDHanhChinh7);
			mapTongCong.put("soVuViecRutDonThongQuaGiaiThichThuyetPhuc",
					tongCongSoVuViecRutDonThongQuaGiaiThichThuyetPhuc8);
			mapTongCong.put("khieuNaiDung", tongCongKhieuNaiDung9);
			mapTongCong.put("khieuNaiSai", tongCongKhieuNaiSai10);
			mapTongCong.put("khieuNaiDungMotPhan", tongCongKhieuNaiDungMotPhan11);
			mapTongCong.put("giaiQuyetLan1", tongCongGiaiQuyetLan112);
			mapTongCong.put("congNhanQuyetDinhGiaiQuyetLan1", tongCongCongNhanQuyetDinhGiaiQuyetLan113);
			mapTongCong.put("huySuaQuyetDinhGiaiQuyetLan1", tongCongHuySuaQuyetDinhGiaiQuyetLan114);
			mapTongCong.put("kienNghiThuHoiChoNhaNuocTien", tongCongKienNghiThuHoiChoNhaNuocTien15);
			mapTongCong.put("kienNghiThuHoiChoNhaNuocDat", tongCongKienNghiThuHoiChoNhaNuocDat16);
			mapTongCong.put("traLaiChoCongDanTien", tongCongTraLaiChoCongDanTien17);
			mapTongCong.put("traLaiChoCongDanDat", tongCongTraLaiChoCongDanDat18);
			mapTongCong.put("soNguoiDuocTraLaiQuyenLoi", tongCongSoNguoiDuocTraLaiQuyenLoi19);
			mapTongCong.put("kienNghiXuLyHanhChinhTongSoNguoi", tongCongKienNghiXuLyHanhChinhTongSoNguoi20);
			mapTongCong.put("kienNghiXuLyHanhChinhSoNguoiDaBiXuLy", tongCongKienNghiXuLyHanhChinhSoNguoiDaBiXuLy21);
			mapTongCong.put("soVuChuyenCoQuanDieuTra", tongTongSoVuChuyenCoQuanDieuTra22);
			mapTongCong.put("soDoiTuongChuyenCoQuanDieuTra", tongCongSoDoiTuongChuyenCoQuanDieuTra23);
			mapTongCong.put("soVuDaKhoiTo", tongCongSoVuDaKhoiTo24);
			mapTongCong.put("soDoiTuongDaKhoiTo", tongCongSoDoiTuongDaKhoiTo25);
			mapTongCong.put("soVuViecGiaiQuyetDungThoiHan", tongCongSoVuViecGiaiQuyetDungThoiHan26);
			mapTongCong.put("soVuViecGiaiQuyetQuaThoiHan", tongCongSoVuViecGiaiQuyetQuaThoiHan27);
			mapTongCong.put("tongSoQuyetDinhPhaiToChucThucHien", tongCongTongSoQuyetDinhPhaiToChucThucHien28);
			mapTongCong.put("tongSoQuyetDinhPhaiToChucThucHienDaThucHien",
					tongCongTongSoQuyetDinhPhaiToChucThucHienDaThucHien29);
			mapTongCong.put("tienPhaiThuChoNhaNuoc", tongCongTienPhaiThuChoNhaNuoc30);
			mapTongCong.put("datPhaiThuChoNhaNuoc", tongCongDatPhaiThuChoNhaNuoc31);
			mapTongCong.put("tienDaThuChoNhaNuoc", tongCongTienDaThuChoNhaNuoc32);
			mapTongCong.put("datDaThuChoNhaNuoc", tongCongDatDaThuChoNhaNuoc33);
			mapTongCong.put("tienPhaiTraChoCongDan", tongCongTienPhaiTraChoCongDan34);
			mapTongCong.put("datPhaiTraChoCongDan", tongCongDatPhaiTraChoCongDan35);
			mapTongCong.put("tienDaTraChoCongDan", tongCongTienDaTraChoCongDan36);
			mapTongCong.put("datDaTraChoCongDan", tongCongDatDaTraChoCongDan37);

			map.put("tongCongs", mapTongCong);
			map.put("maSos", maSos);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaGiaiQuyetDonToCao")
	@ApiOperation(value = "Tổng hợp kết quả kết quả giải quyết khiếu nại", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaGiaiQuyetDonToCao(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			PersistentEntityResourceAssembler eass) {
		try {

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Long donViXuLy = Long
					.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Map<String, Object> mapDonVi = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			ThamSo phongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo phuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			Long idCapCQQLPhongBan = Long.valueOf(phongBan.getGiaTri().toString());
			Long idCapCQQLPhuongXa = Long.valueOf(phuongXa.getGiaTri().toString());
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			Map<String, Object> mapTongCong = new HashMap<>();
			List<Map<String, Object>> maSos = new ArrayList<>();

			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));

			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

			if (year == null || year == 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}

			Long tongCongDonNhanTrongKyBaoCao1 = 0L;
			Long tongCongDonNhanTrongKyBaoCao2 = 0L;
			Long tongCongDonTonKyTruocChuyenSang3 = 0L;
			Long tongCongTongSoVuViec4 = 0L;
			Long tongCongSoDonThuocThamQuyen5 = 0L;
			Long tongCongSoVuViecThuocThamQuyen6 = 0L;
			Long tongCongToCaoDung7 = 0L;
			Long tongCongToCaoSai8 = 0L;
			Long tongCongToCaoDungMotPhan9 = 0L;
			Long tongCongKienNghiThuHoiChoNhaNuocTien10 = 0L;
			Long tongCongKienNghiThuHoiChoNhaNuocDat11 = 0L;
			Long tongCongTraLaiChoCongDanTien12 = 0L;
			Long tongCongTraLaiChoCongDanDat13 = 0L;
			Long tongCongSoNguoiDuocTraLaiQuyenLoi14 = 0L;
			Long tongCongKienNghiXuLyHanhChinhTongSoNguoi15 = 0L;
			Long tongCongKienNghiXuLyHanhChinhSoNguoiDaBiXuLy16 = 0L;
			Long tongCongSoVuChuyenCoQuanDieuTra17 = 0L;
			Long tongCongSoDoiTuongChuyenCoQuanDieuTra18 = 0L;
			Long tongCongSoVuDaKhoiTo19 = 0L;
			Long tongCongSoDoiTuongDaKhoiTo20 = 0L;
			Long tongCongSoVuViecGiaiQuyetDungThoiHan21 = 0L;
			Long tongTongSoVuViecGiaiQuyetQuaThoiHan22 = 0L;
			Long tongCongTongSoQuyetDinhPhaiToChucThucHien23 = 0L;
			Long tongCongTongSoQuyetDinhPhaiToChucThucHienDaThucHien24 = 0L;
			Long tongCongTienPhaiThuChoNhaNuoc25 = 0L;
			Long tongCongDatPhaiThuChoNhaNuoc26 = 0L;
			Long tongCongTienDaThuChoNhaNuoc27 = 0L;
			Long tongCongDatDaThuChoNhaNuoc28 = 0L;
			Long tongCongTienPhaiTraChoCongDan29 = 0L;
			Long tongCongDatPhaiTraChoCongDan30 = 0L;
			Long tongCongTienDaTraChoCongDan31 = 0L;
			Long tongCongDatDaTraChoCongDan32 = 0L;

			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null && listDonVis.size() > 0) {
					for (CoQuanQuanLy dv : listDonVis) {
						if (dv != null) {
							CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
							if (coQuan != null) {
								list.add(coQuan);
							}
						}
					}
					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null && listCapDonVis.size() > 0) {
					List<Long> thamSos = new ArrayList<Long>();
					thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

					for (CapCoQuanQuanLy cdv : listCapDonVis) {
						CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
						if (capDonVi != null) {
							listCapCQs.add(capDonVi.getId());
						}
					}
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));

					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			}

			BooleanExpression predAllDSDon = (BooleanExpression) thongKeBaoCaoTongHopKQGQDService
					.predicateFindAllGQD(loaiKy, quy, year, month, tuNgay, denNgay);
			predAllDSDon = predAllDSDon.and(QDon.don.loaiDon.eq(LoaiDonEnum.DON_TO_CAO))
					.and(QDon.don.thanhLapDon.eq(true)).and(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.DE_XUAT_THU_LY));

			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSGQDDonVi = predAllDSDon;
				predAllDSGQDDonVi = predAllDSGQDDonVi.and(QDon.don.donViXuLyGiaiQuyet.id.eq(cq.getId())
						.or(QDon.don.donViXuLyGiaiQuyet.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))
						.or(QDon.don.donViXuLyGiaiQuyet.cha.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa)))));

				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				mapMaSo.put("donVi", mapDonVi);

				// 1 - Tong so don to cao
				Long tongSoDonToCao = thongKeBaoCaoTongHopKQGQDService.getTongSoDon(predAllDSGQDDonVi);
				tongCongDonNhanTrongKyBaoCao1 += tongSoDonToCao;
				mapMaSo.put("tongSoDonToCao", tongSoDonToCao);

				Long donNhanTrongKyBaoCao = thongKeBaoCaoTongHopKQGQDService
						.getTongSoDonTrongKyBaoCao(predAllDSGQDDonVi, loaiKy, quy, year, month, tuNgay, denNgay);

				// ThongKeBaoCaoLoaiKyEnum loaiKyEnum =
				// ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
				Long donTonKyTruocChuyenSang = loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON)
						&& StringUtils.isBlank(tuNgay) ? 0
								: thongKeBaoCaoTongHopKQGQDService.getTongSoDonTonKyTruoc(predAllDSGQDDonVi, loaiKy,
										quy, year, month, tuNgay, denNgay);

				// 2 - Don nhan trong ky bao cao
				tongCongDonNhanTrongKyBaoCao2 += donNhanTrongKyBaoCao;
				mapMaSo.put("donNhanTrongKyBaoCao", donNhanTrongKyBaoCao);

				// 3 - Don ton ky truoc chuyen sang
				tongCongDonTonKyTruocChuyenSang3 += donTonKyTruocChuyenSang;
				mapMaSo.put("donTonKyTruocChuyenSang", donTonKyTruocChuyenSang);

				// 4 - Tong so vu viec
				Long tongSoVuViec = thongKeBaoCaoTongHopKQGQDService.getTongSoVuViec(predAllDSGQDDonVi);
				tongCongTongSoVuViec4 += tongSoVuViec;
				mapMaSo.put("tongSoVuViec", tongSoVuViec);

				// 5 - So don thuoc tham quyen
				Long soDonThuocThamQuyen = thongKeBaoCaoTongHopKQGQDService
						.getTongSoDonToCaoThuocThamQuyen(predAllDSGQDDonVi);
				tongCongSoDonThuocThamQuyen5 += soDonThuocThamQuyen;
				mapMaSo.put("soDonThuocThamQuyen", soDonThuocThamQuyen);

				// 6 - So vu viec thuoc tham quyen
				Long soVuViecThuocThamQuyen = thongKeBaoCaoTongHopKQGQDService
						.getTongSoVuViecToCaoThuocThamQuyen(predAllDSGQDDonVi);
				tongCongSoVuViecThuocThamQuyen6 += soVuViecThuocThamQuyen;
				mapMaSo.put("soVuViecThuocThamQuyen", soVuViecThuocThamQuyen);

				// 7 - to Cao Dung
				Long toCaoDung = thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiDung(predAllDSGQDDonVi);
				tongCongToCaoDung7 += toCaoDung;
				mapMaSo.put("toCaoDung", toCaoDung);

				// 8 - To cao sai
				Long toCaoSai = thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiSai(predAllDSGQDDonVi);
				tongCongToCaoSai8 += toCaoSai;
				mapMaSo.put("toCaoSai", toCaoSai);

				// 9 - To cao dung 1 phan
				Long toCaoDungMotPhan = thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecDungMotPhan(predAllDSGQDDonVi);
				tongCongToCaoDungMotPhan9 += toCaoDungMotPhan;
				mapMaSo.put("toCaoDungMotPhan", toCaoDungMotPhan);

				// 10 - Kien nghi thu hoi cho nha nuoc Tien
				Long kienNghiThuHoiChoNhaNuocTien = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "tienNhaNuoc");
				tongCongKienNghiThuHoiChoNhaNuocTien10 += kienNghiThuHoiChoNhaNuocTien;
				mapMaSo.put("kienNghiThuHoiChoNhaNuocTien", kienNghiThuHoiChoNhaNuocTien);

				// 11 - Kien nghi thu hoi cho nha nuoc Dat
				Long kienNghiThuHoiChoNhaNuocDat = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "datNhaNuoc");
				tongCongKienNghiThuHoiChoNhaNuocDat11 += kienNghiThuHoiChoNhaNuocDat;
				mapMaSo.put("kienNghiThuHoiChoNhaNuocDat", kienNghiThuHoiChoNhaNuocDat);

				// 12 - Tra lai cho cong dan Tien
				Long traLaiChoCongDanTien = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "tienCongDan");
				tongCongTraLaiChoCongDanTien12 += traLaiChoCongDanTien;
				mapMaSo.put("traLaiChoCongDanTien", traLaiChoCongDanTien);

				// 13 - Tra lai cho cong dan Dat
				Long traLaiChoCongDanDat = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "datCongDan");
				tongCongTraLaiChoCongDanDat13 += traLaiChoCongDanDat;
				mapMaSo.put("traLaiChoCongDanDat", traLaiChoCongDanDat);

				// 14 - So nguoi duoc bao ve quyen loi
				Long soNguoiDuocTraLaiQuyenLoi = thongKeBaoCaoTongHopKQGQDService
						.getTongSoNguoiDuocTraLaiQuyenLoi(predAllDSGQDDonVi);
				tongCongSoNguoiDuocTraLaiQuyenLoi14 += soNguoiDuocTraLaiQuyenLoi;
				mapMaSo.put("soNguoiDuocTraLaiQuyenLoi", soNguoiDuocTraLaiQuyenLoi);

				// 15 - Kien nghi xu ly hanh chinh Tong so nguoi
				Long kienNghiXuLyHanhChinhTongSoNguoi = thongKeBaoCaoTongHopKQGQDService
						.getTongSoNguoiXuLyHanhChinh(predAllDSGQDDonVi);
				tongCongKienNghiXuLyHanhChinhTongSoNguoi15 += kienNghiXuLyHanhChinhTongSoNguoi;
				mapMaSo.put("kienNghiXuLyHanhChinhTongSoNguoi", kienNghiXuLyHanhChinhTongSoNguoi);

				// 16 - Kien nghi xu ly hanh chinh So nguoi da bi xu ly
				Long kienNghiXuLyHanhChinhSoNguoiDaBiXuLy = thongKeBaoCaoTongHopKQGQDService
						.getTongSoNguoiDaBiXuLyHanhChinh(predAllDSGQDDonVi);
				tongCongKienNghiXuLyHanhChinhSoNguoiDaBiXuLy16 += kienNghiXuLyHanhChinhSoNguoiDaBiXuLy;
				mapMaSo.put("kienNghiXuLyHanhChinhSoNguoiDaBiXuLy", kienNghiXuLyHanhChinhSoNguoiDaBiXuLy);

				// 17 - Chuyen co quan dieu tra so vu
				Long soVuChuyenCoQuanDieuTra = thongKeBaoCaoTongHopKQGQDService
						.getTongSoVuChuyenCoQuanDieuTra(predAllDSGQDDonVi);
				tongCongSoVuChuyenCoQuanDieuTra17 += soVuChuyenCoQuanDieuTra;
				mapMaSo.put("soVuChuyenCoQuanDieuTra", soVuChuyenCoQuanDieuTra);

				// 18 - Chuyen co quan dieu tra so doi tuong
				Long soDoiTuongChuyenCoQuanDieuTra = thongKeBaoCaoTongHopKQGQDService
						.getTongSoDoiTuongChuyenCoQuanDieuTra(predAllDSGQDDonVi);
				tongCongSoDoiTuongChuyenCoQuanDieuTra18 += soDoiTuongChuyenCoQuanDieuTra;
				mapMaSo.put("soDoiTuongChuyenCoQuanDieuTra", soDoiTuongChuyenCoQuanDieuTra);

				// 19 - So vu da khoi to
				Long soVuDaKhoiTo = thongKeBaoCaoTongHopKQGQDService.getTongSoVuDaKhoiTo(predAllDSGQDDonVi);
				tongCongSoVuDaKhoiTo19 += soVuDaKhoiTo;
				mapMaSo.put("soVuDaKhoiTo", soVuDaKhoiTo);

				// 20 - So doi tuong da khoi to
				Long soDoiTuongDaKhoiTo = thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongDaKhoiTo(predAllDSGQDDonVi);
				tongCongSoDoiTuongDaKhoiTo20 += soDoiTuongDaKhoiTo;
				mapMaSo.put("soDoiTuongDaKhoiTo", soDoiTuongDaKhoiTo);

				// 21 - So vu viec giai quyet dung thoi han
				Long soVuViecGiaiQuyetDungThoiHan = thongKeBaoCaoTongHopKQGQDService
						.getTongSoVuViecGiaiQuyetToCaoDungThoiHan(predAllDSGQDDonVi);
				tongCongSoVuViecGiaiQuyetDungThoiHan21 += soVuViecGiaiQuyetDungThoiHan;
				mapMaSo.put("soVuViecGiaiQuyetDungThoiHan", soVuViecGiaiQuyetDungThoiHan);

				// 22 - So vu viec giai quyet qua thoi han
				Long soVuViecGiaiQuyetQuaThoiHan = thongKeBaoCaoTongHopKQGQDService
						.getTongSoVuViecGiaiQuyetToCaoQuaThoiHan(predAllDSGQDDonVi);
				tongTongSoVuViecGiaiQuyetQuaThoiHan22 += soVuViecGiaiQuyetQuaThoiHan;
				mapMaSo.put("soVuViecGiaiQuyetQuaThoiHan", soVuViecGiaiQuyetQuaThoiHan);

				// 23 - Tong so quyet dinh phai to chuc thuc hien trong ky bao
				// cao
				Long tongSoQuyetDinhPhaiToChucThucHien = 0L;
				tongCongTongSoQuyetDinhPhaiToChucThucHien23 += tongSoQuyetDinhPhaiToChucThucHien;
				mapMaSo.put("tongSoQuyetDinhPhaiToChucThucHien", tongSoQuyetDinhPhaiToChucThucHien);

				// 24 - Tong so quyet dinh phai to chuc thuc hien trong ky bao
				// cao da thuc hien
				Long tongSoQuyetDinhPhaiToChucThucHienDaThucHien = 0L;
				tongCongTongSoQuyetDinhPhaiToChucThucHienDaThucHien24 += tongSoQuyetDinhPhaiToChucThucHienDaThucHien;
				mapMaSo.put("tongSoQuyetDinhPhaiToChucThucHienDaThucHien", 0L);

				// 25 - Tien phai thu cho nha nuoc
				Long tienPhaiThuChoNhaNuoc = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienNhaNuocPhaiThu");
				tongCongTienPhaiThuChoNhaNuoc25 += tienPhaiThuChoNhaNuoc;
				mapMaSo.put("tienPhaiThuChoNhaNuoc", tienPhaiThuChoNhaNuoc);

				// 26 - Dat phai thu cho nha nuoc
				Long datPhaiThuChoNhaNuoc = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datNhaNuocPhaiThu");
				tongCongDatPhaiThuChoNhaNuoc26 += datPhaiThuChoNhaNuoc;
				mapMaSo.put("datPhaiThuChoNhaNuoc", datPhaiThuChoNhaNuoc);

				// 27 - Tien da thu cho nha nuoc
				Long tienDaThuChoNhaNuoc = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienNhaNuocDaThu");
				tongCongTienDaThuChoNhaNuoc27 += tienDaThuChoNhaNuoc;
				mapMaSo.put("tienDaThuChoNhaNuoc", tienDaThuChoNhaNuoc);

				// 28 - Dat da thu cho nha nuoc
				Long datDaThuChoNhaNuoc = thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"datNhaNuocDaThu");
				tongCongDatDaThuChoNhaNuoc28 += datDaThuChoNhaNuoc;
				mapMaSo.put("datDaThuChoNhaNuoc", datDaThuChoNhaNuoc);

				// 29 - Tien phai tra cho cong dan
				Long tienPhaiTraChoCongDan = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienCongDanPhaiTra");
				tongCongTienPhaiTraChoCongDan29 += tienPhaiTraChoCongDan;
				mapMaSo.put("tienPhaiTraChoCongDan", tienPhaiTraChoCongDan);

				// 30 - Dat phai tra cho cong dan
				Long datPhaiTraChoCongDan = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datCongDanPhaiTra");
				tongCongDatPhaiTraChoCongDan30 += datPhaiTraChoCongDan;
				mapMaSo.put("datPhaiTraChoCongDan", datPhaiTraChoCongDan);

				// 31 - Tien da tra cho cong dan
				Long tienDaTraChoCongDan = thongKeBaoCaoTongHopKQGQDService
						.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienCongDanDaTra");
				tongCongTienDaTraChoCongDan31 += tienDaTraChoCongDan;
				mapMaSo.put("tienDaTraChoCongDan", tienDaTraChoCongDan);

				// 32 - Dat da tra cho cong dan
				Long datDaTraChoCongDan = thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"datCongDanDaTra");
				tongCongDatDaTraChoCongDan32 += datDaTraChoCongDan;
				mapMaSo.put("datDaTraChoCongDan", datDaTraChoCongDan);

				// 33 - Ghi chu
				mapMaSo.put("ghiChu", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
				mapDonVi = new HashMap<String, Object>();
			}

			mapTongCong.put("tongSoDonToCao", tongCongDonNhanTrongKyBaoCao1);
			mapTongCong.put("donNhanTrongKyBaoCao", tongCongDonNhanTrongKyBaoCao2);
			mapTongCong.put("donTonKyTruocChuyenSang", tongCongDonTonKyTruocChuyenSang3);
			mapTongCong.put("tongSoVuViec", tongCongTongSoVuViec4);
			mapTongCong.put("soDonThuocThamQuyen", tongCongSoDonThuocThamQuyen5);
			mapTongCong.put("soVuViecThuocThamQuyen", tongCongSoVuViecThuocThamQuyen6);
			mapTongCong.put("toCaoDung", tongCongToCaoDung7);
			mapTongCong.put("toCaoSai", tongCongToCaoSai8);
			mapTongCong.put("toCaoDungMotPhan", tongCongToCaoDungMotPhan9);
			mapTongCong.put("kienNghiThuHoiChoNhaNuocTien", tongCongKienNghiThuHoiChoNhaNuocTien10);
			mapTongCong.put("kienNghiThuHoiChoNhaNuocDat", tongCongKienNghiThuHoiChoNhaNuocDat11);
			mapTongCong.put("traLaiChoCongDanTien", tongCongTraLaiChoCongDanTien12);
			mapTongCong.put("traLaiChoCongDanDat", tongCongTraLaiChoCongDanDat13);
			mapTongCong.put("soNguoiDuocTraLaiQuyenLoi", tongCongSoNguoiDuocTraLaiQuyenLoi14);
			mapTongCong.put("kienNghiXuLyHanhChinhTongSoNguoi", tongCongKienNghiXuLyHanhChinhTongSoNguoi15);
			mapTongCong.put("kienNghiXuLyHanhChinhSoNguoiDaBiXuLy", tongCongKienNghiXuLyHanhChinhSoNguoiDaBiXuLy16);
			mapTongCong.put("soVuChuyenCoQuanDieuTra", tongCongSoVuChuyenCoQuanDieuTra17);
			mapTongCong.put("soDoiTuongChuyenCoQuanDieuTra", tongCongSoDoiTuongChuyenCoQuanDieuTra18);
			mapTongCong.put("soVuDaKhoiTo", tongCongSoVuDaKhoiTo19);
			mapTongCong.put("soDoiTuongDaKhoiTo", tongCongSoDoiTuongDaKhoiTo20);
			mapTongCong.put("soVuViecGiaiQuyetDungThoiHan", tongCongSoVuViecGiaiQuyetDungThoiHan21);
			mapTongCong.put("soVuViecGiaiQuyetQuaThoiHan", tongTongSoVuViecGiaiQuyetQuaThoiHan22);
			mapTongCong.put("tongSoQuyetDinhPhaiToChucThucHien", tongCongTongSoQuyetDinhPhaiToChucThucHien23);
			mapTongCong.put("tongSoQuyetDinhPhaiToChucThucHienDaThucHien",
					tongCongTongSoQuyetDinhPhaiToChucThucHienDaThucHien24);
			mapTongCong.put("tienPhaiThuChoNhaNuoc", tongCongTienPhaiThuChoNhaNuoc25);
			mapTongCong.put("datPhaiThuChoNhaNuoc", tongCongDatPhaiThuChoNhaNuoc26);
			mapTongCong.put("tienDaThuChoNhaNuoc", tongCongTienDaThuChoNhaNuoc27);
			mapTongCong.put("datDaThuChoNhaNuoc", tongCongDatDaThuChoNhaNuoc28);
			mapTongCong.put("tienPhaiTraChoCongDan", tongCongTienPhaiTraChoCongDan29);
			mapTongCong.put("datPhaiTraChoCongDan", tongCongDatPhaiTraChoCongDan30);
			mapTongCong.put("tienDaTraChoCongDan", tongCongTienDaTraChoCongDan31);
			mapTongCong.put("datDaTraChoCongDan", tongCongDatDaTraChoCongDan32);

			map.put("tongCongs", mapTongCong);
			map.put("maSos", maSos);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaTiepCongDan/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp báo cáo tiếp công dân", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcel(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "donViId", required = false) Long donViId) throws IOException {

		try {
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<Map<String, Object>> maSos = new ArrayList<>();

			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

			if (year == null || year == 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}

			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQTCDService
					.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais.add(55L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachs.add(5L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs.add(53L);

			List<LinhVucDonThu> linhVucTranhChapVeDatDais = new ArrayList<LinhVucDonThu>();
			linhVucTranhChapVeDatDais.addAll(linhVucDonThuService
					.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais));

			List<LinhVucDonThu> linhVucVeChinhSachs = new ArrayList<LinhVucDonThu>();
			linhVucVeChinhSachs.addAll(
					linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeChinhSachs));

			List<LinhVucDonThu> linhVucVeCheDoCCVCs = new ArrayList<LinhVucDonThu>();
			linhVucVeCheDoCCVCs.addAll(
					linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs));

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(54L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(57L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(58L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(59L);

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

			List<LinhVucDonThu> linhVucLienQuanDenDatDais = new ArrayList<LinhVucDonThu>();
			linhVucLienQuanDenDatDais.addAll(linhVucDonThuService
					.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais));

			List<LinhVucDonThu> linhVucCTVHXHKKhacs = new ArrayList<LinhVucDonThu>();
			linhVucCTVHXHKKhacs.addAll(linhVucDonThuService
					.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs));

			List<LinhVucDonThu> linhVucVeChinhSachCCVCs = new ArrayList<LinhVucDonThu>();
			linhVucVeChinhSachCCVCs.addAll(
					linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs));

			List<LinhVucDonThu> linhVucVeNhaCuaTaiSans = new ArrayList<LinhVucDonThu>();
			linhVucVeNhaCuaTaiSans.addAll(
					linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));

			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));

			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null) {
					for (CoQuanQuanLy dv : listDonVis) {
						CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
						if (coQuan != null) {
							list.add(coQuan);
						}
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null) {
					if (listCapDonVis.size() > 0) {
						List<Long> thamSos = new ArrayList<Long>();
						thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

						for (CapCoQuanQuanLy cdv : listCapDonVis) {
							CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
							if (capDonVi != null) {
								listCapCQs.add(capDonVi.getId());
							}
						}
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			}

			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSTCDDonVi = predAllDSTCD;
				if (cq.getCapCoQuanQuanLy().getId().equals(Long.valueOf(thamSoUBNDTP.getGiaTri().toString()))) {
					predAllDSTCDDonVi = predAllDSTCDDonVi
							.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(cq.getId()));
				} else {
					predAllDSTCDDonVi = predAllDSTCDDonVi.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id
							.eq(cq.getId()).or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(cq.getId()))
							.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(cq.getId())));
				}

				BooleanExpression predAllDSTCDThuongXuyen = predAllDSTCDDonVi;
				predAllDSTCDThuongXuyen = predAllDSTCDThuongXuyen
						.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.THUONG_XUYEN));
				predAllDSTCDThuongXuyen = predAllDSTCDThuongXuyen
						.and(QSoTiepCongDan.soTiepCongDan.don.yeuCauGapTrucTiepLanhDao.isFalse());

				BooleanExpression predAllDSTCDLanhDao = predAllDSTCDDonVi;
				predAllDSTCDLanhDao = predAllDSTCDLanhDao
						.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DINH_KY)
								.or(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DOT_XUAT)));

				mapMaSo.put("tenDonVi", cq.getTen());

				// tiep cong dan thuong xuyen
				// luoc - 1
				mapMaSo.put("tiepCongDanThuongXuyenLuot",
						thongKeBaoCaoTongHopKQTCDService.getTongSoLuocTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen));

				// nguoi - 2
				mapMaSo.put("tiepCongDanThuongXuyenNguoi", thongKeBaoCaoTongHopKQTCDService
						.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true, false));

				// vu viec cu - 3
				mapMaSo.put("tiepCongDanThuongXuyenVuViecCu", thongKeBaoCaoTongHopKQTCDService
						.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, false, LoaiVuViecEnum.CU));

				// vu viec moi phat sinh - 4
				mapMaSo.put("tiepCongDanThuongXuyenVuViecMoiPhatSinh",
						thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanThuongXuyen(
								predAllDSTCDThuongXuyen, false, LoaiVuViecEnum.MOI_PHAT_SINH));

				// doan dong nguoi
				// so doan - 5
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiSoDoan", thongKeBaoCaoTongHopKQTCDService
						.getTongSoDoanDongNguoiTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true));

				// nguoi - 6
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiSoNguoi", thongKeBaoCaoTongHopKQTCDService
						.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, false, true));

				// doan dong nguoi - vu viec
				// cu - 7
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiVuViecCu", thongKeBaoCaoTongHopKQTCDService
						.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true, LoaiVuViecEnum.CU));

				// moi phat sinh - 8
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh",
						thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanThuongXuyen(
								predAllDSTCDThuongXuyen, true, LoaiVuViecEnum.MOI_PHAT_SINH));

				// tiep cong dan dinh ky dot xuat cua lanh dao
				// luoc - 9
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoLuot",
						thongKeBaoCaoTongHopKQTCDService.getTongSoLuocTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao));

				// nguoi - 10
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi", thongKeBaoCaoTongHopKQTCDService
						.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao));

				// tiep cong dan cua lanh dao - vu viec
				// cu - 11
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu", thongKeBaoCaoTongHopKQTCDService
						.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, false, LoaiVuViecEnum.CU));

				// moi phat sinh - 12
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh",
						thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(
								predAllDSTCDLanhDao, false, LoaiVuViecEnum.MOI_PHAT_SINH));

				// tiep cong dan cua lanh dao - doan dong nguoi
				// so doan - 13
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan", thongKeBaoCaoTongHopKQTCDService
						.getTongSoDoanDongNguoiTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true));

				// so nguoi - 14
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi", thongKeBaoCaoTongHopKQTCDService
						.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, false, true));

				// tiep cong dan cua lanh dao - doan dong nguoi - vu viec
				// cu - 15
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu", thongKeBaoCaoTongHopKQTCDService
						.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true, LoaiVuViecEnum.CU));

				// moi phat sinh - 16
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh",
						thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(
								predAllDSTCDLanhDao, true, LoaiVuViecEnum.MOI_PHAT_SINH));

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
				// tong - 17
				mapMaSo.put("tongVuViecDonKhieuNaiLinhVucHanhChinh", tongVuViecDonKhieuNaiLinhVucHanhChinh17);

				// lien quan dat dai - 18
				mapMaSo.put("donKhieuNaiLinhVucHanhChinhLienQuanDenDatDai",
						tongSoVuViecKhieuNaiLinhVucHanhChinhLienQuanDenDatDai18);

				// ve nha tai san - 19
				mapMaSo.put("donKhieuNaiLinhVucHanhChinhVeNhaTaiSan",
						tongSoVuViecKhieuNaiLinhVucHanhChinhVeNhaTaiSan19);

				// ve chinh sach che do cc, vc - 20
				mapMaSo.put("donKhieuNaiLinhVucHanhChinhVeChinhSachCCVC",
						tongSoVuViecKhieuNaiLinhVucHanhChinhVeChinhSachCCVC20);

				// ve chinh sach che do cc, vc - 21
				mapMaSo.put("donKhieuNaiLinhVucHanhChinhVeChinhCTVHXHKKhac",
						tongSoVuViecKhieuNaiLinhVucHanhChinhCTVHXHKKhac21);

				// linh Vuc Tu Phap - 22
				mapMaSo.put("donKhieuNaiLinhVucTuPhap",
						thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi,
								linhVucHanhChinhKhieuNaiTuPhap));

				// ve dang - 23
				mapMaSo.put("donKhieuNaiLinhVucVeDang",
						thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi,
								linhVucHanhChinhKhieuNaiVeDang));

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

				// tong so vu viec don to cao - 24
				mapMaSo.put("tongVuViecDonToCao", tongVuViecDonToCaoLinhVucHanhChinh18);

				// tong so vu viec don to cao linh vuc hanh chinh - 25
				mapMaSo.put("donToCaoLinhVucHanhChinh", donToCaoLinhVucHanhChinh);

				// tong so vu viec don to cao linh vuc tu phap - 26
				mapMaSo.put("donToCaoLinhVucTuPhap", donToCaoLinhVucTuPhap);

				// tong so vu viec don to cao linh vuc tham nhung - 27
				mapMaSo.put("donToCaoLinhVucThamNhung", donToCaoLinhVucThamNhung);

				// tong so vu viec don to cao linh vuc ve dang - 28
				mapMaSo.put("donToCaoLinhVucVeDang", donToCaoLinhVucVeDang);

				// tong so vu viec don to cao linh vuc khac - 29
				mapMaSo.put("donToCaoLinhVucKhac", donToCaoLinhVucKhac);

				// don kien nghi phan anh
				// kienNghiPhanAnh - 30
				// mapMaSo.put("phanAnhKienNghiKhac",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKienNghiPhanAnh(predAllDSTCDDonVi));
				mapMaSo.put("phanAnhKienNghiKhac", thongKeBaoCaoTongHopKQTCDService
						.getTongSoDonKienNghiPhanAnhHXLLuuDonVaTheoDoi(predAllDSTCDDonVi));

				// ket qua tiep dan
				// chua duoc giai quyet - 31
				// mapMaSo.put("chuaDuocGiaiQuyet",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonChuaChotHuongXuLyXLD(predAllDSTCDDonVi));
				mapMaSo.put("chuaDuocGiaiQuyet", 0);

				// da duoc giai quyet
				// chua co quyet dinh giai quyet - 32
				// mapMaSo.put("chuaCoQuyetDinhGiaiQuyet",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonChuaCoQuyetDinhGiaiQuyet(predAllDSTCDDonVi));
				mapMaSo.put("chuaCoQuyetDinhGiaiQuyet", 0);

				// da co quyet dinh giai quyet - 33
				// mapMaSo.put("daCoQuyetDinhGiaiQuyet",
				// thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonDaCoQuyetDinhGiaiQuyet(predAllDSTCDDonVi));
				mapMaSo.put("daCoQuyetDinhGiaiQuyet", 0);

				// da co ban an cua toa - 34
				mapMaSo.put("daCoBanAnCuaToa", 0);

				// da co ban an cua toa - 35
				mapMaSo.put("ghiChu", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopBaoCaoTiepCongDan(response, "DanhSachTongHopThongKeBaoCaoTiepCongDan", "sheetName",
					maSos, tuNgay, denNgay, "Danh sách tổng hợp thống kê báo cáo tiếp công dân");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaBaoCaoXuLyDon")
	@ApiOperation(value = "Tổng hợp kết quả báo cáo xử lý đơn thư", position = 3, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaBaoCaoXuLyDon(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			PersistentEntityResourceAssembler eass) {
		try {

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Long donViXuLy = Long
					.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());

			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			Map<String, Object> mapTongCong = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();

			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

			if (year == null || year == 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}

//			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService
//					.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService
					.predicateFindAllXLD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLDTrongKy = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService
					.predicateFindAllXLDTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLDKyTruoc = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService
					.predicateFindAllXLDKyTruoc(loaiKy, quy, year, month, tuNgay, denNgay);
			
			// khieu nai
			List<Long> idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(59L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(58L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(57L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(54L);

			LinhVucDonThu linhVucHanhChinhDonKhieuNai = linhVucDonThuRepo.findOne(1L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiVeDang = linhVucDonThuRepo.findOne(56L);

			// to cao
			LinhVucDonThu linhVucHanhChinhDonToCao = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhapDonToCao = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhungDonToCao = linhVucDonThuRepo.findOne(39L);
			LinhVucDonThu linhVucVeDangDonToCao = linhVucDonThuRepo.findOne(62L);
			LinhVucDonThu linhVucKhacDonToCao = linhVucDonThuRepo.findOne(63L);

			// tham quyen giai quyet
			ThamQuyenGiaiQuyet thamQuyenGiaiQuyetHanhChinh = thamQuyenGiaiQuyetRepo.findOne(1L);
			ThamQuyenGiaiQuyet thamQuyenGiaiQuyetTuPhap = thamQuyenGiaiQuyetRepo.findOne(2L);
			// ThamQuyenGiaiQuyet thamQuyenGiaiQuyetCQDang =
			// thamQuyenGiaiQuyetRepo.findOne(3L);

			List<LinhVucDonThu> linhVucLienQuanDenDatDais = new ArrayList<LinhVucDonThu>();
			linhVucLienQuanDenDatDais.addAll(linhVucDonThuService
					.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais));

			List<LinhVucDonThu> linhVucCTVHXHKKhacs = new ArrayList<LinhVucDonThu>();
			linhVucCTVHXHKKhacs.addAll(linhVucDonThuService
					.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs));

			List<LinhVucDonThu> linhVucVeChinhSachCCVCs = new ArrayList<LinhVucDonThu>();
			linhVucVeChinhSachCCVCs.addAll(
					linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs));

			List<LinhVucDonThu> linhVucVeNhaCuaTaiSans = new ArrayList<LinhVucDonThu>();
			linhVucVeNhaCuaTaiSans.addAll(
					linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));

			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));

			/* Tong cong */
			Long tongCongTongSoDonTiepNhanXLDTCD1 = 0L;
			Long tongCongTongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy2 = 0L;
			Long tongCongTongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy3 = 0L;
			Long tongCongTongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang4 = 0L;
			Long tongCongTongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang5 = 0L;
			Long tongCongTongSoDonDuDieuKienThuLy6 = 0L;
			Long tongCongTongSoDonKhieuNaiLinhVucHanhChinh7 = 0L;
			Long tongCongTongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai8 = 0L;
			Long tongCongTongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan9 = 0L;
			Long tongCongTongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC10 = 0L;
			Long tongCongTongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac11 = 0L;
			Long tongCongTongSoDonKhieuNaiLinhVucTuPhap12 = 0L;
			Long tongCongTongSoDonKhieuNaiLinhVucVeDang13 = 0L;
			Long tongCongTongSoDonLinhVucToCao14 = 0L;
			Long tongCongTongSoDonToCaoLinhVucHanhChinh15 = 0L;
			Long tongCongTongSoDonToCaoLinhVucTuPhap16 = 0L;
			Long tongCongTongSoDonToCaoLinhVucThamNhung17 = 0L; 
			Long tongCongTongSoDonToCaoLinhVucVeDang18 = 0L;
			Long tongCongTongSoDonToCaoLinhVucKhac19 = 0L;
			Long tongCongTongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap20 = 0L;
			Long tongCongTongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap21 = 0L;
			Long tongTongSoDonTheoTQGQCuaCoQuanDang22 = 0L;
			Long tongCongTongSoDonTheoTTGiaiQuyetChuaGiaiQuyet23 = 0L;
			Long tongCongTongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyet24 = 0L;
			Long tongCongTongSoDonXLDDonKienNghiPhanAnh25 = 0L;
			Long tongCongTongSoDonXLDSoVanBanHuongDan26 = 0L;
			Long tongCongTongSoDonChuyenCQCoThamQuyen27 = 0L;
			Long tongCongTongSoDonCoSoCongVanDonDocViecGiaiQuyet28 = 0L;
			Long tongCongTongSoDonThuocThamQuyenKhieuNai29 = 0L;
			Long tongCongTongSoDonThuocThamQuyenToCao30 = 0L;

			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null && listDonVis.size() > 0) {
					for (CoQuanQuanLy dv : listDonVis) {
						if (dv != null) {
							CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
							if (coQuan != null) {
								list.add(coQuan);
							}
						}
					}
					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null && listCapDonVis.size() > 0) {
					List<Long> thamSos = new ArrayList<Long>();
					thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

					for (CapCoQuanQuanLy cdv : listCapDonVis) {
						CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
						if (capDonVi != null) {
							listCapCQs.add(capDonVi.getId());
						}
					}
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));

					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			}
			

			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDXLDDonVi = predAllDSXLD;
				BooleanExpression predAllDXLDDonViTrongKy = predAllDSXLDTrongKy;
				BooleanExpression predAllDXLDDonViKyTruoc = predAllDSXLDKyTruoc;
				//BooleanExpression predAllDSTCDDonVi = predAllDSTCD;
				
				if (cq.getCapCoQuanQuanLy().getId().equals(Long.valueOf(thamSoUBNDTP.getGiaTri().toString()))) {
//					predAllDSTCDDonVi = predAllDSTCDDonVi
//							.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(cq.getId()));
					predAllDXLDDonVi = predAllDXLDDonVi.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId()));
					predAllDXLDDonViTrongKy = predAllDXLDDonViTrongKy.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId()));
					predAllDXLDDonViKyTruoc = predAllDXLDDonViKyTruoc.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId()));
				} else {
//					predAllDSTCDDonVi = predAllDSTCDDonVi.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id
//							.eq(cq.getId()).or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(cq.getId()))
//							.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(cq.getId())));

					predAllDXLDDonVi = predAllDXLDDonVi.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId())
							.or(QXuLyDon.xuLyDon.donViXuLy.cha.id.eq(cq.getId()))
							.or(QXuLyDon.xuLyDon.donViXuLy.cha.cha.id.eq(cq.getId())));
					
					predAllDXLDDonViTrongKy = predAllDXLDDonViTrongKy.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId())
							.or(QXuLyDon.xuLyDon.donViXuLy.cha.id.eq(cq.getId()))
							.or(QXuLyDon.xuLyDon.donViXuLy.cha.cha.id.eq(cq.getId())));
					
					predAllDXLDDonViKyTruoc = predAllDXLDDonViKyTruoc.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId())
							.or(QXuLyDon.xuLyDon.donViXuLy.cha.id.eq(cq.getId()))
							.or(QXuLyDon.xuLyDon.donViXuLy.cha.cha.id.eq(cq.getId())));
				}
				
				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				mapMaSo.put("donVi", mapDonVi);

				Long tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTiepNhanTrongKyDonCoNhieuNguoiDungTenXLDTCD(predAllDXLDDonViTrongKy, loaiKy, quy, month,
								tuNgay, denNgay);
				Long tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTiepNhanTrongKyDonCoMotNguoiDungTenXLDTCD(predAllDXLDDonViTrongKy, loaiKy, quy, month,
								tuNgay, denNgay);
				Long tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonKyTruocChuyenSangDonCoNhieuNguoiDungTenXLDTCD(predAllDXLDDonViKyTruoc, loaiKy,
								year, quy, month, tuNgay, denNgay);
				Long tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang = thongKeBaoCaoTongHopKQXLDService
						.getTongSoKyTruocChuyenSangDonCoMotNguoiDungTenXLDTCD(predAllDXLDDonViKyTruoc, loaiKy, year,
								quy, month, tuNgay, denNgay);
				Long tongSoDonTiepNhanXLDTCD = tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy
						+ tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy
						+ tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang
						+ tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang;

				// tong so don - 1
				tongCongTongSoDonTiepNhanXLDTCD1 += tongSoDonTiepNhanXLDTCD;
				mapMaSo.put("tongSoDonTiepNhanXLDTCD", tongSoDonTiepNhanXLDTCD);

				// don co nhieu nguoi dung ten - trong ky - 2
				tongCongTongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy2 += tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy;
				mapMaSo.put("tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy",
						tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy);

				// don co mot nguoi dung ten - trong ky - 3
				tongCongTongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy3 += tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy;
				mapMaSo.put("tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy",
						tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy);

				// don co nhieu nguoi dung ten - truoc ky - 4
				tongCongTongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang4 += tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang;
				mapMaSo.put("tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang",
						tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang);

				// don co mot nguoi dung ten - truoc ky - 5
				tongCongTongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang5 += tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang;
				mapMaSo.put("tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang",
						tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang);
				// Long tongSoDonCoHXLLuDonVaTheoDoi =
				// thongKeBaoCaoTongHopKQXLDService.getTongSoDonDuDieuKienThuLyLuuDonVaTheoDoi(predAllDXLDDonVi);
				// Long tongSoDonDuDieuKienThuLy = tongSoDonTiepNhanXLDTCD -
				// tongSoDonCoHXLLuDonVaTheoDoi;
				Long tongSoDonDuDieuKienThuLy = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonDuDieuKienThuLy(predAllDXLDDonVi);

				// don du dieu kien xu ly - 6
				tongCongTongSoDonDuDieuKienThuLy6 += tongSoDonDuDieuKienThuLy;
				mapMaSo.put("tongSoDonDuDieuKienThuLy", tongSoDonDuDieuKienThuLy);

				Long tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi,
								linhVucHanhChinhDonKhieuNai, linhVucLienQuanDenDatDais);
				Long tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi,
								linhVucHanhChinhDonKhieuNai, linhVucVeNhaCuaTaiSans);
				Long tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi,
								linhVucHanhChinhDonKhieuNai, linhVucVeChinhSachCCVCs);
				Long tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi,
								linhVucHanhChinhDonKhieuNai, linhVucCTVHXHKKhacs);
				Long tongSoDonKhieuNaiLinhVucHanhChinh = tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai
						+ tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan
						+ tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC
						+ tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac;

				// tong - 7
				tongCongTongSoDonKhieuNaiLinhVucHanhChinh7 += tongSoDonKhieuNaiLinhVucHanhChinh;
				mapMaSo.put("tongSoDonKhieuNaiLinhVucHanhChinh", tongSoDonKhieuNaiLinhVucHanhChinh);

				// lien quan den dat dai - 8
				tongCongTongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai8 += tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai;
				mapMaSo.put("tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai",
						tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai);

				// ve nha tai san - 9
				tongCongTongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan9 += tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan;
				mapMaSo.put("tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan",
						tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan);

				// ve chinh sach che do, cc, vc - 10
				tongCongTongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC10 += tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC;
				mapMaSo.put("tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC",
						tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC);

				// linh vuc kinh te chinh tri xa hoi khac - 11
				tongCongTongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac11 += tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac;
				mapMaSo.put("tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac",
						tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac);

				// linh vuc tu phap - 12
				Long tongSoDonKhieuNaiLinhVucTuPhap12 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(predAllDXLDDonVi,
								linhVucHanhChinhKhieuNaiTuPhap);
				tongCongTongSoDonKhieuNaiLinhVucTuPhap12 += tongSoDonKhieuNaiLinhVucTuPhap12;
				mapMaSo.put("tongSoDonKhieuNaiLinhVucTuPhap", tongSoDonKhieuNaiLinhVucTuPhap12);

				// linh vuc ve dang - 13
				Long tongSoDonKhieuNaiLinhVucVeDang13 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(predAllDXLDDonVi,
								linhVucHanhChinhKhieuNaiVeDang);
				tongCongTongSoDonKhieuNaiLinhVucVeDang13 += tongSoDonKhieuNaiLinhVucVeDang13;
				mapMaSo.put("tongSoDonKhieuNaiLinhVucVeDang", tongSoDonKhieuNaiLinhVucVeDang13);

				Long tongSoDonToCaoLinhVucHanhChinh = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi,
								linhVucHanhChinhDonToCao);
				Long tongSoDonToCaoLinhVucTuPhap = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucTuPhapDonToCao);
				Long tongSoDonToCaoLinhVucThamNhung = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi,
								linhVucThamNhungDonToCao);
				Long tongSoDonToCaoLinhVucVeDang = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucVeDangDonToCao);
				Long tongSoDonToCaoLinhVucKhac = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucKhacDonToCao);
				Long tongSoDonLinhVucToCao = tongSoDonToCaoLinhVucHanhChinh + tongSoDonToCaoLinhVucTuPhap
						+ tongSoDonToCaoLinhVucThamNhung + tongSoDonToCaoLinhVucVeDang + tongSoDonToCaoLinhVucKhac;

				// tong so don linh vuc to cao - 14
				tongCongTongSoDonLinhVucToCao14 += tongSoDonLinhVucToCao;
				mapMaSo.put("tongSoDonLinhVucToCao", tongSoDonLinhVucToCao);

				// don to cao linh vuc hanh chinh - 15
				tongCongTongSoDonToCaoLinhVucHanhChinh15 += tongSoDonToCaoLinhVucHanhChinh;
				mapMaSo.put("tongSoDonToCaoLinhVucHanhChinh", tongSoDonToCaoLinhVucHanhChinh);

				// don to cao linh vuc tu phap - 16
				tongCongTongSoDonToCaoLinhVucTuPhap16 += tongSoDonToCaoLinhVucTuPhap;
				mapMaSo.put("tongSoDonToCaoLinhVucTuPhap", tongSoDonToCaoLinhVucTuPhap);

				// don to cao linh vuc tham nhung - 17
				tongCongTongSoDonToCaoLinhVucThamNhung17 += tongSoDonToCaoLinhVucThamNhung;
				mapMaSo.put("tongSoDonToCaoLinhVucThamNhung", tongSoDonToCaoLinhVucThamNhung);

				// don to cao linh vuc ve dang - 18
				tongCongTongSoDonToCaoLinhVucVeDang18 += tongSoDonToCaoLinhVucVeDang;
				mapMaSo.put("tongSoDonToCaoLinhVucVeDang", tongSoDonToCaoLinhVucVeDang);

				// don to cao linh vuc khac - 19
				tongCongTongSoDonToCaoLinhVucKhac19 += tongSoDonToCaoLinhVucKhac;
				mapMaSo.put("tongSoDonToCaoLinhVucKhac", tongSoDonToCaoLinhVucKhac);

				Long tongSoDonToCaoLinhVucThamNhungTQGQHanhChinh17 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucChaCoTQGQLaHanhChinh(predAllDXLDDonVi,
								linhVucThamNhungDonToCao, thamQuyenGiaiQuyetHanhChinh);
				Long tongSoDonToCaoLinhVucThamNhungTQGQHanhChinh19 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucChaCoTQGQLaHanhChinh(predAllDXLDDonVi,
								linhVucKhacDonToCao, thamQuyenGiaiQuyetHanhChinh);
				Long tongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap = 
						//tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai
						//+ tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan
						//+ tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC
//						+ tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac + tongSoDonToCaoLinhVucHanhChinh
						tongSoDonKhieuNaiLinhVucHanhChinh + tongSoDonToCaoLinhVucHanhChinh
						+ tongSoDonToCaoLinhVucThamNhungTQGQHanhChinh17 + tongSoDonToCaoLinhVucThamNhungTQGQHanhChinh19;

				// theo tham quyen giai quyet - cua cac co quan hanh chinh cac
				// cap - 20
				tongCongTongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap20 += tongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap;
				mapMaSo.put("tongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap",
						tongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap);

				Long tongSoDonToCaoLinhVucThamNhungTQGQTuPhap17 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucChaCoTQGQLaHanhChinh(predAllDXLDDonVi,
								linhVucThamNhungDonToCao, thamQuyenGiaiQuyetTuPhap);
				Long tongSoDonToCaoLinhVucThamNhungTQGQTuPhap19 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucChaCoTQGQLaHanhChinh(predAllDXLDDonVi,
								linhVucKhacDonToCao, thamQuyenGiaiQuyetTuPhap);
				Long tongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap = tongSoDonKhieuNaiLinhVucTuPhap12
						+ tongSoDonToCaoLinhVucTuPhap + tongSoDonToCaoLinhVucThamNhungTQGQTuPhap17
						+ tongSoDonToCaoLinhVucThamNhungTQGQTuPhap19;
				// theo tham quyen giai quyet - cua cac co quan tu phap cac cap
				// - 21
				tongCongTongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap21 += tongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap;
				mapMaSo.put("tongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap", tongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap);

				Long tongSoDonTheoTQGQCuaCoQuanDang = tongSoDonKhieuNaiLinhVucVeDang13 + tongSoDonToCaoLinhVucVeDang;
				// theo tham quyen giai quyet - cua co quan dang - 22
				tongTongSoDonTheoTQGQCuaCoQuanDang22 += tongSoDonTheoTQGQCuaCoQuanDang;
				mapMaSo.put("tongSoDonTheoTQGQCuaCoQuanDang", tongSoDonTheoTQGQCuaCoQuanDang);

				// theo trinh tu giai quyet - chua giai quyet - 23
				Long tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonXLDTheoTrinhTuGiaiQuyetChuaDuocGiaiQuyet(predAllDXLDDonVi);
				tongCongTongSoDonTheoTTGiaiQuyetChuaGiaiQuyet23 += tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet;
				mapMaSo.put("tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet", tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet);

				//theo trinh tu giai quyet - da duoc giai quyet lan dau - 24
				Long tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyetLanDau = 0L;
				if (tongSoDonDuDieuKienThuLy > 0) { 
					tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyetLanDau = tongSoDonDuDieuKienThuLy - tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet;
				}

				tongCongTongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyet24 += tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyetLanDau;
				mapMaSo.put("tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyet", tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyetLanDau);

				// don kien nghi phan anh - 25
				Long tongSoDonXLDDonKienNghiPhanAnh = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonKienNghiPhanAnhHXLLuuDonVaTheoDoi(predAllDXLDDonVi);
				tongCongTongSoDonXLDDonKienNghiPhanAnh25 += tongSoDonXLDDonKienNghiPhanAnh;
				mapMaSo.put("tongSoDonXLDDonKienNghiPhanAnh", tongSoDonXLDDonKienNghiPhanAnh);

				// so van ban huong dan - 26
				Long tongSoDonXLDSoVanBanHuongDan26 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonHXLTraDonVaHuongDan(predAllDXLDDonVi);
				tongCongTongSoDonXLDSoVanBanHuongDan26 += tongSoDonXLDSoVanBanHuongDan26;
				mapMaSo.put("tongSoDonXLDSoVanBanHuongDan", tongSoDonXLDSoVanBanHuongDan26);

				// so don chuyen co tham quyen - 27
				Long tongSoDonChuyenCQCoThamQuyen27 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonChuyenCQCoThamQuyen(predAllDXLDDonVi);
				tongCongTongSoDonChuyenCQCoThamQuyen27 += tongSoDonChuyenCQCoThamQuyen27;
				mapMaSo.put("tongSoDonChuyenCQCoThamQuyen", tongSoDonChuyenCQCoThamQuyen27);

				// so cong van don doc viec giai quyet - 28
				Long tongSoDonCoSoCongVanDonDocViecGiaiQuyet28 = 0L;
				tongCongTongSoDonCoSoCongVanDonDocViecGiaiQuyet28 += tongSoDonCoSoCongVanDonDocViecGiaiQuyet28;
				mapMaSo.put("tongSoDonCoSoCongVanDonDocViecGiaiQuyet", tongSoDonCoSoCongVanDonDocViecGiaiQuyet28);

				// tong so don thuoc tham quyen khieu nai - 29
				Long tongSoDonChuyenCQCoThamQuyen29 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonThuocThamQuyenKhieuNai(predAllDXLDDonVi);
				tongCongTongSoDonThuocThamQuyenKhieuNai29 += tongSoDonChuyenCQCoThamQuyen29;
				mapMaSo.put("tongSoDonThuocThamQuyenKhieuNai", tongSoDonChuyenCQCoThamQuyen29);

				// tong so don thuoc tham quyen to cao - 30
				Long tongSoDonThuocThamQuyenToCao30 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonThuocThamQuyenToCao(predAllDXLDDonVi);
				tongCongTongSoDonThuocThamQuyenToCao30 += tongSoDonThuocThamQuyenToCao30;
				mapMaSo.put("tongSoDonThuocThamQuyenToCao", tongSoDonThuocThamQuyenToCao30);

				// ghi chu - 31
				mapMaSo.put("ghiChu", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
				mapDonVi = new HashMap<String, Object>();
			}

			mapTongCong.put("tongSoDonTiepNhanXLDTCD", tongCongTongSoDonTiepNhanXLDTCD1);
			mapTongCong.put("tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy",
					tongCongTongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy2);
			mapTongCong.put("tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy",
					tongCongTongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy3);
			mapTongCong.put("tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang",
					tongCongTongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang4);
			mapTongCong.put("tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang",
					tongCongTongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang5);
			mapTongCong.put("tongSoDonDuDieuKienThuLy", tongCongTongSoDonDuDieuKienThuLy6);
			mapTongCong.put("tongSoDonKhieuNaiLinhVucHanhChinh", tongCongTongSoDonKhieuNaiLinhVucHanhChinh7);
			mapTongCong.put("tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai",
					tongCongTongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai8);
			mapTongCong.put("tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan",
					tongCongTongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan9);
			mapTongCong.put("tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC",
					tongCongTongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC10);
			mapTongCong.put("tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac",
					tongCongTongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac11);
			mapTongCong.put("tongSoDonKhieuNaiLinhVucTuPhap", tongCongTongSoDonKhieuNaiLinhVucTuPhap12);
			mapTongCong.put("tongSoDonKhieuNaiLinhVucVeDang", tongCongTongSoDonKhieuNaiLinhVucVeDang13);
			mapTongCong.put("tongSoDonLinhVucToCao", tongCongTongSoDonLinhVucToCao14);
			mapTongCong.put("tongSoDonToCaoLinhVucHanhChinh", tongCongTongSoDonToCaoLinhVucHanhChinh15);
			mapTongCong.put("tongSoDonToCaoLinhVucTuPhap", tongCongTongSoDonToCaoLinhVucTuPhap16);
			mapTongCong.put("tongSoDonToCaoLinhVucThamNhung", tongCongTongSoDonToCaoLinhVucThamNhung17);
			mapTongCong.put("tongSoDonToCaoLinhVucVeDang", tongCongTongSoDonToCaoLinhVucVeDang18);
			mapTongCong.put("tongSoDonToCaoLinhVucKhac", tongCongTongSoDonToCaoLinhVucKhac19);
			mapTongCong.put("tongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap",
					tongCongTongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap20);
			mapTongCong.put("tongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap",
					tongCongTongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap21);
			mapTongCong.put("tongSoDonTheoTQGQCuaCoQuanDang", tongTongSoDonTheoTQGQCuaCoQuanDang22);
			mapTongCong.put("tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet", tongCongTongSoDonTheoTTGiaiQuyetChuaGiaiQuyet23);
			mapTongCong.put("tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyet",
					tongCongTongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyet24);
			mapTongCong.put("tongSoDonXLDDonKienNghiPhanAnh", tongCongTongSoDonXLDDonKienNghiPhanAnh25);
			mapTongCong.put("tongSoDonXLDSoVanBanHuongDan", tongCongTongSoDonXLDSoVanBanHuongDan26);
			mapTongCong.put("tongSoDonChuyenCQCoThamQuyen", tongCongTongSoDonChuyenCQCoThamQuyen27);
			mapTongCong.put("tongSoDonCoSoCongVanDonDocViecGiaiQuyet",
					tongCongTongSoDonCoSoCongVanDonDocViecGiaiQuyet28);
			mapTongCong.put("tongSoDonThuocThamQuyenKhieuNai", tongCongTongSoDonThuocThamQuyenKhieuNai29);
			mapTongCong.put("tongSoDonThuocThamQuyenToCao", tongCongTongSoDonThuocThamQuyenToCao30);

			map.put("tongCongs", mapTongCong);
			map.put("maSos", maSos);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaXuLyDonThu/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp báo cáo xử lý đơn thư", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExceluLyDonThu(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe) throws IOException {

		try {
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<Map<String, Object>> maSos = new ArrayList<>();

			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

			if (year == null || year == 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}

//			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService
//					.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService
					.predicateFindAllXLD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLDTrongKy = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService
					.predicateFindAllXLDTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLDKyTruoc = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService
					.predicateFindAllXLDKyTruoc(loaiKy, quy, year, month, tuNgay, denNgay);

			// khieu nai
			List<Long> idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(59L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(58L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(57L);

			List<Long> idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(54L);

			LinhVucDonThu linhVucHanhChinhDonKhieuNai = linhVucDonThuRepo.findOne(1L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiVeDang = linhVucDonThuRepo.findOne(56L);

			// to cao
			LinhVucDonThu linhVucHanhChinhDonToCao = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhapDonToCao = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhungDonToCao = linhVucDonThuRepo.findOne(39L);
			LinhVucDonThu linhVucVeDangDonToCao = linhVucDonThuRepo.findOne(62L);
			LinhVucDonThu linhVucKhacDonToCao = linhVucDonThuRepo.findOne(63L);

			// tham quyen giai quyet
			ThamQuyenGiaiQuyet thamQuyenGiaiQuyetHanhChinh = thamQuyenGiaiQuyetRepo.findOne(1L);
			ThamQuyenGiaiQuyet thamQuyenGiaiQuyetTuPhap = thamQuyenGiaiQuyetRepo.findOne(2L);
			// ThamQuyenGiaiQuyet thamQuyenGiaiQuyetCQDang =
			// thamQuyenGiaiQuyetRepo.findOne(3L);

			List<LinhVucDonThu> linhVucLienQuanDenDatDais = new ArrayList<LinhVucDonThu>();
			linhVucLienQuanDenDatDais.addAll(linhVucDonThuService
					.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais));

			List<LinhVucDonThu> linhVucCTVHXHKKhacs = new ArrayList<LinhVucDonThu>();
			linhVucCTVHXHKKhacs.addAll(linhVucDonThuService
					.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs));

			List<LinhVucDonThu> linhVucVeChinhSachCCVCs = new ArrayList<LinhVucDonThu>();
			linhVucVeChinhSachCCVCs.addAll(
					linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs));

			List<LinhVucDonThu> linhVucVeNhaCuaTaiSans = new ArrayList<LinhVucDonThu>();
			linhVucVeNhaCuaTaiSans.addAll(
					linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));

			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));

			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null) {
					for (CoQuanQuanLy dv : listDonVis) {
						CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
						if (coQuan != null) {
							list.add(coQuan);
						}
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null) {
					if (listCapDonVis.size() > 0) {
						List<Long> thamSos = new ArrayList<Long>();
						thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

						for (CapCoQuanQuanLy cdv : listCapDonVis) {
							CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
							if (capDonVi != null) {
								listCapCQs.add(capDonVi.getId());
							}
						}
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			}

			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDXLDDonVi = predAllDSXLD;
				//BooleanExpression predAllDSTCDDonVi = predAllDSTCD;
				BooleanExpression predAllDXLDDonViTrongKy = predAllDSXLDTrongKy;
				BooleanExpression predAllDXLDDonViKyTruoc = predAllDSXLDKyTruoc;
				
				if (cq.getCapCoQuanQuanLy().getId().equals(Long.valueOf(thamSoUBNDTP.getGiaTri().toString()))) {
//					predAllDSTCDDonVi = predAllDSTCDDonVi
//							.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(cq.getId()));
					predAllDXLDDonVi = predAllDXLDDonVi.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId()));
					predAllDXLDDonViTrongKy = predAllDXLDDonViTrongKy.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId()));
					predAllDXLDDonViKyTruoc = predAllDXLDDonViKyTruoc.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId()));
				} else {
//					predAllDSTCDDonVi = predAllDSTCDDonVi.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id
//							.eq(cq.getId()).or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(cq.getId()))
//							.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(cq.getId())));

					predAllDXLDDonVi = predAllDXLDDonVi.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId())
							.or(QXuLyDon.xuLyDon.donViXuLy.cha.id.eq(cq.getId()))
							.or(QXuLyDon.xuLyDon.donViXuLy.cha.cha.id.eq(cq.getId())));
					
					predAllDXLDDonViTrongKy = predAllDXLDDonViTrongKy.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId())
							.or(QXuLyDon.xuLyDon.donViXuLy.cha.id.eq(cq.getId()))
							.or(QXuLyDon.xuLyDon.donViXuLy.cha.cha.id.eq(cq.getId())));
					
					predAllDXLDDonViKyTruoc = predAllDXLDDonViKyTruoc.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId())
							.or(QXuLyDon.xuLyDon.donViXuLy.cha.id.eq(cq.getId()))
							.or(QXuLyDon.xuLyDon.donViXuLy.cha.cha.id.eq(cq.getId())));
				}

				Long tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTiepNhanTrongKyDonCoNhieuNguoiDungTenXLDTCD(predAllDXLDDonViTrongKy, loaiKy, quy, month,
								tuNgay, denNgay);
				Long tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTiepNhanTrongKyDonCoMotNguoiDungTenXLDTCD(predAllDXLDDonViTrongKy, loaiKy, quy, month,
								tuNgay, denNgay);
				Long tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonKyTruocChuyenSangDonCoNhieuNguoiDungTenXLDTCD(predAllDXLDDonViKyTruoc, loaiKy,
								year, quy, month, tuNgay, denNgay);
				Long tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang = thongKeBaoCaoTongHopKQXLDService
						.getTongSoKyTruocChuyenSangDonCoMotNguoiDungTenXLDTCD(predAllDXLDDonViKyTruoc, loaiKy, year,
								quy, month, tuNgay, denNgay);
				Long tongSoDonTiepNhanXLDTCD = tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy
						+ tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy
						+ tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang
						+ tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang;

				mapMaSo.put("0", cq.getTen());
				mapMaSo.put("1", tongSoDonTiepNhanXLDTCD);
				mapMaSo.put("2", tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy);
				mapMaSo.put("3", tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy);
				mapMaSo.put("4", tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang);
				mapMaSo.put("5", tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang);

				// Long tongSoDonCoHXLLuDonVaTheoDoi =
				// thongKeBaoCaoTongHopKQXLDService.getTongSoDonDuDieuKienThuLyLuuDonVaTheoDoi(predAllDXLDDonVi);
				// Long tongSoDonDuDieuKienThuLy = tongSoDonTiepNhanXLDTCD -
				// tongSoDonCoHXLLuDonVaTheoDoi;
				Long tongSoDonDuDieuKienThuLy = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonDuDieuKienThuLy(predAllDXLDDonVi);
				mapMaSo.put("6", tongSoDonDuDieuKienThuLy);

				Long tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi,
								linhVucHanhChinhDonKhieuNai, linhVucLienQuanDenDatDais);
				Long tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi,
								linhVucHanhChinhDonKhieuNai, linhVucVeNhaCuaTaiSans);
				Long tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi,
								linhVucHanhChinhDonKhieuNai, linhVucVeChinhSachCCVCs);
				Long tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi,
								linhVucHanhChinhDonKhieuNai, linhVucCTVHXHKKhacs);
				Long tongSoDonKhieuNaiLinhVucHanhChinh = tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai
						+ tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan
						+ tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC
						+ tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac;

				mapMaSo.put("7", tongSoDonKhieuNaiLinhVucHanhChinh);
				mapMaSo.put("8", tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai);
				mapMaSo.put("9", tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan);
				mapMaSo.put("10", tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC);
				mapMaSo.put("11", tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac);

				Long tongSoDonKhieuNaiLinhVucTuPhap12 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(predAllDXLDDonVi,
								linhVucHanhChinhKhieuNaiTuPhap);
				mapMaSo.put("12", tongSoDonKhieuNaiLinhVucTuPhap12);
				// mapMaSo.put("12",
				// thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(predAllDXLDDonVi,
				// linhVucHanhChinhKhieuNaiTuPhap));

				Long tongSoDonKhieuNaiLinhVucVeDang13 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(predAllDXLDDonVi,
								linhVucHanhChinhKhieuNaiVeDang);
				// mapMaSo.put("13",
				// thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(predAllDXLDDonVi,
				// linhVucHanhChinhKhieuNaiVeDang));
				mapMaSo.put("13", tongSoDonKhieuNaiLinhVucVeDang13);

				Long tongSoDonToCaoLinhVucHanhChinh = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi,
								linhVucHanhChinhDonToCao);
				Long tongSoDonToCaoLinhVucTuPhap = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucTuPhapDonToCao);
				Long tongSoDonToCaoLinhVucThamNhung = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucChiTietCha(predAllDXLDDonVi,
								linhVucTuPhapDonToCao, linhVucThamNhungDonToCao);
				Long tongSoDonToCaoLinhVucVeDang = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucVeDangDonToCao);
				Long tongSoDonToCaoLinhVucKhac = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucKhacDonToCao);
				Long tongSoDonLinhVucToCao = tongSoDonToCaoLinhVucHanhChinh + tongSoDonToCaoLinhVucTuPhap
						+ tongSoDonToCaoLinhVucThamNhung + tongSoDonToCaoLinhVucVeDang + tongSoDonToCaoLinhVucKhac;

				mapMaSo.put("14", tongSoDonLinhVucToCao);
				mapMaSo.put("15", tongSoDonToCaoLinhVucHanhChinh);
				mapMaSo.put("16", tongSoDonToCaoLinhVucTuPhap);
				mapMaSo.put("17", tongSoDonToCaoLinhVucThamNhung);
				mapMaSo.put("18", tongSoDonToCaoLinhVucVeDang);
				mapMaSo.put("19", tongSoDonToCaoLinhVucKhac);

				Long tongSoDonToCaoLinhVucThamNhungTQGQHanhChinh17 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucChaCoTQGQLaHanhChinh(predAllDXLDDonVi,
								linhVucThamNhungDonToCao, thamQuyenGiaiQuyetHanhChinh);
				Long tongSoDonToCaoLinhVucThamNhungTQGQHanhChinh19 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucChaCoTQGQLaHanhChinh(predAllDXLDDonVi,
								linhVucKhacDonToCao, thamQuyenGiaiQuyetHanhChinh);
				Long tongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap = 
						//tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai
						//+ tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan
						//+ tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC
						tongSoDonKhieuNaiLinhVucHanhChinh + tongSoDonToCaoLinhVucHanhChinh
						+ tongSoDonToCaoLinhVucThamNhungTQGQHanhChinh17 + tongSoDonToCaoLinhVucThamNhungTQGQHanhChinh19;

				mapMaSo.put("20", tongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap);
				// mapMaSo.put("20",
				// thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoThamQuyenGiaiQuyet(predAllDXLDDonVi,
				// thamQuyenGiaiQuyetHanhChinh));

				Long tongSoDonToCaoLinhVucThamNhungTQGQTuPhap17 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucChaCoTQGQLaHanhChinh(predAllDXLDDonVi,
								linhVucThamNhungDonToCao, thamQuyenGiaiQuyetTuPhap);
				Long tongSoDonToCaoLinhVucThamNhungTQGQTuPhap19 = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucChaCoTQGQLaHanhChinh(predAllDXLDDonVi,
								linhVucKhacDonToCao, thamQuyenGiaiQuyetTuPhap);
				Long tongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap = tongSoDonKhieuNaiLinhVucTuPhap12
						+ tongSoDonToCaoLinhVucTuPhap + tongSoDonToCaoLinhVucThamNhungTQGQTuPhap17
						+ tongSoDonToCaoLinhVucThamNhungTQGQTuPhap19;
				mapMaSo.put("21", tongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap);
				// mapMaSo.put("21",
				// thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoThamQuyenGiaiQuyet(predAllDXLDDonVi,
				// thamQuyenGiaiQuyetTuPhap));

				Long tongSoDonTheoTQGQCuaCoQuanDang = tongSoDonKhieuNaiLinhVucVeDang13 + tongSoDonToCaoLinhVucVeDang;
				// mapMaSo.put("22",
				// thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoThamQuyenGiaiQuyet(predAllDXLDDonVi,
				// thamQuyenGiaiQuyetCQDang));
				mapMaSo.put("22", tongSoDonTheoTQGQCuaCoQuanDang);

				Long tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet = thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonXLDTheoTrinhTuGiaiQuyetChuaDuocGiaiQuyet(predAllDXLDDonVi);
				mapMaSo.put("23", tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet);

				Long tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyetLanDau = 0L;
				if (tongSoDonDuDieuKienThuLy > 0) { 
					tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyetLanDau = tongSoDonDuDieuKienThuLy - tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet;
				}
				// mapMaSo.put("24",
				// thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoTrinhTuGiaiQuyetDaDuocGiaiQuyetLanDau(predAllDXLDDonVi));
				mapMaSo.put("24", tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyetLanDau);

				// mapMaSo.put("25",
				// thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoTrinhTuGiaiQuyetDaDuocGiaiQuyetNhieuLan(predAllDXLDDonVi));

				mapMaSo.put("25", thongKeBaoCaoTongHopKQXLDService
						.getTongSoDonKienNghiPhanAnhHXLLuuDonVaTheoDoi(predAllDXLDDonVi));

				mapMaSo.put("26", thongKeBaoCaoTongHopKQXLDService.getTongSoDonHXLTraDonVaHuongDan(predAllDXLDDonVi));

				mapMaSo.put("27", thongKeBaoCaoTongHopKQXLDService.getTongSoDonChuyenCQCoThamQuyen(predAllDXLDDonVi));

				mapMaSo.put("28", 0);

				mapMaSo.put("29",
						thongKeBaoCaoTongHopKQXLDService.getTongSoDonThuocThamQuyenKhieuNai(predAllDXLDDonVi));

				mapMaSo.put("30", thongKeBaoCaoTongHopKQXLDService.getTongSoDonThuocThamQuyenToCao(predAllDXLDDonVi));

				mapMaSo.put("31", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopBaoCaoXuLyDonThu(response, "DanhSachTongHopThongKeBaoCaoXuLyDonThu", "sheetName",
					maSos, tuNgay, denNgay, "Danh sách tổng hợp thống kê báo cáo xử lý đơn thư");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaGiaiQuyetDonKhieuNai/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả giải quyết đơn khiếu nại", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelGiaiQuyetKhieuNai(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "donViId", required = false) Long donViId) throws IOException {

		try {
			ThamSo phongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo phuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			Long idCapCQQLPhongBan = Long.valueOf(phongBan.getGiaTri().toString());
			Long idCapCQQLPhuongXa = Long.valueOf(phuongXa.getGiaTri().toString());
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<Map<String, Object>> maSos = new ArrayList<>();

			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

			if (year == null || year == 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}

			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));

			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null) {
					for (CoQuanQuanLy dv : listDonVis) {
						CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
						if (coQuan != null) {
							list.add(coQuan);
						}
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null) {
					if (listCapDonVis.size() > 0) {
						List<Long> thamSos = new ArrayList<Long>();
						thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

						for (CapCoQuanQuanLy cdv : listCapDonVis) {
							CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
							if (capDonVi != null) {
								listCapCQs.add(capDonVi.getId());
							}
						}
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			}

			BooleanExpression predAllDSDon = (BooleanExpression) thongKeBaoCaoTongHopKQGQDService
					.predicateFindAllGQD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSDonTrongKy = (BooleanExpression) thongKeBaoCaoTongHopKQGQDService
					.predicateFindAllGQDTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSDonTruocKy = (BooleanExpression) thongKeBaoCaoTongHopKQGQDService
					.predicateFindAllGQDTruocKy(loaiKy, quy, year, month, tuNgay, denNgay);
			predAllDSDon = predAllDSDon.and(QDon.don.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
					.and(QDon.don.thanhLapDon.eq(true)).and(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.DE_XUAT_THU_LY));
			predAllDSDonTrongKy = predAllDSDonTrongKy.and(QDon.don.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
					.and(QDon.don.thanhLapDon.eq(true)).and(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.DE_XUAT_THU_LY));
			predAllDSDonTruocKy = predAllDSDonTruocKy.and(QDon.don.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
					.and(QDon.don.thanhLapDon.eq(true)).and(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.DE_XUAT_THU_LY));

			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSGQDDonVi = predAllDSDon;
				BooleanExpression predAllDSGQDDonViTrongKy = predAllDSDonTrongKy;
				BooleanExpression predAllDSGQDDonViTruocKy = predAllDSDonTruocKy;
				predAllDSGQDDonVi = predAllDSGQDDonVi.and(QDon.don.donViXuLyGiaiQuyet.id.eq(cq.getId())
						.or(QDon.don.donViXuLyGiaiQuyet.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))
						.or(QDon.don.donViXuLyGiaiQuyet.cha.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa)))));

				predAllDSGQDDonViTrongKy = predAllDSGQDDonViTrongKy.and(QDon.don.donViXuLyGiaiQuyet.id.eq(cq.getId())
						.or(QDon.don.donViXuLyGiaiQuyet.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))
						.or(QDon.don.donViXuLyGiaiQuyet.cha.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa)))));
				
				predAllDSGQDDonViTruocKy = predAllDSGQDDonViTruocKy.and(QDon.don.donViXuLyGiaiQuyet.id.eq(cq.getId())
						.or(QDon.don.donViXuLyGiaiQuyet.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))
						.or(QDon.don.donViXuLyGiaiQuyet.cha.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa)))));

				// ThongKeBaoCaoLoaiKyEnum loaiKyEnum =
				// ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
				Long donNhanTrongKyBaoCao = thongKeBaoCaoTongHopKQGQDService
						.getTongSoDonTrongKyBaoCao(predAllDSGQDDonViTrongKy, loaiKy, quy, year, month, tuNgay, denNgay);

				Long donThuLyKyTruocChuyenSang1 = loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON)
						&& StringUtils.isBlank(tuNgay) ? 0
								: thongKeBaoCaoTongHopKQGQDService.getTongSoDonTonKyTruoc(predAllDSGQDDonViTruocKy,
										loaiKy, quy, year, month, tuNgay, denNgay);
				
//				Long soDonThuocThamQuyen5 = loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON)
//						&& StringUtils.isBlank(tuNgay) ? 0
//								: thongKeBaoCaoTongHopKQGQDService.getTongSoDonKhieuNaiThuocThamQuyenCuaKyTruoc(
//										predAllDSGQDDonViTruocKy, loaiKy, quy, year, month, tuNgay, denNgay);
				
//				Long donTonKyTruocChuyenSang = donThuLyKyTruocChuyenSang1 - soDonThuocThamQuyen5;
//				Long tongSoDonKhieuNai = donNhanTrongKyBaoCao + donTonKyTruocChuyenSang;

				Long tongSoDonKhieuNai = donNhanTrongKyBaoCao + donThuLyKyTruocChuyenSang1; //5
				Long donTonKyTruocChuyenSang = donThuLyKyTruocChuyenSang1; //2
				
				mapMaSo.put("0", cq.getTen());

				mapMaSo.put("1", tongSoDonKhieuNai);

				// mapMaSo.put("2",
				// thongKeBaoCaoTongHopKQGQDService.getTongSoDonTrongKyBaoCao(predAllDSGQDDonVi,
				// loaiKy, quy, year, month, tuNgay, denNgay));
				mapMaSo.put("2", donNhanTrongKyBaoCao);

				mapMaSo.put("3", donTonKyTruocChuyenSang);

				mapMaSo.put("4", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViec(predAllDSGQDDonVi));
				mapMaSo.put("5",
						thongKeBaoCaoTongHopKQGQDService.getTongSoDonKhieuNaiThuocThamQuyen(predAllDSGQDDonVi));

				// mapMaSo.put("6",
				// thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecToCaoThuocThamQuyen(predAllDSGQDDonVi));

				Long soVuViecRutDonThongQuaGiaiThichThuyetPhuc8 = thongKeBaoCaoTongHopKQGQDService
						.getTongSoDonDinhChi(predAllDSGQDDonVi);
				Long khieuNaiDung9 = thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiDung(predAllDSGQDDonVi);
				Long khieuNaiSai10 = thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiSai(predAllDSGQDDonVi);
				Long khieuNaiDungMotPhan11 = thongKeBaoCaoTongHopKQGQDService
						.getTongSoVuViecDungMotPhan(predAllDSGQDDonVi);
				Long soVuViecGiaiQuyetBangQDHanhChinh7 = khieuNaiDung9 + khieuNaiSai10 + khieuNaiDungMotPhan11;
				Long soVuViecThuocThamQuyen6 = soVuViecGiaiQuyetBangQDHanhChinh7
						+ soVuViecRutDonThongQuaGiaiThichThuyetPhuc8;

				mapMaSo.put("6", soVuViecThuocThamQuyen6);

				// mapMaSo.put("7",
				// thongKeBaoCaoTongHopKQGQDService.getTongSoDonCoQuyetDinh(predAllDSGQDDonVi));

				mapMaSo.put("7", soVuViecGiaiQuyetBangQDHanhChinh7);

				// mapMaSo.put("8",
				// thongKeBaoCaoTongHopKQGQDService.getTongSoDonDinhChi(predAllDSGQDDonVi));
				mapMaSo.put("8", soVuViecRutDonThongQuaGiaiThichThuyetPhuc8);

				// mapMaSo.put("9",
				// thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiDung(predAllDSGQDDonVi));

				mapMaSo.put("9", khieuNaiDung9);

				// mapMaSo.put("10",
				// thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiSai(predAllDSGQDDonVi));

				mapMaSo.put("10", khieuNaiSai10);

				// mapMaSo.put("11",
				// thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecDungMotPhan(predAllDSGQDDonVi));

				mapMaSo.put("11", khieuNaiDungMotPhan11);

				mapMaSo.put("12", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetLan1(predAllDSGQDDonVi));
				mapMaSo.put("13",
						thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetLan2CongNhan(predAllDSGQDDonVi));
				mapMaSo.put("14",
						thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetLan2HuySua(predAllDSGQDDonVi));
				mapMaSo.put("15", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi,
						"tienNhaNuoc"));
				mapMaSo.put("16", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi,
						"datNhaNuoc"));
				mapMaSo.put("17", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi,
						"tienCongDan"));
				mapMaSo.put("18", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi,
						"datCongDan"));
				mapMaSo.put("19", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiDuocTraLaiQuyenLoi(predAllDSGQDDonVi));
				mapMaSo.put("20", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiXuLyHanhChinh(predAllDSGQDDonVi));
				mapMaSo.put("21", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiDaBiXuLyHanhChinh(predAllDSGQDDonVi));
				mapMaSo.put("22", thongKeBaoCaoTongHopKQGQDService.getTongSoVuChuyenCoQuanDieuTra(predAllDSGQDDonVi));
				mapMaSo.put("23",
						thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongChuyenCoQuanDieuTra(predAllDSGQDDonVi));
				mapMaSo.put("24", thongKeBaoCaoTongHopKQGQDService.getTongSoVuDaKhoiTo(predAllDSGQDDonVi));
				mapMaSo.put("25", thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongDaKhoiTo(predAllDSGQDDonVi));
				mapMaSo.put("26", thongKeBaoCaoTongHopKQGQDService
						.getTongSoVuViecGiaiQuyetKhieuNaiDungThoiHan(predAllDSGQDDonVi));
				mapMaSo.put("27",
						thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetKhieuNaiQuaThoiHan(predAllDSGQDDonVi));

				mapMaSo.put("28", soVuViecGiaiQuyetBangQDHanhChinh7);

				Long tongSoQuyetDinhPhaiToChucThucHienDaThucHien = thongKeBaoCaoTongHopKQGQDService
						.getTongSoQuyetDinhPhaiToChucThucHienDaThucHien(predAllDSGQDDonVi);
				mapMaSo.put("29", tongSoQuyetDinhPhaiToChucThucHienDaThucHien);

				mapMaSo.put("30", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"tienNhaNuocPhaiThu"));
				mapMaSo.put("31", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"datNhaNuocPhaiThu"));
				mapMaSo.put("32", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"tienNhaNuocDaThu"));
				mapMaSo.put("33", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"datNhaNuocDaThu"));
				mapMaSo.put("34", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"tienCongDanPhaiTra"));
				mapMaSo.put("35", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"datCongDanPhaiTra"));
				mapMaSo.put("36", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"tienCongDanDaTra"));
				mapMaSo.put("37", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"datCongDanDaTra"));
				mapMaSo.put("38", "");
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopBaoCaoGiaiQuyetKhieuNai(response, "DanhSachTongHopThongKeBaoCaoGiaiQuyetKhieuNai",
					"sheetName", maSos, tuNgay, denNgay,
					"Danh sách tổng hợp thống kê báo cáo giải quyết đơn khiếu nại");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaGiaiQuyetDonToCao/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả giải quyết đơn tố cáo", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelGiaiQuyetToCao(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "donViId", required = false) Long donViId) throws IOException {

		try {
			ThamSo phongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo phuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			Long idCapCQQLPhongBan = Long.valueOf(phongBan.getGiaTri().toString());
			Long idCapCQQLPhuongXa = Long.valueOf(phuongXa.getGiaTri().toString());
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<Map<String, Object>> maSos = new ArrayList<>();

			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

			if (year == null || year == 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}

			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));

			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null) {
					for (CoQuanQuanLy dv : listDonVis) {
						CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
						if (coQuan != null) {
							list.add(coQuan);
						}
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null) {
					if (listCapDonVis.size() > 0) {
						List<Long> thamSos = new ArrayList<Long>();
						thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

						for (CapCoQuanQuanLy cdv : listCapDonVis) {
							CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
							if (capDonVi != null) {
								listCapCQs.add(capDonVi.getId());
							}
						}
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			}

			BooleanExpression predAllDSDon = (BooleanExpression) thongKeBaoCaoTongHopKQGQDService
					.predicateFindAllGQD(loaiKy, quy, year, month, tuNgay, denNgay);
			predAllDSDon = predAllDSDon.and(QDon.don.loaiDon.eq(LoaiDonEnum.DON_TO_CAO))
					.and(QDon.don.thanhLapDon.eq(true)).and(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.DE_XUAT_THU_LY));

			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSGQDDonVi = predAllDSDon;

				predAllDSGQDDonVi = predAllDSGQDDonVi.and(QDon.don.donViXuLyGiaiQuyet.id.eq(cq.getId())
						.or(QDon.don.donViXuLyGiaiQuyet.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))
						.or(QDon.don.donViXuLyGiaiQuyet.cha.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa)))));

				mapMaSo.put("0", cq.getTen());
				mapMaSo.put("1", thongKeBaoCaoTongHopKQGQDService.getTongSoDon(predAllDSGQDDonVi));
				// ThongKeBaoCaoLoaiKyEnum loaiKyEnum =
				// ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
				Long donTonKyTruocChuyenSang = loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON)
						&& StringUtils.isBlank(tuNgay) ? 0
								: thongKeBaoCaoTongHopKQGQDService.getTongSoDonTonKyTruoc(predAllDSGQDDonVi, loaiKy,
										quy, year, month, tuNgay, denNgay);
				mapMaSo.put("2", thongKeBaoCaoTongHopKQGQDService.getTongSoDonTrongKyBaoCao(predAllDSGQDDonVi, loaiKy,
						quy, year, month, tuNgay, denNgay));
				mapMaSo.put("3", donTonKyTruocChuyenSang);
				mapMaSo.put("4", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViec(predAllDSGQDDonVi));
				mapMaSo.put("5", thongKeBaoCaoTongHopKQGQDService.getTongSoDonToCaoThuocThamQuyen(predAllDSGQDDonVi));
				mapMaSo.put("6",
						thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecToCaoThuocThamQuyen(predAllDSGQDDonVi));
				mapMaSo.put("7", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiDung(predAllDSGQDDonVi));
				mapMaSo.put("8", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiSai(predAllDSGQDDonVi));
				mapMaSo.put("9", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecDungMotPhan(predAllDSGQDDonVi));
				mapMaSo.put("10", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi,
						"tienNhaNuoc"));
				mapMaSo.put("11", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi,
						"datNhaNuoc"));
				mapMaSo.put("12", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi,
						"tienCongDan"));
				mapMaSo.put("13", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi,
						"datCongDan"));
				mapMaSo.put("14", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiDuocTraLaiQuyenLoi(predAllDSGQDDonVi));
				mapMaSo.put("15", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiXuLyHanhChinh(predAllDSGQDDonVi));
				mapMaSo.put("16", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiDaBiXuLyHanhChinh(predAllDSGQDDonVi));
				mapMaSo.put("17", thongKeBaoCaoTongHopKQGQDService.getTongSoVuChuyenCoQuanDieuTra(predAllDSGQDDonVi));
				mapMaSo.put("18",
						thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongChuyenCoQuanDieuTra(predAllDSGQDDonVi));
				mapMaSo.put("19", thongKeBaoCaoTongHopKQGQDService.getTongSoVuDaKhoiTo(predAllDSGQDDonVi));
				mapMaSo.put("20", thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongDaKhoiTo(predAllDSGQDDonVi));
				mapMaSo.put("21",
						thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetToCaoDungThoiHan(predAllDSGQDDonVi));
				mapMaSo.put("22",
						thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetToCaoQuaThoiHan(predAllDSGQDDonVi));
				mapMaSo.put("23", 0L);
				mapMaSo.put("24", 0L);
				mapMaSo.put("25", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"tienNhaNuocPhaiThu"));
				mapMaSo.put("26", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"datNhaNuocPhaiThu"));
				mapMaSo.put("27", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"tienNhaNuocDaThu"));
				mapMaSo.put("28", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"datNhaNuocDaThu"));
				mapMaSo.put("29", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"tienCongDanPhaiTra"));
				mapMaSo.put("30", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"datCongDanPhaiTra"));
				mapMaSo.put("31", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"tienCongDanDaTra"));
				mapMaSo.put("32", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi,
						"datCongDanDaTra"));
				mapMaSo.put("33", "");
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopBaoCaoGiaiQuyetToCao(response, "DanhSachTongHopThongKeBaoCaoGiaiQuyetToCao",
					"sheetName", maSos, tuNgay, denNgay, "Danh sách tổng hợp thống kê báo cáo giải quyết đơn tố cáo");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	/*
	 *
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoHanhChinh")
	@ApiOperation(value = "Tổng hợp kết quả thanh tra theo hành chính", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaThanhTraTheoHanhChinh(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			PersistentEntityResourceAssembler eass) {
		try {

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Long donViXuLy = Long
					.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());

			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			if (year == null) {
				year = Utils.localDateTimeNow().getYear();
			}

			Long field1 = 0L;
			Long field2 = 0L;
			Long field3 = 0L;
			Long field4 = 0L;
			Long field5 = 0L;
			Long field6 = 0L;
			Long field7 = 0L;
			Long field8 = 0L;
			Long field9 = 0L;
			Long field10 = 0L;
			Long field11 = 0L;
			Long field12 = 0L;
			Long field13 = 0L;
			Long field14 = 0L;
			Long field15 = 0L;
			Long field16 = 0L;
			Long field17 = 0L;
			Long field18 = 0L;
			Long field19 = 0L;
			Long field20 = 0L;
			Long field21 = 0L;
			Long field22 = 0L;
			Long field23 = 0L;
			Long field24 = 0L;
			Long field25 = 0L;
			Long field26 = 0L;
			Long field27 = 0L;
			Long field28 = 0L;
			Long field29 = 0L;
			Long field30 = 0L;

			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null && listDonVis.size() > 0) {
					for (CoQuanQuanLy dv : listDonVis) {
						if (dv != null) {
							CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
							if (coQuan != null) {
								list.add(coQuan);
							}
						}
					}
					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null && listCapDonVis.size() > 0) {
					List<Long> thamSos = new ArrayList<Long>();
					thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

					for (CapCoQuanQuanLy cdv : listCapDonVis) {
						CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
						if (capDonVi != null) {
							listCapCQs.add(capDonVi.getId());
						}
					}
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));

					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			}

			BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTra(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraKyTruoc(loaiKy, quy, year, month, tuNgay, denNgay);

			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllCuocThanhTraCoQuan = predAllCuocThanhTra
						.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId()));

				// Get cuoc thanh tra theo linh vuc hanh chinh
				predAllCuocThanhTraCoQuan = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(predAllCuocThanhTraCoQuan, LinhVucThanhTraEnum.TAI_CHINH);

				// Dem so cuoc thanh tra
				Long tongSoCuocThanhTra = Long
						.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size());
				field1 += tongSoCuocThanhTra;
				mapMaSo.put("tongSo", tongSoCuocThanhTra);

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
				field8 += tongSoDonViDuocThanhTra;

				// Lay danh sach cuoc thanh tra co vi pham
				BooleanExpression predAllCuocThanhTraCoQuanViPham = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraCoViPham(predAllCuocThanhTraCoQuan, cuocThanhTraRepo);

				// Dem so don vi co vi pham
				Long tongSoDonViCoViPham = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanViPham)).size());
				field9 += tongSoDonViCoViPham;

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

				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				
				mapMaSo.put("donVi", mapDonVi);

				// So cuoc thanh tra
				BooleanExpression predAllCuocThanhTraCoQuanTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraTrongKy
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.TAI_CHINH);
				BooleanExpression predAllCuocThanhTraCoQuanKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraKyTruoc
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.TAI_CHINH);

				Long tongSoCuocThanhTraTrongKy = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanTrongKy)).size());
				Long tongSoCuocThanhTraKyTruoc = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanKyTruoc)).size());

				// Map<String, Object> dangThucHien = new HashMap<>();
				field2 += tongSoCuocThanhTraKyTruoc;
				// dangThucHien.put("kyTruocChuyenSang",
				// tongSoCuocThanhTraKyTruoc);
				mapMaSo.put("kyTruocChuyenSang", tongSoCuocThanhTraKyTruoc);

				field3 += tongSoCuocThanhTraTrongKy;
				// dangThucHien.put("trienKhaiTrongKyBaoCao",
				// tongSoCuocThanhTraTrongKy);
				mapMaSo.put("trienKhaiTrongKyBaoCao", tongSoCuocThanhTraTrongKy);

				// Map<String, Object> hinhThuc = new HashMap<>();
				field4 += tongSoCuocThanhTraTheoKeHoach;
				// hinhThuc.put("theoKeHoach", tongSoCuocThanhTraTheoKeHoach);
				mapMaSo.put("theoKeHoach", tongSoCuocThanhTraTheoKeHoach);

				field5 += tongSoCuocThanhTraTheoKeHoach;
				// hinhThuc.put("dotXuat", tongSoCuocThanhTraDotXuat);
				mapMaSo.put("dotXuat", tongSoCuocThanhTraDotXuat);

				// Map<String, Object> tienDo = new HashMap<>();
				field6 += tongSoCuocThanhTraKetThucTrucTiep;
				// tienDo.put("ketThucThanhTraTrucTiep",
				// tongSoCuocThanhTraKetThucTrucTiep);
				mapMaSo.put("ketThucThanhTraTrucTiep", tongSoCuocThanhTraKetThucTrucTiep);

				field7 += tongSoCuocThanhTraDaBanHanhKetLuan;
				// tienDo.put("daBanHanhKetLuan",
				// tongSoCuocThanhTraDaBanHanhKetLuan);
				mapMaSo.put("daBanHanhKetLuan", tongSoCuocThanhTraDaBanHanhKetLuan);

				// Map<String, Object> soCuocThanhTra = new HashMap<>();
				// soCuocThanhTra.put("tongSo", tongSoCuocThanhTra);
				// soCuocThanhTra.put("dangThucHien", dangThucHien);
				// soCuocThanhTra.put("hinhThuc", hinhThuc);
				// soCuocThanhTra.put("tienDo", tienDo);

				mapMaSo.put("soDonViDuocThanhTra", tongSoDonViDuocThanhTra);
				mapMaSo.put("soDonViCoViPham", tongSoDonViCoViPham);

				// Tong vi pham
				// Map<String, Object> tongViPham = new HashMap<>();
				field10 += tongViPhamTien;
				// tongViPham.put("tien", tongViPhamTien);
				mapMaSo.put("tongViPhamTien", tongViPhamTien);

				field11 += tongViPhamDat;
				// tongViPham.put("dat", tongViPhamDat);
				mapMaSo.put("tongViPhamDat", tongViPhamDat);

				// Kien nghi thu hoi
				// Map<String, Object> kienNghiThuHoi = new HashMap<>();
				field12 += tongKNTHTien;
				// kienNghiThuHoi.put("tien", tongKNTHTien);
				mapMaSo.put("kienNghiThuHoiTien", tongKNTHTien);

				field13 += tongKNTHDat;
				// kienNghiThuHoi.put("dat", tongKNTHDat);
				mapMaSo.put("kienNghiThuHoiDat", tongKNTHDat);

				// Kien nghi khac
				// Map<String, Object> kienNghiKhac = new HashMap<>();
				field14 += tongKNKTien;
				// kienNghiKhac.put("tien", tongKNKTien);
				mapMaSo.put("kienNghiKhacTien", tongKNKTien);

				field15 += tongKNKDat;
				// kienNghiKhac.put("dat", tongKNKDat);
				mapMaSo.put("kienNghiKhacDat", tongKNKDat);

				// Kien nghi xu ly
				// Map<String, Object> hanhChinh = new HashMap<>();
				field16 += tongKNXLHanhChinhToChuc;
				// hanhChinh.put("toChuc", tongKNXLHanhChinhToChuc);
				mapMaSo.put("kienNghiXLHanhChinhToChuc", tongKNXLHanhChinhToChuc);

				field17 += tongKNXLHanhChinhCaNhan;
				// hanhChinh.put("caNhan", tongKNXLHanhChinhCaNhan);
				mapMaSo.put("kienNghiXLHanhChinhCaNhan", tongKNXLHanhChinhCaNhan);

				// Map<String, Object> chuyenCoQuanDieuTra = new HashMap<>();
				field18 += tongKNXLVu;
				// chuyenCoQuanDieuTra.put("vu", tongKNXLVu);
				mapMaSo.put("kienNghiXLVu", tongKNXLVu);

				field19 += tongKNXLDoiTuong;
				// chuyenCoQuanDieuTra.put("doiTuong", tongKNXLDoiTuong);
				mapMaSo.put("kienNghiXLDoiTuong", tongKNXLDoiTuong);

				// Map<String, Object> kienNghiXuly = new HashMap<>();
				// kienNghiXuly.put("hanhChinh", hanhChinh);
				// kienNghiXuly.put("chuyenCoQuanDieuTra", chuyenCoQuanDieuTra);

				// Da thu
				// Map<String, Object> daThu = new HashMap<>();
				Long daThuTien = thongKeTongHopThanhTraService
						.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "TIEN");
				field20 += daThuTien;
				// daThu.put("tien", daThuTien);
				mapMaSo.put("daThuTien", daThuTien);

				Long daThuDat = thongKeTongHopThanhTraService
						.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "DAT");
				field21 += daThuDat;
				// daThu.put("dat", daThuDat);
				mapMaSo.put("daThuDat", daThuDat);

				/* Note: Chua xong Theo doi thuc hien */
				// Kiem tra don doc
				// Map<String, Object> ketQuaKiemTraDonDoc = new HashMap<>();

				// Map<String, Object> tongHopketQuaKiemTraDonDoc = new
				// HashMap<>();
				// tongHopketQuaKiemTraDonDoc.put("phaiThu", 0);
				// tongHopketQuaKiemTraDonDoc.put("daThu", 0);
				// ketQuaKiemTraDonDoc.put("tien", tongHopketQuaKiemTraDonDoc);
				field22 += 0;
				mapMaSo.put("tongSoKLTTVaQDXLDaKTDD", 0);

				field23 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocTienPhaiThu", 0);

				field24 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocTienDaThu", 0);

				// tongHopketQuaKiemTraDonDoc = new HashMap<>();
				// tongHopketQuaKiemTraDonDoc.put("phaiThu", 0);
				// tongHopketQuaKiemTraDonDoc.put("daThu", 0);
				// ketQuaKiemTraDonDoc.put("dat", tongHopketQuaKiemTraDonDoc);
				field25 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDatPhaiThu", 0);

				field26 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDatDaThu", 0);

				// tongHopketQuaKiemTraDonDoc = new HashMap<>();
				// tongHopketQuaKiemTraDonDoc.put("toChuc", 0);
				// tongHopketQuaKiemTraDonDoc.put("caNhan", 0);
				// ketQuaKiemTraDonDoc.put("daXuLyHanhChinh",
				// tongHopketQuaKiemTraDonDoc);
				field27 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDaXuLyHCToChuc", 0);

				field28 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDaXuLyHCCaNhan", 0);

				// tongHopketQuaKiemTraDonDoc = new HashMap<>();
				// tongHopketQuaKiemTraDonDoc.put("vu", 0);
				// tongHopketQuaKiemTraDonDoc.put("doiTuong", 0);
				// ketQuaKiemTraDonDoc.put("daKhoiTo",
				// tongHopketQuaKiemTraDonDoc);
				field29 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDaKhoiToVu", 0);

				field30 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDaKhoiToDoiTuong", 0);

				// Map<String, Object> kiemTraDonDoc = new HashMap<>();
				// kiemTraDonDoc.put("tongSo", 0);
				// kiemTraDonDoc.put("ketQuaKiemTraDonDoc",
				// ketQuaKiemTraDonDoc);

				// mapMaSo.put("dangThucHien", mapDonVi);

				// mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("donVi", mapDonVi);
				// mapMaSo.put("soCuocThanhTra", soCuocThanhTra);
				// mapMaSo.put("soDonViDuocThanhTra", tongSoDonViDuocThanhTra);
				// mapMaSo.put("soDonViCoViPham", tongSoDonViCoViPham);
				// mapMaSo.put("tongViPham", tongViPham);
				// mapMaSo.put("kienNghiThuHoi", kienNghiThuHoi);
				// mapMaSo.put("kienNghiKhac", kienNghiKhac);
				// mapMaSo.put("kienNghiXuLy", kienNghiXuly);
				// mapMaSo.put("daThu", daThu);
				// mapMaSo.put("kiemTraDonDoc", kiemTraDonDoc);
				mapMaSo.put("ghiChu", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
				mapDonVi = new HashMap<String, Object>();
			}

			// Map tong so
			Map<String, Object> mapTongSo = new HashMap<String, Object>();
			mapTongSo.put("tongSo", field1);
			mapTongSo.put("kyTruocChuyenSang", field2);
			mapTongSo.put("trienKhaiTrongKyBaoCao", field3);
			mapTongSo.put("theoKeHoach", field4);
			mapTongSo.put("dotXuat", field5);
			mapTongSo.put("ketThucThanhTraTrucTiep", field6);
			mapTongSo.put("daBanHanhKetLuan", field7);
			mapTongSo.put("soDonViDuocThanhTra", field8);
			mapTongSo.put("soDonViCoViPham", field9);
			mapTongSo.put("tongViPhamTien", field10);
			mapTongSo.put("tongViPhamDat", field11);
			mapTongSo.put("kienNghiThuHoiTien", field12);
			mapTongSo.put("kienNghiThuHoiDat", field13);
			mapTongSo.put("kienNghiKhacTien", field14);
			mapTongSo.put("kienNghiKhacDat", field15);
			mapTongSo.put("kienNghiXLHanhChinhToChuc", field16);
			mapTongSo.put("kienNghiXLHanhChinhCaNhan", field17);
			mapTongSo.put("kienNghiXLVu", field18);
			mapTongSo.put("kienNghiXLDoiTuong", field19);
			mapTongSo.put("daThuTien", field20);
			mapTongSo.put("daThuDat", field21);
			mapTongSo.put("tongSoKLTTVaQDXLDaKTDD", field22);
			mapTongSo.put("ketQuaKiemTraDonDocTienPhaiThu", field23);
			mapTongSo.put("ketQuaKiemTraDonDocTienDaThu", field24);
			mapTongSo.put("ketQuaKiemTraDonDocDatPhaiThu", field25);
			mapTongSo.put("ketQuaKiemTraDonDocDatDaThu", field26);
			mapTongSo.put("ketQuaKiemTraDonDocDaXuLyHCToChuc", field27);
			mapTongSo.put("ketQuaKiemTraDonDocDaXuLyHCCaNhan", field28);
			mapTongSo.put("ketQuaKiemTraDonDocDaKhoiToVu", field29);
			mapTongSo.put("ketQuaKiemTraDonDocDaKhoiToDoiTuong", field30);
			mapTongSo.put("ghiChu", "");

			map.put("tongCongs", mapTongSo);
			map.put("maSos", maSos);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoHanhChinh/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả thanh tra theo hành chính", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelKetQuaThanhTraTheoHanhChinh(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis) throws IOException {

		try {
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));

			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			if (year == null) {
				year = Utils.localDateTimeNow().getYear();
			}

			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null) {
					for (CoQuanQuanLy dv : listDonVis) {
						CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
						if (coQuan != null) {
							list.add(coQuan);
						}
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null) {
					if (listCapDonVis.size() > 0) {
						List<Long> thamSos = new ArrayList<Long>();
						thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

						for (CapCoQuanQuanLy cdv : listCapDonVis) {
							CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
							if (capDonVi != null) {
								listCapCQs.add(capDonVi.getId());
							}
						}
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			}

			BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTra(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraKyTruoc(loaiKy, quy, year, month, tuNgay, denNgay);

			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllCuocThanhTraCoQuan = predAllCuocThanhTra
						.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId()));

				// Get cuoc thanh tra theo linh vuc hanh chinh
				predAllCuocThanhTraCoQuan = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(predAllCuocThanhTraCoQuan, LinhVucThanhTraEnum.TAI_CHINH);
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("0", cq.getTen());
				mapMaSo.put("1", Long
						.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size()));
				BooleanExpression predAllCuocThanhTraCoQuanTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraTrongKy
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.TAI_CHINH);
				BooleanExpression predAllCuocThanhTraCoQuanKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraKyTruoc
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.TAI_CHINH);

				Long tongSoCuocThanhTraTrongKy = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanTrongKy)).size());
				Long tongSoCuocThanhTraKyTruoc = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanKyTruoc)).size());
				mapMaSo.put("2", tongSoCuocThanhTraKyTruoc);
				mapMaSo.put("3", tongSoCuocThanhTraTrongKy);
				mapMaSo.put("4", thongKeTongHopThanhTraService.getCuocThanhTraTheoHinhThuc(predAllCuocThanhTraCoQuan,
						HinhThucThanhTraEnum.THEO_KE_HOACH, cuocThanhTraRepo));
				mapMaSo.put("5", thongKeTongHopThanhTraService.getCuocThanhTraTheoHinhThuc(predAllCuocThanhTraCoQuan,
						HinhThucThanhTraEnum.DOT_XUAT, cuocThanhTraRepo));
				mapMaSo.put("6", thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(predAllCuocThanhTraCoQuan,
						TienDoThanhTraEnum.KET_THUC_THANH_TRA_TRUC_TIEP, cuocThanhTraRepo));
				mapMaSo.put("7", thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(predAllCuocThanhTraCoQuan,
						TienDoThanhTraEnum.DA_BAN_HANH_KET_LUAN, cuocThanhTraRepo));
				mapMaSo.put("8", thongKeTongHopThanhTraService.getSoDonViDuocThanhTra(predAllCuocThanhTraCoQuan,
						cuocThanhTraRepo));
				BooleanExpression predAllCuocThanhTraCoQuanViPham = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraCoViPham(predAllCuocThanhTraCoQuan, cuocThanhTraRepo);
				mapMaSo.put("9", Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanViPham)).size()));
				mapMaSo.put("10", thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
						"TONG_VI_PHAM", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("11", thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
						"TONG_VI_PHAM", "DAT", cuocThanhTraRepo));
				mapMaSo.put("12", thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
						"KIEN_NGHI_THU_HOI", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("13", thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
						"KIEN_NGHI_THU_HOI", "DAT", cuocThanhTraRepo));
				mapMaSo.put("14", thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
						"KIEN_NGHI_KHAC", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("15", thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
						"KIEN_NGHI_KHAC", "DAT", cuocThanhTraRepo));
				mapMaSo.put("16", thongKeTongHopThanhTraService
						.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuanViPham, "TO_CHUC", cuocThanhTraRepo));
				mapMaSo.put("17", thongKeTongHopThanhTraService
						.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuanViPham, "CA_NHAN", cuocThanhTraRepo));
				BooleanExpression predAllCuocThanhTraChuyenCoQuanDieuTra = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraChuyenCoQuanDieuTra(predAllCuocThanhTraCoQuan, cuocThanhTraRepo);
				mapMaSo.put("18", thongKeTongHopThanhTraService
						.getKienNghiXuLyCCQDT(predAllCuocThanhTraChuyenCoQuanDieuTra, "VU", cuocThanhTraRepo));
				mapMaSo.put("19", thongKeTongHopThanhTraService
						.getKienNghiXuLyCCQDT(predAllCuocThanhTraChuyenCoQuanDieuTra, "DOI_TUONG", cuocThanhTraRepo));
				mapMaSo.put("20", thongKeTongHopThanhTraService
						.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "TIEN"));
				mapMaSo.put("21", thongKeTongHopThanhTraService
						.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "DAT"));
				mapMaSo.put("22", 0);
				mapMaSo.put("23", 0);
				mapMaSo.put("24", 0);
				mapMaSo.put("25", 0);
				mapMaSo.put("26", 0);
				mapMaSo.put("27", 0);
				mapMaSo.put("28", 0);
				mapMaSo.put("29", 0);
				mapMaSo.put("30", 0);
				mapMaSo.put("31", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}

			map.put("maSos", maSos);
			ExcelUtil.exportTongHopKetQuaThanhTraTheoHanhChinh(response, "DanhSachTongHopKetQuaThanhTraTheoHanhChinh",
					"sheetName", maSos, tuNgay, denNgay,
					"Thống kê đối với tất cả các cuộc thanh tra chọn LĨNH VỰC THANH TRA là HÀNH CHÍNH");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoDauTuXayDungCoBan")
	@ApiOperation(value = "Tổng hợp kết quả thanh tra theo đầu tư xây dựng cơ bản", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaThanhTraTheoDTXDCB(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			PersistentEntityResourceAssembler eass) {
		try {

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Long donViXuLy = Long
					.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());

			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			if (year == null) {
				year = Utils.localDateTimeNow().getYear();
			}

			Long field1 = 0L;
			Long field2 = 0L;
			Long field3 = 0L;
			Long field4 = 0L;
			Long field5 = 0L;
			Long field6 = 0L;
			Long field7 = 0L;
			Long field8 = 0L;
			Long field9 = 0L;
			Long field10 = 0L;
			Long field11 = 0L;
			Long field12 = 0L;
			Long field13 = 0L;
			Long field14 = 0L;
			Long field15 = 0L;
			Long field16 = 0L;
			Long field17 = 0L;
			Long field18 = 0L;
			Long field19 = 0L;
			Long field20 = 0L;
			Long field21 = 0L;
			Long field22 = 0L;
			Long field23 = 0L;
			Long field24 = 0L;
			Long field25 = 0L;
			Long field26 = 0L;
			Long field27 = 0L;
			Long field28 = 0L;
			Long field29 = 0L;
			Long field30 = 0L;

			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null && listDonVis.size() > 0) {
					for (CoQuanQuanLy dv : listDonVis) {
						if (dv != null) {
							CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
							if (coQuan != null) {
								list.add(coQuan);
							}
						}
					}
					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null && listCapDonVis.size() > 0) {
					List<Long> thamSos = new ArrayList<Long>();
					thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

					for (CapCoQuanQuanLy cdv : listCapDonVis) {
						CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
						if (capDonVi != null) {
							listCapCQs.add(capDonVi.getId());
						}
					}
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));

					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			}

			BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTra(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraKyTruoc(loaiKy, quy, year, month, tuNgay, denNgay);

			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllCuocThanhTraCoQuan = predAllCuocThanhTra
						.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId()));

				// Get cuoc thanh tra theo linh vuc hanh chinh
				predAllCuocThanhTraCoQuan = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(predAllCuocThanhTraCoQuan,
								LinhVucThanhTraEnum.XAY_DUNG_CO_BAN);

				// Dem so cuoc thanh tra
				Long tongSoCuocThanhTra = Long
						.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size());
				field1 += tongSoCuocThanhTra;
				mapMaSo.put("tongSo", tongSoCuocThanhTra);

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
				field8 += tongSoDonViDuocThanhTra;

				// Lay danh sach cuoc thanh tra co vi pham
				BooleanExpression predAllCuocThanhTraCoQuanViPham = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraCoViPham(predAllCuocThanhTraCoQuan, cuocThanhTraRepo);

				// Dem so don vi co vi pham
				Long tongSoDonViCoViPham = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanViPham)).size());
				field9 += tongSoDonViCoViPham;

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

				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());

				mapMaSo.put("donVi", mapDonVi);

				// So cuoc thanh tra
				BooleanExpression predAllCuocThanhTraCoQuanTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraTrongKy
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.XAY_DUNG_CO_BAN);
				BooleanExpression predAllCuocThanhTraCoQuanKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraKyTruoc
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.XAY_DUNG_CO_BAN);

				Long tongSoCuocThanhTraTrongKy = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanTrongKy)).size());
				Long tongSoCuocThanhTraKyTruoc = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanKyTruoc)).size());

				field2 += tongSoCuocThanhTraKyTruoc;
				mapMaSo.put("kyTruocChuyenSang", tongSoCuocThanhTraKyTruoc);

				field3 += tongSoCuocThanhTraTrongKy;
				mapMaSo.put("trienKhaiTrongKyBaoCao", tongSoCuocThanhTraTrongKy);

				field4 += tongSoCuocThanhTraTheoKeHoach;
				mapMaSo.put("theoKeHoach", tongSoCuocThanhTraTheoKeHoach);

				field5 += tongSoCuocThanhTraTheoKeHoach;
				mapMaSo.put("dotXuat", tongSoCuocThanhTraDotXuat);

				field6 += tongSoCuocThanhTraKetThucTrucTiep;
				mapMaSo.put("ketThucThanhTraTrucTiep", tongSoCuocThanhTraKetThucTrucTiep);

				field7 += tongSoCuocThanhTraDaBanHanhKetLuan;
				mapMaSo.put("daBanHanhKetLuan", tongSoCuocThanhTraDaBanHanhKetLuan);

				// 8
				mapMaSo.put("soDonViDuocThanhTra", tongSoDonViDuocThanhTra);

				// 9
				mapMaSo.put("soDonViCoViPham", tongSoDonViCoViPham);

				// Tong vi pham
				field10 += tongViPhamTien;
				mapMaSo.put("tongViPhamTien", tongViPhamTien);

				field11 += tongViPhamDat;
				mapMaSo.put("tongViPhamDat", tongViPhamDat);

				// Kien nghi thu hoi
				field12 += tongKNTHTien;
				mapMaSo.put("kienNghiThuHoiTien", tongKNTHTien);

				field13 += tongKNTHDat;
				mapMaSo.put("kienNghiThuHoiDat", tongKNTHDat);

				// Kien nghi khac
				field14 += tongKNKTien;
				mapMaSo.put("kienNghiKhacTien", tongKNKTien);

				field15 += tongKNKDat;
				mapMaSo.put("kienNghiKhacDat", tongKNKDat);

				// Kien nghi xu ly
				field16 += tongKNXLHanhChinhToChuc;
				mapMaSo.put("kienNghiXLHanhChinhToChuc", tongKNXLHanhChinhToChuc);

				field17 += tongKNXLHanhChinhCaNhan;
				mapMaSo.put("kienNghiXLHanhChinhCaNhan", tongKNXLHanhChinhCaNhan);

				field18 += tongKNXLVu;
				mapMaSo.put("kienNghiXLVu", tongKNXLVu);

				field19 += tongKNXLDoiTuong;
				mapMaSo.put("kienNghiXLDoiTuong", tongKNXLDoiTuong);

				// Da thu
				// Map<String, Object> daThu = new HashMap<>();
				Long daThuTien = thongKeTongHopThanhTraService
						.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "TIEN");
				field20 += daThuTien;
				mapMaSo.put("daThuTien", daThuTien);

				// daThu.put("tien", daThuTien);
				Long daThuDat = thongKeTongHopThanhTraService
						.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "DAT");
				field21 += daThuDat;
				// daThu.put("dat", daThuDat);
				mapMaSo.put("daThuDat", daThuDat);

				/* Note: Chua xong Theo doi thuc hien */
				// Kiem tra don doc
				field22 += 0;
				mapMaSo.put("tongSoKLTTVaQDXLDaKTDD", 0);

				field23 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocTienPhaiThu", 0);

				field24 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocTienDaThu", 0);

				field25 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDatPhaiThu", 0);

				field26 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDatDaThu", 0);

				field27 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDaXuLyHCToChuc", 0);

				field28 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDaXuLyHCCaNhan", 0);

				field29 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDaKhoiToVu", 0);

				field30 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDaKhoiToDoiTuong", 0);

				mapMaSo.put("donVi", mapDonVi);
				mapMaSo.put("ghiChu", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
				mapDonVi = new HashMap<String, Object>();
			}

			// Map tong so
			Map<String, Object> mapTongSo = new HashMap<String, Object>();
			mapTongSo.put("tongSo", field1);
			mapTongSo.put("kyTruocChuyenSang", field2);
			mapTongSo.put("trienKhaiTrongKyBaoCao", field3);
			mapTongSo.put("theoKeHoach", field4);
			mapTongSo.put("dotXuat", field5);
			mapTongSo.put("ketThucThanhTraTrucTiep", field6);
			mapTongSo.put("daBanHanhKetLuan", field7);
			mapTongSo.put("soDonViDuocThanhTra", field8);
			mapTongSo.put("soDonViCoViPham", field9);
			mapTongSo.put("tongViPhamTien", field10);
			mapTongSo.put("tongViPhamDat", field11);
			mapTongSo.put("kienNghiThuHoiTien", field12);
			mapTongSo.put("kienNghiThuHoiDat", field13);
			mapTongSo.put("kienNghiKhacTien", field14);
			mapTongSo.put("kienNghiKhacDat", field15);
			mapTongSo.put("kienNghiXLHanhChinhToChuc", field16);
			mapTongSo.put("kienNghiXLHanhChinhCaNhan", field17);
			mapTongSo.put("kienNghiXLVu", field18);
			mapTongSo.put("kienNghiXLDoiTuong", field19);
			mapTongSo.put("daThuTien", field20);
			mapTongSo.put("daThuDat", field21);
			mapTongSo.put("tongSoKLTTVaQDXLDaKTDD", field22);
			mapTongSo.put("ketQuaKiemTraDonDocTienPhaiThu", field23);
			mapTongSo.put("ketQuaKiemTraDonDocTienDaThu", field24);
			mapTongSo.put("ketQuaKiemTraDonDocDatPhaiThu", field25);
			mapTongSo.put("ketQuaKiemTraDonDocDatDaThu", field26);
			mapTongSo.put("ketQuaKiemTraDonDocDaXuLyHCToChuc", field27);
			mapTongSo.put("ketQuaKiemTraDonDocDaXuLyHCCaNhan", field28);
			mapTongSo.put("ketQuaKiemTraDonDocDaKhoiToVu", field29);
			mapTongSo.put("ketQuaKiemTraDonDocDaKhoiToDoiTuong", field30);
			mapTongSo.put("ghiChu", "");

			map.put("tongCongs", mapTongSo);
			map.put("maSos", maSos);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoDauTuXayDungCoBan/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả thanh tra theo lĩnh vực đầu tư xây dựng cơ bản", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelKetQuaThanhTraTheoDauTuXayDungCoBan(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis) throws IOException {

		try {
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			if (year == null) {
				year = Utils.localDateTimeNow().getYear();
			}

			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null) {
					for (CoQuanQuanLy dv : listDonVis) {
						CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
						if (coQuan != null) {
							list.add(coQuan);
						}
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null) {
					if (listCapDonVis.size() > 0) {
						List<Long> thamSos = new ArrayList<Long>();
						thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

						for (CapCoQuanQuanLy cdv : listCapDonVis) {
							CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
							if (capDonVi != null) {
								listCapCQs.add(capDonVi.getId());
							}
						}
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			}

			BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTra(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraKyTruoc(loaiKy, quy, year, month, tuNgay, denNgay);

			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllCuocThanhTraCoQuan = predAllCuocThanhTra
						.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId()));

				// Get cuoc thanh tra theo linh vuc hanh chinh
				predAllCuocThanhTraCoQuan = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(predAllCuocThanhTraCoQuan,
								LinhVucThanhTraEnum.XAY_DUNG_CO_BAN);
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("0", cq.getTen());
				mapMaSo.put("1", Long
						.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size()));
				BooleanExpression predAllCuocThanhTraCoQuanTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraTrongKy
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.XAY_DUNG_CO_BAN);
				BooleanExpression predAllCuocThanhTraCoQuanKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraKyTruoc
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.XAY_DUNG_CO_BAN);

				Long tongSoCuocThanhTraTrongKy = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanTrongKy)).size());
				Long tongSoCuocThanhTraKyTruoc = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanKyTruoc)).size());
				mapMaSo.put("2", tongSoCuocThanhTraKyTruoc);
				mapMaSo.put("3", tongSoCuocThanhTraTrongKy);
				mapMaSo.put("4", thongKeTongHopThanhTraService.getCuocThanhTraTheoHinhThuc(predAllCuocThanhTraCoQuan,
						HinhThucThanhTraEnum.THEO_KE_HOACH, cuocThanhTraRepo));
				mapMaSo.put("5", thongKeTongHopThanhTraService.getCuocThanhTraTheoHinhThuc(predAllCuocThanhTraCoQuan,
						HinhThucThanhTraEnum.DOT_XUAT, cuocThanhTraRepo));
				mapMaSo.put("6", thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(predAllCuocThanhTraCoQuan,
						TienDoThanhTraEnum.KET_THUC_THANH_TRA_TRUC_TIEP, cuocThanhTraRepo));
				mapMaSo.put("7", thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(predAllCuocThanhTraCoQuan,
						TienDoThanhTraEnum.DA_BAN_HANH_KET_LUAN, cuocThanhTraRepo));
				mapMaSo.put("8", thongKeTongHopThanhTraService.getSoDonViDuocThanhTra(predAllCuocThanhTraCoQuan,
						cuocThanhTraRepo));
				BooleanExpression predAllCuocThanhTraCoQuanViPham = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraCoViPham(predAllCuocThanhTraCoQuan, cuocThanhTraRepo);
				mapMaSo.put("9", Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanViPham)).size()));
				mapMaSo.put("10", thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
						"TONG_VI_PHAM", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("11", thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
						"TONG_VI_PHAM", "DAT", cuocThanhTraRepo));
				mapMaSo.put("12", thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
						"KIEN_NGHI_THU_HOI", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("13", thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
						"KIEN_NGHI_THU_HOI", "DAT", cuocThanhTraRepo));
				mapMaSo.put("14", thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
						"KIEN_NGHI_KHAC", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("15", thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
						"KIEN_NGHI_KHAC", "DAT", cuocThanhTraRepo));
				mapMaSo.put("16", thongKeTongHopThanhTraService
						.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuanViPham, "TO_CHUC", cuocThanhTraRepo));
				mapMaSo.put("17", thongKeTongHopThanhTraService
						.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuanViPham, "CA_NHAN", cuocThanhTraRepo));
				BooleanExpression predAllCuocThanhTraChuyenCoQuanDieuTra = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraChuyenCoQuanDieuTra(predAllCuocThanhTraCoQuan, cuocThanhTraRepo);
				mapMaSo.put("18", thongKeTongHopThanhTraService
						.getKienNghiXuLyCCQDT(predAllCuocThanhTraChuyenCoQuanDieuTra, "VU", cuocThanhTraRepo));
				mapMaSo.put("19", thongKeTongHopThanhTraService
						.getKienNghiXuLyCCQDT(predAllCuocThanhTraChuyenCoQuanDieuTra, "DOI_TUONG", cuocThanhTraRepo));
				mapMaSo.put("20", thongKeTongHopThanhTraService
						.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "TIEN"));
				mapMaSo.put("21", thongKeTongHopThanhTraService
						.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "DAT"));
				mapMaSo.put("22", 0);
				mapMaSo.put("23", 0);
				mapMaSo.put("24", 0);
				mapMaSo.put("25", 0);
				mapMaSo.put("26", 0);
				mapMaSo.put("27", 0);
				mapMaSo.put("28", 0);
				mapMaSo.put("29", 0);
				mapMaSo.put("30", 0);
				mapMaSo.put("31", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}

			map.put("maSos", maSos);
			ExcelUtil.exportTongHopKetQuaThanhTraTheoDauTuXayDungCoBan(response,
					"DanhSachTongHopKetQuaThanhTraTheoDauTuXayDungCoBan ", "sheetName", maSos, tuNgay, denNgay,
					"TỔNG HỢP KẾT QUẢ THANH TRA TRONG LĨNH VỰC ĐẦU TƯ XÂY DỰNG CƠ BẢN");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoTaiChinhNganSach")
	@ApiOperation(value = "Tổng hợp kết quả thanh tra theo tài chính ngân sách", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaThanhTraTheoTCNS(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			PersistentEntityResourceAssembler eass) {
		try {

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Long donViXuLy = Long
					.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());

			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			if (year == null) {
				year = Utils.localDateTimeNow().getYear();
			}

			Long field1 = 0L;
			Long field2 = 0L;
			Long field3 = 0L;
			Long field4 = 0L;
			Long field5 = 0L;
			Long field6 = 0L;
			Long field7 = 0L;
			Long field8 = 0L;
			Long field9 = 0L;
			Long field10 = 0L;
			Long field11 = 0L;
			Long field12 = 0L;
			Long field13 = 0L;
			Long field14 = 0L;
			Long field15 = 0L;
			Long field16 = 0L;
			Long field17 = 0L;
			Long field18 = 0L;
			Long field19 = 0L;
			Long field20 = 0L;
			Long field21 = 0L;
			Long field22 = 0L;
			Long field23 = 0L;
			Long field24 = 0L;

			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null && listDonVis.size() > 0) {
					for (CoQuanQuanLy dv : listDonVis) {
						if (dv != null) {
							CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
							if (coQuan != null) {
								list.add(coQuan);
							}
						}
					}
					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null && listCapDonVis.size() > 0) {
					List<Long> thamSos = new ArrayList<Long>();
					thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

					for (CapCoQuanQuanLy cdv : listCapDonVis) {
						CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
						if (capDonVi != null) {
							listCapCQs.add(capDonVi.getId());
						}
					}
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));

					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			}

			BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTra(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraKyTruoc(loaiKy, quy, year, month, tuNgay, denNgay);

			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllCuocThanhTraCoQuan = predAllCuocThanhTra
						.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId()));

				// Get cuoc thanh tra theo linh vuc hanh chinh
				predAllCuocThanhTraCoQuan = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(predAllCuocThanhTraCoQuan, LinhVucThanhTraEnum.TAI_CHINH);

				// Dem so cuoc thanh tra
				Long tongSoCuocThanhTra = Long
						.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size());
				field1 += tongSoCuocThanhTra;
				mapMaSo.put("tongSo", tongSoCuocThanhTra);

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
				field8 += tongSoDonViDuocThanhTra;

				// Lay danh sach cuoc thanh tra co vi pham
				BooleanExpression predAllCuocThanhTraCoQuanViPham = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraCoViPham(predAllCuocThanhTraCoQuan, cuocThanhTraRepo);

				// Dem so don vi co vi pham
				Long tongSoDonViCoViPham = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanViPham)).size());
				field9 += tongSoDonViCoViPham;
				
				// Block noi dung vi pham
				Long tongViPhamTien = thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "TONG_VI_PHAM", "TIEN", cuocThanhTraRepo);
				field10 += tongViPhamTien;

				Long tongKNTHTien = thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
						"KIEN_NGHI_THU_HOI", "TIEN", cuocThanhTraRepo);
				field11 += tongKNTHTien;
				mapMaSo.put("soTienKienNghiThuHoi", tongKNTHTien);

				Long tongKNKTien = thongKeTongHopThanhTraService.getTienDatTheoViPham(predAllCuocThanhTraCoQuanViPham,
						"KIEN_NGHI_KHAC", "TIEN", cuocThanhTraRepo);
				field12 += tongKNKTien;
				mapMaSo.put("kienNghiKhac", tongKNKTien);
				
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
				field17 += daThuTien;

				mapDonVi = new HashMap<String, Object>();
				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());

				mapMaSo.put("donVi", mapDonVi);

				// So cuoc thanh tra
				BooleanExpression predAllCuocThanhTraCoQuanTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraTrongKy
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.TAI_CHINH);
				BooleanExpression predAllCuocThanhTraCoQuanKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraKyTruoc
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.TAI_CHINH);

				Long tongSoCuocThanhTraTrongKy = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanTrongKy)).size());
				Long tongSoCuocThanhTraKyTruoc = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanKyTruoc)).size());

				field2 += tongSoCuocThanhTraKyTruoc;
				mapMaSo.put("kyTruocChuyenSang", tongSoCuocThanhTraKyTruoc);

				field3 += tongSoCuocThanhTraTrongKy;
				mapMaSo.put("trienKhaiTrongKyBaoCao", tongSoCuocThanhTraTrongKy);

				field4 += tongSoCuocThanhTraTheoKeHoach;
				mapMaSo.put("theoKeHoach", tongSoCuocThanhTraTheoKeHoach);

				field5 += tongSoCuocThanhTraDotXuat;
				mapMaSo.put("dotXuat", tongSoCuocThanhTraDotXuat);

				field6 += tongSoCuocThanhTraKetThucTrucTiep;
				mapMaSo.put("ketThucThanhTraTrucTiep", tongSoCuocThanhTraKetThucTrucTiep);

				field7 += tongSoCuocThanhTraDaBanHanhKetLuan;
				mapMaSo.put("daBanHanhKetLuan", tongSoCuocThanhTraDaBanHanhKetLuan);

				// 8
				mapMaSo.put("soDonViDuocThanhTra", tongSoDonViDuocThanhTra);
				
				// 9
				mapMaSo.put("soDonViCoViPham", tongSoDonViCoViPham);
				
				// 10
				mapMaSo.put("soTienViPham", tongViPhamTien);

				// Kien nghi xu ly
				field13 += tongKNXLHanhChinhToChuc;
				mapMaSo.put("kienNghiXLHanhChinhToChuc", tongKNXLHanhChinhToChuc);

				field14 += tongKNXLHanhChinhCaNhan;
				mapMaSo.put("kienNghiXLHanhChinhCaNhan", tongKNXLHanhChinhCaNhan);

				field15 += tongKNXLVu;
				mapMaSo.put("kienNghiXuLyVu", tongKNXLVu);

				field16 += tongKNXLDoiTuong;
				mapMaSo.put("kienNghiXuLyDoiTuong", tongKNXLDoiTuong);
				
				// 17
				mapMaSo.put("soTienDaThu", daThuTien);

				// 18
				field18 += 0;
				mapMaSo.put("tongSoKLTTVaQDXLDaKTDD", 0);

				/* Note: Chua xong Theo doi thuc hien */
				// Kiem tra don doc
				field19 += 0;
				mapMaSo.put("kiemTraDonDocTienPhaiThu", 0);

				field20 += 0;
				mapMaSo.put("kiemTraDonDocTienDaThu", 0);

				field21 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDaXuLyHCToChuc", 0);

				field22 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDaXuLyHCCaNhan", 0);
				
				field23 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDaKhoiToVu", 0);

				field24 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDaKhoiToDoiTuong", 0);

				mapMaSo.put("ghiChu", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
				mapDonVi = new HashMap<String, Object>();
			}
			
			// Map tong so
			Map<String, Object> mapTongSo = new HashMap<>();
			mapTongSo.put("tongSo", field1);
			mapTongSo.put("kyTruocChuyenSang", field2);
			mapTongSo.put("trienKhaiTrongKyBaoCao", field3);
			mapTongSo.put("theoKeHoach", field4);
			mapTongSo.put("dotXuat", field5);
			mapTongSo.put("ketThucThanhTraTrucTiep", field6);
			mapTongSo.put("daBanHanhKetLuan", field7);
			mapTongSo.put("soDonViDuocThanhTra", field8);
			mapTongSo.put("soDonViCoViPham", field9);
			mapTongSo.put("soTienViPham", field10);
			mapTongSo.put("soTienKienNghiThuHoi", field11);
			mapTongSo.put("kienNghiKhac", field12);
			mapTongSo.put("kienNghiXLHanhChinhToChuc", field13);
			mapTongSo.put("kienNghiXLHanhChinhCaNhan", field14);
			mapTongSo.put("kienNghiXuLyVu", field15);
			mapTongSo.put("kienNghiXuLyDoiTuong", field16);
			mapTongSo.put("soTienDaThu", field17);
			mapTongSo.put("tongSoKLTTVaQDXLDaKTDD", field18);
			mapTongSo.put("kiemTraDonDocTienPhaiThu", field19);
			mapTongSo.put("kiemTraDonDocTienDaThu", field20);
			mapTongSo.put("ketQuaKiemTraDonDocDaXuLyHCToChuc", field21);
			mapTongSo.put("ketQuaKiemTraDonDocDaXuLyHCCaNhan", field22);
			mapTongSo.put("ketQuaKiemTraDonDocDaKhoiToVu", field23);
			mapTongSo.put("ketQuaKiemTraDonDocDaKhoiToDoiTuong", field24);
			
			map.put("tongCongs", mapTongSo);
			map.put("maSos", maSos);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoTaiChinhNganSach/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả thanh tra theo lĩnh vực đầu tư xây dựng cơ bản", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelKetQuaThanhTraTheoTaiChinhNganSach(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis)
			throws IOException {

		try {
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			if (year == null) {
				year = Utils.localDateTimeNow().getYear();
			}
			
			BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTra(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraKyTruoc(loaiKy, quy, year, month, tuNgay, denNgay);
			
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null) {
					for (CoQuanQuanLy dv : listDonVis) {
						CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
						if (coQuan != null) {
							list.add(coQuan);
						}
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null) {
					if (listCapDonVis.size() > 0) {
						List<Long> thamSos = new ArrayList<Long>();
						thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

						for (CapCoQuanQuanLy cdv : listCapDonVis) {
							CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
							if (capDonVi != null) {
								listCapCQs.add(capDonVi.getId());
							}
						}
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			}
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllCuocThanhTraCoQuan = predAllCuocThanhTra;

				// Get cuoc thanh tra theo linh vuc hanh chinh
				predAllCuocThanhTraCoQuan = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraCoQuan
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.TAI_CHINH);
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("0", cq.getTen());
				mapMaSo.put("1", Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size()));
				BooleanExpression predAllCuocThanhTraCoQuanTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraTrongKy
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.TAI_CHINH);
				BooleanExpression predAllCuocThanhTraCoQuanKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraKyTruoc
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.TAI_CHINH);

				Long tongSoCuocThanhTraTrongKy = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanTrongKy)).size());
				Long tongSoCuocThanhTraKyTruoc = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanKyTruoc)).size());
				mapMaSo.put("2", tongSoCuocThanhTraKyTruoc);
				mapMaSo.put("3", tongSoCuocThanhTraTrongKy);
				mapMaSo.put("4", thongKeTongHopThanhTraService.getCuocThanhTraTheoHinhThuc(
						predAllCuocThanhTraCoQuan, HinhThucThanhTraEnum.THEO_KE_HOACH, cuocThanhTraRepo));
				mapMaSo.put("5", thongKeTongHopThanhTraService.getCuocThanhTraTheoHinhThuc(
						predAllCuocThanhTraCoQuan, HinhThucThanhTraEnum.DOT_XUAT, cuocThanhTraRepo));
				mapMaSo.put("6", thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(predAllCuocThanhTraCoQuan,
						TienDoThanhTraEnum.KET_THUC_THANH_TRA_TRUC_TIEP, cuocThanhTraRepo));
				mapMaSo.put("7", thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(predAllCuocThanhTraCoQuan,
						TienDoThanhTraEnum.DA_BAN_HANH_KET_LUAN, cuocThanhTraRepo));
				mapMaSo.put("8", thongKeTongHopThanhTraService.getSoDonViDuocThanhTra(predAllCuocThanhTraCoQuan,
						cuocThanhTraRepo));
				BooleanExpression predAllCuocThanhTraCoQuanViPham = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraCoViPham(predAllCuocThanhTraCoQuan, cuocThanhTraRepo);
				mapMaSo.put("9", Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanViPham)).size()));
				mapMaSo.put("10", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "TONG_VI_PHAM", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("11", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_THU_HOI", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("12", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_KHAC", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("13", thongKeTongHopThanhTraService
						.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuanViPham, "TO_CHUC", cuocThanhTraRepo));
				mapMaSo.put("14", thongKeTongHopThanhTraService
						.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuanViPham, "CA_NHAN", cuocThanhTraRepo));
				BooleanExpression predAllCuocThanhTraChuyenCoQuanDieuTra = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraChuyenCoQuanDieuTra(predAllCuocThanhTraCoQuan, cuocThanhTraRepo);
				mapMaSo.put("15", thongKeTongHopThanhTraService
						.getKienNghiXuLyCCQDT(predAllCuocThanhTraChuyenCoQuanDieuTra, "VU", cuocThanhTraRepo));
				mapMaSo.put("16", thongKeTongHopThanhTraService.getKienNghiXuLyCCQDT(
						predAllCuocThanhTraChuyenCoQuanDieuTra, "DOI_TUONG", cuocThanhTraRepo));
				mapMaSo.put("17", thongKeTongHopThanhTraService.getSoDaThuTrongQuaTrinhThanhTra(
						predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "TIEN"));
				mapMaSo.put("18", 0);
				mapMaSo.put("19", 0);
				mapMaSo.put("20", 0);
				mapMaSo.put("21", 0);
				mapMaSo.put("22", 0);
				mapMaSo.put("23", 0);
				mapMaSo.put("24", 0);
				mapMaSo.put("25", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}
			
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopKetQuaThanhTraTheoTaiChinhNganSach(response,
					"DanhSachTongHopKetQuaThanhTraTheoTaiChinhNganSach", "sheetName", maSos, tuNgay, denNgay,
					"TỔNG HỢP KẾT QUẢ THANH TRA TRONG LĨNH VỰC TÀI CHÍNH NGÂN SÁCH");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoDatDai")
	@ApiOperation(value = "Tổng hợp kết quả thanh tra theo đất đai", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaThanhTraTheoDatDai(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			PersistentEntityResourceAssembler eass) {
		try {

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Long donViXuLy = Long
					.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();

			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			if (year == null) {
				year = Utils.localDateTimeNow().getYear();
			}

			Long field1 = 0L;
			Long field2 = 0L;
			Long field3 = 0L;
			Long field4 = 0L;
			Long field5 = 0L;
			Long field6 = 0L;
			Long field7 = 0L;
			Long field8 = 0L;
			Long field9 = 0L;
			Long field10 = 0L;
			Long field11 = 0L;
			Long field12 = 0L;
			Long field13 = 0L;
			Long field14 = 0L;
			Long field15 = 0L;
			Long field16 = 0L;
			Long field17 = 0L;
			Long field18 = 0L;
			Long field19 = 0L;
			Long field20 = 0L;
			Long field21 = 0L;
			Long field22 = 0L;
			Long field23 = 0L;
			Long field24 = 0L;
			Long field25 = 0L;
			Long field26 = 0L;
			Long field27 = 0L;
			Long field28 = 0L;
			Long field29 = 0L;
			Long field30 = 0L;
			Long field31 = 0L;
			Long field32 = 0L;
			Long field33 = 0L;
			Long field34 = 0L;
			Long field35 = 0L;
			Long field36 = 0L;
			Long field37 = 0L;
			Long field38 = 0L;
			Long field39 = 0L;
			Long field40 = 0L;
			
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null && listDonVis.size() > 0) {
					for (CoQuanQuanLy dv : listDonVis) {
						if (dv != null) {
							CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
							if (coQuan != null) {
								list.add(coQuan);
							}
						}
					}
					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null && listCapDonVis.size() > 0) {
					List<Long> thamSos = new ArrayList<Long>();
					thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

					for (CapCoQuanQuanLy cdv : listCapDonVis) {
						CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
						if (capDonVi != null) {
							listCapCQs.add(capDonVi.getId());
						}
					}
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));

					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			}
			
			BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTra(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraKyTruoc(loaiKy, quy, year, month, tuNgay, denNgay);
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllCuocThanhTraCoQuan = predAllCuocThanhTra
						.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId()));

				// Get cuoc thanh tra theo linh vuc hanh chinh
				predAllCuocThanhTraCoQuan = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(predAllCuocThanhTraCoQuan,
								LinhVucThanhTraEnum.DAT_DAI);

				// Dem so cuoc thanh tra
				Long tongSoCuocThanhTra = Long
						.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size());

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

				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				mapMaSo.put("donVi", mapDonVi);
				
				// So cuoc thanh tra
				BooleanExpression predAllCuocThanhTraCoQuanTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraTrongKy
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.DAT_DAI);
				BooleanExpression predAllCuocThanhTraCoQuanKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraKyTruoc
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.DAT_DAI);

				Long tongSoCuocThanhTraTrongKy = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanTrongKy)).size());
				Long tongSoCuocThanhTraKyTruoc = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanKyTruoc)).size());
				
				field1 += tongSoCuocThanhTra;
				mapMaSo.put("tongSo", tongSoCuocThanhTra);
				
				field2 += tongSoCuocThanhTraKyTruoc;
				mapMaSo.put("kyTruocChuyenSang", tongSoCuocThanhTraKyTruoc);
				
				field3 += tongSoCuocThanhTraTrongKy;
				mapMaSo.put("trienKhaiTrongKyBaoCao", tongSoCuocThanhTraTrongKy);
				
				field4 += tongSoCuocThanhTraTrongKy;
				mapMaSo.put("theoKeHoach", tongSoCuocThanhTraTheoKeHoach);
				
				field5 += tongSoCuocThanhTraDotXuat;
				mapMaSo.put("dotXuat", tongSoCuocThanhTraDotXuat);
				
				field6 += tongSoCuocThanhTraKetThucTrucTiep;
				mapMaSo.put("ketThucThanhTraTrucTiep", tongSoCuocThanhTraKetThucTrucTiep);
				
				field7 += tongSoCuocThanhTraKetThucTrucTiep;
				mapMaSo.put("daBanHanhKetLuan", tongSoCuocThanhTraDaBanHanhKetLuan);
				
				//8
				field8 += tongSoDonViDuocThanhTra;
				mapMaSo.put("soDonViDuocThanhTra", tongSoDonViDuocThanhTra);
				
				//9
				field9 += tongSoDonViCoViPham;
				mapMaSo.put("soDonViCoViPham", tongSoDonViCoViPham);

				// Tong vi pham
				field10 += tongViPhamTien;
				mapMaSo.put("tongViPhamTien", tongViPhamTien);
				
				field11 += tongViPhamDat;
				mapMaSo.put("tongViPhamDat", tongViPhamDat);

				// Cac dang vi pham ve dat
				Long cacDangViPhamVeDatDatLanChiem = thongKeTongHopThanhTraService
						.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham, "DAT_LAN_CHIEM", cuocThanhTraRepo);
				field12 += cacDangViPhamVeDatDatLanChiem;
				mapMaSo.put("datLanChiem", cacDangViPhamVeDatDatLanChiem);
				
				Long cacDangViPhamVeDatGiaoDatCapDatSaiDoiTuong = thongKeTongHopThanhTraService
						.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham, "GIAO_DAT_CAP_DAT_SAI_DOI_TUONG",
								cuocThanhTraRepo);
				field13 += cacDangViPhamVeDatGiaoDatCapDatSaiDoiTuong;
				mapMaSo.put("giaoDatCapDatSaiDoiTuong", cacDangViPhamVeDatGiaoDatCapDatSaiDoiTuong);
				
				Long cacDangViPhamVeDatCapBanDatTraiThamQuyen = thongKeTongHopThanhTraService.getCacDangViPhamVeDat(
						predAllCuocThanhTraCoQuanViPham, "CAP_BAN_GIAO_DAT_TRAI_THAM_QUYEN", cuocThanhTraRepo);
				field14 += cacDangViPhamVeDatCapBanDatTraiThamQuyen;
				mapMaSo.put("capBanDatTraiThamQuyen", cacDangViPhamVeDatCapBanDatTraiThamQuyen);
				
				Long cacDangViPhamVeDatCapGCNQSDDSai = thongKeTongHopThanhTraService.getCacDangViPhamVeDat(
						predAllCuocThanhTraCoQuanViPham, "CAP_GCN_QSDD_SAI", cuocThanhTraRepo);
				field15 += cacDangViPhamVeDatCapGCNQSDDSai;
				mapMaSo.put("capGCNQSDDSai", cacDangViPhamVeDatCapGCNQSDDSai);
				
				Long cacDangViPhamVeDatChuyenNhuongChoThueKhongDungQuyDinh = thongKeTongHopThanhTraService
						.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham,
								"CHUYEN_NHUONG_CHO_THUE_KHONG_DUNG_QUY_DINH", cuocThanhTraRepo);
				field16 += cacDangViPhamVeDatChuyenNhuongChoThueKhongDungQuyDinh;
				mapMaSo.put("chuyenNhuongChoThueKhongDungQuyDinh",
						cacDangViPhamVeDatChuyenNhuongChoThueKhongDungQuyDinh);
				
				Long cacDangViPhamVeDatSuDungDatKhongDungMucDich = thongKeTongHopThanhTraService
						.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham, "SU_DUNG_DAT_KHONG_DUNG_MUC_DICH",
								cuocThanhTraRepo);
				field17 += cacDangViPhamVeDatSuDungDatKhongDungMucDich;
				mapMaSo.put("suDungDatKhongDungMucDich", cacDangViPhamVeDatSuDungDatKhongDungMucDich);
				
				Long cacDangViPhamVeDatBoHoangHoa = thongKeTongHopThanhTraService
						.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham, "BO_HOANG_HOA", cuocThanhTraRepo);
				field18 += cacDangViPhamVeDatBoHoangHoa;
				mapMaSo.put("boHoangHoa", cacDangViPhamVeDatBoHoangHoa);
				
				Long cacDangViPhamVeDatViPhamKhac = thongKeTongHopThanhTraService
						.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham, "VI_PHAM_KHAC", cuocThanhTraRepo);
				field19 += cacDangViPhamVeDatViPhamKhac;
				mapMaSo.put("viPhamKhac", cacDangViPhamVeDatViPhamKhac);
				
				// Kien nghi thu hoi
				field20 += tongKNTHTien;
				mapMaSo.put("kienNghiThuHoiTien", tongKNTHTien);
				
				field21 += tongKNTHDat;
				mapMaSo.put("kienNghiThuHoiDat", tongKNTHDat);
				
				field22 += tongKNTHQDGiaoDat;
				mapMaSo.put("kienNghiThuHoiQDGiaoDat", tongKNTHQDGiaoDat);
				
				// Kien nghi khac
				field23 += tongKNKTien;
				mapMaSo.put("kienNghiKhacTien", tongKNKTien);
				
				field24 += tongKNKDat;
				mapMaSo.put("kienNghiKhacDat", tongKNKDat);
				
				// Kien nghi xu ly
				field25 += tongKNXLHanhChinhToChuc;
				mapMaSo.put("hanhChinhToChuc", tongKNXLHanhChinhToChuc);
				
				field26 += tongKNXLHanhChinhCaNhan;
				mapMaSo.put("hanhChinhCaNhan", tongKNXLHanhChinhCaNhan);
				
				field27 += tongKNXLVu;
				mapMaSo.put("chuyenCoQuanDieuTraVu", tongKNXLVu);
				
				field28 += tongKNXLDoiTuong;
				mapMaSo.put("chuyenCoQuanDieuTraDoiTuong", tongKNXLDoiTuong);


				// Da thu
				Long daThuTien = thongKeTongHopThanhTraService
						.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "TIEN");
				field29 += daThuTien;
				mapMaSo.put("daThuTien", daThuTien);
				
				Long daThuDat = thongKeTongHopThanhTraService
						.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "DAT");
				field30 += daThuDat;
				mapMaSo.put("daThuDat", daThuDat);
				
				Long daThuQdGiaoDat = thongKeTongHopThanhTraService.getSoDaThuTrongQuaTrinhThanhTra(
						predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "QD_GIAO_DAT");
				field31 += daThuQdGiaoDat;
				mapMaSo.put("daThuQdGiaoDat", daThuQdGiaoDat);
				
				field32 += 0;
				mapMaSo.put("tongSoKLTTVaQDXLDaKTDD", 0);
				
				/* Note: Chua xong Theo doi thuc hien */
				// Kiem tra don doc
				field33 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocTienPhaiThu", 0);
				
				field34 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocTienDaThu", 0);
				
				field35 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDatPhaiThu", 0);
				
				field36 += 0;
				mapMaSo.put("ketQuaKiemTraDonDocDatDaThu", 0);
				
				field37 += 0;
				mapMaSo.put("daXuLyHanhChinhToChuc", 0);
				
				field38 += 0;
				mapMaSo.put("daXuLyHanhChinhCaNhan", 0);
				
				field39 += 0;
				mapMaSo.put("daKhoiToVu", 0);
				
				field40 += 0;
				mapMaSo.put("daKhoiToDoiTuong", 0);
				
				mapMaSo.put("ghiChu", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
				mapDonVi = new HashMap<String, Object>();
			}
			
			// map Tong so
			Map<String, Object> mapTongCong = new HashMap<String, Object>();
			mapTongCong.put("tongSo", field1);
			mapTongCong.put("kyTruocChuyenSang", field2);
			mapTongCong.put("trienKhaiTrongKyBaoCao", field3);
			mapTongCong.put("theoKeHoach", field4);
			mapTongCong.put("dotXuat", field5);
			mapTongCong.put("ketThucThanhTraTrucTiep", field6);
			mapTongCong.put("daBanHanhKetLuan", field7);
			mapTongCong.put("soDonViDuocThanhTra", field8);
			mapTongCong.put("soDonViCoViPham", field9);
			mapTongCong.put("tongViPhamTien", field10);
			mapTongCong.put("tongViPhamDat", field11);
			mapTongCong.put("datLanChiem", field12);
			mapTongCong.put("giaoDatCapDatSaiDoiTuong", field13);
			mapTongCong.put("capBanDatTraiThamQuyen", field14);
			mapTongCong.put("capGCNQSDDSai", field15);
			mapTongCong.put("chuyenNhuongChoThueKhongDungQuyDinh", field16);
			mapTongCong.put("suDungDatKhongDungMucDich", field17);
			mapTongCong.put("boHoangHoa", field18);
			mapTongCong.put("viPhamKhac", field19);
			mapTongCong.put("kienNghiThuHoiTien", field20);
			mapTongCong.put("kienNghiThuHoiDat", field21);
			mapTongCong.put("kienNghiThuHoiQDGiaoDat", field22);
			mapTongCong.put("kienNghiKhacTien", field23);
			mapTongCong.put("kienNghiKhacDat", field24);
			mapTongCong.put("hanhChinhToChuc", field25);
			mapTongCong.put("hanhChinhCaNhan", field26);
			mapTongCong.put("chuyenCoQuanDieuTraVu", field27);
			mapTongCong.put("chuyenCoQuanDieuTraDoiTuong", field28);
			mapTongCong.put("daThuTien", field29);
			mapTongCong.put("daThuDat", field30);
			mapTongCong.put("daThuQdGiaoDat", field31);
			mapTongCong.put("tongSoKLTTVaQDXLDaKTDD", field32);
			mapTongCong.put("ketQuaKiemTraDonDocTienPhaiThu", field33);
			mapTongCong.put("ketQuaKiemTraDonDocTienDaThu", field34);
			mapTongCong.put("ketQuaKiemTraDonDocDatPhaiThu", field35);
			mapTongCong.put("ketQuaKiemTraDonDocDatDaThu", field36);
			mapTongCong.put("daXuLyHanhChinhToChuc", field37);
			mapTongCong.put("daXuLyHanhChinhCaNhan", field38);
			mapTongCong.put("daKhoiToVu", field39);
			mapTongCong.put("daKhoiToDoiTuong", field40);
			mapTongCong.put("ghiChu", "");
			
			map.put("tongCongs", mapTongCong);
			map.put("maSos", maSos);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoDatDai/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả thanh tra theo lĩnh vực đất đai", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelKetQuaThanhTraTheoDatDai(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe) throws IOException {

		try {
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			if (year == null) {
				year = Utils.localDateTimeNow().getYear();
			}
			
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null) {
					for (CoQuanQuanLy dv : listDonVis) {
						CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
						if (coQuan != null) {
							list.add(coQuan);
						}
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null) {
					if (listCapDonVis.size() > 0) {
						List<Long> thamSos = new ArrayList<Long>();
						thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

						for (CapCoQuanQuanLy cdv : listCapDonVis) {
							CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
							if (capDonVi != null) {
								listCapCQs.add(capDonVi.getId());
							}
						}
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			}
			
			BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTra(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraKyTruoc(loaiKy, quy, year, month, tuNgay, denNgay);
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllCuocThanhTraCoQuan = predAllCuocThanhTra
						.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId()));

				// Get cuoc thanh tra theo linh vuc hanh chinh
				predAllCuocThanhTraCoQuan = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(predAllCuocThanhTraCoQuan,
								LinhVucThanhTraEnum.DAT_DAI);
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("0", cq.getTen());
				mapMaSo.put("1", Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size()));
				BooleanExpression predAllCuocThanhTraCoQuanTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraTrongKy
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.DAT_DAI);
				BooleanExpression predAllCuocThanhTraCoQuanKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(
								predAllCuocThanhTraKyTruoc
										.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())),
								LinhVucThanhTraEnum.DAT_DAI);

				Long tongSoCuocThanhTraTrongKy = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanTrongKy)).size());
				Long tongSoCuocThanhTraKyTruoc = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanKyTruoc)).size());
				mapMaSo.put("2", tongSoCuocThanhTraKyTruoc);
				mapMaSo.put("3", tongSoCuocThanhTraTrongKy);
				mapMaSo.put("4", thongKeTongHopThanhTraService.getCuocThanhTraTheoHinhThuc(
						predAllCuocThanhTraCoQuan, HinhThucThanhTraEnum.THEO_KE_HOACH, cuocThanhTraRepo));
				mapMaSo.put("5", thongKeTongHopThanhTraService.getCuocThanhTraTheoHinhThuc(
						predAllCuocThanhTraCoQuan, HinhThucThanhTraEnum.DOT_XUAT, cuocThanhTraRepo));
				mapMaSo.put("6", thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(predAllCuocThanhTraCoQuan,
						TienDoThanhTraEnum.KET_THUC_THANH_TRA_TRUC_TIEP, cuocThanhTraRepo));
				mapMaSo.put("7", thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(predAllCuocThanhTraCoQuan,
						TienDoThanhTraEnum.DA_BAN_HANH_KET_LUAN, cuocThanhTraRepo));
				mapMaSo.put("8", thongKeTongHopThanhTraService.getSoDonViDuocThanhTra(predAllCuocThanhTraCoQuan,
						cuocThanhTraRepo));
				BooleanExpression predAllCuocThanhTraCoQuanViPham = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraCoViPham(predAllCuocThanhTraCoQuan, cuocThanhTraRepo);
				mapMaSo.put("9", Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuanViPham)).size()));
				mapMaSo.put("10", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "TONG_VI_PHAM", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("11", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "TONG_VI_PHAM", "DAT", cuocThanhTraRepo));
				mapMaSo.put("12", thongKeTongHopThanhTraService
						.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham, "DAT_LAN_CHIEM", cuocThanhTraRepo));
				mapMaSo.put("13", thongKeTongHopThanhTraService.getCacDangViPhamVeDat(
						predAllCuocThanhTraCoQuanViPham, "GIAO_DAT_CAP_DAT_SAI_DOI_TUONG", cuocThanhTraRepo));
				mapMaSo.put("14", thongKeTongHopThanhTraService.getCacDangViPhamVeDat(
						predAllCuocThanhTraCoQuanViPham, "CAP_BAN_GIAO_DAT_TRAI_THAM_QUYEN", cuocThanhTraRepo));
				mapMaSo.put("15", thongKeTongHopThanhTraService.getCacDangViPhamVeDat(
						predAllCuocThanhTraCoQuanViPham, "CAP_GCN_QSDD_SAI", cuocThanhTraRepo));
				mapMaSo.put("16",
						thongKeTongHopThanhTraService.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham,
								"CHUYEN_NHUONG_CHO_THUE_KHONG_DUNG_QUY_DINH", cuocThanhTraRepo));
				mapMaSo.put("17", thongKeTongHopThanhTraService.getCacDangViPhamVeDat(
						predAllCuocThanhTraCoQuanViPham, "SU_DUNG_DAT_KHONG_DUNG_MUC_DICH", cuocThanhTraRepo));
				mapMaSo.put("18", thongKeTongHopThanhTraService
						.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham, "BO_HOANG_HOA", cuocThanhTraRepo));
				mapMaSo.put("19", thongKeTongHopThanhTraService
						.getCacDangViPhamVeDat(predAllCuocThanhTraCoQuanViPham, "VI_PHAM_KHAC", cuocThanhTraRepo));
				mapMaSo.put("20", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_THU_HOI", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("21", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_THU_HOI", "DAT", cuocThanhTraRepo));
				mapMaSo.put("22", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_THU_HOI", "QD_GIAO_DAT", cuocThanhTraRepo));
				mapMaSo.put("23", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_KHAC", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("24", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_KHAC", "DAT", cuocThanhTraRepo));
				mapMaSo.put("25", thongKeTongHopThanhTraService
						.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuanViPham, "TO_CHUC", cuocThanhTraRepo));
				mapMaSo.put("26", thongKeTongHopThanhTraService
						.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuanViPham, "CA_NHAN", cuocThanhTraRepo));
				BooleanExpression predAllCuocThanhTraChuyenCoQuanDieuTra = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraChuyenCoQuanDieuTra(predAllCuocThanhTraCoQuan, cuocThanhTraRepo);
				mapMaSo.put("27", thongKeTongHopThanhTraService
						.getKienNghiXuLyCCQDT(predAllCuocThanhTraChuyenCoQuanDieuTra, "VU", cuocThanhTraRepo));
				mapMaSo.put("28", thongKeTongHopThanhTraService.getKienNghiXuLyCCQDT(
						predAllCuocThanhTraChuyenCoQuanDieuTra, "DOI_TUONG", cuocThanhTraRepo));
				mapMaSo.put("29", thongKeTongHopThanhTraService.getSoDaThuTrongQuaTrinhThanhTra(
						predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "TIEN"));
				mapMaSo.put("30", thongKeTongHopThanhTraService
						.getSoDaThuTrongQuaTrinhThanhTra(predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "DAT"));
				mapMaSo.put("31", thongKeTongHopThanhTraService.getSoDaThuTrongQuaTrinhThanhTra(
						predAllCuocThanhTraCoQuanViPham, cuocThanhTraRepo, "QD_GIAO_DAT"));
				mapMaSo.put("32", 0);
				mapMaSo.put("33", 0);
				mapMaSo.put("34", 0);
				mapMaSo.put("35", 0);
				mapMaSo.put("36", 0);
				mapMaSo.put("37", 0);
				mapMaSo.put("38", 0);
				mapMaSo.put("39", 0);
				mapMaSo.put("40", 0);
				mapMaSo.put("41", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}			
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopKetQuaThanhTraTheoDatDai(response, "DanhSachTongHopKetQuaThanhTraTheoDatDai",
					"sheetName", maSos, tuNgay, denNgay, "TỔNG HỢP KẾT QUẢ THANH TRA TRONG LĨNH VỰC ĐẤT ĐAI");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoChuyenNganh")
	@ApiOperation(value = "Tổng hợp kết quả thanh tra theo chuyên ngành", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaThanhTraTheoChuyenNganh(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			PersistentEntityResourceAssembler eass) {
		try {

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Long donViXuLy = Long
					.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();

			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			if (year == null) {
				year = Utils.localDateTimeNow().getYear();
			}

			Long field1 = 0L;
			Long field2 = 0L;
			Long field3 = 0L;
			Long field4 = 0L;
			Long field5 = 0L;
			Long field6 = 0L;
			Long field7 = 0L;
			Long field8 = 0L;
			Long field9 = 0L;
			Long field10 = 0L;
			Long field11 = 0L;
			Long field12 = 0L;
			Long field13 = 0L;
			Long field14 = 0L;
			Long field15 = 0L;
			Long field16 = 0L;
			Long field17 = 0L;
			Long field18 = 0L;
			Long field19 = 0L;
			Long field20 = 0L;
			Long field21 = 0L;
			Long field22 = 0L;
			Long field23 = 0L;
			Long field24 = 0L;
			Long field25 = 0L;
			Long field26 = 0L;
			
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null && listDonVis.size() > 0) {
					for (CoQuanQuanLy dv : listDonVis) {
						if (dv != null) {
							CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
							if (coQuan != null) {
								list.add(coQuan);
							}
						}
					}
					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null && listCapDonVis.size() > 0) {
					List<Long> thamSos = new ArrayList<Long>();
					thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

					for (CapCoQuanQuanLy cdv : listCapDonVis) {
						CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
						if (capDonVi != null) {
							listCapCQs.add(capDonVi.getId());
						}
					}
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));

					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			}
			
			BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			
			for (CoQuanQuanLy cq : donVis) {
				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				mapMaSo.put("donVi", mapDonVi);
				
				BooleanExpression predAllCuocThanhTraCoQuan = predAllCuocThanhTra
						.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId()));

				// Get cuoc thanh tra theo linh vuc hanh chinh
				predAllCuocThanhTraCoQuan = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(predAllCuocThanhTraCoQuan,
								LinhVucThanhTraEnum.TAI_CHINH);
				// So Cuoc Thanh Tra
				Long soCuocTTKTTongSo = Long
						.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size());
				mapMaSo.put("tongSo", soCuocTTKTTongSo);
				field1 += soCuocTTKTTongSo;
				
				Long soCuocTTKTThanhLapDoan = Long.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo
						.findAll(predAllCuocThanhTraCoQuan.and(QCuocThanhTra.cuocThanhTra.thanhVienDoan.isTrue())))
								.size());
				field2 += soCuocTTKTThanhLapDoan;
				mapMaSo.put("thanhLapDoan", soCuocTTKTThanhLapDoan);
				
				Long soCuocTTKTThanhTraDocLap = Long.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo
						.findAll(predAllCuocThanhTraCoQuan.and(QCuocThanhTra.cuocThanhTra.thanhVienDoan.isFalse())))
								.size());
				field3 += soCuocTTKTThanhTraDocLap;
				mapMaSo.put("thanhTraDocLap", soCuocTTKTThanhTraDocLap);

				// So Ca nhan duoc thanh tra kiem tra
				Long caNhanDuocTTKTThanhTra = 0L;
				field4 += caNhanDuocTTKTThanhTra;
				mapMaSo.put("caNhanDuocThanhTra", caNhanDuocTTKTThanhTra);
				Long caNhanDuocTTKTKiemTra = 0L;
				field5 += caNhanDuocTTKTKiemTra;
				mapMaSo.put("caNhanDuocKiemTra", caNhanDuocTTKTKiemTra);

				// So To chuc duoc thanh tra, kiem tra
				Long toChucDuocTTKTThanhTra = 0L;
				field6 += toChucDuocTTKTThanhTra;
				mapMaSo.put("toChucDuocThanhTra", toChucDuocTTKTThanhTra);
				Long toChucDuocTTKTKiemTra = 0L;
				field7 += toChucDuocTTKTKiemTra;
				mapMaSo.put("toChucDuocKiemTra", toChucDuocTTKTKiemTra);

				// Ket qua
				// So co vi pham
				Long soCoViPhamTongSo = thongKeTongHopThanhTraService
						.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TONG_SO", cuocThanhTraRepo);
				field8 += soCoViPhamTongSo;
				mapMaSo.put("soCoViPhamTongSo", soCoViPhamTongSo);
				Long soCoViPhamCaNhan = thongKeTongHopThanhTraService
						.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "CA_NHAN", cuocThanhTraRepo);
				field9 += soCoViPhamCaNhan;
				mapMaSo.put("soCoViPhamCaNhan", soCoViPhamCaNhan);
				Long soCoViPhanToChuc = thongKeTongHopThanhTraService
						.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TO_CHUC", cuocThanhTraRepo);
				field10 += soCoViPhanToChuc;
				mapMaSo.put("soCoViPhanToChuc", soCoViPhanToChuc);

				// So quyet dinh xu phat hanh chinh duoc ban hanh
				Long soQDXuPhatHanhChinhTongSo = thongKeTongHopThanhTraService
						.getSoQuyetDinhXuPhatKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TONG_SO",
								cuocThanhTraRepo);
				field11 += soQDXuPhatHanhChinhTongSo;
				mapMaSo.put("soQDXuPhatHanhChinhTongSo", soQDXuPhatHanhChinhTongSo);
				Long soQDXuPhatHanhChinhCaNhan = thongKeTongHopThanhTraService
						.getSoQuyetDinhXuPhatKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "CA_NHAN",
								cuocThanhTraRepo);
				field12 += soQDXuPhatHanhChinhCaNhan;
				mapMaSo.put("soQDXuPhatHanhChinhCaNhan", soQDXuPhatHanhChinhCaNhan);
				Long soQDXuPhatHanhChinhToChuc = thongKeTongHopThanhTraService
						.getSoQuyetDinhXuPhatKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TO_CHUC",
								cuocThanhTraRepo);
				field13 += soQDXuPhatHanhChinhToChuc;
				mapMaSo.put("soQDXuPhatHanhChinhToChuc", soQDXuPhatHanhChinhToChuc);

				// So tien vi pham
				Long soTienViPhamTongSo = thongKeTongHopThanhTraService
						.getTienViPhamKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TONG_SO", cuocThanhTraRepo);
				field14 += soTienViPhamTongSo;
				mapMaSo.put("soTienViPhamTongSo", soTienViPhamTongSo);
				Long soTienViPhamCaNhan = thongKeTongHopThanhTraService
						.getTienViPhamKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "CA_NHAN", cuocThanhTraRepo);
				field15 += soTienViPhamCaNhan;
				mapMaSo.put("soTienViPhamCaNhan", soTienViPhamCaNhan);
				Long soTienViPhamToChuc = thongKeTongHopThanhTraService
						.getTienViPhamKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TO_CHUC", cuocThanhTraRepo);
				field16 += soTienViPhamToChuc;
				mapMaSo.put("soTienViPhamToChuc", soTienViPhamToChuc);

				// So tien kien nghi thu hoi
				Long soTienKienNghiThuHoi = thongKeTongHopThanhTraService.getTienViPhamKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "KIEN_NGHI_THU_HOI", cuocThanhTraRepo);
				field17 += soTienKienNghiThuHoi;
				mapMaSo.put("soTienKienNghiThuHoi", soTienKienNghiThuHoi);
				
				// So tien xu ly tai san vi pham
				Long soTienXuLyTaiSanTongSo = thongKeTongHopThanhTraService.getTienXuLyTaiSanKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "TONG_SO", cuocThanhTraRepo);
				field18 += soTienXuLyTaiSanTongSo;
				mapMaSo.put("soTienXuLyTaiSanTongSo", soTienXuLyTaiSanTongSo);
				Long soTienXuLyTaiSanTichThu = thongKeTongHopThanhTraService.getTienXuLyTaiSanKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "TICH_THU", cuocThanhTraRepo);
				field19 += soTienXuLyTaiSanTichThu;
				mapMaSo.put("soTienXuLyTaiSanTichThu", soTienXuLyTaiSanTichThu);
				Long soTienXuLyTaiSanTieuHuy = thongKeTongHopThanhTraService.getTienXuLyTaiSanKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "TIEU_HUY", cuocThanhTraRepo);
				field20 += soTienXuLyTaiSanTieuHuy;
				mapMaSo.put("soTienXuLyTaiSanTieuHuy", soTienXuLyTaiSanTieuHuy);

				// So tien xu ly vi pham
				Long soTienXuLyViPhamTongSo = thongKeTongHopThanhTraService
						.getTienXuPhatViPhamKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TONG_SO",
								cuocThanhTraRepo);
				field21 += soTienXuLyViPhamTongSo;
				mapMaSo.put("soTienXuLyViPhamTongSo", soTienXuLyViPhamTongSo);
				Long soTienXuLyViPhamCaNhan = thongKeTongHopThanhTraService
						.getTienXuPhatViPhamKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "CA_NHAN",
								cuocThanhTraRepo);
				field22 += soTienXuLyViPhamCaNhan;
				mapMaSo.put("soTienXuLyViPhamCaNhan", soTienXuLyViPhamCaNhan);
				Long soTienXuLyViPhamToChuc = thongKeTongHopThanhTraService
						.getTienXuPhatViPhamKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan, "TO_CHUC",
								cuocThanhTraRepo);
				field23 += soTienXuLyViPhamToChuc;
				mapMaSo.put("soTienXuLyViPhamToChuc", soTienXuLyViPhamToChuc);

				// So tien da thu
				Long soTienDaThuTongSo = thongKeTongHopThanhTraService
						.getTienDaThuThanhTraChuyenNganh(predAllCuocThanhTraCoQuan, "TONG_SO", cuocThanhTraRepo);
				field24 += soTienDaThuTongSo;
				mapMaSo.put("soTienDaThuTongSo", soTienDaThuTongSo);
				Long soTienDaThuCaNhan = thongKeTongHopThanhTraService
						.getTienDaThuThanhTraChuyenNganh(predAllCuocThanhTraCoQuan, "CA_NHAN", cuocThanhTraRepo);
				field25 += soTienDaThuCaNhan;
				mapMaSo.put("soTienDaThuCaNhan", soTienDaThuCaNhan);
				Long soTienDaThuToChuc = thongKeTongHopThanhTraService
						.getTienDaThuThanhTraChuyenNganh(predAllCuocThanhTraCoQuan, "TO_CHUC", cuocThanhTraRepo);
				field26 += soTienDaThuToChuc;
				mapMaSo.put("soTienDaThuToChuc", soTienDaThuToChuc);

				mapMaSo.put("ghiChu", "");
				
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
				mapDonVi = new HashMap<String, Object>();
			}
			// map Tong so
			Map<String, Object> mapTongCong = new HashMap<String, Object>();
			mapTongCong.put("tongSo", field1);
			mapTongCong.put("thanhLapDoan", field2);
			mapTongCong.put("thanhTraDocLap", field3);
			mapTongCong.put("caNhanDuocThanhTra", field4);
			mapTongCong.put("caNhanDuocKiemTra", field5);
			mapTongCong.put("toChucDuocThanhTra", field6);
			mapTongCong.put("toChucDuocKiemTra", field7);
			mapTongCong.put("soCoViPhamTongSo", field8);
			mapTongCong.put("soCoViPhamCaNhan", field9);
			mapTongCong.put("soCoViPhanToChuc", field10);
			mapTongCong.put("soQDXuPhatHanhChinhTongSo", field11);
			mapTongCong.put("soQDXuPhatHanhChinhCaNhan", field12);
			mapTongCong.put("soQDXuPhatHanhChinhToChuc", field13);
			mapTongCong.put("soTienViPhamTongSo", field14);
			mapTongCong.put("soTienViPhamCaNhan", field15);
			mapTongCong.put("soTienViPhamToChuc", field16);
			mapTongCong.put("soTienKienNghiThuHoi", field17);
			mapTongCong.put("soTienXuLyTaiSanTongSo", field18);
			mapTongCong.put("soTienXuLyTaiSanTichThu", field19);
			mapTongCong.put("soTienXuLyTaiSanTieuHuy", field20);
			mapTongCong.put("soTienXuLyViPhamTongSo", field21);
			mapTongCong.put("soTienXuLyViPhamCaNhan", field22);
			mapTongCong.put("soTienXuLyViPhamToChuc", field23);
			mapTongCong.put("soTienDaThuTongSo", field24);
			mapTongCong.put("soTienDaThuCaNhan", field25);
			mapTongCong.put("soTienDaThuToChuc", field26);
			mapTongCong.put("ghiChu", "");
			
			map.put("tongCongs", mapTongCong);
			map.put("maSos", maSos);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoChuyenNganh/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả thanh tra theo chuyên ngành", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelKetQuaThanhTraTheoChuyenNganh(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis)
			throws IOException {

		try {
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			if (year == null) {
				year = Utils.localDateTimeNow().getYear();
			}
			
			BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null) {
					for (CoQuanQuanLy dv : listDonVis) {
						CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
						if (coQuan != null) {
							list.add(coQuan);
						}
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null) {
					if (listCapDonVis.size() > 0) {
						List<Long> thamSos = new ArrayList<Long>();
						thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

						for (CapCoQuanQuanLy cdv : listCapDonVis) {
							CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
							if (capDonVi != null) {
								listCapCQs.add(capDonVi.getId());
							}
						}
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			}
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllCuocThanhTraCoQuan = predAllCuocThanhTra
						.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId()));

				// Get cuoc thanh tra theo linh vuc hanh chinh
				predAllCuocThanhTraCoQuan = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraTheoLinhVuc(predAllCuocThanhTraCoQuan,
								LinhVucThanhTraEnum.TAI_CHINH);
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("0", cq.getTen());
				mapMaSo.put("1", Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraCoQuan)).size()));
				mapMaSo.put("2",
						Long.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(
								predAllCuocThanhTraCoQuan.and(QCuocThanhTra.cuocThanhTra.thanhVienDoan.isTrue())))
										.size()));
				mapMaSo.put("3",
						Long.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(
								predAllCuocThanhTraCoQuan.and(QCuocThanhTra.cuocThanhTra.thanhVienDoan.isFalse())))
										.size()));
				mapMaSo.put("4", 0);
				mapMaSo.put("5", 0);
				mapMaSo.put("6", 0);
				mapMaSo.put("7", 0);
				mapMaSo.put("8", thongKeTongHopThanhTraService.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan,
						"TONG_SO", cuocThanhTraRepo));
				mapMaSo.put("9", thongKeTongHopThanhTraService.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan,
						"CA_NHAN", cuocThanhTraRepo));
				mapMaSo.put("10", thongKeTongHopThanhTraService.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuan,
						"TO_CHUC", cuocThanhTraRepo));
				mapMaSo.put("11", thongKeTongHopThanhTraService.getSoQuyetDinhXuPhatKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "TONG_SO", cuocThanhTraRepo));
				mapMaSo.put("12", thongKeTongHopThanhTraService.getSoQuyetDinhXuPhatKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "CA_NHAN", cuocThanhTraRepo));
				mapMaSo.put("13", thongKeTongHopThanhTraService.getSoQuyetDinhXuPhatKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "TO_CHUC", cuocThanhTraRepo));
				mapMaSo.put("14", thongKeTongHopThanhTraService.getTienViPhamKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "TONG_SO", cuocThanhTraRepo));
				mapMaSo.put("15", thongKeTongHopThanhTraService.getTienViPhamKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "CA_NHAN", cuocThanhTraRepo));
				mapMaSo.put("16", thongKeTongHopThanhTraService.getTienViPhamKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "TO_CHUC", cuocThanhTraRepo));
				mapMaSo.put("17", thongKeTongHopThanhTraService.getTienViPhamKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "KIEN_NGHI_THU_HOI", cuocThanhTraRepo));
				mapMaSo.put("18", thongKeTongHopThanhTraService.getTienXuLyTaiSanKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "TONG_SO", cuocThanhTraRepo));
				mapMaSo.put("19", thongKeTongHopThanhTraService.getTienXuLyTaiSanKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "TICH_THU", cuocThanhTraRepo));
				mapMaSo.put("20", thongKeTongHopThanhTraService.getTienXuLyTaiSanKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "TIEU_HUY", cuocThanhTraRepo));
				mapMaSo.put("21", thongKeTongHopThanhTraService.getTienXuPhatViPhamKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "TONG_SO", cuocThanhTraRepo));
				mapMaSo.put("22", thongKeTongHopThanhTraService.getTienXuPhatViPhamKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "CA_NHAN", cuocThanhTraRepo));
				mapMaSo.put("23", thongKeTongHopThanhTraService.getTienXuPhatViPhamKienNghiXuLyHanhChinh(
						predAllCuocThanhTraCoQuan, "TO_CHUC", cuocThanhTraRepo));
				mapMaSo.put("24", thongKeTongHopThanhTraService
						.getTienDaThuThanhTraChuyenNganh(predAllCuocThanhTraCoQuan, "TONG_SO", cuocThanhTraRepo));
				mapMaSo.put("25", thongKeTongHopThanhTraService
						.getTienDaThuThanhTraChuyenNganh(predAllCuocThanhTraCoQuan, "CA_NHAN", cuocThanhTraRepo));
				mapMaSo.put("26", thongKeTongHopThanhTraService
						.getTienDaThuThanhTraChuyenNganh(predAllCuocThanhTraCoQuan, "TO_CHUC", cuocThanhTraRepo));
				mapMaSo.put("27", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}
			
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopKetQuaThanhTraTheoChuyenNganh(response,
					"DanhSachTongHopKetQuaThanhTraTheoChuyenNganh", "sheetName", maSos, tuNgay, denNgay,
					"TỔNG HỢP KẾT QUẢ THANH TRA, KIỂM TRA CHUYÊN NGÀNH");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraLai")
	@ApiOperation(value = "Tổng hợp kết quả thanh tra lại", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaThanhTraTheoLai(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "loaiKy", required = true) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			PersistentEntityResourceAssembler eass) {
		try {

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Long donViXuLy = Long
					.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());

			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			if (year == null) {
				year = Utils.localDateTimeNow().getYear();
			}
			
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			
			Long field1 = 0L;
			Long field2 = 0L;
			Long field3 = 0L;
			Long field4 = 0L;
			Long field5 = 0L;
			Long field6 = 0L;
			Long field7 = 0L;
			Long field8 = 0L;
			Long field9 = 0L;
			Long field10 = 0L;
			Long field11 = 0L;
			Long field12 = 0L;
			Long field13 = 0L;
			Long field14 = 0L;
			Long field15 = 0L;
			Long field16 = 0L;
			Long field17 = 0L;
			Long field18 = 0L;
			Long field19 = 0L;
			Long field20 = 0L;
			Long field21 = 0L;
			Long field22 = 0L;
			Long field23 = 0L;
			Long field24 = 0L;
			Long field25 = 0L;
			Long field26 = 0L;
			Long field27 = 0L;
			Long field28 = 0L;
			
			BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTra(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraKyTruoc(loaiKy, quy, year, month, tuNgay, denNgay);
			
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null && listDonVis.size() > 0) {
					for (CoQuanQuanLy dv : listDonVis) {
						if (dv != null) {
							CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
							if (coQuan != null) {
								list.add(coQuan);
							}
						}
					}
					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null && listCapDonVis.size() > 0) {
					List<Long> thamSos = new ArrayList<Long>();
					thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

					for (CapCoQuanQuanLy cdv : listCapDonVis) {
						CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
						if (capDonVi != null) {
							listCapCQs.add(capDonVi.getId());
						}
					}
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));

					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			}
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllCuocThanhTraLai = predAllCuocThanhTra;

				// Get cuoc thanh tra lai
				predAllCuocThanhTraLai = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraLai(predAllCuocThanhTraLai
								.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())));

				// Dem so cuoc thanh tra lai
				Long tongSoCuocThanhTraLai = Long
						.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraLai)).size());

				// Dem so cuoc theo thanh lai
				// Trong ky
				BooleanExpression predAllCuocThanhTraLaiTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraLai(predAllCuocThanhTraTrongKy
								.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())));
				BooleanExpression predAllCuocThanhTraLaiKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraLai(predAllCuocThanhTraKyTruoc
								.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())));

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
				field11 += tongSoDonViDuocThanhTra;

				// Lay danh sach cuoc thanh tra co vi pham
				BooleanExpression predAllCuocThanhTraCoQuanViPham = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraCoViPham(predAllCuocThanhTraLai, cuocThanhTraRepo);

				// Dem so don vi co vi pham
				Long tongSoDonViCoViPham = thongKeTongHopThanhTraService
						.getSoDonViDuocThanhTraCoViPham(predAllCuocThanhTraLai, cuocThanhTraRepo);
				field12 += tongSoDonViCoViPham;

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

				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				mapMaSo.put("donVi", mapDonVi);
				
				// So cuoc thanh tra
				field1 += tongSoCuocThanhTraLai;
				mapMaSo.put("tongSo", tongSoCuocThanhTraLai);
				
				field2 += tongSoCuocThanhTraLai;
				mapMaSo.put("kyTruocChuyenSang", tongSoCuocThanhKyTruoc);
				field3 += tongSoCuocThanhTrongKy;
				mapMaSo.put("trienKhaiTrongKyBaoCao", tongSoCuocThanhTrongKy);

				field4 += tongSoCuocThanhTraKetThucTrucTiep;
				mapMaSo.put("ketThucThanhTraTrucTiep", tongSoCuocThanhTraKetThucTrucTiep);
				field5 += tongSoCuocThanhTraDaBanHanhKetLuan;
				mapMaSo.put("daBanHanhKetLuan", tongSoCuocThanhTraDaBanHanhKetLuan);

				Long canCuThanhTraLaiViPhamTrinhTu = thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(
						predAllCuocThanhTraLai, CanCuThanhTraLaiEnum.VI_PHAM_TRINH_TU, cuocThanhTraRepo);
				field6 += canCuThanhTraLaiViPhamTrinhTu;
				mapMaSo.put("viPhamTrinhTu", canCuThanhTraLaiViPhamTrinhTu);
				Long canCuThanhTraLaiKetLuanKhongPhuHop = thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(
						predAllCuocThanhTraLai, CanCuThanhTraLaiEnum.KET_LUAN_KHONG_PHU_HOP, cuocThanhTraRepo);
				field7 += canCuThanhTraLaiKetLuanKhongPhuHop;
				mapMaSo.put("ketLuanKhongPhuHop", canCuThanhTraLaiKetLuanKhongPhuHop);
				Long canCuThanhTraLaiSaiLamApDungPhapLuat = thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(
						predAllCuocThanhTraLai, CanCuThanhTraLaiEnum.SAI_LAM_AP_DUNG_PHAP_LUAT, cuocThanhTraRepo);
				field8 += canCuThanhTraLaiSaiLamApDungPhapLuat;
				mapMaSo.put("saiLamApDungPhapLuat", canCuThanhTraLaiSaiLamApDungPhapLuat);
				Long canCuThanhTraLaiCoYLamSaiLechHoSo = thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(
						predAllCuocThanhTraLai, CanCuThanhTraLaiEnum.CO_Y_LAM_SAI_LECH_HO_SO, cuocThanhTraRepo);
				field9 += canCuThanhTraLaiCoYLamSaiLechHoSo;
				mapMaSo.put("coYLamSaiLechHoSo", canCuThanhTraLaiCoYLamSaiLechHoSo);
				Long canCuThanhTraLaiViPhamNghiemTrong = thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(
						predAllCuocThanhTraLai, CanCuThanhTraLaiEnum.VI_PHAM_NGHIEM_TRONG, cuocThanhTraRepo);
				field10 += canCuThanhTraLaiViPhamNghiemTrong;
				mapMaSo.put("viPhamNghiemTrong", canCuThanhTraLaiViPhamNghiemTrong);
				
				//11
				field11 += tongSoDonViDuocThanhTra;
				mapMaSo.put("soDonViDaThanhTraLai", tongSoDonViDuocThanhTra);
				
				//12
				field12 += tongSoDonViCoViPham;
				mapMaSo.put("soDonViCoViPham", tongSoDonViCoViPham);
				
				// Tong vi pham
				field13 += tongViPhamTien;
				mapMaSo.put("viPhamVeKinhTeTien", tongViPhamTien);
				field14 += tongViPhamDat;
				mapMaSo.put("viPhamVeKinhTeDat", tongViPhamDat);

				// Kien nghi thu hoi
				field15 += tongKNTHTien;
				mapMaSo.put("kienNghiThuHoiTien", tongKNTHTien);
				field16 += tongKNTHDat;
				mapMaSo.put("kienNghiThuHoiDat", tongKNTHDat);

				// Kien nghi khac
				field17 += tongKNKTien;
				mapMaSo.put("kienNghiKhacTien", tongKNKTien);
				field18 += tongKNKDat;
				mapMaSo.put("kienNghiKhacDat", tongKNKDat);

				// Kien nghi xu ly
				field19 += tongKNXLHanhChinhToChuc;
				mapMaSo.put("hanhChinhToChuc", tongKNXLHanhChinhToChuc);
				field20 += tongKNXLHanhChinhCaNhan;
				mapMaSo.put("hanhChinhCaNhan", tongKNXLHanhChinhCaNhan);

				field21 += tongKNXLVu;
				mapMaSo.put("chuyenCoQuanDieuTraVu", tongKNXLVu);
				field22 += tongKNXLDoiTuong;
				mapMaSo.put("chuyenCoQuanDieuTraDoiTuong", tongKNXLDoiTuong);

				// Ket qua thuc hien
				field23 += 0;
				mapMaSo.put("ketQuaThucHienTienDaThu", 0);
				
				field24 += 0;
				mapMaSo.put("ketQuaThucHienDatDaThu", 0);
				
				field25 += 0;
				mapMaSo.put("ketQuaThucHienToChuc", 0);
				
				field26 += 0;
				mapMaSo.put("ketQuaThucHienCaNhan", 0);
				
				field27 += 0;
				mapMaSo.put("ketQuaThucHienVu", 0);
				
				field28 += 0;
				mapMaSo.put("ketQuaThucHienDoiTuong", 0);
				
				mapMaSo.put("ghiChu", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
				mapDonVi = new HashMap<String, Object>();
			}
			
			// Map tong so
			Map<String, Object> mapTongSo = new HashMap<String, Object>();
			mapTongSo.put("tongSo", field1);
			mapTongSo.put("kyTruocChuyenSang", field2);
			mapTongSo.put("trienKhaiTrongKyBaoCao", field3);
			mapTongSo.put("ketThucThanhTraTrucTiep", field4);
			mapTongSo.put("daBanHanhKetLuan", field5);
			mapTongSo.put("viPhamTrinhTu", field6);
			mapTongSo.put("ketLuanKhongPhuHop", field7);
			mapTongSo.put("saiLamApDungPhapLuat", field8);
			mapTongSo.put("coYLamSaiLechHoSo", field9);
			mapTongSo.put("viPhamNghiemTrong", field10);
			mapTongSo.put("soDonViDaThanhTraLai", field11);
			mapTongSo.put("soDonViCoViPham", field12);
			mapTongSo.put("viPhamVeKinhTeTien", field13);
			mapTongSo.put("viPhamVeKinhTeDat", field14);
			mapTongSo.put("kienNghiThuHoiTien", field15);
			mapTongSo.put("kienNghiThuHoiDat", field16);
			mapTongSo.put("kienNghiKhacTien", field17);
			mapTongSo.put("kienNghiKhacDat", field18);
			mapTongSo.put("hanhChinhToChuc", field19);
			mapTongSo.put("hanhChinhCaNhan", field20);
			mapTongSo.put("chuyenCoQuanDieuTraVu", field21);
			mapTongSo.put("chuyenCoQuanDieuTraDoiTuong", field22);
			mapTongSo.put("ketQuaThucHienTienDaThu", field23);
			mapTongSo.put("ketQuaThucHienDatDaThu", field24);
			mapTongSo.put("ketQuaThucHienToChuc", field25);
			mapTongSo.put("ketQuaThucHienCaNhan", field26);
			mapTongSo.put("ketQuaThucHienVu", field27);
			mapTongSo.put("ketQuaThucHienDoiTuong", field28);
			mapTongSo.put("ghiChu", "");
			
			map.put("tongCongs", mapTongSo);
			map.put("maSos", maSos);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraLai/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả thanh tra lại", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelKetQuaThanhTraLai(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis) throws IOException {

		try {
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();

			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			if (year == null) {
				year = Utils.localDateTimeNow().getYear();
			}
			
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			
			BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTra(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllCuocThanhTraKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraKyTruoc(loaiKy, quy, year, month, tuNgay, denNgay);
			
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null) {
					for (CoQuanQuanLy dv : listDonVis) {
						CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
						if (coQuan != null) {
							list.add(coQuan);
						}
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null) {
					if (listCapDonVis.size() > 0) {
						List<Long> thamSos = new ArrayList<Long>();
						thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

						for (CapCoQuanQuanLy cdv : listCapDonVis) {
							CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
							if (capDonVi != null) {
								listCapCQs.add(capDonVi.getId());
							}
						}
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			}
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllCuocThanhTraLai = predAllCuocThanhTra;
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("0", cq.getTen());
				// Get cuoc thanh tra lai
				predAllCuocThanhTraLai = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraLai(predAllCuocThanhTraLai
								.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())));
				Long tongSoCuocThanhTraLai = Long
						.valueOf(((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraLai)).size());
				BooleanExpression predAllCuocThanhTraLaiTrongKy = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraLai(predAllCuocThanhTraTrongKy
								.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())));
				BooleanExpression predAllCuocThanhTraLaiKyTruoc = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraLai(predAllCuocThanhTraKyTruoc
								.and(QCuocThanhTra.cuocThanhTra.donViChuTri.id.eq(cq.getId())));

				Long tongSoCuocThanhTrongKy = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraLaiTrongKy)).size());
				Long tongSoCuocThanhKyTruoc = Long.valueOf(
						((List<CuocThanhTra>) cuocThanhTraRepo.findAll(predAllCuocThanhTraLaiKyTruoc)).size());

				mapMaSo.put("1", tongSoCuocThanhTraLai);
				mapMaSo.put("2", tongSoCuocThanhKyTruoc);
				mapMaSo.put("3", tongSoCuocThanhTrongKy);
				Long tongSoCuocThanhTraKetThucTrucTiep = thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(
						predAllCuocThanhTraLai, TienDoThanhTraEnum.KET_THUC_THANH_TRA_TRUC_TIEP, cuocThanhTraRepo);
				Long tongSoCuocThanhTraDaBanHanhKetLuan = thongKeTongHopThanhTraService.getCuocThanhTraTheoTienDo(
						predAllCuocThanhTraLai, TienDoThanhTraEnum.DA_BAN_HANH_KET_LUAN, cuocThanhTraRepo);
				mapMaSo.put("4", tongSoCuocThanhTraKetThucTrucTiep);
				mapMaSo.put("5", tongSoCuocThanhTraDaBanHanhKetLuan);

				mapMaSo.put("6", thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(predAllCuocThanhTraLai,
						CanCuThanhTraLaiEnum.VI_PHAM_TRINH_TU, cuocThanhTraRepo));
				mapMaSo.put("7", thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(predAllCuocThanhTraLai,
						CanCuThanhTraLaiEnum.KET_LUAN_KHONG_PHU_HOP, cuocThanhTraRepo));
				mapMaSo.put("8", thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(predAllCuocThanhTraLai,
						CanCuThanhTraLaiEnum.SAI_LAM_AP_DUNG_PHAP_LUAT, cuocThanhTraRepo));
				mapMaSo.put("9", thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(predAllCuocThanhTraLai,
						CanCuThanhTraLaiEnum.CO_Y_LAM_SAI_LECH_HO_SO, cuocThanhTraRepo));
				mapMaSo.put("10", thongKeTongHopThanhTraService.getCuocThanhTraTheoCanCu(predAllCuocThanhTraLai,
						CanCuThanhTraLaiEnum.VI_PHAM_NGHIEM_TRONG, cuocThanhTraRepo));

				// Dem so don vi duoc thanh tra
				Long tongSoDonViDuocThanhTra = thongKeTongHopThanhTraService
						.getSoDonViDuocThanhTra(predAllCuocThanhTraLai, cuocThanhTraRepo);
				BooleanExpression predAllCuocThanhTraCoQuanViPham = (BooleanExpression) thongKeTongHopThanhTraService
						.predicateFindCuocThanhTraCoViPham(predAllCuocThanhTraLai, cuocThanhTraRepo);
				Long tongSoDonViCoViPham = thongKeTongHopThanhTraService
						.getSoDonViDuocThanhTraCoViPham(predAllCuocThanhTraLai, cuocThanhTraRepo);
				mapMaSo.put("11", tongSoDonViDuocThanhTra);
				mapMaSo.put("12", tongSoDonViCoViPham);

				mapMaSo.put("13", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "TONG_VI_PHAM", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("14", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "TONG_VI_PHAM", "DAT", cuocThanhTraRepo));
				mapMaSo.put("15", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_THU_HOI", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("16", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_THU_HOI", "DAT", cuocThanhTraRepo));
				mapMaSo.put("17", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_KHAC", "TIEN", cuocThanhTraRepo));
				mapMaSo.put("18", thongKeTongHopThanhTraService.getTienDatTheoViPham(
						predAllCuocThanhTraCoQuanViPham, "KIEN_NGHI_KHAC", "DAT", cuocThanhTraRepo));

				mapMaSo.put("19", thongKeTongHopThanhTraService
						.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuanViPham, "TO_CHUC", cuocThanhTraRepo));
				mapMaSo.put("20", thongKeTongHopThanhTraService
						.getKienNghiXuLyHanhChinh(predAllCuocThanhTraCoQuanViPham, "CA_NHAN", cuocThanhTraRepo));
				mapMaSo.put("21", thongKeTongHopThanhTraService.getKienNghiXuLyCCQDT(predAllCuocThanhTraLai, "VU",
						cuocThanhTraRepo));
				mapMaSo.put("22", thongKeTongHopThanhTraService.getKienNghiXuLyCCQDT(predAllCuocThanhTraLai,
						"DOI_TUONG", cuocThanhTraRepo));

				mapMaSo.put("23", 0);
				mapMaSo.put("24", 0);
				mapMaSo.put("25", 0);
				mapMaSo.put("26", 0);
				mapMaSo.put("27", 0);
				mapMaSo.put("28", 0);
				mapMaSo.put("29", 0);
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}
			
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopKetQuaThanhTraLai(response, "DanhSachTongHopKetQuaThanhTraLai", "sheetName", maSos,
					tuNgay, denNgay, "Thống kê đối với tất cả các cuộc thanh tra chọn LOẠI HÌNH là THANH TRA LẠI");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaPhatHienThamNhungQuaThanhTra")
	@ApiOperation(value = "Tổng hợp kết quả thanh tra lại", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaPhatHienThamNhungQuaThanhTra(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "loaiKy", required = true) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			PersistentEntityResourceAssembler eass) {
		try {

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Long donViXuLy = Long
					.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());

			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			
			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);

			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			if (year == null) {
				year = Utils.localDateTimeNow().getYear();
			}
			
			Long field1 = 0L;
			Long field2 = 0L;
			Long field3 = 0L;
			Long field4 = 0L;
			Long field5 = 0L;
			Long field6 = 0L;
			Long field7 = 0L;
			Long field8 = 0L;
			Long field9 = 0L;
			Long field10 = 0L;
			Long field11 = 0L;
			Long field12 = 0L;
			Long field13 = 0L;
			Long field14 = 0L;
			Long field15 = 0L;
			Long field16 = 0L;
			Long field17 = 0L;
			Long field18 = 0L;
			Long field19 = 0L;
			Long field20 = 0L;
			Long field21 = 0L;
			Long field22 = 0L;
			Long field23 = 0L;
			Long field24 = 0L;
			
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null && listDonVis.size() > 0) {
					for (CoQuanQuanLy dv : listDonVis) {
						if (dv != null) {
							CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
							if (coQuan != null) {
								list.add(coQuan);
							}
						}
					}
					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null && listCapDonVis.size() > 0) {
					List<Long> thamSos = new ArrayList<Long>();
					thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

					for (CapCoQuanQuanLy cdv : listCapDonVis) {
						CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
						if (capDonVi != null) {
							listCapCQs.add(capDonVi.getId());
						}
					}
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));

					if (list.size() == 0) {
						list.add(coQuanQuanLyRepo.findOne(donViXuLy));
					}
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
							.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
				}
				donVis.addAll(list);
			}
			
			BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllThamNhungQuaThanhTra = predAllCuocThanhTra
						.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(cq));

				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				mapMaSo.put("donVi", mapDonVi);
				
				// So vu tham nhung
				Long soVuThamNhung = thongKeTongHopThanhTraService
						.getSoVuNguoiThamNhung(predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "VU");
				field1 += soVuThamNhung;
				mapMaSo.put("soVu", soVuThamNhung);

				Long soNguoiThamNhung = thongKeTongHopThanhTraService
						.getSoVuNguoiThamNhung(predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "NGUOI");
				field2 += soNguoiThamNhung;
				mapMaSo.put("soNguoi", soNguoiThamNhung);
				
				// Tai san tham nhung
				Long taiSanThamNhungTongTien = thongKeTongHopThanhTraService.getGiaTriThamNhung(
						predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "TAI_SAN_THAM_NHUNG", "TONG_TIEN");
				field3 += taiSanThamNhungTongTien;
				mapMaSo.put("taiSanThamNhungTongTien", taiSanThamNhungTongTien);
				Long taiSanThamNhungTien = thongKeTongHopThanhTraService.getGiaTriThamNhung(
						predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "TAI_SAN_THAM_NHUNG", "TIEN");
				field4 += taiSanThamNhungTien;
				mapMaSo.put("taiSanThamNhungTien", taiSanThamNhungTien);
				Long taiSanThamNhungTaiSanKhac = thongKeTongHopThanhTraService.getGiaTriThamNhung(
						predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "TAI_SAN_THAM_NHUNG", "TAI_SAN_KHAC");
				field5 += taiSanThamNhungTaiSanKhac;
				mapMaSo.put("taiSanThamNhungTaiSanKhac", taiSanThamNhungTaiSanKhac);
				Long taiSanThamNhungDat = thongKeTongHopThanhTraService.getGiaTriThamNhung(
						predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "TAI_SAN_THAM_NHUNG", "DAT");
				field6 += taiSanThamNhungDat;
				mapMaSo.put("taiSanThamNhungDat", taiSanThamNhungDat);

				// Kien nghi thu hoi
				Long kienNghiThuHoiTongTien = thongKeTongHopThanhTraService.getGiaTriThamNhung(
						predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "KIEN_NGHI_THU_HOI", "TONG_TIEN");
				field7 += kienNghiThuHoiTongTien;
				mapMaSo.put("kienNghiThuHoiTongTien", kienNghiThuHoiTongTien);
				Long kienNghiThuHoiTien = thongKeTongHopThanhTraService.getGiaTriThamNhung(
						predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "KIEN_NGHI_THU_HOI", "TIEN");
				field8 += kienNghiThuHoiTien;
				mapMaSo.put("kienNghiThuHoiTien", kienNghiThuHoiTien);
				Long kienNghiThuHoiTaiSanKhac = thongKeTongHopThanhTraService.getGiaTriThamNhung(
						predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "KIEN_NGHI_THU_HOI", "TAI_SAN_KHAC");
				field9 += kienNghiThuHoiTaiSanKhac;
				mapMaSo.put("kienNghiThuHoiTaiSanKhac", kienNghiThuHoiTaiSanKhac);
				Long kienNghiThuHoiDat = thongKeTongHopThanhTraService.getGiaTriThamNhung(
						predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "KIEN_NGHI_THU_HOI", "DAT");
				field10 += kienNghiThuHoiDat;
				mapMaSo.put("kienNghiThuHoiDat", kienNghiThuHoiDat);

				// Da thu
				Long daThuTongTien = thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "DA_THU", "TONG_TIEN");
				field11 += daThuTongTien;
				mapMaSo.put("daThuTongTien", daThuTongTien);
				Long daThuTien = thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "DA_THU", "TIEN");
				field12 += daThuTien;
				mapMaSo.put("daThuTien", daThuTien);
				Long daThuTaiSanKhac = thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "DA_THU", "TAI_SAN_KHAC");
				field13 += daThuTaiSanKhac;
				mapMaSo.put("daThuTaiSanKhac", daThuTaiSanKhac);
				Long daThuDat = thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "DA_THU", "DAT");
				field14 += daThuDat;
				mapMaSo.put("daThuDat", daThuDat);

				// Kien nghi xu ly
				Long hanhChinhToChuc = thongKeTongHopThanhTraService.getSoKienNghiXuLyHanhChinhThamNhung(
						predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "TO_CHUC");
				field15 += hanhChinhToChuc;
				mapMaSo.put("hanhChinhToChuc", hanhChinhToChuc);
				Long hanhChinhCaNhan = thongKeTongHopThanhTraService.getSoKienNghiXuLyHanhChinhThamNhung(
						predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "CA_NHAN");
				field16 += hanhChinhCaNhan;
				mapMaSo.put("hanhChinhCaNhan", hanhChinhCaNhan);

				Long chuyenCoQuanDieuTraVu = thongKeTongHopThanhTraService
						.getSoChuyenCoQuanDieuTraThamNhung(predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "VU");
				field17 += chuyenCoQuanDieuTraVu;
				mapMaSo.put("chuyenCoQuanDieuTraVu", chuyenCoQuanDieuTraVu);
				Long chuyenCoQuanDieuTraDoiTuong = thongKeTongHopThanhTraService.getSoChuyenCoQuanDieuTraThamNhung(
						predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "DOI_TUONG");
				field18 += chuyenCoQuanDieuTraDoiTuong;
				mapMaSo.put("chuyenCoQuanDieuTraDoiTuong", chuyenCoQuanDieuTraDoiTuong);

				// Da xu ly tham nhung
				field19 += 0;
				mapMaSo.put("daXuLyHanhChinhToChuc", 0);
				
				field20 += 0;
				mapMaSo.put("daXuLyHanhChinhCaNhan", 0);

				field21 += 0;
				mapMaSo.put("daKhoiToVu", 0);
				field22 += 0;
				mapMaSo.put("daKhoiToDoiTuong", 0);
				
				// XuLyTrachNghiemNguoiDungDau
				field23 += 0;
				mapMaSo.put("xuLyTrachNhiemNguoiDungDauKienNghi", 0L);
				field24 += 0;
				mapMaSo.put("xuLyTrachNhiemNguoiDungDauDaXuLy", 0);
				
				mapMaSo.put("ghiChu", "");

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
				mapDonVi = new HashMap<String, Object>();
			}
			
			// Map tong so
			Map<String, Object> mapTongSo = new HashMap<String, Object>();			
			mapTongSo.put("soVu", field1);
			mapTongSo.put("soNguoi", field2);
			mapTongSo.put("taiSanThamNhungTongTien", field3);
			mapTongSo.put("taiSanThamNhungTien", field4);
			mapTongSo.put("taiSanThamNhungTaiSanKhac", field5);
			mapTongSo.put("taiSanThamNhungDat", field6);
			mapTongSo.put("kienNghiThuHoiTongTien", field7);
			mapTongSo.put("kienNghiThuHoiTien", field8);
			mapTongSo.put("kienNghiThuHoiTaiSanKhac", field9);
			mapTongSo.put("kienNghiThuHoiDat", field10);
			mapTongSo.put("daThuTongTien", field11);
			mapTongSo.put("daThuTien", field12);
			mapTongSo.put("daThuTaiSanKhac", field13);
			mapTongSo.put("daThuDat", field14);
			mapTongSo.put("hanhChinhToChuc", field15);
			mapTongSo.put("hanhChinhCaNhan", field16);
			mapTongSo.put("chuyenCoQuanDieuTraVu", field17);
			mapTongSo.put("chuyenCoQuanDieuTraDoiTuong", field18);
			mapTongSo.put("daXuLyHanhChinhToChuc", field19);
			mapTongSo.put("daXuLyHanhChinhCaNhan", field20);
			mapTongSo.put("daKhoiToVu", field21);
			mapTongSo.put("daKhoiToDoiTuong", field22);
			mapTongSo.put("xuLyTrachNhiemNguoiDungDauKienNghi", field23);
			mapTongSo.put("xuLyTrachNhiemNguoiDungDauDaXuLy", field24);
			mapTongSo.put("ghiChu", "");

			map.put("tongCongs", mapTongSo);
			map.put("maSos", maSos);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaPhatHienThamNhungQuaThanhTra/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả thanh tra lại", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelKetQuaPhatHienThamNhungQuaThanhTra(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			@RequestParam(value = "hinhThucThongKe", required = true) String hinhThucThongKe,
			@RequestParam(value = "listCapDonVis", required = false) List<CapCoQuanQuanLy> listCapDonVis,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis) throws IOException {

		try {
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			
			HinhThucThongKeEnum hinhThucTK = HinhThucThongKeEnum.valueOf(hinhThucThongKe);
			ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
			
			if (month == null) {
				month = Utils.localDateTimeNow().getMonthValue();
			}
			if (loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.THEO_QUY)) {
				if (quy == null) {
					quy = Utils.getQuyHienTai();
				}
			}
			if (year == null) {
				year = Utils.localDateTimeNow().getYear();
			}
			
			BooleanExpression predAllCuocThanhTra = (BooleanExpression) thongKeTongHopThanhTraService
					.predicateFindAllCuocThanhTraTrongKy(loaiKy, quy, year, month, tuNgay, denNgay);
			
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (hinhThucTK.equals(HinhThucThongKeEnum.DON_VI)) {
				if (listDonVis != null) {
					for (CoQuanQuanLy dv : listDonVis) {
						CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
						if (coQuan != null) {
							list.add(coQuan);
						}
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			} else {
				List<Long> listCapCQs = new ArrayList<Long>();
				if (listCapDonVis != null) {
					if (listCapDonVis.size() > 0) {
						List<Long> thamSos = new ArrayList<Long>();
						thamSos.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						thamSos.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));

						for (CapCoQuanQuanLy cdv : listCapDonVis) {
							CapCoQuanQuanLy capDonVi = capCoQuanQuanLyRepo.findOne(cdv.getId());
							if (capDonVi != null) {
								listCapCQs.add(capDonVi.getId());
							}
						}
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predFindDonViByCapCoQuanQuanLysTKBC(listCapCQs, thamSos)));
					}
				} else {
					if (donViId != null && donViId > 0) {
						list.add(coQuanQuanLyRepo.findOne(donViId));
					} else {
						List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
						capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
						list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo
								.findAll(coQuanQuanLyService.predicateFindDonViByCapCoQuanQuanLys(capCoQuanQuanLyIds)));
					}
				}
				donVis.addAll(list);
			}
			
			for (CoQuanQuanLy cq : donVis) {
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("0", cq.getTen());
				// Get cuoc thanh tra lai
				BooleanExpression predAllThamNhungQuaThanhTra = predAllCuocThanhTra
						.and(QCuocThanhTra.cuocThanhTra.donViChuTri.eq(cq));

				mapMaSo.put("1", thongKeTongHopThanhTraService.getSoVuNguoiThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "VU"));
				mapMaSo.put("2", thongKeTongHopThanhTraService.getSoVuNguoiThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "NGUOI"));
				mapMaSo.put("3", thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "TAI_SAN_THAM_NHUNG", "TONG_TIEN"));
				mapMaSo.put("4", thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "TAI_SAN_THAM_NHUNG", "TIEN"));
				mapMaSo.put("5", thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "TAI_SAN_THAM_NHUNG", "DAT"));
				mapMaSo.put("6", thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "TAI_SAN_THAM_NHUNG", "TAI_SAN_KHAC"));
				mapMaSo.put("7", thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "KIEN_NGHI_THU_HOI", "TONG_TIEN"));
				mapMaSo.put("8", thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "KIEN_NGHI_THU_HOI", "TIEN"));
				mapMaSo.put("9", thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "KIEN_NGHI_THU_HOI", "DAT"));
				mapMaSo.put("10", thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "KIEN_NGHI_THU_HOI", "TAI_SAN_KHAC"));
				mapMaSo.put("11", thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "DA_THU", "TONG_TIEN"));
				mapMaSo.put("12", thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "DA_THU", "TIEN"));
				mapMaSo.put("13", thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "DA_THU", "DAT"));
				mapMaSo.put("14", thongKeTongHopThanhTraService.getGiaTriThamNhung(predAllThamNhungQuaThanhTra,
						cuocThanhTraRepo, "DA_THU", "TAI_SAN_KHAC"));
				mapMaSo.put("15", thongKeTongHopThanhTraService.getSoKienNghiXuLyHanhChinhThamNhung(
						predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "TO_CHUC"));
				mapMaSo.put("16", thongKeTongHopThanhTraService.getSoKienNghiXuLyHanhChinhThamNhung(
						predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "CA_NHAN"));
				mapMaSo.put("17", thongKeTongHopThanhTraService
						.getSoChuyenCoQuanDieuTraThamNhung(predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "VU"));
				mapMaSo.put("18", thongKeTongHopThanhTraService.getSoChuyenCoQuanDieuTraThamNhung(
						predAllThamNhungQuaThanhTra, cuocThanhTraRepo, "DOI_TUONG"));
				mapMaSo.put("19", 0);
				mapMaSo.put("20", 0);
				mapMaSo.put("21", 0);
				mapMaSo.put("22", 0);
				mapMaSo.put("23", 0);
				mapMaSo.put("24", 0);
				mapMaSo.put("25", 0);

				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}

			map.put("maSos", maSos);
			ExcelUtil.exportTongHopKetQuaPhatHienThamNhungQuaThanhTra(response,
					"DanhSachTongHopKetQuaPhatHienThamNhungQuaThanhTra", "sheetName", maSos, tuNgay, denNgay,
					"TỔNG HỢP KẾT QUẢ PHÁT HIỆN, XỬ LÝ THAM NHŨNG PHÁT HIỆN QUA CÔNG TÁC CỦA NGÀNH THANH TRA");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
}
