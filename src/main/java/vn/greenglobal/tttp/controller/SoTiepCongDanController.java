 package vn.greenglobal.tttp.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.DoiTuongThayDoiEnum;
import vn.greenglobal.tttp.enums.FlowStateEnum;
import vn.greenglobal.tttp.enums.HuongGiaiQuyetTCDEnum;
import vn.greenglobal.tttp.enums.HuongXuLyTCDEnum;
import vn.greenglobal.tttp.enums.LoaiTiepDanEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.QuaTrinhXuLyEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.enums.TinhTrangGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.TrangThaiYeuCauGapLanhDaoEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CoQuanToChucTiepDan;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.LichSuQuaTrinhXuLy;
import vn.greenglobal.tttp.model.LichSuThayDoi;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.model.PropertyChangeObject;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.model.medial.Medial_UpdateTrangThaiYCGLD;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CoQuanToChucTiepDanRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.LichSuQuaTrinhXuLyRepository;
import vn.greenglobal.tttp.repository.ProcessRepository;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.repository.StateRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.repository.ThongTinGiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.service.CongChucService;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.service.GiaiQuyetDonService;
import vn.greenglobal.tttp.service.LichSuQuaTrinhXuLyService;
import vn.greenglobal.tttp.service.LichSuThayDoiService;
import vn.greenglobal.tttp.service.ProcessService;
import vn.greenglobal.tttp.service.SoTiepCongDanService;
import vn.greenglobal.tttp.service.StateService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.service.ThongTinGiaiQuyetDonService;
import vn.greenglobal.tttp.service.TransitionService;
import vn.greenglobal.tttp.util.ExcelUtil;
import vn.greenglobal.tttp.util.Utils;
import vn.greenglobal.tttp.util.WordUtil;

@RestController
@RepositoryRestController
@Api(value = "soTiepCongDans", description = "Sổ tiếp công dân")
public class SoTiepCongDanController extends TttpController<SoTiepCongDan> {

	@Autowired
	private SoTiepCongDanRepository repo;

	@Autowired
	private SoTiepCongDanService soTiepCongDanService;

	@Autowired
	private DonRepository repoDon;
	
	@Autowired
	private StateRepository repoState;
	
	@Autowired
	private ThongTinGiaiQuyetDonRepository repoThongTinGiaiQuyetDon;
	
	@Autowired
	private ThongTinGiaiQuyetDonService thongTinGiaiQuyetDonService;
	
	@Autowired
	private StateService stateService;

	@Autowired
	private DonService donService;
	
	@Autowired
	private CongChucRepository repoCongChuc;
	
	@Autowired
	private PagedResourcesAssembler<Don> assemblerDon;

	@Autowired
	private CoQuanToChucTiepDanRepository repoCoQuanToChucTiepDan;
	
	@Autowired
	private DonCongDanRepository repoDonCongDan;
	
	@Autowired
	private LichSuQuaTrinhXuLyRepository lichSuQuaTrinhXuLyRepo;
	
	@Autowired
	private LichSuQuaTrinhXuLyService lichSuQuaTrinhXuLyService;
	
	@Autowired
	private GiaiQuyetDonService giaiQuyetDonService;
	
	@Autowired
	private CoQuanQuanLyRepository repoCoQuanQuanLy;
	
	@Autowired
	private TransitionRepository repoTransition;
	
	@Autowired
	private TransitionService transitionService;
	
	@Autowired
	private ProcessRepository repoProcess;
	
	@Autowired
	private ProcessService processService;
	
	@Autowired
	private CongChucService congChucService;

	@Autowired
	private ThamSoRepository thamSoRepository;
	
	@Autowired
	private ThamSoService thamSoService;

	@Autowired
	private LichSuThayDoiService lichSuThayDoiService;
	
