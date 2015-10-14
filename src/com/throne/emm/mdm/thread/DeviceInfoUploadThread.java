package com.throne.emm.mdm.thread;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.loopj.android.http.RequestParams;
import com.throne.emm.activity.AppLockActivity;
import com.throne.emm.application.MyApplication;
import com.throne.emm.common.utils.AppManageHelper;
import com.throne.emm.common.utils.LogUtil;
import com.throne.emm.common.utils.TelUtils;
import com.throne.emm.constant.CommonConstants;
import com.throne.emm.constant.ConstantTime;
import com.throne.emm.eventbus.event.PostInfoEvent;
import com.throne.emm.mdm.manage.AppManage;
import com.throne.emm.model.AppInfo;
import com.throne.emm.net.result.PostInfoResult;
import com.throne.emm.net.utils.NetRequestUtil;

import de.greenrobot.event.EventBus;

public class DeviceInfoUploadThread implements Runnable {
	public DeviceInfoUploadThread(Context mContext) {
		super();
		this.mContext = mContext;
	}

	private AppManage mAppManage;
	private Context mContext;

	@Override
	public void run() {
		UploadSoftWare();
		MyApplication.mLogger.info("UploadSoftWare");
		UploadHardWare();
		MyApplication.mLogger.info("UploadHardWare");
	}

	private void UploadHardWare() {
		TelephonyManager tm = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
		PostInfoResult mPostInfoResult = new PostInfoResult();
		NetRequestUtil mNetRequestUtil = new NetRequestUtil(mPostInfoResult);
		RequestParams params = new RequestParams();
		params.put("act", "addDeviceHardware");
		params.put("deviceTypeName", Build.MODEL);
		params.put("uuid", MyApplication.UUID);
		params.put("deviceSoftwareVersion",tm.getDeviceSoftwareVersion() );
		params.put("getLine1Numbe", tm.getLine1Number());
		params.put("networkCountryIso", tm.getNetworkCountryIso());
		params.put("networkTypeName", TelUtils.getNetworkType(tm.getNetworkType()));
		params.put("simOperatorName", tm.getSimOperatorName());
		mNetRequestUtil.doPost(CommonConstants.getUpload(), params);
	}

	private void UploadSoftWare() {
		List<AppInfo> appInfos = AppManageHelper.queryFilterAppInfo(
				AppManageHelper.FILTER_THIRD_APP, mContext);
		try {
			JSONObject mJSONObject = new JSONObject();
			mJSONObject.put("uuid", MyApplication.UUID);
			JSONArray mJsonArray = new JSONArray();
			for (AppInfo app : appInfos) {
				JSONObject mJsonObject2 = new JSONObject();
				mJsonObject2.put("pkgName", app.getPackageName());
				mJsonObject2.put("version", app.getPackageVersion());
				mJsonObject2.put("appName", app.getAppName());
				mJsonArray.put(mJsonObject2);
			}
			mJSONObject.put("pkgs", mJsonArray);
			PostInfoResult mPostInfoResult = new PostInfoResult();
			NetRequestUtil mNetRequestUtil = new NetRequestUtil(mPostInfoResult);
			RequestParams params = new RequestParams();
			params.put("act", "addSoftHardware");
			params.put("soft", mJSONObject.toString());
			mNetRequestUtil.doPost(CommonConstants.getUpload(), params);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
