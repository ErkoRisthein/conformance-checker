package ee.ut.cs.modeling.checker.domain.petrinet.node;

import ee.ut.cs.modeling.checker.domain.petrinet.arc.Arc;

import java.util.List;

public class Transition {

	private String event;
	private List<Arc> input;
	private List<Arc> output;

	public Transition(String event, List<Arc> input, List<Arc> output) {
		this.event = event;
		this.input = input;
		this.output = output;
	}

	public List<Arc> getInputs() {
		return input;
	}

	public List<Arc> getOutputs() {
		return output;
	}

	@Override
	public String toString() {
		return input + "->" + event + "->" + output;
	}
}
