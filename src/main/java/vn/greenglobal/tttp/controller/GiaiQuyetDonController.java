package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.FlowStateEnum;
import vn.greenglobal.tttp.enums.HuongGiaiQuyetTCDEnum;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.KetQuaTrangThaiDonEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.QuaTrinhXuLyEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.enums.TinhTrangGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.LichSuQuaTrinhXuLy;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.model.medial.Medial_DinhChiDonGiaiQuyet;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.ThongTinGiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.LichSuQuaTrinhXuLyRepository;
import vn.greenglobal.tttp.repository.ProcessRepository;
import vn.greenglobal.tttp.repository.StateRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.ThongTinGiaiQuyetDonService;
import vn.greenglobal.tttp.service.CongChucService;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.service.GiaiQuyetDonService;
import vn.greenglobal.tttp.service.LichSuQuaTrinhXuLyService;
import vn.greenglobal.tttp.service.ProcessService;
import vn.greenglobal.tttp.service.SoTiepCongDanService;
import vn.greenglobal.tttp.service.StateService;
import vn.greenglobal.tttp.service.TransitionService;
import vn.greenglobal.tttp.service.XuLyDonService;
import vn.greenglobal.tttp.util.Utils;
import vn.greenglobal.tttp.util.WordUtil;

@RestController
@RepositoryRestController
@Api(value = "giaiQuyetDons", description = "Giải quyết đơn")
public class GiaiQuyetDonController extends TttpController<GiaiQuyetDon> {
	
	@Autowired
	private XuLyDonRepository xuLyDonRepository;
	
	@Autowired
	private GiaiQuyetDonRepository repo;
	
	@Autowired
	private StateRepository stateRepo;
	
	@Autowired
	private DonRepository donRepo;
	
	@Autowired
	private DonService donService;
	
	@Autowired
	private StateService stateService;
	
	@Autowired
	private ThongTinGiaiQuyetDonService thongTinGiaiQuyetDonService;
	
	@Autowired
	private ThongTinGiaiQuyetDonRepository thongTinGiaiQuyetDonRepo;
	
	@Autowired
	private CongChucRepository congChucRepo;
	
	@Autowired
	private CongChucService congChucService;
	
	@Autowired
	private ProcessRepository processRepo;
	
	@Autowired
	private ProcessService processService;
	
	@Autowired
	private TransitionRepository transitionRepo;
	
	@Autowired
	private TransitionService transitionService;

	@Autowired
	private GiaiQuyetDonService giaiQuyetDonService;
	
	@Autowired
	private LichSuQuaTrinhXuLyService lichSuQuaTrinhXuLyService;
	
	@Autowired
	private SoTiepCongDanService soTiepCongDanService;
	
	@Autowired
	private LichSuQuaTrinhXuLyRepository lichSuQuaTrinhXuLyRepo;
	
	@Autowired
	private XuLyDonService xuLyDonService;

	public GiaiQuyetDonController(BaseRepository<GiaiQuyetDon, Long> repo) {
		super(repo);
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(method = RequestMethod.POST, value = "/giaiQuyetDons")
	@ApiOperation(value = "Thêm mới Lịch sử  giải quyết đơn", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Thêm mới Lịch sử  giải quyết đơn thành công", response = GiaiQuyetDon.class),
			@ApiResponse(code = 201, message = "Thêm mới Lịch sử  giải quyết đơn thành công", response = GiaiQuyetDon.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody GiaiQuyetDon giaiQuyetDon, PersistentEntityResourceAssembler eass) {
		
		try {
			NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_SUA);
			CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
			if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
					&& commonProfile.containsAttribute("coQuanQuanLyId")) {
				if (giaiQuyetDon.getThongTinGiaiQuyetDon() == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.THONGTINGIAIQUYETDON_REQUIRED.name(), ApiErrorEnum.THONGTINGIAIQUYETDON_REQUIRED.getText(), ApiErrorEnum.THONGTINGIAIQUYETDON_REQUIRED.getText());
				}
				if (giaiQuyetDon.getNextState() == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.NEXT_STATE_REQUIRED.name(), ApiErrorEnum.NEXT_STATE_REQUIRED.getText(), ApiErrorEnum.NEXT_STATE_REQUIRED.getText());
				}
				State nextState = stateRepo.findOne(stateService.predicateFindOne(giaiQuyetDon.getNextState().getId()));
				Long thongTinGiaiQuyetDonId = giaiQuyetDon.getThongTinGiaiQuyetDon().getId();
				ThongTinGiaiQuyetDon thongTinGiaiQuyetDon = thongTinGiaiQuyetDonRepo.findOne(thongTinGiaiQuyetDonService.predicateFindOne(thongTinGiaiQuyetDonId));
				
				Don don = thongTinGiaiQuyetDon.getDon();
				
				if (don == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DON_REQUIRED.name(), ApiErrorEnum.DON_REQUIRED.getText(), ApiErrorEnum.DON_REQUIRED.getText());
				}
				
