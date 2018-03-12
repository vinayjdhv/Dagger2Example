package com.figure1.test.figure1test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vinayjadhav on 2018-03-11.
 */

public class ImageListAdapter extends RecyclerView.Adapter<ImageListViewHolder> {

    private Context context;

    private ArrayList<ImageData> imageDataArrayList;

    public ImageListAdapter(Context context, ArrayList<ImageData> imageDataArrayList) {
        this.context = context;
        this.imageDataArrayList = imageDataArrayList;
    }

    @Override
    public ImageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_images, null);
        return new ImageListViewHolder(view, context, imageDataArrayList);

    }

    @Override
    public void onBindViewHolder(ImageListViewHolder holder, int position) {

        ImageData imageData = imageDataArrayList.get(position);
        if(imageData.getDescription() != null && !imageData.getDescription().equals("null")) {
            holder.textViewDescription.setText(imageData.getDescription());
        }

        holder.imageView.setImageResource(R.drawable.no_image_available);

        Picasso.get().
                load(getThumbnailUrl(imageData.getLink())).error(R.drawable.no_image_available).
                into(holder.imageView);

    }



    private String getThumbnailUrl(String link) {


        String thumnailImgURL = link;
        if(link.endsWith(".jpg")){
            thumnailImgURL = link.replace(".jpg", "l.jpg" );
        }

        if(link.endsWith(".jpeg")){
            thumnailImgURL = link.replace(".jpeg", "l.jpeg" );
        }
        if(link.endsWith(".gif")){
            thumnailImgURL = link.replace(".gif", "l.gif" );
        }

        if(link.endsWith(".png")){
            thumnailImgURL = link.replace(".png", "l.png" );
        }

        if(link.endsWith(".mp4")){
            thumnailImgURL = link.replace(".mp4", "l.mp4" );
        }

        return thumnailImgURL;

    }

    @Override
    public int getItemCount() {
        return imageDataArrayList.size();
    }

    public void setImageDataArrayList(ArrayList<ImageData> imageDataArrayList) {
        this.imageDataArrayList = imageDataArrayList;
    }
}
