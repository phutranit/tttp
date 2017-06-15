package vn.greenglobal.tttp.service;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.model.Form;
import vn.greenglobal.tttp.model.QForm;
import vn.greenglobal.tttp.model.QTransition;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.repository.FormRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;

@Component
public class FormService {

	BooleanExpression base = QForm.form.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa, String processType) {
		BooleanExpression predAll = base;
		if (StringUtils.isNotBlank(tuKhoa)) { 
			predAll = predAll.and(QForm.form.ten.eq(tuKhoa))
					.or(QForm.form.alias.eq(tuKhoa));
		}
		if (StringUtils.isNotBlank(processType)) { 
			ProcessTypeEnum type = ProcessTypeEnum.valueOf(processType);
			predAll = predAll.and(QForm.form.processType.eq(type));
		}
		return predAll;
	}
	
	public boolean checkExistsData(FormRepository repo, Form body) {
		BooleanExpression predAll = base;
		if (!body.isNew()) {
			predAll = predAll.and(QForm.form.id.ne(body.getId()));
		}
		ProcessTypeEnum processType = body.getProcessType();
		predAll = predAll.and(QForm.form.alias.eq(body.getAlias()))
				.and(QForm.form.processType.eq(processType));
		
		Form form = repo.findOne(predAll);
		return form != null ? true : false;
	}
	
	public boolean checkUsedData(TransitionRepository repo, Long id) {
		List<Transition> transitionList = (List<Transition>) repo.findAll(QTransition.transition.daXoa.eq(false)
						.and(QTransition.transition.form.id.eq(id)));
		if (transitionList != null && transitionList.size() > 0) {
			return true;
		}
		return false;
	}
	
	public boolean isExists(FormRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QForm.form.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public Form delete(FormRepository repo, Long id) {
		Form form = repo.findOne(predicateFindOne(id));

		if (form != null) {
			form.setDaXoa(true);
		}

		return form;
	}
	
	public Predicate predicateFindOne(Long id) {
		return base.and(QForm.form.id.eq(id));
	}
}
