package utils;

public class Constant {

//	public static final String DRIVERPATH = "/Users/maodajun/Downloads/chromedriver";  
	public static final String DRIVERPATH = "/Users/maodajun/Downloads/chromedriver"; 
	
	public static final String LOGIN_URL = "https://www.mybmwclub.cn/member.php?mod=logging&action=login&no_collection=1&referer=";
	
	public static final String LOGIN_ONE ="https://www.mybmwclub.cn/member.php?mod=logging&action=login&loginsubmit=yes&username=%s&password=%s&no_collection=1";
//	public static final String LOGIN_ONE ="http://m2.beijingdaxing.cn/Api/Member/login";
	
	public static final String LOGIN_PAGE_LOGINID_DOMID = "username";
	public static final String LOGIN_PAGE_LOGINPASS_DOMID = "password3";

//	public static final String REPEAT_URL = "http://m2.beijingdaxing.cn/Home/News/detail/?member_id=#forumId#";
	public static final String REPEAT_URL = "http://m2.beijingdaxing.cn/Api/Comment/add";
	
	public static final String FORUM_URL = "http://m2.beijingdaxing.cn/Home/News/detail/?id=#forumId#";
	public static final String LIKE_URL = "https://www.mybmwclub.cn/mobcent/app/web/?r=forum/newtopicsupport&tid=#forumId#&inajax=1";
	
	public static final String COLL_URL = "https://www.mybmwclub.cn/mobcent/app/web/?r=forum/newtopicfavorite&tid=#forumId#&inajax=1";

	public static final String FORUM_PAGE_FAVORITE_DOMID = "k_favorite";

//	public static final String DRIVERPATH = PropertiesUtil.getStringByKey("webdriver.chrome.driver");  
//	
//	public static final String LOGIN_URL = PropertiesUtil.getStringByKey("page.login.url");
//	public static final String LOGIN_PAGE_LOGINID_DOMID = PropertiesUtil.getStringByKey("page.login.input.login.id");
//	public static final String LOGIN_PAGE_LOGINPASS_DOMID = PropertiesUtil.getStringByKey("page.login.input.login.password");
//	
//	public static final String FORUM_URL = PropertiesUtil.getStringByKey("page.forum.url");
//	
//	public static final String FORUM_PAGE_FAVORITE_DOMID = PropertiesUtil.getStringByKey("page.forum.favorite.id");
	
}
