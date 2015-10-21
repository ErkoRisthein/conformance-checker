package ee.ut.cs.modeling.checker.domain.petrinet.node;

import ee.ut.cs.modeling.checker.domain.petrinet.arc.Arc;
import ee.ut.cs.modeling.checker.domain.petrinet.token.Token;

import java.util.List;

public class Place {

	private String name;
	private List<Arc> input;
	private List<Arc> output;
	private List<Token> tokens;

	public Place(String name, List<Arc> input, List<Arc> output) {
		this.name = name;
		this.input = input;
		this.output = output;
	}

	public List<Arc> getInputs() {
		return input;
	}

	public List<Arc> getOutputs() {
		return output;
	}


}
