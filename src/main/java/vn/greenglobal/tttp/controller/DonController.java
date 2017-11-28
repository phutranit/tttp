package vn.greenglobal.tttp.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
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
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.DoiTuongThayDoiEnum;
import vn.greenglobal.tttp.enums.FlowStateEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.LichSuQuaTrinhXuLy;
import vn.greenglobal.tttp.model.LichSuThayDoi;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.model.PropertyChangeObject;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.model.medial.Medial_DonTraCuu;
import vn.greenglobal.tttp.model.medial.Medial_Form_State;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.LichSuQuaTrinhXuLyRepository;
import vn.greenglobal.tttp.repository.LichSuThayDoiRepository;
import vn.greenglobal.tttp.repository.ProcessRepository;
import vn.greenglobal.tttp.repository.StateRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.DonCongDanService;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.service.LichSuQuaTrinhXuLyService;
import vn.greenglobal.tttp.service.LichSuThayDoiService;
import vn.greenglobal.tttp.service.ProcessService;
import vn.greenglobal.tttp.service.StateService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.service.TransitionService;
import vn.greenglobal.tttp.service.XuLyDonService;
import vn.greenglobal.tttp.util.ExcelUtil;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "dons", description = "Danh Sách Đơn")
public class DonController extends TttpController<Don> {
	
	@Autowired
	private DonCongDanRepository donCongDanRepository;
	
	@Autowired
	private DonCongDanService donCongDanService;
	
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
	private LichSuThayDoiRepository lichSuRepo;

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
	
	@Autowired
	private LichSuThayDoiService lichSuThayDoiService;

	@Autowired
	protected PagedResourcesAssembler<LichSuThayDoi> lichSuThayDoiAssembler;
	
	@Autowired
	protected PagedResourcesAssembler<Medial_DonTraCuu> mediaTraCuuDonAssembler;
	
	@Autowired
	private ThamSoRepository thamSoRepository;
	
