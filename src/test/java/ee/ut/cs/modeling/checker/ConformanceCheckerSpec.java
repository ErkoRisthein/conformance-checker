package ee.ut.cs.modeling.checker;

import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.PNMLUtils;
import org.deckfour.xes.in.XesXmlParser;
import org.deckfour.xes.model.XLog;
import org.junit.Test;

import java.io.File;

public class ConformanceCheckerSpec {

	@Test
	public void newConformanceChecker() {
		ConformanceChecker conformanceChecker = new ConformanceChecker();
	}

	@Test
	public void testImportPnml() throws Exception {
		File f = new File("exercise_ab.pnml");
		HLAPIRootClass rc = PNMLUtils.importPnmlDocument(f, false);
	}

	@Test
	public void testImportXes() throws Exception {
		XesXmlParser parser = new XesXmlParser();
		File f = new File("exercise_ab.xes");
		XLog log = parser.parse(f).get(0);
	}
}
