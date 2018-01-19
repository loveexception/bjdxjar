package utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nutz.castor.castor.Object2String;
import org.nutz.http.Http;
import org.nutz.http.ProxySwitcher;
import org.nutz.http.Request;
import org.nutz.http.Response;
import org.nutz.json.Json;
import org.nutz.lang.Dumps;
import org.nutz.lang.Stopwatch;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.trans.NutTransaction;
//import org.nutz.http.Http;
//import org.nutz.http.Response;
//import org.nutz.json.Json;
//import org.nutz.lang.Stopwatch;
import org.openqa.selenium.remote.server.handler.RefreshPage;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.is.web.bmw.entity.ProxyResponse;
import com.maodajun.http.MyJavaScriptEngine;

public class ProxyUtils {
    private static final Log log = Logs.getLog(ProxyUtils.class);
    //http://dev.25-88.cn/api/?key=20170811185835377&getnum=30&filter=1&ipaddress=%u5317%u4EAC%2C%u5E7F%u4E1C%2C%u4E0A%u6D77&area=1&sarea=1&formats=2
	public List<ProxyResponse> getProxyList() throws Exception {
		StringBuffer sbParamer = new StringBuffer();
		sbParamer.append("http://dec.ip3366.net/api/?").append("key=20170811185835377").append("&getnum=100")
		//.append("&anonymoustype=2")
		.append("&filter=1")
		//.append("&ipaddress=%u4E0A%u6D77%2C%u5317%u4EAC%2C%u5E7F%u4E1C%2C%u676D%u5DDE.%u6DF1%u5733")
		.append("&area=1").append("&formats=2");
		Response response = null;

		try{
			response = Http.get(sbParamer.toString());
		}catch (Exception e) {
			return new ArrayList<ProxyResponse>();
			
		}
			String postdata = response.getContent("GBK");


			
			postdata = StringUtils.trim(postdata);

			Gson gson = new Gson();
			log.debug(sbParamer.toString());
			Map[] maps = null;
			try{
				maps = Json.fromJsonAsArray(Map.class, postdata);
			}catch (Exception e) {
				maps = null;
			}
			List<ProxyResponse> oks = new ArrayList<ProxyResponse>();
			List<ProxyResponse> errors = new ArrayList<ProxyResponse>();
			 checked(maps, oks, errors);
			System.out.println("errors:"+errors.size());
			System.out.println("oks:"+oks.size());

			return new ArrayList<ProxyResponse>(oks);
		

	}

	public List<ProxyResponse> checkList(List<ProxyResponse> responseList) {
		List<ProxyResponse> result = new ArrayList<ProxyResponse>();
		for (ProxyResponse proxy : responseList) {
			try {
				myCheck(proxy.ip, proxy.port);
				result.add(proxy);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return result;
	}

	public void myCheck(String ip, String port) throws ClientProtocolException, IOException {

		HttpClient client = HttpClientBuilder.create().setProxy(new HttpHost(ip, Integer.parseInt(port))).build();

		// 设置代理结束
		HttpGet get = new HttpGet("https://www.mybmwclub.cn");
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000).setConnectionRequestTimeout(1000)
				.setSocketTimeout(1000).build();
		get.setConfig(requestConfig);
		client.execute(get);

		return;
	}

	public void check(String ip, String port) {
		port = StringUtils.trim(port);
		HttpClient client = HttpClientBuilder.create().setProxy(new HttpHost(ip, Integer.parseInt(port))).build();

	}

	public static List<ProxyResponse> getProxyList2() {
		StringBuffer url = new StringBuffer();
		url.append("http://dec.ip3366.net/api/?")
		.append("key=20170811185835377")
		.append("&getnum=30")
				//.append("&anonymoustype=3")
				.append("&ipaddress=%u4E0A%u6D77%2C%u5317%u4EAC%2C%u5E7F%u4E1C%2C%u676D%u5DDE.%u6DF1%u5733")
				.append("&area=1").append("&formats=2");
		;

		Response response = Http.get(url.toString());
		String str = response.getContent("GBK");
		Map[] maps = Json.fromJsonAsArray(Map.class, str);

		List<ProxyResponse> oks = new ArrayList<ProxyResponse>();
		List<ProxyResponse> errors = new ArrayList<ProxyResponse>();
		 myCheck(maps, oks, errors);
		System.out.println("errors:"+errors.size());
		System.out.println("oks:"+oks.size());
		return oks;
	}
	private static void checked(Map[] maps, List<ProxyResponse> oks, List<ProxyResponse> errors) {
		if(maps==null){
			return ;
		}
		final int count = maps.length; // 计数次数  

		final CountDownLatch latch = new CountDownLatch(count); 

		for (Map m : maps) {  
		    new Thread(new Runnable() {  
		        @Override  
		        public void run() {  
					Stopwatch sw = Stopwatch.begin();
					String ip = "" + m.get("Ip");
					int port = Integer.parseInt("" + m.get("Port"));
	   				ProxyResponse pr = new ProxyResponse();
	   				pr.setIp(ip);
	   				pr.setPort(""+port);
	   				
	   				WebClient web = new WebClient(BrowserVersion.CHROME, ip, port);
					try{  
						web.getOptions().setCssEnabled(false);
						web.getOptions().setJavaScriptEnabled(false);
						web.getOptions().setTimeout(5000);

						if(ip.contains("106.14.189.88")){
		   					throw new RuntimeException("beijing ali yun");
		   		
		   				}
		   				if(ip.contains("106.14.189.88")){
		   					throw new RuntimeException("beijing ali yun");
		   				}
		   				HtmlPage p = web.getPage("http://www.wsfk.cn/");
						if(p.getTitleText().contains("北京维思远达软件技术有限公司")){
							oks.add(pr);
							
						}
		            } catch (Throwable e) {  
		            	log.error(e.getMessage());
		            	errors.add(pr);
		            } finally {  
		            	sw.stop();
		            	pr.setSec(""+sw.getDuration());
						//log.debug("|" + sw.getDuration() + "\t");
		                latch.countDown();  
		                web.close();
		            }  
		        }  
		    }).start();  
		}  
		try {  
		    latch.await(5,TimeUnit.SECONDS);  
		} catch (InterruptedException e) {  
			
		}  
		System.out.println("Finish");  
	}
	private static void myCheck(Map[] maps, List<ProxyResponse> oks, List<ProxyResponse> errors) {
		
		for (Map m : maps) {
			ProxyResponse pr = new ProxyResponse();
			Stopwatch sw = Stopwatch.begin();
			String ip = "" + m.get("Ip");
			int port = Integer.parseInt("" + m.get("Port"));
			Http.setHttpProxy("" + ip, port);
			try {
				Http.get("https://www.mybmwclub.cn", 4 * 1000);
				pr.setIp("" + ip);
				pr.setPort("" + port);
				oks.add(pr);
			} catch (Exception e) {
				errors.add(pr);
			} finally {
				sw.stop();
				
				//System.out.print("|" + sw.getDuration() + "\t");
			}

		}
	}
}
