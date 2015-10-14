package com.throne.emm.admin;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;

import com.throne.emm.R;
import com.throne.emm.mdm.service.FloatingService;

public class SampleAdmin extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        // 启动服务
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        // 启动防止取消激活功能
        Intent startFloat = new Intent(context, FloatingService.class);
        context.startService(startFloat);
        return context.getString(R.string.disable_warning);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
    }

    @Override
    public void onPasswordExpiring(Context context, Intent intent) {
        super.onPasswordExpiring(context, intent);
    }
}
