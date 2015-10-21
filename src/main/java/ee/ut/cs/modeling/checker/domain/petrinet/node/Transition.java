package ee.ut.cs.modeling.checker.domain.petrinet.node;

import ee.ut.cs.modeling.checker.domain.petrinet.arc.Arc;

import java.util.List;

public class Transition {

	private List<Arc> input;
	private List<Arc> output;


	public Transition(List<Arc> input, List<Arc> output) {
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