	@Autowired
	private ThamSoService thamSoService;
		
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
			@RequestParam(value = "trangThaiDonToanHT", required = false) String trangThaiDonToanHT,
			@RequestParam(value = "ketQuaToanHT", required = false) String ketQuaToanHT,
			PersistentEntityResourceAssembler eass) {

		try {

			NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_LIETKE);
			if (nguoiDung != null) {
				boolean coQuyTrinh = true;
				Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
				Long phongBanXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
				String vaiTroNguoiDungHienTai = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro").toString();
				Long canBoXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
				CongChuc congChuc = congChucRepo.findOne(canBoXuLyXLD);
				if (StringUtils.equals(VaiTroEnum.LANH_DAO.name(), vaiTroNguoiDungHienTai) 
						|| StringUtils.equals(VaiTroEnum.VAN_THU.name(), vaiTroNguoiDungHienTai)) {
					phongBanXuLyXLD = 0L;
				}
				
				NumberExpression<Integer> canBoXuLyChiDinh = null;
				OrderSpecifier<LocalDateTime> sortOrderDon = null;
				OrderSpecifier<Integer> sortOrderDonByCanBo = null;
				List<Don> listDon = new ArrayList<Don>();
				
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
								
				if (StringUtils.isNotBlank(trangThaiDon)) {
					if ("DANG_XU_LY".equals(trangThaiDon) || "DANG_GIAI_QUYET".equals(trangThaiDon)) {
						canBoXuLyChiDinh = QDon.don.canBoXuLyChiDinh.id.when(canBoXuLyXLD)					
								.then(Expressions.numberTemplate(Integer.class, "0"))					
								.otherwise(Expressions.numberTemplate(Integer.class, "1"));
						sortOrderDonByCanBo = canBoXuLyChiDinh.asc();
						sortOrderDon = QDon.don.ngayTiepNhan.desc();
						listDon = (List<Don>) repo.findAll(donService.predicateFindAll(maDon, tuKhoa, nguonDon, phanLoaiDon, tiepNhanTuNgay, tiepNhanDenNgay,
								hanGiaiQuyetTuNgay, hanGiaiQuyetDenNgay, tinhTrangXuLy, thanhLapDon, trangThaiDon,
								phongBanGiaiQuyet, canBoXuLyXLD, phongBanXuLyXLD, coQuanTiepNhanXLD, donViXuLyXLD, 
								vaiTroNguoiDungHienTai, congChuc.getNguoiDung().getVaiTros(), hoTen, trangThaiDonToanHT, ketQuaToanHT, xuLyRepo, repo, giaiQuyetDonRepo,
								coQuyTrinh), 
								sortOrderDonByCanBo, sortOrderDon);
					} else if ("DA_XU_LY".equals(trangThaiDon) || "DA_GIAI_QUYET".equals(trangThaiDon)) {
						sortOrderDon = QDon.don.ngayTiepNhan.desc();
						listDon = (List<Don>) repo.findAll(donService.predicateFindAll(maDon, tuKhoa, nguonDon, phanLoaiDon, tiepNhanTuNgay, tiepNhanDenNgay,
								hanGiaiQuyetTuNgay, hanGiaiQuyetDenNgay, tinhTrangXuLy, thanhLapDon, trangThaiDon,
								phongBanGiaiQuyet, canBoXuLyXLD, phongBanXuLyXLD, coQuanTiepNhanXLD, donViXuLyXLD, 
								vaiTroNguoiDungHienTai, congChuc.getNguoiDung().getVaiTros(), hoTen, trangThaiDonToanHT, ketQuaToanHT, xuLyRepo, repo, giaiQuyetDonRepo,
								coQuyTrinh), 
								sortOrderDon);
					}
				}
				
				int start = pageable.getOffset();
				int end = (start + pageable.getPageSize()) > listDon.size() ? listDon.size() : (start + pageable.getPageSize());
				
				Page<Don> pages = new PageImpl<Don>(listDon.subList(start, end), pageable, listDon.size());
				
				return assembler.toResource(pages, (ResourceAssembler) eass);
			}
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
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
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "nguonDon", required = false) String nguonDon,
			@RequestParam(value = "phanLoaiDon", required = false) String phanLoaiDon,
			@RequestParam(value = "tiepNhanTuNgay", required = false) String tiepNhanTuNgay,
			@RequestParam(value = "tiepNhanDenNgay", required = false) String tiepNhanDenNgay,
			@RequestParam(value = "thanhLapDon", required = true) boolean thanhLapDon,
			@RequestParam(value = "tinhTrangGiaiQuyet", required = false) String tinhTrangGiaiQuyet,
			@RequestParam(value = "trangThaiDon", required = true) String trangThaiDon,
			@RequestParam(value = "hoTen", required = false) String hoTen,
			@RequestParam(value = "trangThaiDonToanHT", required = false) String trangThaiDonToanHT,
			@RequestParam(value = "ketQuaToanHT", required = false) String ketQuaToanHT,
			PersistentEntityResourceAssembler eass) {

		try {
			NguoiDung nguoiDung = null;
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_LIETKE) != null) {
				nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_LIETKE);
			}
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_XEM) != null) {
				nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_XEM);
			}

			if (nguoiDung != null) {
				String vaiTro = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro").toString();
				Long donViGiaiQuyetId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
				Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
				CongChuc congChuc = congChucRepo.findOne(congChucId);
				
				NumberExpression<Integer> canBoXuLyChiDinh = QDon.don.canBoXuLyChiDinh.id.when(congChucId)					
						.then(Expressions.numberTemplate(Integer.class, "0"))					
						.otherwise(Expressions.numberTemplate(Integer.class, "1"));
				OrderSpecifier<Long> sortOrderDon = QDon.don.id.desc();
				
				List<Don> listDon = (List<Don>) repo.findAll(
						donService.predicateFindAllGQD(maDon, tuKhoa, nguonDon, phanLoaiDon, tiepNhanTuNgay, tiepNhanDenNgay,
								thanhLapDon, tinhTrangGiaiQuyet, trangThaiDon, congChuc.getCoQuanQuanLy().getId(), donViGiaiQuyetId,
								congChuc.getId(), vaiTro, hoTen, trangThaiDonToanHT, ketQuaToanHT, giaiQuyetDonRepo, xuLyRepo), 
						canBoXuLyChiDinh.asc(), sortOrderDon);
				
				int start = pageable.getOffset();
				int end = (start + pageable.getPageSize()) > listDon.size() ? listDon.size() : (start + pageable.getPageSize());
				
				Page<Don> pages = new PageImpl<Don>(listDon.subList(start, end), pageable, listDon.size());
				return assembler.toResource(pages, (ResourceAssembler) eass);
			}
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	public List<Process> getProcess(String authorization, Long nguoiTaoId, String processType) {
		Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		List<VaiTroEnum> listVaiTro = congChuc.getNguoiDung().getVaiTros().stream().map(d -> d.getLoaiVaiTro()).distinct().collect(Collectors.toList());
		boolean isOwner = congChucId.longValue() == nguoiTaoId.longValue() ? true : false;
		CoQuanQuanLy donVi = congChuc.getCoQuanQuanLy().getDonVi();
		ProcessTypeEnum processTypeEnum = ProcessTypeEnum.valueOf(StringUtils.upperCase(processType));
		List<Process> listProcess = new ArrayList<Process>();
		for (VaiTroEnum vaiTroEnum : listVaiTro) {
			Process process = repoProcess.findOne(processService.predicateFindAll(vaiTroEnum.toString(), donVi, isOwner, processTypeEnum));			
			if (process == null && isOwner) {
				process = repoProcess.findOne(processService.predicateFindAll(vaiTroEnum.toString(), donVi, false, processTypeEnum));
			}
			if (process != null) {
				listProcess.add(process);
			}
		}
		
		return listProcess;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/currentForm")
	@ApiOperation(value = "Lấy danh sách Trạng thái tiếp theo", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy dữ liệu trạng thái thành công", response = State.class),
			@ApiResponse(code = 203, message = "Không có quyền lấy dữ liệu"),
			@ApiResponse(code = 204, message = "Không có dữ liệu"),
			@ApiResponse(code = 400, message = "Param không đúng kiểu"), })
	public @ResponseBody Object getCurrentForm(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable,
			@RequestParam(value = "donId", required = false) Long donId, PersistentEntityResourceAssembler eass) {

		try {
			NguoiDung nguoiDung = null;
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA) != null) {
				nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA);
			}
			
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_SUA) != null) {
				nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_SUA);			
			}
			
			if (nguoiDung != null) {
				Medial_Form_State media = new Medial_Form_State();
				State currentState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.BAT_DAU));
				
				Long nguoiTaoId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
				Don don = null;
				if (donId != null && donId.longValue() > 0) {
					don = repo.findOne(donService.predicateFindOne(donId));
					if (don == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DON_REQUIRED.name(),
								ApiErrorEnum.DON_REQUIRED.getText(), ApiErrorEnum.DON_REQUIRED.getText());
					}
					nguoiTaoId = don.getNguoiTao() != null ? don.getNguoiTao().getId() : 0L;
					currentState = don.getCurrentState();
				}
				Long currentStateId2 = currentState.getId();
				String processType = "XU_LY_DON";
				if (don != null) {
					currentStateId2 = (don.getCurrentState() != null && don.getCurrentState().getId().longValue() > 0) ? don.getCurrentState().getId() : currentState.getId();
					processType = don.getProcessType() != null ? don.getProcessType().toString() : processType;
				}
				
				List<Process> listProcess = getProcess(authorization, nguoiTaoId, processType);
				if (listProcess.size() < 1) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_NOT_FOUND.name(),
							ApiErrorEnum.PROCESS_NOT_FOUND.getText(), ApiErrorEnum.PROCESS_NOT_FOUND.getText());
				}
				List<State> listAllState = new ArrayList<State>();
				List<State> listState = new ArrayList<State>();
				
				Process process = null;
				for (Process processFromList : listProcess) {
					Predicate predicate = serviceState.predicateFindAll(currentStateId2, processFromList, repoTransition);
					listState = ((List<State>) repoState.findAll(predicate));
					System.out.println("processFromList: " + processFromList.getTenQuyTrinh() + " __ " + listState.size());
					if (listState.size() > 0) {
						process = processFromList;
						listAllState.addAll(listState);
					}
				}
				
				media.setListNextStates(listAllState);
				Transition transition = null;
				if (listAllState.size() < 1) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_INVALID.name(),
							ApiErrorEnum.TRANSITION_INVALID.getText(), ApiErrorEnum.TRANSITION_INVALID.getText());
				} else {
					for (State nextState : listAllState) {
						transition = transitionRepo.findOne(transitionService.predicatePrivileged(currentState, nextState, process));
						if (transition != null) {
							media.setCurrentForm(transition.getForm());
							break;
						} 
					}
					if (transition == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_FORBIDDEN.name(),
								ApiErrorEnum.TRANSITION_FORBIDDEN.getText(), ApiErrorEnum.TRANSITION_FORBIDDEN.getText());
					}
				}
				return new ResponseEntity<>(eass.toFullResource(media), HttpStatus.OK);
			}
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/traCuuDons/congDan")
	@ApiOperation(value = "Lấy thông tin đơn", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy thông tin đơn thành công", response = Don.class),
			@ApiResponse(code = 203, message = "Không có quyền lấy dữ liệu"),
			@ApiResponse(code = 204, message = "Không có dữ liệu"),
			@ApiResponse(code = 400, message = "Param không đúng kiểu"), })
	public @ResponseBody Object getTraCuuDonCongDan(@RequestParam(value = "maDon", required = true) String maDon, PersistentEntityResourceAssembler eass) {

		try {
			Don don = repo.findOne(QDon.don.daXoa.eq(false).and(QDon.don.ma.equalsIgnoreCase(maDon)));
			if (don == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DON_NOT_FOUND.name(),
						ApiErrorEnum.DON_NOT_FOUND.getText(), ApiErrorEnum.DON_NOT_FOUND.getText());
			}
			Medial_DonTraCuu media = new Medial_DonTraCuu();
			media.copyDon(don);
			return new ResponseEntity<>(eass.toFullResource(media), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/traCuuDons/canBo")
	@ApiOperation(value = "Lấy danh sách Đơn", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy dữ liệu đơn thành công", response = Medial_DonTraCuu.class),
			@ApiResponse(code = 203, message = "Không có quyền lấy dữ liệu"),
			@ApiResponse(code = 204, message = "Không có dữ liệu"),
			@ApiResponse(code = 400, message = "Param không đúng kiểu"), })
	public @ResponseBody Object getTraCuuDonCanBo(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "maDon", required = false) String maDon,
			@RequestParam(value = "diaChi", required = false) String diaChi,
			@RequestParam(value = "nguonDon", required = false) String nguonDon,
			@RequestParam(value = "phanLoaiDon", required = false) String phanLoaiDon,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "tinhTrangXuLy", required = false) String tinhTrangXuLy,
			@RequestParam(value = "linhVucId", required = false) Long linhVucId,
			@RequestParam(value = "linhVucChiTietId", required = false) Long linhVucChiTietId,
			@RequestParam(value = "trangThaiDon", required = false) String trangThaiDon,
			@RequestParam(value = "hoTen", required = false) String hoTen, 
			@RequestParam(value = "trangThaiDonToanHT", required = false) String trangThaiDonToanHT,
			@RequestParam(value = "ketQuaToanHT", required = false) String ketQuaToanHT,
			@RequestParam(value = "taiDonVi", required = false) boolean taiDonVi,
			@RequestParam(value = "listDonViTiepNhan", required = false) List<CoQuanQuanLy> listDonViTiepNhan,
			PersistentEntityResourceAssembler eass) {

		try {

			NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_LIETKE);
			if (nguoiDung != null) {
				Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
				OrderSpecifier<LocalDateTime> sortOrderDon = null;
				List<Don> listDon = new ArrayList<Don>();
				
				sortOrderDon = QDon.don.ngayTiepNhan.desc();
				listDon = (List<Don>) repo.findAll(donService.predicateFindAllDonTraCuu(maDon, diaChi, nguonDon, phanLoaiDon, linhVucId, 
						linhVucChiTietId, tuNgay, denNgay, tinhTrangXuLy, donViXuLyXLD, hoTen, ketQuaToanHT, 
						taiDonVi, listDonViTiepNhan, xuLyRepo, repo, giaiQuyetDonRepo), 
						sortOrderDon);
				
				int start = pageable.getOffset();
				int end = (start + pageable.getPageSize()) > listDon.size() ? listDon.size() : (start + pageable.getPageSize());
				List<Medial_DonTraCuu> listMedial = new ArrayList<Medial_DonTraCuu>();
				Medial_DonTraCuu media = null;
				for (Don don : listDon.subList(start, end)) {
					media = new Medial_DonTraCuu();
					media.copyDon(don);
					listMedial.add(media);
				}
				Page<Medial_DonTraCuu> pages = new PageImpl<Medial_DonTraCuu>(listMedial, pageable, listDon.size());
				
				return mediaTraCuuDonAssembler.toResource(pages, (ResourceAssembler) eass);
			}
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
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
		try {
			NguoiDung nguoiDungHienTai = null;
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_THEM) != null) {
				nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_THEM);
			}
			
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_THEM) != null) {
				nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_THEM);			
			}
			
			CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
			
			if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
					&& commonProfile.containsAttribute("coQuanQuanLyId")) {
				Long congChucId = Long.valueOf(commonProfile.getAttribute("congChucId").toString());
				Long coQuanQuanLyId = Long.valueOf(commonProfile.getAttribute("coQuanQuanLyId").toString());
				Long donViId = Long.valueOf(commonProfile.getAttribute("donViId").toString());
				if (don.isThanhLapDon()) {
					if (!don.isSaveTmp()) {
						if (don.getLoaiDon() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.LOAIDON_REQUIRED.name(),
									ApiErrorEnum.LOAIDON_REQUIRED.getText(), ApiErrorEnum.LOAIDON_REQUIRED.getText());
						}
						if (don.getLinhVucDonThu() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.LINHVUCDONTHU_REQUIRED.name(),
									ApiErrorEnum.LINHVUCDONTHU_REQUIRED.getText(), ApiErrorEnum.LINHVUCDONTHU_REQUIRED.getText());
						}
						if (don.getNoiDung() == null || don.getNoiDung().isEmpty()) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.NOIDUNG_REQUIRED.name(),
									ApiErrorEnum.NOIDUNG_REQUIRED.getText(), ApiErrorEnum.NOIDUNG_REQUIRED.getText());
						}
						if (don.getLoaiDon().equals(LoaiDonEnum.DON_KHIEU_NAI) || don.getLoaiDon().equals(LoaiDonEnum.DON_KHIEU_NAI)) {
							if (don.getLoaiDoiTuong() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.LOAIDOITUONG_REQUIRED.name(),
										ApiErrorEnum.LOAIDOITUONG_REQUIRED.getText(), ApiErrorEnum.LOAIDOITUONG_REQUIRED.getText());
							}
						}
						if (don.getLoaiVuViec() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.LOAIVUVIEC_REQUIRED.name(),
									ApiErrorEnum.LOAIVUVIEC_REQUIRED.getText(), ApiErrorEnum.LOAIVUVIEC_REQUIRED.getText());
						}
	//					if (don.getPhanLoaiDon() == null) {
	//						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHANLOAIDON_REQUIRED.name(),
	//								ApiErrorEnum.PHANLOAIDON_REQUIRED.getText(), ApiErrorEnum.PHANLOAIDON_REQUIRED.getText());
	//					}
					}
				}
				don = checkDataThongTinDon(don);
				don.setNgayLapDonGapLanhDaoTmp(Utils.localDateTimeNow());
				Don donMoi = donService.save(don, congChucId);
				donMoi.setCoQuanDangGiaiQuyet(coQuanQuanLyRepo.findOne(donViId));
				donMoi.setDonViTiepDan(coQuanQuanLyRepo.findOne(donViId));
				donMoi.setNgayNhanTraDonChuyen(don.getNgayNhanTraDonChuyen());
				
				if (donMoi.isThanhLapDon()) {
					donMoi.setMaHoSo(donService.getMaHoSo(repo, null));
					donMoi.setProcessType(ProcessTypeEnum.XU_LY_DON);
					State beginState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.BAT_DAU));					
					Predicate predicateProcess = processService.predicateFindAllByDonVi(coQuanQuanLyRepo.findOne(donViId), ProcessTypeEnum.XU_LY_DON);
					List<Process> listProcess = (List<Process>) repoProcess.findAll(predicateProcess);
					donMoi.setCurrentState(beginState);
					donMoi.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DANG_XU_LY);
					donMoi.setDonViXuLyGiaiQuyet(coQuanQuanLyRepo.findOne(donViId));
					//Vai tro tiep theo
					List<State> listState = new ArrayList<State>();
					List<Process> listProcessHaveBeginState = new ArrayList<Process>();
					for (Process processFromList : listProcess) {
						Predicate predicate = serviceState.predicateFindAll(beginState.getId(), processFromList, repoTransition);
						listState = ((List<State>) repoState.findAll(predicate));						
						if (listState.size() > 0) {
							State state = listState.get(0);
							if (!state.getType().equals(FlowStateEnum.KET_THUC)) {								
								listProcessHaveBeginState.add(processFromList);
							}						
						}
					}

					// Them xu ly don
					XuLyDon xuLyDon = new XuLyDon();
					xuLyDon.setDon(donMoi);
					xuLyDon.setChucVu(listProcessHaveBeginState.size() == 1 || listProcessHaveBeginState.size() == 2 ? listProcessHaveBeginState.get(0).getVaiTro().getLoaiVaiTro() : null);
					xuLyDon.setChucVu2(listProcessHaveBeginState.size() == 2 ? listProcessHaveBeginState.get(1).getVaiTro().getLoaiVaiTro() : null);
					//xuLyDon.setPhongBanXuLy(coQuanQuanLyRepo.findOne(QCoQuanQuanLy.coQuanQuanLy.id.eq(coQuanQuanLyId)));
					xuLyDon.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
					xuLyDon.setThuTuThucHien(0);
					xuLyDon.setNoiDungXuLy(don.getNoiDungThongTinTrinhLanhDao());
					xuLyDon.setCongChuc(congChucRepo.findOne(congChucId));
					
					//set co quan & don vi
					xuLyDon.setPhongBanXuLy(coQuanQuanLyRepo.findOne((coQuanQuanLyId)));
					xuLyDon.setDonViXuLy(coQuanQuanLyRepo.findOne((donViId)));
					
					//set thoi han xu ly
					donMoi.setNgayBatDauXLD(Utils.localDateTimeNow());
					if (don.getNgayTiepNhan() != null) {
						donMoi.setNgayTiepNhan(don.getNgayTiepNhan());
					} else { 
						donMoi.setNgayTiepNhan(donMoi.getNgayBatDauXLD());
					}
					
					donMoi.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
					
					//tao lich su qua trinh xu ly don
