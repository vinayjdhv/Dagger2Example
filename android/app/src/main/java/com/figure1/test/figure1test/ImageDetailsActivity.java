package com.figure1.test.figure1test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by vinayjadhav on 2018-03-11.
 *
 * This activity displays a image details
 */

public class ImageDetailsActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView descriptionTextView;

    public static String IMAGE_DESCRIPTION = "IMAGE_DESCRIPTION";
    public static String IMAGE_URL = "IMAGE_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        imageView = (ImageView) findViewById(R.id.image);
        descriptionTextView = (TextView) findViewById(R.id.image_description);

        String description = getIntent().getStringExtra(IMAGE_DESCRIPTION);
        if(!description.isEmpty() && !description.equals("null")) {
            descriptionTextView.setText(description);
        }

        String imageURL = getIntent().getStringExtra(IMAGE_URL);

        Picasso.get().
                load(getHighThumbnailUrl(imageURL)).error(R.drawable.no_image_available).
                into(imageView);
    }

    /**
     * This is just to avoid a no image when its a video
     *
     * @param link
     * @return
     */
    private String getHighThumbnailUrl(String link) {

        String thumnailImgURL = link;
        if(link.endsWith(".mp4")){
            thumnailImgURL = link.replace(".mp4", "h.mp4" );
        }

        return thumnailImgURL;
    }
}
