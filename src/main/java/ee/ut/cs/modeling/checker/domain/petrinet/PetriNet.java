package ee.ut.cs.modeling.checker.domain.petrinet;

import com.google.common.base.MoreObjects;
import ee.ut.cs.modeling.checker.domain.petrinet.arc.Arc;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Place;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Transition;

import java.util.HashMap;
import java.util.Map;

public class PetriNet {

	private Map<String, Place> places = new HashMap<>();
	private Map<String, Transition> transitions = new HashMap<>();
	private Place start;
	private Place end;

	public void addPlace(Place place) {
		places.put(place.getName(), place);
		updateStartEnd(place);
	}

	private void updateStartEnd(Place place) {
		if (place.getInputs().isEmpty()) {
			start = place;
		}
		if (place.getOutputs().isEmpty()) {
			end = place;
		}
	}

	public Place getStart() {
		return start;
	}

	public Place getEnd() {
		return end;
	}

	public void addTransition(Transition transition) {
		transitions.put(transition.getName(), transition);
	}

	public boolean transitionHasAllInputTokens(String transitionName) {
		for (Arc input : transitions.get(transitionName).getInputs()) {
			String placeName = input.getFrom();
			if(!places.get(placeName).hasTokens()) {
				 return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("places", places)
				.add("transitions", transitions)
				.toString();
	}
}
