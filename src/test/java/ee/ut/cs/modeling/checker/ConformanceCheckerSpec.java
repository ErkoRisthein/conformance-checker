package ee.ut.cs.modeling.checker;

import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import ee.ut.cs.modeling.checker.parsers.PnmlImportUtils;

import ee.ut.cs.modeling.checker.parsers.XLogReader;
import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.extension.std.XLifecycleExtension;
import org.deckfour.xes.extension.std.XTimeExtension;
import org.deckfour.xes.in.XesXmlParser;
import org.deckfour.xes.model.XAttributeMap;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.junit.Test;
import org.processmining.models.connections.GraphLayoutConnection;
import org.processmining.models.graphbased.directed.petrinet.PetrinetEdge;
import org.processmining.models.graphbased.directed.petrinet.PetrinetGraph;
import org.processmining.models.graphbased.directed.petrinet.PetrinetNode;
import org.processmining.models.graphbased.directed.petrinet.elements.Place;
import org.processmining.models.graphbased.directed.petrinet.elements.Transition;
import org.processmining.models.graphbased.directed.petrinet.impl.PetrinetFactory;
import org.processmining.models.semantics.petrinet.Marking;
import org.processmining.plugins.pnml.Pnml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;


public class ConformanceCheckerSpec {

	@Test
	public void newConformanceChecker() {
		ConformanceChecker conformanceChecker = new ConformanceChecker();
	}

	@Test
	public void testImportPnml() throws Exception {

		PnmlImportUtils ut = new PnmlImportUtils();
		File f = new File ("test.pnml");

		InputStream input = new FileInputStream(f);
		Pnml pnml = ut.importPnmlFromStream(input, f.getName(), f.length());
		PetrinetGraph net = PetrinetFactory.newInhibitorNet(pnml.getLabel() + " (imported from " + f.getName() + ")");
		Marking marking = new Marking();
		pnml.convertToNet(net,marking ,new GraphLayoutConnection(net));
		Collection<Place> places = net.getPlaces();
		Collection<Transition> transitions = net.getTransitions();
		Place aPlace = places.iterator().next();
		Transition aTransition = transitions.iterator().next();

		// to get outgoing edges from a place
		Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> edgesOutP = net.getOutEdges(aPlace);

		//to get ingoing edges to a place
		Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> edgesInP = net.getInEdges(aPlace);

		//to get outgoing edges from a transition
		Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> edgesOutT = net.getOutEdges(aTransition);

		//to get ingoing edges to a transition
		Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> edgesInT = net.getInEdges(aTransition);

	}

	@Test
	public void testImportXes() throws Exception {
		XLog log = XLogReader.openLog("test.xes");
		for(XTrace trace:log){
			String traceName = XConceptExtension.instance().extractName(trace);
			XAttributeMap caseAttributes = trace.getAttributes();
			for(XEvent event : trace){
				String activityName = XConceptExtension.instance().extractName(event);
				Date timestamp = XTimeExtension.instance().extractTimestamp(event);
				String eventType = XLifecycleExtension.instance().extractTransition(event);
				XAttributeMap eventAttributes = event.getAttributes();
				for(String key :eventAttributes.keySet()){
					String value = eventAttributes.get(key).toString();
				}
				for(String key :caseAttributes.keySet()){
					String value = caseAttributes.get(key).toString();
				}
			}
		}
	}

	@Test
	public void testPetriNetGeneration() throws Exception {

		File f = new File ("test.pnml");

		InputConverters converters = new InputConverters();

		PetriNet petriNet = converters.getPetriNetFromFile(f);



	}
}
