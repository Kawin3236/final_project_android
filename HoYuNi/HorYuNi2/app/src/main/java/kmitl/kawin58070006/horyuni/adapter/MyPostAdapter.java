package kmitl.kawin58070006.horyuni.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import kmitl.kawin58070006.horyuni.controller.activity.MainActivity;
import kmitl.kawin58070006.horyuni.controller.fragment.DetailFragment;
import kmitl.kawin58070006.horyuni.controller.fragment.HomeFragment;
import kmitl.kawin58070006.horyuni.R;
import kmitl.kawin58070006.horyuni.controller.fragment.MyPostFragment;
import kmitl.kawin58070006.horyuni.model.Detail;
import kmitl.kawin58070006.horyuni.model.ImageUpload;

/**
 * Created by Administrator on 1/11/2560.
 */

public class MyPostAdapter extends ArrayAdapter<ImageUpload> {
    private Activity context;
    private int resource;
    private List<ImageUpload> listImage;
    private FragmentActivity fragmentActivity;

    public MyPostAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<ImageUpload> objects, FragmentActivity fragmentActivity) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
        this.fragmentActivity = fragmentActivity;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(resource, null);
        final TextView tvName = (TextView) v.findViewById(R.id.tvImageName);
        final TextView tvName2 = (TextView) v.findViewById(R.id.tvImageName2);
        final TextView txtUsername = (TextView) v.findViewById(R.id.txtUsername);
        final ImageView imgPicUser = (ImageView) v.findViewById(R.id.imgPicUser);
        final TextView textDate = (TextView) v.findViewById(R.id.textDate);
        final Button btnDelete = (Button) v.findViewById(R.id.btnDelete);
        Glide.with(context).load(listImage.get(position).getUriProfile()).into(imgPicUser);
        txtUsername.setText(listImage.get(position).getUsername());
        textDate.setText(listImage.get(position).getDate());
        ImageView img = (ImageView) v.findViewById(R.id.imgView);
        tvName.setText(listImage.get(position).getName());
        Glide.with(context).load(listImage.get(position).getUrl()).into(img);
        tvName2.setText(listImage.get(position).getZone());
        TextView btnDetail = v.findViewById(R.id.btn_detail);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Detail detail = new Detail(listImage.get(position).getName(), listImage.get(position), listImage.get(position).getZone(), listImage.get(position).getMoreDetail());
                HomeFragment.newInstance().setDetail(detail);
                // go to fragment
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainer, DetailFragment.newInstance(detail))
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog(position);
            }
        });


        return v;
    }
    public void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(new CharSequence[]{"ลบ", "ยกเลิก"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.FB_Database_Path);
                                mDatabaseRef.child(listImage.get(position).getId()).removeValue();
                                break;
                            case 1:

                                break;
                        }
                    }
                });
        builder.show();
    }
}