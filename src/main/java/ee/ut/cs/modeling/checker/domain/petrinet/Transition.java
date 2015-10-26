package ee.ut.cs.modeling.checker.domain.petrinet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Transition implements Node {

	private final String name;
	private Set<Arc> inputs = new HashSet<>();
	private Set<Arc> outputs = new HashSet<>();

	public Transition(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Transition addInputs(Arc... arcs) {
		Collections.addAll(inputs, arcs);
		return this;
	}

	@Override
	public Transition addOutputs(Arc... arcs) {
		Collections.addAll(outputs, arcs);
		return this;
	}

	@Override
	public Set<Arc> getInputs() {
		return inputs;
	}

	@Override
	public Set<Arc> getOutputs() {
		return outputs;
	}

	@Override
	public String toString() {
		return inputs + "->" + name + "->" + outputs;
	}

}
