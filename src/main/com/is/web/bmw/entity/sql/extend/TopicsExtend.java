package com.is.web.bmw.entity.sql.extend;

import com.is.web.bmw.entity.sql.entity.Topics;

/**
   数据对象：t_topics扩展数据
*/
public class TopicsExtend extends Topics {

	private static final long serialVersionUID = 1L;
	
	public TopicsExtend(){}
	
	public TopicsExtend(Integer id){
		this.id = id;
	}
	
	public TopicsExtend(Topics topics){
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
	
	public TopicsExtend(TopicsExtend topicsExtend){
		if(null==topicsExtend){
			return;
		}
		this.setId(topicsExtend.getId()); 
		this.setName(topicsExtend.getName()); 
		this.setReaded(topicsExtend.getReaded()); 
		this.setReadedplan(topicsExtend.getReadedplan()); 
		this.setLiked(topicsExtend.getLiked()); 
		this.setLikedplan(topicsExtend.getLikedplan()); 
		this.setCollected(topicsExtend.getCollected()); 
		this.setCollectedplan(topicsExtend.getCollectedplan()); 
		this.setOldid(topicsExtend.getOldid()); 
		this.setContent(topicsExtend.getContent()); 
		this.setIdMults(topicsExtend.getIdMults()); 
	}
 	/**
	 * id多个值检索条件 <i>int(32)</i><br>
	 * id多个值检索条件<br>
	 */
	protected Integer[] idMults; 
	
	/**
	 * 获取id多个值检索条件
	 * @return Integer[]
	 */
	public Integer[] getIdMults() {
		return idMults;
	}
	/**
	 * 设定id多个值检索条件
	 * @param idMults<br/>
	 */
	public void setIdMults(Integer[] idMults) {
		this.idMults = idMults;
	}
}
