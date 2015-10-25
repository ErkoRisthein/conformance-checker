package ee.ut.cs.modeling.checker;

import com.google.common.collect.ImmutableSet;
import ee.ut.cs.modeling.checker.domain.eventlog.Event;
import ee.ut.cs.modeling.checker.domain.eventlog.EventLog;
import ee.ut.cs.modeling.checker.domain.eventlog.Trace;
import ee.ut.cs.modeling.checker.domain.eventlog.TraceParameters;
import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import ee.ut.cs.modeling.checker.domain.petrinet.arc.Arc;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Place;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Transition;
import ee.ut.cs.modeling.checker.parsers.EventLogParser;
import ee.ut.cs.modeling.checker.parsers.PetriNetParser;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class ConformanceCheckerSpec {

	ConformanceChecker conformanceChecker;
	EventLogParser eventLogParser;
	PetriNetParser petriNetParser;

	@Before
	public void setUp() {
		conformanceChecker = new ConformanceChecker();
		eventLogParser = new EventLogParser();
		petriNetParser = new PetriNetParser();
	}

	@Test
	public void replayDefaultLog() {
		replayLog("test.xes");
	}

	@Test
	public void getFitnessDefault() {
		assertFitness("test.xes", 1d);
	}

	@Test
	public void replayExtraLog() {
		replayLog("test_extra.xes");
	}

	@Test
	public void getFitnessExtra() {
		assertFitness("test_extra.xes", 0.94d);
	}

	@Test
	public void replayMultiInputOutputPetriNet() {
		PetriNet petriNet = new PetriNet();

		petriNet.addPlace(new Place("p1", noArc(), arc("p1", "A")));
		petriNet.addPlace(new Place("p2", arc("A", "p2"), arc("p2", "B")));
		petriNet.addPlace(new Place("p3", arc("A", "p3"), arc("p3", "B")));
		petriNet.addPlace(new Place("p4", arc("A", "p4"), arc("p4", "B")));
		petriNet.addPlace(new Place("p5", arc("B", "p5"), noArc()));

		petriNet.addTransition(new Transition("A", arc("p1", "A"), arc("A", "p2", "A", "p3", "A", "p4")));
		petriNet.addTransition(new Transition("B", arc("p2", "B", "p3", "B", "p4", "B"), arc("B", "p5")));

		EventLog eventLog = new EventLog();
		eventLog.addTrace(trace("A", "B"));

		double fitness = conformanceChecker.getFitness(petriNet, eventLog);

		assertThat(fitness, equalTo(1d));
	}

	private void replayLog(String eventLogFilename) {
		PetriNet petriNet = petriNetParser.getPetriNetFromFile("test.pnml");
		EventLog eventLog = eventLogParser.getEventLogFromFile(eventLogFilename);

		conformanceChecker.replayLog(petriNet, eventLog);

		TraceParameters abcdTraceParameters = eventLog.getAggregatedTraces().get(trace("A", "B", "C", "D"));
		assertParams(abcdTraceParameters, 0, 0, 5, 5, 3);

		TraceParameters abeTraceParameters = eventLog.getAggregatedTraces().get(trace("A", "B", "E"));
		assertParams(abeTraceParameters, 0, 0, 4, 4, 6);
	}

	private void assertFitness(String eventLogFilename, double expectedFitness) {
		PetriNet petriNet = petriNetParser.getPetriNetFromFile("test.pnml");
		EventLog eventLog = eventLogParser.getEventLogFromFile(eventLogFilename);

		double fitness = conformanceChecker.getFitness(petriNet, eventLog);

		assertThat(fitness, equalTo(expectedFitness));
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

	private ImmutableSet<Arc> noArc() {
		return ImmutableSet.of();
	}

	private Set<Arc> arc(String... arcParams) {
		HashSet<Arc> arcs = new HashSet<>();

		for (int i = 0; i < arcParams.length; i += 2) {
			String from = arcParams[i];
			String to = arcParams[i + 1];
			arcs.add(new Arc(from, to));
		}

		return arcs;
	}

}
