package vn.greenglobal.tttp.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.profile.CommonProfile;
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
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.LichSuQuaTrinhXuLy;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.model.medial.Medial_Form_State;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.LichSuQuaTrinhXuLyRepository;
import vn.greenglobal.tttp.repository.ProcessRepository;
import vn.greenglobal.tttp.repository.StateRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.service.LichSuQuaTrinhXuLyService;
import vn.greenglobal.tttp.service.ProcessService;
import vn.greenglobal.tttp.service.StateService;
import vn.greenglobal.tttp.service.TransitionService;
import vn.greenglobal.tttp.service.XuLyDonService;
import vn.greenglobal.tttp.util.ExcelUtil;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "dons", description = "Danh Sách Đơn")
public class DonController extends TttpController<Don> {
	
	@Autowired
	private DonRepository repo;

	@Autowired
	private XuLyDonRepository xuLyRepo;

	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepo;

	@Autowired
	private CongChucRepository congChucRepo;

	@Autowired
	private ProcessRepository repoProcess;

	@Autowired
	private TransitionRepository repoTransition;

	@Autowired
	private StateRepository repoState;

	@Autowired
	private GiaiQuyetDonRepository giaiQuyetDonRepo;
	
	@Autowired
	private StateService serviceState;
	
	@Autowired
	private ProcessService processService;

	@Autowired
	protected PagedResourcesAssembler<State> assemblerState;

	@Autowired
	private DonService donService;
	
	@Autowired
	private XuLyDonService xuLyDonService;
	
	@Autowired
	private TransitionRepository transitionRepo;
	
	@Autowired
	private TransitionService transitionService;
	
	@Autowired
	private LichSuQuaTrinhXuLyRepository lichSuQuaTrinhXuLyRepo;
	
	@Autowired
	private LichSuQuaTrinhXuLyService lichSuQuaTrinhXuLyService;
	
