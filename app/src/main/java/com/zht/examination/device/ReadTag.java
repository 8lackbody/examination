package com.zht.examination.device;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

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

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ReadTag readTag = (ReadTag) o;
		return Objects.equals(epcId, readTag.epcId);
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public int hashCode() {
		return Objects.hash(epcId);
	}
}
