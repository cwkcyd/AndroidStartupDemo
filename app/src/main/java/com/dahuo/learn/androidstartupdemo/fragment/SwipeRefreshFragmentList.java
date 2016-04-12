package com.dahuo.learn.androidstartupdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dahuo.learn.androidstartupdemo.R;
import com.dahuo.learn.androidstartupdemo.adapter.SimpleAdapter;
import com.dahuo.learn.androidstartupdemo.constant.AppConstants;
import com.dahuo.learn.androidstartupdemo.widget.AppLoadMoreFooterView;
import com.github.captain_miao.recyclerviewutils.WrapperRecyclerView;
import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;
import com.github.captain_miao.recyclerviewutils.listener.RefreshRecyclerViewListener;

import java.util.ArrayList;




/**
 * Created on 15/08/01
 */

public class SwipeRefreshFragmentList extends BaseFragment implements RefreshRecyclerViewListener, OnRecyclerItemClickListener {
    private static final String TAG = SwipeRefreshFragmentList.class.getSimpleName();
    private String mTitle;

    private SimpleAdapter mAdapter;
    private WrapperRecyclerView mWrapperRecyclerView;
    public static SwipeRefreshFragmentList newInstance(String title) {
        SwipeRefreshFragmentList f = new SwipeRefreshFragmentList();

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
        View rootView = inflater.inflate(R.layout.frag_swipe_refresh_list, container, false);
        mWrapperRecyclerView = (WrapperRecyclerView) rootView.findViewById(R.id.recycler_view);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mWrapperRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new SimpleAdapter(new ArrayList<String>(), this);
        mAdapter.setLoadMoreFooterView(new AppLoadMoreFooterView(getActivity()));
        mWrapperRecyclerView.setAdapter(mAdapter);

        mWrapperRecyclerView.setRecyclerViewListener(this);

        mWrapperRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mWrapperRecyclerView.autoRefresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        mWrapperRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWrapperRecyclerView.refreshComplete();
                mAdapter.add(mTitle + " +" + mAdapter.getItemCount(), false);
                mAdapter.add(mTitle + " +" + mAdapter.getItemCount(), false);
                mAdapter.add(mTitle + " +" + mAdapter.getItemCount(), false);
                mAdapter.add(mTitle + " +" + mAdapter.getItemCount(), false);
                mAdapter.add(mTitle + " +" + mAdapter.getItemCount(), false);
                mAdapter.notifyDataSetChanged();
            }
        }, 1000);//1秒
    }

    @Override
    public void onLoadMore(int pagination, int pageSize) {
        mAdapter.showLoadMoreView();
        mWrapperRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int position = mAdapter.getItemCount();
                if (mAdapter.getItemCount() > 50) {
                    mAdapter.showNoMoreDataView();
                } else {
                    mAdapter.add(mTitle + " +" + mAdapter.getItemCount(), false);
                    mAdapter.add(mTitle + " +" + mAdapter.getItemCount(), false);
                    mAdapter.add(mTitle + " +" + mAdapter.getItemCount(), false);
                    mAdapter.add(mTitle + " +" + mAdapter.getItemCount(), false);
                    mAdapter.add(mTitle + " +" + mAdapter.getItemCount(), false);
                    mAdapter.hideFooterView();
                    mAdapter.notifyDataSetChanged();
                    mWrapperRecyclerView.getRecyclerView().scrollToPosition(position);
                }
            }
        }, 2000);//2秒
    }

    @Override
    public void onClick(View v, int position) {
        Toast.makeText(v.getContext(), "on click " + position, Toast.LENGTH_SHORT).show();
    }
}
