package ee.ut.cs.modeling.checker.parsers;

import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import ee.ut.cs.modeling.checker.domain.petrinet.arc.Arc;
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
import java.util.HashSet;
import java.util.Set;

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

		for (Place place : places) {
			Set<Arc> inputArcs = getInputArcs(net, place);
			Set<Arc> outputArcs = getOutputArcs(net, place);
			petriNet.addPlace(
					new ee.ut.cs.modeling.checker.domain.petrinet.node.Place(
							place.getLabel(),
							inputArcs,
							outputArcs)
			);
		}

		for (Transition transition : transitions) {
			Set<Arc> inputArcs = getInputArcs(net, transition);
			Set<Arc> outputArcs = getOutputArcs(net, transition);
			petriNet.addTransition(new ee.ut.cs.modeling.checker.domain.petrinet.node.Transition(
					transition.getLabel(),
					inputArcs,
					outputArcs));
		}

		return petriNet;
	}


	private Set<Arc> nativeArcList(Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> parserArcs) {

		Set<Arc> result = new HashSet<>();

		for (PetrinetEdge arc : parserArcs) {
			// For some reason getLabel() doesn't work here, using toString().
			String from = arc.getSource().toString();
			String to = arc.getTarget().toString();

			result.add(new Arc(from, to));
		}

		return result;
	}

	private Set<Arc> getInputArcs(PetrinetGraph net, PetrinetNode node) {

		Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> arcs = net.getInEdges(node);

		return nativeArcList(arcs);

	}


	private Set<Arc> getOutputArcs(PetrinetGraph net, PetrinetNode node) {

		Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> arcs = net.getOutEdges(node);

		return nativeArcList(arcs);

	}

}
