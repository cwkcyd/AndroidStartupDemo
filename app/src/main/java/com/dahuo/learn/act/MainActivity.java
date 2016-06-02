package com.dahuo.learn.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.dahuo.learn.startup.R;
import com.dahuo.learn.startup.databinding.ActMainBinding;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private int  mTimer;
    private DrawerLayout mDrawerLayout;
    private ActMainBinding mDataBinding;
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

    }


    private void setTimeCount(){
        mDataBinding.setSeconds(++mTimer);
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
}
