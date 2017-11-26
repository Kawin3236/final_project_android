package kmitl.kawin58070006.horyuni.controller.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import kmitl.kawin58070006.horyuni.R;
import kmitl.kawin58070006.horyuni.adapter.PostAdapter;
import kmitl.kawin58070006.horyuni.controller.activity.MainActivity;
import kmitl.kawin58070006.horyuni.model.Detail;
import kmitl.kawin58070006.horyuni.model.ImageUpload;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailSearchFragment extends Fragment {
    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imgList;
    private ProgressDialog progressDialog;
    private static String nameZone;

    public DetailSearchFragment() {
        // Required empty public constructor
    }

    public static DetailSearchFragment newInstance(String zone) {

        Bundle args = new Bundle();
        setZone(zone);
        DetailSearchFragment fragment = new DetailSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void setZone(String zone) {
        DetailSearchFragment.nameZone = zone;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_detail_search, container, false);

        imgList = new ArrayList<>();

        //Show progress dialog during list image loading
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait loading list image...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.FB_Database_Path);
        mDatabaseRef.orderByChild("zone").equalTo(nameZone).addValueEventListener(new ValueEventListener() {
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

                PostAdapter postAdaptet = new PostAdapter(getActivity(), getActivity());
                postAdaptet.setData(imgList);
                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.listViewImage3);
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

}
