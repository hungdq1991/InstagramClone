package com.example.instagram_clone.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.instagram_clone.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class GridImageAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResource;
    private String mAppend;
    private ArrayList<String> imgURLs;

    public GridImageAdapter(Context context
                        , int layoutResource
                        , String mAppend
                        , ArrayList<String> imgURLs) {
        super(context, layoutResource, imgURLs);
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.layoutResource = layoutResource;
        this.mAppend = mAppend;
        this.imgURLs = imgURLs;
    }

    private static class ViewHolder{
        ImageView profileImage;
        ProgressBar mProgressBar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;

        /**
         * ViewHolder build pattern (Similar to recyclerview)
         */
        if(convertView == null) {
            convertView = mInflater.inflate( layoutResource, parent, false );
            holder = new ViewHolder();
            holder.mProgressBar = (ProgressBar) convertView.findViewById( R.id.gridImageProgressbar );
            holder.profileImage = (ImageView) convertView.findViewById( R.id.gridImageView );

            convertView.setTag( holder );
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String imgURL = getItem( position );

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage( mAppend + imgURL, holder.profileImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(holder.mProgressBar != null) {
                    holder.mProgressBar.setVisibility( View.VISIBLE );
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(holder != null) {
                    holder.mProgressBar.setVisibility( View.GONE );
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(holder != null) {
                    holder.mProgressBar.setVisibility( View.GONE );
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(holder != null) {
                    holder.mProgressBar.setVisibility( View.GONE );
                }
            }
        } );

        return convertView;
    }
}
