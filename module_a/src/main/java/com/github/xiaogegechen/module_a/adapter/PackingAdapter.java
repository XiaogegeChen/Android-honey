package com.github.xiaogegechen.module_a.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.design.view.ProgressButton;
import com.github.xiaogegechen.module_a.R;
import com.github.xiaogegechen.module_a.model.PackingItem;
import com.github.xiaogegechen.module_a.model.event.NotifyPackingItemClickedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PackingAdapter extends RecyclerView.Adapter<PackingAdapter.ViewHolder> {

    private List<PackingItem> mDataSource;

    public PackingAdapter(List<PackingItem> dataSource) {
        mDataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ViewHolder holder = new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.module_a_big_pic_pack_item, parent, false
                )
        );
        holder.mProgressButton.setOnClickListener(v -> {
            NotifyPackingItemClickedEvent event = new NotifyPackingItemClickedEvent();
            event.setPackingItem(mDataSource.get(holder.getAdapterPosition()));
            event.setProgressButton(holder.mProgressButton);
            EventBus.getDefault().post(event);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PackingItem item = mDataSource.get(position);
        holder.mProgressButton.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ProgressButton mProgressButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mProgressButton = itemView.findViewById(R.id.packing_pb);
        }
    }
}
