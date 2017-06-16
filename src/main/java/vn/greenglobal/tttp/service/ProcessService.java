package vn.greenglobal.tttp.service;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.QProcess;
import vn.greenglobal.tttp.model.QTransition;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.repository.ProcessRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;

@Component
public class ProcessService {

	BooleanExpression base = QProcess.process.daXoa.eq(false);

	public Predicate predicateFindAll(String vaiTro, CoQuanQuanLy donVi, boolean isOwner, ProcessTypeEnum processType) {
		BooleanExpression predAll = base;
		predAll = predAll.and(QProcess.process.vaiTro.loaiVaiTro.eq(VaiTroEnum.valueOf(StringUtils.upperCase(vaiTro))))
			.and(QProcess.process.coQuanQuanLy.eq(donVi))
			.and(QProcess.process.owner.eq(isOwner))
			.and(QProcess.process.processType.eq(processType));
		return predAll;
	}
	
	public Predicate predicateFindAll(String tuKhoa, String type) {
		BooleanExpression predAll = base;
		if (StringUtils.isNotBlank(tuKhoa)) { 
			predAll = predAll.and(QProcess.process.tenQuyTrinh.eq(tuKhoa));
		}
		if (StringUtils.isNotBlank(type)) { 
			ProcessTypeEnum processType = ProcessTypeEnum.valueOf(type);
			predAll = predAll.and(QProcess.process.processType.eq(processType));
		}
		return predAll;
	}
	
	public Predicate predicateFindOne(Long id) {
		return base.and(QProcess.process.id.eq(id));
	}
	
	public boolean isExists(ProcessRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QProcess.process.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}
	
	public boolean checkExistsData(ProcessRepository repo, Process body) {
		BooleanExpression predAll = base;
		if (!body.isNew()) {
			predAll = predAll.and(QProcess.process.id.ne(body.getId()));
		}
		predAll = predAll.and(QProcess.process.tenQuyTrinh.eq(body.getTenQuyTrinh()))
				.and(QProcess.process.coQuanQuanLy.id.eq(body.getCoQuanQuanLy().getId()))
				.and(QProcess.process.vaiTro.id.eq(body.getVaiTro().getId()));
		ProcessTypeEnum processType = body.getProcessType();
		predAll = predAll.and(QProcess.process.processType.eq(processType));
		Process process = repo.findOne(predAll);
		return process != null ? true : false;
	}
	
	public boolean checkUsedData(TransitionRepository transitionRepo, Long id) {
		List<Transition> transitions = (List<Transition>) transitionRepo
				.findAll(QTransition.transition.daXoa.eq(false)
						.and(QTransition.transition.process.id.eq(id)));
		
		if (transitions != null && transitions.size() > 0) {
			return true;
		}
		
		return false;
	}
	
	public Process delete(ProcessRepository repo, Long id) {
		Process process = repo.findOne(predicateFindOne(id));

		if (process != null) {
			process.setDaXoa(true);
		}

		return process;
	}
	
	public Predicate predicateFindAllByDonVi(CoQuanQuanLy donVi, ProcessTypeEnum processType) {
		BooleanExpression predAll = base;
		predAll = predAll
			.and(QProcess.process.coQuanQuanLy.eq(donVi))
			.and(QProcess.process.processType.eq(processType));
		return predAll;
	}

}
