package ee.ut.cs.modeling.checker;

import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.PNMLUtils;
import fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PnObjectHLAPI;
import org.deckfour.xes.in.XesXmlParser;
import org.deckfour.xes.model.XLog;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ConformanceCheckerSpec {

	@Test
	public void newConformanceChecker() {
		ConformanceChecker conformanceChecker = new ConformanceChecker();
	}

	@Test
	public void testImportPnml() throws Exception {
		File f = new File("simple_test.pnml");
		HLAPIRootClass rc = PNMLUtils.importPnmlDocument(f, false);
		// May also use getObjects_ArcHLAPI(), getObjects_TransitionHLAPI(), getObjects_PlaceHLAPI() here.
		List<PnObjectHLAPI> petriNetComponents = ((PetriNetDocHLAPI) rc).getNetsHLAPI().get(0)
				.getPagesHLAPI().get(0).getObjectsHLAPI();

	}

	@Test
	public void testImportXes() throws Exception {
		XesXmlParser parser = new XesXmlParser();
		File f = new File("exercise_ab.xes");
		XLog log = parser.parse(f).get(0);
	}
}
