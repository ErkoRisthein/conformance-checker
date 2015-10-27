package ee.ut.cs.modeling.checker;

import ee.ut.cs.modeling.checker.domain.eventlog.Event;
import ee.ut.cs.modeling.checker.domain.eventlog.Trace;

import static java.util.Arrays.stream;

public class PetriNetTestHelper {

	public static Trace trace(String... events) {
		Trace trace = new Trace();
		stream(events).forEach(event -> trace.addEvent(new Event(event)));
		return trace;
	}

}