//					LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy();
//					lichSuQTXLD.setDon(donMoi);
//					lichSuQTXLD.setNguoiXuLy(congChucRepo.findOne(congChucId));
//					lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
//					lichSuQTXLD.setNoiDung("Tiếp nhận đơn và chuyển đơn sang bộ phận xử lý");
//					lichSuQTXLD.setTen("Chuyển Xử lý đơn");
//					lichSuQTXLD.setDonViXuLy(coQuanQuanLyRepo.findOne(donViId));
//					lichSuQTXLD.setThuTuThucHien(0);
					
					if (don.getThoiHanXuLyXLD() != null) {
						donMoi.setThoiHanXuLyXLD(don.getThoiHanXuLyXLD());
					} else {
						ThamSo thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_XU_LY_DON_MAC_DINH"));
						Long soNgayXuLyMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 10L;
						donMoi.setThoiHanXuLyXLD(Utils.convertNumberToLocalDateTimeTinhTheoNgayLamViec(donMoi.getNgayBatDauXLD(), soNgayXuLyMacDinh));
					}
					xuLyDonService.save(xuLyDon, congChucId);
//					if (donMoi.isTuTCDChuyenQua()) { 
//						lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
//					}
				}
				
				List<PropertyChangeObject> listThayDoi = donService.getListThayDoi(donMoi, new Don());
				LichSuThayDoi lichSu = new LichSuThayDoi();
				lichSu.setDoiTuongThayDoi(DoiTuongThayDoiEnum.DON);
				lichSu.setIdDoiTuong(donMoi.getId());
				lichSu.setNoiDung("Tạo mới đơn");
				lichSu.setChiTietThayDoi(getChiTietThayDoi(listThayDoi));
				lichSuThayDoiService.save(lichSu, congChucId);
				return donService.doSave(donMoi, congChucId, eass, HttpStatus.CREATED);
			}
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/dons/{id}")
	@ApiOperation(value = "Lấy Đơn theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Đơn thành công", response = Don.class) })
	public ResponseEntity<Object> getDon(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			NguoiDung nguoiDungHienTai = null;
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA) != null) {
				nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA);
			}
			
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_SUA) != null) {
				nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_SUA);			
			}
			
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_SUA) != null) {
				nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_SUA);			
			}
			
			if (nguoiDungHienTai != null) {
				Don don = repo.findOne(donService.predicateFindOne(id));
				if (don == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
							ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
				}
				return new ResponseEntity<>(eass.toFullResource(don), HttpStatus.OK);
			}
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/dons/{id}")
	@ApiOperation(value = "Cập nhật Đơn", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cập nhật Đơn thành công", response = Don.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody Don don, PersistentEntityResourceAssembler eass) {
		try {
			NguoiDung nguoiDungHienTai = null;
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA) != null) {
				nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_SUA);
			}
			
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_SUA) != null) {
				nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_SUA);			
			}
			
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_SUA) != null) {
				nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_SUA);			
			}
			
			CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);

			if (nguoiDungHienTai != null && commonProfile.containsAttribute("congChucId")
					&& commonProfile.containsAttribute("coQuanQuanLyId")) {
				Long congChucId = Long.valueOf(commonProfile.getAttribute("congChucId").toString());
				Long coQuanQuanLyId = Long.valueOf(commonProfile.getAttribute("coQuanQuanLyId").toString());
				Long donViId = Long.valueOf(commonProfile.getAttribute("donViId").toString());
				if (don.isThanhLapDon()) {
					if (!don.isSaveTmp()) {
						if (don.getLoaiDon() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.LOAIDON_REQUIRED.name(),
									ApiErrorEnum.LOAIDON_REQUIRED.getText(), ApiErrorEnum.LOAIDON_REQUIRED.getText());
						}
						if (don.getLinhVucDonThu() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.LINHVUCDONTHU_REQUIRED.name(),
									ApiErrorEnum.LINHVUCDONTHU_REQUIRED.getText(), ApiErrorEnum.LINHVUCDONTHU_REQUIRED.getText());
						}
						if (don.getNoiDung() == null || don.getNoiDung().isEmpty()) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.NOIDUNG_REQUIRED.name(),
									ApiErrorEnum.NOIDUNG_REQUIRED.getText(), ApiErrorEnum.NOIDUNG_REQUIRED.getText());
						}
						if (don.getLoaiDon().equals(LoaiDonEnum.DON_KHIEU_NAI) || don.getLoaiDon().equals(LoaiDonEnum.DON_KHIEU_NAI)) {
							if (don.getLoaiDoiTuong() == null) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.LOAIDOITUONG_REQUIRED.name(),
										ApiErrorEnum.LOAIDOITUONG_REQUIRED.getText(), ApiErrorEnum.LOAIDOITUONG_REQUIRED.getText());
							}
						}
						if (don.getLoaiVuViec() == null) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.LOAIVUVIEC_REQUIRED.name(),
									ApiErrorEnum.LOAIVUVIEC_REQUIRED.getText(), ApiErrorEnum.LOAIVUVIEC_REQUIRED.getText());
						}
						
