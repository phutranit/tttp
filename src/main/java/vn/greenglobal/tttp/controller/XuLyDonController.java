package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
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

import com.querydsl.core.types.Predicate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.FlowStateEnum;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.KetQuaTrangThaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.QuaTrinhXuLyEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.enums.TinhTrangGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.TrangThaiTTXMEnum;
import vn.greenglobal.tttp.enums.TrangThaiYeuCauGapLanhDaoEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.DoanDiCung;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.DonViNgoaiHeThong;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.LichSuQuaTrinhXuLy;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.TaiLieuBangChung;
import vn.greenglobal.tttp.model.TaiLieuVanThu;
import vn.greenglobal.tttp.model.TepDinhKem;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.DonViNgoaiHeThongRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.ThongTinGiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.LichSuQuaTrinhXuLyRepository;
import vn.greenglobal.tttp.repository.ProcessRepository;
import vn.greenglobal.tttp.repository.StateRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.service.GiaiQuyetDonService;
import vn.greenglobal.tttp.service.LichSuCanBoXuLyService;
import vn.greenglobal.tttp.service.LichSuQuaTrinhXuLyService;
import vn.greenglobal.tttp.service.ProcessService;
import vn.greenglobal.tttp.service.StateService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.service.ThongTinGiaiQuyetDonService;
import vn.greenglobal.tttp.service.TransitionService;
import vn.greenglobal.tttp.service.XuLyDonService;
import vn.greenglobal.tttp.util.Utils;
import vn.greenglobal.tttp.util.WordUtil;

@RestController
@RepositoryRestController
@Api(value = "xuLyDons", description = "Xử lý đơn")
public class XuLyDonController extends TttpController<XuLyDon> {
	
	@Autowired
	private XuLyDonService xuLyDonService;
	
	@Autowired
	private DonService donService;

	@Autowired
	private XuLyDonRepository repo;

	@Autowired
	private DonRepository donRepo;
	
	@Autowired
	private ThongTinGiaiQuyetDonRepository thongTinGiaiQuyetDonRepo;
	
	@Autowired
	private GiaiQuyetDonService giaiQuyetDonService;

	@Autowired
	private GiaiQuyetDonRepository giaiQuyetDonRepo;
	
	@Autowired
	private XuLyDonRepository xuLyDonRepo;

	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepo;

	@Autowired
	private TransitionRepository transitionRepo;

	@Autowired
	private TransitionService transitionService;
	
	@Autowired
	private ThongTinGiaiQuyetDonService thongTinGiaiQuyetDonService;

	@Autowired
	private ProcessRepository repoProcess;

	@Autowired
	private ProcessService processService;

	@Autowired
	private StateRepository repoState;

	@Autowired
	private StateService stateService;

	@Autowired
	private CongChucRepository congChucRepo;

	@Autowired
	private LichSuQuaTrinhXuLyRepository lichSuQuaTrinhXuLyRepo;
	
	@Autowired
	private LichSuQuaTrinhXuLyService lichSuQuaTrinhXuLyService;
	
	@Autowired
	private LichSuCanBoXuLyService lichSuCanBoXuLyService;
			
	@Autowired
	private StateService serviceState;
	
	@Autowired
	private ThamSoRepository thamSoRepository;
	
	@Autowired
	private ThamSoService thamSoService;
	
	@Autowired
	private TransitionRepository repoTransition;
	
	@Autowired
	private DonViNgoaiHeThongRepository donViNgoaiHeThongRepo;
	
	@Autowired
	private ThamSoRepository repoThamSo;
	
	public XuLyDonController(BaseRepository<XuLyDon, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/lichSuXuLyDon")
	@ApiOperation(value = "Lấy danh sách lịch sử xử lý đơn", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy danh sách lịch sử xử lý đơn", response = XuLyDon.class) })
	public @ResponseBody Object getDanhSachLichSuXuLyDon(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam("id") Long id, PersistentEntityResourceAssembler eass) {
		
		try {
			NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_XEM);
			if (nguoiDung != null) {
				Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
				Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
				Long phongBanXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());

				Don don = donRepo.findOne(donService.predicateFindOne(id));
				if (don != null) {
					Page<XuLyDon> pageData =  xuLyDonRepo.findAll(xuLyDonService.predFindLichSuXLD(repo, don.getId(), donViId, phongBanXuLyXLD, congChucId), pageable);
					return assembler.toResource(pageData, (ResourceAssembler) eass);
				}
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/thongTinXuLyDon")
	@ApiOperation(value = "Lấy thông tin Xử lý đơn", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy thông tin Xử lý đơn", response = XuLyDon.class) })
	public ResponseEntity<Object> getThongTinXuLyDon(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam("id") Long id, PersistentEntityResourceAssembler eass) {
		
		try {
			NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_XEM);
			if (nguoiDung != null) {
				Don don = donRepo.findOne(donService.predicateFindOne(id));
				Long phongBanXuLyXLD = 0L;
				String vaiTroNguoiDungHienTai = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro").toString();
				Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
				Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
				if (!StringUtils.equals(VaiTroEnum.LANH_DAO.name(), vaiTroNguoiDungHienTai)) { 
					phongBanXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
				}
				boolean isChuyenVienNhapLieu = Boolean.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("isChuyenVienNhapLieu").toString());
				if (don != null) {
					if (don.getGiaiQuyetDonCuoiCungId() == null) {
						if (don.getXuLyDonCuoiCungId() != null && don.getXuLyDonCuoiCungId() > 0
								&& don.getDonViXuLyGiaiQuyet() != null
								&& donViId.equals(don.getDonViXuLyGiaiQuyet().getId())) {
							XuLyDon xld = xuLyDonRepo
									.findOne(xuLyDonService.predicateFindOne(don.getXuLyDonCuoiCungId()));
							return new ResponseEntity<>(eass.toFullResource(xld), HttpStatus.OK);
						} else {
							State beginState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.BAT_DAU));					
							Predicate predicateProcess = processService.predicateFindAllByDonVi(coQuanQuanLyRepo.findOne(donViId), ProcessTypeEnum.XU_LY_DON);
							List<Process> listProcess = (List<Process>) repoProcess.findAll(predicateProcess);
							List<State> listState = new ArrayList<State>();
							List<Process> listProcessHaveBeginState = new ArrayList<Process>();
							for (Process processFromList : listProcess) {
								Predicate predicate = serviceState.predicateFindAll(beginState.getId(), processFromList, repoTransition);
								listState = ((List<State>) repoState.findAll(predicate));						
								if (listState.size() > 0) {
									State state = listState.get(0);
									if (!state.getType().equals(FlowStateEnum.KET_THUC)) {
										listProcessHaveBeginState.add(processFromList);
										break;
									}
								}
							}
							XuLyDon xuLyDon = xuLyDonService.predFindThongTinXuLy(repo, don.getId(), donViId,
									phongBanXuLyXLD, congChucId, "", isChuyenVienNhapLieu);
							if (listProcessHaveBeginState.size() > 0) {
								xuLyDon = xuLyDonService.predFindThongTinXuLy(repo, don.getId(), donViId,
										phongBanXuLyXLD, congChucId, vaiTroNguoiDungHienTai, isChuyenVienNhapLieu);
							}
							if (xuLyDon != null) {
								return new ResponseEntity<>(eass.toFullResource(xuLyDon), HttpStatus.OK);
							}
						}
					} Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
							ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
				}
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
				
			}
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/xuLyDons/tuXuLy")
	@ApiOperation(value = "Quy trình xử lý đơn", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Thêm quy trình xử lý đơn thành công", response = XuLyDon.class) })
	public ResponseEntity<Object> xuLyNgay(
			@RequestHeader(value = "Authorization", required = true) String authorization, @RequestBody XuLyDon xuLyDon,
			PersistentEntityResourceAssembler eass) {
		try {
			NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA);
			CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
			Long congChucId = Long.valueOf(
					profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
					&& commonProfile.containsAttribute("coQuanQuanLyId")) {
				if (xuLyDon.getDon() == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DON_REQUIRED.name(),
							ApiErrorEnum.DON_REQUIRED.getText(), ApiErrorEnum.DON_REQUIRED.getText());
				}
				Long donId = xuLyDon.getDon().getId();
				Don don = donRepo.findOne(donService.predicateFindOne(donId));
				Long donViId = Long.valueOf(
						profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
				CongChuc congChuc = congChucRepo.findOne(congChucId);
				CoQuanQuanLy donVi = congChuc.getCoQuanQuanLy().getDonVi();
				if (don.getProcessType() == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_TYPE_REQUIRED.name(),
							ApiErrorEnum.PROCESS_TYPE_REQUIRED.getText(), ApiErrorEnum.PROCESS_TYPE_REQUIRED.getText());
				}
				XuLyDon xuLyDonHienTai = xuLyDonService.predFindXuLyDonCuoiCung(repo, donId, donViId);
				if (xuLyDonHienTai != null) {
					// Xu ly don khong co van thu
					HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy();
					if (huongXuLyXLD == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.HUONGXULY_REQUIRED.name(),
								ApiErrorEnum.HUONGXULY_REQUIRED.getText(), ApiErrorEnum.HUONGXULY_REQUIRED.getText());
					}
					boolean isOwner = false;
					if (xuLyDonHienTai.getCanBoXuLyChiDinh() != null) {
						isOwner = don.getNguoiTao().getId() == null || don.getNguoiTao().getId().equals(0L) ? true
								: xuLyDonHienTai.getCanBoXuLyChiDinh().getId().longValue() == don.getNguoiTao().getId().longValue() ? true : false;
					}					
					State nextState = repoState.findOne(stateService.predicateFindByType(FlowStateEnum.KET_THUC));
					Process process = repoProcess.findOne(processService.predicateFindAll(xuLyDonHienTai.getChucVu().toString(), donVi, isOwner, don.getProcessType()));
					if (process == null && isOwner) {
						process = repoProcess.findOne(processService.predicateFindAll(xuLyDonHienTai.getChucVu().toString(), donVi, false, don.getProcessType()));
					}
					Transition transition = transitionRepo.findOne(transitionService.predicatePrivileged(don.getCurrentState(), nextState, process));
					
					
					xuLyDonHienTai.setNextState(nextState);
					xuLyDonHienTai.setNextForm(transition.getForm());
					
					xuLyDonHienTai.setHuongXuLy(huongXuLyXLD);
					xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
					CongChuc canBoXuLyChiDinh = xuLyDonHienTai.getCanBoXuLyChiDinh();
					if (HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO.equals(huongXuLyXLD)) {
						xuLyDonHienTai = chuyenVienChuyenChoVanThuYeuCauGapLanhDao(xuLyDon, xuLyDonHienTai, congChucId);
						lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDonHienTai.getDon(), canBoXuLyChiDinh, 
								xuLyDonHienTai.getDonViXuLy().getDonVi());
						return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.DE_XUAT_THU_LY.equals(huongXuLyXLD)) {
						
//						//TODO tim kiem don vi do co phong ban hay khong
//						List<CoQuanQuanLy> listPhongBan = new ArrayList<CoQuanQuanLy>();
//						listPhongBan = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(
//								coQuanQuanLyService.predicateFindPhongBanDonBanDonvi(donVi.getId(), thamSoService, thamSoRepo));				
						Transition transitionGQD = null;					
						
						//Tim kiem VaiTro giai quyet don
						Predicate predicate = processService.predicateFindAllByDonVi(donVi, ProcessTypeEnum.GIAI_QUYET_DON);
						List<Process> listProcessGQD = (List<Process>) repoProcess.findAll(predicate);
						if (listProcessGQD.size() < 1) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_GQD_NOT_FOUND.name(),
									ApiErrorEnum.PROCESS_GQD_NOT_FOUND.getText(), ApiErrorEnum.PROCESS_GQD_NOT_FOUND.getText());
						}
						
						List<Transition> listTransitionHaveBegin = new ArrayList<>();
						boolean isQuyTrinhBat2DauKetThuc = false;
						for (Process processFromList : listProcessGQD) {
							//Transition transitionGQDBatDau = transitionRepo.findOne(transitionService.predicateFindFromCurrentAndNext(FlowStateEnum.BAT_DAU, FlowStateEnum.KET_THUC, processFromList));
							List<Transition> transitionGQDBatDau = (List<Transition>) transitionRepo.findAll(transitionService.predicateFindFromCurrent(FlowStateEnum.BAT_DAU, processFromList));
							if (transitionGQDBatDau != null && transitionGQDBatDau.size() > 0) {
								transitionGQD = transitionGQDBatDau.get(0);
								listTransitionHaveBegin.addAll(transitionGQDBatDau);
								if (transitionGQDBatDau.get(0).getNextState().getType().equals(FlowStateEnum.KET_THUC)) {
									isQuyTrinhBat2DauKetThuc = true;
								}
//								if (processFromList.getVaiTro() != null && processFromList.getVaiTro().getId() == 2) {
//									isQuyTrinhBat2DauKetThuc = true;
//								}
							}
						}
						if (transitionGQD == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_GQD_INVALID.name(),
									ApiErrorEnum.TRANSITION_GQD_INVALID.getText(), ApiErrorEnum.TRANSITION_GQD_INVALID.getText());
						}

						if (!isQuyTrinhBat2DauKetThuc) {
							if (xuLyDon.getHanGiaiQuyet() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.HANGIAIQUYET_REQUIRED.name(),
										ApiErrorEnum.HANGIAIQUYET_REQUIRED.getText(), ApiErrorEnum.HANGIAIQUYET_REQUIRED.getText());
							}
							if (xuLyDon.getTrangThaiTTXM() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANGTHAITTXM_REQUIRED.name(),
										ApiErrorEnum.TRANGTHAITTXM_REQUIRED.getText(), ApiErrorEnum.TRANGTHAITTXM_REQUIRED.getText());
							} else if (xuLyDon.getTrangThaiTTXM().equals(TrangThaiTTXMEnum.GIAO_TTXM)) {
								
								if (xuLyDon.getDonViThamTraXacMinh() == null) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.name(),
											ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.getText(), ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.getText());
								}
								if (xuLyDon.getThoiHanBaoCaoKetQuaTTXM() == null) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.THOIHANBAOCAOKETQUATTXM_REQUIRED.name(),
											ApiErrorEnum.THOIHANBAOCAOKETQUATTXM_REQUIRED.getText(), ApiErrorEnum.THOIHANBAOCAOKETQUATTXM_REQUIRED.getText());
								}
								Predicate predicateTTXM = processService.predicateFindAllByDonVi(xuLyDon.getDonViThamTraXacMinh(), ProcessTypeEnum.THAM_TRA_XAC_MINH);
								List<Process> listProcessTTXM = (List<Process>) repoProcess.findAll(predicateTTXM);
								if (listProcessTTXM.size() < 1) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.name(),
											ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.getText(), ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.getText());
								}						
								Transition transitionTTXM = null;
								List<Transition> listTransitionHaveBeginTTXM = new ArrayList<Transition>();
								for (Process processFromList : listProcessTTXM) {
									transitionTTXM = transitionRepo.findOne(transitionService.predicateFindFromCurrent(FlowStateEnum.BAT_DAU, processFromList));
									if (transitionTTXM != null) {
										listTransitionHaveBeginTTXM.add(transitionTTXM);
									}
								}
								if (listTransitionHaveBeginTTXM.size() == 0) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_TTXM_INVALID.name(),
											ApiErrorEnum.TRANSITION_TTXM_INVALID.getText(), ApiErrorEnum.TRANSITION_TTXM_INVALID.getText());
								}	
								VaiTroEnum vaiTro1 = listTransitionHaveBeginTTXM.size() == 1 || listTransitionHaveBeginTTXM.size() == 2 ? 
										listTransitionHaveBeginTTXM.get(0).getProcess().getVaiTro().getLoaiVaiTro() : null;
								VaiTroEnum vaiTro2 = listTransitionHaveBeginTTXM.size() == 2 ? 
										listTransitionHaveBeginTTXM.get(1).getProcess().getVaiTro().getLoaiVaiTro() : null;
								xuLyDonHienTai = chuyenVienDeXuatThuLyVaGiaoTTXM(xuLyDon, xuLyDonHienTai, congChucId, vaiTro1, vaiTro2, !isQuyTrinhBat2DauKetThuc);
							} else {
								if (xuLyDon.getPhongBanGiaiQuyet() == null) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.name(),
											ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText(), ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText());
								}
								if (xuLyDon.getCanBoXuLyChiDinh() == null) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.CAN_BO_GIAI_QUYET_REQUIRED.name(),
											ApiErrorEnum.CAN_BO_GIAI_QUYET_REQUIRED.getText(), ApiErrorEnum.CAN_BO_GIAI_QUYET_REQUIRED.getText());
								}
								xuLyDonHienTai = chuyenVienDeXuatThuLyChoCanBoGiaiQuyet(xuLyDon, xuLyDonHienTai, congChucId, listTransitionHaveBegin.size() == 1 ? 
										listTransitionHaveBegin.get(0).getProcess().getVaiTro().getLoaiVaiTro() : null);
							}
							
						} else {
							if (xuLyDon.getHanGiaiQuyet() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.HANGIAIQUYET_REQUIRED.name(),
										ApiErrorEnum.HANGIAIQUYET_REQUIRED.getText(), ApiErrorEnum.HANGIAIQUYET_REQUIRED.getText());
							}
							if (xuLyDon.getTrangThaiTTXM() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANGTHAITTXM_REQUIRED.name(),
										ApiErrorEnum.TRANGTHAITTXM_REQUIRED.getText(), ApiErrorEnum.TRANGTHAITTXM_REQUIRED.getText());
							} else if (xuLyDon.getTrangThaiTTXM().equals(TrangThaiTTXMEnum.GIAO_TTXM)) {
								if (xuLyDon.getDonViThamTraXacMinh() == null) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.name(),
											ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.getText(), ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.getText());
								}
								if (xuLyDon.getThoiHanBaoCaoKetQuaTTXM() == null) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.THOIHANBAOCAOKETQUATTXM_REQUIRED.name(),
											ApiErrorEnum.THOIHANBAOCAOKETQUATTXM_REQUIRED.getText(), ApiErrorEnum.THOIHANBAOCAOKETQUATTXM_REQUIRED.getText());
								}
								Predicate predicateTTXM = processService.predicateFindAllByDonVi(xuLyDon.getDonViThamTraXacMinh(), ProcessTypeEnum.THAM_TRA_XAC_MINH);
								List<Process> listProcessTTXM = (List<Process>) repoProcess.findAll(predicateTTXM);
								if (listProcessTTXM.size() < 1) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.name(),
											ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.getText(), ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.getText());
								}						
								Transition transitionTTXM = null;
								List<Transition> listTransitionHaveBeginTTXM = new ArrayList<Transition>();
								for (Process processFromList : listProcessTTXM) {
									transitionTTXM = transitionRepo.findOne(transitionService.predicateFindFromCurrent(FlowStateEnum.BAT_DAU, processFromList));
									if (transitionTTXM != null) {
										listTransitionHaveBeginTTXM.add(transitionTTXM);
									}
								}
								if (listTransitionHaveBeginTTXM.size() == 0) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_TTXM_INVALID.name(),
											ApiErrorEnum.TRANSITION_TTXM_INVALID.getText(), ApiErrorEnum.TRANSITION_TTXM_INVALID.getText());
								}	
								VaiTroEnum vaiTro1 =  listTransitionHaveBeginTTXM.size() == 1 || listTransitionHaveBeginTTXM.size() == 2 ? 
										listTransitionHaveBeginTTXM.get(0).getProcess().getVaiTro().getLoaiVaiTro() : null;
								VaiTroEnum vaiTro2 =  listTransitionHaveBeginTTXM.size() == 2 ? 
										listTransitionHaveBeginTTXM.get(1).getProcess().getVaiTro().getLoaiVaiTro() : null;
								if (xuLyDon.getCanBoXuLyChiDinh() == null) {
									xuLyDon.setCanBoXuLyChiDinh(congChuc);
								}
								xuLyDonHienTai = chuyenVienDeXuatThuLyVaGiaoTTXM(xuLyDon, xuLyDonHienTai, congChucId, vaiTro1, vaiTro2, isQuyTrinhBat2DauKetThuc);
							} else {
								if (xuLyDon.getCanBoXuLyChiDinh() == null) {
									xuLyDon.setCanBoXuLyChiDinh(congChuc);
								}
								CongChuc canBoGiaiQuyet = congChucRepo.findOne(xuLyDon.getCanBoXuLyChiDinh().getId());
								xuLyDon.setPhongBanGiaiQuyet(canBoGiaiQuyet.getCoQuanQuanLy());
								xuLyDonHienTai = chuyenVienDeXuatThuLy(xuLyDon, xuLyDonHienTai, congChucId, listTransitionHaveBegin.size() == 1 ? 
										listTransitionHaveBegin.get(0).getProcess().getVaiTro().getLoaiVaiTro() : null);
							}
						}
						lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDonHienTai.getDon(), canBoXuLyChiDinh, 
								xuLyDonHienTai.getDonViXuLy().getDonVi());
						return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.CHUYEN_DON.equals(huongXuLyXLD)) {
						//Tim kiem vai tro dau tien o quy trinh
						
						if (xuLyDon.getThamQuyenGiaiQuyet() == null) { 
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
									ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
						}
						
						ThamSo thamSoTQGQHC = repoThamSo.findOne(thamSoService.predicateFindTen("TQGQ_HANH_CHINH"));
						
						//if (xuLyDon.getCoQuanTiepNhan() != null && xuLyDon.getCoQuanTiepNhan().getId() != 0L) {
						if (thamSoTQGQHC != null && Long.valueOf(thamSoTQGQHC.getGiaTri()) == xuLyDon.getThamQuyenGiaiQuyet().getId()) {
							Long donViXuLyId = xuLyDon.getCoQuanTiepNhan().getId();
							State beginState = repoState
									.findOne(serviceState.predicateFindByType(FlowStateEnum.BAT_DAU));
							Predicate predicateProcess = processService.predicateFindAllByDonVi(
									coQuanQuanLyRepo.findOne(donViXuLyId), ProcessTypeEnum.XU_LY_DON);
							List<Process> processes = (List<Process>) repoProcess.findAll(predicateProcess);
							
							// Vai tro tiep theo
							List<State> states = new ArrayList<State>();
							List<Process> listProcessHaveBeginState = new ArrayList<Process>();
							for (Process processFromList : processes) {								
								Predicate predicate = serviceState.predicateFindAll(beginState.getId(), processFromList,
										repoTransition);
								states = ((List<State>) repoState.findAll(predicate));
								if (states.size() > 0) {
									State state = states.get(0);
									if (!state.getType().equals(FlowStateEnum.KET_THUC)) {
										listProcessHaveBeginState.add(processFromList);
									}
								}
							}
							XuLyDon xuLyDonTiepTheo = new XuLyDon();
							xuLyDonTiepTheo = chuyenVienChuyenDon(xuLyDon, xuLyDonHienTai, congChucId, listProcessHaveBeginState);
							return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						}
						
						xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
						xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
						xuLyDonHienTai.setCongChuc(xuLyDonHienTai.getCanBoXuLyChiDinh() != null ? xuLyDonHienTai.getCanBoXuLyChiDinh() : congChucRepo.findOne(congChucId));
						xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
						xuLyDonHienTai.setCoQuanTiepNhan(null);
						xuLyDonHienTai.setDonViNgoaiHeThong(xuLyDon.getDonViNgoaiHeThong());
						don.setHuongXuLyXLD(huongXuLyXLD);
						don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
						don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DA_XU_LY);
						don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.CHUYEN_DON);
						don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
						don.setCanBoCoTheThuHoi(null);
						don.setCoQuanDangGiaiQuyet(xuLyDonHienTai.getDonViXuLy());
						don.setNgayKetThucXLD(Utils.localDateTimeNow());
						//don.setTuXuLyXLDGQD(false);
						don.setHoanThanhDon(true);
						don.setNgayThucHienKetQuaXuLy(Utils.localDateTimeNow());
						
						//tao lich su qua trinh xu ly don
						LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
						lichSuQTXLD.setDon(don);
						lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
						lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
						lichSuQTXLD.setTen(huongXuLyXLD.getText());
						DonViNgoaiHeThong donViNgoaiHT = donViNgoaiHeThongRepo.findOne(xuLyDonHienTai.getDonViNgoaiHeThong().getId());
						lichSuQTXLD.setNoiDung("Đơn vị tiếp nhận: " + donViNgoaiHT.getTen() + "\n\n" + xuLyDonHienTai.getNoiDungXuLy());
						lichSuQTXLD.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
						int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), 
								xuLyDonHienTai.getDonViXuLy().getId());
						lichSuQTXLD.setThuTuThucHien(thuTu);
						
						//donService.save(don, congChucId);
						Utils.changeQuyenTuXuLy(don, false, false, false);
						lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
						lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDonHienTai.getDon(), canBoXuLyChiDinh, 
								xuLyDonHienTai.getDonViXuLy().getDonVi());
						return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.equals(huongXuLyXLD)
							|| HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.equals(huongXuLyXLD)
							|| HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.equals(huongXuLyXLD)
							|| HuongXuLyXLDEnum.HUONG_DAN_VIET_LAI_DON.equals(huongXuLyXLD)
							|| HuongXuLyXLDEnum.KHONG_XU_LY_NEU_LY_DO.equals(huongXuLyXLD)) {
						if (xuLyDon.getThamQuyenGiaiQuyet() != null) { 
							xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
						}
						if (HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.equals(huongXuLyXLD)
								|| HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.equals(huongXuLyXLD)) {
							don.setHoanThanhDon(true);
							if (HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.equals(huongXuLyXLD)) {
								don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.KHONG_DU_DIEU_KIEN_THU_LY);
							} else if (HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.equals(huongXuLyXLD)) {
								don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.LUU_DON_VA_THEO_DOI);
							} else if (HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.equals(huongXuLyXLD)) {
								don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.TRA_DON_VA_HUONG_DAN);
							}
						} else {
//							don.setHoanThanhDon(true);
//							don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.TRA_DON_VA_HUONG_DAN);
							
							if (HuongXuLyXLDEnum.KHONG_XU_LY_NEU_LY_DO.equals(huongXuLyXLD)) {
								don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.KHONG_XU_LY_NEU_LY_DO);
							}
							if (HuongXuLyXLDEnum.HUONG_DAN_VIET_LAI_DON.equals(huongXuLyXLD)) {
								don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.HUONG_DAN_VIET_LAI_DON);
							}
						}
						xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
						xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						don.setHuongXuLyXLD(huongXuLyXLD);
						don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());

						//don.setHuongXuLyXLD(HuongXuLyXLDEnum.DINH_CHI);
						don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DA_XU_LY);
						don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
						don.setCanBoCoTheThuHoi(null);
						don.setCoQuanDangGiaiQuyet(xuLyDonHienTai.getDonViXuLy());
						don.setNgayKetThucXLD(Utils.localDateTimeNow());
						don.setCanBoXuLyChiDinh(null);
						don.setNgayThucHienKetQuaXuLy(Utils.localDateTimeNow());
						
						//tao lich su qua trinh xu ly don
						LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
						lichSuQTXLD.setDon(don);
						lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
						lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
						lichSuQTXLD.setTen(huongXuLyXLD.getText());
						lichSuQTXLD.setNoiDung(xuLyDonHienTai.getNoiDungXuLy());
						lichSuQTXLD.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
						int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), 
								xuLyDonHienTai.getDonViXuLy().getId());
						lichSuQTXLD.setThuTuThucHien(thuTu);
						
						donService.save(don, congChucId);
						lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
						lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDonHienTai.getDon(), canBoXuLyChiDinh, 
								xuLyDonHienTai.getDonViXuLy().getDonVi());
						return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.equals(huongXuLyXLD)
							&& xuLyDonHienTai.isDonChuyen()) {
						Predicate predicate = processService.predicateFindAllByDonVi(xuLyDonHienTai.getCoQuanChuyenDon().getDonVi(), ProcessTypeEnum.XU_LY_DON);
						List<Process> listProcessXLD = (List<Process>) repoProcess.findAll(predicate);
						if (listProcessXLD.size() < 1) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_INVALID.name(),
							ApiErrorEnum.TRANSITION_INVALID.getText(), ApiErrorEnum.TRANSITION_INVALID.getText());
						}
						Transition transitionXLD = null;
						for (Process processFromList : listProcessXLD) {
							List<Transition> transitionXLDs = new ArrayList<Transition>();
							transitionXLDs.addAll((List<Transition>) transitionRepo.findAll(transitionService.predicateFindFromCurrentAndNext(FlowStateEnum.LANH_DAO_GIAO_VIEC_CAN_BO, FlowStateEnum.KET_THUC, processFromList)));
							transitionXLDs.addAll((List<Transition>) transitionRepo.findAll(transitionService.predicateFindFromCurrentAndNext(FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO, FlowStateEnum.KET_THUC, processFromList)));
							if (transitionXLDs != null && transitionXLDs.size() > 0) {
								transitionXLD = transitionXLDs.get(0);
								if (transitionXLD != null) {
									break;
								}
							}
						}
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = chuyenVienTraLaiDonKhongDungThamQuyen(xuLyDon, xuLyDonHienTai, congChucId, transitionXLD);
						lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDonHienTai.getDon(), canBoXuLyChiDinh, 
								xuLyDonHienTai.getDonViXuLy().getDonVi());
						return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					}
				}
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
				
			}
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/xuLyDons")
	@ApiOperation(value = "Quy trình xử lý đơn", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Thêm quy trình xử lý đơn thành công", response = XuLyDon.class) })
	public ResponseEntity<Object> createWorkflow(
			@RequestHeader(value = "Authorization", required = true) String authorization, @RequestBody XuLyDon xuLyDon,
			PersistentEntityResourceAssembler eass) {
		try {
			NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA);
			CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
			if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
					&& commonProfile.containsAttribute("coQuanQuanLyId")) {				
				if (xuLyDon.getDon() == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DON_REQUIRED.name(),
							ApiErrorEnum.DON_REQUIRED.getText(), ApiErrorEnum.DON_REQUIRED.getText());
				}
				if (xuLyDon.getNextState() == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.NEXT_STATE_REQUIRED.name(),
							ApiErrorEnum.NEXT_STATE_REQUIRED.getText(), ApiErrorEnum.NEXT_STATE_REQUIRED.getText());
				}
				State nextStage = repoState.findOne(stateService.predicateFindOne(xuLyDon.getNextState().getId()));
				Long donId = xuLyDon.getDon().getId();
				Don don = donRepo.findOne(donService.predicateFindOne(donId));
				
