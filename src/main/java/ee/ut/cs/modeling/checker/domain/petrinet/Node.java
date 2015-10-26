package ee.ut.cs.modeling.checker.domain.petrinet;

import java.util.Set;

public interface Node {

	String getName();

	Node addInputs(Arc... arcs);

	Node addOutputs(Arc... arcs);

	Set<Arc> getInputs();

	Set<Arc> getOutputs();

}
