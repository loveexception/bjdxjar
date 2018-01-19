package com.is.web.bmw.entity.sql.extend;

import com.is.web.bmw.entity.sql.entity.Persons;

/**
   数据对象：t_persons扩展数据
*/
public class PersonsExtend extends Persons {

	private static final long serialVersionUID = 1L;
	
	public PersonsExtend(){}
	
	public PersonsExtend(Integer id){
		this.id = id;
	}
	
	public PersonsExtend(Persons persons){
		if(null==persons){
			return;
		}
		this.setId(persons.getId()); 
		this.setName(persons.getName()); 
		this.setPassword(persons.getPassword()); 
		this.setOldid(persons.getOldid()); 
	}
	
	public PersonsExtend(PersonsExtend personsExtend){
		if(null==personsExtend){
			return;
		}
		this.setId(personsExtend.getId()); 
		this.setName(personsExtend.getName()); 
		this.setPassword(personsExtend.getPassword()); 
		this.setOldid(personsExtend.getOldid()); 
		this.setIdMults(personsExtend.getIdMults()); 
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
