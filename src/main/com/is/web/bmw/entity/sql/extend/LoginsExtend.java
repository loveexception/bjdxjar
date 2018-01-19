package com.is.web.bmw.entity.sql.extend;

import com.is.web.bmw.entity.sql.entity.Logins;

import com.is.web.bmw.entity.sql.entityenum.LoginsStauts;
/**
   数据对象：t_logins扩展数据
*/
public class LoginsExtend extends Logins {

	private static final long serialVersionUID = 1L;
	
	public LoginsExtend(){}
	
	public LoginsExtend(Integer id){
		this.id = id;
	}
	
	public LoginsExtend(Logins logins){
		if(null==logins){
			return;
		}
		this.setId(logins.getId()); 
		this.setPid(logins.getPid()); 
		this.setIp(logins.getIp()); 
		this.setPort(logins.getPort()); 
		this.setStauts(logins.getStauts()); 
		this.setPlan(logins.getPlan()); 
		this.setSend(logins.getSend()); 
		this.setUid(logins.getUid()); 
		this.setUpdatetime(logins.getUpdatetime()); 
	}
	
	public LoginsExtend(LoginsExtend loginsExtend){
		if(null==loginsExtend){
			return;
		}
		this.setId(loginsExtend.getId()); 
		this.setPid(loginsExtend.getPid()); 
		this.setIp(loginsExtend.getIp()); 
		this.setPort(loginsExtend.getPort()); 
		this.setStauts(loginsExtend.getStauts()); 
		this.setPlan(loginsExtend.getPlan()); 
		this.setSend(loginsExtend.getSend()); 
		this.setUid(loginsExtend.getUid()); 
		this.setUpdatetime(loginsExtend.getUpdatetime()); 
		this.setPidName(loginsExtend.getPidName()); 
		this.setPidPassword(loginsExtend.getPidPassword()); 
		this.setStautsChnval(loginsExtend.getStautsChnval()); 
		this.setIdMults(loginsExtend.getIdMults()); 
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
	 * stauts_字典值 <i>VARCHAR</i><br>
	 * stauts_字典值<br>
	 */
	protected String stautsChnval; 
	
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
	 * 获取stauts_字典值
	 * @return String
	 */
	public String getStautsChnval() {
		if(null!=this.stauts){
			stautsChnval = LoginsStauts.getEnumShowByEnumVal(this.stauts);
		}
		return stautsChnval;
	}
	/**
	 * 设定stauts_字典值
	 * @param stautsChnval<br/>
	 */
	public void setStautsChnval(String stautsChnval) {
		this.stautsChnval = stautsChnval;
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
