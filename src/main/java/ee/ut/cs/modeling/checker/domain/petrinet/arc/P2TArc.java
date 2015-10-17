package ee.ut.cs.modeling.checker.domain.petrinet.arc;

import ee.ut.cs.modeling.checker.domain.petrinet.node.Place;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Transition;

public class P2TArc implements Arc {

	private Place from;
	private Transition to;

	@Override
	public Place getFrom() {
		return from;
	}

	@Override
	public Transition getTo() {
		return to;
	}

}
