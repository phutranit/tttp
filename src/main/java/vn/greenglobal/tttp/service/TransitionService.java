package vn.greenglobal.tttp.service;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.FlowStateEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.model.QProcess;
import vn.greenglobal.tttp.model.QTransition;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.ProcessRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class TransitionService {
	
	@Autowired
	private TransitionRepository transitionRepository;

	BooleanExpression base = QTransition.transition.daXoa.eq(false);

	public Predicate predicatePrivileged(State currentState, State nextState, Process process) {
		BooleanExpression predAll = base;
		
		if (currentState != null) {
			predAll = predAll
					.and(QTransition.transition.process.eq(process))
					.and(QTransition.transition.currentState.id.eq(currentState.getId()))
					.and(QTransition.transition.nextState.id.eq(nextState.getId()));
		} else {
			predAll = predAll
					.and(QTransition.transition.process.eq(process))
					.and(QTransition.transition.currentState.type.eq(FlowStateEnum.BAT_DAU))
					.and(QTransition.transition.nextState.id.eq(nextState.getId()));
		}
		
		return predAll;
	}
	
	public Predicate predicateFindFromCurrent(FlowStateEnum current, Process process) {
		BooleanExpression predAll = base;		
		predAll = predAll
				.and(QTransition.transition.process.eq(process))
				.and(QTransition.transition.currentState.type.eq(current));
		
		return predAll;
	}
	
	public Predicate predicateFindFromCurrentAndNext(FlowStateEnum current, FlowStateEnum next, Process process) {
		BooleanExpression predAll = base;		
		predAll = predAll
				.and(QTransition.transition.process.eq(process))
				.and(QTransition.transition.currentState.type.eq(current))
				.and(QTransition.transition.nextState.type.eq(next));
		
		return predAll;
	}
	
	public Predicate predicateFindLast(Long donViId, String processType, ProcessRepository processRepo) {
		BooleanExpression predAll = base;
		BooleanExpression processQuery = QProcess.process.daXoa.eq(false);
		processQuery = processQuery.and(QProcess.process.coQuanQuanLy.id.eq(donViId))
			.and(QProcess.process.processType.eq(ProcessTypeEnum.valueOf(StringUtils.upperCase(processType))));
		List<Process> listProcess = (List<Process>) processRepo.findAll(processQuery);

		predAll = predAll.and(QTransition.transition.process.in(listProcess))
				.and(QTransition.transition.nextState.type.eq(FlowStateEnum.KET_THUC));
		return predAll;
	}
	
	public Predicate predicateFindLastTTXM(Long donViId, String processType, ProcessRepository processRepo, boolean isDuThao) {
		BooleanExpression predAll = base;
		BooleanExpression processQuery = QProcess.process.daXoa.eq(false);
		processQuery = processQuery.and(QProcess.process.coQuanQuanLy.id.eq(donViId))
			.and(QProcess.process.processType.eq(ProcessTypeEnum.valueOf(StringUtils.upperCase(processType))));
		List<Process> listProcess = (List<Process>) processRepo.findAll(processQuery);
		if (isDuThao) {
			predAll = predAll.and(QTransition.transition.process.in(listProcess))
					.and(QTransition.transition.nextState.type.eq(FlowStateEnum.CAN_BO_CHUYEN_DU_THAO_VE_DON_VI_GIAI_QUYET));
		} else {
			predAll = predAll.and(QTransition.transition.process.in(listProcess))
					.and(QTransition.transition.nextState.type.eq(FlowStateEnum.CAN_BO_CHUYEN_VE_DON_VI_GIAI_QUYET));
		}
		
		return predAll;
	}
	
	public Predicate predicateFindLastKTDX(Long donViId, String processType, ProcessRepository processRepo) {
		BooleanExpression predAll = base;
		BooleanExpression processQuery = QProcess.process.daXoa.eq(false);
		processQuery = processQuery.and(QProcess.process.coQuanQuanLy.id.eq(donViId))
			.and(QProcess.process.processType.eq(ProcessTypeEnum.valueOf(StringUtils.upperCase(processType))));
		List<Process> listProcess = (List<Process>) processRepo.findAll(processQuery);

		predAll = predAll.and(QTransition.transition.process.in(listProcess))
				.and(QTransition.transition.nextState.type.eq(FlowStateEnum.CAN_BO_CHUYEN_KET_QUA_DON_VI_GIAO));
		return predAll;
	}
	
	public Predicate predicateFindAll(long congChucId, long nguoiTaoId, String vaiTro, String processType, 
			State currentState, CongChucRepository congChucRepo, ProcessRepository processRepo, ThamSoRepository repoThamSo, ThamSoService thamSoService) {
		BooleanExpression predAll = base;
		CongChuc congChuc = congChucRepo.findOne(congChucId);
		
		boolean isOwner = nguoiTaoId < 1 ? true : congChucId == nguoiTaoId ? true : false;
		
		BooleanExpression processQuery = QProcess.process.daXoa.eq(false);
		
		ThamSo thamSo = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
		CoQuanQuanLy donVi = null;
		if (congChuc != null && congChuc.getCoQuanQuanLy() != null) {
			if (thamSo != null && thamSo.getGiaTri().toString().equals(congChuc.getCoQuanQuanLy().getCapCoQuanQuanLy().getId())) {
				donVi = congChuc.getCoQuanQuanLy().getCha();
			} else {
				donVi = congChuc.getCoQuanQuanLy();
			}
		}
		
		processQuery.and(QProcess.process.vaiTro.loaiVaiTro.eq(VaiTroEnum.valueOf(StringUtils.upperCase(vaiTro))))
			.and(QProcess.process.coQuanQuanLy.eq(donVi))
			.and(QProcess.process.owner.eq(isOwner))
			.and(QProcess.process.processType.eq(ProcessTypeEnum.valueOf(StringUtils.upperCase(processType))));
		
		Process process = processRepo.findOne(processQuery);
		
		predAll = predAll.and(QTransition.transition.process.eq(process)).and(QTransition.transition.currentState.eq(currentState));
		return predAll;
	}
	
	public Predicate predicateFindAll(Long form_id, Long process_id, Long currentState_id, Long nextState_id) { 
		BooleanExpression predAll = base;
		if (form_id != null && form_id > 0) {
			predAll = predAll.and(QTransition.transition.form.id.ne(form_id));
		}
		if (process_id != null && process_id > 0) {
			predAll = predAll.and(QTransition.transition.process.id.ne(process_id));
		}
		if (currentState_id != null && currentState_id > 0) {
			predAll = predAll.and(QTransition.transition.currentState.id.ne(currentState_id));
		}
		if (nextState_id != null && nextState_id > 0) {
			predAll = predAll.and(QTransition.transition.nextState.id.ne(nextState_id));
		}
		return predAll;
	}
	
	public Predicate predicateFindOne(Long id) {
		return base.and(QTransition.transition.id.eq(id));
	}
	
	public boolean checkExistsData(TransitionRepository repo, Transition body) {
		BooleanExpression predAll = base;
		if (!body.isNew()) {
			predAll = predAll.and(QTransition.transition.id.ne(body.getId()));
		}
		predAll = predAll.and(QTransition.transition.nextState.eq(body.getNextState()))
				.and(QTransition.transition.form.eq(body.getForm()))
				.and(QTransition.transition.currentState.eq(body.getCurrentState()))
				.and(QTransition.transition.process.eq(body.getProcess()));
		Transition transition = repo.findOne(predAll);
		return transition != null ? true : false;
	}
	
	public boolean isExists(TransitionRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QTransition.transition.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}
	
	public Transition delete(TransitionRepository repo, Long id) {
		Transition transition = repo.findOne(predicateFindOne(id));

		if (transition != null) {
			transition.setDaXoa(true);
		}

		return transition;
	}
	
	public Transition save(Transition obj, Long congChucId) {
		return Utils.save(transitionRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(Transition obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(transitionRepository, obj, congChucId, eass, status);		
	}
}