//				Bo phanLoaiDon - cap nhat 16/11
//				if (don.getPhanLoaiDon() == null && xuLyDon.getPhanLoaiDon() == null) {
//					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHANLOAIDON_REQUIRED.name(),
//							ApiErrorEnum.PHANLOAIDON_REQUIRED.getText(), ApiErrorEnum.PHANLOAIDON_REQUIRED.getText());
//				}
				
				if (don.getProcessType() == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_TYPE_REQUIRED.name(),
							ApiErrorEnum.PROCESS_TYPE_REQUIRED.getText(), ApiErrorEnum.PROCESS_TYPE_REQUIRED.getText());
				}
				String vaiTroNguoiDungHienTai = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro")
						.toString();
				Long congChucId = Long.valueOf(
						profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());

				CongChuc congChuc = congChucRepo.findOne(congChucId);
				List<VaiTroEnum> listVaiTro = congChuc.getNguoiDung().getVaiTros().stream().map(d -> d.getLoaiVaiTro()).distinct().collect(Collectors.toList());
				
				boolean isOwner = don.getNguoiTao().getId() == null || don.getNguoiTao().getId().equals(0L) ? true
						: congChucId.longValue() == don.getNguoiTao().getId().longValue() ? true : false;
				CoQuanQuanLy donVi = congChuc.getCoQuanQuanLy().getDonVi();
				
				List<Process> listProcess = new ArrayList<Process>();
				for (VaiTroEnum vaiTroEnum : listVaiTro) {
					Process process = repoProcess.findOne(processService.predicateFindAll(vaiTroEnum.toString(), donVi, isOwner, don.getProcessType()));			
					if (process == null && isOwner) {
						process = repoProcess.findOne(processService.predicateFindAll(vaiTroEnum.toString(), donVi, false, don.getProcessType()));
					}
					if (process != null) {
						listProcess.add(process);
					}
				}
				
				if (listProcess.size() < 1) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_NOT_FOUND.name(),
							ApiErrorEnum.PROCESS_NOT_FOUND.getText(), ApiErrorEnum.PROCESS_NOT_FOUND.getText());
				}
				
				List<State> listState = new ArrayList<State>();
				Process process = null;
				for (Process processFromList : listProcess) {
					Predicate predicate = serviceState.predicateFindAll(don.getCurrentState().getId(), processFromList, transitionRepo);
					listState = ((List<State>) repoState.findAll(predicate));
					if (listState.size() > 0) {
						process = processFromList;
						vaiTroNguoiDungHienTai = process.getVaiTro().getLoaiVaiTro().toString();
						break;
					}
				}
				
				Transition transition = transitionRepo.findOne(
						transitionService.predicatePrivileged(don.getCurrentState(), xuLyDon.getNextState(), process));

				if (transition == null) {				
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_FORBIDDEN.name(),
							ApiErrorEnum.TRANSITION_FORBIDDEN.getText(), ApiErrorEnum.TRANSITION_FORBIDDEN.getText());
				}

				Long coQuanQuanLyId = Long.valueOf(
						profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());				
				Long donViId = Long.valueOf(
						profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
				boolean isChuyenVienNhapLieu = Boolean.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("isChuyenVienNhapLieu").toString());
				boolean coQuyTrinh = kiemTraDonViCoQuyTrinhXLD(donViId);
				XuLyDon xuLyDonHienTai = xuLyDonService.predFindXuLyDonHienTai(repo, donId, donViId, coQuanQuanLyId, congChucId, vaiTroNguoiDungHienTai,
						coQuyTrinh, isChuyenVienNhapLieu);					
				if (xuLyDonHienTai != null) {
					FlowStateEnum currentState = don.getCurrentState() != null ? don.getCurrentState().getType() : null;
					FlowStateEnum nextState = nextStage.getType();
					if (!FlowStateEnum.LANH_DAO_THU_HOI.equals(nextState) && !FlowStateEnum.TRUONG_PHONG_THU_HOI.equals(nextState)) {
						xuLyDonHienTai.setNextState(xuLyDon.getNextState());
						xuLyDonHienTai.setNextForm(transition.getForm());
					} else {
						xuLyDonHienTai.setOld(true);
					}
					// Thong tin xu ly don			

					if (FlowStateEnum.TRINH_LANH_DAO.equals(nextState)) {
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = trinhDon(xuLyDon, xuLyDonHienTai, coQuanQuanLyId, donViId, congChucId, vaiTroNguoiDungHienTai);
						return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} if (FlowStateEnum.TRINH_TRUONG_PHONG.equals(nextState)) {
						ThamSo thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("CQQL_BAN_TIEP_CONG_DAN"));
						ThamSo thamSoUBNDTP = thamSoRepository.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));						
						if (thamSo == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TSBANTIEPCONGDAN_NOT_FOUND.name(),
									ApiErrorEnum.TSBANTIEPCONGDAN_NOT_FOUND.getText(), ApiErrorEnum.TSBANTIEPCONGDAN_NOT_FOUND.getText());
						} 
						CoQuanQuanLy banTiepCongDan = coQuanQuanLyRepo.findOne(Long.parseLong(thamSo.getGiaTri()));
						if (banTiepCongDan == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.BANTIEPCONGDAN_NOT_FOUND.name(),
									ApiErrorEnum.BANTIEPCONGDAN_NOT_FOUND.getText(), ApiErrorEnum.BANTIEPCONGDAN_NOT_FOUND.getText());
						}
						
						if (Long.valueOf(thamSoUBNDTP.getGiaTri()) == donViId) {
							xuLyDon.setCanBoXuLyChiDinh(null);
						}
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = trinhTruongPhong(xuLyDon, xuLyDonHienTai, donViId, congChucId, banTiepCongDan, vaiTroNguoiDungHienTai);
						return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					}  else if (FlowStateEnum.LANH_DAO_GIAO_VIEC_TRUONG_PHONG.equals(nextState)) {
						if (xuLyDon.getPhongBanXuLyChiDinh() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONGBANXULYCHIDINH_REQUIRED.name(),
									ApiErrorEnum.PHONGBANXULYCHIDINH_REQUIRED.getText(), ApiErrorEnum.PHONGBANXULYCHIDINH_REQUIRED.getText());
						}					
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = lanhDaoGiaoViec(xuLyDon, xuLyDonHienTai, donViId, congChucId, nextState);
						return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.LANH_DAO_GIAO_VIEC_CAN_BO.equals(nextState)) {
						if (xuLyDon.getPhongBanXuLyChiDinh() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONGBANXULYCHIDINH_REQUIRED.name(),
									ApiErrorEnum.PHONGBANXULYCHIDINH_REQUIRED.getText(), ApiErrorEnum.PHONGBANXULYCHIDINH_REQUIRED.getText());
						}
						if (xuLyDon.getCanBoXuLyChiDinh() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.name(),
									ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText(), ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText());
						}
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						
						xuLyDonTiepTheo = lanhDaoGiaoViec(xuLyDon, xuLyDonHienTai, donViId, congChucId, nextState);
						return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.TRUONG_PHONG_DE_XUAT_GIAO_VIEC_LAI.equals(nextState)) {
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = truongPhongGiaoViecLai(xuLyDon, xuLyDonHienTai, donViId, congChucId);
						return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.LANH_DAO_THU_HOI.equals(nextState)) {
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						XuLyDon xuLyDonThuHoi = xuLyDonService.predFindXuLyDonThuHoi(repo, donId, donViId, congChucId);

						if (xuLyDonThuHoi != null) {
							xuLyDonTiepTheo = lanhDaoThuHoi(xuLyDon, xuLyDonThuHoi, congChucId, VaiTroEnum.LANH_DAO);
							return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						}
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_INVALID.name(),
								ApiErrorEnum.DATA_INVALID.getText(), ApiErrorEnum.DATA_INVALID.getText());
					} else if (FlowStateEnum.TRUONG_PHONG_THU_HOI.equals(nextState)) {
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						XuLyDon xuLyDonThuHoi = xuLyDonService.predFindXuLyDonThuHoi(repo, donId, donViId, congChucId);
						if (xuLyDonThuHoi != null) {
							xuLyDonTiepTheo = lanhDaoThuHoi(xuLyDon, xuLyDonThuHoi, congChucId, VaiTroEnum.TRUONG_PHONG);
							return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						}
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_INVALID.name(),
								ApiErrorEnum.DATA_INVALID.getText(), ApiErrorEnum.DATA_INVALID.getText());
					} else if (FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO.equals(nextState)) {
						if (xuLyDon.getCanBoXuLyChiDinh() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.name(),
									ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText(), ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText());
						}
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = truongPhongGiaoViec(xuLyDon, xuLyDonHienTai, congChucId);
						return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);

					} else if (FlowStateEnum.CAN_BO_DE_XUAT_GIAO_VIEC_LAI.equals(nextState)) {
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = chuyenVienGiaoViecLai(xuLyDon, xuLyDonHienTai, congChucId);
						return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);

					} else if (FlowStateEnum.YEU_CAU_GAP_LANH_DAO.equals(nextState)) {
						xuLyDonHienTai.setHuongXuLy(HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO);
						xuLyDonHienTai = chuyenVienChuyenChoVanThuYeuCauGapLanhDao(xuLyDon, xuLyDonHienTai, congChucId);					
						return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.CAN_BO_DE_XUAT_HUONG_XU_LY.equals(nextState)) {
						HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy();
						// huongXuLy
						if (huongXuLyXLD == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.HUONGXULY_REQUIRED.name(),
									ApiErrorEnum.HUONGXULY_REQUIRED.getText(), ApiErrorEnum.HUONGXULY_REQUIRED.getText());
						}
						xuLyDonHienTai.setHuongXuLy(huongXuLyXLD);
						xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
						xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
						if (HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO.equals(huongXuLyXLD)) {
							xuLyDonHienTai = chuyenVienChuyenChoVanThuYeuCauGapLanhDao(xuLyDon, xuLyDonHienTai, congChucId);
							return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
						} else if (HuongXuLyXLDEnum.DE_XUAT_THU_LY.equals(huongXuLyXLD)) {
							if (xuLyDon.getPhongBanGiaiQuyet() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.name(),
										ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText(), ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText());
							}
							XuLyDon xuLyDonTiepTheo = new XuLyDon();
							xuLyDonTiepTheo = chuyenVienChuyenChoVanThuDeXuatThuLy(xuLyDon, xuLyDonHienTai, congChucId);
							return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (HuongXuLyXLDEnum.CHUYEN_DON.equals(huongXuLyXLD)) {
							XuLyDon xuLyDonTiepTheo = new XuLyDon();
							xuLyDonTiepTheo = chuyenVienChuyenDonChoVanThu(xuLyDon, xuLyDonHienTai, congChucId);
							return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.equals(huongXuLyXLD)
								|| HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.equals(huongXuLyXLD)
								|| HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.equals(huongXuLyXLD)) {
							XuLyDon xuLyDonTiepTheo = new XuLyDon();
							xuLyDonTiepTheo = chuyenVienXuLyKhongDuDieuKienThuLy(xuLyDon, xuLyDonHienTai, congChucId);
							return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.equals(huongXuLyXLD)
								&& xuLyDonHienTai.isDonChuyen()) {
							XuLyDon xuLyDonTiepTheo = new XuLyDon();
							xuLyDonTiepTheo = chuyenVienTraLaiDonKhongDungThamQuyenChoVanThu(xuLyDon, xuLyDonHienTai, congChucId);
							return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						}
					} else if (FlowStateEnum.CAN_BO_DE_XUAT_HUONG_XU_LY.equals(currentState)
							&& FlowStateEnum.KET_THUC.equals(nextState)) {
						// Xu ly don co van thu
						HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy();
						if (huongXuLyXLD == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.HUONGXULY_REQUIRED.name(),
									ApiErrorEnum.HUONGXULY_REQUIRED.getText(), ApiErrorEnum.HUONGXULY_REQUIRED.getText());
						}
						if (HuongXuLyXLDEnum.CHUYEN_DON.equals(huongXuLyXLD)) {
							XuLyDon xuLyDonTiepTheo = new XuLyDon();
							xuLyDonTiepTheo = vanThuChuyenDon(xuLyDon, xuLyDonHienTai, congChucId);
							return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.equals(huongXuLyXLD)
								&& xuLyDonHienTai.isDonChuyen()) {
							XuLyDon xuLyDonTiepTheo = new XuLyDon();
							xuLyDonTiepTheo = vanThuTraLaiDonKhongDungThamQuyen(xuLyDon, xuLyDonHienTai, congChucId);
							return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (HuongXuLyXLDEnum.DE_XUAT_THU_LY.equals(huongXuLyXLD)) {
							xuLyDonHienTai = deXuatThuLy(xuLyDon, xuLyDonHienTai, congChucId);
							return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
						} else if (HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.equals(huongXuLyXLD)
								|| HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.equals(huongXuLyXLD)
								|| HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.equals(huongXuLyXLD)) {
							if (HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.equals(huongXuLyXLD)
									|| HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.equals(huongXuLyXLD)) {
								don.setHoanThanhDon(true);
							}
							xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
							xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
							don.setHuongXuLyXLD(huongXuLyXLD);
							don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
							don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
							don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
							// set ngay ket thuc cho don
							don.setNgayKetThucXLD(Utils.localDateTimeNow());
							
//							Bo phanLoaiDon - cap nhat 16/11
//							if (xuLyDon.getPhanLoaiDon() != null) {
//								don.setPhanLoaiDon(xuLyDon.getPhanLoaiDon());
//								if (PhanLoaiDonEnum.DU_DIEU_KIEN_XU_LY.equals(xuLyDon.getPhanLoaiDon())) {
//									don.setLyDoKhongDuDieuKienThuLy(null);
//								} else {
//									don.setLyDoKhongDuDieuKienThuLy(xuLyDon.getLyDoKhongDuDieuKienThuLy());
//								}
//							}
							
							donService.save(don, congChucId);
							return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
						}
					} else if ((FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO.equals(currentState) 
							|| FlowStateEnum.LANH_DAO_GIAO_VIEC_CAN_BO.equals(currentState)
							|| FlowStateEnum.TRINH_TRUONG_PHONG.equals(currentState)
							|| FlowStateEnum.LANH_DAO_GIAO_VIEC_TRUONG_PHONG.equals(currentState)
							|| FlowStateEnum.BAT_DAU.equals(currentState))
							&& FlowStateEnum.KET_THUC.equals(nextState)) {
						// Xu ly don khong co van thu
						HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy();
						if (huongXuLyXLD == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.HUONGXULY_REQUIRED.name(),
									ApiErrorEnum.HUONGXULY_REQUIRED.getText(), ApiErrorEnum.HUONGXULY_REQUIRED.getText());
						}
						xuLyDonHienTai.setHuongXuLy(huongXuLyXLD);
						xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
						if (HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO.equals(huongXuLyXLD)) {
							xuLyDonHienTai = chuyenVienChuyenChoVanThuYeuCauGapLanhDao(xuLyDon, xuLyDonHienTai, congChucId);
							return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
						} else if (HuongXuLyXLDEnum.DE_XUAT_THU_LY.equals(huongXuLyXLD)) {
//							//TODO tim kiem don vi do co phong ban hay khong
//							List<CoQuanQuanLy> listPhongBan = new ArrayList<CoQuanQuanLy>();
//							listPhongBan = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(
//									coQuanQuanLyService.predicateFindPhongBanDonBanDonvi(donVi.getId(), thamSoService, thamSoRepo));	
							Transition transitionGQD = null;					
							
							//Tim kiem VaiTro giai quyet don
							Predicate predicate = processService.predicateFindAllByDonVi(donVi, ProcessTypeEnum.GIAI_QUYET_DON);
							List<Process> listProcessGQD = (List<Process>) repoProcess.findAll(predicate);
							if (listProcessGQD.size() < 1) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_GQD_NOT_FOUND.name(),
										ApiErrorEnum.PROCESS_GQD_NOT_FOUND.getText(), ApiErrorEnum.PROCESS_GQD_NOT_FOUND.getText());
							}
							
							List<Transition> listTransitionHaveBegin = new ArrayList<>();
							boolean isQuyTrinhBat2DauKetThuc = false;
							for (Process processFromList : listProcessGQD) {
								//Transition transitionGQDBatDau = transitionRepo.findOne(transitionService.predicateFindFromCurrentAndNext(FlowStateEnum.BAT_DAU, FlowStateEnum.KET_THUC, processFromList));
								List<Transition> transitionGQDBatDau = (List<Transition>) transitionRepo.findAll(transitionService.predicateFindFromCurrent(FlowStateEnum.BAT_DAU, processFromList));
								if (transitionGQDBatDau != null && transitionGQDBatDau.size() > 0) {
									transitionGQD = transitionGQDBatDau.get(0);
									listTransitionHaveBegin.addAll(transitionGQDBatDau);
									if (transitionGQDBatDau.get(0).getNextState().getType().equals(FlowStateEnum.KET_THUC)) {
										isQuyTrinhBat2DauKetThuc = true;
									}
//									if (processFromList.getVaiTro() != null && processFromList.getVaiTro().getId() == 2) {
//										isQuyTrinhBat2DauKetThuc = true;
//									}
								}
							}
							if (transitionGQD == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_GQD_INVALID.name(),
										ApiErrorEnum.TRANSITION_GQD_INVALID.getText(), ApiErrorEnum.TRANSITION_GQD_INVALID.getText());
							}

							if (!isQuyTrinhBat2DauKetThuc) {
								if (xuLyDon.getHanGiaiQuyet() == null) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.HANGIAIQUYET_REQUIRED.name(),
											ApiErrorEnum.HANGIAIQUYET_REQUIRED.getText(), ApiErrorEnum.HANGIAIQUYET_REQUIRED.getText());
								}
								if (xuLyDon.getTrangThaiTTXM() == null) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANGTHAITTXM_REQUIRED.name(),
											ApiErrorEnum.TRANGTHAITTXM_REQUIRED.getText(), ApiErrorEnum.TRANGTHAITTXM_REQUIRED.getText());
								} else if (xuLyDon.getTrangThaiTTXM().equals(TrangThaiTTXMEnum.GIAO_TTXM)) {
									
									if (xuLyDon.getDonViThamTraXacMinh() == null) {
										return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.name(),
												ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.getText(), ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.getText());
									}
									if (xuLyDon.getThoiHanBaoCaoKetQuaTTXM() == null) {
										return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.THOIHANBAOCAOKETQUATTXM_REQUIRED.name(),
												ApiErrorEnum.THOIHANBAOCAOKETQUATTXM_REQUIRED.getText(), ApiErrorEnum.THOIHANBAOCAOKETQUATTXM_REQUIRED.getText());
									}
									Predicate predicateTTXM = processService.predicateFindAllByDonVi(xuLyDon.getDonViThamTraXacMinh(), ProcessTypeEnum.THAM_TRA_XAC_MINH);
									List<Process> listProcessTTXM = (List<Process>) repoProcess.findAll(predicateTTXM);
									if (listProcessTTXM.size() < 1) {
										return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.name(),
												ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.getText(), ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.getText());
									}						
									Transition transitionTTXM = null;
									List<Transition> listTransitionHaveBeginTTXM = new ArrayList<Transition>();
									for (Process processFromList : listProcessTTXM) {
										transitionTTXM = transitionRepo.findOne(transitionService.predicateFindFromCurrent(FlowStateEnum.BAT_DAU, processFromList));
										if (transitionTTXM != null) {
											listTransitionHaveBeginTTXM.add(transitionTTXM);
										}
									}
									if (transitionTTXM == null) {
										return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_TTXM_INVALID.name(),
												ApiErrorEnum.TRANSITION_TTXM_INVALID.getText(), ApiErrorEnum.TRANSITION_TTXM_INVALID.getText());
									}	
									VaiTroEnum vaiTro1 = listTransitionHaveBeginTTXM.size() == 1 || listTransitionHaveBeginTTXM.size() == 2 ? 
											listTransitionHaveBeginTTXM.get(0).getProcess().getVaiTro().getLoaiVaiTro() : null;
								    VaiTroEnum vaiTro2 = listTransitionHaveBeginTTXM.size() == 2 ? listTransitionHaveBeginTTXM.get(1).getProcess().getVaiTro().getLoaiVaiTro() : null;
								    xuLyDonHienTai = chuyenVienDeXuatThuLyVaGiaoTTXM(xuLyDon, xuLyDonHienTai, congChucId, vaiTro1, vaiTro2, !isQuyTrinhBat2DauKetThuc);
								} else {
									if (xuLyDon.getPhongBanGiaiQuyet() == null) {
										return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.name(),
												ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText(), ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText());
									}
									if (xuLyDon.getCanBoXuLyChiDinh() == null) {
										return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.CAN_BO_GIAI_QUYET_REQUIRED.name(),
												ApiErrorEnum.CAN_BO_GIAI_QUYET_REQUIRED.getText(), ApiErrorEnum.CAN_BO_GIAI_QUYET_REQUIRED.getText());
									}
									xuLyDonHienTai = chuyenVienDeXuatThuLyChoCanBoGiaiQuyet(xuLyDon, xuLyDonHienTai, congChucId, listTransitionHaveBegin.size() == 1 ? 
											listTransitionHaveBegin.get(0).getProcess().getVaiTro().getLoaiVaiTro() : null);
								}
								
							} else {
								if (xuLyDon.getHanGiaiQuyet() == null) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.HANGIAIQUYET_REQUIRED.name(),
											ApiErrorEnum.HANGIAIQUYET_REQUIRED.getText(), ApiErrorEnum.HANGIAIQUYET_REQUIRED.getText());
								}
								if (xuLyDon.getTrangThaiTTXM() == null) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANGTHAITTXM_REQUIRED.name(),
											ApiErrorEnum.TRANGTHAITTXM_REQUIRED.getText(), ApiErrorEnum.TRANGTHAITTXM_REQUIRED.getText());
								} else if (xuLyDon.getTrangThaiTTXM().equals(TrangThaiTTXMEnum.GIAO_TTXM)) {
									if (xuLyDon.getDonViThamTraXacMinh() == null) {
										return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.name(),
												ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.getText(), ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.getText());
									}
									if (xuLyDon.getThoiHanBaoCaoKetQuaTTXM() == null) {
										return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.THOIHANBAOCAOKETQUATTXM_REQUIRED.name(),
												ApiErrorEnum.THOIHANBAOCAOKETQUATTXM_REQUIRED.getText(), ApiErrorEnum.THOIHANBAOCAOKETQUATTXM_REQUIRED.getText());
									}
									Predicate predicateTTXM = processService.predicateFindAllByDonVi(xuLyDon.getDonViThamTraXacMinh(), ProcessTypeEnum.THAM_TRA_XAC_MINH);
									List<Process> listProcessTTXM = (List<Process>) repoProcess.findAll(predicateTTXM);
									if (listProcessTTXM.size() < 1) {
										return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.name(),
												ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.getText(), ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.getText());
									}						
									Transition transitionTTXM = null;
									List<Transition> listTransitionHaveBeginTTXM = new ArrayList<Transition>();
									for (Process processFromList : listProcessTTXM) {
										transitionTTXM = transitionRepo.findOne(transitionService.predicateFindFromCurrent(FlowStateEnum.BAT_DAU, processFromList));
										if (transitionTTXM != null) {
											listTransitionHaveBeginTTXM.add(transitionTTXM);
										}
									}
									if (listTransitionHaveBeginTTXM.size() == 0) {
										return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_TTXM_INVALID.name(),
												ApiErrorEnum.TRANSITION_TTXM_INVALID.getText(), ApiErrorEnum.TRANSITION_TTXM_INVALID.getText());
									}	
									VaiTroEnum vaiTro1 = listTransitionHaveBeginTTXM.size() == 1 || listTransitionHaveBeginTTXM.size() == 2 ? 
											listTransitionHaveBeginTTXM.get(0).getProcess().getVaiTro().getLoaiVaiTro() : null;
									VaiTroEnum vaiTro2 = listTransitionHaveBeginTTXM.size() == 2 ? 
											listTransitionHaveBeginTTXM.get(1).getProcess().getVaiTro().getLoaiVaiTro() : null;
									xuLyDonHienTai = chuyenVienDeXuatThuLyVaGiaoTTXM(xuLyDon, xuLyDonHienTai, congChucId, vaiTro1, vaiTro2, isQuyTrinhBat2DauKetThuc);
								} else {
									if (xuLyDon.getCanBoXuLyChiDinh() == null) {
										xuLyDon.setCanBoXuLyChiDinh(congChuc);
									}
									CongChuc canBoGiaiQuyet = congChucRepo.findOne(xuLyDon.getCanBoXuLyChiDinh().getId());
									xuLyDon.setPhongBanGiaiQuyet(canBoGiaiQuyet.getCoQuanQuanLy());
									xuLyDonHienTai = chuyenVienDeXuatThuLy(xuLyDon, xuLyDonHienTai, congChucId, listTransitionHaveBegin.size() == 1 ? 
											listTransitionHaveBegin.get(0).getProcess().getVaiTro().getLoaiVaiTro() : null);
								}
							}
							return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
						} else if (HuongXuLyXLDEnum.CHUYEN_DON.equals(huongXuLyXLD)) {
							//Tim kiem vai tro dau tien o quy trinh
							
							if (xuLyDon.getThamQuyenGiaiQuyet() == null) { 
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
										ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
							}
							ThamSo thamSoTQGQHC = repoThamSo.findOne(thamSoService.predicateFindTen("TQGQ_HANH_CHINH"));
							
//							if (xuLyDon.getCoQuanTiepNhan() != null && xuLyDon.getCoQuanTiepNhan().getId() != 0L) {
							if (thamSoTQGQHC != null && Long.valueOf(thamSoTQGQHC.getGiaTri()) == xuLyDon.getThamQuyenGiaiQuyet().getId()) {
								Long donViXuLyId = xuLyDon.getCoQuanTiepNhan().getId();
								State beginState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.BAT_DAU));
								Predicate predicateProcess = processService.predicateFindAllByDonVi(coQuanQuanLyRepo.findOne(donViXuLyId), ProcessTypeEnum.XU_LY_DON);
								List<Process> processes = (List<Process>) repoProcess.findAll(predicateProcess);
								
								//Vai tro tiep theo
								List<State> states = new ArrayList<State>();
								List<Process> listProcessHaveBeginState = new ArrayList<Process>();
								for (Process processFromList : processes) {
									Predicate predicate = serviceState.predicateFindAll(beginState.getId(), processFromList, repoTransition);
									states = ((List<State>) repoState.findAll(predicate));						
									if (states.size() > 0) {
										State state = states.get(0);
										if (!state.getType().equals(FlowStateEnum.KET_THUC)) {
											listProcessHaveBeginState.add(processFromList);
										}						
									}
								}
								XuLyDon xuLyDonTiepTheo = new XuLyDon();
								xuLyDonTiepTheo = chuyenVienChuyenDon(xuLyDon, xuLyDonHienTai, congChucId, listProcessHaveBeginState);
								return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
							}
							
							xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
							xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
							xuLyDonHienTai.setCongChuc(xuLyDonHienTai.getCanBoXuLyChiDinh() != null ? xuLyDonHienTai.getCanBoXuLyChiDinh() : congChucRepo.findOne(congChucId));
							xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
							xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
							xuLyDonHienTai.setCoQuanTiepNhan(null);
							xuLyDonHienTai.setDonViNgoaiHeThong(xuLyDon.getDonViNgoaiHeThong());
							don.setHuongXuLyXLD(huongXuLyXLD);
							don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
							don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
							don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DA_XU_LY);
							don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.CHUYEN_DON);
							don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
							don.setCanBoCoTheThuHoi(null);
							don.setCoQuanDangGiaiQuyet(xuLyDonHienTai.getDonViXuLy());
							don.setNgayKetThucXLD(Utils.localDateTimeNow());
							//don.setTuXuLyXLDGQD(false);
							don.setHoanThanhDon(true);
							don.setNgayThucHienKetQuaXuLy(Utils.localDateTimeNow());
							
