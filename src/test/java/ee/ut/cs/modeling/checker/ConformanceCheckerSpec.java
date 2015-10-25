package ee.ut.cs.modeling.checker;

import ee.ut.cs.modeling.checker.domain.eventlog.Event;
import ee.ut.cs.modeling.checker.domain.eventlog.EventLog;
import ee.ut.cs.modeling.checker.domain.eventlog.Trace;
import ee.ut.cs.modeling.checker.domain.eventlog.TraceParameters;
import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import ee.ut.cs.modeling.checker.parsers.EventLogParser;
import ee.ut.cs.modeling.checker.parsers.PetriNetParser;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class ConformanceCheckerSpec {

	ConformanceChecker conformanceChecker = new ConformanceChecker();
	EventLogParser eventLogParser = new EventLogParser();
	PetriNetParser petriNetParser = new PetriNetParser();

	@Test
	public void getFitness() {
		PetriNet petriNet = petriNetParser.getPetriNetFromFile("test.pnml");
		EventLog eventLog = eventLogParser.getEventLogFromFile("test.xes");

		double fitness = conformanceChecker.getFitness(petriNet, eventLog);

		assertThat(fitness, equalTo(1d));
	}

	@Test
	public void logReplay() {
		PetriNet petriNet = petriNetParser.getPetriNetFromFile("test.pnml");
		EventLog eventLog = eventLogParser.getEventLogFromFile("test.xes");

		conformanceChecker.replayLog(petriNet, eventLog);

		TraceParameters abcdTraceParameters = eventLog.getAggregatedTraces().get(trace("A", "B", "C", "D"));
		assertParams(abcdTraceParameters, 0, 0, 5, 5, 3);

		TraceParameters abeTraceParameters = eventLog.getAggregatedTraces().get(trace("A", "B", "E"));
		assertParams(abeTraceParameters, 0, 0, 4, 4, 6);
	}

	private void assertParams(TraceParameters abcdTraceParameters, int missing, int remaining, int consumed, int produced, int count) {
		assertThat(abcdTraceParameters.getMissing(), equalTo(missing));
		assertThat(abcdTraceParameters.getRemaining(), equalTo(remaining));
		assertThat(abcdTraceParameters.getConsumed(), equalTo(consumed));
		assertThat(abcdTraceParameters.getProduced(), equalTo(produced));
		assertThat(abcdTraceParameters.getCount(), equalTo(count));
	}

	private Trace trace(String... events) {
		Trace trace = new Trace();
		for (String event : events) {
			trace.addEvent(new Event(event));
		}
		return trace;
	}
}
