package com.github.xiaogegechen.bing.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.bing.Constants;
import com.github.xiaogegechen.bing.R;
import com.github.xiaogegechen.bing.model.Topic;
import com.github.xiaogegechen.bing.view.impl.BingTopicDetailActivity;
import com.github.xiaogegechen.common.util.ImageParam;
import com.github.xiaogegechen.common.util.ImageUtil;

import java.util.List;

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.ViewHolder> {

    private List<Topic> mTopicList;
    private Context mContext;

    public TopicListAdapter(List<Topic> topicList, Context context) {
        mTopicList = topicList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bing_fragment_bing_inner_recycler_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            Topic topic = mTopicList.get(position);
            // 启动 BingTopicDetailActivity 并传递参数
            Intent intent = new Intent(mContext, BingTopicDetailActivity.class);
            intent.putExtra(Constants.INTENT_PARAM_NAME, topic);
            mContext.startActivity(intent);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topic topic = mTopicList.get(position);
        holder.mTitleTextView.setText(topic.getTitle());
        ImageParam param = new ImageParam.Builder()
                .context(mContext)
                .imageView(holder.mCoverImageView)
                .url(topic.getCoverUrl())
                .placeholder(mContext.getResources().getDrawable(R.drawable.bing_photo_loading))
                .error(mContext.getResources().getDrawable(R.drawable.bing_load_photo_failed))
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
            mCoverImageView = itemView.findViewById(R.id.bing_fragment_bing_inner_recycler_item_image);
            mTitleTextView = itemView.findViewById(R.id.bing_fragment_bing_inner_recycler_item_title);
        }
    }
}
