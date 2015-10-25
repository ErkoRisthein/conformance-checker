package ee.ut.cs.modeling.checker.domain.petrinet;

import com.google.common.base.MoreObjects;
import ee.ut.cs.modeling.checker.domain.petrinet.arc.Arc;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Place;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Transition;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
		for (Arc input : getInputs(transitionName)) {
			String placeName = input.getFrom();
			if(!places.get(placeName).hasTokens()) {
				 return false;
			}
		}
		return true;
	}

	public void createMissingToken(String transitionName) {
		for (Arc input : getInputs(transitionName)) {
			String placeName = input.getFrom();
			Place place = places.get(placeName);
			if (!place.hasTokens()) {
				place.addToken();
			}
		}
	}

	public void consumeInputTokens(String transitionName) {
		for (Arc input : getInputs(transitionName)) {
			String placeName = input.getFrom();
			places.get(placeName).removeToken();
		}
	}

	public void produceOutputTokens(String transitionName) {
		for (Arc output : getOutputs(transitionName)) {
			String placeName = output.getTo();
			places.get(placeName).addToken();
		}
	}

	private Set<Arc> getInputs(String transitionName) {
		return transitions.get(transitionName).getInputs();
	}

	private Set<Arc> getOutputs(String transitionName) {
		return transitions.get(transitionName).getOutputs();
	}

	public int countRemainingTokens() {
		int count = 0;
		for (Place place : places.values()) {
			count += place.getTokenCount();
		}
		return count;
	}

	public void cleanUpRemainingTokens() {
		for (Place place : places.values()) {
			place.removeAllTokens();
		}
	}

	public void addStartToken() {
		start.addToken();
	}

	public boolean hasEndToken() {
		return end.hasTokens();
	}

	public void removeEndToken() {
		end.removeToken();
	}

	public void addEndToken() {
		end.addToken();
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("places", places)
				.add("transitions", transitions)
				.toString();
	}
}
