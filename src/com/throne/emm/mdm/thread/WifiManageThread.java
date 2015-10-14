package com.throne.emm.mdm.thread;


import com.throne.emm.mdm.manage.WifiManage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

public class WifiManageThread implements Runnable {
    public WifiManageThread(WifiManager mWifiManager, ConnectivityManager mConnectivityManager, Context mContext) {
        super();
        this.mWifiManager = mWifiManager;
        this.mContext = mContext;
        this.mConnectivityManager = mConnectivityManager;
    }

    private WifiManager mWifiManager;
    private Context mContext;
    private ConnectivityManager mConnectivityManager;
    private WifiManage mWifiManage;

    @Override
    public void run() {
        boolean wifi = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        if (wifi)
        {
            manageWifiWhiteList();
        }
    }

    private void manageWifiWhiteList() {
        String  BSSID= mWifiManager.getConnectionInfo().getBSSID();
        mWifiManage=new WifiManage(mContext);
        if(mWifiManage.isWhiteWifi(BSSID))
        {
            mWifiManager.disconnect();
        }
    }
}
