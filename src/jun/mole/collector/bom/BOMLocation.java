package jun.mole.collector.bom;

import java.net.MalformedURLException;
import java.net.URL;

public class BOMLocation {

	public String protocol;
	public String hostname;
	public String href;
	public String path;
	public int port;

	public BOMLocation() {
	}

	public BOMLocation(URL url) {
		protocol = url.getProtocol() + ":";
		hostname = url.getHost();

		href = url.toString();
		path = url.getPath();
		port = url.getPort();
		if (port == -1)
			port = url.getDefaultPort();
	}

	public BOMLocation(String url) throws MalformedURLException {
		this(new URL(url));
	}

}
