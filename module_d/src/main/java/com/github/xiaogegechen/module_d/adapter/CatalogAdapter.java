package com.github.xiaogegechen.module_d.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.module_d.R;
import com.github.xiaogegechen.module_d.event.NotifyBookListRefreshEvent;
import com.github.xiaogegechen.module_d.model.CatalogInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {

    private List<CatalogInfo> mCatalogInfoList;

    public CatalogAdapter(List<CatalogInfo> catalogInfoList) {
        mCatalogInfoList = catalogInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_d_activity_book_list_catalog_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        // 点击事件，点击之后要通知右侧的界面刷新，这里通过EventBus发
        // 消息来实现
        holder.mTextView.setOnClickListener(v -> {
            CatalogInfo catalogInfo = mCatalogInfoList.get(holder.getAdapterPosition());
            NotifyBookListRefreshEvent event = new NotifyBookListRefreshEvent(catalogInfo.getId());
            EventBus.getDefault().post(event);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CatalogInfo catalogInfo = mCatalogInfoList.get(position);
        holder.mTextView.setText(catalogInfo.getCatalog());
    }

    @Override
    public int getItemCount() {
        return mCatalogInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.module_d_activity_book_list_catalog_item_text);
        }
    }
}
