package com.logo3d.logomaker.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.logo3d.logomaker.R;
import com.logo3d.logomaker.adapter.CustomPagerAdapter;
import com.logo3d.logomaker.database.DBAdapter;
import com.logo3d.logomaker.share.Share;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class ActivityShowImageFullScreen extends Activity implements View.OnClickListener {

    private int STORAGE_PERMISSION_CODE = 23;
    private static final int REQUEST_SETTINGS_PERMISSION = 102;
    private List<String> listPermissionsNeeded = new ArrayList<>();

    ViewPager viewPager;
    CustomPagerAdapter customPagerAdapter;

    private ImageView iv_forward, iv_back, iv_close_pager, iv_delete_pager_items;
    private ImageView cb_fav, cb_unfav;
    private ImageView iv_facebook_share, iv_instagram_share, iv_email_share, iv_whatsup_share, iv_share_image;
    private TextView tv_current_page, tv_total_page;
    private RelativeLayout ll_pager_indicator;
    private LinearLayout favorite_layout;

    private boolean is_click = true;
    private ArrayList<File> al_my_photos = new ArrayList<>();
    private File[] allFiles;
    DBAdapter dbHelper;
    Bitmap b;
    Cursor cursor;
    String data;
    private static final String KEY_IMAGE_PATH = "image_path";

    private ArrayList<File> al_dummy = new ArrayList<>();


    ActivityShowImageFullScreen activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_show_image_full_screen);


        activity = ActivityShowImageFullScreen.this;

        Random random = new Random();
        int ran = 0 + random.nextInt(5);


        System.gc();
        Runtime.getRuntime().gc();

        if (checkAndRequestPermissions()) {
            //loadFBAdsBanner();

            getSHAKey();
            getarray();
            setReffrance();
            setListner();
            //loadFBAdsBanner();
        } else {

        }

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void getarray() {

        if (Share.Fragment.equalsIgnoreCase("MyPhotosFragment")) {

            setData();

        } else {
            fill_Array();
        }

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

        Log.e("TAG", "listPermissionsNeeded===>" + listPermissionsNeeded);
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

                Log.e("TAG", "onRequestPermissionsResult: deny");

            } else {
                Log.e("TAG", "onRequestPermissionsResult: dont ask again");

                final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
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


        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.e("TAG", "onResume: call ");

        if (checkAndRequestPermissions()) {

            check_like_data();


        } else {

            Log.e("TAG", "onResume: else");

            //finish();
        }


    }

    private void setListner() {

        iv_close_pager.setOnClickListener(this);
        iv_delete_pager_items.setOnClickListener(this);
        iv_share_image.setOnClickListener(this);
        cb_fav.setOnClickListener(this);
        cb_unfav.setOnClickListener(this);


        //********** Sharing Option ***********//

        iv_facebook_share.setOnClickListener(this);
        iv_instagram_share.setOnClickListener(this);
        iv_email_share.setOnClickListener(this);
        iv_whatsup_share.setOnClickListener(this);
    }

    private void getSHAKey() {

        dbHelper = new DBAdapter(ActivityShowImageFullScreen.this);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void setReffrance() {

        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        iv_forward = (ImageView) findViewById(R.id.iv_forward);
//        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_delete_pager_items = (ImageView) findViewById(R.id.iv_delete_pager_items);
        iv_close_pager = (ImageView) findViewById(R.id.iv_close_pager);
        tv_current_page = (TextView) findViewById(R.id.tv_current_page);
        tv_total_page = (TextView) findViewById(R.id.tv_total_page);
        customPagerAdapter = new CustomPagerAdapter(this, al_my_photos);
        ll_pager_indicator = (RelativeLayout) findViewById(R.id.ll_pager_indicator);
        favorite_layout = (LinearLayout) findViewById(R.id.favorite_layout);
        cb_fav = (ImageView) findViewById(R.id.iv_fav);
        cb_unfav = (ImageView) findViewById(R.id.iv_unfav);
        int height = (int) (getApplicationContext().getResources().getDisplayMetrics().heightPixels * 0.11);
        ll_pager_indicator.getLayoutParams().height = height;
        favorite_layout.getLayoutParams().height = height;

        //********** Sharing Option ***********//

        iv_facebook_share = (ImageView) findViewById(R.id.iv_facebook_share);
        iv_instagram_share = (ImageView) findViewById(R.id.iv_instagram_share);
        iv_email_share = (ImageView) findViewById(R.id.iv_email_share);
        iv_whatsup_share = (ImageView) findViewById(R.id.iv_whatsup_share);
        iv_share_image = (ImageView) findViewById(R.id.iv_share_image);


        viewPager.setAdapter(customPagerAdapter);
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("avairy")) {
            viewPager.setCurrentItem(0);
        } else {

            if (Share.Fragment.equalsIgnoreCase("MyPhotosFragment")) {
                viewPager.setCurrentItem(Share.my_photos_position);
            } else {
                viewPager.setCurrentItem(Share.my_favourite_position);
            }


            Log.e("TAG", "Share.my_photos_position=>" + Share.my_photos_position);
        }

        Animation bottomUp = AnimationUtils.loadAnimation(ActivityShowImageFullScreen.this,
                R.anim.bottom_up);
        ll_pager_indicator.startAnimation(bottomUp);

//        tv_total_page.setText(String.valueOf(al_my_photos.size()));

        if (al_my_photos.size() < 10) {

            tv_total_page.setText("0" + String.valueOf(al_my_photos.size()));

        } else {

            tv_total_page.setText(String.valueOf(al_my_photos.size() + 1));
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("TAG", "onPageScrolled");
                displayMetaInfo(position);

                check_like_data();

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (Share.Fragment.equalsIgnoreCase("MyPhotosFragment")) {

            iv_delete_pager_items.setEnabled(true);

        } else {

            iv_delete_pager_items.setAlpha(0.5f);
            iv_delete_pager_items.setEnabled(false);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }

    private void setData() {

        File path = new File(Share.IMAGE_PATH);

        Log.e("TAG", "PATH ===>" + path);

        if (path.exists()) {
            allFiles = path.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png"));
                }
            });
        }
        if (allFiles != null) {
            if (allFiles.length > 0) {

                for (int i = 0; i < allFiles.length; i++) {
                    /*  Bitmap mBitmap = BitmapFactory.decodeFile(String.valueOf(allFiles[i]));*/
                    al_my_photos.add(allFiles[i]);
                }
                Collections.sort(al_my_photos, Collections.reverseOrder());
            }
        }
    }

    private void fill_Array() {

        al_my_photos.clear();
        al_my_photos = new ArrayList<>();

        cursor = dbHelper.getFavData();

        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {

        } else {

            if (cursor.moveToFirst()) {
                do {
                    data = cursor.getString(cursor.getColumnIndex(KEY_IMAGE_PATH));
                    Log.e("PATH------", cursor.getString(cursor.getColumnIndex(KEY_IMAGE_PATH)) + "");
                    File path = new File(data).getAbsoluteFile();

                    if (!path.exists()) {
                        try {
                            path.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    al_dummy.add(path);

                } while (cursor.moveToNext());
            }

            for (int i = al_dummy.size() - 1; i >= 0; i--) {
                al_my_photos.add(al_dummy.get(i));
            }
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    public void handleAction(View view) {

        if (is_click) {
            Log.e("LOG", "if");
            Animation bottomUp = AnimationUtils.loadAnimation(ActivityShowImageFullScreen.this,
                    R.anim.bottom_down);
            ll_pager_indicator.startAnimation(bottomUp);
            ll_pager_indicator.setVisibility(View.INVISIBLE);
            is_click = false;
        } else {
            Log.e("LOG", "else");
            Animation bottomUp = AnimationUtils.loadAnimation(ActivityShowImageFullScreen.this,
                    R.anim.bottom_up);
            ll_pager_indicator.startAnimation(bottomUp);
            ll_pager_indicator.setVisibility(View.VISIBLE);
            is_click = true;
        }
    }

    @Override
    public void onClick(View v) {

        if (v == iv_close_pager) {

            onBackPressed();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);

        } else if (v == iv_delete_pager_items) {

            try {
                deleteImage(viewPager.getCurrentItem());
                dbHelper.deleteDrawDetails(String.valueOf(al_my_photos.get(viewPager.getCurrentItem())));

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (v == iv_instagram_share) {

            try {
                if (appInstalledOrNot("com.instagram.android")) {

                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setPackage("com.instagram.android");
                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(al_my_photos.get(viewPager.getCurrentItem())));
                    share.putExtra(Intent.EXTRA_TEXT, "Make more logo with app link \n https://play.google.com/store/apps/details?id=" + getPackageName());
                    share.setType("image/jpeg");
                    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(share, "Share Picture"));

                } else {

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.instagram.android")));
                    } catch (Exception e) {
                        e.printStackTrace();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.instagram.android")));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (v == iv_facebook_share) {

            try {
                if (appInstalledOrNot("com.facebook.katana")) {

                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setPackage("com.facebook.katana");
                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(al_my_photos.get(viewPager.getCurrentItem())));
//            Share.putExtra(Intent.EXTRA_TEXT, "Make more pics with app link \n https://play.google.com/store/apps/details?id=" + ActivityShowImageFullScreen.this.getPackageName());
                    share.setType("image/jpeg");
                    startActivity(Intent.createChooser(share, "Share Picture"));
                } else {
                    final String appPackageName = "com.facebook.katana";
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (v == iv_email_share) {

            try {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(al_my_photos.get(viewPager.getCurrentItem())));
                intent.putExtra(Intent.EXTRA_TEXT, "Make more logo with app link \n https://play.google.com/store/apps/details?id=" + getPackageName());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(intent, "Share Picture"));
                } else {
                    Toast.makeText(getApplicationContext(), "Mail app have not been installed", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (v == iv_whatsup_share) {

            try {
                if (appInstalledOrNot("com.whatsapp")) {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setPackage("com.whatsapp");
                    share.setType("image/jpeg");
                    share.putExtra(Intent.EXTRA_TEXT, "Make more logo with app link \n https://play.google.com/store/apps/details?id=" + getPackageName());
                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(al_my_photos.get(viewPager.getCurrentItem())));
                    startActivity(Intent.createChooser(share, "Select"));
                } else
                    Toast.makeText(getApplicationContext(), "Whatsapp have not been installed", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (v == iv_share_image) {

            try {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_TEXT, "Make more pics with app link \n https://play.google.com/store/apps/details?id=" + getPackageName());
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(al_my_photos.get(viewPager.getCurrentItem())));
                startActivity(Intent.createChooser(intent, "Share Picture"));

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (v == cb_fav) {

            cb_fav.setVisibility(View.GONE);
            cb_unfav.setVisibility(View.VISIBLE);
            // handleFav();

        } else if (v == cb_unfav) {

            cb_fav.setVisibility(View.VISIBLE);
            cb_unfav.setVisibility(View.GONE);
            //handleFav();
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    /*public void handleFav() {
        if (cb_fav.getVisibility() == View.VISIBLE) {

            //Share.image_path = String.valueOf(al_my_photos.get(viewPager.getCurrentItem()));
           // Share.SELECTED_BITMAP = BitmapFactory.decodeFile(String.valueOf(al_my_photos.get(viewPager.getCurrentItem())));
            // byte[] img = getBitmapAsByteArray(Share.SELECTED_BITMAP);
            //byte[] img = null;
            //dbHelper.saveMessageData(img, Share.image_path);


           // long count = dbHelper.GetRowCountofTable();
           // Log.e("count", "" + count);


        } else {
            Share.image_path = String.valueOf(al_my_photos.get(viewPager.getCurrentItem()));

            if (Share.Fragment.equalsIgnoreCase("FavouriteFragment")) {
                if (cb_unfav.getVisibility() == View.VISIBLE) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityShowImageFullScreen.this, R.style.MyAlertDialog);
                    builder.setMessage("Are you sure you want to unfavorite it ?")
                            .setCancelable(false)

                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dbHelper.deleteDrawDetails(Share.image_path);

                                    if (al_my_photos.size() > 0) {

                                        al_my_photos.remove(viewPager.getCurrentItem());
                                        int current_page = viewPager.getCurrentItem();
                                        if (al_my_photos.size() == 0) {
                                            tv_current_page.setText("00" + " / ");
                                            onBackPressed();
                                        }
                                        customPagerAdapter.notifyDataSetChanged();
                                        viewPager.setAdapter(customPagerAdapter);
                                        // viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                                        //viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                                        viewPager.setCurrentItem(current_page - 1);

                                        displayMetaInfo(viewPager.getCurrentItem());

                                    }

                                    String data = dbHelper.getSingleFavData(Share.image_path);

                                    if (Share.image_path.equalsIgnoreCase(data)) {
                                        // cb_fav.setChecked(true);
                                        cb_fav.setVisibility(View.VISIBLE);
                                        cb_unfav.setVisibility(View.GONE);
                                    } else {
                                        // cb_fav.setChecked(false);
                                        cb_fav.setVisibility(View.GONE);
                                        cb_unfav.setVisibility(View.VISIBLE);
                                    }


                                    long row = dbHelper.GetRowCountofTable();

                                    if (Share.Fragment.equalsIgnoreCase("MyPhotosFragment")) {

                                    } else {
                                        if (row == 0) {
                                            finish();
                                        }
                                    }
                                    Log.e("dbhelper", "" + row);

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String data = dbHelper.getSingleFavData(Share.image_path);

                                    if (Share.image_path.equalsIgnoreCase(data)) {
                                        //cb_fav.setChecked(true);
                                        cb_fav.setVisibility(View.VISIBLE);
                                        cb_unfav.setVisibility(View.GONE);
                                    } else {
                                        //cb_fav.setChecked(false);
                                        cb_fav.setVisibility(View.GONE);
                                        cb_unfav.setVisibility(View.VISIBLE);
                                    }

                                    long row = dbHelper.GetRowCountofTable();
                                    Log.e("dbhelper", "" + row);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

            } else {
                if (cb_unfav.getVisibility() == View.VISIBLE) {
                    dbHelper.deleteDrawDetails(Share.image_path);
                    String data = dbHelper.getSingleFavData(Share.image_path);

                    if (Share.image_path.equalsIgnoreCase(data)) {
                        //cb_fav.setChecked(true);
                        cb_fav.setVisibility(View.VISIBLE);
                        cb_unfav.setVisibility(View.GONE);
                    } else {
                        //cb_fav.setChecked(false);
                        cb_fav.setVisibility(View.GONE);
                        cb_unfav.setVisibility(View.VISIBLE);
                    }

                    long row = dbHelper.GetRowCountofTable();
                    if (Share.Fragment.equalsIgnoreCase("MyPhotosFragment")) {

                    } else {
                        if (row == 0) {
                            finish();
                        }
                    }
                    Log.e("dbhelper", "" + row);
                }
            }
        }
    }*/


    private void check_like_data() {

        try {
            // Share.image_path = String.valueOf(al_my_photos.get(viewPager.getCurrentItem()));
            // String data = dbHelper.getSingleFavData(Share.image_path);

           /* if (Share.image_path.equalsIgnoreCase(data)) {
                //  cb_fav.setChecked(true);
                cb_fav.setVisibility(View.VISIBLE);
                cb_unfav.setVisibility(View.GONE);
            } else {
                //cb_fav.setChecked(false);
                cb_fav.setVisibility(View.GONE);
                cb_unfav.setVisibility(View.VISIBLE);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            return outputStream.toByteArray();
        }
        return new byte[0];
    }


    private void deleteImage(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityShowImageFullScreen.this, R.style.MyAlertDialog);
        //builder.setMessage(getResources().getString(R.string.delete_emoji_msg));
        builder.setMessage("Are you sure want to delete photo ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.e("TAG", "al_my_photos.size():" + al_my_photos.size());
                if (al_my_photos.size() > 0) {

                    boolean isDeleted = al_my_photos.get(position).delete();
                    Log.e("TAG", "isDeleted:" + isDeleted);
                    if (isDeleted) {
                        if (al_my_photos.get(viewPager.getCurrentItem()).exists()) {
                            al_my_photos.get(viewPager.getCurrentItem()).delete();
//
                        }
                        File f = new File(String.valueOf(al_my_photos.get(viewPager.getCurrentItem())));
                        if (f.exists()) {
                            Log.e("TAG", "img:" + f);
                            f.delete();
                        }
                        al_my_photos.remove(position);
                        if (al_my_photos.size() == 0) {
                            onBackPressed();
                        }
                        customPagerAdapter.notifyDataSetChanged();
                        viewPager.setAdapter(customPagerAdapter);
                        viewPager.setCurrentItem(position - 1);
                        displayMetaInfo(viewPager.getCurrentItem());

                    } else {
                    }
                }
                dialog.cancel();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void displayMetaInfo(int position) {
        if (position < 9) {
            tv_current_page.setText("0" + (position + 1) + " / ");
        } else {
            tv_current_page.setText((position + 1) + " / ");
        }
        Log.e("TAG", "al_my phots==>" + al_my_photos.size());
        if (al_my_photos.size() < 10) {
            tv_total_page.setText("0" + String.valueOf(al_my_photos.size()));
        } else {
            tv_total_page.setText(String.valueOf(al_my_photos.size()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
