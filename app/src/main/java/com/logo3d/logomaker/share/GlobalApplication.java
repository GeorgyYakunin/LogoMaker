package com.logo3d.logomaker.share;


import android.os.StrictMode;

import com.logo3d.logomaker.R;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class GlobalApplication extends MultiDexApplication {



    private static final String TAG = "Application";

    private static GlobalApplication appInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;
        MultiDex.install(this);
        //TODO to solve camera issue
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());




    }







    public static synchronized GlobalApplication getInstance() {
        return appInstance;
    }

}
