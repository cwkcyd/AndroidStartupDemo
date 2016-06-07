package com.dahuo.learn.act;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dahuo.learn.agera.SimpleObservable;
import com.dahuo.learn.model.HeWeatherInfo;
import com.dahuo.learn.startup.R;
import com.dahuo.learn.startup.databinding.ActMainBinding;
import com.dahuo.learn.supplier.WeatherSupplier;
import com.dahuo.learn.weather.WeatherUtil;
import com.dahuo.learn.weather.city.ui.ChoiceCityActivity;
import com.dahuo.learn.weather.setting.ui.SettingActivity;
import com.github.captain_miao.recyclerviewutils.listener.RefreshRecyclerViewListener;
import com.google.android.agera.Function;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.RepositoryConfig;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Updatable, RefreshRecyclerViewListener {
    private DrawerLayout mDrawerLayout;
    private ActMainBinding mDataBinding;
    private PtrClassicFrameLayout mPtrFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.act_main);
        setSupportActionBar(mDataBinding.toolbar);
        mDrawerLayout = mDataBinding.drawerLayout;

        //for navigation
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mDataBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mDataBinding.navView.setNavigationItemSelectedListener(this);

        mPtrFrameLayout = mDataBinding.ptrFrame;

        mPtrFrameLayout.setEnabledNextPtrAtOnce(true);
        // header
//        final RentalsSunHeaderView header = new RentalsSunHeaderView(this);
//        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
//        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
//        header.setUp(mPtrFrameLayout);
        //final StoreHouseHeader header = new StoreHouseHeader(this);
        //header.setTextColor(R.color.colorAccent);
        //header.setPadding(0, getResources().getDimensionPixelSize(R.dimen.fab_margin), 0, 0);
        //header.initWithString("running");
//        mPtrFrameLayout.setLoadingMinTime(1000);
//        mPtrFrameLayout.setDurationToCloseHeader(1500);
//        mPtrFrameLayout.setHeaderView(header);
//        mPtrFrameLayout.addPtrUIHandler(header);
//        mPtrFrameLayout.setPullToRefresh(true);

        //mPtrFrame = (PtrClassicFrameLayout) contentView.findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrameLayout.setLastUpdateTimeRelateObject(this);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mObservable.update();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

//        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return true;
//            }
//
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                mObservable.update();
//            }
//        });


//        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                mObservable.update();
//            }
//        });
        setUpRepository();
        mRepository.addUpdatable(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRepository.removeUpdatable(this);
        networkExecutor.shutdown();
    }


    //for agera
    private ExecutorService networkExecutor;
    private SimpleObservable mObservable;
    private Repository<Result<List<HeWeatherInfo>>> mRepository;

    private void setUpRepository() {
        networkExecutor = Executors.newSingleThreadExecutor();
        mObservable = new SimpleObservable();

        mRepository = Repositories.repositoryWithInitialValue(Result.<List<HeWeatherInfo>>absent())
                .observe(mObservable)
                .onUpdatesPerLoop()
                .goTo(networkExecutor)
                .getFrom(new WeatherSupplier(new Supplier<String>() {
                    @NonNull
                    @Override
                    public String get() {
                        return "beijing";
                    }
                }))
                .thenTransform(new Function<Result<List<HeWeatherInfo>>, Result<List<HeWeatherInfo>>>() {
                    @NonNull
                    @Override
                    public Result<List<HeWeatherInfo>> apply(@NonNull Result<List<HeWeatherInfo>> input) {
                        return input;
                    }
                })
                .onDeactivation(RepositoryConfig.SEND_INTERRUPT)
                .compile();
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // mDrawerLayout.closeDrawer(GravityCompat.START);
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id) {
            case R.id.nav_set:
                Intent intentSetting = new Intent(MainActivity.this, SettingActivity.class);
                intentSetting.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentSetting);
                break;
            case R.id.nav_city:
                 startActivityForResult(new Intent(this, ChoiceCityActivity.class), 1);
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }

    @Override
    public void update() {
        if(mRepository.get().isPresent()) {
            // 只显示 一个城市的天气
            HeWeatherInfo weatherInfo = mRepository.get().get().get(0);
            mDataBinding.setWeather(weatherInfo);
            mDataBinding.setImgRes(WeatherUtil.getWeatherIcon(weatherInfo.now.cond.txt));
        }
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void onRefresh() {
        mObservable.update();
    }

    @Override
    public void onLoadMore(int pagination, int pageSize) {
        // do nothing
    }
}