	public SoTiepCongDanController(BaseRepository<SoTiepCongDan, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans")
	@ApiOperation(value = "Lấy danh sách Tiếp Công Dân", position = 1, produces = MediaType.APPLICATION_JSON_VALUE, response = SoTiepCongDan.class)
	public @ResponseBody Object getDanhSachTiepCongDans(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "phanLoaiDon", required = false) String phanLoaiDon,
			@RequestParam(value = "huongXuLy", required = false) String huongXuLy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "loaiTiepCongDan", required = false) String loaiTiepCongDan,
			@RequestParam(value = "lanhDaoId", required = false) Long lanhDaoId,
			@RequestParam(value = "tenNguoiDungDon", required = false) String tenNguoiDungDon,
			@RequestParam(value = "tenLanhDao", required = false) String tenLanhDao,
			@RequestParam(value = "tinhTrangXuLy", required = false) String tinhTrangXuLy,
			@RequestParam(value = "ketQuaTiepDan", required = false) String ketQuaTiepDan,
			PersistentEntityResourceAssembler eass) {
		
		try {
			Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_LIETKE) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(), ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			Page<SoTiepCongDan> page = repo.findAll(soTiepCongDanService.predicateFindAllTCD(tuKhoa, phanLoaiDon, huongXuLy,
					tuNgay, denNgay, loaiTiepCongDan, donViId, lanhDaoId, tenLanhDao, tenNguoiDungDon, tinhTrangXuLy, ketQuaTiepDan, congChucService, repoCongChuc, repoDonCongDan), pageable);

			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans/{id}")
	@ApiOperation(value = "Lấy Tiếp Công Dân theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy lượt Tiếp Công Dân thành công", response = SoTiepCongDan.class) })
	public ResponseEntity<Object> getSoTiepCongDans(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			SoTiepCongDan soTiepCongDan = repo.findOne(soTiepCongDanService.predicateFindOne(id));
			if (soTiepCongDan == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(eass.toFullResource(soTiepCongDan), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/soTiepCongDans")
	@ApiOperation(value = "Thêm mới Sổ Tiếp Công Dân", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Sổ Tiếp Công Dân thành công", response = SoTiepCongDan.class),
			@ApiResponse(code = 201, message = "Thêm mới Sổ Tiếp Công Dân thành công", response = SoTiepCongDan.class) })
	public ResponseEntity<Object> createSoTiepCongDan(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody SoTiepCongDan soTiepCongDan, PersistentEntityResourceAssembler eass) {
		
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_THEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			if (soTiepCongDan != null && !soTiepCongDan.getCoQuanToChucTiepDans().isEmpty()) {
				for (CoQuanToChucTiepDan coQuanToChucTiepDan : soTiepCongDan.getCoQuanToChucTiepDans()) {
					Utils.save(repoCoQuanToChucTiepDan, coQuanToChucTiepDan, congChucId);
				}
			}
			boolean flagChuyenDonViKiemTra = false;
			Don don = repoDon.findOne(soTiepCongDan.getDon().getId());
			CoQuanQuanLy donVi = repoCoQuanQuanLy.findOne(donViId);
			don.setDonViTiepDan(donVi);
			if (LoaiTiepDanEnum.DINH_KY.equals(soTiepCongDan.getLoaiTiepDan())) {
				don.setThanhLapTiepDanGapLanhDao(true);
				don.setTrangThaiYeuCauGapLanhDao(TrangThaiYeuCauGapLanhDaoEnum.DONG_Y);
				soTiepCongDan.setHuongGiaiQuyetTCDLanhDao(HuongGiaiQuyetTCDEnum.KHOI_TAO);
			}
			if (LoaiTiepDanEnum.DINH_KY.equals(soTiepCongDan.getLoaiTiepDan())
					|| LoaiTiepDanEnum.DOT_XUAT.equals(soTiepCongDan.getLoaiTiepDan())) {
				soTiepCongDan.setHuongXuLy(HuongXuLyTCDEnum.KHOI_TAO);
				if (soTiepCongDan.getHuongGiaiQuyetTCDLanhDao() == null) {
					return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.HUONGGIAIQUYET_REQUIRED.name(),
							ApiErrorEnum.HUONGGIAIQUYET_REQUIRED.getText(), ApiErrorEnum.HUONGGIAIQUYET_REQUIRED.getText());
				}
				if (HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET.equals(soTiepCongDan.getHuongGiaiQuyetTCDLanhDao())) {
					if (soTiepCongDan.getDonViChuTri() == null) {
						return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DONVICHUTRI_REQUIRED.name(),
								ApiErrorEnum.DONVICHUTRI_REQUIRED.getText(), ApiErrorEnum.DONVICHUTRI_REQUIRED.getText());
					}
					if (soTiepCongDan.getyKienXuLy() == null || "".equals(soTiepCongDan.getyKienXuLy())
							|| StringUtils.isBlank(soTiepCongDan.getyKienXuLy().trim())) {
						return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_REQUIRED.name(),
								ApiErrorEnum.DATA_REQUIRED.getText(), ApiErrorEnum.DATA_REQUIRED.getText());
					}
					if (soTiepCongDan.isChuyenDonViKiemTra()) {
						flagChuyenDonViKiemTra = true;
						soTiepCongDan.setTinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum.GIAO_DON_VI_KIEM_TRA_VA_DE_XUAT);
					}
				}
				if (soTiepCongDan.isHoanThanhTCDLanhDao()) {
					soTiepCongDan.setTinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum.HOAN_THANH);
					don.setDaXuLy(true);
					don.setDaGiaiQuyet(true);
				} else {
					if (soTiepCongDan.getTinhTrangXuLyTCDLanhDao() == null) {
						soTiepCongDan.setTinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum.DANG_XU_LY);
					}
					don.setDaXuLy(true);
				}
			} else if (LoaiTiepDanEnum.THUONG_XUYEN.equals(soTiepCongDan.getLoaiTiepDan())) {
				int soLuotTiep = don.getTongSoLuotTCD();
				soTiepCongDan.setSoThuTuLuotTiep(soLuotTiep + 1);
				don.setTongSoLuotTCD(soLuotTiep + 1);
				if (HuongXuLyTCDEnum.YEU_CAU_GAP_LANH_DAO.equals(soTiepCongDan.getHuongXuLy())
						&& soTiepCongDan.isGuiYeuCauGapLanhDao()) {
					don.setTrangThaiYeuCauGapLanhDao(TrangThaiYeuCauGapLanhDaoEnum.CHO_XIN_Y_KIEN);
					don.setYeuCauGapTrucTiepLanhDao(true);
					don.setNgayLapDonGapLanhDaoTmp(Utils.localDateTimeNow());
				} 
//				else {
//					don.setYeuCauGapTrucTiepLanhDao(false);
//					don.setNgayLapDonGapLanhDaoTmp(null);
//				}
			}
			
			Transition transitionTTXM = null;
			List<Transition> listTransitionHaveBegin = new ArrayList<Transition>();
			if (flagChuyenDonViKiemTra) {
				Predicate predicate = processService.predicateFindAllByDonVi(soTiepCongDan.getDonViChuTri(), ProcessTypeEnum.KIEM_TRA_DE_XUAT);
				List<Process> listProcess = (List<Process>) repoProcess.findAll(predicate);
				if (listProcess.size() < 1) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_GQD_NOT_FOUND.name(),
							ApiErrorEnum.PROCESS_GQD_NOT_FOUND.getText(), ApiErrorEnum.PROCESS_GQD_NOT_FOUND.getText());
				}			
				for (Process processFromList : listProcess) {
					transitionTTXM = repoTransition.findOne(transitionService.predicateFindFromCurrent(FlowStateEnum.BAT_DAU, processFromList));
					if (transitionTTXM != null) {
						listTransitionHaveBegin.add(transitionTTXM);
					}
				}
				if (listTransitionHaveBegin.size() < 1) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_GQD_INVALID.name(),
							ApiErrorEnum.TRANSITION_GQD_INVALID.getText(), ApiErrorEnum.TRANSITION_GQD_INVALID.getText());
				}	
			}

			ResponseEntity<Object> output = soTiepCongDanService.doSave(soTiepCongDan, congChucId, eass, HttpStatus.CREATED);
			if (output.getStatusCode().equals(HttpStatus.CREATED)) {
				if (flagChuyenDonViKiemTra) {								
					State beginState = repoState.findOne(stateService.predicateFindByType(FlowStateEnum.BAT_DAU));
					don.setProcessType(ProcessTypeEnum.KIEM_TRA_DE_XUAT);					
					don.setCurrentState(beginState);
					don.setThanhLapDon(true);
//					don.setDonViXuLyGiaiQuyet(soTiepCongDan.getDonViChuTri());
//					don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DANG_GIAI_QUYET);
					don.setDonViKiemTraDeXuat(soTiepCongDan.getDonViChuTri());
					don.setTrangThaiKTDX(TrangThaiDonEnum.DANG_GIAI_QUYET);
					don.setNgayTiepNhan(Utils.localDateTimeNow());
					don.setLanhDaoDuyet(true);
					don.setDangGiaoKTDX(true);
					
					ThongTinGiaiQuyetDon thongTinGiaiQuyetDon = repoThongTinGiaiQuyetDon.findOne(thongTinGiaiQuyetDonService.predicateFindByDon(don.getId()));
					if (thongTinGiaiQuyetDon == null) {
						thongTinGiaiQuyetDon = new ThongTinGiaiQuyetDon();
						thongTinGiaiQuyetDon.setDon(don);
						thongTinGiaiQuyetDon.setNgayBatDauKTDX(Utils.localDateTimeNow());
					}
//					thongTinGiaiQuyetDon.setNgayBatDauGiaiQuyet(Utils.localDateTimeNow());
//					ThamSo thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_GIAI_QUYET_DON_MAC_DINH"));
//					Long soNgayGiaiQuyetMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 45L;
//					LocalDateTime ngayHetHanGiaiQuyet = Utils.convertNumberToLocalDateTimeGoc(Utils.localDateTimeNow(), soNgayGiaiQuyetMacDinh);
//					thongTinGiaiQuyetDon.setNgayHetHanGiaiQuyet(ngayHetHanGiaiQuyet);
					if (soTiepCongDan.getNgayBaoCaoKetQua() != null) { 
						thongTinGiaiQuyetDon.setNgayHetHanKTDX(soTiepCongDan.getNgayBaoCaoKetQua());
					} else {
						ThamSo thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_GIAI_QUYET_DON_MAC_DINH"));
						Long soNgayKTDXMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 45L;
						LocalDateTime ngayHetHanKTDX = Utils.convertNumberToLocalDateTimeGoc(Utils.localDateTimeNow(), soNgayKTDXMacDinh);
						thongTinGiaiQuyetDon.setNgayHetHanKTDX(ngayHetHanKTDX);
					}
					//thongTinGiaiQuyetDon.setDonViThamTraXacMinh(soTiepCongDan.getDonViChuTri());
					thongTinGiaiQuyetDonService.save(thongTinGiaiQuyetDon, congChucId);
					GiaiQuyetDon giaiQuyetDon = new GiaiQuyetDon();
					giaiQuyetDon.setThongTinGiaiQuyetDon(thongTinGiaiQuyetDon);
					giaiQuyetDon.setChucVu(listTransitionHaveBegin.size() == 1 || listTransitionHaveBegin.size() == 2? listTransitionHaveBegin.get(0).getProcess().getVaiTro().getLoaiVaiTro() : null);
					giaiQuyetDon.setDonViGiaiQuyet(soTiepCongDan.getDonViChuTri());
					giaiQuyetDon.setDonViChuyenDon(soTiepCongDan.getDonViTiepDan());
					giaiQuyetDon.setSoTiepCongDan(soTiepCongDan);
					giaiQuyetDon.setChucVu2(listTransitionHaveBegin.size() == 2? listTransitionHaveBegin.get(1).getProcess().getVaiTro().getLoaiVaiTro() : null);
					giaiQuyetDon.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
					giaiQuyetDon.setThuTuThucHien(1);
					giaiQuyetDon.setDonChuyen(true);
					/*don.setDonViXuLyGiaiQuyet(soTiepCongDan.getDonViChuTri());*/
					giaiQuyetDonService.save(giaiQuyetDon, congChucId);
				}
				
				LichSuQuaTrinhXuLy lichSuQTXL = new LichSuQuaTrinhXuLy();
				lichSuQTXL.setDon(soTiepCongDan.getDon());
				lichSuQTXL.setNgayXuLy(Utils.localDateTimeNow());
				lichSuQTXL.setNguoiXuLy(repoCongChuc.findOne(congChucId));
				lichSuQTXL.setTen(QuaTrinhXuLyEnum.CHUYEN_DON_VI_KTDX.getText());
				lichSuQTXL.setNoiDung(soTiepCongDan.getNoiDungTiepCongDan());
				lichSuQTXL.setDonViXuLy(repoCoQuanQuanLy.findOne(donViId));
				
				if (LoaiTiepDanEnum.THUONG_XUYEN.equals(soTiepCongDan.getLoaiTiepDan())) {
					if (!HuongXuLyTCDEnum.YEU_CAU_GAP_LANH_DAO.equals(soTiepCongDan.getHuongXuLy())) {
						List<SoTiepCongDan> soTiepCongDanYCGLDs = new ArrayList<SoTiepCongDan>();
						soTiepCongDanYCGLDs.addAll(soTiepCongDanService
								.getCuocTiepDanDinhKyCuaLanhDaoTruoc(repo, soTiepCongDan.getDon().getId()));
						if (soTiepCongDanYCGLDs.size() == 0) {
							don.setYeuCauGapTrucTiepLanhDao(false);
							don.setNgayLapDonGapLanhDaoTmp(null);
							don.setTrangThaiYeuCauGapLanhDao(null);
						}
					}
				}
				
				donService.save(don, congChucId);
				
				int size = 0;
				size = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), lichSuQTXL.getDonViXuLy().getId());
				if (size == 0) {
					lichSuQTXL.setTen(QuaTrinhXuLyEnum.TIEP_CONG_DAN.getText());
					lichSuQTXL.setNoiDung("Tạo mới hồ sơ tiếp công dân");
					lichSuQTXL.setThuTuThucHien(0);
					if (StringUtils.isNoneBlank(soTiepCongDan.getNoiDungTiepCongDan())) { 
						lichSuQTXL.setNoiDung(soTiepCongDan.getNoiDungTiepCongDan());
					}
					lichSuQuaTrinhXuLyService.save(lichSuQTXL, congChucId);
				} 
				if (don.isThanhLapDon()) { 
					LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
					lichSuQTXLD.setDon(don);
					lichSuQTXLD.setNguoiXuLy(repoCongChuc.findOne(congChucId));
					lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
					lichSuQTXLD.setNoiDung("Tiếp nhận đơn và chuyển đơn sang bộ phận xử lý");
					lichSuQTXLD.setTen(QuaTrinhXuLyEnum.CHUYEN_XU_LY_DON.getText());
					lichSuQTXLD.setDonViXuLy(repoCoQuanQuanLy.findOne(donViId));
					lichSuQTXLD.setThuTuThucHien(1);
					lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
				}
			}
			return output;
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/soTiepCongDan/{id}")
	@ApiOperation(value = "Cập nhật Sổ Tiếp Công Dân", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Sổ Tiếp Công Dân thành công", response = SoTiepCongDan.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody SoTiepCongDan soTiepCongDan, PersistentEntityResourceAssembler eass) {
		
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			soTiepCongDan.setId(id);
			Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			
			for (CoQuanToChucTiepDan coQuanToChucTiepDan : soTiepCongDan.getCoQuanToChucTiepDans()) {
				Utils.save(repoCoQuanToChucTiepDan, coQuanToChucTiepDan, congChucId);
			}

			Don don = repoDon.findOne(soTiepCongDan.getDon().getId());
			SoTiepCongDan soTiepCongDanOld = repo.findOne(soTiepCongDan.getId());
			
			if (LoaiTiepDanEnum.DINH_KY.equals(soTiepCongDan.getLoaiTiepDan())) {
				don.setThanhLapTiepDanGapLanhDao(true);
				don.setTrangThaiYeuCauGapLanhDao(TrangThaiYeuCauGapLanhDaoEnum.DONG_Y);
			}

			if (LoaiTiepDanEnum.DINH_KY.equals(soTiepCongDan.getLoaiTiepDan())
					|| LoaiTiepDanEnum.DOT_XUAT.equals(soTiepCongDan.getLoaiTiepDan())) {
				soTiepCongDan.setHuongXuLy(HuongXuLyTCDEnum.KHOI_TAO);
				if (soTiepCongDan.getHuongGiaiQuyetTCDLanhDao() == null || HuongGiaiQuyetTCDEnum.KHOI_TAO.equals(soTiepCongDan.getHuongGiaiQuyetTCDLanhDao())) {
					return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.HUONGGIAIQUYET_REQUIRED.name(), ApiErrorEnum.HUONGGIAIQUYET_REQUIRED.getText(), ApiErrorEnum.HUONGGIAIQUYET_REQUIRED.getText());
				}
				if (soTiepCongDanOld.getTinhTrangXuLyTCDLanhDao() != null) { 
					soTiepCongDan.setTinhTrangXuLyTCDLanhDao(soTiepCongDanOld.getTinhTrangXuLyTCDLanhDao());
				} else { 
					soTiepCongDan.setTinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum.DANG_XU_LY);
				}
				if (HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET.equals(soTiepCongDan.getHuongGiaiQuyetTCDLanhDao())) {
					if (soTiepCongDan.getDonViChuTri() == null) {
						return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DONVICHUTRI_REQUIRED.name(),
								ApiErrorEnum.DONVICHUTRI_REQUIRED.getText(), ApiErrorEnum.DONVICHUTRI_REQUIRED.getText());
					}
					if (soTiepCongDan.getyKienXuLy() == null || "".equals(soTiepCongDan.getyKienXuLy())
							|| StringUtils.isBlank(soTiepCongDan.getyKienXuLy().trim())) {
						return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_REQUIRED.name(),
								ApiErrorEnum.DATA_REQUIRED.getText(), ApiErrorEnum.DATA_REQUIRED.getText());
					}
					if (soTiepCongDan.getDonViPhoiHops() != null && 
							soTiepCongDan.getDonViPhoiHops().size() > 0) {
						
					}
					
					Transition transitionTTXM = null;
					List<Transition> listTransitionHaveBegin = new ArrayList<Transition>();
					if (soTiepCongDan.isChuyenDonViKiemTra()) {
						Predicate predicate = processService.predicateFindAllByDonVi(soTiepCongDan.getDonViChuTri(), ProcessTypeEnum.KIEM_TRA_DE_XUAT);
						List<Process> listProcess = (List<Process>) repoProcess.findAll(predicate);
						if (listProcess.size() < 1) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_GQD_NOT_FOUND.name(),
									ApiErrorEnum.PROCESS_GQD_NOT_FOUND.getText(), ApiErrorEnum.PROCESS_GQD_NOT_FOUND.getText());
						}			
						for (Process processFromList : listProcess) {
							transitionTTXM = repoTransition.findOne(transitionService.predicateFindFromCurrent(FlowStateEnum.BAT_DAU, processFromList));
							if (transitionTTXM != null) {
								listTransitionHaveBegin.add(transitionTTXM);
							}
						}
						if (listTransitionHaveBegin.size() < 1) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_GQD_INVALID.name(),
									ApiErrorEnum.TRANSITION_GQD_INVALID.getText(), ApiErrorEnum.TRANSITION_GQD_INVALID.getText());
						}
						
						if (!HuongGiaiQuyetTCDEnum.GIAO_DON_VI_KIEM_TRA_VA_DE_XUAT.equals(soTiepCongDan.getTinhTrangXuLyTCDLanhDao())) {
							soTiepCongDan.setTinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum.GIAO_DON_VI_KIEM_TRA_VA_DE_XUAT);
							State beginState = repoState.findOne(stateService.predicateFindByType(FlowStateEnum.BAT_DAU));
							don.setProcessType(ProcessTypeEnum.KIEM_TRA_DE_XUAT);					
							don.setCurrentState(beginState);
							don.setThanhLapDon(true);
