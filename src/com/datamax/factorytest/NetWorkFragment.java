package com.datamax.factorytest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.view.View;

@SuppressLint("NewApi")
public abstract class NetWorkFragment extends Fragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    protected boolean hasFocused(View v) {
        return v != null && v.isFocused();
    }

}
