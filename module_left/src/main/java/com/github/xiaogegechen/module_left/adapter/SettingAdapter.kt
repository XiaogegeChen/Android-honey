package com.github.xiaogegechen.module_left.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Switch
import android.widget.TextView
import com.github.xiaogegechen.module_left.R
import com.github.xiaogegechen.module_left.model.Setting

class SettingAdapter(var mContext: Context, var resource: Int, var settingList: MutableList<Setting>): ArrayAdapter<Setting>(mContext, resource, settingList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val setting = getItem(position)
        val view: View
        val holder: ViewHolder
        if(convertView == null){
            view = LayoutInflater.from(mContext).inflate(resource, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        }else{
            view = convertView
            holder = view.tag as ViewHolder
        }

        holder.mTitle?.text = setting?.name
        if(setting!!.switchVisibility){
            holder.mSwitch?.visibility = View.VISIBLE
        }else{
            holder.mSwitch?.visibility = View.INVISIBLE
        }

        return view
    }

    private class ViewHolder(view: View?){
        var mSwitch: Switch? = view?.findViewById(R.id.setSwitch)
        var mTitle: TextView? = view?.findViewById(R.id.setTitle)
    }
}