package com.is.web.bmw.entity.sql.vojo;



import com.is.web.bmw.entity.sql.entity.Subtopics;
import com.is.web.bmw.entity.sql.extend.SubtopicsExtend;


public class SubtopicsVojo extends SubtopicsExtend {
	private static final long serialVersionUID = 1L;
	
	public SubtopicsVojo(){}

	public SubtopicsVojo(Subtopics subtopics){
		if(null==subtopics){
			return;
		}
		this.setId(subtopics.getId()); 
		this.setTid(subtopics.getTid()); 
		this.setContext(subtopics.getContext()); 
		this.setPid(subtopics.getPid()); 
		this.setSend(subtopics.getSend()); 
		this.setPlan(subtopics.getPlan()); 
		this.setStatus(subtopics.getStatus()); 
	}
	
	public SubtopicsVojo(SubtopicsExtend subtopicsExtend){
		if(null==subtopicsExtend){
			return;
		}
		this.setId(subtopicsExtend.getId()); 
		this.setTid(subtopicsExtend.getTid()); 
		this.setContext(subtopicsExtend.getContext()); 
		this.setPid(subtopicsExtend.getPid()); 
		this.setSend(subtopicsExtend.getSend()); 
		this.setPlan(subtopicsExtend.getPlan()); 
		this.setStatus(subtopicsExtend.getStatus()); 
		this.setPidName(subtopicsExtend.getPidName()); 
		this.setPidPassword(subtopicsExtend.getPidPassword()); 
		this.setStatusChnval(subtopicsExtend.getStatusChnval()); 
		this.setIdMults(subtopicsExtend.getIdMults()); 
	}
}
