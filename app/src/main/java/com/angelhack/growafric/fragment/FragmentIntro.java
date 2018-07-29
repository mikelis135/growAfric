package com.angelhack.growafric.fragment;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angelhack.growafric.R;
import com.angelhack.growafric.adapter.BusinessAdapter;
import com.angelhack.growafric.helper.CustomItemClickListener;
import com.angelhack.growafric.helper.GridSpacingItemDecoration;
import com.angelhack.growafric.models.BusinessModel;
import com.angelhack.growafric.views.IView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.angelhack.growafric.presenters.BusinessPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentIntro extends Fragment implements IView{
    public FragmentIntro() {
    }

    public static FragmentIntro newInstance() {
        FragmentIntro fragment = new FragmentIntro();
        return fragment;
    }

    private View rootView;
    private RecyclerView recyclerView;
    private BusinessAdapter businessAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private BusinessModel businessModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_intro, container, false);
        mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmerAnimation();

        recyclerView = rootView.findViewById(R.id.rv_business);
        RecyclerView.LayoutManager mLayoutManager = null;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            mLayoutManager = new GridLayoutManager(getActivity(), 3);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
        }
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        businessModel = new BusinessModel();
            //Retrieve all items in the list
        new BusinessPresenter(this).retrieveData();
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    private int dpToPx() {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, r.getDisplayMetrics()));
    }

    @Override
    public void onPostSuccessful(@NotNull BusinessModel model) {

    }

    @Override
    public void onPostError(@NotNull BusinessModel model, @NotNull String error) {

    }

    @Override
    public void onProcessing(int percentage) {

    }

    @Override
    public void onRetrievalSuccess(@NotNull final ArrayList<BusinessModel> businessModels) {

        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.INVISIBLE);
        businessAdapter = new BusinessAdapter(getActivity(), businessModels, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String email = "Email : "+ businessModels.get(position).getEmail();
                String founder_name =   "Founder : "+  businessModels.get(position).getFounder_name();
                String name =  "Name : "+   businessModels.get(position).getName();
                String amountNeeded =  "Amount : " +    businessModels.get(position).getAmountNeeded();
                String details = founder_name+"\n\n"+name+"\n\n"+amountNeeded+"\n\n"+email;
                Snackbar.make(rootView, details, Snackbar.LENGTH_SHORT).show();

                Snackbar snackbar = Snackbar.make(rootView, "", 8000);
                View snackbarView = snackbar.getView();
                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setText(details);
                textView.setMaxLines(10);
                snackbar.show();

                    Log.e ("b", details);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });
        businessAdapter.setItemss(businessModels);
        recyclerView.setAdapter(businessAdapter);

        businessAdapter.notifyDataSetChanged();
    }
}