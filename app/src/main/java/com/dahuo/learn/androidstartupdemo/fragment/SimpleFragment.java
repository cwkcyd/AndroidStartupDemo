package com.dahuo.learn.androidstartupdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahuo.learn.androidstartupdemo.R;
import com.dahuo.learn.androidstartupdemo.constant.AppConstants;


/**
 * Created on 15/08/01
 */

public class SimpleFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = SimpleFragment.class.getSimpleName();
    private String mTitle;
    private TextView mTvTitle;

    public SimpleFragment() {
    }


    public static SimpleFragment newInstance(String title) {
        SimpleFragment f = new SimpleFragment();

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
        View rootView = inflater.inflate(R.layout.frag_simple, null);
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
        mHandler.sendEmptyMessageDelayed(100, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_content:
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage:" + msg.what);
        }
    };

}
