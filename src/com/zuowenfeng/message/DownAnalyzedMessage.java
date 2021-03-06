package com.zuowenfeng.message;

public class DownAnalyzedMessage {
	private String device_id;
	private String plc_id;
	private String sensor_id;
	private String value;
	private String downtype;
	
	public DownAnalyzedMessage( String device_id, String plc_id, String sensor_id, float value, String downtype ) {
		this.device_id = "设备地址:" + device_id;
		this.plc_id = "从机地址:" + plc_id;
		this.sensor_id = "传感器地址:" + sensor_id;
		this.value = "值:" + value;
		this.downtype = "下发类型:" + downtype;
	}
	
	public String getDeviceid() {
		return this.device_id;
	}
	
	public String getPlcid() {
		return this.plc_id;
	}
	
	public String getSensorid() {
		return this.sensor_id;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getDowntype() {
		return this.downtype;
	}
}
