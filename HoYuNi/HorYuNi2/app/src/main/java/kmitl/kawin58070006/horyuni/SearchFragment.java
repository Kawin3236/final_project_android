package kmitl.kawin58070006.horyuni;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    private ImageView zoneK1;
    private ImageView zoneK2;
    private ImageView zoneK3;
    private ImageView zoneWP;
    private ImageView zoneJL;
    private ImageView zoneHM;
    private ImageView zoneRNP;
    private ImageView zonePapaMama;
    private ImageView zoneKS;
    private TextView numK1;
    private TextView numK2;
    private TextView numK3;
    private TextView numWP;
    private TextView numJL;
    private TextView numHM;
    private TextView numRNP;
    private TextView numPapaMama;
    private TextView numKS;


    private DatabaseReference mDatabaseRef;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        zoneK1 = rootView.findViewById(R.id.ZoneK1);
        zoneK2 = rootView.findViewById(R.id.ZoneK2);
        zoneK3 = rootView.findViewById(R.id.ZoneK3);
        zoneWP = rootView.findViewById(R.id.ZoneWP);
        zoneJL = rootView.findViewById(R.id.ZoneKJL);
        zoneHM = rootView.findViewById(R.id.ZoneHM);
        zoneRNP = rootView.findViewById(R.id.ZoneRNP);
        zonePapaMama = rootView.findViewById(R.id.ZonePapaMama);
        zoneKS = rootView.findViewById(R.id.ZoneKS);

        numK1 = rootView.findViewById(R.id.numK1);
        numK2 = rootView.findViewById(R.id.numK2);
        numK3 = rootView.findViewById(R.id.numK3);
        numWP = rootView.findViewById(R.id.numWP);
        numJL = rootView.findViewById(R.id.numJL);
        numHM = rootView.findViewById(R.id.numHM);
        numRNP = rootView.findViewById(R.id.numRNP);
        numPapaMama = rootView.findViewById(R.id.numPapaMama);
        numKS = rootView.findViewById(R.id.numKS);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.FB_Database_Path);
        countZone("เกกี1", numK1);
        countZone("เกกี2", numK2);
        countZone("เกกี3", numK3);
        countZone("Workpoint", numWP);
        countZone("เจ๊เล๊ง", numJL);
        countZone("ซอยหอใหม่", numHM);
        countZone("RNP", numRNP);
        countZone("ปาป๊ามาม๊า", numPapaMama);
        countZone("กลางสวน", numKS);

        zoneK1.setOnClickListener(this);
        zoneK2.setOnClickListener(this);
        zoneK3.setOnClickListener(this);
        zoneWP.setOnClickListener(this);
        zoneJL.setOnClickListener(this);
        zoneHM.setOnClickListener(this);
        zoneRNP.setOnClickListener(this);
        zonePapaMama.setOnClickListener(this);
        zoneKS.setOnClickListener(this);

        return rootView;
    }

    private void countZone(String zone, final TextView numZone) {
        mDatabaseRef.orderByChild("zone").equalTo(zone).addValueEventListener(new ValueEventListener() {
            int num = 0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    num++;
                    numZone.setText(String.valueOf(num));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(":::::::1111111");
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (zoneK1.getId() == v.getId()) {
            goToDetail("เกกี1", numK1);
        } else if (zoneK2.getId() == v.getId()) {
            goToDetail("เกกี่2", numK2);
        } else if (zoneK3.getId() == v.getId()) {
            goToDetail("เกกี3", numK3);
        } else if (zoneWP.getId() == v.getId()) {
            goToDetail("Workpoint", numWP);
        } else if (zoneJL.getId() == v.getId()) {
            goToDetail("เจ๊เล๊ง", numJL);
        } else if (zoneHM.getId() == v.getId()) {
            goToDetail("ซอยหอใหม่", numHM);
        } else if (zoneRNP.getId() == v.getId()) {
            goToDetail("RNP", numRNP);
        } else if (zonePapaMama.getId() == v.getId()) {
            goToDetail("ปาป๊ามาม๊า", numPapaMama);
        } else if (zoneKS.getId() == v.getId()) {
            goToDetail("กลางสวน", numKS);
        }
    }

    private void goToDetail(String zone, TextView numZone) {
        if (Integer.parseInt(numZone.getText().toString()) == 0) {
            Toast.makeText(getContext(), zone + "ไม่มีหอว่าง", Toast.LENGTH_SHORT).show();
        } else
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, DetailSearchFragment.newInstance(zone)).addToBackStack(null).commit();
    }


}
