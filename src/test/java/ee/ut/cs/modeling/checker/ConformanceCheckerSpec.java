package ee.ut.cs.modeling.checker;

import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.PNMLUtils;
import org.junit.Test;

import java.io.File;

public class ConformanceCheckerSpec {

	@Test
	public void newConformanceChecker() {
		ConformanceChecker conformanceChecker = new ConformanceChecker();
	}

	@Test
	public void testImportPnml() throws Exception {
		File f = new File("simple_test.pnml");
		HLAPIRootClass rc = PNMLUtils.importPnmlDocument(f, false);
	}
}
