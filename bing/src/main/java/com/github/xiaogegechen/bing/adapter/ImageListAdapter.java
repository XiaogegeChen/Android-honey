package com.github.xiaogegechen.bing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.bing.R;
import com.github.xiaogegechen.bing.model.Image;
import com.github.xiaogegechen.bing.model.event.NotifyGotoBigPicEvent;
import com.github.xiaogegechen.common.util.ImageParam;
import com.github.xiaogegechen.common.util.ImageUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private List<Image> mImageList;
    private Context mContext;

    public ImageListAdapter(List<Image> imageList, Context context) {
        mImageList = imageList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bing_activity_bing_topic_detail_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mImageView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            EventBus.getDefault().post(new NotifyGotoBigPicEvent(mImageList.get(position), holder.mImageView));
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Image image = mImageList.get(position);
        ImageParam param = new ImageParam.Builder()
                .context(mContext)
                .imageView(holder.mImageView)
                .url(image.getThumbnailUrl())
                .placeholder(mContext.getResources().getDrawable(R.drawable.bing_photo_loading))
                .error(mContext.getResources().getDrawable(R.drawable.bing_load_photo_failed))
                .build();
        ImageUtil.INSTANCE.displayImage(param);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.bing_activity_bing_topic_detail_item_image);
        }
    }
}
