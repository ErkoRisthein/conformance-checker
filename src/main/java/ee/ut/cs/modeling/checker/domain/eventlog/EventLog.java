package ee.ut.cs.modeling.checker.domain.eventlog;

import java.util.HashMap;
import java.util.Map;

public class EventLog {

	private Map<Trace, TraceParameters> traces = new HashMap<>();

	public void addTrace(Trace trace) {
		traces.put(trace, incrementCount(trace));
	}

	private TraceParameters incrementCount(Trace trace) {
		TraceParameters params = traces.get(trace);
		params = (params == null) ? new TraceParameters() : params;
		params.incrementCount();
		return params;
	}

	public Map<Trace, TraceParameters> getTraces() {
		return traces;
	}

	@Override
	public String toString() {
		return traces.toString();
	}
}
