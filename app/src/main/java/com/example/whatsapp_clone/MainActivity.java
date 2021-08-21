package com.example.whatsapp_clone;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabsAccessorAdapter tabsAccessorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Amra Amrai to");


        viewPager = (ViewPager) findViewById(R.id.main_tabs_pager);
        tabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsAccessorAdapter);

        tabLayout = (TabLayout) findViewById(R.id.main_page_tabs);
        tabLayout.setupWithViewPager(viewPager);



    }


}