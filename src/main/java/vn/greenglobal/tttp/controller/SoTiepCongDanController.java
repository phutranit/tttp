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
import vn.greenglobal.tttp.enums.FlowStateEnum;
import vn.greenglobal.tttp.enums.HuongGiaiQuyetTCDEnum;
import vn.greenglobal.tttp.enums.HuongXuLyTCDEnum;
import vn.greenglobal.tttp.enums.LoaiTiepDanEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.QuaTrinhXuLyEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.enums.TinhTrangGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CoQuanToChucTiepDan;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.LichSuQuaTrinhXuLy;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.model.Transition;
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
			System.out.println("don " +soTiepCongDan.getDon().getId());
			if (LoaiTiepDanEnum.DINH_KY.equals(soTiepCongDan.getLoaiTiepDan())) {
				don.setThanhLapTiepDanGapLanhDao(true);
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
					if (soTiepCongDan.isChuyenDonViKiemTra()) {
						flagChuyenDonViKiemTra = true;
						soTiepCongDan.setTrinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum.GIAO_DON_VI_KIEM_TRA_VA_DE_XUAT);
					}
				}
				if (soTiepCongDan.isHoanThanhTCDLanhDao()) {
					soTiepCongDan.setTrinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum.HOAN_THANH);
					don.setDaXuLy(true);
					don.setDaGiaiQuyet(true);
				} else {
					if (soTiepCongDan.getTrinhTrangXuLyTCDLanhDao() == null) {
						soTiepCongDan.setTrinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum.DANG_XU_LY);
					}
					don.setDaXuLy(true);
				}
			} else if (LoaiTiepDanEnum.THUONG_XUYEN.equals(soTiepCongDan.getLoaiTiepDan())) {
				int soLuotTiep = don.getTongSoLuotTCD();
				soTiepCongDan.setSoThuTuLuotTiep(soLuotTiep + 1);
				don.setTongSoLuotTCD(soLuotTiep + 1);
				if (HuongXuLyTCDEnum.YEU_CAU_GAP_LANH_DAO.equals(soTiepCongDan.getHuongXuLy())) {
					don.setYeuCauGapTrucTiepLanhDao(true);
				}
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
				if (transitionTTXM == null) {
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
					don.setDonViXuLyGiaiQuyet(soTiepCongDan.getDonViChuTri());
					don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DANG_GIAI_QUYET);
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
					thongTinGiaiQuyetDon.setDonViThamTraXacMinh(soTiepCongDan.getDonViChuTri());
					thongTinGiaiQuyetDonService.save(thongTinGiaiQuyetDon, congChucId);
					GiaiQuyetDon giaiQuyetDon = new GiaiQuyetDon();
					giaiQuyetDon.setThongTinGiaiQuyetDon(thongTinGiaiQuyetDon);
					giaiQuyetDon.setChucVu(listTransitionHaveBegin.size() == 1 ? transitionTTXM.getProcess().getVaiTro().getLoaiVaiTro() : null);
					giaiQuyetDon.setDonViGiaiQuyet(soTiepCongDan.getDonViChuTri());
					giaiQuyetDon.setDonViChuyenDon(soTiepCongDan.getDonViTiepDan());
					giaiQuyetDon.setSoTiepCongDan(soTiepCongDan);
					giaiQuyetDon.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
					giaiQuyetDon.setThuTuThucHien(1);
					don.setDonViXuLyGiaiQuyet(soTiepCongDan.getDonViChuTri());
					giaiQuyetDonService.save(giaiQuyetDon, congChucId);
				}
				
				LichSuQuaTrinhXuLy lichSuQTXL = new LichSuQuaTrinhXuLy();
				lichSuQTXL.setDon(soTiepCongDan.getDon());
				lichSuQTXL.setNgayXuLy(Utils.localDateTimeNow());
				lichSuQTXL.setNguoiXuLy(repoCongChuc.findOne(congChucId));
				lichSuQTXL.setTen(QuaTrinhXuLyEnum.CHUYEN_DON_VI_KTDX.getText());
				lichSuQTXL.setNoiDung(soTiepCongDan.getNoiDungTiepCongDan());
				lichSuQTXL.setDonViXuLy(repoCoQuanQuanLy.findOne(donViId));
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
			}

			if (LoaiTiepDanEnum.DINH_KY.equals(soTiepCongDan.getLoaiTiepDan())
					|| LoaiTiepDanEnum.DOT_XUAT.equals(soTiepCongDan.getLoaiTiepDan())) {
				soTiepCongDan.setHuongXuLy(HuongXuLyTCDEnum.KHOI_TAO);
				if (soTiepCongDan.getHuongGiaiQuyetTCDLanhDao() == null || HuongGiaiQuyetTCDEnum.KHOI_TAO.equals(soTiepCongDan.getHuongGiaiQuyetTCDLanhDao())) {
					return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.HUONGGIAIQUYET_REQUIRED.name(), ApiErrorEnum.HUONGGIAIQUYET_REQUIRED.getText(), ApiErrorEnum.HUONGGIAIQUYET_REQUIRED.getText());
				}
				if (soTiepCongDanOld.getTrinhTrangXuLyTCDLanhDao() != null) { 
					soTiepCongDan.setTrinhTrangXuLyTCDLanhDao(soTiepCongDanOld.getTrinhTrangXuLyTCDLanhDao());
				} else { 
					soTiepCongDan.setTrinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum.DANG_XU_LY);
				}
				if (HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET.equals(soTiepCongDan.getHuongGiaiQuyetTCDLanhDao())) {
					if (soTiepCongDan.getDonViChuTri() == null) {
						return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DONVICHUTRI_REQUIRED.name(),
								ApiErrorEnum.DONVICHUTRI_REQUIRED.getText(), ApiErrorEnum.DONVICHUTRI_REQUIRED.getText());
					}
					if (soTiepCongDan.getDonViPhoiHops() != null && 
							soTiepCongDan.getDonViPhoiHops().size() > 0) {
						
					}
					if (soTiepCongDan.isChuyenDonViKiemTra()) {
						if (!HuongGiaiQuyetTCDEnum.GIAO_DON_VI_KIEM_TRA_VA_DE_XUAT.equals(soTiepCongDan.getTrinhTrangXuLyTCDLanhDao())) {
							soTiepCongDan.setTrinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum.GIAO_DON_VI_KIEM_TRA_VA_DE_XUAT);
							State beginState = repoState.findOne(stateService.predicateFindByType(FlowStateEnum.BAT_DAU));
							don.setProcessType(ProcessTypeEnum.KIEM_TRA_DE_XUAT);					
							don.setCurrentState(beginState);
							don.setThanhLapDon(true);
							don.setNgayTiepNhan(Utils.localDateTimeNow());
							don.setLanhDaoDuyet(true);
							don.setDangGiaoKTDX(true);
							
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
							
							thongTinGiaiQuyetDon.setDonViThamTraXacMinh(soTiepCongDan.getDonViChuTri());
							thongTinGiaiQuyetDonService.save(thongTinGiaiQuyetDon, congChucId);
							GiaiQuyetDon giaiQuyetDon = new GiaiQuyetDon();
							giaiQuyetDon.setThongTinGiaiQuyetDon(thongTinGiaiQuyetDon);
							giaiQuyetDon.setSoTiepCongDan(soTiepCongDan);
							giaiQuyetDon.setDonViGiaiQuyet(soTiepCongDan.getDonViChuTri());
							giaiQuyetDon.setDonViChuyenDon(soTiepCongDan.getDonViTiepDan());
							giaiQuyetDon.setChucVu(VaiTroEnum.VAN_THU);
							giaiQuyetDon.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
							giaiQuyetDon.setThuTuThucHien(1);
							don.setDonViXuLyGiaiQuyet(soTiepCongDan.getDonViChuTri());
							
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
					soTiepCongDan.setTrinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum.HOAN_THANH);
				} 
			} else if (LoaiTiepDanEnum.THUONG_XUYEN.equals(soTiepCongDan.getLoaiTiepDan())) {
				if (HuongXuLyTCDEnum.YEU_CAU_GAP_LANH_DAO.equals(soTiepCongDan.getHuongXuLy())) {
					don.setYeuCauGapTrucTiepLanhDao(true);
				}
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
				
				donService.save(don, congChucId);
//				lichSuQuaTrinhXuLyService.save(lichSuQTXL, congChucId);
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
			
			if (LoaiTiepDanEnum.THUONG_XUYEN.equals(soTiepCongDan.getLoaiTiepDan())) {
				Don don = soTiepCongDan.getDon();
				
				if (soTiepCongDan.getSoThuTuLuotTiep() < don.getTongSoLuotTCD()) {
					return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.DATA_INVALID.name(), ApiErrorEnum.DATA_INVALID.getText(), ApiErrorEnum.DATA_INVALID.getText());
				}
				int tongSoLuotTCD = don.getTongSoLuotTCD();
				don.setTongSoLuotTCD(tongSoLuotTCD - 1);
				donService.save(don, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			}		
			soTiepCongDan.setDaXoa(true);
			soTiepCongDanService.save(soTiepCongDan, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/hoSoVuViecYeuCauGapLanhDaos")
	@ApiOperation(value = "Lấy danh sách Hồ Sơ Vụ Việc Yêu Cầu Gặp Lãnh Đạo", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListHoSoVuViecYeuCauGapLanhDao(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay, 
			@RequestParam(value = "linhVucId", required = false) Long linhVucId, 
			@RequestParam(value = "linhVucChiTietChaId", required = false) Long linhVucChiTietChaId, 
			@RequestParam(value = "linhVucChiTietConId", required = false) Long linhVucChiTietConId,
			PersistentEntityResourceAssembler eass) {
		
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_LIETKE) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			Page<Don> page = repoDon.findAll(donService.predicateFindDonYeuCauGapLanhDao(tuNgay, denNgay, linhVucId, linhVucChiTietChaId, linhVucChiTietConId), pageable);
			return assemblerDon.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/soTiepCongDans/{id}/huyCuocTiepDanDinhKyCuaLanhDao")
	@ApiOperation(value = "Xoá Sổ Tiếp Công Dân", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
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
						"Danh sách sổ tiếp dân");
			} else if (LoaiTiepDanEnum.DINH_KY.name().equals(loaiTiepCongDan) || LoaiTiepDanEnum.DOT_XUAT.name().equals(loaiTiepCongDan)) {
				ExcelUtil.exportDanhSachTiepDanLanhDao(response, "DanhSachSoTiepCongDan",
						"sheetName", (List<SoTiepCongDan>) repo.findAll(soTiepCongDanService.predicateFindAllTCD("",
								null, null, tuNgay, denNgay, loaiTiepCongDan, coQuanQuanLyId, lanhDaoId, tenLanhDao, tenNguoiDungDon, tinhTrangXuLy, 
								ketQuaTiepDan, congChucService, repoCongChuc, repoDonCongDan), order),
						"Danh sách sổ tiếp dân định kỳ");
			}
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
}
