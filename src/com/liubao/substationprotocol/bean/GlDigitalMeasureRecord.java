package com.liubao.substationprotocol.bean;

import java.io.Serializable;
import java.sql.*;

class GlDigitalMeasureRecord implements Serializable{
	
	/*   主键        */
	String device_id;
	String sensor_id;
	String plc_id;
	String boilerRoom;
	
	/*   非主键       */
	String boiler;
	String field;
	String description;
	String measure_type;
	double value;
	int    whenout;
	int    isBeyond;
	int    close;
	int    open;
	int    state;
	
	
	/*   非基本类型       */
	Timestamp timeStamp = null;
	Date      date = null;
	Time      time = null;
	
	
	/*    获得各字段的值    */
	String getDevice_id(String s){
		device_id = s;
		return device_id;
	}
	String getSensor_id(String s){
		sensor_id = s;
		return sensor_id;
	}
	
	String getPlc_id(String s){
		plc_id = s;
		return plc_id;
	}
	
	String getBoilerRoom(String s){
		boilerRoom = s;
		return boilerRoom;
	}
	
	String getBoiler(String s){
		boiler = s;
		return boiler;
	}
	
	String getField(String s){
		field = s;
		return field;
	}
	
	String getDescription(String s){
		description = s;
		return description;
	}
	
	String getMeasure_type(String s){
		measure_type = s;
		return measure_type;
	}
	
	double getValue(double f){
		value = f;
		return value;
	}
	
	int getWhenout(int n){
		whenout = n;
		return whenout;
	}
	
	int getIsBeyond(int n){
		isBeyond = n;
		return isBeyond;
	}
	
	int getClose(int n){
		close = n;
		return close;
	}
	
	int getOpen(int n){
		open = n;
		return open;
	}
	
	int getState(int n){
		state = n;
		return state;
	}
	
	Timestamp getTimestamp(Timestamp ts){
		timeStamp = ts;
		return timeStamp;
	}
	
	Date getDate(Date d){
		date = d;
		return date;
	}
	
	Time getTime(Time t){
		time = t;
		return time;
	}
}
