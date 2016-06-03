package com.dahuo.learn.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.dahuo.learn.act.MainActivity;
import com.dahuo.learn.startup.R;
import com.dahuo.learn.startup.databinding.ActSplashBinding;

import java.util.Random;


/**
 * Tips:App启动页面
 * 该页面不要继承AppCompatActivity
 * 会导致界面启动卡顿
 * AppCompatActivity会默认去加载主题的原因.
 * <p/>
 * 启动界面图片资源来自专栏App,感谢分享~
 */
public class SplashActivity extends Activity {

    private static final int ANIMATION_TIME = 2500;

    private static final float SCALE_END = 1.13F;

    private static final int[] IMAGES = {
            R.drawable.splash0,
            R.drawable.splash1,
            R.drawable.splash2,
            R.drawable.splash3,
            R.drawable.splash4,
            R.drawable.splash5,
            R.drawable.splash6,
            R.drawable.splash7,
            R.drawable.splash8,
            R.drawable.splash9,
            R.drawable.splash10,
            R.drawable.splash11,
            R.drawable.splash12,
            R.drawable.splash13,
            R.drawable.splash14,
            R.drawable.splash15,
            R.drawable.splash16,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActSplashBinding binding = DataBindingUtil.setContentView(this, R.layout.act_splash);

        Random random = new Random(SystemClock.elapsedRealtime());
        binding.setImageRes(IMAGES[random.nextInt(IMAGES.length)]);
        startAnim(binding.ivSplash);
    }

    private void startAnim(View splashImage) {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(splashImage, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(splashImage, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_TIME).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
                SplashActivity.this.overridePendingTransition(0, 0);
            }
        });
    }
}
