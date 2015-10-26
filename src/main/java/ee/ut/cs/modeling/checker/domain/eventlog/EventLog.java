package ee.ut.cs.modeling.checker.domain.eventlog;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

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

	public TraceParameters getTraceParameters(Trace trace) {
		return traces.get(trace);
	}

	public Collection<TraceParameters> getTraceParameters() {
		return traces.values();
	}

	public void forEach(BiConsumer<Trace, TraceParameters> action) {
		traces.forEach(action);
	}

	@Override
	public String toString() {
		return traces.toString();
	}
}