//							Bo phanLoaiDon - cap nhat 16/11
//							if (xuLyDon.getPhanLoaiDon() != null) {
//								don.setPhanLoaiDon(xuLyDon.getPhanLoaiDon());
//								if (PhanLoaiDonEnum.DU_DIEU_KIEN_XU_LY.equals(xuLyDon.getPhanLoaiDon())) {
//									don.setLyDoKhongDuDieuKienThuLy(null);
//								} else {
//									don.setLyDoKhongDuDieuKienThuLy(xuLyDon.getLyDoKhongDuDieuKienThuLy());
//								}
//							}
							
							if (!coQuyTrinh) { 
								don.setLanhDaoDuyet(true);
							}
							
							//tao lich su qua trinh xu ly don
							LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
							lichSuQTXLD.setDon(don);
							lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
							lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
							lichSuQTXLD.setTen(huongXuLyXLD.getText());
							DonViNgoaiHeThong donViNgoaiHT = donViNgoaiHeThongRepo.findOne(xuLyDonHienTai.getDonViNgoaiHeThong().getId());
							lichSuQTXLD.setNoiDung("Đơn vị tiếp nhận: " + donViNgoaiHT.getTen() + "\n\n" + xuLyDonHienTai.getNoiDungXuLy());
							lichSuQTXLD.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
							int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), 
									xuLyDonHienTai.getDonViXuLy().getId());
							lichSuQTXLD.setThuTuThucHien(thuTu);
							
							//donService.save(don, congChucId);
							Utils.changeQuyenTuXuLy(don, false, false, false);
							lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
							
							return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
						} else if (HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.equals(huongXuLyXLD)
								|| HuongXuLyXLDEnum.KHONG_XU_LY_NEU_LY_DO.equals(huongXuLyXLD)
								|| HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.equals(huongXuLyXLD)
								|| HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.equals(huongXuLyXLD)
								|| HuongXuLyXLDEnum.HUONG_DAN_VIET_LAI_DON.equals(huongXuLyXLD)) {
							if (xuLyDon.getThamQuyenGiaiQuyet() != null) { 
								xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
							}
							if (HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.equals(huongXuLyXLD)
									|| HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.equals(huongXuLyXLD)
									|| HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.equals(huongXuLyXLD)) {
								don.setHoanThanhDon(true);
								if (HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.equals(huongXuLyXLD)) {
									don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.KHONG_DU_DIEU_KIEN_THU_LY);
								} else if (HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.equals(huongXuLyXLD)) {
									don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.LUU_DON_VA_THEO_DOI);
								} else if (HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.equals(huongXuLyXLD)) {
									don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.TRA_DON_VA_HUONG_DAN);
								}
							} else {
//								don.setHoanThanhDon(true);
//								don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.TRA_DON_VA_HUONG_DAN);
								if (HuongXuLyXLDEnum.KHONG_XU_LY_NEU_LY_DO.equals(huongXuLyXLD)) {
									don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.KHONG_XU_LY_NEU_LY_DO);
								}
								if (HuongXuLyXLDEnum.HUONG_DAN_VIET_LAI_DON.equals(huongXuLyXLD)) {
									don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.HUONG_DAN_VIET_LAI_DON);
								}
							}
							xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
							xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
							don.setHuongXuLyXLD(huongXuLyXLD);
							don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());

							//don.setHuongXuLyXLD(HuongXuLyXLDEnum.DINH_CHI);
							don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
							don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DA_XU_LY);
							don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
							don.setCanBoCoTheThuHoi(null);
							don.setCoQuanDangGiaiQuyet(xuLyDonHienTai.getDonViXuLy());
							don.setNgayKetThucXLD(Utils.localDateTimeNow());
							don.setNgayThucHienKetQuaXuLy(Utils.localDateTimeNow());
							don.setCanBoXuLyChiDinh(null);
							
//							Bo phanLoaiDon - cap nhat 16/11
//							if (xuLyDon.getPhanLoaiDon() != null) {
//								don.setPhanLoaiDon(xuLyDon.getPhanLoaiDon());
//								if (PhanLoaiDonEnum.DU_DIEU_KIEN_XU_LY.equals(xuLyDon.getPhanLoaiDon())) {
//									don.setLyDoKhongDuDieuKienThuLy(null);
//								} else {
//									don.setLyDoKhongDuDieuKienThuLy(xuLyDon.getLyDoKhongDuDieuKienThuLy());
//								}
//							}
							
							if (!coQuyTrinh) { 
								don.setLanhDaoDuyet(true);
							}
							
							Utils.changeQuyenTuXuLy(don, false, false, false);
							
							//tao lich su qua trinh xu ly don
							LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
							lichSuQTXLD.setDon(don);
							lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
							lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
							lichSuQTXLD.setTen(huongXuLyXLD.getText());
							lichSuQTXLD.setNoiDung(xuLyDonHienTai.getNoiDungXuLy());
							lichSuQTXLD.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
							int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), 
									xuLyDonHienTai.getDonViXuLy().getId());
							lichSuQTXLD.setThuTuThucHien(thuTu);
							
							donService.save(don, congChucId);
							lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
							return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
						} else if (HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.equals(huongXuLyXLD)
								&& xuLyDonHienTai.isDonChuyen()) {
							Predicate predicate = processService.predicateFindAllByDonVi(xuLyDonHienTai.getCoQuanChuyenDon().getDonVi(), ProcessTypeEnum.XU_LY_DON);
							List<Process> listProcessXLD = (List<Process>) repoProcess.findAll(predicate);
							if (listProcessXLD.size() < 1) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_INVALID.name(),
								ApiErrorEnum.TRANSITION_INVALID.getText(), ApiErrorEnum.TRANSITION_INVALID.getText());
							}
							Transition transitionXLD = null;
							for (Process processFromList : listProcessXLD) {
								List<Transition> transitionXLDs = new ArrayList<Transition>();
								transitionXLDs.addAll((List<Transition>) transitionRepo.findAll(transitionService.predicateFindFromCurrentAndNext(FlowStateEnum.LANH_DAO_GIAO_VIEC_CAN_BO, FlowStateEnum.KET_THUC, processFromList)));
								transitionXLDs.addAll((List<Transition>) transitionRepo.findAll(transitionService.predicateFindFromCurrentAndNext(FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO, FlowStateEnum.KET_THUC, processFromList)));
								if (transitionXLDs != null && transitionXLDs.size() > 0) {
									transitionXLD = transitionXLDs.get(0);
									if (transitionXLD != null) {
										break;
									}
								}
							}
							XuLyDon xuLyDonTiepTheo = new XuLyDon();
							xuLyDonTiepTheo = chuyenVienTraLaiDonKhongDungThamQuyen(xuLyDon, xuLyDonHienTai, congChucId, transitionXLD);
							return xuLyDonService.doSave(xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						}
					} else {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_INVALID.name(),
								ApiErrorEnum.DATA_INVALID.getText(), ApiErrorEnum.DATA_INVALID.getText());
					}
				}
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/xuLyDons/{id}/dinhChiDon")
	@ApiOperation(value = "Đình chỉ đơn	", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Đình chỉ đơn thành công", response = XuLyDon.class) })
	public ResponseEntity<Object> dinhChiDon(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id,
			@RequestBody XuLyDon xuLyDon, PersistentEntityResourceAssembler eass) {

		try {
			NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA);
			CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
			if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
					&& commonProfile.containsAttribute("coQuanQuanLyId")) {
				
				Long donId = xuLyDon.getDon().getId();
				XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(repo, donId);
				String vaiTroNguoiDungHienTai = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro")
						.toString();

				// Thong tin xu ly don
				Long congChucId = Long.valueOf(
						profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());

				if (xuLyDonHienTai != null) {
					if (StringUtils.equals(vaiTroNguoiDungHienTai, VaiTroEnum.VAN_THU.name())
							|| StringUtils.equals(vaiTroNguoiDungHienTai, VaiTroEnum.LANH_DAO.name())
							|| StringUtils.equals(vaiTroNguoiDungHienTai, VaiTroEnum.CHUYEN_VIEN.name())
							|| StringUtils.equals(vaiTroNguoiDungHienTai, VaiTroEnum.TRUONG_PHONG.name())) {
						xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
						xuLyDonHienTai.setNgayQuyetDinhDinhChi(xuLyDon.getNgayQuyetDinhDinhChi());
						xuLyDonHienTai.setSoQuyetDinhDinhChi(xuLyDon.getSoQuyetDinhDinhChi());
						xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						xuLyDonHienTai.setHuongXuLy(HuongXuLyXLDEnum.DINH_CHI);
						Don don = donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
//						don.setHuongXuLyXLD(HuongXuLyXLDEnum.DINH_CHI);
						don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						don.setHoanThanhDon(true);
						don.setLyDoDinhChi(xuLyDon.getyKienXuLy());
						don.setNgayQuyetDinhDinhChi(xuLyDon.getNgayQuyetDinhDinhChi());
						don.setSoQuyetDinhDinhChi(xuLyDon.getSoQuyetDinhDinhChi());
						don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
						don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DA_XU_LY);
						don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.DINH_CHI);
						// set ngay ket thuc xu ly don cho don
						don.setNgayKetThucXLD(Utils.localDateTimeNow());
						don.setCanBoCoTheThuHoi(null);
						if (don.getTrangThaiTTXM() != null) { 
							don.setTrangThaiTTXM(TrangThaiDonEnum.DA_GIAI_QUYET);
							don.setKetQuaTTXM(KetQuaTrangThaiDonEnum.DINH_CHI);
						}
						don.setNgayThucHienKetQuaXuLy(Utils.localDateTimeNow());
						
						boolean coQuyTrinh = kiemTraDonViCoQuyTrinhXLD(xuLyDonHienTai.getDonViXuLy().getId());
						if (!coQuyTrinh) { 
							don.setLanhDaoDuyet(true);
						}
						
						//tao lich su qua trinh xu ly don
						LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
						lichSuQTXLD.setDon(don);
						lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
						lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
						lichSuQTXLD.setTen(xuLyDonHienTai.getHuongXuLy().getText());
						lichSuQTXLD.setNoiDung(xuLyDonHienTai.getNoiDungXuLy());
						lichSuQTXLD.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
						int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), 
								xuLyDonHienTai.getDonViXuLy().getId());
						lichSuQTXLD.setThuTuThucHien(thuTu);
						
						Utils.changeQuyenTuXuLy(don, false, false, false);
						donService.save(don, congChucId);
						if (don.getThongTinGiaiQuyetDon() != null) { 
							Long thongTinGiaiQuyetDonId = don.getThongTinGiaiQuyetDon().getId();
							GiaiQuyetDon giaiQuyetDonHienTai = giaiQuyetDonService.predFindCurrent(giaiQuyetDonRepo, thongTinGiaiQuyetDonId, false);
							GiaiQuyetDon giaiQuyetDonHienTaiTTXM = giaiQuyetDonService.predFindCurrent(giaiQuyetDonRepo, thongTinGiaiQuyetDonId, true);
							giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
							if (giaiQuyetDonHienTaiTTXM != null) giaiQuyetDonHienTaiTTXM.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
							giaiQuyetDonService.save(giaiQuyetDonHienTai, congChucId);
							if (giaiQuyetDonHienTaiTTXM != null) giaiQuyetDonService.save(giaiQuyetDonHienTaiTTXM, congChucId);
						}
						lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
						return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
					}
					return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
							ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
				}
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(method = RequestMethod.PATCH, value = "/xuLyDons/tuXuLy/{id}")
	@ApiOperation(value = "Cập nhật thông tin xử lý đơn	", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Cập nhật thông tin xử lý đơn thành công", response = XuLyDon.class) })
	public ResponseEntity<Object> capNhatThongTinXuLyDonTuXuLy(
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			@PathVariable("id") long id,
			@RequestBody XuLyDon xuLyDon, PersistentEntityResourceAssembler eass) {
		try {
			NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA);
			CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
			if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
					&& commonProfile.containsAttribute("coQuanQuanLyId")) {
				Don donOld = donRepo.findOne(id);
				if (donOld == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
							ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
				}
				
				Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
				XuLyDon xuLyDonHienTai = xuLyDonService.predFindXuLyDonCuoiCung(repo, id, donViId);
				CongChuc canBoXuLyChiDinhOld = xuLyDonHienTai.getCanBoXuLyChiDinh();
				if (xuLyDonHienTai == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
							ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
				}
				
				Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());				
				updateXuLyDon(xuLyDon, xuLyDonHienTai, donOld, congChucId, eass);
				
				donService.save(donOld, congChucId);
				xuLyDonHienTai.setCanBoXuLyChiDinh(canBoXuLyChiDinhOld);
				return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
			}
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.PATCH, value = "/xuLyDons/{id}")
	@ApiOperation(value = "Cập nhật thông tin xử lý đơn	", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Cập nhật thông tin xử lý đơn thành công", response = XuLyDon.class) })
	public ResponseEntity<Object> capNhatThongTinXuLyDon(
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			@PathVariable("id") long id,
			@RequestBody XuLyDon xuLyDon, PersistentEntityResourceAssembler eass) {
		try {
			NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA);
			CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
			if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
					&& commonProfile.containsAttribute("coQuanQuanLyId")) {
				Don donOld = donRepo.findOne(id);
				if (donOld == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
							ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
				}
				XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donOld.getId());
				if (xuLyDonHienTai == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
							ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
				}
				
				Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());				
				updateXuLyDon(xuLyDon, xuLyDonHienTai, donOld, congChucId, eass);
				
				donService.save(donOld, congChucId);
				return xuLyDonService.doSave(xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
			}
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	private void updateXuLyDon(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Don donOld, Long congChucId, PersistentEntityResourceAssembler eass) {
		if (xuLyDon.getNextState() != null) {					
			if (!xuLyDon.getNextState().equals(xuLyDonHienTai.getNextState())) { 
				xuLyDonHienTai.setNextState(xuLyDon.getNextState());
				xuLyDonHienTai.setThamQuyenGiaiQuyet(null);
				xuLyDonHienTai.setCoQuanTiepNhan(null);
				xuLyDonHienTai.setPhongBanGiaiQuyet(null);
				xuLyDonHienTai.setNgayHenGapLanhDao(null);
				xuLyDonHienTai.setCanBoXuLyChiDinh(null);
				xuLyDonHienTai.setPhongBanXuLyChiDinh(null);
				xuLyDonHienTai.setNoiDungXuLy(null);
				xuLyDonHienTai.setDiaDiem(null);
				xuLyDonHienTai.setNgayQuyetDinhDinhChi(null);
				xuLyDonHienTai.setSoQuyetDinhDinhChi(null);
				xuLyDonHienTai.setTruongPhongChiDinh(null);
				xuLyDonHienTai.setChuyenVienChiDinh(null);
				xuLyDonHienTai.setHuongXuLy(null);
				xuLyDonHienTai.setDonViNgoaiHeThong(null);
			}
		}

		if (xuLyDon.getTrangThaiTTXM() != null) {
			xuLyDonHienTai.setTrangThaiTTXM(xuLyDon.getTrangThaiTTXM());
		}
		if (xuLyDon.getDonViThamTraXacMinh() != null) {
			xuLyDonHienTai.setDonViThamTraXacMinh(xuLyDon.getDonViThamTraXacMinh());
		}
		if (xuLyDon.getHanGiaiQuyet() != null) {
			xuLyDonHienTai.setHanGiaiQuyet(xuLyDon.getHanGiaiQuyet());
		}
		if (xuLyDon.getThoiHanBaoCaoKetQuaTTXM() != null) {
			xuLyDonHienTai.setThoiHanBaoCaoKetQuaTTXM(xuLyDon.getThoiHanBaoCaoKetQuaTTXM());
		}
		
		if (xuLyDon.getHuongXuLy() != null) { 
			xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());					
			if (!xuLyDon.getHuongXuLy().equals(HuongXuLyXLDEnum.DE_XUAT_THU_LY)) {
				xuLyDonHienTai.setTrangThaiTTXM(null);
				xuLyDonHienTai.setDonViThamTraXacMinh(null);
				xuLyDonHienTai.setThoiHanBaoCaoKetQuaTTXM(null);						
			} else if (xuLyDon.getTrangThaiTTXM() != null) {
				if (xuLyDon.getTrangThaiTTXM().equals(TrangThaiTTXMEnum.TU_TTXM)) {
					xuLyDonHienTai.setDonViThamTraXacMinh(null);
					xuLyDonHienTai.setThoiHanBaoCaoKetQuaTTXM(null);	
				}
			}
		}				
		
