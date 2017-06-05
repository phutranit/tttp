package vn.greenglobal.tttp.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.FlowStateEnum;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.enums.TinhTrangGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.model.VaiTro;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.ThongTinGiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.ProcessRepository;
import vn.greenglobal.tttp.repository.StateRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.service.ProcessService;
import vn.greenglobal.tttp.service.StateService;
import vn.greenglobal.tttp.service.ThongTinGiaiQuyetDonService;
import vn.greenglobal.tttp.service.TransitionService;
import vn.greenglobal.tttp.service.XuLyDonService;
import vn.greenglobal.tttp.util.Utils;
import vn.greenglobal.tttp.util.WordUtil;

@RestController
@RepositoryRestController
@Api(value = "xuLyDons", description = "Xử lý đơn")
public class XuLyDonController extends TttpController<XuLyDon> {

	private static XuLyDonService xuLyDonService = new XuLyDonService();
	private static DonService donService = new DonService();

	@Autowired
	private XuLyDonRepository repo;

	@Autowired
	private DonRepository donRepo;
	
	@Autowired
	private ThongTinGiaiQuyetDonRepository thongTinGiaiQuyetDonRepo;
	
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

	public XuLyDonController(BaseRepository<XuLyDon, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/xuLyDons")
	@ApiOperation(value = "Quy trình xử lý đơn", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Thêm quy trình xử lý đơn thành công", response = XuLyDon.class) })
	public ResponseEntity<Object> createWorkflow(
			@RequestHeader(value = "Authorization", required = true) String authorization, @RequestBody XuLyDon xuLyDon,
			PersistentEntityResourceAssembler eass) {
		NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA);
		CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
		if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
				&& commonProfile.containsAttribute("coQuanQuanLyId")) {
			if (xuLyDon.getDon() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DON_REQUIRED.name(),
						ApiErrorEnum.DON_REQUIRED.getText());
			}
			if (xuLyDon.getNextState() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.NEXT_STATE_REQUIRED.name(),
						ApiErrorEnum.NEXT_STATE_REQUIRED.getText());
			}
			State nextStage = repoState.findOne(stateService.predicateFindOne(xuLyDon.getNextState().getId()));
			Long donId = xuLyDon.getDon().getId();
			Don don = donRepo.findOne(donService.predicateFindOne(donId));
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
			Process process = repoProcess.findOne(
					processService.predicateFindAll(vaiTroNguoiDungHienTai, donVi, isOwner, don.getProcessType()));

			if (process == null && isOwner) {
				process = repoProcess.findOne(
						processService.predicateFindAll(vaiTroNguoiDungHienTai, donVi, false, don.getProcessType()));
			}

