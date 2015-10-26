package ee.ut.cs.modeling.checker.parsers;

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

public class PetriNetParser {

	private PetriNet petriNet;

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

		petriNet = new PetriNet();

		for (Place place : net.getPlaces()) {
			ee.ut.cs.modeling.checker.domain.petrinet.Place p = new ee.ut.cs.modeling.checker.domain.petrinet.Place(place.getLabel());
			petriNet.addPlace(p);
		}

		for (Transition transition : net.getTransitions()) {
			ee.ut.cs.modeling.checker.domain.petrinet.Transition t = new ee.ut.cs.modeling.checker.domain.petrinet.Transition(transition.getLabel());
			petriNet.addTransition(t);
		}

		for (PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode> edge : net.getEdges()) {
			String fromName = edge.getSource().getLabel();
			String toName = edge.getTarget().getLabel();

			ee.ut.cs.modeling.checker.domain.petrinet.Transition fromTransition = petriNet.getTransition(fromName);
			ee.ut.cs.modeling.checker.domain.petrinet.Place toPlace = petriNet.getPlace(toName);

			fromTransition.to(toPlace);
			toPlace.from(fromTransition);

			ee.ut.cs.modeling.checker.domain.petrinet.Place fromPlace = petriNet.getPlace(fromName);
			ee.ut.cs.modeling.checker.domain.petrinet.Transition toTransition = petriNet.getTransition(toName);

			fromPlace.to(toTransition);
			toTransition.from(fromPlace);

		}

		return petriNet;
	}

}
