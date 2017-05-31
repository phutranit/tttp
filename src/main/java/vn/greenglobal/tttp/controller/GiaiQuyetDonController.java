package vn.greenglobal.tttp.controller;

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
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.ThongTinGiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.ProcessRepository;
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
		
		NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA);
		CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
		if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
				&& commonProfile.containsAttribute("coQuanQuanLyId")) {
			if (giaiQuyetDon.getThongTinGiaiQuyetDon() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.THONGTINGIAIQUYETDON_REQUIRED.name(),
						ApiErrorEnum.THONGTINGIAIQUYETDON_REQUIRED.getText());
			}
			if (giaiQuyetDon.getNextState() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.NEXT_STATE_REQUIRED.name(),
						ApiErrorEnum.NEXT_STATE_REQUIRED.getText());
			}
			State nextState = stateRepo.findOne(stateService.predicateFindOne(giaiQuyetDon.getNextState().getId()));
			Long thongTinGiaiQuyetDonId = giaiQuyetDon.getThongTinGiaiQuyetDon().getId();
			ThongTinGiaiQuyetDon thongTinGiaiQuyetDon = thongTinGiaiQuyetDonRepo.findOne(thongTinGiaiQuyetDonService.predicateFindOne(thongTinGiaiQuyetDonId));
			
			Don don = thongTinGiaiQuyetDon.getDon();
			
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

			GiaiQuyetDon giaiQuyetDonHienTai = giaiQuyetDonService.predFindCurrent(repo, thongTinGiaiQuyetDonId);
			if (giaiQuyetDonHienTai != null) {
				FlowStateEnum currentState = don.getCurrentState() != null ? don.getCurrentState().getType() : null;
				FlowStateEnum nextStateType = nextState.getType();
				giaiQuyetDonHienTai.setNextState(nextState);
				giaiQuyetDonHienTai.setNextForm(transition.getForm());
				// Thong tin xu ly don
				String note = vaiTroNguoiDungHienTai + " " + nextState.getTenVietTat() + " ";
				Long coQuanQuanLyId = new Long(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
				if (FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO.equals(nextStateType)) {
					GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
					giaiQuyetDonTiepTheo = truongPhongGiaoViecChuyenVien(giaiQuyetDonHienTai, giaiQuyetDon, coQuanQuanLyId, congChucId, note);
					return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
				} else if (FlowStateEnum.CAN_BO_DE_XUAT_GIAO_VIEC_LAI.equals(nextStateType)) {
					GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
					giaiQuyetDonTiepTheo = chuyenVienDeXuatGiaoViecLai(giaiQuyetDonHienTai, giaiQuyetDon, coQuanQuanLyId, congChucId, note);
					return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
				} else if (FlowStateEnum.CAN_BO_CHUYEN_VAN_THU_GIAO_TTXM.equals(nextStateType)) {
					GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
					giaiQuyetDonTiepTheo = chuyenVienChuyenVanThuGiaoTTXM(giaiQuyetDonHienTai, giaiQuyetDon, coQuanQuanLyId, congChucId, note);
					return Utils.doSave(repo, giaiQuyetDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
				} else if (FlowStateEnum.CAN_BO_GIAI_QUYET_DON.equals(nextStateType)) {
					
				} else if (FlowStateEnum.VAN_THU_CHUYEN_DON_VI_TTXM.equals(nextStateType)) {
					
				}
			} 
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/giaiQuyetDons/{id}/dinhChi")
	@ApiOperation(value = "Đình chỉ đơn giải quyết", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Đình chỉ đơn giải quyết thành công") })
	public ResponseEntity<Object> dinhChiDonGiaiQuyet(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {
		
		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_DINHCHI) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		
//		SoTiepCongDan soTiepCongDan = soTiepCongDanService.cancelCuocTiepDanDinhKyCuaLanhDao(repo, id);
//		if (soTiepCongDan == null) {
//			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
//		}
//
//		Utils.save(repo, soTiepCongDan, new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	private GiaiQuyetDon truongPhongGiaoViecChuyenVien(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long coQuanQuanLyId, Long congChucId, String note) {
		Long donId = giaiQuyetDonHienTai.getThongTinGiaiQuyetDon().getDon().getId();
		giaiQuyetDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		giaiQuyetDonHienTai.setCanBoXuLyChiDinh(giaiQuyetDon.getCanBoXuLyChiDinh());
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDonHienTai.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
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
	
	private GiaiQuyetDon chuyenVienDeXuatGiaoViecLai(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long coQuanQuanLyId, Long congChucId, String note) {
		Long donId = giaiQuyetDon.getThongTinGiaiQuyetDon().getDon().getId();
		giaiQuyetDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		giaiQuyetDonHienTai.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
		
		Don don = donRepo.findOne(donId);
		don.setCurrentState(giaiQuyetDon.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(repo, giaiQuyetDonHienTai, congChucId);
		return giaiQuyetDonTiepTheo;
	}
	
	private GiaiQuyetDon chuyenVienChuyenVanThuGiaoTTXM(GiaiQuyetDon giaiQuyetDonHienTai, GiaiQuyetDon giaiQuyetDon, Long coQuanQuanLyId, Long congChucId, String note) {
		Long donId = giaiQuyetDon.getThongTinGiaiQuyetDon().getDon().getId();
		giaiQuyetDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		giaiQuyetDonHienTai.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DA_GIAI_QUYET);
		
		
		GiaiQuyetDon giaiQuyetDonTiepTheo = new GiaiQuyetDon();
		giaiQuyetDonTiepTheo.setThongTinGiaiQuyetDon(giaiQuyetDon.getThongTinGiaiQuyetDon());
		giaiQuyetDonTiepTheo.setyKienGiaiQuyet(giaiQuyetDon.getyKienGiaiQuyet());
		giaiQuyetDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		giaiQuyetDonTiepTheo.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDonTiepTheo.setThuTuThucHien(giaiQuyetDonHienTai.getThuTuThucHien() + 1);
		
		Don don = donRepo.findOne(donId);
		don.setCurrentState(giaiQuyetDon.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(repo, giaiQuyetDonHienTai, congChucId);
		return giaiQuyetDonTiepTheo;
	}
}