//		Bo phanLoaiDon - cap nhat 16/11
//		if (xuLyDon.getPhanLoaiDon() != null) {
//			donOld.setPhanLoaiDon(xuLyDon.getPhanLoaiDon());
//			if (PhanLoaiDonEnum.DU_DIEU_KIEN_XU_LY.equals(xuLyDon.getPhanLoaiDon())) {
//				donOld.setLyDoKhongDuDieuKienThuLy(null);
//			} else {
//				donOld.setLyDoKhongDuDieuKienThuLy(xuLyDon.getLyDoKhongDuDieuKienThuLy());
//			}
//		} else {
//			donOld.setPhanLoaiDon(null);
//		}
		
		if (xuLyDon.getThamQuyenGiaiQuyet() != null) { 
			xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
			donOld.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		}
		if (xuLyDon.getCoQuanTiepNhan() != null) {
			xuLyDonHienTai.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
			if (xuLyDon.getCoQuanTiepNhan().getId() == 0) { 
				xuLyDonHienTai.setCoQuanTiepNhan(null);
			} else {
				xuLyDonHienTai.setDonViNgoaiHeThong(null);
			}
		}
		if (xuLyDon.getDonViNgoaiHeThong() != null) {
			xuLyDonHienTai.setDonViNgoaiHeThong(xuLyDon.getDonViNgoaiHeThong());
		}
		if (xuLyDon.getPhongBanGiaiQuyet() != null) { 
			xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
			donOld.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		} else { 
			xuLyDonHienTai.setPhongBanGiaiQuyet(null);
			donOld.setPhongBanGiaiQuyet(null);
		}
		if (xuLyDon.getNgayHenGapLanhDao() != null) { 
			xuLyDonHienTai.setNgayHenGapLanhDao(xuLyDon.getNgayHenGapLanhDao());
		} else {
			xuLyDonHienTai.setNgayHenGapLanhDao(null);
		}
		if (StringUtils.isNotBlank(xuLyDon.getDiaDiem())) { 
			xuLyDonHienTai.setDiaDiem(xuLyDon.getDiaDiem());
		} else {
			xuLyDonHienTai.setDiaDiem("");
		}
		if (xuLyDon.getPhongBanXuLyChiDinh() != null) {
			xuLyDonHienTai.setPhongBanXuLyChiDinh(xuLyDon.getPhongBanXuLyChiDinh());
		}
		
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getNoiDungYeuCauXuLy());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		if (StringUtils.isNotBlank(xuLyDon.getNoiDungYeuCauXuLy())) {
			xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getNoiDungYeuCauXuLy());
		}
		if (StringUtils.isNotBlank(xuLyDon.getyKienXuLy())) { 
			xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		}
		if (StringUtils.isNotBlank(xuLyDon.getNoiDungThongTinTrinhLanhDao())) {
			xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getNoiDungThongTinTrinhLanhDao());
			donOld.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
		}
		if (xuLyDon.getThoiHanXuLy() != null) { 
			donOld.setThoiHanXuLyXLD(xuLyDon.getThoiHanXuLy());
		}
		if (xuLyDon.getNgayQuyetDinhDinhChi() != null) { 
			xuLyDonHienTai.setNgayQuyetDinhDinhChi(xuLyDon.getNgayQuyetDinhDinhChi());
			donOld.setNgayQuyetDinhDinhChi(xuLyDon.getNgayQuyetDinhDinhChi());
		}
		if (xuLyDon.getSoQuyetDinhDinhChi() != null) { 
			xuLyDonHienTai.setSoQuyetDinhDinhChi(xuLyDon.getSoQuyetDinhDinhChi());
			donOld.setSoQuyetDinhDinhChi(xuLyDon.getSoQuyetDinhDinhChi());
		}
		if (xuLyDon.getCanBoXuLyChiDinh() != null) {
			xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		} else {
			xuLyDonHienTai.setCanBoXuLyChiDinh(null);
		}
		if (xuLyDon.getTruongPhongChiDinh() != null) {
			xuLyDonHienTai.setTruongPhongChiDinh(xuLyDon.getTruongPhongChiDinh());
			xuLyDonHienTai.setChuyenVienChiDinh(null);
		} else {
			xuLyDonHienTai.setTruongPhongChiDinh(null);
		}
		if (xuLyDon.getChuyenVienChiDinh() != null) {
			xuLyDonHienTai.setChuyenVienChiDinh(xuLyDon.getChuyenVienChiDinh());
			xuLyDonHienTai.setTruongPhongChiDinh(null);
		} else {
			xuLyDonHienTai.setChuyenVienChiDinh(null);
		}
		List<LichSuQuaTrinhXuLy> lichSuList = new ArrayList<LichSuQuaTrinhXuLy>();
		lichSuList.addAll(lichSuQuaTrinhXuLyService.getDSLichSuQuaTrinhXuLys(lichSuQuaTrinhXuLyRepo, donOld.getId(), xuLyDonHienTai.getDonViXuLy().getId()));
		if (lichSuList.size() == 1) { 
			for (LichSuQuaTrinhXuLy lichSuQuaTrinhXuLy : lichSuList) {
				if (StringUtils.isNotBlank(xuLyDon.getNoiDungThongTinTrinhLanhDao())) {
					lichSuQuaTrinhXuLy.setNoiDung(xuLyDon.getNoiDungThongTinTrinhLanhDao());
				}
				lichSuQuaTrinhXuLyService.save(lichSuQuaTrinhXuLy, congChucId);
			}
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuTraDonChuyenKhongDungThamQuyen")
	@ApiOperation(value = "In phiếu trả đơn chuyển không đúng thẩm quyền", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordTraDonChuyenKhongDungThamQuyen(
			@RequestParam(value = "donViXuLyId", required = true) Long donViXuLyId,
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "coQuanNhanDon", required = true) String coQuanNhanDon,
			@RequestParam(value = "coQuanChuyenDon", required = true) String coQuanChuyenDon,
			@RequestParam(value = "hoTenNguoiCoDon", required = true) String hoTenNguoiCoDon,
			@RequestParam(value = "noiDung", required = true) String noiDung,
			@RequestParam(value = "diaChi", required = true) String diaChi,
			HttpServletResponse response) {
		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			CoQuanQuanLy cq = coQuanQuanLyRepo.findOne(donViXuLyId);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoUBNPX = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoTTTP = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			
			String soVB = "";
			mappings.put("kyTen", "");
			mappings.put("capHanhChinh", "");
			mappings.put("coQuanTrucThuoc", "");
			if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri()) || cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDQH.getGiaTri()) ||
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNPX.getGiaTri())) {
				mappings.put("capHanhChinh", WordUtil.splitWord(cq.getTen()).toUpperCase());
				if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri())) {
					mappings.put("kyTen", "ỦY BAN NHÂN DÂN".toUpperCase());
				} else { 
					mappings.put("kyTen", "CHỦ TỊCH".toUpperCase());
				}
				soVB = "UBND";
			} else if (cq.getId() == Long.valueOf(thamSoTTTP.getGiaTri()) || 
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoSBN.getGiaTri())) {
				CoQuanQuanLy ubndtp = coQuanQuanLyRepo.findOne(Long.valueOf(thamSoUBNDTPDN.getGiaTri()));
				mappings.put("capHanhChinh", ubndtp.getTen().toUpperCase());
				mappings.put("coQuanTrucThuoc", cq.getTen().toUpperCase());
				mappings.put("kyTen", "Giám đốc sở".toUpperCase());
				soVB = Utils.splitWords(cq.getTen());
			}
			
			mappings.put("soVB", soVB);
			mappings.put("ngayTiepNhan", ngayTiepNhan);
			mappings.put("coQuanNhanDon", cq.getTen());
			mappings.put("coQuanChuyenDon", coQuanChuyenDon);
			mappings.put("hoTenNguoiCoDon", hoTenNguoiCoDon);
			mappings.put("noiDung", noiDung != null && noiDung != "" ? noiDung.concat(".") : "");
			mappings.put("diaChi", diaChi);
			
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/XLD_PHIEU_TRA_DON_CHUYEN_KHONG_DUNG_THAM_QUYEN.docx").getFile(), mappings, 
					"Phiếu thông báo về việc trả lại đơn chuyển không đúng thẩm quyền.docx");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuDeXuatThuLy")
	@ApiOperation(value = "In phiếu đề xuất thụ lý", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordPhieuDeXuatThuLy(
			@RequestParam(value = "donViXuLyId", required = true) Long donViXuLyId,
			@RequestParam(value = "loaiDon", required = true) String loaiDon,
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "coQuanTiepNhan", required = true) String coQuanTiepNhan,
			@RequestParam(value = "nguoiDungDon", required = true) String nguoiDungDon,
			@RequestParam(value = "noiDung", required = true) String noiDung,
			@RequestParam(value = "diaChi", required = false) String diaChi,
			@RequestParam(value = "soCMNDHoChieu", required = false) String soCMNDHoChieu, 
			@RequestParam(value = "noiCap", required = false) String noiCap,
			@RequestParam(value = "ngayCap", required = false) String ngayCap, 
			HttpServletResponse response) {
		
		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			CoQuanQuanLy cq = coQuanQuanLyRepo.findOne(donViXuLyId);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoUBNPX = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoTTTP = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			
			String soVB = "";
			mappings.put("capHanhChinh", "");
			mappings.put("coQuanTrucThuoc", "");
			if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri()) || cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDQH.getGiaTri()) ||
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNPX.getGiaTri())) {
				mappings.put("capHanhChinh", WordUtil.splitWord(cq.getTen()).toUpperCase());
				soVB = "UBND";
			} else if (cq.getId() == Long.valueOf(thamSoTTTP.getGiaTri()) || 
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoSBN.getGiaTri())) {
				CoQuanQuanLy ubndtp = coQuanQuanLyRepo.findOne(Long.valueOf(thamSoUBNDTPDN.getGiaTri()));
				mappings.put("capHanhChinh", ubndtp.getTen().toUpperCase());
				mappings.put("coQuanTrucThuoc", cq.getTen().toUpperCase());
				soVB = Utils.splitWords(cq.getTen());
			}
			
			mappings.put("soVB", soVB);
			mappings.put("loaiDonTieuDe", loaiDon.toUpperCase());
			mappings.put("loaiDon", loaiDon.toLowerCase());
			mappings.put("ngayTiepNhan", ngayTiepNhan);
			mappings.put("coQuanTiepNhan", cq.getTen());
			mappings.put("nguoiDungDon", nguoiDungDon);
			mappings.put("diaChi", diaChi);
			mappings.put("soCMNDHoChieu", soCMNDHoChieu != null && soCMNDHoChieu != "" ? soCMNDHoChieu : "................");
			mappings.put("ngayCap", ngayCap != null && ngayCap != "" ? ngayCap : "................");
			mappings.put("noiCap", noiCap != null && noiCap != "" ? noiCap : "................");
			mappings.put("noiDung", noiDung != null && noiDung != "" ? noiDung.concat(".") : "");
						
			//WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/XLD_PHIEU_DE_XUAT_THU_LY.docx").getFile(), mappings);
			String fileName = "Phiếu đề xuất thụ lý đơn " +loaiDon.toLowerCase().replace(",", " -") +".docx";
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/XLD_PHIEU_DE_XUAT_THU_LY.docx").getFile(), mappings, fileName);
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuKhongDuDieuKienThuLyKhieuNai")
	@ApiOperation(value = "In phiếu không đủ điều kiện thụ lý khiếu nại", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordPhieuKhongDuDieuKienThuLyKhieuNai(
			@RequestParam(value = "donViXuLyId", required = true) Long donViXuLyId,
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "coQuanTiepNhan", required = true) String coQuanTiepNhan,
			@RequestParam(value = "nguoiDungDon", required = true) String nguoiDungDon,
			@RequestParam(value = "diaChiNguoiDungDon", required = false) String diaChiNguoiDungDon,
			@RequestParam(value = "noiDung", required = false) String noiDung,
			@RequestParam(value = "lyDoDinhChi", required = false) String lyDoDinhChi, HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			CoQuanQuanLy cq = coQuanQuanLyRepo.findOne(donViXuLyId);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoUBNPX = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoTTTP = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			
			String soVB = "";
			mappings.put("kyTen", "");
			mappings.put("capHanhChinh", "");
			mappings.put("coQuanTrucThuoc", "");
			if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri()) || cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDQH.getGiaTri()) ||
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNPX.getGiaTri())) {
				mappings.put("capHanhChinh", WordUtil.splitWord(cq.getTen()).toUpperCase());
				if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri())) {
					mappings.put("kyTen", "ỦY BAN NHÂN DÂN".toUpperCase());
				} else { 
					mappings.put("kyTen", "CHỦ TỊCH".toUpperCase());
				}
				soVB = "UBND";
			} else if (cq.getId() == Long.valueOf(thamSoTTTP.getGiaTri()) || 
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoSBN.getGiaTri())) {
				CoQuanQuanLy ubndtp = coQuanQuanLyRepo.findOne(Long.valueOf(thamSoUBNDTPDN.getGiaTri()));
				mappings.put("capHanhChinh", ubndtp.getTen().toUpperCase());
				mappings.put("coQuanTrucThuoc", cq.getTen().toUpperCase());
				mappings.put("kyTen", "Giám đốc sở".toUpperCase());
				soVB = Utils.splitWords(cq.getTen());
			}
			
			mappings.put("soVB", soVB);
			mappings.put("ngayTiepNhan", ngayTiepNhan);
			mappings.put("coQuanTiepNhan", cq.getTen());
			mappings.put("nguoiDungDon", nguoiDungDon);
			mappings.put("diaChiNguoiDungDon", diaChiNguoiDungDon); 
			mappings.put("noiDung", noiDung != null && noiDung != "" ? noiDung.concat(".") : "");
			mappings.put("lyDoDinhChi", lyDoDinhChi != null && lyDoDinhChi != "" 
					? lyDoDinhChi : "...................................................................................................................................(3)");
			
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/khieunai/XLD_PHIEU_KHONG_DU_DIEU_KIEN_THU_LY.docx").getFile(), mappings, 
					"Phiếu thông báo về việc khiếu nại không đủ điều kiện thụ lý giải quyết.docx");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuKhongDuDieuKienThuLyToCao")
	@ApiOperation(value = "In phiếu không đủ điều kiện thụ lý tố cáo", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordPhieuKhongDuDieuKienThuLyToCao(
			@RequestParam(value = "donViXuLyId", required = true) Long donViXuLyId,
			@RequestParam(value = "donId", required = true) Long donId,
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "coQuanTiepNhan", required = true) String coQuanTiepNhan,
			@RequestParam(value = "nguoiToCao", required = true) String nguoiToCao,
			@RequestParam(value = "diaChiNguoiToCao", required = false) String diaChiNguoiToCao,
			@RequestParam(value = "noiDung", required = false) String noiDung,
			@RequestParam(value = "lyDo", required = false) String lyDo,
			HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			CoQuanQuanLy cq = coQuanQuanLyRepo.findOne(donViXuLyId);
			Don don = donRepo.findOne(donId);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoUBNPX = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoTTTP = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			
			mappings.put("capHanhChinh", "");
			mappings.put("coQuanTrucThuoc", "");
			String soVB = "";
			
			if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri()) || cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDQH.getGiaTri()) ||
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNPX.getGiaTri())) {
				mappings.put("capHanhChinh", WordUtil.splitWord(cq.getTen()).toUpperCase());
				soVB = "UBND";
			} else if (cq.getId() == Long.valueOf(thamSoTTTP.getGiaTri()) || 
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoSBN.getGiaTri())) {
				CoQuanQuanLy ubndtp = coQuanQuanLyRepo.findOne(Long.valueOf(thamSoUBNDTPDN.getGiaTri()));
				mappings.put("capHanhChinh", ubndtp.getTen().toUpperCase());
				mappings.put("coQuanTrucThuoc", cq.getTen().toUpperCase());
				soVB = Utils.splitWords(cq.getTen());
			}
			
			mappings.put("coQuanChuyenDon", "................................................................(1)");
			if (don != null && don.isDonChuyen()) {
				Don donGoc = donRepo.findOne(don.getDonGocId());
				mappings.put("coQuanChuyenDon", donGoc.getDonViXuLyGiaiQuyet().getTen());
			}
			
			mappings.put("soVB", soVB);
			mappings.put("ngayTiepNhan", ngayTiepNhan);
			mappings.put("coQuanTiepNhan", cq.getTen());
			mappings.put("nguoiToCao", nguoiToCao);
			mappings.put("diaChi", diaChiNguoiToCao);
			mappings.put("diaChiNguoiToCao", diaChiNguoiToCao); 
			mappings.put("noiDung", noiDung != null && noiDung != "" ? noiDung.concat(".") : "");
			mappings.put("lyDoDinhChi", lyDo != null && lyDo != "" ? lyDo.concat(".") : "");
