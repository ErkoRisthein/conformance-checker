package ee.ut.cs.modeling.checker;

import ee.ut.cs.modeling.checker.domain.eventlog.Event;
import ee.ut.cs.modeling.checker.domain.eventlog.EventLog;
import ee.ut.cs.modeling.checker.domain.eventlog.Trace;
import ee.ut.cs.modeling.checker.domain.eventlog.TraceParameters;
import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;

public class ConformanceChecker {

	private PetriNet petriNet;
	private EventLog eventLog;
	private Trace trace;
	private TraceParameters params;
	private String transitionName;

	public double getFitness(PetriNet petriNet, EventLog eventLog) {
		replayLog(petriNet, eventLog);
		return calculateFitness();
	}

	public double getSimpleBehavioralAppropriateness(PetriNet petriNet, EventLog eventLog) {
		replayLog(petriNet, eventLog);
		return calculateSimpleBehavioralAppropriateness();
	}

	public double getSimpleStructuralAppropriateness(PetriNet petriNet) {
		double L = petriNet.countTransitions();
		double N = L + petriNet.countPlaces();
		return (L + 2) / N;
	}

	void replayLog(PetriNet petriNet, EventLog eventLog) {
		this.petriNet = petriNet;
		this.eventLog = eventLog;

		eventLog.forEach((trace, params) -> {
			this.trace = trace;
			this.params = params;
			replayTrace();
		});

	}

	private void replayTrace() {
		addStartToken();
		replayEvents();
		consumeEndToken();
		setRemainingTokens();
	}

	private void setRemainingTokens() {
		int remainingTokens = petriNet.countRemainingTokens();
		params.setRemaining(remainingTokens);
		petriNet.cleanUpRemainingTokens();
	}

	private void consumeEndToken() {
		if (!petriNet.hasEndToken()) {
			petriNet.addEndToken();
			params.incrementMissing();
		}
		petriNet.removeEndToken();
		params.incrementConsumed();
	}

	private void replayEvents() {
		for (Event event : trace.getTrace()) {
			this.transitionName = event.getName();
			addEnabledTransition();
			createMissingTokensIfNeeded();
			consumeInputTokens();
			produceOutputTokens();
		}
	}

	private void addEnabledTransition() {
		params.addEnabledTransition(petriNet.countEnabledTransitions());
	}

	private void createMissingTokensIfNeeded() {
		if (!petriNet.transitionHasAllInputTokens(transitionName)) {
			petriNet.createMissingToken(transitionName);
			params.incrementMissing();
		}
	}

	private void produceOutputTokens() {
		petriNet.produceOutputTokens(transitionName);
		params.incrementProduced();
	}

	private void consumeInputTokens() {
		petriNet.consumeInputTokens(transitionName);
		params.incrementConsumed();
	}

	private void addStartToken() {
		petriNet.addStartToken();
		params.incrementProduced();
	}

	private double calculateFitness() {
		double missing = 0;
		double remaining = 0;
		double consumed = 0;
		double produced = 0;

		for (TraceParameters p : eventLog.getTraceParameters()) {
			missing += p.getCount() * p.getMissing();
			remaining += p.getCount() * p.getRemaining();
			consumed += p.getCount() * p.getConsumed();
			produced += p.getCount() * p.getProduced();
		}

		return 0.5 * (1 - (missing / consumed)) + 0.5 * (1 - (remaining / produced));
	}

	private double calculateSimpleBehavioralAppropriateness() {
		int Tv = petriNet.countTransitions();
		double sum1 = 0;
		double sum2 = 0;

		for (TraceParameters params : eventLog.getTraceParameters()) {
			int ni = params.getCount();
			double xi = params.getMeanEnabledTransitions();

			sum1 += ni * (Tv - xi);
			sum2 += ni;
		}

		return sum1 / ((Tv - 1) * sum2);
	}
}
