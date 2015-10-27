package ee.ut.cs.modeling.checker;

import ee.ut.cs.modeling.checker.domain.eventlog.Event;
import ee.ut.cs.modeling.checker.domain.eventlog.EventLog;
import ee.ut.cs.modeling.checker.domain.eventlog.Trace;
import ee.ut.cs.modeling.checker.domain.eventlog.TraceParameters;
import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import ee.ut.cs.modeling.checker.domain.petrinet.Transition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConformanceChecker {

	private PetriNet petriNet;
	private EventLog eventLog;
	private Map<Trace, TraceParameters> traceToParams = new HashMap<>();

	private Trace trace;
	private TraceParameters params;
	private Transition transition;

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
			this.trace = trace;
			this.params = new TraceParameters(count);
			replayTrace();
			traceToParams.put(this.trace, this.params);
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
			this.transition = petriNet.getTransition(event.getName());
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
		if (!transition.hasAllInputTokens()) {
			transition.createMissingToken();
			params.incrementMissing();
		}
	}

	private void produceOutputTokens() {
		transition.produceOutputTokens();
		params.incrementProduced();
	}

	private void consumeInputTokens() {
		transition.consumeInputTokens();
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

		for (TraceParameters p : getTraceParameters()) {
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

		for (TraceParameters params : getTraceParameters()) {
			int ni = params.getCount();
			double xi = params.getMeanEnabledTransitions();

			sum1 += ni * (Tv - xi);
			sum2 += ni;
		}

		return sum1 / ((Tv - 1) * sum2);
	}

	private Collection<TraceParameters> getTraceParameters() {
		return traceToParams.values();
	}

	TraceParameters getTraceParameters(Trace trace) {
		return traceToParams.get(trace);
	}
}
