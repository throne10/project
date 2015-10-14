package com.throne.emm.Fragment;

import java.util.List;

import com.throne.emm.R;
import com.throne.emm.eventbus.event.AppGetEvent;
import com.throne.emm.eventbus.event.BaseEvent;
import com.throne.emm.model.AppInfo;

import de.greenrobot.event.EventBus;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment{
	private Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this); 
	}
	public static Fragment getInstance(Context mContext) {
		SettingsFragment instance = new SettingsFragment();
		instance.mContext=mContext;
		return instance;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.settings_fragment, container, false);
	}
	
	public void onEventBackgroundThread(AppGetEvent mAppGetEvent)  
	{  
		Log.i("yxd", "1");
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(mContext);
	}
}
