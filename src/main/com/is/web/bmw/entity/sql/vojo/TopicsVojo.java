package com.is.web.bmw.entity.sql.vojo;



import com.is.web.bmw.entity.sql.entity.Topics;
import com.is.web.bmw.entity.sql.extend.TopicsExtend;


public class TopicsVojo extends TopicsExtend {
	private static final long serialVersionUID = 1L;
	
	public TopicsVojo(){}

	public TopicsVojo(Topics topics){
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
	
	public TopicsVojo(TopicsExtend topicsExtend){
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
}
