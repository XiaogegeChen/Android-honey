package com.github.xiaogegechen.module_d.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.common.util.ImageParam;
import com.github.xiaogegechen.common.util.ImageUtil;
import com.github.xiaogegechen.module_d.R;
import com.github.xiaogegechen.module_d.model.BingPictureTopic;

import java.util.List;

/**
 * {@link com.github.xiaogegechen.module_d.view.impl.BingPictureFragment} 界面的横向RecyclerView
 * 的adapter。用于展示一个module下的多个topic
 */
public class BingPictureTopicListAdapter extends RecyclerView.Adapter<BingPictureTopicListAdapter.ViewHolder> {

    private List<BingPictureTopic> mTopicList;
    private Context mContext;

    public BingPictureTopicListAdapter(List<BingPictureTopic> topicList, Context context) {
        mTopicList = topicList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_d_fragment_bing_picture_inner_recycler_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            // TODO 点击跳转
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BingPictureTopic topic = mTopicList.get(position);
        holder.mTitleTextView.setText(topic.getTitle());
        ImageParam param = new ImageParam.Builder()
                .context(mContext)
                .imageView(holder.mCoverImageView)
                .url(topic.getCoverUrl())
                .placeholder(mContext.getResources().getDrawable(R.drawable.module_d_bing_photo_loading))
                .error(mContext.getResources().getDrawable(R.drawable.module_d_bing_load_photo_failed))
                .build();
        ImageUtil.INSTANCE.displayImage(param);
    }

    @Override
    public int getItemCount() {
        return mTopicList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mCoverImageView;
        TextView mTitleTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCoverImageView = itemView.findViewById(R.id.module_d_fragment_bing_picture_inner_recycler_item_image);
            mTitleTextView = itemView.findViewById(R.id.module_d_fragment_bing_picture_inner_recycler_item_title);
        }
    }
}
