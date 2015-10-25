package ee.ut.cs.modeling.checker.domain.eventlog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventLog {

	private List<Trace> traces = new ArrayList<>();
	private Map<Trace, Integer> aggregatedTraces = new HashMap<>();

	public void addTrace(Trace trace) {
		traces.add(trace);
		aggregatedTraces.put(trace, count(trace) + 1);
	}

	public Map<Trace, Integer> getAggregatedTraces() {
		return aggregatedTraces;
	}

	private Integer count(Trace trace) {
		Integer count = aggregatedTraces.get(trace);
		count = (count == null) ? 0 : count;
		return count;
	}


	@Override
	public String toString() {
		return aggregatedTraces.toString();
	}
}
