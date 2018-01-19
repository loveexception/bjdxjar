package com.is.web.bmw.entity.sql.extend;

import com.is.web.bmw.entity.sql.entity.Statist;

/**
   数据对象：t_statist扩展数据
*/
public class StatistExtend extends Statist {

	private static final long serialVersionUID = 1L;
	
	public StatistExtend(){}
	
	public StatistExtend(Integer id){
		this.id = id;
	}
	
	public StatistExtend(Statist statist){
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
	
	public StatistExtend(StatistExtend statistExtend){
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
