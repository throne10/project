package com.throne.emm.mdm.thread;

import android.content.Context;

public class ForceInstallManageThread implements Runnable {
    public ForceInstallManageThread(Context mContext) {
        super();
        this.mContext = mContext;
    }

    private Context mContext;

    @Override
    public void run() {
            manageForceInstall();
    }

    private void manageForceInstall() {
        
    }
}
