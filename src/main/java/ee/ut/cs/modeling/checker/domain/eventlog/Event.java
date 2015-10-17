package ee.ut.cs.modeling.checker.domain.eventlog;

import ee.ut.cs.modeling.checker.domain.petrinet.node.Transition;

public final class Event {

	private final Transition transition;

	public Event(Transition transition) {
		this.transition = transition;
	}

	public Transition getTransition() {
		return transition;
	}

}