//							don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DANG_GIAI_QUYET);
							don.setNgayTiepNhan(Utils.localDateTimeNow());
							don.setLanhDaoDuyet(true);
							don.setDangGiaoKTDX(true);
							don.setTrangThaiKTDX(TrangThaiDonEnum.DANG_GIAI_QUYET);
							
							ThongTinGiaiQuyetDon thongTinGiaiQuyetDon = repoThongTinGiaiQuyetDon.findOne(thongTinGiaiQuyetDonService.predicateFindByDon(don.getId()));
							if (thongTinGiaiQuyetDon == null) {
								thongTinGiaiQuyetDon = new ThongTinGiaiQuyetDon();
								thongTinGiaiQuyetDon.setDon(don);
								thongTinGiaiQuyetDon.setNgayBatDauKTDX(Utils.localDateTimeNow());
							}
							//thongTinGiaiQuyetDon.setNgayBatDauGiaiQuyet(Utils.localDateTimeNow());
							//ThamSo thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_GIAI_QUYET_DON_MAC_DINH"));
							//Long soNgayGiaiQuyetMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 45L;
							//LocalDateTime ngayHetHanGiaiQuyet = Utils.convertNumberToLocalDateTimeGoc(Utils.localDateTimeNow(), soNgayGiaiQuyetMacDinh);
							//thongTinGiaiQuyetDon.setNgayHetHanGiaiQuyet(ngayHetHanGiaiQuyet);
							
							if (soTiepCongDan.getNgayBaoCaoKetQua() != null) { 
								thongTinGiaiQuyetDon.setNgayHetHanKTDX(soTiepCongDan.getNgayBaoCaoKetQua());
							} else {
								ThamSo thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_GIAI_QUYET_DON_MAC_DINH"));
								Long soNgayKTDXMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 45L;
								LocalDateTime ngayHetHanKTDX = Utils.convertNumberToLocalDateTimeGoc(Utils.localDateTimeNow(), soNgayKTDXMacDinh);
								thongTinGiaiQuyetDon.setNgayHetHanKTDX(ngayHetHanKTDX);
								soTiepCongDan.setNgayBaoCaoKetQua(ngayHetHanKTDX);
							}
							
							//thongTinGiaiQuyetDon.setDonViThamTraXacMinh(soTiepCongDan.getDonViChuTri());
							thongTinGiaiQuyetDonService.save(thongTinGiaiQuyetDon, congChucId);
							GiaiQuyetDon giaiQuyetDon = new GiaiQuyetDon();
							giaiQuyetDon.setThongTinGiaiQuyetDon(thongTinGiaiQuyetDon);
							giaiQuyetDon.setSoTiepCongDan(soTiepCongDan);
							giaiQuyetDon.setDonViGiaiQuyet(soTiepCongDan.getDonViChuTri());
							giaiQuyetDon.setDonViChuyenDon(soTiepCongDan.getDonViTiepDan());
							giaiQuyetDon.setChucVu(listTransitionHaveBegin.size() == 1 || listTransitionHaveBegin.size() == 2 ? listTransitionHaveBegin.get(0).getProcess().getVaiTro().getLoaiVaiTro() : null);
