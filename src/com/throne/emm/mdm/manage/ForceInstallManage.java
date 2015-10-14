package com.throne.emm.mdm.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.throne.emm.model.AppInfo;
import com.throne.emm.model.WifiInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ForceInstallManage {
    private SharedPreferences mSp;
    private Context mContext;
    private Editor mEditor;
    private List<String> mArrayList = null;

    public ForceInstallManage(Context mContext) {
        super();
        this.mContext = mContext;
        mSp = mContext.getSharedPreferences("Force_install_list", Context.MODE_PRIVATE);
        mEditor = mSp.edit();
    }

    public void saveForceInstall(List<AppInfo> apps) {
        for (AppInfo mAppInfo : apps) {
            mEditor.putString(mAppInfo.getPackageName(), "");
        }
    }

    public void deleForceInstall(String PackageName) {
        mEditor.remove(PackageName);
    }

    public List<String> getForceInstall() {
        ArrayList<String> mArrayList = new ArrayList<String>();
        Map<String, ?> mMap = mSp.getAll();
        for (Entry<String, ?> entry : mMap.entrySet()) {
            mArrayList.add(entry.getKey());
        }
        return mArrayList;
    }
}
