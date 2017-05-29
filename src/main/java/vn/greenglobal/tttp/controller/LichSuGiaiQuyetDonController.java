package vn.greenglobal.tttp.controller;

import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.enums.TinhTrangGiaiQuyetEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.LichSuGiaiQuyetDon;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.LichSuGiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.ProcessRepository;
import vn.greenglobal.tttp.repository.StateRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.service.GiaiQuyetDonService;
import vn.greenglobal.tttp.service.LichSuGiaiQuyetDonService;
import vn.greenglobal.tttp.service.ProcessService;
import vn.greenglobal.tttp.service.StateService;
import vn.greenglobal.tttp.service.TransitionService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "lichSuGiaiQuyetDons", description = "Lịch sử giải quyết đơn")
public class LichSuGiaiQuyetDonController extends TttpController<LichSuGiaiQuyetDon> {

	@Autowired
	private LichSuGiaiQuyetDonRepository repo;
	
	@Autowired
	private StateRepository stateRepo;
	
	@Autowired
	private DonRepository donRepo;
	
	@Autowired
	private StateService stateService;
	
	@Autowired
	private GiaiQuyetDonService giaiQuyetDonService;
	
	@Autowired
	private GiaiQuyetDonRepository giaiQuyetDonRepo;
	
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
	private LichSuGiaiQuyetDonService lichSuGiaiQuyetDonService;

