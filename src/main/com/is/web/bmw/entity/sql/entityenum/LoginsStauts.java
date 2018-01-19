package com.is.web.bmw.entity.sql.entityenum;

import org.apache.commons.lang3.StringUtils;

/**
 * 枚举 ：t_logins stauts<br/>
 * 值：1:未执行,9:已发布,3:服务失败
 * @author hanbing
 *
 */
public enum LoginsStauts{
	
    /**
	 * 值：1 <br/>
	 * 显示：未执行
	 */
	EV_1("1","未执行"),
    /**
	 * 值：9 <br/>
	 * 显示：已发布
	 */
	EV_9("9","已发布"),
    /**
	 * 值：3 <br/>
	 * 显示：服务失败
	 */
	EV_3("3","服务失败");
	
	/**
	 * 数值
	 */
	public String enumVal;
	/**
	 * 显示内容
	 */
	public String enumShow;
	/**
	 * 获取数值
	 * @return
	 */
	public String getEnumVal() {
		return enumVal;
	}
	/**
	 * 设定数值
	 * @param eVal
	 */
	public void setEnumVal(String enumVal) {
		this.enumVal = enumVal;
	}
	/**
	 * 获取显示内容
	 * @return
	 */
	public String getEnumShow() {
		return enumShow;
	}
	/**
	 * 设定显示内容
	 * @param eShow
	 */
	public void setEnumShow(String enumShow) {
		this.enumShow = enumShow;
	}
	
	private LoginsStauts(String eVal,String eShow){
		this.enumVal = eVal;
		this.enumShow = eShow;
	}
	
	/**
	 * 判断值，是否在枚举范围值内
	 * @param val	需要判断的值
	 * @return
	 */
	public static boolean checkIsEnumValue(String val){
		for(LoginsStauts loginsStauts:LoginsStauts.values()){
			if(StringUtils.equals(loginsStauts.getEnumVal(), val)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据传入的值，返回对应的显示文本
	 * @param val	传入的值
	 * @return
	 */
	public static String getEnumShowByEnumVal(String val){
		for(LoginsStauts loginsStauts:LoginsStauts.values()){
			if(StringUtils.equals(loginsStauts.getEnumVal(), val)){
				return loginsStauts.getEnumShow();
			}
		}
		return null;
	}
}
