package com.maodajun.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.swing.plaf.ListUI;

import mappers.is.web.bmw.sql.LoginsExtendViewMapper;
import mappers.is.web.bmw.sql.LoginsMapper;
import mappers.is.web.bmw.sql.PersonsMapper;
import mappers.is.web.bmw.sql.SubtopicsMapper;
import mappers.is.web.bmw.sql.TopicsMapper;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nutz.http.Response;
import org.nutz.lang.Stopwatch;
import org.yaml.snakeyaml.tokens.DirectiveToken;

import utils.Constant;
import utils.ProxyUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.is.web.bmw.entity.ProxyResponse;
import com.is.web.bmw.entity.sql.entity.Persons;
import com.is.web.bmw.entity.sql.entity.Subtopics;
import com.is.web.bmw.entity.sql.entity.Topics;
import com.is.web.bmw.entity.sql.entityenum.LoginsStauts;
import com.is.web.bmw.entity.sql.entityenum.SubtopicsStatus;
import com.is.web.bmw.entity.sql.extend.LoginsExtend;
import com.is.web.bmw.entity.sql.extend.TopicsExtend;
import com.is.web.common.utils.Utils;

public class MyBmw {
	private static String resource = "mybatis-config.xml";

	//WebClient driver;

	static Logger logger = LogManager.getLogger(MyBmw.class.getName());
	static List<ProxyResponse> responseList = null;
	public Random random = new Random();

	static Date beginTime;

