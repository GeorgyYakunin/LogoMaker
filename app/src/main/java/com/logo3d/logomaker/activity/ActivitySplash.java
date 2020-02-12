package com.logo3d.logomaker.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.logo3d.logomaker.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySplash extends AppCompatActivity {

    Handler handler = new Handler();
    Runnable myRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.act_splash);


        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        for (Signature signature : info.signatures) {

            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            md.update(signature.toByteArray());
            Log.e("TAG", "KeyHash:--->" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
        }
        initViews();

    }

    private void initViews() {

        handler = new Handler();

        myRunnable = new Runnable() {
            public void run() {


                    Intent i = new Intent(ActivitySplash.this, ActivityMain.class);
                    startActivity(i);
                    finish();



            }
        };
        handler.postDelayed(myRunnable, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

}
