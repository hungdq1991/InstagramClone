package com.example.instagram_clone.Login;

import android.content.Context;
import android.content.Intent;
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

import com.example.instagram_clone.Home.HomeActivity;
import com.example.instagram_clone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    //Firebase
    private FirebaseAuth mAuth;

    private Context mContext;
    private ProgressBar mProgressBar;
    private EditText mEmail, mPassword;
    private TextView mPleaseWait, linkSignUp;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        Log.d( TAG, "onCreate: started." );

        mContext = LoginActivity.this;

        mProgressBar = (ProgressBar) findViewById( R.id.progressBar );
        mPleaseWait = (TextView) findViewById( R.id.pleaseWait );
        mEmail = (EditText) findViewById( R.id.input_email );
        mPassword = (EditText) findViewById( R.id.input_password );
        btnLogin = (Button) findViewById( R.id.btn_login );
        linkSignUp = (TextView) findViewById( R.id.link_signup );

        mPleaseWait.setVisibility( View.GONE );
        mProgressBar.setVisibility( View.GONE );

        setupFirebaseAuth();
        init();
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

    /**
     * ------------------------------------------Firebase------------------------------------------
     */
    private void init()
    {
        //Initialize the button for logging in
        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "onClick: attempting to log in." );

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if(!isStringNull(email) && !isStringNull(password))
                {
                    Log.d( TAG, "onClick: " + email);
                    Log.d( TAG, "onClick: " + password);
                    Toast.makeText(mContext, "Fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    showProgressBar();
                    // [START sign_in_with_email]
                    mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.d(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(mContext, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                hideProgressBar();
                            }
                        });
                    // [END sign_in_with_email]
                }
            }
        } );

        linkSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "onClick: navigating to register screen" );
                Intent intent = new Intent( LoginActivity.this, RegisterActivity.class );
                startActivity( intent );
            }
        } );

        /* If the user is logged in then navigate to Home Activity and call 'finish()' */
        if(mAuth.getCurrentUser() != null)
        {
            Log.d( TAG, "init: user is logged" );
            Intent intent = new Intent( mContext, HomeActivity.class );
            startActivity( intent );
            finish();
        }
    }

    private void showProgressBar()
    {
        mProgressBar.setVisibility( View.VISIBLE );
        mPleaseWait.setVisibility( View.VISIBLE );
    }

    private void hideProgressBar()
    {
        mProgressBar.setVisibility( View.GONE );
        mPleaseWait.setVisibility( View.GONE );
    }

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

    private void updateUI(FirebaseUser user) {
        if (user != null) {

        } else {
            Intent intent = new Intent( mContext, LoginActivity.class );
            startActivity( intent );
        }
    }
}
