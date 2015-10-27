package ee.ut.cs.modeling.checker.domain.petrinet;

import com.google.common.base.MoreObjects;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public class PetriNet {

	private Map<String, Place> places = new HashMap<>();
	private Map<String, Transition> transitions = new HashMap<>();

	public void addPlace(Place... places) {
		this.places.putAll(stream(places).collect(toMap(Place::name, place -> place)));
	}

	public void addTransition(Transition... transitions) {
		this.transitions.putAll(stream(transitions).collect(toMap(Transition::name, transition -> transition)));
	}

	public Place getStart() {
		return places.values().stream().filter(Place::hasZeroInputs).findFirst().get();
	}

	public Place getEnd() {
		return places.values().stream().filter(Place::hasZeroOutputs).findFirst().get();
	}

	public int countRemainingTokens() {
		return places.values().stream().mapToInt(Place::getTokenCount).sum();
	}

	public void cleanUpRemainingTokens() {
		places.values().forEach(Place::removeAllTokens);
	}

	public int countEnabledTransitions() {
		return places.values().stream().filter(Place::hasTokens).mapToInt(Place::getOutputCount).sum();
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

	public Transition getTransition(String name) {
		return transitions.get(name) != null ? transitions.get(name) : Transition.NULL;
	}

	public Place getPlace(String name) {
		return places.get(name) != null ? places.get(name) : Place.NULL;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("places", places)
				.add("transitions", transitions)
				.toString();
	}
}