//			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/tocao/XLD_PHIEU_KHONG_THU_LY_GIAI_QUYET_TO_CAO.docx").getFile(), mappings);

			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/tocao/XLD_PHIEU_KHONG_THU_LY_GIAI_QUYET_TO_CAO.docx").getFile(), mappings, 
					"Phiếu thông báo về việc không thụ lý giải quyết tố cáo.docx");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuKhongDuDieuKienThuLyKienNghi")
	@ApiOperation(value = "In phiếu không đủ điều kiện thụ lý kiến nghị", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordPhieuKhongDuDieuKienThuLyKienNghi(
			@RequestParam(value = "donViXuLyId", required = true) Long donViXuLyId,
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "coQuanTiepNhan", required = true) String coQuanTiepNhan,
			@RequestParam(value = "nguoiKienNghi", required = true) String nguoiKienNghi,
			@RequestParam(value = "diaChiNguoiKienNghi", required = false) String diaChiNguoiKienNghi,
			@RequestParam(value = "soCMNDHoChieu", required = false) String soCMNDHoChieu,
			@RequestParam(value = "ngayCap", required = false) String ngayCap,
			@RequestParam(value = "noiCap", required = false) String noiCap,
			@RequestParam(value = "lyDoDinhChi", required = false) String lyDoDinhChi,
			@RequestParam(value = "noiDung", required = false) String noiDung,
			HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			CoQuanQuanLy cq = coQuanQuanLyRepo.findOne(donViXuLyId);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoUBNPX = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoTTTP = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			
			String soVB = "";
			mappings.put("capHanhChinh", "");
			mappings.put("coQuanTrucThuoc", "");
			if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri()) || cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDQH.getGiaTri()) ||
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNPX.getGiaTri())) {
				mappings.put("capHanhChinh", WordUtil.splitWord(cq.getTen()).toUpperCase());
				soVB = "UBND";
			} else if (cq.getId() == Long.valueOf(thamSoTTTP.getGiaTri()) || 
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoSBN.getGiaTri())) {
				CoQuanQuanLy ubndtp = coQuanQuanLyRepo.findOne(Long.valueOf(thamSoUBNDTPDN.getGiaTri()));
				mappings.put("capHanhChinh", ubndtp.getTen().toUpperCase());
				mappings.put("coQuanTrucThuoc", cq.getTen().toUpperCase());
				soVB = Utils.splitWords(cq.getTen());
			}
			
			mappings.put("soVB", soVB);
			mappings.put("ngayTiepNhan", ngayTiepNhan);
			mappings.put("coQuanTiepNhan", cq.getTen());
			mappings.put("nguoiKienNghi", nguoiKienNghi);
			mappings.put("diaChiNguoiKienNghi", diaChiNguoiKienNghi);
			mappings.put("soCMNDHoChieu", soCMNDHoChieu != null && soCMNDHoChieu != "" ? soCMNDHoChieu : "................");
			mappings.put("ngayCap", ngayCap != null && ngayCap != "" ? ngayCap : "................");
			mappings.put("noiCap", noiCap != null && noiCap != "" ? noiCap : "................");
			mappings.put("lyDoDinhChi", lyDoDinhChi != null && lyDoDinhChi != "" 
					? lyDoDinhChi.concat(".") : "...................................................................................................................................(3)");
			mappings.put("noiDung", noiDung != null && noiDung != "" ? noiDung.concat(".") : "");
			
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/kiennghiphananh/XLD_PHIEU_KHONG_THU_LY_GIAI_QUYET_KIEN_NGHI.docx").getFile(), mappings, 
					"Phiếu thông báo về việc không xử lý giải quyết kiến nghị - phản ánh.docx");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuTraDonVaHuongDanKhieuNai")
	@ApiOperation(value = "In phiếu trả đơn và hướng dẫn khiếu nại", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordKhieuNaiTraDonVaHuongDan(
			@RequestParam(value = "donViXuLyId", required = true) Long donViXuLyId,
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "nguoiDungDon", required = true) String nguoiDungDon,
			@RequestParam(value = "noiDung", required = false) String noiDung,
			@RequestParam(value = "coQuanTiepNhan", required = false) String coQuanTiepNhan,
			HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			CoQuanQuanLy cq = coQuanQuanLyRepo.findOne(donViXuLyId);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoUBNPX = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoTTTP = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			
			String soVB = "";
			mappings.put("kyTen", "");
			mappings.put("capHanhChinh", "");
			mappings.put("coQuanTrucThuoc", "");
			if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri()) || cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDQH.getGiaTri()) ||
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNPX.getGiaTri())) {
				mappings.put("capHanhChinh", WordUtil.splitWord(cq.getTen()).toUpperCase());
				if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri())) {
					mappings.put("kyTen", "ỦY BAN NHÂN DÂN".toUpperCase());
				} else { 
					mappings.put("kyTen", "CHỦ TỊCH".toUpperCase());
				}
				soVB = "UBND";
			} else if (cq.getId() == Long.valueOf(thamSoTTTP.getGiaTri()) || 
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoSBN.getGiaTri())) {
				CoQuanQuanLy ubndtp = coQuanQuanLyRepo.findOne(Long.valueOf(thamSoUBNDTPDN.getGiaTri()));
				mappings.put("capHanhChinh", ubndtp.getTen().toUpperCase());
				mappings.put("coQuanTrucThuoc", cq.getTen().toUpperCase());
				mappings.put("kyTen", "Giám đốc sở".toUpperCase());
				soVB = Utils.splitWords(cq.getTen());
			}
			
			mappings.put("soVB", soVB);
			mappings.put("ngayTiepNhan", ngayTiepNhan);
			mappings.put("nguoiDungDon", nguoiDungDon);
			mappings.put("noiDung", noiDung != null && noiDung != "" ? noiDung.concat(".") : "");
			mappings.put("coQuanTiepNhan", cq.getTen());
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/khieunai/XLD_PHIEU_TRA_DON_VA_HUONG_DAN_DON_KHIEU_NAI.docx").getFile(), mappings, 
					"Phiếu thông báo về việc hướng dẫn người khiếu nại.docx");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuChuyenDonKienNghiPhanAnh")
	@ApiOperation(value = "In phiếu chuyển đơn kiến nghị phản ánh", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordChuyenDonKienNghiPhanAnh(
			@RequestParam(value = "donViXuLyId", required = true) Long donViXuLyId,
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "nguoiDungDon", required = true) String nguoiDungDon,
			@RequestParam(value = "diaChiNguoiDungDon", required = false) String diaChiNguoiDungDon,
			@RequestParam(value = "soCMNDHoChieu", required = false) String soCMNDHoChieu,
			@RequestParam(value = "ngayCap", required = false) String ngayCap,
			@RequestParam(value = "noiCap", required = false) String noiCap,
			@RequestParam(value = "noiDung", required = false) String noiDung,
			@RequestParam(value = "coQuanTiepNhan", required = false) String coQuanTiepNhan,
			//@RequestParam(value = "coQuanChuyenDon", required = false) String coQuanChuyenDon,
			HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			CoQuanQuanLy cq = coQuanQuanLyRepo.findOne(donViXuLyId);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoUBNPX = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoTTTP = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			
			String soVB = "";
			mappings.put("kyTen", "");
			mappings.put("capHanhChinh", "");
			mappings.put("coQuanTrucThuoc", "");
			if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri()) || cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDQH.getGiaTri()) ||
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNPX.getGiaTri())) {
				mappings.put("capHanhChinh", WordUtil.splitWord(cq.getTen()).toUpperCase());
				if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri())) {
					mappings.put("kyTen", "ỦY BAN NHÂN DÂN".toUpperCase());
				} else { 
					mappings.put("kyTen", "CHỦ TỊCH".toUpperCase());
				}
				soVB = "UBND";
			} else if (cq.getId() == Long.valueOf(thamSoTTTP.getGiaTri()) || 
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoSBN.getGiaTri())) {
				CoQuanQuanLy ubndtp = coQuanQuanLyRepo.findOne(Long.valueOf(thamSoUBNDTPDN.getGiaTri()));
				mappings.put("capHanhChinh", ubndtp.getTen().toUpperCase());
				mappings.put("coQuanTrucThuoc", cq.getTen().toUpperCase());
				mappings.put("kyTen", "Giám đốc sở".toUpperCase());
				soVB = Utils.splitWords(cq.getTen());
			}
			
			mappings.put("soVB", soVB);
			mappings.put("ngayTiepNhan", ngayTiepNhan);
			mappings.put("nguoiDungDon", nguoiDungDon);
			mappings.put("diaChiNguoiDungDon", diaChiNguoiDungDon);
			mappings.put("soCMNDHoChieu", soCMNDHoChieu != null && soCMNDHoChieu != "" ? soCMNDHoChieu : "................");
			mappings.put("ngayCap", ngayCap != null && ngayCap != "" ? ngayCap : "................");
			mappings.put("noiCap", noiCap != null && noiCap != "" ? noiCap : "................");
			mappings.put("noiDung", noiDung != null && noiDung != "" ? noiDung.concat(".") : "");
			mappings.put("coQuanTiepNhan", coQuanTiepNhan);
			mappings.put("coQuanChuyenDon", cq.getTen());
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/kiennghiphananh/XLD_PHIEU_CHUYEN_DON_KIEN_NGHI_PHAN_ANH.docx").getFile(), mappings, 
					"Phiếu thông báo về việc chuyển đơn kiến nghị - phản ánh.docx");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuChuyenDonToCao")
	@ApiOperation(value = "In phiếu chuyển đơn tố cáo", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordChuyenDonToCao(
			@RequestParam(value = "donViXuLyId", required = true) Long donViXuLyId,
			@RequestParam(value = "donViTiepNhanDonId", required = false) Long donViTiepNhanDonId,
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "nguoiDungDon", required = true) String nguoiDungDon,
			@RequestParam(value = "diaChiNguoiDungDon", required = false) String diaChiNguoiDungDon,
			@RequestParam(value = "noiDung", required = false) String noiDung,
			@RequestParam(value = "coQuanTiepNhan", required = false) String coQuanTiepNhan,
			@RequestParam(value = "coQuanChuyenDon", required = false) String coQuanChuyenDon,
			HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			CoQuanQuanLy cq = coQuanQuanLyRepo.findOne(donViXuLyId);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoUBNPX = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoTTTP = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			
			String soVB = "";
			mappings.put("kyTen", "");
			mappings.put("capHanhChinh", "");
			mappings.put("coQuanTrucThuoc", "");
			String chucDanh = "...............................................(1)";
			
			if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri()) || cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDQH.getGiaTri()) ||
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNPX.getGiaTri())) {
				mappings.put("capHanhChinh", WordUtil.splitWord(cq.getTen()).toUpperCase());
				if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri())) {
					mappings.put("kyTen", "ỦY BAN NHÂN DÂN".toUpperCase());
				} else { 
					mappings.put("kyTen", "CHỦ TỊCH".toUpperCase());
				}
				soVB = "UBND";
			} else if (cq.getId() == Long.valueOf(thamSoTTTP.getGiaTri()) || 
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoSBN.getGiaTri())) {
				CoQuanQuanLy ubndtp = coQuanQuanLyRepo.findOne(Long.valueOf(thamSoUBNDTPDN.getGiaTri()));
				mappings.put("capHanhChinh", ubndtp.getTen().toUpperCase());
				mappings.put("coQuanTrucThuoc", cq.getTen().toUpperCase());
				mappings.put("kyTen", "Giám đốc sở".toUpperCase());
				soVB = Utils.splitWords(cq.getTen());
			}
			
			if (donViTiepNhanDonId != null) {
				CoQuanQuanLy coQuanTiepNhanDon = coQuanQuanLyRepo.findOne(donViTiepNhanDonId);
				String coQuan = coQuanTiepNhanDon.getTen();
				if (coQuanTiepNhanDon.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri()) || 
						coQuanTiepNhanDon.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDQH.getGiaTri()) ||
						coQuanTiepNhanDon.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNPX.getGiaTri())) {
					if (coQuanTiepNhanDon.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri())) {
						chucDanh = "ỦY BAN NHÂN DÂN";
						coQuan = coQuan.replace("UBND ", "");
					} else { 
						chucDanh = "CHỦ TỊCH";
					}
				} else if (coQuanTiepNhanDon.getId() == Long.valueOf(thamSoTTTP.getGiaTri()) || 
						coQuanTiepNhanDon.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoSBN.getGiaTri())) {
					chucDanh = "Giám đốc sở";
				}
				chucDanh = String.format("%s %s", WordUtil.capitaliseName(chucDanh), coQuan);
			}
			
			mappings.put("chucDanh", chucDanh);
			mappings.put("soVB", soVB);
			mappings.put("ngayTiepNhan", ngayTiepNhan);
			mappings.put("nguoiDungDon", nguoiDungDon);
			mappings.put("diaChiNguoiDungDon", diaChiNguoiDungDon);
			mappings.put("noiDung", noiDung != null && noiDung != "" ? noiDung.concat(".") : "");
			mappings.put("coQuanTiepNhan", coQuanTiepNhan);
			mappings.put("coQuanChuyenDon", cq.getTen());
			
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/tocao/XLD_PHIEU_CHUYEN_DON_TO_CAO.docx").getFile(), mappings, 
					"Phiếu thông báo về việc chuyển đơn tố cáo.docx");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuDuThaoThongBaoThuLyGQTC")
	@ApiOperation(value = "In phiếu dự thảo thông báo thụ lý giải quyết tố cáo", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordDuThaoThongBaoThuLyGQTC(
			@RequestParam(value = "donViXuLyId", required = true) Long donViXuLyId,
			@RequestParam(value = "donId", required = true) Long donId,
			@RequestParam(value = "doiTuongGiaiQuyet", required = false) String doiTuongGiaiQuyet,
			@RequestParam(value = "nguoiDungDon", required = true) String nguoiDungDon,
			@RequestParam(value = "doiTuongBiToCao", required = false) String doiTuongBiToCao,
			@RequestParam(value = "noiDungToCao", required = false) String noiDungToCao, HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			CoQuanQuanLy cq = coQuanQuanLyRepo.findOne(donViXuLyId);
			Don don = donRepo.findOne(donId);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoUBNPX = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoTTTP = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			
			String soVB = "";
			mappings.put("capHanhChinh", "");
			mappings.put("coQuanTrucThuoc", "");
			if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri()) || cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDQH.getGiaTri()) ||
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNPX.getGiaTri())) {
				mappings.put("capHanhChinh", WordUtil.splitWord(cq.getTen()).toUpperCase());
				soVB = "UBND";
			} else if (cq.getId() == Long.valueOf(thamSoTTTP.getGiaTri()) || 
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoSBN.getGiaTri())) {
				CoQuanQuanLy ubndtp = coQuanQuanLyRepo.findOne(Long.valueOf(thamSoUBNDTPDN.getGiaTri()));
				mappings.put("capHanhChinh", ubndtp.getTen().toUpperCase());
				mappings.put("coQuanTrucThuoc", cq.getTen().toUpperCase());
				soVB = Utils.splitWords(cq.getTen());
			}
			
			mappings.put("coQuanChuyenDon", "................................................................(1)");
			if (don != null && don.isDonChuyen()) {
				Don donGoc = donRepo.findOne(don.getDonGocId());
				mappings.put("coQuanChuyenDon", donGoc.getDonViXuLyGiaiQuyet().getTen());
			}
			
			mappings.put("soVB", soVB);
			mappings.put("doiTuongGiaiQuyet", cq.getTen());
			mappings.put("nguoiDungDon", nguoiDungDon);
			mappings.put("doiTuongBiToCao", doiTuongBiToCao);
			mappings.put("noiDungToCao", noiDungToCao != null && noiDungToCao != "" ? noiDungToCao.concat(".") : "");
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/tocao/XLD_PHIEU_DU_THAO_THONG_BAO_THU_LY_GQTC.docx").getFile(), mappings, 
					"Phiếu thông báo về việc thụ lý giải quyết tố cáo.docx");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuDuThaoThongBaoThuLyKhieuNai")
	@ApiOperation(value = "In phiếu dự thảo thông báo thụ lý giải quyết khiếu nại", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordDuThaoThongBaoThuLyKhieuNai(
			@RequestParam(value = "donViXuLyId", required = true) Long donViXuLyId,
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "coQuanTiepNhan", required = true) String coQuanTiepNhan,
			@RequestParam(value = "nguoiKhieuNai", required = true) String nguoiKhieuNai,
			@RequestParam(value = "diaChiNguoiKhieuNai", required = false) String diaChiNguoiKhieuNai,
			@RequestParam(value = "SoCMNDHoChieu", required = false) String soCMNDHoChieu,
			@RequestParam(value = "ngayCap", required = false) String ngayCap,
			@RequestParam(value = "noiCap", required = false) String noiCap,
			@RequestParam(value = "noiDungKhieuNai", required = false) String noiDungKhieuNai,
			HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			CoQuanQuanLy cq = coQuanQuanLyRepo.findOne(donViXuLyId);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoUBNPX = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoTTTP = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			
			String soVB = "";
			mappings.put("capHanhChinh", "");
			mappings.put("coQuanTrucThuoc", "");
			if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri()) || cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDQH.getGiaTri()) ||
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNPX.getGiaTri())) {
				mappings.put("capHanhChinh", WordUtil.splitWord(cq.getTen()).toUpperCase());
				soVB = "UBND";
			} else if (cq.getId() == Long.valueOf(thamSoTTTP.getGiaTri()) || 
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoSBN.getGiaTri())) {
				CoQuanQuanLy ubndtp = coQuanQuanLyRepo.findOne(Long.valueOf(thamSoUBNDTPDN.getGiaTri()));
				mappings.put("capHanhChinh", ubndtp.getTen().toUpperCase());
				mappings.put("coQuanTrucThuoc", cq.getTen().toUpperCase());
				soVB = Utils.splitWords(cq.getTen());
			}
			
			mappings.put("soVB", soVB);
			mappings.put("ngayTiepNhan", ngayTiepNhan);
			mappings.put("coQuanTiepNhan", cq.getTen());
			mappings.put("nguoiKhieuNai", nguoiKhieuNai);
			mappings.put("diaChiNguoiKhieuNai", diaChiNguoiKhieuNai);
			mappings.put("SoCMNDHoChieu", StringUtils.isNotBlank(soCMNDHoChieu) ? soCMNDHoChieu : ".................");
			mappings.put("ngayCap", StringUtils.isNotBlank(ngayCap) ? ngayCap : ".................");
			mappings.put("noiCap", StringUtils.isNotBlank(noiCap) ? noiCap : ".................");
			mappings.put("noiDungKhieuNai", noiDungKhieuNai != null && noiDungKhieuNai != "" ? noiDungKhieuNai.concat(".") : "");
			
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/khieunai/XLD_PHIEU_DU_THAO_THONG_BAO_THU_LY_KHIEU_NAI.docx").getFile(), mappings, 
					"Phiếu thông báo về việc thụ lý giải quyết khiếu nại.docx");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuDuThaoThongBaoThuLyKienNghi")
	@ApiOperation(value = "In phiếu dự thảo thông báo thụ lý giải quyết kiến nghị", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordDuThaoThongBaoThuLyKienNghi(
			@RequestParam(value = "donViXuLyId", required = true) Long donViXuLyId,
			@RequestParam(value = "loaiDon", required = true) String loaiDon,
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "nguoiDungDon", required = true) String nguoiDungDon,
			@RequestParam(value = "noiDung", required = true) String noiDung,
			@RequestParam(value = "diaChi", required = false) String diaChi, HttpServletResponse response) {
		
		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			CoQuanQuanLy cq = coQuanQuanLyRepo.findOne(donViXuLyId);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoUBNPX = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoTTTP = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			
			String soVB = "";
			mappings.put("capHanhChinh", "");
			mappings.put("coQuanTrucThuoc", "");
			if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri()) || cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDQH.getGiaTri()) ||
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNPX.getGiaTri())) {
				mappings.put("capHanhChinh", WordUtil.splitWord(cq.getTen()).toUpperCase());
				soVB = "UBND";
			} else if (cq.getId() == Long.valueOf(thamSoTTTP.getGiaTri()) || 
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoSBN.getGiaTri())) {
				CoQuanQuanLy ubndtp = coQuanQuanLyRepo.findOne(Long.valueOf(thamSoUBNDTPDN.getGiaTri()));
				mappings.put("capHanhChinh", ubndtp.getTen().toUpperCase());
				mappings.put("coQuanTrucThuoc", cq.getTen().toUpperCase());
				soVB = Utils.splitWords(cq.getTen());
			}
			
			mappings.put("soVB", soVB);
			mappings.put("loaiDonTieuDe", loaiDon.toUpperCase());
			mappings.put("loaiDon", loaiDon.toLowerCase());
			mappings.put("ngayTiepNhan", ngayTiepNhan);
			mappings.put("nguoiDungDon", nguoiDungDon);
			mappings.put("noiDung", noiDung != null && noiDung != "" ? noiDung.concat(".") : "");
			mappings.put("diaChi", diaChi);
			
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/kiennghiphananh/XLD_PHIEU_DU_THAO_THONG_BAO_THU_LY_KIEN_NGHI.docx").getFile(), mappings, 
					"Phiếu thông báo về việc thụ lý giải quyết kiến nghị - phản ánh.docx");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuHuongDanToCao")
	@ApiOperation(value = "In phiếu hướng dẫn tố cáo", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportPhieuHuongDanToCao(
			@RequestParam(value = "donViXuLyId", required = true) Long donViXuLyId,
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
			CoQuanQuanLy cq = coQuanQuanLyRepo.findOne(donViXuLyId);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoUBNPX = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoTTTP = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			
			String soVB = "";
			mappings.put("kyTen", "");
			mappings.put("capHanhChinh", "");
			mappings.put("coQuanTrucThuoc", "");
			if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri()) || cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDQH.getGiaTri()) ||
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNPX.getGiaTri())) {
				mappings.put("capHanhChinh", WordUtil.splitWord(cq.getTen()).toUpperCase());
				if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri())) {
					mappings.put("kyTen", "ỦY BAN NHÂN DÂN".toUpperCase());
				} else { 
					mappings.put("kyTen", "CHỦ TỊCH".toUpperCase());
				}
				soVB = "UBND";
			} else if (cq.getId() == Long.valueOf(thamSoTTTP.getGiaTri()) || 
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoSBN.getGiaTri())) {
				CoQuanQuanLy ubndtp = coQuanQuanLyRepo.findOne(Long.valueOf(thamSoUBNDTPDN.getGiaTri()));
				mappings.put("capHanhChinh", ubndtp.getTen().toUpperCase());
				mappings.put("coQuanTrucThuoc", cq.getTen().toUpperCase());
				mappings.put("kyTen", "Giám đốc sở".toUpperCase());
				soVB = Utils.splitWords(cq.getTen());
			}
			
			mappings.put("soVB", soVB);
			mappings.put("ngayTiepNhan", ngayTiepNhan);
			mappings.put("coQuanTiepNhan", cq.getTen());
			mappings.put("hoVaTen", hoVaTen);
			mappings.put("soCMND", soCMND != null && soCMND != "" ? soCMND : "................");
			mappings.put("ngayCap", ngayCap != null && ngayCap != "" ? ngayCap : "................");
			mappings.put("noiCap", noiCap != null && noiCap != "" ? noiCap : "................");
			mappings.put("diaChi", diaChi);
			mappings.put("noiDung", noiDung != null && noiDung != "" ? noiDung.concat(".") : "");
			
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/tocao/XLD_PHIEU_HUONG_DAN_TO_CAO.docx").getFile(), mappings, 
					"Phiếu thông báo về việc hướng dẫn người tố cáo.docx");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuHuongDanKienNghi")
	@ApiOperation(value = "In phiếu hướng dẫn kiến nghị", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportPhieuHuongDanKienNghi(
			@RequestParam(value = "donViXuLyId", required = true) Long donViXuLyId,
			@RequestParam(value = "ngayTiepNhan", required = false) String ngayTiepNhan,
			@RequestParam(value = "hoVaTen", required = false) String hoVaTen,
			//@RequestParam(value = "soCMND", required = false) String soCMND,
			//@RequestParam(value = "ngayCap", required = false) String ngayCap,
			//@RequestParam(value = "noiCap", required = false) String noiCap,
			@RequestParam(value = "diaChi", required = false) String diaChi,
			@RequestParam(value = "noiDung", required = false) String noiDung,
			HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			CoQuanQuanLy cq = coQuanQuanLyRepo.findOne(donViXuLyId);
			ThamSo thamSoUBNDTP = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_TINH_TP"));
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoUBNPX = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoTTTP = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			
			String soVB = "";
			mappings.put("kyTen", "");
			mappings.put("capHanhChinh", "");
			mappings.put("coQuanTrucThuoc", "");
			if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri()) || cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDQH.getGiaTri()) ||
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNPX.getGiaTri())) {
				mappings.put("capHanhChinh", WordUtil.splitWord(cq.getTen()).toUpperCase());
				if (cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoUBNDTP.getGiaTri())) {
					mappings.put("kyTen", "ỦY BAN NHÂN DÂN".toUpperCase());
				} else { 
					mappings.put("kyTen", "CHỦ TỊCH".toUpperCase());
				}
				soVB = "UBND";
			} else if (cq.getId() == Long.valueOf(thamSoTTTP.getGiaTri()) || 
					cq.getCapCoQuanQuanLy().getId() == Long.valueOf(thamSoSBN.getGiaTri())) {
				CoQuanQuanLy ubndtp = coQuanQuanLyRepo.findOne(Long.valueOf(thamSoUBNDTPDN.getGiaTri()));
				mappings.put("capHanhChinh", ubndtp.getTen().toUpperCase());
				mappings.put("coQuanTrucThuoc", cq.getTen().toUpperCase());
				mappings.put("kyTen", "Giám đốc sở".toUpperCase());
				soVB = Utils.splitWords(cq.getTen());
			}
			
			mappings.put("soVB", soVB);
			mappings.put("ngayTiepNhan", ngayTiepNhan);
			mappings.put("coQuanTiepNhan", cq.getTen());
			mappings.put("hoVaTen", hoVaTen);
			//mappings.put("soCMND", soCMND != null && soCMND != "" ? soCMND : "................");
			//mappings.put("ngayCap", ngayCap != null && ngayCap != "" ? ngayCap : "................");
			//mappings.put("noiCap", noiCap != null && noiCap != "" ? noiCap : "................");
			mappings.put("diaChi", diaChi);
			mappings.put("noiDung", noiDung != null && noiDung != "" ? noiDung.concat(".") : "");
			
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/xulydon/kiennghiphananh/XLD_PHIEU_HUONG_DAN_VIET_DON_KIEN_NGHI.docx").getFile(), mappings, 
					"Phiếu hướng dẫn viết lại đơn kiến nghị.docx");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	private void disableDonChuyenCu(Long donId, Long congChucId, Long donViId) {
		List<Don> donChuyenCus = (List<Don>) donRepo.findAll(donService.predFindOld(donId, donViId));
		if (donChuyenCus != null) {
			for (Don don : donChuyenCus) {
				don.setOld(true);
				donService.save(don, congChucId);
			}
		}
	}

	private void disableXuLyDonCu(VaiTroEnum vaiTro, Long donId, Long congChucId, Long phongBanId, Long donViId) {
		List<XuLyDon> xuLyDonCu = (List<XuLyDon>) repo.findAll(xuLyDonService.predFindOld(donId, phongBanId, donViId, vaiTro));
		if (xuLyDonCu != null) {
			for (XuLyDon xld: xuLyDonCu) {
				xld.setOld(true);
				xuLyDonService.save(xld, congChucId);
			}
		}
	}
	
	private void disableXuLyDonLanhDaoVanThuCu(VaiTroEnum vaiTro, Long donId, Long congChucId, Long donViId) {
		List<XuLyDon> xuLyDonCu = (List<XuLyDon>) repo.findAll(xuLyDonService.predFindLanhDaoVanThuOld(vaiTro, donId, donViId));
		if (xuLyDonCu != null) {
			for (XuLyDon xld : xuLyDonCu) {
				if (!xld.isOld()) {
					xld.setOld(true);
					xuLyDonService.save(xld, congChucId);
				}
			}
		}
	}
	
	private void disableXuLyDonTruongPhongCu(VaiTroEnum vaiTro, Long donId, Long congChucId, Long phongBanId, Long donViId) {
		List<XuLyDon> xuLyDonCu = (List<XuLyDon>) repo.findAll(xuLyDonService.predFindOld(donId, phongBanId, donViId, vaiTro));
		if (xuLyDonCu != null) {
			for (XuLyDon xld: xuLyDonCu) {
				xld.setOld(true);
				xuLyDonService.save(xld, congChucId);
			}
		}
	}
	
	private void disableXuLyDonChuyenVienCu(VaiTroEnum vaiTro, Long donId, Long congChucId, Long canBoId, Long phongBanId, Long donViId) {
		List<XuLyDon> xuLyDonCu = (List<XuLyDon>) repo.findAll(xuLyDonService.predFindChuyenVienOld(donId, canBoId, phongBanId, donViId, vaiTro));
		if (xuLyDonCu != null) {
			for (XuLyDon xld: xuLyDonCu) {
				xld.setOld(true);
				xuLyDonService.save(xld, congChucId);
			}
		}
	}
	
	private void disableXuLyDonKhongQuyTrinh(Long donId, Long congChucId, Long phongBanId, Long donViId) {
		List<XuLyDon> xuLyDonCu = (List<XuLyDon>) repo.findAll(xuLyDonService.predFindChucVuIsNull(donId, phongBanId, donViId));
		if (xuLyDonCu != null) {
			for (XuLyDon xld: xuLyDonCu) {
				xld.setOld(true);
				xuLyDonService.save(xld, congChucId);
			}
		}
	}
	
	private boolean kiemTraDonViCoQuyTrinhXLD(Long donViXuLyXLD) { 
		boolean coQuyTrinh = true;
		State beginState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.BAT_DAU));
		Predicate predicateProcess = processService.predicateFindAllByDonVi(coQuanQuanLyRepo.findOne(donViXuLyXLD), ProcessTypeEnum.XU_LY_DON);
		List<Process> listProcess = (List<Process>) repoProcess.findAll(predicateProcess);
		List<State> listState = new ArrayList<State>();
		List<Process> listProcessHaveBeginState = new ArrayList<Process>();
		for (Process processFromList : listProcess) {
			Predicate predicate = serviceState.predicateFindAll(beginState.getId(), processFromList, repoTransition);
			listState = ((List<State>) repoState.findAll(predicate));
			if (listState.size() > 0) {
				State state = listState.get(0);
				if (!state.getType().equals(FlowStateEnum.KET_THUC)) {								
					listProcessHaveBeginState.add(processFromList);
					break;
				}						
			}
		}
		if (listProcessHaveBeginState.size() < 1) {
			coQuyTrinh = false;
		}
		return coQuyTrinh;
	}
	
	public XuLyDon lanhDaoGiaoViec(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long donViId, Long congChucId,
			FlowStateEnum nextState) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		XuLyDon xuLyDonTruongPhong = new XuLyDon();
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		Don don = donRepo.findOne(donId);
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setPhongBanXuLyChiDinh(xuLyDon.getPhongBanXuLyChiDinh());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getNoiDungYeuCauXuLy());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLyChiDinh());
		xuLyDonTiepTheo.setPhongBanXuLyChiDinh(xuLyDon.getPhongBanXuLyChiDinh());
		xuLyDonTiepTheo.setChucVuGiaoViec(VaiTroEnum.LANH_DAO);
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		xuLyDonTiepTheo.setCanBoGiaoViec(congChuc);
		if (FlowStateEnum.LANH_DAO_GIAO_VIEC_TRUONG_PHONG.equals(nextState)) {
			lichSuQTXLD.setTen(QuaTrinhXuLyEnum.GIAO_PHONG_BAN.getText());
			xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
			xuLyDonTiepTheo.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
			disableXuLyDonCu(VaiTroEnum.TRUONG_PHONG, donId, congChucId, xuLyDonTiepTheo.getPhongBanXuLy().getId(), donViId);			
			xuLyDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
			Utils.changeQuyenTuXuLy(don, false, false, false);
		} else {
			Utils.changeQuyenTuXuLy(don, true, false, false);
			lichSuQTXLD.setTen(QuaTrinhXuLyEnum.GIAO_CAN_BO_XU_LY.getText());
			List<Transition> listTransitionEnd = (List<Transition>) transitionRepo.findAll(transitionService.predicateFindLast(donViId, ProcessTypeEnum.XU_LY_DON.toString(), repoProcess));
			if (listTransitionEnd.size() > 0) {
				xuLyDonTiepTheo.setNextForm(listTransitionEnd.get(0).getForm());
			}
			
			xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
			xuLyDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
			xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh());
			xuLyDonTiepTheo.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
			
			//disableXuLyDonCu(VaiTroEnum.CHUYEN_VIEN, donId, congChucId, xuLyDonTiepTheo.getPhongBanXuLy().getId(), donViId);
			disableXuLyDonChuyenVienCu(VaiTroEnum.CHUYEN_VIEN, donId, congChucId, xuLyDonTiepTheo.getCanBoXuLyChiDinh().getId(),
					xuLyDonTiepTheo.getPhongBanXuLy().getId(), donViId);
			
			//Tat ca truong phong
			xuLyDonTruongPhong.setNguoiTao(congChuc);
			xuLyDonTruongPhong.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
			xuLyDonTruongPhong.setDon(xuLyDonHienTai.getDon());
			xuLyDonTruongPhong.setPhongBanXuLy(xuLyDon.getPhongBanXuLyChiDinh());
			xuLyDonTruongPhong.setPhongBanXuLyChiDinh(xuLyDon.getPhongBanXuLyChiDinh());
			xuLyDonTruongPhong.setChucVu(VaiTroEnum.TRUONG_PHONG);
			xuLyDonTruongPhong.setChucVuGiaoViec(VaiTroEnum.LANH_DAO);
			xuLyDonTruongPhong.setNoiDungXuLy(xuLyDon.getNoiDungYeuCauXuLy());
			xuLyDonTruongPhong.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
			xuLyDonTruongPhong.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
			xuLyDonTruongPhong.setDonViXuLy(coQuanQuanLyRepo.findOne(donViId));
			if (xuLyDonHienTai.isDonChuyen()) {
				xuLyDonTruongPhong.setDonChuyen(xuLyDonHienTai.isDonChuyen());
				xuLyDonTruongPhong.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
				xuLyDonTruongPhong.setCanBoChuyenDon(xuLyDonHienTai.getCanBoChuyenDon());
			}
			if (xuLyDonHienTai.isDonTra()) {
				xuLyDonTruongPhong.setDonTra(true);
			}
			Predicate predicate = processService.predicateFindAllByDonVi(coQuanQuanLyRepo.findOne(donViId), ProcessTypeEnum.XU_LY_DON);
			List<Process> listProcessXLD = (List<Process>) repoProcess.findAll(predicate);
			
			Transition transitionXLD = null;
			for (Process processFromList : listProcessXLD) {
				transitionXLD = repoTransition.findOne(transitionService.predicateFindFromCurrentAndNext(FlowStateEnum.LANH_DAO_GIAO_VIEC_TRUONG_PHONG, 
						FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO, processFromList));
				if (transitionXLD != null) {
					break;
				}
			}
			
			xuLyDonTruongPhong.setNextForm(transitionXLD.getForm());
			xuLyDonTruongPhong.setNextState(xuLyDon.getNextState());
			xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 2);
		}

		if (xuLyDonHienTai.isDonChuyen()) {
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
			xuLyDonTiepTheo.setCanBoChuyenDon(xuLyDonHienTai.getCanBoChuyenDon());
		}
		if (xuLyDonHienTai.isDonTra()) {
			xuLyDonTiepTheo.setDonTra(true);
		}
		
		//set don vi 
		xuLyDonTiepTheo.setDonViXuLy(coQuanQuanLyRepo.findOne(donViId));
		
		// set don
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setLanhDaoDuyet(true);
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setCoQuanDangGiaiQuyet(coQuanQuanLyRepo.findOne(donViId));
		don.setCanBoCoTheThuHoi(congChuc);
		
		// set thoi han xu ly cho don
		if (xuLyDon.getThoiHanXuLy() != null) {
			// set thoi han xu ly cho don
			don.setThoiHanXuLyXLD(xuLyDon.getThoiHanXuLy());
		}
		don.setCurrentState(xuLyDonHienTai.getNextState());
		
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setNoiDung(xuLyDonHienTai.getNoiDungXuLy());
		lichSuQTXLD.setDonViXuLy(xuLyDonTiepTheo.getDonViXuLy());
		lichSuQTXLD.setLaLanhDao(true);
		
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, donId, 
				xuLyDonTiepTheo.getDonViXuLy().getId());
		lichSuQTXLD.setThuTuThucHien(thuTu);
		xuLyDonTiepTheo = xuLyDonService.save(xuLyDonTiepTheo, congChucId);
		if (FlowStateEnum.LANH_DAO_GIAO_VIEC_CAN_BO.equals(nextState)) {
			don.setXuLyDonCuoiCungId(xuLyDonTiepTheo.getId());
		}
		
		donService.save(don, congChucId);
		xuLyDonService.save(xuLyDonHienTai, congChucId);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		// Luu buoc cua trương phong khi lanh dao giao viec can bo
		if (FlowStateEnum.LANH_DAO_GIAO_VIEC_CAN_BO.equals(nextState)) {
			disableXuLyDonCu(VaiTroEnum.TRUONG_PHONG, donId, congChucId, xuLyDonTruongPhong.getPhongBanXuLy().getId(), donViId);
			xuLyDonService.save(xuLyDonTruongPhong, congChucId);
		}
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				coQuanQuanLyRepo.findOne(donViId));
		return xuLyDonTiepTheo;
	}

	public XuLyDon trinhDon(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long coQuanQuanLyId, Long donViId, Long congChucId,
			String chucVu) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		
		//xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
