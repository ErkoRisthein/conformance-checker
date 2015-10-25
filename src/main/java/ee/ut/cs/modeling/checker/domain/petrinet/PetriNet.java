package ee.ut.cs.modeling.checker.domain.petrinet;

import com.google.common.base.MoreObjects;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Place;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Transition;

import java.util.ArrayList;
import java.util.List;

public class PetriNet {

	private List<Place> places = new ArrayList<>();
	private List<Transition> transitions = new ArrayList<>();

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


	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("places", places)
				.add("transitions", transitions)
				.toString();
	}
}
