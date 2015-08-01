package jun.mole.collector.bom;

import org.w3c.dom.Document;

public class BOMWindow {
	public BOMDocument doc;
	public BOMLocation location;
	public BOMNavigator navigator;

	public BOMWindow(Document d) {
		doc = new BOMDocument(d);
		location = doc.location;
		navigator = BOMNavigator.createNavigator();
	}

	public int setTimeout(Object obj, int interval) {

		return 1;
	}

}
