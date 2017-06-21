package vn.greenglobal.tttp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QState;
import vn.greenglobal.tttp.model.QTransition;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.repository.DonViHasStateRepository;
import vn.greenglobal.tttp.repository.StateRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.util.Utils;
import vn.greenglobal.tttp.enums.FlowStateEnum;
import vn.greenglobal.tttp.model.DonViHasState;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.model.QDonViHasState;

@Component
public class StateService {

	@Autowired
	private StateRepository stateRepository;

	BooleanExpression base = QState.state.daXoa.eq(false);

	public Predicate predicateFindAll(Long currentStateId, Process process, TransitionRepository transitionRepo) {
		BooleanExpression predAll = base;
		BooleanExpression transitionQuery = QTransition.transition.daXoa.eq(false);
		transitionQuery = transitionQuery.and(QTransition.transition.process.eq(process))
				.and(QTransition.transition.currentState.id.eq(currentStateId));

		Collection<Transition> transitionCollections = new ArrayList<Transition>();
		Collection<State> stateCollections = new ArrayList<State>();
		Iterable<Transition> transitions = transitionRepo.findAll(transitionQuery);

		CollectionUtils.addAll(transitionCollections, transitions.iterator());
		stateCollections = transitionCollections.stream().map(d -> d.getNextState()).distinct()
				.collect(Collectors.toList());
		predAll = predAll.and(QState.state.in(stateCollections));

		return predAll;
	}

	public Predicate predicateFindAll(String tuKhoa, String type) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QState.state.ten.containsIgnoreCase(tuKhoa.trim())
					.or(QState.state.tenVietTat.containsIgnoreCase(tuKhoa.trim())));
		}

		if (type != null && StringUtils.isNotBlank(type)) {
			predAll = predAll.and(QState.state.type.eq(FlowStateEnum.valueOf(type)));
		}
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QState.state.id.eq(id));
	}

	public Predicate predicateFindByType(FlowStateEnum type) {
		return base.and(QState.state.type.eq(type));
	}

	public boolean isExists(StateRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QState.state.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public boolean checkExistsData(StateRepository repo, State body) {
		BooleanExpression predAll = base;
		if (!body.isNew()) {
			predAll = predAll.and(QState.state.id.ne(body.getId()));
		}
		predAll = predAll.and(QState.state.ten.eq(body.getTen()));
		predAll = predAll.and(QState.state.type.eq(body.getType()));
		State state = repo.findOne(predAll);
		return state != null ? true : false;
	}

	public State delete(StateRepository repo, Long id) {
		State state = repo.findOne(predicateFindOne(id));

		if (state != null) {
			state.setDaXoa(true);
		}

		return state;
	}

	public boolean checkUsedData(DonViHasStateRepository donViHasStateRepository, TransitionRepository transitionRepo,
			Long id) {
		List<DonViHasState> donViHasStates = (List<DonViHasState>) donViHasStateRepository.findAll(
				QDonViHasState.donViHasState.daXoa.eq(false).and(QDonViHasState.donViHasState.state.id.eq(id)));
		List<Transition> transitions = (List<Transition>) transitionRepo.findAll(QTransition.transition.daXoa.eq(false)
				.and(QTransition.transition.currentState.id.eq(id)).or(QTransition.transition.nextState.id.eq(id)));

		if ((donViHasStates != null && donViHasStates.size() > 0) || (transitions != null && transitions.size() > 0)) {
			return true;
		}

		return false;
	}

	public State save(State obj, Long congChucId) {
		return Utils.save(stateRepository, obj, congChucId);
	}

	public ResponseEntity<Object> doSave(State obj, Long congChucId, PersistentEntityResourceAssembler eass,
			HttpStatus status) {
		return Utils.doSave(stateRepository, obj, congChucId, eass, status);
	}

}
