package kmitl.kawin58070006.horyuni;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
    private ImageView btnShare;
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
        Glide.with(getActivity()).load(detail.getImg().getUrl()).into(imgMainDetail);
        imageView1 = rootView.findViewById(R.id.imageDetail1);
        Glide.with(getActivity()).load(detail.getImg().getUrl()).into(imageView1);
        viewCapture = rootView.findViewById(R.id.viewCapture);
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


        btnShare = rootView.findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);
        return rootView;
    }


    public static void setDetail(Detail detail) {
        DetailFragment.detail = detail;
    }
    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public void onClick(View view) {
        if (btnShare.getId() == view.getId()) {
//            try {
//                Bitmap bitmap = Screenshot.takescreenshotofRootView(viewCapture);
//                viewCapture.setImageBitmap(bitmap);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("image/png");
//                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imgFile));
//                startActivity(Intent.createChooser(intent, "share to..."));
//
//            } catch (Throwable e) {
//
//            }
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
}
