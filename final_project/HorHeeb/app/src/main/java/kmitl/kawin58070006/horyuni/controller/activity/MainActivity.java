package kmitl.kawin58070006.horyuni.controller.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import kmitl.kawin58070006.horyuni.controller.fragment.MyPostFragment;
import kmitl.kawin58070006.horyuni.controller.fragment.PostFragment;
import kmitl.kawin58070006.horyuni.controller.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout linearList;
    private LinearLayout linearPost;
    private LinearLayout linearZone;
    private ImageView imgList;
    private ImageView imgZone;
    private ImageView imgAddPost;
    private ImageView imgProfile;
    private String username = "";
    private String uriProfile;
    private String uploadId;
    public static final String FB_Database_Path = "post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        SharedPreferences sharedpreferences = getSharedPreferences("shareUploadId", Context.MODE_PRIVATE);
        uploadId = sharedpreferences.getString("uploadId", "");
        username = sharedpreferences.getString("username", "");
        uriProfile = sharedpreferences.getString("uriProfile", "");
        linearList = findViewById(R.id.linearList);
        linearPost = findViewById(R.id.linearPost);
        linearZone = findViewById(R.id.linearZone);
        imgProfile = findViewById(R.id.imgProfile);
        Glide.with(this).load(uriProfile).into(imgProfile);
        imgList = findViewById(R.id.imgList);
        imgZone = findViewById(R.id.imgZone);
        imgAddPost = findViewById(R.id.imgAddPost);
        linearList.setOnClickListener(this);
        linearPost.setOnClickListener(this);
        linearZone.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        if (AccessToken.getCurrentAccessToken() == null) {
            goToActivity();
        } else {
            initialFragment();
        }
    }

    private void initialFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, HomeFragment.newInstance())
                .commit();
    }


    @Override
    public void onClick(View view) {
        if (linearList.getId() == view.getId()) {
            imgList.setImageResource(R.drawable.ic_home_blue);
            imgZone.setImageResource(R.drawable.ic_lotion_on_black);
            imgAddPost.setImageResource(R.drawable.ic_add_black);
            goToFragment(HomeFragment.newInstance());
        } else if (linearPost.getId() == view.getId()) {
            imgAddPost.setImageResource(R.drawable.ic_add_blue);
            imgZone.setImageResource(R.drawable.ic_lotion_on_black);
            imgList.setImageResource(R.drawable.ic_action_name);
            goToFragment(PostFragment.newInstance(username, uriProfile));
        } else if (linearZone.getId() == view.getId()) {
            imgList.setImageResource(R.drawable.ic_action_name);
            imgZone.setImageResource(R.drawable.ic_location_on_blue);
            imgAddPost.setImageResource(R.drawable.ic_add_black);
            goToFragment(SearchFragment.newInstance());
        } else if (imgProfile.getId() == view.getId()) {
            dialog();
        }
    }

    private void goToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
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
        LoginManager.getInstance().logOut();
        SharedPreferences sharedpreferences = getSharedPreferences("shareUploadId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        goToActivity();
    }

    public void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new CharSequence[]{"โพสต์ของฉัน", "ออกจากระบบ", "ยกเลิก"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                imgAddPost.setImageResource(R.drawable.ic_add_black);
                                imgZone.setImageResource(R.drawable.ic_lotion_on_black);
                                imgList.setImageResource(R.drawable.ic_action_name);
                                getSupportFragmentManager().beginTransaction()
                                        .add(R.id.fragmentContainer, MyPostFragment.newInstance(username))
                                        .addToBackStack(null)
                                        .commit();
                                break;
                            case 1:
                                logout();
                                dialog.dismiss();
                                break;
                            case 2:
                                dialog.dismiss();
                        }
                    }
                });
        builder.show();
    }
}
