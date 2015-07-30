package jun.mole.collector;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Weibo {
	private static final Logger logger = LoggerFactory.getLogger(Weibo.class);
	private static final String WEIBO_URL = "http://weibo.com/u/1696137703";

	public static void fetch(String url, OutputStream output)
			throws IOException {
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;

		try {
			client = HttpClients.createDefault();

			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(1000).setConnectTimeout(1000).build();
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
					"SINAGLOBAL=868032223079.3535.1426339716301; "
							+ "wvr=6; SUHB=0BLof5x0GZnlwJ; UOR=www.douyutv.com,weibo.com,login.sina.com.cn; "
							+ "YF-Page-G0=0dccd34751f5184c59dfe559c12ac40a; SUB=_2AkMi5SuHdcNhrABTm_4cxWjiaYhH-jjGieTAAH_pJhExUSp-7SRJAKkdtmYEnyRqic-kO0g9DpAV; "
							+ "SUBP=0033WrSXqPxfM72wWs9jqgMF55529P9D9WWNM4IRU0_Xi6eZ99Hpg_Kd5JpX5K-t; _s_tentry=passport.weibo.com; Apache=5056501722428.947.1438229682320; "
							+ "ULV=1438229682332:46:3:3:5056501722428.947.1438229682320:1438166146968; YF-Ugrow-G0=69bfe9ce876ec10bd6de7dcebfb6883e; "
							+ "TC-Ugrow-G0=370f21725a3b0b57d0baaf8dd6f16a18");

			System.out.println("Request Headers:");
			for (Header h : httpGet.getAllHeaders()) {
				System.out.format("%s: %s\n", h.getName(), h.getValue());
			}

			response = client.execute(httpGet);

			System.out.println(response.getStatusLine().toString());

			System.out.println("Response Headers:");
			Header[] headers = response.getAllHeaders();
			for (int i = 0; i < headers.length; ++i) {
				System.out.println(headers[i]);
			}

			HttpEntity entity = response.getEntity();

			if (entity != null) {
				System.out.println("Content-Type: " + entity.getContentType());
				System.out.println("Content-Length: "
						+ entity.getContentLength());
				entity.writeTo(output);
			}

		} catch (ProtocolException ex) {
			logger.debug(ex.getMessage());
		} finally {
			if (response != null)
				response.close();
			if (client != null)
				client.close();
		}

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

	public static void main(String args[]) throws Exception {
		logger.info("Enter main");

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine nashorn = manager.getEngineByName("nashorn");
		nashorn.eval("load('src/hello.js')");

		OutputStream file = new FileOutputStream("aaa.txt");
		try {
			fetch(WEIBO_URL, file);
			file.close();
		} finally {
			file.close();
		}

		logger.info("Leave main");
	}
}
