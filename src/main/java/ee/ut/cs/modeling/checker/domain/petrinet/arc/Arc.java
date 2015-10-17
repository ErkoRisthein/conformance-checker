package ee.ut.cs.modeling.checker.domain.petrinet.arc;

import ee.ut.cs.modeling.checker.domain.petrinet.node.Node;

public interface Arc {

	Node getFrom();
	Node getTo();

}
