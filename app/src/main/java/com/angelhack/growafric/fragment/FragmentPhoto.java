package com.angelhack.growafric.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.angelhack.growafric.R;
import com.angelhack.growafric.helper.CameraUtils;
import com.angelhack.growafric.models.BusinessModel;
import com.angelhack.growafric.views.IView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.angelhack.growafric.presenters.BusinessPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentPhoto extends Fragment implements IView{

    private OnCameraClickListener listener;
    private Bitmap bitmap2;

    @Override
    public void onPostSuccessful(@NotNull BusinessModel model) {
        Log.e("business", model.getName()+model.getFounder_name());
    }

    @Override
    public void onPostError(@NotNull BusinessModel model, @NotNull String error) {

    }

    @Override
    public void onProcessing(int percentage) {

    }

    @Override
    public void onRetrievalSuccess(@NotNull ArrayList<BusinessModel> businessModels) {

    }

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

    private TextInputEditText businessName, address, revenue_generated, skills, previous_business_revenue, amount_needed, current_revenue, accountBVN, sociallinks;

    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;
    private BusinessModel businessModel;
    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";

    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";

    private static String imageStoragePath;

    private TextView txtDescription;
    private CircularImageView imgPreview;
    private VideoView videoPreview;
    private Button register;
    private TextView photohint;
    private SharedPreferences sharedPref;
    private String _businessName, _address, _revenue_generated, _skills, _previous_business_revenue, _amount_needed, _current_revenue, _accountBVN, _sociallinks;


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
        register = rootView.findViewById(R.id.register);
        businessName = rootView.findViewById(R.id.businessName);
        address = rootView.findViewById(R.id.address);
        revenue_generated = rootView.findViewById(R.id.revenueGenerated);
        skills = rootView.findViewById(R.id.skills);
        previous_business_revenue = rootView.findViewById(R.id.previousBusinessRevenue);
        amount_needed = rootView.findViewById(R.id.amountNeeded);
        current_revenue = rootView.findViewById(R.id.currentrevenue);
        accountBVN = rootView.findViewById(R.id.accountBvn);
        sociallinks = rootView.findViewById(R.id.socialMediaLink);

        imgPreview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              listener.onCameraClicked();
            }
        });
        sharedPref = getActivity().getBaseContext().getSharedPreferences("com.angelhack.growafric.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = sharedPref.getString("userid", "");
                String displayname = sharedPref.getString("displayname", "");
                String email = sharedPref.getString("email", "");
                _businessName = businessName.getText().toString();
                _address = address.getText().toString();
                _revenue_generated = revenue_generated.getText().toString();
                _skills = skills.getText().toString();
                _previous_business_revenue = previous_business_revenue.getText().toString();
                _amount_needed = amount_needed.getText().toString();
                _current_revenue = current_revenue.getText().toString();
                _accountBVN = accountBVN.getText().toString();
                _sociallinks = sociallinks.getText().toString();


                //  private TextInputEditText businessName, address, revenue_generated, skills, previous_business_revenue, current_revenue;
                businessModel = new BusinessModel(userid, _businessName, _address, _revenue_generated, _amount_needed, displayname, _skills, _previous_business_revenue, _accountBVN, email, _sociallinks, "" );

                //Make the call
                new BusinessPresenter(FragmentPhoto.this).startPostData(businessModel, bitmap2);

            }
        });

        return rootView;
}

    public void setImage(Bitmap bitmap){
        //todo handle image setting
        imgPreview.setImageBitmap(bitmap);
        bitmap2 = bitmap;
        photohint.setVisibility(View.GONE);
        imgPreview.setAlpha(1);

    }

}
