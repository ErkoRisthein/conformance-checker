package ee.ut.cs.modeling.checker.domain.petrinet;

import ee.ut.cs.modeling.checker.domain.petrinet.node.Place;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Transition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetriNet {

	private Map<String, Place> places;
	private Map<String, Transition> transitions;

	public PetriNet() {
		this.places = new HashMap<String, Place>();
		this.transitions = new HashMap<String, Transition>();
	}

//  Currently seems like we dont need to keep track of arcs in the PetriNet.
//	private List<Arc> arcs;


	public Map<String, Place> getPlaces() {
		return places;
	}

	public void setPlace(String key, Place place) {
		places.put(key, place);
	}

	public Map<String, Transition> getTransitions() {
		return transitions;
	}

	public void setTransition(String key, Transition transition) {
		transitions.put(key, transition);
	}


//	public List<Arc> getArcs() {
//		return arcs;
//	}

}
