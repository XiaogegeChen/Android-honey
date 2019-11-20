package com.github.xiaogegechen.module_a.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.common.adapter.LoadMoreRecyclerViewAdapter;
import com.github.xiaogegechen.common.util.ImageParam;
import com.github.xiaogegechen.common.util.ImageUtil;
import com.github.xiaogegechen.module_a.R;
import com.github.xiaogegechen.module_a.model.PictureItem;

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
        return new ViewHolder(itemView);
    }

    @Override
    public void onItemBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        PictureItem item = mList.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTextView.setText(item.getDescription());
        ImageParam param = new ImageParam.Builder()
                .context(mContext)
                .imageView(holder.mImageView)
                .placeholder(mContext.getResources().getDrawable(R.drawable.module_a_photo_loading))
                .error(mContext.getResources().getDrawable(R.drawable.module_a_load_photo_failed))
                .url(item.getUrl())
                .build();
        ImageUtil.INSTANCE.displayImage(param);
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
