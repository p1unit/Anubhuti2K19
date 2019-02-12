package com.anubhuti.knit.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anubhuti.knit.Migration.UserMigration;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.anubhuti.knit.Utils.Config;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
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
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private GoogleSignInOptions gso;
    private GoogleSignInClient signInClient;
    private CardView googleSignIn;
    private Integer RC_SIGN_IN =100;
    private CardView facebookloginButton;
    private CallbackManager callbackManager;
    private UserMigration userMigration;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        userMigration=new UserMigration();

        googleSignIn= this.findViewById(R.id.google_sign_in);
        googleSignIn.setOnClickListener(this);
        facebookloginButton=this.findViewById(R.id.facebook_sign_in);
        facebookloginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        pd=new ProgressDialog(this);
        pd.setMessage("Please Wait for a Sec");
        pd.setCancelable(false);
        pd.show();

        switch (view.getId()){
            case R.id.google_sign_in:
                statGoogleLogin();
                break;
            case R.id.facebook_sign_in:
                statFacebookLogin();
                break;

        }

    }

    private void statFacebookLogin() {


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                String s=loginResult.getAccessToken().getUserId().toString();
                Config.toastShort(ApplicationContextProvider.getContext(),"Loging you in Please Wait");
            }


            @Override
            public void onCancel() {
                Config.toastShort(getApplicationContext(), Config.FACEBOOK_CANCEL_TOAST);
            }

            @Override
            public void onError(FacebookException error) {

                Config.toastShort(getApplicationContext(), Config.ERROR_TOAST);
            }
        });

    }


    private void statGoogleLogin() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        signInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        pd.dismiss();

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else {

            callbackManager.onActivityResult(requestCode,resultCode,data);
            boolean loggedIn = AccessToken.getCurrentAccessToken() == null;

            if (!loggedIn){
                Config.toastShort(this,"Welcome to our Castle");
                loginDone();
            }else {
                Config.toastShort(this,Config.ERROR_TOAST);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Config.toastShort(this,"Welcome to our Castle");
            loginDone();
        } catch (ApiException e) {
            Config.toastShort(this,Config.ERROR_TOAST);
            Config.logE("signInResult:failed",String.valueOf(e.getStatusCode()));

        }
    }

    private void loginDone() {

        userMigration.setuserLogin();

        Intent intent=new Intent(this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().freeMemory();
    }

}
