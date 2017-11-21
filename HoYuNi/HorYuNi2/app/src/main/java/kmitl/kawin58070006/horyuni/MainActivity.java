package kmitl.kawin58070006.horyuni;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import static android.graphics.Color.parseColor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ImageView imageView;
    private EditText editText;
    private Uri imguri;
    private Button btnSearchList;
    private Button btnSearchZone;
    private String username;
    public static final String FB_Storage_Path = "image/";
    public static final String FB_Database_Path = "post";
    public static final int Request_Code = 1234;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        btnSearchList = findViewById(R.id.btnHome);
        btnSearchZone = findViewById(R.id.btnSearch);
        firebaseAuth = FirebaseAuth.getInstance();
        btnSearchList.setOnClickListener(this);
        btnSearchZone.setOnClickListener(this);
        if (AccessToken.getCurrentAccessToken() == null) {
            goToActivity();
            //initialFragment();

        } else {
            Intent intent = getIntent();
            username = intent.getStringExtra("username");
            initialFragment();
        }
        Toast.makeText(getApplicationContext(),"Main : "+username, Toast.LENGTH_SHORT).show();

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
            return true;
        }
        if (id == R.id.id_post) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, PostFragment.newInstance(username))
                    .addToBackStack(null)
                    .commit();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (btnSearchList.getId() == view.getId()) {
            btnSearchList.setBackgroundColor(parseColor("#5eb4aa"));
            btnSearchZone.setBackgroundColor(parseColor("#3a7069"));
            goToFragment(HomeFragment.newInstance());
        } else if (btnSearchZone.getId() == view.getId()) {

            btnSearchZone.setBackgroundColor(parseColor("#5eb4aa"));
            btnSearchList.setBackgroundColor(parseColor("#3a7069"));
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
        firebaseAuth.signOut();
        LoginManager.getInstance().logOut();
        goToActivity();

    }
}
