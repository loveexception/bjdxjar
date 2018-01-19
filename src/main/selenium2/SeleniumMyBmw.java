package selenium2;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import mappers.is.web.bmw.sql.LoginsExtendViewMapper;
import mappers.is.web.bmw.sql.LoginsMapper;
import mappers.is.web.bmw.sql.PersonsMapper;
import mappers.is.web.bmw.sql.SubtopicsMapper;
import mappers.is.web.bmw.sql.TopicsMapper;
import net.sf.cglib.core.CollectionUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nutz.lang.Stopwatch;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Constant;
import utils.ProxyUtils;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.Collections2;
import com.is.web.bmw.entity.ProxyResponse;
import com.is.web.bmw.entity.sql.entity.Persons;
import com.is.web.bmw.entity.sql.entity.Subtopics;
import com.is.web.bmw.entity.sql.entity.Topics;
import com.is.web.bmw.entity.sql.entityenum.LoginsStauts;
import com.is.web.bmw.entity.sql.entityenum.SubtopicsStatus;
import com.is.web.bmw.entity.sql.extend.LoginsExtend;
import com.is.web.common.utils.Utils;

public class SeleniumMyBmw {
	static Map<String,Integer> bag = new HashMap<String,Integer>();
	private static String resource = "mybatis-config.xml";

	// WebDriver driver;

	static Logger logger = LogManager.getLogger(SeleniumMyBmw.class.getName());
	static ChromeDriverService server;
	static List<ProxyResponse> responseList = null;

	static Date beginTime;

	public static void main(String[] args) throws IOException, InterruptedException {

		if (null == args) {
			System.setProperty("webdriver.chrome.driver", Constant.DRIVERPATH);
		} else {
			System.setProperty("webdriver.chrome.driver", args[0]);
		}
		server = ChromeDriverService.createDefaultService();

		while (true) {
			try{
				// responseList =new ProxyUtils().getProxyList();
			}catch (Exception e) {
				e.printStackTrace();
			}
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			SeleniumMyBmw slmb = new SeleniumMyBmw();
			List<LoginsExtend> logins =null;
			try{
				logins = slmb.loginPerson(session);

			}catch (Exception e) {
				e.printStackTrace();
			}

			for (LoginsExtend login : logins) {
				if(login.getPidPassword()==null){
					continue;
				}
				WebDriver driver = checkProxy(login);
				try {
					slmb.loginOne(session, driver, login);
					slmb.readPage(session, driver, login);
					slmb.collPage(session, driver, login);
					slmb.likePage(session, driver, login);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					webQuit(driver);
				}

			}
			List<Subtopics> subpages =new ArrayList<Subtopics>();
			try{
				 subpages = slmb.getSubTpics(session);
			}catch(Exception e){
				
			}
			for (Subtopics subtopics : subpages) {
				WebDriver driver=null;
				try{
					Persons person = slmb.getSubLogins(session, subtopics);
					 driver = checkProxy(new LoginsExtend());

					slmb.loginOne(session, driver, person);
					slmb.repeatOne(session,driver,subtopics);
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					webQuit(driver);
				}
				

			}

			session.close();
			Thread.sleep(1 * 1000);//* 60 
			server.stop();
			server = ChromeDriverService.createDefaultService();

		}

	}



	private List<LoginsExtend> loginPerson(SqlSession session) {
		LoginsExtendViewMapper loginMapper = session.getMapper(LoginsExtendViewMapper.class);
		Map<String, Object> compareCols = new HashMap<String, Object>();
		compareCols.put("planLessThan", new Date());
		LoginsExtend loginFilter = new LoginsExtend();
		loginFilter.setStauts(LoginsStauts.EV_1.enumVal+","+LoginsStauts.EV_3);
		loginFilter.setCompareCols(compareCols);
		loginFilter.setOrderBys(Utils.checkOrderBys(new LinkedHashMap<String, String>(){
		    private static final long serialVersionUID = 7790881489441411175L;
		    { 
			     put("stauts","asc");
			     put("plan","desc");
		    }},loginFilter.columMap));
		return loginMapper.getAllLoginsByPageWithJoin(0, 5, loginFilter);
		
	}



