package com.is.web.bmw.entity.sql.entity;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;


import com.is.web.bmw.entity.EntityQualification;

/**
   数据对象：t_topics
*/
public class Topics extends EntityQualification implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@SuppressWarnings("serial")
	public static final Map<String,String> columMap = new LinkedHashMap<String,String>(){{ 
		put("id","ID");put("name","NAME");put("readed","READED");put("readedplan","READEDPLAN");put("liked","LIKED");put("likedplan","LIKEDPLAN");put("collected","COLLECTED");put("collectedplan","COLLECTEDPLAN");put("oldid","OLDID");put("content","CONTENT");
	}};
	
	@SuppressWarnings("unused")
	private static final String NAME_OF_TABLE_IN_DATABASE = "t_topics";
	@SuppressWarnings("unused")
	private static final String CODE_OF_TABLE_IN_DATABASE = "T_TOPICS";
	
	/**
	 * 初始化空<i>t_topics</i><br>
	 * @return Topics
	 */
	public Topics(){}
	
	/**
	 * 初始化设定好主键值的<i>t_topics</i><br>
	 * @return Topics
	 */
	public Topics(Integer id){
		this.id = id;
	}
	
	public Topics(Topics topics){
		if(null==topics){
			return;
		}
		this.setId(topics.getId()); 
		this.setName(topics.getName()); 
		this.setReaded(topics.getReaded()); 
		this.setReadedplan(topics.getReadedplan()); 
		this.setLiked(topics.getLiked()); 
		this.setLikedplan(topics.getLikedplan()); 
		this.setCollected(topics.getCollected()); 
		this.setCollectedplan(topics.getCollectedplan()); 
		this.setOldid(topics.getOldid()); 
		this.setContent(topics.getContent()); 
	}
	
	public Map<String,Object> checkLength(Topics topics) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("result", true);
		if(StringUtils.isNotBlank(topics.getName()) && StringUtils.length(topics.getName())> 128){
			resultMap.put("result", false);
			resultMap.put("msg", "name[name]"+"：值太长。");
		}
		if(StringUtils.isNotBlank(topics.getOldid()) && StringUtils.length(topics.getOldid())> 128){
			resultMap.put("result", false);
			resultMap.put("msg", "oldid[oldid]"+"：值太长。");
		}
		return resultMap;
	}


 	/**
	 * <b>id</b> <i>int(32)</i> <b>不能为空，默认值为：0</b><br>
	 * id<br>
	 */
	protected Integer id; 
	
 	/**
	 * <b>name</b> <i>varchar(128)</i><br>
	 * name<br>
	 */
	protected String name; 
	
 	/**
	 * <b>readed</b> <i>int(32)</i><br>
	 * 初始阅读次数<br>
	 */
	protected Integer readed; 
	
 	/**
	 * <b>readedplan</b> <i>int(32)</i><br>
	 * 计划达到阅读次数<br>
	 */
	protected Integer readedplan; 
	
 	/**
	 * <b>liked</b> <i>int(32)</i><br>
	 * liked<br>
	 */
	protected Integer liked; 
	
 	/**
	 * <b>likedplan</b> <i>int(32)</i><br>
	 * likedplan<br>
	 */
	protected Integer likedplan; 
	
 	/**
	 * <b>collected</b> <i>int(32)</i><br>
	 * 初始收藏数<br>
	 */
	protected Integer collected; 
	
 	/**
	 * <b>collectedplan</b> <i>int(32)</i><br>
	 * 计划达到收藏数<br>
	 */
	protected Integer collectedplan; 
	
 	/**
	 * <b>oldid</b> <i>varchar(128)</i><br>
	 * ???  id<br>
	 */
	protected String oldid; 
	
 	/**
	 * <b>content</b> <i>text</i><br>
	 * content<br>
	 */
	protected String content; 
	
	
	/**
	 * <b>获取id</b>
	 * @return Integer
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * <b>设定id</b>
	 * @param id<br/>
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * <b>获取name</b>
	 * @return String
	 */
	public String getName() {
		return name;
	}
	/**
	 * <b>设定name</b>
	 * @param name<br/>
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * <b>获取readed</b>
	 * @return Integer
	 */
	public Integer getReaded() {
		return readed;
	}
	/**
	 * <b>设定readed</b>
	 * @param readed<br/>
	 */
	public void setReaded(Integer readed) {
		this.readed = readed;
	}
	/**
	 * <b>获取readedplan</b>
	 * @return Integer
	 */
	public Integer getReadedplan() {
		return readedplan;
	}
	/**
	 * <b>设定readedplan</b>
	 * @param readedplan<br/>
	 */
	public void setReadedplan(Integer readedplan) {
		this.readedplan = readedplan;
	}
	/**
	 * <b>获取liked</b>
	 * @return Integer
	 */
	public Integer getLiked() {
		return liked;
	}
	/**
	 * <b>设定liked</b>
	 * @param liked<br/>
	 */
	public void setLiked(Integer liked) {
		this.liked = liked;
	}
	/**
	 * <b>获取likedplan</b>
	 * @return Integer
	 */
	public Integer getLikedplan() {
		return likedplan;
	}
	/**
	 * <b>设定likedplan</b>
	 * @param likedplan<br/>
	 */
	public void setLikedplan(Integer likedplan) {
		this.likedplan = likedplan;
	}
	/**
	 * <b>获取collected</b>
	 * @return Integer
	 */
	public Integer getCollected() {
		return collected;
	}
	/**
	 * <b>设定collected</b>
	 * @param collected<br/>
	 */
	public void setCollected(Integer collected) {
		this.collected = collected;
	}
	/**
	 * <b>获取collectedplan</b>
	 * @return Integer
	 */
	public Integer getCollectedplan() {
		return collectedplan;
	}
	/**
	 * <b>设定collectedplan</b>
	 * @param collectedplan<br/>
	 */
	public void setCollectedplan(Integer collectedplan) {
		this.collectedplan = collectedplan;
	}
	/**
	 * <b>获取oldid</b>
	 * @return String
	 */
	public String getOldid() {
		return oldid;
	}
	/**
	 * <b>设定oldid</b>
	 * @param oldid<br/>
	 */
	public void setOldid(String oldid) {
		this.oldid = oldid;
	}
	/**
	 * <b>获取content</b>
	 * @return String
	 */
	public String getContent() {
		return content;
	}
	/**
	 * <b>设定content</b>
	 * @param content<br/>
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
