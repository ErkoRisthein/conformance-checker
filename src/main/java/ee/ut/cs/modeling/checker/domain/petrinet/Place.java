package ee.ut.cs.modeling.checker.domain.petrinet;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.addAll;

public class Place {

	public static final Place NULL = new Place("null");

	private final String name;
	private Set<Transition> inputs = new HashSet<>();
	private Set<Transition> outputs = new HashSet<>();
	private int tokens;

	public Place(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}


	public Place from(Transition... transitions) {
		addAll(inputs, transitions);
		return this;
	}

	public Place to(Transition... transitions) {
		addAll(outputs, transitions);
		return this;
	}

	public boolean hasZeroInputs() {
		return inputs.isEmpty();
	}

	public boolean hasZeroOutputs() {
		return outputs.isEmpty();
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
		return Objects.equals(name, place.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return inputs + "->" + name + "(" + tokens + ")->" + outputs;
	}
}
