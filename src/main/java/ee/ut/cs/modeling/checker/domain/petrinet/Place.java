package ee.ut.cs.modeling.checker.domain.petrinet;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Place {

	public static final Place NULL = new Place("null");

	private final String name;
	private Set<Transition> from = new HashSet<>();
	private Set<Transition> to = new HashSet<>();
	private int tokens;

	public Place(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


	public Place from(Transition transition) {
		from.add(transition);
		return this;
	}

	public Place to(Transition transition) {
		to.add(transition);
		return this;
	}

	public Set<Transition> getFrom() {
		return from;
	}

	public Set<Transition> getTo() {
		return to;
	}

	public int getOutputCount() {
		return to.size();
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
		return from + "->" + name + "(" + tokens + ")->" + to;
	}
}
