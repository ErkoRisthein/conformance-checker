package ee.ut.cs.modeling.checker;

import ee.ut.cs.modeling.checker.domain.eventlog.EventLog;
import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import ee.ut.cs.modeling.checker.parsers.EventLogParser;
import ee.ut.cs.modeling.checker.parsers.PetriNetParser;
import org.apache.commons.cli.*;

public class CheckConformance {

	public static void main(String[] args) {
		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("p", "petrinet", true, "petrinet input filename");
		options.addOption("e", "eventlog", true, "eventlog input filename");

		try {
			CommandLine line = parser.parse(options, args);

			if (line.hasOption("p") && line.hasOption("e")) {
				String petriNetFilename = line.getOptionValue("p");
				String eventLogFilename = line.getOptionValue("e");

				PetriNet petriNet = new PetriNetParser().getPetriNetFromFile(petriNetFilename);
				EventLog eventLog = new EventLogParser().getEventLogFromFile(eventLogFilename);

				double fitness = new ConformanceChecker(petriNet, eventLog).getFitness();
				double sba = new ConformanceChecker(petriNet, eventLog).getSimpleBehavioralAppropriateness();
				double ssa = new ConformanceChecker(petriNet, eventLog).getSimpleStructuralAppropriateness();

				System.out.println("Fitness: " + fitness);
				System.out.println("Simple Behavioral Appropriateness: " + sba);
				System.out.println("Simple Structural Appropriateness: " + ssa);
			} else {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("java CheckConformance", options);
			}
		} catch (ParseException exp) {
			System.out.println("Unexpected exception:" + exp.getMessage());
		}
	}

}
