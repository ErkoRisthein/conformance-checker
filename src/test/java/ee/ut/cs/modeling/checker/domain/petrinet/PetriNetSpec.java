package ee.ut.cs.modeling.checker.domain.petrinet;

import com.google.common.collect.ImmutableSet;
import ee.ut.cs.modeling.checker.domain.petrinet.arc.Arc;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Place;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Transition;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PetriNetSpec {

	@Test
	public void DoesNotHaveAllInputTokens() {

		PetriNet petriNet = new PetriNet();
		petriNet.addPlace(new Place("p1", noArc(), arc("p1", "A")));
		petriNet.addTransition(new Transition("A", arc("p1", "A"),  arc("A", "p2")));

		assertThat(petriNet.transitionHasAllInputTokens("A"), equalTo(false));

	}

	@Test
	public void hasAllInputTokens() {
		Transition transition = new Transition("A", arc("p1", "A"),  arc("A", "p2"));

		Place place = new Place("p1", noArc(), arc("p1", "A"));
		place.addToken();

		PetriNet petriNet = new PetriNet();
		petriNet.addPlace(place);
		petriNet.addTransition(transition);

		assertThat(petriNet.transitionHasAllInputTokens("A"), equalTo(true));

	}

	private ImmutableSet<Arc> noArc() {
		return ImmutableSet.of();
	}

	private ImmutableSet<Arc> arc(String from, String to) {
		return ImmutableSet.of(new Arc(from, to));
	}
}