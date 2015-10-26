package ee.ut.cs.modeling.checker;

import ee.ut.cs.modeling.checker.domain.eventlog.Event;
import ee.ut.cs.modeling.checker.domain.eventlog.Trace;

public class PetriNetTestHelper {

	public static Trace trace(String... events) {
		Trace trace = new Trace();
		for (String event : events) {
			trace.addEvent(new Event(event));
		}
		return trace;
	}

}
