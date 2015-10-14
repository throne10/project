package com.throne.emm.application;

import org.apache.log4j.Logger;
import com.nevin.dmeo.jnimac.jni.MacAddr;
import com.throne.emm.common.utils.LogUtil;
import com.throne.emm.common.utils.Md5;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
	private Context mContext;
	public static String UUID;
	public static Logger mLogger;
	public void onCreate() {
		super.onCreate();
		mContext=this;
		init();
		getUUID();
	}
	private void init() {
		LogUtil.LogCofig();
		mLogger=LogUtil.logInstance(this.toString());
	}

	private void getUUID() {
		String device_MAC;
		String sn = null;
		device_MAC = MacAddr.getMacAddr(mContext);
		sn = android.os.Build.SERIAL;
		UUID = Md5.md5(device_MAC + sn);
		mLogger.info(UUID);
	}
}