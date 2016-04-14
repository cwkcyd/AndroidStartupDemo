package com.dahuo.learn.androidstartupdemo.fragment;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import com.dahuo.learn.androidstartupdemo.R;
import com.dahuo.learn.app.AndroidApplication;
import com.squareup.leakcanary.RefWatcher;


/**
 * Created on 15/08/01
 */
public class BaseFragment extends Fragment {
    protected String TAG = "BaseSupportActivity";


    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = AndroidApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    protected ProgressDialog bongProgressDialog;
    protected void dismissProgressDialog() {
        try {
            if (bongProgressDialog != null && bongProgressDialog.isShowing()) {
                bongProgressDialog.dismiss();
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            bongProgressDialog = null;
        }
    }

    protected void showProgressDialog(int rid){
        bongProgressDialog = ProgressDialog.show(getActivity(), null, getString(rid));
        bongProgressDialog.setCancelable(false);
    }
    protected void showProgressDialog(String msg){
        bongProgressDialog = ProgressDialog.show(getActivity(), null, msg);
        bongProgressDialog.setCancelable(false);
    }
    protected void showProgressDialog(){
        bongProgressDialog = ProgressDialog.show(getActivity(), null, getString(R.string.app_loading));
        bongProgressDialog.setCancelable(false);
    }
}