				if (don.getProcessType() == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_TYPE_REQUIRED.name(), ApiErrorEnum.PROCESS_TYPE_REQUIRED.getText(), ApiErrorEnum.PROCESS_TYPE_REQUIRED.getText());
				}
				
				String vaiTroNguoiDungHienTai = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro").toString();
				Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
				Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());

				CongChuc congChuc = congChucRepo.findOne(congChucService.predicateFindOne(congChucId));

				boolean isOwner = don.getNguoiTao().getId() == null || don.getNguoiTao().getId().equals(0L) ? true
						: congChucId.longValue() == don.getNguoiTao().getId().longValue() ? true : false;

				CoQuanQuanLy donVi = congChuc.getCoQuanQuanLy().getDonVi();
				Process process = processRepo.findOne(processService.predicateFindAll(vaiTroNguoiDungHienTai, donVi, isOwner, don.getProcessType()));

				if (process == null && isOwner) {
					process = processRepo.findOne(processService.predicateFindAll(vaiTroNguoiDungHienTai, donVi, false, don.getProcessType()));
				}
				if (process == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_NOT_FOUND.name(), ApiErrorEnum.PROCESS_NOT_FOUND.getText(), ApiErrorEnum.PROCESS_NOT_FOUND.getText());
				}
				Transition transition = transitionRepo.findOne(transitionService.predicatePrivileged(don.getCurrentState(), nextState, process));

				if (transition == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_FORBIDDEN.name(), ApiErrorEnum.TRANSITION_FORBIDDEN.getText(), ApiErrorEnum.TRANSITION_FORBIDDEN.getText());
				}
				if (ProcessTypeEnum.GIAI_QUYET_DON.equals(don.getProcessType())) {
					GiaiQuyetDon giaiQuyetDonHienTai = giaiQuyetDonService.predFindCurrent(repo, thongTinGiaiQuyetDonId, false);
					if (giaiQuyetDonHienTai != null) {
						FlowStateEnum currentState = don.getCurrentState() != null ? don.getCurrentState().getType() : null;
						FlowStateEnum nextStateType = nextState.getType();
						giaiQuyetDonHienTai.setNextState(nextState);
						giaiQuyetDonHienTai.setNextForm(transition.getForm());
						// Thong tin xu ly don
						String note = vaiTroNguoiDungHienTai + " " + nextState.getTenVietTat() + " ";
						Long coQuanQuanLyId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
						
						thongTinGiaiQuyetDon.setyKienGiaiQuyet("");
						thongTinGiaiQuyetDon.setCanBoXuLyChiDinh(null);
						thongTinGiaiQuyetDon.setPhongBanGiaiQuyet(null);
						thongTinGiaiQuyetDon.setNextState(null);
						thongTinGiaiQuyetDonService.save(thongTinGiaiQuyetDon, congChucId);
						
						if (FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO.equals(nextStateType)) {
							if (giaiQuyetDon.getCanBoXuLyChiDinh() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.name(),
										ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText(), ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText());
							}
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = truongPhongGiaoViecChuyenVien(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, donViId);
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.CAN_BO_DE_XUAT_GIAO_VIEC_LAI.equals(nextStateType)) {
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = chuyenVienDeXuatGiaoViecLai(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note);
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.CAN_BO_CHUYEN_VAN_THU_GIAO_TTXM.equals(nextStateType)) {
							if (giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.name(),
										ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.getText(), ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.getText());
							}
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = chuyenVienChuyenVanThuGiaoTTXM(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, donViId);
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.KET_THUC.equals(nextStateType)) {
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = chuyenVienGiaiQuyet(giaiQuyetDonHienTai, giaiQuyetDon, coQuanQuanLyId, congChucId, note);
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.VAN_THU_CHUYEN_DON_VI_TTXM.equals(nextStateType)) {
							//Tim kiem vai tro dau tien o quy trinh ThamTraXacMinh							
							Predicate predicate = processService.predicateFindAllByDonVi(giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh(), ProcessTypeEnum.THAM_TRA_XAC_MINH);
							List<Process> listProcess = (List<Process>) processRepo.findAll(predicate);
							if (listProcess.size() < 1) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.name(),
										ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.getText(), ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.getText());
							}						
							Transition transitionTTXM = null;
							List<Transition> listTransitionHaveBegin = new ArrayList<Transition>();
							for (Process processFromList : listProcess) {
								transitionTTXM = transitionRepo.findOne(transitionService.predicateFindFromCurrent(FlowStateEnum.BAT_DAU, processFromList));
								if (transitionTTXM != null) {
									listTransitionHaveBegin.add(transitionTTXM);
								}
							}
							if (transitionTTXM == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_TTXM_INVALID.name(),
										ApiErrorEnum.TRANSITION_TTXM_INVALID.getText(), ApiErrorEnum.TRANSITION_TTXM_INVALID.getText());
							}						
							giaiQuyetDonHienTai = vanThuChuyenVanThuDonViTTXM(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, donViId, 
									listTransitionHaveBegin.size() == 1 ? transitionTTXM.getProcess().getVaiTro().getLoaiVaiTro() : null);
							return giaiQuyetDonService.doSave(giaiQuyetDonHienTai, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.CAN_BO_CHUYEN_DON_VI_TTXM.equals(nextStateType)) {
							if (giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.name(),
										ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.getText(), ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.getText());
							}
							Predicate predicate = processService.predicateFindAllByDonVi(giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh(), ProcessTypeEnum.THAM_TRA_XAC_MINH);
							List<Process> listProcess = (List<Process>) processRepo.findAll(predicate);
							if (listProcess.size() < 1) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.name(),
										ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.getText(), ApiErrorEnum.PROCESS_TTXM_NOT_FOUND.getText());
							}						
							Transition transitionTTXM = null;
							for (Process processFromList : listProcess) {
								transitionTTXM = transitionRepo.findOne(transitionService.predicateFindFromCurrent(FlowStateEnum.BAT_DAU, processFromList));
								if (transitionTTXM != null) {
									break;
								}
							}
							if (transitionTTXM == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_TTXM_INVALID.name(),
										ApiErrorEnum.TRANSITION_TTXM_INVALID.getText(), ApiErrorEnum.TRANSITION_TTXM_INVALID.getText());
							}						
							giaiQuyetDonHienTai = vanThuChuyenVanThuDonViTTXM(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, donViId, transitionTTXM.getProcess().getVaiTro().getLoaiVaiTro());
							return giaiQuyetDonService.doSave(giaiQuyetDonHienTai, congChucId, eass, HttpStatus.CREATED);
						}
					} 
				} else if (ProcessTypeEnum.THAM_TRA_XAC_MINH.equals(don.getProcessType())){
					GiaiQuyetDon giaiQuyetDonHienTai = giaiQuyetDonService.predFindCurrent(repo, thongTinGiaiQuyetDonId, true);
					if (giaiQuyetDonHienTai != null) {
						FlowStateEnum currentState = don.getCurrentState() != null ? don.getCurrentState().getType() : null;
						FlowStateEnum nextStateType = nextState.getType();
						giaiQuyetDonHienTai.setNextState(nextState);
						giaiQuyetDonHienTai.setNextForm(transition.getForm());
						String note = vaiTroNguoiDungHienTai + " " + nextState.getTenVietTat() + " ";
						Long coQuanQuanLyId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
						if (FlowStateEnum.TRINH_LANH_DAO.equals(nextStateType)) {
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = vanThuDonViTTXMTrinhLanhDao(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true, donViId);
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.LANH_DAO_GIAO_VIEC_TRUONG_PHONG.equals(nextStateType)) {
							if (giaiQuyetDon.getPhongBanGiaiQuyet() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.name(),
										ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText(), ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText());
							}
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = lanhDaoDonViTTXMGiaoViec(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true, donViId);
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.LANH_DAO_GIAO_VIEC_CAN_BO.equals(nextStateType)) {
							if (giaiQuyetDon.getPhongBanGiaiQuyet() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.name(),
										ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText(), ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText());
							}
							if (giaiQuyetDon.getCanBoXuLyChiDinh() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.name(),
										ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText(), ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText());
							}
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = lanhDaoDonViTTXMGiaoViec(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true, donViId);
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO.equals(nextStateType)) {
							if (giaiQuyetDon.getCanBoXuLyChiDinh() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.name(),
										ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText(), ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText());
							}
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = truongPhongDonViTTXMGiaoViec(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true, donViId);
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.CAN_BO_DE_XUAT_GIAO_VIEC_LAI.equals(nextStateType)) {
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = canBoDonViTTXMDeXuatGiaoViecLai(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true, donViId);
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.TRUONG_PHONG_DE_XUAT_GIAO_VIEC_LAI.equals(nextStateType)) {
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = truongPhongDonViTTXMDeXuatGiaoViecLai(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true, donViId);
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.CAN_BO_CHUYEN_VE_DON_VI_GIAI_QUYET.equals(nextStateType)) {
							//tim kiem vai tro nhan giai quyet
							GiaiQuyetDon giaiQuyetDonBenGiaiQuyet = giaiQuyetDonService.predFindCurrent(repo, giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getId(), false);
							Predicate predicate = processService.predicateFindAllByDonVi(giaiQuyetDonBenGiaiQuyet.getDonViGiaiQuyet().getDonVi(), ProcessTypeEnum.GIAI_QUYET_DON);
							List<Process> listProcessGQD = (List<Process>) processRepo.findAll(predicate);
							if (listProcessGQD.size() < 1) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_GQD_NOT_FOUND.name(),
										ApiErrorEnum.PROCESS_GQD_NOT_FOUND.getText(), ApiErrorEnum.PROCESS_GQD_NOT_FOUND.getText());
							}
							Transition transitionGQD = null;
							for (Process processFromList : listProcessGQD) {
								transitionGQD = transitionRepo.findOne(transitionService.predicateFindFromCurrent(FlowStateEnum.CAN_BO_NHAN_KET_QUA_TTXM, processFromList));
								if (transitionGQD != null) {
									break;
								}
							}
							if (transitionGQD == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_GQD_INVALID.name(),
										ApiErrorEnum.TRANSITION_GQD_INVALID.getText(), ApiErrorEnum.TRANSITION_GQD_INVALID.getText());
							}	
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = canBoChuyenVeDonViGiaiQuyet(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, donViId, transitionGQD.getProcess().getVaiTro().getLoaiVaiTro());
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						}
					}
				} else if (ProcessTypeEnum.KIEM_TRA_DE_XUAT.equals(don.getProcessType())) {
					GiaiQuyetDon giaiQuyetDonHienTai = giaiQuyetDonService.predFindCurrent(repo, thongTinGiaiQuyetDonId, false);
					if (giaiQuyetDonHienTai != null) {
						FlowStateEnum currentState = don.getCurrentState() != null ? don.getCurrentState().getType() : null;
						FlowStateEnum nextStateType = nextState.getType();
						giaiQuyetDonHienTai.setNextState(nextState);
						giaiQuyetDonHienTai.setNextForm(transition.getForm());
						String note = vaiTroNguoiDungHienTai + " " + nextState.getTenVietTat() + " ";
						Long coQuanQuanLyId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
						if (FlowStateEnum.TRINH_LANH_DAO.equals(nextStateType)) {
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = vanThuDonViTTXMTrinhLanhDao(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, false, donViId);
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.LANH_DAO_GIAO_VIEC_TRUONG_PHONG.equals(nextStateType)) {
							if (giaiQuyetDon.getPhongBanGiaiQuyet() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.name(),
										ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText(), ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText());
							}
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = lanhDaoDonViTTXMGiaoViec(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, false, donViId);
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.LANH_DAO_GIAO_VIEC_CAN_BO.equals(nextStateType)) {
							if (giaiQuyetDon.getPhongBanGiaiQuyet() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.name(),
										ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText(), ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText());
							}
							if (giaiQuyetDon.getCanBoXuLyChiDinh() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.name(),
										ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText(), ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText());
							}
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = lanhDaoDonViTTXMGiaoViec(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, false, donViId);
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO.equals(nextStateType)) {
							if (giaiQuyetDon.getCanBoXuLyChiDinh() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.name(),
										ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText(), ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText());
							}
							GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
							giaiQuyetDonTiepTheo = truongPhongDonViTTXMGiaoViec(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, false, donViId);
							return giaiQuyetDonService.doSave(giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (FlowStateEnum.CAN_BO_CHUYEN_KET_QUA_DON_VI_GIAO.equals(nextStateType)) {
							giaiQuyetDonHienTai = canBoChuyenKetQuaVeDonViGiao(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, donViId);
							return giaiQuyetDonService.doSave(giaiQuyetDonHienTai, congChucId, eass, HttpStatus.CREATED);
						}
					}
				}
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(), ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	private void disableGiaiQuyetDonCu(VaiTroEnum vaiTro, Long donId, CongChuc congChuc) {
		List<GiaiQuyetDon> giaiQuyetDonCu = (List<GiaiQuyetDon>) repo.findAll(giaiQuyetDonService.predFindOld(donId, vaiTro, congChuc));
		if (giaiQuyetDonCu != null) {
			for (GiaiQuyetDon gqd: giaiQuyetDonCu) {
				gqd.setOld(true);
				giaiQuyetDonService.save(gqd, congChuc.getId());
			}
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/giaiQuyetDons/{id}/dinhChi")
	@ApiOperation(value = "Đình chỉ đơn giải quyết", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Đình chỉ đơn giải quyết thành công") })
	public ResponseEntity<Object> dinhChiDonGiaiQuyet(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id, @RequestBody Medial_DinhChiDonGiaiQuyet params) {
		
		try {
			NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_DINHCHI);
			if (nguoiDungHienTai == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(), ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			Don don = donRepo.findOne(donService.predicateFindOne(id));
			Long thongTinGiaiQuyetDonId = don.getThongTinGiaiQuyetDon().getId();
			Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			GiaiQuyetDon giaiQuyetDonHienTai = giaiQuyetDonService.predFindCurrent(repo, thongTinGiaiQuyetDonId, false);

			giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
			
			don.setHuongXuLyXLD(HuongXuLyXLDEnum.DINH_CHI);
			don.setTrangThaiDon(TrangThaiDonEnum.DA_GIAI_QUYET);
			don.setLyDoDinhChi(params.getLyDoDinhChi());
			don.setSoQuyetDinhDinhChi(params.getSoQuyetDinhDinhChi());
			don.setNgayQuyetDinhDinhChi(params.getNgayQuyetDinhDinhChi());
			
			giaiQuyetDonService.save(giaiQuyetDonHienTai, congChucId);
			donService.save(don, congChucId);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/giaiQuyetDons/thongTinGiaiQuyetDon")
	@ApiOperation(value = "Lấy thông tin Giải quyết đơn", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy thông tin Giải quyết đơn", response = GiaiQuyetDon.class) })
	public ResponseEntity<Object> getThongTinXuLyDon(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam("id") Long id, PersistentEntityResourceAssembler eass) {
		
		try {
			NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_XEM);
			if (nguoiDung != null) {
				Don don = donRepo.findOne(donService.predicateFindOne(id));
				Long phongBanXuLyXLD = 0L;
				String vaiTroNguoiDungHienTai = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro").toString();
				Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
				Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
				if (!StringUtils.equals(VaiTroEnum.LANH_DAO.name(), vaiTroNguoiDungHienTai)) { 
					phongBanXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
				}
				
				if (don != null) {
					GiaiQuyetDon giaiQuyetDon = giaiQuyetDonService.predFindThongTinXuLy(repo, don.getId(), donViId, phongBanXuLyXLD, congChucId, vaiTroNguoiDungHienTai);
					if (giaiQuyetDon != null) {
						return new ResponseEntity<>(eass.toFullResource(giaiQuyetDon), HttpStatus.OK);
					}
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
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
	
	private GiaiQuyetDon truongPhongGiaoViecChuyenVien(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, Long donViId) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucService.predicateFindOne(congChucId));
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		disableGiaiQuyetDonCu(VaiTroEnum.CHUYEN_VIEN, donId, congChuc);
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDonHienTai.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setPhongBanGiaiQuyet(giaiQuyetDonHienTai.getPhongBanGiaiQuyet());
		giaiQuyetDonTiepTheo.setDonViGiaiQuyet(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
		giaiQuyetDonTiepTheo.setCanBoXuLyChiDinh(giaiQuyetDonHienTai.getCanBoXuLyChiDinh());
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
		
		Don don = donRepo.findOne(donId);
		don.setCurrentState(giaiQuyetDon.getNextState());
		don.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		donService.save(don, congChucId);
		giaiQuyetDonService.save(giaiQuyetDonHienTai, congChucId);
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		//State state = stateRepo.findOne(giaiQuyetDonHienTai.getNextState().getId());
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setTen(QuaTrinhXuLyEnum.GIAO_CAN_BO_GIAI_QUYET.getText());
		lichSuQTXLD.setDonViXuLy(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		lichSuQTXLD.setNoiDung(giaiQuyetDonHienTai.getyKienGiaiQuyet());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), donViId);
		lichSuQTXLD.setThuTuThucHien(thuTu);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon chuyenVienDeXuatGiaoViecLai(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucService.predicateFindOne(congChucId));
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		disableGiaiQuyetDonCu(VaiTroEnum.TRUONG_PHONG, donId, congChuc);
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setPhongBanGiaiQuyet(giaiQuyetDonHienTai.getPhongBanGiaiQuyet());
		giaiQuyetDonTiepTheo.setDonViGiaiQuyet(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
		
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		don.setCurrentState(giaiQuyetDon.getNextState());
		donService.save(don, congChucId);
		giaiQuyetDonService.save(giaiQuyetDonHienTai, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon chuyenVienGiaiQuyet(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long coQuanQuanLyId, Long congChucId, String note) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		don.setCurrentState(giaiQuyetDon.getNextState());
		don.setTrangThaiDon(TrangThaiDonEnum.DA_GIAI_QUYET);
		don.setHoanThanhDon(true);
		donService.save(don, congChucId);
		
		giaiQuyetDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		return giaiQuyetDonHienTai;
	}
	
	private GiaiQuyetDon chuyenVienChuyenVanThuGiaoTTXM(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, Long donViId) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucService.predicateFindOne(congChucId));
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
				
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		disableGiaiQuyetDonCu(VaiTroEnum.VAN_THU, donId, congChuc);
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		giaiQuyetDonTiepTheo.setPhongBanGiaiQuyet(giaiQuyetDonHienTai.getPhongBanGiaiQuyet());
		giaiQuyetDonTiepTheo.setDonViGiaiQuyet(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		giaiQuyetDonTiepTheo.setCanBoXuLyChiDinh(giaiQuyetDonHienTai.getCanBoXuLyChiDinh());
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
		
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		don.setCurrentState(giaiQuyetDon.getNextState());
		donService.save(don, congChucId);
		giaiQuyetDonService.save(giaiQuyetDonHienTai, congChucId);
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		//State state = stateRepo.findOne(giaiQuyetDonHienTai.getNextState().getId());
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setDonViXuLy(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setTen(QuaTrinhXuLyEnum.CHUYEN_CHUYEN_VIEN_NHAP_LIEU.getText());
		lichSuQTXLD.setNoiDung("");
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), donViId);
		lichSuQTXLD.setThuTuThucHien(thuTu);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		
		// disable XULYDON cua VANTHU
		List<XuLyDon> xuLyDonCu = (List<XuLyDon>) xuLyDonRepository.findAll(xuLyDonService.predFindLanhDaoVanThuOld(
				VaiTroEnum.VAN_THU, don.getId(), giaiQuyetDonHienTai.getDonViGiaiQuyet().getId()));
		if (xuLyDonCu != null) {
			for (XuLyDon xld : xuLyDonCu) {
				if (!xld.isOld()) {
					xld.setOld(true);
					xuLyDonService.save(xld, congChucId);
				}
			}
		}
		
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon vanThuChuyenVanThuDonViTTXM(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, Long donViId, VaiTroEnum chucVuNhanTTXM) {
		
		giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().setyKienCuaDonViGiaoTTXM(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().setNgayBatDauTTXM(Utils.localDateTimeNow());
		
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucService.predicateFindOne(congChucId));
		giaiQuyetDonHienTai.setPhongBanGiaiQuyet(congChuc.getCoQuanQuanLy());
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		Don don = donRepo.findOne(donId);
		State beginState = stateRepo.findOne(stateService.predicateFindByType(FlowStateEnum.BAT_DAU));
		don.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		don.setProcessType(ProcessTypeEnum.THAM_TRA_XAC_MINH);
		don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.DANG_TTXM);
		don.setTrangThaiTTXM(TrangThaiDonEnum.DANG_GIAI_QUYET);
		don.setCurrentState(beginState);
		don.setDonViThamTraXacMinh(don.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh());
		donService.save(don, congChucId);
		
		GiaiQuyetDon giaiQuyetDonTTXM = new GiaiQuyetDon();
		giaiQuyetDonTTXM.setThongTinGiaiQuyetDon(giaiQuyetDonHienTai.getThongTinGiaiQuyetDon());
		giaiQuyetDonTTXM.setChucVu(chucVuNhanTTXM);
		giaiQuyetDonTTXM.setDonViChuyenDon(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		giaiQuyetDonTTXM.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTTXM.setDonViGiaiQuyet(giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh());
		giaiQuyetDonTTXM.setThuTuThucHien(1);
		giaiQuyetDonTTXM.setDonChuyen(true);
		giaiQuyetDonTTXM.setLaTTXM(true);
		giaiQuyetDonService.save(giaiQuyetDonTTXM, congChucId);
		thongTinGiaiQuyetDonService.save(giaiQuyetDonHienTai.getThongTinGiaiQuyetDon(), congChucId);
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		LichSuQuaTrinhXuLy lichSuQTXLDTaiDonViTTXM = new LichSuQuaTrinhXuLy();
		//State state = stateRepo.findOne(giaiQuyetDonHienTai.getNextState().getId());
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setDonViXuLy(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setTen(QuaTrinhXuLyEnum.CHUYEN_DON_VI_TTXM.getText());
		lichSuQTXLD.setNoiDung(giaiQuyetDonHienTai.getyKienGiaiQuyet());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), donViId);
		lichSuQTXLD.setThuTuThucHien(thuTu);
		
		// set them 1 dong lich su qua trinh cho don vi TTXM
		lichSuQTXLDTaiDonViTTXM = lichSuQTXLD;
		lichSuQTXLDTaiDonViTTXM.setId(null);
		lichSuQTXLDTaiDonViTTXM.setDonViXuLy(giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh());
		lichSuQTXLDTaiDonViTTXM.setThuTuThucHien(0);
		
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLDTaiDonViTTXM, congChucId);
		
		return giaiQuyetDonHienTai;
	}
	
	private GiaiQuyetDon vanThuDonViTTXMTrinhLanhDao(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, boolean isLaTTXM, Long donViId) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucService.predicateFindOne(congChucId));
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setPhongBanGiaiQuyet(congChuc.getCoQuanQuanLy());
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		disableGiaiQuyetDonCu(VaiTroEnum.LANH_DAO, donId, congChuc);
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.LANH_DAO);
		giaiQuyetDonTiepTheo.setLaTTXM(isLaTTXM);
		giaiQuyetDonTiepTheo.setDonChuyen(true);
		giaiQuyetDonTiepTheo.setDonViChuyenDon(giaiQuyetDonHienTai.getDonViChuyenDon());
		giaiQuyetDonTiepTheo.setDonViGiaiQuyet(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
		if (!isLaTTXM) {
			giaiQuyetDonTiepTheo.setSoTiepCongDan(giaiQuyetDonHienTai.getSoTiepCongDan());
		}
		Don don = donRepo.findOne(donId);
		don.setCurrentState(giaiQuyetDon.getNextState());
		don.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		don.setDonViThamTraXacMinh(don.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh());
		donService.save(don, congChucId);
		giaiQuyetDonService.save(giaiQuyetDonHienTai, congChucId);
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		//State state = stateRepo.findOne(giaiQuyetDonHienTai.getNextState().getId());
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setDonViXuLy(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		lichSuQTXLD.setTen(QuaTrinhXuLyEnum.TRINH_LANH_DAO.getText());
		lichSuQTXLD.setNoiDung(giaiQuyetDonHienTai.getyKienGiaiQuyet());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), donViId);
		lichSuQTXLD.setThuTuThucHien(thuTu);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon lanhDaoDonViTTXMGiaoViec(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, boolean isLaTTXM, Long donViId) {
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucService.predicateFindOne(congChucId));
		GiaiQuyetDon giaiQuyetDonTruongPhong = new GiaiQuyetDon();
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setPhongBanGiaiQuyet(congChuc.getCoQuanQuanLy());
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		if (giaiQuyetDon.getCanBoXuLyChiDinh() == null) {
			disableGiaiQuyetDonCu(VaiTroEnum.TRUONG_PHONG, donId, congChuc);
			giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
			giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);

			lichSuQTXLD.setTen(QuaTrinhXuLyEnum.GIAO_PHONG_BAN.getText());
		} else {			
			// set giao viec cho truong phong
			disableGiaiQuyetDonCu(VaiTroEnum.TRUONG_PHONG, donId, congChuc);
			giaiQuyetDonTruongPhong.setChucVu(VaiTroEnum.TRUONG_PHONG);
			giaiQuyetDonTruongPhong.setPhongBanGiaiQuyet(giaiQuyetDon.getPhongBanGiaiQuyet());
			giaiQuyetDonTruongPhong.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
			giaiQuyetDonTruongPhong.setDonViGiaiQuyet(giaiQuyetDonHienTai.getDonViGiaiQuyet());
			giaiQuyetDonTruongPhong.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
			giaiQuyetDonTruongPhong.setDonViChuyenDon(giaiQuyetDonHienTai.getDonViChuyenDon());
			giaiQuyetDonTruongPhong.setLaTTXM(isLaTTXM);
			giaiQuyetDonTruongPhong.setDonChuyen(true);
			giaiQuyetDonTruongPhong.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
			giaiQuyetDonTruongPhong.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
			giaiQuyetDonTruongPhong.setCongChuc(congChuc);
			giaiQuyetDonTruongPhong.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
			giaiQuyetDonTruongPhong.setNextForm(giaiQuyetDonHienTai.getNextForm());
			giaiQuyetDonTruongPhong.setNextState(giaiQuyetDon.getNextState());
			if (!isLaTTXM) {
				giaiQuyetDonTruongPhong.setSoTiepCongDan(giaiQuyetDonHienTai.getSoTiepCongDan());
			}
			
			giaiQuyetDonHienTai.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
			disableGiaiQuyetDonCu(VaiTroEnum.CHUYEN_VIEN, donId, congChuc);
			giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
			giaiQuyetDonTiepTheo.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
			giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 2);
			
			lichSuQTXLD.setTen(QuaTrinhXuLyEnum.GIAO_CAN_BO_GIAI_QUYET.getText());
		}
		giaiQuyetDonTiepTheo.setPhongBanGiaiQuyet(giaiQuyetDon.getPhongBanGiaiQuyet());
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setDonViGiaiQuyet(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setDonViChuyenDon(giaiQuyetDonHienTai.getDonViChuyenDon());
		giaiQuyetDonTiepTheo.setLaTTXM(isLaTTXM);
		giaiQuyetDonTiepTheo.setDonChuyen(true);
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		if (!isLaTTXM) {
			giaiQuyetDonTiepTheo.setSoTiepCongDan(giaiQuyetDonHienTai.getSoTiepCongDan());
		}
		Don don = donRepo.findOne(donId);
		don.setDonViThamTraXacMinh(don.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh());
		don.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		don.setCurrentState(giaiQuyetDon.getNextState());
		donService.save(don, congChucId);
		giaiQuyetDonService.save(giaiQuyetDonHienTai, congChucId);
		giaiQuyetDonService.save(giaiQuyetDonTruongPhong, congChucId);
		
		//tao lich su qua trinh xu ly don
		//State state = stateRepo.findOne(giaiQuyetDonHienTai.getNextState().getId());
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setDonViXuLy(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		lichSuQTXLD.setNoiDung(giaiQuyetDonHienTai.getyKienGiaiQuyet());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), donViId);
		lichSuQTXLD.setThuTuThucHien(thuTu);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon truongPhongDonViTTXMGiaoViec(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, boolean isLaTTXM, Long donViId) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucService.predicateFindOne(congChucId));
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		disableGiaiQuyetDonCu(VaiTroEnum.CHUYEN_VIEN, donId, congChuc);
		giaiQuyetDonTiepTheo.setPhongBanGiaiQuyet(giaiQuyetDonHienTai.getPhongBanGiaiQuyet());
		giaiQuyetDonTiepTheo.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setDonViGiaiQuyet(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		giaiQuyetDonTiepTheo.setDonViChuyenDon(giaiQuyetDonHienTai.getDonViChuyenDon());
		giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
		giaiQuyetDonTiepTheo.setLaTTXM(isLaTTXM);
		giaiQuyetDonTiepTheo.setDonChuyen(true);
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
		if (!isLaTTXM) {
			giaiQuyetDonTiepTheo.setSoTiepCongDan(giaiQuyetDonHienTai.getSoTiepCongDan());
		}
		Don don = donRepo.findOne(donId);
		don.setDonViThamTraXacMinh(don.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh());
		don.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		don.setCurrentState(giaiQuyetDon.getNextState());
		donService.save(don, congChucId);
		giaiQuyetDonService.save(giaiQuyetDonHienTai, congChucId);
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		//State state = stateRepo.findOne(giaiQuyetDonHienTai.getNextState().getId());
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setDonViXuLy(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		lichSuQTXLD.setTen(QuaTrinhXuLyEnum.GIAO_CAN_BO_GIAI_QUYET.getText());
		lichSuQTXLD.setNoiDung(giaiQuyetDonHienTai.getyKienGiaiQuyet());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), donViId);
		lichSuQTXLD.setThuTuThucHien(thuTu);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon canBoDonViTTXMDeXuatGiaoViecLai(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, boolean isLaTTXM, Long donViId) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucService.predicateFindOne(congChucId));
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		disableGiaiQuyetDonCu(VaiTroEnum.TRUONG_PHONG, donId, congChuc);
		giaiQuyetDonTiepTheo.setPhongBanGiaiQuyet(giaiQuyetDonHienTai.getPhongBanGiaiQuyet());
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
		giaiQuyetDonTiepTheo.setDonViGiaiQuyet(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		giaiQuyetDonTiepTheo.setDonViChuyenDon(giaiQuyetDonHienTai.getDonViChuyenDon());
		giaiQuyetDonTiepTheo.setLaTTXM(isLaTTXM);
		giaiQuyetDonTiepTheo.setDonChuyen(true);
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
		if (!isLaTTXM) {
			giaiQuyetDonTiepTheo.setSoTiepCongDan(giaiQuyetDonHienTai.getSoTiepCongDan());
		}
		Don don = donRepo.findOne(donId);
		don.setDonViThamTraXacMinh(don.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh());
		don.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		don.setCurrentState(giaiQuyetDon.getNextState());
		donService.save(don, congChucId);
		giaiQuyetDonService.save(giaiQuyetDonHienTai, congChucId);
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		State state = stateRepo.findOne(giaiQuyetDonHienTai.getNextState().getId());
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setDonViXuLy(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		lichSuQTXLD.setTen(state.getTenVietTat());
		lichSuQTXLD.setNoiDung(giaiQuyetDonHienTai.getyKienGiaiQuyet());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), donViId);
		lichSuQTXLD.setThuTuThucHien(thuTu);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon truongPhongDonViTTXMDeXuatGiaoViecLai(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, boolean isLaTTXM, Long donViId) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucService.predicateFindOne(congChucId));
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		disableGiaiQuyetDonCu(VaiTroEnum.LANH_DAO, donId, congChuc);
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.LANH_DAO);
		giaiQuyetDonTiepTheo.setDonViGiaiQuyet(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		giaiQuyetDonTiepTheo.setLaTTXM(isLaTTXM);
		giaiQuyetDonTiepTheo.setDonViChuyenDon(giaiQuyetDonHienTai.getDonViChuyenDon());
		giaiQuyetDonTiepTheo.setDonChuyen(true);
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
		if (!isLaTTXM) {
			giaiQuyetDonTiepTheo.setSoTiepCongDan(giaiQuyetDonHienTai.getSoTiepCongDan());
		}
		Don don = donRepo.findOne(donId);
		don.setDonViThamTraXacMinh(don.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh());
		don.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		don.setCurrentState(giaiQuyetDon.getNextState());
		donService.save(don, congChucId);
		giaiQuyetDonService.save(giaiQuyetDonHienTai, congChucId);
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		State state = stateRepo.findOne(giaiQuyetDonHienTai.getNextState().getId());
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setDonViXuLy(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		lichSuQTXLD.setTen(state.getTenVietTat());
		lichSuQTXLD.setNoiDung(giaiQuyetDonHienTai.getyKienGiaiQuyet());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), donViId);
		lichSuQTXLD.setThuTuThucHien(thuTu);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		return giaiQuyetDonTiepTheo;
	}

	private GiaiQuyetDon canBoChuyenVeDonViGiaiQuyet(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, Long donViId, VaiTroEnum vaiTroGQD) {
		giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().setNgayKetThucTTXM(Utils.localDateTimeNow());
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucService.predicateFindOne(congChucId));
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		GiaiQuyetDon giaiQuyetDonBenGiaiQuyet = giaiQuyetDonService.predFindCurrent(repo, giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getId(), false);
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		disableGiaiQuyetDonCu(vaiTroGQD, donId, giaiQuyetDonBenGiaiQuyet.getCanBoXuLyChiDinh());
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setDonViGiaiQuyet(giaiQuyetDonBenGiaiQuyet.getDonViGiaiQuyet());
		giaiQuyetDonTiepTheo.setCanBoXuLyChiDinh(giaiQuyetDonBenGiaiQuyet.getCanBoXuLyChiDinh());
		giaiQuyetDonTiepTheo.setPhongBanGiaiQuyet(giaiQuyetDonBenGiaiQuyet.getCanBoXuLyChiDinh().getCoQuanQuanLy());
		giaiQuyetDonTiepTheo.setChucVu(vaiTroGQD);
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonBenGiaiQuyet.getThuTuThucHien() + 1);
		
		State canBoNhanKetQuaState = stateRepo.findOne(stateService.predicateFindByType(FlowStateEnum.CAN_BO_NHAN_KET_QUA_TTXM));		
		Don don = donRepo.findOne(donId);
		don.setProcessType(ProcessTypeEnum.GIAI_QUYET_DON);
		don.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		don.setCurrentState(canBoNhanKetQuaState);
		donService.save(don, congChucId);
		giaiQuyetDonService.save(giaiQuyetDonHienTai, congChucId);
		thongTinGiaiQuyetDonService.save(giaiQuyetDonHienTai.getThongTinGiaiQuyetDon(), congChucId);
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setDonViXuLy(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		lichSuQTXLD.setTen(QuaTrinhXuLyEnum.GUI_BAO_CAO_TTXM_CHO_DON_VI_GIAO.getText());
		lichSuQTXLD.setNoiDung(giaiQuyetDonHienTai.getyKienGiaiQuyet());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), donViId);
		lichSuQTXLD.setThuTuThucHien(thuTu);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon canBoChuyenKetQuaVeDonViGiao(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, Long donViId) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucService.predicateFindOne(congChucId));
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		//giaiQuyetDonHienTai.setGuiBaoCaoKiemTraDeXuat(true);
		giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().setNgayKetThucKTDX(Utils.localDateTimeNow());
		
		SoTiepCongDan stcd = giaiQuyetDonHienTai.getSoTiepCongDan();
		stcd.setTrinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum.DA_CO_BAO_CAO_KIEM_TRA_DE_XUAT);
		//stcd.setKetQuaGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		stcd.setNoiDungBaoCaoKetQuaKiemTra(giaiQuyetDon.getyKienGiaiQuyet());
		stcd.setNgayGuiKetQua(Utils.localDateTimeNow());
		soTiepCongDanService.save(stcd, congChucId);
		
		Don don = donRepo.findOne(donId);
		don.setProcessType(null);
		don.setCurrentState(null);
		don.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		donService.save(don, congChucId);
		
		//tao lich su qua trinh xu ly don
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
		lichSuQTXLD.setDon(don);
		lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
		lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
		lichSuQTXLD.setTen(QuaTrinhXuLyEnum.CHUYEN_KET_QUA_VE_DON_VI_GIAI_QUYET.getText());
		lichSuQTXLD.setDonViXuLy(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		lichSuQTXLD.setNoiDung(giaiQuyetDonHienTai.getyKienGiaiQuyet());
		int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, don.getId(), donViId);
		lichSuQTXLD.setThuTuThucHien(thuTu);
		lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
		thongTinGiaiQuyetDonService.save(giaiQuyetDonHienTai.getThongTinGiaiQuyetDon(), congChucId);
		return giaiQuyetDonHienTai;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/giaiQuyetDons/inPhieuGiaoNhiemVuXacMinhToCao")
	@ApiOperation(value = "In phiếu giao nhiêm vụ xác minh khiếu nại", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordPhieuXacMinhKhieuNai(@RequestParam(value = "tenCoQuan", required = false) String tenCoQuan,
			@RequestParam(value = "noiDungDonThu", required = false) String noiDungDonThu,
			@RequestParam(value = "ghiChu", required = false) String ghiChu,
			HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			mappings.put("hoVaTen", tenCoQuan);
			mappings.put("noiDungDonThu", noiDungDonThu);
			mappings.put("ghiChu", ghiChu);
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/giaiquyetdon/GQD_PHIEU_XAC_MINH_TO_CAO.docx").getFile(), mappings);
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/giaiQuyetDons/inPhieuGiaoNhiemVuXacMinhKhieuNai")
	@ApiOperation(value = "In phiếu giao nhiêm vụ xác minh khiếu nại", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordPhieuXacMinhToCao(@RequestParam(value = "tenCoQuanThuLyGQKN", required = false) String tenCoQuanThuLyGQKN,
			@RequestParam(value = "tenCoQuanDuocGiaoNhiemVuXM", required = false) String tenCoQuanDuocGiaoNhiemVuXM,
			@RequestParam(value = "hoTenNguoiKhieuNai", required = false) String hoTenNguoiKhieuNai,
			@RequestParam(value = "noiDungTTXM", required = false) String noiDungTTXM,
			HttpServletResponse response) {

		try {
			HashMap<String, String> mappings = new HashMap<String, String>();
			mappings.put("tenCoQuanThuLyGQKN", tenCoQuanThuLyGQKN);
			mappings.put("tenCoQuanDuocGiaoNhiemVuXM", tenCoQuanDuocGiaoNhiemVuXM);
			mappings.put("hoTenNguoiKhieuNai", hoTenNguoiKhieuNai);
			mappings.put("noiDungTTXM", noiDungTTXM);
			WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/giaiquyetdon/GQD_PHIEU_XAC_MINH_KHIEU_NAI_1.docx").getFile(), mappings);
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}	
}
