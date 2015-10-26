package ee.ut.cs.modeling.checker.domain.petrinet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Place implements Node {

	public static final Place NULL = new Place("null");

	private final String name;
	private Set<Arc> inputs = new HashSet<>();
	private Set<Arc> outputs = new HashSet<>();
	private Set<Transition> from = new HashSet<>();
	private Set<Transition> to = new HashSet<>();
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

	public void addFrom(Transition transition) {
		from.add(transition);
		addInputs(new Arc(transition.getName(), name));
	}

	public void addTo(Transition transition) {
		to.add(transition);
		addOutputs(new Arc(name, transition.getName()));
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
