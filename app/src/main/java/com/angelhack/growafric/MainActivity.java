package com.angelhack.growafric;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button investBtn;
    private static String imageStoragePath;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final String IMAGE_EXTENSION = "jpg";
    private ImageView imgPreview;
    public static final int BITMAP_SAMPLE_SIZE = 8;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        investBtn = findViewById(R.id.investBtn);
        imgPreview = findViewById(R.id.imgPreview);

        final Intent intent = new Intent(this, Home.class);
        investBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                finish();
            }
        });

    }


}
