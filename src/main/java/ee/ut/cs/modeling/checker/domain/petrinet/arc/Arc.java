package ee.ut.cs.modeling.checker.domain.petrinet.arc;

public class Arc {

	private String from;
	private String to;

	public Arc(String from, String to) {
		this.from = from;
		this.to = to;
	}

	public String getTo() {
		return to;
	}

	public String getFrom() {
		return from;
	}
}
