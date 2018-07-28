package com.angelhack.growafric;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.angelhack.growafric.adapter.ViewPagerAdapter;
import com.angelhack.growafric.fragment.FragmentDetails;
import com.angelhack.growafric.fragment.FragmentIntro;
import com.angelhack.growafric.fragment.FragmentPhoto;

public class Register extends AppCompatActivity {

    private TextView mTextMessage;
    private ViewPager view_pager;
    private FragmentIntro fragmentIntro;
    private FragmentPhoto fragmentPhoto;
    private FragmentDetails fragmentDetails;
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
        fragmentDetails = new FragmentDetails();

        adapter.addFragment(fragmentIntro);
        adapter.addFragment(fragmentPhoto);
        adapter.addFragment(fragmentDetails);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }
}
