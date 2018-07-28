package com.angelhack.growafric;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.angelhack.growafric.adapter.ViewPagerAdapter;
import com.angelhack.growafric.fragment.FragmentDetails;
import com.angelhack.growafric.fragment.FragmentIntro;
import com.angelhack.growafric.fragment.FragmentPhoto;
import com.angelhack.growafric.helper.CameraUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;

public class Register extends AppCompatActivity implements FragmentPhoto.OnCameraClickListener  {

    private TextView mTextMessage;
    private ViewPager view_pager;
    private FragmentIntro fragmentIntro;
    private FragmentPhoto fragmentPhoto;
    private FragmentDetails fragmentDetails;
    private static String imageStoragePath;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final String IMAGE_EXTENSION = "jpg";
    private ImageView imgPreview;
    public static final int BITMAP_SAMPLE_SIZE = 8;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Bitmap bitmap;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_intro:
                 //   mTextMessage.setText(R.string.title_intro);
                    view_pager.setCurrentItem(0);
                    return true;
                case R.id.navigation_photo:
                //    mTextMessage.setText(R.string.title_photo);
                    view_pager.setCurrentItem(1);
                    return true;
                case R.id.navigation_details:
                //    mTextMessage.setText(R.string.title_details);
                    view_pager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        view_pager = findViewById(R.id.view_pager);
        setupViewPager(view_pager);
    }

    private void setupViewPager(ViewPager viewPager)  {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        fragmentIntro = new FragmentIntro();

        fragmentPhoto = new FragmentPhoto();
        fragmentPhoto.setListener(this);

        fragmentDetails = new FragmentDetails();

        adapter.addFragment(fragmentIntro);
        adapter.addFragment(fragmentPhoto);
        adapter.addFragment(fragmentDetails);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onCameraClicked() {
        if (CameraUtils.checkPermissions(getApplicationContext())) {
            captureImage();
            fragmentPhoto.setImage(bitmap);
        } else {
            requestCameraPermission(MEDIA_TYPE_IMAGE);
        }

    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
            Log.e("path", imageStoragePath+"");
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    private void restoreFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_IMAGE_STORAGE_PATH)) {
                imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
                if (!TextUtils.isEmpty(imageStoragePath)) {
                    if (imageStoragePath.substring(imageStoragePath.lastIndexOf(".")).equals("." + IMAGE_EXTENSION)) {
                        previewCapturedImage();
                    }
                }
            }
        }
    }

    private void previewCapturedImage() {
        try {
            bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);
            fragmentPhoto.setImage(bitmap);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void requestCameraPermission(final int type) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                captureImage();
                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image


        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);
                Log.e("j", "l");
                // successfully captured the image
                // display it in image view
                previewCapturedImage();

            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(Register.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

}