	public static void main(String[] args) throws IOException, InterruptedException {
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
		"org.apache.commons.logging.impl.NoOpLog");
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
		while (true) {
			List<LoginsExtend> logins = null;
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();

			MyBmw slmb = new MyBmw();
			List<ProxyResponse> proxys = new ArrayList<ProxyResponse>();

			try {
				proxys = new ProxyUtils().getProxyList();
				logins = slmb.loginPerson(session);

				for (int i = 0; i < logins.size()&&i<proxys.size(); i++) {
					LoginsExtend login = logins.get(i);
					if (login.getPidPassword() == null) {
						continue;
					}
					 
					try(WebClient client = slmb.webOne(proxys, i)) {
						
						HtmlPage page = slmb.loginOne(session, client, login);
						if(page==null){
							slmb.readPages(session,new WebClient(BrowserVersion.CHROME) , login);
						}else{
							logger.debug("login:",page.getTitleText());
							List<HtmlPage> pages = slmb.readPages(session, client, login);
							
						}
						Thread.sleep(50);
						
					} catch (Exception e) {
						logger.error(e.getMessage());
					} finally {
						
					}
					try(WebClient client = new WebClient(BrowserVersion.CHROME)){
						List<HtmlPage> pages = slmb.collPage(session, client, login);
						pages = slmb.likePage(session, client, login);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				
			}finally {
				session.commit();
				session.close();
			}
			// List<Subtopics> subpages = slmb.getSubTpics(session);
			// for (Subtopics subtopics : subpages) {
			// WebClient driver = null;
			// try {
			// WebClient client = slmb.webOne(proxys);
			//
			// Persons person = slmb.getSubLogins(session, subtopics);
			// slmb.loginOne(session, driver, person);
			// slmb.repeatOne(session,driver,subtopics);
			// } catch (Exception e) {
			// e.printStackTrace();
			// } finally {
			// webQuit(driver);
			// }
			//
			// }

			//session.close();
			Thread.sleep(5 * 1000);// * 60

		}

	}

	private WebClient webOne(List<ProxyResponse> proxys) {
		WebClient web = new WebClient(BrowserVersion.CHROME);
		web.getOptions().setCssEnabled(false);
		web.getOptions().setTimeout(10000);
		web.setJavaScriptTimeout(1000);

		web.setAjaxController(new NicelyResynchronizingAjaxController());

		web.getOptions().setThrowExceptionOnScriptError(false);
		web.getCookieManager().setCookiesEnabled(true);
		return web;
	}

	public WebClient webOne(List<ProxyResponse> proxys, int i) {
		logger.debug(proxys);
		if(proxys.isEmpty()){
			return new WebClient(BrowserVersion.CHROME);
			
		}
		int s = i % (proxys.size() + 1);
		logger.debug("代理：",s , proxys.size());
		ProxyResponse proxy = proxys.get(s);
		String ip = proxy.getIp();
		int port = Integer.parseInt(StringUtils.defaultString(proxy.getPort(), "80"));
		WebClient web = new WebClient(BrowserVersion.CHROME, ip, port);
		logger.info("使用代理地址为：[{}]", "i" + i, "s" + s, ip, "port" + port, proxy.getSec());
		web.getOptions().setCssEnabled(false);
		web.getOptions().setTimeout(2000);
		web.setJavaScriptTimeout(2000);
		web.setAjaxController(new NicelyResynchronizingAjaxController());
		web.getOptions().setThrowExceptionOnFailingStatusCode(false);
		web.getOptions().setThrowExceptionOnScriptError(false);// 如果JavaScript有错误是否抛出，这里的抛出指的是下面获取到的ScriptResult对象为空
		web.getCookieManager().setCookiesEnabled(true);
		return web;
	}

	public List<LoginsExtend> loginPerson(SqlSession session) {
		LoginsExtendViewMapper loginMapper = session.getMapper(LoginsExtendViewMapper.class);
		Map<String, Object> compareCols = new HashMap<String, Object>();
		compareCols.put("planLessThan", new Date());
		LoginsExtend loginFilter = new LoginsExtend();
		loginFilter.setStauts(LoginsStauts.EV_1.enumVal);
		loginFilter.setCompareCols(compareCols);
		//LinkedHashMap<String, String> order =new LinkedHashMap<String,String>();
		//order.put("plan", "desc");
		loginFilter.setOrderBys(Utils.checkOrderBys(new LinkedHashMap<String, String>(){
		    private static final long serialVersionUID = 7790881489441411175L;
		    { 
		     put("plan","desc");
		    }},loginFilter.columMap));
		  
		
		//loginFilter.setOrderBys(order);

		return loginMapper.getAllLoginsByPageWithJoin(0, 10, loginFilter);

	}

	/**
	 * 立刻关闭
	 * 
	 * @param driver
	 */
	public static void webQuit(WebClient driver) {
		Date endTime = new Date();
		logger.info("操作完成：用时[{}秒],开始[{}]，结束[{}]", (endTime.getTime() - beginTime.getTime()) / 1000, beginTime, endTime);
		driver.close();

	}

	/**
	 * 设定访问代理
	 * 
	 * @return
	 */
	public static WebClient checkProxy() {
		beginTime = new Date();
		// 随机找个代理
		Random random = new Random();

		if (responseList == null || responseList.size() == 0) {
			WebClient driver = new WebClient();

			driver.getOptions().setCssEnabled(false);
			driver.getOptions().setTimeout(5000);
			driver.getOptions().setThrowExceptionOnScriptError(false);// 如果JavaScript有错误是否抛出，这里的抛出指的是下面获取到的ScriptResult对象为空
			driver.setJavaScriptEngine(new MyJavaScriptEngine(driver));// 自定义JavaScript引擎，有js错误不打印
            driver.getCookieManager().setCookiesEnabled(true);//开启cookie管理  

			return driver;

		}
		int s = random.nextInt(responseList.size()) % (responseList.size() + 1);
		String ip = responseList.get(s).getIp();
		int port = Integer.parseInt(StringUtils.defaultString(responseList.get(s).getPort(), "80"));
		WebClient driver = new WebClient(BrowserVersion.CHROME, ip, port);
		logger.info("使用代理地址为：[{}]", ip, "" + port);
		driver.getOptions().setCssEnabled(false);
		driver.getOptions().setTimeout(2000);
		driver.getOptions().setThrowExceptionOnScriptError(false);// 如果JavaScript有错误是否抛出，这里的抛出指的是下面获取到的ScriptResult对象为空
		driver.setJavaScriptEngine(new MyJavaScriptEngine(driver));// 自定义JavaScript引擎，有js错误不打印
        driver.getCookieManager().setCookiesEnabled(true);//开启cookie管理  

		return driver;

	}

	/**
	 * 登录任务
	 * 
	 * @param session
	 * @return
	 */

	public HtmlPage loginOne(SqlSession session, WebClient driver, LoginsExtend loginE) {
		HtmlPage page = null;
		Stopwatch sw = Stopwatch.begin();
    	
		try {
			String name = URLEncoder.encode(loginE.getPidName(), "utf-8");
	        //加密后的字符串
			String pass = DigestUtils.md5Hex(loginE.getPidPassword());
			String url = String.format(Constant.LOGIN_ONE, name, pass);
			logger.debug("登陆开始"+url);

			page = driver.getPage(url);
			
			sw.stop();
			//logger.debug("登陆完成"+page.asText() );

			if (page.asText().indexOf("注销") < 0) {
				logger.error("登陆"+page.getTitleText()+ page.asText());
				//throw new RuntimeException("登陆不成功");
				return null;
			}

		} catch (Exception e) {
			logger.error(" login error "+ loginE.getPidName()+ loginE.getPidPassword()+ loginE.getPlan()+
					e.getMessage());
			LoginsMapper loginM = session.getMapper(LoginsMapper.class);
			loginE.setStauts(LoginsStauts.EV_3.enumVal);
			loginE.setSend(new Date());
			loginE.setIp("error");
			loginM.updLoginsByPrimaryKey(loginE);

			throw new RuntimeException(e);
		}

		LoginsMapper loginM = session.getMapper(LoginsMapper.class);
		loginE.setStauts(LoginsStauts.EV_9.enumVal);
		loginE.setSend(new Date());
		loginM.updLoginsByPrimaryKey(loginE);

		return page;

	}



	/**
	 * 打开帖子
	 * 
	 * @param logins
	 * @param loginId
	 * @param password
	 * @param forumId
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 */
//	public List<HtmlPage> readPage(SqlSession session, WebClient driver, LoginsExtend loginE)
//			throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {
//		TopicsMapper topicMapper = session.getMapper(TopicsMapper.class);
//
//		List<Map> result = topicMapper.getResultForSelectParam(
//				" id,name,readed,readedplan,liked,likedplan,collected,collectedplan,oldid,content from t_topics where readedplan>readed limit 0,10");
//		List<HtmlPage> pages = new ArrayList<HtmlPage>();
//		for (int i = 0; i < result.size(); i++) {
//			Map topics = result.get(i);
//			int read = (int) topics.get("readed");
//			int readPlan = (int) topics.get("readedplan");
//			HtmlPage page = myReadOne(topics, driver, loginE);
//			pages.add(page);
//			logger.info("登录操作完成,任务ID：[{}]", topics.get("id").toString());
//
//			Topics topicsUpd = new Topics();
//			topicsUpd.setId((Integer) topics.get("id"));
//			topicsUpd.setReadedplan(readPlan);
//			topicsUpd.setReaded(read + 1);
//			topicMapper.updTopicsByPrimaryKey(topicsUpd);
//		}
//
//		return pages;
//
//	}
	public List<HtmlPage> readPages(SqlSession session, WebClient web, LoginsExtend loginE){
		TopicsMapper topicMapper = session.getMapper(TopicsMapper.class);

		List<Map> result = topicMapper.getResultForSelectParam(
				" id,name,readed,readedplan,liked,likedplan,collected,collectedplan,oldid,content from t_topics where readedplan>readed limit 0,30");
		List<HtmlPage> pages = new ArrayList<HtmlPage>();
		
		
		final int count = result.size(); // 计数次数  

		final CountDownLatch latch = new CountDownLatch(count);  
		for (Map m : result) {  
        	TopicsExtend topics = new TopicsExtend();
        	topics.setId((Integer) m.get("id"));
        	topics.setReaded((Integer) m.get("readed")+1);  	
        	topicMapper.updTopicsByPrimaryKey(topics);
		    new Thread(new Runnable() {  
		        @Override  
		        public void run() {  
					Stopwatch sw = Stopwatch.begin();
//					String url = Constant.FORUM_URL.replace("#forumId#", m.get("oldid").toString()) ;
					
					InetAddress addr;
					String ip = "";
					try {
						addr = InetAddress.getLocalHost();
						 ip=addr.getHostAddress().toString();//获得本机IP
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    
					String url = Constant.FORUM_URL.replace("#forumId#", m.get("oldid").toString())+"&local="+ip  ;
					System.out.println(url);
					try{  
				        web.addRequestHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 7_0_2 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A501 Safari/9537.53");
				        web.addRequestHeader("Referer", Constant.LOGIN_URL);//设置请求报文头里的refer字段  
						web.getCookieManager().setCookiesEnabled(true);

				        //web.addRequestHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 7_0_2 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A501 Safari/9537.53");
		   				HtmlPage p = web.getPage(url);
		   				System.out.println("阅读"+url);
						pages.add(p);
			
		            } catch (Throwable e) {  
		            	logger.error(e.getMessage());
		            } finally {  
		            	sw.stop();
						logger.debug("时间",sw.getDuration() );
		                latch.countDown();  
		            }  
		        }  
		    }).start();  
		}  
		try {  
		    latch.await(30,TimeUnit.SECONDS);  
		} catch (InterruptedException e) {  
			
		}  
		return pages;
	}

	public HtmlPage myReadOne(Map topics, WebClient driver, LoginsExtend loginE)
			throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		Stopwatch sw = Stopwatch.begin();
		logger.debug("开始阅读");
		final HtmlPage page = driver.getPage(Constant.FORUM_URL.replace("#forumId#", topics.get("oldid").toString()));
    	sw.stop();
		logger.debug(sw.getDuration() );
		return page;

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
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 */
	public List<HtmlPage> collPage(SqlSession session, WebClient driver, LoginsExtend loginE)
			throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		TopicsMapper topicMapper = session.getMapper(TopicsMapper.class);

		List<Map> result = topicMapper.getResultForSelectParam(
				"id,name,readed,readedplan,liked,likedplan,collected,collectedplan,oldid,content from t_topics where collectedplan>collected limit 0,30");
		List<HtmlPage> pages = new ArrayList<HtmlPage>();
		for (int i = 0; i < result.size(); i++) {
			Map topics = result.get(i);
			int collected = (int) topics.get("collected");
			int collectedplan = (int) topics.get("collectedplan");

			HtmlPage page = myCollectOne(driver, loginE, topics);
			pages.add(page);
			Topics topicsUpd = new Topics();
			topicsUpd.setId((Integer) topics.get("id"));
			topicsUpd.setCollectedplan(collectedplan);
			topicsUpd.setCollected(collected + 1);
			topicMapper.updTopicsByPrimaryKey(topicsUpd);
			session.commit();

		}

		return pages;

	}

	public HtmlPage myCollectOne(WebClient driver, LoginsExtend loginE, Map topics)
			throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		logger.debug(topics.get("oldid"));
		final HtmlPage page = driver.getPage(Constant.FORUM_URL.replace("#forumId#", topics.get("oldid").toString()));
		return page;

	}

