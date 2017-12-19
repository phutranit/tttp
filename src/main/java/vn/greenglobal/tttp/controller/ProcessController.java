package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.enums.TenQuyTrinhEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.DonViHasState;
import vn.greenglobal.tttp.model.Form;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.model.QDonViHasState;
import vn.greenglobal.tttp.model.QProcess;
import vn.greenglobal.tttp.model.QTransition;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.model.VaiTro;
import vn.greenglobal.tttp.model.medial.Medial_Process_Post_Patch;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.DonViHasStateRepository;
import vn.greenglobal.tttp.repository.ProcessRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.service.CoQuanQuanLyService;
import vn.greenglobal.tttp.service.DonViHasStateService;
import vn.greenglobal.tttp.service.ProcessService;
import vn.greenglobal.tttp.service.TransitionService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "processes", description = "Quy trình")
public class ProcessController extends TttpController<Process> {
	
	@Autowired
	private ProcessRepository processRepo;
	
	@Autowired
	private ProcessService processService;
	
	@Autowired
	private TransitionRepository transitionRepo;
	
	@Autowired
	private TransitionService transitionService;
	
	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepository;
	
	@Autowired
	private CoQuanQuanLyService coQuanQuanLyService;
	
	@Autowired
	private DonViHasStateRepository donViHasStateRepo;
	
	@Autowired
	private DonViHasStateService donViHasStateService;
	
