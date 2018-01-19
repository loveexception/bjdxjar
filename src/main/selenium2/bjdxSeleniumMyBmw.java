package selenium2;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
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
import mappers.is.web.bmw.sql.PlansMapper;
import mappers.is.web.bmw.sql.StatistMapper;
import mappers.is.web.bmw.sql.SubtopicsMapper;
import mappers.is.web.bmw.sql.TopicsMapper;
import net.sf.cglib.core.CollectionUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.http.Header;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
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
import com.is.web.bmw.entity.sql.entity.Plans;
import com.is.web.bmw.entity.sql.entity.Statist;
import com.is.web.bmw.entity.sql.entity.Subtopics;
import com.is.web.bmw.entity.sql.entity.Topics;
import com.is.web.bmw.entity.sql.entityenum.LoginsStauts;
import com.is.web.bmw.entity.sql.entityenum.SubtopicsStatus;
import com.is.web.bmw.entity.sql.extend.LoginsExtend;
import com.is.web.common.utils.Utils;

public class bjdxSeleniumMyBmw {
	static Map<String, Integer> bag = new HashMap<String, Integer>();
	private static String resource = "mybatis-config.xml";

	static Logger logger = LogManager.getLogger(bjdxSeleniumMyBmw.class.getName());


	public static void main(String[] args) throws IOException, InterruptedException {


		bjdxSeleniumMyBmw slmb = new bjdxSeleniumMyBmw();

		while (true) {
			SqlSession session  = null;
			try {
				Stopwatch sw = Stopwatch.begin();//秒表

				session = getSession();
				LoginsExtend login = slmb.checkUserAlive(session);
				if (Lang.isEmpty(login)) {
					continue;
				}
				slmb.loginOne(session, login);
				session.commit();
				
				logger.info(sw.toString()+ "who is :"+login.getUid()+"-"+login.getPidName());
				
				
				List<Map> pagers = slmb.readPage(session);
				session.commit();
				int count = 0;
				for (Map topic : pagers) {
					count += slmb.clickPV( topic); // 可以替换为HTTP
				}

				logger.info(sw.toString()+ "click:"+pagers.size()+"pages");
				slmb.countPV(session, count);

				List<Subtopics> subpages = replyTopic(session, slmb);
				session.commit();
				logger.info(sw.toString()+ "reply:"+subpages.size()+"topic");
				slmb.countReply(session, Lang.length(subpages));
				sw.stop();				
				logger.info("end:"+sw.toString());
		
			} catch (Exception e) {
				logger.error(e);
			}finally {
				session.close();
			}
		}

	}

	private LoginsExtend checkUserAlive(SqlSession session) throws InterruptedException {
		List<Map> result = isUserSleep(session);
		if (Lang.length(result) == 0) {
			Thread.sleep(10 * 60000); // 休眠10分钟
			return null;
		}
		
		List<LoginsExtend> logins = isUserStandBy(session);
		if (Lang.length(logins) == 0) {
			return null;
		}
		LoginsExtend login = logins.iterator().next();
		if (Lang.isEmpty(login.getPidPassword())) {
			return null;
		}
		return login;
	}

	public List<Map> isUserSleep(SqlSession session) {
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("H");
		String ymd = df.format(day);
		PlansMapper plansMapper = session.getMapper(PlansMapper.class);
		List<Map> result = plansMapper
				.getResultForSelectParam("name from t_plans where name like" + '"' + "%" + ymd + "%" + '"');
		return result;
	}

	public static List<Subtopics> replyTopic(SqlSession session, bjdxSeleniumMyBmw slmb) throws InterruptedException {
		List<Subtopics> subpages = new ArrayList<Subtopics>();
			subpages = slmb.getSubTpics(session);
	
		for (Subtopics subtopics : subpages) {
			PersonsMapper personMapper = session.getMapper(PersonsMapper.class);
			Persons person = personMapper.getPersonsByPrimaryKey(new Persons(subtopics.getPid()));
			slmb.repeatOne(session, person, subtopics);

		}
		return subpages;
	}

	private static SqlSession getSession() throws IOException {
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		return session;
	}

	public List<LoginsExtend> isUserStandBy(SqlSession session) {
			LoginsExtendViewMapper loginMapper = session.getMapper(LoginsExtendViewMapper.class);
			Map<String, Object> compareCols = new HashMap<String, Object>();
			compareCols.put("planLessThan", new Date());
			LoginsExtend loginFilter = new LoginsExtend();
			loginFilter.setStauts(LoginsStauts.EV_1.enumVal + "," + LoginsStauts.EV_3);
			loginFilter.setCompareCols(compareCols);
			loginFilter.setOrderBys(Utils.checkOrderBys(new LinkedHashMap<String, String>() {
				private static final long serialVersionUID = 7790881489441411175L;
				{
					put("stauts", "asc");
					put("plan", "desc");
				}
			}, loginFilter.columMap));
			return loginMapper.getAllLoginsByPageWithJoin(0, 1, loginFilter);
	

	}



