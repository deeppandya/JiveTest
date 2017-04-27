package mainpackage.jivetest.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import mainpackage.jivetest.model.FlickerPhotos;
import mainpackage.jivetest.R;
import mainpackage.jivetest.iviews.IMainView;
import mainpackage.jivetest.support.Utils;

public class FlickerPhotosListAdapter extends RecyclerView.Adapter<FlickerPhotosListAdapter.StoryViewHolder> {

    private Context mContext;
    private List<FlickerPhotos> flickerPhotosList;
    private IMainView mainView;

    public class StoryViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView imgFlicker;
        private View mainView;

        private StoryViewHolder(View view) {
            super(view);

            mainView=view;

            title = (TextView) view.findViewById(R.id.txtTitle);

            imgFlicker = (ImageView) view.findViewById(R.id.imgFlicker);
        }
    }


    public FlickerPhotosListAdapter(Context mContext, IMainView mainView) {
        this.mContext = mContext;
        this.mainView=mainView;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flicker_photo_card, parent, false);

        return new StoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StoryViewHolder holder, int position) {
        final FlickerPhotos flickerPhotos = flickerPhotosList.get(position);

        holder.title.setText(flickerPhotos.getTitle());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Glide.with(mContext).load(Utils.getFlickerPhotoUrl(flickerPhotos)).placeholder(mContext.getDrawable(R.mipmap.ic_launcher)).into(holder.imgFlicker);
        }else{
            Glide.with(mContext).load(Utils.getFlickerPhotoUrl(flickerPhotos)).into(holder.imgFlicker);
        }

        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainView.openPhotoFullView(flickerPhotos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flickerPhotosList.size();
    }

    public void setFlickerPhotosList(List<FlickerPhotos> flickerPhotosList){
        this.flickerPhotosList = flickerPhotosList;
    }
}
