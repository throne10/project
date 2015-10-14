package com.throne.emm.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.throne.emm.constant.CommonConstants;
import com.throne.emm.model.AppInfo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.Settings;

public class AppManageHelper {
	private static final String SCHEME = "package";
	public static final int FILTER_ALL_APP = 0; // 所有应用程序
	public static final int FILTER_SYSTEM_APP = 1; // 系统程序
	public static final int FILTER_THIRD_APP = 2; // 第三方应用程序
	public static final int FILTER_SDCARD_APP = 3; // 安装在SDCard的应用程序
	public static final int FILTER_NO_APP = 4;// 仅服务器的应用

	// 获取启动器
	public static String getLauncherPackageName(Context context) {
		final Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		final ResolveInfo res = context.getPackageManager().resolveActivity(
				intent, 0);
		if (res.activityInfo == null) {
			return null;
		} else {
			return res.activityInfo.packageName;
		}
	}

	// 进入设置详情
	public static void showInstalledAppDetails(Context context,
			String packageName) {
		Intent mIntent = new Intent();
		mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts(SCHEME, packageName, null);
		mIntent.setData(uri);
		context.startActivity(mIntent);
	}

	// 弹出卸载界面
	private static void uninstallApk(Context context, String packagename) {
		Intent intentcc = new Intent();
		intentcc.setAction(Intent.ACTION_DELETE);
		intentcc.setData(Uri.parse("package:" + packagename));
		context.startActivity(intentcc);
	}

	// 下载完成弹出安装界面
	public static void installpkg(Context context, String PackFile) {
		Intent intentcc = new Intent(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(PackFile));
		intentcc.setDataAndType(uri, "application/vnd.android.package-archive");
		intentcc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intentcc);
	}

	// 包名打开apk
	public static boolean startAppByPackageName(Context context,
			String packageName) {
		PackageInfo pi = null;
		try {
			pi = context.getPackageManager().getPackageInfo(packageName, 0);
			Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
			resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			resolveIntent.setPackage(pi.packageName);

			List<ResolveInfo> apps = context.getPackageManager()
					.queryIntentActivities(resolveIntent, 0);

			ResolveInfo ri = apps.iterator().next();
			if (ri != null) {
				String packageName1 = ri.activityInfo.packageName;
				String className = ri.activityInfo.name;
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);
				ComponentName cn = new ComponentName(packageName1, className);
				intent.setComponent(cn);
				context.startActivity(intent);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static ArrayList<AppInfo> getInstallApp(Context context) {
		ArrayList<AppInfo> appList = new ArrayList<AppInfo>(); // 用来存储获取的应用信息数据
		List<PackageInfo> packages = context.getPackageManager()
				.getInstalledPackages(0);

		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageInfo = packages.get(i);
			AppInfo tmpInfo = new AppInfo();
			tmpInfo.setAppName(packageInfo.applicationInfo.loadLabel(
					context.getPackageManager()).toString());
			tmpInfo.setPackageName(packageInfo.packageName);
			// if((packageInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==0)
			// {
			// }
			appList.add(tmpInfo);
		}
		return appList;
	}

	public static ArrayList<AppInfo> getInstallMyApp(Context context,
			List<AppInfo> mAppList) {
		ArrayList<AppInfo> appList = new ArrayList<AppInfo>(); // 用来存储获取的应用信息数据
		List<PackageInfo> packages = context.getPackageManager()
				.getInstalledPackages(0);

		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageInfo = packages.get(i);
			for (AppInfo mApp : mAppList) {
				if ((packageInfo.packageName).equalsIgnoreCase(mApp
						.getPackageName())
						&& (packageInfo.versionName).equalsIgnoreCase(mApp
								.getPackageVersion())) {
					appList.add(mApp);
				}
			}
		}
		return appList;
	}

	public static boolean isAppInstalled(Context context, String packagename) {
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					packagename, 0);
		} catch (NameNotFoundException e) {
			packageInfo = null;
			e.printStackTrace();
		}
		if (packageInfo == null) {
			// System.out.println("没有安装");
			return false;
		} else {
			// System.out.println("已经安装");
			return true;
		}
	}

	public static List<AppInfo> queryFilterAppInfo(int filter, Context mContext) {
		PackageManager pm = mContext.getPackageManager();
		// 查询所有已经安装的应用程序
		List<PackageInfo> PackageInfos = pm
				.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		List<AppInfo> appInfos = new ArrayList<AppInfo>(); // 保存过滤查到的AppInfo

		// 根据条件来过滤
		switch (filter) {
		case FILTER_ALL_APP: // 所有应用程序
			for (PackageInfo app : PackageInfos) {
				AppInfo appInfo2 = getAppInfo(app, pm);
				appInfos.add(appInfo2);
			}
			break;
		case FILTER_SYSTEM_APP: // 系统程序
			for (PackageInfo app : PackageInfos) {
				if ((app.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
					AppInfo appInfo2 = getAppInfo(app, pm);
					appInfos.add(appInfo2);
				}
			}
			break;
		case FILTER_THIRD_APP: // 第三方应用程序
			for (PackageInfo app : PackageInfos) {
				if ((app.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
					AppInfo appInfo2 = getAppInfo(app, pm);
					appInfos.add(appInfo2);
				}
			}
			break;
		case FILTER_SDCARD_APP: // 安装在SDCard的应用程序
			for (PackageInfo app : PackageInfos) {
				if ((app.applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
					AppInfo appInfo2 = getAppInfo(app, pm);
					appInfos.add(appInfo2);
				}
			}
			break;
		case FILTER_NO_APP: // 仅服务器上的

			break;
		default:
			break;
		}
		return appInfos;
	}

	// 构造一个AppInfo对象 ，并赋值
	private static AppInfo getAppInfo(PackageInfo app, PackageManager pm) {
		AppInfo appInfo = new AppInfo();
		appInfo.setAppName((String) app.applicationInfo.loadLabel(pm));
		appInfo.setPackageName(app.packageName);
		appInfo.setPackageVersion(app.versionName);
		return appInfo;
	}
}
