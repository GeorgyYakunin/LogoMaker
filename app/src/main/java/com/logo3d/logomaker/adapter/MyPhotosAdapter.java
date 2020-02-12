package com.logo3d.logomaker.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.logo3d.logomaker.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MyPhotosAdapter extends RecyclerView.Adapter<MyPhotosAdapter.ViewHolder> {

    final Context context;
    int screen_width;
    int screen_height;
    private ArrayList<File> al_my_photos = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public MyPhotosAdapter(final Context context, final ArrayList<File> al_my_photos, OnItemClickListener onItemClickListener) {

        this.context = context;
        this.al_my_photos = al_my_photos;
        screen_width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        screen_height = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
        mOnItemClickListener = onItemClickListener;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_cake_image;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_cake_image = (ImageView) itemView.findViewById(R.id.iv_cake_image);
           // progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_creating, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.setIsRecyclable(false);

        Picasso.get()
                .load(al_my_photos.get(position))
                .fit()
                .into(holder.iv_cake_image, new Callback() {
                    @Override
                    public void onSuccess() {
                       // holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }

                });

        //holder.iv_cake_image.setScaleType(ImageView.ScaleType.FIT_XY);
        //holder.iv_cake_image.getLayoutParams().width = (int) (screen_width / 3);
       // holder.iv_cake_image.getLayoutParams().height = (int)  ((screen_width / 3) *1.2);
        //holder.iv_cake_image.getLayoutParams().height = (int)  ((screen_width / 2) *1.5);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return al_my_photos.size();
    }
}
