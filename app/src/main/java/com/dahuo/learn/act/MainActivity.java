package com.dahuo.learn.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.dahuo.learn.agera.SimpleObservable;
import com.dahuo.learn.model.HeWeatherInfo;
import com.dahuo.learn.startup.R;
import com.dahuo.learn.startup.databinding.ActMainBinding;
import com.dahuo.learn.supplier.WeatherSupplier;
import com.dahuo.learn.weather.WeatherInfoAdapter;
import com.github.captain_miao.recyclerviewutils.WrapperRecyclerView;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Updatable, RefreshRecyclerViewListener {
    private DrawerLayout mDrawerLayout;
    private ActMainBinding mDataBinding;

    private WeatherInfoAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.act_main);
        setSupportActionBar(mDataBinding.toolbar);
        mDrawerLayout = mDataBinding.drawerLayout;

        //for navigation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mDataBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mDataBinding.navView.setNavigationItemSelectedListener(this);


        WrapperRecyclerView refreshRecyclerView = mDataBinding.refreshRecyclerView;
        mAdapter = new WeatherInfoAdapter();
        refreshRecyclerView.setAdapter(mAdapter);

        refreshRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshRecyclerView.setRecyclerViewListener(this);
        refreshRecyclerView.setPadding(0, 0, 0, 20);




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

        //mDrawerLayout.closeDrawer(GravityCompat.START);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            //startActivity(new Intent(this, ThreadActivity.class));
        } else if (id == R.id.nav_gallery) {
            //startActivity(new Intent(this, SimpleAsyncTaskActivity.class));
        } else if (id == R.id.nav_slideshow) {
            //startActivity(new Intent(this, SimpleIntentServiceActivity.class));
        } else if (id == R.id.nav_manage) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        return true;
    }

    @Override
    public void update() {
        if(mRepository.get().isPresent()) {
            mAdapter.clear();
            mAdapter.addAll(mRepository.get().get());
            //mAdapter.notifyDataSetChanged();
            //mDataBinding.setWeather(mRepository.get().get().get(0).status);
        }
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
