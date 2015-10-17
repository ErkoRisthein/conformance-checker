package ee.ut.cs.modeling.checker.domain.petrinet;

import ee.ut.cs.modeling.checker.domain.petrinet.arc.Arc;
import ee.ut.cs.modeling.checker.domain.petrinet.node.Node;

import java.util.List;

public class PetriNet {

	private List<Node> nodes;
	private List<Arc> arcs;

	public List<Node> getNodes() {
		return nodes;
	}

	public List<Arc> getArcs() {
		return arcs;
	}
}
