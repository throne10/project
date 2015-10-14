package com.throne.emm.mdm.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.throne.emm.model.WifiInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class WifiManage {
    private Context mContext;
    private SharedPreferences mSp;
    private Editor mEditor;
    private List<String> mArrayList;

    public WifiManage(Context mContext) {
        super();
        this.mContext = mContext;
        mSp = mContext.getSharedPreferences("wifi_white_list", Context.MODE_PRIVATE);
        mEditor = mSp.edit();
    }

    public void saveWhiteWifi(List<WifiInfo> macs) {
        for (WifiInfo mWifiInfo : macs) {
            mEditor.putString(mWifiInfo.getMac(), mWifiInfo.getStatus());
        }
        mEditor.commit();
    }

    public void deleWifiList(String macs) {
        mEditor.remove(macs);
        mEditor.commit();
    }

    public List<String> getWhiteWifi() {
        ArrayList<String> mArrayList = new ArrayList<String>();
        Map<String, ?> mMap = mSp.getAll();
        for (Entry<String, ?> entry : mMap.entrySet()) {
                mArrayList.add(entry.getKey());
        }
        return mArrayList;
    }

    public boolean isWhiteWifi(String mac) {
        mArrayList = getWhiteWifi();
        for (String whitewifi : mArrayList) {
            if (whitewifi.equals(mac)) {
                return true;
            }
        }
        return false;
    }
    
}
