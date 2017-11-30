package kmitl.kawin58070006.horyuni.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import kmitl.kawin58070006.horyuni.R;
import kmitl.kawin58070006.horyuni.controller.activity.MainActivity;
import kmitl.kawin58070006.horyuni.controller.fragment.DetailFragment;
import kmitl.kawin58070006.horyuni.controller.fragment.HomeFragment;
import kmitl.kawin58070006.horyuni.model.Detail;
import kmitl.kawin58070006.horyuni.model.ImageUpload;

/**
 * Created by Kawin on 26/11/2560.
 */

public class MyPostItemAdapter extends RecyclerView.Adapter<MyPostItemAdapter.Holder> {
    private Activity context;
    private int resource;
    private List<ImageUpload> listImage;
    private FragmentActivity fragmentActivity;

    public MyPostItemAdapter(Activity context, FragmentActivity fragmentActivity) {
        this.context = context;

        this.fragmentActivity = fragmentActivity;
    }

    public void setData(List<ImageUpload> data) {
        this.listImage = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_image_item, null);
        Holder holder = new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        TextView tvName = holder.tvName;
        TextView tvName2 = holder.tvName2;
        TextView txtUsername = holder.txtUsername;
        ImageView imgPicUser = holder.imgPicUser;
        TextView textDate = holder.textDate;
        TextView btnDetail = holder.btnDetail;
        ImageView img = holder.img;
        Glide.with(context).load(listImage.get(position).getUriProfile()).into(imgPicUser);
        txtUsername.setText(listImage.get(position).getUsername());
        textDate.setText(listImage.get(position).getDate());

        tvName.setText(listImage.get(position).getName());
        Glide.with(context).load(listImage.get(position).getUrl()).into(img);
        tvName2.setText(listImage.get(position).getZone());
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

        Button btnDelete = holder.btnDelete;
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final TextView tvName2;
        final TextView txtUsername;
        final ImageView imgPicUser;
        final TextView textDate;
        TextView btnDetail;
        ImageView img;
        final Button btnDelete;

        public Holder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tvImageName);
            tvName2 = (TextView) v.findViewById(R.id.tvImageName2);
            txtUsername = (TextView) v.findViewById(R.id.txtUsername);
            imgPicUser = (ImageView) v.findViewById(R.id.imgPicUser);
            textDate = (TextView) v.findViewById(R.id.textDate);
            btnDetail = v.findViewById(R.id.btn_detail);
            img = (ImageView) v.findViewById(R.id.imgView);
            btnDelete = (Button) v.findViewById(R.id.btnDelete);
        }
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
