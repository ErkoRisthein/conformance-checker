package ee.ut.cs.modeling.checker.domain.petrinet.node;

import ee.ut.cs.modeling.checker.domain.petrinet.arc.Arc;

import java.util.Objects;
import java.util.Set;

public class Place {

	private String name;
	private Set<Arc> inputs;
	private Set<Arc> outputs;
	private int tokens;

	public Place(String name, Set<Arc> inputs, Set<Arc> outputs) {
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

	public int getOutputCount() {
		return outputs.size();
	}

	public int getTokenCount() {
		return tokens;
	}

	public void addToken() {
		tokens++;
	}

	public boolean hasTokens() {
		return tokens > 0;
	}

	public void removeToken() {
		tokens--;
	}

	public void removeAllTokens() {
		tokens = 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Place place = (Place) o;
		return Objects.equals(name, place.name) &&
				Objects.equals(inputs, place.inputs) &&
				Objects.equals(outputs, place.outputs);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, inputs, outputs);
	}

	@Override
	public String toString() {
		return inputs + "->" + name + "(" + tokens + ")->" + outputs;
	}
}
