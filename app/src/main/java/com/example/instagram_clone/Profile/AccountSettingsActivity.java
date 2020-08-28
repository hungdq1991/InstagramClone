package com.example.instagram_clone.Profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.instagram_clone.R;
import com.example.instagram_clone.Utils.BottomNavigationViewHelper;
import com.example.instagram_clone.Utils.SectionsStatePagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class AccountSettingsActivity extends AppCompatActivity {
    private static final String TAG = "AccountSettingsActivity";
    private static final int ACTIVITY_NUM = 4;

    private Context mContext;

    private SectionsStatePagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_accountsettings);
        mContext = AccountSettingsActivity.this;
        mViewPager = (ViewPager) findViewById( R.id.container );
        mRelativeLayout = (RelativeLayout) findViewById( R.id.reLayout1 );
        Log.d( TAG, "onCreate: started" );

        setupSettingList();
        setupBottomNavigationView();
        setupFragments();

        //setup the backArrow for navigating back to "Profile Activity"
        ImageView backArrow = (ImageView) findViewById( R.id.backArrow );
        backArrow.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "onClick: navigating back to 'ProfileActivity'" );
                finish();
            }
        } );
    }

    private void setupFragments(){
        Log.d( TAG, "setupFragments: fragment0, fragment1" );
        pagerAdapter = new SectionsStatePagerAdapter( getSupportFragmentManager() );
        pagerAdapter.addFragment( new EditProfileFragment(), getString(R.string.edit_profile_fragment ) );  //Fragment 0
        pagerAdapter.addFragment( new SignOutFragment(), getString(R.string.sign_out_fragment ) );          //Fragment 1
    }

    private void setViewPager(int fragmentNumber)
    {
        mRelativeLayout.setVisibility( View.GONE );
        Log.d( TAG, "setViewPage: navigating to fragment #: " + fragmentNumber);
        mViewPager.setAdapter( pagerAdapter );
        mViewPager.setCurrentItem( fragmentNumber );
    }

    private void setupSettingList()
    {
        Log.d( TAG, "setupSettingList: initializing 'Account Settings' list." );
        ListView listView = (ListView) findViewById( R.id.lvAccountSettings );

        ArrayList<String> options = new ArrayList<>(  );
        options.add( getString( R.string.edit_profile_fragment ) );   //Fragment 0
        options.add( getString( R.string.sign_out_fragment ) );       //Fragment 1

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, options);
        listView.setAdapter( adapter );

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d( TAG, "onItemClick: navigating to fragment #:" + position );
                setViewPager( position );
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
