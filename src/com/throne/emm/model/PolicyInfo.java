package com.throne.emm.model;

import java.util.List;

public class PolicyInfo {
	private boolean bluetooth; 
	private boolean serverlist; 
	private String[] white;
	private String[] uninstall;
	public boolean isBluetooth() {
		return bluetooth;
	}
	public void setBluetooth(boolean bluetooth) {
		this.bluetooth = bluetooth;
	}
	public String[] getUninstall() {
		return uninstall;
	}
	public void setUninstall(String[] uninstall) {
		this.uninstall = uninstall;
	}
	public boolean isServerlist() {
		return serverlist;
	}
	public void setServerlist(boolean serverlist) {
		this.serverlist = serverlist;
	}
	public String[] getWhite() {
		return white;
	}
	public void setWhite(String[] white) {
		this.white = white;
	}
}
