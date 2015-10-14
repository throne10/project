package com.throne.emm.mdm.thread;

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.throne.emm.activity.AppLockActivity;
import com.throne.emm.common.utils.AppManageHelper;
import com.throne.emm.mdm.manage.AppForceInstallManage;
import com.throne.emm.mdm.manage.AppManage;

public class AppForceInstallThread implements Runnable {
	 private ActivityManager mActivityManager;
	    private AppManage mAppManage;
	    private Context mContext;
		private AppForceInstallManage mAppForceInstallManage;

    public AppForceInstallThread(Context mContext) {
        super();
        this.mContext = mContext;
        mAppForceInstallManage=new AppForceInstallManage(mContext);
    }


    @Override
    public void run() {
        InstallSaveApp();
    }

    private void InstallSaveApp() {
    	List<String> mList=mAppForceInstallManage.getForceInstallPath();
    	for(String s:mList)
    	{
    		AppManageHelper.installpkg(mContext,s);
    	}
    }
}
