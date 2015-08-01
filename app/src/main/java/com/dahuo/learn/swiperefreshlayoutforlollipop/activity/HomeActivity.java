package com.dahuo.learn.swiperefreshlayoutforlollipop.activity;

import android.os.Bundle;

import com.dahuo.learn.swiperefreshlayoutforlollipop.R;
import com.dahuo.learn.swiperefreshlayoutforlollipop.fragment.SimpleFragment;
import com.dahuo.learn.swiperefreshlayoutforlollipop.fragment.SwipeRefreshFragment;
import com.dahuo.learn.swiperefreshlayoutforlollipop.fragment.SwipeRefreshFragmentList;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

/**
 * Created on 15/08/01.
 */
public class HomeActivity extends MaterialNavigationDrawer {
    @Override
    public void init(Bundle bundle) {



        MaterialSection homeSection =
                newSection(getString(R.string.app_home), R.mipmap.ic_launcher,
                        SwipeRefreshFragment.newInstance(getString(R.string.app_home)));
        MaterialSection scheduleSection =
                newSection(getString(R.string.app_plan), R.mipmap.ic_launcher,
                        SwipeRefreshFragmentList.newInstance(getString(R.string.app_plan)));
        MaterialSection historySection =
                newSection(getString(R.string.app_history), R.mipmap.ic_launcher,
                        SimpleFragment.newInstance(getString(R.string.app_history)));
        MaterialSection deviceSection =
                newSection(getString(R.string.app_device), R.mipmap.ic_launcher,
                        SimpleFragment.newInstance(getString(R.string.app_device)));
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
        allowArrowAnimation();
        enableToolbarElevation();
    }



}
