package ee.ut.cs.modeling.checker.domain.eventlog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventLog {

	private List<Trace> traces = new ArrayList<>();
	private Map<Trace, TraceParameters> aggregatedTraces = new HashMap<>();

	public void addTrace(Trace trace) {
		traces.add(trace);
		aggregatedTraces.put(trace, incrementCount(trace));
	}

	private TraceParameters incrementCount(Trace trace) {
		TraceParameters params = aggregatedTraces.get(trace);
		params = (params == null) ? new TraceParameters() : params;
		params.incrementCount();
		return params;
	}

	public Map<Trace, TraceParameters> getAggregatedTraces() {
		return aggregatedTraces;
	}




	@Override
	public String toString() {
		return aggregatedTraces.toString();
	}
}
