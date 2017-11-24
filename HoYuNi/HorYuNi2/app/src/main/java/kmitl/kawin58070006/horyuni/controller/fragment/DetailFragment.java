package kmitl.kawin58070006.horyuni.controller.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;

import kmitl.kawin58070006.horyuni.R;
import kmitl.kawin58070006.horyuni.model.Detail;
import kmitl.kawin58070006.horyuni.model.Screenshot;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements View.OnClickListener {
    private TextView name;
    private TextView nameZone;
    private TextView moreDetail;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private ImageView imgMainDetail;
    private ImageView viewCapture;
    private LinearLayout btnShare;
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

        imgMainDetail = rootView.findViewById(R.id.imgMainDetail);
        setToImageView(detail.getImg().getUrl(), imgMainDetail);

        imageView1 = rootView.findViewById(R.id.imageDetail1);
        setToImageView(detail.getImg().getUrl(), imageView1);

        imageView2 = rootView.findViewById(R.id.imageDetail2);
        setToImageView(detail.getImg().getUrl2(), imageView2);

        imageView3 = rootView.findViewById(R.id.imageDetail3);
        setToImageView(detail.getImg().getUrl3(), imageView3);

        imageView4 = rootView.findViewById(R.id.imageDetail4);
        setToImageView(detail.getImg().getUrl4(), imageView4);

        imageView5 = rootView.findViewById(R.id.imageDetail5);
        setToImageView(detail.getImg().getUrl5(), imageView5);

        imageView6 = rootView.findViewById(R.id.imageDetail6);
        setToImageView(detail.getImg().getUrl6(), imageView6);

        viewCapture = rootView.findViewById(R.id.viewCapture);

        btnShare = rootView.findViewById(R.id.linearShare);
        btnShare.setOnClickListener(this);
        return rootView;
    }


    public static void setDetail(Detail detail) {
        DetailFragment.detail = detail;
    }

    @Override
    public void onClick(View view) {
        if (btnShare.getId() == view.getId()) {
            Bitmap bitmap = Screenshot.takescreenshotofRootView(viewCapture);
            try {

                File file = new File(getActivity().getExternalCacheDir(), "logicchip.png");
                FileOutputStream fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
                file.setReadable(true, false);
                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                intent.setType("image/png");
                startActivity(Intent.createChooser(intent, "Share image via"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void setToImageView(String uri, ImageView imageView){
        Glide.with(getActivity()).load(uri).into(imageView);
    }


}
