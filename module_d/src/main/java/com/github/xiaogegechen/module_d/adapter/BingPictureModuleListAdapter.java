package com.github.xiaogegechen.module_d.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.module_d.R;
import com.github.xiaogegechen.module_d.model.BingPictureModule;

import java.util.List;

/**
 * {@link com.github.xiaogegechen.module_d.view.impl.BingPictureFragment} 界面的纵向RecyclerView的
 * adapter。用于展示多个module
 */
public class BingPictureModuleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_BODY = 100;
    private static final int TYPE_FOOT = 101;

    private List<BingPictureModule> mModuleList;
    private Context mContext;

    public BingPictureModuleListAdapter(List<BingPictureModule> moduleList, Context context) {
        mModuleList = moduleList;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == mModuleList.size()){
            return TYPE_FOOT;
        }else{
            return TYPE_BODY;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if(viewType == TYPE_BODY){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_d_fragment_bing_picture_recycler_item, parent, false);
            viewHolder = new BodyViewHolder(view);
        }else if(viewType == TYPE_FOOT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_d_fragment_bing_picture_recycler_foot, parent, false);
            viewHolder = new FootViewHolder(view);
        }else{
            throw new IllegalArgumentException("viewType is not correct");
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BingPictureModule module = mModuleList.get(position);
        if(holder instanceof BodyViewHolder){
            BodyViewHolder bodyViewHolder = (BodyViewHolder) holder;
            bodyViewHolder.mTitleTextView.setText(module.getTitle());
            // topic的recyclerView
            bodyViewHolder.mTopicRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            bodyViewHolder.mTopicRecyclerView.setAdapter(new BingPictureTopicListAdapter(module.getTopicList(), mContext));
        }
    }

    @Override
    public int getItemCount() {
        return mModuleList.size() + 1;
    }

    static class BodyViewHolder extends RecyclerView.ViewHolder{

        TextView mTitleTextView;
        RecyclerView mTopicRecyclerView;

        BodyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.module_d_fragment_bing_picture_recycler_item_title);
            mTopicRecyclerView = itemView.findViewById(R.id.module_d_fragment_bing_picture_recycler_item_list);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder{

        FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
