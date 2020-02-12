package com.logo3d.logomaker.database;

public class Table_Favourite_Images_model {

    Integer Id;
    String Image_Path;
    byte[] Image_url;

    public Table_Favourite_Images_model(Integer id, byte[] image_url, String image_Path ) {
        Id = id;
        Image_Path = image_Path;
        Image_url = image_url;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getImage_Path() {
        return Image_Path;
    }

    public void setImage_Path(String image_Path) {
        Image_Path = image_Path;
    }

    public byte[] getImage_url() {
        return Image_url;
    }

    public void setImage_url(byte[] image_url) {
        Image_url = image_url;
    }


}
