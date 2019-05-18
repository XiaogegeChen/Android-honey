package com.xiaogege.honey.adapter;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaogege.honey.R;
import com.xiaogege.honey.ui.Picture;

import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {
    private List<Picture> mPictureList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View pictureView;
        ImageView pictureImageView;
        TextView pictureTextView;

        public ViewHolder(View view){
            super(view);
            pictureView=view;
            pictureImageView=view.findViewById (R.id.picture_image);
            pictureTextView=view.findViewById (R.id.picture_descriptions);
        }
    }

    public PictureAdapter(List<Picture> pictureList){
        mPictureList=pictureList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view=LayoutInflater.from (parent.getContext ()).inflate (R.layout.picture_item,parent,false);
        final ViewHolder viewHolder=new ViewHolder (view);
        viewHolder.pictureView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition ();
                Picture picture=mPictureList.get(position);
                View viewDiag=LayoutInflater.from(parent.getContext ()).inflate (R.layout.picture_alert_layout,parent,false);
                ImageView image=viewDiag.findViewById (R.id.picture_alert_image);
                if(picture.getMyself ()==0){
                    image.setImageResource (picture.getImageId ());
                }else if (picture.getMyself ()==1){
                    Bitmap bitmap=BitmapFactory.decodeFile (picture.getImagePath ());
                    image.setImageBitmap (bitmap);
                }
                new AlertDialog.Builder (parent.getContext ()).setView (viewDiag).setPositiveButton ("OK",null).show ();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,int position){
        Picture picture=mPictureList.get(position);
        viewHolder.pictureTextView.setText (picture.getDescriptions ());
        if(picture.getMyself ()==0){
            viewHolder.pictureImageView.setImageResource (picture.getImageId ());
        }else if(picture.getMyself ()==1){
            Bitmap bitmap=BitmapFactory.decodeFile (picture.getImagePath ());
            viewHolder.pictureImageView.setImageBitmap (bitmap);
        }
    }

    @Override
    public int getItemCount(){
        return mPictureList.size ();
    }
}