//		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getNoiDungThongTinTrinhLanhDao());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		
//		disableXuLyDonCu(VaiTroEnum.LANH_DAO, donId, congChucId, xuLyDonHienTai.getPhongBanXuLy().getId(),
//				donViId);
		disableXuLyDonLanhDaoVanThuCu(VaiTroEnum.LANH_DAO, donId, congChucId, donViId);
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.LANH_DAO);
		xuLyDonTiepTheo.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		
		//xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		//xuLyDonTiepTheo.setNoiDungXuLy(xuLyDon.getNoiDungThongTinTrinhLanhDao());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		
		if (xuLyDonHienTai.isDonChuyen()) {
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
			xuLyDonTiepTheo.setCanBoChuyenDon(xuLyDonHienTai.getCanBoChuyenDon());
		}
		
		if (xuLyDonHienTai.isDonTra()) {
			xuLyDonTiepTheo.setDonTra(true);
		}
		
		if ((xuLyDonHienTai.getCongChuc() != null && congChucId == xuLyDonHienTai.getCongChuc().getId()) || 
				xuLyDonHienTai.getCongChuc() == null) {
			xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
			xuLyDonHienTai.setChucVu(VaiTroEnum.valueOf(chucVu));
			xuLyDonHienTai.setChucVu2(null);
		} else {
			XuLyDon xuLyDonOwner = new XuLyDon();
			
			xuLyDonHienTai.setChucVu(xuLyDonHienTai.getCongChuc().getNguoiDung().getVaiTroMacDinh().getLoaiVaiTro());
			xuLyDonHienTai.setChucVu2(null);
			xuLyDonOwner.setChucVu(VaiTroEnum.valueOf(chucVu));
			xuLyDonOwner.setChucVu2(null);
			xuLyDonOwner.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
			xuLyDonOwner.setNextState(xuLyDonHienTai.getNextState());
			xuLyDonOwner.setNextForm(xuLyDonHienTai.getNextForm());
			xuLyDonOwner.setDon(xuLyDonHienTai.getDon());
			xuLyDonOwner.setCongChuc(congChucRepo.findOne(congChucId));
			xuLyDonOwner.setPhongBanXuLy(xuLyDonOwner.getCongChuc().getCoQuanQuanLy());
			xuLyDonOwner.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
			xuLyDonOwner.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
			xuLyDonOwner.setTrangThaiDon(xuLyDonHienTai.getTrangThaiDon());
			xuLyDonOwner.setNoiDungXuLy(xuLyDonHienTai.getNoiDungXuLy());
			xuLyDonOwner.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
			xuLyDonOwner.setCoQuanTiepNhan(xuLyDonHienTai.getCoQuanTiepNhan());
			xuLyDonOwner.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
			xuLyDonOwner.setCanBoChuyenDon(xuLyDonHienTai.getCanBoChuyenDon());
			xuLyDonOwner.setDonChuyen(xuLyDonHienTai.isDonChuyen());
			xuLyDonOwner.setDonTra(xuLyDonHienTai.isDonTra());
			
			xuLyDonTiepTheo.setThuTuThucHien(xuLyDonOwner.getThuTuThucHien() + 1);
			xuLyDonService.save(xuLyDonOwner, congChucId);
		}
		
		// set don
		Don don = donRepo.findOne(donId);
		CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViId);
		// set thoi han xu ly cho don
		if (xuLyDon.getThoiHanXuLy() != null) {
			don.setThoiHanXuLyXLD(xuLyDon.getThoiHanXuLy());
		}
		
//		Bo phanLoaiDon - cap nhat 16/11
//		if (xuLyDon.getPhanLoaiDon() != null) {
//			don.setPhanLoaiDon(xuLyDon.getPhanLoaiDon());
//			if (PhanLoaiDonEnum.DU_DIEU_KIEN_XU_LY.equals(xuLyDon.getPhanLoaiDon())) {
//				don.setLyDoKhongDuDieuKienThuLy(null);
//			} else {
//				don.setLyDoKhongDuDieuKienThuLy(xuLyDon.getLyDoKhongDuDieuKienThuLy());
//			}
//		}
		
		don.setCanBoXuLyPhanHeXLD(congChuc);
		// workflow
		don.setCurrentState(xuLyDonHienTai.getNextState());
		don.setCoQuanDangGiaiQuyet(xuLyDonHienTai.getDonViXuLy());
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DANG_XU_LY);
		don.setDonViXuLyGiaiQuyet(donVi);
		//don.setNoiDungThongTinTrinhLanhDao(xuLyDonHienTai.getNoiDungXuLy());
		
		//tao lich su qua trinh xu ly don
		donService.save(don, congChucId);
		xuLyDonService.save(xuLyDonHienTai, congChucId);
		//State state = repoState.findOne(xuLyDonHienTai.getNextState().getId());
		lichSuQuaTrinhXuLyService.saveLichSuQuaTrinhXuLy(xuLyDon.getDon(), congChuc,
				xuLyDonHienTai.getNoiDungXuLy(), xuLyDonTiepTheo.getDonViXuLy(), QuaTrinhXuLyEnum.TRINH_LANH_DAO.getText());	
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChuc, donVi);
		return xuLyDonTiepTheo;
	}	
	
	public XuLyDon trinhTruongPhong(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long donViId, Long congChucId, CoQuanQuanLy banTiepCongDan, String chucVu) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonTiepTheo = new XuLyDon();

		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
//		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getNoiDungThongTinTrinhLanhDao());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		
//		disableXuLyDonCu(VaiTroEnum.LANH_DAO, donId, congChucId, xuLyDonHienTai.getPhongBanXuLy().getId(),
//				donViId);
		disableXuLyDonLanhDaoVanThuCu(VaiTroEnum.TRUONG_PHONG, donId, congChucId, donViId);
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
		xuLyDonTiepTheo.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		
		//xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		//xuLyDonTiepTheo.setNoiDungXuLy(xuLyDon.getNoiDungThongTinTrinhLanhDao());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		xuLyDonTiepTheo.setPhongBanXuLy(banTiepCongDan);
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		if (xuLyDonHienTai.isDonChuyen()) {
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
			xuLyDonTiepTheo.setCanBoChuyenDon(xuLyDonHienTai.getCanBoChuyenDon());
		}
		
		if (xuLyDonHienTai.isDonTra()) {
			xuLyDonTiepTheo.setDonTra(true);
		}

		// set don
		Don don = donRepo.findOne(donId);
		// set thoi han xu ly cho don
		if (xuLyDon.getThoiHanXuLy() != null) {
			don.setThoiHanXuLyXLD(xuLyDon.getThoiHanXuLy());
		}
		
//		Bo phanLoaiDon - cap nhat 16/11
//		if (xuLyDon.getPhanLoaiDon() != null) {
//			don.setPhanLoaiDon(xuLyDon.getPhanLoaiDon());
//			if (PhanLoaiDonEnum.DU_DIEU_KIEN_XU_LY.equals(xuLyDon.getPhanLoaiDon())) {
//				don.setLyDoKhongDuDieuKienThuLy(null);
//			} else {
//				don.setLyDoKhongDuDieuKienThuLy(xuLyDon.getLyDoKhongDuDieuKienThuLy());
//			}
//		}
		
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		// workflow
		don.setCurrentState(xuLyDonHienTai.getNextState());
		don.setCoQuanDangGiaiQuyet(xuLyDonHienTai.getDonViXuLy());
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DANG_XU_LY);
		don.setDonViXuLyGiaiQuyet(coQuanQuanLyRepo.findOne(donViId));
		//don.setNoiDungThongTinTrinhLanhDao(xuLyDonHienTai.getNoiDungXuLy());
		
		if ((xuLyDonHienTai.getCongChuc() != null && congChucId == xuLyDonHienTai.getCongChuc().getId()) || 
				xuLyDonHienTai.getCongChuc() == null) {
			xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
			xuLyDonHienTai.setChucVu(VaiTroEnum.valueOf(chucVu));
			xuLyDonHienTai.setChucVu2(null);
		} else {
			XuLyDon xuLyDonOwner = new XuLyDon();
			
			xuLyDonHienTai.setChucVu(xuLyDonHienTai.getCongChuc().getNguoiDung().getVaiTroMacDinh().getLoaiVaiTro());
			xuLyDonHienTai.setChucVu2(null);
			xuLyDonOwner.setChucVu(VaiTroEnum.valueOf(chucVu));
			xuLyDonOwner.setChucVu2(null);
			xuLyDonOwner.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
			xuLyDonOwner.setNextState(xuLyDonHienTai.getNextState());
			xuLyDonOwner.setNextForm(xuLyDonHienTai.getNextForm());
			xuLyDonOwner.setDon(xuLyDonHienTai.getDon());
			xuLyDonOwner.setCongChuc(congChucRepo.findOne(congChucId));
			xuLyDonOwner.setPhongBanXuLy(xuLyDonOwner.getCongChuc().getCoQuanQuanLy());
			xuLyDonOwner.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
			xuLyDonOwner.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
			xuLyDonOwner.setTrangThaiDon(xuLyDonHienTai.getTrangThaiDon());
			xuLyDonOwner.setNoiDungXuLy(xuLyDonHienTai.getNoiDungXuLy());
			xuLyDonOwner.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
			xuLyDonOwner.setCoQuanTiepNhan(xuLyDonHienTai.getCoQuanTiepNhan());
			xuLyDonOwner.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
			xuLyDonOwner.setCanBoChuyenDon(xuLyDonHienTai.getCanBoChuyenDon());
			xuLyDonOwner.setDonChuyen(xuLyDonHienTai.isDonChuyen());
			xuLyDonOwner.setDonTra(xuLyDonHienTai.isDonTra());
			
			xuLyDonTiepTheo.setThuTuThucHien(xuLyDonOwner.getThuTuThucHien() + 1);
			xuLyDonService.save(xuLyDonOwner, congChucId);
		}
		
		//tao lich su qua trinh xu ly don
		donService.save(don, congChucId);
		xuLyDonService.save(xuLyDonHienTai, congChucId);
		
		String tenLanhDao = "";
		if (don.getLoaiDon().equals(LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH)) {
			tenLanhDao = "Trình trưởng ban";
		} else {
			tenLanhDao = QuaTrinhXuLyEnum.TRINH_LANH_DAO.getText();
		}

		//State state = repoState.findOne(xuLyDonHienTai.getNextState().getId());
		lichSuQuaTrinhXuLyService.saveLichSuQuaTrinhXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId),
				xuLyDonHienTai.getNoiDungXuLy(), xuLyDonTiepTheo.getDonViXuLy(), tenLanhDao);	
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				coQuanQuanLyRepo.findOne(donViId));
		return xuLyDonTiepTheo;
	}	

	public XuLyDon truongPhongGiaoViecLai(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long donViId, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonTiepTheo = new XuLyDon();

		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());

		if (xuLyDonHienTai.getPhongBanXuLy() != null) {
			CoQuanQuanLy coQuanQuanLy = xuLyDonHienTai.getPhongBanXuLy().getCha();
			xuLyDonTiepTheo.setPhongBanXuLy(coQuanQuanLy);
		}
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		disableXuLyDonCu(VaiTroEnum.LANH_DAO, donId, congChucId, xuLyDonHienTai.getPhongBanXuLy().getId(), xuLyDonHienTai.getDonViXuLy().getId());
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.LANH_DAO);
		xuLyDonTiepTheo.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		
		//set phong ban va don vi
		//xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		if (xuLyDonHienTai.isDonChuyen()) {
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		if (xuLyDonHienTai.isDonTra()) {
			xuLyDonTiepTheo.setDonTra(true);
		}
		
		// set don
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setCoQuanDangGiaiQuyet(xuLyDonHienTai.getDonViXuLy());
		don.setCurrentState(xuLyDonHienTai.getNextState());
		
		donService.save(don, congChucId);
		xuLyDonService.save(xuLyDonHienTai, congChucId);

		//State state = repoState.findOne(xuLyDonHienTai.getNextState().getId());
		lichSuQuaTrinhXuLyService.saveLichSuQuaTrinhXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId),
				xuLyDonHienTai.getNoiDungXuLy(), xuLyDonTiepTheo.getDonViXuLy(), QuaTrinhXuLyEnum.GIAO_CAN_BO_XU_LY.getText());	
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				coQuanQuanLyRepo.findOne(donViId));
		return xuLyDonTiepTheo;
	}

	public XuLyDon lanhDaoThuHoi(XuLyDon xuLyDon, XuLyDon xuLyDonThuHoi, Long congChucId, VaiTroEnum vaiTro) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		xuLyDonThuHoi.setCongChuc(congChuc);
		xuLyDonThuHoi.setNextState(xuLyDon.getNextState());
		xuLyDonThuHoi.setCanBoXuLyChiDinh(null);
		xuLyDonThuHoi.setOld(true);
		xuLyDonThuHoi.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonThuHoi.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		//disableXuLyDonCu(VaiTroEnum.CHUYEN_VIEN, donId, congChucId, xuLyDonHienTai.getPhongBanXuLy().getId(), xuLyDonHienTai.getDonViXuLy().getId());
		disableXuLyDonChuyenVienCu(VaiTroEnum.CHUYEN_VIEN, donId, congChucId, congChucId, 
				xuLyDonThuHoi.getPhongBanXuLy().getId(), xuLyDonThuHoi.getDonViXuLy().getId());
		if (vaiTro.equals(VaiTroEnum.TRUONG_PHONG)) {
			xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonThuHoi.getPhongBanXuLy());
		}
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDon(xuLyDonThuHoi.getDon());
		xuLyDonTiepTheo.setCongChuc(congChuc);
		xuLyDonTiepTheo.setChucVu(vaiTro);
		xuLyDonTiepTheo.setCanBoXuLyChiDinh(congChuc);
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonThuHoi.getThuTuThucHien() + 1);
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonThuHoi.getDonViXuLy());
		
		if (xuLyDonThuHoi.isDonChuyen()) {
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonThuHoi.getCoQuanChuyenDon());
			xuLyDonTiepTheo.setCanBoChuyenDon(xuLyDonThuHoi.getCanBoChuyenDon());
		}
		if (xuLyDonThuHoi.isDonTra()) {
			xuLyDonTiepTheo.setDonTra(true);
		}
		xuLyDonTiepTheo = xuLyDonService.save(xuLyDonTiepTheo, congChucId);
		// set don
		Don don = donRepo.findOne(donId);
//		don.setXuLyDonCuoiCung(xuLyDonTiepTheo);
		don.setCanBoXuLyPhanHeXLD(congChuc);
		don.setCoQuanDangGiaiQuyet(xuLyDonThuHoi.getDonViXuLy());
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setXuLyDonCuoiCungId(null);
		don.setCurrentState(xuLyDonThuHoi.getNextState());
		don.setCanBoCoTheThuHoi(null);
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChuc);
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		if (VaiTroEnum.LANH_DAO.equals(vaiTro)) {
			lichSuQTXLD.setTen(QuaTrinhXuLyEnum.LANH_DAO_THU_HOI.getText());
		} else if (VaiTroEnum.TRUONG_PHONG.equals(vaiTro)) {
			lichSuQTXLD.setTen(QuaTrinhXuLyEnum.TRUONG_PHONG_THU_HOI.getText());
		}
		lichSuQTXLD.setNoiDung(xuLyDonThuHoi.getNoiDungXuLy());
		lichSuQTXLD.setDonViXuLy(xuLyDonTiepTheo.getDonViXuLy());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, donId, 
				xuLyDonTiepTheo.getDonViXuLy().getDonVi().getId());
		lichSuQTXLD.setThuTuThucHien(thuTu);
		
		Utils.changeQuyenTuXuLy(don, false, false, false);
		donService.save(don, congChucId);
		xuLyDonService.save(xuLyDonThuHoi, congChucId);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonThuHoi.getThuTuThucHien() + 1);
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				xuLyDonTiepTheo.getDonViXuLy().getDonVi());
		return xuLyDonTiepTheo;
	}
	
	public XuLyDon truongPhongGiaoViec(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		xuLyDonHienTai.setCongChuc(congChuc);
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		//disableXuLyDonCu(VaiTroEnum.CHUYEN_VIEN, donId, congChucId, xuLyDonHienTai.getPhongBanXuLy().getId(), xuLyDonHienTai.getDonViXuLy().getId());
		disableXuLyDonChuyenVienCu(VaiTroEnum.CHUYEN_VIEN, donId, congChucId, xuLyDonHienTai.getCanBoXuLyChiDinh().getId(), xuLyDonHienTai.getPhongBanXuLy().getId(), xuLyDonHienTai.getDonViXuLy().getId());
		
		List<Transition> listTransitionEnd = (List<Transition>) transitionRepo.findAll(transitionService.predicateFindLast(xuLyDonHienTai.getDonViXuLy().getId(), 
				ProcessTypeEnum.XU_LY_DON.toString(), repoProcess));
		if (listTransitionEnd.size() > 0) {
			xuLyDonTiepTheo.setNextForm(listTransitionEnd.get(0).getForm());
		}
		
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
//		xuLyDonTiepTheo.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setChucVuGiaoViec(VaiTroEnum.TRUONG_PHONG);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setPhongBanXuLyChiDinh(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		xuLyDonTiepTheo.setCanBoGiaoViec(congChuc);
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		if (xuLyDonHienTai.isDonChuyen()) {
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
			xuLyDonTiepTheo.setCanBoChuyenDon(xuLyDonHienTai.getCanBoChuyenDon());
		}
		if (xuLyDonHienTai.isDonTra()) {
			xuLyDonTiepTheo.setDonTra(true);
		}
		xuLyDonTiepTheo = xuLyDonService.save(xuLyDonTiepTheo, congChucId);
		// set don
		Don don = donRepo.findOne(donId);
//		don.setXuLyDonCuoiCung(xuLyDonTiepTheo);
		don.setCanBoXuLyPhanHeXLD(congChuc);
		don.setCoQuanDangGiaiQuyet(xuLyDonHienTai.getDonViXuLy());
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setXuLyDonCuoiCungId(xuLyDonTiepTheo.getId());
		don.setCanBoCoTheThuHoi(congChuc);
		don.setLanhDaoDuyet(true);
		don.setCurrentState(xuLyDonHienTai.getNextState());
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChuc);
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setTen(QuaTrinhXuLyEnum.GIAO_CAN_BO_XU_LY.getText());
		lichSuQTXLD.setNoiDung(xuLyDonHienTai.getNoiDungXuLy());
		lichSuQTXLD.setDonViXuLy(xuLyDonTiepTheo.getDonViXuLy());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, donId, 
				xuLyDonTiepTheo.getDonViXuLy().getDonVi().getId());
		lichSuQTXLD.setThuTuThucHien(thuTu);
		
		Utils.changeQuyenTuXuLy(don, true, false, false);
		donService.save(don, congChucId);
		xuLyDonService.save(xuLyDonHienTai, congChucId);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				xuLyDonTiepTheo.getDonViXuLy().getDonVi());
		return xuLyDonTiepTheo;
	}

	public XuLyDon chuyenVienGiaoViecLai(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonTiepTheo = new XuLyDon();

		xuLyDonHienTai.setNextState(xuLyDon.getNextState());

		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		disableXuLyDonCu(VaiTroEnum.TRUONG_PHONG, donId, congChucId, xuLyDonHienTai.getPhongBanXuLy().getId(), xuLyDonHienTai.getDonViXuLy().getId());
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		xuLyDonTiepTheo.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setCanBoXuLyChiDinh(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLyChiDinh(xuLyDonHienTai.getPhongBanXuLyChiDinh());
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		if (xuLyDonHienTai.isDonChuyen()) {
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		if (xuLyDonHienTai.isDonTra()) {
			xuLyDonTiepTheo.setDonTra(true);
		}
		
		// set don
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCoQuanDangGiaiQuyet(xuLyDonHienTai.getDonViXuLy());
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setCurrentState(xuLyDonHienTai.getNextState());
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		State state = repoState.findOne(xuLyDonHienTai.getNextState().getId());
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setTen(state.getTenVietTat());
		lichSuQTXLD.setNoiDung(xuLyDonHienTai.getNoiDungXuLy());
		lichSuQTXLD.setDonViXuLy(xuLyDonTiepTheo.getDonViXuLy());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, donId, 
				xuLyDonTiepTheo.getDonViXuLy().getId());
		lichSuQTXLD.setThuTuThucHien(thuTu);
		
		donService.save(don, congChucId);
		xuLyDonService.save(xuLyDonHienTai, congChucId);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				xuLyDonTiepTheo.getDonViXuLy().getDonVi());
		return xuLyDonTiepTheo;
	}

	public XuLyDon chuyenVienChuyenChoVanThuYeuCauGapLanhDao(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId) {
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonHienTai.setNgayHenGapLanhDao(Utils.localDateTimeNow());
		xuLyDonHienTai.setDiaDiem(xuLyDon.getDiaDiem());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		
		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
		don.setNgayLapDonGapLanhDaoTmp(Utils.localDateTimeNow());
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setNgayKetThucXLD(Utils.localDateTimeNow());
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setCoQuanDangGiaiQuyet(xuLyDonHienTai.getDonViXuLy());
		don.setHuongXuLyXLD(xuLyDonHienTai.getHuongXuLy());
		don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DA_XU_LY);
		don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.YEU_CAU_GAP_LANH_DAO);
		don.setCanBoCoTheThuHoi(null);
		
		boolean coQuyTrinh = kiemTraDonViCoQuyTrinhXLD(xuLyDonHienTai.getDonViXuLy().getId());
		if (!coQuyTrinh) { 
			don.setLanhDaoDuyet(true);
		}

		if (don.getDonViXuLyGiaiQuyet() == null) {
			don.setDonViXuLyGiaiQuyet(xuLyDonHienTai.getDonViXuLy());
		}
		don.setTrangThaiYeuCauGapLanhDao(TrangThaiYeuCauGapLanhDaoEnum.CHO_XIN_Y_KIEN);
		don.setNgayThucHienKetQuaXuLy(Utils.localDateTimeNow());
		
