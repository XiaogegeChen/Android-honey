package com.github.xiaogegechen.weather.adapter;

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
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.weather.Constants;
import com.github.xiaogegechen.weather.R;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.model.SelectedCity;
import com.github.xiaogegechen.weather.model.event.NotifyCityRemovedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * {@link com.github.xiaogegechen.weather.view.impl.ManageCityActivity}中recyclerView的adapter
 */
public class SelectedCityListAdapter extends RecyclerView.Adapter<SelectedCityListAdapter.ViewHolder> {

    private static final String TAG = "SelectedCityListAdapter";

    private List<SelectedCity> mSelectedCityList;
    private Context mContext;

    public SelectedCityListAdapter(List<SelectedCity> selectedCityList, Context context) {
        mSelectedCityList = selectedCityList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.weather_activity_manage_city_item,
                parent,
                false
        );
        final ViewHolder holder = new ViewHolder(view);
        holder.mDeleteImageView.setOnClickListener(v -> {
            // 点击事件，发送移除事件
            final int position = holder.getAdapterPosition();
            SelectedCity selectedCity = mSelectedCityList.get(position);
            LogUtil.d(TAG, "selectedCity is : " + selectedCity);
            EventBus.getDefault().post(new NotifyCityRemovedEvent(
                    convertSelectedCity2CityInfo(selectedCity),
                    NotifyCityRemovedEvent.FLAG_FROM_MANAGE_CITY_ACTIVITY)
            );
        });
        holder.itemView.setOnClickListener(v -> {
            // TODO 点击事件，跳转到weatherActivity界面。
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SelectedCity selectedCity = mSelectedCityList.get(position);
        // 城市名
        String location = selectedCity.getLocation();
        // 天气数据
        StringBuilder weatherBuilder = new StringBuilder();
        if(selectedCity.getWeatherDescription() == null){
            weatherBuilder.append(Constants.NULL_DATA);
        }else{
            weatherBuilder.append(selectedCity.getWeatherDescription());
        }
        weatherBuilder.append(" ");
        if(selectedCity.getTemp() == null){
            weatherBuilder.append(Constants.NULL_DATA);
        }else{
            weatherBuilder.append(selectedCity.getTemp());
        }
        weatherBuilder.append("°");
        // 填充
        holder.mCityTextView.setText(location);
        holder.mWeatherTextView.setText(weatherBuilder.toString());
        ImageParam param = new ImageParam.Builder()
                .context(mContext)
                .imageView(holder.mWeatherImageView)
                .url(Constants.WEATHER_ICON_URL + selectedCity.getWeatherCode() + ".png")
                .error(mContext.getResources().getDrawable(R.drawable.weather_ic_na))
                .placeholder(mContext.getResources().getDrawable(R.drawable.weather_ic_na))
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
            mWeatherImageView = itemView.findViewById(R.id.weather_activity_manage_city_item_icon);
            mDeleteImageView = itemView.findViewById(R.id.weather_activity_manage_city_item_delete);
            mCityTextView = itemView.findViewById(R.id.weather_activity_manage_city_item_city);
            mWeatherTextView = itemView.findViewById(R.id.weather_activity_manage_city_item_weather);
        }
    }

    /**
     * 将 SelectedCity 实例转化为 CityInfo 实例。因为是发送给
     * {@link com.github.xiaogegechen.weather.view.impl.ManageCityActivity}进行删除操作的，只需要
     * cityId 和 location 两个字段即可
     * @param selectedCity selectedCity
     * @return cityInfo，cityId 和 location 两个字段不为空值，其它字段都是空值。
     */
    private static CityInfo convertSelectedCity2CityInfo(SelectedCity selectedCity){
        CityInfo cityInfo = new CityInfo();
        cityInfo.setLocation(selectedCity.getLocation());
        cityInfo.setCityId(selectedCity.getId());
        return cityInfo;
    }

}