	public WebElement loginOne(SqlSession session, LoginsExtend loginE) {

		LoginsMapper loginM = session.getMapper(LoginsMapper.class);
		loginE.setStauts(LoginsStauts.EV_9.enumVal);
		loginE.setSend(new Date());
		loginM.updLoginsByPrimaryKey(loginE);

		session.commit();

		return null;

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
	public List<Map> readPage(SqlSession session) throws InterruptedException {
		TopicsMapper topicMapper = session.getMapper(TopicsMapper.class);

		List<Map> result = topicMapper.getResultForSelectParam(
				" id,name,readed,readedplan,liked,likedplan,collected,collectedplan,oldid,content from t_topics where readedplan>readed order by oldid desc limit 0,20");
		
		for (Map topic : result) {
			Topics topicsUpd = makeOldTopic(topic);
			topicMapper.updTopicsByPrimaryKey(topicsUpd);
		}
				
		return result;

	}

	public Topics makeOldTopic(Map topics) {

		int read = (int) topics.get("readed");
		int readPlan = (int) topics.get("readedplan");
		Topics topicsUpd = new Topics();
		topicsUpd.setId((Integer) topics.get("id"));
		topicsUpd.setReadedplan(readPlan);
		topicsUpd.setReaded(read + 10);
		return topicsUpd;
	}


	public int clickPV( Map topics) {
		String url = Constant.FORUM_URL.replace("#forumId#", topics.get("oldid").toString());
		for(int i =0 ; i<10 ;i++){
			Response res =Http.get(url);
			System.out.print(".");
		}
		return 10;
	}
	public void countPV(SqlSession session, int count) {
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String ymd = df.format(day);
		System.out.println(ymd);
		StatistMapper statistM = session.getMapper(StatistMapper.class);
		List<Map> staresult = statistM.getResultForSelectParam(
				" id,ymd,pageview,userview from t_statist where ymd like " + '"' + "%" + ymd + "%" + '"');
		Iterator<Map> it = staresult.iterator();
		if (it.hasNext()) {
			Map statists = it.next();
			Integer pageview = (Integer) statists.get("pageview");
			Statist statistUpd = new Statist();
			statistUpd.setId((Integer) statists.get("id"));
			statistUpd.setPageview(pageview + count);
			statistM.updStatistByPrimaryKey(statistUpd);
		} else {
			Statist statistUpd = new Statist();
			statistUpd.setYmd(ymd);
			statistUpd.setPageview(20);
			statistUpd.setUserview(0);
			statistUpd.setPageview(count);

			statistM.addStatist(statistUpd);
		}
		session.commit();
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

	private void repeatOne(SqlSession session, Persons person, Subtopics subtopics) throws InterruptedException {
		TopicsMapper topicMapper = session.getMapper(TopicsMapper.class);
		SubtopicsMapper subtopicsMapper = session.getMapper(SubtopicsMapper.class);
		Topics topic = new Topics();
		topic.setId(subtopics.getTid());
		topic = topicMapper.getTopicsByPrimaryKey(topic);

		String title = topic.getName();
		String did = topic.getOldid();
		String content = subtopics.getContext();
		String body = createBody(Integer.parseInt(person.getOldid()), title, Integer.parseInt(did), content);
		String str = httpPost(Constant.REPEAT_URL, body);
		subtopics.setStatus(SubtopicsStatus.EV_9.enumVal);
		subtopics.setSend(new Date());
		subtopicsMapper.updSubtopicsByPrimaryKey(subtopics);

	}

	public void countReply(SqlSession session, int count) {
		String ymd = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH");

		StatistMapper mapper = session.getMapper(StatistMapper.class);
		List<Map> result = mapper.getResultForSelectParam(
				" id,ymd,pageview,userview from t_statist where ymd like " + '"' + "%" + ymd + "%" + '"');

		Iterator<Map> it = result.iterator();
		Statist statist = new Statist();

		if (it.hasNext()) {
			Map statists = it.next();
			int userview = (int) statists.get("userview");
			statist.setId((Integer) statists.get("id"));
			statist.setUserview(userview + count);
			mapper.updStatistByPrimaryKey(statist);
		} else {
			statist.setYmd(ymd);
			statist.setUserview(count);
			mapper.addStatist(statist);
		}

		session.commit();
	}

	public String httpPost(String url, String body) {
		Map map = new HashMap();
		map.put("Content-Type", "application/json");
		Header header = Header.create(map);
		Response response = Http.post3(url, body, header, 60000);
		String str = response.getContent();
		return str;
	}

	public String createBody(int member_id, String title, int did, String content) {
		String body = "{\"head\": {    },\"data\":{    \"member_id\":" + member_id + ", \"title\":\"" + title
				+ "\", \"did\":\"" + did + "\",\"content\":\"" + content + "\"  }}";
		return body;

	}

}
