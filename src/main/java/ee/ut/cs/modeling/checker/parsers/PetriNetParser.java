package ee.ut.cs.modeling.checker.parsers;

import ee.ut.cs.modeling.checker.domain.petrinet.Arc;
import ee.ut.cs.modeling.checker.domain.petrinet.Node;
import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;

public class PetriNetParser {

	public PetriNet getPetriNetFromFile(String fileName) {
		File f = new File (fileName);

		PnmlImportUtils pnmlImportUtils = new PnmlImportUtils();

		InputStream input = null;
		try {
			input = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Pnml pnml = null;
		try {
			pnml = pnmlImportUtils.importPnmlFromStream(input, f.getName(), f.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		PetrinetGraph net = PetrinetFactory.newInhibitorNet(pnml.getLabel() + " (imported from " + f.getName() + ")");
		Marking marking = new Marking();
		pnml.convertToNet(net, marking, new GraphLayoutConnection(net));

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

		PetriNet petriNet = new PetriNet();

		for (Place place : net.getPlaces()) {
			ee.ut.cs.modeling.checker.domain.petrinet.Place p = new ee.ut.cs.modeling.checker.domain.petrinet.Place(place.getLabel());
			addInputArcs(net, place, p);
			addOutputArcs(net, place, p);
			petriNet.addPlace(p);
		}

		for (Transition transition : net.getTransitions()) {
			ee.ut.cs.modeling.checker.domain.petrinet.Transition t = new ee.ut.cs.modeling.checker.domain.petrinet.Transition(transition.getLabel());
			addInputArcs(net, transition, t);
			addOutputArcs(net, transition, t);
			petriNet.addTransition(t);
		}

		return petriNet;
	}

	private void addInputArcs(PetrinetGraph net, PetrinetNode node, Node n) {

		net.getInEdges(node).forEach(arc -> {
			String from = arc.getSource().toString();
			String to = arc.getTarget().toString();
			n.addInputs(new Arc(from, to));
		});

	}

	private void addOutputArcs(PetrinetGraph net, PetrinetNode node, Node n) {

		net.getOutEdges(node).forEach(arc -> {
			String from = arc.getSource().toString();
			String to = arc.getTarget().toString();
			n.addOutputs(new Arc(from, to));
		});

	}


}
