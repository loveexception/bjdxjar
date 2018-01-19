package selenium2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mappers.is.web.bmw.sql.LoginsExtendViewMapper;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.nutz.http.Http;
import org.nutz.http.Response;

import utils.Constant;
import utils.ProxyUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.is.web.bmw.entity.ProxyResponse;
import com.is.web.bmw.entity.sql.entity.Logins;
import com.is.web.bmw.entity.sql.extend.LoginsExtend;

public class SeleniumTest{

	static Logger logger = LogManager.getLogger(SeleniumTest.class.getName());
	
	@Before
	public void setUp(){
		System.setProperty("webdriver.chrome.driver",Constant.DRIVERPATH);
		
//		SeleniumMyBmw.checkProxy();
	}
	
//	@Test
//	public void testSe(){
//		SeleniumMyBmw.login("小海豹", "123456zxc");
//	}
	
//	@Test
//	public void testMybatis() throws IOException{
//		String resource = "mybatis-config.xml";
//		InputStream inputStream = Resources.getResourceAsStream(resource);
//		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//		SqlSession session = sqlSessionFactory.openSession();
//		
//		LoginsExtendViewMapper loginMapper = session.getMapper(LoginsExtendViewMapper.class);
//		System.out.println(loginMapper.getAllLoginsBySearchWithJoin(new LoginsExtend(new Logins())));
//	}
	
//	@Test
//	public void testProxy(){
//		List<ProxyResponse> responseList = ProxyUtils.getProxyList();
//		System.out.println(responseList.size());
//	}
	
	
//	@Test
//	public void testrepeat(){
//		bjdxSeleniumMyBmw t = new bjdxSeleniumMyBmw();
//		String str = t.httpPost("http://m2.beijingdaxing.cn/Api/Comment/add","{    \"head\": {    },    \"data\":{    \"member_id\":2,                \"did\":\"20268\",        \"content\":\"好好真的好真的很\"  }}");
//		System.out.println(str);
//	}
	
	@Test
   public void testcreatebody(){
		
//	   String str = httpPost("http://m2.beijingdaxing.cn/Api/Comment/add", 3, "@大兴人，高层火灾逃生应该这样做", 20277, "很给力");
		bjdxSeleniumMyBmw t = new bjdxSeleniumMyBmw();
		String str =t.createBody(4, "@大兴人，高层火灾逃生应该这样做", 20277, "看起来好壮观啊");
	   System.out.println(str);
		String body="{\"head\": {    },\"data\":{    \"member_id\":4, \"title\":\"@大兴人，高层火灾逃生应该这样做\", \"did\":\"20277\",\"content\":\"看起来好壮观啊\"  }}";

	   assertEquals(body, str);
   }
	
	
}
