package com.throne.emm.mdm.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppManage {
	private SharedPreferences mSp;
	private Context mContext;
	private Editor mEditor;
	private List<String> mArrayList = null;

	public AppManage(Context mContext) {
		super();
		this.mContext = mContext;
		mSp = mContext.getSharedPreferences("App_white_list",
				Context.MODE_PRIVATE);
		mEditor = mSp.edit();
	}

	private static final String[] DEF_WHITE_APP = { "com.throne.emm",
			"com.android.settings" };

	public boolean isWhiteApp(String currentPackageName) {
		for (String whiteapp : DEF_WHITE_APP) {
			if (whiteapp.equals(currentPackageName)) {
				return true;
			}
		}
		if (mArrayList == null) {
			mArrayList = getWhiteApp();
		}
		for (String whiteapp : mArrayList) {
			if (whiteapp.equals(currentPackageName)) {
				return true;
			}
		}
		return false;
	}

	public void saveWhiteApp(String packeagename) {
		mEditor.putString(packeagename, packeagename);
		 mEditor.commit();
	}

	public List<String> getWhiteApp() {
		ArrayList<String> mArrayList = new ArrayList<String>();
		Map<String, ?> mMap = mSp.getAll();
		for (Entry<String, ?> entry : mMap.entrySet()) {
			mArrayList.add(entry.getKey());
		}
		return mArrayList;
	}

	public void deleWhiteApp(String packeagename) {
		mEditor.remove(packeagename);
		 mEditor.commit();
	}

	public void updateAppWhiteList() {
		mArrayList = getWhiteApp();
	}
}
