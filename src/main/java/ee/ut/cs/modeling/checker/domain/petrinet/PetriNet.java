package ee.ut.cs.modeling.checker.domain.petrinet;

import ee.ut.cs.modeling.checker.domain.petrinet.node.Place;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetriNet {

	private List<Place> places;
	private List<Transition> transitions;

	public PetriNet() {
		this.places = new ArrayList<Place>();
		this.transitions = new ArrayList<Transition>();
	}

//  Currently seems like we dont need to keep track of arcs in the PetriNet.
//	private List<Arc> arcs;


	public List<Place> getPlaces() {
		return places;
	}

	public void addPlace(Place place) {
		places.add(place);
	}

	public List<Transition> getTransitions() {
		return transitions;
	}

	public void addTransition(Transition transition) {
		transitions.add(transition);
	}


//	public List<Arc> getArcs() {
//		return arcs;
//	}

}
