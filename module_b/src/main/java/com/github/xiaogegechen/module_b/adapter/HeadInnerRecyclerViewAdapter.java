package com.github.xiaogegechen.module_b.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.github.xiaogegechen.module_b.R;
import com.github.xiaogegechen.module_b.model.HeadItem;

import java.util.List;

public class HeadInnerRecyclerViewAdapter extends RecyclerView.Adapter<HeadInnerRecyclerViewAdapter.ViewHolder> {

    private List<HeadItem> mList;
    private Context mContext;

    public HeadInnerRecyclerViewAdapter(List<HeadItem> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.module_b_recycler_view_item_head_inner_recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HeadItem item = mList.get(position);
        holder.mContent.setText(item.getContent());
        holder.mTitle.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTitle;
        private TextView mContent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.module_b_recycler_view_item_head_inner_recycler_view_item_title);
            mContent = itemView.findViewById(R.id.module_b_recycler_view_item_head_inner_recycler_view_item_content);
        }
    }

}
