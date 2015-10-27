package ee.ut.cs.modeling.checker;

import ee.ut.cs.modeling.checker.domain.eventlog.EventLog;
import ee.ut.cs.modeling.checker.domain.eventlog.TraceParameters;
import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import ee.ut.cs.modeling.checker.domain.petrinet.Place;
import ee.ut.cs.modeling.checker.domain.petrinet.Transition;
import ee.ut.cs.modeling.checker.parsers.EventLogParser;
import ee.ut.cs.modeling.checker.parsers.PetriNetParser;
import org.junit.Before;
import org.junit.Test;

import static ee.ut.cs.modeling.checker.PetriNetTestHelper.trace;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.IsCloseTo.closeTo;


public class ConformanceCheckerSpec {

	EventLogParser eventLogParser;
	PetriNetParser petriNetParser;

	@Before
	public void setUp() {
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

		ConformanceChecker conformanceChecker = new ConformanceChecker(petriNet, eventLog);
		double fitness = conformanceChecker.getFitness();

		assertThat(fitness, is(equalTo(1d)));
	}

	@Test
	public void getSimpleBehavioralAppropriateness() {
		PetriNet petriNet = getTestPetriNet();
		EventLog eventLog = getEventLog("test.xes");

		ConformanceChecker conformanceChecker = new ConformanceChecker(petriNet, eventLog);
		double sba = conformanceChecker.getSimpleBehavioralAppropriateness();

		assertThat(sba, is(closeTo(0.9236111, 0.0000001)));
	}

	@Test
	public void getSimpleStructuralAppropriateness() {
		PetriNet petriNet = getTestPetriNet();

		ConformanceChecker conformanceChecker = new ConformanceChecker(petriNet, null);
		double ssa = conformanceChecker.getSimpleStructuralAppropriateness();

		assertThat(ssa, is(equalTo(0.7)));
	}

	private void replayLog(String eventLogFilename) {
		PetriNet petriNet = getTestPetriNet();
		EventLog eventLog = getEventLog(eventLogFilename);

		ConformanceChecker conformanceChecker = new ConformanceChecker(petriNet, eventLog);
		conformanceChecker.replayLog();

		TraceParameters abcdParams = conformanceChecker.getTraceParameters(trace("A", "B", "C", "D"));
		assertParams(abcdParams, 0, 0, 5, 5, 3);

		TraceParameters abeParams = conformanceChecker.getTraceParameters(trace("A", "B", "E"));
		assertParams(abeParams, 0, 0, 4, 4, 6);
	}

	private PetriNet getTestPetriNet() {
		return petriNetParser.getPetriNetFromFile("test.pnml");
	}

	private EventLog getEventLog(String fileName) {
		return eventLogParser.getEventLogFromFile(fileName);
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

		ConformanceChecker conformanceChecker = new ConformanceChecker(petriNet, eventLog);
		double fitness = conformanceChecker.getFitness();

		assertThat(fitness, is(equalTo(expectedFitness)));
	}

	private PetriNet generateMultiInputPetriNet() {
		PetriNet petriNet = new PetriNet();

		Transition a = new Transition("A");
		Transition b = new Transition("B");

		Place p1 = new Place("p1");
		Place p2 = new Place("p2");
		Place p3 = new Place("p3");
		Place p4 = new Place("p4");
		Place p5 = new Place("p5");

		p1.to(a);
		p2.from(a).to(b);
		p3.from(a).to(b);
		p4.from(a).to(b);
		p5.from(b);

		a.from(p1).to(p2, p3, p4);
		b.from(p2, p3, p4).to(p5);

		petriNet.addPlace(p1, p2, p3, p4, p5);
		petriNet.addTransition(a, b);

		return petriNet;
	}

	private EventLog generateAbEventLog() {
		EventLog eventLog = new EventLog();

		eventLog.addTrace(trace("A", "B"));

		return eventLog;
	}

}
