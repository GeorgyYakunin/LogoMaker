package com.logo3d.logomaker.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.logo3d.logomaker.R;
import com.logo3d.logomaker.adapter.MyPhotosAdapter;
import com.logo3d.logomaker.database.DBAdapter;
import com.logo3d.logomaker.share.Share;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyPhotosFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rcvImages;
    private GridLayoutManager gridLayoutManager;
    private MyPhotosAdapter myPhotosAdapter;
    private ArrayList<File> al_my_photos = new ArrayList<>();
    private File[] allFiles;
    private RelativeLayout rlMyphoto;
    private LinearLayout lnNophoto;
    DBAdapter dbAdapter;
    ImageView imgAlldelet;
    public int posi;

    private List<String> listPermissionsNeeded = new ArrayList<>();

    public static MyPhotosFragment newInstance() {
        Bundle args = new Bundle();
        MyPhotosFragment fragment = new MyPhotosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_m_photo, container, false);

        if (checkAndRequestPermissions()) {

            dbAdapter = new DBAdapter(getActivity());
            rcvImages = (RecyclerView) view.findViewById(R.id.rcv_images);

            gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            rlMyphoto = (RelativeLayout) view.findViewById(R.id.rl_my_photos);
            lnNophoto = (LinearLayout) view.findViewById(R.id.ll_no_photos);
            rcvImages.setLayoutManager(gridLayoutManager);
            // myPhotosAdapter = new MyPhotosAdapter(getActivity(), al_my_photos);
            imgAlldelet = (ImageView) getActivity().findViewById(R.id.iv_all_delete);
            imgAlldelet.setOnClickListener(this);

            Share.Fragment = "MyPhotosFragment";


        } else {

        }
        return view;
    }


    private void setData() {

        File path = new File(Share.IMAGE_PATH);

        al_my_photos.clear();
        Share.al_my_photos_photo.clear();
        allFiles = null;

        if (path.exists()) {

            allFiles = path.listFiles(new FilenameFilter() {

                public boolean accept(File dir, String name) {
                    return (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png"));
                }

            });

            if (allFiles.length > 0) {


                rlMyphoto.setVisibility(View.VISIBLE);
                lnNophoto.setVisibility(View.GONE);
                imgAlldelet.setAlpha(1f);
                imgAlldelet.setEnabled(true);
                for (int i = 0; i < allFiles.length; i++) {
                    al_my_photos.add(allFiles[i]);
                    ///Now set this bitmap on imageview
                }

                Collections.sort(al_my_photos, Collections.reverseOrder());
                Share.al_my_photos_photo.addAll(al_my_photos);

                myPhotosAdapter = new MyPhotosAdapter(getActivity(), Share.al_my_photos_photo, new MyPhotosAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        posi = position;
                        go_on_fullscreenimage();
                    }
                });
                rcvImages.setAdapter(myPhotosAdapter);

            } else {

                al_my_photos.clear();
                imgAlldelet.setAlpha(0.5f);
                imgAlldelet.setEnabled(false);
                rlMyphoto.setVisibility(View.GONE);
                lnNophoto.setVisibility(View.VISIBLE);
            }
        } else {

            al_my_photos.clear();
            imgAlldelet.setAlpha(0.5f);
            imgAlldelet.setEnabled(false);
            rlMyphoto.setVisibility(View.GONE);
            lnNophoto.setVisibility(View.VISIBLE);
        }
    }

    private void go_on_fullscreenimage() {


        Random random = new Random();
        int ran = 0+random.nextInt(5);

        if(ran==0)
        {
                nextActivity();

        }else
        {

            nextActivity();
        }



    }

    private void nextActivity() {
        Intent intent = new Intent(getActivity(), ActivityShowImageFullScreen.class);
        Share.Fragment = "MyPhotosFragment";
        Share.my_photos_position = posi;
        getActivity().startActivity(intent);
        Activity activity = (Activity) getActivity();
        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_all_delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialog);
                //builder.setMessage(getResources().getString(R.string.delete_emoji_msg));
                builder.setMessage("Are you sure want to delete all photos ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i < al_my_photos.size(); i++) {

                            File f = new File(String.valueOf(al_my_photos.get(i)));

                            f.delete();
                            al_my_photos.get(i).delete();

                            delFolder();
                            // delete(getContext(),new File(String.valueOf(al_my_photos.get(i))));

                        }
                        al_my_photos.clear();

                        dbAdapter.deleteFavData();
                        long count = dbAdapter.GetRowCountofTable();

                        if (count == 0) {

                            imgAlldelet.setAlpha(0.5f);
                            imgAlldelet.setEnabled(false);
                            rlMyphoto.setVisibility(View.GONE);
                            lnNophoto.setVisibility(View.VISIBLE);

                        }

                        if (al_my_photos.size() == 0) {

                            imgAlldelet.setAlpha(0.5f);
                            imgAlldelet.setEnabled(false);
                            rlMyphoto.setVisibility(View.GONE);
                            lnNophoto.setVisibility(View.VISIBLE);

                        }
                        setData();
                        myPhotosAdapter.notifyDataSetChanged();


                        al_my_photos.clear();
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
                break;
        }
    }

    public void delFolder() {

        File file = new File(Share.IMAGE_PATH);
        file.delete();

        if (file.exists()) {

            try {

                file.getCanonicalFile().delete();

            } catch (IOException e) {

                e.printStackTrace();
            }
            if (file.exists()) {
                getActivity().deleteFile(file.getName());
            }
        }

    }

    public static boolean delete(final Context context, final File file) {

        final String where = MediaStore.MediaColumns.DATA + "=?";

        final String[] selectionArgs = new String[]{
                file.getAbsolutePath()
        };

        final ContentResolver contentResolver = context.getContentResolver();
        final Uri filesUri = MediaStore.Files.getContentUri("external");

        contentResolver.delete(filesUri, where, selectionArgs);

        if (file.exists()) {

            contentResolver.delete(filesUri, where, selectionArgs);
        }
        return !file.exists();

    }

    public void deleteFileFromMediaStore(final ContentResolver contentResolver, final File file) {

        String canonicalPath;
        try {
            canonicalPath = file.getCanonicalPath();
        } catch (IOException e) {
            canonicalPath = file.getAbsolutePath();
        }
        final Uri uri = MediaStore.Files.getContentUri("external");
        final int result = contentResolver.delete(uri,
                MediaStore.Files.FileColumns.DATA + "=?", new String[]{canonicalPath});
        if (result == 0) {
            final String absolutePath = file.getAbsolutePath();
            if (!absolutePath.equals(canonicalPath)) {
                contentResolver.delete(uri,
                        MediaStore.Files.FileColumns.DATA + "=?", new String[]{absolutePath});
            }
        }
    }

    private boolean checkAndRequestPermissions() {

        listPermissionsNeeded.clear();

        int writeStorage = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readStorage = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

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


        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {


            } else {

                final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(getContext());
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
                                        Uri.fromParts("package", getActivity().getPackageName(), null));
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
    public void onResume() {
        super.onResume();

        if (checkAndRequestPermissions()) {
            Share.Fragment = "MyPhotosFragment";
            setData();
        }



    }

}
