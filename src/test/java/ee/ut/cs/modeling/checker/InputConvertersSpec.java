package ee.ut.cs.modeling.checker;

import ee.ut.cs.modeling.checker.domain.eventlog.Event;
import ee.ut.cs.modeling.checker.domain.eventlog.EventLog;
import ee.ut.cs.modeling.checker.domain.eventlog.Trace;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class InputConvertersSpec {

	InputConverters converters = new InputConverters();

	@Test
	public void getPetriNetFromFile() {

	}

	@Test
	public void getEventLogFromFile() {
		EventLog eventLog = converters.getEventLogFromFile("test.xes");

		assertThat(count(eventLog, trace("A", "B", "E")), equalTo(6));
		assertThat(count(eventLog, trace("A", "B", "C", "D")), equalTo(3));
	}

	private Integer count(EventLog eventLog, Trace trace) {
		return eventLog.getAggregatedTraces().get(trace);
	}

	private Trace trace(String... events) {
		Trace trace = new Trace();
		for (String event : events) {
			trace.addEvent(new Event(event));
		}
		return trace;
	}
}