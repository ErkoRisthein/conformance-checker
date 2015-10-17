package ee.ut.cs.modeling.checker.domain.petrinet.node;

import ee.ut.cs.modeling.checker.domain.petrinet.arc.Arc;

public interface Node {

	Arc getInput();
	Arc getOutput();

}
