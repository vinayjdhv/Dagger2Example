package com.figure1.test.figure1test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by vinayjadhav on 2018-03-11.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showImages(View view) {
        Intent intent = new Intent(this, ImageGalleryActivity.class);
        startActivity(intent);
    }
}
