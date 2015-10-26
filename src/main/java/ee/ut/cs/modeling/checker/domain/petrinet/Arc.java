package ee.ut.cs.modeling.checker.domain.petrinet;

import java.util.Objects;

public class Arc {

	private String from;
	private String to;

	public Arc(String from, String to) {
		this.from = from;
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Arc arc = (Arc) o;
		return Objects.equals(from, arc.from) &&
				Objects.equals(to, arc.to);
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}

	@Override
	public String toString() {
		return from + "-" + to;
	}
}
