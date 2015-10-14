package com.throne.emm.activity;

import java.util.ArrayList;
import java.util.List;

import com.throne.emm.R;
import com.throne.emm.Fragment.AppListAdapter;
import com.throne.emm.Fragment.AppListFragment;
import com.throne.emm.Fragment.MyAppFragment;
import com.throne.emm.Fragment.MyFragmentPagerAdapter;
import com.throne.emm.Fragment.MyViewPager;
import com.throne.emm.Fragment.SettingsFragment;
import com.throne.emm.common.utils.MdmUtils;
import com.throne.emm.constant.CommonConstants;
import com.throne.emm.eventbus.event.AppGetEvent;
import com.throne.emm.eventbus.event.AppWhiteEvent;
import com.throne.emm.eventbus.event.BaseEvent;
import com.throne.emm.mdm.service.ManageService;
import com.throne.emm.model.AppInfo;
import com.throne.emm.net.result.AppGetResult;
import com.throne.emm.net.result.BaseResult;
import com.throne.emm.net.utils.NetRequestUtil;

import de.greenrobot.event.EventBus;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class InitActivity extends FragmentActivity {
	private Context mContext;
	private MyViewPager mViewPager;
	private List<Fragment> mDatas;
	private RadioGroup mRadioGroup;
	protected Drawable mDrawable;
	private List<AppInfo> mAppList;
	private RadioButton tab_my_app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// EventBus.getDefault().register(this);
		mContext = this;
		startService(new Intent(mContext, ManageService.class));
		// 激活更新策略应用
		initView();
		initDate();
	}

	private void initDate() {
		mDatas = new ArrayList<Fragment>();

		Fragment mAppListFragment = AppListFragment.getInstance(this);
		Fragment mMyAppFragment = MyAppFragment.getInstance(this);
		Fragment mSettingsFragment = SettingsFragment.getInstance(this);

		mDatas.add(mMyAppFragment);
		mDatas.add(mAppListFragment);
		mDatas.add(mSettingsFragment);

		MyFragmentPagerAdapter mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(
				getSupportFragmentManager(), mDatas);
		mViewPager.setAdapter(mMyFragmentPagerAdapter);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.tab_my_app:
					mViewPager.setCurrentItem(0);
					break;
				case R.id.tab_app_list:
					mViewPager.setCurrentItem(1);
					break;
				case R.id.tab_settings:
					mViewPager.setCurrentItem(2);
					break;
				default:
					break;
				}
			}
		});
		tab_my_app.performClick();
	}

	private void initView() {
		mViewPager = (MyViewPager) findViewById(R.id.id_viewpager);
		mRadioGroup = (RadioGroup) findViewById(R.id.tabs_rg);
		tab_my_app = (RadioButton) findViewById(R.id.tab_my_app);
	}

	@Override
	protected void onResume() {
		if(!MdmUtils.isAdminActive(mContext))
		{
			MdmUtils.addAdmin(mContext);
		}
		super.onResume();
	}

	/**
	 * 返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// EventBus.getDefault().unregister(this);
	}
}
