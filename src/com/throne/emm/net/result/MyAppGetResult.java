package com.throne.emm.net.result;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.throne.emm.eventbus.event.AppGetEvent;
import com.throne.emm.eventbus.event.BaseEvent;
import com.throne.emm.eventbus.event.MyAppGetEvent;
import com.throne.emm.model.AppInfo;

import de.greenrobot.event.EventBus;

import android.os.Handler;
import android.os.Message;

public class MyAppGetResult extends BaseResult {


	private MyAppGetEvent mMyAppGetEvent;

	@Override
	public void dataAnalysis(String content) {
		try {
			JSONArray mJSONArray = new JSONArray(content);
			mMyAppGetEvent=new MyAppGetEvent();
			List<AppInfo> mlist=new ArrayList<AppInfo>();
			for(int i=0;i<mJSONArray.length();i++)
			{
				AppInfo mAppInfo=new AppInfo();
				JSONObject mJSONObject = (JSONObject) mJSONArray.get(i);
				mAppInfo.setAppName(mJSONObject.getString("name"));
				mAppInfo.setPackageName(mJSONObject.getString("packageName"));
				mAppInfo.setPkgHeadpicPath(mJSONObject.getString("iconPath"));
				mAppInfo.setPkgFilePath(mJSONObject.getString("appPath"));
				mAppInfo.setBackground(mJSONObject.getString("isShow"));
				mAppInfo.setUpdateTime(mJSONObject.getString("updateTime"));
				mAppInfo.setPackageVersion(mJSONObject.getString("version"));
				mAppInfo.setIsUpdate(mJSONObject.getString("isUpdate"));
				mAppInfo.setIsDelete(mJSONObject.getString("isDelete"));
				mAppInfo.setId(mJSONObject.getString("id"));
				mlist.add(mAppInfo);
			}
			mMyAppGetEvent.setEvent(mlist);
			mMyAppGetEvent.setResult(BaseEvent.Success);
			EventBus.getDefault().post(mMyAppGetEvent);
		} catch (Exception e) {
			mMyAppGetEvent.setResult(BaseEvent.Fail);
			e.printStackTrace();
		}
	}
	@Override
	public void postFailure() {
		mMyAppGetEvent.setResult(BaseEvent.Fail);
		EventBus.getDefault().post(mMyAppGetEvent);
	}
}
