package com.logo3d.logomaker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.logo3d.logomaker.R;
import com.logo3d.logomaker.share.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import androidx.viewpager.widget.PagerAdapter;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<File> al_image;
    LayoutInflater mLayoutInflater;

    public CustomPagerAdapter(Context context, ArrayList<File> al_image) {
        mContext = context;
        this.al_image = al_image;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return al_image.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_pager, container, false);

        final TouchImageView iv_my_photos = (TouchImageView) itemView.findViewById(R.id.iv_my_photos);
        final ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);

        Picasso.get()
                .load(al_image.get(position))
                .placeholder(R.drawable.transparent_background)
                .into(iv_my_photos, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                        iv_my_photos.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }

                });

        container.addView(itemView);

        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

