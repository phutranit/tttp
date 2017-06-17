package vn.greenglobal.tttp.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QState;
import vn.greenglobal.tttp.model.QTransition;
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.model.Transition;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.enums.FlowStateEnum;
import vn.greenglobal.tttp.model.Process;

@Component
public class StateService {

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
		stateCollections = transitionCollections.stream().map(d -> d.getNextState()).distinct().collect(Collectors.toList());
		predAll = predAll.and(QState.state.in(stateCollections));
		
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QState.state.id.eq(id));
	}
	
	public Predicate predicateFindByType(FlowStateEnum type) {
		return base.and(QState.state.type.eq(type));
	}
}
