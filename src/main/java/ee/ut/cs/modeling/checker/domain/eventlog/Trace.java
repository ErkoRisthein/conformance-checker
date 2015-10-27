package ee.ut.cs.modeling.checker.domain.eventlog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Trace {

	private List<Event> trace = new ArrayList<>();

	public void addEvent(Event event) {
		trace.add(event);
	}

	public void forEach(Consumer<Event> action) {
		trace.forEach(action);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Trace trace1 = (Trace) o;
		return Objects.equals(trace, trace1.trace);
	}

	@Override
	public int hashCode() {
		return Objects.hash(trace);
	}

	@Override
	public String toString() {
		return trace.toString();
	}

}
