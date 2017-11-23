package kmitl.kawin58070006.horyuni.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import kmitl.kawin58070006.horyuni.R;
import kmitl.kawin58070006.horyuni.controller.fragment.HomeFragment;
import kmitl.kawin58070006.horyuni.controller.fragment.PostFragment;
import kmitl.kawin58070006.horyuni.controller.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private LinearLayout linearList;
    private LinearLayout linearPost;
    private LinearLayout linearZone;
    private ImageView imgList;
    private ImageView imgZone;
    private ImageView imgAddPost;
    private String username = "";
    private String uriProfile;
    private String uploadId;
    public static final String FB_Storage_Path = "image/";
    public static final String FB_Database_Path = "post";
    public static final String FB_Database_Path_User = "users";
    public static final int Request_Code = 1234;

    //private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        SharedPreferences sharedpreferences = getSharedPreferences("shareUploadId", Context.MODE_PRIVATE);
        uploadId = sharedpreferences.getString("uploadId", "");
        username = sharedpreferences.getString("username", "");
        uriProfile= sharedpreferences.getString("uriProfile", "");
        linearList = findViewById(R.id.linearList);
        linearPost = findViewById(R.id.linearPost);
        linearZone = findViewById(R.id.linearZone);
        imgList = findViewById(R.id.imgList);
        imgZone = findViewById(R.id.imgZone);
        imgAddPost = findViewById(R.id.imgAddPost);
        //firebaseAuth = FirebaseAuth.getInstance();
        linearList.setOnClickListener(this);
        linearPost.setOnClickListener(this);
        linearZone.setOnClickListener(this);
        if (AccessToken.getCurrentAccessToken() == null) {
            goToActivity();
            //initialFragment();

        } else {
            initialFragment();


        }
        Toast.makeText(getApplicationContext(), "Main : " + username + " >>> " + uploadId, Toast.LENGTH_SHORT).show();

    }

    private void initialFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, HomeFragment.newInstance())
                .commit();
    }

    //Menubar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_logout) {
            logout();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (linearList.getId() == view.getId()) {

            imgList.setImageResource(R.drawable.ic_home_blue);
            imgZone.setImageResource(R.drawable.ic_lotion_on_black);
            imgAddPost.setImageResource(R.drawable.ic_add_black);
            goToFragment(HomeFragment.newInstance());
        }
        else if (linearPost.getId() == view.getId()){
            imgAddPost.setImageResource(R.drawable.ic_add_blue);
            imgZone.setImageResource(R.drawable.ic_lotion_on_black);
            imgList.setImageResource(R.drawable.ic_action_name);
            goToFragment(PostFragment.newInstance(username, uriProfile));
        }
        else if (linearZone.getId() == view.getId()) {

            imgList.setImageResource(R.drawable.ic_action_name);
            imgZone.setImageResource(R.drawable.ic_location_on_blue);
            imgAddPost.setImageResource(R.drawable.ic_add_black);
            goToFragment(SearchFragment.newInstance());
        }
    }

    private void goToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void goToActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("logout", true);
        startActivity(intent);
        finish();
    }

    private void logout() {
        //firebaseAuth.signOut();
        LoginManager.getInstance().logOut();

        //storageReference = FirebaseStorage.getInstance().getReference();
        //databaseReference = FirebaseDatabase.getInstance().getReference(FB_Database_Path_User);
        //databaseReference.child(uploadId).removeValue();
        SharedPreferences sharedpreferences = getSharedPreferences("shareUploadId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        goToActivity();
    }
}
