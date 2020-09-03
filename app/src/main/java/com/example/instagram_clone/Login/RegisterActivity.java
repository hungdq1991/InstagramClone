package com.example.instagram_clone.Login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.instagram_clone.R;
import com.example.instagram_clone.Utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private Context mContext;
    private String email, username, password;
    private EditText mEmail, mPassword, mUsername;
    private TextView loadingPleaseWait;
    private Button btnRegister;
    private ProgressBar mProgressBar;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        firebaseMethods = new FirebaseMethods( mContext );
        Log.d( TAG, "onCreate: started." );

        initWidgets();
        setupFirebaseAuth();
        init();
    }

    private void init(){
        btnRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();

                if(checkInputs( email, username, password ))
                {
                    showProgressBar();

                    firebaseMethods.registerNewEmail( email, password, username );
                }
            }
        } );
    }

    private boolean checkInputs(String email, String username, String password){
        Log.d( TAG, "checkInputs: checking inputs for null values." );
        if(email.equals( "" ) || username.equals( "" ) || password.equals( "" ))
        {
            Toast.makeText(mContext, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /***
     * Initialize the activity widgets
     */
    private void initWidgets()
    {
        Log.d( TAG, "initWWidgets: Initializing Widgets." );
        mEmail = (EditText) findViewById( R.id.input_email );
        mUsername = (EditText) findViewById( R.id.input_username );
        mPassword = (EditText) findViewById( R.id.input_password );
        btnRegister = (Button) findViewById( R.id.btn_register );
        mProgressBar = (ProgressBar) findViewById( R.id.progressbar );
        loadingPleaseWait = (TextView) findViewById( R.id.loadingPleaseWait );
        mContext = RegisterActivity.this;
        hideProgressBar();
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

        // [START initialize_database]
        // Initialize Firebase Database
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        // [END initialize_database]

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null)
        {
            myRef.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //1st check: Make sure the username is not already in use

                    //add new user to the database

                    //add new user account setting to the database
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            } );
        }
    }

    private boolean isStringNull(String string)
    {
        Log.d( TAG, "isStringNull: check string if null" );

        if(string.equals( "" ))
        {
            return true;
        } else {
            return false;
        }
    }

    private void showProgressBar()
    {
        mProgressBar.setVisibility( View.VISIBLE );
        loadingPleaseWait.setVisibility( View.VISIBLE );
    }

    private void hideProgressBar()
    {
        mProgressBar.setVisibility( View.GONE );
        loadingPleaseWait.setVisibility( View.GONE );
    }
}
