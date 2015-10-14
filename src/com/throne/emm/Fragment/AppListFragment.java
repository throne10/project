package com.throne.emm.Fragment;

import java.util.List;
import java.util.zip.Inflater;

import com.throne.emm.R;
import com.throne.emm.constant.CommonConstants;
import com.throne.emm.eventbus.event.AppGetEvent;
import com.throne.emm.eventbus.event.BaseEvent;
import com.throne.emm.model.AppInfo;
import com.throne.emm.net.result.AppGetResult;
import com.throne.emm.net.result.BaseResult;
import com.throne.emm.net.utils.NetRequestUtil;

import de.greenrobot.event.EventBus;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AppListFragment extends Fragment{
	private Context mContext;
	private ListView myapp_listview;
	private List<AppInfo> mAppList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this); 
	}
	@Override
	public void onResume() {
		super.onResume();
		getApp();
	}
	public static Fragment getInstance(Context mContext) {
		AppListFragment instance = new AppListFragment();
		instance.mContext=mContext;
		return instance;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView=inflater.inflate(R.layout.app_list_fragment, container, false);
		myapp_listview =(ListView)mView.findViewById(R.id.myapp_listview);
		return mView;
	}
	private void getApp() {
		Log.i("yxd", "AppListFragment getApp");
		BaseResult mAppGetResult= new AppGetResult();
		NetRequestUtil myNetRequestUtil=new NetRequestUtil(mAppGetResult);
		myNetRequestUtil.doGet(CommonConstants.getApp(), null);
	}
	//Event
	public void onEventMainThread(AppGetEvent mAppGetEvent)  
	{  

		switch (mAppGetEvent.getResult()) {
		case BaseEvent.Success:
			mAppList = (List<AppInfo>)mAppGetEvent.getEvent();
			myapp_listview.setAdapter(new AppListAdapter(mContext,mAppList));
			break;
		case BaseEvent.Fail:

			break;
		default:
			break;
		}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
