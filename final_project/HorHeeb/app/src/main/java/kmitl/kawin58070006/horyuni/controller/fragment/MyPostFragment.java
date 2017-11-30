package kmitl.kawin58070006.horyuni.controller.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kmitl.kawin58070006.horyuni.R;
import kmitl.kawin58070006.horyuni.adapter.MyPostItemAdapter;
import kmitl.kawin58070006.horyuni.controller.activity.MainActivity;
import kmitl.kawin58070006.horyuni.model.ImageUpload;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPostFragment extends Fragment {
    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imgList;

    private ProgressDialog progressDialog;


    private static String username;

    public MyPostFragment() {
        // Required empty public constructor
    }

    public static MyPostFragment newInstance(String username) {

        Bundle args = new Bundle();
        setUsername(username);
        MyPostFragment fragment = new MyPostFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_my_post, container, false);
        imgList = new ArrayList<>();

        //Show progress dialog during list image loading
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait loading list image...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.FB_Database_Path);
        mDatabaseRef.orderByChild("username").equalTo(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                imgList.clear();
                //Fetch image data from firebase database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor
                    ImageUpload img = snapshot.getValue(ImageUpload.class);
                    imgList.add(img);
                }
                //Init adapter
                Collections.reverse(imgList);

                MyPostItemAdapter postAdaptet = new MyPostItemAdapter(getActivity(), getActivity());
                postAdaptet.setData(imgList);
                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.listViewImage2);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(postAdaptet);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });

        return rootView;
    }

    public static void setUsername(String username) {
        MyPostFragment.username = username;
    }

}
