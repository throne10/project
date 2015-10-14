package com.throne.emm.mdm.thread;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.throne.emm.activity.AppLockActivity;
import com.throne.emm.mdm.manage.AppManage;

public class AppManageThread implements Runnable {
    public AppManageThread(ActivityManager mActivityManager,Context mContext) {
        super();
        this.mActivityManager=mActivityManager;
        this.mContext = mContext;
    }

    private ActivityManager mActivityManager;
    private AppManage mAppManage;
    private Context mContext;

    @Override
    public void run() {
        manageAppWhiteList();
    }

    private void manageAppWhiteList() {
        String currentPackageName = mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        mAppManage = new AppManage(mContext);
        if (!mAppManage.isWhiteApp(currentPackageName)) {
            Intent mIntent = new Intent(mContext, AppLockActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(mIntent);
        }
    }
}
