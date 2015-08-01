package jun.mole.collector.bom;

public class BOMNavigator {
	public String appCodeName = "Mozilla";
	public String appName = "Netscape";
	public String appVersion = "5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36";
	public boolean cookieEnabled = true;

	public String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36";
	public String vendor = "Google Inc.";
	public String vendorSub = "";

	private static BOMNavigator single;

	private void init() {

	}

	private BOMNavigator() {
		init();
	}

	public static BOMNavigator createNavigator() {
		if (single == null) {
			single = new BOMNavigator();
		}
		return single;
	}
}
