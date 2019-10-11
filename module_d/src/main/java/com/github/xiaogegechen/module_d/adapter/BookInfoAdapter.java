package com.github.xiaogegechen.module_d.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.module_d.R;
import com.github.xiaogegechen.module_d.model.BookInfoItem;

import java.util.List;

/**
 * {@link com.github.xiaogegechen.module_d.view.impl.BookInfoActivity}中的recyclerView
 * 的adapter
 */
public class BookInfoAdapter extends RecyclerView.Adapter<BookInfoAdapter.ViewHolder> {

    private List<BookInfoItem> mList;

    public BookInfoAdapter(List<BookInfoItem> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_d_book_info_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookInfoItem item = mList.get(position);
        holder.mTitle.setText(item.getTitle());
        holder.mContent.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTitle;
        TextView mContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.module_d_book_info_item_title);
            mContent = itemView.findViewById(R.id.module_d_book_info_item_content);
        }
    }
}
