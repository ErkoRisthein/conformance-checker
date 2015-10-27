package ee.ut.cs.modeling.checker;

import ee.ut.cs.modeling.checker.domain.conformance.ConformanceParameters;
import ee.ut.cs.modeling.checker.domain.eventlog.Event;
import ee.ut.cs.modeling.checker.domain.eventlog.EventLog;
import ee.ut.cs.modeling.checker.domain.eventlog.Trace;
import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import ee.ut.cs.modeling.checker.domain.petrinet.Transition;

import java.util.ArrayList;
import java.util.List;

public class ConformanceChecker {

	private PetriNet petriNet;
	private EventLog eventLog;
	List<ConformanceParameters> conformanceParams = new ArrayList<>();

	public ConformanceChecker(PetriNet petriNet, EventLog eventLog) {
		this.petriNet = petriNet;
		this.eventLog = eventLog;
	}

	public double getFitness() {
		replayLog();
		return calculateFitness();
	}

	public double getSimpleBehavioralAppropriateness() {
		replayLog();
		return calculateSimpleBehavioralAppropriateness();
	}

	public double getSimpleStructuralAppropriateness() {
		double L = petriNet.countTransitions();
		double N = L + petriNet.countPlaces();
		return (L + 2) / N;
	}

	void replayLog() {
		eventLog.forEach((trace, count) -> {
			ConformanceParameters params = new ConformanceParameters(trace, count);
			replay(trace, params);
			conformanceParams.add(params);
		});

	}

	private void replay(Trace trace, ConformanceParameters params) {
		addStartToken(params);
		replayEvents(trace, params);
		consumeEndToken(params);
		setRemainingTokens(params);
	}

	private void setRemainingTokens(ConformanceParameters params) {
		int remainingTokens = petriNet.countRemainingTokens();
		params.setRemaining(remainingTokens);
		petriNet.cleanUpRemainingTokens();
	}

	private void consumeEndToken(ConformanceParameters params) {
		if (!petriNet.hasEndToken()) {
			petriNet.addEndToken();
			params.incrementMissing();
		}
		petriNet.removeEndToken();
		params.incrementConsumed();
	}

	private void replayEvents(Trace trace, ConformanceParameters params) {
		for (Event event : trace.getTrace()) {
			Transition transition = petriNet.getTransition(event.name());
			addEnabledTransition(params);
			createMissingTokensIfNeeded(transition, params);
			consumeInputTokens(transition, params);
			produceOutputTokens(transition, params);
		}
	}

	private void addEnabledTransition(ConformanceParameters params) {
		params.addEnabledTransition(petriNet.countEnabledTransitions());
	}

	private void createMissingTokensIfNeeded(Transition transition, ConformanceParameters params) {
		if (!transition.hasAllInputTokens()) {
			transition.createMissingToken();
			params.incrementMissing();
		}
	}

	private void produceOutputTokens(Transition transition, ConformanceParameters params) {
		transition.produceOutputTokens();
		params.incrementProduced();
	}

	private void consumeInputTokens(Transition transition, ConformanceParameters params) {
		transition.consumeInputTokens();
		params.incrementConsumed();
	}

	private void addStartToken(ConformanceParameters params) {
		petriNet.addStartToken();
		params.incrementProduced();
	}

	private double calculateFitness() {
		double missing = 0;
		double remaining = 0;
		double consumed = 0;
		double produced = 0;

		for (ConformanceParameters p : conformanceParams) {
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

		for (ConformanceParameters p : conformanceParams) {
			int ni = p.getCount();
			double xi = p.getMeanEnabledTransitions();

			sum1 += ni * (Tv - xi);
			sum2 += ni;
		}

		return sum1 / ((Tv - 1) * sum2);
	}


}
