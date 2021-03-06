package com.example.instagram_clone.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.instagram_clone.Login.LoginActivity;
import com.example.instagram_clone.R;
import com.example.instagram_clone.Utils.BottomNavigationViewHelper;
import com.example.instagram_clone.Utils.SectionsPagerAdapter;
import com.example.instagram_clone.Utils.UniversalImageLoader;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;

    private Context mContext = HomeActivity.this;

    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
        Log.d(TAG, "onCreate: starting.");

        setupFirebaseAuth();

        initImageLoader();
        setupBottomNavigationView();
        setupViewPager();
    }

    /**
     * ------------------------------------------Firebase------------------------------------------
     */
    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth()
    {
        Log.d( TAG, "setupFirebaseAuth: setting up firebase auth." );

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

        } else {
            Intent intent = new Intent( mContext, LoginActivity.class );
            startActivity( intent );
        }
    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader( mContext );
        ImageLoader.getInstance().init( universalImageLoader.getConfig() );
    }

    /**
     * Responsible for adding the 3 tabs: Camera, Home, Messages
     */
    private void setupViewPager()
    {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter( getSupportFragmentManager() );
        adapter.addFragment( new CameraFragment() );    //index 0
        adapter.addFragment( new HomeFragment() );      //index 1
        adapter.addFragment( new MessagesFragment() );  //index 2
        ViewPager viewPager = (ViewPager) findViewById( R.id.container );
        viewPager.setAdapter( adapter );

        TabLayout tabLayout = (TabLayout) findViewById( R.id.tabs );
        tabLayout.setupWithViewPager( viewPager );

        tabLayout.getTabAt( 0 ).setIcon( R.drawable.ic_camera );
        tabLayout.getTabAt( 1 ).setIcon( R.drawable.ic_action_name );
        tabLayout.getTabAt( 2 ).setIcon( R.drawable.ic_arrow );
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