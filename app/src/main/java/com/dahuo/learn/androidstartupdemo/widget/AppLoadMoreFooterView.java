package com.dahuo.learn.androidstartupdemo.widget;

import android.content.Context;

import com.dahuo.learn.androidstartupdemo.R;
import com.github.captain_miao.recyclerviewutils.common.BaseLoadMoreFooterView;

/**
 * @author YanLu
 * @since 16/4/12
 */
public class AppLoadMoreFooterView extends BaseLoadMoreFooterView{

    public AppLoadMoreFooterView(Context context) {
        super(context);
    }

    @Override
    public int getLoadMoreLayoutResource() {
        return R.layout.list_load_more;
    }
}
