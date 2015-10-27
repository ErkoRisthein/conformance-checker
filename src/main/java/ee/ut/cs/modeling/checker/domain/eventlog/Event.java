package ee.ut.cs.modeling.checker.domain.eventlog;

import java.util.Objects;

public final class Event {

	private final String name;

	public Event(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Event event = (Event) o;
		return Objects.equals(name, event.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return name;
	}
}
