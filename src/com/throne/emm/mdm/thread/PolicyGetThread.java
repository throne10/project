package com.throne.emm.mdm.thread;


import com.loopj.android.http.RequestParams;
import com.throne.emm.constant.CommonConstants;
import com.throne.emm.net.result.PolicyGetResult;
import com.throne.emm.net.result.PostInfoResult;
import com.throne.emm.net.utils.NetRequestUtil;

import android.content.Context;


public class PolicyGetThread implements Runnable {
	public PolicyGetThread(Context mContext) {
		super();
		this.mContext = mContext;
	}

	private Context mContext;

	@Override
	public void run() {
		getPolicy();
	}

	private void getPolicy() {
		PolicyGetResult mPolicyGetResult = new PolicyGetResult();
		NetRequestUtil mNetRequestUtil = new NetRequestUtil(mPolicyGetResult);
		mNetRequestUtil.doGet(CommonConstants.getPolicy(), null);		
	}


}
