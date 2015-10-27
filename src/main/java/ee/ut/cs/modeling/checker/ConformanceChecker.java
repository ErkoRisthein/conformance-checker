package ee.ut.cs.modeling.checker;

import ee.ut.cs.modeling.checker.domain.conformance.ConformanceParameters;
import ee.ut.cs.modeling.checker.domain.eventlog.EventLog;
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
			replay(params);
			conformanceParams.add(params);
		});

	}

	private void replay(ConformanceParameters params) {
		addStartToken(params);
		replayEvents(params);
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

	private void replayEvents(ConformanceParameters params) {
		params.trace().forEach(event -> {
			Transition transition = petriNet.getTransition(event.name());
			addEnabledTransition(params);
			createMissingTokensIfNeeded(transition, params);
			consumeInputTokens(transition, params);
			produceOutputTokens(transition, params);
		});
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
			missing += p.count() * p.missing();
			remaining += p.count() * p.remaining();
			consumed += p.count() * p.consumed();
			produced += p.count() * p.produced();
		}

		return 0.5 * (1 - (missing / consumed)) + 0.5 * (1 - (remaining / produced));
	}

	private double calculateSimpleBehavioralAppropriateness() {
		int Tv = petriNet.countTransitions();
		double sum1 = 0;
		double sum2 = 0;

		for (ConformanceParameters p : conformanceParams) {
			int ni = p.count();
			double xi = p.getMeanEnabledTransitions();

			sum1 += ni * (Tv - xi);
			sum2 += ni;
		}

		return sum1 / ((Tv - 1) * sum2);
	}


}