//						Bo phanLoaiDon - cap nhat 16/11
//						if (don.getPhanLoaiDon() == null) {
//							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PHANLOAIDON_REQUIRED.name(),
//									ApiErrorEnum.PHANLOAIDON_REQUIRED.getText(), ApiErrorEnum.PHANLOAIDON_REQUIRED.getText());
//						}
					}
				}
				don.setId(id);
				don = checkDataThongTinDon(don);
				
				if (!donService.isExists(repo, id)) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
							ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
				}
				Don donOld = repo.findOne(donService.predicateFindOne(id));
				if (don.isThanhLapDon() && !donOld.isThanhLapDon()) {
					don.setMaHoSo(donService.getMaHoSo(repo, don.getId()));
				}
				don.setNgayThucHienKetQuaXuLy(donOld.getNgayThucHienKetQuaXuLy());
				don.setNgayNhanTraDonChuyen(donOld.getNgayNhanTraDonChuyen());
				don.setYeuCauGapTrucTiepLanhDao(donOld.isYeuCauGapTrucTiepLanhDao());
				don.setThanhLapTiepDanGapLanhDao(donOld.isThanhLapTiepDanGapLanhDao());
				don.setLanhDaoDuyet(donOld.isLanhDaoDuyet());
				don.setDonViXuLyGiaiQuyet(coQuanQuanLyRepo.findOne(donViId));
				don.setXuLyDonCuoiCungId(donOld.getXuLyDonCuoiCungId());
				don.setGiaiQuyetDonCuoiCungId(donOld.getGiaiQuyetDonCuoiCungId());
				don.setTrangThaiXLDGiaiQuyet(donOld.getTrangThaiXLDGiaiQuyet());
				don.setTrangThaiTTXM(donOld.getTrangThaiTTXM());
				don.setDonViXuLyGiaiQuyet(donOld.getDonViXuLyGiaiQuyet());
				// truong hop luu don set can bo chi dinh
				don.setCanBoXuLyChiDinh(donOld.getCanBoXuLyChiDinh());
				don.setDonViTiepDan(donOld.getDonViTiepDan());
				
