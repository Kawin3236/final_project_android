package kmitl.kawin58070006.horyuni;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.Arrays;

import kmitl.kawin58070006.horyuni.model.User;

import static android.content.SharedPreferences.*;
import static kmitl.kawin58070006.horyuni.MainActivity.FB_Database_Path_User;

public class LoginActivity extends AppCompatActivity {
    private LoginButton loginButton;
    private SharedPreferences sharedPreferences;
    private CallbackManager callbackManager;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private User user;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private String uploadId;
    public static final String FB_Database_Path_User = "users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        Toast.makeText(getApplicationContext(), "this is login", Toast.LENGTH_SHORT).show();
        callbackManager = CallbackManager.Factory.create();
//        if (getIntent().hasExtra("logout")){
//            LoginManager.getInstance().logOut();
//        }

        loginButton = findViewById(R.id.loginButton);
        loginButton.setReadPermissions(Arrays.asList("email"));


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccesToken(loginResult.getAccessToken());

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
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    storageReference = FirebaseStorage.getInstance().getReference();
                    databaseReference = FirebaseDatabase.getInstance().getReference(FB_Database_Path_User);
                    String uploadId = databaseReference.push().getKey();
                    Profile profile = Profile.getCurrentProfile();
                    user = new User(uploadId, profile.getName(), profile.getProfilePictureUri(35, 35).toString());
                    databaseReference.child(uploadId).setValue(user);
                    SharedPreferences sharedpreferences = getSharedPreferences("shareUploadId", Context.MODE_PRIVATE);
                    Editor editor = sharedpreferences.edit();
                    editor.putString("uploadId", uploadId);
                    editor.putString("username", profile.getName());
                    editor.putString("uriProfile", profile.getProfilePictureUri(35,35).toString());
                    editor.commit();

                    goMainScreen(profile.getName(), profile.getProfilePictureUri(35, 35).toString(), uploadId);
                }


            }
        };
    }

    private void handleFacebookAccesToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait.");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "firebase_error_login", Toast.LENGTH_SHORT).show();
                        } else progressDialog.dismiss();
                    }
                });
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
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
