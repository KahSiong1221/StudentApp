package com.example.studentapp_eoc_soc.library;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;

import androidx.viewpager2.widget.ViewPager2;

import com.example.studentapp_eoc_soc.R;
import com.google.android.material.tabs.TabLayout;


public class viewMyBookingActivity extends AppCompatActivity{

   private TabLayout tabLayout;
   private ViewPager2 viewPager2;
   private  mybookingFragmentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaymybooking);


        tabLayout = (TabLayout)findViewById(R.id.tabs);
        viewPager2 = (ViewPager2)findViewById(R.id.viewpager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new mybookingFragmentAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Computer"));
        tabLayout.addTab(tabLayout.newTab().setText("Room"));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        Button exitMyBookingButton= (Button)findViewById(R.id.exitMyBookingButton);
        exitMyBookingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();
            }
        });
    }

}
