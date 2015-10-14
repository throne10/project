package com.throne.emm.common.utils;

import com.throne.emm.admin.SampleAdmin;
import com.throne.emm.mdm.service.ManageService;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MdmUtils {
    /**
     * 启动管理服务
     * @param mContext
     */
    public static void startManageService(Context mContext) {
        Intent mIntent = new Intent(mContext, ManageService.class);
        mContext.startService(mIntent);
    }

    /**
     * 增加设备管理器
     * @param context
     * @return
     */
    public static boolean addAdmin(Context context) {
        boolean isAdmin = false;
        if (isAdminActive(context)) {
            isAdmin = true;
        } else {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(context, SampleAdmin.class));
            context.startActivity(intent);
            Toast.makeText(context, "为了更好的保护您的设备，请激活设备管理器", Toast.LENGTH_LONG).show();
        }

        return isAdmin;
    }

    /**
     * 设备管理器是否激活
     * @param context
     * @return
     */
    public static boolean isAdminActive(Context context) {
        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName cname = new ComponentName(context, SampleAdmin.class);

        return dpm != null && dpm.isAdminActive(cname);
    }
}
