package ee.ut.cs.modeling.checker.domain.petrinet.node;

import ee.ut.cs.modeling.checker.domain.petrinet.arc.P2TArc;
import ee.ut.cs.modeling.checker.domain.petrinet.arc.T2PArc;

public class Transition implements Node {

	private P2TArc input;
	private T2PArc output;

	@Override
	public P2TArc getInput() {
		return input;
	}

	@Override
	public T2PArc getOutput() {
		return output;
	}

}
