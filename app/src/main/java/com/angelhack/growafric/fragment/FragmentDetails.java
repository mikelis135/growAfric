package com.angelhack.growafric.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angelhack.growafric.R;


public class FragmentDetails extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView foundersName, businessName, email;

    public FragmentDetails() {

    }

    private SharedPreferences sharedPref;
    public static FragmentDetails newInstance(String param1, String param2) {
        FragmentDetails fragment = new FragmentDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_details, container, false);
        foundersName = v.findViewById(R.id.foundersName);
        businessName = v.findViewById(R.id.businessName);
        email = v.findViewById(R.id.email);
        sharedPref = getActivity().getBaseContext().getSharedPreferences("com.angelhack.growafric.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        String displayname = sharedPref.getString("displayname", "");
        String emailname = sharedPref.getString("email", "");

        foundersName.setText(displayname);
        businessName.setText("Member, growAfric");
        email.setText(emailname);

        return  v;
    }
    
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

        }