//				Bo phanLoaiDon - cap nhat 16/11
//				if (don.getPhanLoaiDon() != null) {
//					if (PhanLoaiDonEnum.DU_DIEU_KIEN_XU_LY.equals(don.getPhanLoaiDon())) {
//						don.setLyDoKhongDuDieuKienThuLy(null);
//					} else {
//						don.setLyDoKhongDuDieuKienThuLy(don.getLyDoKhongDuDieuKienThuLy());
//					}
//				} else {
//					don.setPhanLoaiDon(donOld.getPhanLoaiDon());
//					don.setLyDoKhongDuDieuKienThuLy(donOld.getLyDoKhongDuDieuKienThuLy());
//				}
				
				don.setTrangThaiYeuCauGapLanhDao(donOld.getTrangThaiYeuCauGapLanhDao());
				don.setLyDoThayDoiTTYeuCauGapLanhDao(donOld.getLyDoThayDoiTTYeuCauGapLanhDao());
				don.setNgayLapDonGapLanhDaoTmp(donOld.getNgayLapDonGapLanhDaoTmp());
				if (donOld.isThanhLapDon()) { 
					don.setThanhLapDon(donOld.isThanhLapDon());
				}
				
				if (don.isYeuCauGapTrucTiepLanhDao() && !donOld.isYeuCauGapTrucTiepLanhDao()) {
					don.setNgayLapDonGapLanhDaoTmp(Utils.localDateTimeNow());
				}
				
				don.setProcessType(donOld.getProcessType());
				don.setCoQuanDangGiaiQuyet(donOld.getCoQuanDangGiaiQuyet());
				if (don.getNoiDungThongTinTrinhLanhDao().isEmpty()) { 
					don.setNoiDungThongTinTrinhLanhDao(donOld.getNoiDungThongTinTrinhLanhDao());
				}
				
				if (don.isThanhLapDon() && don.getProcessType() == null) {
					don.setDonViXuLyGiaiQuyet(coQuanQuanLyRepo.findOne(donViId));
					don.setCoQuanDangGiaiQuyet(coQuanQuanLyRepo.findOne(donViId));
					don.setNgayBatDauXLD(donOld.getNgayBatDauXLD());
					don.setTrangThaiXLDGiaiQuyet(TrangThaiDonEnum.DANG_XU_LY);
					don.setCanBoXuLyPhanHeXLD(donOld.getCanBoXuLyPhanHeXLD());
					if (donOld.getNgayTiepNhan() == null) {
						if (don.getNgayTiepNhan() == null) {
							don.setNgayTiepNhan(Utils.localDateTimeNow());
						}
					} else {
						if (don.getNgayTiepNhan() == null) {
							don.setNgayTiepNhan(donOld.getNgayTiepNhan());
						}
					}
					
					if (donOld.getThoiHanXuLyXLD() == null) {
						don.setNgayBatDauXLD(Utils.localDateTimeNow());
						if (don.getThoiHanXuLyXLD() == null) {
							ThamSo thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_XU_LY_DON_MAC_DINH"));
							Long soNgayXuLyMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 10L;
							don.setThoiHanXuLyXLD(Utils.convertNumberToLocalDateTimeTinhTheoNgayLamViec(don.getNgayBatDauXLD(), soNgayXuLyMacDinh));
						}
					} else {
						if (don.getThoiHanXuLyXLD() == null) {
							don.setThoiHanXuLyXLD(donOld.getThoiHanXuLyXLD());
						}
					}
					
					if (donOld.isHoanThanhDon()) {
						don.setHoanThanhDon(donOld.isHoanThanhDon());
					}
					
					// Them xu ly don
					if(donOld.getXuLyDons().size() <= 0) {
						State beginState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.BAT_DAU));
						Predicate predicateProcess = processService.predicateFindAllByDonVi(coQuanQuanLyRepo.findOne(donViId), ProcessTypeEnum.XU_LY_DON);
						List<Process> listProcess = (List<Process>) repoProcess.findAll(predicateProcess);
						if (don.getProcessType() == null) {
							don.setProcessType(ProcessTypeEnum.XU_LY_DON);
						}
						if (don.getCurrentState() == null) {
							don.setCurrentState(beginState);
						}
						
						//Vai tro tiep theo
						List<State> listState = new ArrayList<State>();
						List<Process> listProcessHaveBeginState = new ArrayList<Process>();
						for (Process processFromList : listProcess) {
							Predicate predicate = serviceState.predicateFindAll(beginState.getId(), processFromList, repoTransition);
							listState = ((List<State>) repoState.findAll(predicate));
							if (listState.size() > 0 ) {
								State state = listState.get(0);
								if (!state.getType().equals(FlowStateEnum.KET_THUC)) {								
									listProcessHaveBeginState.add(processFromList);
								}						
							}
						}
						
