package ee.ut.cs.modeling.checker.domain.petrinet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Transition implements Node {

	public static final Transition NULL = new Transition("null");

	private final String name;
	private Set<Arc> inputs = new HashSet<>();
	private Set<Arc> outputs = new HashSet<>();
	private Set<Place> from = new HashSet<>();
	private Set<Place> to = new HashSet<>();

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

	public void addFrom(Place place) {
		from.add(place);
		addInputs(new Arc(place.getName(), name));
	}

	public void addTo(Place place) {
		to.add(place);
		addOutputs(new Arc(name, place.getName()));
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
