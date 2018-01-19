package com.is.web.bmw.entity.sql.extend;

import com.is.web.bmw.entity.sql.entity.Plans;

/**
   数据对象：t_plans扩展数据
*/
public class PlansExtend extends Plans {

	private static final long serialVersionUID = 1L;
	
	public PlansExtend(){}
	
	public PlansExtend(Integer id){
		this.id = id;
	}
	
	public PlansExtend(Plans plans){
		if(null==plans){
			return;
		}
		this.setId(plans.getId()); 
		this.setName(plans.getName()); 
		this.setCount(plans.getCount()); 
	}
	
	public PlansExtend(PlansExtend plansExtend){
		if(null==plansExtend){
			return;
		}
		this.setId(plansExtend.getId()); 
		this.setName(plansExtend.getName()); 
		this.setCount(plansExtend.getCount()); 
		this.setIdMults(plansExtend.getIdMults()); 
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
