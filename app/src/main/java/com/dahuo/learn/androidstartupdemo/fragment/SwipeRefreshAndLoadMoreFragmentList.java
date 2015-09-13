package com.dahuo.learn.androidstartupdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dahuo.learn.androidstartupdemo.R;
import com.dahuo.learn.androidstartupdemo.constant.AppConstants;
import com.dahuo.learn.refresh.BaseLoadMoreRecyclerAdapter;
import com.dahuo.learn.refresh.EndlessRecyclerOnScrollListener;
import com.dahuo.learn.refresh.MaterialProgressBarSupport;

import java.util.ArrayList;
import java.util.List;


/**
 * Created on 15/08/01
 */

public class SwipeRefreshAndLoadMoreFragmentList extends BaseFragment implements View.OnClickListener {
    private static final String TAG = SwipeRefreshAndLoadMoreFragmentList.class.getSimpleName();
    private String mTitle;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static RecyclerView mRecyclerView;
    private List<String> mDataList = new ArrayList<>();
    private SimpleStringRecyclerViewAdapter mAdapter;

    public static SwipeRefreshAndLoadMoreFragmentList newInstance(String title) {
        SwipeRefreshAndLoadMoreFragmentList f = new SwipeRefreshAndLoadMoreFragmentList();

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
        View rootView = inflater.inflate(R.layout.frag_swipe_refresh_list, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView =  (RecyclerView) rootView.findViewById(R.id.recycler_view);
        //mTvLoadMore = inflater.inflate(R.layout.item_view_load_more, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置加载圈圈的颜色
        //mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.line_color_run_speed_1);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.line_color_run_speed_13);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.appendToTop(mTitle + " +" + mAdapter.getItemCount());
                        mAdapter.appendToTop(mTitle + " +" + mAdapter.getItemCount());
                        mAdapter.appendToTop(mTitle + " +" + mAdapter.getItemCount());
                        mAdapter.appendToTop(mTitle + " +" + mAdapter.getItemCount());
                        mAdapter.appendToTop(mTitle + " +" + mAdapter.getItemCount());
                        mAdapter.notifyDataSetChanged();
                    }
                }, 1000);//1秒
            }
        });

        mDataList.add(mTitle);

        mRecyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(getActivity()));
        mAdapter = new SimpleStringRecyclerViewAdapter(getActivity(), mDataList);
        mAdapter.setHasMoreData(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                mAdapter.setHasFooter(true);
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int position = mAdapter.getItemCount();
                        if (mAdapter.getItemCount() > 50) {
                            mAdapter.setHasMoreDataAndFooter(false, true);
                        } else {
                            mAdapter.append(mTitle + " +" + mAdapter.getItemCount());
                            mAdapter.append(mTitle + " +" + mAdapter.getItemCount());
                            mAdapter.append(mTitle + " +" + mAdapter.getItemCount());
                            mAdapter.append(mTitle + " +" + mAdapter.getItemCount());
                            mAdapter.append(mTitle + " +" + mAdapter.getItemCount());
                        }
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.scrollToPosition(position);
                    }
                }, 2000);//2秒
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

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;
        public final TextView mTextView;

        public ItemViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.avatar);
            mTextView = (TextView) view.findViewById(R.id.tv_content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }


    public static class SimpleStringRecyclerViewAdapter
            extends BaseLoadMoreRecyclerAdapter<String, ItemViewHolder> {

        public SimpleStringRecyclerViewAdapter(Context context, List<String> items) {
            appendToList(items);
        }


        @Override
        public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = SwipeRefreshAndLoadMoreFragmentList.mRecyclerView.getChildAdapterPosition(v);
                    remove(itemPosition);
                    notifyItemRemoved(itemPosition);
                }
            });
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(ItemViewHolder holder, int position) {
            holder.mTextView.setText(getItem(position));
            holder.mImageView.setImageResource(R.mipmap.ic_launcher);
        }
    }
}
