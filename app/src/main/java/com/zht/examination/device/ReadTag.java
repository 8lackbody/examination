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

	public String getEpcId() {
		return epcId;
	}

	public void setEpcId(String epcId) {
		this.epcId = epcId;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public int getAntId() {
		return antId;
	}

	public void setAntId(int antId) {
		this.antId = antId;
	}
}
