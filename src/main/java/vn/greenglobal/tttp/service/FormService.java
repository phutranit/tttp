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

import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.model.Form;
import vn.greenglobal.tttp.model.QForm;
import vn.greenglobal.tttp.model.QTransition;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.repository.FormRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class FormService {

	@Autowired
	private FormRepository formRepository;

	BooleanExpression base = QForm.form.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa, String processType) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QForm.form.ten.equalsIgnoreCase(tuKhoa))
					.or(QForm.form.alias.equalsIgnoreCase(tuKhoa));
		}
		if (processType != null && StringUtils.isNotBlank(processType)) {
			predAll = predAll.and(QForm.form.processType.eq(ProcessTypeEnum.valueOf(processType)));
		}
		return predAll;
	}

	public boolean checkExistsData(FormRepository repo, Form body) {
		BooleanExpression predAll = base;
		if (!body.isNew()) {
			predAll = predAll.and(QForm.form.id.ne(body.getId()));
		}
		predAll = predAll.and(QForm.form.ten.eq(body.getTen()));
		predAll = predAll.and(QForm.form.alias.eq(body.getAlias()));
		predAll = predAll.and(QForm.form.processType.eq(body.getProcessType()));

		Form form = repo.findOne(predAll);
		return form != null ? true : false;
	}

	public boolean checkUsedData(TransitionRepository repo, Long id) {
		List<Transition> transitionList = (List<Transition>) repo
				.findAll(QTransition.transition.daXoa.eq(false).and(QTransition.transition.form.id.eq(id)));
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

	public Form save(Form obj, Long congChucId) {
		return Utils.save(formRepository, obj, congChucId);
	}

	public ResponseEntity<Object> doSave(Form obj, Long congChucId, PersistentEntityResourceAssembler eass,
			HttpStatus status) {
		return Utils.doSave(formRepository, obj, congChucId, eass, status);
	}
}
