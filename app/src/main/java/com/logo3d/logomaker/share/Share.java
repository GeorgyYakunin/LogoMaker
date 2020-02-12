package com.logo3d.logomaker.share;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

public class Share {

    public static final String D_NAME = "LogoMakerFree";
    public static final String TEMP_FILE_NAME = "temp_file.jpeg";
    public static File filetosave;


    public static final String IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "Logo Maker";

    public static ArrayList<File> al_my_photos_photo = new ArrayList<>();

    public static String Fragment = "MyPhotosFragmen" + "t";

    public static int my_favourite_position = 0;
    public static int my_photos_position = 0;

}
