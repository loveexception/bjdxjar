package com.maodajun.http;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.is.web.bmw.entity.ProxyResponse;
import com.is.web.bmw.entity.sql.entity.Subtopics;
import com.is.web.bmw.entity.sql.extend.LoginsExtend;

import mappers.is.web.bmw.sql.TopicsMapper;
import utils.Constant;
import utils.ProxyUtils;

public class IpProxyToolsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProxyString() throws Exception {
		ProxyUtils utils = new ProxyUtils();
		List list =utils.getProxyList();
		assertNotNull(list);
		assertTrue(list.size()>0);
		
	}

	@Test
	public void testProxy() throws Exception {
		MyBmw my = new MyBmw();
		ProxyUtils utils = new ProxyUtils();
		List list =utils.getProxyList();
		for ( int i = 0 ; i < 20 ; i++) {
			my.webOne(list, i);

		}
	}
	@Test
	public void testLoginOne() throws Exception {
		MyBmw my = new MyBmw();
		WebClient driver = new WebClient(BrowserVersion.CHROME);
		LoginsExtend loginE = new LoginsExtend();
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		my.loginOne(session, driver, loginE);

	}
	@Test
	public void testReadOne() throws Exception {
		MyBmw my = new MyBmw();
		WebClient driver = new WebClient(BrowserVersion.BEST_SUPPORTED);
		driver.getOptions().setCssEnabled(false);
		driver.getOptions().setTimeout(5000);
		driver.getOptions().setThrowExceptionOnScriptError(false);// 如果JavaScript有错误是否抛出，这里的抛出指的是下面获取到的ScriptResult对象为空
		driver.setJavaScriptEngine(new MyJavaScriptEngine(driver));// 自定义JavaScript引擎，有js错误不打印

		LoginsExtend loginE = new LoginsExtend();
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		List<LoginsExtend> list= my.loginPerson(session);
		System.out.println(list);
		loginE = list.get(0);
		System.out.println( String.format(Constant.LOGIN_ONE, loginE.getPidName(), loginE.getPidPassword()));
		HtmlPage page = my.loginOne(session, driver, loginE);
		System.out.println(page.asText().indexOf("注销"));
		Map<String, String> topics = new HashMap<String,String>();
		topics .put("oldid", "3222");
		System.out.println(Constant.FORUM_URL.replace("#forumId#", topics.get("oldid").toString()));
		page = my.myReadOne(topics, driver, loginE);
		System.out.println(page.asXml().indexOf("注销"));

	}
	@Test
	public void testReadTwo() throws Exception {
		MyBmw my = new MyBmw();
		//List<ProxyResponse> proxys =new  ProxyUtils().getProxyList();
		//ProxyResponse proxy = proxys.iterator().next();
		//WebClient driver = new WebClient(BrowserVersion.CHROME,proxy.getIp(),Integer.parseInt(proxy.getPort()));
		//WebClient driver = creatWebClient();

		LoginsExtend loginE = new LoginsExtend();
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		List<LoginsExtend> list= my.loginPerson(session);
		System.out.println(list);
		loginE = list.get(0);
		System.out.println( String.format(Constant.LOGIN_ONE, loginE.getPidName(), loginE.getPidPassword()));
		HtmlPage page ;
		try(WebClient driver =creatWebClient()){
		page = my.loginOne(session,driver , loginE);
		}
		System.out.println(page.asText().indexOf("注销"));
	
		TopicsMapper topicMapper = session.getMapper(TopicsMapper.class);

		List<Map> result = topicMapper.getResultForSelectParam(
				" id,name,readed,readedplan,liked,likedplan,collected,collectedplan,oldid,content from t_topics where readedplan>readed limit 0,10");
		for (Map topics : result) {
			System.out.println("maodajun.start");
			try(WebClient driver = creatWebClient()){
			page = my.myReadOne(topics, driver, loginE);
			}
			System.out.println("maodajun.end");

		}
		
		System.out.println(page.asXml().indexOf("注销"));
	}

	private WebClient creatWebClient() {
		WebClient driver = new WebClient(BrowserVersion.BEST_SUPPORTED);
		driver.getOptions().setCssEnabled(false);
		driver.getOptions().setJavaScriptEnabled(true);
		driver.getOptions().setTimeout(1000);
		driver.setJavaScriptTimeout(1000);
		driver.waitForBackgroundJavaScript(1000);
		driver.getOptions().setThrowExceptionOnScriptError(false);// 如果JavaScript有错误是否抛出，这里的抛出指的是下面获取到的ScriptResult对象为空
		//driver.setJavaScriptEngine(new MyJavaScriptEngine(driver));// 自定义JavaScript引擎，有js错误不打印
		return driver;
	}
}
