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
import com.dahuo.learn.view.MaterialProgressBarSupport;

import java.util.ArrayList;
import java.util.List;


/**
 * Created on 15/08/01
 */

public class SwipeRefreshFragmentList extends BaseFragment implements View.OnClickListener {
    private static final String TAG = SwipeRefreshFragmentList.class.getSimpleName();
    private String mTitle;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static RecyclerView mRecyclerView;
    private List<String> mDataList = new ArrayList<>();
    private SimpleStringRecyclerViewAdapter mAdapter;

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
                        mDataList.add(0, mTitle + " +" + mDataList.size());
                        mDataList.add(0, mTitle + " +" + mDataList.size());
                        mDataList.add(0, mTitle + " +" + mDataList.size());
                        mDataList.add(0, mTitle + " +" + mDataList.size());
                        mDataList.add(0, mTitle + " +" + mDataList.size());
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                }, 1000);//1秒
            }
        });

        mDataList.add(mTitle);

        mRecyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(getActivity()));
        mAdapter = new SimpleStringRecyclerViewAdapter(getActivity(), mDataList);
        mAdapter.setHassMoreData(true);
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isPullUp = false;
            boolean isRefreshing = false;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i(TAG, "dy = " + dy);
                isPullUp = dy > 0;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //super.onScrollStateChanged(recyclerView, newState);
                if(isPullUp && !isRefreshing && mAdapter.hasMoreData()) {
                    int lastPos = mLinearLayoutManager.findLastVisibleItemPosition();
                    if (lastPos > mDataList.size() - 2) {//最后一个位置的时候加载更多
                        mAdapter.setHasFooter(true);
                        isRefreshing = true;
                        mSwipeRefreshLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //mSwipeRefreshLayout.setRefreshing(false);
                                //mTvLoadMore.setVisibility(View.GONE);
                                isRefreshing = false;
                                int position = mDataList.size();
                                mDataList.add(mTitle + " -" + mDataList.size());
                                mDataList.add(mTitle + " -" + mDataList.size());
                                mDataList.add(mTitle + " -" + mDataList.size());
                                mDataList.add(mTitle + " -" + mDataList.size());
                                mDataList.add(mTitle + " -" + mDataList.size());
                                if(mDataList.size() > 50){
                                    mAdapter.setHasMoreDataAndFooter(false, true);
                                }
                                //mAdapter.setHasFooter(false);
                                //mRecyclerView.getAdapter().notifyDataSetChanged();
                                mRecyclerView.scrollToPosition(position);
                            }
                        }, 1000);//1秒
                    }
                }
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




    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter {
        private static final int TYPE_HEADER = Integer.MIN_VALUE;
        private static final int TYPE_FOOTER = Integer.MIN_VALUE + 1;
        private static final int TYPE_ADAPTEE_OFFSET = 2;
        private List<String> mValues;
        private boolean hasFooter;

        private boolean hasMoreData;

        public int getBasicItemCount() {
            return mValues.size();
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
        public static class FooterViewHolder extends RecyclerView.ViewHolder {
            public final MaterialProgressBarSupport mProgressView;
            public final TextView mTextView;

            public FooterViewHolder(View view) {
                super(view);
                mProgressView = (MaterialProgressBarSupport) view.findViewById(R.id.progress_view);
                mTextView = (TextView) view.findViewById(R.id.tv_content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public String getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<String> items) {
            mValues = items;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_FOOTER){
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_view_load_more, parent, false);
                return new FooterViewHolder(view);
            } else {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int itemPosition = SwipeRefreshFragmentList.mRecyclerView.getChildAdapterPosition(v);
                        mValues.remove(itemPosition);
                        notifyItemRemoved(itemPosition);
                    }
                });
                return new ItemViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof ItemViewHolder) {
                ((ItemViewHolder)holder).mTextView.setText(mValues.get(position));
                ((ItemViewHolder)holder).mImageView.setImageResource(R.mipmap.ic_launcher);
            } else {
                //没有更多数据
                if(hasMoreData){
                    ((FooterViewHolder) holder).mProgressView.setVisibility(View.VISIBLE);
                    ((FooterViewHolder) holder).mProgressView.startProgress();
                    //((FooterViewHolder) holder).mProgressView.setIndeterminate(true);
                    ((FooterViewHolder) holder).mTextView.setText(R.string.app_loading_more);
                } else {
                    ((FooterViewHolder) holder).mProgressView.stopProgress();
                    ((FooterViewHolder) holder).mProgressView.setVisibility(View.GONE);
                    //((FooterViewHolder) holder).mProgressView.st;
                    ((FooterViewHolder) holder).mTextView.setText("没有更多数据了");
                }
            }
        }

        @Override
        public int getItemCount() {
            return mValues.size() + (hasFooter ? 1 : 0);
        }

        @Override
        public int getItemViewType(int position) {

            if (position == getBasicItemCount() && hasFooter) {
                return TYPE_FOOTER;
            }
            return super.getItemViewType(position);
        }


        public boolean hasFooter() {
            return hasFooter;
        }

        public void setHasFooter(boolean hasFooter) {
            if(this.hasFooter != hasFooter) {
                this.hasFooter = hasFooter;
                notifyDataSetChanged();
            }
        }


        public boolean hasMoreData() {
            return hasMoreData;
        }

        public void setHassMoreData(boolean isMoreData) {
            if(this.hasMoreData != isMoreData) {
                this.hasMoreData = isMoreData;
                notifyDataSetChanged();
            }
        }
        public void setHasMoreDataAndFooter(boolean hasMoreData, boolean hasFooter) {
            if(this.hasMoreData != hasMoreData || this.hasFooter != hasFooter) {
                this.hasMoreData = hasMoreData;
                this.hasFooter = hasFooter;
                notifyDataSetChanged();
            }
        }
    }
}
