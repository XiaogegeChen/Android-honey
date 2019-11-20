package com.github.xiaogegechen.module_left.adapter;

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
import com.github.xiaogegechen.module_left.Constants;
import com.github.xiaogegechen.module_left.R;
import com.github.xiaogegechen.module_left.model.SelectedCity;

import java.util.List;

/**
 * {@link com.github.xiaogegechen.module_left.view.impl.ManageCityActivity}中recyclerView的adapter
 */
public class SelectedCityListAdapter extends RecyclerView.Adapter<SelectedCityListAdapter.ViewHolder> {

    private List<SelectedCity> mSelectedCityList;
    private Context mContext;

    public SelectedCityListAdapter(List<SelectedCity> selectedCityList, Context context) {
        mSelectedCityList = selectedCityList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_left_activity_manage_city_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mDeleteImageView.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            SelectedCity selectedCity = mSelectedCityList.get(position);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SelectedCity selectedCity = mSelectedCityList.get(position);
        String location = selectedCity.getLocation();
        String weather = selectedCity.getWeatherDescription() + " " + selectedCity.getTemp() + "°";
        holder.mCityTextView.setText(location);
        holder.mWeatherTextView.setText(weather);
        ImageParam param = new ImageParam.Builder()
                .context(mContext)
                .imageView(holder.mWeatherImageView)
                .url(Constants.WEATHER_ICON_URL + "")
                .error(mContext.getResources().getDrawable(R.drawable.module_left_weather_na))
                .placeholder(mContext.getResources().getDrawable(R.drawable.module_left_weather_na))
                .build();
        ImageUtil.INSTANCE.displayImage(param);
    }

    @Override
    public int getItemCount() {
        return mSelectedCityList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mWeatherImageView;
        ImageView mDeleteImageView;
        TextView mCityTextView;
        TextView mWeatherTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mWeatherImageView = itemView.findViewById(R.id.module_left_activity_manage_city_item_icon);
            mDeleteImageView = itemView.findViewById(R.id.module_left_activity_manage_city_item_delete);
            mCityTextView = itemView.findViewById(R.id.module_left_activity_manage_city_item_city);
            mWeatherTextView = itemView.findViewById(R.id.module_left_activity_manage_city_item_weather);
        }
    }

}
