package ee.ut.cs.modeling.checker;

import ee.ut.cs.modeling.checker.domain.eventlog.EventLog;
import ee.ut.cs.modeling.checker.domain.eventlog.Trace;
import ee.ut.cs.modeling.checker.domain.eventlog.TraceParameters;
import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import ee.ut.cs.modeling.checker.domain.petrinet.Place;
import ee.ut.cs.modeling.checker.domain.petrinet.Transition;
import ee.ut.cs.modeling.checker.parsers.EventLogParser;
import ee.ut.cs.modeling.checker.parsers.PetriNetParser;
import org.junit.Before;
import org.junit.Test;

import static ee.ut.cs.modeling.checker.PetriNetTestHelper.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.IsCloseTo.closeTo;


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
		assertFitness("test_extra.xes", 0.94);
	}

	@Test
	public void multiInputOutputPetriNetFitness() {
		PetriNet petriNet = generateMultiInputPetriNet();
		EventLog eventLog = generateAbEventLog();

		double fitness = conformanceChecker.getFitness(petriNet, eventLog);

		assertThat(fitness, is(equalTo(1d)));
	}

	@Test
	public void getSimpleBehavioralAppropriateness() {
		PetriNet petriNet = getTestPetriNet();
		EventLog eventLog = getEventLog("test.xes");

		double sba = conformanceChecker.getSimpleBehavioralAppropriateness(petriNet, eventLog);

		assertThat(sba, is(closeTo(0.9236111, 0.0000001)));
	}

	@Test
	public void getSimpleStructuralAppropriateness() {
		PetriNet petriNet = getTestPetriNet();

		double ssa = conformanceChecker.getSimpleStructuralAppropriateness(petriNet);

		assertThat(ssa, is(equalTo(0.7)));
	}

	private void replayLog(String eventLogFilename) {
		PetriNet petriNet = getTestPetriNet();
		EventLog eventLog = getEventLog(eventLogFilename);

		conformanceChecker.replayLog(petriNet, eventLog);

		TraceParameters abcdParams = getParams(eventLog, trace("A", "B", "C", "D"));
		assertParams(abcdParams, 0, 0, 5, 5, 3);

		TraceParameters abeParams = getParams(eventLog, trace("A", "B", "E"));
		assertParams(abeParams, 0, 0, 4, 4, 6);
	}

	private PetriNet getTestPetriNet() {
		return petriNetParser.getPetriNetFromFile("test.pnml");
	}

	private EventLog getEventLog(String fileName) {
		return eventLogParser.getEventLogFromFile(fileName);
	}

	private TraceParameters getParams(EventLog eventLog, Trace trace) {
		return eventLog.getTraces().get(trace);
	}

	private void assertParams(TraceParameters traceParameters, int missing, int remaining, int consumed, int produced, int count) {
		assertThat(traceParameters.getMissing(), is(equalTo(missing)));
		assertThat(traceParameters.getRemaining(), is(equalTo(remaining)));
		assertThat(traceParameters.getConsumed(), is(equalTo(consumed)));
		assertThat(traceParameters.getProduced(), is(equalTo(produced)));
		assertThat(traceParameters.getCount(), is(equalTo(count)));
	}

	private void assertFitness(String eventLogFilename, double expectedFitness) {
		PetriNet petriNet = getTestPetriNet();
		EventLog eventLog = getEventLog(eventLogFilename);

		double fitness = conformanceChecker.getFitness(petriNet, eventLog);

		assertThat(fitness, is(equalTo(expectedFitness)));
	}

	private PetriNet generateMultiInputPetriNet() {
		PetriNet petriNet = new PetriNet();

		petriNet.addPlace(new Place("p1", noArc(), arcs("p1", "A")));
		petriNet.addPlace(new Place("p2", arcs("A", "p2"), arcs("p2", "B")));
		petriNet.addPlace(new Place("p3", arcs("A", "p3"), arcs("p3", "B")));
		petriNet.addPlace(new Place("p4", arcs("A", "p4"), arcs("p4", "B")));
		petriNet.addPlace(new Place("p5", arcs("B", "p5"), noArc()));

		petriNet.addTransition(new Transition("A", arcs("p1", "A"), arcs("A", "p2", "A", "p3", "A", "p4")));
		petriNet.addTransition(new Transition("B", arcs("p2", "B", "p3", "B", "p4", "B"), arcs("B", "p5")));

		return petriNet;
	}

	private EventLog generateAbEventLog() {
		EventLog eventLog = new EventLog();

		eventLog.addTrace(trace("A", "B"));

		return eventLog;
	}

}
