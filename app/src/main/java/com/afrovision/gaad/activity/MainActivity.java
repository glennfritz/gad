package com.afrovision.gaad.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.afrovision.gaad.R;
import com.afrovision.gaad.adapter.PagerAdapter;
import com.afrovision.gaad.fragment.LearningLeaders;
import com.afrovision.gaad.fragment.SkillLeaders;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    ViewPager view_pager;
    PagerAdapter adapter;
    TabLayout tabLayout;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        view_pager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);
        adapter = new PagerAdapter (getSupportFragmentManager ());
        adapter.addFragment(new LearningLeaders(),getString(R.string.learning_leader ));
        adapter.addFragment(new SkillLeaders(),getString(R.string.skill_leader ));
        view_pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(view_pager);
        tabLayout.setTabGravity (TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager (view_pager);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SubmitActivity.class));
            }
        });

    }
}
