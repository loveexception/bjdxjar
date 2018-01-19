package selenium2;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.nutz.castor.Castors;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.json.Json;
import org.nutz.lang.Stopwatch;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import com.is.web.bmw.entity.ProxyResponse;
import com.is.web.bmw.entity.sql.extend.LoginsExtend;

import utils.Constant;
import utils.ProxyUtils;

public class login {
	static String resource = "mybatis-config.xml";
	static SqlSession session;
	static SeleniumMyBmw slmb ;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		 session = sqlSessionFactory.openSession();
		 slmb = new SeleniumMyBmw();
		 slmb.responseList =getProxyList2();
		 System.setProperty("webdriver.chrome.driver", Constant.DRIVERPATH);
		 slmb.server = ChromeDriverService.createDefaultService();
		
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
//	public void test() {
//		WebDriver driver = slmb.checkProxy();
//		LoginsExtend login = new LoginsExtend();
//		login.setPidName("朱笑笑");
//		login.setPidPassword("123456");
//		slmb.loginOne(session, driver, login);
//	}
	public static List<ProxyResponse> getProxyList2(){
		Response response = Http.get("http://dec.ip3366.net/api/?key=20170306100439929&getnum=10&anonymoustype=3&area=1");
		String str = response.getContent();
		String[] maps = str.split("\n");
		
		List<ProxyResponse> oks = new ArrayList<ProxyResponse>();
		List<ProxyResponse> errors = new ArrayList<ProxyResponse>();
		for (String m : maps) {
			ProxyResponse pr = new ProxyResponse();
			String[] poxy = m.split(":"); 
			String ip = poxy[0];
			String p =poxy[1];
			p = StringUtils.trim(p);
			int port = Castors.me().castTo(p, int.class);
			Http.setHttpProxy(ip, port);
			try{
				response = Http.get("https://www.mybmwclub.cn", 5 * 1000);
				pr.setIp(ip);
				pr.setPort(""+port);
				oks.add(pr);
				return oks;
			}catch (Exception e) {
				errors.add(pr);
			}finally{

			}
			

		}
		return oks;
	}
}
