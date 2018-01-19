package com.is.web.bmw.entity.sql.vojo;



import com.is.web.bmw.entity.sql.entity.Statist;
import com.is.web.bmw.entity.sql.extend.StatistExtend;


public class StatistVojo extends StatistExtend {
	private static final long serialVersionUID = 1L;
	
	public StatistVojo(){}

	public StatistVojo(Statist statist){
		if(null==statist){
			return;
		}
//		this.setId(plans.getId()); 
//		this.setName(plans.getName()); 
//		this.setCount(plans.getCount()); 
		this.setId(statist.getId());
		this.setYmd(statist.getYmd());
		this.setPageview(statist.getPageview());
		this.setUserview(statist.getUserview());
	}
	
	public StatistVojo(StatistExtend statistExtend){
		if(null==statistExtend){
			return;
		}
//		this.setId(plansExtend.getId()); 
//		this.setName(plansExtend.getName()); 
//		this.setCount(plansExtend.getCount()); 
//		this.setIdMults(plansExtend.getIdMults()); 
		this.setId(statistExtend.getId());
		this.setYmd(statistExtend.getYmd());
		this.setPageview(statistExtend.getPageview());
		this.setUserview(statistExtend.getUserview());
		this.setIdMults(statistExtend.getIdMults());
	}
}
