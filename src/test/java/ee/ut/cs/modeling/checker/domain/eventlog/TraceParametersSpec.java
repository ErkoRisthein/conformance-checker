package ee.ut.cs.modeling.checker.domain.eventlog;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TraceParametersSpec {

	TraceParameters traceParameters = new TraceParameters();

	@Test
	public void meanIs1Point5() {
		traceParameters.addEnabledTransition(1);
		traceParameters.addEnabledTransition(2);

		double meanEnabledTransitions = traceParameters.getMeanEnabledTransitions();

		assertThat(meanEnabledTransitions, is(equalTo(1.5d)));
	}

}