//							giaiQuyetDon.setChucVu(VaiTroEnum.VAN_THU);
							giaiQuyetDon.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
							giaiQuyetDon.setThuTuThucHien(1);
							giaiQuyetDon.setChucVu2(listTransitionHaveBegin.size() == 2 ? listTransitionHaveBegin.get(1).getProcess().getVaiTro().getLoaiVaiTro() : null);
							giaiQuyetDon.setDonChuyen(true);
//							don.setDonViXuLyGiaiQuyet(soTiepCongDan.getDonViChuTri());
							don.setDonViKiemTraDeXuat(soTiepCongDan.getDonViChuTri());

							//lich su kiem tra de xuat
							LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
							lichSuQTXLD.setDon(don);
							lichSuQTXLD.setNguoiXuLy(repoCongChuc.findOne(congChucId));
							lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
							lichSuQTXLD.setNoiDung(soTiepCongDan.getyKienXuLy());
							lichSuQTXLD.setTen(QuaTrinhXuLyEnum.CHUYEN_DON_VI_KTDX.getText());
							lichSuQTXLD.setDonViXuLy(soTiepCongDan.getDonViChuTri());
							lichSuQTXLD.setThuTuThucHien(1);
							lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
							giaiQuyetDonService.save(giaiQuyetDon, congChucId);
						}
					}
				}
				if (soTiepCongDan.isHoanThanhTCDLanhDao()) {
					don.setDaGiaiQuyet(true);
					soTiepCongDan.setTinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum.HOAN_THANH);
				} 
			} else if (LoaiTiepDanEnum.THUONG_XUYEN.equals(soTiepCongDan.getLoaiTiepDan())) {
				if (HuongXuLyTCDEnum.YEU_CAU_GAP_LANH_DAO.equals(soTiepCongDan.getHuongXuLy())) {
					if (!soTiepCongDan.getHuongXuLy().equals(soTiepCongDanOld.getHuongXuLy()) && soTiepCongDan.isGuiYeuCauGapLanhDao()) { 
						don.setYeuCauGapTrucTiepLanhDao(true);
						don.setNgayLapDonGapLanhDaoTmp(Utils.localDateTimeNow());
						don.setTrangThaiYeuCauGapLanhDao(TrangThaiYeuCauGapLanhDaoEnum.CHO_XIN_Y_KIEN);
					}
				} 
//				else {
//					don.setYeuCauGapTrucTiepLanhDao(false);
//					don.setNgayLapDonGapLanhDaoTmp(null);
//					don.setTrangThaiYeuCauGapLanhDao(null);
//				}
			}

			ResponseEntity<Object> output = soTiepCongDanService.doSave(soTiepCongDan, congChucId, eass, HttpStatus.CREATED);
			if (output.getStatusCode().equals(HttpStatus.CREATED)) {
//				LichSuQuaTrinhXuLy lichSuQTXL = new LichSuQuaTrinhXuLy();
//				lichSuQTXL.setDon(soTiepCongDan.getDon());
//				lichSuQTXL.setNgayXuLy(Utils.localDateTimeNow());
//				lichSuQTXL.setNguoiXuLy(repoCongChuc.findOne(congChucId));
//				lichSuQTXL.setTen(soTiepCongDan.getHuongXuLy() != null ? soTiepCongDan.getHuongXuLy().getText() : "");
//				lichSuQTXL.setNoiDung(soTiepCongDan.getGhiChuXuLy());
//				int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, soTiepCongDan.getDon().getId());
//				lichSuQTXL.setThuTuThucHien(thuTu);
				
				if (LoaiTiepDanEnum.THUONG_XUYEN.equals(soTiepCongDan.getLoaiTiepDan())) {
					List<SoTiepCongDan> soTiepCongDanYCGLDs = new ArrayList<SoTiepCongDan>();
					soTiepCongDanYCGLDs.addAll(soTiepCongDanService
							.getCuocTiepDanDinhKyCuaLanhDaoTruoc(repo, soTiepCongDan.getDon().getId()));
					if (!HuongXuLyTCDEnum.YEU_CAU_GAP_LANH_DAO.equals(soTiepCongDan.getHuongXuLy())) {
						if (soTiepCongDanYCGLDs.size() == 0) {
							don.setYeuCauGapTrucTiepLanhDao(false);
							don.setNgayLapDonGapLanhDaoTmp(null);
							don.setTrangThaiYeuCauGapLanhDao(null);
						}
					}
				}
				
				donService.save(don, congChucId);
//				lichSuQuaTrinhXuLyService.save(lichSuQTXL, congChucId);
			}
			return output;
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.PATCH, value = "/soTiepCongDan/{id}/capNhatTrangThaiYeuCauGapLanhDao")
	@ApiOperation(value = "Cập nhật Trạng Thái Yêu Cầu Gặp Lãnh Đạo", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Trạng thái thành công", response = Don.class) })
	public ResponseEntity<Object> updateTrangThaiYeuCauGapLanhDao(
			@RequestHeader(value = "Authorization", required = true) String authorization,  @PathVariable("id") long id,
			@RequestBody Medial_UpdateTrangThaiYCGLD params, PersistentEntityResourceAssembler eass) {
		
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			if (params.getTrangThaiYeuCauGapLanhDao() == null || !StringUtils.isNotBlank(params.getTrangThaiYeuCauGapLanhDao().getText())) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_REQUIRED.name(),
						ApiErrorEnum.DATA_REQUIRED.getText(), ApiErrorEnum.TRANG_THAI_YCGLD_REQUIRED.getText());
			}
			if (!StringUtils.isNotBlank(params.getLyDoThayDoiTTYeuCauGapLanhDao())) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.DATA_REQUIRED.name(),
						ApiErrorEnum.DATA_REQUIRED.getText(), ApiErrorEnum.LY_DO_THAY_DOI_TRANG_THAI_YCGLD_REQUIRED.getText());
			}
			
			TrangThaiYeuCauGapLanhDaoEnum trangThaiYeuCauGapLanhDaoEnum = params.getTrangThaiYeuCauGapLanhDao();
			Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
			List<PropertyChangeObject> list = new ArrayList<PropertyChangeObject>();
			Don donOld = repoDon.findOne(donService.predicateFindOne(id));
			if (donOld.getTrangThaiYeuCauGapLanhDao() != null && TrangThaiYeuCauGapLanhDaoEnum.DONG_Y.equals(donOld.getTrangThaiYeuCauGapLanhDao())) {
				if (!trangThaiYeuCauGapLanhDaoEnum.equals(TrangThaiYeuCauGapLanhDaoEnum.DONG_Y)) { 
					soTiepCongDans.addAll(soTiepCongDanService.getDSCuocTiepDanDinhKyCuaLanhDao(repo, donOld.getId()));
				}
			}

			if (trangThaiYeuCauGapLanhDaoEnum != null && !trangThaiYeuCauGapLanhDaoEnum.equals(donOld.getTrangThaiYeuCauGapLanhDao())) {
				list.add(new PropertyChangeObject("Trạng thái yêu cầu gặp lãnh đạo",
						donOld.getTrangThaiYeuCauGapLanhDao() != null ? donOld.getTrangThaiYeuCauGapLanhDao().getText() : "",
								params.getTrangThaiYeuCauGapLanhDao() != null ? params.getTrangThaiYeuCauGapLanhDao().getText() : ""));
			}
			if (params.getLyDoThayDoiTTYeuCauGapLanhDao() != null && !params.getLyDoThayDoiTTYeuCauGapLanhDao().equals(donOld.getLyDoThayDoiTTYeuCauGapLanhDao())) {
				list.add(new PropertyChangeObject("Lý do thay đổi trạng thái", donOld.getLyDoThayDoiTTYeuCauGapLanhDao(), params.getLyDoThayDoiTTYeuCauGapLanhDao()));
			}
			
			Don don = repoDon.findOne(donService.predicateFindOne(id));
			don.setTrangThaiYeuCauGapLanhDao(params.getTrangThaiYeuCauGapLanhDao());
			don.setLyDoThayDoiTTYeuCauGapLanhDao(params.getLyDoThayDoiTTYeuCauGapLanhDao());
			if (!don.getTrangThaiYeuCauGapLanhDao().equals(TrangThaiYeuCauGapLanhDaoEnum.DONG_Y)) { 
				don.setThanhLapTiepDanGapLanhDao(false);
			}
			
