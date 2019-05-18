package com.xiaogege.honey.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xiaogege.honey.AboutActivity;
import com.xiaogege.honey.R;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {
    private ListView aboutList;
    private ArrayAdapter<String> aboutAdapter;
    private List<String> dataList=new ArrayList<> ();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate (R.layout.about_fragment,container,false);
        aboutList=view.findViewById (R.id.about_list_view);
        dataList.add("关于");
        dataList.add("功能介绍");
        dataList.add("版本");
        dataList.add("反馈");
        dataList.add("github");
        aboutAdapter=new ArrayAdapter<> (getContext (),android.R.layout.simple_list_item_1,dataList);
        aboutList.setAdapter (aboutAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated (savedInstanceState);
        aboutList.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=dataList.get (position);
                switch (item){
                    case "关于":
                        Intent aboutIntent=new Intent (getContext (),AboutActivity.class);
                        aboutIntent.putExtra ("about","about");
                        startActivity (aboutIntent);
                        break;
                    case "github":
                        Intent gitHubIntent=new Intent (getContext (),AboutActivity.class);
                        gitHubIntent.putExtra ("about","github");
                        startActivity (gitHubIntent);
                        break;
                    case "功能介绍":
                        Intent functionIntent=new Intent (getContext (),AboutActivity.class);
                        functionIntent.putExtra ("about","function");
                        startActivity (functionIntent);
                        break;
                    case "版本":
                        Intent helpIntent=new Intent (getContext (),AboutActivity.class);
                        helpIntent.putExtra ("about","help");
                        startActivity (helpIntent);
                        break;
                    case "反馈":
                        Intent feedbackIntent=new Intent (getContext (),AboutActivity.class);
                        feedbackIntent.putExtra ("about","feedback");
                        startActivity (feedbackIntent);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}