package com.angelhack.growafric.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.angelhack.growafric.R;
import com.angelhack.growafric.helper.CameraUtils;

public class FragmentPhoto extends Fragment {

    private OnCameraClickListener listener;

    public interface OnCameraClickListener {
        void onCameraClicked();
    }


    public void setListener(OnCameraClickListener listener){
        this.listener = listener;
    }

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    // key to store image path in savedInstance state
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;

    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";

    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";

    private static String imageStoragePath;

    private TextView txtDescription;
    private ImageView imgPreview;
    private VideoView videoPreview;
    private Button btnCapturePicture;
    private TextView photohint;

    public static FragmentIntro newInstance() {
        FragmentIntro fragment = new FragmentIntro();
        return fragment;
    }

    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_photo, container, false);


        // Checking availability of the camera
        if (!CameraUtils.isDeviceSupportCamera(getActivity())) {
            Toast.makeText(getActivity(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device doesn't have camera
            getActivity().finish();
        }

       // txtDescription = rootView.findViewById(R.id.txt_desc);
        imgPreview = rootView.findViewById(R.id.imgPreview);
        photohint = rootView.findViewById(R.id.photohint);
        imgPreview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              listener.onCameraClicked();
            }
        });

        return rootView;
}

    public void setImage(Bitmap bitmap){
        //todo handle image setting
        Log.e("j", "l");
        imgPreview.setImageBitmap(bitmap);
        photohint.setVisibility(View.GONE);

    }

}
