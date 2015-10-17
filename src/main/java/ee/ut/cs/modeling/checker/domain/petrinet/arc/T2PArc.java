package ee.ut.cs.modeling.checker.domain.petrinet.arc;

import ee.ut.cs.modeling.checker.domain.petrinet.node.Place;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Transition;

public class T2PArc implements Arc {

	private Transition from;
	private Place to;

	@Override
	public Transition getFrom() {
		return from;
	}

	@Override
	public Place getTo() {
		return to;
	}

}
