package com.throne.emm.net.result;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.throne.emm.eventbus.event.AppGetEvent;
import com.throne.emm.eventbus.event.BaseEvent;
import com.throne.emm.eventbus.event.CheckDeviceEvent;
import com.throne.emm.model.AppInfo;

import de.greenrobot.event.EventBus;

import android.os.Handler;
import android.os.Message;

public class CheckDeviceResult extends BaseResult {


	private CheckDeviceEvent mCheckDeviceEvent= new CheckDeviceEvent();
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
				mCheckDeviceEvent.setResult(CheckDeviceEvent.Success);
				EventBus.getDefault().post(mCheckDeviceEvent);
			}
			else
			{
				mCheckDeviceEvent.setResult(CheckDeviceEvent.NO_REGISTER);
				EventBus.getDefault().post(mCheckDeviceEvent);
			}
		} catch (Exception e) {
			mCheckDeviceEvent.setResult(BaseEvent.Fail);
			EventBus.getDefault().post(mCheckDeviceEvent);
			e.printStackTrace();
		}
	}
	@Override
	public void postFailure() {
		mCheckDeviceEvent.setResult(BaseEvent.Fail);
		EventBus.getDefault().post(mCheckDeviceEvent);
	}
}