	public DonController(BaseRepository<Don, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/dons")
	@ApiOperation(value = "Lấy danh sách Đơn", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy dữ liệu đơn thành công", response = Don.class),
			@ApiResponse(code = 203, message = "Không có quyền lấy dữ liệu"),
			@ApiResponse(code = 204, message = "Không có dữ liệu"),
			@ApiResponse(code = 400, message = "Param không đúng kiểu"), })
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "maDon", required = false) String maDon,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "nguonDon", required = false) String nguonDon,
			@RequestParam(value = "phanLoaiDon", required = false) String phanLoaiDon,
			@RequestParam(value = "tiepNhanTuNgay", required = false) String tiepNhanTuNgay,
			@RequestParam(value = "tiepNhanDenNgay", required = false) String tiepNhanDenNgay,
			@RequestParam(value = "hanGiaiQuyetTuNgay", required = false) String hanGiaiQuyetTuNgay,
			@RequestParam(value = "hanGiaiQuyetDenNgay", required = false) String hanGiaiQuyetDenNgay,
			@RequestParam(value = "tinhTrangXuLy", required = false) String tinhTrangXuLy,
			@RequestParam(value = "thanhLapDon", required = true) boolean thanhLapDon,
			@RequestParam(value = "trangThaiDon", required = false) String trangThaiDon,
			@RequestParam(value = "phongBanGiaiQuyetXLD", required = false) Long phongBanGiaiQuyet,
			@RequestParam(value = "coQuanTiepNhanXLD", required = false) Long coQuanTiepNhanXLD,
			@RequestParam(value = "hoTen", required = false) String hoTen,
			PersistentEntityResourceAssembler eass) {

		NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_LIETKE);
		if (nguoiDung != null) {
			Long donViXuLyXLD = new Long(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Long phongBanXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
			String vaiTroNguoiDungHienTai = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro").toString();
			Long canBoXuLyXLD = new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			if (StringUtils.equals(VaiTroEnum.LANH_DAO.name(), vaiTroNguoiDungHienTai) 
					|| StringUtils.equals(VaiTroEnum.VAN_THU.name(), vaiTroNguoiDungHienTai)) {
				phongBanXuLyXLD = 0L;
			}

			Page<Don> pageData = repo.findAll(
					donService.predicateFindAll(maDon, tuKhoa, nguonDon, phanLoaiDon, tiepNhanTuNgay, tiepNhanDenNgay,
							hanGiaiQuyetTuNgay, hanGiaiQuyetDenNgay, tinhTrangXuLy, thanhLapDon, trangThaiDon,
							phongBanGiaiQuyet, canBoXuLyXLD, phongBanXuLyXLD, coQuanTiepNhanXLD, donViXuLyXLD, 
							vaiTroNguoiDungHienTai, hoTen, xuLyRepo, repo, giaiQuyetDonRepo),
					pageable);
			return assembler.toResource(pageData, (ResourceAssembler) eass);
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/giaiQuyetDons")
	@ApiOperation(value = "Lấy danh sách Đơn", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy dữ liệu đơn thành công", response = Don.class),
			@ApiResponse(code = 203, message = "Không có quyền lấy dữ liệu"),
			@ApiResponse(code = 204, message = "Không có dữ liệu"),
			@ApiResponse(code = 400, message = "Param không đúng kiểu"), })
	public @ResponseBody Object getListGiaiQuyetDon(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "maDon", required = false) String maDon,
			@RequestParam(value = "nguonDon", required = false) String nguonDon,
			@RequestParam(value = "phanLoaiDon", required = false) String phanLoaiDon,
			@RequestParam(value = "tiepNhanTuNgay", required = false) String tiepNhanTuNgay,
			@RequestParam(value = "tiepNhanDenNgay", required = false) String tiepNhanDenNgay,
			@RequestParam(value = "thanhLapDon", required = true) boolean thanhLapDon,
			@RequestParam(value = "trangThaiDon", required = false) String trangThaiDon,
			@RequestParam(value = "hoTen", required = false) String hoTen,
			PersistentEntityResourceAssembler eass) {

		NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_LIETKE);
		if (nguoiDung != null) {
			String vaiTro = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro").toString();
			Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			CongChuc congChuc = congChucRepo.findOne(congChucId);
			Page<Don> pageData = repo.findAll(
					donService.predicateFindAllGQD(maDon, nguonDon, phanLoaiDon, tiepNhanTuNgay, tiepNhanDenNgay,
							thanhLapDon, trangThaiDon, congChuc.getCoQuanQuanLy().getId(), 
							congChuc.getId(), vaiTro, hoTen, giaiQuyetDonRepo),
					pageable);
			return assembler.toResource(pageData, (ResourceAssembler) eass);
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}
	
	public Process getProcess(String authorization, Long nguoiTaoId, String processType) {
		Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
		String vaiTro = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro").toString();
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		boolean isOwner = congChucId.longValue() == nguoiTaoId.longValue() ? true : false;
		CoQuanQuanLy donVi = congChuc.getCoQuanQuanLy().getDonVi();
		ProcessTypeEnum processTypeEnum = ProcessTypeEnum.valueOf(StringUtils.upperCase(processType));
		Process process = repoProcess.findOne(processService.predicateFindAll(vaiTro, donVi, isOwner, processTypeEnum));			
		if (process == null && isOwner) {
			process = repoProcess.findOne(processService.predicateFindAll(vaiTro, donVi, false, processTypeEnum));
		}
		return process;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/listNextStates")
	@ApiOperation(value = "Lấy danh sách Trạng thái tiếp theo", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy dữ liệu trạng thái thành công thành công", response = State.class),
			@ApiResponse(code = 203, message = "Không có quyền lấy dữ liệu"),
			@ApiResponse(code = 204, message = "Không có dữ liệu"),
			@ApiResponse(code = 400, message = "Param không đúng kiểu"), })
	public @ResponseBody Object getListNextStates(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable,
			@RequestParam(value = "donId", required = true) Long donId,
			@RequestParam(value = "processType", required = true) String processType,
			@RequestParam(value = "currentStateId", required = true) Long currentStateId, PersistentEntityResourceAssembler eass) {

		NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA);
		if (nguoiDung != null) {
			Don don = repo.findOne(donService.predicateFindOne(donId));
			if (don == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DON_REQUIRED.name(),
						ApiErrorEnum.DON_REQUIRED.getText());
			}
			Process process = getProcess(authorization, don.getNguoiTao() != null ? don.getNguoiTao().getId() : 0L, processType);
			if (process == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_NOT_FOUND.name(),
						ApiErrorEnum.PROCESS_NOT_FOUND.getText());
			}
			
			Predicate predicate = serviceState.predicateFindAll(currentStateId, process, repoTransition);
			List<State> listState = ((List<State>) repoState.findAll(predicate));
			if (listState.size() < 1) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_INVALID.name(),
						ApiErrorEnum.TRANSITION_INVALID.getText());
			} else {
				for (State nextState : listState) {
					Transition transition = transitionRepo.findOne(transitionService.predicatePrivileged(don.getCurrentState(), nextState, process));
					if (transition == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_FORBIDDEN.name(),
								ApiErrorEnum.TRANSITION_FORBIDDEN.getText());
					}
				}
			}
			Page<State> pageData = repoState.findAll(predicate, pageable);
			return assemblerState.toResource(pageData, (ResourceAssembler) eass);
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/currentForm")
	@ApiOperation(value = "Lấy danh sách Trạng thái tiếp theo", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy dữ liệu trạng thái thành công thành công", response = State.class),
			@ApiResponse(code = 203, message = "Không có quyền lấy dữ liệu"),
			@ApiResponse(code = 204, message = "Không có dữ liệu"),
			@ApiResponse(code = 400, message = "Param không đúng kiểu"), })
	public @ResponseBody Object getCurrentForm(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable,
			@RequestParam(value = "donId", required = false) Long donId,
			@RequestParam(value = "processType", required = true) String processType,
			@RequestParam(value = "currentStateId", required = false) Long currentStateId, PersistentEntityResourceAssembler eass) {

		NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA);
		if (nguoiDung != null) {
			Medial_Form_State media = new Medial_Form_State();
			State currentState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.BAT_DAU));
			Long currentStateId2 = (currentStateId != null && currentStateId.longValue() > 0) ? currentStateId : currentState.getId();
			Long nguoiTaoId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			if (donId != null && donId.longValue() > 0) {
				Don don = repo.findOne(donService.predicateFindOne(donId));
				if (don == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DON_REQUIRED.name(),
							ApiErrorEnum.DON_REQUIRED.getText());
				}
				nguoiTaoId = don.getNguoiTao() != null ? don.getNguoiTao().getId() : 0L;
				currentState = don.getCurrentState();
			}
			
			Process process = getProcess(authorization, nguoiTaoId, processType);
			if (process == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_NOT_FOUND.name(),
						ApiErrorEnum.PROCESS_NOT_FOUND.getText());
			}
			
			Predicate predicate = serviceState.predicateFindAll(currentStateId2, process, repoTransition);
			List<State> listState = ((List<State>) repoState.findAll(predicate));
			media.setListNextStates(listState);
			if (listState.size() < 1) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_INVALID.name(),
						ApiErrorEnum.TRANSITION_INVALID.getText());
			} else {
				for (State nextState : listState) {
					Transition transition = transitionRepo.findOne(transitionService.predicatePrivileged(currentState, nextState, process));
					if (transition == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_FORBIDDEN.name(),
								ApiErrorEnum.TRANSITION_FORBIDDEN.getText());
					} else {
						media.setCurrentForm(transition.getForm());
					}
				}
			}
			return new ResponseEntity<>(eass.toFullResource(media), HttpStatus.OK);
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}

	public boolean checkInputDateTime(String tuNgay, String denNgay) {

		if (StringUtils.isNotBlank(tuNgay)) {
			try {
				LocalDateTime.parse(denNgay);
			} catch (DateTimeParseException ex) {
				return false;
			}
			if (StringUtils.isNotBlank(tuNgay)) {
				try {
					LocalDateTime.parse(denNgay);
				} catch (DateTimeParseException ex) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/dons")
	@ApiOperation(value = "Thêm mới Đơn", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Thêm mới Đơn thành công", response = Don.class),
			@ApiResponse(code = 201, message = "Thêm mới Đơn thành công", response = Don.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Don don, PersistentEntityResourceAssembler eass) {
		NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_THEM);
		CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
		
		if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
				&& commonProfile.containsAttribute("coQuanQuanLyId")) {
			Long congChucId = Long.valueOf(commonProfile.getAttribute("congChucId").toString());
			Long coQuanQuanLyId = Long.valueOf(commonProfile.getAttribute("coQuanQuanLyId").toString());
			Long donViId = Long.valueOf(commonProfile.getAttribute("donViId").toString());
			
			don.setNgayLapDonGapLanhDaoTmp(LocalDateTime.now());
			Don donMoi = Utils.save(repo, don, congChucId);
			donMoi.setCoQuanDangGiaiQuyet(coQuanQuanLyRepo.findOne(donViId));
			
			//tao lich su qua trinh xu ly don
			LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
			lichSuQTXLD.setDon(donMoi);
			lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
			lichSuQTXLD.setNgayXuLy(LocalDateTime.now());
			
			if (donMoi.isThanhLapDon()) {
				donMoi.setProcessType(ProcessTypeEnum.XU_LY_DON);
				State beginState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.BAT_DAU));					
				Process process = getProcess(authorization, congChucId, ProcessTypeEnum.XU_LY_DON.toString());
				if (process == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_NOT_FOUND.name(),
							ApiErrorEnum.PROCESS_NOT_FOUND.getText());
				}
				donMoi.setCurrentState(beginState);
				// Them xu ly don
				XuLyDon xuLyDon = new XuLyDon();
				xuLyDon.setDon(donMoi);
				xuLyDon.setChucVu(VaiTroEnum.VAN_THU);
				//xuLyDon.setPhongBanXuLy(coQuanQuanLyRepo.findOne(QCoQuanQuanLy.coQuanQuanLy.id.eq(coQuanQuanLyId)));
				xuLyDon.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
				xuLyDon.setThuTuThucHien(0);
				xuLyDon.setNoiDungXuLy(don.getNoiDungThongTinTrinhLanhDao());
				
				//set co quan & don vi
				xuLyDon.setPhongBanXuLy(coQuanQuanLyRepo.findOne((coQuanQuanLyId)));
				xuLyDon.setDonViXuLy(coQuanQuanLyRepo.findOne((donViId)));
				
				//set thoi han xu ly
				donMoi.setNgayBatDauXLD(LocalDateTime.now());
				donMoi.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
				lichSuQTXLD.setNoiDung(xuLyDon.getNoiDungXuLy());
				lichSuQTXLD.setTen("Chuyển Xử lý đơn");

				if (don.getThoiHanXuLyXLD() != null) {
					donMoi.setThoiHanXuLyXLD(don.getThoiHanXuLyXLD());
				} else {
					long soNgayXuLyMacDinh = 10;
					donMoi.setThoiHanXuLyXLD(Utils.convertNumberToLocalDateTimeGoc(donMoi.getNgayBatDauXLD(), soNgayXuLyMacDinh));
				}
				Utils.save(xuLyRepo, xuLyDon, congChucId);
			} else { 
				lichSuQTXLD.setTen("Tạo mới xử lý đơn");
			}
			lichSuQTXLD.setDonViXuLy(coQuanQuanLyRepo.findOne(donViId));
			lichSuQTXLD.setThuTuThucHien(0);
			Utils.save(lichSuQuaTrinhXuLyRepo, lichSuQTXLD, congChucId);
			return Utils.doSave(repo, donMoi, congChucId, eass, HttpStatus.CREATED);
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dons/{id}")
	@ApiOperation(value = "Lấy Đơn theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Đơn thành công", response = Don.class) })
	public ResponseEntity<Object> getDon(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_XEM);
		if (nguoiDung != null) {
			Don don = repo.findOne(donService.predicateFindOne(id));
			if (don == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			return new ResponseEntity<>(eass.toFullResource(don), HttpStatus.OK);
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/dons/{id}")
	@ApiOperation(value = "Cập nhật Đơn", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cập nhật Đơn thành công", response = Don.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody Don don, PersistentEntityResourceAssembler eass) {
		NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_THEM);
		CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);

		if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
				&& commonProfile.containsAttribute("coQuanQuanLyId")) {
			Long congChucId = Long.valueOf(commonProfile.getAttribute("congChucId").toString());
			Long coQuanQuanLyId = Long.valueOf(commonProfile.getAttribute("coQuanQuanLyId").toString());
			Long donViId = Long.valueOf(commonProfile.getAttribute("donViId").toString());
			
			don.setId(id);

			if (!donService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			Don donOld = repo.findOne(id);
			don.setNgayLapDonGapLanhDaoTmp(donOld.getNgayLapDonGapLanhDaoTmp());
			don.setYeuCauGapTrucTiepLanhDao(donOld.isYeuCauGapTrucTiepLanhDao());
			don.setThanhLapTiepDanGapLanhDao(donOld.isThanhLapTiepDanGapLanhDao());
			don.setLanhDaoDuyet(donOld.isLanhDaoDuyet());
			if (don.isYeuCauGapTrucTiepLanhDao() && !donOld.isYeuCauGapTrucTiepLanhDao()) {
				don.setNgayLapDonGapLanhDaoTmp(LocalDateTime.now());
			}
			don.setProcessType(donOld.getProcessType());
			don.setCoQuanDangGiaiQuyet(donOld.getCoQuanDaGiaiQuyet());
			if (don.getNoiDungThongTinTrinhLanhDao().isEmpty()) { 
				don.setNoiDungThongTinTrinhLanhDao(donOld.getNoiDungThongTinTrinhLanhDao());
			}
			if (don.isThanhLapDon()) {
				don.setNgayBatDauXLD(donOld.getNgayBatDauXLD());
				if (donOld.getThoiHanXuLyXLD() == null) {
					don.setNgayBatDauXLD(LocalDateTime.now());
					if (don.getThoiHanXuLyXLD() == null) {
						long soNgayXuLyMacDinh = 10;
						don.setThoiHanXuLyXLD(Utils.convertNumberToLocalDateTimeGoc(don.getNgayBatDauXLD(), soNgayXuLyMacDinh));
					}
				} else {
					if (don.getThoiHanXuLyXLD() == null) {
						don.setThoiHanXuLyXLD(donOld.getThoiHanXuLyXLD());
					}
				}
				
				// Them xu ly don
				if(donOld.getXuLyDons().size() <= 0) {
					XuLyDon xuLyDon = new XuLyDon();
					xuLyDon.setDon(don);
					xuLyDon.setChucVu(VaiTroEnum.VAN_THU);
					//xuLyDon.setPhongBanXuLy(coQuanQuanLyRepo.findOne(QCoQuanQuanLy.coQuanQuanLy.id.eq(coQuanQuanLyId)));
					xuLyDon.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
					xuLyDon.setThuTuThucHien(0);
					xuLyDon.setNoiDungXuLy(don.getNoiDungThongTinTrinhLanhDao());
					//set co quan & don vi
					xuLyDon.setPhongBanXuLy(coQuanQuanLyRepo.findOne((coQuanQuanLyId)));
					xuLyDon.setDonViXuLy(coQuanQuanLyRepo.findOne((donViId)));
					
					//tao lich su qua trinh xu ly don
					LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
					lichSuQTXLD.setDon(donOld);
					lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
					lichSuQTXLD.setNgayXuLy(LocalDateTime.now());
					lichSuQTXLD.setTen("Chuyển Xử lý đơn");
					lichSuQTXLD.setNoiDung(xuLyDon.getNoiDungXuLy());
					CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViId);
					lichSuQTXLD.setDonViXuLy(donVi);
					int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, donOld.getId(), 
							donVi.getId());
					lichSuQTXLD.setThuTuThucHien(thuTu);
					Utils.save(xuLyRepo, xuLyDon, congChucId);
					Utils.save(lichSuQuaTrinhXuLyRepo, lichSuQTXLD, congChucId);
				} else {
					XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyRepo, don.getId());
					if (xuLyDonHienTai != null) {
						if (!don.isLanhDaoDuyet() && StringUtils.isNotBlank(don.getNoiDungThongTinTrinhLanhDao())) {
							xuLyDonHienTai.setNoiDungXuLy(don.getNoiDungThongTinTrinhLanhDao());
							xuLyDonHienTai.setNoiDungThongTinTrinhLanhDao(don.getNoiDungThongTinTrinhLanhDao());
						}
						Utils.save(xuLyRepo, xuLyDonHienTai, congChucId);
					}
				}
				
				don.setCoQuanDangGiaiQuyet(coQuanQuanLyRepo.findOne(donViId));
				don.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
				don.setProcessType(ProcessTypeEnum.XU_LY_DON);
				State beginState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.BAT_DAU));					
				Process process = getProcess(authorization, don.getNguoiTao() != null ? don.getNguoiTao().getId() : 0L, ProcessTypeEnum.XU_LY_DON.toString());
				if (process == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_NOT_FOUND.name(),
							ApiErrorEnum.PROCESS_NOT_FOUND.getText());
				}
				don.setCurrentState(beginState);
			}

			return Utils.doSave(repo, don,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/dons/{id}")
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_XOA);
		if (nguoiDung != null) {

			Don don = donService.deleteDon(repo, id);
			if (don == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			Utils.save(repo, don,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dons/xuatExcel")
	@ApiOperation(value = "Xuất file excel", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcel(HttpServletResponse response,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "nguonDon", required = false) String nguonDon,
			@RequestParam(value = "phanLoaiDon", required = false) String phanLoaiDon,
			@RequestParam(value = "tiepNhanTuNgay", required = false) String tiepNhanTuNgay,
			@RequestParam(value = "tiepNhanDenNgay", required = false) String tiepNhanDenNgay,
			@RequestParam(value = "tinhTrangXuLy", required = false) String tinhTrangXuLy,
			@RequestParam(value = "thanhLapDon", required = true) boolean thanhLapDon,
			@RequestParam(value = "trangThaiDon", required = false) String trangThaiDon,
			@RequestParam(value = "phongBanGiaiQuyetXLD", required = false) Long phongBanGiaiQuyet,
			@RequestParam(value = "canBoXuLyXLD", required = false) Long canBoXuLyXLD,
			@RequestParam(value = "phongBanXuLyXLD", required = false) Long phongBanXuLyXLD,
			@RequestParam(value = "donViXuLyXLD", required = false) Long donViXuLyXLD,
			@RequestParam(value = "coQuanTiepNhanXLD", required = false) Long coQuanTiepNhanXLD,
			@RequestParam(value = "vaiTro", required = true) String vaiTro,
			@RequestParam(value = "hoTen", required = false) String hoTen) throws IOException {
		OrderSpecifier<LocalDateTime> order = QDon.don.ngayTiepNhan.desc();
		
		ExcelUtil.exportDanhSachXuLyDon(response, "DanhSachXuLyDon", "sheetName", 
				(List<Don>) repo.findAll(donService.predicateFindAll("", tuKhoa, nguonDon, phanLoaiDon, tiepNhanTuNgay, tiepNhanDenNgay, "", "", tinhTrangXuLy, 
						thanhLapDon, trangThaiDon, phongBanGiaiQuyet, canBoXuLyXLD, phongBanXuLyXLD, coQuanTiepNhanXLD, donViXuLyXLD, vaiTro, hoTen, xuLyRepo, repo, giaiQuyetDonRepo), order),
				"Danh sách xử lý đơn");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dons/ngayMacDinhThoiHanXuLyDon")
	@ApiOperation(value = "Lấy Ngày mặc định của thời hạn xử lý đơn", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Ngày mặc định  thành công", response = Don.class) })
	public Object getNgayMacDinhCuaThoiHanXuLyDon(@RequestHeader(value = "Authorization", required = true) String authorization,
			PersistentEntityResourceAssembler eass) {
		long soNgayMacDinh = 10;
		LocalDateTime thoiHan = Utils.convertNumberToLocalDateTimeGoc(LocalDateTime.now(), soNgayMacDinh);
		if (thoiHan == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		return new ResponseEntity<>(thoiHan, HttpStatus.OK);
	}
}