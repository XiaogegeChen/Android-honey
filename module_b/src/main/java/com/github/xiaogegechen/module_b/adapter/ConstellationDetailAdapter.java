package com.github.xiaogegechen.module_b.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.library.ColorTextView;
import com.github.xiaogegechen.module_b.R;
import com.github.xiaogegechen.module_b.model.*;

import java.util.ArrayList;
import java.util.List;

public class ConstellationDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ConstellationDetailAdap";

    private static final int TYPE_HEAD = 100;
    private static final int TYPE_BODY = 101;
    private static final int TYPE_FOOT = 102;

    // 数据源
    private List<Object> mList;
    private Context mContext;

    public ConstellationDetailAdapter(List<Object> list, Context context) {
        mList = list;
        mContext = context;
    }

    /**
     * 向数据源尾部添加数据
     * @param list 添加的数据
     */
    public void addData(List<Object> list){
        mList.addAll(list);
        LogUtil.d(TAG, "add data to the end, now the list is: " + mList);
        notifyDataSetChanged();
    }

    /**
     * 向数据源头部添加数据
     * @param object 数据
     */
    public void addDataToHead(Object object){
        mList.add(0, object);
        LogUtil.d(TAG, "add data to the head, now the list is: " + mList);
        notifyDataSetChanged();
    }

    /**
     * 提供数据源
     * @return 数据源
     */
    public List<Object> getDataList(){
        return mList;
    }

    /**
     * 改变数据源
     * @param list 数据源
     */
    public void setData(List<Object> list){
        mList.clear();
        mList.addAll(list);
        LogUtil.d(TAG, "override data, now the list is: " + mList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(mList.get(position) instanceof Head){
            return TYPE_HEAD;
        }else if(mList.get(position) instanceof Body){
            return TYPE_BODY;
        } else if(mList.get(position) instanceof Foot){
            return TYPE_FOOT;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_HEAD:
                View headView = LayoutInflater.from(mContext).inflate(R.layout.module_b_recycler_view_item_head, parent, false);
                return new HeadViewHolder(headView);
            case TYPE_BODY:
                View bodyView = LayoutInflater.from(mContext).inflate(R.layout.module_b_recycler_view_item_body, parent, false);
                return new BodyViewHolder(bodyView);
            case TYPE_FOOT:
                View footView = LayoutInflater.from(mContext).inflate(R.layout.module_b_recycler_view_item_foot, parent, false);
                return new FootViewHolder(footView);
            default:
                throw new IllegalArgumentException("viewType is wrong!");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = mList.get(position);
        if(holder instanceof HeadViewHolder){
            Head head = (Head) item;
            Today today =  head.getToday();
            if(today != null){
                // 设置内容
                ((HeadViewHolder) holder).mColorTextView.setText(today.getAll());
                ((HeadViewHolder) holder).mIcon.setImageResource(head.getIconId());
                ((HeadViewHolder) holder).mSummary.setText(today.getSummary());
                // innerRecyclerView设置
                List<HeadItem> headItemList = new ArrayList<>();
                headItemList.add(new HeadItem("幸运色", today.getColor()));
                headItemList.add(new HeadItem("健康指数", today.getHealth()));
                headItemList.add(new HeadItem("爱情指数", today.getLove()));
                headItemList.add(new HeadItem("财运指数", today.getMoney()));
                headItemList.add(new HeadItem("幸运数字", today.getNumber()));
                headItemList.add(new HeadItem("速配星座", today.getFriend()));
                headItemList.add(new HeadItem("工作指数", today.getWork()));
                HeadInnerRecyclerViewAdapter adapter = new HeadInnerRecyclerViewAdapter(headItemList, mContext);
                RecyclerView recyclerView = ((HeadViewHolder) holder).mRecyclerView;
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
                recyclerView.setAdapter(adapter);
            }
        }else if(holder instanceof BodyViewHolder){
            Body body = (Body) item;
            ((BodyViewHolder) holder).mSummary.setText(body.getContent());
            ((BodyViewHolder) holder).mIcon.setImageResource(body.getIconId());
            int numStars = ((BodyViewHolder) holder).mRatingBar.getNumStars();
            ((BodyViewHolder) holder).mRatingBar.setRating(body.getRate() * numStars);
            ((BodyViewHolder) holder).mTitle.setText(body.getTitle());
        }else if(holder instanceof FootViewHolder){
            Foot foot = (Foot) item;
        }else {
            throw new IllegalArgumentException("the type of holder is wrong! please check it carefully");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private static class HeadViewHolder extends RecyclerView.ViewHolder{

        ColorTextView mColorTextView;
        ImageView mIcon;
        RecyclerView mRecyclerView;
        TextView mSummary;

        HeadViewHolder(@NonNull View itemView) {
            super(itemView);
            mColorTextView = itemView.findViewById(R.id.module_b_recycler_view_item_head_rate);
            mIcon = itemView.findViewById(R.id.module_b_recycler_view_item_head_icon);
            mRecyclerView = itemView.findViewById(R.id.module_b_recycler_view_item_recycler_view);
            mSummary = itemView.findViewById(R.id.module_b_recycler_view_item_head_sum);
        }
    }

    private static class FootViewHolder extends RecyclerView.ViewHolder{

        FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class BodyViewHolder extends RecyclerView.ViewHolder{

        TextView mTitle;
        RatingBar mRatingBar;
        TextView mSummary;
        ImageView mIcon;

        BodyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.module_b_recycler_view_item_body_title);
            mRatingBar = itemView.findViewById(R.id.module_b_recycler_view_item_body_rate);
            mIcon = itemView.findViewById(R.id.module_b_recycler_view_item_body_icon);
            mSummary = itemView.findViewById(R.id.module_b_recycler_view_item_body_sum);
        }
    }
}
