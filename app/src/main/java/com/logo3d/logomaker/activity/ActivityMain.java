package com.logo3d.logomaker.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.logo3d.logomaker.BuildConfig;
import com.logo3d.logomaker.R;
import com.logo3d.logomaker.database.DBAdapter;
import com.logo3d.logomaker.database.ImportDatabase;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener {

    public static InputStream databaseInputStream1;
    final DBAdapter dba = new DBAdapter(this);

    String image_name = "";
    public final int STORAGE_PERMISSION_CODE = 23;
    private List<String> listPermissionsNeeded = new ArrayList<>();

    LinearLayout lnCreate, lnSaved;
    ImageView imgShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.act_main);

        findViews();
        initViews();

    }

    private void findViews() {

        lnCreate = findViewById(R.id.create_logo);
        lnSaved = findViewById(R.id.my_logo);

//        imgPolicy = findViewById(R.id.policy);
//        imgRate = findViewById(R.id.rate);
        imgShare = findViewById(R.id.share);

    }


    private void initViews() {

        lnCreate.setOnClickListener(this);
        lnSaved.setOnClickListener(this);
//        imgPolicy.setOnClickListener(this);
//        imgRate.setOnClickListener(this);
        imgShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == lnCreate) {


            Random random = new Random();
            int ran = 0 + random.nextInt(5);

            if (ran == 0) {

                if (checkAndRequestPermissions()) {



                        image_name = "create";
                        startActivity(new Intent(ActivityMain.this, ActivityCreatingLogo.class));



                } else {
                    image_name = "create";
                    ActivityCompat.requestPermissions(ActivityMain.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), STORAGE_PERMISSION_CODE);

                }
            } else {

                if (checkAndRequestPermissions()) {

                    image_name = "create";
                    startActivity(new Intent(ActivityMain.this, ActivityCreatingLogo.class));

                } else {
                    image_name = "create";
                    ActivityCompat.requestPermissions(ActivityMain.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), STORAGE_PERMISSION_CODE);
                }
            }


        } else if (v == lnSaved) {


            Random random = new Random();
            int ran = 0 + random.nextInt(5);

            if (ran == 0) {
                if (checkAndRequestPermissions()) {


                        image_name = "saved";
                        startActivity(new Intent(ActivityMain.this, ActivityMyCreative.class));



                } else {
                    image_name = "saved";
                    ActivityCompat.requestPermissions(ActivityMain.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), STORAGE_PERMISSION_CODE);

                }
            } else {

                if (checkAndRequestPermissions()) {

                    image_name = "saved";
                    startActivity(new Intent(ActivityMain.this, ActivityMyCreative.class));

                } else {

                    image_name = "saved";
                    ActivityCompat.requestPermissions(ActivityMain.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), STORAGE_PERMISSION_CODE);
                }
            }


        }  else if (v == imgShare) {

            try {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                String shareMessage = "Logo Maker" + "\n\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));

            } catch (Exception e) {

            }

        }

    }


    private boolean checkAndRequestPermissions() {

        listPermissionsNeeded.clear();

        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readStorage = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);

        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (readStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(READ_EXTERNAL_STORAGE);
        }


        if (!listPermissionsNeeded.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case STORAGE_PERMISSION_CODE:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED

                        || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

                            || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {


                    } else {

                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                        alertDialogBuilder.setTitle("Permissions Required")
                                .setMessage("Please allow permission for storage")
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
                    // Permission has already been granted
                    if (image_name == "create") {


                        startActivity(new Intent(ActivityMain.this, ActivityCreatingLogo.class));

                    } else if (image_name == "saved") {

                        // startActivity(new Intent(ActivityMain.this, ActivityMyCreative.class));

                    }
                }
                break;

        }
    }

    public class CopyDB extends AsyncTask<String, Void, Boolean> {

        @SuppressLint("SdCardPath")
        @Override
        protected void onPreExecute() {

            try {

                File f = new File("/data/data/" + getPackageName() + "/databases/Suitdb.sql");

                Log.e("File of Local DataBase", "f  : " + f);
                if (f.exists()) {

                } else {
                    try {
                        dba.open();
                        dba.close();
                        System.out.println("Database is copying.....");
                        databaseInputStream1 = getAssets().open("Suitdb.sql");
                        ImportDatabase.copyDataBase();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {

            Boolean success = false;

            try {
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        new CopyDB().execute("");


    }
//
//    @Override
//    public void onBackPressed() {
//
//        try {
//
////            final Dialog dialog = new Dialog(ActivityMain.this);
////            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////            dialog.setContentView(R.layout.dlg_exit1);
////            dialog.getWindow().setLayout((int) (DisplayMetricsHandler.getScreenWidth() - 50), Toolbar.LayoutParams.WRAP_CONTENT);
////            dialog.setCancelable(true);
////            dialog.setCanceledOnTouchOutside(false);
////            dialog.show();
//
//            LinearLayout rateus, shareapp, nothnks;
//
////            rateus = dialog.findViewById(R.id.rateus);
////            shareapp = dialog.findViewById(R.id.shareapp);
////            nothnks = dialog.findViewById(R.id.nothnks);
//
//            rateus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    try {
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
//                    } catch (android.content.ActivityNotFoundException anfe) {
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
//                    }
//
//                }
//            });
//
//            shareapp.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    try {
//
//                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                        shareIntent.setType("text/plain");
//                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
//                        String shareMessage = "Logo Maker" + "\n\nLet me recommend you this application\n\n";
//                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
//                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//                        startActivity(Intent.createChooser(shareIntent, "choose one"));
//                        dialog.dismiss();
//
//                    } catch (Exception e) {
//
//                    }
//
//                }
//            });
//
//            nothnks.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    dialog.cancel();
//                    ActivityMain.super.onBackPressed();
//                    finish();
//
//                }
//            });
//
//        } catch (Exception e) {
//            e.getMessage();
//        }
//
//    }


}
