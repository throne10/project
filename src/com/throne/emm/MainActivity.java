package com.throne.emm;


import com.throne.emm.activity.InitActivity;
import com.throne.emm.application.MyApplication;
import com.throne.emm.constant.CommonConstants;
import com.throne.emm.eventbus.event.CheckDeviceEvent;
import com.throne.emm.eventbus.event.CheckVersionEvent;
import com.throne.emm.eventbus.event.RegsiterDeviceEvent;
import com.throne.emm.net.result.CheckDeviceResult;
import com.throne.emm.net.result.CheckVersionResult;
import com.throne.emm.net.result.RegsiterDeviceResult;
import com.throne.emm.net.utils.NetRequestUtil;
import com.throne.emm.view.MyAlertDialog;
import com.throne.emm.view.MyProcessAlertDialog;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	private MyProcessAlertDialog mProcesscheckDevice;
	private Context mContext;
	private MyAlertDialog checkerror;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		EventBus.getDefault().register(this); 
		mContext=this;
		initProcesscheckDevice("检测应用安装版本......");
		
		CheckVersion();

	}

	private void CheckVersion() {
		PackageManager pm = mContext.getPackageManager();  
		PackageInfo pi;
		int versioncode = 0;
		try {
			pi = pm.getPackageInfo(mContext.getPackageName(), 0);
			versioncode = pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}  
		CheckVersionResult mCheckVersionResult=new CheckVersionResult();
		NetRequestUtil mNetRequestUtil=new NetRequestUtil(mCheckVersionResult);
		mNetRequestUtil.doGet(CommonConstants.CheckVersion(versioncode),null);  
	}

	private void initProcesscheckDevice(String info) {
		mProcesscheckDevice=new MyProcessAlertDialog(this);
		mProcesscheckDevice.setCancelable(false);
		mProcesscheckDevice.setTitle(info);		
	}

	private void CheckDevice() {
		CheckDeviceResult mCheckDeviceResult=new CheckDeviceResult();
		NetRequestUtil mNetRequestUtil=new NetRequestUtil(mCheckDeviceResult);
		mNetRequestUtil.doGet(CommonConstants.getDeviceInfo(MyApplication.UUID),null);  		
	}

	private void RegisterDevice() {
		RegsiterDeviceResult mRegsiterDeviceResult=new RegsiterDeviceResult();
		NetRequestUtil mNetRequestUtil=new NetRequestUtil(mRegsiterDeviceResult);
		String MODEL=Build.MODEL;
		TelephonyManager tm = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
		String number = tm.getLine1Number();
		mNetRequestUtil.doGet(CommonConstants.RegsiterDevice(MODEL,number,"1",MyApplication.UUID),null);  		
	}
	
	/**
	 * 
	 * @param mRegsiterDeviceEvent
	 */
	public void onEventMainThread(RegsiterDeviceEvent mRegsiterDeviceEvent)  
	{  
		mProcesscheckDevice.dismiss();
		int result = mRegsiterDeviceEvent.getResult();
		switch (result) {
		case CheckDeviceEvent.Success:
			startActivity(new Intent(mContext,InitActivity.class));
			finish();
			break;
		case CheckDeviceEvent.Fail:
			checkerror=new MyAlertDialog(mContext);
			checkerror.setTitle("设备激活失败");
			checkerror.setCancelable(false);
			checkerror.setPositiveButton("重试", new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					checkerror.dismiss();
					initProcesscheckDevice("正在激活设备......");
					RegisterDevice();
				}
			});
			break;
		}
	}
	public void onEventMainThread(CheckVersionEvent mCheckVersionEvent)  
	{
		mProcesscheckDevice.dismiss();
		int result = mCheckVersionEvent.getResult();
		switch (result) {
		case CheckVersionEvent.NO_UPDATE:
			initProcesscheckDevice("检测设备激活状态......");
			CheckDevice();
			break;
		case CheckVersionEvent.Fail:
			checkerror=new MyAlertDialog(mContext);
			checkerror.setTitle("获取应用版本信息失败");
			checkerror.setCancelable(false);
			checkerror.setPositiveButton("重试", new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					checkerror.dismiss();
					initProcesscheckDevice("检测设备激活状态......");
					CheckDevice();
				}
			});
			break;
		case CheckVersionEvent.UPDATE:
			
			break;
			}

	}
	public void onEventMainThread(CheckDeviceEvent mCheckDeviceEvent)  
	{  
		mProcesscheckDevice.dismiss();
		int result = mCheckDeviceEvent.getResult();
		switch (result) {
		case CheckDeviceEvent.Success:
			//进入应用
			startActivity(new Intent(mContext,InitActivity.class));
			finish();
			break;
		case CheckDeviceEvent.Fail:
			checkerror=new MyAlertDialog(mContext);
			checkerror.setTitle("获取设备信息失败");
			checkerror.setCancelable(false);
			checkerror.setPositiveButton("重试", new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					checkerror.dismiss();
					initProcesscheckDevice("检测设备激活状态......");
					CheckDevice();
				}
			});
			break;
		case CheckDeviceEvent.NO_REGISTER:
			initProcesscheckDevice("正在激活设备......");
			RegisterDevice();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this); 
	}
}
