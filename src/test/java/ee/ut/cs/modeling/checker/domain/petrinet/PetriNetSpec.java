package ee.ut.cs.modeling.checker.domain.petrinet;

import com.google.common.collect.ImmutableSet;
import ee.ut.cs.modeling.checker.domain.petrinet.arc.Arc;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Place;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Transition;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PetriNetSpec {

	@Test
	public void DoesNotHaveAllInputTokens() {

		PetriNet petriNet = new PetriNet();
		petriNet.addPlace(new Place("p1", noArc(), arc("p1", "A")));
		petriNet.addTransition(new Transition("A", arc("p1", "A"),  arc("A", "p2")));

		assertThat(petriNet.transitionHasAllInputTokens("A"), is(equalTo(false)));
	}

	@Test
	public void hasAllInputTokens() {
		Transition transition = new Transition("A", arc("p1", "A"),  arc("A", "p2"));

		Place place = new Place("p1", noArc(), arc("p1", "A"));
		place.addToken();

		PetriNet petriNet = new PetriNet();
		petriNet.addPlace(place);
		petriNet.addTransition(transition);

		assertThat(petriNet.transitionHasAllInputTokens("A"), is(equalTo(true)));
	}

	@Test
	public void createMissingTokenWorks() {
		PetriNet petriNet = new PetriNet();
		petriNet.addPlace(new Place("p1", noArc(), arc("p1", "A")));
		petriNet.addTransition(new Transition("A", arc("p1", "A"), arc("A", "p2")));

		assertThat(petriNet.transitionHasAllInputTokens("A"), is(equalTo(false)));
		petriNet.createMissingToken("A");
		assertThat(petriNet.transitionHasAllInputTokens("A"), is(equalTo(true)));
	}

	@Test
	public void consumeInputTokens() {
		Transition transition = new Transition("A", arc("p1", "A"), arc("A", "p2"));

		Place place = new Place("p1", noArc(), arc("p1", "A"));
		place.addToken();

		PetriNet petriNet = new PetriNet();
		petriNet.addPlace(place);
		petriNet.addTransition(transition);

		assertThat(petriNet.transitionHasAllInputTokens("A"), is(equalTo(true)));
		petriNet.consumeInputTokens("A");
		assertThat(petriNet.transitionHasAllInputTokens("A"), is(equalTo(false)));
	}

	@Test
	public void produceOutputTokens() {
		PetriNet petriNet = new PetriNet();

		Place inputPlace = new Place("p1", noArc(), arc("p1", "A"));
		petriNet.addPlace(inputPlace);

		petriNet.addTransition(new Transition("A", arc("p1", "A"), arc("A", "p2")));

		Place outputPlace = new Place("p2", arc("A", "p2"), noArc());
		petriNet.addPlace(outputPlace);

		assertThat(outputPlace.hasTokens(), is(equalTo(false)));
		petriNet.produceOutputTokens("A");
		assertThat(outputPlace.hasTokens(), is(equalTo(true)));
	}

	@Test
	public void countRemainingTokensIsZero() {
		PetriNet petriNet = new PetriNet();
		petriNet.addPlace(new Place("p1", noArc(), arc("p1", "A")));
		petriNet.addTransition(new Transition("A", arc("p1", "A"), arc("A", "p2")));

		assertThat(petriNet.countRemainingTokens(), is(equalTo(0)));
	}

	@Test
	public void countRemainingTokensIsOne() {
		PetriNet petriNet = new PetriNet();

		Place place = new Place("p1", noArc(), arc("p1", "A"));
		petriNet.addPlace(place);
		place.addToken();

		petriNet.addTransition(new Transition("A", arc("p1", "A"), arc("A", "p2")));

		assertThat(petriNet.countRemainingTokens(), is(equalTo(1)));
	}

	@Test
	public void cleanUpRemainingTokens() {
		PetriNet petriNet = new PetriNet();

		Place place = new Place("p1", noArc(), arc("p1", "A"));
		petriNet.addPlace(place);
		place.addToken();

		petriNet.addTransition(new Transition("A", arc("p1", "A"), arc("A", "p2")));

		assertThat(petriNet.countRemainingTokens(), is(equalTo(1)));
		petriNet.cleanUpRemainingTokens();
		assertThat(petriNet.countRemainingTokens(), is(equalTo(0)));
	}

	@Test
	public void countEnabledTransitionsIsZero() {
		PetriNet petriNet = new PetriNet();

		petriNet.addPlace(new Place("p1", noArc(), arc("p1", "A")));
		petriNet.addTransition(new Transition("A", arc("p1", "A"), arc("A", "p2")));

		assertThat(petriNet.countEnabledTransitions(), is(equalTo(0)));
	}

	@Test
	public void countEnabledTransitionsIsOne() {
		PetriNet petriNet = new PetriNet();

		Place place = new Place("p1", noArc(), arc("p1", "A"));
		place.addToken();
		petriNet.addPlace(place);
		petriNet.addTransition(new Transition("A", arc("p1", "A"), arc("A", "p2")));

		assertThat(petriNet.countEnabledTransitions(), is(equalTo(1)));
	}

	private ImmutableSet<Arc> noArc() {
		return ImmutableSet.of();
	}

	private ImmutableSet<Arc> arc(String from, String to) {
		return ImmutableSet.of(new Arc(from, to));
	}
}