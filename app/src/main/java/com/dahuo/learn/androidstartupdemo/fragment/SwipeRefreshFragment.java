package com.dahuo.learn.androidstartupdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahuo.learn.androidstartupdemo.R;
import com.dahuo.learn.androidstartupdemo.constant.AppConstants;


/**
 * Created on 15/08/01
 */

public class SwipeRefreshFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = SwipeRefreshFragment.class.getSimpleName();
    private String mTitle;
    private TextView mTvTitle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public SwipeRefreshFragment() {
    }


    public static SwipeRefreshFragment newInstance(String title) {
        SwipeRefreshFragment f = new SwipeRefreshFragment();

        Bundle args = new Bundle();

        args.putString(AppConstants.KEY_TITLE, title);
        f.setArguments(args);

        return (f);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mTitle = getArguments().getString(AppConstants.KEY_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_swipe_refresh, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mTvTitle = (TextView) rootView.findViewById(R.id.tv_content);
        mTvTitle.setOnClickListener(this);// for mock
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(!TextUtils.isEmpty(mTitle)){
            mTvTitle.setText(mTitle);
        }
        //设置加载圈圈的颜色
        //mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.line_color_run_speed_1);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.line_color_run_speed_7,
                R.color.line_color_run_speed_9,
                R.color.line_color_run_speed_11);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        //mSwipeRefreshLayout.setEnabled(false);//可以禁止下拉刷新
                    }
                }, 3000);//3秒
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_content:
                break;
        }
    }


}
