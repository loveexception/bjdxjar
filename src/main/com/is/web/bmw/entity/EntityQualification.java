package com.is.web.bmw.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class EntityQualification {

	public EntityQualification(){}
 	/**
	 * 排序检索字段 <i>LinkedHashMap<String, String></i><br>
	 * 用于数据检索时加入排序条件<br>
	 */
	protected LinkedHashMap<String, String> orderBys; 
	
 	/**
	 * 分组检索字段 <i>String</i><br>
	 * 用于数据检索时加入分组条件<br>
	 */
	protected String groupBys; 
	
 	/**
	 * 关键字检索 <i>String</i><br>
	 * 关键字检索<br>
	 */
	protected String searchKey; 
	
 	/**
	 * 关键字检索涉及到的列 <i>String</i><br>
	 * 关键字检索涉及到的列,","分隔<br>
	 */
	protected String searchCols; 
	
 	/**
	 * 需要查询的列 <i>String</i><br>
	 * 用于设定检索字段<br>
	 */
	protected List<String> selectCols = new ArrayList<String>(); 
	/**
	 * 检索时，设定值为null的列 <i>String</i><br>
	 * 用于设定值为null的检索字段<br>
	 */
	protected List<String> isNullCols;
	
 	/**
	 * 需要计算更新的字段及类型 <i>Map<String, String></i><br>
	 * 在sql运算中进行更新，计算方式：+,-,*,/<br>
	 */
	protected Map<String, String> updateInSqlColType; 
	
	/**
	 * 比较大小的扩展列 <i>Map<String, Object></i><br>
	 * 数字、日期比较大小的列及关系<br>
	 */
	protected Map<String, Object> compareCols;

	/**
	 * 获取排序检索字段
	 * @return LinkedHashMap<String, String>
	 */
	public LinkedHashMap<String, String> getOrderBys() {
		return orderBys;
	}
	/**
	 * 设定排序检索字段
	 * @param orderBys<br/>
	 */
	public void setOrderBys(LinkedHashMap<String, String> orderBys) {
		this.orderBys = orderBys;
	}
	/**
	 * 获取分组检索字段
	 * @return String
	 */
	public String getGroupBys() {
		return groupBys;
	}
	/**
	 * 设定分组检索字段
	 * @param groupBys<br/>
	 */
	public void setGroupBys(String groupBys) {
		this.groupBys = groupBys;
	}
	/**
	 * 获取关键字检索
	 * @return String
	 */
	public String getSearchKey() {
		return searchKey;
	}
	/**
	 * 设定关键字检索
	 * @param searchKey<br/>
	 */
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	/**
	 * 获取关键字检索涉及到的列
	 * @return String
	 */
	public String getSearchCols() {
		return searchCols;
	}
	/**
	 * 设定关键字检索涉及到的列
	 * @param searchCols<br/>
	 */
	public void setSearchCols(String searchCols) {
		this.searchCols = searchCols;
	}
	/**
	 * 获取需要查询的列
	 * @return List<String>
	 */
	public List<String> getSelectCols() {
		return selectCols;
	}
	/**
	 * 设定需要查询的列
	 * @param selectCols<br/>
	 */
	public void setSelectCols(List<String> selectCols) {
		if(null==this.selectCols){
			this.selectCols = new ArrayList<String>();
		}
		for(String sCol:selectCols){
			if(StringUtils.isNotBlank(sCol)){
				this.selectCols.add(sCol);
			}
		}
	}
	/**
	 * 检索时，设定值为null的列 <i>String</i><br>
	 * @return List<String>
	 */
	public List<String> getIsNullCols() {
		return isNullCols;
	}
	/**
	 * 检索时，设定值为null的列 <i>String</i><br>
	 * @param isNullCols
	 */
	public void setIsNullCols(List<String> isNullCols) {
		this.isNullCols = isNullCols;
	}
	/**
	 * 获取需要计算更新的字段及类型
	 * @return Map<String, String>
	 */
	public Map<String, String> getUpdateInSqlColType() {
		if(null==updateInSqlColType){
			updateInSqlColType = new HashMap<String,String>();
		}
		return updateInSqlColType;
	}
	/**
	 * 设定需要计算更新的字段及类型
	 * @param updateInSqlColType<br/>
	 */
	public void setUpdateInSqlColType(Map<String, String> updateInSqlColType) {
		this.updateInSqlColType = updateInSqlColType;
	}
	/**
	 * 获取比较大小的扩展列
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getCompareCols() {
		if(null==compareCols){
			compareCols = new HashMap<String,Object>();
		}
		return compareCols;
	}
	/**
	 * 设定比较大小的扩展列
	 * @param compareCols<br/>
	 */
	public void setCompareCols(Map<String, Object> compareCols) {
		this.compareCols = compareCols;
	} 
	
	
}
