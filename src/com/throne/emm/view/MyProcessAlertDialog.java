package com.throne.emm.view;

import com.throne.emm.R;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyProcessAlertDialog {
	Context context;
	android.app.AlertDialog ad;
	TextView tv_dialog_tip;

	public MyProcessAlertDialog(Context context) {
		this.context = context;
		ad = new android.app.AlertDialog.Builder(context).create();
		ad.show();
		Window window = ad.getWindow();
		window.setContentView(R.layout.processdialog);
		initView(window);
	}

	/**
	 * 初始化组件
	 * @date 2014-6-4下午2:12:00 
	 * @version：  V3.0.0327.1
	 */
	private void initView(Window window) {
		tv_dialog_tip = (TextView) window.findViewById(R.id.tv_dialog_tip);
	}

	/**
	 * 设置标题
	 * @date 2014-6-4下午3:09:45 
	 * @version：  V3.0.0327.1
	 */
	public void setTitle(String title) {
		tv_dialog_tip.setText(title);
	}

	/**
	 * 是否可以被取消
	 * @param true:可以返回键退出，false:禁止退出
	 * @date 2014-6-4下午3:32:09 
	 * @version：  V3.0.0327.1
	 */
	public void setCancelable(boolean cancelable) {
		ad.setCancelable(cancelable);
	}

	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		ad.dismiss();
	}
	public void setTitleTextColor(int color) {
		tv_dialog_tip.setTextColor(color);
	}
}