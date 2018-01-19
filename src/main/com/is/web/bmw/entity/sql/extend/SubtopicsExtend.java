package com.is.web.bmw.entity.sql.extend;

import com.is.web.bmw.entity.sql.entity.Subtopics;

import com.is.web.bmw.entity.sql.entityenum.SubtopicsStatus;
/**
   数据对象：t_subtopics扩展数据
*/
public class SubtopicsExtend extends Subtopics {

	private static final long serialVersionUID = 1L;
	
	public SubtopicsExtend(){}
	
	public SubtopicsExtend(Integer id){
		this.id = id;
	}
	
	public SubtopicsExtend(Subtopics subtopics){
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
	
	public SubtopicsExtend(SubtopicsExtend subtopicsExtend){
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
 	/**
	 * name <i>varchar(128)</i><br>
	 * pid_扩展_name<br>
	 */
	protected String pidName; 
	
 	/**
	 * password <i>varchar(128)</i><br>
	 * pid_扩展_password<br>
	 */
	protected String pidPassword; 
	
 	/**
	 * status_字典值 <i>VARCHAR</i><br>
	 * status_字典值<br>
	 */
	protected String statusChnval; 
	
 	/**
	 * id多个值检索条件 <i>int(32)</i><br>
	 * id多个值检索条件<br>
	 */
	protected Integer[] idMults; 
	
	/**
	 * 获取name
	 * @return String
	 */
	public String getPidName() {
		return pidName;
	}
	/**
	 * 设定name
	 * @param pidName<br/>
	 */
	public void setPidName(String pidName) {
		this.pidName = pidName;
	}
	/**
	 * 获取password
	 * @return String
	 */
	public String getPidPassword() {
		return pidPassword;
	}
	/**
	 * 设定password
	 * @param pidPassword<br/>
	 */
	public void setPidPassword(String pidPassword) {
		this.pidPassword = pidPassword;
	}
	/**
	 * 获取status_字典值
	 * @return String
	 */
	public String getStatusChnval() {
		if(null!=this.status){
			statusChnval = SubtopicsStatus.getEnumShowByEnumVal(this.status);
		}
		return statusChnval;
	}
	/**
	 * 设定status_字典值
	 * @param statusChnval<br/>
	 */
	public void setStatusChnval(String statusChnval) {
		this.statusChnval = statusChnval;
	}
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
