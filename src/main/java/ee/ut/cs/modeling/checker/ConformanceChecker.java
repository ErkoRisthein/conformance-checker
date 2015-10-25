package ee.ut.cs.modeling.checker;

import ee.ut.cs.modeling.checker.domain.eventlog.Event;
import ee.ut.cs.modeling.checker.domain.eventlog.EventLog;
import ee.ut.cs.modeling.checker.domain.eventlog.Trace;
import ee.ut.cs.modeling.checker.domain.eventlog.TraceParameters;
import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;

import java.util.Map;

public class ConformanceChecker {

	private PetriNet petriNet;
	private Trace trace;
	private TraceParameters params;
	private String transitionName;
	private EventLog eventLog;

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

	EventLog replayLog(PetriNet petriNet, EventLog eventLog) {
		this.petriNet = petriNet;
		this.eventLog = eventLog;

		for (Map.Entry<Trace, TraceParameters> entry : eventLog.getAggregatedTraces().entrySet()) {
			trace = entry.getKey();
			params = entry.getValue();
			replayTrace();
		}

		return eventLog;
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
			transitionName = event.getName();
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
		double aggregatedMissing = 0f;
		double aggregatedRemaining = 0f;
		double aggregatedConsumed = 0f;
		double aggregatedProduced = 0f;

		for (Map.Entry<Trace, TraceParameters> entry : eventLog.getAggregatedTraces().entrySet()) {
			TraceParameters params = entry.getValue();

			aggregatedMissing += params.getCount() * params.getMissing();
			aggregatedRemaining += params.getCount() * params.getRemaining();
			aggregatedConsumed += params.getCount() * params.getConsumed();
			aggregatedProduced += params.getCount() * params.getProduced();
		}

		return 0.5 * (1 - (aggregatedMissing / aggregatedConsumed)) + 0.5 * (1 - (aggregatedRemaining / aggregatedProduced));
	}

	private double calculateSimpleBehavioralAppropriateness() {
		int Tv = petriNet.countTransitions();
		double sum1 = 0d;
		double sum2 = 0d;

		for (Map.Entry<Trace, TraceParameters> entry : eventLog.getAggregatedTraces().entrySet()) {
			TraceParameters params = entry.getValue();

			int ni = params.getCount();
			double xi = params.getMeanEnabledTransitions();

			sum1 += ni * (Tv - xi);
			sum2 += ni;
		}

		return sum1 / ((Tv - 1) * sum2);
	}
}
