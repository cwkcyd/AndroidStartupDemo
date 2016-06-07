package com.dahuo.learn.weather.city.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.dahuo.learn.agera.SimpleObservable;
import com.dahuo.learn.base.BaseActivity;
import com.dahuo.learn.startup.R;
import com.dahuo.learn.weather.ACache;
import com.dahuo.learn.weather.city.adapter.CityAdapter;
import com.dahuo.learn.weather.city.db.DBManager;
import com.dahuo.learn.weather.city.db.WeatherDB;
import com.dahuo.learn.weather.city.domain.City;
import com.dahuo.learn.weather.city.domain.Province;
import com.dahuo.learn.weather.setting.Setting;
import com.google.android.agera.Function;
import com.google.android.agera.Functions;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.RepositoryConfig;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;


/**
 * Created by hugo on 2016/2/19 0019.
 */
public class ChoiceCityActivity extends BaseActivity implements Updatable {
    private static final String TAG = "ChoiceCityActivity";

    RecyclerView mRecyclerView;
    ProgressBar mProgressBar;
    private DBManager mDBManager;
    public ACache aCache;
    public Setting mSetting = null;


    private ArrayList<String> dataList = new ArrayList<>();
    private Province selectedProvince;
    private City selectedCity;
    private List<Province> provincesList = new ArrayList<>();
    private List<City> cityList;
    private CityAdapter mAdapter;

    public static final int LEVEL_PROVINCE = 1;
    public static final int LEVEL_CITY = 2;
    private int currentLevel;

    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.act_choice_city);
        aCache = ACache.get(getApplication());
        mSetting = Setting.getInstance();
        initView();
        mDBManager = new DBManager(ChoiceCityActivity.this);
        mDBManager.openDatabase();
        setUpRepository();
        mProgressBar.setVisibility(View.VISIBLE);
    }


    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new FadeInUpAnimator());
        mAdapter = new CityAdapter(this, dataList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CityAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provincesList.get(pos);
                    mRecyclerView.smoothScrollToPosition(0);
                    mObservable.update();
                } else if (currentLevel == LEVEL_CITY) {
                    if(dataList != null && dataList.size() > 0 && pos < dataList.size()) {
                        // selectedCity = dataList.get(pos);
                        if(dataList.get(pos) != null) {
                            mSetting.setCityName(dataList.get(pos));
                            finish();
                        }
                    }
                }
            }
        });
    }

    Supplier<List<Province>> mProvinceSupplier = new Supplier<List<Province>>() {
        @NonNull
        @Override
        public List<Province> get() {
            return WeatherDB.loadProvinces(mDBManager.getDatabase());
        }
    };
    Supplier<List<City>> mCitySupplier = new Supplier<List<City>>() {
        @NonNull
        @Override
        public List<City> get() {
            if(selectedProvince != null) {
                return WeatherDB.loadCities(mDBManager.getDatabase(), selectedProvince.ProSort);
            } else {
                return Collections.emptyList();
            }
        }
    };


    @Override
    public void onBackPressed() {
        //super.onBackPressed();  http://www.eoeandroid.com/thread-275312-1-1.html 这里的坑
        if (currentLevel == LEVEL_PROVINCE) {
            finish();
        } else {
            mUpdatable.update();
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBManager.closeDatabase();
        mExecutor.shutdown();
    }



    private ExecutorService mExecutor;
    private SimpleObservable mObservable;
    private Repository<Result<List<Province>>> mProvinceRepository;
    private Repository<Result<List<String>>> mCityRepository;

    private void setUpRepository() {
        mExecutor = Executors.newSingleThreadExecutor();
        mObservable = new SimpleObservable();

        mProvinceRepository = Repositories.repositoryWithInitialValue(Result.<List<Province>>absent())
                .observe()
                .onUpdatesPerLoop()
                .goTo(mExecutor)
                .getFrom(mProvinceSupplier)
                .thenTransform(new Function<List<Province>, Result<List<Province>>>() {
                    @NonNull
                    @Override
                    public Result<List<Province>> apply(@NonNull List<Province> input) {
                        return input != null && input.size() > 0 ? Result.success(input) : Result.<List<Province>>failure();
                    }
                })
                .onDeactivation(RepositoryConfig.SEND_INTERRUPT)
                .compile();
        mCityRepository = Repositories.repositoryWithInitialValue(Result.<List<String>>absent())
                .observe(mObservable)
                .onUpdatesPerLoop()
                .goTo(mExecutor)
                .getFrom(mCitySupplier)
                .transform(mCityFunction)
                .thenTransform(new Function<List<String>, Result<List<String>>>() {
                    @NonNull
                    @Override
                    public Result<List<String>> apply(@NonNull List<String> input) {
                        return input != null && input.size() > 0 ? Result.success(input) : Result.<List<String>>failure();
                    }
                })
                .onDeactivation(RepositoryConfig.SEND_INTERRUPT)
                .compile();
    }

    private Updatable mUpdatable = new Updatable() {
        @Override
        public void update() {
            currentLevel = LEVEL_PROVINCE;
            if (provincesList.isEmpty()) {
                provincesList.addAll(mProvinceRepository.get().get());
            }
            dataList.clear();
            for(Province province : provincesList){
                dataList.add(province.ProName);
            }
            initRecyclerView();
            mProgressBar.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mProvinceRepository.addUpdatable(mUpdatable);
        mCityRepository.addUpdatable(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mProvinceRepository.removeUpdatable(mUpdatable);
        mCityRepository.removeUpdatable(this);
    }

    @Override
    public void update() {
        if(mCityRepository.get().succeeded()) {
            dataList.clear();
            currentLevel = LEVEL_CITY;
            dataList.addAll(mCityRepository.get().get());
            mAdapter.notifyDataSetChanged();
            //定位到第一个item
            mRecyclerView.smoothScrollToPosition(0);
        }
    }


    private Function<List<City>, List<String>> mCityFunction
            = Functions
            .functionFrom((Class<List<City>>) Collections.<City>emptyList().getClass())
            .unpack(new Function<List<City>, List<City>>() {
                @NonNull
                @Override
                public List<City> apply(@NonNull List<City> input) {
                    return input;
                }
            })
            .thenMap(new Function<City, String>() {

                @NonNull
                @Override
                public String apply(@NonNull City input) {
                    return input.CityName;
                }
            });


}
