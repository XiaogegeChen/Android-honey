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
import com.github.xiaogegechen.module_d.event.NotifyStartInfoActivityEvent;
import com.github.xiaogegechen.module_d.model.db.BookInDB;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * {@link com.github.xiaogegechen.module_d.view.impl.BookListActivity}中右侧recyclerView
 * 的adapter
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder>{

    private List<BookInDB> mList;
    private Context mContext;

    public BookListAdapter(List<BookInDB> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.module_d_activity_book_list_book_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        // 点击事件，发送消息给activity，通知activity跳转
        View.OnClickListener listener = v -> {
            BookInDB bookInDB = mList.get(holder.getAdapterPosition());
            NotifyStartInfoActivityEvent event = new NotifyStartInfoActivityEvent(bookInDB, holder.mImageView);
            EventBus.getDefault().post(event);
        };
        holder.mTextView.setOnClickListener(listener);
        holder.mImageView.setOnClickListener(listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookInDB bookInDB = mList.get(position);
        String title = bookInDB.getTitle();
        String url = bookInDB.getImg();
        holder.mTextView.setText(title);
        ImageParam param = new ImageParam.Builder()
                .context(mContext)
                .imageView(holder.mImageView)
                .error(mContext.getResources().getDrawable(R.drawable.module_d_load_book_failed))
                .url(url)
                .build();
        ImageUtil.INSTANCE.displayImage(param);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.module_d_activity_book_list_book_list_item_icon);
            mTextView = itemView.findViewById(R.id.module_d_activity_book_list_book_list_item_title);
        }
    }

}
