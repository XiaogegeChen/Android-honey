package com.github.xiaogegechen.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.xiaogegechen.weather.R;
import com.github.xiaogegechen.weather.model.Setting;

import java.util.List;

/**
 * 设置界面的listView的adapter
 */
public class SettingAdapter extends ArrayAdapter<Setting> {

    private int mResource;
    private  List<Setting> mList;

    public SettingAdapter(@NonNull Context context, int resource, @NonNull List<Setting> objects) {
        super(context, resource, objects);
        mResource = resource;
        mList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(mResource, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Setting setting = mList.get(position);
        viewHolder.mTextView.setText(setting.getName());
        if(setting.isSwitchVisibility()){
            viewHolder.mSwitch.setVisibility(View.VISIBLE);
            viewHolder.mSwitch.setChecked(setting.isSwitchChecked());
        }else{
            viewHolder.mSwitch.setVisibility(View.GONE);
        }
        return view;
    }

    static class ViewHolder{
        Switch mSwitch;
        TextView mTextView;

        ViewHolder(View itemView){
            mSwitch = itemView.findViewById(R.id.switchButton);
            mTextView = itemView.findViewById(R.id.title);
        }
    }
}
