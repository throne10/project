package com.throne.emm.net.result;

import org.json.JSONException;
import org.json.JSONObject;

import com.throne.emm.eventbus.event.BaseEvent;
import com.throne.emm.eventbus.event.PostInfoEvent;
import com.throne.emm.model.PolicyInfo;

import de.greenrobot.event.EventBus;

public class PolicyGetResult extends BaseResult {

	private PostInfoEvent mPostInfoEvent = new PostInfoEvent();

	@Override
	public void dataAnalysis(String content) {
		//
		PolicyInfo mPolicyInfo = new PolicyInfo();
		try {
			JSONObject mJsonObject = new JSONObject(content);
			mJsonObject=(JSONObject) mJsonObject.get("content");
			if (mJsonObject.has("bluetooth")) {
				mPolicyInfo.setBluetooth(mJsonObject.getBoolean("bluetooth"));
			}
			if (mJsonObject.has("white")) {
				JSONObject json=(JSONObject) mJsonObject.get("white");
				String[] whitelist=json.getString("list").split(";");
				String[] uninstallpackage = json.getString("uninstall").split(";");
				mPolicyInfo.setUninstall(uninstallpackage);
				mPolicyInfo.setWhite(whitelist);
			}
			mPostInfoEvent.setEvent(mPolicyInfo);
			EventBus.getDefault().post(mPostInfoEvent);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void postFailure() {
		mPostInfoEvent.setResult(BaseEvent.Fail);
		EventBus.getDefault().post(mPostInfoEvent);
	}
}
