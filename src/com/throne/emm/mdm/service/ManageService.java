package com.throne.emm.mdm.service;

import org.json.JSONObject;

import com.throne.emm.activity.AppLockActivity;
import com.throne.emm.activity.EmmSettingActivity;
import com.throne.emm.application.MyApplication;
import com.throne.emm.common.utils.AppManageHelper;
import com.throne.emm.common.utils.LogUtil;
import com.throne.emm.common.utils.MdmUtils;
import com.throne.emm.constant.CommonConstants;
import com.throne.emm.constant.ConstantTime;
import com.throne.emm.eventbus.event.AppWhiteEvent;
import com.throne.emm.eventbus.event.MyAppAddEvent;
import com.throne.emm.eventbus.event.PolicyGetEvent;
import com.throne.emm.eventbus.event.PostInfoEvent;
import com.throne.emm.mdm.manage.AppManage;
import com.throne.emm.mdm.thread.AppForceInstallThread;
import com.throne.emm.mdm.thread.AppManageThread;
import com.throne.emm.mdm.thread.DeviceInfoUploadThread;
import com.throne.emm.mdm.thread.PolicyGetThread;
import com.throne.emm.mdm.thread.WifiManageThread;
import com.throne.emm.model.PolicyInfo;
import com.throne.emm.net.result.PostInfoResult;

import de.greenrobot.event.EventBus;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

public class ManageService extends Service {
	private Context mContext;
	private ActivityManager mActivityManager;
	private AppManage mAppManage;
	private WifiManager mWifiManager;
	private ConnectivityManager mConnectivityManager;
	private long UploadInfoTime = 0L;
	private long PolicyTime = 0L;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		EventBus.getDefault().register(this);
		mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		/**
		 * 启动此管理的定时任务，每隔一分半钟执行
		 */
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(mContext, ManageService.class);
		PendingIntent tpr = PendingIntent.getService(mContext,
				CommonConstants.ALARM_REQUEST_CODE, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		am.setRepeating(AlarmManager.RTC_WAKEUP,
				(System.currentTimeMillis() + (1000 * 10)),
				ConstantTime.ALARM_MANAGE_INTERVAL, tpr);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 设置默认桌面
		manageMyLauncher();

		if (MdmUtils.isAdminActive(mContext)) {
			long now = System.currentTimeMillis();
			
			new Thread(new AppForceInstallThread(mContext)).start();
			// 应用锁定
			new Thread(new AppManageThread(mActivityManager, mContext)).start();

			new Thread(new WifiManageThread(mWifiManager, mConnectivityManager,
					mContext)).start();
			if (now - UploadInfoTime > ConstantTime.UPLOADINFO_TRIGGERTIME) {
				UploadInfoTime = now;
				new Thread(new DeviceInfoUploadThread(mContext)).start();
			}

			if (now - PolicyTime > ConstantTime.POLICYTIME_TRIGGERTIME) {
				PolicyTime = now;
				new Thread(new PolicyGetThread(mContext)).start();
			}

		}

		return super.onStartCommand(intent, flags, startId);
	}

	private void manageMyLauncher() {
		String mlauncherPackageName = AppManageHelper
				.getLauncherPackageName(this);
		if (!(mlauncherPackageName != null && mlauncherPackageName
				.equalsIgnoreCase(CommonConstants.PACKAG_NAME))) {
			if (mlauncherPackageName
					.equalsIgnoreCase(CommonConstants.ANDORID_NAME)) {
				Intent mIntent = new Intent(mContext, EmmSettingActivity.class);
				mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mIntent.putExtra(CommonConstants.INTENT_TYPE,
						CommonConstants.SETTING_LAUNCHER);
				mIntent.putExtra(CommonConstants.INTENT_PACKAGE_NAME,
						mlauncherPackageName);
				mContext.startActivity(mIntent);

			} else {
				Intent mIntent = new Intent(mContext, EmmSettingActivity.class);
				mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mIntent.putExtra(CommonConstants.INTENT_TYPE,
						CommonConstants.CLEAN_LAUNCHER);
				mIntent.putExtra(CommonConstants.INTENT_PACKAGE_NAME,
						mlauncherPackageName);
				mContext.startActivity(mIntent);
			}
		}
	}

	public void onEventBackgroundThread(AppWhiteEvent AppWhiteEvent) {
		mAppManage.updateAppWhiteList();
	}
	public void onEventBackgroundThread(PostInfoEvent PostInfoEvent) {
		MyApplication.mLogger.info("POST:"+PostInfoEvent.getResult());
	}
	public void onEventBackgroundThread(PolicyGetEvent PolicyGetEvent) {
		PolicyInfo mPostInfo=(PolicyInfo)PolicyGetEvent.getEvent();
		MyAppAddEvent mMyAppAddEvent=new MyAppAddEvent();
		mMyAppAddEvent.setEvent(mPostInfo.getWhite());
		EventBus.getDefault().post(mMyAppAddEvent);
	}
	// onEventBackgroundThread onEventAsync

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}
