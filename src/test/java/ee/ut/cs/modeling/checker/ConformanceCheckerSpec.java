package ee.ut.cs.modeling.checker;

import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.PNMLUtils;
import fr.lip6.move.pnml.framework.utils.exception.ImportException;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import org.junit.Test;

import java.io.File;

public class ConformanceCheckerSpec {

	@Test
	public void newConformanceChecker() {
		ConformanceChecker conformanceChecker = new ConformanceChecker();
	}

	@Test
	public void testImportPnml() {
		File f = new File("test1.pnml");
		try {
			HLAPIRootClass rc = PNMLUtils.importPnmlDocument(f, false);
		} catch (ImportException | InvalidIDException e) {
			e.printStackTrace();
		}


	}
}
