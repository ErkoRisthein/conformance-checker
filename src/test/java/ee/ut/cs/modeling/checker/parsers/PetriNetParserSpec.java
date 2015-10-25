package ee.ut.cs.modeling.checker.parsers;

import ee.ut.cs.modeling.checker.domain.petrinet.PetriNet;
import org.junit.Test;

public class PetriNetParserSpec {

	PetriNetParser converters = new PetriNetParser();

	@Test
	public void testPetriNetGeneration() {
		PetriNet petriNet = converters.getPetriNetFromFile("test.pnml");
	}
}