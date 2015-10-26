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

		Place expectedStart = new Place("p1");
		Place expectedEnd = new Place("p5");

		assertThat(petriNet.getStart(), is(equalTo(expectedStart)));
		assertThat(petriNet.getEnd(), is(equalTo(expectedEnd)));
		assertThat(petriNet.countPlaces(), is(equalTo(5)));
		assertThat(petriNet.countTransitions(), is(equalTo(5)));


	}

}