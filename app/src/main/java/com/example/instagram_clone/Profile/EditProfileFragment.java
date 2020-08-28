package com.example.instagram_clone.Profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.instagram_clone.R;
import com.example.instagram_clone.Utils.UniversalImageLoader;

public class EditProfileFragment extends Fragment {
    private static final String TAG = "EditProfileFragment";

    private ImageView mProfilePhoto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d( TAG, "onCreateView: started." );
        View view = inflater.inflate( R.layout.fragment_editprofile, container, false );
        mProfilePhoto = (ImageView) view.findViewById( R.id.profile_photo );

        setProfileImage();

        //Back arrow for navigating back to "Profile Activity"
        ImageView backArrow = (ImageView) view.findViewById( R.id.backArrow );
        backArrow.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "onClick: navigating back to Profile Activity." );
                getActivity().finish();
            }
        } );

        return view;
    }

    private void setProfileImage(){
        Log.d( TAG, "setProfileImage: setting profile image." );
        String imgURL = "drawable://" + R.drawable.profile_photo;
        UniversalImageLoader.setImage( imgURL, mProfilePhoto, null,"" );
    }
}
