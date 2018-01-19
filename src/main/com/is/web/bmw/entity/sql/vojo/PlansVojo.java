package com.is.web.bmw.entity.sql.vojo;



import com.is.web.bmw.entity.sql.entity.Plans;
import com.is.web.bmw.entity.sql.extend.PlansExtend;


public class PlansVojo extends PlansExtend {
	private static final long serialVersionUID = 1L;
	
	public PlansVojo(){}

	public PlansVojo(Plans plans){
		if(null==plans){
			return;
		}
		this.setId(plans.getId()); 
		this.setName(plans.getName()); 
		this.setCount(plans.getCount()); 
	}
	
	public PlansVojo(PlansExtend plansExtend){
		if(null==plansExtend){
			return;
		}
		this.setId(plansExtend.getId()); 
		this.setName(plansExtend.getName()); 
		this.setCount(plansExtend.getCount()); 
		this.setIdMults(plansExtend.getIdMults()); 
	}
}
