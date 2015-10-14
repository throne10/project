package com.throne.emm.net.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.throne.emm.R;
import com.throne.emm.Fragment.AppListAdapter.AppDownloadAjaxCallBack;
import com.throne.emm.Fragment.AppListAdapter.ViewHolder;
import com.throne.emm.common.utils.AppManageHelper;
import com.throne.emm.constant.CommonConstants;
import com.throne.emm.model.AppInfo;

public class AppDownloadUtil {
	public static Map<String, AppDownloadAjaxCallBack> mDownloadMap=new HashMap<String, AppDownloadAjaxCallBack>();
	public static void appDownload(final ViewHolder holder, final AppInfo app, AppDownloadAjaxCallBack appDownloadAjaxCallBack){
		File f=new File(CommonConstants.MDMPATH);
		if(!f.exists())
		{
			f.mkdir();
		}
		mDownloadMap.put(app.getPackageName(), appDownloadAjaxCallBack);
		FinalHttp fh = new FinalHttp();  
		//调用download方法开始下载
		HttpHandler<File> handler = fh.download(CommonConstants.getFullUrl(app.getPkgFilePath()), //这里是下载的路径
				CommonConstants.getAppTmpPath(app.getPackageName()), //这是保存到本地的路径
				true,//true:断点续传 false:不断点续传（全新下载）
				appDownloadAjaxCallBack);  
		holder.iv_app_cancel.setTag(R.id.tag_downhandler, handler);
		appDownloadAjaxCallBack.setHandler(handler);

	}
}
