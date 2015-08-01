package jun.mole.collector;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.script.SimpleScriptContext;

import jun.mole.collector.bom.BOMDocument;
import jun.mole.collector.bom.BOMLocation;
import jun.mole.collector.bom.BOMWindow;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.cyberneko.html.HTMLConfiguration;
import org.cyberneko.html.parsers.DOMFragmentParser;
import org.cyberneko.html.parsers.DOMParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Weibo {
	private static final Logger logger = LoggerFactory.getLogger(Weibo.class);
	private static final String WEIBO_URL = "http://weibo.com/u/1696137703";

	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine nashorn = manager.getEngineByName("nashorn");
	Document doc;

	ArrayList<String> jsList = new ArrayList<String>();

	StringBuilder jsCodes = new StringBuilder();

	public void fetch(String url) throws Exception {
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;

		try {
			client = HttpClients.createDefault();

			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(3000).setConnectTimeout(3000).build();
			HttpGet httpGet = new HttpGet(url);

			httpGet.setConfig(requestConfig);
			httpGet.addHeader("Connection", "keep-alive");
			httpGet.addHeader("Cache-Control", "max-age=0");
			httpGet.addHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			httpGet.addHeader(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36");
			httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
			httpGet.addHeader("Accept-Language",
					"zh,zh-CN;q=0.8,en-US;q=0.6,en;q=0.4");
			httpGet.addHeader(
					"Cookie",
					"SUB=_2AkMi5WmRf8NjqwJRmPkcxGnlZYx2yQDEiebDAHzsJxIxHnMF7CQiCFTxK9pKAV3-mn_6QF4twq4a; SUBP=0033WrSXqPxfM72-Ws9jqgMF55z29P9D9W5ZezrznMXdl.ag65_Jx125; SINAGLOBAL=5936069602612.406.1438246578558; _s_tentry=developer.51cto.com; UOR=,,sishuok.com; YF-Page-G0=2d32d406b6cb1e7730e4e69afbffc88c; Apache=976566986646.5032.1438256824704; ULV=1438256824716:2:2:2:976566986646.5032.1438256824704:1438246578566");

			response = client.execute(httpGet);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.println("Connect failed.");
				return;
			}
			DOMParser parser = new DOMParser();

			parser.setFeature(
					"http://cyberneko.org/html/features/augmentations", true);
			parser.setProperty(
					"http://cyberneko.org/html/properties/names/elems", "lower");
			parser.setProperty(
					"http://cyberneko.org/html/properties/default-encoding",
					"utf-8");
			parser.parse(new InputSource(response.getEntity().getContent()));

			doc = parser.getDocument();
			doc.setDocumentURI(WEIBO_URL);

		} finally {
			if (response != null)
				response.close();
			if (client != null)
				client.close();
		}

	}

	static void createDOM(InputStream in) throws Exception {
		DOMParser parser = new DOMParser();

		parser.setFeature("http://cyberneko.org/html/features/augmentations",
				true);
		parser.setProperty("http://cyberneko.org/html/properties/names/elems",
				"lower");
		parser.setProperty(
				"http://cyberneko.org/html/properties/default-encoding",
				"utf-8");
		parser.parse(new InputSource(in));

		Document doc = parser.getDocument();
		NodeList jsNodes = doc.getElementsByTagName("script");
		for (int i = 0; i < jsNodes.getLength(); ++i) {
			System.out.println(jsNodes.item(i).toString());
		}

	}

	void evalJs(Node jsFragment) throws Exception {
		if (jsFragment.getNodeType() != Node.ELEMENT_NODE) {
			System.out.println("not a  element node");
			return;
		}
		if (!((Element) jsFragment).getTagName().toLowerCase().equals("script")) {
			System.out.println("not a  javascript element node");
			return;
		}
		String jsText = jsFragment.getFirstChild().getNodeValue();
		System.out.println("Eval... ");
		System.out.println(jsText);
		Object ret = nashorn.eval(jsText);
		System.out.println("Result: " + ret);
	}

	void evalAllJs() throws Exception {
		if (doc == null) {
			System.out.println("doc == null");
			return;
		}

		extractAllJs();

		for (int i = 0; i < jsList.size(); ++i) {
			System.out.format("eval %d : %s\n", i, jsList.get(i));
			if (i == 5) {
				nashorn.eval("print(FM.view)");
			}
			nashorn.eval(jsList.get(i));
		}
		// nashorn.eval(jsCodes.toString());

	}

	void extractAllJs() throws IOException, ScriptException {
		NodeList nodelist = doc.getElementsByTagName("script");
		for (int i = 0; i < nodelist.getLength(); ++i) {
			if (nodelist.item(i).getFirstChild().getNodeType() == Node.TEXT_NODE) {
				// jsCodes.append(nodelist.item(i).getFirstChild().getNodeValue());
				// jsCodes.append('\n');

				jsList.add(nodelist.item(i).getFirstChild().getNodeValue());
			}
		}
		// FileWriter writer = new FileWriter("jsCode.js");
		// writer.write(jsCodes.toString());
		// writer.close();
	}

	private void createBOM() throws MalformedURLException,
			FileNotFoundException, ScriptException {
		Bindings bindings = new SimpleBindings();
		BOMDocument bomDoc = new BOMDocument(doc);
		// bindings.put("window", bomWindow);
		// bindings.put("document", bomWindow.doc);
		// bindings.put("location", bomWindow.location);
		// bindings.put("navigator", bomWindow.navigator);
		bindings.put("curDoc", bomDoc);
		// bindings.put("document.location", bomDoc.location);
		// bindings.put("curURL", new URL(WEIBO_URL));

		nashorn.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);

		nashorn.eval(new FileReader("src/bom.js"));
	}

	static void download() throws Exception {
		URL url = new URL(WEIBO_URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("Accept-Language",
				"zh,zh-CN;q=0.8,en-US;q=0.6,en;q=0.4");
		conn.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("connection", "Keep-Alive");

		InputStream inStream = conn.getInputStream();
		OutputStream out = new FileOutputStream("bbb.txt");
		byte[] buffer = new byte[1024];
		int bytesRead = 0;
		while ((bytesRead = inStream.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
		}
		out.close();
		inStream.close();
	}

	private static void printDoc0(Node node, PrintWriter out) {
		out.printf("NodeName: %s, NodeValue: %s\n", node.getNodeName(),
				node.getNodeValue());
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); ++i) {
			out.printf("\t");
			printDoc0(nodeList.item(i), out);
		}
	}

	public void printDoc(PrintWriter out) {
		printDoc0(doc, out);
	}

	public static void main(String args[]) throws Exception {
		logger.info("Enter main");

		Weibo weibo = new Weibo();
		weibo.fetch(Weibo.WEIBO_URL);

		// weibo.nashorn.eval("print(fun1(1,2))");
		weibo.createBOM();
		// weibo.nashorn.eval("print(curDoc)");
		// weibo.nashorn.eval("print(curURL)");
		// weibo.nashorn.eval("print(document.getFirstChild())");
		// weibo.nashorn.eval("print(document.location)");
		// weibo.evalAllJs();
		weibo.nashorn.eval(new FileReader("src/hello.js"));
		logger.info("Leave main");
	}

}
