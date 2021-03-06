package kmitl.kawin58070006.horyuni.controller.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class HomeFragment extends Fragment {
    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imgList;

    private ProgressDialog progressDialog;
    private Detail detail;



    public HomeFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        //lv = (ListView) rootView.findViewById(R.id.listViewImage);
        //Show progress dialog during list image loading
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("กำลังโหลดข้อมูล");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){}
                return true;
            }
        });
        imgList = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.FB_Database_Path);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
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
               try {
                   Collections.reverse(imgList);

                   if (getActivity() != null) {
                       //adapter = new ImageListAdapter(getActivity(), R.layout.image_item, imgList, getActivity());
                       //Set adapter for listview
                       PostAdapter postAdaptet = new PostAdapter(getActivity(), getActivity());
                       postAdaptet.setData(imgList);
                       RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.listViewImage);
                       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                       //lv.setAdapter(adapter);
                       recyclerView.setAdapter(postAdaptet);
                   }
               }catch (Exception e){

               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        return rootView;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

}
