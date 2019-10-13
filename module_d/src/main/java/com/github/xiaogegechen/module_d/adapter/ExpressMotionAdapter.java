package com.github.xiaogegechen.module_d.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.module_d.R;
import com.github.xiaogegechen.module_d.model.ExpressMotionItem;

import java.util.List;

/**
 * {@link com.github.xiaogegechen.module_d.view.impl.ExpressActivity}中的recyclerView
 *  的adapter
 */
public class ExpressMotionAdapter extends RecyclerView.Adapter<ExpressMotionAdapter.ViewHolder> {

    private List<ExpressMotionItem> mList;

    public ExpressMotionAdapter(List<ExpressMotionItem> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_d_activity_express_motion_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpressMotionItem item = mList.get(position);
        holder.mTimeTextView.setText(item.getTime());
        holder.mTimeTextView.setTextColor(item.getColor());
        holder.mExactTimeTextView.setText(item.getExactTime());
        holder.mExactTimeTextView.setTextColor(item.getColor());
        holder.mIconImageView.setImageResource(item.getIconId());
        holder.mSplitView.setBackgroundColor(item.getSplitColor());
        holder.mInfoTextView.setText(item.getInfo());
        holder.mInfoTextView.setTextColor(item.getColor());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTimeTextView;
        TextView mExactTimeTextView;
        ImageView mIconImageView;
        View mSplitView;
        TextView mInfoTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTimeTextView = itemView.findViewById(R.id.module_d_activity_express_motion_item_time);
            mExactTimeTextView = itemView.findViewById(R.id.module_d_activity_express_motion_item_time_exactly);
            mIconImageView = itemView.findViewById(R.id.module_d_activity_express_motion_item_icon);
            mSplitView = itemView.findViewById(R.id.module_d_activity_express_motion_item_split);
            mInfoTextView = itemView.findViewById(R.id.module_d_activity_express_motion_item_info);
        }
    }
}
