package ee.ut.cs.modeling.checker;

import com.google.common.collect.ImmutableSet;
import ee.ut.cs.modeling.checker.domain.eventlog.Event;
import ee.ut.cs.modeling.checker.domain.eventlog.Trace;
import ee.ut.cs.modeling.checker.domain.petrinet.Arc;

import java.util.HashSet;
import java.util.Set;

public class PetriNetTestHelper {

	public static Trace trace(String... events) {
		Trace trace = new Trace();
		for (String event : events) {
			trace.addEvent(new Event(event));
		}
		return trace;
	}

	public static ImmutableSet<Arc> noArc() {
		return ImmutableSet.of();
	}

	public static Set<Arc> arcs(String... arcParams) {
		HashSet<Arc> arcs = new HashSet<>();

		for (int i = 0; i < arcParams.length; i += 2) {
			String from = arcParams[i];
			String to = arcParams[i + 1];
			arcs.add(new Arc(from, to));
		}

		return arcs;
	}

}
