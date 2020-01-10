package com.github.xiaogegechen.module_a.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.xiaogegechen.common.adapter.LoadMoreRecyclerViewAdapter;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.module_a.R;
import com.github.xiaogegechen.module_a.model.PictureItem;
import com.github.xiaogegechen.module_a.model.event.NotifyPicClickedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PictureListAdapter extends LoadMoreRecyclerViewAdapter<PictureListAdapter.ViewHolder, PictureItem> {

    private static final String TAG = "PictureListAdapter";

    public PictureListAdapter(List<PictureItem> list, Context context) {
        super(list, context);
    }

    @Override
    public int getItemViewLayout() {
        return R.layout.module_a_fragment_a_recycler_item;
    }

    @Override
    public ViewHolder getItemViewHolder(View itemView) {
        final ViewHolder holder = new ViewHolder(itemView);
        holder.mImageView.setOnClickListener(v -> {
            // 发消息给fragmentA，如果直接发给PicListFragment会有三个实例，响应三次
            final int position = holder.getAdapterPosition();
            final PictureItem item = mList.get(position);
            NotifyPicClickedEvent event = new NotifyPicClickedEvent();
            event.setPictureItem(item);
            event.setImageView(holder.mImageView);
            EventBus.getDefault().post(event);
        });
        holder.mTextView.setOnClickListener(v -> {
            // TODO 跳转到查看大文本界面
        });
        return holder;
    }

    @Override
    public void onItemBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final PictureItem item = mList.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTextView.setText(item.getDescription());
        LogUtil.d(TAG, "url is -> " + item.getCompressUrl());
        Glide.with(mContext)
                .load(item.getCompressUrl())
                .apply(new RequestOptions()
                        .placeholder(mContext.getResources().getDrawable(R.drawable.module_a_photo_loading))
                        .error(mContext.getResources().getDrawable(R.drawable.module_a_load_photo_failed)))
                .into(holder.mImageView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.module_a_fragment_a_recycler_item_pic);
            mTextView = itemView.findViewById(R.id.module_a_fragment_a_recycler_item_text);
        }
    }
}
