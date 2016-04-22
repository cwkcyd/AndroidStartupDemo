package com.dahuo.learn.androidstartupdemo.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.dahuo.learn.androidstartupdemo.R;
import com.dahuo.learn.androidstartupdemo.fragment.SimpleFragment;
import com.dahuo.learn.androidstartupdemo.fragment.SwipeRefreshAndLoadMoreFragmentGridView;
import com.dahuo.learn.androidstartupdemo.fragment.SwipeRefreshFragment;
import com.dahuo.learn.androidstartupdemo.fragment.SwipeRefreshFragmentList;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

/**
 * Created on 15/08/01.
 */
public class HomeActivity extends MaterialNavigationDrawer {
    @Override
    public void init(Bundle bundle) {
        //KitKat translucent modes
        //setTranslucentStatus(this, true);
        setDrawerHeaderImage(R.drawable.rocket);
        MaterialSection homeSection =
                newSection(getString(R.string.app_home), R.mipmap.ic_launcher,
                        SwipeRefreshFragment.newInstance(getString(R.string.app_home)));
        MaterialSection scheduleSection =
                newSection(getString(R.string.app_plan), R.mipmap.ic_launcher,
                        SwipeRefreshFragmentList.newInstance(getString(R.string.app_plan)));
        MaterialSection historySection =
                newSection(getString(R.string.app_history), R.mipmap.ic_launcher,
                        new Intent(this, SimpleActivity.class));
        MaterialSection deviceSection =
                newSection(getString(R.string.app_device), R.mipmap.ic_launcher,
                        SwipeRefreshAndLoadMoreFragmentGridView.newInstance(getString(R.string.app_device)));
        MaterialSection userSection =
                newSection(getString(R.string.app_user_info), R.mipmap.ic_launcher,
                        SimpleFragment.newInstance(getString(R.string.app_user_info)));


        addSection(homeSection);
        addSection(scheduleSection);
        addSection(historySection);
        addSection(deviceSection);
        addSection(userSection);

        disableLearningPattern();
        // add pattern
        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_BACK_TO_FIRST);
        //allowArrowAnimation();
        enableToolbarElevation();
    }



    @TargetApi(19)
   	public void setTranslucentStatus(Activity activity, boolean on) {
   		if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
   			Window win = activity.getWindow();
   			WindowManager.LayoutParams winParams = win.getAttributes();
   			final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
   			if (on) {
   				winParams.flags |= bits;
   			} else {
   				winParams.flags &= ~bits;
   			}
   			win.setAttributes(winParams);

   			SystemBarTintManager tintManager = new SystemBarTintManager(activity);
   			tintManager.setStatusBarTintEnabled(true);
   			tintManager.setNavigationBarTintEnabled(false);
   			tintManager.setStatusBarTintColor(activity.getResources().getColor(R.color.colorPrimary));
   			//tintManager.setNavigationBarTintColor(activity.getResources().getColor(R.color.colorPrimary));
   //			tintManager.setStatusBarTintResource(R.color.colorPrimary);
   		}
   	}

}
