package ee.ut.cs.modeling.checker.domain.eventlog;

import ee.ut.cs.modeling.checker.domain.petrinet.node.Transition;

public final class Event {

	private final String name;

	public Event(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
