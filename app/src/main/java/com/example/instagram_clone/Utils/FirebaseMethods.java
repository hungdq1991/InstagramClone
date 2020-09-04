package com.example.instagram_clone.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.instagram_clone.R;
import com.example.instagram_clone.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseMethods {
    private static final String TAG = "FirebaseMethods";

    private Context mContext;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;

    public FirebaseMethods(Context mContext) {
        this.mContext = mContext;
        this.mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null)
        {
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public boolean checkIfUsernameExists(String username, DataSnapshot dataSnapshot)
    {
        Log.d( TAG, "checkIfUsernameExists: check if " + username + "already exists." );

        User user = new User();

        for(DataSnapshot ds:dataSnapshot.getChildren())
        {
            Log.d( TAG, "checkIfUsernameExists: dataSnapshot: " + ds);

            user.setUsername( ds.getValue(User.class).getUsername() );
            Log.d( TAG, "checkIfUsernameExists: dataSnapshot: " + user.getUsername());

            if(StringManipulation.expandUsername( user.getUsername()).equals( username ))
            {
                Log.d( TAG, "checkIfUsernameExists: FOUND A MATCH: " + user.getUsername());
                return true;
            }
        }
        return false;
    }

    /**
     * Register a new email and password to Firebase Authentication
     * @param email
     * @param password
     * @param username
     */
    public void registerNewEmail(final String email, String password, String username){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(mContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                }
            });
    }

    public void addNewUser(String email, String username, String description, String website, String profile_photo)
    {
        User user = new User(userID, 1, email, StringManipulation.condenseUsername( username ));

        myRef.child( mContext.getString( R.string.dbname_user) )
                .child( userID )
                .setValue( user );
    }
}
