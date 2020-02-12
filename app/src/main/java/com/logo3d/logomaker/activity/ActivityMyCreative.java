package com.logo3d.logomaker.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;

import com.logo3d.logomaker.R;
import com.logo3d.logomaker.share.Share;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityMyCreative extends AppCompatActivity implements View.OnClickListener {

    public static ArrayList<File> al_my_photos = new ArrayList<>();
    private File[] allFiles;

    private ImageView iv_back_my_photos, iv_all_delete;

    private List<String> listPermissionsNeeded = new ArrayList<>();

    androidx.fragment.app.Fragment fragment = null;

    ActivityMyCreative activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_creative);

        activity = ActivityMyCreative.this;
        if (checkAndRequestPermissions()) {

            iv_back_my_photos = (ImageView) findViewById(R.id.iv_back_my_photos);
            iv_all_delete = (ImageView) findViewById(R.id.iv_all_delete);

            setListner();
            setData();

            updateFragment(MyPhotosFragment.newInstance());

        } else {


        }

    }


    private void updateFragment(androidx.fragment.app.Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

    }

    private void setListner() {

        iv_back_my_photos.setOnClickListener(this);
        iv_all_delete.setOnClickListener(this);

    }

    private void setData() {

        File path = new File(Share.IMAGE_PATH);
        al_my_photos.clear();

        if (path.exists()) {
            allFiles = path.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png"));
                }
            });

            if (allFiles.length > 0) {
                iv_all_delete.setAlpha(1f);
                iv_all_delete.setEnabled(true);
                for (int i = 0; i < allFiles.length; i++) {
                    Bitmap mBitmap = BitmapFactory.decodeFile(String.valueOf(allFiles[i]));
                    al_my_photos.add(allFiles[i]);

                }
                Collections.sort(al_my_photos, Collections.reverseOrder());

            } else {
                iv_all_delete.setAlpha(0.5f);
                iv_all_delete.setEnabled(false);

            }
        } else {
            iv_all_delete.setAlpha(0.5f);
            iv_all_delete.setEnabled(false);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }

    private boolean checkAndRequestPermissions() {

        listPermissionsNeeded.clear();

        int writeStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writeStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (readStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED

                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {


            } else {

                final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Permissions Required")
                        .setMessage("Please allow permission for Storage")
                        .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", getPackageName(), null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }

        } else {


        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkAndRequestPermissions()) {

            setData();


        } else {


        }

    }

    @Override
    public void onClick(View v) {


        if (v == iv_back_my_photos) {
            finish();
        }

    }



}

