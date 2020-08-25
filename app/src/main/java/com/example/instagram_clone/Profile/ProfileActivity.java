package com.example.instagram_clone.Profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.instagram_clone.R;
import com.example.instagram_clone.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    private Context mContext = ProfileActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );
        Log.d( TAG, "onCreate: started" );

        setupBottomNavigationView();
        setupToolbar();
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById( R.id.profileToolBar );
        setSupportActionBar( toolbar );

        toolbar.setOnMenuItemClickListener( new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d( TAG, "onMenuItemClick: clicked menu item: " + item);

//                switch (item.getItemId()){
//                    case R.id.profileMenu:
//                    Log.d( TAG, "onMenuItemClick: Navigating to Profile Preferences." );
//                }
//
                return false;
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
    }
}
