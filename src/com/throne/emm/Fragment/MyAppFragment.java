package com.throne.emm.Fragment;

import java.util.List;

import com.throne.emm.R;
import com.throne.emm.common.utils.AppManageHelper;
import com.throne.emm.constant.CommonConstants;
import com.throne.emm.eventbus.event.AppGetEvent;
import com.throne.emm.eventbus.event.BaseEvent;
import com.throne.emm.eventbus.event.MyAppAddEvent;
import com.throne.emm.eventbus.event.MyAppGetEvent;
import com.throne.emm.eventbus.event.PolicyGetEvent;
import com.throne.emm.mdm.manage.AppManage;
import com.throne.emm.model.AppInfo;
import com.throne.emm.model.PolicyInfo;
import com.throne.emm.net.result.AppGetResult;
import com.throne.emm.net.result.BaseResult;
import com.throne.emm.net.result.MyAppGetResult;
import com.throne.emm.net.utils.NetRequestUtil;

import de.greenrobot.event.EventBus;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class MyAppFragment extends Fragment {
	private ListView myapp_listview;
	private Context mContext;
	private List<AppInfo> mAppList;
	private String[] defapp = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		getMyApp();
	}

	public static Fragment getInstance(Context mContext) {
		MyAppFragment instance = new MyAppFragment();
		instance.mContext = mContext;
		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.my_app_fragment, container,
				false);
		myapp_listview = (ListView) mView.findViewById(R.id.myapp_listview);
		return mView;
	}

	private void getMyApp() {
		BaseResult mMyAppGetResult = new MyAppGetResult();
		NetRequestUtil myNetRequestUtil = new NetRequestUtil(mMyAppGetResult);
		myNetRequestUtil.doGet(CommonConstants.getApp(), null);
	}

	// Event
	public void onEventMainThread(MyAppGetEvent mMyAppGetEvent) {
		switch (mMyAppGetEvent.getResult()) {
		case BaseEvent.Success:
			Log.i("yxd", "MY");
			mAppList = (List<AppInfo>) mMyAppGetEvent.getEvent();
			addDefApp();
			mAppList = AppManageHelper.getInstallMyApp(mContext, mAppList);
			myapp_listview.setAdapter(new AppListAdapter(mContext, mAppList));
			break;
		case BaseEvent.Fail:

			break;
		default:
			break;
		}
	}

	private void addDefApp() {
		PackageManager pm = mContext.getPackageManager();
		if (defapp != null) {
			AppInfo mAppInfo = new AppInfo();
			for (String s : defapp) {
				try {
					if(AppManageHelper.isAppInstalled(mContext, s))
					{
						PackageInfo mPackageInfo = pm.getPackageInfo(s, 0);
						mAppInfo.setPackageName(s);
						mAppInfo.setAppName((String)mPackageInfo.applicationInfo.loadLabel(pm));
					}
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	public void onEventBackgroundThread(MyAppAddEvent MyAppAddEvent) {
		defapp = (String[]) MyAppAddEvent.getEvent();
	}
}