	/**
	 * 立刻关闭
	 * 
	 * @param driver
	 */
	public static void webQuit(WebDriver driver) {
		Date endTime = new Date();
		logger.info("操作完成：用时[{}秒],开始[{}]，结束[{}]", (endTime.getTime() - beginTime.getTime()) / 1000, beginTime, endTime);
		driver.quit();

	}

	

	/**
	 * 设定访问代理
	 * @param login 
	 * 
	 * @return
	 */
	public static WebDriver checkProxy(LoginsExtend login) {
		beginTime = new Date();
		// 随机找个代理
		Random random = new Random();
		try {
			responseList =new ProxyUtils().getProxyList();
		} catch (Exception e) {
			
		}
		if(responseList==null||responseList.size()==0){
			return new ChromeDriver(server);
 
		}
			
		String proxyIpAndPort =  "";
		for (ProxyResponse p : responseList) {
			if(p==null){
				continue;
			}
			Integer  time = bag.get(p.getIp());
			if(time==null){
				bag.put(p.getIp(), 1);
				proxyIpAndPort = p.getIp().concat(":").concat(p.getPort());
				login.setIp(proxyIpAndPort);
				break;
			}else{
				time++;
				bag.put(p.getIp(), time);
			}
			
		}
		if ("".equals(proxyIpAndPort) ){
			login.setIp("proxy is empty");
			logger.error(" proxy is empty",bag.size());
			return new ChromeDriver(server);
		}
		logger.info("使用代理地址为：[{}]", proxyIpAndPort);
				
		DesiredCapabilities cap = new DesiredCapabilities();
		Proxy proxy = new Proxy();
		proxy.setHttpProxy(proxyIpAndPort).setFtpProxy(proxyIpAndPort).setSslProxy(proxyIpAndPort);
		cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
		cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);

		cap.setCapability(CapabilityType.PROXY, proxy);



		
		 WebDriver driver =  new ChromeDriver(server, cap);


		 return driver;
		
	}

	/**
	 * 登录任务
	 * 
	 * @param session
	 * @return
	 */
