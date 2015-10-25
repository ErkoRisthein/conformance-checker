package ee.ut.cs.modeling.checker.parsers;

import ee.ut.cs.modeling.checker.domain.eventlog.Event;
import ee.ut.cs.modeling.checker.domain.eventlog.EventLog;
import ee.ut.cs.modeling.checker.domain.eventlog.Trace;
import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.model.XAttributable;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XTrace;

import java.util.List;

public class EventLogParser {

	public EventLog getEventLogFromFile(String fileName) {
		try {
			return eventLogFromFile(fileName);
		} catch (Exception e) {
			throw new EventLogParseException("Problem with parsing the event log");
		}
	}

	private EventLog eventLogFromFile(String fileName) throws Exception {
		EventLog eventLog = new EventLog();
		List<XTrace> xLog = XLogReader.openLog(fileName);

		for (List<XEvent> xTrace : xLog) {
			Trace trace = toTrace(xTrace);
			eventLog.addTrace(trace);
		}

		return eventLog;
	}

	private Trace toTrace(List<XEvent> xTrace) {
		Trace trace = new Trace();
		for (XEvent xEvent : xTrace) {
			Event event = new Event(name(xEvent));
			trace.addEvent(event);
		}
		return trace;
	}

	private String name(XAttributable xAttributable) {
		return XConceptExtension.instance().extractName(xAttributable);
	}

	private class EventLogParseException extends RuntimeException {
		public EventLogParseException(String message) {
			super(message);
		}
	}

}
