package vn.greenglobal.tttp.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.FlowStateEnum;
import vn.greenglobal.tttp.enums.HuongGiaiQuyetTCDEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.enums.TinhTrangGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.ThongTinGiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.ProcessRepository;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.repository.StateRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.service.ThongTinGiaiQuyetDonService;
import vn.greenglobal.tttp.service.GiaiQuyetDonService;
import vn.greenglobal.tttp.service.ProcessService;
import vn.greenglobal.tttp.service.StateService;
import vn.greenglobal.tttp.service.TransitionService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "giaiQuyetDons", description = "Giải quyết đơn")
public class GiaiQuyetDonController extends TttpController<GiaiQuyetDon> {

	@Autowired
	private GiaiQuyetDonRepository repo;
	
	@Autowired
	private StateRepository stateRepo;
	
	@Autowired
	private DonRepository donRepo;
	
	@Autowired
	private StateService stateService;
	
	@Autowired
	private ThongTinGiaiQuyetDonService thongTinGiaiQuyetDonService;
	
	@Autowired
	private ThongTinGiaiQuyetDonRepository thongTinGiaiQuyetDonRepo;
	
	@Autowired
	private SoTiepCongDanRepository songTiepCongDanRepo;
	
	@Autowired
	private CongChucRepository congChucRepo;
	
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
		
		NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_SUA);
		CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
		if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
				&& commonProfile.containsAttribute("coQuanQuanLyId")) {
			if (giaiQuyetDon.getThongTinGiaiQuyetDon() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.THONGTINGIAIQUYETDON_REQUIRED.name(), ApiErrorEnum.THONGTINGIAIQUYETDON_REQUIRED.getText());
			}
			if (giaiQuyetDon.getNextState() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.NEXT_STATE_REQUIRED.name(), ApiErrorEnum.NEXT_STATE_REQUIRED.getText());
			}
			State nextState = stateRepo.findOne(stateService.predicateFindOne(giaiQuyetDon.getNextState().getId()));
			Long thongTinGiaiQuyetDonId = giaiQuyetDon.getThongTinGiaiQuyetDon().getId();
			ThongTinGiaiQuyetDon thongTinGiaiQuyetDon = thongTinGiaiQuyetDonRepo.findOne(thongTinGiaiQuyetDonService.predicateFindOne(thongTinGiaiQuyetDonId));
			
			Don don = thongTinGiaiQuyetDon.getDon();
			
			if (don == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DON_REQUIRED.name(), ApiErrorEnum.DON_REQUIRED.getText());
			}
			
			if (don.getProcessType() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_TYPE_REQUIRED.name(), ApiErrorEnum.PROCESS_TYPE_REQUIRED.getText());
			}
			
			String vaiTroNguoiDungHienTai = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro").toString();
			Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());

			CongChuc congChuc = congChucRepo.findOne(congChucId);

			boolean isOwner = don.getNguoiTao().getId() == null || don.getNguoiTao().getId().equals(0L) ? true
					: congChucId.longValue() == don.getNguoiTao().getId().longValue() ? true : false;

			CoQuanQuanLy donVi = congChuc.getCoQuanQuanLy().getDonVi();
			Process process = processRepo.findOne(processService.predicateFindAll(vaiTroNguoiDungHienTai, donVi, isOwner, don.getProcessType()));

			if (process == null && isOwner) {
				process = processRepo.findOne(processService.predicateFindAll(vaiTroNguoiDungHienTai, donVi, false, don.getProcessType()));
			}

			if (process == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_NOT_FOUND.name(),
						ApiErrorEnum.PROCESS_NOT_FOUND.getText());
			}
			Transition transition = transitionRepo.findOne(
					transitionService.predicatePrivileged(don.getCurrentState(), nextState, process));

			if (transition == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_FORBIDDEN.name(),
						ApiErrorEnum.TRANSITION_FORBIDDEN.getText());
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
					if (FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO.equals(nextStateType)) {
						if (giaiQuyetDon.getCanBoXuLyChiDinh() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.name(),
									ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText());
						}
						GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
						giaiQuyetDonTiepTheo = truongPhongGiaoViecChuyenVien(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.CAN_BO_DE_XUAT_GIAO_VIEC_LAI.equals(nextStateType)) {
						GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
						giaiQuyetDonTiepTheo = chuyenVienDeXuatGiaoViecLai(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.CAN_BO_CHUYEN_VAN_THU_GIAO_TTXM.equals(nextStateType)) {
						if (giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.name(),
									ApiErrorEnum.DONVITHAMTRAXACMINH_REQUIRED.getText());
						}
						GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
						giaiQuyetDonTiepTheo = chuyenVienChuyenVanThuGiaoTTXM(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.KET_THUC.equals(nextStateType)) {
						GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
						giaiQuyetDonTiepTheo = chuyenVienGiaiQuyet(giaiQuyetDonHienTai, giaiQuyetDon, coQuanQuanLyId, congChucId, note);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.VAN_THU_CHUYEN_DON_VI_TTXM.equals(nextStateType)) {
						giaiQuyetDonHienTai = vanThuChuyenVanThuDonViTTXM(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note);
						return Utils.doSave(repo, giaiQuyetDonHienTai, congChucId, eass, HttpStatus.CREATED);
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
						giaiQuyetDonTiepTheo = vanThuDonViTTXMTrinhLanhDao(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.LANH_DAO_GIAO_VIEC_TRUONG_PHONG.equals(nextStateType)) {
						if (giaiQuyetDon.getPhongBanGiaiQuyet() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.name(),
									ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText());
						}
						GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
						giaiQuyetDonTiepTheo = lanhDaoDonViTTXMGiaoViec(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.LANH_DAO_GIAO_VIEC_CAN_BO.equals(nextStateType)) {
						if (giaiQuyetDon.getPhongBanGiaiQuyet() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.name(),
									ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText());
						}
						if (giaiQuyetDon.getCanBoXuLyChiDinh() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.name(),
									ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText());
						}
						GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
						giaiQuyetDonTiepTheo = lanhDaoDonViTTXMGiaoViec(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO.equals(nextStateType)) {
						if (giaiQuyetDon.getCanBoXuLyChiDinh() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.name(),
									ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText());
						}
						GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
						giaiQuyetDonTiepTheo = truongPhongDonViTTXMGiaoViec(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.CAN_BO_DE_XUAT_GIAO_VIEC_LAI.equals(nextStateType)) {
						GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
						giaiQuyetDonTiepTheo = canBoDonViTTXMDeXuatGiaoViecLai(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.TRUONG_PHONG_DE_XUAT_GIAO_VIEC_LAI.equals(nextStateType)) {
						GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
						giaiQuyetDonTiepTheo = truongPhongDonViTTXMDeXuatGiaoViecLai(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.CAN_BO_CHUYEN_VE_DON_VI_GIAI_QUYET.equals(nextStateType)) {
						GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
						giaiQuyetDonTiepTheo = canBoChuyenVeDonViGiaiQuyet(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
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
					Long coQuanQuanLyId = new Long(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
					if (FlowStateEnum.TRINH_LANH_DAO.equals(nextStateType)) {
						GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
						giaiQuyetDonTiepTheo = vanThuDonViTTXMTrinhLanhDao(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, false);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.LANH_DAO_GIAO_VIEC_TRUONG_PHONG.equals(nextStateType)) {
						if (giaiQuyetDon.getPhongBanGiaiQuyet() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.name(),
									ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText());
						}
						GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
						giaiQuyetDonTiepTheo = lanhDaoDonViTTXMGiaoViec(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.LANH_DAO_GIAO_VIEC_CAN_BO.equals(nextStateType)) {
						if (giaiQuyetDon.getPhongBanGiaiQuyet() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.name(),
									ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText());
						}
						if (giaiQuyetDon.getCanBoXuLyChiDinh() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.name(),
									ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText());
						}
						GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
						giaiQuyetDonTiepTheo = lanhDaoDonViTTXMGiaoViec(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO.equals(nextStateType)) {
						if (giaiQuyetDon.getCanBoXuLyChiDinh() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.name(),
									ApiErrorEnum.CANBOXULYCHIDINH_REQUIRED.getText());
						}
						GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
						giaiQuyetDonTiepTheo = truongPhongDonViTTXMGiaoViec(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note, true);
						return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (FlowStateEnum.CAN_BO_CHUYEN_KET_QUA_DON_VI_GIAO.equals(nextStateType)) {
						giaiQuyetDonHienTai = canBoChuyenKetQuaVeDonViGiao(giaiQuyetDonHienTai, giaiQuyetDon, congChucId, note);
						return Utils.doSave(repo, giaiQuyetDonHienTai, congChucId, eass, HttpStatus.CREATED);
					}
				}
			}
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}
	
	private void disableGiaiQuyetDonCu(VaiTroEnum vaiTro, Long donId, CongChuc congChuc) {
		List<GiaiQuyetDon> giaiQuyetDonCu = (List<GiaiQuyetDon>) repo.findAll(giaiQuyetDonService.predFindOld(donId, vaiTro, congChuc));
		if (giaiQuyetDonCu != null) {
			for (GiaiQuyetDon gqd: giaiQuyetDonCu) {
				gqd.setOld(true);
				Utils.save(repo, gqd, congChuc.getId());
			}
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/giaiQuyetDons/{id}/dinhChi")
	@ApiOperation(value = "Đình chỉ đơn giải quyết", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Đình chỉ đơn giải quyết thành công") })
	public ResponseEntity<Object> dinhChiDonGiaiQuyet(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {
		
		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_DINHCHI) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}		
		// Đang đợi confirm của khách hàng

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	private GiaiQuyetDon truongPhongGiaoViecChuyenVien(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc =congChucRepo.findOne(congChucId);
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
		Utils.save(donRepo, don, congChucId);
		Utils.save(repo, giaiQuyetDonHienTai, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon chuyenVienDeXuatGiaoViecLai(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucId);
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
		don.setCurrentState(giaiQuyetDon.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(repo, giaiQuyetDonHienTai, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon chuyenVienGiaiQuyet(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long coQuanQuanLyId, Long congChucId, String note) {
		giaiQuyetDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		return giaiQuyetDonHienTai;
	}
	
	private GiaiQuyetDon chuyenVienChuyenVanThuGiaoTTXM(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		disableGiaiQuyetDonCu(VaiTroEnum.VAN_THU, donId, congChuc);
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		giaiQuyetDonTiepTheo.setDonViGiaiQuyet(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		giaiQuyetDonTiepTheo.setCanBoXuLyChiDinh(giaiQuyetDonHienTai.getCanBoXuLyChiDinh());
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
		
		Don don = donRepo.findOne(donId);
		don.setCurrentState(giaiQuyetDon.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(repo, giaiQuyetDonHienTai, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon vanThuChuyenVanThuDonViTTXM(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		giaiQuyetDonHienTai.setPhongBanGiaiQuyet(congChuc.getCoQuanQuanLy());
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		Don don = donRepo.findOne(donId);
		State beginState = stateRepo.findOne(stateService.predicateFindByType(FlowStateEnum.BAT_DAU));
		don.setProcessType(ProcessTypeEnum.THAM_TRA_XAC_MINH);
		don.setCurrentState(beginState);
		don.setDonViThamTraXacMinh(don.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh());
		Utils.save(donRepo, don, congChucId);
		
		GiaiQuyetDon giaiQuyetDonTTXM = new GiaiQuyetDon();
		giaiQuyetDonTTXM.setThongTinGiaiQuyetDon(giaiQuyetDonHienTai.getThongTinGiaiQuyetDon());
		giaiQuyetDonTTXM.setChucVu(VaiTroEnum.VAN_THU);
		giaiQuyetDonTTXM.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTTXM.setDonViGiaiQuyet(giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh());
		giaiQuyetDonTTXM.setThuTuThucHien(1);
		giaiQuyetDonTTXM.setDonChuyen(true);
		giaiQuyetDonTTXM.setLaTTXM(true);
		Utils.save(repo, giaiQuyetDonTTXM, congChucId);
		
		return giaiQuyetDonHienTai;
	}
	
	private GiaiQuyetDon vanThuDonViTTXMTrinhLanhDao(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, boolean isLaTTXM) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setPhongBanGiaiQuyet(congChuc.getCoQuanQuanLy());
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		disableGiaiQuyetDonCu(VaiTroEnum.LANH_DAO, donId, congChuc);
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.LANH_DAO);
		giaiQuyetDonTiepTheo.setLaTTXM(isLaTTXM);
		giaiQuyetDonTiepTheo.setDonChuyen(true);
		giaiQuyetDonTiepTheo.setDonViGiaiQuyet(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
		if (!isLaTTXM) {
			giaiQuyetDonTiepTheo.setSoTiepCongDan(giaiQuyetDonHienTai.getSoTiepCongDan());
		}
		Don don = donRepo.findOne(donId);
		don.setCurrentState(giaiQuyetDon.getNextState());
		don.setDonViThamTraXacMinh(don.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh());
		Utils.save(donRepo, don, congChucId);
		Utils.save(repo, giaiQuyetDonHienTai, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon lanhDaoDonViTTXMGiaoViec(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, boolean isLaTTXM) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setPhongBanGiaiQuyet(congChuc.getCoQuanQuanLy());
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		if (giaiQuyetDon.getCanBoXuLyChiDinh() == null) {
			disableGiaiQuyetDonCu(VaiTroEnum.TRUONG_PHONG, donId, congChuc);
			giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
		} else {
			disableGiaiQuyetDonCu(VaiTroEnum.CHUYEN_VIEN, donId, congChuc);
			giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
			giaiQuyetDonTiepTheo.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		}
		giaiQuyetDonTiepTheo.setPhongBanGiaiQuyet(giaiQuyetDon.getPhongBanGiaiQuyet());
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setDonViGiaiQuyet(giaiQuyetDonHienTai.getDonViGiaiQuyet());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setLaTTXM(isLaTTXM);
		giaiQuyetDonTiepTheo.setDonChuyen(true);
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
		if (!isLaTTXM) {
			giaiQuyetDonTiepTheo.setSoTiepCongDan(giaiQuyetDonHienTai.getSoTiepCongDan());
		}
		Don don = donRepo.findOne(donId);
		don.setDonViThamTraXacMinh(don.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh());
		don.setCurrentState(giaiQuyetDon.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(repo, giaiQuyetDonHienTai, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon truongPhongDonViTTXMGiaoViec(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, boolean isLaTTXM) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		disableGiaiQuyetDonCu(VaiTroEnum.CHUYEN_VIEN, donId, congChuc);
		giaiQuyetDonTiepTheo.setPhongBanGiaiQuyet(giaiQuyetDonHienTai.getPhongBanGiaiQuyet());
		giaiQuyetDonTiepTheo.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setDonViGiaiQuyet(giaiQuyetDonHienTai.getDonViGiaiQuyet());
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
		don.setCurrentState(giaiQuyetDon.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(repo, giaiQuyetDonHienTai, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon canBoDonViTTXMDeXuatGiaoViecLai(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, boolean isLaTTXM) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucId);
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
		giaiQuyetDonTiepTheo.setLaTTXM(isLaTTXM);
		giaiQuyetDonTiepTheo.setDonChuyen(true);
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
		if (!isLaTTXM) {
			giaiQuyetDonTiepTheo.setSoTiepCongDan(giaiQuyetDonHienTai.getSoTiepCongDan());
		}
		Don don = donRepo.findOne(donId);
		don.setDonViThamTraXacMinh(don.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh());
		don.setCurrentState(giaiQuyetDon.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(repo, giaiQuyetDonHienTai, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon truongPhongDonViTTXMDeXuatGiaoViecLai(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note, boolean isLaTTXM) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucId);
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
		giaiQuyetDonTiepTheo.setDonChuyen(true);
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
		if (!isLaTTXM) {
			giaiQuyetDonTiepTheo.setSoTiepCongDan(giaiQuyetDonHienTai.getSoTiepCongDan());
		}
		Don don = donRepo.findOne(donId);
		don.setDonViThamTraXacMinh(don.getThongTinGiaiQuyetDon().getDonViThamTraXacMinh());
		don.setCurrentState(giaiQuyetDon.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(repo, giaiQuyetDonHienTai, congChucId);
		return giaiQuyetDonTiepTheo;
	}

	private GiaiQuyetDon canBoChuyenVeDonViGiaiQuyet(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		GiaiQuyetDon giaiQuyetDonBenGiaiQuyet = giaiQuyetDonService.predFindCurrent(repo, giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getId(), false);
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		disableGiaiQuyetDonCu(VaiTroEnum.CHUYEN_VIEN, donId, giaiQuyetDonBenGiaiQuyet.getCanBoXuLyChiDinh());
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setDonViGiaiQuyet(giaiQuyetDonBenGiaiQuyet.getDonViGiaiQuyet());
		giaiQuyetDonTiepTheo.setCanBoXuLyChiDinh(giaiQuyetDonBenGiaiQuyet.getCanBoXuLyChiDinh());
		giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonBenGiaiQuyet.getThuTuThucHien() + 1);
		
		State canBoNhanKetQuaState = stateRepo.findOne(stateService.predicateFindByType(FlowStateEnum.CAN_BO_NHAN_KET_QUA_TTXM));		
		Don don = donRepo.findOne(donId);
		don.setProcessType(ProcessTypeEnum.GIAI_QUYET_DON);
		don.setCurrentState(canBoNhanKetQuaState);
		Utils.save(donRepo, don, congChucId);
		Utils.save(repo, giaiQuyetDonHienTai, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon canBoChuyenKetQuaVeDonViGiao(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long congChucId, String note) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		giaiQuyetDonHienTai.setCongChuc(congChuc);
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		SoTiepCongDan stcd = giaiQuyetDonHienTai.getSoTiepCongDan();
		stcd.setTrinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum.DA_CO_BAO_CAO_KIEM_TRA_DE_XUAT);
		Utils.save(songTiepCongDanRepo, stcd, congChucId);
		
		Don don = donRepo.findOne(donId);
		don.setProcessType(null);
		don.setCurrentState(null);
		Utils.save(donRepo, don, congChucId);
		return giaiQuyetDonHienTai;
	}
}
