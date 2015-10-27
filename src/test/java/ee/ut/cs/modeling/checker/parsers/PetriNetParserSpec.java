package ee.ut.cs.modeling.checker.parsers;

import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import ee.ut.cs.modeling.checker.domain.petrinet.Place;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PetriNetParserSpec {

	PetriNetParser petriNetParser = new PetriNetParser();

	@Test
	public void testPetriNetEndAndStartParsing() {
		PetriNet petriNet = petriNetParser.getPetriNetFromFile("test.pnml");

		Place p1 = new Place("p1");
		Place p5 = new Place("p5");

		assertThat(petriNet.start(), is(equalTo(p1)));
		assertThat(petriNet.end(), is(equalTo(p5)));
		assertThat(petriNet.countPlaces(), is(equalTo(5)));
		assertThat(petriNet.countTransitions(), is(equalTo(5)));


	}

}