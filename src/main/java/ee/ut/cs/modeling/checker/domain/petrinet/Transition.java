package ee.ut.cs.modeling.checker.domain.petrinet;

import java.util.Set;

public class Transition {

	private final String name;
	private Set<Arc> inputs;
	private Set<Arc> outputs;

	public Transition(String name, Set<Arc> inputs, Set<Arc> outputs) {
		this.name = name;
		this.inputs = inputs;
		this.outputs = outputs;
	}

	public String getName() {
		return name;
	}

	public Set<Arc> getInputs() {
		return inputs;
	}

	public Set<Arc> getOutputs() {
		return outputs;
	}

	@Override
	public String toString() {
		return inputs + "->" + name + "->" + outputs;
	}

}