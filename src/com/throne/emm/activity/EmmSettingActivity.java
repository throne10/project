package com.throne.emm.activity;

import com.throne.emm.R;
import com.throne.emm.common.utils.AppManageHelper;
import com.throne.emm.constant.CommonConstants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class EmmSettingActivity extends Activity {
    private Context mContext;
    private Intent mIntent;
    private String mString;
    private ImageView iv_intruduction;
    private Button bt_Settingstart;
    private int mInt;
    private LinearLayout ll_intruduction;
    private LinearLayout ll_clean;
    private Button bt_clean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_settting);
        mContext = this;
        initView();
        initData();
    }

    private void initData() {
        mIntent = getIntent();
        if (mIntent.hasExtra(CommonConstants.INTENT_PACKAGE_NAME) && mIntent.hasExtra(CommonConstants.INTENT_TYPE)) {
            mString = mIntent.getStringExtra(CommonConstants.INTENT_PACKAGE_NAME);
            mInt = mIntent.getIntExtra(CommonConstants.INTENT_TYPE, -1);
        }
        switch (mInt) {
        case CommonConstants.SETTING_LAUNCHER:
            ll_intruduction.setVisibility(View.VISIBLE);
            ll_clean.setVisibility(View.GONE);
            break;
        case CommonConstants.CLEAN_LAUNCHER:
            ll_intruduction.setVisibility(View.GONE);
            ll_clean.setVisibility(View.VISIBLE);
            break;
        }
        bt_Settingstart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_MAIN);
                it.addCategory(Intent.CATEGORY_HOME);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(it);
                finish();
            }
        });
        bt_clean.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManageHelper.showInstalledAppDetails(mContext, mString);
                finish();
            }
        });
    }

    private void initView() {
        ll_clean = (LinearLayout) this.findViewById(R.id.ll_clean);
        ll_intruduction = (LinearLayout) this.findViewById(R.id.ll_intruduction);
        iv_intruduction = (ImageView) this.findViewById(R.id.iv_intruduction);
        bt_Settingstart = (Button) this.findViewById(R.id.bt_Settingstart);
        bt_clean = (Button) this.findViewById(R.id.bt_clean);
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
}
