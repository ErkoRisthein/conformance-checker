package ee.ut.cs.modeling.checker.domain.petrinet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Place implements Node {

	private final String name;
	private Set<Arc> inputs = new HashSet<>();
	private Set<Arc> outputs = new HashSet<>();
	private int tokens;

	public Place(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Place addInputs(Arc... arcs) {
		Collections.addAll(inputs, arcs);
		return this;
	}

	@Override
	public Place addOutputs(Arc... arcs) {
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
