package kmitl.kawin58070006.horyuni.adapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import kmitl.kawin58070006.horyuni.DetailFragment;
import kmitl.kawin58070006.horyuni.HomeFragment;
import kmitl.kawin58070006.horyuni.R;
import kmitl.kawin58070006.horyuni.model.Detail;
import kmitl.kawin58070006.horyuni.model.ImageUpload;

/**
 * Created by Administrator on 1/11/2560.
 */

public class ImageListAdapter extends ArrayAdapter<ImageUpload> {
    private Activity context;
    private int resource;
    private List<ImageUpload> listImage;
    private FragmentActivity fragmentActivity;

    public ImageListAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<ImageUpload> objects, FragmentActivity fragmentActivity) {
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
        ImageView img = (ImageView) v.findViewById(R.id.imgView);


            tvName.setText(listImage.get(position).getName());

            Glide.with(context).load(listImage.get(position).getUrl()).into(img);

            Button btnDetail = v.findViewById(R.id.btn_detail);
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Detail detail = new Detail(listImage.get(position).getName(), listImage.get(position), listImage.get(position).getZone(), listImage.get(position).getMoreDetail());
                    HomeFragment.newInstance().setDetail(detail);

                    tvName2.setText(detail.getMoreDetail());

                    // go to fragment
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragmentContainer, DetailFragment.newInstance(detail))
                            .addToBackStack(null)
                            .commit();
                }
            });


        return v;

    }

}