			if (process == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_NOT_FOUND.name(),
						ApiErrorEnum.PROCESS_NOT_FOUND.getText());
			}

			Transition transition = transitionRepo.findOne(
					transitionService.predicatePrivileged(don.getCurrentState(), xuLyDon.getNextState(), process));

			if (transition == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_FORBIDDEN.name(),
						ApiErrorEnum.TRANSITION_FORBIDDEN.getText());
			}

			XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(repo, donId);

			if (xuLyDonHienTai != null) {
				FlowStateEnum currentState = don.getCurrentState() != null ? don.getCurrentState().getType() : null;
				FlowStateEnum nextState = nextStage.getType();
				xuLyDonHienTai.setNextState(xuLyDon.getNextState());
				xuLyDonHienTai.setNextForm(transition.getForm());
				// Thong tin xu ly don
				String note = vaiTroNguoiDungHienTai + " " + nextStage.getTenVietTat() + " ";
				Long coQuanQuanLyId = new Long(
						profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());				
				Long donViId = new Long(
						profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
				
				if (FlowStateEnum.TRINH_LANH_DAO.equals(nextState)) {

					XuLyDon xuLyDonTiepTheo = new XuLyDon();
					xuLyDonTiepTheo = trinhDon(xuLyDon, xuLyDonHienTai, coQuanQuanLyId, congChucId, note);
					return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);

				} else if (FlowStateEnum.LANH_DAO_GIAO_VIEC_TRUONG_PHONG.equals(nextState)
						|| FlowStateEnum.LANH_DAO_GIAO_VIEC_CAN_BO.equals(nextState)) {

					XuLyDon xuLyDonTiepTheo = new XuLyDon();
					xuLyDonTiepTheo = lanhDaoGiaoViec(xuLyDon, xuLyDonHienTai, donViId, congChucId, note);
					return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);

				} else if (FlowStateEnum.TRUONG_PHONG_DE_XUAT_GIAO_VIEC_LAI.equals(nextState)) {

					XuLyDon xuLyDonTiepTheo = new XuLyDon();
					xuLyDonTiepTheo = truongPhongGiaoViecLai(xuLyDon, xuLyDonHienTai, congChucId, note);
					return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);

				} else if (FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO.equals(nextState)) {

					XuLyDon xuLyDonTiepTheo = new XuLyDon();
					xuLyDonTiepTheo = truongPhongGiaoViec(xuLyDon, xuLyDonHienTai, congChucId, note);
					return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);

				} else if (FlowStateEnum.CAN_BO_DE_XUAT_GIAO_VIEC_LAI.equals(nextState)) {

					XuLyDon xuLyDonTiepTheo = new XuLyDon();
					xuLyDonTiepTheo = chuyenVienGiaoViecLai(xuLyDon, xuLyDonHienTai, congChucId, note);
					return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);

				} else if (FlowStateEnum.CAN_BO_DE_XUAT_HUONG_XU_LY.equals(nextState)) {

					HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy();
					// huongXuLy
					if (huongXuLyXLD == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.HUONGXULY_REQUIRED.name(),
								ApiErrorEnum.HUONGXULY_REQUIRED.getText());
					}
					note = note + huongXuLyXLD.getText().toLowerCase().trim() + " ";
					xuLyDonHienTai.setHuongXuLy(huongXuLyXLD);
					xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
					xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
					if (HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO.equals(huongXuLyXLD)) {
						xuLyDonHienTai = chuyenVienChuyenChoVanThuYeuCauGapLanhDao(xuLyDon, xuLyDonHienTai, congChucId, note);
						return Utils.doSave(repo, xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.DE_XUAT_THU_LY.equals(huongXuLyXLD)) {
						if (xuLyDon.getPhongBanGiaiQuyet() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.name(),
									ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText());
						}
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = chuyenVienChuyenChoVanThuDeXuatThuLy(xuLyDon, xuLyDonHienTai, congChucId, note);
						return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.CHUYEN_DON.equals(huongXuLyXLD)) {
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = chuyenVienChuyenDonChoVanThu(xuLyDon, xuLyDonHienTai, congChucId, note);
						return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.equals(huongXuLyXLD)
							|| HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.equals(huongXuLyXLD)
							|| HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.equals(huongXuLyXLD)) {
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = chuyenVienXuLyKhongDuDieuKienThuLy(xuLyDon, xuLyDonHienTai, congChucId, note);
						return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.equals(huongXuLyXLD)
							&& xuLyDonHienTai.isDonChuyen()) {
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = chuyenVienTraLaiDonKhongDungThamQuyenChoVanThu(xuLyDon, xuLyDonHienTai, congChucId,
								note);
						return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					}
				} else if (FlowStateEnum.CAN_BO_DE_XUAT_HUONG_XU_LY.equals(currentState)
						&& FlowStateEnum.KET_THUC.equals(nextState)) {
					// Xu ly don co van thu
					HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy();
					if (huongXuLyXLD == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.HUONGXULY_REQUIRED.name(),
								ApiErrorEnum.HUONGXULY_REQUIRED.getText());
					}
					if (HuongXuLyXLDEnum.CHUYEN_DON.equals(huongXuLyXLD)) {
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = vanThuChuyenDon(xuLyDon, xuLyDonHienTai, congChucId);
						return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.equals(huongXuLyXLD)
							&& xuLyDonHienTai.isDonChuyen()) {
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = vanThuTraLaiDonKhongDungThamQuyen(xuLyDon, xuLyDonHienTai, congChucId);
						return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.DE_XUAT_THU_LY.equals(huongXuLyXLD)) {
						xuLyDonHienTai = deXuatThuLy(xuLyDon, xuLyDonHienTai, congChucId);
						return Utils.doSave(repo, xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.equals(huongXuLyXLD)
							|| HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.equals(huongXuLyXLD)
							|| HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.equals(huongXuLyXLD)) {
						xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
						xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						don.setHuongXuLyXLD(huongXuLyXLD);
						don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
						don.setTrangThaiDon(TrangThaiDonEnum.DINH_CHI);
						don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
						// set ngay ket thuc cho don
						don.setNgayKetThucXLD(LocalDateTime.now());
						Utils.save(donRepo, don, congChucId);
						return Utils.doSave(repo, xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
					}
				} else if ((FlowStateEnum.TRUONG_PHONG_GIAO_VIEC_CAN_BO.equals(currentState) || FlowStateEnum.LANH_DAO_GIAO_VIEC_CAN_BO.equals(currentState))
						&& FlowStateEnum.KET_THUC.equals(nextState)) {
					// Xu ly don khong co van thu
					HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy();
					if (huongXuLyXLD == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.HUONGXULY_REQUIRED.name(),
								ApiErrorEnum.HUONGXULY_REQUIRED.getText());
					}
					note = note + huongXuLyXLD.getText().toLowerCase().trim() + " ";
					xuLyDonHienTai.setHuongXuLy(huongXuLyXLD);
					xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
					if (HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO.equals(huongXuLyXLD)) {
						xuLyDonHienTai = chuyenVienChuyenChoVanThuYeuCauGapLanhDao(xuLyDon, xuLyDonHienTai, congChucId, note);
						return Utils.doSave(repo, xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.DE_XUAT_THU_LY.equals(huongXuLyXLD)) {
						if (xuLyDon.getPhongBanGiaiQuyet() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.name(),
									ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText());
						}
						xuLyDonHienTai = chuyenVienDeXuatThuLy(xuLyDon, xuLyDonHienTai, congChucId, note);
						return Utils.doSave(repo, xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.CHUYEN_DON.equals(huongXuLyXLD)) {
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = chuyenVienChuyenDon(xuLyDon, xuLyDonHienTai, congChucId);
						return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY.equals(huongXuLyXLD)
							|| HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN.equals(huongXuLyXLD)
							|| HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI.equals(huongXuLyXLD)) {
						xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
						xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						don.setHuongXuLyXLD(huongXuLyXLD);
						don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
						don.setHuongXuLyXLD(HuongXuLyXLDEnum.DINH_CHI);
						don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
						// set ngay ket thuc cho don
						don.setNgayKetThucXLD(LocalDateTime.now());
						Utils.save(donRepo, don, congChucId);
						return Utils.doSave(repo, xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
					} else if (HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN.equals(huongXuLyXLD)
							&& xuLyDonHienTai.isDonChuyen()) {
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonTiepTheo = chuyenVienTraLaiDonKhongDungThamQuyen(xuLyDon, xuLyDonHienTai, congChucId, note);
						return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					}
				} else {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_INVALID.name(),
							ApiErrorEnum.DATA_INVALID.getText());
				}
			}
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/xuLyDons/dinhChiDon")
	@ApiOperation(value = "Đình chỉ đơn	", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Đình chỉ đơn thành công", response = XuLyDon.class) })
	public ResponseEntity<Object> dinhChiDon(
			@RequestHeader(value = "Authorization", required = true) String authorization, @RequestParam Long id,
			@RequestBody XuLyDon xuLyDon, PersistentEntityResourceAssembler eass) {

		NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA);
		CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
		if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
				&& commonProfile.containsAttribute("coQuanQuanLyId")) {

			Long donId = xuLyDon.getDon().getId();
			XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(repo, donId);
			// Xac dinh vai tro cua nguoi dung (Nhieu vai tro cho 1 nguoi)
			VaiTro vaiTro = nguoiDungHienTai.getVaiTros().iterator().next();

			// Thay alias
			String vaiTroNguoiDungHienTai = vaiTro.getLoaiVaiTro().name();

			// Thong tin xu ly don
			Long congChucId = new Long(
					profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());

			State nextStage = xuLyDon.getNextState();
			String note = vaiTroNguoiDungHienTai + " " + nextStage.getTenVietTat() + " ";
			if (xuLyDonHienTai != null) {
				if (StringUtils.equals(vaiTroNguoiDungHienTai, VaiTroEnum.VAN_THU.name())
						|| StringUtils.equals(vaiTroNguoiDungHienTai, VaiTroEnum.LANH_DAO.name())
						|| StringUtils.equals(vaiTroNguoiDungHienTai, VaiTroEnum.CHUYEN_VIEN.name())
						|| StringUtils.equals(vaiTroNguoiDungHienTai, VaiTroEnum.TRUONG_PHONG.name())) {
					xuLyDonHienTai.setGhiChu(note);
					xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
					xuLyDonHienTai.setNgayQuyetDinhDinhChi(xuLyDon.getNgayQuyetDinhDinhChi());
					xuLyDonHienTai.setSoQuyetDinhDinhChi(xuLyDon.getSoQuyetDinhDinhChi());
					xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
					Don don = donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
					don.setHuongXuLyXLD(HuongXuLyXLDEnum.DINH_CHI);
					don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
					don.setLyDoDinhChi(xuLyDon.getyKienXuLy());
					don.setNgayQuyetDinhDinhChi(xuLyDon.getNgayQuyetDinhDinhChi());
					don.setSoQuyetDinhDinhChi(xuLyDon.getSoQuyetDinhDinhChi());
					don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));

					// set ngay ket thuc xu ly don cho don
					don.setNgayKetThucXLD(LocalDateTime.now());

					Utils.save(donRepo, don, congChucId);
					return Utils.doSave(repo, xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
				}
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuDeXuatThuLy")
	@ApiOperation(value = "In phiếu đề xuất thụ lý", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordPhieuDeXuatThuLy(@RequestParam(value = "loaiDon", required = true) String loaiDon,
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "nguoiDungDon", required = true) String nguoiDungDon,
			@RequestParam(value = "noiDung", required = true) String noiDung,
			@RequestParam(value = "diaChi", required = false) String diaChi, HttpServletResponse response) {
		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("loaiDonTieuDe", loaiDon.toUpperCase());
		mappings.put("loaiDon", loaiDon.toLowerCase());
		mappings.put("ngayTiepNhan", ngayTiepNhan);
		mappings.put("nguoiDungDon", nguoiDungDon);
		mappings.put("diaChi", diaChi);
		mappings.put("noiDung", noiDung);
		WordUtil.exportWord(response,
				getClass().getClassLoader().getResource("word/xulydon/XLD_PHIEU_DE_XUAT_THU_LY.docx").getFile(),
				mappings);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuKhongDuDieuKienThuLyKhieuNai")
	@ApiOperation(value = "In phiếu không đủ điều kiện thụ lý khiếu nại", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordPhieuKhongDuDieuKienThuLy(
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "nguoiDungDon", required = true) String nguoiDungDon,
			@RequestParam(value = "diaChiNguoiDungDon", required = false) String diaChiNguoiDungDon,
			@RequestParam(value = "noiDung", required = false) String noiDung,
			@RequestParam(value = "lyDoDinhChi", required = false) String lyDoDinhChi, HttpServletResponse response) {

		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("ngayTiepNhan", ngayTiepNhan);
		mappings.put("nguoiDungDon", nguoiDungDon);
		mappings.put("diaChiNguoiDungDon", diaChiNguoiDungDon);
		mappings.put("noiDung", noiDung);
		mappings.put("lyDoDinhChi", lyDoDinhChi);
		WordUtil.exportWord(response, getClass().getClassLoader()
				.getResource("word/xulydon/XLD_PHIEU_KHONG_DU_DIEU_KIEN_THU_LY.docx").getFile(), mappings);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuTraDonVaHuongDanKhieuNai")
	@ApiOperation(value = "In phiếu trả đơn và hướng dẫn khiếu nại", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordKhieuNaiTraDonVaHuongDan(
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "nguoiDungDon", required = true) String nguoiDungDon,
			@RequestParam(value = "noiDung", required = false) String noiDung,
			@RequestParam(value = "coQuanTiepNhan", required = false) String coQuanTiepNhan,
			HttpServletResponse response) {

		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("ngayTiepNhan", ngayTiepNhan);
		mappings.put("nguoiDungDon", nguoiDungDon);
		mappings.put("noiDung", noiDung);
		mappings.put("coQuanTiepNhan", coQuanTiepNhan);
		WordUtil.exportWord(response, getClass().getClassLoader()
				.getResource("word/xulydon/khieunai/XLD_PHIEU_TRA_DON_VA_HUONG_DAN_DON_KHIEU_NAI.docx").getFile(),
				mappings);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuChuyenDonKienNghiPhanAnh")
	@ApiOperation(value = "In phiếu chuyển đơn kiến nghị phản ánh", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordChuyenDonKienNghiPhanAnh(
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "nguoiDungDon", required = true) String nguoiDungDon,
			@RequestParam(value = "diaChiNguoiDungDon", required = false) String diaChiNguoiDungDon,
			@RequestParam(value = "noiDung", required = false) String noiDung,
			@RequestParam(value = "coQuanTiepNhan", required = false) String coQuanTiepNhan,
			HttpServletResponse response) {

		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("ngayTiepNhan", ngayTiepNhan);
		mappings.put("nguoiDungDon", nguoiDungDon);
		mappings.put("diaChiNguoiDungDon", diaChiNguoiDungDon);
		mappings.put("noiDung", noiDung);
		mappings.put("coQuanTiepNhan", coQuanTiepNhan);
		WordUtil.exportWord(response, getClass().getClassLoader()
				.getResource("word/xulydon/kiennghiphananh/XLD_PHIEU_CHUYEN_DON_KIEN_NGHI_PHAN_ANH.docx").getFile(),
				mappings);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuChuyenDonToCao")
	@ApiOperation(value = "In phiếu chuyển đơn tố cáo", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordChuyenDonToCao(@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "nguoiDungDon", required = true) String nguoiDungDon,
			@RequestParam(value = "diaChiNguoiDungDon", required = false) String diaChiNguoiDungDon,
			@RequestParam(value = "noiDung", required = false) String noiDung,
			@RequestParam(value = "coQuanTiepNhan", required = false) String coQuanTiepNhan,
			HttpServletResponse response) {

		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("ngayTiepNhan", ngayTiepNhan);
		mappings.put("nguoiDungDon", nguoiDungDon);
		mappings.put("diaChiNguoiDungDon", diaChiNguoiDungDon);
		mappings.put("noiDung", noiDung);
		mappings.put("coQuanTiepNhan", coQuanTiepNhan);
		WordUtil.exportWord(response, getClass().getClassLoader()
				.getResource("word/xulydon/tocao/XLD_PHIEU_CHUYEN_DON_TO_CAO.docx").getFile(), mappings);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuDuThaoThongBaoThuLyGQTC")
	@ApiOperation(value = "In phiếu dự thảo thông báo thụ lý giải quyết tố cáo", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordDuThaoThongBaoThuLyGQTC(
			@RequestParam(value = "doiTuongGiaiQuyet", required = false) String doiTuongGiaiQuyet,
			@RequestParam(value = "nguoiDungDon", required = true) String nguoiDungDon,
			@RequestParam(value = "doiTuongBiToCao", required = false) String doiTuongBiToCao,
			@RequestParam(value = "noiDungToCao", required = false) String noiDungToCao, HttpServletResponse response) {

		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("doiTuongGiaiQuyet", doiTuongGiaiQuyet);
		mappings.put("nguoiDungDon", nguoiDungDon);
		mappings.put("doiTuongBiToCao", doiTuongBiToCao);

		mappings.put("noiDungToCao", noiDungToCao);
		WordUtil.exportWord(response,
				getClass().getClassLoader()
						.getResource("word/xulydon/tocao/XLD_PHIEU_DU_THAO_THONG_BAO_THU_LY_GQTC.docx").getFile(),
				mappings);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuDuThaoThongBaoThuLyKhieuNai")
	@ApiOperation(value = "In phiếu dự thảo thông báo thụ lý giải quyết khiếu nại", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordDuThaoThongBaoThuLyKhieuNai(
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "nguoiKhieuNai", required = true) String nguoiKhieuNai,
			@RequestParam(value = "diaChiNguoiKhieuNai", required = false) String diaChiNguoiKhieuNai,
			@RequestParam(value = "SoCMNDHoChieu", required = false) String SoCMNDHoChieu,
			@RequestParam(value = "ngayCap", required = false) String ngayCap,
			@RequestParam(value = "noiCap", required = false) String noiCap,
			@RequestParam(value = "noiDungKhieuNai", required = false) String noiDungKhieuNai,
			HttpServletResponse response) {

		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("ngayTiepNhan", ngayTiepNhan);
		mappings.put("nguoiKhieuNai", nguoiKhieuNai);
		mappings.put("diaChiNguoiKhieuNai", diaChiNguoiKhieuNai);
		mappings.put("SoCMNDHoChieu", StringUtils.isNotBlank(SoCMNDHoChieu) ? SoCMNDHoChieu : ".................");
		mappings.put("ngayCap", StringUtils.isNotBlank(ngayCap) ? ngayCap : ".................");
		mappings.put("noiCap", StringUtils.isNotBlank(noiCap) ? noiCap : ".................");
		mappings.put("noiDungKhieuNai", noiDungKhieuNai);
		WordUtil.exportWord(response, getClass().getClassLoader()
				.getResource("word/xulydon/khieunai/XLD_PHIEU_DU_THAO_THONG_BAO_THU_LY_KHIEU_NAI.docx").getFile(),
				mappings);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/xuLyDons/inPhieuDuThaoThongBaoThuLyKienNghi")
	@ApiOperation(value = "In phiếu dự thảo thông báo thụ lý giải quyết kiến nghị", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWordDuThaoThongBaoThuLyKienNghi(@RequestParam(value = "loaiDon", required = true) String loaiDon,
			@RequestParam(value = "ngayTiepNhan", required = true) String ngayTiepNhan,
			@RequestParam(value = "nguoiDungDon", required = true) String nguoiDungDon,
			@RequestParam(value = "noiDung", required = true) String noiDung,
			@RequestParam(value = "diaChi", required = false) String diaChi, HttpServletResponse response) {
		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("loaiDonTieuDe", loaiDon.toUpperCase());
		mappings.put("loaiDon", loaiDon.toLowerCase());
		mappings.put("ngayTiepNhan", ngayTiepNhan);
		mappings.put("nguoiDungDon", nguoiDungDon);
		mappings.put("noiDung", noiDung);
		mappings.put("diaChi", diaChi);
		WordUtil.exportWord(response,
				getClass().getClassLoader()
						.getResource("word/xulydon/kiennghiphananh/XLD_PHIEU_DU_THAO_THONG_BAO_THU_LY_KIEN_NGHI.docx")
						.getFile(),
				mappings);
	}

	/*
	 * 
	 * @RequestMapping(method = RequestMethod.POST, value = "/xuLyDons2")
	 * 
	 * @ApiOperation(value = "Quy trình xử lý đơn", position = 1, produces =
	 * MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @ApiResponses(value = {
	 * 
	 * @ApiResponse(code = 202, message = "Thêm quy trình xử lý đơn thành công",
	 * response = XuLyDon.class) }) public ResponseEntity<Object>
	 * create(@RequestHeader(value = "Authorization", required = true) String
	 * authorization,
	 * 
	 * @RequestBody XuLyDon xuLyDon, PersistentEntityResourceAssembler eass) {
	 * 
	 * NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil,
	 * authorization, QuyenEnum.DON_SUA); CommonProfile commonProfile =
	 * profileUtil.getCommonProfile(authorization); if (nguoiDungHienTai != null
	 * && commonProfile.containsAttribute("congChucId") &&
	 * commonProfile.containsAttribute("coQuanQuanLyId")) {
	 * 
	 * Long donId = xuLyDon.getDon().getId(); XuLyDon xuLyDonHienTai =
	 * xuLyDonService.predFindCurrent(repo, donId); if (xuLyDonHienTai != null)
	 * {
	 * 
	 * // Xac dinh vai tro cua nguoi dung (Nhieu vai tro cho 1 nguoi) VaiTro
	 * vaiTro = nguoiDungHienTai.getVaiTros().iterator().next();
	 * 
	 * // Thay alias String vaiTroNguoiDungHienTai =
	 * vaiTro.getLoaiVaiTro().name();
	 * 
	 * // Thong tin xu ly don QuyTrinhXuLyDonEnum quyTrinhXuLy =
	 * xuLyDon.getQuyTrinhXuLy(); String note = vaiTroNguoiDungHienTai + " " +
	 * quyTrinhXuLy.getText().toLowerCase() + " "; Long congChucId = new Long(
	 * profileUtil.getCommonProfile(authorization).getAttribute("congChucId").
	 * toString()); Long coQuanQuanLyId = new Long(
	 * profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId"
	 * ).toString());
	 * 
	 * if (StringUtils.equals(vaiTroNguoiDungHienTai,
	 * VaiTroEnum.VAN_THU.name())) {
	 * 
	 * // vai tro VAN_THU // set thong tin nguoi thuc hien va quy trinh xu ly
	 * HuongXuLyXLDEnum huongXuLyXLD = xuLyDonHienTai.getHuongXuLy();
	 * xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
	 * xuLyDonHienTai.setQuyTrinhXuLy(quyTrinhXuLy); if
	 * (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.TRINH_LANH_DAO)) {
	 * 
	 * XuLyDon xuLyDonTiepTheo = new XuLyDon(); note = note +
	 * VaiTroEnum.LANH_DAO.getText().toLowerCase() + " " +
	 * coQuanQuanLyRepo.findOne(coQuanQuanLyId).getTen().toLowerCase().trim() +
	 * " "; xuLyDonHienTai.setNoiDungThongTinTrinhLanhDao(xuLyDon.
	 * getNoiDungThongTinTrinhLanhDao());
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
	 * xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
	 * xuLyDonHienTai.setThoiHanXuLy(Utils.convertNumberToLocalDateTime(
	 * xuLyDonHienTai.getDon().getNgayTiepNhan(), xuLyDon.getSoNgayXuLy()));
	 * xuLyDonTiepTheo.setThoiHanXuLy(Utils.convertNumberToLocalDateTime(
	 * xuLyDonHienTai.getDon().getNgayTiepNhan(), xuLyDon.getSoNgayXuLy()));
	 * xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
	 * xuLyDonTiepTheo.setChucVu(VaiTroEnum.LANH_DAO);
	 * xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
	 * xuLyDonTiepTheo.setNoiDungThongTinTrinhLanhDao(xuLyDon.
	 * getNoiDungThongTinTrinhLanhDao());
	 * xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
	 * // xuLyDonTiepTheo.setThoiHanXuLy(); if (xuLyDonHienTai.isDonChuyen()) {
	 * 
	 * note = note + "đơn chuyển từ " +
	 * xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
	 * xuLyDonTiepTheo.setDonChuyen(true);
	 * xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
	 * } xuLyDonHienTai.setGhiChu(note); //set don Don don =
	 * donRepo.findOne(donId);
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * Utils.save(repo, xuLyDonHienTai, congChucId); Utils.save(donRepo, don,
	 * congChucId); return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass,
	 * HttpStatus.CREATED); } // else if
	 * (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.DINH_CHI)) { // //
	 * xuLyDonHienTai.setGhiChu(note); //
	 * xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy()); //
	 * xuLyDonHienTai.setNgayQuyetDinhDinhChi(xuLyDon.getNgayQuyetDinhDinhChi())
	 * ; //
	 * xuLyDonHienTai.setSoQuyetDinhDinhChi(xuLyDon.getSoQuyetDinhDinhChi()); //
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY); // Don don =
	 * donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
	 * // don.setTrangThaiDon(TrangThaiDonEnum.DINH_CHI); //
	 * don.setNgayQuyetDinhDinhChi(xuLyDon.getNgayQuyetDinhDinhChi()); //
	 * don.setSoQuyetDinhDinhChi(xuLyDon.getSoQuyetDinhDinhChi()); //
	 * Utils.save(donRepo, don, congChucId); // return Utils.doSave(repo,
	 * xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED); } else if
	 * (huongXuLyXLD.equals(HuongXuLyXLDEnum.CHUYEN_DON)) {
	 * 
	 * XuLyDon xuLyDonTiepTheo = new XuLyDon();
	 * xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY); Don don =
	 * donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId
	 * ())); don.setHuongXuLyXLD(huongXuLyXLD);
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * 
	 * xuLyDonTiepTheo.setDon(don);
	 * xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getCoQuanTiepNhan());
	 * xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
	 * xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
	 * xuLyDonTiepTheo.setDaXoa(true);
	 * xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getPhongBanXuLy());
	 * don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
	 * Utils.save(donRepo, don, congChucId); Utils.save(repo, xuLyDonHienTai,
	 * congChucId); return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass,
	 * HttpStatus.CREATED); } else if
	 * (huongXuLyXLD.equals(HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN)
	 * && xuLyDonHienTai.isDonChuyen()) { //van thu tra don XuLyDon
	 * xuLyDonTiepTheo = new XuLyDon(); Don don =
	 * donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId
	 * ())); don.setHuongXuLyXLD(huongXuLyXLD);
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * 
	 * xuLyDonTiepTheo.setDon(don);
	 * xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getCoQuanChuyenDon());
	 * xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
	 * xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
	 * xuLyDonTiepTheo.setDaXoa(true);
	 * xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getPhongBanXuLy());
	 * don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * Utils.save(donRepo, don, congChucId); Utils.save(repo, xuLyDonHienTai,
	 * congChucId); return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass,
	 * HttpStatus.CREATED); }
	 * 
	 * //Hoan thanh else if
	 * (huongXuLyXLD.equals(HuongXuLyXLDEnum.DE_XUAT_THU_LY)) {
	 * xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
	 * 
	 * Don don =
	 * donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId
	 * ())); don.setHuongXuLyXLD(huongXuLyXLD);
	 * don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
	 * don.setPhongBanGiaiQuyet(xuLyDonHienTai.getPhongBanGiaiQuyet());
	 * don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * 
	 * Utils.save(donRepo, don, congChucId); return Utils.doSave(repo,
	 * xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED); } else if
	 * (huongXuLyXLD.equals(HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY) ||
	 * huongXuLyXLD.equals(HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI) ||
	 * huongXuLyXLD.equals(HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN)) {
	 * xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
	 * 
	 * Don don =
	 * donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId
	 * ())); don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
	 * don.setTrangThaiDon(TrangThaiDonEnum.DINH_CHI);
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * 
	 * Utils.save(donRepo, don, congChucId); return Utils.doSave(repo,
	 * xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED); } } else if
	 * (StringUtils.equals(vaiTroNguoiDungHienTai,
	 * VaiTroEnum.CHUYEN_VIEN.name())) {
	 * 
	 * // vai tro CHUYEN_VIEN
	 * xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
	 * xuLyDonHienTai.setQuyTrinhXuLy(quyTrinhXuLy); if
	 * (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI)) {
	 * 
	 * XuLyDon xuLyDonTiepTheo = new XuLyDon(); note = note +
	 * VaiTroEnum.TRUONG_PHONG.getText().toLowerCase().trim() + " " +
	 * xuLyDonHienTai.getPhongBanXuLy().getTen().trim() + " ";
	 * xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
	 * xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
	 * xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
	 * xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
	 * xuLyDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
	 * xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
	 * xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
	 * xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
	 * xuLyDonTiepTheo.setCanBoXuLyChiDinh(xuLyDonHienTai.getCongChuc()); if
	 * (xuLyDonHienTai.isDonChuyen()) {
	 * 
	 * note = note + "đơn chuyển từ " +
	 * xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
	 * xuLyDonTiepTheo.setDonChuyen(true);
	 * xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
	 * } xuLyDonHienTai.setGhiChu(note); //set don Don don =
	 * donRepo.findOne(donId);
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * 
	 * Utils.save(repo, xuLyDonHienTai, congChucId); Utils.save(donRepo, don,
	 * congChucId); return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass,
	 * HttpStatus.CREATED); } else if
	 * (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.CHUYEN_CHO_VAN_THU)) {
	 * 
	 * HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy(); // huongXuLy note
	 * = note + huongXuLyXLD.getText().toLowerCase().trim() + " ";
	 * xuLyDonHienTai.setHuongXuLy(huongXuLyXLD);
	 * xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy()); if
	 * (huongXuLyXLD.equals(HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO)) {
	 * 
	 * CoQuanQuanLy coQuanQuanLy = xuLyDonHienTai.getPhongBanXuLy().getCha();
	 * note = note + VaiTroEnum.LANH_DAO.getText() +
	 * coQuanQuanLy.getTen().toLowerCase().trim();
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
	 * xuLyDonHienTai.setNgayHenGapLanhDao(xuLyDon.getNgayHenGapLanhDao());
	 * xuLyDonHienTai.setDiaDiem(xuLyDon.getDiaDiem());
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY); Don don =
	 * donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
	 * don.setNgayLapDonGapLanhDaoTmp(xuLyDon.getNgayHenGapLanhDao());
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * 
	 * xuLyDonHienTai.setGhiChu(note); Utils.save(donRepo, don, congChucId);
	 * return Utils.doSave(repo, xuLyDonHienTai, congChucId, eass,
	 * HttpStatus.CREATED); } else if
	 * (huongXuLyXLD.equals(HuongXuLyXLDEnum.DE_XUAT_THU_LY)) {
	 * 
	 * XuLyDon xuLyDonTiepTheo = new XuLyDon(); note = note +
	 * VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " " +
	 * xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
	 * xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
	 * xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
	 * xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
	 * xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
	 * xuLyDonTiepTheo.setHuongXuLy(huongXuLyXLD);
	 * xuLyDonTiepTheo.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
	 * xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
	 * xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
	 * xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
	 * xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
	 * xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
	 * if (xuLyDonHienTai.isDonChuyen()) {
	 * 
	 * note = note + "đơn chuyển từ " +
	 * xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
	 * xuLyDonTiepTheo.setDonChuyen(true);
	 * xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
	 * } xuLyDonHienTai.setGhiChu(note); //set don Don don =
	 * donRepo.findOne(donId);
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * 
	 * Utils.save(repo, xuLyDonHienTai, congChucId); Utils.save(donRepo, don,
	 * congChucId); return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass,
	 * HttpStatus.CREATED); } else if
	 * (huongXuLyXLD.equals(HuongXuLyXLDEnum.CHUYEN_DON)) {
	 * 
	 * XuLyDon xuLyDonTiepTheo = new XuLyDon(); note = note +
	 * VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " " +
	 * xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
	 * xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
	 * xuLyDonHienTai.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
	 * xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
	 * xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
	 * xuLyDonTiepTheo.setHuongXuLy(huongXuLyXLD);
	 * xuLyDonTiepTheo.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
	 * xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
	 * xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
	 * xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
	 * xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
	 * xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
	 * if (xuLyDonHienTai.isDonChuyen()) {
	 * 
	 * note = note + "đơn chuyển từ " +
	 * xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
	 * xuLyDonTiepTheo.setDonChuyen(true);
	 * xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
	 * } xuLyDonHienTai.setGhiChu(note); //set don Don don =
	 * donRepo.findOne(donId);
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * 
	 * Utils.save(repo, xuLyDonHienTai, congChucId); Utils.save(donRepo, don,
	 * congChucId); return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass,
	 * HttpStatus.CREATED); } else if
	 * (huongXuLyXLD.equals(HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY) ||
	 * huongXuLyXLD.equals(HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN) ||
	 * huongXuLyXLD.equals(HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI)) {
	 * 
	 * XuLyDon xuLyDonTiepTheo = new XuLyDon(); note = note +
	 * VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " " +
	 * xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
	 * xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
	 * xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
	 * xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
	 * xuLyDonTiepTheo.setHuongXuLy(huongXuLyXLD);
	 * xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
	 * xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
	 * xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
	 * xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
	 * xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
	 * if (xuLyDonHienTai.isDonChuyen()) {
	 * 
	 * note = note + "đơn chuyển từ " +
	 * xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
	 * xuLyDonTiepTheo.setDonChuyen(true);
	 * xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
	 * } xuLyDonHienTai.setGhiChu(note); //set don Don don =
	 * donRepo.findOne(donId);
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * 
	 * Utils.save(repo, xuLyDonHienTai, congChucId); Utils.save(donRepo, don,
	 * congChucId); return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass,
	 * HttpStatus.CREATED); } else if
	 * (huongXuLyXLD.equals(HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN)
	 * && xuLyDonHienTai.isDonChuyen()) { //van thu tra don XuLyDon
	 * xuLyDonTiepTheo = new XuLyDon(); note = note +
	 * VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " " +
	 * xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim(); //
	 * xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
	 * xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
	 * xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
	 * // xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
	 * xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
	 * xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
	 * xuLyDonTiepTheo.setHuongXuLy(huongXuLyXLD);
	 * xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
	 * xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
	 * xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
	 * 
	 * note = note + "đơn chuyển từ " +
	 * xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
	 * xuLyDonTiepTheo.setDonChuyen(true);
	 * xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
	 * xuLyDonHienTai.setGhiChu(note); //set don Don don =
	 * donRepo.findOne(donId);
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * 
	 * Utils.save(repo, xuLyDonHienTai, congChucId); Utils.save(donRepo, don,
	 * congChucId); return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass,
	 * HttpStatus.CREATED); } } } else if
	 * (StringUtils.equals(vaiTroNguoiDungHienTai,
	 * VaiTroEnum.TRUONG_PHONG.name())) {
	 * 
	 * // vai tro TRUONG_PHONG
	 * xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
	 * xuLyDonHienTai.setQuyTrinhXuLy(quyTrinhXuLy); if
	 * (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI)) {
	 * 
	 * XuLyDon xuLyDonTiepTheo = new XuLyDon(); CoQuanQuanLy coQuanQuanLy =
	 * xuLyDonHienTai.getPhongBanXuLy().getCha(); note = note +
	 * VaiTroEnum.LANH_DAO.getText().toLowerCase().trim() + " " +
	 * coQuanQuanLy.getTen().toLowerCase().trim() + " ";
	 * xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
	 * xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
	 * xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
	 * xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
	 * xuLyDonTiepTheo.setChucVu(VaiTroEnum.LANH_DAO);
	 * xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
	 * xuLyDonTiepTheo.setPhongBanXuLy(coQuanQuanLy);
	 * xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
	 * if (xuLyDonHienTai.isDonChuyen()) {
	 * 
	 * note = note + "đơn chuyển từ " +
	 * xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
	 * xuLyDonTiepTheo.setDonChuyen(true);
	 * xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
	 * } xuLyDonHienTai.setGhiChu(note); //set don Don don =
	 * donRepo.findOne(donId);
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * 
	 * Utils.save(repo, xuLyDonHienTai, congChucId); Utils.save(donRepo, don,
	 * congChucId); return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass,
	 * HttpStatus.CREATED); } else if
	 * (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.GIAO_VIEC)) {
	 * 
	 * XuLyDon xuLyDonTiepTheo = new XuLyDon(); note = note +
	 * VaiTroEnum.CHUYEN_VIEN.getText().toLowerCase().trim() + " " +
	 * xuLyDonHienTai.getPhongBanXuLy().getTen().toLowerCase().trim() + " ";
	 * xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
	 * xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
	 * xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
	 * xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
	 * xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
	 * xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh());
	 * xuLyDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
	 * xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
	 * xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
	 * xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
	 * if (xuLyDonHienTai.isDonChuyen()) {
	 * 
	 * note = note + "đơn chuyển từ " +
	 * xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
	 * xuLyDonTiepTheo.setDonChuyen(true);
	 * xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
	 * } xuLyDonHienTai.setGhiChu(note); //set don Don don =
	 * donRepo.findOne(donId);
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * 
	 * Utils.save(repo, xuLyDonHienTai, congChucId); Utils.save(donRepo, don,
	 * congChucId);
	 * xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
	 * return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass,
	 * HttpStatus.CREATED); } } else if
	 * (StringUtils.equals(vaiTroNguoiDungHienTai, VaiTroEnum.LANH_DAO.name()))
	 * {
	 * 
	 * // vai tro LANH_DAO
	 * xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
	 * xuLyDonHienTai.setQuyTrinhXuLy(quyTrinhXuLy); if
	 * (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.GIAO_VIEC)) {
	 * 
	 * XuLyDon xuLyDonTiepTheo = new XuLyDon();
	 * xuLyDonHienTai.setPhongBanXuLyChiDinh(xuLyDon.getPhongBanXuLyChiDinh());
	 * xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
	 * xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
	 * xuLyDonHienTai.setThoiHanXuLy(Utils.convertNumberToLocalDateTime(
	 * xuLyDonHienTai.getDon().getNgayTiepNhan(), xuLyDon.getSoNgayXuLy()));
	 * xuLyDonTiepTheo.setThoiHanXuLy(Utils.convertNumberToLocalDateTime(
	 * xuLyDonHienTai.getDon().getNgayTiepNhan(), xuLyDon.getSoNgayXuLy())); //
	 * xuLyDonHienTai.setThoiHanXuLy(thoiHanXuLy);
	 * xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
	 * xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLyChiDinh());
	 * xuLyDonTiepTheo.setChucVuGiaoViec(VaiTroEnum.LANH_DAO); //
	 * xuLyDonTiepTheo.setThoiHanXuLy(thoiHanXuLy);
	 * xuLyDonTiepTheo.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
	 * xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
	 * if (xuLyDon.getCanBoXuLyChiDinh() == null) {
	 * 
	 * note = note + xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim() +
	 * " "; xuLyDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG); } else {
	 * 
	 * note = note + xuLyDon.getCanBoXuLyChiDinh().getHoVaTen().trim() + " " +
	 * xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim() + " ";
	 * xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
	 * xuLyDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
	 * xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh()); }
	 * 
	 * if (xuLyDonHienTai.isDonChuyen()) {
	 * 
	 * note = note + "đơn chuyển từ " +
	 * xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
	 * xuLyDonTiepTheo.setDonChuyen(true);
	 * xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
	 * } xuLyDonHienTai.setGhiChu(note); //set don Don don =
	 * donRepo.findOne(donId);
	 * don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
	 * 
	 * Utils.save(repo, xuLyDonHienTai, congChucId); Utils.save(donRepo, don,
	 * congChucId); return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass,
	 * HttpStatus.CREATED); } // else if
	 * (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.DINH_CHI)) { // //
	 * xuLyDonHienTai.setGhiChu(note); //
	 * xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy()); //
	 * xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY); // // so quyet
	 * dinh va ngay quyet dinh ? // Don don =
	 * donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
	 * // don.setNgayLapDonGapLanhDaoTmp(xuLyDon.getNgayHenGapLanhDao()); //
	 * Utils.save(donRepo, don, congChucId); // return Utils.doSave(repo,
	 * xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED); // } } } return
	 * Utils.responseErrors(HttpStatus.NOT_FOUND,
	 * ApiErrorEnum.DATA_NOT_FOUND.name(),
	 * ApiErrorEnum.DATA_NOT_FOUND.getText()); } return
	 * Utils.responseErrors(HttpStatus.FORBIDDEN,
	 * ApiErrorEnum.ROLE_FORBIDDEN.name(),
	 * ApiErrorEnum.ROLE_FORBIDDEN.getText()); }
	 * 
	 */

	private void disableXuLyDonCu(VaiTroEnum vaiTro, Long donId, Long congChucId) {
		List<XuLyDon> xuLyDonCu = (List<XuLyDon>) repo.findAll(xuLyDonService.predFindOld(donId, vaiTro));
		if (xuLyDonCu != null) {
			for (XuLyDon xld: xuLyDonCu) {
				xld.setOld(true);
				Utils.save(repo, xld, congChucId);
			}
		}
	}
	
	public XuLyDon lanhDaoGiaoViec(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long donViId, Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		XuLyDon xuLyDonTruongPhong = new XuLyDon();
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());

		xuLyDonHienTai.setPhongBanXuLyChiDinh(xuLyDon.getPhongBanXuLyChiDinh());
		xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		// xuLyDonHienTai.setThoiHanXuLy(thoiHanXuLy);
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLyChiDinh());
		xuLyDonTiepTheo.setPhongBanXuLyChiDinh(xuLyDon.getPhongBanXuLyChiDinh());
		xuLyDonTiepTheo.setChucVuGiaoViec(VaiTroEnum.LANH_DAO);
		// xuLyDonTiepTheo.setThoiHanXuLy(thoiHanXuLy);
		xuLyDonTiepTheo.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		if (xuLyDon.getCanBoXuLyChiDinh() == null) {
			disableXuLyDonCu(VaiTroEnum.TRUONG_PHONG, donId, congChucId);
			if (xuLyDon.getPhongBanXuLy() != null) {
				note = note + xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim() + " ";
			}
			xuLyDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
		} else {
			disableXuLyDonCu(VaiTroEnum.CHUYEN_VIEN, donId, congChucId);
			note = note + xuLyDon.getCanBoXuLyChiDinh().getHoVaTen().trim() + " ";
			if (xuLyDon.getPhongBanXuLyChiDinh() != null) {
				note = note + xuLyDon.getCanBoXuLyChiDinh().getHoVaTen().trim() + " "
						+ xuLyDon.getPhongBanXuLyChiDinh().getTen().toLowerCase().trim() + " ";
			}
			xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
			xuLyDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
			xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh());
			
			//Tat ca truong phong
			xuLyDonTruongPhong.setNguoiTao(congChucRepo.findOne(congChucId));
			xuLyDonTruongPhong.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
			xuLyDonTruongPhong.setDon(xuLyDonHienTai.getDon());
			xuLyDonTruongPhong.setPhongBanXuLy(xuLyDon.getPhongBanXuLyChiDinh());
			xuLyDonTruongPhong.setPhongBanXuLyChiDinh(xuLyDon.getPhongBanXuLyChiDinh());
			xuLyDonTruongPhong.setChucVuGiaoViec(VaiTroEnum.LANH_DAO);
			xuLyDonTruongPhong.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
			xuLyDonTruongPhong.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 2);
		}

		if (xuLyDonHienTai.isDonChuyen()) {
			note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		
		//set don vi 
		xuLyDonTiepTheo.setDonViXuLy(coQuanQuanLyRepo.findOne(donViId));
		
		// set don
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setLanhDaoDuyet(true);
		
		// set thoi han xu ly cho don
		if (xuLyDon.getSoNgayXuLy() != null && xuLyDon.getSoNgayXuLy() > 0) {
			xuLyDonHienTai.setThoiHanXuLy(Utils.convertNumberToLocalDateTime(xuLyDonHienTai.getDon().getNgayTiepNhan(),
					xuLyDon.getSoNgayXuLy()));
			xuLyDonTiepTheo.setThoiHanXuLy(Utils.convertNumberToLocalDateTime(xuLyDonHienTai.getDon().getNgayTiepNhan(),
					xuLyDon.getSoNgayXuLy()));

			// set thoi han xu ly cho don
			don.setThoiHanXuLyXLD(Utils.convertNumberToLocalDateTime(xuLyDonHienTai.getDon().getNgayTiepNhan(),
					xuLyDon.getSoNgayXuLy()));
		}

		// set ngay bat dau xu ly don cho don
		// don.setNgayBatDauXLD(LocalDateTime.now());

		don.setCurrentState(xuLyDonHienTai.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonTruongPhong, congChucId);
		
		return xuLyDonTiepTheo;
	}

	public XuLyDon trinhDon(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long coQuanQuanLyId, Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonTiepTheo = new XuLyDon();

		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());

		note = note + VaiTroEnum.LANH_DAO.getText().toLowerCase() + " "
				+ coQuanQuanLyRepo.findOne(coQuanQuanLyId).getTen().toLowerCase().trim() + " ";

		xuLyDonHienTai.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonHienTai.setThoiHanXuLy(
				Utils.convertNumberToLocalDateTime(xuLyDonHienTai.getDon().getNgayTiepNhan(), xuLyDon.getSoNgayXuLy()));
		disableXuLyDonCu(VaiTroEnum.LANH_DAO, donId, congChucId);
		xuLyDonTiepTheo.setThoiHanXuLy(
				Utils.convertNumberToLocalDateTime(xuLyDonHienTai.getDon().getNgayTiepNhan(), xuLyDon.getSoNgayXuLy()));
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.LANH_DAO);
		//xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		// xuLyDonTiepTheo.setThoiHanXuLy();
		
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);

		// set don
		Don don = donRepo.findOne(donId);
		// set thoi han xu ly cho don
		if (xuLyDon.getSoNgayXuLy() != null && xuLyDon.getSoNgayXuLy() > 0) {
			don.setThoiHanXuLyXLD(Utils.convertNumberToLocalDateTime(xuLyDonHienTai.getDon().getNgayTiepNhan(),
					xuLyDon.getSoNgayXuLy()));
			xuLyDonHienTai.setThoiHanXuLy(Utils.convertNumberToLocalDateTime(xuLyDonHienTai.getDon().getNgayTiepNhan(),
					xuLyDon.getSoNgayXuLy()));
			xuLyDonTiepTheo.setThoiHanXuLy(Utils.convertNumberToLocalDateTime(xuLyDonHienTai.getDon().getNgayTiepNhan(),
					xuLyDon.getSoNgayXuLy()));
		}
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));

		// workflow
		don.setCurrentState(xuLyDonHienTai.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		return xuLyDonTiepTheo;
	}

	public XuLyDon truongPhongGiaoViecLai(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonTiepTheo = new XuLyDon();

		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());

		note = note + VaiTroEnum.LANH_DAO.getText().toLowerCase().trim() + " ";
		if (xuLyDonHienTai.getPhongBanXuLy() != null) {
			CoQuanQuanLy coQuanQuanLy = xuLyDonHienTai.getPhongBanXuLy().getCha();
			note = note + coQuanQuanLy.getTen().toLowerCase().trim() + " ";
			xuLyDonTiepTheo.setPhongBanXuLy(coQuanQuanLy);
		}
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonHienTai.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
		disableXuLyDonCu(VaiTroEnum.LANH_DAO, donId, congChucId);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.LANH_DAO);
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		xuLyDonTiepTheo.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
		
		//set phong ban va don vi
		//xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);

		// set don
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		// set thoi han xu ly cho don
		don.setThoiHanXuLyXLD(xuLyDonHienTai.getThoiHanXuLy());

		don.setCurrentState(xuLyDonHienTai.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		return xuLyDonTiepTheo;
	}

	public XuLyDon truongPhongGiaoViec(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonTiepTheo = new XuLyDon();

		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());

		note = note + VaiTroEnum.CHUYEN_VIEN.getText().toLowerCase().trim() + " ";
		if (xuLyDonHienTai.getPhongBanXuLy() != null) {
			note += xuLyDonHienTai.getPhongBanXuLy().getTen().toLowerCase().trim() + " ";
		}
		disableXuLyDonCu(VaiTroEnum.CHUYEN_VIEN, donId, congChucId);
		xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setChucVuGiaoViec(VaiTroEnum.TRUONG_PHONG);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setPhongBanXuLyChiDinh(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		// set don
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));

		// set thoi han xu ly cho don
		don.setThoiHanXuLyXLD(xuLyDonHienTai.getThoiHanXuLy());

		don.setCurrentState(xuLyDonHienTai.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);

		return xuLyDonTiepTheo;
	}

	public XuLyDon chuyenVienGiaoViecLai(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonTiepTheo = new XuLyDon();

		xuLyDonHienTai.setNextState(xuLyDon.getNextState());

		note = note + VaiTroEnum.TRUONG_PHONG.getText().toLowerCase().trim() + " ";
		if (xuLyDonHienTai.getPhongBanXuLy() != null) {
			note += xuLyDonHienTai.getPhongBanXuLy().getTen().trim() + " ";
		}

		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		disableXuLyDonCu(VaiTroEnum.TRUONG_PHONG, donId, congChucId);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setCanBoXuLyChiDinh(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLyChiDinh(xuLyDonHienTai.getPhongBanXuLyChiDinh());
		
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		// set don
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		// set thoi han xu ly cho don
		don.setThoiHanXuLyXLD(xuLyDonHienTai.getThoiHanXuLy());

		don.setCurrentState(xuLyDonHienTai.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		return xuLyDonTiepTheo;
	}

	public XuLyDon chuyenVienChuyenChoVanThuYeuCauGapLanhDao(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, String note) {
		CoQuanQuanLy coQuanQuanLy = xuLyDonHienTai.getPhongBanXuLy().getCha();
		note = note + VaiTroEnum.LANH_DAO.getText() + coQuanQuanLy.getTen().toLowerCase().trim();
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonHienTai.setNgayHenGapLanhDao(xuLyDon.getNgayHenGapLanhDao());
		xuLyDonHienTai.setDiaDiem(xuLyDon.getDiaDiem());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
		don.setNgayLapDonGapLanhDaoTmp(xuLyDon.getNgayHenGapLanhDao());
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));

		xuLyDonHienTai.setGhiChu(note);
		Utils.save(donRepo, don, congChucId);
		return xuLyDonHienTai;
	}

	public XuLyDon chuyenVienDeXuatThuLy(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, String note) {
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);

		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));

		//tao ma don
		if (don.getMa() == null || don.getMa().isEmpty()) {
			don.setMa(donService.getMaDon(donRepo, don.getId()));
		}
		
		HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy();
		don.setHuongXuLyXLD(huongXuLyXLD);
		don.setThoiHanXuLyXLD(xuLyDon.getThoiHanXuLy());
		don.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		don.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);	
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		State beginState = repoState.findOne(stateService.predicateFindByType(FlowStateEnum.BAT_DAU));
		// set thoi han xu ly cho don
		don.setThoiHanXuLyXLD(xuLyDonHienTai.getThoiHanXuLy());
		don.setProcessType(ProcessTypeEnum.GIAI_QUYET_DON);
		don.setCurrentState(beginState);
		Utils.save(donRepo, don, congChucId);
		
		//Quy trinh Giai Quyet Don
		ThongTinGiaiQuyetDon thongTinGiaiQuyetDon = thongTinGiaiQuyetDonRepo.findOne(thongTinGiaiQuyetDonService.predicateFindByDon(xuLyDonHienTai.getDon().getId()));
		if (thongTinGiaiQuyetDon == null) {
			thongTinGiaiQuyetDon = new ThongTinGiaiQuyetDon();
			thongTinGiaiQuyetDon.setDon(don);
			Utils.save(thongTinGiaiQuyetDonRepo, thongTinGiaiQuyetDon, congChucId);
		}
		
		//Lich su Giai quyet don
		GiaiQuyetDon giaiQuyetDon = new GiaiQuyetDon();
		giaiQuyetDon.setThongTinGiaiQuyetDon(thongTinGiaiQuyetDon);
		giaiQuyetDon.setChucVu(VaiTroEnum.TRUONG_PHONG);
		giaiQuyetDon.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		giaiQuyetDon.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDon.setThuTuThucHien(1);
		Utils.save(giaiQuyetDonRepo, giaiQuyetDon, congChucId);

		return xuLyDonHienTai;
	}

	public XuLyDon chuyenVienChuyenChoVanThuDeXuatThuLy(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());

		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " ";

		if (xuLyDon.getPhongBanXuLy() != null) {
			note += xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
		}
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		disableXuLyDonCu(VaiTroEnum.VAN_THU, donId, congChucId);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());

		xuLyDonTiepTheo.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonTiepTheo.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		// set don
		Don don = donRepo.findOne(donId);
		// set thoi han xu ly cho don
		don.setThoiHanXuLyXLD(xuLyDonHienTai.getThoiHanXuLy());
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));

		don.setCurrentState(xuLyDonHienTai.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);

		return xuLyDonTiepTheo;
	}

	public XuLyDon chuyenVienChuyenDonChoVanThu(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();

		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " ";
		if (xuLyDon.getPhongBanXuLy() != null) {
			note += xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
		}
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		disableXuLyDonCu(VaiTroEnum.VAN_THU, donId, congChucId);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonTiepTheo.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		// set don
		Don don = donRepo.findOne(donId);
		// set thoi han xu ly cho don
		don.setThoiHanXuLyXLD(xuLyDonHienTai.getThoiHanXuLy());

		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCurrentState(xuLyDonHienTai.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);

		return xuLyDonTiepTheo;
	}

	public XuLyDon chuyenVienChuyenDon(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId) {
		// set thoi han xu ly cho don
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());

		HuongXuLyXLDEnum huongXuLyXLD = xuLyDonHienTai.getHuongXuLy();
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		don.setHuongXuLyXLD(huongXuLyXLD);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		disableXuLyDonCu(VaiTroEnum.VAN_THU, xuLyDonHienTai.getDon().getId(), congChucId);
		xuLyDonTiepTheo.setDon(don);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getCoQuanTiepNhan());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDaXoa(true);
		xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setCoQuanTiepNhan(xuLyDonHienTai.getCoQuanTiepNhan());
		xuLyDonTiepTheo.setyKienXuLy(xuLyDonHienTai.getyKienXuLy());
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());

		// set don
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		// set thoi han xu ly cho don
		don.setThoiHanXuLyXLD(xuLyDonHienTai.getThoiHanXuLy());
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));

		don.setCurrentState(xuLyDonHienTai.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);

		return xuLyDonTiepTheo;
	}

	public XuLyDon chuyenVienXuLyKhongDuDieuKienThuLy(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " ";
		if (xuLyDon.getPhongBanXuLy() != null) {
			note += xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
		}

		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		disableXuLyDonCu(VaiTroEnum.VAN_THU, donId, congChucId);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		// set don
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		// set thoi han xu ly cho don
		don.setThoiHanXuLyXLD(xuLyDonHienTai.getThoiHanXuLy());

		don.setCurrentState(xuLyDonHienTai.getNextState());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);

		return xuLyDonTiepTheo;
	}

	public XuLyDon chuyenVienTraLaiDonKhongDungThamQuyenChoVanThu(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();

		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());

		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " ";
		if (xuLyDon.getPhongBanXuLy() != null) {
			note += xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
		}
		//
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		//
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		disableXuLyDonCu(VaiTroEnum.VAN_THU, donId, congChucId);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);

		note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
		xuLyDonTiepTheo.setDonChuyen(true);
		xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		xuLyDonHienTai.setGhiChu(note);

		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		// set don
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		// set thoi han xu ly cho don
		don.setThoiHanXuLyXLD(xuLyDonHienTai.getThoiHanXuLy());

		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);

		return xuLyDonTiepTheo;
	}

	public XuLyDon chuyenVienTraLaiDonKhongDungThamQuyen(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, String note) {
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());

		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		don.setHuongXuLyXLD(HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		don.setThoiHanXuLyXLD(xuLyDonHienTai.getThoiHanXuLy());

		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		disableXuLyDonCu(VaiTroEnum.VAN_THU, xuLyDonHienTai.getDon().getId(), congChucId);
		xuLyDonTiepTheo.setDon(don);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getCoQuanChuyenDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDaXoa(true);
		xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getPhongBanXuLy());

		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);

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
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		disableXuLyDonCu(VaiTroEnum.VAN_THU, xuLyDonHienTai.getDon().getId(), congChucId);
		xuLyDonTiepTheo.setDon(don);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getCoQuanTiepNhan());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDaXoa(true);
		xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setCoQuanTiepNhan(xuLyDonHienTai.getCoQuanTiepNhan());
		xuLyDonTiepTheo.setyKienXuLy(xuLyDonHienTai.getyKienXuLy());
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		// set don
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		// set thoi han xu ly cho don
		don.setThoiHanXuLyXLD(xuLyDonHienTai.getThoiHanXuLy());
		// don.setNgayBatDauXLD(LocalDateTime.now());

		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);

		return xuLyDonTiepTheo;
	}

	//
	public XuLyDon vanThuTraLaiDonKhongDungThamQuyen(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId) {
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);

		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		don.setHuongXuLyXLD(HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());

		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		disableXuLyDonCu(VaiTroEnum.VAN_THU, xuLyDonHienTai.getDon().getId(), congChucId);
		xuLyDonTiepTheo.setDon(don);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getCoQuanChuyenDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDaXoa(true);
		xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getPhongBanXuLy());
		
		//set don vi
		xuLyDonTiepTheo.setDonViXuLy(xuLyDonHienTai.getDonViXuLy());
		
		// set don
		don.setNgayKetThucXLD(LocalDateTime.now());

		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);

		return xuLyDonTiepTheo;
	}

	public XuLyDon deXuatThuLy(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId) {
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);

		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));

		//tao ma don
		if (don.getMa() == null || don.getMa().isEmpty()) {
			don.setMa(donService.getMaDon(donRepo, don.getId()));
		}
		
		HuongXuLyXLDEnum huongXuLyXLD = xuLyDonHienTai.getHuongXuLy();
		don.setHuongXuLyXLD(huongXuLyXLD);
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		don.setPhongBanGiaiQuyet(xuLyDonHienTai.getPhongBanGiaiQuyet());
		don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));

		State beginState = repoState.findOne(stateService.predicateFindByType(FlowStateEnum.BAT_DAU));
		
		// set ngay ket thuc cho don
		don.setNgayKetThucXLD(LocalDateTime.now());		
		don.setProcessType(ProcessTypeEnum.GIAI_QUYET_DON);
		don.setCurrentState(beginState);
		Utils.save(donRepo, don, congChucId);
		
		//Quy trinh Giai Quyet Don
		ThongTinGiaiQuyetDon thongTinGiaiQuyetDon = thongTinGiaiQuyetDonRepo.findOne(thongTinGiaiQuyetDonService.predicateFindByDon(xuLyDonHienTai.getDon().getId()));
		if (thongTinGiaiQuyetDon == null) {
			thongTinGiaiQuyetDon = new ThongTinGiaiQuyetDon();
			thongTinGiaiQuyetDon.setDon(don);
			Utils.save(thongTinGiaiQuyetDonRepo, thongTinGiaiQuyetDon, congChucId);
		}
		
		//Lich su Giai quyet don
		GiaiQuyetDon giaiQuyetDon = new GiaiQuyetDon();
		giaiQuyetDon.setThongTinGiaiQuyetDon(thongTinGiaiQuyetDon);
		giaiQuyetDon.setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum.DANG_GIAI_QUYET);
		giaiQuyetDon.setPhongBanGiaiQuyet(xuLyDonHienTai.getPhongBanGiaiQuyet());
		giaiQuyetDon.setChucVu(VaiTroEnum.TRUONG_PHONG);
		giaiQuyetDon.setThuTuThucHien(1);
		Utils.save(giaiQuyetDonRepo, giaiQuyetDon, congChucId);

		return xuLyDonHienTai;
	}
}