	public ProcessController(BaseRepository<Process, Long> repo) {
		super(repo);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/processes")
	@ApiOperation(value = "Lấy danh sách Quy Trình", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable,  @RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "processType", required = false) String processType,
			@RequestParam(value = "coQuanQuanLyId", required = false) Long coQuanQuanLyId,
			@RequestParam(value = "vaiTroId", required = false) Long vaiTroId,
			PersistentEntityResourceAssembler eass) {
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.PROCESS_LIETKE) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.PROCESS_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Page<Process> page = processRepo.findAll(processService.predicateFindAll(tuKhoa, processType, coQuanQuanLyId, vaiTroId), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/processes/{id}")
	@ApiOperation(value = "Lấy Quy Trình theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Quy Trình thành công", response = Process.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.PROCESS_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Process process = processRepo.findOne(processService.predicateFindOne(id));
			if (process == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(eass.toFullResource(process), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/processes")
	@ApiOperation(value = "Thêm mới Quy Trình", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Quy Trình thành công", response = Process.class),
			@ApiResponse(code = 201, message = "Thêm mới Quy Trình thành công", response = Process.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Process process, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.PROCESS_THEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			if (StringUtils.isNotBlank(process.getTenQuyTrinh()) && process.getCoQuanQuanLy() != null && process.getProcessType() != null && 
					process.getVaiTro() != null &&  processService.checkExistsData(processRepo, process)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_EXISTS.name(),
						ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.DATA_EXISTS.getText());
			}
			
			return processService.doSave(process,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.PATCH, value = "/processes/{id}")
	@ApiOperation(value = "Cập nhật Quy Trình", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Quy Trình thành công", response = Process.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody Process process, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.PROCESS_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			process.setId(id);
			if (!processService.isExists(processRepo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			
			if (StringUtils.isNotBlank(process.getTenQuyTrinh()) && process.getProcessType() != null && process.getCoQuanQuanLy() != null && 
					process.getVaiTro() != null && processService.checkExistsData(processRepo, process)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_EXISTS.name(),
						ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.DATA_EXISTS.getText());
			}
			
			return processService.doSave(process,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/processes/{id}")
	@ApiOperation(value = "Xoá Quy Trình", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Quy Trình thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.PROCESS_XOA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			if (processService.checkUsedData(transitionRepo, id)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_USED.name(),
						ApiErrorEnum.DATA_USED.getText(), ApiErrorEnum.DATA_USED.getText());
			}
			
			Process process = processService.delete(processRepo, id);
			if (process == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			processService.save(process,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST, value = "/maTran")
	@ApiOperation(value = "Thêm mới Ma Trận", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Ma Trận thành công", response = Process.class),
			@ApiResponse(code = 201, message = "Thêm mới Ma Trận thành công", response = Process.class) })
	public ResponseEntity<Object> createMaTran(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_Process_Post_Patch params, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.PROCESS_THEM) == null
					|| Utils.quyenValidate(profileUtil, authorization, QuyenEnum.PROCESS_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			Medial_Process_Post_Patch result = new Medial_Process_Post_Patch();
			
			if (params != null) {
				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
						Process process = params.getProcess();
						if (process != null && params.getTransitions().size() > 0) {
							if (process.getTenQuyTrinh() == null || StringUtils.isBlank(process.getTenQuyTrinh().trim())
									|| process.getProcessType() == null || process.getCoQuanQuanLy() == null
									|| process.getCoQuanQuanLy().getId() <= 0 || process.getVaiTro() == null
									|| process.getVaiTro().getId() <= 0) {
								return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_REQUIRED.name(),
										ApiErrorEnum.DATA_REQUIRED.getText(), ApiErrorEnum.DATA_REQUIRED.getText());
							}
							
							for (Transition transition : params.getTransitions()) {
								if (transition.getForm() == null || transition.getForm().getId() <= 0
										|| transition.getCurrentState() == null || transition.getCurrentState().getId() <= 0
										|| transition.getNextState() == null || transition.getCurrentState().getId() <= 0) {
									return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_REQUIRED.name(),
											ApiErrorEnum.DATA_REQUIRED.getText(), ApiErrorEnum.DATA_REQUIRED.getText());
								}
							}
								
							if (processService.checkExistsData(processRepo, process)) {
								return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_EXISTS.name(),
										ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.DATA_EXISTS.getText());
							}
							
							process = processService.save(params.getProcess(), congChucId);
							if (process != null && process.getId() > 0) {
								result.setProcess(process);
								for (Transition transition : params.getTransitions()) {
									transition.setProcess(process);
									transitionService.save(transition, congChucId);
									result.getTransitions().add(transition);
								}
							}
						}
						return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.CREATED);
					}
				});
			}
			return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PATCH, value = "/maTran")
	@ApiOperation(value = "Cập nhật Ma Trận", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cập nhật Ma Trận thành công", response = Process.class) })
	public ResponseEntity<Object> updateMaTran(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_Process_Post_Patch params, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.PROCESS_THEM) == null
					|| Utils.quyenValidate(profileUtil, authorization, QuyenEnum.PROCESS_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			Medial_Process_Post_Patch result = new Medial_Process_Post_Patch();
			List<Transition> listUpdate = new ArrayList<Transition>();
			
			if (params != null) {
				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						try {
							Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
							Process process = params.getProcess();
							if (process != null && params.getTransitions().size() > 0) {
								if (process.getId() <= 0 || process.getTenQuyTrinh() == null
										|| StringUtils.isBlank(process.getTenQuyTrinh().trim())
										|| process.getProcessType() == null || process.getCoQuanQuanLy() == null
										|| process.getCoQuanQuanLy().getId() <= 0 || process.getVaiTro() == null
										|| process.getVaiTro().getId() <= 0) {
									return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_REQUIRED.name(),
											ApiErrorEnum.DATA_REQUIRED.getText(), ApiErrorEnum.DATA_REQUIRED.getText());
								}
								
								for (Transition transition : params.getTransitions()) {
									transition.setProcess(process);
									if (transition.getId() <= 0 || transition.getForm() == null
											|| transition.getForm().getId() <= 0 || transition.getCurrentState() == null
											|| transition.getCurrentState().getId() <= 0
											|| transition.getNextState() == null
											|| transition.getCurrentState().getId() <= 0) {
										return Utils.responseErrors(HttpStatus.BAD_REQUEST,
												ApiErrorEnum.DATA_REQUIRED.name(), ApiErrorEnum.DATA_REQUIRED.getText(),
												ApiErrorEnum.DATA_REQUIRED.getText());
									}
									
									if (transitionService.checkExistsData(transitionRepo, transition)) {
										return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_EXISTS.name(),
												ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.DATA_EXISTS.getText());
									}
									
									listUpdate.add(transition);
								}
									
								if (processService.checkExistsData(processRepo, process)) {
									return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_EXISTS.name(),
											ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.DATA_EXISTS.getText());
								}
								
								process = processService.save(params.getProcess(), congChucId);
								if (process != null && process.getId() > 0) {
									result.setProcess(process);
									for (Transition transition : listUpdate) {
										transition.setProcess(process);
										transitionService.save(transition, congChucId);
										result.getTransitions().add(transition);
									}
								}
							}
							return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.CREATED);
						} catch (Exception e) {
							return Utils.responseInternalServerErrors(e);
						}
					}
				});
			}
			return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/maTran/auto")
	@ApiOperation(value = "Tự động tạo ma trận cho đơn vị theo quy trình đã chọn", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Tự động tạo ma trận cho đơn vị theo quy trình đã chọn", response = Process.class),
			@ApiResponse(code = 201, message = "Tự động tạo ma trận cho đơn vị theo quy trình đã chọn", response = Process.class) })
	public ResponseEntity<Object> createMaTranAuto(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "coQuyTrinhDayDu", required = true) boolean coQuyTrinhDayDu, @RequestParam(value = "donViId", required = true) Long donViId,
			PersistentEntityResourceAssembler eass) {
		try {
			
			for (int i = 1; i <= 26; i++) {
				Long count = processRepo.count(QProcess.process.coQuanQuanLy.id.eq(Long.valueOf(i+"")));
				Long count2 = transitionRepo.count(QTransition.transition.process.coQuanQuanLy.id.eq(Long.valueOf(i+"")));
				Long count3 = donViHasStateRepo.count(QDonViHasState.donViHasState.coQuanQuanLy.id.eq(Long.valueOf(i+"")));
				if (count == 14 && count2 == 24 && count3 == 25) {
					System.out.println("ID: " + i + " --- OK");
				} else {
					System.out.println("ID: " + i + " --- count: " + count + " --- count2: " + count2 + " --- count3: " + count3);
				}
			}
			
			for (int i = 176; i <= 191; i++) {
				Long count = processRepo.count(QProcess.process.coQuanQuanLy.id.eq(Long.valueOf(i+"")));
				Long count2 = transitionRepo.count(QTransition.transition.process.coQuanQuanLy.id.eq(Long.valueOf(i+"")));
				Long count3 = donViHasStateRepo.count(QDonViHasState.donViHasState.coQuanQuanLy.id.eq(Long.valueOf(i+"")));
				if (count == 14 && count2 == 24 && count3 == 25) {
					System.out.println("ID: " + i + " --- OK");
				} else {
					System.out.println("ID: " + i + " --- count: " + count + " --- count2: " + count2 + " --- count3: " + count3);
				}
			}
			
			for (int i = 27; i <= 82; i++) {
				Long count = processRepo.count(QProcess.process.coQuanQuanLy.id.eq(Long.valueOf(i+"")));
				Long count2 = transitionRepo.count(QTransition.transition.process.coQuanQuanLy.id.eq(Long.valueOf(i+"")));
				Long count3 = donViHasStateRepo.count(QDonViHasState.donViHasState.coQuanQuanLy.id.eq(Long.valueOf(i+"")));
				if (count == 16 && count2 == 24 && count3 == 10) {
					System.out.println("ID: " + i + " --- OK");
				} else {
					System.out.println("ID: " + i + " --- count: " + count + " --- count2: " + count2 + " --- count3: " + count3);
				}
			}
			
			Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.PROCESS_THEM) == null
					|| Utils.quyenValidate(profileUtil, authorization, QuyenEnum.PROCESS_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (coQuanQuanLyRepository.findOne(coQuanQuanLyService.predicateFindOne(donViId)) == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DON_VI_NOT_EXISTS.name(),
						ApiErrorEnum.DON_VI_NOT_EXISTS.getText(), ApiErrorEnum.DON_VI_NOT_EXISTS.getText());
			}
			
			List<Process> list = new ArrayList<Process>();
			list = (List<Process>) processRepo.findAll(processService.predicateFindAllByDonVi(donViId));
			if (list != null && list.size() > 0) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.MA_TRA_EXISTS.name(),
						ApiErrorEnum.MA_TRA_EXISTS.getText(), ApiErrorEnum.MA_TRA_EXISTS.getText());
			}
			
			for (int i = 1; i <= 82; i++) {
				donViId = Long.valueOf(i + "");
				if (i <= 26) {
					coQuyTrinhDayDu = true;
				} else {
					coQuyTrinhDayDu = false;
				}
				
				Process process = null;
				Transition transition = null;
				DonViHasState donViHasState = null;
				
				saveMaTran(coQuyTrinhDayDu, donViHasState, transition, process, i, congChucId, donViId);
			}
			
			for (int i = 176; i <= 191; i++) {
				donViId = Long.valueOf(i + "");
				coQuyTrinhDayDu = false;
				
				Process process = null;
				Transition transition = null;
				DonViHasState donViHasState = null;
				
				saveMaTran(coQuyTrinhDayDu, donViHasState, transition, process, i, congChucId, donViId);
			}
			
			List<Integer> listId = new ArrayList<Integer>();
			listId.add(141);
			listId.add(142);
			listId.add(143);
			listId.add(148);
			listId.add(149);
			
			listId.add(282);
			listId.add(283);
			listId.add(284);
			listId.add(285);
			listId.add(286);
			listId.add(287);
			listId.add(288);
			listId.add(289);
			listId.add(290);
			listId.add(291);
			listId.add(293);
			listId.add(294);
			
			for (Integer idItem : listId) {
				donViId = Long.valueOf(idItem + "");
				coQuyTrinhDayDu = false;
				
				Process process = null;
				Transition transition = null;
				DonViHasState donViHasState = null;
				
				saveMaTran(coQuyTrinhDayDu, donViHasState, transition, process, 0, congChucId, donViId);
			}
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	private Process addDataProcess(ProcessTypeEnum processType, String tenQuyTrinh, boolean isOwner, Long chaId, Long donViId, Long vaiTroId, boolean daXoa) {
		Process process = new Process();
		process.setProcessType(processType);
		process.setTenQuyTrinh(tenQuyTrinh);
		process.setOwner(isOwner);
		if (chaId != null && chaId > 0) {
			Process processCha = new Process();
			processCha.setId(chaId);
			process.setCha(processCha);
		}
		CoQuanQuanLy cq = new CoQuanQuanLy();
		cq.setId(donViId);
		process.setCoQuanQuanLy(cq);
		VaiTro vaiTro = new VaiTro();
		vaiTro.setId(vaiTroId);
		process.setVaiTro(vaiTro);
		process.setDaXoa(daXoa);
		return process;
	}
	
	private Transition addDataTrasition(Process process, Long currentStateId, Long nextStateId, Long formId, boolean daXoa, TenQuyTrinhEnum tenQuyTrinh) {
		Transition transition = new Transition();
		transition.setProcess(process);
		State curentState = new State();
		curentState.setId(currentStateId);
		transition.setCurrentState(curentState);
		State nextState = new State();
		nextState.setId(nextStateId);
		transition.setNextState(nextState);
		Form form = new Form();
		form.setId(formId);
		transition.setForm(form);
		transition.setDaXoa(daXoa);
		transition.setTenQuyTrinh(tenQuyTrinh);
		return transition;
	}
	
	private DonViHasState addDataState(ProcessTypeEnum processType, int soThuTu, Long donViId, Long stateId) {
		DonViHasState donViHasState = new DonViHasState();
		donViHasState.setProcessType(processType);
		donViHasState.setSoThuTu(soThuTu);
		CoQuanQuanLy cq = new CoQuanQuanLy();
		cq.setId(donViId);
		donViHasState.setCoQuanQuanLy(cq);
		State state = new State();
		state.setId(stateId);
		donViHasState.setState(state);
		return donViHasState;
	}
	
	private void saveMaTran(boolean coQuyTrinhDayDu, DonViHasState donViHasState, Transition transition, Process process, int index, Long congChucId, Long donViId) {
		if (coQuyTrinhDayDu) {
			// Don vi has State
			if (index == 1) {
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 1, donViId, 1L);
				donViHasStateService.save(donViHasState, congChucId);
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 5, donViId, 5L);
				donViHasStateService.save(donViHasState, congChucId);
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 6, donViId, 17L);
				donViHasStateService.save(donViHasState, congChucId);
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 7, donViId, 8L);
				donViHasStateService.save(donViHasState, congChucId);
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 8, donViId, 32L);
				donViHasStateService.save(donViHasState, congChucId);
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 9, donViId, 23L);
				donViHasStateService.save(donViHasState, congChucId);
			} else {
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 1, donViId, 1L);
				donViHasStateService.save(donViHasState, congChucId);
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 2, donViId, 2L);
				donViHasStateService.save(donViHasState, congChucId);
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 3, donViId, 3L);
				donViHasStateService.save(donViHasState, congChucId);
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 4, donViId, 9L);
				donViHasStateService.save(donViHasState, congChucId);
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 5, donViId, 5L);
				donViHasStateService.save(donViHasState, congChucId);
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 6, donViId, 17L);
				donViHasStateService.save(donViHasState, congChucId);
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 7, donViId, 8L);
				donViHasStateService.save(donViHasState, congChucId);
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 8, donViId, 22L);
				donViHasStateService.save(donViHasState, congChucId);
				donViHasState = new DonViHasState();
				donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 9, donViId, 23L);
				donViHasStateService.save(donViHasState, congChucId);
			}
			
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 1, donViId, 1L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 2, donViId, 5L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 3, donViId, 19L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 4, donViId, 20L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 5, donViId, 23L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 6, donViId, 24L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 7, donViId, 25L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 8, donViId, 26L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 9, donViId, 27L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 10, donViId, 8L);
			donViHasStateService.save(donViHasState, congChucId);
			
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 11, donViId, 33L);
			donViHasStateService.save(donViHasState, congChucId);
			
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THAM_TRA_XAC_MINH, 1, donViId, 1L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THAM_TRA_XAC_MINH, 2, donViId, 2L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THAM_TRA_XAC_MINH, 3, donViId, 3L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THAM_TRA_XAC_MINH, 4, donViId, 9L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THAM_TRA_XAC_MINH, 5, donViId, 5L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THAM_TRA_XAC_MINH, 6, donViId, 15L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THAM_TRA_XAC_MINH, 7, donViId, 22L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THAM_TRA_XAC_MINH, 8, donViId, 23L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THAM_TRA_XAC_MINH, 9, donViId, 28L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THAM_TRA_XAC_MINH, 10, donViId, 29L);
			donViHasStateService.save(donViHasState, congChucId);
			
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.KIEM_TRA_DE_XUAT, 1, donViId, 1L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.KIEM_TRA_DE_XUAT, 2, donViId, 2L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.KIEM_TRA_DE_XUAT, 3, donViId, 3L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.KIEM_TRA_DE_XUAT, 4, donViId, 9L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.KIEM_TRA_DE_XUAT, 5, donViId, 5L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.KIEM_TRA_DE_XUAT, 6, donViId, 16L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.KIEM_TRA_DE_XUAT, 7, donViId, 22L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.KIEM_TRA_DE_XUAT, 8, donViId, 23L);
			donViHasStateService.save(donViHasState, congChucId);
			
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THEO_DOI_THUC_HIEN, 1, donViId, 1L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THEO_DOI_THUC_HIEN, 2, donViId, 2L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THEO_DOI_THUC_HIEN, 3, donViId, 3L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THEO_DOI_THUC_HIEN, 4, donViId, 9L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THEO_DOI_THUC_HIEN, 5, donViId, 5L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THEO_DOI_THUC_HIEN, 6, donViId, 34L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THEO_DOI_THUC_HIEN, 7, donViId, 22L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THEO_DOI_THUC_HIEN, 8, donViId, 23L);
			donViHasStateService.save(donViHasState, congChucId);
			
			// Xu Ly Don
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.XU_LY_DON, "Xử lý đơn của Chuyên Viên", false, null, donViId, 3L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {	
				if (index == 1) {
					// Chuyên viên xử lý đề xuất hướng xử lý sau khi được trưởng phòng giao việc
					transition = addDataTrasition(process, 5L, 8L, 4L, false, TenQuyTrinhEnum.QUY_TRINH_BAN_TIEP_CONG_DAN_I);
					transitionService.save(transition, congChucId);
					// Chuyên viên xử lý nhận đơn và trình trưởng phòng
					transition = addDataTrasition(process, 1L, 32L, 1L, false, TenQuyTrinhEnum.QUY_TRINH_BAN_TIEP_CONG_DAN_I);
					transitionService.save(transition, congChucId);
					// Chuyên viên xử lý nhận đơn và gửi yêu cầu gặp lãnh đạo
					transition = addDataTrasition(process, 1L, 17L, 1L, false, TenQuyTrinhEnum.QUY_TRINH_BAN_TIEP_CONG_DAN_I);
					transitionService.save(transition, congChucId);
				} else {
					// Chuyên viên xử lý nhận đơn và trình lãnh đạo
					transition = addDataTrasition(process, 1L, 2L, 1L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
					// Chuyên viên xử lý đề xuất hướng xử lý sau khi được trưởng phòng giao việc
					transition = addDataTrasition(process, 5L, 8L, 4L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
					// Chuyên viên xử lý đề xuất hướng xử lý sau khi được lãnh đạo giao việc
					transition = addDataTrasition(process, 9L, 8L, 4L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
					// Chuyên viên xử lý nhận đơn và gửi yêu cầu gặp lãnh đạo
					transition = addDataTrasition(process, 1L, 17L, 1L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
				}						
			}
			
//			process = new Process();
//			transition = new Transition();
//			process = addDataProcess(ProcessTypeEnum.XU_LY_DON, "Xử lý đơn của Chuyên Viên nhập liệu", true, 1L, donViId, 4L, false);
//			process = processService.save(process, congChucId);
//			if (process != null && process.getId() != null && process.getId() > 0) {
//				transition = addDataTrasition(process, 1L, 2L, 1L, false);
//				transitionService.save(transition, congChucId);
//				transition = addDataTrasition(process, 1L, 17L, 1L, false);
//				transitionService.save(transition, congChucId);
//			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.XU_LY_DON, "Xử lý đơn của Trưởng Phòng", false, null, donViId, 2L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {	
				if (index == 1) {
					// Trưởng phòng giao viêc cán bộ sau khi được Chuyên viên xử lý hoặc chuyên viên nhập liệu trình đơn
					transition = addDataTrasition(process, 32L, 5L, 3L, false, TenQuyTrinhEnum.QUY_TRINH_BAN_TIEP_CONG_DAN_I);
					transitionService.save(transition, congChucId);
					// Trưởng phòng tự xử lý sau khi được Chuyên viên xử lý hoặc chuyên viên nhập liệu trình đơn
					transition = addDataTrasition(process, 32L, 8L, 3L, false, TenQuyTrinhEnum.QUY_TRINH_BAN_TIEP_CONG_DAN_I);
					transitionService.save(transition, congChucId);
					// Trưởng phòng giao việc cán bộ sau khi thu hồi đơn
					transition = addDataTrasition(process, 23L, 5L, 3L, false, TenQuyTrinhEnum.QUY_TRINH_BAN_TIEP_CONG_DAN_I);
					transitionService.save(transition, congChucId);
					// Trưởng phòng tự xử lý sau thu hồi đơn
					transition = addDataTrasition(process, 23L, 8L, 3L, false, TenQuyTrinhEnum.QUY_TRINH_BAN_TIEP_CONG_DAN_I);
					transitionService.save(transition, congChucId);
					// Trưởng phòng thu hồi đơn sau khi giao việc cho cán bộ
					transition = addDataTrasition(process, 5L, 23L, 30L, false, TenQuyTrinhEnum.QUY_TRINH_BAN_TIEP_CONG_DAN_I);
					transitionService.save(transition, congChucId);
				} else {
					// Trưởng phòng giao việc cán bộ sau khi được lãnh đạo giao việc
					transition = addDataTrasition(process, 3L, 5L, 3L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
					// Trưởng phòng giao việc cán bộ sau khi thu hồi đơn
					transition = addDataTrasition(process, 23L, 5L, 3L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
					// Trưởng phòng thu hồi đơn sau khi giao việc cho cán bộ
					transition = addDataTrasition(process, 5L, 23L, 30L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
					// Trưởng phòng tự xử lý sau khi lãnh đạo giao viêc
					transition = addDataTrasition(process, 3L, 8L, 3L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
					// Trưởng phòng tự xử lý sau thu hồi đơn
					transition = addDataTrasition(process, 23L, 8L, 3L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
				}
			}
			
			if (index != 1) {
				process = new Process();
				transition = new Transition();
				process = addDataProcess(ProcessTypeEnum.XU_LY_DON, "Xử lý đơn của Lãnh Đạo", false, null, donViId, 1L, false);
				process = processService.save(process, congChucId);
				if (process != null && process.getId() != null && process.getId() > 0) {
					// Lãnh đạo giao việc trường phòng sau khi 
					transition = addDataTrasition(process, 2L, 3L, 2L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
					// Lãnh đạo giao việc cán bộ sau khi được chuyên viên nhập liệu hoặc chuyên viên xử lý trình đơn
					transition = addDataTrasition(process, 2L, 9L, 2L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
					// Lãnh đạo thu hồi sau khi giao việc trưởng phòng
					transition = addDataTrasition(process, 3L, 22L, 30L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
					// Lãnh đạo thu hồi sau khi giao việc chuyên viên xử lý
					transition = addDataTrasition(process, 9L, 22L, 30L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
					// Lãnh đạo giao việc trưởng phòng sau khi thu hồi đơn
					transition = addDataTrasition(process, 22L, 3L, 2L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
					// Lãnh đạo giao việc cán bộ sau khi thu hồi đơn
					transition = addDataTrasition(process, 22L, 9L, 2L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
				}
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.XU_LY_DON, "Xử lý đơn của Chuyên Viên nhập liệu", false, null, donViId, 4L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				if (index == 1) {
					// Chuyên viên nhập liệu nhận đơn và trình trưởng phòng
					transition = addDataTrasition(process, 1L, 32L, 1L, false, TenQuyTrinhEnum.QUY_TRINH_BAN_TIEP_CONG_DAN_I);
					transitionService.save(transition, congChucId);
					// Chuyên viên nhập liệu nhận đơn và gửi yêu cầu gặp lãnh đạo
					transition = addDataTrasition(process, 1L, 17L, 1L, false, TenQuyTrinhEnum.QUY_TRINH_BAN_TIEP_CONG_DAN_I);
					transitionService.save(transition, congChucId);
				} else {
					// Chuyên viên nhập liệu nhận đơn và trình lãnh đạo
					transition = addDataTrasition(process, 1L, 2L, 1L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
					// Chuyên viên nhập liệu gửi yêu cầu gặp lãnh đạo
					transition = addDataTrasition(process, 1L, 17L, 1L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
					transitionService.save(transition, congChucId);
				}
			}
			
			// Giai Quyet Don
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.GIAI_QUYET_DON, "Giải quyết đơn của Chuyên Viên", false, null, donViId, 3L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				// Chuyên viên xử lý giải quyết đơn sau khi trưởng phòng giao việc chuyên viên xử lý
				transition = addDataTrasition(process, 5L, 8L, 12L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				// Chuyên viên xử lý giải quyết đơn sau khi trưởng phòng nhận kết quả thẩm tra xác minh và giao việc chuyên viên xử lý
				transition = addDataTrasition(process, 24L, 8L, 18L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				// Chuyên viên xử lý giải quyết đơn sau khi trưởng phòng nhận dự thảo và giao việc chuyên viên xử lý
				transition = addDataTrasition(process, 27L, 8L, 41L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				// Chuyên viên xử lý đưa yêu càu theo dõi thực hiện sau khi trưởng phòng nhận dự thảo và giao việc chuyên viên xử lý
				transition = addDataTrasition(process, 27L, 33L, 41L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				// Chuyên viên xử lý giao đơn vị TTXM lập dự thảo sau khi trưởng phòng nhận kết quả thẩm tra xác minh và giao việc chuyên viên xử lý
				transition = addDataTrasition(process, 24L, 25L, 38L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				// Chuyên viên xử lý đưa yêu cầu theo dõi thực hiện sau khi trưởng phòng nhận kết quả thẩm tra xác minh và giao việc chuyên viên xử lý
				transition = addDataTrasition(process, 24L, 33L, 18L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.GIAI_QUYET_DON, "Giải quyết đơn của Trưởng Phòng", false, null, donViId, 2L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				// Trưởng phòng giao viêc chuyên viên xử lý sau khi nhận giải quyết đơn
				transition = addDataTrasition(process, 1L, 5L, 11L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				// Trưởng phòng tự xử lý chuyên cho đơn vị TTXM
				transition = addDataTrasition(process, 1L, 20L, 11L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				// 
				transition = addDataTrasition(process, 20L, 19L, 29L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 19L, 19L, 31L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);						
				transition = addDataTrasition(process, 19L, 24L, 31L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);						
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 19L, 33L, 31L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);						
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 19L, 8L, 31L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 19L, 25L, 31L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);		
				transition = addDataTrasition(process, 23L, 24L, 31L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);						
				transition = addDataTrasition(process, 23L, 19L, 31L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);	
				transition = addDataTrasition(process, 23L, 8L, 31L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);						
				transition = addDataTrasition(process, 23L, 25L, 31L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);	
				transition = addDataTrasition(process, 24L, 23L, 32L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 25L, 26L, 37L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 27L, 31L, 32L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);		
				
				transition = addDataTrasition(process, 31L, 27L, 37L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 31L, 8L, 37L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);						
								
				transition = addDataTrasition(process, 26L, 27L, 37L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 26L, 8L, 37L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 26L, 8L, 33L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
			}
			
			// Tham Tra Xac Minh
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THAM_TRA_XAC_MINH, "Thẩm tra xác minh của Chuyên Viên", false, null, donViId, 3L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 9L, 15L, 17L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 5L, 15L, 17L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 30L, 29L, 40L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 1L, 2L, 14L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THAM_TRA_XAC_MINH, "Thẩm tra xác minh của Trưởng Phòng", false, null, donViId, 2L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 3L, 5L, 16L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 23L, 5L, 16L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 5L, 23L, 33L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 15L, 28L, 39L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 28L, 30L, 39L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);		
				transition = addDataTrasition(process, 31L, 29L, 39L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 31L, 30L, 39L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 30L, 31L, 33L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);	
				transition = addDataTrasition(process, 3L, 15L, 17L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 23L, 15L, 16L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 28L, 29L, 39L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THAM_TRA_XAC_MINH, "Thẩm tra xác minh của Lãnh Đạo", false, null, donViId, 1L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 2L, 3L, 15L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 2L, 9L, 15L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 22L, 3L, 15L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 22L, 9L, 15L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 3L, 22L, 33L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 9L, 22L, 33L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THAM_TRA_XAC_MINH, "Thẩm tra xác minh của Chuyên Viên nhập liệu", false, null, donViId, 4L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 2L, 14L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
			}
			
			// Kiem Tra De Xuat
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.KIEM_TRA_DE_XUAT, "Kiểm tra đề xuất của Chuyên Viên", false, null, donViId, 3L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 5L, 16L, 22L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 9L, 16L, 22L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 1L, 2L, 19L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.KIEM_TRA_DE_XUAT, "Kiểm tra đề xuất của Trưởng Phòng", false, null, donViId, 2L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {	
				transition = addDataTrasition(process, 3L, 5L, 21L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 23L, 5L, 21L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 5L, 23L, 34L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 3L, 16L, 21L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 23L, 16L, 21L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.KIEM_TRA_DE_XUAT, "Kiểm tra đề xuất của Lãnh Đạo", false, null, donViId, 1L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {						
				transition = addDataTrasition(process, 2L, 3L, 20L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 2L, 9L, 20L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 22L, 3L, 20L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 22L, 9L, 20L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 3L, 22L, 34L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 9L, 22L, 34L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.KIEM_TRA_DE_XUAT, "Kiểm tra đề xuất của Chuyên viên nhập liệu", false, null, donViId, 4L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 2L, 19L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THEO_DOI_THUC_HIEN, "Theo dõi thực hiện của Lãnh Đạo", false, null, donViId, 1L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				//Lanh dao giao viec truong phong
				transition = addDataTrasition(process, 2L, 3L, 47L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				//Lanh dao giao viec truong phong sau khi thu hoi
				transition = addDataTrasition(process, 22L, 3L, 47L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				//Lanh dao giao viec cán bộ
				transition = addDataTrasition(process, 2L, 9L, 47L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				//Lanh dao giao viec cán bộ sau khi thu hoi
				transition = addDataTrasition(process, 22L, 9L, 47L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				//Lanh dao thu hồi đơn
				transition = addDataTrasition(process, 3L, 22L, 48L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				//Lanh dao thu hồi đơn
				transition = addDataTrasition(process, 9L, 22L, 48L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THEO_DOI_THUC_HIEN, "Theo dõi thực hiện của Chuyên viên", false, null, donViId, 3L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				//Cán bộ lưu đơn sau khi duoc truong phong giao viec
				transition = addDataTrasition(process, 5L, 34L, 46L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				//Cán bộ lưu đơn sau khi duoc lanh dao giao viec
				transition = addDataTrasition(process, 9L, 34L, 46L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				//Chuyen vien trinh lanh dao
				transition = addDataTrasition(process, 1L, 2L, 45L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THEO_DOI_THUC_HIEN, "Theo dõi thực hiện của Chuyên viên nhập liệu", false, null, donViId, 4L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				//Chuyen vien nhap lieu trinh lanh dao
				transition = addDataTrasition(process, 1L, 2L, 45L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THEO_DOI_THUC_HIEN, "Theo dõi thực hiện của Trưởng phòng", false, null, donViId, 2L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				//Trưởng phòng giao việc cán bộ sau khi được lãnh đạo giao
				transition = addDataTrasition(process, 3L, 5L, 49L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				//Trưởng phòng tự lưu đơn sau khi được lãnh đạo giao
				transition = addDataTrasition(process, 3L, 34L, 49L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				//Trưởng phòng giao việc cán bộ sau khi thu hồi đơn
				transition = addDataTrasition(process, 23L, 5L, 49L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				//Trưởng phòng tự lưu đơn sau khi thu hồi đơn
				transition = addDataTrasition(process, 23L, 34L, 49L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				//Trưởng phòng giao việc cán bộ nếu đơn vị cũng là đơn vị TTXM
				transition = addDataTrasition(process, 1L, 5L, 49L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				//Trưởng phòng thu hồi đơn
				transition = addDataTrasition(process, 5L, 23L, 48L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
				//Trưởng phòng tự lưu đơn nếu đơn vị cũng là đơn vị TTXM
				transition = addDataTrasition(process, 1L, 34L, 49L, false, TenQuyTrinhEnum.QUY_TRINH_4_BUOC_DAY_DU_I);
				transitionService.save(transition, congChucId);
			}
		} else {
			// Don vi has State
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 1, donViId, 1L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.XU_LY_DON, 2, donViId, 8L);
			donViHasStateService.save(donViHasState, congChucId);
			
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 1, donViId, 1L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 2, donViId, 19L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 3, donViId, 20L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 4, donViId, 25L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 5, donViId, 26L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.GIAI_QUYET_DON, 6, donViId, 8L);
			donViHasStateService.save(donViHasState, congChucId);
			
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THAM_TRA_XAC_MINH, 1, donViId, 1L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THAM_TRA_XAC_MINH, 2, donViId, 15L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THAM_TRA_XAC_MINH, 2, donViId, 28L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THAM_TRA_XAC_MINH, 2, donViId, 29L);
			donViHasStateService.save(donViHasState, congChucId);
			
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.KIEM_TRA_DE_XUAT, 1, donViId, 1L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.KIEM_TRA_DE_XUAT, 2, donViId, 16L);
			donViHasStateService.save(donViHasState, congChucId);
			
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THEO_DOI_THUC_HIEN, 1, donViId, 1L);
			donViHasStateService.save(donViHasState, congChucId);
			donViHasState = new DonViHasState();
			donViHasState = addDataState(ProcessTypeEnum.THEO_DOI_THUC_HIEN, 2, donViId, 34L);
			donViHasStateService.save(donViHasState, congChucId);
			
			// Xu Ly Don
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.XU_LY_DON, "Xử lý đơn của Chuyên Viên", false, null, donViId, 3L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 8L, 23L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.XU_LY_DON, "Xử lý đơn của Chuyên Viên nhập liệu", false, null, donViId, 4L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 8L, 23L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.XU_LY_DON, "Xử lý đơn của Trưởng Phòng", false, null, donViId, 2L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 8L, 23L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.XU_LY_DON, "Xử lý đơn của Lãnh Đạo", false, null, donViId, 1L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 8L, 23L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			// Giai Quyet Don
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.GIAI_QUYET_DON, "Giải quyết đơn của Chuyên Viên", false, null, donViId, 3L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 8L, 24L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 1L, 20L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 20L, 19L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 19L, 8L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 19L, 25L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 25L, 26L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 26L, 8L, 43L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.GIAI_QUYET_DON, "Giải quyết đơn của Chuyên viên nhập liệu", false, null, donViId, 4L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 8L, 24L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 1L, 20L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 20L, 19L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 19L, 8L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 19L, 25L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 25L, 26L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 26L, 8L, 43L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.GIAI_QUYET_DON, "Giải quyết đơn của Trưởng Phòng", false, null, donViId, 2L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 8L, 24L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 1L, 20L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 20L, 19L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 19L, 8L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 19L, 25L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 25L, 26L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 26L, 8L, 43L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.GIAI_QUYET_DON, "Giải quyết đơn của Lãnh Đạo", false, null, donViId, 1L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 8L, 24L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 1L, 20L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 20L, 19L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 19L, 8L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 19L, 25L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 25L, 26L, 26L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 26L, 8L, 43L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			// Tham Tra Xac Minh
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THAM_TRA_XAC_MINH, "Thẩm tra xác minh của Chuyen Viên", false, null, donViId, 3L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 15L, 25L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 15L, 28L, 44L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 28L, 29L, 44L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THAM_TRA_XAC_MINH, "Thẩm tra xác minh của Chuyên Viên nhập liệu", false, null, donViId, 4L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 15L, 25L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 15L, 28L, 44L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 28L, 29L, 44L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THAM_TRA_XAC_MINH, "Thẩm tra xác minh của Trưởng Phòng", false, null, donViId, 2L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 15L, 25L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 15L, 28L, 44L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 28L, 29L, 44L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THAM_TRA_XAC_MINH, "Thẩm tra xác minh của Lãnh Đạo", false, null, donViId, 1L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 15L, 25L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 15L, 28L, 44L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
				transition = addDataTrasition(process, 28L, 29L, 44L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			// Kiem Tra De Xuat
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.KIEM_TRA_DE_XUAT, "Kiểm tra đề xuất của Chuyên Viên", false, null, donViId, 3L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 16L, 27L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.KIEM_TRA_DE_XUAT, "Kiểm tra đề xuất của Chuyên viên nhập liệu", false, null, donViId, 4L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 16L, 27L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.KIEM_TRA_DE_XUAT, "Kiểm tra đề xuất của Trưởng Phòng", false, null, donViId, 2L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 16L, 27L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.KIEM_TRA_DE_XUAT, "Kiểm tra đề xuất của Lãnh Đạo", false, null, donViId, 1L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 16L, 27L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THEO_DOI_THUC_HIEN, "Theo dõi thực hiện của Lãnh Đạo", false, null, donViId, 1L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 34L, 50L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THEO_DOI_THUC_HIEN, "Theo dõi thực hiện của Trưởng phòng", false, null, donViId, 2L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 34L, 50L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THEO_DOI_THUC_HIEN, "Theo dõi thực hiện của Chuyên viên nhập liệu", false, null, donViId, 4L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 34L, 50L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
			
			process = new Process();
			transition = new Transition();
			process = addDataProcess(ProcessTypeEnum.THEO_DOI_THUC_HIEN, "Theo dõi thực hiện của Chuyên viên", false, null, donViId, 3L, false);
			process = processService.save(process, congChucId);
			if (process != null && process.getId() != null && process.getId() > 0) {
				transition = addDataTrasition(process, 1L, 34L, 50L, false, TenQuyTrinhEnum.QUY_TRINH_1_BUOC_KHONG_DAY_DU);
				transitionService.save(transition, congChucId);
			}
		}
	}
}
