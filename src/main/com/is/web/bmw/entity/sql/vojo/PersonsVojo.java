package com.is.web.bmw.entity.sql.vojo;


import com.is.web.bmw.entity.sql.entity.Persons;
import com.is.web.bmw.entity.sql.extend.PersonsExtend;

public class PersonsVojo extends PersonsExtend {
	private static final long serialVersionUID = 1L;
	
	public PersonsVojo(){}

	public PersonsVojo(Persons persons){
		if(null==persons){
			return;
		}
		this.setId(persons.getId()); 
		this.setName(persons.getName()); 
		this.setPassword(persons.getPassword()); 
		this.setOldid(persons.getOldid()); 
	}
	
	public PersonsVojo(PersonsExtend personsExtend){
		if(null==personsExtend){
			return;
		}
		this.setId(personsExtend.getId()); 
		this.setName(personsExtend.getName()); 
		this.setPassword(personsExtend.getPassword()); 
		this.setOldid(personsExtend.getOldid()); 
		this.setIdMults(personsExtend.getIdMults()); 
	}
}
