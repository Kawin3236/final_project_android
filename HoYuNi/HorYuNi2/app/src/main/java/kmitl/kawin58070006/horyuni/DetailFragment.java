package kmitl.kawin58070006.horyuni;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kmitl.kawin58070006.horyuni.model.Detail;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private TextView name;
    private TextView nameZone;
    private TextView moreDetail;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private static Detail detail;


    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Detail detail) {
        Bundle args = new Bundle();
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        setDetail(detail);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        name = rootView.findViewById(R.id.textnameDetail);
        name.setText(detail.getName());
        nameZone = rootView.findViewById(R.id.txtDZone);
        nameZone.setText(detail.getZone());
        moreDetail = rootView.findViewById(R.id.textMoreDetail);
        moreDetail.setText(detail.getMoreDetail());

        imageView1 = rootView.findViewById(R.id.imageDetail1);
        Glide.with(getActivity()).load(detail.getImg().getUrl()).into(imageView1);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("this is image");
                builder.create();
                builder.show();
            }
        });

        imageView2 = rootView.findViewById(R.id.imageDetail2);
        Glide.with(getActivity()).load(detail.getImg().getUrl2()).into(imageView2);
        imageView3 = rootView.findViewById(R.id.imageDetail3);
        Glide.with(getActivity()).load(detail.getImg().getUrl3()).into(imageView3);
        imageView4 = rootView.findViewById(R.id.imageDetail4);
        Glide.with(getActivity()).load(detail.getImg().getUrl4()).into(imageView4);
        imageView5 = rootView.findViewById(R.id.imageDetail5);
        Glide.with(getActivity()).load(detail.getImg().getUrl5()).into(imageView5);
        imageView6 = rootView.findViewById(R.id.imageDetail6);
        Glide.with(getActivity()).load(detail.getImg().getUrl6()).into(imageView6);
        return rootView;


    }


    public static void setDetail(Detail detail) {
       DetailFragment.detail = detail;
    }
}
