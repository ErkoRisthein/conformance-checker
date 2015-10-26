package ee.ut.cs.modeling.checker.domain.petrinet;

import java.util.Objects;

public class Arc {

	private String from;
	private String to;
	private Node src;
	private Node tgt;

	public Arc(String from, String to) {
		this.from = from;
		this.to = to;
	}


	public Arc(Node src, Node tgt) {
		this.src = src;
		this.tgt = tgt;
		this.from = src != null ? src.getName() : null;
		this.to = tgt != null ? tgt.getName() : null;
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
