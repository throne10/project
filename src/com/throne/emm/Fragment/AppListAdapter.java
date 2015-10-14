package com.throne.emm.Fragment;

import java.io.File;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.throne.emm.R;
import com.throne.emm.activity.AppDetailActivity;
import com.throne.emm.common.utils.AppManageHelper;
import com.throne.emm.constant.CommonConstants;
import com.throne.emm.mdm.manage.AppForceInstallManage;
import com.throne.emm.model.AppInfo;
import com.throne.emm.net.utils.AppDownloadUtil;
import com.throne.emm.view.MyProgressBar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AppListAdapter extends BaseAdapter implements OnClickListener {
	private LayoutInflater inflater;
	private DisplayImageOptions options_icon;
	private DisplayImageOptions options_bg;
	private List<AppInfo> mApplist;
	private ImageLoader imageLoader;
	private Context mContext;
	private String appl;
	private PackageManager pm;

	public AppListAdapter(Context mContext, List<AppInfo> applist) {
		inflater = LayoutInflater.from(mContext);
		options_bg = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).build();
		options_icon = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).showImageForEmptyUri(R.drawable.appdefault)
				.showImageOnFail(R.drawable.appdefault).build();
		this.mContext = mContext;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		pm = mContext.getPackageManager();
		this.mApplist = applist;
	}

	public static class ViewHolder {

		public TextView iv_app_open;
		public ImageView iv_app_icon;
		public TextView iv_app_detail;
		public TextView iv_app_install;
		public TextView tv_app_more;
		public TextView tv_app_name;
		public ImageView iv_item_bg;
		public TextView iv_app_cancel;
		public LinearLayout ll_app_download;
		public MyProgressBar mp_app_progressBar;
		public TextView tv_app_tip;

	}

	@Override
	public int getCount() {
		return mApplist.size();
	}

	@Override
	public Object getItem(int position) {
		return mApplist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			ViewHolder holder;
			holder = new ViewHolder();
			AppInfo app = mApplist.get(position);
			convertView = inflater.inflate(R.layout.app_list_item, parent,
					false);
			holder.tv_app_more = (TextView) convertView
					.findViewById(R.id.tv_app_more);
			holder.tv_app_name = (TextView) convertView
					.findViewById(R.id.tv_app_name);
			holder.iv_app_open = (TextView) convertView
					.findViewById(R.id.iv_app_open);
			holder.iv_app_icon = (ImageView) convertView
					.findViewById(R.id.iv_app_icon);
			holder.iv_item_bg = (ImageView) convertView
					.findViewById(R.id.iv_item_bg);
			holder.iv_app_detail = (TextView) convertView
					.findViewById(R.id.iv_app_detail);
			holder.iv_app_install = (TextView) convertView
					.findViewById(R.id.iv_app_install);
			holder.tv_app_tip = (TextView) convertView
					.findViewById(R.id.tv_app_tip);
			holder.iv_app_cancel = (TextView) convertView
					.findViewById(R.id.iv_app_cancel);
			holder.ll_app_download = (LinearLayout) convertView
					.findViewById(R.id.ll_app_download);
			holder.mp_app_progressBar = (MyProgressBar) convertView
					.findViewById(R.id.mp_app_progressBar);
			holder.iv_app_open.setOnClickListener(this);
			holder.iv_app_detail.setOnClickListener(this);
			holder.iv_app_install.setOnClickListener(this);
			holder.iv_app_cancel.setOnClickListener(this);
			holder.tv_app_name.setText(app.getAppName());
			if (AppManageHelper.isAppInstalled(mContext, app.getPackageName())) {
				ButtonViewChange(holder, View.GONE, View.VISIBLE, View.VISIBLE,
						View.GONE);
			} else {
				ButtonViewChange(holder, View.VISIBLE, View.GONE, View.VISIBLE,
						View.GONE);
			}
			if (AppDownloadUtil.mDownloadMap.containsKey(app.getPackageName())) {
				ButtonViewChange(holder, View.GONE, View.GONE, View.VISIBLE,
						View.VISIBLE);
				DownloadViewChange(holder, View.VISIBLE, View.GONE, "正在下载...");
				AppDownloadAjaxCallBack mAppDownloadAjaxCallBack = AppDownloadUtil.mDownloadMap
						.get(app.getPackageName());
				mAppDownloadAjaxCallBack.reSetHolder(holder);
			}
			String url_icon = CommonConstants.getFullUrl(app
					.getPkgHeadpicPath());
			String url_bg = CommonConstants.getFullUrl(app.getBackground());
			setTag(holder, position);
			if (url_icon != null && !url_icon.equals("")) {
				imageLoader.displayImage(url_icon, holder.iv_app_icon,
						options_icon);
			} else {
				PackageInfo mPackageInfo;
				try {
					mPackageInfo = pm.getPackageInfo(app.getPackageName(), 0);
					holder.iv_app_icon.setBackgroundDrawable(mPackageInfo.applicationInfo.loadIcon(pm));
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
			}
			if (!url_bg.equals("")) {
				imageLoader.displayImage(url_bg, holder.iv_item_bg, options_bg);
			}
		}
		return convertView;
	}

	// 调用stop()方法停止下载
	// handler.stop();

	private void setTag(ViewHolder holder, int position) {
		holder.iv_app_open.setTag(mApplist.get(position));
		holder.iv_app_open.setTag(R.id.tag_holder, holder);
		holder.iv_app_detail.setTag(mApplist.get(position));
		holder.iv_app_detail.setTag(R.id.tag_holder, holder);
		holder.iv_app_install.setTag(mApplist.get(position));
		holder.iv_app_install.setTag(R.id.tag_holder, holder);
		holder.iv_app_cancel.setTag(mApplist.get(position));
		holder.iv_app_cancel.setTag(R.id.tag_holder, holder);

	}

	private void ButtonViewChange(ViewHolder holder, int View1, int View2,
			int View3, int View4) {
		holder.iv_app_install.setVisibility(View1);
		holder.iv_app_open.setVisibility(View2);
		holder.iv_app_detail.setVisibility(View3);
		holder.iv_app_cancel.setVisibility(View4);
	}

	private void DownloadViewChange(ViewHolder holder, int View1, int View2,
			String string) {
		holder.ll_app_download.setVisibility(View1);
		holder.tv_app_more.setVisibility(View2);
		holder.tv_app_tip.setText(string);
	}

	public class AppDownloadAjaxCallBack extends AjaxCallBack<File> {
		public AppDownloadAjaxCallBack(ViewHolder holder, AppInfo app) {
			super();
			this.holder = holder;
			this.app = app;
		}

		private ViewHolder holder;
		private int Progress;
		private HttpHandler<File> mHttpHandler;

		public ViewHolder getHolder() {
			return holder;
		}

		public void reSetHolder(ViewHolder holder) {
			this.holder = holder;
			this.holder.iv_app_cancel
			.setTag(R.id.tag_downhandler, getHandler());
			this.holder.mp_app_progressBar.setProgress(Progress);
		}

		private AppInfo app;

		@Override
		public void onLoading(long count, long current) {
			holder.tv_app_tip.setText("正在下载...");
			Progress = (int) ((current * 100 / count));
			holder.mp_app_progressBar.setProgress(Progress);
			Log.i("yxd", "下载进度：" + current + "/" + count + " "
					+ (int) ((current * 100 / count)));
		}

		public int getProgress() {
			return Progress;
		}

		public void setProgress(int progress) {
			Progress = progress;
		}

		@Override
		public void onSuccess(File t) {
			File f = new File(CommonConstants.getAppPath(app.getPackageName()));
			t.renameTo(f);
			AppManageHelper
			.installpkg(mContext, f.getAbsolutePath().toString());
			AppForceInstallManage mAppForceInstallManage=new AppForceInstallManage(mContext);
			mAppForceInstallManage.saveForceInstallApp(app.getPackageName(), f.getAbsolutePath().toString());
			DownloadViewChange(holder, View.GONE, View.VISIBLE, "正在初始化...");
			ButtonViewChange(holder, View.VISIBLE, View.GONE, View.VISIBLE,
					View.GONE);
			AppDownloadUtil.mDownloadMap.remove(app.getPackageName());
			Log.i("yxd", t == null ? "null" : f.getAbsoluteFile().toString());

		}

		@Override
		public void onFailure(Throwable throwable, int i, String s) {
			DownloadViewChange(holder, View.GONE, View.VISIBLE, "正在初始化...");
			ButtonViewChange(holder, View.VISIBLE, View.GONE, View.VISIBLE,
					View.GONE);
			Toast.makeText(mContext, app.getAppName() + "下载失败！",
					Toast.LENGTH_SHORT).show();
		}

		public void setHandler(HttpHandler<File> mHttpHandler) {
			this.mHttpHandler = mHttpHandler;
		}

		public HttpHandler<File> getHandler() {
			return mHttpHandler;
		}
	}

	@Override
	public void onClick(View view) {
		AppInfo app = (AppInfo) view.getTag();
		ViewHolder holder = (ViewHolder) view.getTag(R.id.tag_holder);
		switch (view.getId()) {
		case R.id.iv_app_open:
			AppManageHelper.startAppByPackageName(mContext,
					app.getPackageName());
			break;
		case R.id.iv_app_detail:
			Intent mIntent = new Intent(mContext, AppDetailActivity.class);
			Bundle mBundle = new Bundle();
			mBundle.putSerializable("app", app);
			mIntent.putExtras(mBundle);
			mContext.startActivity(mIntent);
			break;
		case R.id.iv_app_install:
			File f = new File(CommonConstants.getAppPath(app.getPackageName()));
			if (f.exists()) {
				AppManageHelper.installpkg(mContext, f.getAbsolutePath()
						.toString());
			} else {
				AppDownloadAjaxCallBack mAppDownloadAjaxCallBack = new AppDownloadAjaxCallBack(
						holder, app);
				AppDownloadUtil.appDownload(holder, app,
						mAppDownloadAjaxCallBack);
				DownloadViewChange(holder, View.VISIBLE, View.GONE, "正在初始化...");
				ButtonViewChange(holder, View.GONE, View.GONE, View.VISIBLE,
						View.VISIBLE);
			}
			break;
		case R.id.iv_app_cancel:
			HttpHandler<File> handler = (HttpHandler<File>) view
			.getTag(R.id.tag_downhandler);
			handler.stop();
			DownloadViewChange(holder, View.GONE, View.VISIBLE, "正在初始化...");
			ButtonViewChange(holder, View.VISIBLE, View.GONE, View.VISIBLE,
					View.GONE);
			break;
		default:
			break;
		}
	}
}