//	public List<LoginsExtend> login(SqlSession session) {
//		LoginsExtendViewMapper loginMapper = session.getMapper(LoginsExtendViewMapper.class);
//		Map<String, Object> compareCols = new HashMap<String, Object>();
//		compareCols.put("planLessThan", new Date());
//		LoginsExtend loginFilter = new LoginsExtend();
//		loginFilter.setStauts(LoginsStauts.EV_1.enumVal);
//		loginFilter.setCompareCols(compareCols);
//
//		List<LoginsExtend> resultList = loginMapper.getAllLoginsBySearchWithJoin(loginFilter);
//		System.out.println("login size :" + resultList.size());
//		for (int i = 0; i < resultList.size(); i++) {
//			LoginsExtend loginE = resultList.get(i);
//			if (StringUtils.isEmpty(loginE.getPidName()) || StringUtils.isBlank(loginE.getPidPassword())) {
//				logger.error("登录操作失败：登录信息为空,[{}]", loginE.getId());
//			} else {
//				WebDriver driver = null;
//				try {
//					logger.info("登录操作完成,任务ID：[{}]", loginE.getId());
//					driver = checkProxy();
//					WebElement element = loginOne(session, driver, loginE);
//					if (element != null) {
//						LoginsMapper loginM = session.getMapper(LoginsMapper.class);
//						loginE.setStauts(LoginsStauts.EV_9.enumVal);
//						loginE.setSend(new Date());
//						loginM.updLoginsByPrimaryKey(loginE);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					LoginsMapper loginM = session.getMapper(LoginsMapper.class);
//					loginE.setStauts(LoginsStauts.EV_3.enumVal);
//					loginE.setSend(new Date());
//					loginM.updLoginsByPrimaryKey(loginE);
//					String message = driver.getPageSource();
//					logger.error(message);
//
//				} finally {
//					webQuit(driver);
//				}
//
//			}
//			session.commit();
//		}
//		return resultList;
//
//	}

	public WebElement loginOne(SqlSession session, WebDriver driver, LoginsExtend loginE) {
		String url  =String.format(Constant.LOGIN_ONE, loginE.getPidName(),loginE.getPidPassword());
		try{
			driver.get(url );

			new WebDriverWait(driver,10,2000).until(new ExpectedCondition<Boolean>() {
	              public Boolean apply(WebDriver driver) {
	                  System.out.println("sleep:"+driver.findElement(By.xpath("//a[@class='j-cancel']")).isEnabled());
	                  return driver.findElement(By.xpath("//a[@class='j-cancel']")).isEnabled();
	              }
				
	          });
		}catch (Exception e) {
			logger.error(" login error ",loginE.getPidName(),loginE.getPidPassword(),loginE.getPlan(),e);
			LoginsMapper loginM = session.getMapper(LoginsMapper.class);
			loginE.setStauts(LoginsStauts.EV_3.enumVal);
			loginE.setSend(new Date());
			loginM.updLoginsByPrimaryKey(loginE);	
			throw new RuntimeException(e);
		}

//		WebElement elementPass = driver.findElement(By.id(Constant.LOGIN_PAGE_LOGINPASS_DOMID));
//		elementPass.sendKeys(loginE.getPidPassword());
//
//		WebElement element = driver.findElement(By.id(Constant.LOGIN_PAGE_LOGINID_DOMID));
//		element.sendKeys(loginE.getPidName());
//		element.submit();
		LoginsMapper loginM = session.getMapper(LoginsMapper.class);
		loginE.setStauts(LoginsStauts.EV_9.enumVal);
		loginE.setSend(new Date());
		loginM.updLoginsByPrimaryKey(loginE);
		
		return null;


	}

	private void loginOne(SqlSession session, WebDriver driver, Persons person) {
//		driver.get(Constant.LOGIN_URL);
//
//		WebElement elementPass = driver.findElement(By.id(Constant.LOGIN_PAGE_LOGINPASS_DOMID));
//		elementPass.sendKeys(person.getPassword());
//
//		WebElement element = driver.findElement(By.id(Constant.LOGIN_PAGE_LOGINID_DOMID));
//		element.sendKeys(person.getName());
//		element.submit();
		String name;
		try {
			name = URLEncoder.encode(person.getName(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			name ="";
		}
		String pass = DigestUtils.md5Hex(person.getPassword());


		String url  =String.format(Constant.LOGIN_ONE, name,pass);

		driver.get(url );
		
		new WebDriverWait(driver,10,500).until(new ExpectedCondition<Boolean>() {
              public Boolean apply(WebDriver driver) {
                  System.out.println("sleep:"+driver.findElement(By.xpath("//a[@class='j-cancel']")).isEnabled());
                  return driver.findElement(By.xpath("//a[@class='j-cancel']")).isEnabled();
              }
			
          });

	}

	/**
	 * 打开帖子
	 * 
	 * @param logins
	 * @param loginId
	 * @param password
	 * @param forumId
	 * @throws InterruptedException
	 */
	public List<Map> readPage(SqlSession session, WebDriver driver, LoginsExtend loginE) throws InterruptedException {
		TopicsMapper topicMapper = session.getMapper(TopicsMapper.class);

		List<Map> result = topicMapper.getResultForSelectParam(
				" id,name,readed,readedplan,liked,likedplan,collected,collectedplan,oldid,content from t_topics where readedplan>readed limit 0,20");
		if(result.size()<10){
			result = topicMapper.getResultForSelectParam(
					" id,name,readed,readedplan,liked,likedplan,collected,collectedplan,oldid,content from t_topics  ORDER BY RAND() limit 0,20");			
		}
		for (int i = 0; i < result.size(); i++) {
			Map topics = result.get(i);
			int read = (int) topics.get("readed");
			int readPlan = (int) topics.get("readedplan");
			Topics topicsUpd = new Topics();
			topicsUpd.setId((Integer) topics.get("id"));
			topicsUpd.setReadedplan(readPlan);
			topicsUpd.setReaded(read + 1);
			topicMapper.updTopicsByPrimaryKey(topicsUpd);
			System.out.println(topicsUpd);
			session.commit();
		}

		for (int i = 0; i < result.size(); i++) {
			Map topics = result.get(i);
			  new Thread(new Runnable() {  
			        @Override  
			        public void run() {  
						Stopwatch sw = Stopwatch.begin();
						String url = Constant.FORUM_URL.replace("#forumId#", topics.get("oldid").toString());
						InetAddress addr;
						String ip = "";
						try {
							addr = InetAddress.getLocalHost();
							 ip=addr.getHostAddress().toString();//获得本机IP
						} catch (UnknownHostException e1) {
						}
						url +="&local="+ip ;
						try{  
							JavascriptExecutor executor = (JavascriptExecutor) driver;
							executor.executeScript("window.open('" + url + "')");
			            } catch (Throwable e) {  
			            	logger.error(e.getMessage());
			            } finally {  
			            	sw.stop();
							logger.debug("时间",sw.getDuration() );
			            }  
			        }  
			    }).start();  
			  
			  
//			try{
//				
//				myReadOne(topics, driver, loginE);
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//			logger.info("登录操作完成,任务ID：[{}]", topics.get("id").toString());
			
		
		}

		try {  
		    Thread.sleep(60000);
		} catch (InterruptedException e) {  
			
		}  
		return result;

	}

	private WebElement myReadOne(Map topics, WebDriver driver, LoginsExtend loginE) throws InterruptedException {
		driver.get(Constant.FORUM_URL.replace("#forumId#", topics.get("oldid").toString()));
//		new WebDriverWait(driver, 10,500).until(new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver driver) {
//            	logger.debug("read",driver.findElement(By.xpath("//div[@id='f_pst']")).isEnabled());
//                return driver.findElement(By.xpath("//div[@id='f_pst']")).isEnabled();
//            }
//			
//        });
		return null;

	}



	/**
	 * 打开帖子，并点赞
	 * 
	 * @param logins
	 * 
	 * @param loginId
	 * @param password
	 * @param forumId
	 * @return
	 * @throws InterruptedException
	 */
	public List<Map> collPage(SqlSession session, WebDriver driver, LoginsExtend loginE) throws InterruptedException {
		TopicsMapper topicMapper = session.getMapper(TopicsMapper.class);

		List<Map> result = topicMapper.getResultForSelectParam(
				" id,name,readed,readedplan,liked,likedplan,collected,collectedplan,oldid,content from t_topics where collectedplan>collected limit 0,10");

		for (int i = 0; i < result.size(); i++) {
			Map topics = result.get(i);
			int collected = (int) topics.get("collected");
			int collectedplan = (int) topics.get("collectedplan");

			myCollectOne(driver, loginE, topics);

			Topics topicsUpd = new Topics();
			topicsUpd.setId((Integer) topics.get("id"));
			topicsUpd.setCollectedplan(collectedplan);
			topicsUpd.setCollected(collected+1);
			topicMapper.updTopicsByPrimaryKey(topicsUpd);
			session.commit();

		}

		return result;

	}

	private void myCollectOne(WebDriver driver, LoginsExtend loginE, Map topics) throws InterruptedException {
		driver.get(Constant.COLL_URL.replace("#forumId#", "" + topics.get("oldid")));
		Thread.sleep(1000);
	}

	public List<Map> likePage(SqlSession session, WebDriver driver, LoginsExtend loginE) throws InterruptedException {
		TopicsMapper topicMapper = session.getMapper(TopicsMapper.class);

		List<Map> result = topicMapper.getResultForSelectParam(
				" id,name,readed,readedplan,liked,likedplan,collected,collectedplan,oldid,content from t_topics where likedplan>liked limit 0,10");

		for (int i = 0; i < result.size(); i++) {
			Map topics = result.get(i);
			int liked = (int) topics.get("liked");
			int likedplan = (int) topics.get("likedplan");

			myLikeOne(driver, loginE, topics);

			Topics topicsUpd = new Topics();
			topicsUpd.setId((Integer) topics.get("id"));
			topicsUpd.setLikedplan(likedplan);
			topicsUpd.setLiked(liked + 1);
			topicMapper.updTopicsByPrimaryKey(topicsUpd);
			session.commit();

		}

		return result;

	}

	private void myLikeOne(WebDriver driver, LoginsExtend loginE, Map topics) throws InterruptedException {
		driver.get(Constant.LIKE_URL.replace("#forumId#", "" + topics.get("oldid")));
		Thread.sleep(1000);
	}

	private List<Subtopics> getSubTpics(SqlSession session) {
		SubtopicsMapper subtopicsMapper = session.getMapper(SubtopicsMapper.class);

		Subtopics subtopicsFilter = new Subtopics();
		Map<String, Object> compareCols = new HashMap<String, Object>();
		compareCols.put("planLessThan", new Date());
		subtopicsFilter.setStatus(LoginsStauts.EV_1.enumVal);
		subtopicsFilter.setCompareCols(compareCols);
		List<Subtopics> result = subtopicsMapper.getAllSubtopicsByPage(0, 20, subtopicsFilter);
		return result;
	}

	private Persons getSubLogins(SqlSession session, Subtopics subtopics) {
		PersonsMapper personMapper = session.getMapper(PersonsMapper.class);
		Persons person = personMapper.getPersonsByPrimaryKey(new Persons(subtopics.getPid()));

		return person;
	}

	/**
	 * 打开帖子，回帖
	 * 
	 * @param loginId
	 * @param password
	 * @param forumId
	 * @param content
	 * @return
	 * @throws InterruptedException
	 */

	private void repeatOne(SqlSession session, WebDriver driver, Subtopics subtopics) throws InterruptedException {
		TopicsMapper topicMapper = session.getMapper(TopicsMapper.class);
		SubtopicsMapper subtopicsMapper = session.getMapper(SubtopicsMapper.class);
		Topics topic = new Topics();
		topic.setId(subtopics.getTid());
		topic = topicMapper.getTopicsByPrimaryKey(topic);
		logger.info("登录操作完成,任务ID：[{}]", subtopics,topic);

		driver.get(Constant.REPEAT_URL.replace("#forumId#", ""+topic.getOldid()));

		List<WebElement> inputs = driver.findElements(By.xpath("//textarea[@id=\"fastpostmessage\"]"));
		if (!inputs.isEmpty()) {
			WebElement elementInput = inputs.get(0);
			elementInput.sendKeys(subtopics.getContext());
			// elementInput.submit();
			inputs = driver.findElements(By.xpath("//button[@id=\"fastpostsubmit\"]"));
			inputs.get(0).click();
			Thread.sleep(1000);
			subtopics.setStatus(SubtopicsStatus.EV_9.enumVal);
			subtopics.setSend(new Date());
			subtopicsMapper.updSubtopicsByPrimaryKey(subtopics);
		} else {
			subtopics.setStatus(SubtopicsStatus.EV_3.enumVal);
			subtopics.setSend(null);
			subtopicsMapper.updSubtopicsByPrimaryKey(subtopics);
			System.out.println(inputs);
		}

		session.commit();	
	}

}
