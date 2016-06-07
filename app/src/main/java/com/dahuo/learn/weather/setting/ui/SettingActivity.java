package com.dahuo.learn.weather.setting.ui;

import android.os.Bundle;

import com.dahuo.learn.base.BaseActivity;
import com.dahuo.learn.startup.R;


/**
 * Created by hugo on 2016/2/19 0019.
 */
public class SettingActivity extends BaseActivity {

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.act_setting);

        getFragmentManager().beginTransaction().replace(R.id.framelayout, new SettingFragment()).commit();
    }
}
