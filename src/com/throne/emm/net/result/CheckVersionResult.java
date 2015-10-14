package com.throne.emm.net.result;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.throne.emm.eventbus.event.AppGetEvent;
import com.throne.emm.eventbus.event.BaseEvent;
import com.throne.emm.eventbus.event.CheckDeviceEvent;
import com.throne.emm.eventbus.event.CheckVersionEvent;
import com.throne.emm.model.AppInfo;

import de.greenrobot.event.EventBus;

import android.os.Handler;
import android.os.Message;

public class CheckVersionResult extends BaseResult {


	private CheckVersionEvent mCheckVersionEvent= new CheckVersionEvent();
	private boolean success;

	@Override
	public void dataAnalysis(String content) {
		try {
			JSONObject mJSONObject = new JSONObject(content);
			if(mJSONObject.has("success"))
			{
				success = mJSONObject.getBoolean("success");
			}
			if(success)
			{
				int mint=mJSONObject.getInt("message");
				if(mint==1)
				{
					mCheckVersionEvent.setResult(CheckVersionEvent.UPDATE);
				}
				else
				{
					mCheckVersionEvent.setResult(CheckVersionEvent.NO_UPDATE);
				}
			}
			else
			{
				mCheckVersionEvent.setResult(CheckVersionEvent.Fail);
			}
			EventBus.getDefault().post(mCheckVersionEvent);
		} catch (Exception e) {
			mCheckVersionEvent.setResult(BaseEvent.Fail);
			EventBus.getDefault().post(mCheckVersionEvent);
			e.printStackTrace();
		}
	}
	@Override
	public void postFailure() {
		mCheckVersionEvent.setResult(BaseEvent.Fail);
		EventBus.getDefault().post(mCheckVersionEvent);
	}
}
