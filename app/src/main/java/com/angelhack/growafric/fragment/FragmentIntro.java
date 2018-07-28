package com.angelhack.growafric.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angelhack.growafric.R;

public class FragmentIntro extends Fragment {
    public FragmentIntro() {
    }

    public static FragmentIntro newInstance() {
        FragmentIntro fragment = new FragmentIntro();
        return fragment;
    }

    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_intro, container, false);

        return rootView;
    }
}