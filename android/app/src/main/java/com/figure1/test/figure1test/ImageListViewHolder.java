package com.figure1.test.figure1test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vinayjadhav on 2018-03-11.
 */

public class ImageListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    private ArrayList<ImageData> imageDataArrayList;
    private Context context;

    TextView textViewDescription;
    ImageView imageView;

    public ImageListViewHolder(View itemView, Context context, ArrayList<ImageData> imageDataArrayList) {
        super(itemView);

        this.imageDataArrayList = imageDataArrayList;
        this.context = context;

        textViewDescription = itemView.findViewById(R.id.textViewDescription);
        imageView = itemView.findViewById(R.id.imageView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int position = getAdapterPosition();
        ImageData imageData = this.imageDataArrayList.get(position);
        Intent intent = new Intent(this.context, ImageDetailsActivity.class);
        intent.putExtra(ImageDetailsActivity.IMAGE_URL, imageData.getLink());
        intent.putExtra(ImageDetailsActivity.IMAGE_DESCRIPTION, imageData.getDescription());
        context.startActivity(intent);
    }
}
