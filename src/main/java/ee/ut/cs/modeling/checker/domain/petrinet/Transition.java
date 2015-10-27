package ee.ut.cs.modeling.checker.domain.petrinet;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.addAll;

public class Transition {

	public static final Transition NULL = new Transition("null");

	private final String name;
	private Set<Place> inputs = new HashSet<>();
	private Set<Place> outputs = new HashSet<>();

	public Transition(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public Transition from(Place... places) {
		addAll(inputs, places);
		return this;
	}

	public Transition to(Place... places) {
		addAll(outputs, places);
		return this;
	}

	public Set<Place> inputs() {
		return inputs;
	}

	public Set<Place> outputs() {
		return outputs;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Transition that = (Transition) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return inputs + "->" + name + "->" + outputs;
	}

}