	public List<HtmlPage> likePage(SqlSession session, WebClient driver, LoginsExtend loginE)
			throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		TopicsMapper topicMapper = session.getMapper(TopicsMapper.class);

		List<Map> result = topicMapper.getResultForSelectParam(
				"id,name,readed,readedplan,liked,likedplan,collected,collectedplan,oldid,content from t_topics where likedplan>liked limit 0,10");
		List<HtmlPage> pages = new ArrayList<HtmlPage>();
		for (int i = 0; i < result.size(); i++) {
			Map topics = result.get(i);
			int liked = (int) topics.get("liked");
			int likedplan = (int) topics.get("likedplan");

			HtmlPage page = myLikeOne(driver, loginE, topics);
			pages.add(page);
			Topics topicsUpd = new Topics();
			topicsUpd.setId((Integer) topics.get("id"));
			topicsUpd.setLikedplan(likedplan);
			topicsUpd.setLiked(liked + 1);
			topicMapper.updTopicsByPrimaryKey(topicsUpd);
			session.commit();

		}

		return pages;

	}

	public HtmlPage myLikeOne(WebClient driver, LoginsExtend loginE, Map topics)
			throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		final HtmlPage page = driver.getPage(Constant.LIKE_URL.replace("#forumId#", topics.get("oldid").toString()));
		return page;

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

	public void repeatOne(SqlSession session, WebClient driver, Subtopics subtopics) throws InterruptedException {
		TopicsMapper topicMapper = session.getMapper(TopicsMapper.class);
		SubtopicsMapper subtopicsMapper = session.getMapper(SubtopicsMapper.class);
		Topics topic = new Topics();
		topic.setId(subtopics.getTid());
		topic = topicMapper.getTopicsByPrimaryKey(topic);
		logger.info("登录操作完成,任务ID：[{}]", subtopics, topic);

		// driver.get(Constant.FORUM_URL.replace("#forumId#",
		// ""+topic.getOldid()));
		//
		// List<WebElement> inputs =
		// driver.findElements(By.xpath("//textarea[@id=\"fastpostmessage\"]"));
		// if (!inputs.isEmpty()) {
		// WebElement elementInput = inputs.get(0);
		// elementInput.sendKeys(subtopics.getContext());
		// // elementInput.submit();
		// inputs =
		// driver.findElements(By.xpath("//button[@id=\"fastpostsubmit\"]"));
		// inputs.get(0).click();
		// Thread.sleep(1000);
		// subtopics.setStatus(SubtopicsStatus.EV_9.enumVal);
		// subtopics.setSend(new Date());
		// subtopicsMapper.updSubtopicsByPrimaryKey(subtopics);
		// } else {
		// subtopics.setStatus(SubtopicsStatus.EV_3.enumVal);
		// subtopics.setSend(null);
		// subtopicsMapper.updSubtopicsByPrimaryKey(subtopics);
		// }

		session.commit();
	}

}
