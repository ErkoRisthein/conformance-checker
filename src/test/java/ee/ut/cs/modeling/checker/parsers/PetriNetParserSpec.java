package ee.ut.cs.modeling.checker.parsers;

import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import ee.ut.cs.modeling.checker.domain.petrinet.Place;
import org.junit.Test;

import static ee.ut.cs.modeling.checker.PetriNetTestHelper.arc;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PetriNetParserSpec {

	PetriNetParser petriNetParser = new PetriNetParser();

	@Test
	public void testPetriNetEndAndStartParsing() {
		PetriNet petriNet = petriNetParser.getPetriNetFromFile("test.pnml");

		Place expectedStart = new Place("p1").addOutputs(arc("p1", "A"));
		Place expectedEnd = new Place("p5").addInputs(arc("D", "p5"), arc("E", "p5"));

		assertThat(petriNet.getStart(), is(equalTo(expectedStart)));
		assertThat(petriNet.getEnd(), is(equalTo(expectedEnd)));
	}

}