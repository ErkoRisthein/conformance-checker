package ee.ut.cs.modeling.checker;

import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import ee.ut.cs.modeling.checker.domain.petrinet.arc.Arc;
import ee.ut.cs.modeling.checker.parsers.PnmlImportUtils;
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
import java.util.*;

/**
 * Created by robert on 21.10.15.
 */
public class InputConverters {

	public PetriNet getPetriNetFromFile(File f) {

		PnmlImportUtils ut = new PnmlImportUtils();

		InputStream input = null;
		try {
			input = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Pnml pnml = null;
		try {
			pnml = ut.importPnmlFromStream(input, f.getName(), f.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
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

		PetriNet petriNet = new PetriNet();

		for (Place place : places) {
			System.out.println(place);
			List<Arc> inputArcs = getInputArcs(net, place);
			List<Arc> outputArcs = getOutputArcs(net, place);
			petriNet.setPlace(place.getLabel(),
					new ee.ut.cs.modeling.checker.domain.petrinet.node.Place(inputArcs, outputArcs));
		}

		for (Transition transition : transitions) {
			System.out.println(transition);
			List<Arc> inputArcs = getInputArcs(net, transition);
			List<Arc> outputArcs = getOutputArcs(net, transition);

			petriNet.setTransition(
					transition.getLabel(),
					new ee.ut.cs.modeling.checker.domain.petrinet.node.Transition(
							inputArcs,
							outputArcs));
		}

		return petriNet;
	}


	private List<Arc> nativeArcList(Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> parserArcs) {

		List<Arc> result = new ArrayList<Arc>();

		for (PetrinetEdge arc : parserArcs) {
			System.out.println(arc);
			// For some reason getLabel() doesn't work here, using toString().
			String from = arc.getSource().toString();
			String to = arc.getTarget().toString();
			result.add(new Arc(from, to));
		}

		return result;
	}

	private List<Arc> getInputArcs(PetrinetGraph net, PetrinetNode node) {

		Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> arcs = net.getInEdges(node);

		return nativeArcList(arcs);

	}


	private List<Arc> getOutputArcs(PetrinetGraph net, PetrinetNode node) {

		Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> arcs = net.getOutEdges(node);

		return nativeArcList(arcs);

	}
}