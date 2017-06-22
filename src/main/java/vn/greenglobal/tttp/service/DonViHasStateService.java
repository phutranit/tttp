package vn.greenglobal.tttp.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.model.DonViHasState;
import vn.greenglobal.tttp.model.QDonViHasState;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.repository.DonViHasStateRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class DonViHasStateService {
	
	@Autowired
	private DonViHasStateRepository donViHasStateRepository;
	
	BooleanExpression base = QDonViHasState.donViHasState.daXoa.eq(false);
	
	public Predicate predicateFindAll(Long coQuanQuanLyId, Long stateId, String processType) {
		BooleanExpression predAll = base;
		if (coQuanQuanLyId != null && coQuanQuanLyId > 0) { 
			predAll = predAll.and(QDonViHasState.donViHasState.coQuanQuanLy.id.eq(coQuanQuanLyId));
		}
		if (stateId != null && stateId > 0) { 
			predAll = predAll.and(QDonViHasState.donViHasState.state.id.eq(stateId));
		}
		if (StringUtils.isNotBlank(processType)) { 
			ProcessTypeEnum type = ProcessTypeEnum.valueOf(processType);
			predAll = predAll.and(QDonViHasState.donViHasState.processType.eq(type));
		}
		return predAll;
	}
	
	public boolean checkExistsData(DonViHasStateRepository repo, DonViHasState body) {
		BooleanExpression predAll = base;
		if (!body.isNew()) {
			predAll = predAll.and(QDonViHasState.donViHasState.id.ne(body.getId()));
		}
		ProcessTypeEnum processType = body.getProcessType();
		State state = body.getState();
		
		predAll = predAll.and(QDonViHasState.donViHasState.processType.eq(processType));
		predAll = predAll.and(QDonViHasState.donViHasState.state.eq(state));
		predAll = predAll.and(QDonViHasState.donViHasState.coQuanQuanLy.id.eq(body.getCoQuanQuanLy().getId()));
		
		DonViHasState donViHasState = repo.findOne(predAll);
		return donViHasState != null ? true : false;
	}
	
	public boolean isExists(DonViHasStateRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QDonViHasState.donViHasState.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}
	
	public DonViHasState delete(DonViHasStateRepository repo, Long id) {
		DonViHasState donViHasState = repo.findOne(predicateFindOne(id));

		if (donViHasState != null) {
			donViHasState.setDaXoa(true);
		}

		return donViHasState;
	}
	
	public Predicate predicateFindOne(Long id) {
		return base.and(QDonViHasState.donViHasState.id.eq(id));
	}
	
	public DonViHasState save(DonViHasState obj, Long congChucId) {
		return Utils.save(donViHasStateRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(DonViHasState obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(donViHasStateRepository, obj, congChucId, eass, status);		
	}
	
}
