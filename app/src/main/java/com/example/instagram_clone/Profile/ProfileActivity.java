package com.example.instagram_clone.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.instagram_clone.R;
import com.example.instagram_clone.Utils.BottomNavigationViewHelper;
import com.example.instagram_clone.Utils.GridImageAdapter;
import com.example.instagram_clone.Utils.UniversalImageLoader;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 4;
    private static final int NUM_GRID_COLUMNS = 3;

    private Context mContext = ProfileActivity.this;

    private ProgressBar mProgressBar;
    private ImageView profilePhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );
        Log.d( TAG, "onCreate: started" );

        setupBottomNavigationView();
        setupToolbar();
        setupActivityWidgets();
        settingProfileImage();

        tempGridSetup();
    }

    private void tempGridSetup(){
        ArrayList<String> imgURLs = new ArrayList<>(  );
        imgURLs.add( "drawable://" + R.drawable.a_1 );
        imgURLs.add( "drawable://" + R.drawable.a_2 );
        imgURLs.add( "drawable://" + R.drawable.a_3 );
        imgURLs.add( "drawable://" + R.drawable.a_4 );
        imgURLs.add( "drawable://" + R.drawable.a_4 );
        imgURLs.add( "drawable://" + R.drawable.a_3 );
        imgURLs.add( "drawable://" + R.drawable.a_2 );
        imgURLs.add( "drawable://" + R.drawable.a_1 );
        imgURLs.add( "drawable://" + R.drawable.a_1 );
        imgURLs.add( "drawable://" + R.drawable.a_2 );
        imgURLs.add( "drawable://" + R.drawable.a_3 );
        imgURLs.add( "drawable://" + R.drawable.a_4 );
        imgURLs.add( "drawable://" + R.drawable.a_4 );
        imgURLs.add( "drawable://" + R.drawable.a_3 );
        imgURLs.add( "drawable://" + R.drawable.a_2 );
        imgURLs.add( "drawable://" + R.drawable.a_1 );

        setupImageGrid( imgURLs );
    }

    private void setupImageGrid(ArrayList<String> imgURLs)
    {
        GridView gridView = (GridView) findViewById( R.id.gridView );

        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth / NUM_GRID_COLUMNS;

        gridView.setColumnWidth( imageWidth );
        GridImageAdapter adapter = new GridImageAdapter( mContext, R.layout.layout_grid_imageview, "", imgURLs);
        gridView.setAdapter( adapter );
    }

    private void settingProfileImage()
    {
        Log.d( TAG, "settingProfileImage: setting profile photo." );
        String imgURL = "drawable://" + R.drawable.profile_photo;
        UniversalImageLoader.setImage( imgURL, profilePhoto, mProgressBar, "" );
    }

    private void setupActivityWidgets()
    {
        Log.d( TAG, "setupActivityWidgets: settingView." );
        mProgressBar = (ProgressBar) findViewById( R.id.profileProgressBar );
        mProgressBar.setVisibility( View.GONE );
        profilePhoto = (ImageView) findViewById( R.id.profile_photo );
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById( R.id.profileToolBar );
        setSupportActionBar( toolbar );

        ImageView profileMenu = (ImageView) findViewById( R.id.imageProfileMenu );
        profileMenu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "onClick: navigating to account settings" );
                Intent intent = new Intent( mContext, AccountSettingsActivity.class );
                startActivity( intent );
            }
        } );
    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById( R.id.bottomNavViewBar );
        BottomNavigationViewHelper.setupBottomNavigationView( bottomNavigationViewEx );
        BottomNavigationViewHelper.enableNavigation( mContext, bottomNavigationViewEx );
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem( ACTIVITY_NUM );
        menuItem.setChecked( true );
    }
}
