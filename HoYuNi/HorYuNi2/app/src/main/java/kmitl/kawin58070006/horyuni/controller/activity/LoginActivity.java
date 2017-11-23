package kmitl.kawin58070006.horyuni.controller.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;

import kmitl.kawin58070006.horyuni.R;
import kmitl.kawin58070006.horyuni.model.User;

import static android.content.SharedPreferences.*;

public class LoginActivity extends AppCompatActivity {
    private LoginButton loginButton;
    private SharedPreferences sharedPreferences;
    private CallbackManager callbackManager;
    private ProgressDialog progressDialog;
    //private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private User user;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private String uploadId;
    public static final String FB_Database_Path_User = "users";
    private AccessToken accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
//        if (getIntent().hasExtra("logout")){
//            LoginManager.getInstance().logOut();
//        }
        accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null) {
            connectionWithFacebook();
        }

        loginButton = findViewById(R.id.loginButton);
        loginButton.setReadPermissions(Arrays.asList("email"));


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //handleFacebookAccesToken(loginResult.getAccessToken());
                connectionWithFacebook();



            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "cancel login", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "error login", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void connectionWithFacebook() {

        //progress.show(getSupportFragmentManager(), "progress");
        boolean isError = false;

        try {
            Profile profile = Profile.getCurrentProfile();
            user = new User(uploadId, profile.getName(), profile.getProfilePictureUri(35, 35).toString());
            SharedPreferences sharedpreferences = getSharedPreferences("shareUploadId", Context.MODE_PRIVATE);
            Editor editor = sharedpreferences.edit();
            editor.putString("uploadId", uploadId);
            editor.putString("username", profile.getName());
            editor.putString("uriProfile", profile.getProfilePictureUri(35,35).toString());
            editor.commit();

            goMainScreen(profile.getName(), profile.getProfilePictureUri(35, 35).toString(), uploadId);


        } catch (NullPointerException ex) {

            Toast.makeText(LoginActivity.this, "กรุณาเชื่อมต่อ facebook อีกครั้ง", Toast.LENGTH_SHORT).show();
            isError = true;
        } catch (Exception ex) {

            Toast.makeText(LoginActivity.this, "กรุณาเชื่อมต่อ facebook อีกครั้ง", Toast.LENGTH_SHORT).show();
            isError = true;
        }

        if(isError){
            LoginManager.getInstance().logOut();
            //progress.dismiss();
        }
    }



    private void goMainScreen(String username, String uriProfile, String uploadId) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("username", username);
        intent.putExtra("uriProfile", uriProfile);
        intent.putExtra("uploadId", uploadId);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