//			if (list.size() > 0) {
//				LichSuThayDoi lichSu = new LichSuThayDoi();
//				lichSu.setDoiTuongThayDoi(DoiTuongThayDoiEnum.DON);
//				lichSu.setIdDoiTuong(id);
//				lichSu.setNoiDung("Cập nhật trạng thái yêu cầu gặp lãnh đạo");
//				lichSu.setChiTietThayDoi(getChiTietThayDoi(list));
//				lichSuThayDoiService.save(lichSu, congChucId);
//			}
			
			ResponseEntity<Object> output = donService.doSave(don, congChucId, eass, HttpStatus.CREATED);
			if (output.getStatusCode().equals(HttpStatus.CREATED)) {
				if (list.size() > 0) {
					LichSuThayDoi lichSu = new LichSuThayDoi();
					lichSu.setDoiTuongThayDoi(DoiTuongThayDoiEnum.DON);
					lichSu.setIdDoiTuong(id);
					lichSu.setNoiDung("Cập nhật trạng thái yêu cầu gặp lãnh đạo");
					lichSu.setChiTietThayDoi(getChiTietThayDoi(list));
					lichSuThayDoiService.save(lichSu, congChucId);
				}
				if (soTiepCongDans.size() > 0) {
					for (SoTiepCongDan soTiepCongDan : soTiepCongDans) {
						soTiepCongDan.setDaXoa(true);
						soTiepCongDanService.save(soTiepCongDan, congChucId);
					}
				}
			}
			return output;
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/soTiepCongDans/{id}")
	@ApiOperation(value = "Xoá Sổ Tiếp Công Dân", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Sổ Tiếp Công Dân thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {
		
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_XOA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			SoTiepCongDan soTiepCongDan = repo.findOne(soTiepCongDanService.predicateFindOne(id));
			if (soTiepCongDan == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			
			Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			
			Don don = soTiepCongDan.getDon();
			if (LoaiTiepDanEnum.THUONG_XUYEN.equals(soTiepCongDan.getLoaiTiepDan())) {
				if (soTiepCongDan.getSoThuTuLuotTiep() < don.getTongSoLuotTCD()) {
					return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.DATA_INVALID.name(), ApiErrorEnum.DATA_INVALID.getText(), ApiErrorEnum.DATA_INVALID.getText());
				}
				int tongSoLuotTCD = don.getTongSoLuotTCD();
				don.setTongSoLuotTCD(tongSoLuotTCD - 1);
				
				List<SoTiepCongDan> soTiepCongDanYCGLDs = new ArrayList<SoTiepCongDan>();
				soTiepCongDanYCGLDs.addAll(soTiepCongDanService
						.getCuocTiepDanDinhKyCuaLanhDaoTruocNotId(repo, soTiepCongDan.getDon().getId(),  soTiepCongDan.getId()));
				if (soTiepCongDanYCGLDs.size() == 0) {
					don.setYeuCauGapTrucTiepLanhDao(false);
					don.setNgayLapDonGapLanhDaoTmp(null);
					don.setTrangThaiYeuCauGapLanhDao(null);
				}
				
				donService.save(don, congChucId);
			}
			
			if (!don.isThanhLapDon()) {
				List<SoTiepCongDan> soTiepCongDans = new ArrayList<SoTiepCongDan>();
				soTiepCongDans.addAll(soTiepCongDanService
						.getKiemTraSoTiepCongDan(repo, soTiepCongDan.getDon().getId()));
				if (soTiepCongDans.size() == 1) {
					don.setDaXoa(true);
					donService.save(don, congChucId);
				}
			}
			
			soTiepCongDan.setDaXoa(true);
			soTiepCongDanService.save(soTiepCongDan, congChucId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/danhSachYeuCauGapLanhDaoDinhKys")
	@ApiOperation(value = "Lấy danh sách Yêu Cầu Gặp Lãnh Đạo Định Kỳ", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListYeuCauGapLanhDaoDinhKy(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "phanLoai", required = false) String phanLoai, 
			@RequestParam(value = "nguonDon", required = false) String nguonDon, 
			@RequestParam(value = "trangThai", required = false) String trangThai,
			PersistentEntityResourceAssembler eass) {
		
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_LIETKE) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Page<Don> page = repoDon.findAll(donService.predicateFindDonYeuCauGapLanhDaoDinhKy(tuKhoa, tuNgay, denNgay, phanLoai, nguonDon, trangThai, donViId), pageable);
			return assemblerDon.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/hoSoVuViecYeuCauGapLanhDaos")
	@ApiOperation(value = "Lấy danh sách Hồ Sơ Vụ Việc Yêu Cầu Gặp Lãnh Đạo", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListHoSoVuViecYeuCauGapLanhDao(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "loaiDon", required = false) String loaiDon, 
			@RequestParam(value = "linhVucId", required = false) Long linhVucId, 
			@RequestParam(value = "linhVucChiTietChaId", required = false) Long linhVucChiTietChaId, 
			@RequestParam(value = "linhVucChiTietConId", required = false) Long linhVucChiTietConId,
			PersistentEntityResourceAssembler eass) {
		
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_LIETKE) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Page<Don> page = repoDon.findAll(donService.predicateFindDonYeuCauGapLanhDao(tuNgay, denNgay, loaiDon, linhVucId, linhVucChiTietChaId, linhVucChiTietConId, donViId), pageable);
			return assemblerDon.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/soTiepCongDans/{id}/huyCuocTiepDanDinhKyCuaLanhDao")
	@ApiOperation(value = "Xoá Sổ Tiếp Công Dân", position = 8, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Sổ Tiếp Công Dân thành công") })
	public ResponseEntity<Object> cancelCuocTiepDanDinhKyCuaLanhDao(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {
		
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_XOA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			SoTiepCongDan soTiepCongDan = soTiepCongDanService.cancelCuocTiepDanDinhKyCuaLanhDao(repo, id);
			if (soTiepCongDan == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			soTiepCongDanService.save(soTiepCongDan,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans/danhSachYeuCauGapLanhDao/excel")
	@ApiOperation(value = "Xuất file excel", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportDSYeuCauGapLanhDaoExcel(HttpServletResponse response,
			@RequestParam(value = "donViId", required = true) Long donViId,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "phanLoai", required = false) String phanLoai, 
			@RequestParam(value = "nguonDon", required = false) String nguonDon, 
			@RequestParam(value = "trangThai", required = false) String trangThai) throws IOException {
		
		try {
			ExcelUtil.exportDanhSachYeuCauGapLanhDao(response,
					"DanhSachYeuCauGapLanhDao", "sheetName", (List<Don>) repoDon.findAll(donService
							.predicateFindDonYeuCauGapLanhDaoDinhKy(tuKhoa, tuNgay, denNgay, phanLoai, nguonDon, trangThai, donViId)),
					"Danh sách yêu cầu gặp lãnh đạo");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans/inPhieuHen")
	@ApiOperation(value = "In phiếu hẹn", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWord(@RequestParam(value = "hoVaTen", required = false) String hoVaTen,
			@RequestParam(value = "soCMND", required = false) String soCMND,
			@RequestParam(value = "ngayCap", required = false) String ngayCap,
			@RequestParam(value = "noiCap", required = false) String noiCap,
			@RequestParam(value = "diaChi", required = false) String diaChi,
			@RequestParam(value = "diaDiemTiepCongDan", required = false) String diaDiemTiepCongDan,
			@RequestParam(value = "thoiGianTiepCongDan", required = false) String thoiGianTiepCongDan,
			@RequestParam(value = "ngayHenTiepCongDan", required = false) String ngayHenTiepCongDan,
			HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			mappings.put("hoVaTen", hoVaTen);
			mappings.put("soCMND", soCMND);
			mappings.put("ngayCap", ngayCap);
			mappings.put("noiCap", noiCap);
			mappings.put("diaChi", diaChi);
			mappings.put("diaDiemTiepCongDan", diaDiemTiepCongDan);
			mappings.put("thoiGianTiepCongDan", thoiGianTiepCongDan);
			mappings.put("thoiGianHen", ngayHenTiepCongDan);
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/tiepcongdan/TCD_PHIEU_HEN.doc").getFile(), mappings);
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans/inPhieuHuongDanToCao")
	@ApiOperation(value = "In phiếu hẹn", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportPhieuHuongDanToCao(
			@RequestParam(value = "ngayTiepNhan", required = false) String ngayTiepNhan,
			@RequestParam(value = "hoVaTen", required = false) String hoVaTen,
			@RequestParam(value = "soCMND", required = false) String soCMND,
			@RequestParam(value = "ngayCap", required = false) String ngayCap,
			@RequestParam(value = "noiCap", required = false) String noiCap,
			@RequestParam(value = "diaChi", required = false) String diaChi,
			@RequestParam(value = "noiDung", required = false) String noiDung,
			HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			mappings.put("ngayTiepNhan", ngayTiepNhan);
			mappings.put("hoVaTen", hoVaTen);
			mappings.put("soCMND", soCMND);
			mappings.put("ngayCap", ngayCap);
			mappings.put("noiCap", noiCap);
			mappings.put("diaChi", diaChi);
			mappings.put("noiDung", noiDung);
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/tiepcongdan/TCD_PHIEU_HUONG_DAN_TO_CAO.docx").getFile(), mappings);
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans/inPhieuHuongDanKhieuNai")
	@ApiOperation(value = "In phiếu hẹn", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportPhieuHuongDanKhieuNai(
			@RequestParam(value = "ngayTiepNhan", required = false) String ngayTiepNhan,
			@RequestParam(value = "hoVaTen", required = false) String hoVaTen,
			@RequestParam(value = "soCMND", required = false) String soCMND,
			@RequestParam(value = "ngayCap", required = false) String ngayCap,
			@RequestParam(value = "noiCap", required = false) String noiCap,
			@RequestParam(value = "diaChi", required = false) String diaChi,
			@RequestParam(value = "noiDung", required = false) String noiDung,
			HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			mappings.put("ngayTiepNhan", ngayTiepNhan);
			mappings.put("hoVaTen", hoVaTen);
			mappings.put("soCMND", soCMND);
			mappings.put("ngayCap", ngayCap);
			mappings.put("noiCap", noiCap);
			mappings.put("diaChi", diaChi);
			mappings.put("noiDung", noiDung);
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/tiepcongdan/TCD_PHIEU_HUONG_DAN_KHIEU_NAI.docx").getFile(), mappings);
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans/inPhieuTuChoi")
	@ApiOperation(value = "In phiếu hẹn", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportPhieuTuChoi(
			@RequestParam(value = "ngayThongBao", required = false) String ngayThongBao,
			@RequestParam(value = "hoVaTen", required = false) String hoVaTen,
			@RequestParam(value = "soCMND", required = false) String soCMND,
			@RequestParam(value = "ngayCap", required = false) String ngayCap,
			@RequestParam(value = "noiCap", required = false) String noiCap,
			@RequestParam(value = "diaChi", required = false) String diaChi,
			@RequestParam(value = "noiDung", required = false) String noiDung,
			HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			mappings.put("ngayThongBao", ngayThongBao);
			mappings.put("hoVaTen", hoVaTen);
			mappings.put("soCMND", soCMND);
			mappings.put("ngayCap", ngayCap);
			mappings.put("noiCap", noiCap);
			mappings.put("diaChi", diaChi);
			mappings.put("noiDung", noiDung);
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/tiepcongdan/TCD_PHIEU_TU_CHOI.docx").getFile(), mappings);
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans/excel")
	@ApiOperation(value = "Xuất file excel", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcel(HttpServletResponse response,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "loaiTiepCongDan", required = true) String loaiTiepCongDan,
			@RequestParam(value = "coQuanQuanLyId", required = true) Long coQuanQuanLyId,
			@RequestParam(value = "lanhDaoId", required = false) Long lanhDaoId,
			@RequestParam(value = "tenNguoiDungDon", required = false) String tenNguoiDungDon,
			@RequestParam(value = "tenLanhDao", required = false) String tenLanhDao,
			@RequestParam(value = "tinhTrangXuLy", required = false) String tinhTrangXuLy,
			@RequestParam(value = "ketQuaTiepDan", required = false) String ketQuaTiepDan) throws IOException {
		
		try {
			OrderSpecifier<LocalDateTime> order = QSoTiepCongDan.soTiepCongDan.ngayTiepDan.desc();
			if (LoaiTiepDanEnum.THUONG_XUYEN.name().equals(loaiTiepCongDan)) {
				ExcelUtil.exportDanhSachTiepDanThuongXuyen(response,
						"DanhSachSoTiepCongDan", "sheetName", (List<SoTiepCongDan>) repo.findAll(soTiepCongDanService
								.predicateFindAllTCD("", null, null, tuNgay, denNgay, loaiTiepCongDan, coQuanQuanLyId, lanhDaoId, 
										tenLanhDao, tenNguoiDungDon, tinhTrangXuLy, ketQuaTiepDan, congChucService, repoCongChuc, repoDonCongDan), order),
						"Sổ tiếp công dân thường xuyên");
			} else if (LoaiTiepDanEnum.DOT_XUAT.name().equals(loaiTiepCongDan)) {
				ExcelUtil.exportDanhSachTiepDanLanhDao(response, "DanhSachSoTiepCongDan",
						"sheetName", (List<SoTiepCongDan>) repo.findAll(soTiepCongDanService.predicateFindAllTCD("",
								null, null, tuNgay, denNgay, loaiTiepCongDan, coQuanQuanLyId, lanhDaoId, tenLanhDao, tenNguoiDungDon, tinhTrangXuLy, 
								ketQuaTiepDan, congChucService, repoCongChuc, repoDonCongDan), order),
						"Sổ tiếp công dân đột xuất của lãnh đạo");
			} else if (LoaiTiepDanEnum.DINH_KY.name().equals(loaiTiepCongDan)) {
				ExcelUtil.exportDanhSachTiepDanLanhDao(response, "DanhSachSoTiepCongDan",
						"sheetName", (List<SoTiepCongDan>) repo.findAll(soTiepCongDanService.predicateFindAllTCD("",
								null, null, tuNgay, denNgay, loaiTiepCongDan, coQuanQuanLyId, lanhDaoId, tenLanhDao, tenNguoiDungDon, tinhTrangXuLy, 
								ketQuaTiepDan, congChucService, repoCongChuc, repoDonCongDan), order),
						"Sổ tiếp công dân định kỳ của lãnh đạo");
			}
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
}