//						if (listState.size() < 1) {
//							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_INVALID.name(),
//									ApiErrorEnum.TRANSITION_INVALID.getText(), ApiErrorEnum.TRANSITION_INVALID.getText());
//						} else {
//							for (State stateFromList : listState) {
//								transition = transitionRepo.findOne(transitionService.predicatePrivileged(beginState, stateFromList, process));
//								if (transition != null) {
//									break;
//								} 						
//							}					
//							if (transition == null) {
//								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.TRANSITION_FORBIDDEN.name(),
//										ApiErrorEnum.TRANSITION_FORBIDDEN.getText(), ApiErrorEnum.TRANSITION_FORBIDDEN.getText());
//							}
//						}
						
						XuLyDon xuLyDon = new XuLyDon();
						xuLyDon.setDon(don);
						xuLyDon.setChucVu(listProcessHaveBeginState.size() == 1 || listProcessHaveBeginState.size() == 2 ? listProcessHaveBeginState.get(0).getVaiTro().getLoaiVaiTro() : null);
						xuLyDon.setChucVu2(listProcessHaveBeginState.size() == 2 ? listProcessHaveBeginState.get(1).getVaiTro().getLoaiVaiTro() : null);
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
						lichSuQTXLD.setNgayXuLy(Utils.localDateTimeNow());
						lichSuQTXLD.setTen("Chuyển Xử lý đơn");
						lichSuQTXLD.setNoiDung("Tiếp nhận đơn và chuyển đơn sang bộ phận xử lý");
						if (StringUtils.isNotBlank(xuLyDon.getNoiDungXuLy())) { 
							lichSuQTXLD.setNoiDung(xuLyDon.getNoiDungXuLy());
						}
						CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViId);
						lichSuQTXLD.setDonViXuLy(donVi);
						int thuTu = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, donOld.getId(), 
								donVi.getId());
						lichSuQTXLD.setThuTuThucHien(thuTu);
						int size = 0;
						size = lichSuQuaTrinhXuLyService.timThuTuLichSuQuaTrinhXuLyHienTai(lichSuQuaTrinhXuLyRepo, donOld.getId(), lichSuQTXLD.getDonViXuLy().getId());
						if (size == 1) { 
							lichSuQuaTrinhXuLyService.save(lichSuQTXLD, congChucId);
						}
						xuLyDonService.save(xuLyDon, congChucId);
					} else {
						XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyRepo, don.getId());
						if (xuLyDonHienTai != null) {
							if (!don.isLanhDaoDuyet() && StringUtils.isNotBlank(don.getNoiDungThongTinTrinhLanhDao())) {
								xuLyDonHienTai.setNoiDungXuLy(don.getNoiDungThongTinTrinhLanhDao());
								xuLyDonHienTai.setNoiDungThongTinTrinhLanhDao(don.getNoiDungThongTinTrinhLanhDao());
							}
							xuLyDonService.save(xuLyDonHienTai, congChucId);
						}
					}
					
					don.setCoQuanDangGiaiQuyet(coQuanQuanLyRepo.findOne(donViId));
					don.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
					if (don.getProcessType() == null) {
						don.setProcessType(ProcessTypeEnum.XU_LY_DON);
					}				
									
					/*
					Process process = getProcess(authorization, don.getNguoiTao() != null ? don.getNguoiTao().getId() : 0L, ProcessTypeEnum.XU_LY_DON.toString());
					if (process == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.PROCESS_NOT_FOUND.name(),
								ApiErrorEnum.PROCESS_NOT_FOUND.getText());
					}*/
					if (don.getCurrentState() == null) {
						State beginState = repoState.findOne(serviceState.predicateFindByType(FlowStateEnum.BAT_DAU));	
						don.setCurrentState(beginState);
					}
				} else {
					if (don.isThanhLapDon()) {
						don.setNgayTiepNhan(donOld.getNgayTiepNhan());
						don.setNgayBatDauXLD(donOld.getNgayBatDauXLD());
						don.setDonViXuLyGiaiQuyet(coQuanQuanLyRepo.findOne(donViId));
						don.setCoQuanDangGiaiQuyet(coQuanQuanLyRepo.findOne(donViId));
						don.setTrangThaiDon(donOld.getTrangThaiDon());
						don.setCurrentState(donOld.getCurrentState());
						don.setCanBoXuLyPhanHeXLD(donOld.getCanBoXuLyPhanHeXLD());
						don.setNgayKetThucXLD(donOld.getNgayKetThucXLD());
						don.setHuongXuLyXLD(donOld.getHuongXuLyXLD());
						don.setKetQuaXLDGiaiQuyet(donOld.getKetQuaXLDGiaiQuyet());
						
						if (donOld.getThoiHanXuLyXLD() == null) {
							don.setNgayBatDauXLD(Utils.localDateTimeNow());
							if (don.getThoiHanXuLyXLD() == null) {
								ThamSo thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_XU_LY_DON_MAC_DINH"));
								Long soNgayXuLyMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 10L;
								don.setThoiHanXuLyXLD(Utils.convertNumberToLocalDateTimeTinhTheoNgayLamViec(don.getNgayBatDauXLD(), soNgayXuLyMacDinh));
							}
						} else {
							if (don.getThoiHanXuLyXLD() == null) {
								don.setThoiHanXuLyXLD(donOld.getThoiHanXuLyXLD());
							}
						}
					}
				}

				List<PropertyChangeObject> listThayDoi = donService.getListThayDoi(don, donOld);
				if (listThayDoi.size() > 0) {
					LichSuThayDoi lichSu = new LichSuThayDoi();
					lichSu.setDoiTuongThayDoi(DoiTuongThayDoiEnum.DON);
					lichSu.setIdDoiTuong(id);
					lichSu.setNoiDung("Cập nhật thông tin đơn");
					lichSu.setChiTietThayDoi(getChiTietThayDoi(listThayDoi));
					lichSuThayDoiService.save(lichSu, congChucId);
				}
				resetDataNguoiUyQuyen(don, congChucId);
				return donService.doSave(don,
						Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
						HttpStatus.CREATED);
			}
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/dons/{id}")
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			NguoiDung nguoiDung = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.XULYDON_XOA);
			if (nguoiDung != null) {

				Don don = donService.deleteDon(repo, id);
				if (don == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
							ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
				}
				Utils.changeQuyenTuXuLy(don, false, false, false);
				donService.save(don, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dons/xuatExcel")
	@ApiOperation(value = "Xuất file excel", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcel(HttpServletResponse response,
			@RequestParam(value = "maDon", required = false) String maDon,
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
			@RequestParam(value = "canBoXuLyXLD", required = false) Long canBoXuLyXLD,
			@RequestParam(value = "phongBanXuLyXLD", required = false) Long phongBanXuLyXLD,
			@RequestParam(value = "donViXuLyXLD", required = true) Long donViXuLyXLD,
			@RequestParam(value = "coQuanTiepNhanXLD", required = false) Long coQuanTiepNhanXLD,
			@RequestParam(value = "vaiTro", required = true) String vaiTro,
			@RequestParam(value = "hoTen", required = false) String hoTen,
			@RequestParam(value = "trangThaiDonToanHT", required = false) String trangThaiDonToanHT,
			@RequestParam(value = "ketQuaToanHT", required = false) String ketQuaToanHT, 
			@RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "size", required = false) Integer size) throws IOException {
		
		try {
			CongChuc congChuc = congChucRepo.findOne(canBoXuLyXLD);
			NumberExpression<Integer> canBoXuLyChiDinh = null;
			OrderSpecifier<LocalDateTime> sortOrderDon = null;
			OrderSpecifier<Integer> sortOrderDonByCanBo = null;
			List<Don> listDon = new ArrayList<Don>();
			boolean coQuyTrinh = true;
			
			if (StringUtils.isNotBlank(vaiTro)) {
				if (StringUtils.equals(VaiTroEnum.LANH_DAO.name(), vaiTro)
						|| StringUtils.equals(VaiTroEnum.VAN_THU.name(), vaiTro)) {
					phongBanXuLyXLD = 0L;
				}
			}
			
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
			
			if (StringUtils.isNotBlank(trangThaiDon)) {
				if ("DANG_XU_LY".equals(trangThaiDon) || "DANG_GIAI_QUYET".equals(trangThaiDon)) {
					canBoXuLyChiDinh = QDon.don.canBoXuLyChiDinh.id.when(canBoXuLyXLD)					
							.then(Expressions.numberTemplate(Integer.class, "0"))					
							.otherwise(Expressions.numberTemplate(Integer.class, "1"));
					sortOrderDonByCanBo = canBoXuLyChiDinh.asc();
					sortOrderDon = QDon.don.ngayTiepNhan.desc();
					listDon = (List<Don>) repo.findAll(donService.predicateFindAll(maDon, tuKhoa, nguonDon, phanLoaiDon, tiepNhanTuNgay, tiepNhanDenNgay,
							hanGiaiQuyetTuNgay, hanGiaiQuyetDenNgay, tinhTrangXuLy, thanhLapDon, trangThaiDon,
							phongBanGiaiQuyet, canBoXuLyXLD, phongBanXuLyXLD, coQuanTiepNhanXLD, donViXuLyXLD, 
							vaiTro, congChuc.getNguoiDung().getVaiTros(), hoTen, trangThaiDonToanHT, ketQuaToanHT, xuLyRepo, repo, giaiQuyetDonRepo,
							coQuyTrinh), 
							sortOrderDonByCanBo, sortOrderDon);
				} else if ("DA_XU_LY".equals(trangThaiDon) || "DA_GIAI_QUYET".equals(trangThaiDon)) {
					sortOrderDon = QDon.don.ngayTiepNhan.desc();
					listDon = (List<Don>) repo.findAll(donService.predicateFindAll(maDon, tuKhoa, nguonDon, phanLoaiDon, tiepNhanTuNgay, tiepNhanDenNgay,
							hanGiaiQuyetTuNgay, hanGiaiQuyetDenNgay, tinhTrangXuLy, thanhLapDon, trangThaiDon,
							phongBanGiaiQuyet, canBoXuLyXLD, phongBanXuLyXLD, coQuanTiepNhanXLD, donViXuLyXLD, 
							vaiTro, congChuc.getNguoiDung().getVaiTros(), hoTen, trangThaiDonToanHT, ketQuaToanHT, xuLyRepo, repo, giaiQuyetDonRepo,
							coQuyTrinh), 
							sortOrderDon);
				}
			}

//			Pageable pageable = new PageRequest(page != null ? page.intValue() : 0, size != null ? size.intValue() : 10);
//			int start = pageable.getOffset();
//			int end = (start + pageable.getPageSize()) > listDon.size() ? listDon.size() : (start + pageable.getPageSize());
//			Page<Don> pageDon = new PageImpl<Don>(listDon.subList(start, end), pageable, listDon.size());			
			ExcelUtil.exportDanhSachXuLyDon(response, donViXuLyXLD, "DanhSachXuLyDon", "sheetName", listDon, "Danh sách xử lý đơn");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dons/ngayMacDinhThoiHanXuLyDon")
	@ApiOperation(value = "Lấy Ngày mặc định của thời hạn xử lý đơn", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getNgayMacDinhCuaThoiHanXuLyDon(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "type", required = true) String type,
			PersistentEntityResourceAssembler eass) {
		
		try {
			ThamSo thamSo = null;
			if ("DON_KHIEU_NAI".equals(type)) {
				thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_XU_LY_DON_MAC_DINH_KHIEU_NAI"));
			} else if ("DON_TO_CAO".equals(type)) {
				thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_XU_LY_DON_MAC_DINH_TO_CAO"));
			} else if ("DON_KIEN_NGHI_PHAN_ANH".equals(type)) {
				thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_XU_LY_DON_MAC_DINH_KIEN_NGHI_PHAN_ANH"));
			} else if ("DON_TRANH_CHAP_DAT".equals(type)) {
				thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_XU_LY_DON_MAC_DINH_TRANH_CHAP_DAT"));
			}			
			Long soNgayMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 10L;
			LocalDateTime thoiHan = Utils.convertNumberToLocalDateTimeTinhTheoNgayLamViec(Utils.localDateTimeNow(), soNgayMacDinh);
			if (thoiHan == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			return new ResponseEntity<>(thoiHan, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dons/ngayMacDinhThoiHanGiaiQuyetDon")
	@ApiOperation(value = "Lấy Ngày mặc định của thời hạn giải quyết đơn", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getNgayMacDinhCuaThoiHanGiaiQuyeDon(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "type", required = true) String type,
			PersistentEntityResourceAssembler eass) {
		
		try {
			ThamSo thamSo = null;
			if ("DON_KHIEU_NAI".equals(type)) {
				thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_GIAI_QUYET_DON_MAC_DINH_KHIEU_NAI"));
			} else if ("DON_TO_CAO".equals(type)) {
				thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_GIAI_QUYET_DON_MAC_DINH_TO_CAO"));
			} else if ("DON_KIEN_NGHI_PHAN_ANH".equals(type)) {
				thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_GIAI_QUYET_DON_MAC_DINH_KIEN_NGHI_PHAN_ANH"));
			} else if ("DON_TRANH_CHAP_DAT".equals(type)) {
				thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_GIAI_QUYET_DON_MAC_DINH_TRANH_CHAP_DAT"));
			}	
			Long soNgayMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 60L;
			LocalDateTime thoiHan = Utils.convertNumberToLocalDateTimeTinhTheoNgayLamViec(Utils.localDateTimeNow(), soNgayMacDinh);
			if (thoiHan == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			return new ResponseEntity<>(thoiHan, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dons/ngayMacDinhThoiHanTTXM")
	@ApiOperation(value = "Lấy Ngày mặc định của thời hạn thẩm tra xác minh", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getNgayMacDinhCuaThoiHanTTXM(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "type", required = true) String type,
			PersistentEntityResourceAssembler eass) {
		
		try {
			ThamSo thamSo = null;
			if ("DON_KHIEU_NAI".equals(type)) {
				thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_TTXM_MAC_DINH_KHIEU_NAI"));
			} else if ("DON_TO_CAO".equals(type)) {
				thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_TTXM_MAC_DINH_TO_CAO"));
			} else if ("DON_KIEN_NGHI_PHAN_ANH".equals(type)) {
				thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_TTXM_MAC_DINH_KIEN_NGHI_PHAN_ANH"));
			} else if ("DON_TRANH_CHAP_DAT".equals(type)) {
				thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_TTXM_MAC_DINH_TRANH_CHAP_DAT"));
			}	
			Long soNgayMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 20L;
			LocalDateTime thoiHan = Utils.convertNumberToLocalDateTimeTinhTheoNgayLamViec(Utils.localDateTimeNow(), soNgayMacDinh);
			if (thoiHan == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			return new ResponseEntity<>(thoiHan, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dons/ngayMacDinhThoiHanGiaoLapDuThao")
	@ApiOperation(value = "Lấy Ngày mặc định của thời hạn giao lập dự thảo", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getNgayMacDinhCuaThoiHanGiaoLapDuThao(@RequestHeader(value = "Authorization", required = true) String authorization,
			PersistentEntityResourceAssembler eass) {
		
		try {
			ThamSo thamSo = thamSoRepository.findOne(thamSoService.predicateFindTen("HAN_GIAO_LAP_DU_THAO_MAC_DINH"));
			Long soNgayMacDinh = thamSo != null && thamSo.getGiaTri() != null && !"".equals(thamSo.getGiaTri()) ? Long.valueOf(thamSo.getGiaTri()) : 3L;
			LocalDateTime thoiHan = Utils.convertNumberToLocalDateTimeTinhTheoNgayLamViec(Utils.localDateTimeNow(), soNgayMacDinh);
			if (thoiHan == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			return new ResponseEntity<>(thoiHan, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "/dons/{id}/lichSus")
	@ApiOperation(value = "Lấy lịch sử thay đổi của Đơn theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy lịch sử thay đổi của Đơn thành công", response = LichSuThayDoi.class) })
	public PagedResources<Object> getLichSuThayDoiDon(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {
		Page<LichSuThayDoi> page = lichSuRepo
				.findAll(lichSuThayDoiService.predicateFindAll(DoiTuongThayDoiEnum.DON, id), pageable);
		return lichSuThayDoiAssembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/dons/{id}/lichSus/{idLichSu}")
	@ApiOperation(value = "Lấy chi tiết lịch sử thay đổi của Đơn theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy chi tiết lịch sử thay đổi của Đơn thành công", response = LichSuThayDoi.class) })
	public ResponseEntity<Object> getLichSuById(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@PathVariable("idLichSu") long idLichSu, PersistentEntityResourceAssembler eass) {

		LichSuThayDoi lichSuThayDoi = lichSuRepo.findOne(lichSuThayDoiService.predicateFindOne(idLichSu));
		if (lichSuThayDoi == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(lichSuThayDoi), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/dons/layTongSoDonDangXuLy")
	@ApiOperation(value = "Lấy Tổng Số Đơn Đang Xử Lý", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getTongSoDonDangXL(@RequestHeader(value = "Authorization", required = true) String authorization) {
		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			Map<String, Object> map = new HashMap<>();
			Long tongSoDon = 0L;
			boolean coQuyTrinh = true;
			Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Long phongBanXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
			String vaiTroNguoiDungHienTai = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro").toString();
			Long canBoXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			CongChuc congChuc = congChucRepo.findOne(canBoXuLyXLD);
			if (StringUtils.equals(VaiTroEnum.LANH_DAO.name(), vaiTroNguoiDungHienTai) 
					|| StringUtils.equals(VaiTroEnum.VAN_THU.name(), vaiTroNguoiDungHienTai)) {
				phongBanXuLyXLD = 0L;
			}
			List<Don> listDon = new ArrayList<Don>();
			
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

			listDon.addAll((List<Don>) repo.findAll(donService.predicateFindAll(null, null, null, null, null, null,
					null, null, null, true, "DANG_XU_LY",
					null, canBoXuLyXLD, phongBanXuLyXLD, null, donViXuLyXLD, 
					vaiTroNguoiDungHienTai, congChuc.getNguoiDung().getVaiTros(), null, null, null, xuLyRepo, repo, giaiQuyetDonRepo,
					coQuyTrinh)));
			tongSoDon = Long.valueOf(listDon.size());
			map.put("tongSoDon", tongSoDon);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/dons/layTongSoDonDangGiaiQuyet")
	@ApiOperation(value = "Lấy Tổng Số Đơn Đang Giải Quyết", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getTongSoDonDangGQ(@RequestHeader(value = "Authorization", required = true) String authorization) {
		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			Map<String, Object> map = new HashMap<>();
			Long tongSoDon = 0L;
			String vaiTro = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro").toString();
			Long donViGiaiQuyetId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			CongChuc congChuc = congChucRepo.findOne(congChucId);
			List<Don> listDon = new ArrayList<Don>();
			listDon.addAll((List<Don>) repo.findAll(
					donService.predicateFindAllGQD(null, null, null, null, null, null,
							true, null, "DANG_GIAI_QUYET", congChuc.getCoQuanQuanLy().getId(), donViGiaiQuyetId,
							congChuc.getId(), vaiTro, null, null, null, giaiQuyetDonRepo, xuLyRepo)));
			
			tongSoDon = Long.valueOf(listDon.size());
			map.put("tongSoDon", tongSoDon);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	private Don checkDataThongTinDon(Don thongTinDon) {
		if (!thongTinDon.isCoThongTinCoQuanDaGiaiQuyet()) {
			thongTinDon.setCoQuanDaGiaiQuyet(null);
			thongTinDon.setThamQuyenGiaiQuyet(null);
			thongTinDon.setHinhThucDaGiaiQuyet(null);
			thongTinDon.setSoVanBanDaGiaiQuyet("");
			thongTinDon.setNgayBanHanhVanBanDaGiaiQuyet(null);
			thongTinDon.setHuongGiaiQuyetDaThucHien("");
		}
		return thongTinDon;
	}
	
	private void resetDataNguoiUyQuyen(Don don, Long congChucId) {
		if (!don.isCoUyQuyen()) {
			List<Don_CongDan> dcds = (List<Don_CongDan>) donCongDanRepository.findAll(donCongDanService.predicateFindAllByNguoiUyQuyen(don));
			if (dcds != null && dcds.size() > 0) {
				for (Don_CongDan dcd : dcds) {
					dcd.setDaXoa(true);
					donCongDanService.save(dcd, congChucId);
				}
			}
		}
	}
	
}