package com.github.xiaogegechen.module_a.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.xiaogegechen.common.adapter.LoadMoreRecyclerViewAdapter;
import com.github.xiaogegechen.design.view.ColorfulTextView;
import com.github.xiaogegechen.module_a.Constants;
import com.github.xiaogegechen.module_a.R;
import com.github.xiaogegechen.module_a.model.PictureItem;
import com.github.xiaogegechen.module_a.model.event.NotifyPicClickedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PictureListAdapter extends LoadMoreRecyclerViewAdapter<PictureListAdapter.ViewHolder, PictureItem> {
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
        holder.mTextView.setPeriod(Constants.TEXT_COLOR_CHANGE_PERIOD);
        holder.mTextView.start();
        // mImageView 设置高度
        int imageViewWidth = calculateImageWidth(holder.mRootView);
        holder.mImageView.getLayoutParams().height = (int) ((item.getCompressImageHeight() * 1.0 / item.getCompressImageWidth()) * imageViewWidth);
        Glide.with(mContext)
                .load(item.getCompressUrl())
                .apply(new RequestOptions()
                        .placeholder(mContext.getResources().getDrawable(R.drawable.design_loading))
                        .error(mContext.getResources().getDrawable(R.drawable.design_error)))
                .into(holder.mImageView);
    }

    private int calculateImageWidth(View view){
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager){
            int count = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            return mRecyclerView.getWidth() / count - layoutParams.leftMargin - layoutParams.rightMargin;
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        ColorfulTextView mTextView;
        ViewGroup mRootView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.module_a_fragment_a_recycler_item_pic);
            mTextView = itemView.findViewById(R.id.module_a_fragment_a_recycler_item_text);
            mRootView = itemView.findViewById(R.id.root);
        }
    }
}
