package ee.ut.cs.modeling.checker.domain.petrinet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Transition {

	public static final Transition NULL = new Transition("null");

	private final String name;
	private Set<Place> from = new HashSet<>();
	private Set<Place> to = new HashSet<>();

	public Transition(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


	public Transition from(Place... places) {
		Collections.addAll(from, places);
		return this;
	}

	public Transition to(Place... places) {
		Collections.addAll(to, places);
		return this;
	}

	public Set<Place> getFrom() {
		return from;
	}

	public Set<Place> getTo() {
		return to;
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
		return from + "->" + name + "->" + to;
	}

}
