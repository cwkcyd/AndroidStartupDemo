package com.dahuo.learn.androidstartupdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dahuo.learn.androidstartupdemo.R;
import com.github.captain_miao.recyclerviewutils.BaseWrapperRecyclerAdapter;
import com.github.captain_miao.recyclerviewutils.common.ClickableViewHolder;
import com.github.captain_miao.recyclerviewutils.listener.OnRecyclerItemClickListener;

import java.util.List;

/**
 * @author YanLu
 * @since 16/4/12
 */
public class SimpleAdapter extends BaseWrapperRecyclerAdapter<String, RecyclerView.ViewHolder>{

    private OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        mOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public SimpleAdapter(List<String> items, OnRecyclerItemClickListener itemClickListener) {
        this.mOnRecyclerItemClickListener = itemClickListener;
        addAll(items);
    }
    public SimpleAdapter(List<String> items) {
        addAll(items);
    }

    @Override
    public SimpleAdapter.ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(view, mOnRecyclerItemClickListener);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).mTextView.setText(getItem(position));
            ((ItemViewHolder) holder).mImageView.setImageResource(R.mipmap.ic_launcher);
        }
    }


    public static class ItemViewHolder extends ClickableViewHolder {
        public final ImageView mImageView;
        public final TextView mTextView;

        public ItemViewHolder(View view) {
            this(view, null);
        }
        public ItemViewHolder(View view, OnRecyclerItemClickListener itemClickListener) {
            super(view);
            if(itemClickListener != null) {
                setOnRecyclerItemClickListener(itemClickListener);
                addOnItemViewClickListener();
            }
            mImageView = (ImageView) view.findViewById(R.id.avatar);
            mTextView = (TextView) view.findViewById(R.id.tv_content);
        }

    }
}
