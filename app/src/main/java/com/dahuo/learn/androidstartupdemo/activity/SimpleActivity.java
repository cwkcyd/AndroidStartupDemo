package com.dahuo.learn.androidstartupdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.dahuo.learn.androidstartupdemo.R;

/**
 * @author YanLu
 * @since 16/4/22
 */
public class SimpleActivity extends BaseActivity {

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_simple);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onClickMe(View view) {
        Toast.makeText(this, "click me", Toast.LENGTH_LONG).show();
    }
}