//		Bo phanLoaiDon - cap nhat 16/11
//		if (xuLyDon.getPhanLoaiDon() != null) {
//			don.setPhanLoaiDon(xuLyDon.getPhanLoaiDon());
//			if (PhanLoaiDonEnum.DU_DIEU_KIEN_XU_LY.equals(xuLyDon.getPhanLoaiDon())) {
//				don.setLyDoKhongDuDieuKienThuLy(null);
//			} else {
//				don.setLyDoKhongDuDieuKienThuLy(xuLyDon.getLyDoKhongDuDieuKienThuLy());
//			}
//		}
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		State state = repoState.findOne(xuLyDonHienTai.getNextState().getId());
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setTen(state.getTenVietTat());
		lichSuQTXLD.setNoiDung(xuLyDonHienTai.getNoiDungXuLy());
		lichSuQTXLD.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), 
				xuLyDonHienTai.getDonViXuLy().getId());
		lichSuQTXLD.setThuTuThucHien(thuTu);
		
		Utils.changeQuyenTuXuLy(don, false, false, false);
		donService.save(don, congChucId);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				xuLyDonHienTai.getDonViXuLy().getDonVi());
		return xuLyDonHienTai;
	}

	public XuLyDon chuyenVienDeXuatThuLy(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, VaiTroEnum vaiTroNhanGQD) {
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonHienTai.setTrangThaiTTXM(xuLyDon.getTrangThaiTTXM());
		xuLyDonHienTai.setHanGiaiQuyet(xuLyDon.getHanGiaiQuyet());

		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		
		//tao ma don
		if (don.getMa() == null || don.getMa().isEmpty()) {
			don.setMa(donService.getMaDon(donRepo, don.getId()));
			don.setMaHoSo(don.getMa());
		}
		
		HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy();
		
		don.setHuongXuLyXLD(huongXuLyXLD);
		don.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		don.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.DE_XUAT_THU_LY);
		don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DANG_GIAI_QUYET);
		don.setTrangThaiDon(TrangThaiDonEnum.DANG_GIAI_QUYET);
		if (xuLyDon.getCanBoXuLyChiDinh() != null) { 
			don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
			xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		} else { 
			don.setCanBoXuLyChiDinh(null);
		}
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCanBoCoTheThuHoi(null);
		don.setCoQuanDangGiaiQuyet(xuLyDonHienTai.getDonViXuLy());
		State beginState = repoState.findOne(stateService.predicateFindByType(FlowStateEnum.BAT_DAU));
		don.setProcessType(ProcessTypeEnum.GIAI_QUYET_DON);
		don.setCurrentState(beginState);
		don.setNgayKetThucXLD(Utils.localDateTimeNow());
		don.setNgayThucHienKetQuaXuLy(Utils.localDateTimeNow());
		
//		Bo phanLoaiDon - cap nhat 16/11
//		if (xuLyDon.getPhanLoaiDon() != null) {
//			don.setPhanLoaiDon(xuLyDon.getPhanLoaiDon());
//			if (PhanLoaiDonEnum.DU_DIEU_KIEN_XU_LY.equals(xuLyDon.getPhanLoaiDon())) {
//				don.setLyDoKhongDuDieuKienThuLy(null);
//			} else {
//				don.setLyDoKhongDuDieuKienThuLy(xuLyDon.getLyDoKhongDuDieuKienThuLy());
//			}
//		}
		
		boolean coQuyTrinh = kiemTraDonViCoQuyTrinhXLD(xuLyDonHienTai.getDonViXuLy().getId());
		if (!coQuyTrinh) { 
			don.setLanhDaoDuyet(true);
		}
		
		//Quy trinh Giai Quyet Don
		ThongTinGiaiQuyetDon thongTinGiaiQuyetDon = thongTinGiaiQuyetDonRepo.findOne(thongTinGiaiQuyetDonService.predicateFindByDon(xuLyDonHienTai.getDon().getId()));
		if (thongTinGiaiQuyetDon == null) {
			thongTinGiaiQuyetDon = new ThongTinGiaiQuyetDon();
			thongTinGiaiQuyetDon.setDon(don);
			thongTinGiaiQuyetDon.setNgayHetHanGiaiQuyet(xuLyDon.getHanGiaiQuyet());
			thongTinGiaiQuyetDon.setNgayBatDauGiaiQuyet(Utils.localDateTimeNow());
			thongTinGiaiQuyetDon.setyKienXuLyDon(xuLyDon.getyKienXuLy());
			thongTinGiaiQuyetDon.setHuongThuLyGiaiQuyet(xuLyDon.getTrangThaiTTXM());
//			ThamSo thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_GIAI_QUYET_DON_MAC_DINH"));
//			Long soNgayGiaiQuyetMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 45L;
//			LocalDateTime ngayHetHanGiaiQuyet = Utils.convertNumberToLocalDateTimeGoc(Utils.localDateTimeNow(), soNgayGiaiQuyetMacDinh);
//			thongTinGiaiQuyetDon.setNgayHetHanGiaiQuyet(ngayHetHanGiaiQuyet);	
			thongTinGiaiQuyetDonService.save(thongTinGiaiQuyetDon, congChucId);
		}
		
		CoQuanQuanLy donViGiaiQuyet = coQuanQuanLyRepo.findOne(xuLyDon.getPhongBanGiaiQuyet().getId()).getDonVi();
		
		//Lich su Giai quyet don
		GiaiQuyetDon giaiQuyetDon = new GiaiQuyetDon();
		giaiQuyetDon.setThongTinGiaiQuyetDon(thongTinGiaiQuyetDon);
		giaiQuyetDon.setChucVu(vaiTroNhanGQD);
		giaiQuyetDon.setDonChuyen(false);
		giaiQuyetDon.setDonViGiaiQuyet(donViGiaiQuyet);
		giaiQuyetDon.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		giaiQuyetDon.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		giaiQuyetDon.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDon.setThuTuThucHien(1);
		
		List<Transition> listTransitionEnd = (List<Transition>) transitionRepo.findAll(transitionService.predicateFindLast(xuLyDonHienTai.getDonViXuLy().getId(), 
				ProcessTypeEnum.GIAI_QUYET_DON.toString(), repoProcess));

		if (listTransitionEnd.size() > 0) {
			for (Transition tran : listTransitionEnd) {
				if (tran.getCurrentState().getType().equals(FlowStateEnum.BAT_DAU)) {
					giaiQuyetDon.setNextForm(tran.getForm());
				}
			}
		}
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setTen(QuaTrinhXuLyEnum.CHUYEN_GIAI_QUYET.getText());
		lichSuQTXLD.setNoiDung(xuLyDonHienTai.getNoiDungXuLy());
		lichSuQTXLD.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), 
				xuLyDonHienTai.getDonViXuLy().getId());
		lichSuQTXLD.setThuTuThucHien(thuTu);
		
		giaiQuyetDonService.save(giaiQuyetDon, congChucId);
		don.setGiaiQuyetDonCuoiCungId(giaiQuyetDon.getId());
		Utils.changeQuyenTuXuLy(don, false, false, false);
		donService.save(don, congChucId);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				xuLyDonHienTai.getDonViXuLy().getDonVi());
		return xuLyDonHienTai;
	}
	
	public XuLyDon chuyenVienDeXuatThuLyChoCanBoGiaiQuyet(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, VaiTroEnum vaiTroNhanGQD) {
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setCongChuc(xuLyDonHienTai.getCanBoXuLyChiDinh() != null ? xuLyDonHienTai.getCanBoXuLyChiDinh() : congChuc);
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonHienTai.setTrangThaiTTXM(xuLyDon.getTrangThaiTTXM());
		xuLyDonHienTai.setHanGiaiQuyet(xuLyDon.getHanGiaiQuyet());

		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		
		//tao ma don
		if (don.getMa() == null || don.getMa().isEmpty()) {
			don.setMa(donService.getMaDon(donRepo, don.getId()));
			don.setMaHoSo(don.getMa());
		}
		
		HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy();
		
		don.setHuongXuLyXLD(huongXuLyXLD);
		don.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		don.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.DE_XUAT_THU_LY);
		don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DANG_GIAI_QUYET);
		don.setTrangThaiDon(TrangThaiDonEnum.DANG_GIAI_QUYET);
		if (xuLyDon.getCanBoXuLyChiDinh() != null) { 
			don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
			xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		}
		don.setCanBoXuLyPhanHeXLD(congChuc);
		don.setCoQuanDangGiaiQuyet(xuLyDonHienTai.getDonViXuLy());
		State giaoViecState = repoState.findOne(stateService.predicateFindByType(FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO));
		don.setProcessType(ProcessTypeEnum.GIAI_QUYET_DON);
		don.setCurrentState(giaoViecState);
		don.setNgayKetThucXLD(Utils.localDateTimeNow());
		don.setCanBoCoTheThuHoi(null);
		Utils.changeQuyenTuXuLy(don, true, false, false);
		
		donService.save(don, congChucId);
		
		//Quy trinh Giai Quyet Don
		ThongTinGiaiQuyetDon thongTinGiaiQuyetDon = thongTinGiaiQuyetDonRepo.findOne(thongTinGiaiQuyetDonService.predicateFindByDon(xuLyDonHienTai.getDon().getId()));
		if (thongTinGiaiQuyetDon == null) {
			thongTinGiaiQuyetDon = new ThongTinGiaiQuyetDon();
			thongTinGiaiQuyetDon.setDon(don);
			thongTinGiaiQuyetDon.setNgayBatDauGiaiQuyet(Utils.localDateTimeNow());
			thongTinGiaiQuyetDon.setNgayHetHanGiaiQuyet(xuLyDon.getHanGiaiQuyet());
			thongTinGiaiQuyetDon.setyKienXuLyDon(xuLyDon.getyKienXuLy());
			thongTinGiaiQuyetDon.setHuongThuLyGiaiQuyet(xuLyDon.getTrangThaiTTXM());
//			ThamSo thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_GIAI_QUYET_DON_MAC_DINH"));
//			Long soNgayGiaiQuyetMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 45L;
//			LocalDateTime ngayHetHanGiaiQuyet = Utils.convertNumberToLocalDateTimeGoc(Utils.localDateTimeNow(), soNgayGiaiQuyetMacDinh);
//			thongTinGiaiQuyetDon.setNgayHetHanGiaiQuyet(ngayHetHanGiaiQuyet);	
			thongTinGiaiQuyetDonService.save(thongTinGiaiQuyetDon, congChucId);
		}
		
		CoQuanQuanLy donViGiaiQuyet = coQuanQuanLyRepo.findOne(xuLyDon.getPhongBanGiaiQuyet().getId()).getDonVi();
		
		//Lich su Giai quyet don
		GiaiQuyetDon giaiQuyetDon = new GiaiQuyetDon();
		GiaiQuyetDon giaiQuyetDonTruongPhong = new GiaiQuyetDon();
//		giaiQuyetDon.setThongTinGiaiQuyetDon(thongTinGiaiQuyetDon);
//		giaiQuyetDon.setChucVu(vaiTroNhanGQD);
//		giaiQuyetDon.setDonChuyen(true);
//		giaiQuyetDon.setDonViGiaiQuyet(donViGiaiQuyet);
//		giaiQuyetDon.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
//		giaiQuyetDon.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
//		giaiQuyetDon.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
//		giaiQuyetDon.setThuTuThucHien(1);
		
		giaiQuyetDon = chuyenCanBoGiaiQuyet(xuLyDon, xuLyDonHienTai, giaiQuyetDon, giaiQuyetDonTruongPhong, donViGiaiQuyet, thongTinGiaiQuyetDon, congChucId);
		don.setGiaiQuyetDonCuoiCungId(giaiQuyetDon.getId());
		don.setNgayThucHienKetQuaXuLy(Utils.localDateTimeNow());
		donService.save(don, congChucId);
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChuc);
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setTen(QuaTrinhXuLyEnum.CHUYEN_GIAI_QUYET.getText());
		lichSuQTXLD.setNoiDung(xuLyDonHienTai.getNoiDungXuLy());
		lichSuQTXLD.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), 
				xuLyDonHienTai.getDonViXuLy().getId());
		lichSuQTXLD.setThuTuThucHien(thuTu);
		
		giaiQuyetDonService.save(giaiQuyetDon, congChucId);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				xuLyDonHienTai.getDonViXuLy().getDonVi());
		return xuLyDonHienTai;
	}
	
	public XuLyDon chuyenVienDeXuatThuLyVaGiaoTTXM(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, VaiTroEnum chucVuNhanTTXM, VaiTroEnum chucVuNhanTTXM2, boolean khongQuyTrinh) {
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setCongChuc(xuLyDonHienTai.getCanBoXuLyChiDinh());
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonHienTai.setTrangThaiTTXM(xuLyDon.getTrangThaiTTXM());
		xuLyDonHienTai.setHanGiaiQuyet(xuLyDon.getHanGiaiQuyet());
		xuLyDonHienTai.setThoiHanBaoCaoKetQuaTTXM(xuLyDon.getThoiHanBaoCaoKetQuaTTXM());
		xuLyDonHienTai.setDonViThamTraXacMinh(xuLyDon.getDonViThamTraXacMinh());

		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		
		//tao ma don
		if (don.getMa() == null || don.getMa().isEmpty()) {
			don.setMa(donService.getMaDon(donRepo, don.getId()));
			don.setMaHoSo(don.getMa());
		}
		
		HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy();
		don.setHuongXuLyXLD(huongXuLyXLD);
		don.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		don.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.DANG_TTXM);
		don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DANG_GIAI_QUYET);
		don.setTrangThaiDon(TrangThaiDonEnum.DANG_GIAI_QUYET);
		don.setTrangThaiTTXM(TrangThaiDonEnum.DANG_GIAI_QUYET);
		don.setKetQuaTTXM(KetQuaTrangThaiDonEnum.DANG_TTXM);
		don.setDonViThamTraXacMinh(xuLyDon.getDonViThamTraXacMinh());
		don.setCanBoCoTheThuHoi(null);
//		if (xuLyDon.getCanBoXuLyChiDinh() != null) { 
//			don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
//			xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
//		}
		xuLyDonHienTai.setCanBoXuLyChiDinh(don.getCanBoXuLyChiDinh() != null ? don.getCanBoXuLyChiDinh() : xuLyDon.getCanBoXuLyChiDinh());
		don.setCanBoXuLyPhanHeXLD(congChuc);
		don.setCoQuanDangGiaiQuyet(xuLyDonHienTai.getDonViXuLy());
		State beginState = repoState.findOne(stateService.predicateFindByType(FlowStateEnum.BAT_DAU));
		don.setProcessType(ProcessTypeEnum.THAM_TRA_XAC_MINH);
		don.setCurrentState(beginState);
		don.setNgayKetThucXLD(Utils.localDateTimeNow());
		don.setNgayThucHienKetQuaXuLy(Utils.localDateTimeNow());
		Utils.changeQuyenTuXuLy(don, false, false, false);
		
//		Bo phanLoaiDon - cap nhat 16/11
//		if (xuLyDon.getPhanLoaiDon() != null) {
//			don.setPhanLoaiDon(xuLyDon.getPhanLoaiDon());
//			if (PhanLoaiDonEnum.DU_DIEU_KIEN_XU_LY.equals(xuLyDon.getPhanLoaiDon())) {
//				don.setLyDoKhongDuDieuKienThuLy(null);
//			} else {
//				don.setLyDoKhongDuDieuKienThuLy(xuLyDon.getLyDoKhongDuDieuKienThuLy());
//			}
//		}
		
		boolean coQuyTrinh = kiemTraDonViCoQuyTrinhXLD(xuLyDonHienTai.getDonViXuLy().getId());
		if (!coQuyTrinh) { 
			don.setLanhDaoDuyet(true);
		}
		
		donService.save(don, congChucId);
		
		//Quy trinh Giai Quyet Don
		ThongTinGiaiQuyetDon thongTinGiaiQuyetDon = thongTinGiaiQuyetDonRepo.findOne(thongTinGiaiQuyetDonService.predicateFindByDon(xuLyDonHienTai.getDon().getId()));
		if (thongTinGiaiQuyetDon == null) {
			thongTinGiaiQuyetDon = new ThongTinGiaiQuyetDon();
			thongTinGiaiQuyetDon.setDon(don);
			thongTinGiaiQuyetDon.setNgayBatDauGiaiQuyet(Utils.localDateTimeNow());
			thongTinGiaiQuyetDon.setNgayHetHanGiaiQuyet(xuLyDon.getHanGiaiQuyet());
			thongTinGiaiQuyetDon.setNgayBatDauTTXM(Utils.localDateTimeNow());
			thongTinGiaiQuyetDon.setNgayHetHanTTXM(xuLyDon.getThoiHanBaoCaoKetQuaTTXM());
			thongTinGiaiQuyetDon.setDonViGiaoThamTraXacMinh(congChuc.getCoQuanQuanLy().getDonVi());
			thongTinGiaiQuyetDon.setDonViThamTraXacMinh(xuLyDon.getDonViThamTraXacMinh());
			thongTinGiaiQuyetDon.setyKienCuaDonViGiaoTTXM(xuLyDon.getyKienXuLy());
			thongTinGiaiQuyetDon.setyKienXuLyDon(xuLyDon.getyKienXuLy());
			thongTinGiaiQuyetDon.setHuongThuLyGiaiQuyet(xuLyDon.getTrangThaiTTXM());
//			ThamSo thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_GIAI_QUYET_DON_MAC_DINH"));
//			Long soNgayGiaiQuyetMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 45L;
//			LocalDateTime ngayHetHanGiaiQuyet = Utils.convertNumberToLocalDateTimeGoc(Utils.localDateTimeNow(), soNgayGiaiQuyetMacDinh);
//			thongTinGiaiQuyetDon.setNgayHetHanGiaiQuyet(ngayHetHanGiaiQuyet);	
			thongTinGiaiQuyetDonService.save(thongTinGiaiQuyetDon, congChucId);
		}		
		//Lich su Giai quyet don
		GiaiQuyetDon giaiQuyetDonTruongPhong = new GiaiQuyetDon();
		giaiQuyetDonTruongPhong = chuyenTruongPhongGiaiQuyet(xuLyDon, xuLyDonHienTai, giaiQuyetDonTruongPhong, thongTinGiaiQuyetDon, 
				don.getCanBoXuLyChiDinh() != null ? don.getCanBoXuLyChiDinh() : xuLyDon.getCanBoXuLyChiDinh(), congChuc, khongQuyTrinh);
		//don.setGiaiQuyetDonCuoiCungId(giaiQuyetDonTruongPhong.getId());
		//donService.save(don, congChucId);
		
		//TTXM
		GiaiQuyetDon giaiQuyetDonTTXM = new GiaiQuyetDon();
		giaiQuyetDonTTXM.setThongTinGiaiQuyetDon(thongTinGiaiQuyetDon);
		giaiQuyetDonTTXM.setChucVu(chucVuNhanTTXM);
		giaiQuyetDonTTXM.setChucVu2(chucVuNhanTTXM2);
		giaiQuyetDonTTXM.setDonViChuyenDon(congChuc.getCoQuanQuanLy().getDonVi());
		giaiQuyetDonTTXM.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTTXM.setDonViGiaiQuyet(xuLyDon.getDonViThamTraXacMinh());
		giaiQuyetDonTTXM.setThuTuThucHien(1);
		giaiQuyetDonTTXM.setLaTTXM(true);
		//giaiQuyetDonTTXM.setDonChuyen(true);
		giaiQuyetDonService.save(giaiQuyetDonTTXM, congChucId);
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		LichSuQuaTrinhXuLy lichSuQTXLDTaiDonViTTXM = new LichSuQuaTrinhXuLy();
		//State state = stateRepo.findOne(giaiQuyetDonHienTai.getNextState().getId());
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setTen(QuaTrinhXuLyEnum.CHUYEN_DON_VI_TTXM.getText());
		lichSuQTXLD.setNoiDung(xuLyDonHienTai.getNoiDungXuLy());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), xuLyDonHienTai.getDonViXuLy().getId());
		lichSuQTXLD.setThuTuThucHien(thuTu);
		
		// set them 1 dong lich su qua trinh cho don vi TTXM
		BeanUtils.copyProperties(lichSuQTXLD, lichSuQTXLDTaiDonViTTXM);
		lichSuQTXLDTaiDonViTTXM.setId(null);
		lichSuQTXLDTaiDonViTTXM.setDonViXuLy(thongTinGiaiQuyetDon.getDonViThamTraXacMinh());
		lichSuQTXLDTaiDonViTTXM.setThuTuThucHien(0);
		
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);		
		lichSuQuaTrinhXuLyService.save(lichSuQTXLDTaiDonViTTXM, congChucId);
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				xuLyDonHienTai.getDonViXuLy().getDonVi());
		return xuLyDonHienTai;
	}
	
	public GiaiQuyetDon chuyenTruongPhongGiaiQuyet(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai,
			GiaiQuyetDon giaiQuyetDonTruongPhong, ThongTinGiaiQuyetDon thongTinGiaiQuyetDon, CongChuc congChuc, CongChuc congChucXuLy, boolean khongQuyTrinh) {		
		congChuc = congChuc != null ? congChuc : congChucXuLy;
		// set giao viec cho truong phong da xu ly 
		giaiQuyetDonTruongPhong.setChucVu(VaiTroEnum.TRUONG_PHONG);
		giaiQuyetDonTruongPhong.setThongTinGiaiQuyetDon(thongTinGiaiQuyetDon);
		giaiQuyetDonTruongPhong.setDonViGiaiQuyet(congChuc.getCoQuanQuanLy().getDonVi());
		giaiQuyetDonTruongPhong.setPhongBanGiaiQuyet(congChuc.getCoQuanQuanLy());
		giaiQuyetDonTruongPhong.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);		
		giaiQuyetDonTruongPhong.setThuTuThucHien(1);
		if (khongQuyTrinh) {
			giaiQuyetDonTruongPhong.setCanBoXuLyChiDinh(congChuc);
		}
		return giaiQuyetDonService.save(giaiQuyetDonTruongPhong, congChucXuLy.getId());
	}
	
	public GiaiQuyetDon chuyenCanBoGiaiQuyet(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, GiaiQuyetDon giaiQuyetDon,
			GiaiQuyetDon giaiQuyetDonTruongPhong, CoQuanQuanLy donViGiaiQuyet,
			ThongTinGiaiQuyetDon thongTinGiaiQuyetDon, Long congChucId) {
		// set giao viec cho truong phong da xu ly 
		giaiQuyetDonTruongPhong.setChucVu(VaiTroEnum.TRUONG_PHONG);
		giaiQuyetDonTruongPhong.setThongTinGiaiQuyetDon(thongTinGiaiQuyetDon);
		//giaiQuyetDonTruongPhong.setDonChuyen(true);
		giaiQuyetDonTruongPhong.setDonViGiaiQuyet(donViGiaiQuyet);
		giaiQuyetDonTruongPhong.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		giaiQuyetDonTruongPhong.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		giaiQuyetDon.setThuTuThucHien(1);
		
		giaiQuyetDon.setChucVu(VaiTroEnum.CHUYEN_VIEN);
		giaiQuyetDon.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		giaiQuyetDon.setThuTuThucHien(2);
		List<Transition> listTransitionEnd = (List<Transition>) transitionRepo.findAll(transitionService.predicateFindLast(xuLyDonHienTai.getDonViXuLy().getId(), 
				ProcessTypeEnum.GIAI_QUYET_DON.toString(), repoProcess));

		if (listTransitionEnd.size() > 0) {
			for (Transition tran : listTransitionEnd) {
				if (tran.getCurrentState().getType().equals(FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO)) {
					giaiQuyetDon.setNextForm(tran.getForm());
				}
			}
		}
		
		giaiQuyetDon.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		giaiQuyetDon.setThongTinGiaiQuyetDon(thongTinGiaiQuyetDon);
		giaiQuyetDon.setDonViGiaiQuyet(donViGiaiQuyet);
		//giaiQuyetDon.setDonChuyen(true);
		giaiQuyetDon.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		
		giaiQuyetDon = giaiQuyetDonService.save(giaiQuyetDon, congChucId);
		giaiQuyetDonService.save(giaiQuyetDonTruongPhong, congChucId);
		
		return giaiQuyetDon;
	}

	public XuLyDon chuyenVienChuyenChoVanThuDeXuatThuLy(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());

		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		disableXuLyDonCu(VaiTroEnum.VAN_THU, donId, congChucId, xuLyDonHienTai.getPhongBanXuLy().getId(), xuLyDonHienTai.getDonViXuLy().getId());
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonTiepTheo.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		xuLyDonTiepTheo.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		if (xuLyDonHienTai.isDonChuyen()) {
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		// set don
		Don don = donRepo.findOne(donId);
		// set thoi han xu ly cho don
		don.setThoiHanXuLyXLD(xuLyDonHienTai.getThoiHanXuLy());
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setCanBoCoTheThuHoi(null);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));

		don.setCurrentState(xuLyDonHienTai.getNextState());
		donService.save(don, congChucId);
		xuLyDonService.save(xuLyDonHienTai, congChucId);

		return xuLyDonTiepTheo;
	}

	public XuLyDon chuyenVienChuyenDonChoVanThu(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();

		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		disableXuLyDonCu(VaiTroEnum.VAN_THU, donId, congChucId, xuLyDonHienTai.getPhongBanXuLy().getId(), xuLyDonHienTai.getDonViXuLy().getId());
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonTiepTheo.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
		xuLyDonTiepTheo.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		if (xuLyDonHienTai.isDonChuyen()) {
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		// set don
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setCurrentState(xuLyDonHienTai.getNextState());
		don.setCanBoCoTheThuHoi(null);
		
		donService.save(don, congChucId);
		xuLyDonService.save(xuLyDonHienTai, congChucId);
		return xuLyDonTiepTheo;
	}

	public XuLyDon chuyenVienChuyenDon(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, List<Process> listProcess) {
		// set thoi han xu ly cho don
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		HuongXuLyXLDEnum huongXuLyXLD = xuLyDonHienTai.getHuongXuLy();
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setCongChuc(xuLyDonHienTai.getCanBoXuLyChiDinh() != null ? xuLyDonHienTai.getCanBoXuLyChiDinh() : congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
		
		Don donOld = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		donOld.setHuongXuLyXLD(huongXuLyXLD);
		donOld.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getCoQuanTiepNhan());
		//xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		
		xuLyDonTiepTheo.setChucVu(listProcess.size() == 1 || listProcess.size() == 2 ? listProcess.get(0).getVaiTro().getLoaiVaiTro() : null);
		xuLyDonTiepTheo.setChucVu2(listProcess.size() == 2 ? listProcess.get(1).getVaiTro().getLoaiVaiTro() : null);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDonChuyen(true);
		xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getPhongBanXuLy());
		//xuLyDonTiepTheo.setCanBoChuyenDon(xuLyDonHienTai.getCanBoXuLyChiDinh());
		xuLyDonTiepTheo.setCanBoChuyenDon(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setCoQuanTiepNhan(xuLyDonHienTai.getCoQuanTiepNhan());
		//xuLyDonTiepTheo.setNoiDungXuLy(xuLyDonHienTai.getyKienXuLy());
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(xuLyDonTiepTheo.getPhongBanXuLy().getId());
		xuLyDonTiepTheo.setDonViXuLy(donVi.getDonVi());
		disableXuLyDonCu(VaiTroEnum.VAN_THU, donOld.getId(), congChucId, xuLyDonTiepTheo.getPhongBanXuLy().getId(), donVi.getDonVi().getId());
		
		// set don
		donOld.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		donOld.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		donOld.setCanBoXuLyChiDinh(null);
		donOld.setCanBoCoTheThuHoi(null);
		donOld.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DA_XU_LY);
		donOld.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		donOld.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.CHUYEN_DON);
		State beginState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.BAT_DAU));	
		donOld.setCurrentState(beginState);
		donOld.setCoQuanDangGiaiQuyet(donVi.getDonVi());
		donOld.setHoanThanhDon(true);
		donOld.setNgayKetThucXLD(Utils.localDateTimeNow());
		donOld.setNgayNhanTraDonChuyen(Utils.localDateTimeNow());
		
		boolean coQuyTrinh = kiemTraDonViCoQuyTrinhXLD(xuLyDonHienTai.getDonViXuLy().getId());
		if (!coQuyTrinh) { 
			donOld.setLanhDaoDuyet(true);
		}
		
		//tao don moi cua don vi chuyen den
		Don donMoi = new Don();
		BeanUtils.copyProperties(donOld, donMoi);
		donMoi.setId(null);
		donMoi.setDonGocId(donOld.getId());
		donMoi.setDonChuyen(true);
		donMoi.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		donMoi.setNgayBatDauXLD(Utils.localDateTimeNow());
		donMoi.setNgayTiepNhan(Utils.localDateTimeNow());
		donMoi.setNgayKetThucXLD(null);
		ThamSo thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_XU_LY_DON_MAC_DINH"));
		Long soNgayXuLyMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 10L;
		donMoi.setThoiHanXuLyXLD(Utils.convertNumberToLocalDateTimeTinhTheoNgayLamViec(donMoi.getNgayBatDauXLD(), soNgayXuLyMacDinh));
		donMoi.setDonCongDans(new ArrayList<Don_CongDan>());
		donMoi.setTaiLieuBangChungs(new ArrayList<TaiLieuBangChung>());
		donMoi.setDoanDiCungs(new ArrayList<DoanDiCung>());
		donMoi.setXuLyDons(new ArrayList<XuLyDon>());
