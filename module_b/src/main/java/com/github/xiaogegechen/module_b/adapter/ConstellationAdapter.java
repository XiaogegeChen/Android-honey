package com.github.xiaogegechen.module_b.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.xiaogegechen.module_b.R;
import com.github.xiaogegechen.module_b.model.Constellation;

import java.util.List;

public class ConstellationAdapter extends BaseAdapter {

    private List<Constellation> mConstellationList;
    private Context mContext;

    public ConstellationAdapter(List<Constellation> constellationList, Context context) {
        mConstellationList = constellationList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mConstellationList.size();
    }

    @Override
    public Object getItem(int position) {
        return mConstellationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Constellation constellation = mConstellationList.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.module_b_enter_item, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mIcon.setImageResource(constellation.getIconId());
        viewHolder.mDate.setText(constellation.getDate());
        viewHolder.mName.setText(constellation.getName());
        return view;
    }

    private static final class ViewHolder{

        private ImageView mIcon;
        private TextView mName;
        private TextView mDate;

        private ViewHolder(View itemView){
            mIcon = itemView.findViewById(R.id.module_b_enter_item_image);
            mName = itemView.findViewById(R.id.module_b_enter_item_constellation_name);
            mDate = itemView.findViewById(R.id.module_b_enter_item_constellation_date);
        }
    }
}
