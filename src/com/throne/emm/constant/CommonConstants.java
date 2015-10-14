package com.throne.emm.constant;

import android.os.Environment;

public class CommonConstants {
	public static String SERVER_IP = "http://115.29.211.46:8080/mdm119/";
	public static final int ALARM_REQUEST_CODE = 11;
	public static final String PACKAG_NAME = "com.throne.emm";
	public static final String ANDORID_NAME = "android";
	public static final String INTENT_PACKAGE_NAME = "packagename";
	public static final String INTENT_TYPE = "type";
	public static final int SETTING_LAUNCHER = 0;
	public static final int CLEAN_LAUNCHER = 1;
	public static String MDMPATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/mdm/";

	public static String getApp() {
		String address = SERVER_IP + "action/app?act=getAppList";
		return address;
	}

	public static String getFullUrl(String url) {
		String address = SERVER_IP + url;
		return address;
	}

	public static String getAppPath(String PackageName) {
		return CommonConstants.MDMPATH + PackageName + ".apk";
	}

	public static String getAppTmpPath(String PackageName) {
		return CommonConstants.MDMPATH + PackageName + ".tmp";
	}

	public static String getDeviceInfo(String uuid) {
		String address = SERVER_IP + "action/device?act=checkDevice&uuid="
				+ uuid;
		return address;
	}

	/**
	 * 
	 * @param deviceName
	 * @param deviceOs
	 * @param deviceNo
	 * @param deviceStatus
	 * @param uuid
	 * @return
	 */
	public static String RegsiterDevice(String deviceName, String deviceNo,
			String deviceStatus, String uuid) {
		String address = SERVER_IP + "action/device?act=addDevice&uuid= "
				+ uuid + "&deviceName=" + deviceName
				+ "&deviceOs=Android&deviceNo=" + deviceNo + "&deviceStatus="
				+ deviceStatus;
		return address;
	}
	public static String CheckVersion(int version) {
		String address = SERVER_IP + "action/versions?act=checkVersion&version="
				+ version;
		return address;
	}
	public static String getUpload() {
		String address = SERVER_IP + "action/device";
		return address;
	}
	
	public static String getPolicy() {
		String address = SERVER_IP + "action/strategy?act=getStrategy";
		return address;
	}
}
