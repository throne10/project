package com.throne.emm.mdm.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.throne.emm.common.utils.AppManageHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppForceInstallManage {
	private SharedPreferences mSp;
	private Context mContext;
	private Editor mEditor;
	public AppForceInstallManage(Context mContext) {
		super();
		this.mContext = mContext;
		mSp = mContext.getSharedPreferences("App_white_list",
				Context.MODE_PRIVATE);
		mEditor = mSp.edit();
	}
	public void saveForceInstallApp(String packeagename,String path) {
		mEditor.putString(packeagename, path);
		 mEditor.commit();
	}
	
	public List<String> getAllSaveApp() {
		ArrayList<String> mArrayList = new ArrayList<String>();
		Map<String, String> mMap = (Map<String, String>) mSp.getAll();
		for (Entry<String, String> entry : mMap.entrySet()) {
			mArrayList.add(entry.getValue());
		}
		return mArrayList;
	}
	public List<String> getForceInstallPath(){
		ArrayList<String> mArrayList = new ArrayList<String>();
		Map<String, String> mMap = (Map<String, String>) mSp.getAll();
		for (Entry<String, String> entry : mMap.entrySet()) {
			if(!AppManageHelper.isAppInstalled(mContext, entry.getKey()))
			{
				mArrayList.add(entry.getValue());
			}
			else
			{
				mEditor.remove(entry.getKey());
			}
		}
		return mArrayList;
	}
	
}
