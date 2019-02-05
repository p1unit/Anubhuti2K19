package com.anubhuti.knit.Activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anubhuti.knit.R;
import com.anubhuti.knit.Utils.Config;
import com.anubhuti.knit.Utils.ViewProgress;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private GoogleSignInOptions gso;
    private GoogleSignInClient signInClient;
    private Button googleSignIn;
    private Integer RC_SIGN_IN =100;
    private LoginButton facebookloginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);


        googleSignIn= this.findViewById(R.id.google_sign_in);
        googleSignIn.setOnClickListener(this);
        facebookloginButton=(LoginButton)findViewById(R.id.facebook_sign_in);
         callbackManager = CallbackManager.Factory.create();
        facebookloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String s=loginResult.getAccessToken().getUserId().toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.google_sign_in:
//                ViewProgress viewProgress = new ViewProgress();
//                viewProgress.showDialog();
                 statGoogleLogin();

        }

    }



    private void statGoogleLogin() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        signInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else {
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Config.toastShort(this,"Success");
        } catch (ApiException e) {
            Config.logE("signInResult:failed",String.valueOf(e.getStatusCode()));

        }
    }

}