//		donMoi.setTiepCongDans(new ArrayList<SoTiepCongDan>());
		donMoi.setTaiLieuVanThus(new ArrayList<TaiLieuVanThu>());
		donMoi.setTepDinhKems(new ArrayList<TepDinhKem>());
		donMoi.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DANG_XU_LY);
		donMoi.setDonViXuLyGiaiQuyet(donVi.getDonVi());
		donMoi.setDonViTiepDan(donVi.getDonVi());
		donMoi.setKetQuaXLDGiaiQuyet(null);
		donMoi.setDonViXuLyDonChuyen(donVi.getDonVi());
		donMoi.setCanBoXuLyChiDinh(null);
		donMoi.setXuLyDonCuoiCungId(null);
		donMoi.setHoanThanhDon(false);
		donMoi.setCanBoXuLyPhanHeXLD(null);
		donMoi.setCanBoXuLy(null);
		donMoi.setNgayNhanTraDonChuyen(Utils.localDateTimeNow());
		xuLyDonTiepTheo.setDon(donMoi);
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		//State state = repoState.findOne(xuLyDonHienTai.getNextState().getId());
		lichSuQTXLD.setDon(donOld);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setTen(huongXuLyXLD.getText());
		lichSuQTXLD.setNoiDung("Đơn vị tiếp nhận: " + xuLyDonHienTai.getCoQuanTiepNhan().getTen() + "\n\n" + xuLyDonHienTai.getNoiDungXuLy());
		lichSuQTXLD.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, donOld.getId(), 
				xuLyDonHienTai.getDonViXuLy().getId());
		lichSuQTXLD.setThuTuThucHien(thuTu);

		//tao lich su don chuyen di
		LichSuQuaTrinhXuLy lichSuQTXLDChuyenDon = new LichSuQuaTrinhXuLy();
		lichSuQTXLDChuyenDon.setDon(donMoi);
		lichSuQTXLDChuyenDon.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLDChuyenDon.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLDChuyenDon.setTen(QuaTrinhXuLyEnum.CHUYEN_DON.getText());
		lichSuQTXLDChuyenDon.setNoiDung(xuLyDonHienTai.getNoiDungXuLy());
		lichSuQTXLDChuyenDon.setDonViXuLy(donVi.getDonVi());
		int thuTuChuyenDon = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, donOld.getId(), 
				donVi.getDonVi().getId());
		lichSuQTXLDChuyenDon.setThuTuThucHien(thuTuChuyenDon);
		
		disableDonChuyenCu(donOld.getId(), congChucId, donVi.getDonVi().getId());
		Utils.changeQuyenTuXuLy(donOld, false, false, false);
		Utils.changeQuyenTuXuLy(donMoi, false, false, false);
		donService.save(donOld, congChucId);
		donService.save(donMoi, congChucId);
		xuLyDonService.save(xuLyDonHienTai, congChucId);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLDChuyenDon, congChucId);
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				xuLyDonHienTai.getDonViXuLy().getDonVi());
		return xuLyDonTiepTheo;
	}

	public XuLyDon chuyenVienXuLyKhongDuDieuKienThuLy(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		XuLyDon xuLyDonTiepTheo = new XuLyDon();

		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		disableXuLyDonCu(VaiTroEnum.VAN_THU, donId, congChucId, xuLyDonHienTai.getPhongBanXuLy().getId(), xuLyDonHienTai.getDonViXuLy().getId());
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		//xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonTiepTheo.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		if (xuLyDonHienTai.isDonChuyen()) {
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		// set don
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setHoanThanhDon(true);
		don.setCanBoCoTheThuHoi(null);
		don.setCurrentState(xuLyDonHienTai.getNextState());
		donService.save(don, congChucId);
		xuLyDonService.save(xuLyDonHienTai, congChucId);

		return xuLyDonTiepTheo;
	}

	public XuLyDon chuyenVienTraLaiDonKhongDungThamQuyenChoVanThu(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());

		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonTiepTheo.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		//xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setDonChuyen(true);
		xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		disableXuLyDonCu(VaiTroEnum.VAN_THU, donId, congChucId, xuLyDonTiepTheo.getPhongBanXuLy().getId(), xuLyDonTiepTheo.getDonViXuLy().getId());

		// set don
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setCurrentState(xuLyDonHienTai.getNextState());
		don.setCanBoCoTheThuHoi(null);
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		State state = repoState.findOne(xuLyDonHienTai.getNextState().getId());
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setTen(state.getTenVietTat());
		lichSuQTXLD.setNoiDung(xuLyDonHienTai.getNoiDungXuLy());
		lichSuQTXLD.setDonViXuLy(xuLyDonTiepTheo.getDonViXuLy());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), 
				xuLyDonTiepTheo.getDonViXuLy().getId());
		lichSuQTXLD.setThuTuThucHien(thuTu);
		
		xuLyDonService.save(xuLyDonHienTai, congChucId);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				xuLyDonHienTai.getDonViXuLy().getDonVi());
		return xuLyDonTiepTheo;
	}

	public XuLyDon chuyenVienTraLaiDonKhongDungThamQuyen(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, Transition transitionXLD) {
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setNoiDungXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		
//		State beginState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.BAT_DAU));
		//State beginState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.LANH_DAO_GIAO_VIEC_TRUONG_PHONG));
		State endState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.KET_THUC));
		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		don.setHuongXuLyXLD(HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		don.setCurrentState(endState);
		don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DA_XU_LY);
		don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN);
		don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		//don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setCanBoXuLyChiDinh(null);
		don.setNgayKetThucXLD(Utils.localDateTimeNow());
		don.setCanBoCoTheThuHoi(null);
		don.setNgayThucHienKetQuaXuLy(Utils.localDateTimeNow());
		
//		Bo phanLoaiDon - cap nhat 16/11
//		if (xuLyDon.getPhanLoaiDon() != null) {
//			don.setPhanLoaiDon(xuLyDon.getPhanLoaiDon());
//			if (PhanLoaiDonEnum.DU_DIEU_KIEN_XU_LY.equals(xuLyDon.getPhanLoaiDon())) {
//				don.setLyDoKhongDuDieuKienThuLy(null);
//			} else {
//				don.setLyDoKhongDuDieuKienThuLy(xuLyDon.getLyDoKhongDuDieuKienThuLy());
//			}
//		}
		
		boolean coQuyTrinh = kiemTraDonViCoQuyTrinhXLD(xuLyDonHienTai.getDonViXuLy().getId());
		if (!coQuyTrinh) { 
			don.setLanhDaoDuyet(true);
		}
		
		Process process = null;
		List<Process> processList = new ArrayList<Process>();
		processList.addAll((List<Process>) repoProcess.findAll(processService.predicateFindAllByDonVi(xuLyDonHienTai.getCoQuanChuyenDon().getDonVi(), ProcessTypeEnum.XU_LY_DON, VaiTroEnum.TRUONG_PHONG)));
		if (processList != null && processList.size() > 0) {
			process = processList.get(0);
		}
		
		Transition transition = null;
		List<Transition> transitionXLDs = new ArrayList<Transition>();
		if (process != null) { 
			transitionXLDs.addAll((List<Transition>) transitionRepo.findAll(transitionService.predicateFindByProcess(process)));
		}
		if (transitionXLDs != null && transitionXLDs.size() > 0) { 
			transition = transitionXLDs.get(0);
		}
		
		Don donGoc = donRepo.findOne(donService.predicateFindOne(don.getDonGocId()));
		donGoc.setCurrentState(transition.getCurrentState());
		//donGoc.setHuongXuLyXLD(HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN);
		donGoc.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		//donGoc.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		donGoc.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		donGoc.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DANG_XU_LY);
		//donGoc.setCurrentState(beginState);
		donGoc.setHuongXuLyXLD(null);
		donGoc.setKetQuaXLDGiaiQuyet(null);
		donGoc.setCanBoCoTheThuHoi(null);
		
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		XuLyDon xuLyDonCuoiCungHienTai = new XuLyDon();
		Long donViXuLyId = xuLyDonHienTai.getCoQuanChuyenDon().getDonVi().getId();
		if (transitionXLD != null) {
			//xuLyDonCuoiCungHienTai = repo.findOne(donGoc.getXuLyDonCuoiCungId());
			xuLyDonCuoiCungHienTai = xuLyDonService.predFindXuLyDonCuoiCungHienTaiCuaTruongPhong(repo, xuLyDonHienTai.getDon().getDonGocId(),  
					 donViXuLyId, xuLyDonHienTai.getCoQuanChuyenDon().getId(), VaiTroEnum.TRUONG_PHONG);
			
		} else {
			//beginState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.BAT_DAU));
			//donGoc.setCurrentState(beginState);
			xuLyDonCuoiCungHienTai = xuLyDonService.predFindXuLyDonCuoiCungHienTaiDonKhongQuyTrinh(repo, xuLyDonHienTai.getDon().getDonGocId(), donViXuLyId);
		}
		 
		xuLyDonTiepTheo.setDon(donGoc);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getCoQuanChuyenDon());
		//xuLyDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
		//xuLyDonTiepTheo.setCanBoXuLyChiDinh(xuLyDonHienTai.getCanBoChuyenDon());
		xuLyDonTiepTheo.setCongChuc(xuLyDonHienTai.getCanBoChuyenDon());
		//xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		if (transitionXLD != null) { 
			xuLyDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
		} else { 
			xuLyDonTiepTheo.setChucVu(null);
		}
		
		//xuLyDonTiepTheo.setChucVu(transitionXLD != null ? transitionXLD.getProcess().getVaiTro().getLoaiVaiTro() : null);
		xuLyDonTiepTheo.setNextForm(xuLyDonCuoiCungHienTai.getNextForm());
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		//xuLyDonTiepTheo.setDonChuyen(true);
		xuLyDonTiepTheo.setDonTra(true);
		xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getPhongBanXuLy());
		CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(xuLyDonTiepTheo.getPhongBanXuLy().getId());
		xuLyDonTiepTheo.setDonViXuLy(donVi.getDonVi());
		//disableXuLyDonLanhDaoVanThuCu(VaiTroEnum.VAN_THU, donGoc.getId(), congChucId, donVi.getDonVi().getId());
		int thuTuThucHienXLD = xuLyDonService.timThuTuXuLyDonHienTai(repo, donGoc.getId(), donVi.getDonVi().getId());
		xuLyDonTiepTheo.setThuTuThucHien(thuTuThucHienXLD);
		
		if (transitionXLD != null) { 
//			disableXuLyDonChuyenVienCu(VaiTroEnum.CHUYEN_VIEN, donGoc.getId(), congChucId, xuLyDonTiepTheo.getCanBoXuLyChiDinh().getId(), 
//					xuLyDonTiepTheo.getPhongBanXuLy().getId(), donVi.getDonVi().getId());
			
			disableXuLyDonTruongPhongCu(VaiTroEnum.TRUONG_PHONG, donGoc.getId(), congChucId, xuLyDonTiepTheo.getPhongBanXuLy().getId(), donVi.getDonVi().getId());
		} else { 
			disableXuLyDonKhongQuyTrinh(donGoc.getId(), congChucId, xuLyDonTiepTheo.getPhongBanXuLy().getId(), donVi.getDonVi().getId());
		}
		
		
		xuLyDonTiepTheo = xuLyDonService.save(xuLyDonTiepTheo, congChucId);
		
		//disableXuLyDonLanhDaoVanThuCu(VaiTroEnum.VAN_THU, donGoc.getId(), congChucId, donVi.getDonVi().getId());
		//don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setCoQuanDangGiaiQuyet(donVi.getDonVi());
		donGoc.setCoQuanDangGiaiQuyet(donVi.getDonVi());
		//don.setCanBoXuLyChiDinh(xuLyDonHienTai.getCanBoXuLyChiDinh());
		don.setHoanThanhDon(true);
		don.setNgayNhanTraDonChuyen(Utils.localDateTimeNow());
		//donGoc.setCanBoXuLyChiDinh(xuLyDonTiepTheo.getCanBoXuLyChiDinh());
		//donGoc.setXuLyDonCuoiCungId(xuLyDonTiepTheo.getId());
		donGoc.setCanBoXuLyChiDinh(null);
		donGoc.setXuLyDonCuoiCungId(null);
		donGoc.setHoanThanhDon(false);
		donGoc.setNgayKetThucXLD(null);
		donGoc.setNgayNhanTraDonChuyen(Utils.localDateTimeNow());
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		//State state = repoState.findOne(xuLyDonHienTai.getNextState().getId());
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setTen(HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.getText());
		lichSuQTXLD.setNoiDung("Đơn vị tiếp nhận: " + donVi.getDonVi().getTen() + "\n\n" + xuLyDonHienTai.getNoiDungXuLy());
		lichSuQTXLD.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), 
				xuLyDonHienTai.getDonViXuLy().getId());
		lichSuQTXLD.setThuTuThucHien(thuTu);
		
		//tao lich su qua trinh xu ly tra don
		LichSuQuaTrinhXuLy lichSuQTXLDTraDon = new LichSuQuaTrinhXuLy();
		lichSuQTXLDTraDon.setDon(donGoc);
		lichSuQTXLDTraDon.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLDTraDon.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLDTraDon.setTen("Nhận trả đơn");
		lichSuQTXLDTraDon.setNoiDung(xuLyDonHienTai.getNoiDungXuLy());
		lichSuQTXLDTraDon.setDonViXuLy(donVi.getDonVi());
		int thuTuTraDon = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, donGoc.getId(), 
				donVi.getDonVi().getId());
		lichSuQTXLDTraDon.setThuTuThucHien(thuTuTraDon);
		
		Utils.changeQuyenTuXuLy(don, false, false, false);
		Utils.changeQuyenTuXuLy(donGoc, false, false, false);
		donService.save(don, congChucId);
		donService.save(donGoc, congChucId);
		xuLyDonService.save(xuLyDonHienTai, congChucId);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLDTraDon, congChucId);
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				xuLyDonHienTai.getDonViXuLy().getDonVi());
		//disableAllXuLyDonCu(don.getId(), congChucId, xuLyDonTiepTheo.getPhongBanXuLy().getId());
		return xuLyDonTiepTheo;
	}

	public XuLyDon vanThuChuyenDon(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId) {
		XuLyDon xuLyDonTiepTheo = new XuLyDon();

		xuLyDonHienTai.setNextState(xuLyDon.getNextState());

		HuongXuLyXLDEnum huongXuLyXLD = xuLyDonHienTai.getHuongXuLy();
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		don.setHuongXuLyXLD(huongXuLyXLD);
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		xuLyDonTiepTheo.setDon(don);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getCoQuanTiepNhan());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDaXoa(true);
		xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getPhongBanXuLy());
		//xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setCoQuanTiepNhan(xuLyDonHienTai.getCoQuanTiepNhan());
		xuLyDonTiepTheo.setNoiDungXuLy(xuLyDonHienTai.getyKienXuLy());
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		disableXuLyDonCu(VaiTroEnum.VAN_THU, xuLyDonHienTai.getDon().getId(), congChucId, xuLyDonTiepTheo.getPhongBanXuLy().getId(), xuLyDonTiepTheo.getDonViXuLy().getId());
		
		// set don
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		
		donService.save(don, congChucId);
		xuLyDonService.save(xuLyDonHienTai, congChucId);
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				xuLyDonHienTai.getDonViXuLy().getDonVi());
		return xuLyDonTiepTheo;
	}

	public XuLyDon vanThuTraLaiDonKhongDungThamQuyen(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId) {
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);

		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		don.setHuongXuLyXLD(HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());

		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		xuLyDonTiepTheo.setDon(don);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getCoQuanChuyenDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDaXoa(true);
		xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getPhongBanXuLy());
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		disableXuLyDonCu(VaiTroEnum.VAN_THU, xuLyDonHienTai.getDon().getId(), congChucId, xuLyDonTiepTheo.getPhongBanXuLy().getId(), xuLyDonTiepTheo.getDonViXuLy().getId());
		
		// set don
		don.setNgayKetThucXLD(Utils.localDateTimeNow());

		donService.save(don, congChucId);
		xuLyDonService.save(xuLyDonHienTai, congChucId);

		return xuLyDonTiepTheo;
	}

	public XuLyDon deXuatThuLy(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId) {
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);

		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));

		//tao ma don
		if (don.getMa() == null || don.getMa().isEmpty()) {
			don.setMa(donService.getMaDon(donRepo, don.getId()));
			don.setMaHoSo(don.getMa());
		}
		
		HuongXuLyXLDEnum huongXuLyXLD = xuLyDonHienTai.getHuongXuLy();
		don.setHuongXuLyXLD(huongXuLyXLD);
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		don.setPhongBanGiaiQuyet(xuLyDonHienTai.getPhongBanGiaiQuyet());
		don.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		don.setTrangThaiDon(TrangThaiDonEnum.DANG_GIAI_QUYET);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));

		State beginState = repoState.findOne(stateService.predicateFindByType(FlowStateEnum.BAT_DAU));
		
		// set ngay ket thuc cho don
		don.setNgayKetThucXLD(Utils.localDateTimeNow());		
		don.setProcessType(ProcessTypeEnum.GIAI_QUYET_DON);
		don.setCurrentState(beginState);
		donService.save(don, congChucId);
		
		
		//Quy trinh Giai Quyet Don
		ThongTinGiaiQuyetDon thongTinGiaiQuyetDon = thongTinGiaiQuyetDonRepo.findOne(thongTinGiaiQuyetDonService.predicateFindByDon(xuLyDonHienTai.getDon().getId()));
		if (thongTinGiaiQuyetDon == null) {
			thongTinGiaiQuyetDon = new ThongTinGiaiQuyetDon();
			thongTinGiaiQuyetDon.setDon(don);
			thongTinGiaiQuyetDonService.save(thongTinGiaiQuyetDon, congChucId);
		}
		
		//Lich su Giai quyet don
		GiaiQuyetDon giaiQuyetDon = new GiaiQuyetDon();
		giaiQuyetDon.setThongTinGiaiQuyetDon(thongTinGiaiQuyetDon);
		giaiQuyetDon.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDon.setPhongBanGiaiQuyet(xuLyDonHienTai.getPhongBanGiaiQuyet());
		giaiQuyetDon.setDonViGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet().getDonVi());
		giaiQuyetDon.setChucVu(VaiTroEnum.TRUONG_PHONG);
//		giaiQuyetDon.setDonChuyen(true);
		giaiQuyetDon.setThuTuThucHien(1);
		giaiQuyetDonService.save(giaiQuyetDon, congChucId);
		lichSuCanBoXuLyService.saveLichSuCanBoXuLy(xuLyDon.getDon(), congChucRepo.findOne(congChucId), 
				xuLyDonHienTai.getDonViXuLy().getDonVi());
		return xuLyDonHienTai;
	}
}