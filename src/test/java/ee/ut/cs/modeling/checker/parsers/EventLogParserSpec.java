package ee.ut.cs.modeling.checker.parsers;

import ee.ut.cs.modeling.checker.domain.eventlog.EventLog;
import ee.ut.cs.modeling.checker.domain.eventlog.Trace;
import org.junit.Test;

import static ee.ut.cs.modeling.checker.PetriNetTestHelper.trace;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class EventLogParserSpec {

	EventLogParser eventLogParser = new EventLogParser();

	@Test
	public void getEventLogFromDefaultFile() {
		EventLog eventLog = eventLogParser.getEventLogFromFile("test.xes");

		assertThat(count(eventLog, trace("A", "B", "E")), is(equalTo(6)));
		assertThat(count(eventLog, trace("A", "B", "C", "D")), is(equalTo(3)));
	}


	@Test
	public void getEventLogFromExtraFile() {
		EventLog eventLog = eventLogParser.getEventLogFromFile("test_extra.xes");

		assertThat(count(eventLog, trace("A", "B", "E")), is(equalTo(6)));
		assertThat(count(eventLog, trace("A", "B", "C", "D")), is(equalTo(3)));
		assertThat(count(eventLog, trace("A", "B", "D")), is(equalTo(1)));
		assertThat(count(eventLog, trace("A", "B", "C")), is(equalTo(1)));
		assertThat(count(eventLog, trace("A", "E")), is(equalTo(1)));
	}


	private Integer count(EventLog eventLog, Trace trace) {
		return eventLog.getTraces().get(trace).getCount();
	}

}