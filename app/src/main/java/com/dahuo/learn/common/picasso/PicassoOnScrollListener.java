package com.dahuo.learn.common.picasso;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

/**
 * @author YanLu
 * @since 16/5/23
 */
public class PicassoOnScrollListener extends RecyclerView.OnScrollListener {
    public static final Object TAG = new Object();
    private static final int SETTLING_DELAY = 500;
    private int SCROLL_THRESHOLD = 500;

    private static Picasso sPicasso = null;
    private Runnable mSettlingResumeRunnable = null;

    public PicassoOnScrollListener(Context context) {
        if(sPicasso == null) {
            sPicasso = Picasso.with(context.getApplicationContext());
            SCROLL_THRESHOLD = context.getResources().getDisplayMetrics().heightPixels;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
        if(scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            recyclerView.removeCallbacks(mSettlingResumeRunnable);
            sPicasso.resumeTag(TAG);

        } else if(scrollState == RecyclerView.SCROLL_STATE_SETTLING) {
            mSettlingResumeRunnable = new Runnable() {
                @Override
                public void run() {
                    sPicasso.resumeTag(TAG);
                }
            };

            recyclerView.postDelayed(mSettlingResumeRunnable, SETTLING_DELAY);

        }
        //else {
        //    sPicasso.pauseTag(TAG);
        //}
    }



    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (Math.abs(dy) > SCROLL_THRESHOLD) {
            sPicasso.pauseTag(TAG);
        }
//        else {
//            sPicasso.resumeTag(TAG);
//        }
    }

    public static Picasso getPicasso() {
        return sPicasso;
    }


    @BindingAdapter({"compressImageUrl"})
    public static void loadImageCompress(ImageView imageView, String url) {
        Picasso.with(imageView.getContext().getApplicationContext())
                .load(url)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                //.placeholder(R.drawable.ic_image_load_place_holder)
                .config(Bitmap.Config.RGB_565)
                .tag(PicassoOnScrollListener.TAG)
                .into(imageView);
    }
}