	public LichSuGiaiQuyetDonController(BaseRepository<LichSuGiaiQuyetDon, Long> repo) {
		super(repo);
	}
	
	
	@SuppressWarnings("unused")
	@RequestMapping(method = RequestMethod.POST, value = "/lichSuGiaiQuyetDons")
	@ApiOperation(value = "Thêm mới Lịch sử giải quyết đơn", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Thêm mới Lịch sử giải quyết đơn thành công", response = LichSuGiaiQuyetDon.class),
			@ApiResponse(code = 201, message = "Thêm mới Lịch sử giải quyết đơn thành công", response = LichSuGiaiQuyetDon.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody LichSuGiaiQuyetDon lichSuGiaiQuyetDon, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		
		NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DON_SUA);
		CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
		if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
				&& commonProfile.containsAttribute("coQuanQuanLyId")) {
			if (lichSuGiaiQuyetDon.getGiaiQuyetDon() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.GIAIQUYETDON_REQUIRED.name(),
						ApiErrorEnum.GIAIQUYETDON_REQUIRED.getText());
			}
			if (lichSuGiaiQuyetDon.getNextState() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.NEXT_STATE_REQUIRED.name(),
						ApiErrorEnum.NEXT_STATE_REQUIRED.getText());
			}
			State nextState = stateRepo.findOne(stateService.predicateFindOne(lichSuGiaiQuyetDon.getNextState().getId()));
			Long giaiQuyetDonId = lichSuGiaiQuyetDon.getGiaiQuyetDon().getId();
			GiaiQuyetDon giaiQuyetDon = giaiQuyetDonRepo.findOne(giaiQuyetDonService.predicateFindOne(giaiQuyetDonId));
			
			Don don = giaiQuyetDon.getDon();
			
			if (don == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DON_REQUIRED.name(),
						ApiErrorEnum.DON_REQUIRED.getText());
			}
			
			if (don.getProcessType() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_TYPE_REQUIRED.name(),
						ApiErrorEnum.PROCESS_TYPE_REQUIRED.getText());
			}
			
			String vaiTroNguoiDungHienTai = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro")
					.toString();
			Long congChucId = new Long(
					profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());

			CongChuc congChuc = congChucRepo.findOne(congChucId);

			boolean isOwner = don.getNguoiTao().getId() == null || don.getNguoiTao().getId().equals(0L) ? true
					: congChucId.longValue() == don.getNguoiTao().getId().longValue() ? true : false;

			CoQuanQuanLy donVi = congChuc.getCoQuanQuanLy().getDonVi();
			Process process = processRepo.findOne(
					processService.predicateFindAll(vaiTroNguoiDungHienTai, donVi, isOwner, don.getProcessType()));

			if (process == null && isOwner) {
				process = processRepo.findOne(
						processService.predicateFindAll(vaiTroNguoiDungHienTai, donVi, false, don.getProcessType()));
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

			LichSuGiaiQuyetDon giaiQuyetDonHienTai = lichSuGiaiQuyetDonService.predFindCurrent(repo, giaiQuyetDonId);
			if (giaiQuyetDonHienTai != null) {
				FlowStateEnum currentState = don.getCurrentState() != null ? don.getCurrentState().getType() : null;
				FlowStateEnum nextStateType = nextState.getType();
				giaiQuyetDonHienTai.setNextState(nextState);
				// Thong tin xu ly don
				String note = vaiTroNguoiDungHienTai + " " + nextState.getTenVietTat() + " ";
				Long coQuanQuanLyId = new Long(
						profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
				if (FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO.equals(nextStateType)) {

					LichSuGiaiQuyetDon lichSuGiaiQuyetDonTiepTheo = new LichSuGiaiQuyetDon();
					lichSuGiaiQuyetDonTiepTheo = truongPhongGiaoViecChuyenVien(giaiQuyetDonHienTai, coQuanQuanLyId, congChucId, note);
					return Utils.doSave(repo, lichSuGiaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);

				} else if (FlowStateEnum.CAN_BO_DE_XUAT_GIAO_VIEC_LAI.equals(nextStateType)) {
					
				}
			} 
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		
		return Utils.doSave(repo, lichSuGiaiQuyetDon,
				new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
				HttpStatus.CREATED);
	}
	
	private LichSuGiaiQuyetDon truongPhongGiaoViecChuyenVien(LichSuGiaiQuyetDon lichSuGiaiQuyetDon, Long coQuanQuanLyId, Long congChucId, String note) {
		Long donId = lichSuGiaiQuyetDon.getGiaiQuyetDon().getDon().getId();
		LichSuGiaiQuyetDon lichSuGiaiQuyetDonHienTai = lichSuGiaiQuyetDonService.predFindCurrent(repo, lichSuGiaiQuyetDon.getGiaiQuyetDon().getId());
		lichSuGiaiQuyetDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		lichSuGiaiQuyetDonHienTai.setNextState(lichSuGiaiQuyetDon.getNextState());

		lichSuGiaiQuyetDonHienTai.setCanBoXuLyChiDinh(lichSuGiaiQuyetDon.getCanBoXuLyChiDinh());
		lichSuGiaiQuyetDonHienTai.setyKienGiaiQuyet(lichSuGiaiQuyetDon.getyKienGiaiQuyet());
		lichSuGiaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		LichSuGiaiQuyetDon lichSuGiaiQuyetDonTiepTheo = new LichSuGiaiQuyetDon();
		lichSuGiaiQuyetDonTiepTheo.setGiaiQuyetDon(lichSuGiaiQuyetDonHienTai.getGiaiQuyetDon());
		lichSuGiaiQuyetDonTiepTheo.setCongChuc(lichSuGiaiQuyetDon.getCanBoXuLyChiDinh());
		lichSuGiaiQuyetDonTiepTheo.setyKienGiaiQuyet(lichSuGiaiQuyetDon.getyKienGiaiQuyet());
		lichSuGiaiQuyetDonTiepTheo.setCanBoXuLyChiDinh(lichSuGiaiQuyetDonHienTai.getCanBoXuLyChiDinh());
		lichSuGiaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		lichSuGiaiQuyetDonTiepTheo.setThuTuThucHien(lichSuGiaiQuyetDonHienTai.getThuTuThucHien() + 1);
		
		Don don = donRepo.findOne(donId);
		don.setCurrentState(lichSuGiaiQuyetDon.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(repo, lichSuGiaiQuyetDonHienTai, congChucId);
		return lichSuGiaiQuyetDonTiepTheo;
	}
}
