package com.logo3d.logomaker.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.logo3d.logomaker.activity.ActivityCreatingLogo;
import com.logo3d.logomaker.activity.ActivityShowImageFullScreen;
import com.logo3d.logomaker.share.Share;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Save extends AsyncTask<Void, Void, String> {

    Context context;
    private File fileTosave;
    private Bitmap imageToSave;
    private View imageView;
    private ProgressDialog progressDialog;

    public Save(View view, ActivityCreatingLogo activityCreatingLogo) {
        this.imageView = view;
        view.setDrawingCacheEnabled(true);
        this.imageToSave = view.getDrawingCache();
        this.context = activityCreatingLogo;
    }


    /* access modifiers changed from: protected */
    public void onPreExecute() {
        this.progressDialog = new ProgressDialog(this.imageView.getContext());
        this.progressDialog.requestWindowFeature(1);
        this.progressDialog.setMessage("Saving........");
        this.progressDialog.show();
    }

    /* access modifiers changed from: protected */
    public String doInBackground(Void... voidArr) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(Share.D_NAME);
        File file = new File(Share.IMAGE_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(System.currentTimeMillis());
        sb2.append(".png");
        this.fileTosave = new File(file, sb2.toString());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.fileTosave);
            this.imageToSave.compress(CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Share.filetosave = this.fileTosave;
            return "Image Saved Successfully";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(String str) {
        this.progressDialog.dismiss();
        Toast.makeText(this.imageView.getContext(), str, Toast.LENGTH_LONG).show();
        this.imageView.getContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(this.fileTosave)));
        this.imageView.setDrawingCacheEnabled(false);


        Random random = new Random();
        int ran = 0 + random.nextInt(5);

        if (ran == 0) {


        } else {

            Intent intent = new Intent(context, ActivityShowImageFullScreen.class);
            Share.Fragment = "MyPhotosFragment";
            intent.putExtra("avairy", "");
            context.startActivity(intent);
        }


    }
}
