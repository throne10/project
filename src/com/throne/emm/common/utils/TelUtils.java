package com.throne.emm.common.utils;

public class TelUtils {
	public static String getNetworkType(int networkType){
		String networkTypeName = "";
		switch (networkType) {
		case 1:
			networkTypeName = "GPRS网络";
			break;
		case 2:
			networkTypeName = "EDGE网络";
			break;
		case 3:
			networkTypeName = "UMTS网络";
			break;
		case 4:
			networkTypeName = "CDMA网络";
			break;
		case 5:
			networkTypeName = "EVDO网络";
			break;
		case 6:
			networkTypeName = "EVDO网络";
			break;
		case 7:
			networkTypeName = "1xRTT网络";
			break;
		case 8:
			networkTypeName = "HSDPA网络";
			break;
		case 9:
			networkTypeName = "HSUPA网络";
			break;
		case 10:
			networkTypeName = "HSPA网络";
		default:
			networkTypeName = "网络类型未知";
			break;
		}
		return networkTypeName;
	}
}
