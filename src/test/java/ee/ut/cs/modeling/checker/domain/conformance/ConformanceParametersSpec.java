package ee.ut.cs.modeling.checker.domain.conformance;

import ee.ut.cs.modeling.checker.domain.eventlog.Trace;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ConformanceParametersSpec {

	ConformanceParameters conformanceParameters = new ConformanceParameters(new Trace(), 0);

	@Test
	public void meanIs1Point5() {
		conformanceParameters.addEnabledTransition(1);
		conformanceParameters.addEnabledTransition(2);

		double meanEnabledTransitions = conformanceParameters.getMeanEnabledTransitions();

		assertThat(meanEnabledTransitions, is(equalTo(1.5d)));
	}

}