package com.dahuo.learn.weather.setting.ui;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dahuo.learn.startup.R;
import com.dahuo.learn.weather.ACache;
import com.dahuo.learn.weather.setting.Setting;

/**
 * Created by hugo on 2016/2/19 0019.
 * todo 设置 点击是否展示 dialog
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private static String TAG = SettingFragment.class.getSimpleName();
    //private SettingActivity mActivity;
    private Setting mSetting;
    private Preference mChangeUpdate;
    private SwitchPreference mNotificationType;

    private ACache mACache;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        mSetting = Setting.getInstance();
        mACache = ACache.get(getActivity());

        mChangeUpdate = findPreference(Setting.AUTO_UPDATE);

        mNotificationType = (SwitchPreference) findPreference(Setting.NOTIFICATION_MODEL);

        mChangeUpdate.setSummary(mSetting.getAutoUpdate() == 0 ? "禁止刷新" : "每" + mSetting.getAutoUpdate() + "小时更新");


        mChangeUpdate.setOnPreferenceClickListener(this);
        mNotificationType.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
         if (mChangeUpdate == preference) {
            showUpdateDialog();
        } else if (mNotificationType == preference) {
            mNotificationType.setChecked(mNotificationType.isChecked());
            mSetting.setNotificationModel(
                mNotificationType.isChecked() ? Notification.FLAG_AUTO_CANCEL : Notification.FLAG_ONGOING_EVENT);
            Log.i(TAG, mSetting.getAutoUpdate() + "");
        }
        return false;
    }


    private void showUpdateDialog() {
        //将 SeekBar 放入 Dialog 的方案 http://stackoverflow.com/questions/7184104/how-do-i-put-a-seek-bar-in-an-alert-dialog
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogLayout = inflater.inflate(R.layout.update_dialog, (ViewGroup) getActivity().findViewById(
            R.id.dialog_root));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
            .setView(dialogLayout);
        final AlertDialog alertDialog = builder.create();

        final SeekBar mSeekBar = (SeekBar) dialogLayout.findViewById(R.id.time_seekbar);
        final TextView tvShowHour = (TextView) dialogLayout.findViewById(R.id.tv_showhour);
        TextView tvDone = (TextView) dialogLayout.findViewById(R.id.done);

        mSeekBar.setMax(24);
        mSeekBar.setProgress(mSetting.getAutoUpdate());
        tvShowHour.setText(String.format("每%s小时",mSeekBar.getProgress()));
        alertDialog.show();


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvShowHour.setText(String.format("每%s小时",mSeekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetting.setAutoUpdate(mSeekBar.getProgress());
                mChangeUpdate.setSummary(mSetting.getAutoUpdate() == 0 ? "禁止刷新" : "每" + mSetting.getAutoUpdate() + "小时更新");
                //需要再调用一次才能生效设置 不会重复的执行onCreate()， 而是会调用onStart()和onStartCommand()。
                //getActivity().startService(new Intent(getActivity(), AutoUpdateService.class));
                alertDialog.dismiss();

            }
        });
    }
}
