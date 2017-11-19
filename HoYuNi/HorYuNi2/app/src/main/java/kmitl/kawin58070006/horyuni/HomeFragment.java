package kmitl.kawin58070006.horyuni;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kmitl.kawin58070006.horyuni.adapter.ImageListAdapter;
import kmitl.kawin58070006.horyuni.model.Detail;
import kmitl.kawin58070006.horyuni.model.ImageUpload;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imgList;
    private ListView lv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;
    private ImageView addPost;
    private Detail detail;
    private Button btnSearch;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        addPost = rootView.findViewById(R.id.addPost);
        btnSearch = rootView.findViewById(R.id.btnSearch);

        imgList = new ArrayList<>();
        lv = (ListView) rootView.findViewById(R.id.listViewImage);
        //Show progress dialog during list image loading
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait loading list image...");
        progressDialog.show();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.FB_Database_Path);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                //Fetch image data from firebase database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor
                    ImageUpload img = snapshot.getValue(ImageUpload.class);
                    imgList.add(img);
                }
                //Init adapter
                Collections.reverse(imgList);
                adapter = new ImageListAdapter(getActivity(), R.layout.image_item, imgList, getActivity());
                //Set adapter for listview
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        addPost.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        return rootView;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    @Override
    public void onClick(View view) {
        if (addPost.getId() == view.getId()) {
            goToFragment(PostFragment.newInstance());
        } else if (btnSearch.getId() == view.getId()) {
            goToFragment(SearchFragment.newInstance());
        }
    }

    private void goToFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}
