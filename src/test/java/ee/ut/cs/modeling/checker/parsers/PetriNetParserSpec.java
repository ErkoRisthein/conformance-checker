package ee.ut.cs.modeling.checker.parsers;

import com.google.common.collect.ImmutableSet;
import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import ee.ut.cs.modeling.checker.domain.petrinet.arc.Arc;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Place;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PetriNetParserSpec {

	PetriNetParser petriNetParser = new PetriNetParser();

	@Test
	public void testPetriNetEndAndStartParsing() {
		PetriNet petriNet = petriNetParser.getPetriNetFromFile("test.pnml");

		Place expectedStart = new Place("p1", ImmutableSet.<Arc>of(), ImmutableSet.of(new Arc("p1", "A")));
		Place expectedEnd = new Place("p5", ImmutableSet.of(new Arc("D", "p5"), new Arc("E", "p5")), ImmutableSet.<Arc>of());

		assertThat(petriNet.getStart(), is(equalTo(expectedStart)));
		assertThat(petriNet.getEnd(), is(equalTo(expectedEnd)));
	}
}