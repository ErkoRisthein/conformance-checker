package ee.ut.cs.modeling.checker.domain.petrinet;

import com.google.common.base.MoreObjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PetriNet {

	private Map<String, Place> places = new HashMap<>();
	private Map<String, Transition> transitions = new HashMap<>();

	public void addPlace(Place place) {
		places.put(place.getName(), place);
	}

	public void addTransition(Transition transition) {
		transitions.put(transition.getName(), transition);
	}

	public Place getStart() {
		for (Place place : places.values()) {
			if (place.getInputs().isEmpty()) {
				return place;
			}
		}
		return null;
	}

	public Place getEnd() {
		for (Place place : places.values()) {
			if (place.getOutputs().isEmpty()) {
				return place;
			}
		}
		return null;
	}

	public boolean transitionHasAllInputTokens(String transitionName) {
		for (Arc input : getInputs(transitionName)) {
			String placeName = input.getFrom();
			if (!places.get(placeName).hasTokens()) {
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

	public int countEnabledTransitions() {
		int count = 0;
		for (Place place : places.values()) {
			if (place.hasTokens()) {
				count += place.getOutputCount();
			}
		}
		return count;
	}

	public int countTransitions() {
		return transitions.size();
	}

	public int countPlaces() {
		return places.size();
	}

	public void addStartToken() {
		getStart().addToken();
	}

	public boolean hasEndToken() {
		return getEnd().hasTokens();
	}

	public void removeEndToken() {
		getEnd().removeToken();
	}

	public void addEndToken() {
		getEnd().addToken();
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("places", places)
				.add("transitions", transitions)
				.toString();
	}

	public Transition getTransition(String name) {
		return transitions.get(name) != null ? transitions.get(name) : Transition.NULL;
	}

	public Place getPlace(String name) {
		return places.get(name) != null ? places.get(name) : Place.NULL;
	}
}
