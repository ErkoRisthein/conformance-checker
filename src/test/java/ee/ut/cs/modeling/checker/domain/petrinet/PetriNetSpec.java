package ee.ut.cs.modeling.checker.domain.petrinet;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PetriNetSpec {

	@Test
	public void DoesNotHaveAllInputTokens() {
		PetriNet petriNet = new PetriNet();

		Place p1 = new Place("p1");
		Transition a = new Transition("A");

		p1.to(a);
		a.from(p1);

		petriNet.addPlace(p1);
		petriNet.addTransition(a);

		assertThat(petriNet.transitionHasAllInputTokens("A"), is(equalTo(false)));
	}

	@Test
	public void hasAllInputTokens() {
		PetriNet petriNet = new PetriNet();

		Place p1 = new Place("p1");
		Transition a = new Transition("A");

		p1.to(a);
		a.from(p1);

		petriNet.addPlace(p1);
		petriNet.addTransition(a);

		p1.addToken();

		assertThat(petriNet.transitionHasAllInputTokens("A"), is(equalTo(true)));
	}

	@Test
	public void createMissingTokenWorks() {
		PetriNet petriNet = new PetriNet();

		Place p1 = new Place("p1");
		Transition a = new Transition("A");

		p1.to(a);
		a.from(p1);

		petriNet.addPlace(p1);
		petriNet.addTransition(a);

		assertThat(petriNet.transitionHasAllInputTokens("A"), is(equalTo(false)));
		petriNet.createMissingToken("A");
		assertThat(petriNet.transitionHasAllInputTokens("A"), is(equalTo(true)));
	}

	@Test
	public void consumeInputTokens() {
		PetriNet petriNet = new PetriNet();

		Place p1 = new Place("p1");
		Transition a = new Transition("A");

		p1.to(a);
		a.from(p1);

		petriNet.addPlace(p1);
		petriNet.addTransition(a);

		p1.addToken();

		assertThat(petriNet.transitionHasAllInputTokens("A"), is(equalTo(true)));
		petriNet.consumeInputTokens("A");
		assertThat(petriNet.transitionHasAllInputTokens("A"), is(equalTo(false)));
	}

	@Test
	public void produceOutputTokens() {
		PetriNet petriNet = new PetriNet();

		Place p1 = new Place("p1");
		Transition a = new Transition("A");
		Place p2 = new Place("p2");

		p1.to(a);
		a.from(p1).to(p2);
		p2.from(a);

		petriNet.addPlace(p1, p2);
		petriNet.addTransition(a);

		assertThat(p2.hasTokens(), is(equalTo(false)));
		petriNet.produceOutputTokens("A");
		assertThat(p2.hasTokens(), is(equalTo(true)));
	}

	@Test
	public void countRemainingTokensIsZero() {
		PetriNet petriNet = new PetriNet();

		Place p1 = new Place("p1");
		Transition a = new Transition("A");

		p1.to(a);
		a.from(p1);

		petriNet.addPlace(p1);
		petriNet.addTransition(a);

		assertThat(petriNet.countRemainingTokens(), is(equalTo(0)));
	}

	@Test
	public void countRemainingTokensIsOne() {
		PetriNet petriNet = new PetriNet();

		Place p1 = new Place("p1");
		Transition a = new Transition("A");

		p1.to(a);
		a.from(p1);

		petriNet.addPlace(p1);
		petriNet.addTransition(a);

		p1.addToken();
		assertThat(petriNet.countRemainingTokens(), is(equalTo(1)));
	}

	@Test
	public void cleanUpRemainingTokens() {
		PetriNet petriNet = new PetriNet();

		Place p1 = new Place("p1");
		Transition a = new Transition("A");

		p1.to(a);
		a.from(p1);

		petriNet.addPlace(p1);
		petriNet.addTransition(a);

		p1.addToken();

		assertThat(petriNet.countRemainingTokens(), is(equalTo(1)));
		petriNet.cleanUpRemainingTokens();
		assertThat(petriNet.countRemainingTokens(), is(equalTo(0)));
	}

	@Test
	public void countEnabledTransitionsIsZero() {
		PetriNet petriNet = new PetriNet();

		Place p1 = new Place("p1");
		Transition a = new Transition("A");

		p1.to(a);
		a.from(p1);

		petriNet.addPlace(p1);
		petriNet.addTransition(a);

		assertThat(petriNet.countEnabledTransitions(), is(equalTo(0)));
	}

	@Test
	public void countEnabledTransitionsIsOne() {
		PetriNet petriNet = new PetriNet();

		Place p1 = new Place("p1");
		Transition a = new Transition("A");

		p1.to(a);
		a.from(p1);

		petriNet.addPlace(p1);
		petriNet.addTransition(a);

		p1.addToken();

		assertThat(petriNet.countEnabledTransitions(), is(equalTo(1)));
	}

}