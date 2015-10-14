package com.throne.emm.net.result;

import com.throne.emm.eventbus.event.BaseEvent;
import com.throne.emm.eventbus.event.PostInfoEvent;

import de.greenrobot.event.EventBus;

public class PostInfoResult extends BaseResult {

	private PostInfoEvent mPostInfoEvent = new PostInfoEvent();

	@Override
	public void dataAnalysis(String content) {
		mPostInfoEvent.setResult(BaseEvent.Success);
		EventBus.getDefault().post(mPostInfoEvent);
	}

	@Override
	public void postFailure() {
		mPostInfoEvent.setResult(BaseEvent.Fail);
		EventBus.getDefault().post(mPostInfoEvent);
	}
}
