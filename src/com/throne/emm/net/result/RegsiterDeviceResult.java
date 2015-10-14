package com.throne.emm.net.result;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.throne.emm.eventbus.event.AppGetEvent;
import com.throne.emm.eventbus.event.BaseEvent;
import com.throne.emm.eventbus.event.CheckDeviceEvent;
import com.throne.emm.eventbus.event.RegsiterDeviceEvent;
import com.throne.emm.model.AppInfo;

import de.greenrobot.event.EventBus;

import android.os.Handler;
import android.os.Message;

public class RegsiterDeviceResult extends BaseResult {


	private RegsiterDeviceEvent mRegsiterDeviceEvent= new RegsiterDeviceEvent();
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
				mRegsiterDeviceEvent.setResult(CheckDeviceEvent.Success);
				EventBus.getDefault().post(mRegsiterDeviceEvent);
			}
			else
			{
				mRegsiterDeviceEvent.setResult(CheckDeviceEvent.Fail);
				EventBus.getDefault().post(mRegsiterDeviceEvent);
			}
		} catch (Exception e) {
			mRegsiterDeviceEvent.setResult(BaseEvent.Fail);
			EventBus.getDefault().post(mRegsiterDeviceEvent);
			e.printStackTrace();
		}
	}
	@Override
	public void postFailure() {
		mRegsiterDeviceEvent.setResult(BaseEvent.Fail);
		EventBus.getDefault().post(mRegsiterDeviceEvent);
	}
}
