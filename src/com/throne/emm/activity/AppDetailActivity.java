package com.throne.emm.activity;

import java.net.URLEncoder;
import java.util.List;

import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.throne.emm.R;
import com.throne.emm.application.MyApplication;
import com.throne.emm.constant.CommonConstants;
import com.throne.emm.model.AppInfo;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.*;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AppDetailActivity extends Activity {
	public static AppDetailActivity context = null;
	private ImageView iconimg = null;
	private TextView apptext = null;
	private TextView version;
	private TextView time;
	private DisplayImageOptions options;
	private PackageManager pm;
	private ImageView donloadbtn = null;
	private static ProgressBar progress = null;

	public final static int Handler_SET_PROGRESS = 4;
	public final static int Handler_LIST_CLICK = 3;
	public final static int DOWNLOAD_FAILED = 0;
	public final static int DOWNLOAD_SUCCESS = 1;
	public final static int DOWNLOAD_WATTING = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_detail);
		context = this;
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(AppDetailActivity.this));
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).build();

		iconimg = (ImageView) findViewById(R.id.appdetailimg);
		apptext = (TextView) findViewById(R.id.appdetailname);
		version = (TextView) findViewById(R.id.version);
		time = (TextView) findViewById(R.id.time);
		donloadbtn = (ImageView) findViewById(R.id.imageView1);
		progress = (ProgressBar) findViewById(R.id.progressBar1);

		AppInfo AppInfo = (AppInfo) getIntent().getSerializableExtra("app");

		if (AppInfo.getPkgHeadpicPath() == null
				|| AppInfo.getPkgHeadpicPath().equals("")) {
			// iconimg.setImageDrawable(AppInfo.getAppIcon());
			pm = getPackageManager();
			try {
				Drawable appicon = pm.getApplicationIcon(AppInfo
						.getPackageName());
				iconimg.setImageDrawable(appicon);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			imageLoader.displayImage(CommonConstants.getFullUrl(AppInfo.getPkgHeadpicPath()), iconimg,
					options);
		}

		apptext.setText(AppInfo.getAppName());
		time.setText(AppInfo.getUpdateTime());
		version.setText(AppInfo.getPackageVersion());

		final String packName = AppInfo.getPackageName();
		final String apkurl = AppInfo.getPkgFilePath();
		final String apklable = AppInfo.getAppName();

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		doStart();
	}

	private void doStart() {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// Log.v("123","333333333333333333333");
		super.onWindowFocusChanged(hasFocus);
	}

}
