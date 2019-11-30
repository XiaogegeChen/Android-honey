package com.github.xiaogegechen.bing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.bing.R;
import com.github.xiaogegechen.bing.model.Module;

import java.util.List;

public class ModuleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_BODY = 100;
    private static final int TYPE_FOOT = 101;

    private List<Module> mModuleList;
    private Context mContext;

    public ModuleListAdapter(List<Module> moduleList, Context context) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bing_fragment_bing_recycler_item, parent, false);
            viewHolder = new BodyViewHolder(view);
        }else if(viewType == TYPE_FOOT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bing_fragment_bing_recycler_foot, parent, false);
            viewHolder = new FootViewHolder(view);
        }else{
            throw new IllegalArgumentException("viewType is not correct");
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position == mModuleList.size()){
            // 是foot，不需要处理
            return;
        }
        Module module = mModuleList.get(position);
        if(holder instanceof BodyViewHolder){
            BodyViewHolder bodyViewHolder = (BodyViewHolder) holder;
            bodyViewHolder.mTitleTextView.setText(module.getTitle());
            // topic的recyclerView
            bodyViewHolder.mTopicRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            bodyViewHolder.mTopicRecyclerView.setAdapter(new TopicListAdapter(module.getTopicList(), mContext));
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
            mTitleTextView = itemView.findViewById(R.id.bing_fragment_bing_recycler_item_title);
            mTopicRecyclerView = itemView.findViewById(R.id.bing_fragment_bing_recycler_item_list);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder{

        FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
