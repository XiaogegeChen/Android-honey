package com.xiaogege.honey.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaogege.honey.R;
import com.xiaogege.honey.adapter.PictureAdapter;
import com.xiaogege.honey.sentence.MySentence;
import com.xiaogege.honey.ui.Picture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PicturesFragment extends Fragment {
    private RecyclerView pictureRecyclerView;
    private List<Picture> mPictureListMyself;
    private PictureAdapter pictureAdapter;
    private String[] pictureDescriptions=MySentence.PICTUREDESCRIPTION;
    private Random random=new Random ();
    public static final String TAG="PicturesFragment";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate (savedInstanceState);
        initPictureListMyself ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate (R.layout.pictures_fragment_layout,container,false);
        pictureRecyclerView=view.findViewById (R.id.picture_recycler_view);
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager (2,StaggeredGridLayoutManager.VERTICAL);
        pictureRecyclerView.setLayoutManager (manager);
        //pictureAdapter=new PictureAdapter (mPictureList);
        pictureAdapter=new PictureAdapter (mPictureListMyself);
        pictureRecyclerView.setAdapter (pictureAdapter);
        return view;
    }

    // 初始化默认图片
    private void initPictureListMyself(){
        mPictureListMyself=new ArrayList<> ();
        mPictureListMyself.add (getTitlePicture (1));
        mPictureListMyself.add (addPictureMyself (R.drawable.you_1));
        mPictureListMyself.add (addPictureMyself (R.drawable.you_2));
        mPictureListMyself.add (addPictureMyself (R.drawable.you_3));
        mPictureListMyself.add (addPictureMyself (R.drawable.you_4));
        mPictureListMyself.add (addPictureMyself (R.drawable.you_5));
        mPictureListMyself.add (addPictureMyself (R.drawable.you_6));
        mPictureListMyself.add (addPictureMyself (R.drawable.you_7));
        mPictureListMyself.add (getTitlePicture (2));
        mPictureListMyself.add (addPictureMyself (R.drawable.our_1));
        mPictureListMyself.add (addPictureMyself (R.drawable.our_2));
        mPictureListMyself.add (addPictureMyself (R.drawable.our_3));
        mPictureListMyself.add (addPictureMyself (R.drawable.our_4));
        mPictureListMyself.add (addPictureMyself (R.drawable.our_5));
        mPictureListMyself.add (addPictureMyself (R.drawable.our_6));
        mPictureListMyself.add (addPictureMyself (R.drawable.our_7));
        mPictureListMyself.add (getTitlePicture (3));
        mPictureListMyself.add (addPictureMyself (R.drawable.food_1));
        mPictureListMyself.add (addPictureMyself (R.drawable.food_2));
        mPictureListMyself.add (addPictureMyself (R.drawable.food_3));
        mPictureListMyself.add (addPictureMyself (R.drawable.food_4));
        mPictureListMyself.add (addPictureMyself (R.drawable.food_5));
        mPictureListMyself.add (addPictureMyself (R.drawable.food_6));
        mPictureListMyself.add (addPictureMyself (R.drawable.food_7));
        mPictureListMyself.add (getTitlePicture (4));
        mPictureListMyself.add (addPictureMyself (R.drawable.emoticon_1));
        mPictureListMyself.add (addPictureMyself (R.drawable.emoticon_2));
        mPictureListMyself.add (addPictureMyself (R.drawable.emoticon_3));
        mPictureListMyself.add (addPictureMyself (R.drawable.emoticon_4));
        mPictureListMyself.add (addPictureMyself (R.drawable.emoticon_5));
        mPictureListMyself.add (addPictureMyself (R.drawable.emoticon_6));
        mPictureListMyself.add (getTitlePicture (5));
    }

    //默认图片实例的设置方法
    private Picture addPictureMyself(int resourseId){
        Picture picture=new Picture ();
        picture.setImageId (resourseId);
        picture.setMyself (0);
        picture.setDescriptions (pictureDescriptions[random.nextInt (pictureDescriptions.length)]);
        return picture;
    }

    //标题的图片
    private Picture getTitlePicture(int index){
        Picture picture=new Picture ();
        picture.setMyself (0);
        switch (index){
            case 1:
                picture.setImageId (R.drawable.title_1);
                break;
            case 2:
                picture.setImageId (R.drawable.title_2);
                break;
            case 3:
                picture.setImageId (R.drawable.title_3);
                break;
            case 4:
                picture.setImageId (R.drawable.title_4);
                break;
            case 5:
                picture.setImageId (R.drawable.end);
                picture.setDescriptions ("坐等明年的故事!!!");
                break;
            default:
                break;
        }
        return picture;
    }
}