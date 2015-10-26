package ee.ut.cs.modeling.checker.parsers;

import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import ee.ut.cs.modeling.checker.domain.petrinet.Place;
import org.junit.Test;

import static ee.ut.cs.modeling.checker.PetriNetTestHelper.arcs;
import static ee.ut.cs.modeling.checker.PetriNetTestHelper.noArc;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PetriNetParserSpec {

	PetriNetParser petriNetParser = new PetriNetParser();

	@Test
	public void testPetriNetEndAndStartParsing() {
		PetriNet petriNet = petriNetParser.getPetriNetFromFile("test.pnml");

		Place expectedStart = new Place("p1", noArc(), arcs("p1", "A"));
		Place expectedEnd = new Place("p5", arcs("D", "p5", "E", "p5"), noArc());

		assertThat(petriNet.getStart(), is(equalTo(expectedStart)));
		assertThat(petriNet.getEnd(), is(equalTo(expectedEnd)));
	}

}