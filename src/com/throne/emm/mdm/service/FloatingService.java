package com.throne.emm.mdm.service;

import com.throne.emm.R;
import com.throne.emm.common.utils.StringUtils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FloatingService extends Service {

    private View view;// 窗体  
    private Button openBtn;
    private EditText passwordEdt;

    private boolean viewAdded = false;
    private WindowManager wm;
    private WindowManager.LayoutParams wmParams;

    private static String managerPwd = "123456";

    @Override
    public void onCreate() {
        super.onCreate();

        ViewGroup vg = null;
        view = LayoutInflater.from(this).inflate(R.layout.floatingview, vg);

        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
        wmParams.type = 2002;
        wmParams.flags = LayoutParams.FLAG_FULLSCREEN;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角   

        wmParams.x = 0;
        wmParams.y = 0;

        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.format = 1;

        passwordEdt = (EditText) view.findViewById(R.id.float_password_edt);
        openBtn = (Button) view.findViewById(R.id.float_open_btn);
        openBtn.setText(R.string.uninstall_mdm_btn2);
        openBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = passwordEdt.getText().toString();
                pwd = pwd != null ? pwd.trim() : StringUtils.EMPTY;

                // FIXME MDM4.0 管理密码和后台联动动态生成
                if (pwd.equals(getManagerPwd())) {
                    removeView();

                    stopSelf();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.uninstall_mdm_pwd_error, Toast.LENGTH_SHORT)
                    .show();
                }
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        refresh();
        return super.onStartCommand(intent, flags, startId);
    }

    /** 
     * 添加悬浮窗或者更新悬浮窗 如果悬浮窗还没添加则添加 如果已经添加则更新其位置 
     */
    private void refresh() {

        if (viewAdded) {
            wm.updateViewLayout(view, wmParams);
        } else {
            wm.addView(view, wmParams);
            viewAdded = true;
        }

        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /** 
     * 关闭悬浮窗 
     */
    private void removeView() {
        if (viewAdded) {
            wm.removeView(view);
            viewAdded = false;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static String getManagerPwd() {
        return managerPwd;
    }

    public static void setManagerPwd(String managerPwd) {
        FloatingService.managerPwd = managerPwd;
    }
}
