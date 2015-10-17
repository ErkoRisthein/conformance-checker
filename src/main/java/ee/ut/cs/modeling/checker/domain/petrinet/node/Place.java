package ee.ut.cs.modeling.checker.domain.petrinet.node;

import ee.ut.cs.modeling.checker.domain.petrinet.arc.P2TArc;
import ee.ut.cs.modeling.checker.domain.petrinet.arc.T2PArc;
import ee.ut.cs.modeling.checker.domain.petrinet.token.Token;

import java.util.List;

public class Place implements Node {

	private T2PArc input;
	private P2TArc output;
	private List<Token> tokens;

	@Override
	public T2PArc getInput() {
		return input;
	}

	@Override
	public P2TArc getOutput() {
		return output;
	}

}
