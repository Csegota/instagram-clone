package com.android.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
//import android.support.design.widget.TabLayout;

import android.os.Bundle;

public class SocialMediaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewpager;
    //private TabLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
    }
}
