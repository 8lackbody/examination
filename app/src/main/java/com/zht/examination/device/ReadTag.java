package com.zht.examination.device;

public class ReadTag {
	public String epcId;
	public int rssi;
	public int antId;
	public ReadTag(){

	}
	public ReadTag(String epcId, int rssi, int antId){
		this.antId = antId;
		this.rssi = rssi;
		this.epcId = epcId;
	}
}
